var _win_create_MOSS = null;
var _price_create_MOSS = { amountQuote:0.0,weightQuote:0.0,volumeQuote:0.0 };
var _count_create_MOSS = { amount:0,weight:0,volume:0};
var _laststate_create_MOSS = { storageId:'',customerId:'' };
function onCreateOutStorageSheet()
{
	if(null == _win_create_MOSS)
	{
	    createWindow_create_MOSS();
	    STORE_CUSTOMER_ALL_LOAD();
	}else
	{
		resetPage_create_MOSS();
	}
	_win_create_MOSS.setPagePosition(GET_WIN_X(_win_create_MOSS.width),GET_WIN_Y_M(600));
    _win_create_MOSS.show();
}

function createWindow_create_MOSS()
{
	_win_create_MOSS = new Ext.Window({
        title: '新建出库单',
        iconCls: 'commonCreateSheet',
        width: 800,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createSheet_MOSS,
        listeners: LISTENER_WIN_MOVE
    });
}
var _record_MOSS = Ext.data.Record.create([
    {name: 'itemID'},
    {name: 'itemName'},
    {name: 'itemNumber'},
    {name: 'batch'},
    {name: 'unitWeight'},
    {name: 'unitVolume'},
    {name: 'stockAmount'},
    {name: 'amount'},
    {name: 'weight'},
    {name: 'volume'},
    {name: 'dateStockin'}
]);
var _ds_createSheet_MOSS = new Ext.data.Store({
	reader : new Ext.data.JsonReader({
				root : 'resultMapList'
			}, 
			_record_MOSS
		)
});

	var _sm_createSheet_MOSS = new Ext.grid.CheckboxSelectionModel();
	var _cm_createSheet_MOSS = new Ext.grid.ColumnModel({
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_createSheet_MOSS,
			{
				width:80,
				dataIndex : 'dateStockin',
				header : '入库日期'	
			},
			{
				width:60,
				dataIndex : 'itemID',
				header : '物料编号'	
			},{
				width:160,
				dataIndex : 'itemName',
				header : '物料名称'
			},{
				width:80,
				dataIndex : 'itemNumber',
				header : '物料代码'
			},{
				width:60,
				dataIndex : 'batch',
				header : '批次'
			},{
				width:60,
				dataIndex : 'stockAmount',
				header : '库存数量'
			},{
				width:90,
				dataIndex : 'amount',
				header : '出库数量(编辑)',
				editor : {
					xtype: 'numberfield',
					allowNegative: false,
					value: 1,
					allowDecimals : false,
					maxValue: MAX_DOUBLE,
					minValue: 1,
					allowBlank: false,
					selectOnFocus: true
				}
			},{
				width:60,
				dataIndex : 'unitWeight',
				header : '单位重量'	
			},{
				width:60,
				dataIndex : 'unitVolume',
				header : '单位体积'
			}],
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_createSheet_MOSS = new Ext.grid.EditorGridPanel({
				sm : _sm_createSheet_MOSS,
				cm : _cm_createSheet_MOSS,
				ds : _ds_createSheet_MOSS,
				style: 'margin:0px 0px 2px 0px',
				stripeRows : true,
				loadMask : true,
				border : true,
				frame: false,
				height : 200,
				clicksToEdit : 1,
				autoScroll: true,
				width: 641,
				draggable : false,
				fieldLabel: '物料列表',
				viewConfig : {
					forceFit : false
				},
				tbar : new Ext.Toolbar({
					border: true,
					items : [
					{
						text : "移除",
						iconCls: 'commonDelete',
						handler: function(){
							GRID_LIST_DELETE(_grid_createSheet_MOSS);
						}
					},'-',
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'tbfill'
					},
					{
						xtype: 'label',
						id:'title_price_create_MOSS',
						text: '数量报价：'
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'numberfield',
						allowNegative: false,
						value: 0,
						name: 'somdto.unitPrice',
						id: 'price_create_MOSS',
						maxValue: MAX_DOUBLE,
						allowBlank: false,
						selectOnFocus: true,
						width: 60
					},
					{
						xtype: 'tbspacer',
						width: 5
					},'-',
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						text : "查询添加物料",
						iconCls:'commonQuery',
						id :'btn_add_goods_create_MOSS',
						handler: function(){
							var storage = _formPanel_createSheet_MOSS.findById('storage_create_MOSS');
							var customer = _formPanel_createSheet_MOSS.findById('customer_create_MOSS');
							if(storage.getValue()=='' || customer.getValue()=='')
							{
								Ext.Msg.show({
									title : '操作提示',
									msg : '请先选择库存地与客户！',
									buttons : Ext.Msg.OK,
									icon : Ext.Msg.WARNING
								});
								return;
							}
							onQueryOutGoods(1,customer.getValue(),storage.getValue(),customer.getRawValue(),storage.getRawValue());
						}
					},
					{
						xtype: 'tbspacer',
						width: 5
					}]
				}),
				bbar: new Ext.Toolbar({
					border: true,
					frame: true,
					items : [
					{
						xtype: 'tbspacer',
						width: 10
					},
					{
						xtype: 'label',
						text: '数量合计：'
					},{
						xtype: 'tbspacer'
					},
					{
						xtype: 'tbtext',
						text: '0',
						id: 'allCount_create_MOSS',
						width: 50
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'label',
						text: '重量合计：'
					},{
						xtype: 'tbspacer'
					},
					{
						xtype: 'tbtext',
						text: '0',
						id: 'allWeight_create_MOSS',
						width: 50
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'label',
						text: '体积合计：'
					},{
						xtype: 'tbspacer'
					},
					{
						xtype: 'tbtext',
						text: '0',
						id: 'allVolume_create_MOSS',
						width: 50
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'tbfill'
					},'-',
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'label',
						text: '计费方式：'
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					new Ext.form.ComboBox({
					       store : STORE_TRANS_CHARGE_MODE,
					       valueField : 'value',
					       displayField : 'value',
					       mode : 'local',
					       id: 'mode_create_MOSS',
					       hiddenName:'somdto.chargeMode',
					       forceSelection : true,
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '计费方式',
					       value: '数量',
					       width:　100,
					       listeners: {
					       		select: function(combo, record, index){
					       			var title = _grid_createSheet_MOSS.getTopToolbar().findById('title_price_create_MOSS');
					       			var price = _grid_createSheet_MOSS.getTopToolbar().findById('price_create_MOSS');
					       			if(record.get('value')=='数量')
					       			{
					       				title.setText('数量报价：');
					       				price.setValue(_price_create_MOSS.amountQuote);
					       			}
					       			else if(record.get('value')=='重量(KG)')
					       			{
					       				title.setText('重量报价：');
					       				price.setValue(_price_create_MOSS.weightQuote);
					       			}
					       			else
					       			{
					       				title.setText('体积报价：');
					       				price.setValue(_price_create_MOSS.volumeQuote);
					       			}
					       		}
					       }
					}),
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						text : "&nbsp;&nbsp;计算出库费",
						iconCls: 'calculator',
						handler: function(){
							getOutStorageFee_create_MOSS();
						}
					},
					{
						xtype: 'tbspacer',
						width: 5
					}]
				})
			});


