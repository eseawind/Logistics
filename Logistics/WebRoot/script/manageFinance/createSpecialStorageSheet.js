var _win_create_SPEF = null;
function onCreateSpecialStorageSheet()
{
	if(null == _win_create_SPEF)
	{
	    createWindow_create_SPEF();
	    STORE_CUSTOMER_ALL_LOAD();
	}else
	{
		resetPage_create_SPEF();
	}
	_win_create_SPEF.setPagePosition(GET_WIN_X(_win_create_SPEF.width),GET_WIN_Y_M(400));
    _win_create_SPEF.show();
}

function createWindow_create_SPEF()
{
	_win_create_SPEF = new Ext.Window({
        title: '新建特殊仓储收支',
        iconCls: 'commonCreateSheet',
        width: 550,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createSheet_SPEF,
        listeners: LISTENER_WIN_MOVE
    });
}

var _formPanel_createSheet_SPEF = new Ext.FormPanel({
	
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
			if(_formPanel_createSheet_SPEF.getForm().isValid())
			{
				if(-1==checkParams_create_SPEF()) return;
				_formPanel_createSheet_SPEF.getForm().submit({
					url: 'SpecialStockIncome.insert.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						resetPage_create_SPEF();
						_grid_SSF.getStore().reload();
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
		handler: function(){_win_create_SPEF.hide();}
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
				items:new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : STORE_CUSTOMER_ALL,
				       valueField : 'customerID',
				       displayField : 'jointName',
				       mode : 'local',
				       forceSelection : true,
				       hiddenName : 'income.customerID',
				       selectOnFocus:true,
				       typeAhead: false,
				       editable : true,
				       triggerAction : 'all',
				       allowBlank : false,
				       fieldLabel : '客户名称',
				       width:　230,
				       listeners: {
				       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
				       	}
				})
			}]
		},
		{//Row 2
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.68',
				layout: 'form',
				border: false,
				items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_STORAGE,
					       valueField : 'warehouseID',
					       displayField : 'name',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'income.warehouseID',
					       editable : true,
					       typeAhead : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       selectOnFocus:true,
					       fieldLabel : '库存地',
					       width:　230,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
					       }
					})]
			},{//Column 2
				columnWidth: '0.32',
				layout: 'form',
				border: false,
				items: [{
							xtype: 'button',
							text : "&nbsp;&nbsp;计算仓储天数",
							iconCls: 'calculator',
							handler: function(){
								var day = checkParams_create_SPEF();
								if(day != -1)
									_formPanel_createSheet_SPEF.findById('stockDay_create_SPEF').setValue(day);
							}
					}]
			}]
		},
		{//Row 3
			layout: 'column',
			border: false,
			items: [
			{//Column 0
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'datefield',
					fieldLabel: '仓储开始',
					id: 'dateStart_create_SPEF',
					format: 'Y-m-d',
					name: 'income.dateStart',
					value: new Date().dateFormat('Y-m-d'),
					allowBlank: false,
					editable: false,
					width: TEXTFIELDWIDTH
				}]
			},
			{//Column 1
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'datefield',
					fieldLabel: '仓储结束',
					id: 'dateEnd_create_SPEF',
					format: 'Y-m-d',
					name: 'income.dateEnd',
					value: new Date().dateFormat('Y-m-d'),
					allowBlank: false,
					editable: false,
					width: TEXTFIELDWIDTH
				}]
			}]
		},
		{//Row 3
			layout: 'column',
			border: false,
			items: [
			{//Column 2
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					allowNegative: false,
					allowDecimals : false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					id: 'stockDay_create_SPEF',
					selectOnFocus: true,
					name: 'income.daysStock',
					fieldLabel: '仓储天数',
					width: TEXTFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_SELLCENTER,
					       valueField : 'sellCenterID',
					       displayField : 'sellCenterID',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: false,
					       forceSelection : true,
					       hiddenName : 'income.sellCenter',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '销售中心',
					       width:　TEXTFIELDWIDTH,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
					       }
					})]
			}]
		},
		{//Row 3
			layout: 'column',
			border: false,
			items: [
			
			{//Column 1
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					id: 'stockoutFee_create_SPEF',
					selectOnFocus: true,
					name: 'income.quote',
					fieldLabel: '单位费用',
					width: TEXTFIELDWIDTH
				}]
			},
			{//Column 1
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					id: 'area_create_SPEF',
					selectOnFocus: true,
					name: 'income.area',
					fieldLabel: '仓储面积',
					width: TEXTFIELDWIDTH
				}]
			}]
		},
		{//Row 4
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'income.stockFee',
					fieldLabel: '仓储费',
					width: TEXTFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'income.extraFee',
					fieldLabel: '其他费用',
					width: TEXTFIELDWIDTH
				}]
			}]
		},
		{//Row 4
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1.0',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '备注信息',
					regex: REGEX_COMMON_M,
					regexText: REGEX_COMMON_M_TEXT,
					name: 'income.remarks',
					width: 370
				}]
			}]
		}]
});

function checkParams_create_SPEF()
{
	var start = _formPanel_createSheet_SPEF.findById('dateStart_create_SPEF').getValue();
	var end = _formPanel_createSheet_SPEF.findById('dateEnd_create_SPEF').getValue();
	if(end < start)
	{
		Ext.Msg.show({
			title : '操作提示',
			msg : '请正确填写仓储开始与结束日期！',
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.WARNING
		});
		return -1;
	}
	return (end-start)/1000/60/60/24;
}

function resetPage_create_SPEF()
{
	_formPanel_createSheet_SPEF.getForm().reset();
}
