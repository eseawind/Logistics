
var _win_create_MES = null;

function onCreateEntruckingSheet()
{
	if(null == _win_create_MES)
	{
	    createWindow_create_MES();
	    STORE_TRANS_UNIT_LOAD();
		STORE_TRANS_WAY_LOAD();
		STORE_TAKE_UNIT_LOAD();
	}else
	{
		resetPage_create_MES();
	}
	_win_create_MES.setPagePosition(GET_WIN_X(_win_create_MES.width),GET_WIN_Y_M(650));
    _win_create_MES.show();
}

function createWindow_create_MES()
{
	_win_create_MES = new Ext.Window({
        title: '新建装车单',
        iconCls: 'commonCreateSheet',
        width: 815,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createSheet_MES,
        listeners: LISTENER_WIN_MOVE
    });
}
	var _record_MES = Ext.data.Record.create([
	    {name: 'freightManifestID'},
	    {name: 'origin'},
	    {name: 'destination'},
	    {name: 'customer'},
	    {name: 'amount'},
	    {name: 'weight'},
	    {name: 'volume'}
	]);
	
	var _ds_createSheet_MES = new Ext.data.Store({
		reader : new Ext.data.JsonReader({
					root : 'resultMapList'
				}, 
				_record_MES
			)
	});

	var _sm_createSheet_MES = new Ext.grid.CheckboxSelectionModel();
	
	var _cm_createSheet_MES = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_createSheet_MES,
			{
				dataIndex : 'freightManifestID',
				header : '运输单号'	
			},{
				dataIndex : 'origin',
				header : '始发地'	
			},{
				dataIndex : 'destination',
				header : '目的地'	
			},{
				dataIndex : 'customer',
				header : '客户名称'
			},{
				dataIndex : 'amount',
				header : '数量',
				width: 70
			},{
				dataIndex : 'weight',
				header : '重量(KG)',
				width: 70
			},{
				dataIndex : 'volume',
				header : '体积(M3)',
				width: 70
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_createSheet_MES = new Ext.grid.GridPanel({
				sm : _sm_createSheet_MES,
				cm : _cm_createSheet_MES,
				ds : _ds_createSheet_MES,
				style: 'margin:0',
				stripeRows : true,
				loadMask : true,
				border : true,
				frame: false,
				height : 180,
				autoScroll: true,
				width: 641,
				draggable : false,
				fieldLabel: '运输单列表',
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
						text : "移除",
						iconCls: 'commonDelete',
						handler: function(){
							GRID_LIST_DELETE(_grid_createSheet_MES);
						}
					},
					{
						xtype: 'tbfill'
					},
					{
						xtype: 'label',
						text: '运输单号:'
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'textfield',
						selectOnFocus: true,
						name:'XXX',
						id: 'transId_create_MES',
						enableKeyEvents: true,
						listeners: { keyup: KEYDOWN_LENGTH_VLIDATOR },
						width:　DATAFIELDWIDTH
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'tbspacer'
					},
					{
						text : "添加",
						iconCls: 'commonAdd',
						handler: function(){
							var findTrans = _grid_createSheet_MES.getTopToolbar().findById('transId_create_MES');
							var id = findTrans.getValue();
							if(_grid_createSheet_MES.getStore().getCount()>= MAX_TRANS_SHEET_COUNT)
							{
								Ext.Msg.show({
									title : '操作提示',
									msg : '运输单不能超过['+MAX_TRANS_SHEET_COUNT.toString()+']单！',
									buttons : Ext.Msg.OK,
									icon : Ext.Msg.WARNING
								});
								return;
							}
							var field = ['freightManifestID'];
							var value = [id];
							if(ISINLIST(_grid_createSheet_MES,field,value))
							{//判断列表中是否有相同的值
								Ext.Msg.show({
									title : '操作提示',
									msg : '列表中已经存在单号为['+id+']运输单！',
									buttons : Ext.Msg.OK,
									icon : Ext.Msg.WARNING
								});
								return;
							}
							Ext.Ajax.request({
									url: 'ShipmentManifest.queryOneFreightManifest.action',
									method: 'POST',
									success : function(response,options){
										var result = Ext.util.JSON.decode(response.responseText);
										if(!result.success){
											AJAX_FAILURE_CALLBACK(result,'运输单查询失败！');	
										}else{
											if(!result.data.freightManifestID)
											{
												Ext.Msg.show({
													title : '操作提示',
													msg : '所查询的运输单不存在！',
													buttons : Ext.Msg.OK,
													icon : Ext.Msg.WARNING
												});
												findTrans.reset();
											}else
											{
												var record = new _record_MES({
												    freightManifestID: result.data.freightManifestID,
												    origin:result.data.origin,
												    destination:result.data.destination,
												    customer:result.data.customer,
												    amount: result.data.amount,
												    weight: result.data.weight,
												    volume: result.data.volume
												});
												_grid_createSheet_MES.getStore().add(record);
												findTrans.reset();
											}
										}
									},
									failure: function(response,options){
										AJAX_SERVER_FAILURE_CALLBACK(response,options,'运输单查询请求错误!');
									},
									params:{ 'fmdto.freightManifestID': id }
							});//Ajax
						}
					},'-', 
					{
						text : "批量查询添加",
						iconCls: 'commonQuery',
						handler: function(){onQueryTransportationSheet(1);}
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
						text: '数量合计:'
					},{
						xtype: 'tbspacer'
					},
					{
						xtype: 'tbtext',
						text: '0',
						id: 'allCount_create_MES',
						width: 50
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
						id: 'allWeight_create_MES',
						width: 50
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
						id: 'allVolume_create_MES',
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
						text: '计费方式:'
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					new Ext.form.ComboBox({
					       store : STORE_ENTRUCK_CHARGE_MODE,
					       valueField : 'value',
					       displayField : 'value',
					       mode : 'local',
					       id: 'mode_create_MES',
					       hiddenName:'smdto.chargeMode',
					       forceSelection : true,
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '计费方式',
					       value: '直接计费',
					       width:　100
					}),
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						text : "&nbsp;&nbsp;计算运输费",
						iconCls: 'calculator',
						handler: function(){
							getEntruckingFee_create_MES();
						}
					},
					{
						xtype: 'tbspacer',
						width: 5
					}]
				})
			});

	var _ds_shou_create_MES = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'ShipmentManifest.queryConsignees.action',
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
	
	var _ds_carId_create_MES = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'Car.queryCarByContractor.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			[{
				name : 'carID'
			},{
				name : 'driverName'
			},{
				name : 'driverPhone'
			},{
				name : 'carType'
			}]
		)
	});
			
	var _formPanel_createSheet_MES = new Ext.FormPanel({
		
		layout: 'form',
		style: 'margin:0px',
		frame: true,
		labelAlign: 'right',
		bodyStyle: PADDING,
		height: 457,
		autoScroll: true,
		labelWidth: 70,
		border: false,
		buttonAlign: 'center',
		
		buttons:[
		{
			text: '保存',
			iconCls: 'commonSave',
			handler: function()
			{
				if(_formPanel_createSheet_MES.getForm().isValid())
				{
					var params = getGridParams_create_MES();
					_formPanel_createSheet_MES.getForm().submit({
						url: 'ShipmentManifest.insert.action',
						waitTitle:"保存数据",
						waitMsg: '正在保存...',
						success:function(form,action){
							_grid_MES.getStore().reload();
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
				resetPage_create_MES();
			}
		},{
			text: '取消',
			iconCls: 'commonCancel',
			handler: function(){ _win_create_MES.hide(); }
		}],
		
		items:[
			{//Row 1
				layout: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '0.4',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_TRANS_UNIT,
					       id: 'transUnitId_create_MES',
					       valueField : 'freightContractorID',
					       displayField : 'jointName',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'fcdto.freightContractorID',
					       editable : true,
					       selectOnFocus:true,
				     	   typeAhead: false,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '承运单位',
					       width:　200,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY,
					       		select: function(combo, record, index){
					       			_ds_carId_create_MES.removeAll();
					       			_formPanel_createSheet_MES.findById('driverName_create_MES').reset();
					       			_formPanel_createSheet_MES.findById('driverPhone_create_MES').reset();
					       			_formPanel_createSheet_MES.findById('carType_create_MES').reset();
					       			_formPanel_createSheet_MES.findById('carId_create_MES').reset();
					       			_ds_carId_create_MES.load({
				       				callback:function(record,option,success){
										STORE_CALLBACK(_ds_carId_create_MES.reader.message,_ds_carId_create_MES.reader.valid,success);
										if(success)
										{
											if(_ds_carId_create_MES.getCount()>0)
											{
												var carId = _formPanel_createSheet_MES.findById('carId_create_MES');
												carId.fireEvent('select',carId,_ds_carId_create_MES.getAt(0),0);
											}
										}
									 },
									params: { 
										'carDTO.freightContractorID':record.get('freightContractorID')
									}
					       			});
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
					       id:'carId_create_MES',
					       store : _ds_carId_create_MES,
					       valueField : 'carID',
					       displayField : 'carID',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'smdto.carID',
					       editable : true,
					       selectOnFocus: true,
				       	   typeAhead: false,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '车牌号',
					       width:　TEXTFIELDWIDTH,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY,
					       		select: function(combo, record, index){
					       			_formPanel_createSheet_MES.findById('driverName_create_MES').setValue(record.get('driverName'));
					       			_formPanel_createSheet_MES.findById('driverPhone_create_MES').setValue(record.get('driverPhone'));
					       			_formPanel_createSheet_MES.findById('carType_create_MES').setValue(record.get('carType'));
					       			this.setValue(record.get('carID'));
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
						fieldLabel: '驾驶员姓名',
						width: TEXTFIELDWIDTH,
						allowBlank: false,
						id:'driverName_create_MES',
						name: 'smdto.driverName',
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
						xtype: 'label',
						fieldLabel: ' ',
						labelSeparator: ' ',
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 2
					columnWidth: '0.3',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'textfield',
						fieldLabel: '车辆类型',
						id:'carType_create_MES',
						width: TEXTFIELDWIDTH,
						name: 'smdto.carType',
						readOnly:true
					}]
				},
				{//Column 3
					columnWidth: '0.3',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'textfield',
						fieldLabel: '驾驶员电话',
						id:'driverPhone_create_MES',
						width: TEXTFIELDWIDTH,
						name: 'smdto.driverPhone',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT
					}]
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
						items: new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_TAKE_UNIT,
					       valueField : 'consigneeCompany',
					       displayField : 'consigneeCompany',
					       mode : 'local',
					       forceSelection : false,
					       hiddenName : 'smdto.consigneeCompany',
					       editable : true,
					       selectOnFocus:true,
				       	   typeAhead: false,
					       triggerAction : 'all',
					       allowBlank : true,
					       fieldLabel : '收货单位',
					       width:　200,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY,
					       		select: function(combo, record, index){
					       			_ds_shou_create_MES.removeAll();
					       			_formPanel_createSheet_MES.findById('shou_phone_create_MES').reset();
					       			_formPanel_createSheet_MES.findById('shou_address_create_MES').reset();
					       			_formPanel_createSheet_MES.findById('shou_peo_create_MES').reset();
					       			_ds_shou_create_MES.load({
								    	callback:function(record,option,success){
											STORE_CALLBACK(_ds_shou_create_MES.reader.message,_ds_shou_create_MES.reader.valid,success);
											if(success)
											{
												if(_ds_shou_create_MES.getCount()>0)
												{
													var peo = _formPanel_createSheet_MES.findById('shou_peo_create_MES');
													peo.fireEvent('select',peo,_ds_shou_create_MES.getAt(0),0);
												}
											}
										 },
										params: { 
											'smdto.consigneeCompany':record.get('consigneeCompany')
										}
									});
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
					       id:'shou_peo_create_MES',
					       store : _ds_shou_create_MES,
					       valueField : 'consignee',
					       displayField : 'consignee',
					       mode : 'local',
					       forceSelection : false,
					       hiddenName : 'smdto.consignee',
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
					       			_formPanel_createSheet_MES.findById('shou_phone_create_MES').setValue(record.get('consigneePhone'));
					       			_formPanel_createSheet_MES.findById('shou_address_create_MES').setValue(record.get('consigneeAddress'));
					       			if(record.get('originPerson')==''||record.get('originPerson')==null)
					       			{
					       				_formPanel_createSheet_MES.findById('originPerson_create_MES').setValue(C_ORIGINPERSON);
						       			_formPanel_createSheet_MES.findById('originPhone_create_MES').setValue(C_ORIGINPHONE);
						       			_formPanel_createSheet_MES.findById('originAddress_create_MES').setValue(C_ORIGINADDRESS);
					       			}else
					       			{
						       			_formPanel_createSheet_MES.findById('originPerson_create_MES').setValue(record.get('originPerson'));
						       			_formPanel_createSheet_MES.findById('originPhone_create_MES').setValue(record.get('originPhone'));
						       			_formPanel_createSheet_MES.findById('originAddress_create_MES').setValue(record.get('originAddress'));
					       			}
					       			_formPanel_createSheet_MES.findById('originAgent_create_MES').setValue(record.get('originAgent'));
					       			_formPanel_createSheet_MES.findById('destinationAgent_create_MES').setValue(record.get('destinationAgent'));
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
							id:'shou_phone_create_MES',
							name: 'smdto.consigneePhone',
							regex: REGEX_COMMON_S,
							regexText: REGEX_COMMON_S_TEXT
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
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '收货地址',
						name: 'smdto.consigneeAddress',
						id:'shou_address_create_MES',
						regex: REGEX_COMMON_M,
						regexText: REGEX_COMMON_M_TEXT,
						width: 641
					}]
				}]
			},
			{//Row 9+
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '0.4',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'label',
						fieldLabel: ' ',
						labelSeparator: ' ',
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 2
					columnWidth: '0.3',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'textfield',
						fieldLabel: '发货人',
						id:'originPerson_create_MES',
						width: TEXTFIELDWIDTH,
						name: 'smdto.originPerson',
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
						fieldLabel: '联系电话',
						id:'originPhone_create_MES',
						width: TEXTFIELDWIDTH,
						name: 'smdto.originPhone',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT
					}]
				}]
			},
			{//Row 9++
				layout: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '1.0',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '发货地址',
						id:'originAddress_create_MES',
						name: 'smdto.originAddress',
						regex: REGEX_COMMON_M,
						regexText: REGEX_COMMON_M_TEXT,
						width: 641
					}]
				}]
			},
			{//Row 9
				layout: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '0.4',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'textfield',
						fieldLabel: '始发站',
						id:'originAgent_create_MES',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						name: 'smdto.originAgent',
						width: 200
					}]
				},
				{//Column 2
					columnWidth: '0.6',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'textfield',
						fieldLabel: '到货站',
						id:'destinationAgent_create_MES',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						name: 'smdto.destinationAgent',
						width: 200
					}]
				}]
			},
			{//Row 5
				layout: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '0.5',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_TRANS_WAY,
					       id:'freightRouteId_create_MES',
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
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
				       	   }
					})
				]},{//Column 2
						columnWidth: '0.5',
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
				}
			]},
			{//Row 6
				layout: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: _grid_createSheet_MES
				}]
			},
			{//Row 7
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '0.3',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'numberfield',
						allowNegative: false,
						value: 0,
						maxValue: MAX_DOUBLE,
						allowBlank: false,
						id: 'entruckingFee_create_MES',
						selectOnFocus: true,
						name: 'smdto.freightCost',
						fieldLabel: '运输费',
						width: 100
					}]
				},
				{//Column 2
					columnWidth: '0.3',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'numberfield',
						allowNegative: false,
						id:'deliveryFee_create_MES',
						value: 0,
						maxValue: MAX_DOUBLE,
						allowBlank: false,
						selectOnFocus: true,
						name: 'smdto.loadUnloadCost',
						fieldLabel: '送货费',
						width: 100
					}]
				},
				{//Column 3
					columnWidth: '0.4',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'numberfield',
						allowNegative: false,
						id:'extraFee_create_MES',
						value: 0,
						maxValue: MAX_DOUBLE,
						allowBlank: false,
						selectOnFocus: true,
						name: 'smdto.otherCost',
						fieldLabel: '其它费用',
						width: 100
					}]
				}]
			},
			{//Row 7
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '0.3',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'numberfield',
						allowNegative: false,
						value: 0,
						id: 'sumFee_create_MES',
						readOnly: true,
						name: 'XXX',
						fieldLabel: '费用合计',
						width: 100
					}]
				},
				{//Column 2
					columnWidth: '0.7',
					layout: 'form',
					border: false,
					items:[{
						xtype: 'button',
						text: '&nbsp;计算费用合计',
						style: 'margin: 0px 0px 0px 75px',
						iconCls :'calculator',
						width: 100,
						handler: function(){
							var a = _formPanel_createSheet_MES.findById('entruckingFee_create_MES').getValue();
							var b = _formPanel_createSheet_MES.findById('deliveryFee_create_MES').getValue();
							var c = _formPanel_createSheet_MES.findById('extraFee_create_MES').getValue();
							_formPanel_createSheet_MES.findById('sumFee_create_MES').setValue(a+b+c);
						}
					}]
				}]
			},
			{//Row 8
				layout: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '1.0',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'textfield',
						fieldLabel: '注意事项',
						regex: REGEX_COMMON_M,
						regexText: REGEX_COMMON_M_TEXT,
						name: 'smdto.announcements',
						width: 641
					}]
				}]
			},
			{//Row 9
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
						name: 'smdto.remarks',
						width: 641
					}]
				}]
			}
		]
	});

	function getEntruckingFee_create_MES()
	{
		var params = {};
		var store = _grid_createSheet_MES.getStore();
		store.commitChanges();
		
		var transUnitId = _formPanel_createSheet_MES.findById('transUnitId_create_MES').getValue();
		var carId = _formPanel_createSheet_MES.findById('carId_create_MES').getValue();
		var freightRouteId = _formPanel_createSheet_MES.findById('freightRouteId_create_MES').getValue();
		
		var allCount = 0; var allWeight = 0; var allVolume = 0;
		for(var i=0; i<store.getCount();i++)
		{
			var record = store.getAt(i);
			allCount += record.get('amount');
			allWeight += record.get('weight');
			allVolume += record.get('volume');
		}
		allCount=CHANGEDECIMAL(allCount);
		allVolume=CHANGEDECIMAL_M(allVolume,V_DECIMAL);
		allWeight=CHANGEDECIMAL(allWeight);
		_grid_createSheet_MES.getBottomToolbar().findById('allCount_create_MES').setText(allCount.toString());
		_grid_createSheet_MES.getBottomToolbar().findById('allWeight_create_MES').setText(allWeight.toString());
		_grid_createSheet_MES.getBottomToolbar().findById('allVolume_create_MES').setText(allVolume.toString());
		
		if(store.getCount()<=0) return;
		
		if(freightRouteId==''||carId==''||transUnitId=='')
		{
			Ext.Msg.show({
				title : '操作提示',
				msg : '请先选择承运单位、车牌号和运输路线！',
				buttons : Ext.Msg.OK,
				icon : Ext.Msg.WARNING
			});
			return;
		}
		
		params['cargodto.amount'] = allCount;
		params['cargodto.weight'] = allWeight;
		params['cargodto.volume'] = allVolume;
		
		params['smdto.chargeMode'] = _grid_createSheet_MES.getBottomToolbar().findById('mode_create_MES').getValue();
		params['fcdto.freightContractorID'] = transUnitId;
		params['cardto.carID'] = carId;
		params['frdto.freightRouteID'] = freightRouteId;
		
		Ext.Ajax.request({
			url: 'ShipmentManifest.calculateFreightCost.action',
			method: 'POST',
			success : function(response,options){
				var result = Ext.util.JSON.decode(response.responseText);
				if(!result.success){
					AJAX_FAILURE_CALLBACK(result,'费用计算失败！');	
				}else{
					var value = result.data.FreightCost==null?0:result.data.FreightCost;
					var delivery = result.data.deliveryQuote==null?0:result.data.deliveryQuote;
					var extra = result.data.extraQuote==null?0:result.data.extraQuote;
					_formPanel_createSheet_MES.findById('entruckingFee_create_MES').setValue(value);
					_formPanel_createSheet_MES.findById('deliveryFee_create_MES').setValue(delivery);
					_formPanel_createSheet_MES.findById('extraFee_create_MES').setValue(extra);
				}
			},
			failure: function(response,options){
				AJAX_SERVER_FAILURE_CALLBACK(response,options,'费用计算请求错误!');
			},
			params: params
		});//Ajax
	}

	function getGridParams_create_MES()
	{
		params = {};
		var store = _grid_createSheet_MES.getStore();
		var count = store.getCount();
		for(var i=0; i<count; i++)
		{
			var record = store.getAt(i);
			params['fmIDList['+i.toString()+']']=record.get('freightManifestID');
		}
		return params;
	}

	function resetPage_create_MES()
	{
		_formPanel_createSheet_MES.getForm().reset();
		_ds_shou_create_MES.removeAll();
		_ds_carId_create_MES.removeAll();
		_grid_createSheet_MES.getStore().removeAll();
		_grid_createSheet_MES.getBottomToolbar().findById('allCount_create_MES').setText('0');
		_grid_createSheet_MES.getBottomToolbar().findById('allWeight_create_MES').setText('0');
		_grid_createSheet_MES.getBottomToolbar().findById('allVolume_create_MES').setText('0');
		
	}




