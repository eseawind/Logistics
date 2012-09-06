
function update_costCenter(contentPanel,node)
{
    
}

function init_costCenter(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_CCTER);

	_ds_CCTER.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_CCTER.reader.message,_ds_CCTER.reader.valid,success);
		}
	});
}

var _limit_CCTER = 30;
var _ds_CCTER = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url :'CostCenter.queryOnCondition.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'resultMapList'
			}, 
			[{
				name : 'costCenterID'
			}]
		),
	baseParams:{
		start: 0,
		limit : _limit_CCTER
	}
});

var _sm_CCTER = new Ext.grid.CheckboxSelectionModel();
var _cm_CCTER = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
			_sm_CCTER,
		{
			dataIndex : 'costCenterID',
			header : '成本中心'
		}],
		
		defaults: {
			sortable: true,
			menuDisabled: true
		}
});

var _grid_CCTER = new Ext.grid.GridPanel({
			region: 'center',
			sm : _sm_CCTER,
			cm : _cm_CCTER,
			ds : _ds_CCTER,
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
					handler: onCreateCostCenter
				},
				{
					text : "删除",
					iconCls: 'commonDelete',
					handler: function(){ GRID_DELETE(_grid_CCTER,'CostCenter.delete.action','costCenterList','costCenterID'); }
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
						pageSize : _limit_CCTER,
						store : _ds_CCTER,
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
						       value: _limit_CCTER,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_CCTER.baseParams.limit = combo.getValue();
							        	_grid_CCTER.getBottomToolbar().pageSize = combo.getValue();
							        	_grid_CCTER.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
					})

		});

_grid_CCTER.on('rowcontextmenu', onRightClick_CCTER);

var _rightClickMenu_CCTER = new Ext.menu.Menu({
			
			items : [{
						handler: function(){ GRID_DELETE(_grid_CCTER,'CostCenter.delete.action','costCenterList','costCenterID'); },
						text : '删除',
						iconCls: 'commonDelete'
					}]
		});

		
function onRightClick_CCTER(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_CCTER);
}