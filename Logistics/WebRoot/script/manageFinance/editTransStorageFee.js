
var _win_fee_TRANSTOR = null;
var _records_fee_TRANSTOR = null;
var _pos_fee_TRANSTOR = 1;
var mask_fee_TRANSTOR = null;
function onEditTransStorageFee(records,index)
{
	if(null == _win_fee_TRANSTOR)
	{
	    createWindow_fee_TRANSTOR();
	}
	else
	{
		_formPanel_fee_TRANSTOR.getForm().reset();
		_pos_fee_TRANSTOR = 1;
	}
	_grid_TSSF.getTopToolbar().findById('btn_listsave_TSSF').disable();
	mask_fee_TRANSTOR.hide();
	_records_fee_TRANSTOR = records;
	loadData_fee_TRANSTOR(_records_fee_TRANSTOR,_pos_fee_TRANSTOR);
	_win_fee_TRANSTOR.setPagePosition(GET_WIN_X(_win_fee_TRANSTOR.width),GET_WIN_Y());
    _win_fee_TRANSTOR.show();
}

function createWindow_fee_TRANSTOR()
{
	_win_fee_TRANSTOR = new Ext.Window({
        title: '财务维护',
        iconCls: 'commonSaveAll',
        width: 750,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items:_formPanel_fee_TRANSTOR,
        listeners: {
        	move:LISTENER_WIN_MOVE_FUNC,
        	beforehide:function()
        	{
        		_ds_TSSF.reload();
        		_grid_TSSF.getTopToolbar().findById('btn_listsave_TSSF').enable();
        	}
        }
    });
    _win_fee_TRANSTOR.show();
    mask_fee_TRANSTOR = new Ext.LoadMask(_win_fee_TRANSTOR.body, {msg:"正在保存,请稍后..."});
}

var _panel_a_fee_TRANSTOR = new Ext.Panel({
	
	layout: 'form',
	style: 'margin:0px 0px 5px 0px',
	collapsible : false,
	title: '&nbsp;&nbsp;<&nbsp;移库单基本信息&nbsp;>',
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
					fieldLabel: '移库单号',
					id: 'id_fee_TRANSTOR_stockTransId',
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
					fieldLabel: '移库日期',
					id: 'id_fee_TRANSTOR_dateTransfered',
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
					id: 'id_fee_TRANSTOR_dateCreated',
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
					fieldLabel: '移出仓库',
					id: 'id_fee_TRANSTOR_outWarehouseName',
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
					fieldLabel: '移入仓库',
					id: 'id_fee_TRANSTOR_inWarehouseName',
					readOnly: true,
					width: TEXTFIELDWIDTH
				}]
			}]
		}
	]
});

var _panel_b_fee_TRANSTOR = new Ext.Panel({
	
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
					id: 'id_fee_TRANSTOR_allCount',
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
					id: 'id_fee_TRANSTOR_allWeight',
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
					id: 'id_fee_TRANSTOR_allVolume',
					width: NUMBERFIELDWIDTH
				}]
			}]
		}
	]
});

var _panel_c_fee_TRANSTOR = new Ext.Panel({
	
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
					id: 'id_fee_TRANSTOR_loadUnloadCost',
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
					id: 'id_fee_TRANSTOR_extraCost',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 4
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_COSTCENTER,
					       id: 'id_fee_TRANSTOR_costCenter',
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
		{//Row 2
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
					id: 'id_fee_TRANSTOR_financialRemarks',
					width: 614
				}]
			}]
		}
	]
});

