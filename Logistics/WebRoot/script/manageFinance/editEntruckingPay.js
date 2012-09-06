
var _win_pay_ENOUT = null;
var _records_pay_ENOUT = null;
var _pos_pay_ENOUT = 1;
var mask_pay_ENOUT = null;
function onEditEntruckingPay(records,index)
{
	if(null == _win_pay_ENOUT)
	{
	    createWindow_pay_ENOUT();
	}
	else
	{
		_formPanel_pay_ENOUT.getForm().reset();
		_pos_pay_ENOUT = 1;
	}
	_grid_EP.getTopToolbar().findById('btn_listsave_EP').disable();
	mask_pay_ENOUT.hide();
	_records_pay_ENOUT = records;
	loadData_pay_ENOUT(_records_pay_ENOUT,_pos_pay_ENOUT);
	_win_pay_ENOUT.setPagePosition(GET_WIN_X(_win_pay_ENOUT.width),GET_WIN_Y());
    _win_pay_ENOUT.show();
}

function createWindow_pay_ENOUT()
{
	_win_pay_ENOUT = new Ext.Window({
        title: '财务维护',
        iconCls: 'commonSaveAll',
        width: 750,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items:_formPanel_pay_ENOUT,
        listeners: {
        	move:LISTENER_WIN_MOVE_FUNC,
        	beforehide:function()
        	{
        		_ds_EP.reload();
        		_grid_EP.getTopToolbar().findById('btn_listsave_EP').enable();
        	}
        }
    });
    _win_pay_ENOUT.show();
    mask_pay_ENOUT = new Ext.LoadMask(_win_pay_ENOUT.body, {msg:"正在保存,请稍后..."});
}

var _panel_a_pay_ENOUT = new Ext.Panel({
	
	layout: 'form',
	style: 'margin:0px 0px 5px 0px',
	collapsible : false,
	title: '&nbsp;&nbsp;<&nbsp;装车单基本信息&nbsp;>',
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
					fieldLabel: '装车单号',
					id: 'id_pay_ENOUT_entruckId',
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
					fieldLabel: '建单日期',
					id: 'id_pay_ENOUT_dateCreated',
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
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '承运单位',
						id: 'id_pay_ENOUT_freightContractor',
						readOnly: true,
						width: 220
					}]
			}]
		},
		{//Row 2
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
						id: 'id_pay_ENOUT_origin',
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
						id: 'id_pay_ENOUT_destination',
						readOnly: true,
						width: 220
					}]
			}]
		}
	]
});

var _panel_b_pay_ENOUT = new Ext.Panel({
	
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
					id: 'id_pay_ENOUT_allCount',
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
					id: 'id_pay_ENOUT_allWeight',
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
					id: 'id_pay_ENOUT_allVolume',
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
					id: 'id_pay_ENOUT_allValue',
					width: NUMBERFIELDWIDTH
				}]
			}]
		}
	]
});

var _panel_c_pay_ENOUT = new Ext.Panel({
	
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
					fieldLabel: '直接费用',
					id: 'id_pay_ENOUT_freightCost',
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
					id: 'id_pay_ENOUT_loadUnloadCost',
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
					id: 'id_pay_ENOUT_extraCost',
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
					id: 'id_pay_ENOUT_unitQuote',
					width: NUMBERFIELDWIDTH
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
				items: new Ext.form.ComboBox({
					       store : STORE_ENTRUCK_CHARGE_MODE,
					       valueField : 'value',
					       displayField : 'value',
					       mode : 'local',
					       id:'id_pay_ENOUT_chargeMode',
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
					id: 'id_pay_ENOUT_financialRemarks',
					width: 614
				}]
			}]
		}
	]
});

var _formPanel_pay_ENOUT = new Ext.FormPanel({
	
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
			if(_formPanel_pay_ENOUT.getForm().isValid())
			{
				var params = getSubmitParams_pay_ENOUT(0);
				mask_pay_ENOUT.show();
				Ext.Ajax.request({
					url: 'ShipmentCost.updateCost.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_pay_ENOUT.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_pay_ENOUT == _records_pay_ENOUT.length)
							{
								_win_pay_ENOUT.hide();
								_ds_EP.reload();
							}else
							{
								_pos_pay_ENOUT++;
								loadData_pay_ENOUT(_records_pay_ENOUT,_pos_pay_ENOUT);
							}
							mask_pay_ENOUT.hide();
						}
					},
					failure: function(response,options){
						mask_pay_ENOUT.hide();
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
			if(_formPanel_pay_ENOUT.getForm().isValid())
			{
				var params = getSubmitParams_pay_ENOUT(1);
				mask_pay_ENOUT.show();
				Ext.Ajax.request({
					url: 'ShipmentCost.updateCost.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_pay_ENOUT.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_pay_ENOUT == _records_pay_ENOUT.length)
							{
								_win_pay_ENOUT.hide();
								_ds_EP.reload();
							}else
							{
								_pos_pay_ENOUT++;
								loadData_pay_ENOUT(_records_pay_ENOUT,_pos_pay_ENOUT);
							}
							mask_pay_ENOUT.hide();
						}
					},
					failure: function(response,options){
						mask_pay_ENOUT.hide();
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'数据保存错误!');
					},
					params: params
				});//Ajax
			}
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler:　function(){ _win_pay_ENOUT.hide(); }
	}],
	
	items:[
		_panel_a_pay_ENOUT,
		_panel_b_pay_ENOUT,
		_panel_c_pay_ENOUT
	]
});

