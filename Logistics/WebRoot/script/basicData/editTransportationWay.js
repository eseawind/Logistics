
var _win_edit_TW = null;
var _mask_edit_TW = null;
function onEditTransportationWay(wayID)
{
	if(null == _win_edit_TW)
	{
	    createWindow_edit_TW();
	}else
	{
		_formPanel_editWay_TW.getForm().reset();
	}
	_mask_edit_TW.show();
	_formPanel_editWay_TW.getForm().load({
    	url: 'FreightRoute.queryOne.action',
    	method: 'POST',
    	params: {'freightRouteDTO.freightRouteID': wayID},
    	success: function(form,action){
			_mask_edit_TW.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_edit_TW.hide();
    	}
    });
	_win_edit_TW.setPagePosition(GET_WIN_X(_win_edit_TW.width),GET_WIN_Y());
    _win_edit_TW.show();
}

function createWindow_edit_TW()
{
	_win_edit_TW = new Ext.Window({
        title: '修改运输路线',
        iconCls: 'commonEditSheet',
        width: 375,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_editWay_TW,
        listeners: LISTENER_WIN_MOVE
    });
    _win_edit_TW.show();
    _mask_edit_TW = new Ext.LoadMask(_win_edit_TW.body, {msg:"正在载入,请稍后..."});
}


var _formPanel_editWay_TW = new Ext.FormPanel({
	
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
			if(_formPanel_editWay_TW.getForm().isValid())
			{
				_formPanel_editWay_TW.getForm().submit({
					url: 'FreightRoute.update.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_editWay_TW.getForm().reset();
						_win_edit_TW.hide();
						_grid_TW.getStore().reload();
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
		handler: function(){ _win_edit_TW.hide(); }
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
					items: [{
						xtype: 'textfield',
						hidden: true,
						name: 'freightRouteDTO.freightRouteID'
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
				items: new Ext.form.ComboBox({
				       xtype : 'combo',
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
						value: 0,
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





