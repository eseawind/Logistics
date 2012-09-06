
var _win_edit_MTS = null;
var _id_edit_MTS = '';
var _mask_edit_MTS = null;
function onEditTransportationSheet(transId)
{
	if(null == _win_edit_MTS)
	{
	    createWindow_edit_MTS();
	}else
	{
		resetPage_edit_MTS();
		_panel_edit_a_MTS.expand(false);
		_panel_edit_b_MTS.expand(false);
		_panel_edit_c_MTS.expand(false);
		_formPanel_editSheet_MTS.body.scrollTo('top',0,false);
	}
	STORE_CUSTOMER_LOAD(lastCustomerType_edit);
	STORE_CACHE_GOODS_LOAD();
    _mask_edit_MTS.show();
	_formPanel_editSheet_MTS.getForm().load({
    	url: 'FreightManifest.queryOne.action',
    	method: 'POST',
    	params: {'fmdto.freightManifestID': transId},
    	success: function(form,action){
    		if(action.result.resultMapList != null){
	    		_ds_editSheet_MTS.loadData(action.result,false);
    		}
    		setGoodsValue_edit_MTS(action.result.data);
    		_mask_edit_MTS.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_edit_MTS.hide();
    	}
    });
    _id_edit_MTS = transId;
	_win_edit_MTS.setPagePosition(GET_WIN_X(_win_edit_MTS.width),GET_WIN_Y_M(650));
    _win_edit_MTS.show();
}

function setGoodsValue_edit_MTS(data)
{
	_grid_editSheet_MTS.getTopToolbar().findById('count_edit_MTS').setValue(data['fmdto.priceByAmount']);
	_grid_editSheet_MTS.getTopToolbar().findById('weight_edit_MTS').setValue(data['fmdto.priceByVolume']);
	_grid_editSheet_MTS.getTopToolbar().findById('volume_edit_MTS').setValue(data['fmdto.priceByWeight']);
	_grid_editSheet_MTS.getTopToolbar().findById('percent_edit_MTS').setValue(data['fmdto.insuranceRate']);
	_grid_editSheet_MTS.getBottomToolbar().findById('mode_edit_MTS').setValue(data['fmdto.chargeMode']);
	_grid_editSheet_MTS.getBottomToolbar().findById('allCount_edit_MTS').setText(data.sumAmount==null?'0':data.sumAmount.toString());
	_grid_editSheet_MTS.getBottomToolbar().findById('allWeight_edit_MTS').setText(data.sumWeight==null?'0':data.sumWeight.toString());
	_grid_editSheet_MTS.getBottomToolbar().findById('allVolume_edit_MTS').setText(data.sumVolume==null?'0':data.sumVolume.toString());
	_grid_editSheet_MTS.getBottomToolbar().findById('allValue_edit_MTS').setText(data.sumValue==null?'0':data.sumValue.toString());
	
	if('否'==data['fmdto.isCollection'])_panel_edit_b_MTS.findById('collectionFee_edit_MTS').disable();
	if('否'==data['fmdto.isInsurance'])_panel_edit_b_MTS.findById('secureFee_edit_MTS').disable();
	if('否'==data['fmdto.isPrepaid'])_panel_edit_b_MTS.findById('prepaidFee_edit_MTS').disable();
}

