
var _win_create_CWI = null;

function onCreateWorkerInfo()
{
	if(null == _win_create_CWI)
	{
	    createWindow_create_CWI();
	}
	else
	{
		_formPanel_createWokerInfo_CWI.getForm().reset();
	}
	_win_create_CWI.setPagePosition(GET_WIN_X(_win_create_CWI.width),GET_WIN_Y());
    _win_create_CWI.show();
}

function createWindow_create_CWI()
{
	_win_create_CWI = new Ext.Window({
        title: '新建员工信息',
        iconCls: 'commonCreateSheet',
        width: 380,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createWokerInfo_CWI,
        listeners: LISTENER_WIN_MOVE
    });
}

var _formPanel_createWokerInfo_CWI = new Ext.FormPanel({
	
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
			if(_formPanel_createWokerInfo_CWI.getForm().isValid())
			{
				_formPanel_createWokerInfo_CWI.getForm().submit({
					url: 'insertEmployee.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_createWokerInfo_CWI.getForm().reset();
						_formPanel_createWokerInfo_CWI.findById('tab_id_CWI').focus();
						_grid_MWI.getStore().reload();
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
		handler: function(){ _win_create_CWI.hide(); }
	}],
	
	items:[
			{//Row 2
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '姓名',
						id: 'tab_id_CWI',
						allowBlank: false,
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						name: 'Name',
						width:　200
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
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '部门',
						allowBlank: false,
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						name: 'Department',
						width:　200
					}]
				}]
			},
			{//Row 4
				layut: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '职位',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						allowBlank: false,
						name: 'Position',
						width:　200
					}]
				}]
			},
			{//Row 5
				layut: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '手机号码',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						name: 'CellPhone',
						width:　200
					}]
				}]
			},
			{//Row 6
				layut: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '电子邮件',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						name: 'Email',
						width:　200
					}]
				}]
			},
			{//Row 7
				layut: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '身份证号码',
						name: 'IDCard',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width:　200
					}]
				}]
			},
			{//Row 8
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textarea',
						fieldLabel: '备注信息',
						regex: REGEX_COMMON_M,
						regexText: REGEX_COMMON_M_TEXT,
						name: 'Remarks',
						width:　200
					}]
				}]
			}]
});

