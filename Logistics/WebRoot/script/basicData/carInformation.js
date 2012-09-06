
function update_carInformation(contentPanel,node)
{
    
}

function init_carInformation(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_CI);

	_ds_CI.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_CI.reader.message,_ds_CI.reader.valid,success);
		}
	});
}

var _limit_CI = 30;

var _ds_CI = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'Car.queryOnCondition.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'resultMapList'
			}, 
			[{
				name : 'carID'
			}, {
				name : 'driverName'
			}, {
				name : 'ownerName'
			}, {
				name : 'carType'
			}, {
				name : 'roadWorthyCertificateNo'
			}, {
				name : 'phone'
			}, {
				name : 'freightContractor'
			}]
		),
	baseParams:{
		'carDTO.carID': '',
		start: 0,
		limit : _limit_CI
	}
});

var _sm_CI = new Ext.grid.CheckboxSelectionModel();
var _cm_CI = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
			_sm_CI,
		{
			header : '车牌号',
			dataIndex : 'carID'
		},{
			dataIndex : 'driverName',
			header : '驾驶员姓名'
			
		},{
			dataIndex : 'ownerName',
			header : '车主姓名'	
			
		},{
			dataIndex : 'carType',
			header : '车辆类型'
			
		},{
			dataIndex : 'freightContractor',
			header : '所属承运单位'	
			
		},{
			dataIndex : 'roadWorthyCertificateNo',
			header : '行驶证号',
			width: 80,
			renderer:function(value){ 
				if(value==null||value=='')
					return "<span>未登记</span>";
				else return "<span>"+value+"</span>";
			}
		},{
			dataIndex : 'phone',
			header : '联系电话',
			renderer:function(value){ 
				if(value==null||value=='')
					return "<span>未登记</span>";
				else return "<span>"+value+"</span>";
			}
		}],
		
		defaults: {
			sortable: true,
			menuDisabled: true
		}
});

var _grid_CI = new Ext.grid.GridPanel({
	
			region: 'center',
			sm : _sm_CI,
			cm : _cm_CI,
			ds : _ds_CI,
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
					handler: onCreateCarInformation
				},
				{
					text : "修改",
					iconCls: 'commonEdit',
					handler: function(){
						GRID_EDITDETAIL(_grid_CI,onEditCarInformation,'carID');
					}
				},
				{
					text : "删除",
					iconCls: 'commonDelete',
					handler: function(){ GRID_DELETE(_grid_CI,'Car.delete.action','carIDList','carID'); }
				},'-',
				{
					text : "查看明细",
					iconCls: 'commonDetail',
					handler: function(){
						GRID_EDITDETAIL(_grid_CI,onDetailCarInformation,'carID');
					}
				},
				{
					xtype: 'tbfill'
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'label',
					text: '车牌号:'
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'textfield',
					width:　TEXTFIELDWIDTH,
					id: 'conCarID_CI',
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
						_ds_CI.baseParams.start = 0;
						setQeuryCondition_CI();
						_ds_CI.load({
							callback: function(record,option,success){
								STORE_CALLBACK(_ds_CI.reader.message,_ds_CI.reader.valid,success);
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
						pageSize : _limit_CI,
						store : _ds_CI,
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
						       value: _limit_CI,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_CI.baseParams.limit = combo.getValue();
							        	_grid_CI.getBottomToolbar().pageSize = combo.getValue();
							        	setQeuryCondition_CI();
							        	_grid_CI.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
					})

		});

function setQeuryCondition_CI()
{
	_ds_CI.baseParams['carDTO.carID'] = _grid_CI.getTopToolbar().findById('conCarID_CI').getValue();
}

_grid_CI.on('rowcontextmenu', onRightClick_CI);

var _rightClickMenu_CI = new Ext.menu.Menu({
			
			items : [{
						handler: function(){ GRID_EDITDETAIL(_grid_CI,onDetailCarInformation,'carID'); },
						text : '明细',
						iconCls: 'commonDetail'
					},{
						handler :function(){ GRID_EDITDETAIL(_grid_CI,onEditCarInformation,'carID'); },
						text : '修改',
						iconCls: 'commonEdit'
					}, {
						handler: function(){ GRID_DELETE(_grid_CI,'Car.delete.action','carIDList','carID'); },
						text : '删除',
						iconCls: 'commonDelete'
					}]
		});

function onRightClick_CI(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_CI);
}