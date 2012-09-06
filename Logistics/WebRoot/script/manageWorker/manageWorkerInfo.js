
function update_manageWorkerInfo(contentPanel,node)
{
    
}

function init_manageWorkerInfo(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_MWI);
	_ds_MWI.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_MWI.reader.message,_ds_MWI.reader.valid,success);
		}
	});
}

var _limit_MWI = 30;

var _ds_MWI = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'queryEmployee.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'employeeList'
			}, 
			[{
				name : 'EmployeeID'
			}, {
				name : 'Name'
			}, {
				name : 'Department'
			}, {
				name : 'Position'
			}, {
				name : 'CellPhone'
			},{
				name: 'Email'
			},{
				name: 'IDCard'
			},{
				name: 'Remarks'
			}]
		),
		baseParams: {
			EmployeeID : '',
			Name: '',
			Department: '',
			Position: '',
			start: 0,
			limit : _limit_MWI
		}
});

var _sm_MWI = new Ext.grid.CheckboxSelectionModel();
var _cm_MWI = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
			_sm_MWI,
		{
			header : '工号',
			dataIndex : 'EmployeeID'
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
			dataIndex : 'CellPhone',
			header : '电话'	
			
		},{
			dataIndex : 'Email',
			header : '电子邮箱'	
			
		},{
			dataIndex : 'IDCard',
			header : '身份证号码'	
			
		}],
		
		defaults: {
			sortable: true,
			menuDisabled: true
		}
});

var _grid_MWI = new Ext.grid.GridPanel({
	
			region: 'center',
			sm : _sm_MWI,
			cm : _cm_MWI,
			ds : _ds_MWI,
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
					handler: onCreateWorkerInfo
				},
				{
					text : "修改",
					iconCls: 'commonEdit',
					handler: function(){
						GRID_EDITDETAIL(_grid_MWI,onEditWorkerInfo,'EmployeeID');
					}
				},
				{
					text : "删除",
					iconCls: 'commonDelete',
					handler: function(){ 
						GRID_DELETE(_grid_MWI,'deleteEmployees.action','EmployeeIDList','EmployeeID'); 
					}
				},'-',
				{
					text : "查看明细",
					iconCls: 'commonDetail',
					handler: function(){
						GRID_EDITDETAIL(_grid_MWI,onDetailWorkerInfo,'EmployeeID');
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
					id: 'conEmployeeID_MWI',
					xtype: 'textfield',
					width: DATAFIELDWIDTH,
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
					id: 'conName_MWI',
					xtype: 'textfield',
					width:　DATAFIELDWIDTH,
					enableKeyEvents: true,
					listeners: { keyup: KEYDOWN_LENGTH_VLIDATOR }
				},
				{
					xtype: 'tbspacer',
					width: 5
				},
				{
					xtype: 'label',
					text: '部门:'
				},
				{
					xtype: 'tbspacer'
				},
				{
					id: 'conDepartment_MWI',
					xtype: 'textfield',
					width:　DATAFIELDWIDTH,
					enableKeyEvents: true,
					listeners: { keyup: KEYDOWN_LENGTH_VLIDATOR }
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'label',
					text: '职位:'
				},
				{
					xtype: 'tbspacer'
				},
				{
					id: 'conPosition_MWI',
					xtype: 'textfield',
					width:　DATAFIELDWIDTH,
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
						_ds_MWI.baseParams.start = 0;
						setQeuryCondition_MWI();
						_ds_MWI.load({
							callback: function(record,option,success){
								STORE_CALLBACK(_ds_MWI.reader.message,_ds_MWI.reader.valid,success);
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
						pageSize : _limit_MWI,
						store : _ds_MWI,
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
						       value: _limit_MWI,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_MWI.baseParams.limit = combo.getValue();
							        	_grid_MWI.getBottomToolbar().pageSize = combo.getValue();
							        	setQeuryCondition_MWI();
							        	_grid_MWI.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
					})

		});

function setQeuryCondition_MWI()
{
	_ds_MWI.baseParams.EmployeeID = _grid_MWI.getTopToolbar().findById('conEmployeeID_MWI').getValue();
	_ds_MWI.baseParams.Name = _grid_MWI.getTopToolbar().findById('conName_MWI').getValue();
	_ds_MWI.baseParams.Department = _grid_MWI.getTopToolbar().findById('conDepartment_MWI').getValue();
	_ds_MWI.baseParams.Position = _grid_MWI.getTopToolbar().findById('conPosition_MWI').getValue();
}
		
_grid_MWI.on('rowcontextmenu', onRightClick_MWI);

var _rightClickMenu_MWI = new Ext.menu.Menu({
			
			items : [{
						handler: function(){GRID_EDITDETAIL(_grid_MWI,onDetailWorkerInfo,'EmployeeID');},
						text : '明细',
						iconCls: 'commonDetail'
					},{
						handler :function(){GRID_EDITDETAIL(_grid_MWI,onEditWorkerInfo,'EmployeeID'); },
						text : '修改',
						iconCls: 'commonEdit'
					}, {
						handler: function(){ 
							GRID_DELETE(_grid_MWI,'deleteEmployees.action','EmployeeIDList','EmployeeID'); 
						},
						text : '删除',
						iconCls: 'commonDelete'
					}]
		});

function onRightClick_MWI(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_MWI);
}