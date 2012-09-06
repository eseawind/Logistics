
var _win_create_CCTER = null;

function onCreateCostCenter()
{
	if(null == _win_create_CCTER)
	{
	    createWindow_create_CCTER(); 
	}
	else
	{
		_formPanel_createCostCenter_CCTER.getForm().reset();
	}
	_win_create_CCTER.setPagePosition(GET_WIN_X(_win_create_CCTER.width),GET_WIN_Y());
    _win_create_CCTER.show();
}

function createWindow_create_CCTER()
{
	_win_create_CCTER = new Ext.Window({
        title: '新建成本中心',
        iconCls: 'commonCreateSheet',
        width: 375,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createCostCenter_CCTER,
        listeners: LISTENER_WIN_MOVE
    });
}

var _formPanel_createCostCenter_CCTER = new Ext.FormPanel({
	
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
			if(_formPanel_createCostCenter_CCTER.getForm().isValid())
			{
				_formPanel_createCostCenter_CCTER.getForm().submit({
					url: 'CostCenter.insert.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_createCostCenter_CCTER.getForm().reset();
						_formPanel_createCostCenter_CCTER.findById('tab_id_CCTER').focus();
						_grid_CCTER.getStore().reload();
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
		handler: function(){ _win_create_CCTER.hide(); }
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
						fieldLabel: '成本中心',
						id: 'tab_id_CCTER',
						name: 'costCenterDTO.costCenterID',
						allowBlank: false,
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: 200
					}]
			}]
		}
	]
});

