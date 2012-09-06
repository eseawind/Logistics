
function update_manageStocktake(contentPanel,node)
{
	
}

function init_manageStocktake(id)
{
	var page = Ext.getCmp(id);
	page.add(_formPanel_MS);
	page.add(_grid_MS);
	STORE_STORAGE_LOAD();
	_ds_MS.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_MS.reader.message,_ds_MS.reader.valid,success);
		}
	});
}

var _limit_MS = 30;

var _formPanel_MS = new Ext.FormPanel({
	
		region: 'north',
		layout: 'form',
		style: 'margin:0px',
		frame: true,
		labelAlign: 'right',
		bodyStyle: 'padding: 10px 20px 0px 0px',
		autoHeight: true,
		autoScoll: true,
		labelWidth: 60,
		border: false,
		buttonAlign: 'center',
		
		buttons:[
		{
			text: '查询',
			iconCls: 'commonQuery',
			handler: function(){
				if(!SETFORMPARAMTOSTORE(_formPanel_MS,_ds_MS))
					return;
				_ds_MS.baseParams.start = 0;
				_ds_MS.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_MS.reader.message,_ds_MS.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function(){ _formPanel_MS.getForm().reset();}
		}],
		
		items:[
			{//Row 1
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '0.33',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '盘点单号',
						name: 'Imdto.inventoryManifestID',
						regex: REGEX_COMMON_T,
						regexText: REGEX_COMMON_T_TEXT,
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 2
					columnWidth: '0.33',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_STORAGE,
					       valueField : 'warehouseID',
					       displayField : 'name',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'Imdto.warehouseID',
					       editable : true,
					       typeAhead : false,
					       triggerAction : 'all',
					       allowBlank : true,
					       selectOnFocus:true,
					       fieldLabel : '库存地',
					       value: '',
					       width:　TEXTFIELDWIDTH,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
					       }
					})]
				},
				{//Column 3
					columnWidth: '0.34',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'datefield',
						fieldLabel: '盘点日期',
						format: 'Y-m-d',
						name: 'startDate',
						width:　DATAFIELDWIDTH,
						editable: false,
						enableKeyEvents: true,
						listeners: { keyup: KEYDOWN_DATE_VALIDATOR }
					}]
				}]
			},
			{//Row 2
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '0.33',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '盘点人',
						name: 'Imdto.inventoryPerson',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 2
					columnWidth: '0.33',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_STOCKTAKETYPE,
					       valueField : 'value',
					       displayField : 'display',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'Imdto.type',
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '盘点类型',
					       value: '',
					       width:　TEXTFIELDWIDTH
					})]
				},
				{//Column 3
					columnWidth: '0.34',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'datefield',
						fieldLabel: '至',
						format: 'Y-m-d',
						name: 'endDate',
						width:　DATAFIELDWIDTH,
						editable: false,
						enableKeyEvents: true,
						listeners: { keyup: KEYDOWN_DATE_VALIDATOR }
					}]
				}]
			}]
	});

	var _ds_MS = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'InventoryManifest.queryOnCondition.action'
				}),
		reader : new Self.JsonReader({
					totalProperty : 'qualifiedAmount',
					root : 'resultMapList'
				}, 
				[{
					name : 'inventoryManifestID'
				}, {
					name : 'dateInventoried'
				}, {
					name : 'type'
				}, {
					name : "warehouse"
				}, {
					name : 'inventoryPerson'
				}, {
					name : 'result'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_MTSS
			}
	});
	var _sm_MS = new Ext.grid.CheckboxSelectionModel();
	var _cm_MS = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_MS,
			{
				dataIndex : 'inventoryManifestID',
				header : '盘点单号',
				width: 30
			},{
				header : '盘点日期',
				width: 30,
				dataIndex : 'dateInventoried'
			},{
				dataIndex : 'type',
				header : '盘点类型',
				width: 30
			},{
				dataIndex : 'warehouse',
				header : '库存地',
				width: 30
			},{
				dataIndex : 'inventoryPerson',
				header : '盘点人',
				width: 30
			},{
				dataIndex : 'result',
				header : '盘点概况'	
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_MS = new Ext.grid.GridPanel({
		
				region: 'center',
				sm : _sm_MS,
				cm : _cm_MS,
				ds : _ds_MS,
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
						handler: onCreateStocktake
					},
					{
						text : "修改",
						iconCls: 'commonEdit',
						handler: function(){
							GRID_EDITDETAIL(_grid_MS,onEditStocktake,'inventoryManifestID');
						}
					}, 
					{
						text : "删除",
						iconCls: 'commonDelete',
						handler: function(){
							GRID_DELETE(_grid_MS,'InventoryManifest.delete.action','imIDList','inventoryManifestID');
						}
					},'-',
					{
						text : "查看明细",
						iconCls: 'commonDetail',
						handler: function(){
							GRID_EDITDETAIL(_grid_MS,onDetailStocktake,'inventoryManifestID');
						}
					},
//					'-',
//					{
//						text : "打印",
//						iconCls: 'commonPrint'
//					},
					{
						xtype: 'tbfill'
					},
//					{
//						text : "导出数据",
//						iconCls: 'commonExport'
//					},
					{
						xtype: 'tbspacer'
					}]
				}),
				bbar : new Ext.PagingToolbar({
					pageSize : _limit_MS,
					store : _ds_MS,
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
					       value: _limit_MS,
					       width:　DATAFIELDWIDTH,
						   listeners : {
						    	select : function(combo, record, index) {
						        	_ds_MS.baseParams.limit = combo.getValue();
						        	_grid_MS.getBottomToolbar().pageSize = combo.getValue();
						        	SETFORMPARAMTOSTORE(_formPanel_MS,_ds_MS);
						        	_grid_MS.getBottomToolbar().changePage(1);
						   		}//select
						   }
					})]
				})

			});

	_grid_MS.on('rowcontextmenu', onRightClick_MS);
	
	var _rightClickMenu_MS = new Ext.menu.Menu({
				
				items : [{
							handler: function(){
								GRID_EDITDETAIL(_grid_MS,onDetailStocktake,'inventoryManifestID');
							},
							text : '明细',
							iconCls: 'commonDetail'
						},{
							handler: function(){
								GRID_EDITDETAIL(_grid_MS,onEditStocktake,'inventoryManifestID');
							},
							text : '修改',
							iconCls: 'commonEdit'
						}, {
							handler: function(){
								GRID_DELETE(_grid_MS,'InventoryManifest.delete.action','imIDList','inventoryManifestID');
							},
							text : '删除',
							iconCls: 'commonDelete'
						}]
			});

	function onRightClick_MS(grid, rowindex, e) {
		ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_MS);
	}
