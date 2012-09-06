
function update_enterStorageFinance(contentPanel,node)
{
    
}

function init_enterStorageFinance(id)
{
	var page = Ext.getCmp(id);
	page.add(_formPanel_ESF);
	page.add(_grid_ESF);
	STORE_COSTCENTER_LOAD();
    STORE_SELLCENTER_LOAD();
    STORE_STORAGE_LOAD();
	_ds_ESF.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_ESF.reader.message,_ds_ESF.reader.valid,success);
		}
	});
}

var _limit_ESF = 30;

var _formPanel_ESF = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_ESF,_ds_ESF))
					return;
				_ds_ESF.baseParams.start = 0;
				_grid_ESF.getTopToolbar().findById('btn_listsave_ESF').enable();
				_grid_ESF.getTopToolbar().findById('btn_archive_ESF').enable();
				if(_formPanel_ESF.findById('con_financialState_ESF').getValue()=='已归档'){
					_grid_ESF.getTopToolbar().findById('btn_listsave_ESF').disable();
					_grid_ESF.getTopToolbar().findById('btn_archive_ESF').disable();
				}
				_ds_ESF.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_ESF.reader.message,_ds_ESF.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function(){ _formPanel_ESF.getForm().reset();}
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
						fieldLabel: '入库单号',
						name: 'sifdto.stockinManifestID',
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
					       hiddenName : 'sifdto.sellCenter',
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
					columnWidth: '0.33',
					layout: 'form',
					border: false,
					items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_STORAGE,
					       valueField : 'name',
					       displayField : 'name',
					       mode : 'local',
					       forceSelection : false,
					       hiddenName : 'sifdto.warehouseName',
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
					       hiddenName : 'sifdto.costCenter',
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
						name: 'sifdto.customer',
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
					       id:'con_financialState_ESF',
					       forceSelection : true,
					       hiddenName : 'sifdto.financialState',
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

	var _ds_ESF = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'StockinFinance.queryOnCondition.action'
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
					name : 'stockinFee'
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
				limit : _limit_ESF,
				'sifdto.financialState' : '未归档'
			}
	});

	var _sm_ESF = new Ext.grid.CheckboxSelectionModel();
	var _cm_ESF = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_ESF,
			{
				dataIndex : 'stockinManifestID',
				header : '入库单号'	
			},{
				dataIndex : 'dateCreated',
				header : '建单日期'
			},{
				dataIndex : 'dateStockin',
				header : '入库日期'
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
				dataIndex : 'stockinFee',
				header : '入库费用'
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
	
	var _grid_ESF = new Ext.grid.GridPanel({
		
				region: 'center',
				sm : _sm_ESF,
				cm : _cm_ESF,
				ds : _ds_ESF,
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
						id:'btn_listsave_ESF',
						handler:function()
						{
							GRID_FINANCE_SAVE(_grid_ESF,onEditEnterStorageFee,'stockinManifestID');
						}
					},
					{
						text : "归档",
						iconCls: 'filing',
						id:'btn_archive_ESF',
						handler: function()
						{
							GRID_ARCHIVE(_grid_ESF,'StockinFinance.archive.action','mIDList','stockinManifestID');
						}
					},
					{
						text : "取消归档",
						iconCls: 'unfiling',
						handler: function()
						{
							GRID_UNARCHIVE(_grid_ESF,'StockinFinance.unarchive.action','mIDList','stockinManifestID');
						}
					},'-',
					{
						text : "查看明细",
						iconCls: 'commonDetail',
						handler: function(){
							GRID_EDITDETAIL(_grid_ESF,onDetailEnterStorageSheet,'stockinManifestID');
						}
					},
					{
						xtype: 'tbfill'
					},'-',
					{
						text : "导出数据",
						iconCls: 'commonExport',
						handler: function(){GRID_EXPORT(_grid_ESF,'ESF','stockinManifestID');}
					},'-',
					{
						xtype: 'tbspacer'
					}]
				}),

				bbar : new Ext.PagingToolbar({
							pageSize : _limit_ESF,
							store : _ds_ESF,
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
							       value: _limit_ESF,
							       width:　DATAFIELDWIDTH,
								   listeners : {
								    	select : function(combo, record, index) {
								        	_ds_ESF.baseParams.limit = combo.getValue();
								        	_grid_ESF.getBottomToolbar().pageSize = combo.getValue();
								        	SETFORMPARAMTOSTORE(_formPanel_ESF,_ds_ESF);
								        	_grid_ESF.getBottomToolbar().changePage(1);
								   		}//select
								   }
							})]
						})

			});

	_grid_ESF.on('rowcontextmenu', onRightClick_ESF);
	
	var _rightClickMenu_ESF = new Ext.menu.Menu({
				
				items : [{
							handler: function(){GRID_EDITDETAIL(_grid_ESF,onDetailEnterStorageSheet,'stockinManifestID'); },
							text : '明细',
							iconCls: 'commonDetail'
						}, {
							handler: function(){GRID_PRINT(_grid_ESF,'StockinManifest.print.action','stockinManifestID')},
							text : '打印',
							iconCls: 'commonPrint'
						}]
			});

	function onRightClick_ESF(grid, index, e) {
		ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_ESF);
	}
