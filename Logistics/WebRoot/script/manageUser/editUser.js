
var _win_edit_MU = null;
var _mask_edit_MU = null;
function onEditUser(userID)
{
	if(null == userID) 
		return;
	if(null == _win_edit_MU)
	{
	    createWindow_edit_MU();
	}else
	{
		_formPanel_editUser_MU.getForm().reset();
	}
	
	 _ds_roles_MU.load({ 
	 	callback: function(record,option,success){
					STORE_CALLBACK(_ds_roles_MU.reader.message,_ds_roles_MU.reader.valid,success);
				 } 
	});
	_win_edit_MU.setPagePosition(180,80);
	_mask_edit_MU.show();
    _formPanel_editUser_MU.getForm().load({
    	url: 'getAUser.action',
    	method: 'POST',
    	params: {uID: userID},
    	success: function(form,action){
			var tmp_cur = _formPanel_editUser_MU.findById('id_cur_uPassword_edit_MU');
			var tmp_old = _formPanel_editUser_MU.findById('id_old_uPassword_edit_MU');
			tmp_old.setValue(tmp_cur.getValue());
			tmp_cur.setValue('xxxxxx');	
			var tmp_oldID = _formPanel_editUser_MU.findById('id_oldUID_edit_MU');
			var tmp_curID = _formPanel_editUser_MU.findById('id_curUID_edit_MU');
			tmp_oldID.setValue(tmp_curID.getValue());
			_mask_edit_MU.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_edit_MU.hide();
    	}
    });
    _win_edit_MU.show();
}

function createWindow_edit_MU()
{
	_win_edit_MU = new Ext.Window({
        title: '修改用户',
        iconCls: 'editUser',
        width: 320,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_editUser_MU,
        listeners: LISTENER_WIN_MOVE
    });
    _win_edit_MU.show();
    _mask_edit_MU = new Ext.LoadMask(_win_edit_MU.body, {msg:"正在载入,请稍后..."});
}


var _formPanel_editUser_MU = new Ext.FormPanel({
	
		layout: 'form',
		style: 'margin:0px',
		frame: true,
		labelAlign: 'right',
		bodyStyle: PADDING,
		autoHeight: true,
		autoScoll: true,
		labelWidth: 70,
		border: false,
		buttonAlign: 'center',
		
		buttons:[
		{
			text: '保存',
			iconCls: 'commonSave',
			handler: function()
			{
				if(_formPanel_editUser_MU.getForm().isValid())
				{
					var tmp_cur = _formPanel_editUser_MU.findById('id_cur_uPassword_edit_MU');
					if('xxxxxx' == tmp_cur.getValue())
					{
						var tmp_old = _formPanel_editUser_MU.findById('id_old_uPassword_edit_MU');
						tmp_cur.setValue(tmp_old.getValue());
					}
					else
						tmp_cur.setValue(hex_md5(tmp_cur.getValue()));
					tmp_old.setValue('');
					_formPanel_editUser_MU.getForm().submit({
						url: 'updateUser.action',
						waitTitle:"保存数据",
						waitMsg: '正在保存...',
						success:function(form,action){
							_formPanel_editUser_MU.getForm().reset();
							_win_edit_MU.hide();
							_grid_MU.getStore().reload();
						},
						failure: function(form,action){
							_formPanel_editUser_MU.findById('id_cur_uPassword_edit_MU').setValue('');
							FORM_FAILURE_CALLBACK(form,action,"数据保存失败");
						}
					});
				}
			}
		},{
			text: '取消',
			iconCls: 'commonCancel',
			handler: function(){ _win_edit_MU.hide(); }
		}],
		
		items:[
		{//Row 0
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'textfield',
						fieldLabel: '创建日期',
						name: 'uDateCreated',
						readOnly: true,
						width: 150
					}]
				}]
		},
		{//Row 1
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'textfield',
						id: 'id_curUID_edit_MU',
						fieldLabel: '登录账号',
						name: 'uID',
						readOnly: true,
						regex: REGEX_UID,
						regexText : REGEX_UID_TEXT,
						width: 150
					}]
				}]
			},
			{//Row 1+
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: [
					{
						id: 'id_oldUID_edit_MU',
						xtype: 'textfield',
						name: 'oldUID',
						hidden: true
					}]
				}]
			},
			{//Row 2
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '密码重置',
						id: 'id_cur_uPassword_edit_MU',
						regex: REGEX_UPASSWORD,
						regexText: REGEX_UPASSWORD_TEXT,
						inputType: 'password',
						allowBlank: false,
						selectOnFocus : true,
						width: 150,
						name: 'uPassword'
					}]
				}]
			},
			{//Row 2+
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'displayfield',
						id: 'id_old_uPassword_edit_MU',
						hidden: true
					}]
				}]
			},
			{//Row 3
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : _ds_roles_MU,
					       valueField : 'roleID',
					       displayField : 'roleName',
					       forceSelection : true,
					       hiddenName : 'rID',
					       editable : false,
					       triggerAction : 'all',
					       mode: 'local',
					       emptyText: '请选择角色',
					       allowBlank : false,
					       fieldLabel : '账号角色',
					       width:　150
					})]
				}]
			},
			{//Row 4
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : new Ext.data.SimpleStore({      
					              fields : ["displayText"],
					              data : [['启用'], ['禁用']]
					       }),
					       
					       valueField : 'displayText',
					       displayField : 'displayText',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'UState',
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : true,
					       fieldLabel : '账号状态',
					       value: '启用',
					       width:　150
					})]
				}]
			}]
});
