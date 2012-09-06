
var _win_create_CARTP = null;

function onCreateCarType()
{
	if(null == _win_create_CARTP)
	{
	    createWindow_create_CARTP(); 
	}
	else
	{
		_formPanel_createCarType_CARTP.getForm().reset();
	}
	_win_create_CARTP.setPagePosition(GET_WIN_X(_win_create_CARTP.width),GET_WIN_Y());
    _win_create_CARTP.show();
}

function createWindow_create_CARTP()
{
	_win_create_CARTP = new Ext.Window({
        title: '新建车辆类型',
        iconCls: 'commonCreateSheet',
        width: 375,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createCarType_CARTP,
        listeners: LISTENER_WIN_MOVE
    });
}

var _formPanel_createCarType_CARTP = new Ext.FormPanel({
	
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
			if(_formPanel_createCarType_CARTP.getForm().isValid())
			{
				_formPanel_createCarType_CARTP.getForm().submit({
					url: 'CarType.insert.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_createCarType_CARTP.getForm().reset();
						_formPanel_createCarType_CARTP.findById('tab_id_CARTP').focus();
						_grid_CARTP.getStore().reload();
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
		handler: function(){ _win_create_CARTP.hide(); }
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
						fieldLabel: '类型名称',
						id: 'tab_id_CARTP',
						name: 'carTypeDTO.carTypeName',
						allowBlank: false,
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
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textarea',
						fieldLabel: '备注',
						name: 'carTypeDTO.remarks',
						regex: REGEX_COMMON_M,
						regexText: REGEX_COMMON_M_TEXT,
						width: 200
					}]
			}]
		}
	]
});

