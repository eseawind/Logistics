
var _win_income_TRACKCOD = null;
var _records_income_TRACKCOD = null;
var _pos_income_TRACKCOD = 1;
var mask_income_TRACKCOD = null;
function onEditTrackCOD(records,index)
{
	if(null == _win_income_TRACKCOD)
	{
	    createWindow_income_TRACKCOD();
	}
	else
	{
		_formPanel_transIncome_TRACKCOD.getForm().reset();
		_pos_income_TRACKCOD = 1;
	}
	_grid_MCOD.getTopToolbar().findById('btn_listsave_MCOD').disable();
	mask_income_TRACKCOD.hide();
	_records_income_TRACKCOD = records;
	loadData_income_TRACKCOD(_records_income_TRACKCOD,_pos_income_TRACKCOD);
	_win_income_TRACKCOD.setPagePosition(GET_WIN_X(_win_income_TRACKCOD.width),GET_WIN_Y());
    _win_income_TRACKCOD.show();
}

function createWindow_income_TRACKCOD()
{
	_win_income_TRACKCOD = new Ext.Window({
        title: '财务维护',
        iconCls: 'commonSaveAll',
        width: 750,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items:_formPanel_transIncome_TRACKCOD,
        listeners: {
        	move:LISTENER_WIN_MOVE_FUNC,
        	beforehide:function()
        	{
        		_ds_MCOD.reload();
        		_grid_MCOD.getTopToolbar().findById('btn_listsave_MCOD').enable();
        	}
        }	
    });
    _win_income_TRACKCOD.show();
    mask_income_TRACKCOD = new Ext.LoadMask(_win_income_TRACKCOD.body, {msg:"正在保存,请稍后..."});
}

var _panel_a_income_TRACKCOD = new Ext.Panel({
	
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
					fieldLabel: '建单日期',
					id: 'id_income_TRACKCOD_dateCreated',
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
					fieldLabel: '运输单号',
					id: 'id_income_TRACKCOD_transId',
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
					fieldLabel: '货款状态',
					id: 'id_income_TRACKCOD_state',
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
					id: 'id_income_TRACKCOD_customer',
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
					id: 'id_income_TRACKCOD_origin',
					readOnly: true,
					width: 200
				}]
			},
			{//Column 2
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					fieldLabel: '目的地',
					id: 'id_income_TRACKCOD_destination',
					readOnly: true,
					width: 200
				}]
			}]
		}
	]
});

var _panel_c_income_TRACKCOD = new Ext.Panel({
	
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
					fieldLabel: '代收货款',
					id: 'id_income_TRACKCOD_expectedPayment',
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
					fieldLabel: '已收货款',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					id: 'id_income_TRACKCOD_recievedPayment',
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
					fieldLabel: '手续费率',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					id: 'id_income_TRACKCOD_procedureFeeRate',
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
					fieldLabel: '费率收入',
					allowNegative: true,
					value: 0,
					minValue: -MAX_DOUBLE,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					id: 'id_income_TRACKCOD_procedureFee',
					width: NUMBERFIELDWIDTH
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
				items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_COSTCENTER,
					       id: 'id_income_TRACKCOD_costCenter',
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
			},
			{//Column 2
				columnWidth: '0.68',
				layout: 'form',
				border: false,
				items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_COSTCENTER,
					       id: 'id_income_TRACKCOD_sellCenter',
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
					id: 'id_income_TRACKCOD_financialRemarks',
					width: 614
				}]
			}]
		}
	]
});

var _formPanel_transIncome_TRACKCOD = new Ext.FormPanel({
	
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
			if(_formPanel_transIncome_TRACKCOD.getForm().isValid())
			{
				var params = getSubmitParams_income_TRACKCOD(0);
				mask_income_TRACKCOD.show();
				Ext.Ajax.request({
					url: 'PaymentCollection.updateIncome.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_income_TRACKCOD.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_income_TRACKCOD == _records_income_TRACKCOD.length)
							{
								_win_income_TRACKCOD.hide();
								_ds_MCOD.reload();
							}else
							{
								_pos_income_TRACKCOD++;
								loadData_income_TRACKCOD(_records_income_TRACKCOD,_pos_income_TRACKCOD);
							}
							mask_income_TRACKCOD.hide();
						}
					},
					failure: function(response,options){
						mask_income_TRACKCOD.hide();
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
			if(_formPanel_transIncome_TRACKCOD.getForm().isValid())
			{
				var params = getSubmitParams_income_TRACKCOD(1);
				mask_income_TRACKCOD.show();
				Ext.Ajax.request({
					url: 'PaymentCollection.updateIncome.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							mask_income_TRACKCOD.hide();
							AJAX_FAILURE_CALLBACK(result,'数据保存失败!');	
						}else{
							if(_pos_income_TRACKCOD == _records_income_TRACKCOD.length)
							{
								_win_income_TRACKCOD.hide();
								_ds_MCOD.reload();
							}else
							{
								_pos_income_TRACKCOD++;
								loadData_income_TRACKCOD(_records_income_TRACKCOD,_pos_income_TRACKCOD);
							}
							mask_income_TRACKCOD.hide();
						}
					},
					failure: function(response,options){
						mask_income_TRACKCOD.hide();
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'数据保存错误!');
					},
					params: params
				});//Ajax
			}
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler:　function(){ _win_income_TRACKCOD.hide(); }
	}],
	
	items:[
		_panel_a_income_TRACKCOD,
		_panel_c_income_TRACKCOD
	]
});

