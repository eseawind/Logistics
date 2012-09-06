
var _win_income_TRANSIN = null;
var _records_income_TRANSIN = null;
var _pos_income_TRANSIN = 1;
var mask_income_TRANSIN = null;
function onEditTransIncome(records,index)
{
	if(null == _win_income_TRANSIN)
	{
	    createWindow_income_TRANSIN();
	}
	else
	{
		_formPanel_transIncome_TRANSIN.getForm().reset();
		_pos_income_TRANSIN = 1;
	}
	_grid_TI.getTopToolbar().findById('btn_listsave_TI').disable();
	mask_income_TRANSIN.hide();
	_records_income_TRANSIN = records;
	loadData_income_TRANSIN(_records_income_TRANSIN,_pos_income_TRANSIN);
	_win_income_TRANSIN.setPagePosition(GET_WIN_X(_win_income_TRANSIN.width),GET_WIN_Y());
    _win_income_TRANSIN.show();
}

function createWindow_income_TRANSIN()
{
	_win_income_TRANSIN = new Ext.Window({
        title: '财务维护',
        iconCls: 'commonSaveAll',
        width: 750,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items:_formPanel_transIncome_TRANSIN,
        listeners: {
        	move:LISTENER_WIN_MOVE_FUNC,
        	beforehide:function()
        	{
        		_ds_TI.reload();
        		_grid_TI.getTopToolbar().findById('btn_listsave_TI').enable();
        	}
        }
        	
    });
    _win_income_TRANSIN.show();
    mask_income_TRANSIN = new Ext.LoadMask(_win_income_TRANSIN.body, {msg:"正在保存,请稍后..."});
}

var _panel_a_income_TRANSIN = new Ext.Panel({
	
	layout: 'form',
	style: 'margin:0px 0px 5px 0px',
	collapsible : false,
	title: '&nbsp;&nbsp;<&nbsp;运输单基本信息&nbsp;>',
	bodyStyle: PADDING,
	items: [
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.32',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					fieldLabel: '运输单号',
					id: 'id_income_TRANSIN_transId',
					readOnly: true,
					width: TEXTFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.32',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					fieldLabel: '客户单号',
					id: 'id_income_TRANSIN_customerNumber',
					readOnly: true,
					width: TEXTFIELDWIDTH
				}]
			},
			{//Column 3
				columnWidth: '0.36',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					fieldLabel: '建单日期',
					id: 'id_income_TRANSIN_dateCreated',
					readOnly: true,
					width: TEXTFIELDWIDTH
				}]
			}]
		},
		{//Row 2
			layout: 'column',
			border: false,
			items: [
			{//Column 1
					columnWidth: '0.32',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '客户类型',
						id: 'id_income_TRANSIN_customerType',
						readOnly: true,
						width: TEXTFIELDWIDTH
					}]
			},
			{//Column 2
					columnWidth: '0.68',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '客户名称',
						id: 'id_income_TRANSIN_customer',
						readOnly: true,
						width: 348
					}]
			}]
		},
		{//Row 2
			layout: 'column',
			border: false,
			items: [
			{//Column 1
					columnWidth: '0.32',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '收货人',
						id: 'id_income_TRANSIN_consignee',
						readOnly: true,
						width: TEXTFIELDWIDTH
					}]
			},
			{//Column 2
					columnWidth: '0.68',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '收货单位',
						id: 'id_income_TRANSIN_consigneeCompany',
						readOnly: true,
						width: 348
					}]
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
					items: [{
						xtype: 'textfield',
						fieldLabel: '始发地',
						id: 'id_income_TRANSIN_origin',
						readOnly: true,
						width: 220
					}]
			},
			{//Column 2
					columnWidth: '0.5',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '目的地',
						id: 'id_income_TRANSIN_destination',
						readOnly: true,
						width: 220
					}]
			}]
		}
	]
});

