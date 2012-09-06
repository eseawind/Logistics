
function update_manageRole(contentPanel,node)
{
    
}

function init_manageRole(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_MR);
	_ds_MR.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_MR.reader.message,_ds_MR.reader.valid,success);
		}
	});
}

var _limit_MR = 30;

var _ds_MR = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'Role.queryOnCondition.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'resultMapList'
			}, 
			[{
				name : 'roleID'
			}, {
				name : 'roleName'
			}, {
				name : 'remarks'
			}]
		),
		baseParams: {
			start: 0,
			limit : _limit_MR
		}
});

var _sm_MU = new Ext.grid.CheckboxSelectionModel();

var _cm_MR = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
			_sm_MU,
		{
			header : '角色名称',
			dataIndex : 'roleName',
			width: 20
		},{
			dataIndex : 'remarks',
			header : '权限描述'	
		}],
		
		defaults: {
			sortable: true,
			menuDisabled: true
		}
});

var _grid_MR = new Ext.grid.GridPanel({
	
			region: 'center',
			sm : _sm_MU,
			cm : _cm_MR,
			ds : _ds_MR,
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
					iconCls: 'addRole',
					handler: onCreateRole
				},
				{
					text : "修改",
					iconCls: 'editRole',
					handler: function(){GRID_EDITDETAIL(_grid_MR,onEditRole,'roleID');}
				},
				{
					text : "删除",
					iconCls: 'deleteRole',
					handler: function(){ 
						GRID_DELETE(_grid_MR,'Role.delete.action','ids','roleID'); 
					}			
				}]
			}),

			bbar : new Ext.PagingToolbar({
						pageSize : _limit_MR,
						store : _ds_MR,
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
						       value: _limit_MR,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_MR.baseParams.limit = combo.getValue();
							        	_grid_MR.getBottomToolbar().pageSize = combo.getValue();
							        	setQeuryCondition_MR();
							        	_grid_MR.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
					})

		});

_grid_MR.on('rowcontextmenu', onRightClick_MR);

var _rightClickMenu_MR = new Ext.menu.Menu({
			
			items : [{
						handler: function(){GRID_EDITDETAIL(_grid_MR,onEditRole,'logID');},
						text : '修改',
						iconCls: 'commonEdit'
					}, {
						handler: function(){GRID_DELETE(_grid_MR,'Role.delete.action','ids','roleID');},
						text : '删除',
						iconCls: 'commonDelete'
					}]
		});

function onRightClick_MR(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_MR);
}