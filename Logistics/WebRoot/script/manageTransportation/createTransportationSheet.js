
var _win_create_MTS = null;
var _outId_create_MTS = "";
function onCreateTransportationSheet(id)
{
	if(null == _win_create_MTS)
	{
	    createWindow_create_MTS();
	    initiaData_create_MTS();
	}else
	{
		resetPage_create_MTS();
		_panel_a_MTS.expand(false);
		_panel_b_MTS.expand(false);
		_panel_c_MTS.expand(false);
		_formPanel_createSheet_MTS.body.scrollTo('top',0,false);
	}
	if(id!=null)
	{
		_outId_create_MTS=" 对应出库单号为："+id;
	}
	STORE_CUSTOMER_LOAD(lastCustomerType);
	STORE_TRANS_WAY_LOAD();
	STORE_CACHE_GOODS_LOAD();
	_win_create_MTS.setPagePosition(GET_WIN_X(_win_create_MTS.width),GET_WIN_Y_M(650));
    _win_create_MTS.show();
}

function createWindow_create_MTS()
{
	_win_create_MTS = new Ext.Window({
        title: '新建运输单',
        iconCls: 'commonCreateSheet',
        width: 800,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createSheet_MTS,
        listeners: LISTENER_WIN_MOVE
    });
}

	var _record_MTS = Ext.data.Record.create([
	    {name: 'cargoID'},
	    {name: 'amount'},
	    {name: 'weight'},
	    {name: 'volume'},
	    {name: 'value'}
	]);
	
	var _ds_createSheet_MTS = new Ext.data.Store({
		reader : new Ext.data.JsonReader({
					root : 'resultMapList'
				}, 
				_record_MTS
			)
	});

	var _sm_createSheet_MTS = new Ext.grid.CheckboxSelectionModel();
	
	var _cm_createSheet_MTS = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_createSheet_MTS,
			{
				dataIndex : 'cargoID',
				header : '货物名称',
				width: 200,
				editor : 
				{
				       xtype : 'combo',
				       store : STORE_CACHE_GOODS,
				       valueField : 'cargoID',
				       displayField : 'cargoID',
				       mode : 'local',
				       forceSelection : false,
				       selectOnFocus:true,
				       typeAhead: false,
				       editable : true,
				       triggerAction : 'all',
				       allowBlank : false,
				       listeners: {
				       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
				       }
				}
			},{
				dataIndex : 'amount',
				header : '数量',
				editor : {
					xtype: 'numberfield',
					allowNegative: false,
					value: 0,
					allowDecimals : false,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true
				}
			},{
				dataIndex : 'weight',
				header : '重量(KG)',
				editor : {
					xtype: 'numberfield',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true
				}	
			},{
				dataIndex : 'volume',
				header : '体积(M3)',
				editor : {
					xtype: 'numberfield',
					decimalPrecision:V_DECIMAL,
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true
				}
			},{
				dataIndex : 'value',
				header : '声明价值',
				editor : {
					xtype: 'numberfield',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true
				}	
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	// 定义一个表格面板
	var _grid_createSheet_MTS = new Ext.grid.EditorGridPanel({
				sm : _sm_createSheet_MTS,
				cm : _cm_createSheet_MTS,
				ds : _ds_createSheet_MTS,
				style: 'margin:0px 0px 2px 0px',
				stripeRows : true,
				loadMask : true,
				border : true,
				frame: false,
				clicksToEdit : 1,
				height : 200,
				autoScroll: true,
				width: 639,
				draggable : false,
				fieldLabel: '货物列表',
				// UI视图配置
				viewConfig : {
					forceFit : true
				},
				
				tbar : new Ext.Toolbar({
					border: true,
					items : [
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						text : "删除",
						iconCls: 'commonDelete',
						handler: function(){
							GRID_LIST_DELETE(_grid_createSheet_MTS);
						}		
					},'-',
					{
						xtype: 'tbfill'
					},
					{
						xtype: 'label',
						text: '数量报价(/件):'
					},
					{
						xtype: 'tbspacer'
					},
					{
						xtype: 'numberfield',
						allowNegative: false,
						id: 'count_MTS',
						name: 'fmdto.priceByAmount',
						value: 0,
						maxValue: MAX_DOUBLE,
						allowBlank: false,
						selectOnFocus: true,
						width: 50
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'label',
						text: '重量报价(/KG):'
					},
					{
						xtype: 'tbspacer'
					},
					{
						xtype: 'numberfield',
						allowNegative: false,
						value: 0,
						name: 'fmdto.priceByWeight',
						id: 'weight_MTS',
						maxValue: MAX_DOUBLE,
						allowBlank: false,
						selectOnFocus: true,
						width: 50
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'label',
						text: '体积报价(/M3):'
					},
					{
						xtype: 'tbspacer'
					},
					{
						xtype: 'numberfield',
						allowNegative: false,
						value: 0,
						name: 'fmdto.priceByVolume',
						id: 'volume_MTS',
						maxValue: MAX_DOUBLE,
						allowBlank: false,
						selectOnFocus: true,
						width: 50
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'label',
						text: '保价率(%):'
					},
					{
						xtype: 'tbspacer'
					},
					{
						xtype: 'numberfield',
						allowNegative: false,
						value: 5,
						name: 'fmdto.insuranceRate',
						id: 'percent_MTS',
						maxValue: 100,
						allowBlank: false,
						selectOnFocus: true,
						width: 50
					},'-',
					{
						text : "添加",
						iconCls: 'commonAdd',
						handler: function(){
							if(_grid_createSheet_MTS.getStore().getCount()>= MAX_TRANS_GOODS_COUNT)
							{
								Ext.Msg.show({
									title : '操作提示',
									msg : '货品信息不能超过['+MAX_TRANS_GOODS_COUNT.toString()+']条！',
									buttons : Ext.Msg.OK,
									icon : Ext.Msg.WARNING
								});
								return;
							}
							var record = new _record_MTS({
							    cargoID: '编辑货品名称',
							    amount:0,
							    weight:0,
							    volume:0,
							    value: 0
							});
							_grid_createSheet_MTS.getStore().add(record);
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
						width: 5
					},
					{
						xtype: 'label',
						text: '数量合计:'
					},{
						xtype: 'tbspacer'
					},
					{
						xtype: 'tbtext',
						text: '0',
						id: 'allCount_MTS',
						width: 45
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'label',
						text: '重量合计:'
					},{
						xtype: 'tbspacer'
					},
					{
						xtype: 'tbtext',
						text: '0',
						id: 'allWeight_MTS',
						width: 45
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'label',
						text: '体积合计:'
					},{
						xtype: 'tbspacer'
					},
					{
						xtype: 'tbtext',
						text: '0',
						id: 'allVolume_MTS',
						width: 45
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'label',
						text: '声明价值:'
					},{
						xtype: 'tbspacer'
					},
					{
						xtype: 'tbtext',
						text: '0',
						id: 'allValue_MTS',
						width: 45
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
						text: '计费方式:'
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					new Ext.form.ComboBox({
					       store :STORE_TRANS_CHARGE_MODE,
					       valueField : 'value',
					       displayField : 'value',
					       mode : 'local',
					       id: 'mode_MTS',
					       hiddenName:'fmdto.chargeMode',
					       forceSelection : true,
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '计费方式',
					       value: '体积(M3)',
					       width:　75
					}),
					{
						xtype: 'tbspacer',
						width: 3
					},
					{
						text : "计算运费",
						iconCls: 'calculator',
						handler: function(){
							
							var storeA = _grid_createSheet_MTS.getStore();
							storeA.commitChanges();
							
							var eCount = _grid_createSheet_MTS.getTopToolbar().findById('count_MTS');
							var eWeight = _grid_createSheet_MTS.getTopToolbar().findById('weight_MTS');
							var eVolume = _grid_createSheet_MTS.getTopToolbar().findById('volume_MTS');
							var ePercent = _grid_createSheet_MTS.getTopToolbar().findById('percent_MTS');
							
							if(!eCount.isValid() || !eWeight.isValid() || !eVolume.isValid() || !ePercent.isValid())
							{
								Ext.MessageBox.show({
							   		title: '操作提示',
						   			msg: '请填写正确的报价信息！',
							   		buttons: Ext.MessageBox.OK,
							   		icon: Ext.MessageBox.WARNING		   		
						   		});
							}
							var pCount = eCount.getValue();
							var pWeight = eWeight.getValue();
							var pVolume = eVolume.getValue();
							var pPercent = ePercent.getValue();
							
							var mode = _grid_createSheet_MTS.getBottomToolbar().findById('mode_MTS').getValue();
							
							var allCount = 0; var allWeight = 0; var allVolume = 0; allValue = 0;
							var money = 0; var secureMoney = 0;
							for(var i=0; i<storeA.getCount(); i++)
							{
								var record = storeA.getAt(i);
								allCount += record.get('amount');
								allWeight += record.get('weight');
								allVolume += record.get('volume');
								allValue += record.get('value');
							}
							
							if(mode == '重量(KG)'){ money = allWeight*pWeight; }
							else if(mode == '体积(M3)'){ money = allVolume*pVolume; }
							else { money = allCount*pCount; }
							secureMoney = pPercent * allValue / 100.0;
							allCount=CHANGEDECIMAL(allCount);
							allVolume=CHANGEDECIMAL_M(allVolume,V_DECIMAL);
							allWeight=CHANGEDECIMAL(allWeight);
							allValue=CHANGEDECIMAL(allValue);
							secureMoney=CHANGEDECIMAL(secureMoney);
							money=CHANGEDECIMAL(money);
							
							_grid_createSheet_MTS.getBottomToolbar().findById('allCount_MTS').setText(allCount.toString());
							_grid_createSheet_MTS.getBottomToolbar().findById('allWeight_MTS').setText(allWeight.toString());
							_grid_createSheet_MTS.getBottomToolbar().findById('allVolume_MTS').setText(allVolume.toString());
							_grid_createSheet_MTS.getBottomToolbar().findById('allValue_MTS').setText(allValue.toString());
							_panel_b_MTS.findById('freightFee_MTS').setValue(money);
							if(_panel_b_MTS.findById('hasSecureFee_MTS').getValue()=='是'){
								_panel_b_MTS.findById('secureFee_MTS').setValue(secureMoney);
							}
						}
					},
					{
						xtype: 'tbspacer',
						width: 5
					}]
				})
			});

var _ds_ti_MTS = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'FreightManifest.queryConsigners.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			[{
				name : 'consigner'
			},{
				name : 'consignerPhone'
			},{
				name : 'consignerAddress'
			}]
		)
});

