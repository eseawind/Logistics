
var _win_create_TW = null;

function onCreateTransportationWay()
{
	if(null == _win_create_TW)
	{
	    createWindow_create_TW(); 
	}else
	{
		_formPanel_createWay_TW.getForm().reset();
	}
	_win_create_TW.setPagePosition(GET_WIN_X(_win_create_TW.width),GET_WIN_Y());
    _win_create_TW.show();
}

function createWindow_create_TW()
{
	_win_create_TW = new Ext.Window({
        title: '新建路线',
        iconCls: 'commonCreateSheet',
        width: 375,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createWay_TW,
        listeners: LISTENER_WIN_MOVE
    });
}

var _formPanel_createWay_TW = new Ext.FormPanel({
	
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
			if(_formPanel_createWay_TW.getForm().isValid())
			{
				_formPanel_createWay_TW.getForm().submit({
					url: 'FreightRoute.insert.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_grid_TW.getStore().reload();
					},
					failure: function(form,action){
						FORM_FAILURE_CALLBACK(form,action,"数据保存失败");
					}
				});
			}
		}
	},{
		text: '重置',
		iconCls: 'commonReset',
		handler: function(){
			_formPanel_createWay_TW.getForm().reset();
			_formPanel_createWay_TW.findById('tab_id_TW').focus();
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler: function(){ _win_create_TW.hide(); }
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
				items: new Ext.form.ComboBox({
				       xtype : 'combo',
				       id:'tab_id_TW',
				       store : STORE_CITY,
				       valueField : 'cityID',
				       displayField : 'jointName',
				       mode : 'local',
				       forceSelection : true,
				       hiddenName : 'freightRouteDTO.originID',
				       selectOnFocus:true,
				       typeAhead: false,
				       editable : true,
				       triggerAction : 'all',
				       fieldLabel: '始发地',
				       emptyText: '请选择始发城市',
				       allowBlank: false,
				       name : 'city',
				       width:　200,
				       listeners: {
				       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
				       }
				})
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
				items: new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : STORE_CITY,
				       valueField : 'cityID',
				       displayField : 'jointName',
				       mode : 'local',
				       forceSelection : true,
				       hiddenName : 'freightRouteDTO.destinationID',
				       selectOnFocus:true,
				       typeAhead: false,
				       editable : true,
				       triggerAction : 'all',
				       fieldLabel: '目的地',
				       emptyText: '请选择目的城市',
				       allowBlank: false,
				       name : 'city',
				       width:　200,
				       listeners: {
				       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
				       }
				})
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
						xtype: 'numberfield',
						fieldLabel: '运输天数',
						allowNegative: false,
						minValue: 1,
						value: 1,
						allowBlank: false,
						maxValue: MAX_INT,
						allowDecimals: false,
						selectOnFocus: true,
						name: 'freightRouteDTO.daysSpent',
						emptyText: '路线预计运输天数',
						width: 200
					}]
			}]
		},
		{//Row 6
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
						name: 'freightRouteDTO.remarks',
						regex: REGEX_COMMON_M,
						regexText: REGEX_COMMON_M_TEXT,
						width: 200
					}]
			}]
		}
	]
});





