
function update_manageBarCode(contentPanel,node)
{
    
}

function init_manageBarCode(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_MBC);
	_ds_MBC.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_MBC.reader.message,_ds_MBC.reader.valid,success);
		}
	});
}

var _limit_MBC = 30;

var _ds_MBC = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'Barcode.queryOnCondition.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'resultMapList'
			}, 
			[{
				name : 'barcodeID'
			}, {
				name : 'manifestID'
			}, {
				name : 'operationType'
			}, {
				name : "barcode"
			}, {
				name : 'amount'
			}, {
				name : 'itemID'
			}, {
				name : 'itemName'
			}, {
				name : 'itemNumber'
			}, {
				name : 'batch'
			}, {
				name : 'warehouseID'
			}, {
				name : 'warehouse'
			}, {
				name : 'customerID'
			}, {
				name : 'customer'
			}, {
				name : 'remarks'
			}]
		),
		baseParams: {
			'barcode.manifestID' : '',
			'barcode.operationType': '',
			start: 0,
			limit : _limit_MBC
		}
});

var _sm_MBC = new Ext.grid.CheckboxSelectionModel();
var _cm_MBC = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
			_sm_MBC,
		{
			header : '单号',
			dataIndex : 'manifestID'
		},{
			header : '操作类型',
			dataIndex : 'operationType'
		},{
			dataIndex : 'barcode',
			header : '条码值'
			
		},{
			dataIndex : 'amount',
			header : '数量'
		},{
			dataIndex : 'itemID',
			header : '物料编号'
		},{
			dataIndex : 'itemName',
			header : '物料名称'
		},{
			dataIndex : 'itemNumber',
			header : '物料代码'
		},{
			dataIndex : 'batch',
			header : '批次'
		},{
			dataIndex : 'warehouseID',
			header : '库存地编号'
		},{
			dataIndex : 'warehouse',
			header : '库存地名称'
		},{
			dataIndex : 'customerID',
			header : '客户编号'
		},{
			dataIndex : 'customer',
			header : '客户名称'
		},{
			dataIndex : 'remarks',
			header : '备注'
		}],
		
		defaults: {
			sortable: true,
			menuDisabled: true
		}
});

var _grid_MBC = new Ext.grid.GridPanel({
	
			region: 'center',
			sm : _sm_MBC,
			cm : _cm_MBC,
			ds : _ds_MBC,
			style: 'margin:0',
			stripeRows : true,
			loadMask : true,
			border : false,
			autoScroll: true,
			draggable : false,
			viewConfig : {
				forceFit : false
			},
			tbar : new Ext.Toolbar({
				border: true,
				items : [
				{
					xtype: 'tbspacer'
				},
				{
					text : "条码导入",
					iconCls: 'commonImport',
					handler: function(){onImportDate('MBC');}
				},
				{
					text : "条码导出",
					iconCls: 'commonExport',
					handler: function(){GRID_EXPORT(_grid_MBC,'MBC','barcodeID');}
				},'-',
				{
					text : "修改",
					iconCls: 'commonEdit',
					handler: function(){ GRID_EDITDETAIL(_grid_MBC,onEditBarCode,'barcodeID');}
				},
				{
					text : "删除",
					iconCls: 'commonDelete',
					handler: function(){ 
						GRID_DELETE(_grid_MBC,'Barcode.delete.action','ids','barcodeID'); 
					}				
				},
				{
					xtype: 'tbfill'
				},
				{
					xtype: 'label',
					text: '单号:'
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'textfield',
					width: DATAFIELDWIDTH,
					id: 'id_sheetId_MBC',
					enableKeyEvents: true,
					listeners: { keyup: KEYDOWN_LENGTH_VLIDATOR }
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'label',
					text: '操作类型:'
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : new Ext.data.SimpleStore({      
				              fields : ["returnValue", "displayText"],
				              data : [['', '--未选择--'],['出库', '出库'], ['入库','入库']]
				       }),
				       id:'id_sheetType_MBC',
				       valueField : 'returnValue',
				       displayField : 'displayText',
				       mode : 'local',
				       forceSelection : true,
				       editable : false,
				       triggerAction : 'all',
				       allowBlank : false,
				       value: '',
				       width:　DATAFIELDWIDTH
				})
				,
				{
					xtype: 'tbspacer',
					width: 10
				},'-',
				{
					text : "查询",
					iconCls: 'commonQuery',
					handler: function(){
						_ds_MBC.baseParams.start = 0;
						setQeuryCondition_MBC();
						_ds_MBC.load({
							callback: function(record,option,success){
								STORE_CALLBACK(_ds_MBC.reader.message,_ds_MBC.reader.valid,success);
							}
						});
					}
				},'-',
				{
					xtype: 'tbspacer',
					width: 10
				}]
			}),

			bbar : new Ext.PagingToolbar({
						pageSize : _limit_MBC,
						store : _ds_MBC,
						displayInfo : true,
						displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
						emptyMsg : "没有记录",
						items : [
							new Ext.form.ComboBox({
						       xtype : 'combo',
						       store : new Ext.data.SimpleStore({      
						              fields : ["displayText"],
						              data : [[20],[30],[50],[100]]
						       }),
						       hiddenName  : 'num',
						       valueField : 'displayText',
						       displayField : 'displayText',
						       mode : 'local',
						       forceSelection : true,
						       title : '每页结果数量',
						       editable : false,
						       triggerAction : 'all',
						       value: _limit_MBC,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_MBC.baseParams.limit = combo.getValue();
							        	_grid_MBC.getBottomToolbar().pageSize = combo.getValue();
							        	setQeuryCondition_MBC();
							        	_grid_MBC.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
					})

		});

function setQeuryCondition_MBC()
{
	_ds_MBC.baseParams['barcode.manifestID'] = _grid_MBC.getTopToolbar().findById('id_sheetId_MBC').getValue();
	_ds_MBC.baseParams['barcode.operationType'] = _grid_MBC.getTopToolbar().findById('id_sheetType_MBC').getValue();
}
		
_grid_MBC.on('rowcontextmenu', onRightClick_MBC);

var _rightClickMenu_MBC = new Ext.menu.Menu({
			
			items : [{
						handler: function(){ GRID_EDITDETAIL(_grid_MBC,onEditBarCode,'barcodeID');},
						text : '修改',
						iconCls: 'commonEdit'
					}, {
						handler: function(){ GRID_DELETE(_grid_MBC,'Barcode.delete.action','ids','barcodeID'); },
						text : '删除',
						iconCls: 'commonDelete'
					}]
		});

function onRightClick_MBC(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_MBC);
}