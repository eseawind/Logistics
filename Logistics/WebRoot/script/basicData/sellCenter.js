
function update_sellCenter(contentPanel,node)
{
    
}

function init_sellCenter(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_SCTER);

	_ds_SCTER.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_SCTER.reader.message,_ds_SCTER.reader.valid,success);
		}
	});
}

var _limit_SCTER = 30;
var _ds_SCTER = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url :'SellCenter.queryOnCondition.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'resultMapList'
			}, 
			[{
				name : 'sellCenterID'
			}]
		),
	baseParams:{
		start: 0,
		limit : _limit_SCTER
	}
});

var _sm_SCTER = new Ext.grid.CheckboxSelectionModel();
var _cm_SCTER = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
			_sm_SCTER,
		{
			dataIndex : 'sellCenterID',
			header : '销售中心'
		}],
		
		defaults: {
			sortable: true,
			menuDisabled: true
		}
});

var _grid_SCTER = new Ext.grid.GridPanel({
			region: 'center',
			sm : _sm_SCTER,
			cm : _cm_SCTER,
			ds : _ds_SCTER,
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
					handler: onCreateSellCenter
				},
				{
					text : "删除",
					iconCls: 'commonDelete',
					handler: function(){ GRID_DELETE(_grid_SCTER,'SellCenter.delete.action','sellCenterList','sellCenterID'); }
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
						pageSize : _limit_SCTER,
						store : _ds_SCTER,
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
						       value: _limit_SCTER,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_SCTER.baseParams.limit = combo.getValue();
							        	_grid_SCTER.getBottomToolbar().pageSize = combo.getValue();
							        	_grid_SCTER.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
					})

		});

_grid_SCTER.on('rowcontextmenu', onRightClick_SCTER);

var _rightClickMenu_SCTER = new Ext.menu.Menu({
			
			items : [{
						handler: function(){ GRID_DELETE(_grid_SCTER,'SellCenter.delete.action','sellCenterList','sellCenterID'); },
						text : '删除',
						iconCls: 'commonDelete'
					}]
		});

		
function onRightClick_SCTER(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_SCTER);
}