
var _win_fee_OUTSTOR = null;
var _records_fee_OUTSTOR = null;
var _pos_fee_OUTSTOR = 1;
var mask_fee_OUTSTOR = null;
function onEditOutStorageFee(records,index)
{
	if(null == _win_fee_OUTSTOR)
	{
	    createWindow_fee_OUTSTOR();
	}
	else
	{
		_formPanel_fee_OUTSTOR.getForm().reset();
		_pos_fee_OUTSTOR = 1;
	}
	_grid_OSF.getTopToolbar().findById('btn_listsave_OSF').disable();
	mask_fee_OUTSTOR.hide();
	_records_fee_OUTSTOR = records;
	loadData_fee_OUTSTOR(_records_fee_OUTSTOR,_pos_fee_OUTSTOR);
	_win_fee_OUTSTOR.setPagePosition(GET_WIN_X(_win_fee_OUTSTOR.width),GET_WIN_Y());
    _win_fee_OUTSTOR.show();
}

function createWindow_fee_OUTSTOR()
{
	_win_fee_OUTSTOR = new Ext.Window({
        title: '财务维护',
        iconCls: 'commonSaveAll',
        width: 750,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items:_formPanel_fee_OUTSTOR,
        listeners: {
        	move:LISTENER_WIN_MOVE_FUNC,
        	beforehide:function()
        	{
        		_ds_OSF.reload();
        		_grid_OSF.getTopToolbar().findById('btn_listsave_OSF').enable();
        	}
        }   	
    });
    _win_fee_OUTSTOR.show();
    mask_fee_OUTSTOR = new Ext.LoadMask(_win_fee_OUTSTOR.body, {msg:"正在保存,请稍后..."});
}

var _panel_a_fee_OUTSTOR = new Ext.Panel({
	
	layout: 'form',
	style: 'margin:0px 0px 5px 0px',
	collapsible : false,
	title: '&nbsp;&nbsp;<&nbsp;出库单基本信息&nbsp;>',
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
					fieldLabel: '出库单号',
					id: 'id_fee_OUTSTOR_stockoutId',
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
					fieldLabel: '出库日期',
					id: 'id_fee_OUTSTOR_dateStockout',
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
					id: 'id_fee_OUTSTOR_dateCreated',
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
					fieldLabel: '库存地',
					id: 'id_fee_OUTSTOR_warehouseName',
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
					id: 'id_fee_OUTSTOR_customer',
					readOnly: true,
					width: 348
				}]
			}]
		}
	]
});

var _panel_b_fee_OUTSTOR = new Ext.Panel({
	
	layout: 'form',
	style: 'margin:0px 0px 5px 0px',
	collapsible : false,
	title: '&nbsp;&nbsp;<&nbsp;统计信息&nbsp;>',
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
					id: 'id_fee_OUTSTOR_allCount',
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
					id: 'id_fee_OUTSTOR_allWeight',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 3
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '总体积',
					readOnly:true,
					id: 'id_fee_OUTSTOR_allVolume',
					width: NUMBERFIELDWIDTH
				}]
			}]
		}
	]
});

var _panel_c_fee_OUTSTOR = new Ext.Panel({
	
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
					fieldLabel: '出库费用',
					id: 'id_fee_OUTSTOR_stockoutFee',
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
					fieldLabel: '装卸费',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					id: 'id_fee_OUTSTOR_loadUnloadCost',
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
					fieldLabel: '其他费用',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					id: 'id_fee_OUTSTOR_extraCost',
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
					fieldLabel: '单位报价',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					id: 'id_fee_OUTSTOR_unitPrice',
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
					       id:'id_fee_OUTSTOR_chargeMode',
					       forceSelection : true,
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '计费方式',
					       value: '直接计费',
					       width:　80
					})
			},
			{//Column 2
				columnWidth: '0.37',
				layout: 'form',
				border: false,
				items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_COSTCENTER,
					       id: 'id_fee_OUTSTOR_sellCenter',
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
			},
			{//Column 2
				columnWidth: '0.38',
				layout: 'form',
				border: false,
				items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_COSTCENTER,
					       id: 'id_fee_OUTSTOR_costCenter',
					       valueField : 'costCenterID',
					       displayField : 'costCenterID',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: false,
					       forceSelection : false,
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '成本中心',
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
					id: 'id_fee_OUTSTOR_financialRemarks',
					width: 614
				}]
			}]
		}
	]
});

