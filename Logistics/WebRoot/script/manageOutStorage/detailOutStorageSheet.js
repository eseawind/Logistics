var _win_detail_MOSS = null;
var _mask_detail_MOSS = null;
function onDetailOutStorageSheet(id)
{
	if(null == _win_detail_MOSS)
	{
	    createWindow_detail_MOSS();
	}else
	{
		resetPage_detail_MOSS();
	}
	_mask_detail_MOSS.show();
	_formPanel_detailSheet_MOSS.getForm().load({
    	url: 'StockoutManifest.queryOne.action',
    	method: 'POST',
    	params: {'somdto.stockoutManifestID': id},
    	success: function(form,action){
    		if(action.result.resultMapList != null){
	    		_ds_detailSheet_MOSS.loadData(action.result,false);
    		}
    		setSheetValue_detail_MOSS(action.result.data);
    		_mask_detail_MOSS.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'入库单数据读取失败！');
    		_mask_detail_MOSS.hide();
    	}
    });
	_win_detail_MOSS.setPagePosition(GET_WIN_X(_win_detail_MOSS.width),GET_WIN_Y_M(600));
    _win_detail_MOSS.show();
}

function setSheetValue_detail_MOSS(data)
{
	_grid_detailSheet_MOSS.getBottomToolbar().findById('mode_detail_MOSS').setValue(data['somdto.chargeMode']);
	var title = _grid_detailSheet_MOSS.getBottomToolbar().findById('title_price_detail_MOSS');
	if(data['somdto.chargeMode']=='数量')
	{
		title.setText('数量报价：');
	}
	else if(data['simdto.chargeMode']=='重量(KG)')
	{
		title.setText('重量报价：');
	}
	else
	{
		title.setText('体积报价：');
	}
	_grid_detailSheet_MOSS.getBottomToolbar().findById('price_detail_MOSS').setValue(data['somdto.unitPrice']);
	_grid_detailSheet_MOSS.getBottomToolbar().findById('allCount_detail_MOSS').setText(data['somdto.sumAmount'].toString());
	_grid_detailSheet_MOSS.getBottomToolbar().findById('allWeight_detail_MOSS').setText(data['somdto.sumWeight'].toString());
	_grid_detailSheet_MOSS.getBottomToolbar().findById('allVolume_detail_MOSS').setText(data['somdto.sumVolume'].toString());
}

function createWindow_detail_MOSS()
{
	_win_detail_MOSS = new Ext.Window({
        title: '出库单明细',
        iconCls: 'commonDetail',
        width: 800,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_detailSheet_MOSS,
        listeners: LISTENER_WIN_MOVE
    });
    _win_detail_MOSS.show();
    _mask_detail_MOSS = new Ext.LoadMask(_win_detail_MOSS.body, {msg:"正在载入,请稍后..."});
}