var _panel_b_income_TRANSIN = new Ext.Panel({
	
	layout: 'form',
	style: 'margin:0px 0px 5px 0px',
	collapsible : false,
	title: '&nbsp;&nbsp;<&nbsp;统计与报价信息&nbsp;>',
	bodyStyle: PADDING,
	items: [
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.25',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '总数量',
					id: 'id_income_TRANSIN_allCount',
					readOnly:true,
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.25',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '总重量',
					readOnly:true,
					id: 'id_income_TRANSIN_allWeight',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 3
				columnWidth: '0.25',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '总体积',
					readOnly:true,
					id: 'id_income_TRANSIN_allVolume',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 4
				columnWidth: '0.25',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '总价值',
					readOnly:true,
					id: 'id_income_TRANSIN_allValue',
					width: NUMBERFIELDWIDTH
				}]
			}]
		}
	]
});

var _panel_c_income_TRANSIN = new Ext.Panel({
	
	layout: 'form',
	style: 'margin:0px 0px 5px 0px',
	collapsible : false,
	title: '&nbsp;&nbsp;<&nbsp;财务数据维护&nbsp;>',
	bodyStyle: PADDING,
	items: [
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.25',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '数量报价',
					id: 'id_income_TRANSIN_priceByAmount',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.25',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '重量报价',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					id: 'id_income_TRANSIN_priceByWeight',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 3
				columnWidth: '0.25',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '体积报价',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					id: 'id_income_TRANSIN_priceByVolume',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 4
				columnWidth: '0.25',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '保险率(%)',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					id: 'id_income_TRANSIN_insuranceRate',
					width: NUMBERFIELDWIDTH
				}]
			}]
		},
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.25',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '提货费',
					id: 'id_income_TRANSIN_consignIncome',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.25',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '送货费',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					id: 'id_income_TRANSIN_deliveryIncome',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 3
				columnWidth: '0.25',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '保险费',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					id: 'id_income_TRANSIN_insuranceIncome',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 4
				columnWidth: '0.25',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '其他费用',
					allowNegative: true,
					value: 0,
					minValue: -MAX_DOUBLE,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					id: 'id_income_TRANSIN_extraIncome',
					width: NUMBERFIELDWIDTH
				}]
			}]
		},
		{//Row 2
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.25',
				layout: 'form',
				border: false,
				items: new Ext.form.ComboBox({
					       store : STORE_TRANS_CHARGE_MODE,
					       valueField : 'value',
					       displayField : 'value',
					       mode : 'local',
					       id:'id_income_TRANSIN_chargeMode',
					       forceSelection : true,
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '计费方式',
					       value: '数量',
					       width:　80
					})
			},
			{//Column 2
				columnWidth: '0.25',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '运输费',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					id: 'id_income_TRANSIN_freightIncome',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 3
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_COSTCENTER,
					       id: 'id_income_TRANSIN_sellCenter',
					       valueField : 'sellCenterID',
					       displayField : 'sellCenterID',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: false,
					       forceSelection : false,
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '销售中心',
					       width:　TEXTFIELDWIDTH
					})]
			}]
		},
		{//Row 3
			layout: 'column',
			border: false,
			items: [{//Column 2
				columnWidth: '1',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '备注信息',
					regex: REGEX_COMMON_M,
					regexText: REGEX_COMMON_M_TEXT,
					id: 'id_income_TRANSIN_financialRemarks',
					width: 614
				}]
			}]
		}
	]
});

