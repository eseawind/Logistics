
var _win_edit_CARTP = null;
var _mask_edit_CARTP = null;
function onEditCarType(carTypeID)
{
	if(null == _win_edit_CARTP)
	{
	    createWindow_edit_CARTP(); 
	}
	else
	{
		_formPanel_editCarType_CARTP.getForm().reset();
	}
	_mask_edit_CARTP.show();
	_formPanel_editCarType_CARTP.getForm().load({
    	url: 'CarType.queryOne.action',
    	method: 'POST',
    	params: { 'carTypeDTO.carTypeID': carTypeID },
    	success: function(form,action){
			_mask_edit_CARTP.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_edit_CARTP.hide();
    	}
    });
	_win_edit_CARTP.setPagePosition(GET_WIN_X(_win_edit_CARTP.width),GET_WIN_Y());
    _win_edit_CARTP.show();
}

function createWindow_edit_CARTP()
{
	_win_edit_CARTP = new Ext.Window({
        title: '修改车辆类型',
        iconCls: 'commonEditSheet',
        width: 375,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_editCarType_CARTP,
        listeners: LISTENER_WIN_MOVE
    });
    _win_edit_CARTP.show();
    _mask_edit_CARTP = new Ext.LoadMask(_win_edit_CARTP.body, {msg:"正在载入,请稍后..."});
}


var _formPanel_editCarType_CARTP = new Ext.FormPanel({
	
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
			if(_formPanel_editCarType_CARTP.getForm().isValid())
			{
				_formPanel_editCarType_CARTP.getForm().submit({
					url: 'CarType.update.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_editCarType_CARTP.getForm().reset();
						_win_edit_CARTP.hide();
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
		handler: function(){ _win_edit_CARTP.hide(); }
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
						hidden: true,
						name: 'carTypeDTO.carTypeID'
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
						fieldLabel: '类型名称',
						name: 'carTypeDTO.carTypeName',
						allowBlank: false,
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: 200
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