function getSubmitParams_income_TRACKCOD(mode)
{
	params = {};
	params['pcdto.freightManifestID'] = _panel_a_income_TRACKCOD.findById('id_income_TRACKCOD_transId').getValue();
	params['pcdto.expectedPayment'] = _panel_c_income_TRACKCOD.findById('id_income_TRACKCOD_expectedPayment').getValue();
	params['pcdto.recievedPayment'] = _panel_c_income_TRACKCOD.findById('id_income_TRACKCOD_recievedPayment').getValue();
	params['pcdto.procedureFeeRate'] = _panel_c_income_TRACKCOD.findById('id_income_TRACKCOD_procedureFeeRate').getValue();
	params['pcdto.procedureFee'] = _panel_c_income_TRACKCOD.findById('id_income_TRACKCOD_procedureFee').getValue();
	params['pcdto.costCenter'] = _panel_c_income_TRACKCOD.findById('id_income_TRACKCOD_costCenter').getValue();
	params['pcdto.sellCenter'] = _panel_c_income_TRACKCOD.findById('id_income_TRACKCOD_sellCenter').getValue();
	params['pcdto.financialRemarks'] = _panel_c_income_TRACKCOD.findById('id_income_TRACKCOD_financialRemarks').getValue();
	if(mode)
		params['operType'] = '1';
	else
		params['operType'] = '0';
	return params;
}

function loadData_income_TRACKCOD(records,pos)
{
	if(records == null) return;
	if(records.length < pos || pos < 1) return;
	_win_income_TRACKCOD.setTitle('财务维护'+'&nbsp;&nbsp;[&nbsp;正在处理第&nbsp;'+pos+'&nbsp;单&nbsp;,&nbsp;共&nbsp;'+records.length+'&nbsp;单&nbsp;]');
	pos--;
	_panel_a_income_TRACKCOD.findById('id_income_TRACKCOD_dateCreated').setValue(records[pos].get('dateCreated'));
	_panel_a_income_TRACKCOD.findById('id_income_TRACKCOD_transId').setValue(records[pos].get('freightManifestID'));
	_panel_a_income_TRACKCOD.findById('id_income_TRACKCOD_state').setValue(records[pos].get('state'));
	_panel_a_income_TRACKCOD.findById('id_income_TRACKCOD_customer').setValue(records[pos].get('customer'));
	_panel_a_income_TRACKCOD.findById('id_income_TRACKCOD_origin').setValue(records[pos].get('origin'));
	_panel_a_income_TRACKCOD.findById('id_income_TRACKCOD_destination').setValue(records[pos].get('destination'));
	
	_panel_c_income_TRACKCOD.findById('id_income_TRACKCOD_expectedPayment').setValue(records[pos].get('expectedPayment'));
	_panel_c_income_TRACKCOD.findById('id_income_TRACKCOD_recievedPayment').setValue(records[pos].get('recievedPayment'));
	_panel_c_income_TRACKCOD.findById('id_income_TRACKCOD_procedureFeeRate').setValue(records[pos].get('procedureFeeRate'));
	_panel_c_income_TRACKCOD.findById('id_income_TRACKCOD_procedureFee').setValue(records[pos].get('procedureFee'));
	_panel_c_income_TRACKCOD.findById('id_income_TRACKCOD_costCenter').setValue(records[pos].get('costCenter'));
	_panel_c_income_TRACKCOD.findById('id_income_TRACKCOD_sellCenter').setValue(records[pos].get('sellCenter'));
	_panel_c_income_TRACKCOD.findById('id_income_TRACKCOD_financialRemarks').setValue(records[pos].get('financialRemarks'));

}