var _formPanel_fee_OUTSTOR = new Ext.FormPanel({
	
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
			if(_formPanel_fee_OUTSTOR.getForm().isValid())
			{
				var params = getSubmitParams_fee_OUTSTOR(0);
				mask_fee_OUTSTOR.show();
				Ext.Ajax.request({
					url: 'StockoutFinance.updateFinance.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_fee_OUTSTOR.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_fee_OUTSTOR == _records_fee_OUTSTOR.length)
							{
								_win_fee_OUTSTOR.hide();
								_ds_OSF.reload();
							}else
							{
								_pos_fee_OUTSTOR++;
								loadData_fee_OUTSTOR(_records_fee_OUTSTOR,_pos_fee_OUTSTOR);
							}
							mask_fee_OUTSTOR.hide();
						}
					},
					failure: function(response,options){
						mask_fee_OUTSTOR.hide();
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
			if(_formPanel_fee_OUTSTOR.getForm().isValid())
			{
				var params = getSubmitParams_fee_OUTSTOR(1);
				mask_fee_OUTSTOR.show();
				Ext.Ajax.request({
					url: 'StockoutFinance.updateFinance.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_fee_OUTSTOR.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_fee_OUTSTOR == _records_fee_OUTSTOR.length)
							{
								_win_fee_OUTSTOR.hide();
								_ds_OSF.reload();
							}else
							{
								_pos_fee_OUTSTOR++;
								loadData_fee_OUTSTOR(_records_fee_OUTSTOR,_pos_fee_OUTSTOR);
							}
							mask_fee_OUTSTOR.hide();
						}
					},
					failure: function(response,options){
						mask_fee_OUTSTOR.hide();
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'数据保存错误!');
					},
					params: params
				});//Ajax
			}
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler:　function(){ _win_fee_OUTSTOR.hide(); }
	}],
	
	items:[
		_panel_a_fee_OUTSTOR,
		_panel_b_fee_OUTSTOR,
		_panel_c_fee_OUTSTOR
	]
});

function getSubmitParams_fee_OUTSTOR(mode)
{
	params = {};
	params['sofdto.stockoutManifestID'] = _panel_a_fee_OUTSTOR.findById('id_fee_OUTSTOR_stockoutId').getValue();
	params['sofdto.chargeMode'] = _panel_c_fee_OUTSTOR.findById('id_fee_OUTSTOR_chargeMode').getValue();
	params['sofdto.stockoutFee'] = _panel_c_fee_OUTSTOR.findById('id_fee_OUTSTOR_stockoutFee').getValue();
	params['sofdto.sellCenter'] = _panel_c_fee_OUTSTOR.findById('id_fee_OUTSTOR_sellCenter').getValue();
	params['sofdto.costCenter'] = _panel_c_fee_OUTSTOR.findById('id_fee_OUTSTOR_costCenter').getValue();
	params['sofdto.loadUnloadCost'] = _panel_c_fee_OUTSTOR.findById('id_fee_OUTSTOR_loadUnloadCost').getValue();
	params['sofdto.extraCost'] = _panel_c_fee_OUTSTOR.findById('id_fee_OUTSTOR_extraCost').getValue();
	params['sofdto.unitPrice'] = _panel_c_fee_OUTSTOR.findById('id_fee_OUTSTOR_unitPrice').getValue();
	params['sofdto.remarks'] = _panel_c_fee_OUTSTOR.findById('id_fee_OUTSTOR_financialRemarks').getValue();
	if(mode)
		params['operType'] = '1';
	else
		params['operType'] = '0';
	return params;
}

function loadData_fee_OUTSTOR(records,pos)
{
	if(records == null) return;
	if(records.length < pos || pos < 1) return;
	_win_fee_OUTSTOR.setTitle('财务维护'+'&nbsp;&nbsp;[&nbsp;正在处理第&nbsp;'+pos+'&nbsp;单&nbsp;,&nbsp;共&nbsp;'+records.length+'&nbsp;单&nbsp;]');
	pos--;
	_panel_a_fee_OUTSTOR.findById('id_fee_OUTSTOR_stockoutId').setValue(records[pos].get('stockoutManifestID'));
	_panel_a_fee_OUTSTOR.findById('id_fee_OUTSTOR_dateStockout').setValue(records[pos].get('dateStockout'));
	_panel_a_fee_OUTSTOR.findById('id_fee_OUTSTOR_dateCreated').setValue(records[pos].get('dateCreated'));
	_panel_a_fee_OUTSTOR.findById('id_fee_OUTSTOR_warehouseName').setValue(records[pos].get('warehouseName'));
	_panel_a_fee_OUTSTOR.findById('id_fee_OUTSTOR_customer').setValue(records[pos].get('customer'));
	
	_panel_b_fee_OUTSTOR.findById('id_fee_OUTSTOR_allCount').setValue(records[pos].get('sumAmount'));
	_panel_b_fee_OUTSTOR.findById('id_fee_OUTSTOR_allWeight').setValue(records[pos].get('sumWeight'));
	_panel_b_fee_OUTSTOR.findById('id_fee_OUTSTOR_allVolume').setValue(records[pos].get('sumVolume'));
	
	_panel_c_fee_OUTSTOR.findById('id_fee_OUTSTOR_sellCenter').setValue(records[pos].get('sellCenter'));
	_panel_c_fee_OUTSTOR.findById('id_fee_OUTSTOR_costCenter').setValue(records[pos].get('costCenter'));
	_panel_c_fee_OUTSTOR.findById('id_fee_OUTSTOR_stockoutFee').setValue(records[pos].get('stockoutFee'));
	_panel_c_fee_OUTSTOR.findById('id_fee_OUTSTOR_loadUnloadCost').setValue(records[pos].get('loadUnloadCost'));
	_panel_c_fee_OUTSTOR.findById('id_fee_OUTSTOR_extraCost').setValue(records[pos].get('extraCost'));
	_panel_c_fee_OUTSTOR.findById('id_fee_OUTSTOR_unitPrice').setValue(999999);
	_panel_c_fee_OUTSTOR.findById('id_fee_OUTSTOR_chargeMode').setValue(records[pos].get('chargeMode'));
	_panel_c_fee_OUTSTOR.findById('id_fee_OUTSTOR_financialRemarks').setValue(records[pos].get('financialRemarks'));

}
