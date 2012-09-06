function update_manageEnterStorageSheet(contentPanel,node)
{
    
}
function init_manageEnterStorageSheet(id)
{
	var page = Ext.getCmp(id);
	page.add(_formPanel_MESS);
	page.add(_grid_MESS);
	STORE_COSTCENTER_LOAD();
	STORE_SELLCENTER_LOAD();
	STORE_STORAGE_LOAD();
	_ds_MESS.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_MESS.reader.message,_ds_MESS.reader.valid,success);
		}
	});
}

var _limit_MESS = 30;

var _formPanel_MESS = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_MESS,_ds_MESS))
					return;
				_ds_MESS.baseParams.start = 0;
				_ds_MESS.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_MESS.reader.message,_ds_MESS.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function(){ _formPanel_MESS.getForm().reset();}
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
						fieldLabel: '入库单号',
						name: 'simdto.stockinManifestID',
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
					       hiddenName : 'simdto.costCenter',
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
					       hiddenName : 'simdto.auditState',
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
						fieldLabel: '入库日期',
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
					       hiddenName : 'simdto.warehouseName',
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
				},{//Column 2
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
					       hiddenName : 'simdto.sellCenter',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : true,
					       fieldLabel : '销售中心',
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
					       hiddenName : 'simdto.approvalState',
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
						name: 'simdto.customer',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				}]
			}]
	});
	var _ds_MESS = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'StockinManifest.queryOnCondition.action'
				}),
		reader : new Self.JsonReader({
					totalProperty : 'qualifiedAmount',
					root : 'resultMapList'
				}, 
				[{
					name : 'stockinManifestID'
				}, {
					name : 'dateCreated'
				}, {
					name : 'dateStockin'
				}, {
					name : "warehouseName"
				}, {
					name : 'customer'
				}, {
					name : 'deliveryPerson'
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
				limit : _limit_MESS
			}
	});

	var _sm_MESS = new Ext.grid.CheckboxSelectionModel();
	
	var _cm_MESS = new Ext.grid.ColumnModel({
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_MESS,
			{
				dataIndex : 'stockinManifestID',
				header : '入库单号'	
			},{
				header : '建单日期',
				dataIndex : 'dateCreated'
			},{
				header : '入库日期',
				dataIndex : 'dateStockin'
			},{
				dataIndex : 'auditState',
				header : '审核状态'	
			},{
				dataIndex : 'approvalState',
				header : '批准状态'	
			},{
				header : '库存地',
				dataIndex : 'warehouseName'
			},{
				dataIndex : 'customer',
				header : '客户名称'
			},{
				dataIndex : 'deliveryPerson',
				header : '送货人'	
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
	
	var _grid_MESS = new Ext.grid.GridPanel({
		region: 'center',
		sm : _sm_MESS,
		cm : _cm_MESS,
		ds : _ds_MESS,
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
				handler: onCreateEnterStorageSheet
			},
			{
				text : "修改",
				iconCls: 'commonEdit',
				handler: function(){
					GRID_EDITDETAIL(_grid_MESS,onEditEnterStorageSheet,'stockinManifestID');
				}
			}, 
			{
				text : "删除",
				iconCls: 'commonDelete',
				handler: function(){
					GRID_DELETE(_grid_MESS,'StockinManifest.delete.action','simIDList','stockinManifestID');
				}	
			},'-',
			{
				text : "查看明细",
				iconCls: 'commonDetail',
				handler: function(){
					GRID_EDITDETAIL(_grid_MESS,onDetailEnterStorageSheet,'stockinManifestID');
				}
			},'-',
			{
				text : "打印",
				iconCls: 'commonPrint',
				handler: function(){GRID_PRINT(_grid_MESS,'StockinManifest.print.action','stockinManifestID')}
			},
			{
				xtype: 'tbfill'
			},'-',
			{
				text : "审核",
				iconCls: 'commonCheck',
				handler: function()
				{
					GRID_VERIFY(_grid_MESS,'StockinManifest.audit.action','simIDList','stockinManifestID');
				}
			},'-',
			{
				text : "批准",
				iconCls: 'commonAllow',
				handler: function()
				{
					GRID_AUTHORIZE(_grid_MESS,'StockinManifest.approve.action','simIDList','stockinManifestID');
				}
			},
			'-',
//			{
//				text : "导出数据",
//				iconCls: 'commonExport'
//			},
			{
				xtype: 'tbspacer'
			}]
		}),
		bbar : new Ext.PagingToolbar({
			pageSize : _limit_MESS,
			store : _ds_MESS,
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
			       value: _limit_MESS,
			       width:　DATAFIELDWIDTH,
				   listeners : {
				    	select : function(combo, record, index) {
				        	_ds_MESS.baseParams.limit = combo.getValue();
				        	_grid_MESS.getBottomToolbar().pageSize = combo.getValue();
				        	SETFORMPARAMTOSTORE(_formPanel_MESS,_ds_MESS);
				        	_grid_MESS.getBottomToolbar().changePage(1);
				   		}//select
				   }
			})]
	})
});

_grid_MESS.on('rowcontextmenu', onRightClick_MESS);
var _rightClickMenu_MESS = new Ext.menu.Menu({
			
	items : [{
				handler: function(){GRID_EDITDETAIL(_grid_MESS,onDetailEnterStorageSheet,'stockinManifestID');},
				text : '明细',
				iconCls: 'commonDetail'
			},{
				handler: function(){GRID_EDITDETAIL(_grid_MESS,onEditEnterStorageSheet,'stockinManifestID');},
				text : '修改',
				iconCls: 'commonEdit'
			}, {
				handler: function(){
					GRID_DELETE(_grid_MESS,'StockinManifest.delete.action','simIDList','stockinManifestID');
				},
				text : '删除',
				iconCls: 'commonDelete'
			}, {
				handler: function(){GRID_PRINT(_grid_MESS,'StockinManifest.print.action','stockinManifestID')},
				text : '打印',
				iconCls: 'commonPrint'
			}]
});

function onRightClick_MESS(grid,index,e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_MESS);
}
	