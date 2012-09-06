
var _win_detail_MTS = null;
var _id_detail_MTS = '';
var _mask_detail_MTS = null;
function onDetailTransportationSheet(transId)
{
	if(null == _win_detail_MTS)
	{
	    createWindow_detail_MTS();
	}else
	{
		resetPage_detail_MTS();
		_panel_detail_a_MTS.expand(false);
		_panel_detail_b_MTS.expand(false);
		_panel_detail_c_MTS.expand(false);
		_formPanel_detailSheet_MTS.body.scrollTo('top',0,false);
	}
	_mask_detail_MTS.show();
	_formPanel_detailSheet_MTS.getForm().load({
    	url: 'FreightManifest.queryOne.action',
    	method: 'POST',
    	params: {'fmdto.freightManifestID': transId},
    	success: function(form,action){
    		if(action.result.resultMapList != null){
	    		_ds_detailSheet_MTS.loadData(action.result,false);
    		}
    		setGoodsValue_detail_MTS(action.result.data);
    		_mask_detail_MTS.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_detail_MTS.hide();
    	}
    });
    _id_detail_MTS = transId;
	_win_detail_MTS.setPagePosition(GET_WIN_X(_win_detail_MTS.width),GET_WIN_Y_M(650));
    _win_detail_MTS.show();
}

function setGoodsValue_detail_MTS(data)
{
	_grid_detailSheet_MTS.getTopToolbar().findById('count_detail_MTS').setValue(data['fmdto.priceByAmount']);
	_grid_detailSheet_MTS.getTopToolbar().findById('weight_detail_MTS').setValue(data['fmdto.priceByWeight']);
	_grid_detailSheet_MTS.getTopToolbar().findById('volume_detail_MTS').setValue(data['fmdto.priceByVolume']);
	_grid_detailSheet_MTS.getTopToolbar().findById('percent_detail_MTS').setValue(data['fmdto.insuranceRate']);
	_grid_detailSheet_MTS.getBottomToolbar().findById('mode_detail_MTS').setValue(data['fmdto.chargeMode']);
	_grid_detailSheet_MTS.getBottomToolbar().findById('allCount_detail_MTS').setText(data.sumAmount==null?'0':data.sumAmount.toString());
	_grid_detailSheet_MTS.getBottomToolbar().findById('allWeight_detail_MTS').setText(data.sumWeight==null?'0':data.sumWeight.toString());
	_grid_detailSheet_MTS.getBottomToolbar().findById('allVolume_detail_MTS').setText(data.sumVolume==null?'0':data.sumVolume.toString());
	_grid_detailSheet_MTS.getBottomToolbar().findById('allValue_detail_MTS').setText(data.sumValue==null?'0':data.sumValue.toString());
}
//创建界面元素
function createWindow_detail_MTS()
{
	_win_detail_MTS = new Ext.Window({
        title: '运输单明细查看',
        iconCls: 'commonDetail',
        width: 800,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_detailSheet_MTS,
        listeners: LISTENER_WIN_MOVE
    });
    _win_detail_MTS.show();
    _mask_detail_MTS = new Ext.LoadMask(_win_detail_MTS.body, {msg:"正在载入,请稍后..."});
}
	
	var _ds_detailSheet_MTS = new Ext.data.Store({
		reader : new Ext.data.JsonReader({
					root :'resultMapList'
				}, 
				_record_MTS
			)
	});
	
	var _cm_detailSheet_MTS = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
			{
				dataIndex : 'cargoID',
				header : '货物名称',
				width: 200
			},{
				dataIndex : 'amount',
				header : '数量'
			},{
				dataIndex : 'weight',
				header : '重量(KG)'
			},{
				dataIndex : 'volume',
				header : '体积(M3)'
			},{
				dataIndex : 'value',
				header : '声明价值'
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	// 定义一个表格面板
	var _grid_detailSheet_MTS = new Ext.grid.EditorGridPanel({
				cm : _cm_detailSheet_MTS,
				ds : _ds_detailSheet_MTS,
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
						readOnly: true,
						id: 'count_detail_MTS',
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
						readOnly: true,
						id: 'weight_detail_MTS',
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
						readOnly: true,
						id: 'volume_detail_MTS',
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
						readOnly: true,
						id: 'percent_detail_MTS',
						width: 50
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
						id: 'allCount_detail_MTS',
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
						id: 'allWeight_detail_MTS',
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
						id: 'allVolume_detail_MTS',
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
						id: 'allValue_detail_MTS',
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
					{
						xtype: 'textfield',
						readOnly: true,
						id: 'mode_detail_MTS',
						width: 80
					},
					{
						xtype: 'tbspacer',
						width: 5
					}]
				})
			});
			
