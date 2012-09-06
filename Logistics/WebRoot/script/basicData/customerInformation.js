
function update_customerInformation(contentPanel,node)
{
    
}

function init_customerInformation(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_CCI);
	_ds_CCI.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_CCI.reader.message,_ds_CCI.reader.valid,success);
		}
	});
}

var _limit_CCI = 30;

var _ds_CCI = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'Customer.queryOnCondition.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'resultMapList'
			}, 
			[{
				name : 'customerID'
			}, {
				name : 'name'
			}, {
				name : 'type'
			}, {
				name : "contact"
			}, {
				name : 'phone'
			}, {
				name : 'email'
			}, {
				name : 'address'
			}, {
				name : 'remarks'
			}]
		),
	baseParams:{
		'customerDTO.customerID': '',
		'customerDTO.name': '',
		start: 0,
		limit : _limit_CCI
	}
});

var _sm_CCI = new Ext.grid.CheckboxSelectionModel();
var _cm_CCI = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
			_sm_CCI,
		{
			header : '客户编号',
			dataIndex : 'customerID'
		},{
			header : '客户名称',
			dataIndex : 'name'
		},{
			dataIndex : 'type',
			header : '客户类型'
		},{
			dataIndex : 'contact',
			header : '联系人'	
		},{
			dataIndex : 'phone',
			header : '联系电话'
		},{
			dataIndex : 'email',
			header : '邮件'
		},{
			dataIndex : 'address',
			header : '地址'
		}],
		
		defaults: {
			sortable: true,
			menuDisabled: true
		}
});

var _grid_CCI = new Ext.grid.GridPanel({
	
			region: 'center',
			sm : _sm_CCI,
			cm : _cm_CCI,
			ds : _ds_CCI,
			style: 'marCCIn:0',
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
					handler: onCreateCustomerInformation
				},
				{
					text : "修改",
					iconCls: 'commonEdit',
					handler: function(){
						GRID_EDITDETAIL(_grid_CCI,onEditCustomerInformation,'customerID');
					}
				},
				{
					text : "删除",
					iconCls: 'commonDelete',
					handler: function(){ GRID_DELETE(_grid_CCI,'Customer.delete.action','customerIDList','customerID'); }
				},'-',
				{
					text : "查看明细",
					iconCls: 'commonDetail',
					handler: function(){
						GRID_EDITDETAIL(_grid_CCI,onDetailCustomerInformation,'customerID');
					}
				},'-',
				{
					text : "运输报价维护",
					iconCls: 'transportationWay',
					handler: function(){
						var mindex=['customerID','name'];
						GRID_M_EDITDETAIL(_grid_CCI,onEditTransWayFee,mindex);
					}
				},
				{
					xtype: 'tbfill'
				},
				{
					xtype: 'label',
					text: '客户编号:'
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'textfield',
					id: 'conCustomerID_CCI',
					width:　TEXTFIELDWIDTH,
					enableKeyEvents: true,
					listeners: {
						keyup: function(the,e){ KEYDOWN_NO_VLIDATOR(the,e,11); }
					}
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'label',
					text: '客户名称:'
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'textfield',
					id: 'conCustomerName_CCI',
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
						_ds_CCI.baseParams.start = 0;
						setQeuryCondition_CCI();
						_ds_CCI.load({
							callback: function(record,option,success){
								STORE_CALLBACK(_ds_CCI.reader.message,_ds_CCI.reader.valid,success);
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
						pageSize : _limit_CCI,
						store : _ds_CCI,
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
						       value: _limit_CCI,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_CCI.baseParams.limit = combo.getValue();
							        	_grid_CCI.getBottomToolbar().pageSize = combo.getValue();
							        	setQeuryCondition_CCI();
							        	_grid_CCI.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
					})

		});

function setQeuryCondition_CCI()
{
	_ds_CCI.baseParams['customerDTO.customerID'] = _grid_CCI.getTopToolbar().findById('conCustomerID_CCI').getValue();
	_ds_CCI.baseParams['customerDTO.name'] = _grid_CCI.getTopToolbar().findById('conCustomerName_CCI').getValue();
}

_grid_CCI.on('rowcontextmenu', onRightClick_CCI);

var _rightClickMenu_CCI = new Ext.menu.Menu({
			
			items : [{
						handler: function(){ GRID_EDITDETAIL(_grid_CCI,onDetailCustomerInformation,'customerID'); },
						text : '明细',
						iconCls: 'commonDetail'
					},{
						handler :function(){ GRID_EDITDETAIL(_grid_CCI,onEditCustomerInformation,'customerID'); },
						text : '修改',
						iconCls: 'commonEdit'
					}, {
						handler: function(){ GRID_DELETE(_grid_CCI,'Customer.delete.action','customerIDList','customerID'); },
						text : '删除',
						iconCls: 'commonDelete'
					}]
		});

function onRightClick_CCI(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_CCI);
}