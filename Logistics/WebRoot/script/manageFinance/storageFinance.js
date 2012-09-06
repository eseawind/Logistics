
function update_storageFinance(contentPanel,node)
{
    
}

function init_storageFinance(id)
{
	var page = Ext.getCmp(id);
	page.add(_formPanel_SF);
	page.add(_grid_SF);
	STORE_SELLCENTER_LOAD();
    STORE_STORAGE_LOAD();
	_ds_SF.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_SF.reader.message,_ds_SF.reader.valid,success);
		}
	});
}

var _limit_SF = 30;

var _formPanel_SF = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_SF,_ds_SF))
					return;
				_ds_SF.baseParams.start = 0;
				_grid_SF.getTopToolbar().findById('btn_listsave_SF').enable();
				_grid_SF.getTopToolbar().findById('btn_archive_SF').enable();
				if(_formPanel_SF.findById('con_financialState_SF').getValue()=='已归档'){
					_grid_SF.getTopToolbar().findById('btn_listsave_SF').disable();
					_grid_SF.getTopToolbar().findById('btn_archive_SF').disable();
				}
				_ds_SF.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_SF.reader.message,_ds_SF.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function(){ _formPanel_SF.getForm().reset();}
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
						fieldLabel: '收支单号',
						name: 'income.stockIncomeID',
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
					       store : STORE_SELLCENTER,
					       valueField : 'sellCenterID',
					       displayField : 'sellCenterID',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: false,
					       forceSelection : true,
					       hiddenName : 'income.sellCenter',
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
					columnWidth: '0.34',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'datefield',
						fieldLabel: '上次结算',
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
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_STORAGE,
					       valueField : 'name',
					       displayField : 'name',
					       mode : 'local',
					       forceSelection : false,
					       hiddenName : 'income.warehouseName',
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
					columnWidth: '0.33',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '客户名称',
						name: 'income.customerName',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
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
			},
			{//Row 3
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : new Ext.data.SimpleStore({      
					              fields : ["value"],
					              data : [['未归档'], ['已归档']]
					       }),
					       valueField : 'value',
					       displayField : 'value',
					       mode : 'local',
					       id:'con_financialState_SF',
					       forceSelection : true,
					       hiddenName : 'income.financialState',
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '财务状态',
					       value: '未归档',
					       width:　TEXTFIELDWIDTH
					})]
				}]
			}]
	});

	var _ds_SF = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'StockFinance.queryOnCondition.action'
				}),
		reader : new Self.JsonReader({
					totalProperty : 'qualifiedAmount',
					root : 'resultMapList'
				}, 
				[{
					name : 'stockIncomeID'
				}, {
					name : 'dateStockin'
				}, {
					name : 'dateStockout'
				}, {
					name : "financialState"
				}, {
					name : 'sellCenter'
				}, {
					name : 'warehouse'
				}, {
					name : 'itemName'
				}, {
					name : 'itemNumber'
				}, {
					name : 'customerID'
				}, {
					name : 'customer'
				}, {
					name : 'amount'
				}, {
					name : 'weight'
				}, {
					name : 'volume'
				}, {
					name : 'chargeMode'
				}, {
					name : 'unitPrice'
				}, {
					name : 'dateAccountStart'
				}, {
					name : 'dateAccountEnd'
				}, {
					name : 'daysStock'
				}, {
					name : 'stockFee'
				}, {
					name : 'remarks'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_SF,
				'income.financialState' : '未归档'
			}
	});

	var _sm_SF = new Ext.grid.CheckboxSelectionModel();
	var _cm_SF = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_SF,
			{
				dataIndex : 'stockIncomeID',
				header : '收支单号'	
			},{
				dataIndex : 'dateStockin',
				header : '入库日期'	
			},{
				dataIndex : 'dateStockout',
				header : '出库日期',
				renderer:function(value){ 
					if(value==null||value=='')
						return "<span>未出库</span>";
					else return "<span>"+value+"</span>";
				}
			},{
				dataIndex : 'financialState',
				header : '财务状态'	
			},{
				dataIndex : 'sellCenter',
				header : '销售中心',
				renderer:function(value){ 
					if(value==null||value=='')
						return "<span>未维护</span>";
					else return "<span>"+value+"</span>";
				}
			},{
				dataIndex : 'warehouse',
				header : '库存地'
			},{
				dataIndex : 'itemName',
				header : '物料名称'
			},{
				dataIndex : 'itemNumber',
				header : '物料代码'
			},{
				dataIndex : 'customer',
				header : '客户名称'
			},{
				dataIndex : 'amount',
				header : '总数量'
			},{
				dataIndex : 'weight',
				header : '总重量'
			},{
				dataIndex : 'volume',
				header : '总体积'
			},{
				dataIndex : 'chargeMode',
				header : '计费方式',
				renderer:function(value){
					if(value==null||value=='')
						return "<span>未维护</span>";
					else return "<span>"+value+"</span>";
				}
			},{
				dataIndex : 'unitPrice',
				header : '单位价钱'
			},{
				dataIndex : 'dateAccountStart',
				header : '结算开始日期'
			},{
				dataIndex : 'dateAccountEnd',
				header : '结算结束日期'
			},{
				dataIndex : 'daysStock',
				header : '仓储天数'
			},{
				dataIndex : 'stockFee',
				header : '仓储费用'
			},{
				dataIndex : 'remarks',
				header : '备注'
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_SF = new Ext.grid.GridPanel({
		
		region: 'center',
		sm : _sm_SF,
		cm : _cm_SF,
		ds : _ds_SF,
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
			},'-',
			{
				text : "财务维护",
				iconCls: 'commonSaveAll',
				id:'btn_listsave_SF',
				handler:function()
				{
					GRID_FINANCE_SAVE(_grid_SF,onEditStorageFee,'stockIncomeID');
				}
			},
			{
				text : "归档",
				iconCls: 'filing',
				id:'btn_archive_SF',
				handler: function()
				{
					GRID_ARCHIVE(_grid_SF,'StockFinance.archive.action','ids','stockIncomeID');
				}
			},
			{
				text : "取消归档",
				iconCls: 'unfiling',
				handler: function()
				{
					GRID_UNARCHIVE(_grid_SF,'StockFinance.unarchive.action','ids','stockIncomeID');
				}
			},'-',
			{
				text : "新建结算",
				iconCls: 'commonAdd',
				handler: function(){onCreateStorageFee();}
			},
			{
				xtype: 'tbfill'
			},'-',
			{
				text : "导出数据",
				iconCls: 'commonExport',
				handler: function(){GRID_EXPORT(_grid_SF,'SF','stockIncomeID');}
			},'-',
			{
				xtype: 'tbspacer'
			}]
		}),

		bbar : new Ext.PagingToolbar({
					pageSize : _limit_SF,
					store : _ds_SF,
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
					       value: _limit_SF,
					       width:　DATAFIELDWIDTH,
						   listeners : {
						    	select : function(combo, record, index) {
						        	_ds_SF.baseParams.limit = combo.getValue();
						        	_grid_SF.getBottomToolbar().pageSize = combo.getValue();
						        	SETFORMPARAMTOSTORE(_formPanel_SF,_ds_SF);
						        	_grid_SF.getBottomToolbar().changePage(1);
						   		}//select
						   }
					})]
				})

	});