var _formPanel_createSheet_MOSS = new Ext.FormPanel({
	
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
			if(_formPanel_createSheet_MOSS.getForm().isValid())
			{
				var params = getGridParams_create_MOSS();
				_formPanel_createSheet_MOSS.getForm().submit({
					url: 'StockoutManifest.insert.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_grid_MOSS.getStore().reload();
						var value = _formPanel_createSheet_MOSS.findById('checkbox_auto_MOSS').getValue();
						if(value)
						{
							_win_create_MOSS.hide();
							autoCreateTransSheet_MOSS(action.result.message);
						}
					},
					failure: function(form,action){
						FORM_FAILURE_CALLBACK(form,action,"数据保存失败");
					},
					params: params
				});
			}
		}
	},{
		text: '重置',
		iconCls: 'commonReset',
		handler: function(){
			resetPage_create_MOSS();
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler: function(){_win_create_MOSS.hide();}
	}],
	
	items:[
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.4',
				layout: 'form',
				border: false,
				items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_STORAGE,
					       id : 'storage_create_MOSS',
					       valueField : 'warehouseID',
					       displayField : 'name',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'somdto.warehouseID',
					       editable : true,
					       typeAhead : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       selectOnFocus:true,
					       fieldLabel : '库存地',
					       width:　200,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY,
					       		select: function(combo, record, index){
					       			if(record.get('warehouseID')!=_laststate_create_MOSS.storageId)
					       			{
					       				resetGoodsGrid_create_MOSS();
					       				_laststate_create_MOSS.storageId = record.get('warehouseID');
					       			}
					       		}
					       }
					})]
			},
			{//Column 2
				columnWidth: '0.6',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'datefield',
					fieldLabel: '出库日期',
					format: 'Y-m-d',
					name: 'somdto.dateStockout',
					value: new Date().dateFormat('Y-m-d'),
					allowBlank: false,
					editable: false,
					width: TEXTFIELDWIDTH
				}]
			}]
		},
		{//Row 2
			layout: 'column',
			border: false,
			items: [
			{//Column 0
				columnWidth: '0.4',
				layout: 'form',
				border: false,
				items: new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : STORE_CUSTOMER_ALL,
				       id : 'customer_create_MOSS',
				       valueField : 'customerID',
				       displayField : 'jointName',
				       mode : 'local',
				       forceSelection : true,
				       hiddenName : 'somdto.customerID',
				       selectOnFocus:true,
				       typeAhead: false,
				       editable : true,
				       triggerAction : 'all',
				       allowBlank : false,
				       fieldLabel : '客户名称',
				       width:　200,
				       listeners: {
				       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY,
				       		select: function(combo, record, index){
				       			Ext.Ajax.request({
									url: 'StockoutManifest.queryCustomerQuote.action',
									method: 'POST',
									success : function(response,options){
										var result = Ext.util.JSON.decode(response.responseText);
										if(!result.success){
											AJAX_FAILURE_CALLBACK(result,'物料报价获取失败！');	
										}else{
											_price_create_MOSS.amountQuote = result.data.amountQuote;
											_price_create_MOSS.weightQuote = result.data.weightQuote;
											_price_create_MOSS.volumeQuote = result.data.volumeQuote;
											var title = _grid_createSheet_MOSS.getTopToolbar().findById('title_price_create_MOSS');
							       			var price = _grid_createSheet_MOSS.getTopToolbar().findById('price_create_MOSS');
							       			var mode = _grid_createSheet_MOSS.getBottomToolbar().findById('mode_create_MOSS').getValue();
							       			if(mode=='数量')
							       			{
							       				title.setText('数量报价：');
							       				price.setValue(_price_create_MOSS.amountQuote);
							       			}
							       			else if(mode=='重量(KG)')
							       			{
							       				title.setText('重量报价：');
							       				price.setValue(_price_create_MOSS.weightQuote);
							       			}
							       			else
							       			{
							       				title.setText('体积报价：');
							       				price.setValue(_price_create_MOSS.volumeQuote);
							       			}
										}
									},
									failure: function(response,options){
										AJAX_SERVER_FAILURE_CALLBACK(response,options,'物料报价获取错误！');
									},
									params:{ 'cusdto.customerID':record.get('customerID') }
								});//Ajax
								if(record.get('customerID')!=_laststate_create_MOSS.customerId)
				       			{
				       				resetGoodsGrid_create_MOSS();
				       				_laststate_create_MOSS.customerId = record.get('customerID');
				       			}
				       		}
				       }
				})
			},
			{//Column 1
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_COSTCENTER,
					       valueField : 'costCenterID',
					       displayField : 'costCenterID',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: false,
					       forceSelection : true,
					       hiddenName : 'somdto.sellCenter',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '销售中心',
					       width:　TEXTFIELDWIDTH,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
					       }
					})]
			},
			{//Column 2
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_COSTCENTER,
					       valueField : 'costCenterID',
					       displayField : 'costCenterID',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: false,
					       forceSelection : true,
					       hiddenName : 'somdto.costCenter',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '成本中心',
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
				columnWidth: '0.4',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '收货单位',
					width: 200,
					id:'id_consigneeCompany_create_COSS',
					name: 'somdto.consigneeCompany',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT
				}]
			},
			{//Column 2
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '收货人',
					id:'id_consignee_create_COSS',
					width: TEXTFIELDWIDTH,
					name: 'somdto.consignee',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT
				}]
			},
			{//Column 3
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '收货人电话',
					width: TEXTFIELDWIDTH,
					id:'id_consigneePhone_create_COSS',
					name: 'somdto.consigneePhone',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT
				}]
			}]
		},
		{//Row +
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1.0',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '收货地址',
					id:'id_consigneeAddress_create_COSS',
					regex: REGEX_COMMON_M,
					regexText: REGEX_COMMON_M_TEXT,
					name: 'somdto.consigneeAddress',
					width: 641
				}]
			}]
		},
		{//Row 3-Grid
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1.0',
				layout: 'form',
				border: false,
				items: _grid_createSheet_MOSS
			}]
		},
		{//Row 3+
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
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					id: 'stockoutFee_create_MOSS',
					selectOnFocus: true,
					name: 'somdto.stockoutFee',
					fieldLabel: '出库费',
					width: 80
				}]
			},
			{//Column 2
				columnWidth: '0.25',
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
					name: 'somdto.loadUnloadCost',
					fieldLabel: '装卸费',
					width: 80
				}]
			},
			{//Column 3
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'checkbox',
					fieldLabel: ' ',
					id:'checkbox_auto_MOSS',
					checked: false,
					boxLabel:'自动创建运输单',
					labelSeparator: ' ',
					width: 120
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
					name: 'somdto.remarks',
					width: 641
				}]
			}]
		}]
});

