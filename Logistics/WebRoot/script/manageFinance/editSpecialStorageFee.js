
var _win_fee_SPESTOR = null;
var _records_fee_SPESTOR = null;
var _pos_fee_SPESTOR = 1;
var mask_fee_SPESTOR = null;
function onEditSpecialStorageFee(records,index)
{
	if(null == _win_fee_SPESTOR)
	{
	    createWindow_fee_SPESTOR();
	}
	else
	{
		_formPanel_fee_SPESTOR.getForm().reset();
		_pos_fee_SPESTOR = 1;
	}
	_grid_SSF.getTopToolbar().findById('btn_listsave_SSF').disable();
	mask_fee_SPESTOR.hide();
	_records_fee_SPESTOR = records;
	loadData_fee_SPESTOR(_records_fee_SPESTOR,_pos_fee_SPESTOR);
	_win_fee_SPESTOR.setPagePosition(GET_WIN_X(_win_fee_SPESTOR.width),GET_WIN_Y());
    _win_fee_SPESTOR.show();
}

function createWindow_fee_SPESTOR()
{
	_win_fee_SPESTOR = new Ext.Window({
        title: '财务维护',
        iconCls: 'commonSaveAll',
        width: 750,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items:_formPanel_fee_SPESTOR,
        listeners: {
        	move:LISTENER_WIN_MOVE_FUNC,
        	beforehide:function()
        	{
        		_ds_SSF.reload();
        		_grid_SSF.getTopToolbar().findById('btn_listsave_SSF').enable();
        	}
        } 	
    });
    _win_fee_SPESTOR.show();
    mask_fee_SPESTOR = new Ext.LoadMask(_win_fee_SPESTOR.body, {msg:"正在保存,请稍后..."});
}

var _panel_a_fee_SPESTOR = new Ext.Panel({
	
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
					id: 'id_fee_SPESTOR_sheetId',
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
					id: 'id_fee_SPESTOR_dateCreated',
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
					id: 'id_fee_SPESTOR_warehouseName',
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
					id: 'id_fee_SPESTOR_customer',
					readOnly: true,
					width: 348
				}]
			}]
		}
	]
});

var _panel_b_fee_SPESTOR = new Ext.Panel({
	
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
					fieldLabel: '仓储面积',
					id: 'id_fee_SPESTOR_aera',
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
					fieldLabel: '仓储天数',
					readOnly:true,
					id: 'id_fee_SPESTOR_daysStock',
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
					fieldLabel: '开始日期',
					readOnly:true,
					id: 'id_fee_SPESTOR_dateStart',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 4
				columnWidth: '0.25',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '结束日期',
					readOnly:true,
					id: 'id_fee_SPESTOR_dateEnd',
					width: NUMBERFIELDWIDTH
				}]
			}]
		}
	]
});

var _panel_c_fee_SPESTOR = new Ext.Panel({
	
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
					id: 'id_fee_SPESTOR_stockFee',
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
					fieldLabel: '其他费用',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					id: 'id_fee_SPESTOR_extraCost',
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
					fieldLabel: '单位报价',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					id: 'id_fee_SPESTOR_quote',
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
				items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_COSTCENTER,
					       id: 'id_fee_SPESTOR_sellCenter',
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
					id: 'id_fee_SPESTOR_financialRemarks',
					width: 614
				}]
			}]
		}
	]
});

