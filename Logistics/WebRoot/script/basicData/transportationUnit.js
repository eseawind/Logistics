
function update_transportationUnit(contentPanel,node)
{
    
}

function init_transportationUnit(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_TU);
	STORE_TRANS_UNIT_LOAD();
	_ds_TU.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_TU.reader.message,_ds_TU.reader.valid,success);
		}
	});
}

var _limit_TU = 30;
var _ds_TU = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'FreightContractor.queryOnCondition.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'resultMapList'
			}, 
			[{
				name : 'freightContractorID'
			}, {
				name : 'name'
			}, {
				name : 'contact'
			}, {
				name : "phone"
			}, {
				name : 'email'
			}, {
				name : 'address'
			}]
		),
	baseParams:{
		'freightContractorDTO.freightContractorID': '',
		start: 0,
		limit : _limit_TU
	}
});

var _sm_TU = new Ext.grid.CheckboxSelectionModel();
var _cm_TU = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
			_sm_TU,
		{
			header : '单位编号',
			dataIndex : 'freightContractorID',
			width: 80
		},{
			dataIndex : 'name',
			header : '单位名称',
			width: 80
			
		},{
			dataIndex : 'contact',
			header : '联系人',
			width: 80
			
		},{
			dataIndex : 'phone',
			header : '联系电话'	
			
		},{
			dataIndex : 'email',
			header : '邮件',
			width: 80
			
		},{
			dataIndex : 'address',
			header : '地址'	
			
		}],
		
		defaults: {
			sortable: true,
			menuDisabled: true
		}
});

var _grid_TU = new Ext.grid.GridPanel({
	
			region: 'center',
			sm : _sm_TU,
			cm : _cm_TU,
			ds : _ds_TU,
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
					handler: onCreateTransportationUnit
				},
				{
					text : "修改",
					iconCls: 'commonEdit',
					handler: function(){
						GRID_EDITDETAIL(_grid_TU,onEditTransportationUnit,'freightContractorID');
					}
				},
				{
					handler: function(){ GRID_DELETE(_grid_TU,'FreightContractor.delete.action','freightContractorIDList','freightContractorID'); },
					text : "删除",
					iconCls: 'commonDelete'					
				},'-',
				{
					text : "查看明细",
					iconCls: 'commonDetail',
					handler: function(){
						GRID_EDITDETAIL(_grid_TU,onDetailTransportationUnit,'freightContractorID');
					}
				},'-',
				{
					text : "路线报价维护",
					iconCls: 'transportationWay',
					handler: function(){
						var mindex=['freightContractorID','name'];
						GRID_M_EDITDETAIL(_grid_TU,onEditUnitWayFee,mindex);
					}
				},'-',
				{
					text : "车辆报价维护",
					iconCls: 'carType',
					handler: function(){
						var mindex=['freightContractorID','name'];
						GRID_M_EDITDETAIL(_grid_TU,onEditUnitCarFee,mindex);
					}
				},
				{
					xtype: 'tbfill'
				},
				{
					xtype: 'label',
					text: '单位名称:'
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : STORE_TRANS_UNIT,
				       valueField : 'freightContractorID',
				       displayField : 'jointName',
				       mode : 'local',
				       forceSelection : true,
				       id: 'conUnit_TU',
				       selectOnFocus:true,
				       typeAhead: false,
				       editable : true,
				       triggerAction : 'all',
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
						_ds_TU.baseParams.start = 0;
						setQeuryCondition_TU();
						_ds_TU.load({
							callback: function(record,option,success){
								STORE_CALLBACK(_ds_TU.reader.message,_ds_TU.reader.valid,success);
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
						pageSize : _limit_TU,
						store : _ds_TU,
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
						       value: _limit_TU,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_TU.baseParams.limit = combo.getValue();
							        	_grid_TU.getBottomToolbar().pageSize = combo.getValue();
							        	setQeuryCondition_TU();
							        	_grid_TU.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
					})

		});

function setQeuryCondition_TU()
{
	_ds_TU.baseParams['freightContractorDTO.freightContractorID'] = _grid_TU.getTopToolbar().findById('conUnit_TU').getValue();
}

_grid_TU.on('rowcontextmenu', onRightClick_TU);

var _rightClickMenu_TU = new Ext.menu.Menu({
			
			items : [{
						handler: function(){GRID_EDITDETAIL(_grid_TU,onDetailTransportationUnit,'freightContractorID');},
						text : '明细',
						iconCls: 'commonDetail'
					},{
						handler : function() {GRID_EDITDETAIL(_grid_TU,onEditTransportationUnit,'freightContractorID');},
						text : '修改',
						iconCls: 'commonEdit'
					}, {
						handler: function(){ GRID_DELETE(_grid_TU,'FreightContractor.delete.action','freightContractorIDList','freightContractorID'); },
						text : '删除',
						iconCls: 'commonDelete'
					}]
		});

function onRightClick_TU(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_TU);
}