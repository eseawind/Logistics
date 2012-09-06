
function update_manageUser(contentPanel,node)
{
    
}

function init_manageUser(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_MU);
	_ds_MU.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_MU.reader.message,_ds_MU.reader.valid,success);
		}
	});
}

var _limit_MU = 30;

var _ds_MU = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'listUsers.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'userList'
			}, 
			[{
				name : 'UDateCreated'
			},{
				name : 'UID'
			},{
				name : 'Role'
			},{
				name : 'State'
			}]
		),
	baseParams: {
			uID : '',
			uState: '',
			start: 0,
			limit : _limit_MU
		}
});

var _sm_MU = new Ext.grid.CheckboxSelectionModel();
var _cm_MU = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
			_sm_MU,
		{
			header : '创建日期',
			dataIndex : 'UDateCreated',
			width: 50
		},{
			dataIndex : 'UID',
			header : '登录账号'
			
		},{
			dataIndex : 'Role',
			header : '用户角色'	
			
		},{
			dataIndex : 'State',
			header : '用户状态'	
			
		}],
		
		defaults: {
			sortable: true,
			menuDisabled: true
		}
});

var _grid_MU = new Ext.grid.GridPanel({
	
			region: 'center',
			sm : _sm_MU,
			cm : _cm_MU,
			ds : _ds_MU,
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
					iconCls: 'addUser',
					handler: onCreateUser
				},
				{
					text : "修改",
					iconCls: 'editUser',
					handler: function(){
						GRID_EDITDETAIL(_grid_MU,onEditUser,'UID');
					}
				},
				{
					text : "删除",
					iconCls: 'deleteUser',
					handler: function(){ GRID_DELETE(_grid_MU,'deleteUsers.action','uIDList','UID'); }
				},
				{
					xtype: 'tbfill'
				},
				{
					xtype: 'label',
					text: '账号状态:'
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				new Ext.form.ComboBox({
				       xtype : 'combo',
				       id: 'conUState_MU',
				       store : new Ext.data.SimpleStore({      
				              fields : ["returnValue", "displayText"],
				              data : [['', '--未选择--'],['启用', '启用'], ['禁用', '禁用']]
				       }),
				       
				       valueField : 'returnValue',
				       displayField : 'displayText',
				       mode : 'local',
				       forceSelection : true,
				       hiddenName : 'state',
				       editable : false,
				       triggerAction : 'all',
				       allowBlank : true,
				       value: '',
				       width:　DATAFIELDWIDTH
				}),
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'label',
					text: '账号:'
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					id: 'conUID_MU',
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
						_ds_MU.baseParams.start = 0;
						setQeuryCondition_MU();
						_ds_MU.load({
							callback: function(record,option,success){
								STORE_CALLBACK(_ds_MU.reader.message,_ds_MU.reader.valid,success);
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
						pageSize : _limit_MU,
						store : _ds_MU,
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
						       value: _limit_MU,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_MU.baseParams.limit = combo.getValue();
							        	_grid_MU.getBottomToolbar().pageSize = combo.getValue();
							        	setQeuryCondition_MU();
							        	_grid_MU.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
			})//bbar
});

function setQeuryCondition_MU()
{
	_ds_MU.baseParams.uID = _grid_MU.getTopToolbar().findById('conUID_MU').getValue();
	_ds_MU.baseParams.uState = _grid_MU.getTopToolbar().findById('conUState_MU').getValue();
}

_grid_MU.on('rowcontextmenu', onRightClick_MU);

var _rightClickMenu_MU = new Ext.menu.Menu({
			
			items : [{
						handler : function(){
							GRID_EDITDETAIL(_grid_MU,onEditUser,'UID');
						},
						text : '修改',
						iconCls: 'editUser'
					}, {
						handler: function(){
							GRID_DELETE(_grid_MU,'deleteUsers.action','uIDList','UID');
						},
						text : '删除',
						iconCls: 'deleteUser'
					}]
	});
		
function onRightClick_MU(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_MU);
}