var _formPanel_fee_SPESTOR = new Ext.FormPanel({
	
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
			if(_formPanel_fee_SPESTOR.getForm().isValid())
			{
				var params = getSubmitParams_fee_SPESTOR(0);
				mask_fee_SPESTOR.show();
				Ext.Ajax.request({
					url: 'SpecialStockIncome.updateIncome.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_fee_SPESTOR.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_fee_SPESTOR == _records_fee_SPESTOR.length)
							{
								_win_fee_SPESTOR.hide();
								_ds_SSF.reload();
							}else
							{
								_pos_fee_SPESTOR++;
								loadData_fee_SPESTOR(_records_fee_SPESTOR,_pos_fee_SPESTOR);
							}
							mask_fee_SPESTOR.hide();
						}
					},
					failure: function(response,options){
						mask_fee_SPESTOR.hide();
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
			if(_formPanel_fee_SPESTOR.getForm().isValid())
			{
				var params = getSubmitParams_fee_SPESTOR(1);
				mask_fee_SPESTOR.show();
				Ext.Ajax.request({
					url: 'SpecialStockIncome.updateIncome.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_fee_SPESTOR.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_fee_SPESTOR == _records_fee_SPESTOR.length)
							{
								_win_fee_SPESTOR.hide();
								_ds_SSF.reload();
							}else
							{
								_pos_fee_SPESTOR++;
								loadData_fee_SPESTOR(_records_fee_SPESTOR,_pos_fee_SPESTOR);
							}
							mask_fee_SPESTOR.hide();
						}
					},
					failure: function(response,options){
						mask_fee_SPESTOR.hide();
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'数据保存错误!');
					},
					params: params
				});//Ajax
			}
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler:　function(){ _win_fee_SPESTOR.hide(); }
	}],
	
	items:[
		_panel_a_fee_SPESTOR,
		_panel_b_fee_SPESTOR,
		_panel_c_fee_SPESTOR
	]
});

function getSubmitParams_fee_SPESTOR(mode)
{
	params = {};
	params['income.incomeID'] = _panel_a_fee_SPESTOR.findById('id_fee_SPESTOR_sheetId').getValue();
	params['income.stockFee'] = _panel_c_fee_SPESTOR.findById('id_fee_SPESTOR_stockFee').getValue();
	params['income.sellCenter'] = _panel_c_fee_SPESTOR.findById('id_fee_SPESTOR_sellCenter').getValue();
	params['income.extraFee'] = _panel_c_fee_SPESTOR.findById('id_fee_SPESTOR_extraCost').getValue();
	params['income.quote'] = _panel_c_fee_SPESTOR.findById('id_fee_SPESTOR_quote').getValue();
	params['income.remarks'] = _panel_c_fee_SPESTOR.findById('id_fee_SPESTOR_financialRemarks').getValue();
	if(mode)
		params['operType'] = '1';
	else
		params['operType'] = '0';
	return params;
}

function loadData_fee_SPESTOR(records,pos)
{
	if(records == null) return;
	if(records.length < pos || pos < 1) return;
	_win_fee_SPESTOR.setTitle('财务维护'+'&nbsp;&nbsp;[&nbsp;正在处理第&nbsp;'+pos+'&nbsp;单&nbsp;,&nbsp;共&nbsp;'+records.length+'&nbsp;单&nbsp;]');
	pos--;
	_panel_a_fee_SPESTOR.findById('id_fee_SPESTOR_sheetId').setValue(records[pos].get('incomeID'));
	_panel_a_fee_SPESTOR.findById('id_fee_SPESTOR_dateCreated').setValue(records[pos].get('dateCreated'));
	_panel_a_fee_SPESTOR.findById('id_fee_SPESTOR_warehouseName').setValue(records[pos].get('warehouse'));
	_panel_a_fee_SPESTOR.findById('id_fee_SPESTOR_customer').setValue(records[pos].get('customer'));
	
	_panel_b_fee_SPESTOR.findById('id_fee_SPESTOR_aera').setValue(records[pos].get('area'));
	_panel_b_fee_SPESTOR.findById('id_fee_SPESTOR_daysStock').setValue(records[pos].get('daysStock'));
	_panel_b_fee_SPESTOR.findById('id_fee_SPESTOR_dateStart').setValue(records[pos].get('dateStart'));
	_panel_b_fee_SPESTOR.findById('id_fee_SPESTOR_dateEnd').setValue(records[pos].get('dateEnd'));
	
	_panel_c_fee_SPESTOR.findById('id_fee_SPESTOR_sellCenter').setValue(records[pos].get('sellCenter'));
	_panel_c_fee_SPESTOR.findById('id_fee_SPESTOR_stockFee').setValue(records[pos].get('stockFee'));
	_panel_c_fee_SPESTOR.findById('id_fee_SPESTOR_extraCost').setValue(records[pos].get('extraFee'));
	_panel_c_fee_SPESTOR.findById('id_fee_SPESTOR_quote').setValue(records[pos].get('quote'));
	_panel_c_fee_SPESTOR.findById('id_fee_SPESTOR_financialRemarks').setValue(records[pos].get('remarks'));

}
