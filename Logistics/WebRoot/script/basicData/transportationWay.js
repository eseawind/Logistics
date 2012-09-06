
function update_transportationWay(contentPanel,node)
{
    
}

function init_transportationWay(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_TW);
	STORE_CITY_LOAD();
	_ds_TW.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_TW.reader.message,_ds_TW.reader.valid,success);
		}
	});
}

var _limit_TW = 30;

var _ds_TW = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'FreightRoute.queryOnCondition.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'resultMapList'
			}, 
			[{
				name : 'freightRouteID'
			}, {
				name : 'originJointName'
			}, {
				name : 'destinationJointName'
			}, {
				name : "daysSpent"
			}, {
				name : 'remarks'
			}]
		),
	baseParams:{
		'freightRouteDTO.freightRouteID': '',
		'freightRouteDTO.originID': '',
		'freightRouteDTO.destinationID': '',
		start: 0,
		limit : _limit_TW
	}
});

var _sm_TW = new Ext.grid.CheckboxSelectionModel();
var _cm_TW = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
			_sm_TW,
		{
			dataIndex : 'freightRouteID',
			header : '路线编号'
		},{
			dataIndex : 'originJointName',
			header : '始发地'
		},{
			dataIndex : 'destinationJointName',
			header : '目的地'	
		},{
			dataIndex : 'daysSpent',
			header : '预计运输天数'	
		},{
			dataIndex : 'remarks',
			header : '备注'
			
		}],
		
		defaults: {
			sortable: true,
			menuDisabled: true
		}
});

var _grid_TW = new Ext.grid.GridPanel({
	
			region: 'center',
			sm : _sm_TW,
			cm : _cm_TW,
			ds : _ds_TW,
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
					handler: onCreateTransportationWay
				},
				{
					text : "修改",
					iconCls: 'commonEdit',
					handler: function(){ GRID_EDITDETAIL(_grid_TW,onEditTransportationWay,'freightRouteID'); }
				},
				{
					text : "删除",
					iconCls: 'commonDelete',
					handler: function(){ GRID_DELETE(_grid_TW,'FreightRoute.delete.action','freightRouteIDList','freightRouteID'); }
				},
				{
					xtype: 'tbfill'
				},
				{
					xtype: 'label',
					text: '始发地:'
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : STORE_CITY,
				       valueField : 'cityID',
				       displayField : 'jointName',
				       mode : 'local',
				       forceSelection : true,
				       id: 'conStartCity_TW',
				       selectOnFocus:true,
				       typeAhead: false,
				       editable : true,
				       triggerAction : 'all',
				       text: '始发地',
				       name : 'city',
				       width:　150,
				       listeners: {
				       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
				       }
				}),
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'label',
					text: '目的地:'
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : STORE_CITY,
				       valueField : 'cityID',
				       displayField : 'jointName',
				       mode : 'local',
				       forceSelection : true,
				       id: 'conEndCity_TW',
				       selectOnFocus:true,
				       typeAhead: true,
				       editable : true,
				       triggerAction : 'all',
				       text: '目的地',
				       name : 'city',
				       width:　150,
				       listeners: {
				       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
				       }
				}),
				{
					xtype: 'tbspacer',
					width: 10
				},'-',
				{
					text : "查询",
					iconCls: 'commonQuery',
					handler: function(){
						_ds_TW.baseParams.start = 0;
						setQeuryCondition_TW();
						_ds_TW.load({
							callback: function(record,option,success){
								STORE_CALLBACK(_ds_TW.reader.message,_ds_TW.reader.valid,success);
							}
						});
					}
				},'-',
				{
					xtype: 'tbspacer',
					width: 10
				}]
			}),

			// 表格底部分页工具栏
			bbar : new Ext.PagingToolbar({
						pageSize : _limit_TW,
						store : _ds_TW,
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
						       value: _limit_TW,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_TW.baseParams.limit = combo.getValue();
							        	_grid_TW.getBottomToolbar().pageSize = combo.getValue();
							        	setQeuryCondition_TW();
							        	_grid_TW.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
					})

		});

function setQeuryCondition_TW()
{
	_ds_TW.baseParams['freightRouteDTO.originID'] = _grid_TW.getTopToolbar().findById('conStartCity_TW').getValue();
	_ds_TW.baseParams['freightRouteDTO.destinationID'] = _grid_TW.getTopToolbar().findById('conEndCity_TW').getValue();
}		

_grid_TW.on('rowcontextmenu', onRightClick_TW);

var _rightClickMenu_TW = new Ext.menu.Menu({
			
			items : [{
						handler :function(){ GRID_EDITDETAIL(_grid_TW,onEditTransportationWay,'freightRouteID'); },
						text : '修改',
						iconCls: 'commonEdit'
					}, {
						handler: function(){ GRID_DELETE(_grid_TW,'FreightRoute.delete.action','freightRouteIDList','freightRouteID'); },
						text : '删除',
						iconCls: 'commonDelete'
					}]
		});

function onRightClick_TW(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_TW);
}