function getSubmitParams_pay_ENOUT(mode)
{
	params = {};
	params['scdto.shipmentManifestID'] = _panel_a_pay_ENOUT.findById('id_pay_ENOUT_entruckId').getValue();
	params['scdto.chargeMode'] = _panel_c_pay_ENOUT.findById('id_pay_ENOUT_chargeMode').getValue();
	params['scdto.freightCost'] = _panel_c_pay_ENOUT.findById('id_pay_ENOUT_freightCost').getValue();
	params['scdto.loadUnloadCost'] = _panel_c_pay_ENOUT.findById('id_pay_ENOUT_loadUnloadCost').getValue();
	params['scdto.extraCost'] = _panel_c_pay_ENOUT.findById('id_pay_ENOUT_extraCost').getValue();
	params['scdto.unitQuote'] = _panel_c_pay_ENOUT.findById('id_pay_ENOUT_unitQuote').getValue();
	params['scdto.remarks'] = _panel_c_pay_ENOUT.findById('id_pay_ENOUT_financialRemarks').getValue();
	if(mode)
		params['operType'] = '1';
	else
		params['operType'] = '0';
	return params;
}

function loadData_pay_ENOUT(records,pos)
{
	if(records == null) return;
	if(records.length < pos || pos < 1) return;
	_win_pay_ENOUT.setTitle('财务维护'+'&nbsp;&nbsp;[&nbsp;正在处理第&nbsp;'+pos+'&nbsp;单&nbsp;,&nbsp;共&nbsp;'+records.length+'&nbsp;单&nbsp;]');
	pos--;
	_panel_a_pay_ENOUT.findById('id_pay_ENOUT_entruckId').setValue(records[pos].get('shipmentManifestID'));
	_panel_a_pay_ENOUT.findById('id_pay_ENOUT_dateCreated').setValue(records[pos].get('dateCreated'));
	_panel_a_pay_ENOUT.findById('id_pay_ENOUT_freightContractor').setValue(records[pos].get('freightContractor'));
	_panel_a_pay_ENOUT.findById('id_pay_ENOUT_origin').setValue(records[pos].get('originCity')+'-'+records[pos].get('originProvince'));
	_panel_a_pay_ENOUT.findById('id_pay_ENOUT_destination').setValue(records[pos].get('destinationCity')+'-'+records[pos].get('destinationProvince'));
	
	_panel_b_pay_ENOUT.findById('id_pay_ENOUT_allCount').setValue(records[pos].get('sumAmount'));
	_panel_b_pay_ENOUT.findById('id_pay_ENOUT_allWeight').setValue(records[pos].get('sumWeight'));
	_panel_b_pay_ENOUT.findById('id_pay_ENOUT_allVolume').setValue(records[pos].get('sumVolume'));
	_panel_b_pay_ENOUT.findById('id_pay_ENOUT_allValue').setValue(records[pos].get('sumValue'));
	
	_panel_c_pay_ENOUT.findById('id_pay_ENOUT_freightCost').setValue(records[pos].get('freightCost'));
	_panel_c_pay_ENOUT.findById('id_pay_ENOUT_loadUnloadCost').setValue(records[pos].get('loadUnloadCost'));
	_panel_c_pay_ENOUT.findById('id_pay_ENOUT_extraCost').setValue(records[pos].get('extraCost'));
	_panel_c_pay_ENOUT.findById('id_pay_ENOUT_unitQuote').setValue(records[pos].get('unitQuote'));
	_panel_c_pay_ENOUT.findById('id_pay_ENOUT_chargeMode').setValue(records[pos].get('chargeMode'));
	_panel_c_pay_ENOUT.findById('id_pay_ENOUT_financialRemarks').setValue(records[pos].get('financialRemarks'));

}
