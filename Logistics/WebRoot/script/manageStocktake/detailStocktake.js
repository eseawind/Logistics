
var _win_detail_MS = null;
var _mask_detail_MS = null;
function onDetailStocktake(id)
{
	if(null == _win_detail_MS)
	{
	    createWindow_detail_MS();
	}else
	{
		resetPage_detail_MS();
	}
	_mask_detail_MS.show();
	_formPanel_detailSheet_MS.getForm().load({
    	url: 'InventoryManifest.queryOne.action',
    	method: 'POST',
    	params: {'imdto.inventoryManifestID': id},
    	success: function(form,action){
    		if(action.result.resultMapList != null){
	    		_ds_detailSheet_MS.loadData(action.result,false);
    		}
    		_mask_detail_MS.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'盘点单数据读取失败！');
    		_mask_detail_MS.hide();
    	}
    });
	_win_detail_MS.setPagePosition(GET_WIN_X(_win_detail_MS.width),GET_WIN_Y_M(600));
    _win_detail_MS.show();
}

function createWindow_detail_MS()
{
	_win_detail_MS = new Ext.Window({
        title: '盘点单明细',
        iconCls: 'commonDetial',
        width: 800,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_detailSheet_MS,
        listeners: LISTENER_WIN_MOVE
    });
    _win_detail_MS.show();
    _mask_detail_MS = new Ext.LoadMask(_win_detail_MS.body, {msg:"正在载入,请稍后..."});
}

var _ds_detailSheet_MS = new Ext.data.Store({
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			_record_MS
		)
});
	
	var _cm_detailSheet_MS = new Ext.grid.ColumnModel({
			columns:[
				new Ext.grid.RowNumberer(),
			{
				width:60,
				dataIndex : 'itemID',
				header : '物料编号'	
			},{
				dataIndex : 'itemName',
				header : '物料名称'	
			},{
				dataIndex : 'itemNumber',
				header : '物料代码'	
			},{
				width:60,
				dataIndex : 'batch',
				header : '批次'	
			},{
				width:60,
				dataIndex : 'unit',
				header : '物料单位'	
			},{
				width:60,
				dataIndex : 'amountRecorded',
				header : '库存数量'	
			},{
				width:60,
				dataIndex : 'amountExisted',
				header : '实盘数量'
			},{
				width:60,
				dataIndex : 'amountDifference',
				header : '差异'	
			},{
				dataIndex : 'differenceRemarks',
				header : '差异备注'
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_detailSheet_MS = new Ext.grid.GridPanel({
				cm : _cm_detailSheet_MS,
				ds : _ds_detailSheet_MS,
				style: 'margin:0',
				stripeRows : true,
				loadMask : true,
				border : true,
				frame: false,
				height : 280,
				autoScroll: true,
				width: 641,
				draggable : false,
				fieldLabel: '物料列表',
				viewConfig : {
					forceFit : false
				}
			});


var _formPanel_detailSheet_MS = new Ext.FormPanel({
	
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
		handler: function(){_win_detail_MS.hide();}
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
					fieldLabel: '库存地',
					readOnly:true,
					name: 'imdto.warehouseName',
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
					fieldLabel: '盘点日期',
					readOnly:true,
					name:'imdto.dateInventoried',
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
					fieldLabel: '盘点单号',
					width: TEXTFIELDWIDTH,
					name: 'imdto.inventoryManifestID',
					readOnly:true
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
					fieldLabel: '盘点类型',
					width: TEXTFIELDWIDTH,
					name: 'imdto.type',
					readOnly:true
				}]
			},
			{//Column 2
				columnWidth: '0.6',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '盘点人',
					width: TEXTFIELDWIDTH,
					name: 'imdto.inventoryPerson',
					readOnly:true
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
				items: _grid_detailSheet_MS
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
					fieldLabel: '盘点概况',
					readOnly:true,
					name: 'imdto.result',
					width: 641
				}]
			}]
		}]
});

function resetPage_detail_MS()
{
	_formPanel_detailSheet_MS.getForm().reset();
	_ds_detailSheet_MS.removeAll();
}