var _formPanel_transIncome_TRANSIN = new Ext.FormPanel({
	
	layout: 'form',
	style: 'margin:-7px -7px 0px -7px',
	frame: true,
	labelAlign: 'right',
	bodyStyle: 'padding: 0px',
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
			if(_formPanel_transIncome_TRANSIN.getForm().isValid())
			{
				var params = getSubmitParams_income_TRANSIN(0);
				mask_income_TRANSIN.show();
				Ext.Ajax.request({
					url: 'FreightIncome.updateIncome.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_income_TRANSIN.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_income_TRANSIN == _records_income_TRANSIN.length)
							{
								_win_income_TRANSIN.hide();
								_ds_TI.reload();
							}else
							{
								_pos_income_TRANSIN++;
								loadData_income_TRANSIN(_records_income_TRANSIN,_pos_income_TRANSIN);
							}
							mask_income_TRANSIN.hide();
						}
					},
					failure: function(response,options){
						mask_income_TRANSIN.hide();
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'数据保存错误!');
					},
					params: params
				});//Ajax
			}
		}
	},{
		text: '保存并归档',
		iconCls: 'filing',
		handler: function()
		{
			if(_formPanel_transIncome_TRANSIN.getForm().isValid())
			{
				var params = getSubmitParams_income_TRANSIN(1);
				mask_income_TRANSIN.show();
				Ext.Ajax.request({
					url: 'FreightIncome.updateIncome.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_income_TRANSIN.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_income_TRANSIN == _records_income_TRANSIN.length)
							{
								_win_income_TRANSIN.hide();
								_ds_TI.reload();
							}else
							{
								_pos_income_TRANSIN++;
								loadData_income_TRANSIN(_records_income_TRANSIN,_pos_income_TRANSIN);
							}
							mask_income_TRANSIN.hide();
						}
					},
					failure: function(response,options){
						mask_income_TRANSIN.hide();
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'数据保存错误!');
					},
					params: params
				});//Ajax
			}
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler:　function(){ _win_income_TRANSIN.hide(); }
	}],
	
	items:[
		_panel_a_income_TRANSIN,
		_panel_b_income_TRANSIN,
		_panel_c_income_TRANSIN
	]
});

function getSubmitParams_income_TRANSIN(mode)
{
	params = {};
	params['fidto.freightManifestID'] = _panel_a_income_TRANSIN.findById('id_income_TRANSIN_transId').getValue();
	params['fidto.chargeMode'] = _panel_c_income_TRANSIN.findById('id_income_TRANSIN_chargeMode').getValue();
	params['fidto.freightIncome'] = _panel_c_income_TRANSIN.findById('id_income_TRANSIN_freightIncome').getValue();
	params['fidto.consignIncome'] = _panel_c_income_TRANSIN.findById('id_income_TRANSIN_consignIncome').getValue();
	params['fidto.deliveryIncome'] = _panel_c_income_TRANSIN.findById('id_income_TRANSIN_deliveryIncome').getValue();
	params['fidto.insuranceIncome'] = _panel_c_income_TRANSIN.findById('id_income_TRANSIN_insuranceIncome').getValue();
	params['fidto.extraIncome'] = _panel_c_income_TRANSIN.findById('id_income_TRANSIN_extraIncome').getValue();
	params['fidto.financialRemarks'] = _panel_c_income_TRANSIN.findById('id_income_TRANSIN_financialRemarks').getValue();
	params['fidto.sellCenter'] = _panel_c_income_TRANSIN.findById('id_income_TRANSIN_sellCenter').getValue();
	params['fidto.priceByAmount'] = _panel_c_income_TRANSIN.findById('id_income_TRANSIN_priceByAmount').getValue();
	params['fidto.priceByWeight'] = _panel_c_income_TRANSIN.findById('id_income_TRANSIN_priceByWeight').getValue();
	params['fidto.priceByVolume'] = _panel_c_income_TRANSIN.findById('id_income_TRANSIN_priceByVolume').getValue();
	params['fidto.insuranceRate'] = _panel_c_income_TRANSIN.findById('id_income_TRANSIN_insuranceRate').getValue();
	if(mode)
		params['operType'] = '1';
	else
		params['operType'] = '0';
	return params;
}

