
var _win_fee_ENSTOR = null;
var _records_fee_ENSTOR = null;
var _pos_fee_ENSTOR = 1;
var mask_fee_ENSTOR = null;
function onEditEnterStorageFee(records,index)
{
	if(null == _win_fee_ENSTOR)
	{
	    createWindow_fee_ENSTOR();
	}
	else
	{
		_formPanel_fee_ENSTOR.getForm().reset();
		_pos_fee_ENSTOR = 1;
	}
	_grid_ESF.getTopToolbar().findById('btn_listsave_ESF').disable();
	mask_fee_ENSTOR.hide();
	_records_fee_ENSTOR = records;
	loadData_fee_ENSTOR(_records_fee_ENSTOR,_pos_fee_ENSTOR);
	_win_fee_ENSTOR.setPagePosition(GET_WIN_X(_win_fee_ENSTOR.width),GET_WIN_Y());
    _win_fee_ENSTOR.show();
}

function createWindow_fee_ENSTOR()
{
	_win_fee_ENSTOR = new Ext.Window({
        title: '财务维护',
        iconCls: 'commonSaveAll',
        width: 750,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items:_formPanel_fee_ENSTOR,
        listeners: {
        	move:LISTENER_WIN_MOVE_FUNC,
        	beforehide:function()
        	{
        		_ds_ESF.reload();
        		_grid_ESF.getTopToolbar().findById('btn_listsave_ESF').enable();
        	}
        }
    });
    _win_fee_ENSTOR.show();
    mask_fee_ENSTOR = new Ext.LoadMask(_win_fee_ENSTOR.body, {msg:"正在保存,请稍后..."});
}

var _panel_a_fee_ENSTOR = new Ext.Panel({
	
	layout: 'form',
	style: 'margin:0px 0px 5px 0px',
	collapsible : false,
	title: '&nbsp;&nbsp;<&nbsp;入库单基本信息&nbsp;>',
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
					fieldLabel: '入库单号',
					id: 'id_fee_ENSTOR_stockinId',
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
					id: 'id_fee_ENSTOR_dateStockin',
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
					id: 'id_fee_ENSTOR_dateCreated',
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
					id: 'id_fee_ENSTOR_warehouseName',
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
					id: 'id_fee_ENSTOR_customer',
					readOnly: true,
					width: 348
				}]
			}]
		}
	]
});

var _panel_b_fee_ENSTOR = new Ext.Panel({
	
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
					id: 'id_fee_ENSTOR_allCount',
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
					id: 'id_fee_ENSTOR_allWeight',
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
					id: 'id_fee_ENSTOR_allVolume',
					width: NUMBERFIELDWIDTH
				}]
			}]
		}
	]
});

var _panel_c_fee_ENSTOR = new Ext.Panel({
	
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
					fieldLabel: '入库费用',
					id: 'id_fee_ENSTOR_stockinFee',
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
					id: 'id_fee_ENSTOR_loadUnloadCost',
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
					id: 'id_fee_ENSTOR_extraCost',
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
					id: 'id_fee_ENSTOR_unitPrice',
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
					       id:'id_fee_ENSTOR_chargeMode',
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
					       id: 'id_fee_ENSTOR_sellCenter',
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
					       id: 'id_fee_ENSTOR_costCenter',
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
					id: 'id_fee_ENSTOR_financialRemarks',
					width: 614
				}]
			}]
		}
	]
});

var _formPanel_fee_ENSTOR = new Ext.FormPanel({
	
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
			if(_formPanel_fee_ENSTOR.getForm().isValid())
			{
				var params = getSubmitParams_fee_ENSTOR(0);
				mask_fee_ENSTOR.show();
				Ext.Ajax.request({
					url: 'StockinFinance.updateIncome.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_fee_ENSTOR.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_fee_ENSTOR == _records_fee_ENSTOR.length)
							{
								_win_fee_ENSTOR.hide();
								_ds_ESF.reload();
							}else
							{
								_pos_fee_ENSTOR++;
								loadData_fee_ENSTOR(_records_fee_ENSTOR,_pos_fee_ENSTOR);
							}
							mask_fee_ENSTOR.hide();
						}
					},
					failure: function(response,options){
						mask_fee_ENSTOR.hide();
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
			if(_formPanel_fee_ENSTOR.getForm().isValid())
			{
				var params = getSubmitParams_fee_ENSTOR(1);
				mask_fee_ENSTOR.show();
				Ext.Ajax.request({
					url: 'StockinFinance.updateIncome.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_fee_ENSTOR.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_fee_ENSTOR == _records_fee_ENSTOR.length)
							{
								_win_fee_ENSTOR.hide();
								_ds_ESF.reload();
							}else
							{
								_pos_fee_ENSTOR++;
								loadData_fee_ENSTOR(_records_fee_ENSTOR,_pos_fee_ENSTOR);
							}
							mask_fee_ENSTOR.hide();
						}
					},
					failure: function(response,options){
						mask_fee_ENSTOR.hide();
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'数据保存错误!');
					},
					params: params
				});//Ajax
			}
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler:　function(){ _win_fee_ENSTOR.hide(); }
	}],
	
	items:[
		_panel_a_fee_ENSTOR,
		_panel_b_fee_ENSTOR,
		_panel_c_fee_ENSTOR
	]
});