var _ds_shou_MTS = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'FreightManifest.queryConsignees.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			[{
				name : 'consignee'
			},{
				name : 'consigneePhone'
			},{
				name : 'consigneeAddress'
			}]
		)
});

var _ds_take_MTS = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'FreightManifest.queryConsigneeCompanies.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			[{
				name : 'consigneeCompany'
			}]
		)
});
			
var lastCustomerType = '合同客户';
			
var _panel_a_MTS = new Ext.Panel({
	
	layout: 'form',
	style: 'margin:0px 0px 5px 0px',
	collapsible : true,
	title: '&nbsp;&nbsp;<&nbsp;客户方与收货方信息&nbsp;>',
	bodyStyle: PADDING,
	items: [
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.4',
				layout: 'form',
				border: false,
				items: new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : new Ext.data.SimpleStore({      
				              fields : ["value"],
				              data : [['合同客户'], ['零散客户']]
				       }),
				       valueField : 'value',
				       displayField : 'value',
				       mode : 'local',
				       forceSelection : true,
				       hiddenName : 'fmdto.customerType',
				       editable : false,
				       triggerAction : 'all',
				       allowBlank : false,
				       fieldLabel : '客户类型',
				       width:　TEXTFIELDWIDTH,
				       value: '合同客户',
				       listeners: {
				       		select: function(combo, record, index){
				       			var customer = _panel_a_MTS.findById('customerName_create_MTS');
				       			if(lastCustomerType==record.get('value'))
				       				return;
				       			STORE_CUSTOMER.removeAll();
				       			if('零散客户'==record.get('value'))
				       			{
				       				lastCustomerType = '零散客户';
				       				STORE_CUSTOMER_LOAD(lastCustomerType);
				       				customer.forceSelection = false;
				       			}else{
				       				lastCustomerType = '合同客户';
				       				STORE_CUSTOMER_LOAD(lastCustomerType);
				       				customer.forceSelection = true;
				       			}
				       			_ds_ti_MTS.removeAll();
								_ds_shou_MTS.removeAll();
								_ds_take_MTS.removeAll();
				       			customer.reset();
				       			_panel_a_MTS.findById('customerID_create_MTS').reset();
				       			_panel_a_MTS.findById('ti_MTS').reset();
				       			_panel_a_MTS.findById('ti_phone_create_MTS').reset();
				       			_panel_a_MTS.findById('ti_address_create_MTS').reset();
				       			_panel_a_MTS.findById('take_MTS').reset();
				       			_panel_a_MTS.findById('shou_MTS').reset();
				       			_panel_a_MTS.findById('shou_phone_create_MTS').reset();
				       			_panel_a_MTS.findById('shou_address_create_MTS').reset();
				       		}
				       }
				})
			},
			{//Column 2
				columnWidth: '0.6',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '客户单号',
					width: TEXTFIELDWIDTH,
					name: 'fmdto.customerNumber',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT
				}]
			}]
		},
		{//Row 2
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.4',
				layout: 'form',
				border: false,
				items: [
				{
					xtype : 'textfield',
					id: 'customerID_create_MTS',
					name : 'cusdto.customerID',
					value: '',
					hidden: true
				},
				new Ext.form.ComboBox({
				       xtype : 'combo',
				       id: 'customerName_create_MTS',
				       store : STORE_CUSTOMER,
				       valueField : 'jointName',
				       displayField : 'jointName',
				       mode : 'local',
				       forceSelection : true,
				       hiddenName : 'fmdto.customer',
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
				       			var customerId = _panel_a_MTS.findById('customerID_create_MTS');
				       			customerId.setValue(record.get('customerID'));
				       			_ds_ti_MTS.load({
							    	callback:function(record,option,success){
										STORE_CALLBACK(_ds_ti_MTS.reader.message,_ds_ti_MTS.reader.valid,success);
										if(success)
										{
											_grid_createSheet_MTS.getTopToolbar().findById('count_MTS').setValue(_ds_ti_MTS.reader.data.priceByAmount);
											_grid_createSheet_MTS.getTopToolbar().findById('weight_MTS').setValue(_ds_ti_MTS.reader.data.priceByWeight);
											_grid_createSheet_MTS.getTopToolbar().findById('volume_MTS').setValue(_ds_ti_MTS.reader.data.priceByVolume);
											_panel_b_MTS.findById('deliveryFee_MTS').setValue(_ds_ti_MTS.reader.data.deliveryQuote);
											_panel_b_MTS.findById('takingFee_MTS').setValue(_ds_ti_MTS.reader.data.takingQuote);
											
											if(_ds_ti_MTS.getCount()>0)
											{
												_panel_a_MTS.findById('ti_MTS').fireEvent('select',_panel_a_MTS.findById('ti_MTS'),_ds_ti_MTS.getAt(0),0);
											}
										}
									 },
									params: { 'cusdto.customerID': record.get('customerID'),
											'frdto.freightRouteID':_panel_a_MTS.findById('freightRouteID_create_MTS').getValue()}
								});
				       			_ds_take_MTS.load({
							    	callback:function(record,option,success){
												STORE_CALLBACK(_ds_take_MTS.reader.message,_ds_take_MTS.reader.valid,success);
												if(success)
												{
													if(_ds_take_MTS.getCount()>0)
													{
														_panel_a_MTS.findById('take_MTS').fireEvent('select',_panel_a_MTS.findById('take_MTS'),_ds_take_MTS.getAt(0),0);
													}
												}
											 },
									params: { 'cusdto.customerID': record.get('customerID') }
								});
								if(STORE_COSTCENTER.getCount()>0)
								{
									_panel_b_MTS.findById('costCenter_create_MTS').setValue(STORE_COSTCENTER.getAt(0).get('costCenterID'));
								}
								if(STORE_SELLCENTER.getCount()>0)
								{
									_panel_b_MTS.findById('sellCenter_create_MTS').setValue(STORE_SELLCENTER.getAt(0).get('sellCenterID'));
								}
				       		}
				       }
				})]
			},
			{//Column 2
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items: new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : _ds_ti_MTS,
				       valueField : 'consigner',
				       id: 'ti_MTS',
				       displayField : 'consigner',
				       mode : 'local',
				       forceSelection : false,
				       hiddenName : 'fmdto.consigner',
				       editable : true,
				       selectOnFocus:true,
				       typeAhead: true,
				       triggerAction : 'all',
				       allowBlank : false,
				       fieldLabel : '发货人',
				       width:　TEXTFIELDWIDTH,
				       listeners: {
				       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY,
				       		select: function(combo, record, index){
				       			_panel_a_MTS.findById('ti_phone_create_MTS').setValue(record.get('consignerPhone'));
				       			_panel_a_MTS.findById('ti_address_create_MTS').setValue(record.get('consignerAddress'));
				       			this.setValue(record.get('consigner'));
				       		}
				       }
				})
			},
			{//Column 3
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '发货人电话',
					width: TEXTFIELDWIDTH,
					id: 'ti_phone_create_MTS',
					name: 'fmdto.consignerPhone',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT
				}]
			}]
		},
		{//Row 3
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1.0',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '提货地址',
					width: 638,
					id: 'ti_address_create_MTS',
					name: 'fmdto.consignerAddress',
					regex: REGEX_COMMON_M,
					regexText: REGEX_COMMON_M_TEXT
				}]
			}]
		},
		{//Row 4
			layout: 'column',
			border: false,
			items: [
				{//Column 1
					columnWidth: '0.4',
					layout: 'form',
					border: false,
					items: new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : _ds_take_MTS,
				       valueField : 'consigneeCompany',
				       displayField : 'consigneeCompany',
				       id: 'take_MTS',
				       mode : 'local',
				       forceSelection : false,
				       hiddenName : 'fmdto.consigneeCompany',
				       editable : true,
				       selectOnFocus:true,
				       typeAhead: false,
				       triggerAction : 'all',
				       allowBlank : false,
				       fieldLabel : '收货单位',
				       width:　200,
				       listeners: {
				       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY,
				       		select: function(combo, record, index){
				       			_ds_shou_MTS.load({
							    	callback:function(record,option,success){
										STORE_CALLBACK(_ds_shou_MTS.reader.message,_ds_shou_MTS.reader.valid,success);
										if(success)
										{
											if(_ds_shou_MTS.getCount()>0)
											{
												_panel_a_MTS.findById('shou_MTS').fireEvent('select',_panel_a_MTS.findById('shou_MTS'),_ds_shou_MTS.getAt(0),0);
											}
										}
									 },
									params: { 
										'cusdto.customerID': _panel_a_MTS.findById('customerID_create_MTS').getValue(),
										'consigneedto.company':record.get('consigneeCompany')
									}
								});
				       			this.setValue(record.get('consigneeCompany'));
				       		}
				       }
				})
				},
				{//Column 2
					columnWidth: '0.3',
					layout: 'form',
					border: false,
				    items: new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : _ds_shou_MTS,
				       valueField : 'consignee',
				       displayField : 'consignee',
				       mode : 'local',
				       id: 'shou_MTS',
				       forceSelection : false,
				       hiddenName : 'fmdto.consignee',
				       editable : true,
				       selectOnFocus:true,
				       typeAhead: false,
				       triggerAction : 'all',
				       allowBlank : false,
				       fieldLabel : '收货人',
				       width:　TEXTFIELDWIDTH,
				       listeners: {
				       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY,
				       		select: function(combo, record, index){
				       			_panel_a_MTS.findById('shou_phone_create_MTS').setValue(record.get('consigneePhone'));
				       			_panel_a_MTS.findById('shou_address_create_MTS').setValue(record.get('consigneeAddress'));
				       			this.setValue(record.get('consignee'));
				       		}
				       }
					})
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
						id: 'shou_phone_create_MTS',
						name: 'fmdto.consigneePhone',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT
					}]
				}]
		},
		{//Row 5
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1.0',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '收货地址',
					width: 638,
					id: 'shou_address_create_MTS',
					name: 'fmdto.consigneeAddress',
					regex: REGEX_COMMON_M,
					regexText: REGEX_COMMON_M_TEXT
				}]
			}]
		},
		{//Row 6
				layout: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '0.5',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       id:'freightRouteID_create_MTS',
					       store : STORE_TRANS_WAY,
					       valueField : 'freightRouteID',
					       displayField : 'jointName',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'frdto.freightRouteID',
					       editable : true,
					       selectOnFocus:true,
				       	   typeAhead: false,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '运输路线',
					       width:　250,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY,
					       		select: function(combo, record, index){
					       			Ext.Ajax.request({
									url: 'FreightRoute.queryFreightDays.action',
									method: 'POST',
									success : function(response,options){
										var result = Ext.util.JSON.decode(response.responseText);
										if(!result.success){
											AJAX_FAILURE_CALLBACK(result,'预计运输天数获取失败！');	
										}else{
											var date = new Date().add(Date.DAY, result.data.days).dateFormat('Y-m-d');
											_panel_a_MTS.findById('dateExpected_create_MTS').setValue(date);
										}
									},
									failure: function(response,options){
										AJAX_SERVER_FAILURE_CALLBACK(response,options,'预计运输天数获取错误');
									},
									params:{ 'freightRouteDTO.freightRouteID': record.get('freightRouteID') }
								});//Ajax
								if(_panel_a_MTS.findById('customerID_create_MTS').getValue()!='')
								{
									Ext.Ajax.request({
										url: 'FreightManifest.queryCustomerQuote.action',
										method: 'POST',
										success : function(response,options){
											var result = Ext.util.JSON.decode(response.responseText);
											if(!result.success){
												AJAX_FAILURE_CALLBACK(result,'报价信息获取失败！');	
											}else{
												_grid_createSheet_MTS.getTopToolbar().findById('count_MTS').setValue(result.data.priceByAmount);
												_grid_createSheet_MTS.getTopToolbar().findById('weight_MTS').setValue(result.data.priceByWeight);
												_grid_createSheet_MTS.getTopToolbar().findById('volume_MTS').setValue(result.data.priceByVolume);
												_panel_b_MTS.findById('deliveryFee_MTS').setValue(result.data.deliveryQuote);
												_panel_b_MTS.findById('takingFee_MTS').setValue(result.data.takingQuote);
											}
										},
										failure: function(response,options){
											AJAX_SERVER_FAILURE_CALLBACK(response,options,'报价信息获取失败');
										},
										params:{ 'frdto.freightRouteID': record.get('freightRouteID'),
											'cusdto.customerID':_panel_a_MTS.findById('customerID_create_MTS').getValue()}
									});//Ajax
								}
				       		}
			       	   }
					})
				]},{//Column 2
						columnWidth: '0.2',
						layout: 'form',
						border: false,
						items: [
						{
							xtype: 'button',
							text: '&nbsp;&nbsp;添加新路线',
							style: 'margin: 0px 0px 0px 0px',
							iconCls :'transportationWay',
							width: TEXTFIELDWIDTH,
							handler: function(){
								onCommonCreateTransportationWay();
							}
						}
					]
				},{//Column 3
						columnWidth: '0.3',
						layout: 'form',
						border: false,
						items: [
						{
							xtype: 'datefield',
							fieldLabel: '预计到货',
							id: 'dateExpected_create_MTS',
							format: 'Y-m-d',
							name: 'fmdto.dateExpected',
							value: new Date().dateFormat('Y-m-d'),
							allowBlank: false,
							editable: false,
							width: TEXTFIELDWIDTH
						}
					]
				}
			]}
	]
})

