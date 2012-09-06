
var _win_create_MU = null;

function onCreateUser()
{
	if(null == _win_create_MU)
	{
	    createWindow_create_MU();
	}
	else
	{
		_formPanel_createUser_MU.getForm().reset();
	}
    _ds_roles_MU.load({
    	callback:function(record,option,success){
					STORE_CALLBACK(_ds_roles_MU.reader.message,_ds_roles_MU.reader.valid,success);
				 } 
	});
	_win_create_MU.setPagePosition(GET_WIN_X(_win_create_MU.width),GET_WIN_Y());
    _win_create_MU.show();
}

function createWindow_create_MU()
{
	_win_create_MU = new Ext.Window({
        title: '新建用户',
        iconCls: 'addUser',
        width: 320,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createUser_MU,
       	listeners: LISTENER_WIN_MOVE
    });
}

var _ds_roles_MU = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'getAllRoles.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'roleList'
			}, 
			[{
				name : 'roleID'
			},{
				name : 'roleName'
			}]
		)
});

var _formPanel_createUser_MU = new Ext.FormPanel({
	
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
			if(_formPanel_createUser_MU.getForm().isValid())
			{
				var tmp = _formPanel_createUser_MU.findById('id_uPassword_create_MU');
				tmp.setValue(hex_md5(tmp.getValue()));
				_formPanel_createUser_MU.getForm().submit({
					url: 'createUser.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_createUser_MU.getForm().reset();
						_formPanel_createUser_MU.findById('cur_create_MU').focus();
						_grid_MU.getStore().reload();
					},
					failure: function(form,action){
						_formPanel_createUser_MU.findById('id_uPassword_create_MU').setValue('');
						_formPanel_createUser_MU.findById('cur_create_MU').setValue('');
						FORM_FAILURE_CALLBACK(form,action,"数据保存失败");
					}
				});
			}
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler: function(){ _win_create_MU.hide(); }
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
						readOnly: true,
						value: new Date().format('Y-m-d'),
						name: 'uDateCreated',
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
						id: 'cur_create_MU',
						xtype: 'textfield',
						fieldLabel: '登录账号',
						regex: REGEX_UID,
						regexText : REGEX_UID_TEXT,
						name: 'uID',
						allowBlank: false,
						width: 150
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
						id: 'id_uPassword_create_MU',
						fieldLabel: '登录密码',
						regex: REGEX_UPASSWORD,
						regexText: REGEX_UPASSWORD_TEXT,
						allowBlank: false,
						selectOnFocus : true,
						inputType: 'password',
						name: 'uPassword',
						width: 150
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
					       mode : 'local',
					       hiddenName : 'rID',
					       editable : false,
					       triggerAction : 'all',
					       emptyText: '请选择角色',
					       selectOnFocus :true,
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
					       hiddenName : 'uState',
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

