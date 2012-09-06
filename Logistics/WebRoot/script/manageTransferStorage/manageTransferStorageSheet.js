function update_manageTransferStorageSheet(contentPanel,node)
{
    
}
function init_manageTransferStorageSheet(id)
{
	var page = Ext.getCmp(id);
	page.add(_formPanel_MTSS);
	page.add(_grid_MTSS);
	STORE_STORAGE_LOAD();
    STORE_COSTCENTER_LOAD();
	_ds_MTSS.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_MTSS.reader.message,_ds_MTSS.reader.valid,success);
		}
	});
}
var _limit_MTSS = 30;

var _formPanel_MTSS = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_MTSS,_ds_MTSS))
					return;
				_ds_MTSS.baseParams.start = 0;
				_ds_MTSS.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_MTSS.reader.message,_ds_MTSS.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function(){ _formPanel_MTSS.getForm().reset();}
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
						fieldLabel: '移库单号',
						name: 'stmdto.stockTransferManifestID',
						regex: REGEX_COMMON_T,
						regexText: REGEX_COMMON_T_TEXT,
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 2
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_STORAGE,
					       valueField : 'name',
					       displayField : 'name',
					       mode : 'local',
					       forceSelection : false,
					       hiddenName : 'stmdto.outWarehouse',
					       editable : true,
					       typeAhead : false,
					       triggerAction : 'all',
					       allowBlank : true,
					       selectOnFocus:true,
					       fieldLabel : '调出仓库',
					       value: '',
					       width:　TEXTFIELDWIDTH,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
					       }
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
					       hiddenName : 'stmdto.auditState',
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
					items: [
					{
						xtype: 'datefield',
						fieldLabel: '移库日期',
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
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_COSTCENTER,
					       valueField : 'costCenterID',
					       displayField : 'costCenterID',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: false,
					       forceSelection : true,
					       hiddenName : 'stmdto.costCenter',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : true,
					       fieldLabel : '成本中心',
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
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_STORAGE,
					       valueField : 'name',
					       displayField : 'name',
					       mode : 'local',
					       forceSelection : false,
					       hiddenName : 'stmdto.inWarehouse',
					       editable : true,
					       typeAhead : false,
					       triggerAction : 'all',
					       allowBlank : true,
					       selectOnFocus:true,
					       fieldLabel : '调入仓库',
					       value: '',
					       width:　TEXTFIELDWIDTH,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
					       }
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
					       hiddenName : 'stmdto.approvalState',
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
			}]
	});

	var _ds_MTSS = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'StockTransferManifest.queryOnCondition.action'
				}),
		reader : new Self.JsonReader({
					totalProperty : 'qualifiedAmount',
					root : 'resultMapList'
				}, 
				[{
					name : 'stockTransferManifestID'
				}, {
					name : 'dateCreated'
				}, {
					name : 'dateTransfered'
				}, {
					name : "outWarehouse"
				}, {
					name : 'inWarehouse'
				}, {
					name : 'operator'
				}, {
					name : 'costCenter'
				}, {
					name : 'auditState'
				}, {
					name : 'approvalState'
				}, {
					name : 'remarks'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_MTSS
			}
	});
	var _sm_MTSS = new Ext.grid.CheckboxSelectionModel();
	var _cm_MTSS = new Ext.grid.ColumnModel({
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_MTSS,
			{
				dataIndex : 'stockTransferManifestID',
				header : '移库单号'	
			},{
				header : '建单日期',
				dataIndex : 'dateCreated'
			},{
				header : '移库日期',
				dataIndex : 'dateTransfered'
			},{
				dataIndex : 'auditState',
				header : '审核状态'	
			},{
				dataIndex : 'approvalState',
				header : '批准状态'	
			},{
				header : '调出仓库',
				dataIndex : 'outWarehouse'
			},{
				dataIndex : 'inWarehouse',
				header : '调入仓库'
			},{
				dataIndex : 'operator',
				header : '操作员'	
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
	
	var _grid_MTSS = new Ext.grid.GridPanel({
				region: 'center',
				sm : _sm_MTSS,
				cm : _cm_MTSS,
				ds : _ds_MTSS,
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
						handler: onCreateTransferStorageSheet
					},
					{
						text : "修改",
						iconCls: 'commonEdit',
						handler: function(){
							GRID_EDITDETAIL(_grid_MTSS,onEditTransferStorageSheet,'stockTransferManifestID');
						}
					}, 
					{
						text : "删除",
						iconCls: 'commonDelete',
						handler: function(){
							GRID_DELETE(_grid_MTSS,'StockTransferManifest.delete.action','stmIDList','stockTransferManifestID');
						}
					},'-',
					{
						text : "查看明细",
						iconCls: 'commonDetail',
						handler: function(){
							GRID_EDITDETAIL(_grid_MTSS,onDetailTransferStorageSheet,'stockTransferManifestID');
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
							GRID_VERIFY(_grid_MTSS,'StockTransferManifest.audit.action','stmIDList','stockTransferManifestID');
						}
					},'-',
					{
						text : "批准",
						iconCls: 'commonAllow',
						handler: function()
						{
							GRID_AUTHORIZE(_grid_MTSS,'StockTransferManifest.approve.action','stmIDList','stockTransferManifestID');
						}
					},
//					'-',
//					{
//						text : "导出数据",
//						iconCls: 'commonExport'
//					},
					{
						xtype: 'tbspacer'
					}]
				}),
				bbar : new Ext.PagingToolbar({
					pageSize : _limit_MTSS,
					store : _ds_MTSS,
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
					       value: _limit_MTSS,
					       width:　DATAFIELDWIDTH,
						   listeners : {
						    	select : function(combo, record, index) {
						        	_ds_MTSS.baseParams.limit = combo.getValue();
						        	_grid_MTSS.getBottomToolbar().pageSize = combo.getValue();
						        	SETFORMPARAMTOSTORE(_formPanel_MTSS,_ds_MTSS);
						        	_grid_MTSS.getBottomToolbar().changePage(1);
						   		}//select
						   }
					})]
				})

	});

	_grid_MTSS.on('rowcontextmenu', onRightClick_MTSS);
	var _rightClickMenu_MTSS = new Ext.menu.Menu({
				
	items : [{
				handler : function(){
					GRID_EDITDETAIL(_grid_MTSS,onDetailTransferStorageSheet,'stockTransferManifestID');
				},
				text : '明细',
				iconCls: 'commonDetail'
			},{
				handler : function(){
					GRID_EDITDETAIL(_grid_MTSS,onEditTransferStorageSheet,'stockTransferManifestID');
				},
				text : '修改',
				iconCls: 'commonEdit'
			}, {
				handler: function(){
					GRID_DELETE(_grid_MTSS,'StockTransferManifest.delete.action','stmIDList','stockTransferManifestID');
				},
				text : '删除',
				iconCls: 'commonDelete'
			}]
});

function onRightClick_MTSS(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_MTSS);
}
	
	
