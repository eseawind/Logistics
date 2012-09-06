function update_manageOutStorageSheet(contentPanel,node)
{
    
}
function init_manageOutStorageSheet(id)
{
	var page = Ext.getCmp(id);
	page.add(_formPanel_MOSS);
	page.add(_grid_MOSS);
	STORE_COSTCENTER_LOAD();
	STORE_SELLCENTER_LOAD();
	STORE_STORAGE_LOAD();
	_ds_MOSS.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_MOSS.reader.message,_ds_MOSS.reader.valid,success);
		}
	});
}
var _limit_MOSS = 30;

var _formPanel_MOSS = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_MOSS,_ds_MOSS))
					return;
				_ds_MOSS.baseParams.start = 0;
				_ds_MOSS.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_MOSS.reader.message,_ds_MOSS.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function(){ _formPanel_MOSS.getForm().reset();}
		}],
		
		items:[
			{//Row 1
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '出库单号',
						name: 'somdto.stockoutManifestID',
						regex: REGEX_COMMON_T,
						regexText: REGEX_COMMON_T_TEXT,
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 2
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_COSTCENTER,
					       valueField : 'costCenterID',
					       displayField : 'costCenterID',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: false,
					       forceSelection : true,
					       hiddenName : 'somdto.costCenter',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : true,
					       fieldLabel : '成本中心',
					       width:　TEXTFIELDWIDTH
					})]
				},
				{//Column 3
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_VERIFY,
					       valueField : 'value',
					       displayField : 'display',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'somdto.auditState',
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '审核状态',
					       value: '',
					       width:　TEXTFIELDWIDTH
					})]
				},
				{//Column 4
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'datefield',
						fieldLabel: '出库日期',
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
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_STORAGE,
					       valueField : 'name',
					       displayField : 'name',
					       mode : 'local',
					       forceSelection : false,
					       hiddenName : 'somdto.warehouseName',
					       editable : true,
					       typeAhead : false,
					       selectOnFocus:true,
					       triggerAction : 'all',
					       allowBlank : true,
					       fieldLabel : '库存地',
					       value: '',
					       width:　TEXTFIELDWIDTH,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
					       }
					})]
				},
				{//Column 2
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_SELLCENTER,
					       valueField : 'sellCenterID',
					       displayField : 'sellCenterID',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: false,
					       forceSelection : true,
					       hiddenName : 'somdto.sellCenter',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : true,
					       fieldLabel : '销售中心',
					       width:　TEXTFIELDWIDTH
					})]
				},
				{//Column 3
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_AUTHORIZE,
					       valueField : 'value',
					       displayField : 'display',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'somdto.approvalState',
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '批准状态',
					       value: '',
					       width:　TEXTFIELDWIDTH
					})]
				},
				{//Column 4
					columnWidth: '0.25',
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
			},
			{//Row 3
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '客户名称',
						name: 'somdto.customer',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				}]
			}]
	});

	var _ds_MOSS = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'StockoutManifest.queryOnCondition.action'
				}),
		reader : new Self.JsonReader({
					totalProperty : 'qualifiedAmount',
					root : 'resultMapList'
				}, 
				[{
					name : 'stockoutManifestID'
				}, {
					name : 'dateCreated'
				}, {
					name : 'dateStockout'
				}, {
					name : "warehouseName"
				}, {
					name : 'customer'
				}, {
					name : 'consignee'
				}, {
					name : 'costCenter'
				}, {
					name : 'sellCenter'
				}, {
					name : 'approvalState'
				}, {
					name : 'auditState'
				}, {
					name : 'remarks'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_MOSS
			}
	});

	var _sm_MOSS = new Ext.grid.CheckboxSelectionModel();
	var _cm_MOSS = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_MOSS,
			{
				dataIndex : 'stockoutManifestID',
				header : '出库单号'	
			},{
				header : '建单日期',
				dataIndex : 'dateCreated'
			},{
				header : '出库日期',
				dataIndex : 'dateStockout'
			},{
				dataIndex :'auditState',
				header : '审核状态'	
			},{
				dataIndex :'approvalState',
				header : '批准状态'	
			},{
				header : '库存地',
				dataIndex : 'warehouseName'
			},{
				dataIndex : 'customer',
				header : '客户名称'
			},{
				dataIndex : 'consignee',
				header : '收货人'	
			},{
				dataIndex : 'sellCenter',
				header : '销售中心'	
			},{
				dataIndex : 'costCenter',
				header : '成本中心'	
			},{
				dataIndex : 'remarks',
				header : '备注'	
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_MOSS = new Ext.grid.GridPanel({
		region: 'center',
		sm : _sm_MOSS,
		cm : _cm_MOSS,
		ds : _ds_MOSS,
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
				handler: onCreateOutStorageSheet
			},
			{
				text : "修改",
				iconCls: 'commonEdit',
				handler: function(){
					GRID_EDITDETAIL(_grid_MOSS,onEditOutStorageSheet,'stockoutManifestID');
				}
			}, 
			{
				text : "删除",
				iconCls: 'commonDelete',
				handler: function(){
					GRID_DELETE(_grid_MOSS,'StockoutManifest.delete.action','somIDList','stockoutManifestID');
				}				
			},'-',
			{
				text : "查看明细",
				iconCls: 'commonDetail',
				handler: function(){
					GRID_EDITDETAIL(_grid_MOSS,onDetailOutStorageSheet,'stockoutManifestID');
				}
			},
			{
				xtype: 'tbfill'
			},'-',
			{
				text : "审核",
				iconCls: 'commonCheck',
				handler: function()
				{
					GRID_VERIFY(_grid_MOSS,'StockoutManifest.audit.action','somIDList','stockoutManifestID');
				}
			},'-',
			{
				text : "批准",
				iconCls: 'commonAllow',
				handler: function()
				{
					GRID_AUTHORIZE(_grid_MOSS,'StockoutManifest.approve.action','somIDList','stockoutManifestID');
				}
			},
//			'-',
//			{
//				text : "导出数据",
//				iconCls: 'commonExport'
//			},
			{
				xtype: 'tbspacer'
			}]
		}),

		bbar : new Ext.PagingToolbar({
			pageSize : _limit_MOSS,
			store : _ds_MOSS,
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
			       value: _limit_MOSS,
			       width:　DATAFIELDWIDTH,
				   listeners : {
				    	select : function(combo, record, index) {
				        	_ds_MOSS.baseParams.limit = combo.getValue();
				        	_grid_MOSS.getBottomToolbar().pageSize = combo.getValue();
				        	SETFORMPARAMTOSTORE(_formPanel_MOSS,_ds_MOSS);
				        	_grid_MOSS.getBottomToolbar().changePage(1);
				   		}//select
				   }
			})]
		})
	});
	_grid_MOSS.on('rowcontextmenu', onRightClick_MOSS);
	var _rightClickMenu_MOSS = new Ext.menu.Menu({
		items : [{
					handler: function(){GRID_EDITDETAIL(_grid_MOSS,onDetailOutStorageSheet,'stockoutManifestID');},
					text : '明细',
					iconCls: 'commonDetail'
				},{
					handler: function(){GRID_EDITDETAIL(_grid_MOSS,onEditOutStorageSheet,'stockoutManifestID');},
					text : '修改',
					iconCls: 'commonEdit'
				}, {
					handler: function(){
						GRID_DELETE(_grid_MOSS,'StockoutManifest.delete.action','somIDList','stockoutManifestID');
					},
					text : '删除',
					iconCls: 'commonDelete'
				}]
	});

function onRightClick_MOSS(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_MOSS);
}
	