
var _win_detail_MESS = null;
var _mask_detail_MESS = null;
function onDetailEnterStorageSheet(id)
{
	if(null == _win_detail_MESS)
	{
	    createWindow_detail_MESS();
	}else
	{
		resetPage_detail_MESS();
	}
	_mask_detail_MESS.show();
	_formPanel_detailSheet_MESS.getForm().load({
    	url: 'StockinManifest.queryOne.action',
    	method: 'POST',
    	params: {'simdto.stockinManifestID': id},
    	success: function(form,action){
    		if(action.result.resultMapList != null){
	    		_ds_detailSheet_MESS.loadData(action.result,false);
    		}
    		setSheetValue_detail_MESS(action.result.data);
    		_mask_detail_MESS.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'入库单数据读取失败！');
    		_mask_detail_MESS.hide();
    	}
    });
	_win_detail_MESS.setPagePosition(GET_WIN_X(_win_detail_MESS.width),GET_WIN_Y_M(600));
    _win_detail_MESS.show();
}

function setSheetValue_detail_MESS(data)
{
	_grid_detailSheet_MESS.getBottomToolbar().findById('mode_detail_MESS').setValue(data['simdto.chargeMode']);
	var title = _grid_detailSheet_MESS.getBottomToolbar().findById('title_price_detail_MESS');
	if(data['simdto.chargeMode']=='数量')
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
	_grid_detailSheet_MESS.getBottomToolbar().findById('price_detail_MESS').setValue(data['simdto.unitPrice']);
	_grid_detailSheet_MESS.getBottomToolbar().findById('allCount_detail_MESS').setText(data['simdto.sumAmount'].toString());
	_grid_detailSheet_MESS.getBottomToolbar().findById('allWeight_detail_MESS').setText(data['simdto.sumWeight'].toString());
	_grid_detailSheet_MESS.getBottomToolbar().findById('allVolume_detail_MESS').setText(data['simdto.sumVolume'].toString());
}

function createWindow_detail_MESS()
{
	_win_detail_MESS = new Ext.Window({
        title: '入库单明细',
        iconCls: 'commonDetail',
        width: 800,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_detailSheet_MESS,
        listeners: LISTENER_WIN_MOVE
    });
    _win_detail_MESS.show();
    _mask_detail_MESS = new Ext.LoadMask(_win_detail_MESS.body, {msg:"正在载入,请稍后..."});
}

var _ds_detailSheet_MESS = new Ext.data.Store({
	reader : new Ext.data.JsonReader({
				root : 'resultMapList'
			}, 
			_record_MESS
		)
});

	var _cm_detailSheet_MESS = new Ext.grid.ColumnModel({
			columns:[
				new Ext.grid.RowNumberer(),
			{
				width:60,
				dataIndex : 'itemID',
				header : '物料编号'	
			},{
				width:200,
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
				dataIndex : 'expectedAmount',
				header : '应收数量'
			},{
				width:60,
				dataIndex : 'amount',
				header : '实收数量'
			},{
				width:60,
				dataIndex : 'unit',
				header : '计量单位'	
			},{
				width:60,
				dataIndex : 'unitWeight',
				header : '单位重量'	
			},{
				width:60,
				dataIndex : 'unitVolume',
				header : '单位体积'
			},{
				dataIndex : 'label',
				header : '商品检验标志是否OK',
				width: 130
			},{
				dataIndex : 'isSN',
				header : '是否扫描SN',
				width: 90
			},{
				dataIndex : 'remarks',
				header : '备注'
			}],
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_detailSheet_MESS = new Ext.grid.GridPanel({
				cm : _cm_detailSheet_MESS,
				ds : _ds_detailSheet_MESS,
				style: 'margin:0px 0px 2px 0px',
				stripeRows : true,
				loadMask : true,
				border : true,
				frame: false,
				height : 180,
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
						id: 'allCount_detail_MESS',
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
						id: 'allWeight_detail_MESS',
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
						id: 'allVolume_detail_MESS',
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
						id:'title_price_detail_MESS',
						text: '数量报价：'
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'numberfield',
						id: 'price_detail_MESS',
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
						id :'mode_detail_MESS',
						width: 80,
						readOnly:true
					},
					{
						xtype: 'tbspacer',
						width: 5
					}]
				})
			});


var _formPanel_detailSheet_MESS = new Ext.FormPanel({
	
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
		handler: function(){ _win_detail_MESS.hide(); }
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
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '入库单号',
					name: 'simdto.stockinManifestID',
					readOnly: true,
					width: TEXTFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '建单日期',
					name: 'simdto.dateCreated',
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
					fieldLabel: '客户单号',
					name: 'simdto.customerNumber',
					readOnly: true,
					width: TEXTFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '入库日期',
					name: 'simdto.dateStockin',
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
					fieldLabel: '库存地',
					name: 'simdto.warehouseName',
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
					name: 'simdto.sellCenter',
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
					name: 'simdto.costCenter',
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
					fieldLabel: '客户名称',
					name: 'simdto.customer',
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
					fieldLabel: '送货人',
					name: 'simdto.deliveryPerson',
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
					fieldLabel: '送货人电话',
					name: 'simdto.deliveryPhone',
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
					fieldLabel: '提货地址',
					name: 'simdto.takingAddress',
					readOnly: true,
					width: 641
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
					fieldLabel: '启运单位',
					width: 200,
					name: 'simdto.originAgent',
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
					fieldLabel: '启运人',
					width: TEXTFIELDWIDTH,
					name: 'simdto.originPerson',
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
					fieldLabel: '启运人电话',
					width: TEXTFIELDWIDTH,
					name: 'simdto.originPhone',
					readOnly: true
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
					fieldLabel: '启运地址',
					readOnly: true,
					name: 'simdto.originAddress',
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
				items: _grid_detailSheet_MESS
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
					xtype: 'textfield',
					fieldLabel: '入库费',
					name: 'simdto.stockinFee',
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
					xtype: 'textfield',
					fieldLabel: '装卸费',
					name: 'simdto.loadUnloadCost',
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
					name: 'auditState',
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
					name: 'approvalState',
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
					readOnly:true,
					name: 'simdto.remarks',
					fieldLabel: '备注信息',
					width: 641
				}]
			}]
		}]
});

function resetPage_detail_MESS()
{
	_formPanel_detailSheet_MESS.getForm().reset();
	_ds_detailSheet_MESS.removeAll();
	_grid_detailSheet_MESS.getBottomToolbar().findById('mode_detail_MESS').setValue('数量');
	_grid_detailSheet_MESS.getBottomToolbar().findById('title_price_detail_MESS').setText('数量报价：');
	_grid_detailSheet_MESS.getBottomToolbar().findById('price_detail_MESS').setValue(0);
	_grid_detailSheet_MESS.getBottomToolbar().findById('allCount_detail_MESS').setText('0');
	_grid_detailSheet_MESS.getBottomToolbar().findById('allWeight_detail_MESS').setText('0');
	_grid_detailSheet_MESS.getBottomToolbar().findById('allVolume_detail_MESS').setText('0');
	
}