var _ds_detailSheet_MOSS = new Ext.data.Store({
	reader : new Ext.data.JsonReader({
				root : 'resultMapList'
			}, 
			_record_MOSS
		)
});

	var _cm_detailSheet_MOSS = new Ext.grid.ColumnModel({
			columns:[
				new Ext.grid.RowNumberer(),
			{
				width:70,
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
				width:60,
				dataIndex : 'batch',
				header : '批次'
			},{
				width:80,
				dataIndex : 'itemNumber',
				header : '物料代码'
			},{
				width:60,
				dataIndex : 'stockAmount',
				header : '库存数量'
			},{
				width:90,
				dataIndex : 'amount',
				header : '出库数量'
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
	
	var _grid_detailSheet_MOSS = new Ext.grid.GridPanel({
				cm : _cm_detailSheet_MOSS,
				ds : _ds_detailSheet_MOSS,
				style: 'margin:0px 0px 2px 0px',
				stripeRows : true,
				loadMask : true,
				border : true,
				frame: false,
				height : 200,
				autoScroll: true,
				width: 641,
				draggable : false,
				fieldLabel: '物料列表',
				viewConfig : {
					forceFit : false
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
						text: '数量合计：'
					},{
						xtype: 'tbspacer'
					},
					{
						xtype: 'tbtext',
						text: '0',
						id: 'allCount_detail_MOSS',
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
						id: 'allWeight_detail_MOSS',
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
						id: 'allVolume_detail_MOSS',
						width: 50
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'tbfill'
					},
					{
						xtype: 'label',
						id:'title_price_detail_MOSS',
						text: '数量报价：'
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'numberfield',
						name: 'somdto.unitPrice',
						id: 'price_detail_MOSS',
						readOnly:true,
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
						xtype: 'label',
						text: '计费方式：'
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'textfield',
						name: 'somdto.chargeMode',
						id: 'mode_detail_MOSS',
						readOnly:true,
						width: 80
					},
					{
						xtype: 'tbspacer',
						width: 5
					}]
				})
			});


var _formPanel_detailSheet_MOSS = new Ext.FormPanel({
	
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
		text: '关闭',
		iconCls: 'commonCancel',
		handler: function(){_win_detail_MOSS.hide();}
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
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '出库单号',
					name: 'somdto.stockoutManifestID',
					readOnly: true,
					width: TEXTFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.6',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'datefield',
					fieldLabel: '建单日期',
					name: 'somdto.dateCreated',
					readOnly: true,
					width: TEXTFIELDWIDTH
				}]
			}]
		},
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
					fieldLabel: '库存地',
					name: 'somdto.warehouseName',
					readOnly: true,
					width: 200
				}]
			},
			{//Column 2
				columnWidth: '0.6',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '出库日期',
					name: 'somdto.dateStockout',
					readOnly: true,
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
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '客户名称',
					name: 'somdto.customer',
					readOnly: true,
					width: 200
				}]
			},
			{//Column 1
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '销售中心',
					name: 'somdto.sellCenter',
					readOnly: true,
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
					fieldLabel: '成本中心',
					name: 'somdto.costCenter',
					readOnly: true,
					width: TEXTFIELDWIDTH
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
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '收货单位',
					name: 'somdto.consigneeCompany',
					readOnly: true,
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
					fieldLabel: '收货人',
					name: 'somdto.consignee',
					readOnly: true,
					width: TEXTFIELDWIDTH
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
					name: 'somdto.consigneePhone',
					readOnly: true,
					width: TEXTFIELDWIDTH
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
					name: 'somdto.consigneeAddress',
					readOnly: true,
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
				items: _grid_detailSheet_MOSS
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
					fieldLabel: '出库费',
					name: 'somdto.stockoutFee',
					readOnly: true,
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
					fieldLabel: '装卸费',
					name: 'somdto.loadUnloadCost',
					readOnly: true,
					width: 80
				}]
			},
			{//Column 3
				columnWidth: '0.25',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					readOnly:true,
					name: 'somdto.auditState',
					fieldLabel: '审核状态',
					width: 80
				}]
			},
			{//Column 4
				columnWidth: '0.25',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					readOnly:true,
					name: 'somdto.approvalState',
					fieldLabel: '批准状态',
					width: 80
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
					readOnly:true,
					name: 'somdto.remarks',
					width: 641
				}]
			}]
		}]
});

function resetPage_detail_MOSS()
{
	_formPanel_detailSheet_MOSS.getForm().reset();
	_ds_detailSheet_MOSS.removeAll();
	_grid_detailSheet_MOSS.getBottomToolbar().findById('mode_detail_MOSS').setValue('数量');
	_grid_detailSheet_MOSS.getBottomToolbar().findById('title_price_detail_MOSS').setText('数量报价：');
	_grid_detailSheet_MOSS.getBottomToolbar().findById('price_detail_MOSS').setValue(0);
	_grid_detailSheet_MOSS.getBottomToolbar().findById('allCount_detail_MOSS').setText('0');
	_grid_detailSheet_MOSS.getBottomToolbar().findById('allWeight_detail_MOSS').setText('0');
	_grid_detailSheet_MOSS.getBottomToolbar().findById('allVolume_detail_MOSS').setText('0');
}