function getSubmitParams_fee_ENSTOR(mode)
{
	params = {};
	params['sifdto.stockinManifestID'] = _panel_a_fee_ENSTOR.findById('id_fee_ENSTOR_stockinId').getValue();
	params['sifdto.chargeMode'] = _panel_c_fee_ENSTOR.findById('id_fee_ENSTOR_chargeMode').getValue();
	params['sifdto.stockinFee'] = _panel_c_fee_ENSTOR.findById('id_fee_ENSTOR_stockinFee').getValue();
	params['sifdto.sellCenter'] = _panel_c_fee_ENSTOR.findById('id_fee_ENSTOR_sellCenter').getValue();
	params['sifdto.costCenter'] = _panel_c_fee_ENSTOR.findById('id_fee_ENSTOR_costCenter').getValue();
	params['sifdto.loadUnloadCost'] = _panel_c_fee_ENSTOR.findById('id_fee_ENSTOR_loadUnloadCost').getValue();
	params['sifdto.extraCost'] = _panel_c_fee_ENSTOR.findById('id_fee_ENSTOR_extraCost').getValue();
	params['sifdto.unitPrice'] = _panel_c_fee_ENSTOR.findById('id_fee_ENSTOR_unitPrice').getValue();
	params['sifdto.remarks'] = _panel_c_fee_ENSTOR.findById('id_fee_ENSTOR_financialRemarks').getValue();
	if(mode)
		params['operType'] = '1';
	else
		params['operType'] = '0';
	return params;
}

function loadData_fee_ENSTOR(records,pos)
{
	if(records == null) return;
	if(records.length < pos || pos < 1) return;
	_win_fee_ENSTOR.setTitle('财务维护'+'&nbsp;&nbsp;[&nbsp;正在处理第&nbsp;'+pos+'&nbsp;单&nbsp;,&nbsp;共&nbsp;'+records.length+'&nbsp;单&nbsp;]');
	pos--;
	_panel_a_fee_ENSTOR.findById('id_fee_ENSTOR_stockinId').setValue(records[pos].get('stockinManifestID'));
	_panel_a_fee_ENSTOR.findById('id_fee_ENSTOR_dateStockin').setValue(records[pos].get('dateStockin'));
	_panel_a_fee_ENSTOR.findById('id_fee_ENSTOR_dateCreated').setValue(records[pos].get('dateCreated'));
	_panel_a_fee_ENSTOR.findById('id_fee_ENSTOR_warehouseName').setValue(records[pos].get('warehouseName'));
	_panel_a_fee_ENSTOR.findById('id_fee_ENSTOR_customer').setValue(records[pos].get('customer'));
	
	_panel_b_fee_ENSTOR.findById('id_fee_ENSTOR_allCount').setValue(records[pos].get('sumAmount'));
	_panel_b_fee_ENSTOR.findById('id_fee_ENSTOR_allWeight').setValue(records[pos].get('sumWeight'));
	_panel_b_fee_ENSTOR.findById('id_fee_ENSTOR_allVolume').setValue(records[pos].get('sumVolume'));
	
	_panel_c_fee_ENSTOR.findById('id_fee_ENSTOR_sellCenter').setValue(records[pos].get('sellCenter'));
	_panel_c_fee_ENSTOR.findById('id_fee_ENSTOR_costCenter').setValue(records[pos].get('costCenter'));
	_panel_c_fee_ENSTOR.findById('id_fee_ENSTOR_stockinFee').setValue(records[pos].get('stockinFee'));
	_panel_c_fee_ENSTOR.findById('id_fee_ENSTOR_loadUnloadCost').setValue(records[pos].get('loadUnloadCost'));
	_panel_c_fee_ENSTOR.findById('id_fee_ENSTOR_extraCost').setValue(records[pos].get('extraCost'));
	_panel_c_fee_ENSTOR.findById('id_fee_ENSTOR_unitPrice').setValue(999999);
	_panel_c_fee_ENSTOR.findById('id_fee_ENSTOR_chargeMode').setValue(records[pos].get('chargeMode'));
	_panel_c_fee_ENSTOR.findById('id_fee_ENSTOR_financialRemarks').setValue(records[pos].get('financialRemarks'));

}
