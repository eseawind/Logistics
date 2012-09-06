
function update_specialStorageFinance(contentPanel,node)
{
    
}

function init_specialStorageFinance(id)
{
	var page = Ext.getCmp(id);
	page.add(_formPanel_SSF);
	page.add(_grid_SSF);
	STORE_SELLCENTER_LOAD();
    STORE_STORAGE_LOAD();
	_ds_SSF.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_SSF.reader.message,_ds_SSF.reader.valid,success);
		}
	});
}

var _limit_SSF = 30;

var _formPanel_SSF = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_SSF,_ds_SSF))
					return;
				_ds_SSF.baseParams.start = 0;
				_grid_SSF.getTopToolbar().findById('btn_listsave_SSF').enable();
				_grid_SSF.getTopToolbar().findById('btn_archive_SSF').enable();
				if(_formPanel_SSF.findById('con_financialState_SSF').getValue()=='已归档'){
					_grid_SSF.getTopToolbar().findById('btn_listsave_SSF').disable();
					_grid_SSF.getTopToolbar().findById('btn_archive_SSF').disable();
				}
				_ds_SSF.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_SSF.reader.message,_ds_SSF.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function(){ _formPanel_OSF.getForm().reset();}
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
						name: 'income.incomeID',
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
						fieldLabel: '建单日期',
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
					       id:'con_financialState_SSF',
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

	var _ds_SSF = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'SpecialStockIncome.queryOnCondition.action'
				}),
		reader : new Ext.data.JsonReader({
					totalProperty : 'qualifiedAmount',
					root : 'resultMapList'
				}, 
				[{
					name : 'incomeID'
				}, {
					name : 'dateCreated'
				}, {
					name : 'warehouse'
				}, {
					name : "sellCenter"
				}, {
					name : 'customer'
				}, {
					name : 'area'
				}, {
					name : 'quote'
				}, {
					name : 'dateStart'
				}, {
					name : 'dateEnd'
				}, {
					name : 'daysStock'
				}, {
					name : 'stockFee'
				}, {
					name : 'dateEnd'
				}, {
					name : 'extraFee'
				}, {
					name : 'financialState'
				}, {
					name : 'remarks'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_SSF,
				'income.financialState' : '未归档'
			}
	});

	var _sm_SSF = new Ext.grid.CheckboxSelectionModel();
	var _cm_SSF = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_SSF,
			{
				dataIndex : 'incomeID',
				header : '收支单号'	
			},{
				dataIndex : 'dateCreated',
				header : '建单日期'
			},{
				dataIndex : 'financialState',
				header : '财务状态'
			},{
				dataIndex : 'sellCenter',
				header : '销售中心'	
			},{
				header : '库存地',
				dataIndex : 'warehouse'
			},{
				header : '客户名称',
				dataIndex : 'customer'
			},{
				header : '仓储起始日期',
				dataIndex : 'dateStart'
			},{
				header : '仓储结束日期',
				dataIndex : 'dateEnd'
			},{
				header : '仓储面积',
				dataIndex : 'area'
			},{
				header : '仓储天数',
				dataIndex : 'daysStock'
			},{
				dataIndex : 'quote',
				header : '每天单位面积费用'
			},{
				dataIndex : 'stockFee',
				header : '仓储费用'
			},{
				dataIndex : 'extraFee',
				header : '其他费用'
			},{
				dataIndex : 'remarks',
				header : '备注'
			}],
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_SSF = new Ext.grid.GridPanel({
		
				region: 'center',
				sm : _sm_SSF,
				cm : _cm_SSF,
				ds : _ds_SSF,
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
						id:'btn_listsave_SSF',
						handler:function()
						{
							GRID_FINANCE_SAVE(_grid_SSF,onEditSpecialStorageFee,'incomeID');
						}
					},
					{
						text : "归档",
						iconCls: 'filing',
						id:'btn_archive_SSF',
						handler: function()
						{
							GRID_ARCHIVE(_grid_SSF,'SpecialStockIncome.archive.action','ids','incomeID');
						}
					},
					{
						text : "取消归档",
						iconCls: 'unfiling',
						handler: function()
						{
							GRID_UNARCHIVE(_grid_SSF,'SpecialStockIncome.unarchive.action','ids','incomeID');
						}
					},'-',
					{
						text : "新建特殊收支",
						iconCls: 'commonAdd',
						handler: onCreateSpecialStorageSheet
					},
					{
						xtype: 'tbfill'
					},'-',
					{
						text : "导出数据",
						iconCls: 'commonExport',
						handler: function(){GRID_EXPORT(_grid_SSF,'SSF','incomeID');}
					},'-',
					{
						xtype: 'tbspacer'
					}]
				}),

				bbar : new Ext.PagingToolbar({
							pageSize : _limit_SSF,
							store : _ds_SSF,
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
							       value: _limit_SSF,
							       width:　DATAFIELDWIDTH,
								   listeners : {
								    	select : function(combo, record, index) {
								        	_ds_SSF.baseParams.limit = combo.getValue();
								        	_grid_SSF.getBottomToolbar().pageSize = combo.getValue();
								        	SETFORMPARAMTOSTORE(_formPanel_SSF,_ds_SSF);
								        	_grid_SSF.getBottomToolbar().changePage(1);
								   		}//select
								   }
							})]
						})
			});