function getGridParams_create_MOSS()
{
	params = {};
	var store = _grid_createSheet_MOSS.getStore();
	var count = store.getCount();
	for(var i=0; i<count; i++)
	{
		var record = store.getAt(i);
		params['itemList['+i.toString()+'].itemID']=record.get('itemID');
		params['itemList['+i.toString()+'].amount']=record.get('amount');
		params['itemList['+i.toString()+'].dateStockin']=record.get('dateStockin');
	}
	
	params['somdto.sumAmount'] = _count_create_MOSS.amount;
	params['somdto.sumWeight'] = _count_create_MOSS.weight;
	params['somdto.sumVolume'] = _count_create_MOSS.volume;
	
	return params;
}

function getOutStorageFee_create_MOSS()
{
	var store = _grid_createSheet_MOSS.getStore();
	if(store.getCount()<=0) return;
	var allCount = 0; var allWeight = 0; var allVolume = 0;
	for(var i=0; i<store.getCount();i++)
	{
		var record = store.getAt(i);
		allCount += record.get('amount');
		allWeight += record.get('unitWeight') * record.get('amount');
		allVolume += record.get('unitVolume') * record.get('amount');
	}
	allCount=CHANGEDECIMAL(allCount);
	allVolume=CHANGEDECIMAL(allVolume);
	allWeight=CHANGEDECIMAL(allWeight);
	_count_create_MOSS.amount = allCount;
	_count_create_MOSS.volume = allVolume;
	_count_create_MOSS.weight = allWeight;
	_grid_createSheet_MOSS.getBottomToolbar().findById('allCount_create_MOSS').setText(allCount.toString());
	_grid_createSheet_MOSS.getBottomToolbar().findById('allWeight_create_MOSS').setText(allWeight.toString());
	_grid_createSheet_MOSS.getBottomToolbar().findById('allVolume_create_MOSS').setText(allVolume.toString());
	
	var price = _grid_createSheet_MOSS.getTopToolbar().findById('price_create_MOSS').getValue();
	var money = 0;
	var mode = _grid_createSheet_MOSS.getBottomToolbar().findById('mode_create_MOSS').getValue();
	if(mode=='数量')
	{
		money = allCount * price;
	}
	else if(mode=='重量(KG)')
	{
		money = allWeight * price;
	}
	else
	{
		money = allVolume * price;
	}
	
	_formPanel_createSheet_MOSS.findById('stockoutFee_create_MOSS').setValue(money);
	
}