var _formPanel_fee_TRANSTOR = new Ext.FormPanel({
	
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
			if(_formPanel_fee_TRANSTOR.getForm().isValid())
			{
				var params = getSubmitParams_fee_TRANSTOR(0);
				mask_fee_TRANSTOR.show();
				Ext.Ajax.request({
					url: 'StockTransferFinance.updateIncome.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_fee_TRANSTOR.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_fee_TRANSTOR == _records_fee_TRANSTOR.length)
							{
								_win_fee_TRANSTOR.hide();
								_ds_TSSF.reload();
							}else
							{
								_pos_fee_TRANSTOR++;
								loadData_fee_TRANSTOR(_records_fee_TRANSTOR,_pos_fee_TRANSTOR);
							}
							mask_fee_TRANSTOR.hide();
						}
					},
					failure: function(response,options){
						mask_fee_TRANSTOR.hide();
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
			if(_formPanel_fee_TRANSTOR.getForm().isValid())
			{
				var params = getSubmitParams_fee_TRANSTOR(1);
				mask_fee_TRANSTOR.show();
				Ext.Ajax.request({
					url: 'StockTransferFinance.updateIncome.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_fee_TRANSTOR.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_fee_TRANSTOR == _records_fee_TRANSTOR.length)
							{
								_win_fee_TRANSTOR.hide();
								_ds_TSSF.reload();
							}else
							{
								_pos_fee_TRANSTOR++;
								loadData_fee_TRANSTOR(_records_fee_TRANSTOR,_pos_fee_TRANSTOR);
							}
							mask_fee_TRANSTOR.hide();
						}
					},
					failure: function(response,options){
						mask_fee_TRANSTOR.hide();
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'数据保存错误!');
					},
					params: params
				});//Ajax
			}
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler:　function(){ _win_fee_TRANSTOR.hide(); }
	}],
	
	items:[
		_panel_a_fee_TRANSTOR,
		_panel_b_fee_TRANSTOR,
		_panel_c_fee_TRANSTOR
	]
});

function getSubmitParams_fee_TRANSTOR(mode)
{
	params = {};
	params['stfdto.stockTransferManifestID'] = _panel_a_fee_TRANSTOR.findById('id_fee_TRANSTOR_stockTransId').getValue();
	params['stfdto.costCenter'] = _panel_c_fee_TRANSTOR.findById('id_fee_TRANSTOR_costCenter').getValue();
	params['stfdto.loadUnloadCost'] = _panel_c_fee_TRANSTOR.findById('id_fee_TRANSTOR_loadUnloadCost').getValue();
	params['stfdto.extraCost'] = _panel_c_fee_TRANSTOR.findById('id_fee_TRANSTOR_extraCost').getValue();
	params['stfdto.remarks'] = _panel_c_fee_TRANSTOR.findById('id_fee_TRANSTOR_financialRemarks').getValue();
	if(mode)
		params['operType'] = '1';
	else
		params['operType'] = '0';
	return params;
}

function loadData_fee_TRANSTOR(records,pos)
{
	if(records == null) return;
	if(records.length < pos || pos < 1) return;
	_win_fee_TRANSTOR.setTitle('财务维护'+'&nbsp;&nbsp;[&nbsp;正在处理第&nbsp;'+pos+'&nbsp;单&nbsp;,&nbsp;共&nbsp;'+records.length+'&nbsp;单&nbsp;]');
	pos--;
	_panel_a_fee_TRANSTOR.findById('id_fee_TRANSTOR_stockTransId').setValue(records[pos].get('stockTransferManifestID'));
	_panel_a_fee_TRANSTOR.findById('id_fee_TRANSTOR_dateTransfered').setValue(records[pos].get('dateTransfered'));
	_panel_a_fee_TRANSTOR.findById('id_fee_TRANSTOR_dateCreated').setValue(records[pos].get('dateCreated'));
	_panel_a_fee_TRANSTOR.findById('id_fee_TRANSTOR_outWarehouseName').setValue(records[pos].get('outWarehouseName'));
	_panel_a_fee_TRANSTOR.findById('id_fee_TRANSTOR_inWarehouseName').setValue(records[pos].get('inWarehouseName'));
	
	_panel_b_fee_TRANSTOR.findById('id_fee_TRANSTOR_allCount').setValue(records[pos].get('sumAmount'));
	_panel_b_fee_TRANSTOR.findById('id_fee_TRANSTOR_allWeight').setValue(records[pos].get('sumWeight'));
	_panel_b_fee_TRANSTOR.findById('id_fee_TRANSTOR_allVolume').setValue(records[pos].get('sumVolume'));
	
	_panel_c_fee_TRANSTOR.findById('id_fee_TRANSTOR_costCenter').setValue(records[pos].get('costCenter'));
	_panel_c_fee_TRANSTOR.findById('id_fee_TRANSTOR_loadUnloadCost').setValue(records[pos].get('loadUnloadCost'));
	_panel_c_fee_TRANSTOR.findById('id_fee_TRANSTOR_extraCost').setValue(records[pos].get('extraCost'));
	_panel_c_fee_TRANSTOR.findById('id_fee_TRANSTOR_financialRemarks').setValue(records[pos].get('remarks'));

}
