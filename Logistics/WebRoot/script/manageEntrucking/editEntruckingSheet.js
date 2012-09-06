
var _win_edit_MES = null;
var _mask_edit_MES = null;
function onEditEntruckingSheet(entruckId)
{
	if(null == _win_edit_MES)
	{
	    createWindow_edit_MES();
	    STORE_TRANS_UNIT_LOAD();
		STORE_TAKE_UNIT_LOAD();
	}else
	{
		resetPage_edit_MES();
	}
	
	_mask_edit_MES.show();
	_formPanel_editSheet_MES.getForm().load({
    	url: 'ShipmentManifest.queryOne.action',
    	method: 'POST',
    	params: {'smdto.shipmentManifestID': entruckId},
    	success: function(form,action){
    		if(action.result.resultMapList != null){
	    		_ds_editSheet_MES.loadData(action.result,false);
    		}
    		setTransSheetValue_edit_MES(action.result.data);
    		_mask_edit_MES.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'装车单数据读取失败！');
    		_mask_edit_MES.hide();
    	}
    });
	_win_edit_MES.setPagePosition(GET_WIN_X(_win_edit_MES.width),GET_WIN_Y_M(650));
    _win_edit_MES.show();
}

function setTransSheetValue_edit_MES(data)
{
	_grid_editSheet_MES.getBottomToolbar().findById('allCount_edit_MES').setText(data.sumAmount.toString());
	_grid_editSheet_MES.getBottomToolbar().findById('allWeight_edit_MES').setText(data.sumWeight.toString());
	_grid_editSheet_MES.getBottomToolbar().findById('allVolume_edit_MES').setText(data.sumVolume.toString());
	_grid_editSheet_MES.getBottomToolbar().findById('mode_edit_MES').setValue(data['smdto.chargeMode']);
}

