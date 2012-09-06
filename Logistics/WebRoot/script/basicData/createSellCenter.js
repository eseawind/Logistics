
var _win_create_SCTER = null;

function onCreateSellCenter()
{
	if(null == _win_create_SCTER)
	{
	    createWindow_create_SCTER(); 
	}
	else
	{
		_formPanel_createSellCenter_SCTER.getForm().reset();
	}
	_win_create_SCTER.setPagePosition(GET_WIN_X(_win_create_SCTER.width),GET_WIN_Y());
    _win_create_SCTER.show();
}

function createWindow_create_SCTER()
{
	_win_create_SCTER = new Ext.Window({
        title: '新建销售中心',
        iconCls: 'commonCreateSheet',
        width: 375,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createSellCenter_SCTER,
        listeners: LISTENER_WIN_MOVE
    });
}

var _formPanel_createSellCenter_SCTER = new Ext.FormPanel({
	
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
			if(_formPanel_createSellCenter_SCTER.getForm().isValid())
			{
				_formPanel_createSellCenter_SCTER.getForm().submit({
					url: 'SellCenter.insert.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_createSellCenter_SCTER.getForm().reset();
						_formPanel_createSellCenter_SCTER.findById('tab_id_SCTER').focus();
						_grid_SCTER.getStore().reload();
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
		handler: function(){ _win_create_SCTER.hide(); }
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
						fieldLabel: '销售中心',
						id: 'tab_id_SCTER',
						name: 'sellCenterDTO.sellCenterID',
						allowBlank: false,
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: 200
					}]
			}]
		}
	]
});