function resetGoodsGrid_create_MOSS()
{
	onCloseQueryOutGoods();
	_ds_createSheet_MOSS.removeAll();
	_formPanel_createSheet_MOSS.findById('stockoutFee_create_MOSS').setValue(0);
	_grid_createSheet_MOSS.getBottomToolbar().findById('allCount_create_MOSS').setText('0');
	_grid_createSheet_MOSS.getBottomToolbar().findById('allWeight_create_MOSS').setText('0');
	_grid_createSheet_MOSS.getBottomToolbar().findById('allVolume_create_MOSS').setText('0');
	_count_create_MOSS.amount = 0;
	_count_create_MOSS.volume = 0;
	_count_create_MOSS.weight = 0;
}

function resetPage_create_MOSS()
{
	_formPanel_createSheet_MOSS.getForm().reset();
	_ds_createSheet_MOSS.removeAll();
	_grid_createSheet_MOSS.getBottomToolbar().findById('mode_create_MOSS').setValue('数量');
	_grid_createSheet_MOSS.getTopToolbar().findById('title_price_create_MOSS').setText('数量报价：');
	_grid_createSheet_MOSS.getTopToolbar().findById('price_create_MOSS').setValue(0);
	_grid_createSheet_MOSS.getBottomToolbar().findById('allCount_create_MOSS').setText('0');
	_grid_createSheet_MOSS.getBottomToolbar().findById('allWeight_create_MOSS').setText('0');
	_grid_createSheet_MOSS.getBottomToolbar().findById('allVolume_create_MOSS').setText('0');
	_price_create_MOSS.amountQuote = 0;
	_price_create_MOSS.weightQuote = 0;
	_price_create_MOSS.volumeQuote = 0;
	_count_create_MOSS.amount = 0;
	_count_create_MOSS.volume = 0;
	_count_create_MOSS.weight = 0;
}

