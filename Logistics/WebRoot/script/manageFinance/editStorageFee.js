
var _win_fee_STORF = null;
var _records_fee_STORF = null;
var _pos_fee_STORF = 1;
var mask_fee_STORF = null;
function onEditStorageFee(records,index)
{
	if(null == _win_fee_STORF)
	{
	    createWindow_fee_STORF();
	}
	else
	{
		_formPanel_fee_STORF.getForm().reset();
		_pos_fee_STORF = 1;
	}
	_grid_SF.getTopToolbar().findById('btn_listsave_SF').disable();
	mask_fee_STORF.hide();
	_records_fee_STORF = records;
	loadData_fee_STORF(_records_fee_STORF,_pos_fee_STORF);
	_win_fee_STORF.setPagePosition(GET_WIN_X(_win_fee_STORF.width),GET_WIN_Y());
    _win_fee_STORF.show();
}

function createWindow_fee_STORF()
{
	_win_fee_STORF = new Ext.Window({
        title: '财务维护',
        iconCls: 'commonSaveAll',
        width: 750,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items:_formPanel_fee_STORF,
        listeners: {
        	move:LISTENER_WIN_MOVE_FUNC,
        	beforehide:function()
        	{
        		_ds_SF.reload();
        		_grid_SF.getTopToolbar().findById('btn_listsave_SF').enable();
        	}
        }
    });
    _win_fee_STORF.show();
    mask_fee_STORF = new Ext.LoadMask(_win_fee_STORF.body, {msg:"正在保存,请稍后..."});
}

var _panel_a_fee_STORF = new Ext.Panel({
	
	layout: 'form',
	style: 'margin:0px 0px 5px 0px',
	collapsible : false,
	title: '&nbsp;&nbsp;<&nbsp;收支单基本信息&nbsp;>',
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
					fieldLabel: '收支单号',
					id: 'id_fee_STORF_sheetId',
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
					fieldLabel: '入库日期',
					id: 'id_fee_STORF_dateStockin',
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
					fieldLabel: '出库日期',
					id: 'id_fee_STORF_dateStockout',
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
					fieldLabel: '物料代码',
					id: 'id_fee_STORF_itemNumber',
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
					fieldLabel: '物料名称',
					id: 'id_fee_STORF_itemName',
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
				columnWidth: '0.32',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					fieldLabel: '库存地',
					id: 'id_fee_STORF_warehouseName',
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
					id: 'id_fee_STORF_customer',
					readOnly: true,
					width: 348
				}]
			}]
		}
	]
});

var _panel_b_fee_STORF = new Ext.Panel({
	
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
					id: 'id_fee_STORF_allCount',
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
					id: 'id_fee_STORF_allWeight',
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
					id: 'id_fee_STORF_allVolume',
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
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '仓储天数',
					readOnly:true,
					id: 'id_fee_STORF_daysStock',
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
					fieldLabel: '结算日期',
					readOnly:true,
					id: 'id_fee_STORF_dateStart',
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
					fieldLabel: '结束日期',
					readOnly:true,
					id: 'id_fee_STORF_dateEnd',
					width: NUMBERFIELDWIDTH
				}]
			}]
		}
	]
});

var _panel_c_fee_STORF = new Ext.Panel({
	
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
					fieldLabel: '仓储费用',
					id: 'id_fee_STORF_stockFee',
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
					fieldLabel: '单位报价',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					id: 'id_fee_STORF_quote',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 3
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: new Ext.form.ComboBox({
					       store : STORE_TRANS_CHARGE_MODE,
					       valueField : 'value',
					       displayField : 'value',
					       mode : 'local',
					       id:'id_fee_STORF_chargeMode',
					       forceSelection : true,
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '计费方式',
					       value: '直接计费',
					       width:　80
					})
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
				items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_SELLCENTER,
					       id: 'id_fee_STORF_sellCenter',
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
					id: 'id_fee_STORF_financialRemarks',
					width: 614
				}]
			}]
		}
	]
});

