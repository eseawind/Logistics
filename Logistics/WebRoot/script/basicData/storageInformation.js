
function update_storageInformation(contentPanel,node)
{
    
}
function init_storageInformation(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_SI);

	_ds_SI.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_SI.reader.message,_ds_SI.reader.valid,success);
		}
	});
}

var _limit_SI = 30;

var _ds_SI = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'Warehouse.queryOnCondition.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'resultMapList'
			}, 
			[{
				name : 'warehouseID'
			}, {
				name : 'name'
			}, {
				name : 'jointName'
			}, {
				name : "address"
			}, {
				name : 'remarks'
			}]
		),
	baseParams:{
		start: 0,
		limit : _limit_SI
	}
});

var _sm_SI = new Ext.grid.CheckboxSelectionModel();
var _cm_SI = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
			_sm_SI,
		{
			header : '仓库编号',
			dataIndex : 'warehouseID',
			width: 80
		},{
			dataIndex : 'name',
			header : '仓库名称',
			width: 80
			
		},{
			dataIndex : 'jointName',
			header : '仓库所在地'	
			
		},{
			dataIndex : 'address',
			header : '详细地址',
			width: 80
			
		},{
			dataIndex : 'remarks',
			header : '备注',
			width: 80
			
		}],
		
		defaults: {
			sortable: true,
			menuDisabled: true
		}
});

var _grid_SI = new Ext.grid.GridPanel({
	
			region: 'center',
			sm : _sm_SI,
			cm : _cm_SI,
			ds : _ds_SI,
			style: 'margin:0',
			stripeRows : true,
			loadMask : true,
			border : false,
			autoScroll: true,
			draggable : false,
			viewConfig : {
				forceFit : true
			},
			
			tbar : new Ext.Toolbar({
				border: true,
				items : [
				{
					xtype: 'tbspacer'
				},
				{
					text : "新建",
					iconCls: 'commonAdd',
					handler: onCreateStorageInformation
				},
				{
					text : "修改",
					iconCls: 'commonEdit',
					handler: function(){ GRID_EDITDETAIL(_grid_SI,onEditStorageInformation,'warehouseID'); }
				},
				{
					text : "删除",
					iconCls: 'commonDelete'	,
					handler: function(){ GRID_DELETE(_grid_SI,'Warehouse.delete.action','warehouseIDList','warehouseID'); }
				},
				{
					xtype: 'tbfill'
				},
				{
					xtype: 'tbspacer',
					width: 10
				}]
			}),
			bbar : new Ext.PagingToolbar({
						pageSize : _limit_SI,
						store : _ds_SI,
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
						       value: _limit_SI,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_SI.baseParams.limit = combo.getValue();
							        	_grid_SI.getBottomToolbar().pageSize = combo.getValue();
							        	_grid_SI.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
					})
		});

_grid_SI.on('rowcontextmenu', onRightClick_SI);

var _rightClickMenu_SI = new Ext.menu.Menu({
			
			items : [{
						handler : function(){
							GRID_EDITDETAIL(_grid_SI,onEditStorageInformation,'warehouseID');
						},
						text : '修改',
						iconCls: 'commonEdit'
					}, {
						handler: function(){ GRID_DELETE(_grid_SI,'Warehouse.delete.action','warehouseIDList','warehouseID'); },
						text : '删除',
						iconCls: 'commonDelete'
					}]
		});

function onRightClick_SI(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_SI);
}