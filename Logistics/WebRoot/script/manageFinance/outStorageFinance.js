
function update_outStorageFinance(contentPanel,node)
{
    
}

function init_outStorageFinance(id)
{
	var page = Ext.getCmp(id);
	page.add(_formPanel_OSF);
	page.add(_grid_OSF);
	STORE_COSTCENTER_LOAD();
    STORE_SELLCENTER_LOAD();
    STORE_STORAGE_LOAD();
	_ds_OSF.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_OSF.reader.message,_ds_OSF.reader.valid,success);
		}
	});
}
var _limit_OSF = 30;

var _formPanel_OSF = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_OSF,_ds_OSF))
					return;
				_ds_OSF.baseParams.start = 0;
				_grid_OSF.getTopToolbar().findById('btn_listsave_OSF').enable();
				_grid_OSF.getTopToolbar().findById('btn_archive_OSF').enable();
				if(_formPanel_OSF.findById('con_financialState_OSF').getValue()=='已归档'){
					_grid_OSF.getTopToolbar().findById('btn_listsave_OSF').disable();
					_grid_OSF.getTopToolbar().findById('btn_archive_OSF').disable();
				}
				_ds_OSF.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_OSF.reader.message,_ds_OSF.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function(){ _formPanel_OSF.getForm().reset();}
		}],
		
		items:[{//Row 1
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
						fieldLabel: '出库单号',
						name: 'sofdto.stockoutManifestID',
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
					       hiddenName : 'sofdto.sellCenter',
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
					       hiddenName : 'sofdto.warehouseName',
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
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_COSTCENTER,
					       valueField : 'costCenterID',
					       displayField : 'costCenterID',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: false,
					       forceSelection : true,
					       hiddenName : 'sofdto.costCenter',
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
					columnWidth: '0.33',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '客户名称',
						name: 'sofdto.customer',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 2
					columnWidth: '0.67',
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
					       id:'con_financialState_OSF',
					       forceSelection : true,
					       hiddenName : 'sofdto.financialState',
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

	var _ds_OSF = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'StockoutFinance.queryOnCondition.action'
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
					name : "sellCenter"
				}, {
					name : 'costCenter'
				}, {
					name : 'customer'
				}, {
					name : 'warehouseName'
				}, {
					name : 'sumAmount'
				}, {
					name : 'sumVolume'
				}, {
					name : 'sumWeight'
				}, {
					name : 'chargeMode'
				}, {
					name : 'stockoutFee'
				}, {
					name : 'loadUnloadCost'
				}, {
					name : 'extraCost'
				}, {
					name : 'financialState'
				}, {
					name : 'financialRemarks'
				}, {
					name : 'unitPrice'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_OSF,
				'sofdto.financialState' : '未归档'
			}
	});

	var _sm_OSF = new Ext.grid.CheckboxSelectionModel();
	var _cm_OSF = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_OSF,
			{
				dataIndex : 'stockoutManifestID',
				header : '出库单号'	
			},{
				dataIndex : 'dateCreated',
				header : '建单日期'
			},{
				dataIndex : 'dateStockout',
				header : '出库日期'
			},{
				dataIndex : 'financialState',
				header : '财务状态'
			},{
				dataIndex : 'sellCenter',
				header : '销售中心'
			},{
				dataIndex : 'costCenter',
				header : '成本中心'
			},{
				dataIndex : 'customer',
				header : '客户名称'
			},{
				header : '库存地',
				dataIndex : 'warehouseName'
			},{
				header : '总数量',
				dataIndex : 'sumAmount'
			},{
				header : '总重量',
				dataIndex : 'sumWeight'
			},{
				dataIndex : 'sumVolume',
				header : '总体积'
			},{
				header : '计费方式',
				dataIndex : 'chargeMode'
			},{
				dataIndex : 'unitPrice',
				header : '单位报价'
			},{
				dataIndex : 'stockoutFee',
				header : '出库费用'
			},{
				dataIndex : 'loadUnloadCost',
				header : '装卸费用'
			},{
				dataIndex : 'extraCost',
				header : '其他费用'
			},{
				dataIndex : 'financialRemarks',
				header : '备注'
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_OSF = new Ext.grid.GridPanel({
		
				region: 'center',
				sm : _sm_OSF,
				cm : _cm_OSF,
				ds : _ds_OSF,
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
						id:'btn_listsave_OSF',
						handler:function()
						{
							GRID_FINANCE_SAVE(_grid_OSF,onEditOutStorageFee,'stockoutManifestID');
						}
					},
					{
						text : "归档",
						iconCls: 'filing',
						id:'btn_archive_OSF',
						handler: function()
						{
							GRID_ARCHIVE(_grid_OSF,'StockoutFinance.archive.action','midList','stockoutManifestID');
						}
					},
					{
						text : "取消归档",
						iconCls: 'unfiling',
						handler: function()
						{
							GRID_UNARCHIVE(_grid_OSF,'StockoutFinance.unarchive.action','midList','stockoutManifestID');
						}
					},'-',
					{
						text : "查看明细",
						iconCls: 'commonDetail',
						handler: function(){
							GRID_EDITDETAIL(_grid_OSF,onDetailOutStorageSheet,'stockoutManifestID');
						}
					},
					{
						xtype: 'tbfill'
					},'-',
					{
						text : "导出数据",
						iconCls: 'commonExport',
						handler: function(){GRID_EXPORT(_grid_OSF,'OSF','stockoutManifestID');}
					},'-',
					{
						xtype: 'tbspacer'
					}]
				}),
				
				bbar : new Ext.PagingToolbar({
							pageSize : _limit_OSF,
							store : _ds_OSF,
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
							       value: _limit_OSF,
							       width:　DATAFIELDWIDTH,
								   listeners : {
								    	select : function(combo, record, index) {
								        	_ds_OSF.baseParams.limit = combo.getValue();
								        	_grid_OSF.getBottomToolbar().pageSize = combo.getValue();
								        	SETFORMPARAMTOSTORE(_formPanel_OSF,_ds_OSF);
								        	_grid_OSF.getBottomToolbar().changePage(1);
								   		}//select
								   }
							})]
						})

			});

	_grid_OSF.on('rowcontextmenu', onRightClick_OSF);
	
	var _rightClickMenu_OSF = new Ext.menu.Menu({
				
				items : [{
							handler : function(){GRID_EDITDETAIL(_grid_OSF,onDetailOutStorageSheet,'stockoutManifestID');},
							text : '明细',
							iconCls: 'commonDetail'
						}]
			});

	function onRightClick_OSF(grid, index, e) {
		ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_OSF);
	}
