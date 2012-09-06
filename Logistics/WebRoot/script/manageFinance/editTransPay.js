
var _win_pay_TRANSOUT = null;
var _records_pay_TRANSOUT = null;
var _pos_pay_TRANSOUT = 1;
var mask_pay_TRANSOUT = null;
function onEditTransPay(records,index)
{
	if(null == _win_pay_TRANSOUT)
	{
	    createWindow_pay_TRANSOUT();
	}
	else
	{
		_formPanel_transPay_TRANSOUT.getForm().reset();
		_pos_pay_TRANSOUT = 1;
	}
	_grid_TP.getTopToolbar().findById('btn_listsave_TP').disable();
	mask_pay_TRANSOUT.hide();
	_records_pay_TRANSOUT = records;
	loadData_pay_TRANSOUT(_records_pay_TRANSOUT,_pos_pay_TRANSOUT);
	_win_pay_TRANSOUT.setPagePosition(GET_WIN_X(_win_pay_TRANSOUT.width),GET_WIN_Y());
    _win_pay_TRANSOUT.show();
}

function createWindow_pay_TRANSOUT()
{
	_win_pay_TRANSOUT = new Ext.Window({
        title: '财务维护',
        iconCls: 'commonSaveAll',
        width: 750,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items:_formPanel_transPay_TRANSOUT,
        listeners: {
        	move:LISTENER_WIN_MOVE_FUNC,
        	beforehide:function()
        	{
        		_ds_TP.reload();
        		_grid_TP.getTopToolbar().findById('btn_listsave_TP').enable();
        	}
        }
    });
    _win_pay_TRANSOUT.show();
    mask_pay_TRANSOUT = new Ext.LoadMask(_win_pay_TRANSOUT.body, {msg:"正在保存,请稍后..."});
}

var _panel_a_pay_TRANSOUT = new Ext.Panel({
	
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
					id: 'id_pay_TRANSOUT_transId',
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
					id: 'id_pay_TRANSOUT_customerNumber',
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
					id: 'id_pay_TRANSOUT_dateCreated',
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
						id: 'id_pay_TRANSOUT_customerType',
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
						id: 'id_pay_TRANSOUT_customer',
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
						id: 'id_pay_TRANSOUT_consignee',
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
						id: 'id_pay_TRANSOUT_consigneeCompany',
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
						id: 'id_pay_TRANSOUT_origin',
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
						id: 'id_pay_TRANSOUT_destination',
						readOnly: true,
						width: 220
					}]
			}]
		}
	]
});

var _panel_b_pay_TRANSOUT = new Ext.Panel({
	
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
					id: 'id_pay_TRANSOUT_allCount',
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
					id: 'id_pay_TRANSOUT_allWeight',
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
					id: 'id_pay_TRANSOUT_allVolume',
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
					id: 'id_pay_TRANSOUT_allValue',
					width: NUMBERFIELDWIDTH
				}]
			}]
		}
	]
});

var _panel_c_pay_TRANSOUT = new Ext.Panel({
	
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
					fieldLabel: '运输支出',
					id: 'id_pay_TRANSOUT_freightCost',
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
					fieldLabel: '代垫费',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					id: 'id_pay_TRANSOUT_PrepaidCost',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 3
				columnWidth: '0.5',
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
					id: 'id_pay_TRANSOUT_extraCost',
					width: NUMBERFIELDWIDTH
				}]
			}]
		},
		{//Row 2
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1',
				layout: 'form',
				border: false,
				items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_COSTCENTER,
					       id: 'id_pay_TRANSOUT_costCenter',
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
					id: 'id_pay_TRANSOUT_financialRemarks',
					width: 614
				}]
			}]
		}
	]
});

var _formPanel_transPay_TRANSOUT = new Ext.FormPanel({
	
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
			if(_formPanel_transPay_TRANSOUT.getForm().isValid())
			{
				var params = getSubmitParams_pay_TRANSOUT(0);
				mask_pay_TRANSOUT.show();
				Ext.Ajax.request({
					url: 'FreightCost.updateCost.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_pay_TRANSOUT.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_pay_TRANSOUT == _records_pay_TRANSOUT.length)
							{
								_win_pay_TRANSOUT.hide();
								_ds_TP.reload();
							}else
							{
								_pos_pay_TRANSOUT++;
								loadData_pay_TRANSOUT(_records_pay_TRANSOUT,_pos_pay_TRANSOUT);
							}
							mask_pay_TRANSOUT.hide();
						}
					},
					failure: function(response,options){
						mask_pay_TRANSOUT.hide();
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
			if(_formPanel_transPay_TRANSOUT.getForm().isValid())
			{
				var params = getSubmitParams_pay_TRANSOUT(1);
				mask_pay_TRANSOUT.show();
				Ext.Ajax.request({
					url: 'FreightCost.updateCost.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_pay_TRANSOUT.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_pay_TRANSOUT == _records_pay_TRANSOUT.length)
							{
								_win_pay_TRANSOUT.hide();
								_ds_TP.reload();
							}else
							{
								_pos_pay_TRANSOUT++;
								loadData_pay_TRANSOUT(_records_pay_TRANSOUT,_pos_pay_TRANSOUT);
							}
							mask_pay_TRANSOUT.hide();
						}
					},
					failure: function(response,options){
						mask_pay_TRANSOUT.hide();
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'数据保存错误!');
					},
					params: params
				});//Ajax
			}
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler:　function(){ _win_pay_TRANSOUT.hide(); }
	}],
	
	items:[
		_panel_a_pay_TRANSOUT,
		_panel_b_pay_TRANSOUT,
		_panel_c_pay_TRANSOUT
	]
});