//创建界面元素
function createWindow_edit_MTS()
{
	_win_edit_MTS = new Ext.Window({
        title: '修改运输单',
        iconCls: 'commonEditSheet',
        width: 800,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_editSheet_MTS,
        listeners: LISTENER_WIN_MOVE
    });
    _win_edit_MTS.show();
    _mask_edit_MTS =  _mask_edit_MN = new Ext.LoadMask(_win_edit_MTS.body, {msg:"正在载入,请稍后..."});
}

	var _record_edit_MTS = Ext.data.Record.create([
	    {name: 'cargoID'},
	    {name: 'amount'},
	    {name: 'weight'},
	    {name: 'volume'},
	    {name: 'value'}
	]);
	
	var _ds_editSheet_MTS = new Ext.data.Store({
		reader : new Ext.data.JsonReader({
					root : 'resultMapList'
				}, 
				_record_edit_MTS
			)
	});

	var _sm_editSheet_MTS = new Ext.grid.CheckboxSelectionModel();
	
	var _cm_editSheet_MTS = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_editSheet_MTS,
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
					decimalPrecision:V_DECIMAL,
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
	var _grid_editSheet_MTS = new Ext.grid.EditorGridPanel({
				sm : _sm_editSheet_MTS,
				cm : _cm_editSheet_MTS,
				ds : _ds_editSheet_MTS,
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
							GRID_LIST_DELETE(_grid_editSheet_MTS);
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
						id: 'count_edit_MTS',
						value: 0,
						name:'fmdto.priceByAmount',
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
						name:'fmdto.priceByWeight',
						id: 'weight_edit_MTS',
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
						name:'fmdto.priceByVolume',
						id: 'volume_edit_MTS',
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
						name:'fmdto.insuranceRate',
						id: 'percent_edit_MTS',
						maxValue: 100,
						allowBlank: false,
						selectOnFocus: true,
						width: 50
					},'-',
					{
						text : "添加",
						iconCls: 'commonAdd',
						handler: function(){
							if(_grid_editSheet_MTS.getStore().getCount()>= MAX_TRANS_GOODS_COUNT)
							{
								Ext.Msg.show({
									title : '操作提示',
									msg : '货品信息不能超过['+MAX_TRANS_GOODS_COUNT.toString()+']条！',
									buttons : Ext.Msg.OK,
									icon : Ext.Msg.WARNING
								});
								return;
							}
							var record = new _record_edit_MTS({
							    cargoID: '编辑货品名称',
							    amount:0,
							    weight:0,
							    volume:0,
							    value: 0
							});
							_grid_editSheet_MTS.getStore().add(record);
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
						id: 'allCount_edit_MTS',
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
						name:'sumWeight',
						id: 'allWeight_edit_MTS',
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
						name:'sumVolume',
						id: 'allVolume_edit_MTS',
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
						name:'sumValue',
						id: 'allValue_edit_MTS',
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
					       store : STORE_TRANS_CHARGE_MODE,
					       valueField : 'value',
					       displayField : 'value',
					       mode : 'local',
					       id: 'mode_edit_MTS',
					       forceSelection : true,
					       editable : false,
					       hiddenName:'fmdto.chargeMode',
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '计费方式',
					       value: '体积(M3)',
					       width:　80
					}),
					{
						xtype: 'tbspacer',
						width: 1
					},
					{
						text : "计算运费",
						iconCls: 'calculator',
						handler: function(){
							
							var storeA = _grid_editSheet_MTS.getStore();
							storeA.commitChanges();
							
							var eCount = _grid_editSheet_MTS.getTopToolbar().findById('count_edit_MTS');
							var eWeight = _grid_editSheet_MTS.getTopToolbar().findById('weight_edit_MTS');
							var eVolume = _grid_editSheet_MTS.getTopToolbar().findById('volume_edit_MTS');
							var ePercent = _grid_editSheet_MTS.getTopToolbar().findById('percent_edit_MTS');
							
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
							
							var mode = _grid_editSheet_MTS.getBottomToolbar().findById('mode_edit_MTS').getValue();
							
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
							
							_grid_editSheet_MTS.getBottomToolbar().findById('allCount_edit_MTS').setText(allCount.toString());
							_grid_editSheet_MTS.getBottomToolbar().findById('allWeight_edit_MTS').setText(allWeight.toString());
							_grid_editSheet_MTS.getBottomToolbar().findById('allVolume_edit_MTS').setText(allVolume.toString());
							_grid_editSheet_MTS.getBottomToolbar().findById('allValue_edit_MTS').setText(allValue.toString());
							_panel_edit_b_MTS.findById('freightFee_edit_MTS').setValue(money);
							if(_panel_edit_b_MTS.findById('hasSecureFee_edit_MTS').getValue()=='是'){
								_panel_edit_b_MTS.findById('secureFee_edit_MTS').setValue(secureMoney);
							}
							
						}
					},
					{
						xtype: 'tbspacer',
						width: 5
					}]
				})
			});
			
