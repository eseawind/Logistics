
function update_cityInformation(contentPanel,node)
{
    
}

function init_cityInformation(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_CTI);
	_ds_CTI.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_CTI.reader.message,_ds_CTI.reader.valid,success);
		}
	});
}

var _limit_CTI = 30;

var _ds_CTI = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'City.queryCity.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'resultMapList'
			}, 
			[{
				name : 'CityID'
			}, {
				name : 'Name'
			},  {
				name : 'Province'
			}]
		),
	baseParams:{
		'cdto.cityID':'',
		'cdto.province':'',
		start: 0,
		limit : _limit_CTI
	}
});

var _sm_CTI = new Ext.grid.CheckboxSelectionModel();
var _cm_CTI = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
			_sm_CTI,
		{
			header : '城市编号',
			dataIndex : 'CityID',
			width: 80
		},{
			dataIndex : 'Name',
			header : '城市名称'
			
		},{
			dataIndex : 'Province',
			header : '所在省'	
			
		}],
		
		defaults: {
			sortable: true,
			menuDisabled: true
		}
});

var _grid_CTI = new Ext.grid.GridPanel({
	
			region: 'center',
			sm : _sm_CTI,
			cm : _cm_CTI,
			ds : _ds_CTI,
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
					handler: onCreateCityInformation
				},
				{
					text : "修改",
					iconCls: 'commonEdit',
					handler: function(){
						GRID_EDITDETAIL(_grid_CTI,onEditCityInformation,'CityID');
					}
				},
				{
					text : "删除",
					iconCls: 'commonDelete',
					handler: function(){ GRID_DELETE(_grid_CTI,'City.deleteCity.action','cityIDList','CityID'); }
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
					text: '城市编号:'
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'textfield',
					width:　TEXTFIELDWIDTH,
					id: 'conCityId_CTI',
					width:　TEXTFIELDWIDTH,
					enableKeyEvents: true,
					listeners: { keyup: KEYDOWN_LENGTH_VLIDATOR }
				},
				{
					xtype: 'tbspacer',
					width: 10
				},{
					xtype: 'label',
					text: '所在省份:'
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : STORE_PROVINCE,
				       valueField : 'province',
				       displayField : 'display',
				       mode : 'local',
				       forceSelection : true,
				       id: 'conProvince_CTI',
				       selectOnFocus:true,
				       typeAhead: false,
				       editable : true,
				       triggerAction : 'all',
				       width:　TEXTFIELDWIDTH,
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
						_ds_CTI.baseParams.start = 0;
						setQeuryCondition_CTI();
						_ds_CTI.load({
							callback: function(record,option,success){
								STORE_CALLBACK(_ds_CTI.reader.message,_ds_CTI.reader.valid,success);
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
						pageSize : _limit_CTI,
						store : _ds_CTI,
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
						       value: _limit_CTI,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_CTI.baseParams.limit = combo.getValue();
							        	_grid_CTI.getBottomToolbar().pageSize = combo.getValue();
							        	_grid_CTI.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
					})

		});

function setQeuryCondition_CTI()
{
	_ds_CTI.baseParams['cdto.cityID'] = _grid_CTI.getTopToolbar().findById('conCityId_CTI').getValue();
	_ds_CTI.baseParams['cdto.province'] = _grid_CTI.getTopToolbar().findById('conProvince_CTI').getValue();
}
		
_grid_CTI.on('rowcontextmenu', onRightClick_CTI);

var _rightClickMenu_CTI = new Ext.menu.Menu({
			
			items : [{
						handler : function(){
							GRID_EDITDETAIL(_grid_CTI,onEditCityInformation,'CityID');
						},
						text : '修改',
						iconCls: 'commonEdit'
					}, {
						handler: function(){
							GRID_DELETE(_grid_CTI,'City.deleteCity.action','cityIDList','CityID');
						},
						text : '删除',
						iconCls: 'commonDelete'
					}]
		});

		
function onRightClick_CTI(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_CTI);
}