var _panel_b_MTS = new Ext.Panel({
	
	layout: 'form',
	style: 'margin:0px 0px 5px 0px',
	collapsible : true,
	title: '&nbsp;&nbsp;<&nbsp;货物列表与费用信息&nbsp;>',
	bodyStyle: PADDING,
	items: [
		{//Row 1-Grid
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1.0',
				layout: 'form',
				border: false,
				items: _grid_createSheet_MTS
			}]
		},
			{//Row 2
				layout: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '0.265',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : new Ext.data.SimpleStore({      
					              fields : ["returnValue", "displayText"],
					              data : [['是', '有保险'], ['否', '无保险']]
					       }),
					       
					       valueField : 'returnValue',
					       displayField : 'displayText',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'fmdto.isInsurance',
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       value: '是',
					       id: 'hasSecureFee_MTS',
					       fieldLabel : '保险费',
					       width:　120,
					       listeners: {
				       		select: function(combo, record, index){
				       			var secureFee = _panel_b_MTS.findById('secureFee_MTS');
				       			if('否'==record.get('returnValue'))
				       			{
				       				secureFee.disable();
				       				secureFee.reset();
				       			}else
				       			{
				       				secureFee.enable();
				       			}
				       		}
				       	  }
					})]
				},
				{//Column 2
					columnWidth: '0.135',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'numberfield',
						hideLabel: true,
						allowNegative: false,
						value: 0,
						id : 'secureFee_MTS',
						maxValue: MAX_DOUBLE,
						allowBlank: false,
						selectOnFocus: true,
						name: 'fmdto.insuranceFee',
						width: 78
					}]
				},
				{//Column 3
					columnWidth: '0.3',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'numberfield',
						allowNegative: false,
						value: 0,
						id : 'freightFee_MTS',
						maxValue: MAX_DOUBLE,
						allowBlank: false,
						selectOnFocus: true,
						name: 'fmdto.freightFee',
						fieldLabel: '运费',
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 4
					columnWidth: '0.3',
					layout: 'form',
					border: false,
					items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : new Ext.data.SimpleStore({      
					              fields : ["value"],
					              data : [['月结'],['现结'], ['运费到付']]
					       }),
					       valueField : 'value',
					       displayField : 'value',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'fmdto.paymentType',
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       value: '月结',
					       fieldLabel : '结算方式',
					       width:　TEXTFIELDWIDTH
					})]
				}]
			},
		{//Row 2
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '0.265',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : new Ext.data.SimpleStore({      
					              fields : ["returnValue", "displayText"],
					              data : [['是', '有代垫货款'], ['否', '无代垫货款']]
					       }),
					       valueField : 'returnValue',
					       displayField : 'displayText',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'fmdto.isPrepaid',
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       value: '否',
					       fieldLabel : '代垫货款',
					       width:　120,
					       listeners: {
				       		select: function(combo, record, index){
				       			var prepaidFee = _panel_b_MTS.findById('prepaidFee_MTS');
				       			if('否'==record.get('returnValue'))
				       			{
				       				prepaidFee.reset();
				       				prepaidFee.disable();
				       			}else
				       			{
				       				prepaidFee.enable();
				       			}
				       		}
				       	  }
					})]
				},
				{//Column 2
					columnWidth: '0.135',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'numberfield',
						hideLabel: true,
						allowNegative: false,
						value: 0,
					    disabled: true,
						id: 'prepaidFee_MTS',
						maxValue: MAX_DOUBLE,
						allowBlank: false,
						selectOnFocus: true,
						name: 'fmdto.prepaidFee',
						width: 78
					}]
				},
				{//Column 3
					columnWidth: '0.3',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'numberfield',
						allowNegative: false,
						id:'deliveryFee_MTS',
						value: 0,
						maxValue: MAX_DOUBLE,
						allowBlank: false,
						selectOnFocus: true,
						name: 'fmdto.deliveryFee',
						fieldLabel: '送货费',
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 4
					columnWidth: '0.3',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_COSTCENTER,
					       id:'costCenter_create_MTS',
					       valueField : 'costCenterID',
					       displayField : 'costCenterID',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: true,
					       forceSelection : true,
					       hiddenName : 'fmdto.costCenter',
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
				items: [{//Column 1
					columnWidth: '0.265',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : new Ext.data.SimpleStore({      
					              fields : ["returnValue", "displayText"],
					              data : [['是', '有代收货款'], ['否', '无代收货款']]
					       }),
					       
					       valueField : 'returnValue',
					       displayField : 'displayText',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'fmdto.isCollection',
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       value: '否',
					       fieldLabel : '代收货款',
					       width:　120,
					       listeners: {
				       		select: function(combo, record, index){
				       			var collectionFee = _panel_b_MTS.findById('collectionFee_MTS');
				       			if('否'==record.get('returnValue'))
				       			{
				       				collectionFee.reset();
				       				collectionFee.disable();
				       			}else
				       			{
				       				collectionFee.enable();
				       			}
				       		}
				       	  }
					})]
				},
				{//Column 2
					columnWidth: '0.135',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'numberfield',
						hideLabel: true,
						allowNegative: false,
						value: 0,
						disabled: true,
						id: 'collectionFee_MTS',
						maxValue: MAX_DOUBLE,
						allowBlank: false,
						selectOnFocus: true,
						name: 'fmdto.collectionFee',
						width: 78
					}]
				},
				{//Column 3
					columnWidth: '0.3',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'numberfield',
						allowNegative: false,
						id:'takingFee_MTS',
						value: 0,
						maxValue: MAX_DOUBLE,
						allowBlank: false,
						selectOnFocus: true,
						name: 'fmdto.consignFee',
						fieldLabel: '提货费',
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 4
					columnWidth: '0.3',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_SELLCENTER,
					       id:'sellCenter_create_MTS',
					       valueField : 'sellCenterID',
					       displayField : 'sellCenterID',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: true,
					       forceSelection : true,
					       hiddenName : 'fmdto.sellCenter',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '销售中心',
					       width:　TEXTFIELDWIDTH,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
					       }
					})]
				}]
			},
			{//Row 4
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '0.4',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'numberfield',
						allowNegative: false,
						value: 0,
						id: 'sumFee_create_MTS',
						readOnly: true,
						name: 'XXX',
						fieldLabel: '费用合计',
						width: 120
					}]
				},
				{//Column 2
					columnWidth: '0.6',
					layout: 'form',
					border: false,
					items:[{
						xtype: 'button',
						text: '&nbsp;计算费用合计',
						style: 'margin: 0px 0px 0px 75px',
						iconCls :'calculator',
						width: 120,
						handler: function(){
							var a = _panel_b_MTS.findById('secureFee_MTS').getValue();
							var b = _panel_b_MTS.findById('freightFee_MTS').getValue();
							var c = _panel_b_MTS.findById('deliveryFee_MTS').getValue();
							var d = _panel_b_MTS.findById('takingFee_MTS').getValue();
							_panel_b_MTS.findById('sumFee_create_MTS').setValue(a+b+c+d);
						}
					}]
				}]
			}
	]
})