var _formPanel_fee_STORF = new Ext.FormPanel({
	
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
			if(_formPanel_fee_STORF.getForm().isValid())
			{
				var params = getSubmitParams_fee_STORF(0);
				mask_fee_STORF.show();
				Ext.Ajax.request({
					url: 'StockFinance.updateIncome.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_fee_STORF.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_fee_STORF == _records_fee_STORF.length)
							{
								_win_fee_STORF.hide();
								_ds_SF.reload();
							}else
							{
								_pos_fee_STORF++;
								loadData_fee_STORF(_records_fee_STORF,_pos_fee_STORF);
							}
							mask_fee_STORF.hide();
						}
					},
					failure: function(response,options){
						mask_fee_STORF.hide();
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
			if(_formPanel_fee_STORF.getForm().isValid())
			{
				var params = getSubmitParams_fee_STORF(1);
				mask_fee_STORF.show();
				Ext.Ajax.request({
					url: 'StockFinance.updateIncome.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_fee_STORF.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_fee_STORF == _records_fee_STORF.length)
							{
								_win_fee_STORF.hide();
								_ds_SF.reload();
							}else
							{
								_pos_fee_STORF++;
								loadData_fee_STORF(_records_fee_STORF,_pos_fee_STORF);
							}
							mask_fee_STORF.hide();
						}
					},
					failure: function(response,options){
						mask_fee_STORF.hide();
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'数据保存错误!');
					},
					params: params
				});//Ajax
			}
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler:　function(){ _win_fee_STORF.hide(); }
	}],
	
	items:[
		_panel_a_fee_STORF,
		_panel_b_fee_STORF,
		_panel_c_fee_STORF
	]
});

function getSubmitParams_fee_STORF(mode)
{
	params = {};
	params['income.stockIncomeID'] = _panel_a_fee_STORF.findById('id_fee_STORF_sheetId').getValue();
	params['income.chargeMode'] = _panel_c_fee_STORF.findById('id_fee_STORF_chargeMode').getValue();
	params['income.stockFee'] = _panel_c_fee_STORF.findById('id_fee_STORF_stockFee').getValue();
	params['income.sellCenter'] = _panel_c_fee_STORF.findById('id_fee_STORF_sellCenter').getValue();
	params['income.unitPrice'] = _panel_c_fee_STORF.findById('id_fee_STORF_quote').getValue();
	params['income.remarks'] = _panel_c_fee_STORF.findById('id_fee_STORF_financialRemarks').getValue();
	if(mode)
		params['operType'] = '1';
	else
		params['operType'] = '0';
	return params;
}

function loadData_fee_STORF(records,pos)
{
	if(records == null) return;
	if(records.length < pos || pos < 1) return;
	_win_fee_STORF.setTitle('财务维护'+'&nbsp;&nbsp;[&nbsp;正在处理第&nbsp;'+pos+'&nbsp;单&nbsp;,&nbsp;共&nbsp;'+records.length+'&nbsp;单&nbsp;]');
	pos--;
	_panel_a_fee_STORF.findById('id_fee_STORF_sheetId').setValue(records[pos].get('stockIncomeID'));
	_panel_a_fee_STORF.findById('id_fee_STORF_dateStockin').setValue(records[pos].get('dateStockin'));
	_panel_a_fee_STORF.findById('id_fee_STORF_dateStockout').setValue(records[pos].get('dateStockout')==''?'未出库':records[pos].get('dateStockout'));
	_panel_a_fee_STORF.findById('id_fee_STORF_itemNumber').setValue(records[pos].get('itemNumber'));
	_panel_a_fee_STORF.findById('id_fee_STORF_itemName').setValue(records[pos].get('itemName'));
	_panel_a_fee_STORF.findById('id_fee_STORF_warehouseName').setValue(records[pos].get('warehouse'));
	_panel_a_fee_STORF.findById('id_fee_STORF_customer').setValue(records[pos].get('customer'));
	
	_panel_b_fee_STORF.findById('id_fee_STORF_daysStock').setValue(records[pos].get('daysStock'));
	_panel_b_fee_STORF.findById('id_fee_STORF_dateStart').setValue(records[pos].get('dateAccountStart'));
	_panel_b_fee_STORF.findById('id_fee_STORF_dateEnd').setValue(records[pos].get('dateAccountEnd'));
	_panel_b_fee_STORF.findById('id_fee_STORF_allCount').setValue(records[pos].get('amount'));
	_panel_b_fee_STORF.findById('id_fee_STORF_allWeight').setValue(records[pos].get('weight'));
	_panel_b_fee_STORF.findById('id_fee_STORF_allVolume').setValue(records[pos].get('volume'));
	if(records[pos].get('sellCenter')!=null&&records[pos].get('sellCenter')!='')
		_panel_c_fee_STORF.findById('id_fee_STORF_sellCenter').setValue(records[pos].get('sellCenter'));
	if(records[pos].get('chargeMode')!=null&&records[pos].get('chargeMode')!='')	
		_panel_c_fee_STORF.findById('id_fee_STORF_chargeMode').setValue(records[pos].get('chargeMode'));
	_panel_c_fee_STORF.findById('id_fee_STORF_stockFee').setValue(records[pos].get('stockFee'));
	_panel_c_fee_STORF.findById('id_fee_STORF_quote').setValue(records[pos].get('unitPrice'));
	_panel_c_fee_STORF.findById('id_fee_STORF_financialRemarks').setValue(records[pos].get('remarks'));

}