var lastCustomerType_edit = '合同客户';
			
var _panel_edit_a_MTS = new Ext.Panel({
	
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
				       width:　TEXTFIELDWIDTH
				})
			},
			{//Column 2
				columnWidth: '0.3',
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
			},
			{//Column 3
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '运输单号',
					readOnly: true,
					name: 'fmdto.freightManifestID',
					width: TEXTFIELDWIDTH
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
					id: 'customerID_edit_MTS',
					name : 'cusdto.customerID',
					value: '',
					hidden: true
				},
				{
					xtype: 'textfield',
					fieldLabel: '客户名称',
					width: 200,
					allowBlank:false,
					id: 'customerName_edit_MTS',
					name: 'fmdto.customer',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT
				}]
				
			},
			{//Column 2
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items: [{
						xtype: 'textfield',
						fieldLabel: '发货人',
						width: TEXTFIELDWIDTH,
						id: 'ti_edit_MTS',
						allowBlank:false,
						name: 'fmdto.consigner',
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
					fieldLabel: '发货人电话',
					width: TEXTFIELDWIDTH,
					id: 'ti_phone_edit_MTS',
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
					id: 'ti_address_edit_MTS',
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
					items: [{
						xtype: 'textfield',
						fieldLabel: '收货单位',
						width: 200,
						id: 'take_edit_MTS',
						allowBlank:false,
						name: 'fmdto.consigneeCompany',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT
					}]
				},
				{//Column 2
					columnWidth: '0.3',
					layout: 'form',
					border: false,
				    items: [{
						xtype: 'textfield',
						fieldLabel: '收货人',
						width: TEXTFIELDWIDTH,
						id: 'shou_edit_MTS',
						allowBlank:false,
						name: 'fmdto.consignee',
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
						id: 'shou_phone_edit_MTS',
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
					id: 'shou_address_edit_MTS',
					name: 'fmdto.consigneeAddress',
					regex: REGEX_COMMON_M,
					regexText: REGEX_COMMON_M_TEXT
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
							fieldLabel: '始发市县',
							width: 200,
							name: 'fmdto.originCity',
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
							fieldLabel: '始发省',
							width: TEXTFIELDWIDTH,
							name: 'fmdto.originProvince',
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
							xtype: 'datefield',
							fieldLabel: '预计到货',
							format: 'Y-m-d',
							name: 'fmdto.dateExpected',
							allowBlank: false,
							editable: false,
							width: TEXTFIELDWIDTH
						}]
					}
			]
		},
		{//Row 7
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
							fieldLabel: '目的市县',
							width: 200,
							name: 'fmdto.destinationCity',
							regex: REGEX_COMMON_S,
							regexText: REGEX_COMMON_S_TEXT
						}]
					},
					{//Column 2
						columnWidth: '0.6',
						layout: 'form',
						border: false,
						items: [
						{
							xtype: 'textfield',
							fieldLabel: '目的省',
							width: TEXTFIELDWIDTH,
							name: 'fmdto.destinationProvince',
							regex: REGEX_COMMON_S,
							regexText: REGEX_COMMON_S_TEXT
						}]
					}
			]}
	]
})
			

var _panel_edit_b_MTS = new Ext.Panel({
	
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
				items: _grid_editSheet_MTS
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
					       id:'hasSecureFee_edit_MTS',
					       allowBlank : false,
					       value: '否',
					       fieldLabel : '保险费',
					       width:　120,
					       listeners: {
				       		select: function(combo, record, index){
				       			var secureFee = _panel_edit_b_MTS.findById('secureFee_edit_MTS');
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
						id : 'secureFee_edit_MTS',
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
						id : 'freightFee_edit_MTS',
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
					              data : [['月结'],['现结'],['运费到付']]
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
				       			var prepaidFee = _panel_edit_b_MTS.findById('prepaidFee_edit_MTS');
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
						id: 'prepaidFee_edit_MTS',
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
				       			var collectionFee = _panel_edit_b_MTS.findById('collectionFee_edit_MTS');
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
						maxValue: MAX_DOUBLE,
						allowBlank: false,
						id: 'collectionFee_edit_MTS',
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
			}
	]
})

