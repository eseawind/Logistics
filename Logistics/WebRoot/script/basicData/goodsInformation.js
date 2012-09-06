
function update_goodsInformation(contentPanel,node)
{
    
}

function init_goodsInformation(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_GI);
	_ds_GI.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_GI.reader.message,_ds_GI.reader.valid,success);
		}
	});
}

var _limit_GI = 30;

var _ds_GI = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'Item.queryOnCondition.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'resultMapList'
			}, 
			[{
				name : 'itemID'
			}, {
				name : 'batch'
			}, {
				name : 'name'
			}, {
				name : 'unit'
			}, {
				name : "unitVolume"
			}, {
				name : 'unitWeight'
			}, {
				name : 'number'
			}, {
				name : 'remarks'
			}]
		),
	baseParams:{
		'itemDTO.number': '',
		'itemDTO.name': '',
		start: 0,
		limit : _limit_GI
	}
});

var _sm_GI = new Ext.grid.CheckboxSelectionModel();
var _cm_GI = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
			_sm_GI,
		{
			dataIndex : 'itemID',
			header : '物料编号'
		},{
			dataIndex : 'name',
			header : '物料名称'
		},{
			header : '物料代码',
			dataIndex : 'number'
		},{
			header : '批次',
			dataIndex : 'batch',
			width: 90
		},{
			dataIndex : 'unit',
			header : '物料单位',
			width: 90
		},{
			dataIndex : 'unitVolume',
			header : '单位体积',
			width: 90
		},{
			dataIndex : 'unitWeight',
			header : '单位重量',
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

var _grid_GI = new Ext.grid.GridPanel({
	
			region: 'center',
			sm : _sm_GI,
			cm : _cm_GI,
			ds : _ds_GI,
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
					text : "新建",
					iconCls: 'commonAdd',
					handler: onCreateGoodsInformation
				},
				{
					text : "修改",
					iconCls: 'commonEdit',
					handler: function(){
						GRID_EDITDETAIL(_grid_GI,onEditGoodsInformation,'itemID');
					}
				},
				{
					text : "删除",
					iconCls: 'commonDelete',
					handler: function(){ GRID_DELETE(_grid_GI,'Item.delete.action','itemIDList','itemID'); }
				},
				{
					xtype: 'tbfill'
				},
				{
					xtype: 'label',
					text: '物料代码:'
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'textfield',
					id: 'conItemNumber_GI',
					width:　TEXTFIELDWIDTH,
					enableKeyEvents: true,
					listeners: { keyup: KEYDOWN_LENGTH_VLIDATOR }
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'label',
					text: '物料名称:'
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'textfield',
					id: 'conItemName_GI',
					width:　TEXTFIELDWIDTH,
					enableKeyEvents: true,
					listeners: { keyup: KEYDOWN_LENGTH_VLIDATOR }
				},
				{
					xtype: 'tbspacer',
					width: 10
				},'-',
				{
					text : "查询",
					iconCls: 'commonQuery',
					handler: function(){
						_ds_GI.baseParams.start = 0;
						setQeuryCondition_GI();
						_ds_GI.load({
							callback: function(record,option,success){
								STORE_CALLBACK(_ds_GI.reader.message,_ds_GI.reader.valid,success);
							}
						});
					}
				},'-',
				{
					text : "物料导入",
					iconCls: 'commonImport',
					handler: function(){onImportDate('GI');}
				},{
					xtype: 'tbspacer',
					width: 5
				}]
			}),

			bbar : new Ext.PagingToolbar({
						pageSize : _limit_GI,
						store : _ds_GI,
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
						       value: _limit_GI,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_GI.baseParams.limit = combo.getValue();
							        	_grid_GI.getBottomToolbar().pageSize = combo.getValue();
							        	setQeuryCondition_GI();
							        	_grid_GI.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
					})

		});

function setQeuryCondition_GI()
{
	_ds_GI.baseParams['itemDTO.number'] = _grid_GI.getTopToolbar().findById('conItemNumber_GI').getValue();
	_ds_GI.baseParams['itemDTO.name'] = _grid_GI.getTopToolbar().findById('conItemName_GI').getValue();
}

_grid_GI.on('rowcontextmenu', onRightClick_GI);

var _rightClickMenu_GI = new Ext.menu.Menu({
			
			items : [{
						handler :function(){ GRID_EDITDETAIL(_grid_GI,onEditGoodsInformation,'itemID'); },
						text : '修改',
						iconCls: 'commonEdit'
					}, {
						handler: function(){ GRID_DELETE(_grid_GI,'Item.delete.action','itemIDList','itemID'); },
						text : '删除',
						iconCls: 'commonDelete'
					}]
		});

function onRightClick_GI(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_GI);
}