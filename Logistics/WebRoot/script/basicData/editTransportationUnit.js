
var _win_edit_TU = null;
var _mask_edit_TU = null;
function onEditTransportationUnit(unitID)
{
	if(null == _win_edit_TU)
	{
	    createWindow_edit_TU();
	}else
	{
		_formPanel_editTransUnit_TU.getForm().reset();
	}
	_mask_edit_TU.show();
	_formPanel_editTransUnit_TU.getForm().load({
    	url: 'FreightContractor.queryOne.action',
    	method: 'POST',
    	params: {'freightContractorDTO.freightContractorID': unitID},
    	success: function(form,action){
    		_mask_edit_TU.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_edit_TU.hide();
    	}
    });
	_win_edit_TU.setPagePosition(GET_WIN_X(_win_edit_TU.width),GET_WIN_Y_M(250));
    _win_edit_TU.show();
}

function createWindow_edit_TU()
{
	_win_edit_TU = new Ext.Window({
        title: '修改承运单位',
        iconCls: 'commonEditSheet',
        width: 675,
        autoHeight: true,
        autoScroll: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_editTransUnit_TU,
        listeners: LISTENER_WIN_MOVE
    });
    _win_edit_TU.show();
    _mask_edit_TU = new Ext.LoadMask(_win_edit_TU.body, {msg:"正在载入,请稍后..."});
}

var _formPanel_editTransUnit_TU = new Ext.FormPanel({
	
	layout: 'form',
	style: 'margin:0px',
	frame: true,
	labelAlign: 'right',
	bodyStyle: PADDING,
	labelWidth: 70,
	border: false,
	buttonAlign: 'center',
	
	buttons:[
	{
		text: '保存',
		iconCls: 'commonSave',
		handler: function()
		{
			if(_formPanel_editTransUnit_TU.getForm().isValid())
			{
				_formPanel_editTransUnit_TU.getForm().submit({
					url: 'FreightContractor.update.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_editTransUnit_TU.getForm().reset();
						_win_edit_TU.hide();
						_grid_TU.getStore().reload();
					},
					failure: function(form,action){
						FORM_FAILURE_CALLBACK(form,action,"数据保存失败");
					}
				});
			}
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler: function(){ _win_edit_TU.hide(); }
	}],
	
	items:[
	{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '1',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					name: 'freightContractorDTO.freightContractorID',
					readOnly: true,
					fieldLabel: '单位编号',
					width: 200
				}]
			}]
		},
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					allowBlank: false,
					name: 'freightContractorDTO.name',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
					fieldLabel: '单位名称',
					width: 200
				}]
			},
			{//Column 2
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [{
						xtype: 'textfield',
						fieldLabel: '联系人',
						name: 'freightContractorDTO.contact',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: 200
					}]
			}]
		},
		{//Row 2
			layout: 'column',
			border: false,
			items: [
			{//Column 1
					columnWidth: '0.5',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '联系电话',
						name: 'freightContractorDTO.phone',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: 200
				}]
			},
			{//Column 2
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [{
						xtype: 'textfield',
						fieldLabel: '邮件',
						name: 'freightContractorDTO.email',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: 200
					}]
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
					items: [{
						xtype: 'textfield',
						fieldLabel: '地址',
						name: 'freightContractorDTO.address',
						regex: REGEX_COMMON_M,
						regexText: REGEX_COMMON_M_TEXT,
						width: 512
					}]
			}]
		},
		{//Row 5
			layout: 'column',
			border: false,
			items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '备注',
						name: 'freightContractorDTO.remarks',
						regex: REGEX_COMMON_M,
						regexText: REGEX_COMMON_M_TEXT,
						width: 512
					}]
			}]
		}
	]
});