function getSubmitParams_pay_TRANSOUT(mode)
{
	params = {};
	params['fcdto.freightManifestID'] = _panel_a_pay_TRANSOUT.findById('id_pay_TRANSOUT_transId').getValue();
	params['fcdto.freightCost'] = _panel_c_pay_TRANSOUT.findById('id_pay_TRANSOUT_freightCost').getValue();
	params['fcdto.prepaidCost'] = _panel_c_pay_TRANSOUT.findById('id_pay_TRANSOUT_PrepaidCost').getValue();
	params['fcdto.extraCost'] = _panel_c_pay_TRANSOUT.findById('id_pay_TRANSOUT_extraCost').getValue();
	params['fcdto.financialRemarks'] = _panel_c_pay_TRANSOUT.findById('id_pay_TRANSOUT_financialRemarks').getValue();
	params['fcdto.costCenter'] = _panel_c_pay_TRANSOUT.findById('id_pay_TRANSOUT_costCenter').getValue();
	params['fcdto.extraCostRemarks'] = '';
	if(mode)
		params['operType'] = '1';
	else
		params['operType'] = '0';
	return params;
}

function loadData_pay_TRANSOUT(records,pos)
{
	if(records == null) return;
	if(records.length < pos || pos < 1) return;
	_win_pay_TRANSOUT.setTitle('财务维护'+'&nbsp;&nbsp;[&nbsp;正在处理第&nbsp;'+pos+'&nbsp;单&nbsp;,&nbsp;共&nbsp;'+records.length+'&nbsp;单&nbsp;]');
	pos--;
	_panel_a_pay_TRANSOUT.findById('id_pay_TRANSOUT_transId').setValue(records[pos].get('freightManifestID'));
	_panel_a_pay_TRANSOUT.findById('id_pay_TRANSOUT_dateCreated').setValue(records[pos].get('dateCreated'));
	_panel_a_pay_TRANSOUT.findById('id_pay_TRANSOUT_customerType').setValue(records[pos].get('customerType'));
	_panel_a_pay_TRANSOUT.findById('id_pay_TRANSOUT_customer').setValue(records[pos].get('customer'));
	_panel_a_pay_TRANSOUT.findById('id_pay_TRANSOUT_customerNumber').setValue(records[pos].get('customerNumber'));
	_panel_a_pay_TRANSOUT.findById('id_pay_TRANSOUT_consigneeCompany').setValue(records[pos].get('consigneeCompany'));
	_panel_a_pay_TRANSOUT.findById('id_pay_TRANSOUT_consignee').setValue(records[pos].get('consignee'));
	_panel_a_pay_TRANSOUT.findById('id_pay_TRANSOUT_origin').setValue(records[pos].get('originCity')+'-'+records[pos].get('originProvince'));
	_panel_a_pay_TRANSOUT.findById('id_pay_TRANSOUT_destination').setValue(records[pos].get('destinationCity')+'-'+records[pos].get('destinationProvince'));
	
	_panel_b_pay_TRANSOUT.findById('id_pay_TRANSOUT_allCount').setValue(records[pos].get('sumAmount'));
	_panel_b_pay_TRANSOUT.findById('id_pay_TRANSOUT_allWeight').setValue(records[pos].get('sumWeight'));
	_panel_b_pay_TRANSOUT.findById('id_pay_TRANSOUT_allVolume').setValue(records[pos].get('sumVolume'));
	_panel_b_pay_TRANSOUT.findById('id_pay_TRANSOUT_allValue').setValue(records[pos].get('sumValue'));
	
	_panel_c_pay_TRANSOUT.findById('id_pay_TRANSOUT_freightCost').setValue(records[pos].get('freightCost'));
	_panel_c_pay_TRANSOUT.findById('id_pay_TRANSOUT_PrepaidCost').setValue(records[pos].get('PrepaidCost'));
	_panel_c_pay_TRANSOUT.findById('id_pay_TRANSOUT_extraCost').setValue(records[pos].get('extraCost'));
	_panel_c_pay_TRANSOUT.findById('id_pay_TRANSOUT_costCenter').setValue(records[pos].get('costCenter'));
	_panel_c_pay_TRANSOUT.findById('id_pay_TRANSOUT_financialRemarks').setValue(records[pos].get('financialRemarks'));

}