var _panel_c_MTS = new Ext.Panel({
	
	layout: 'form',
	style: 'margin:0px 0px 5px 0px',
	collapsible : true,
	title: '&nbsp;&nbsp;<&nbsp;送货与签收信息&nbsp;>',
	bodyStyle: PADDING,
	items: [
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.4',
				layout: 'form',
				border: false,
				items: new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : new Ext.data.SimpleStore({      
				              fields : ["value"],
				              data : [['送货'], ['自提']]
				       }),
				       valueField : 'value',
				       displayField : 'value',
				       mode : 'local',
				       forceSelection : true,
				       hiddenName : 'fmdto.freightType',
				       editable : false,
				       triggerAction : 'all',
				       allowBlank : false,
				       fieldLabel : '送货方式',
				       width:　TEXTFIELDWIDTH,
				       value: '送货'
				})
			},
			{//Column 2
				columnWidth: '0.6',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					regex: REGEX_COMMON_M,
					regexText: REGEX_COMMON_M_TEXT,
					name: 'fmdto.ackRequirement',
					value:'验明收货人身份证/含公司字样章',
					fieldLabel: '签收要求',
					width: 343
				}]
			}]
		},
		{//Row 2
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1.0',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'textfield',
					regex: REGEX_COMMON_M,
					regexText: REGEX_COMMON_M_TEXT,
					name: 'fmdto.announcements',
					fieldLabel: '特殊要求',
					width: 639
				}]
			}]
		},
		{//Row 3
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1.0',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'textfield',
					id:'id_remarks_create_MTS',
					regex: REGEX_COMMON_M,
					regexText: REGEX_COMMON_M_TEXT,
					name: 'fmdto.remarks',
					fieldLabel: '备注信息',
					width: 639
				}]
			}]
		}
	]
	
});