function autoCreateTransSheet_MOSS(outId)
{
	onCreateTransportationSheet(outId);
	var a1 = _formPanel_createSheet_MOSS.findById('id_consigneeCompany_create_COSS').getValue();
	var a2 = _formPanel_createSheet_MOSS.findById('id_consignee_create_COSS').getValue();
	var a3 = _formPanel_createSheet_MOSS.findById('id_consigneePhone_create_COSS').getValue();
	var a4 = _formPanel_createSheet_MOSS.findById('id_consigneeAddress_create_COSS').getValue();
	var storage = _formPanel_createSheet_MOSS.findById('storage_create_MOSS').getRawValue();
	_panel_a_MTS.findById('take_MTS').setValue(a1);
	_panel_a_MTS.findById('shou_MTS').setValue(a2);
	_panel_a_MTS.findById('shou_phone_create_MTS').setValue(a3);
	_panel_a_MTS.findById('shou_address_create_MTS').setValue(a4);
	
	var store = _grid_createSheet_MOSS.getStore();
	if(store.getCount()<=0) return;
	for(var i=0; i<store.getCount();i++)
	{
		var record = store.getAt(i);
		var rd = new _record_MTS({
		    cargoID: record.get('itemName')+'-'+record.get('itemID')+'-'+record.get('itemNumber')+'-'+record.get('batch')+'-'+storage,
		    amount:record.get('amount'),
		    weight:record.get('unitWeight') * record.get('amount'),
		    volume:record.get('unitVolume') * record.get('amount'),
		    value: 0
		});
		_grid_createSheet_MTS.getStore().add(rd);
	}
}
