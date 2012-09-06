
function update_carType(contentPanel,node)
{
    
}

function init_carType(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_CARTP);

	_ds_CARTP.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_CARTP.reader.message,_ds_CARTP.reader.valid,success);
		}
	});
}
var _limit_CARTP = 30;
var _ds_CARTP = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'CarType.queryOnCondition.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'resultMapList'
			}, 
			[{
				name : 'carTypeID'
			}, {
				name : 'carTypeName'
			},  {
				name : 'remarks'
			}]
		),
	baseParams:{
		start: 0,
		limit : _limit_CARTP
	}
});

var _sm_CARTP = new Ext.grid.CheckboxSelectionModel();
var _cm_CARTP = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
			_sm_CARTP,
		{
			dataIndex : 'carTypeName',
			header : '类型名称',
			width: 30
			
		},{
			dataIndex : 'remarks',
			header : '备注'	
			
		}],
		
		defaults: {
			sortable: true,
			menuDisabled: true
		}
});

var _grid_CARTP = new Ext.grid.GridPanel({
			region: 'center',
			sm : _sm_CARTP,
			cm : _cm_CARTP,
			ds : _ds_CARTP,
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
					handler: onCreateCarType
				},
				{
					text : "修改",
					iconCls: 'commonEdit',
					handler: function(){
						GRID_EDITDETAIL(_grid_CARTP,onEditCarType,'carTypeID');
					}
				},
				{
					text : "删除",
					iconCls: 'commonDelete',
					handler: function(){ GRID_DELETE(_grid_CARTP,'CarType.delete.action','carTypeIDList','carTypeID'); }
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
						pageSize : _limit_CARTP,
						store : _ds_CARTP,
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
						       value: _limit_CARTP,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_CARTP.baseParams.limit = combo.getValue();
							        	_grid_CARTP.getBottomToolbar().pageSize = combo.getValue();
							        	_grid_CARTP.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
					})

		});

_grid_CARTP.on('rowcontextmenu', onRightClick_CARTP);

var _rightClickMenu_CARTP = new Ext.menu.Menu({
			
			items : [{
						handler : function(){
							GRID_EDITDETAIL(_grid_CARTP,onEditCarType,'carTypeID');
						},
						text : '修改',
						iconCls: 'commonEdit'
					}, {
						handler: function(){ GRID_DELETE(_grid_CARTP,'CarType.delete.action','carTypeIDList','carTypeID'); },
						text : '删除',
						iconCls: 'commonDelete'
					}]
		});

		
function onRightClick_CARTP(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_CARTP);
}