function createWindow_edit_MES()
{
	_win_edit_MES = new Ext.Window({
        title: '修改装车单',
        iconCls: 'commonEditSheet',
        width: 815,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_editSheet_MES,
        listeners: LISTENER_WIN_MOVE
    });
    _win_edit_MES.show();
    _mask_edit_MES = new Ext.LoadMask(_win_edit_MES.body, {msg:"正在载入,请稍后..."});
}
	
	var _ds_editSheet_MES = new Ext.data.Store({
		reader : new Ext.data.JsonReader({
					root : 'resultMapList'
				}, 
				_record_MES
			)
	});

	var _sm_editSheet_MES = new Ext.grid.CheckboxSelectionModel();
	
	var _cm_editSheet_MES = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_editSheet_MES,
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
	
	var _grid_editSheet_MES = new Ext.grid.GridPanel({
				sm : _sm_editSheet_MES,
				cm : _cm_editSheet_MES,
				ds : _ds_editSheet_MES,
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
						text : "移除",
						iconCls: 'commonDelete',
						handler: function(){
							GRID_LIST_DELETE(_grid_editSheet_MES);
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
						id: 'transId_edit_MES',
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
							var findTrans = _grid_editSheet_MES.getTopToolbar().findById('transId_edit_MES');
							var id = findTrans.getValue();
							if(_grid_editSheet_MES.getStore().getCount()>= MAX_TRANS_SHEET_COUNT)
							{
								Ext.Msg.show({
									title : '操作提示',
									msg : '运输单不能超过['+MAX_TRANS_SHEET_COUNT.toString()+']条！',
									buttons : Ext.Msg.OK,
									icon : Ext.Msg.WARNING
								});
								return;
							}
							var field = ['freightManifestID'];
							var value = [id];
							if(ISINLIST(_grid_editSheet_MES,field,value))
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
									url: 'ShipmentManifest.queryOneFreightManifest.action?'+'fmdto.freightManifestID='+id,
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
												    origin:result.data.customer,
												    destination:result.data.destination,
												    customer:result.data.customer,
												    amount: result.data.amount,
												    weight: result.data.weight,
												    volume: result.data.volume
												});
												_grid_editSheet_MES.getStore().add(record);
												findTrans.reset();
											}
										}
									},
									failure: function(response,options){
										AJAX_SERVER_FAILURE_CALLBACK(response,options,'运输单查询请求错误!');
									}
							});//Ajax
						}
					},'-', 
					{
						text : "批量查询添加",
						iconCls: 'commonQuery',
						handler: function(){onQueryTransportationSheet(2);}
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
						id: 'allCount_edit_MES',
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
						id: 'allWeight_edit_MES',
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
						id: 'allVolume_edit_MES',
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
					       id: 'mode_edit_MES',
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
						text : "&nbsp;&nbsp;计算合计",
						iconCls: 'calculator',
						handler: function(){
							getEntruckingFee_edit_MES();
						}
					},
					{
						xtype: 'tbspacer',
						width: 5
					}]
				})
			});
			
	var _formPanel_editSheet_MES = new Ext.FormPanel({
		
		layout: 'form',
		style: 'margin:0px',
		frame: true,
		labelAlign: 'right',
		bodyStyle: PADDING,
		height: 487,
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
				if(_formPanel_editSheet_MES.getForm().isValid())
				{
					var params = getGridParams_edit_MES();
					_formPanel_editSheet_MES.getForm().submit({
						url: 'ShipmentManifest.update.action',
						waitTitle:"保存数据",
						waitMsg: '正在保存...',
						success:function(form,action){
							resetPage_edit_MES();
							_grid_MES.getStore().reload();
							_win_edit_MES.hide();
						},
						failure: function(form,action){
							FORM_FAILURE_CALLBACK(form,action,"数据保存失败");
						},
						params: params
					});
				}
			}
		},{
			text: '取消',
			iconCls: 'commonCancel',
			handler: function(){ _win_edit_MES.hide(); }
		}],
		
		items:[
		{//Row 0
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
							fieldLabel: '装车单号',
							width: TEXTFIELDWIDTH,
							name: 'smdto.shipmentManifestID',
							readOnly: true
						}]
					},
					{//Column 2
						columnWidth: '0.6',
						layout: 'form',
						border: false,
						items: [
						{
							xtype: 'textfield',
							fieldLabel: '建单日期',
							width: TEXTFIELDWIDTH,
							name: 'smdto.dateCreated',
							readOnly: true
						}]
					}
			]},
			{//Row 1
				layout: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '0.4',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '承运单位',
						id:'transUnitId_edit_MES',
						width: 200,
						allowBlank: false,
						name: 'smdto.contractor',
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
						fieldLabel: '车牌号',
						id:'carId_edit_MES',
						width: TEXTFIELDWIDTH,
						allowBlank: false,
						name: 'smdto.carID',
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
						fieldLabel: '驾驶员姓名',
						width: TEXTFIELDWIDTH,
						allowBlank: false,
						id:'driverName_edit_MES',
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
						id:'carType_edit_MES',
						width: TEXTFIELDWIDTH,
						name: 'smdto.carType',
						allowBlank: true
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
						id:'driverPhone_edit_MES',
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
				items: [{//Column 1
						columnWidth: '0.4',
						layout: 'form',
						border: false,
					    items:[{
							xtype: 'textfield',
							fieldLabel: '收货单位',
							width: 200,
							allowBlank: false,
							name: 'smdto.consigneeCompany',
							regex: REGEX_COMMON_S,
							regexText: REGEX_COMMON_S_TEXT
						}]
					},
					{//Column 2
						columnWidth: '0.3',
						layout: 'form',
						border: false,
					    items:[{
							xtype: 'textfield',
							fieldLabel: '收货人',
							width: TEXTFIELDWIDTH,
							allowBlank: false,
							name: 'smdto.consignee',
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
							id:'shou_phone_edit_MES',
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
						id:'shou_address_edit_MES',
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
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						name: 'smdto.originAgent',
						width: 200
					}]
				},
				{//Column 2
					columnWidth: '0.3',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'textfield',
						fieldLabel: '始发市县',
						width: TEXTFIELDWIDTH,
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						name: 'smdto.originCity'
					}]
				},
				{//Column 3
						columnWidth: '0.3',
						layout: 'form',
						border: false,
						items: [
						{
							xtype: 'textfield',
							fieldLabel: '始发省',
							width: TEXTFIELDWIDTH,
							name: 'smdto.originProvince',
							regex: REGEX_COMMON_S,
							regexText: REGEX_COMMON_S_TEXT
						}]
				}]
			},
			{//Row 6
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
							fieldLabel: '到货站',
							regex: REGEX_COMMON_S,
							regexText: REGEX_COMMON_S_TEXT,
							name: 'smdto.destinationAgent',
							width: 200
						}]
					},
					{//Column 2
						columnWidth: '0.3',
						layout: 'form',
						border: false,
						items: [
						{
							xtype: 'textfield',
							fieldLabel: '目的市县',
							width: TEXTFIELDWIDTH,
							name: 'smdto.destinationCity',
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
							fieldLabel: '目的省',
							width: TEXTFIELDWIDTH,
							name: 'smdto.destinationProvince',
							regex: REGEX_COMMON_S,
							regexText: REGEX_COMMON_S_TEXT
						}]
					}]
			},
			{//Row 6
				layout: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: _grid_editSheet_MES
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
						id: 'entruckingFee_edit_MES',
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

	function getGridParams_edit_MES()
	{
		params = {};
		var store = _grid_editSheet_MES.getStore();
		var count = store.getCount();
		for(var i=0; i<count; i++)
		{
			var record = store.getAt(i);
			params['fmIDList['+i.toString()+']']=record.get('freightManifestID');
		}
		return params;
	}

	function resetPage_edit_MES()
	{
		_formPanel_editSheet_MES.getForm().reset();
		_grid_editSheet_MES.getStore().removeAll();
		_grid_editSheet_MES.getBottomToolbar().findById('allCount_edit_MES').setText('0');
		_grid_editSheet_MES.getBottomToolbar().findById('allWeight_edit_MES').setText('0');
		_grid_editSheet_MES.getBottomToolbar().findById('allVolume_edit_MES').setText('0');
		
	}

function getEntruckingFee_edit_MES()
{
	var store = _grid_editSheet_MES.getStore();
	store.commitChanges();
	
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
	_grid_editSheet_MES.getBottomToolbar().findById('allCount_edit_MES').setText(allCount.toString());
	_grid_editSheet_MES.getBottomToolbar().findById('allWeight_edit_MES').setText(allWeight.toString());
	_grid_editSheet_MES.getBottomToolbar().findById('allVolume_edit_MES').setText(allVolume.toString());

}