function loadData_income_TRANSIN(records,pos)
{
	if(records == null) return;
	if(records.length < pos || pos < 1) return;
	_win_income_TRANSIN.setTitle('财务维护'+'&nbsp;&nbsp;[&nbsp;正在处理第&nbsp;'+pos+'&nbsp;单&nbsp;,&nbsp;共&nbsp;'+records.length+'&nbsp;单&nbsp;]');
	pos--;
	_panel_a_income_TRANSIN.findById('id_income_TRANSIN_transId').setValue(records[pos].get('freightManifestID'));
	_panel_a_income_TRANSIN.findById('id_income_TRANSIN_dateCreated').setValue(records[pos].get('dateCreated'));
	_panel_a_income_TRANSIN.findById('id_income_TRANSIN_customerType').setValue(records[pos].get('customerType'));
	_panel_a_income_TRANSIN.findById('id_income_TRANSIN_customer').setValue(records[pos].get('customer'));
	_panel_a_income_TRANSIN.findById('id_income_TRANSIN_customerNumber').setValue(records[pos].get('customerNumber'));
	_panel_a_income_TRANSIN.findById('id_income_TRANSIN_consigneeCompany').setValue(records[pos].get('consigneeCompany'));
	_panel_a_income_TRANSIN.findById('id_income_TRANSIN_consignee').setValue(records[pos].get('consignee'));
	_panel_a_income_TRANSIN.findById('id_income_TRANSIN_origin').setValue(records[pos].get('originCity')+'-'+records[pos].get('originProvince'));
	_panel_a_income_TRANSIN.findById('id_income_TRANSIN_destination').setValue(records[pos].get('destinationCity')+'-'+records[pos].get('destinationProvince'));
	
	_panel_b_income_TRANSIN.findById('id_income_TRANSIN_allCount').setValue(records[pos].get('sumAmount'));
	_panel_b_income_TRANSIN.findById('id_income_TRANSIN_allWeight').setValue(records[pos].get('sumWeight'));
	_panel_b_income_TRANSIN.findById('id_income_TRANSIN_allVolume').setValue(records[pos].get('sumVolume'));
	_panel_b_income_TRANSIN.findById('id_income_TRANSIN_allValue').setValue(records[pos].get('sumValue'));
	
	_panel_c_income_TRANSIN.findById('id_income_TRANSIN_sellCenter').setValue(records[pos].get('sellCenter'));
	_panel_c_income_TRANSIN.findById('id_income_TRANSIN_priceByAmount').setValue(records[pos].get('priceByAmount'));
	_panel_c_income_TRANSIN.findById('id_income_TRANSIN_priceByWeight').setValue(records[pos].get('priceByWeight'));
	_panel_c_income_TRANSIN.findById('id_income_TRANSIN_priceByVolume').setValue(records[pos].get('priceByVolume'));
	_panel_c_income_TRANSIN.findById('id_income_TRANSIN_insuranceRate').setValue(records[pos].get('insuranceRate'));
	_panel_c_income_TRANSIN.findById('id_income_TRANSIN_chargeMode').setValue(records[pos].get('chargeMode'));
	_panel_c_income_TRANSIN.findById('id_income_TRANSIN_consignIncome').setValue(records[pos].get('consignIncome'));
	_panel_c_income_TRANSIN.findById('id_income_TRANSIN_deliveryIncome').setValue(records[pos].get('deliveryIncome'));
	_panel_c_income_TRANSIN.findById('id_income_TRANSIN_insuranceIncome').setValue(records[pos].get('insuranceIncome'));
	_panel_c_income_TRANSIN.findById('id_income_TRANSIN_extraIncome').setValue(records[pos].get('extraIncome'));
	_panel_c_income_TRANSIN.findById('id_income_TRANSIN_freightIncome').setValue(records[pos].get('freightIncome'));
	_panel_c_income_TRANSIN.findById('id_income_TRANSIN_financialRemarks').setValue(records[pos].get('financialRemarks'));

}