function resetPage_create_MTS()
{
	_formPanel_createSheet_MTS.getForm().reset();
	_ds_ti_MTS.removeAll();
	_ds_shou_MTS.removeAll();
	_ds_take_MTS.removeAll();
	_grid_createSheet_MTS.getStore().removeAll();
	_grid_createSheet_MTS.getBottomToolbar().findById('allCount_MTS').setText('0');
	_grid_createSheet_MTS.getBottomToolbar().findById('allWeight_MTS').setText('0');
	_grid_createSheet_MTS.getBottomToolbar().findById('allVolume_MTS').setText('0');
	_grid_createSheet_MTS.getBottomToolbar().findById('allValue_MTS').setText('0');
	_panel_b_MTS.findById('secureFee_MTS').enable();
	_panel_b_MTS.findById('prepaidFee_MTS').disable();
	_panel_b_MTS.findById('collectionFee_MTS').disable();
	_outId_create_MTS="";
	lastCustomerType="合同客户";
	initiaData_create_MTS();
}

function initiaData_create_MTS()
{
	var record = new _record_MTS({
	    cargoID: '编辑货品名称',
	    amount:0,
	    weight:0,
	    volume:0,
	    value: 0
	});
	_grid_createSheet_MTS.getStore().add(record);
}

var _formPanel_createSheet_MTS = new Ext.FormPanel({
	
	layout: 'form',
	style: 'margin:-7px -2px 0px -7px',
	frame: true,
	labelAlign: 'right',
	bodyStyle: 'padding: 0px',
	height: 500,
	autoScroll: true,
	labelWidth: 70,
	border: true,
	buttonAlign: 'center',
	
	buttons:[
	{
		text: '保存',
		iconCls: 'commonSave',
		handler: function()
		{
			if(_formPanel_createSheet_MTS.getForm().isValid())
			{
				_grid_createSheet_MTS.getStore().commitChanges();
				if(CHECKLISTSAME(_grid_createSheet_MTS,'cargoID','编辑货品名称'))
				{
					Ext.Msg.show({
						title : '操作提示',
						msg : '货品列表有[同名]或[未编辑]货物！',
						buttons : Ext.Msg.OK,
						icon : Ext.Msg.WARNING
					});
					return;
				}
				var params = getGridParams_MTS();
				var remarks = _panel_c_MTS.findById('id_remarks_create_MTS').getValue();
				_panel_c_MTS.findById('id_remarks_create_MTS').setValue(remarks+_outId_create_MTS);
				_formPanel_createSheet_MTS.getForm().submit({
					url: 'FreightManifest.insert.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_grid_MTS.getStore().reload();
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
			resetPage_create_MTS();
			_formPanel_createSheet_MTS.body.scrollTo('top',0,true);
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler: function(){ _win_create_MTS.hide(); }
	}],
	
	items:[
		_panel_a_MTS,
		_panel_b_MTS,
		_panel_c_MTS
	]
});

function getGridParams_MTS()
{
	var params = {};
	var storeA = _grid_createSheet_MTS.getStore();
	storeA.commitChanges();
	var count = storeA.getCount();
	for(var i=0; i<count; i++)
	{
		var record = storeA.getAt(i);
		params['cargoList['+i.toString()+'].cargoID']=record.get('cargoID');
		params['cargoList['+i.toString()+'].amount']=record.get('amount');
		params['cargoList['+i.toString()+'].weight']=record.get('weight');
		params['cargoList['+i.toString()+'].volume']=record.get('volume');
		params['cargoList['+i.toString()+'].value']=record.get('value');
	}
	return params;
}
