
var _win_detail_MES = null;
var _mask_detail_MES = null;
function onDetailEntruckingSheet(entruckId)
{
	if(null == _win_detail_MES)
	{
	    createWindow_detail_MES();
	}else
	{
		resetPage_detail_MES();
	}
	_mask_detail_MES.show();
	_formPanel_detailSheet_MES.getForm().load({
    	url: 'ShipmentManifest.queryOne.action',
    	method: 'POST',
    	params: {'smdto.shipmentManifestID': entruckId},
    	success: function(form,action){
    		if(action.result.resultMapList != null){
	    		_ds_detailSheet_MES.loadData(action.result,false);
    		}
    		setTransSheetValue_detail_MES(action.result.data);
    		_mask_detail_MES.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_detail_MES.hide();
    	}
    });
	_win_detail_MES.setPagePosition(GET_WIN_X(_win_detail_MES.width),GET_WIN_Y_M(650));
    _win_detail_MES.show();
}

function setTransSheetValue_detail_MES(data)
{
	_grid_detailSheet_MES.getBottomToolbar().findById('allCount_detail_MES').setText(data.sumAmount.toString());
	_grid_detailSheet_MES.getBottomToolbar().findById('allWeight_detail_MES').setText(data.sumWeight.toString());
	_grid_detailSheet_MES.getBottomToolbar().findById('allVolume_detail_MES').setText(data.sumVolume.toString());
	_grid_detailSheet_MES.getBottomToolbar().findById('mode_detail_MES').setValue(data['smdto.chargeMode']);
}

function createWindow_detail_MES()
{
	_win_detail_MES = new Ext.Window({
        title: '装车单明细',
        iconCls: 'commonDetail',
        width: 815,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_detailSheet_MES,
        listeners: LISTENER_WIN_MOVE
    });
    _win_detail_MES.show();
    _mask_detail_MES = new Ext.LoadMask(_win_detail_MES.body, {msg:"正在载入,请稍后..."});
}
	
	var _ds_detailSheet_MES = new Ext.data.Store({
		reader : new Ext.data.JsonReader({
					root : 'resultMapList'
				}, 
				_record_MES
			)
	});
	
	var _cm_detailSheet_MES = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
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
	
	var _grid_detailSheet_MES = new Ext.grid.GridPanel({
				cm : _cm_detailSheet_MES,
				ds : _ds_detailSheet_MES,
				style: 'margin:0',
				stripeRows : true,
				loadMask : true,
				border : true,
				frame: false,
				height : 160,
				autoScroll: true,
				width: 641,
				draggable : false,
				fieldLabel: '运输单列表',
				// UI视图配置
				viewConfig : {
					forceFit : true
				},
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
						id: 'allCount_detail_MES',
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
						id: 'allWeight_detail_MES',
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
						id: 'allVolume_detail_MES',
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
					{
						xtype: 'textfield',
						readOnly: true,
						id: 'mode_detail_MES',
						width: 100
					},
					{
						xtype: 'tbspacer',
						width: 5
					}]
				})
			});

	
	var _formPanel_detailSheet_MES = new Ext.FormPanel({
		
		layout: 'form',
		style: 'margin:0px',
		frame: true,
		labelAlign: 'right',
		bodyStyle: PADDING,
		height: 467,
		autoScroll: true,
		labelWidth: 70,
		border: false,
		buttonAlign: 'center',
		
		buttons:[
		{
			text: '关闭',
			iconCls: 'commonCancel',
			handler: function(){ _win_detail_MES.hide(); }
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
						width: 200,
						name: 'smdto.contractor',
						readOnly: true
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
						width: TEXTFIELDWIDTH,
						name: 'smdto.carID',
						readOnly: true
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
						name: 'smdto.driverName',
						readOnly: true
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
						width: TEXTFIELDWIDTH,
						name: 'smdto.carType',
						readOnly: true
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
						width: TEXTFIELDWIDTH,
						name: 'smdto.driverPhone',
						readOnly: true
					}]
				}]
			},
			{//Row 3
				layout: 'column',
				border: false,
				items:[
					{//Column 1
						columnWidth: '0.4',
						layout: 'form',
						border: false,
						items:  [
						{
							xtype: 'textfield',
							fieldLabel: '收货单位',
							width: 200,
							name: 'smdto.consigneeCompany',
							readOnly: true
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
							name: 'smdto.consignee',
							readOnly: true
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
							name: 'smdto.consigneePhone',
							readOnly:true
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
						readOnly:true,
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
						fieldLabel: '联系电话',
						width: TEXTFIELDWIDTH,
						name: 'smdto.originPhone',
						readOnly:true
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
						readOnly:true,
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
						readOnly:true,
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
						readOnly: true,
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
							readOnly: true
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
							readOnly:true,
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
							readOnly: true
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
							readOnly: true
						}]
					}
				]
		},
			{//Row 6
				layout: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: _grid_detailSheet_MES
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
						xtype: 'textfield',
						readOnly: true,
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
						xtype: 'textfield',
						readOnly: true,
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
						xtype: 'textfield',
						readOnly: true,
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
						readOnly: true,
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
						readOnly: true,
						name: 'smdto.remarks',
						width: 641
					}]
				}]
			}
		]
	});

	function resetPage_detail_MES()
	{
		_formPanel_detailSheet_MES.getForm().reset();
		_grid_detailSheet_MES.getStore().removeAll();
		_grid_detailSheet_MES.getBottomToolbar().findById('allCount_detail_MES').setText('0');
		_grid_detailSheet_MES.getBottomToolbar().findById('allWeight_detail_MES').setText('0');
		_grid_detailSheet_MES.getBottomToolbar().findById('allVolume_detail_MES').setText('0');
		_grid_detailSheet_MES.getBottomToolbar().findById('mode_detail_MES').setValue('');
	}
