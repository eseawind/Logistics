
var _win_detail_MTSS = null;
var _mask_detail_MTSS = null;
function onDetailTransferStorageSheet(id)
{
	if(null == _win_detail_MTSS)
	{
	    createWindow_detail_MTSS();
	}else{
		resetPage_detail_MTSS();
	}
	_mask_detail_MTSS.show();
	_formPanel_detailSheet_MTSS.getForm().load({
    	url: 'StockTransferManifest.queryOne.action',
    	method: 'POST',
    	params: {'stmdto.stockTransferManifestID': id},
    	success: function(form,action){
    		if(action.result.resultMapList != null){
	    		_ds_detailSheet_MTSS.loadData(action.result,false);
    		}
    		setSheetValue_detail_MTSS(action.result.data);
    		_mask_detail_MTSS.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'入库单数据读取失败！');
    		_mask_detail_MTSS.hide();
    	}
    });
	_win_detail_MTSS.setPagePosition(GET_WIN_X(_win_detail_MTSS.width),GET_WIN_Y_M(600));
    _win_detail_MTSS.show();
}

function setSheetValue_detail_MTSS(data)
{
	_grid_detailSheet_MTSS.getBottomToolbar().findById('allCount_detail_MTSS').setText(data['stmdto.sumAmount'].toString());
	_grid_detailSheet_MTSS.getBottomToolbar().findById('allWeight_detail_MTSS').setText(data['stmdto.sumWeight'].toString());
	_grid_detailSheet_MTSS.getBottomToolbar().findById('allVolume_detail_MTSS').setText(data['stmdto.sumVolume'].toString());
}

function createWindow_detail_MTSS()
{
	_win_detail_MTSS = new Ext.Window({
        title: '移库单明细',
        iconCls: 'commonDetail',
        width: 800,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_detailSheet_MTSS,
        listeners: LISTENER_WIN_MOVE
    });
    _win_detail_MTSS.show();
    _mask_detail_MTSS = new Ext.LoadMask(_win_detail_MTSS.body, {msg:"正在载入,请稍后..."});
}

var _ds_detailSheet_MTSS = new Ext.data.Store({
	reader : new Ext.data.JsonReader({
				root : 'resultMapList'
			}, 
			_record_MTSS
		)
})
	
	var _cm_detailSheet_MTSS = new Ext.grid.ColumnModel({
			columns:[
				new Ext.grid.RowNumberer(),
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
				width:60,
				dataIndex : 'amount',
				header : '移库数量'
			},{
				width:60,
				dataIndex : 'unitWeight',
				header : '单位重量'	
			},{
				width:60,
				dataIndex : 'unitVolume',
				header : '单位体积'
			},{
				width:60,
				dataIndex : 'customerID',
				header : '客户编号'	
			},{
				width:160,
				dataIndex : 'customer',
				header : '客户名称'
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_detailSheet_MTSS = new Ext.grid.GridPanel({
				cm : _cm_detailSheet_MTSS,
				ds : _ds_detailSheet_MTSS,
				style: 'margin:0',
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
						id: 'allCount_detail_MTSS',
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
						id: 'allWeight_detail_MTSS',
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
						id: 'allVolume_detail_MTSS',
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
						xtype: 'tbspacer',
						width: 5
					}]
				})
			});


var _formPanel_detailSheet_MTSS = new Ext.FormPanel({
	
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
		handler: function(){_win_detail_MTSS.hide();}
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
					fieldLabel: '调出仓库',
					name: 'stmdto.outWarehouse',
					readOnly:true,
					width: 200
				}]
			},
			{//Column 2
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '移库单号',
					name: 'stmdto.stockTransferManifestID',
					readOnly:true,
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
					fieldLabel: '建单日期',
					width: TEXTFIELDWIDTH,
					readOnly: true,
					name: 'stmdto.dateCreated'
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
					xtype: 'textfield',
					fieldLabel: '调入仓库',
					name: 'stmdto.inWarehouse',
					readOnly:true,
					width: 200
				}]
			},
			{//Column 2
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '移库日期',
					width: TEXTFIELDWIDTH,
					readOnly: true,
					name: 'stmdto.dateTransfered'
				}]
			},
			{//Column 3
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '操作员',
					width: TEXTFIELDWIDTH,
					readOnly: true,
					name: 'stmdto.operator'
				}]
			}]
		},
		{//Row 4-Grid
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1.0',
				layout: 'form',
				border: false,
				items: _grid_detailSheet_MTSS
			}]
		},
		{//Row 5
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					readOnly: true,
					name: 'stmdto.loadUnloadCost',
					fieldLabel: '装卸费',
					width: 80
				}]
			},
			{//Column 2
				columnWidth: '0.7',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'textfield',
					readOnly: true,
					name: 'stmdto.costCenter',
					fieldLabel: '成本中心',
					width: 120
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
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '备注信息',
					readOnly:true,
					name: 'stmdto.remarks',
					width: 641
				}]
			}]
		}]
});

function resetPage_detail_MTSS()
{
	_formPanel_detailSheet_MTSS.getForm().reset();
	_ds_detailSheet_MTSS.removeAll();
	_grid_detailSheet_MTSS.getBottomToolbar().findById('allCount_detail_MTSS').setText('0');
	_grid_detailSheet_MTSS.getBottomToolbar().findById('allWeight_detail_MTSS').setText('0');
	_grid_detailSheet_MTSS.getBottomToolbar().findById('allVolume_detail_MTSS').setText('0');
}
