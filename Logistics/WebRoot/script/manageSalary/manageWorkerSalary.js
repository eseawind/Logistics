
function update_manageWorkerSalary(contentPanel,node)
{
    
}

function init_manageWorkerSalary(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_MWS);
	_ds_MWS.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_MWS.reader.message,_ds_MWS.reader.valid,success);
		}
	});
}

var _limit_MWS = 30;

var _ds_MWS = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'ES.queryEmployeeSalary.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'resultMapList'
			}, 
			[{
				name : 'EmployeeID'
			}, {
				name : 'ESID'
			}, {
				name : 'Name'
			}, {
				name : 'PayDay'
			}, {
				name : 'Department'
			}, {
				name : "Position"
			}, {
				name : 'TotalSalary'
			}, {
				name : 'Remarks'
			}]
		),
		baseParams: {
			'esdto.EmployeeID': '',
			'epdto.Name': '',
			startDate: '',
			endDate: '',
			start: 0,
			limit : _limit_MWS
		}
});

var _sm_MWS = new Ext.grid.CheckboxSelectionModel();
var _cm_MWS = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
			_sm_MWS,
		{
			header : '工资日期',
			dataIndex : 'PayDay',
			width: 60
		},{
			header : '工号',
			dataIndex : 'EmployeeID',
			width: 60
		},{
			dataIndex : 'Name',
			header : '姓名'
			
		},{
			dataIndex : 'Department',
			header : '部门'	
			
		},{
			dataIndex : 'Position',
			header : '职位'	
			
		},{
			dataIndex : 'TotalSalary',
			header : '工资总计'	
			
		}],
		
		defaults: {
			sortable: true,
			menuDisabled: true
		}
});

var _grid_MWS = new Ext.grid.GridPanel({
	
			region: 'center',
			sm : _sm_MWS,
			cm : _cm_MWS,
			ds : _ds_MWS,
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
					handler: onCreateWorkerSalary
				},
				{
					text : "修改",
					iconCls: 'commonEdit',
					handler: function(){
						GRID_EDITDETAIL(_grid_MWS,onEditWorkerSalary,'ESID');
					}
				},
				{
					text : "删除",
					iconCls: 'commonDelete',
					handler: function(){ 
						GRID_DELETE(_grid_MWS,'ES.delete.action','employeeSalaryIDList','ESID'); 
					}
				},'-',
				{
					text : "查看明细",
					iconCls: 'commonDetail',
					handler: function(){
						GRID_EDITDETAIL(_grid_MWS,onDetailWorkerSalary,'ESID');
					}
				},
				{
					xtype: 'tbfill'
				},
				{
					xtype: 'label',
					text: '工号:'
				},
				{
					xtype: 'tbspacer'
				},
				{
					xtype: 'textfield',
					width: DATAFIELDWIDTH,
					id: 'id_conEmployeeID_MWS',
					enableKeyEvents: true,
					listeners: {
						keyup: function(the,e){ KEYDOWN_NO_VLIDATOR(the,e,5); }
					}
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'label',
					text: '姓名:'
				},
				{
					xtype: 'tbspacer'
				},
				{
					xtype: 'textfield',
					width:　DATAFIELDWIDTH,
					id: 'id_conName_MWS',
					enableKeyEvents: true,
					listeners: { keyup: KEYDOWN_LENGTH_VLIDATOR }
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'label',
					text: '工资日期:'
				},
				{
					xtype: 'tbspacer'
				},
				{
					xtype: 'datefield',
					id: 'id_conStartSalaryDate_MWS',
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
					format: 'Y-m-d',
					id: 'id_conEndSalaryDate_MWS',
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
						_ds_MWS.baseParams.start = 0;
						setQeuryCondition_MWS();
						_ds_MWS.load({
							callback: function(record,option,success){
								STORE_CALLBACK(_ds_MWS.reader.message,_ds_MWS.reader.valid,success);
							}
						});
					}
				},'-',
				{
					text : "导出数据",
					iconCls: 'commonExport'
				},'-',
				{
					xtype: 'tbspacer',
					width: 10
				}]
			}),

			bbar : new Ext.PagingToolbar({
						pageSize : _limit_MWS,
						store : _ds_MWS,
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
						       value: _limit_MWS,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_MWS.baseParams.limit = combo.getValue();
							        	_grid_MWS.getBottomToolbar().pageSize = combo.getValue();
							        	setQeuryCondition_MWS();
							        	_grid_MWS.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
					})

		});

function setQeuryCondition_MWS()
{
	_ds_MWS.baseParams['esdto.EmployeeID'] = _grid_MWS.getTopToolbar().findById('id_conEmployeeID_MWS').getValue();
	_ds_MWS.baseParams['epdto.Name'] = _grid_MWS.getTopToolbar().findById('id_conName_MWS').getValue();
	_ds_MWS.baseParams.startDate = _grid_MWS.getTopToolbar().findById('id_conStartSalaryDate_MWS').getValue();
	_ds_MWS.baseParams.endDate = _grid_MWS.getTopToolbar().findById('id_conEndSalaryDate_MWS').getValue();
}

_grid_MWS.on('rowcontextmenu', onRightClick_MWS);

var _rightClickMenu_MWS = new Ext.menu.Menu({
			
			items : [{
						handler: function(){ GRID_EDITDETAIL(_grid_MWS,onDetailWorkerSalary,'ESID'); },
						text : '明细',
						iconCls: 'commonDetail'
					},{
						handler :function(){ GRID_EDITDETAIL(_grid_MWS,onEditWorkerSalary,'ESID'); },
						text : '修改',
						iconCls: 'commonEdit'
					}, {
						handler: function(){ 
							GRID_DELETE(_grid_MWS,'ES.delete.action','employeeSalaryIDList','ESID'); 
						},
						text : '删除',
						iconCls: 'commonDelete'
					}]
		});

function onRightClick_MWS(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_MWS);
}