var _panel_detail_a_MTS = new Ext.Panel({
	
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
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '客户单号',
					width: TEXTFIELDWIDTH,
					name: 'fmdto.customerNumber',
					readOnly: true
				}]
			},
			{//Column 2
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '客户类型',
					width: TEXTFIELDWIDTH,
					name: 'fmdto.customerType',
					readOnly: true
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
					fieldLabel: '客户名称',
					name : 'fmdto.customer',
					readOnly: true,
					width:　200
				}]
			},
			{//Column 2
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items: [
				{
					xtype : 'textfield',
					fieldLabel: '发货人',
					name : 'fmdto.consigner',
					readOnly: true,
					width:　TEXTFIELDWIDTH
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
					readOnly: true,
					name: 'fmdto.consignerPhone'
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
					readOnly: true,
					name: 'fmdto.consignerAddress'
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
					items: [
					{
						xtype: 'textfield',
						fieldLabel: '收货单位',
						width: 200,
						readOnly: true,
						name: 'fmdto.consigneeCompany'
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
						width: TEXTFIELDWIDTH,
						readOnly: true,
						name: 'fmdto.consignee'
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
						readOnly: true,
						name: 'fmdto.consigneePhone'
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
					readOnly: true,
					name: 'fmdto.consigneeAddress'
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
							readOnly: true,
							name: 'fmdto.originCity'
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
							readOnly: true
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
							name: 'fmdto.dateExpected',
							readOnly: true,
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
							fieldLabel: '目的省',
							width: TEXTFIELDWIDTH,
							name: 'fmdto.destinationProvince',
							readOnly: true
						}]
					}
			]}
	]
})
			

var _panel_detail_b_MTS = new Ext.Panel({
	
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
				items: _grid_detailSheet_MTS
			}]
		},
			{//Row 4
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
					              data : [['是', '有保险'], ['否', '无保险']]
					       }),
					       
					       valueField : 'returnValue',
					       displayField : 'displayText',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'fmdto.isInsurance',
					       readOnly: true,
					       fieldLabel : '保险费',
					       width:　120
					})]
				},
				{//Column 2
					columnWidth: '0.135',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'textfield',
						hideLabel: true,
						readOnly: true,
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
						xtype: 'textfield',
						readOnly: true,
						name: 'fmdto.freightFee',
						fieldLabel: '运费',
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 3
					columnWidth: '0.3',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '结算方式',
						width: TEXTFIELDWIDTH,
						name: 'fmdto.paymentType',
						readOnly: true
					}]
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
					       readOnly: true,
					       forceSelection : true,
					       hiddenName : 'fmdto.isPrepaid',
					       fieldLabel : '代垫货款',
					       width:　120
					})]
				},
				{//Column 2
					columnWidth: '0.135',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'textfield',
						hideLabel: true,
						readOnly: true,
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
						xtype: 'textfield',
						readOnly: true,
						name: 'fmdto.deliveryFee',
						fieldLabel: '送货费',
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 4
					columnWidth: '0.3',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						readOnly: true,
						name: 'fmdto.costCenter',
						fieldLabel: '成本中心',
						width: TEXTFIELDWIDTH
					}]
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
					       readOnly: true,
					       fieldLabel : '代收货款',
					       width:　120
					})]
				},
				{//Column 2
					columnWidth: '0.135',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'textfield',
						hideLabel: true,
						readOnly: true,
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
						xtype: 'textfield',
						readOnly: true,
						name: 'fmdto.consignFee',
						fieldLabel: '提货费',
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 4
					columnWidth: '0.3',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						readOnly: true,
						name: 'fmdto.sellCenter',
						fieldLabel: '销售中心',
						width: TEXTFIELDWIDTH
					}]
				}]
			}
	]
})

var _panel_detail_c_MTS = new Ext.Panel({
	
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
				items:[
				{
					xtype: 'textfield',
					readOnly: true,
					name: 'fmdto.freightType',
					fieldLabel: '送货方式',
					width: TEXTFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.6',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					readOnly: true,
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
					readOnly:true,
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
					readOnly:true,
					name: 'fmdto.remarks',
					fieldLabel: '备注信息',
					width: 639
				}]
			}]
		}
	]
	
});

var _panel_detail_d_MTS = new Ext.Panel({
	
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
					readOnly: true,
					format: 'Y-m-d',
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
						onDetailTransportationTrack(_id_detail_MTS);
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
					readOnly:true,
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
					readOnly:true,
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

function resetPage_detail_MTS()
{
	_formPanel_createSheet_MTS.getForm().reset();
	_grid_detailSheet_MTS.getStore().removeAll();
	_grid_detailSheet_MTS.getBottomToolbar().findById('allCount_detail_MTS').setText('0');
	_grid_detailSheet_MTS.getBottomToolbar().findById('allWeight_detail_MTS').setText('0');
	_grid_detailSheet_MTS.getBottomToolbar().findById('allVolume_detail_MTS').setText('0');
	_grid_detailSheet_MTS.getBottomToolbar().findById('allValue_detail_MTS').setText('0');
}

var _formPanel_detailSheet_MTS = new Ext.FormPanel({
	
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
		text: '关闭',
		iconCls: 'commonCancel',
		handler: function(){ _win_detail_MTS.hide(); }
	}],
	
	items:[
		_panel_detail_a_MTS,
		_panel_detail_b_MTS,
		_panel_detail_c_MTS,
		_panel_detail_d_MTS
	]
});
