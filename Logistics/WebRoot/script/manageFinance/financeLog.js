
function update_financeLog(contentPanel,node)
{
    
}

function init_financeLog(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_FL);
	_ds_FL.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_FL.reader.message,_ds_FL.reader.valid,success);
		}
	});
}

var _limit_FL = 30;
var _ds_FL = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'FinancialLog.queryOnCondition.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'resultMapList'
			}, 
			[{
				name : 'logID'
			}, {
				name : 'operationTime'
			}, {
				name : 'type'
			}, {
				name : "user"
			}, {
				name : "content"
			}]
		),
		baseParams: {
			'user': '',
			startDate: '',
			endDate: '',
			start: 0,
			limit : _limit_FL
		}
});

var _sm_FL = new Ext.grid.CheckboxSelectionModel();
var _cm_FL = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
			_sm_FL,
		{
			header : '操作日期',
			dataIndex : 'operationTime',
			width: 25
		},{
			header : '业务类型',
			dataIndex : 'type',
			width: 25
		},{
			dataIndex : 'user',
			header : '操作账号',
			width: 25
		},{
			dataIndex : 'content',
			header : '操作内容'
		}],
		
		defaults: {
			sortable: true,
			menuDisabled: true
		}
});

var _grid_FL = new Ext.grid.GridPanel({
	
			region: 'center',
			sm : _sm_FL,
			cm : _cm_FL,
			ds : _ds_FL,
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
				},'-',
				{
					text : "删除日志",
					iconCls: 'commonDelete'	,
					handler: function(){ 
						GRID_DELETE(_grid_FL,'FinancialLog.delete.action','ids','logID'); 
					}		
				},'-',
				{
					text : "查看明细",
					iconCls: 'commonDetail',
					handler: function(){
						GRID_EDITDETAIL(_grid_FL,onDetailFinanceLog,'logID');
					}
				},'-',
				{
					xtype: 'tbfill'
				},
				{
					xtype: 'label',
					text: '操作账号:'
				},
				{
					xtype: 'tbspacer'
				},
				{
					xtype: 'textfield',
					width: DATAFIELDWIDTH,
					id: 'id_conUser_FL',
					enableKeyEvents: true,
					listeners: { keyup: KEYDOWN_LENGTH_VLIDATOR }
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'label',
					text: '记录日期:'
				},
				{
					xtype: 'tbspacer'
				},
				{
					xtype: 'datefield',
					id: 'id_conStartDate_FL',
					format: 'Y-m-d',
					width:　DATAFIELDWIDTH,
					editable: false,
					enableKeyEvents: true,
					listeners: { keyup: KEYDOWN_DATE_VALIDATOR }
				},
				{
					xtype: 'tbspacer',
					width: 5
				},
				{
					xtype: 'label',
					text: '至'
				},
				{
					xtype: 'tbspacer',
					width: 5
				},
				{
					xtype: 'datefield',
					id: 'id_conEndDate_FL',
					format: 'Y-m-d',
					width:　DATAFIELDWIDTH,
					editable: false,
					enableKeyEvents: true,
					listeners: { keyup: KEYDOWN_DATE_VALIDATOR }
				},
				{
					xtype: 'tbspacer',
					width: 10
				},'-',
				{
					text : "查询",
					iconCls: 'commonQuery',
					handler: function(){
						_ds_FL.baseParams.start = 0;
						setQeuryCondition_FL();
						_ds_FL.load({
							callback: function(record,option,success){
								STORE_CALLBACK(_ds_FL.reader.message,_ds_FL.reader.valid,success);
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
						pageSize : _limit_FL,
						store : _ds_FL,
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
						       value: _limit_FL,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_FL.baseParams.limit = combo.getValue();
							        	_grid_FL.getBottomToolbar().pageSize = combo.getValue();
							        	setQeuryCondition_FL();
							        	_grid_FL.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
					})

		});

function setQeuryCondition_FL()
{
	_ds_FL.baseParams['user'] = _grid_FL.getTopToolbar().findById('id_conUser_FL').getValue();
	_ds_FL.baseParams.startDate = _grid_FL.getTopToolbar().findById('id_conStartDate_FL').getValue();
	_ds_FL.baseParams.endDate = _grid_FL.getTopToolbar().findById('id_conEndDate_FL').getValue();
}

_grid_FL.on('rowcontextmenu', onRightClick_FL);

var _rightClickMenu_FL = new Ext.menu.Menu({
			
			items : [{
						handler: function(){GRID_DELETE(_grid_FL,'FinancialLog.delete.action','ids','logID'); },
						text : '删除',
						iconCls: 'commonDelete'
					},{
						handler: function(){GRID_EDITDETAIL(_grid_FL,onDetailFinanceLog,'logID');},
						text : '明细',
						iconCls: 'commonDetail'
					}]
		});

function onRightClick_FL(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_FL);
}