var _panel_edit_c_MTS = new Ext.Panel({
	
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

var _panel_edit_d_MTS = new Ext.Panel({
	
	layout: 'form',
	style: 'margin:0px 0px 5px 0px',
	collapsible : true,
	title: '&nbsp;&nbsp;<&nbsp;运输状态信息&nbsp;>',
	bodyStyle: PADDING,
	items: [
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.265',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'datefield',
					fieldLabel: '建单日期',
					format: 'Y-m-d',
					readOnly: true,
					name: 'fmdto.dateCreated',
					width: TEXTFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.135',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'button',
					hideLabel: true,
					text : "物流明细",
					iconCls: 'trackDetail',
					handler:function()
					{
						onDetailTransportationTrack(_id_edit_MTS);
					},
					width: 85
				}]
			},
			{//Column 3
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'datefield',
					fieldLabel: '发货日期',
					format: 'Y-m-d',
					name: 'fmdto.dateDelivered',
					allowBlank: true,
					editable: false,
					width: TEXTFIELDWIDTH
				}]
			},
			{//Column 4
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'datefield',
					fieldLabel: '到货签收',
					format: 'Y-m-d',
					name: 'fmdto.dateSigned',
					allowBlank: true,
					editable: false,
					width: TEXTFIELDWIDTH
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
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '业务状态',
					name:'fmdto.freightState',
					readOnly: true,
					width: TEXTFIELDWIDTH
				}]
			}]
		}
	]
	
});

function getListParam_edit_MTS()
{
	var params = {};
	var storeA = _grid_editSheet_MTS.getStore();
	storeA.commitChanges();
	for(var i=0; i<storeA.getCount(); i++)
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

function resetPage_edit_MTS()
{
	_formPanel_editSheet_MTS.getForm().reset();
	_grid_editSheet_MTS.getStore().removeAll();
	_grid_editSheet_MTS.getBottomToolbar().findById('allCount_edit_MTS').setText('0');
	_grid_editSheet_MTS.getBottomToolbar().findById('allWeight_edit_MTS').setText('0');
	_grid_editSheet_MTS.getBottomToolbar().findById('allVolume_edit_MTS').setText('0');
	_grid_editSheet_MTS.getBottomToolbar().findById('allValue_edit_MTS').setText('0');
	_panel_b_MTS.findById('secureFee_MTS').disable();
	_panel_edit_b_MTS.findById('collectionFee_edit_MTS').disable();
	_panel_edit_b_MTS.findById('prepaidFee_edit_MTS').disable();
	_panel_edit_b_MTS.findById('secureFee_edit_MTS').enable();
	lastCustomerType_edit="合同客户";
}

var _formPanel_editSheet_MTS = new Ext.FormPanel({
	
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
			if(_formPanel_editSheet_MTS.getForm().isValid())
			{
				_grid_editSheet_MTS.getStore().commitChanges();
				if(CHECKLISTSAME(_grid_editSheet_MTS,'cargoID','编辑货品名称'))
				{
					Ext.Msg.show({
						title : '操作提示',
						msg : '货品列表有[同名]或[未编辑]货物！',
						buttons : Ext.Msg.OK,
						icon : Ext.Msg.WARNING
					});
					return;
				}
				var params = getListParam_edit_MTS();
				_formPanel_editSheet_MTS.getForm().submit({
					url: 'FreightManifest.update.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						resetPage_edit_MTS();
						_formPanel_editSheet_MTS.body.scrollTo('top',0,true);
						_win_edit_MTS.hide();
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
		text: '取消',
		iconCls: 'commonCancel',
		handler: function(){ _win_edit_MTS.hide(); }
	}],
	
	items:[
		_panel_edit_a_MTS,
		_panel_edit_b_MTS,
		_panel_edit_c_MTS,
		_panel_edit_d_MTS
	]
});
