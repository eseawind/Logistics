
function update_transferStorageFinance(contentPanel,node)
{
    
}

function init_transferStorageFinance(id)
{
	var page = Ext.getCmp(id);
	page.add(_formPanel_TSSF);
	page.add(_grid_TSSF);
	STORE_COSTCENTER_LOAD();
    STORE_STORAGE_LOAD();
	_ds_TSSF.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_TSSF.reader.message,_ds_TSSF.reader.valid,success);
		}
	});
}
var _limit_TSSF = 30;

var _formPanel_TSSF = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_TSSF,_ds_TSSF))
					return;
				_ds_TSSF.baseParams.start = 0;
				_grid_TSSF.getTopToolbar().findById('btn_listsave_TSSF').enable();
				_grid_TSSF.getTopToolbar().findById('btn_archive_TSSF').enable();
				if(_formPanel_TSSF.findById('con_financialState_TSSF').getValue()=='已归档'){
					_grid_TSSF.getTopToolbar().findById('btn_listsave_TSSF').disable();
					_grid_TSSF.getTopToolbar().findById('btn_archive_TSSF').disable();
				}
				_ds_TSSF.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_TSSF.reader.message,_ds_TSSF.reader.valid,success);
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
						fieldLabel: '移库单号',
						name: 'stfdto.stockTransferManifestID',
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
					       valueField : 'name',
					       displayField : 'name',
					       mode : 'local',
					       forceSelection : false,
					       hiddenName : 'stfdto.outWarehouseName',
					       editable : true,
					       typeAhead : false,
					       selectOnFocus:true,
					       triggerAction : 'all',
					       allowBlank : true,
					       fieldLabel : '移出仓库',
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
					items:[
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
					       hiddenName : 'stfdto.costCenter',
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
					       hiddenName : 'stfdto.inWarehouseName',
					       editable : true,
					       typeAhead : false,
					       selectOnFocus:true,
					       triggerAction : 'all',
					       allowBlank : true,
					       fieldLabel : '移入仓库',
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
					       id:'con_financialState_TSSF',
					       forceSelection : true,
					       hiddenName : 'stfdto.financialState',
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

	var _ds_TSSF = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'StockTransferFinance.queryOnCondition.action'
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
					name : "costCenter"
				}, {
					name : 'sumAmount'
				}, {
					name : 'sumVolume'
				}, {
					name : 'sumWeight'
				}, {
					name : 'loadUnloadCost'
				}, {
					name : 'extraCost'
				}, {
					name : 'financialState'
				}, {
					name : 'remarks'
				}, {
					name : 'outWarehouseName'
				}, {
					name : 'inWarehouseName'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_OSF,
				'stfdto.financialState' : '未归档'
			}
	});

	var _sm_TSSF = new Ext.grid.CheckboxSelectionModel();
	var _cm_TSSF = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_TSSF,
			{
				dataIndex : 'stockTransferManifestID',
				header : '移库单号'	
			},{
				dataIndex : 'dateCreated',
				header : '建单日期'
			},{
				header : '移库日期',
				dataIndex : 'dateTransfered'
			},{
				dataIndex : 'financialState',
				header : '财务状态'
			},{
				dataIndex : 'costCenter',
				header : '成本中心'
			},{
				dataIndex : 'outWarehouseName',
				header : '移出仓库'
			},{
				header : '移入仓库',
				dataIndex : 'inWarehouseName'
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
				dataIndex : 'loadUnloadCost',
				header : '装卸费用'
			},{
				dataIndex : 'extraCost',
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
	
	var _grid_TSSF = new Ext.grid.GridPanel({
		
				region: 'center',
				sm : _sm_TSSF,
				cm : _cm_TSSF,
				ds : _ds_TSSF,
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
						id:'btn_listsave_TSSF',
						handler:function()
						{
							GRID_FINANCE_SAVE(_grid_TSSF,onEditTransStorageFee,'stockTransferManifestID');
						}
					},
					{
						text : "归档",
						iconCls: 'filing',
						id:'btn_archive_TSSF',
						handler: function()
						{
							GRID_ARCHIVE(_grid_TSSF,'StockTransferFinance.archive.action','mIDList','stockTransferManifestID');
						}
					},
					{
						text : "取消归档",
						iconCls: 'unfiling',
						handler: function()
						{
							GRID_UNARCHIVE(_grid_TSSF,'StockTransferFinance.unarchive.action','mIDList','stockTransferManifestID');
						}
					},'-',
					{
						text : "查看明细",
						iconCls: 'commonDetail',
						handler: function(){
							GRID_EDITDETAIL(_grid_TSSF,onDetailTransferStorageSheet,'stockTransferManifestID');
						}
					},
					{
						xtype: 'tbfill'
					},'-',
					{
						text : "导出数据",
						iconCls: 'commonExport',
						handler: function(){GRID_EXPORT(_grid_TSSF,'TSSF','stockTransferManifestID');}
					},'-',
					{
						xtype: 'tbspacer'
					}]
				}),

				bbar : new Ext.PagingToolbar({
							pageSize : _limit_TSSF,
							store : _ds_TSSF,
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
							       value: _limit_TSSF,
							       width:　DATAFIELDWIDTH,
								   listeners : {
								    	select : function(combo, record, index) {
								        	_ds_TSSF.baseParams.limit = combo.getValue();
								        	_grid_TSSF.getBottomToolbar().pageSize = combo.getValue();
								        	SETFORMPARAMTOSTORE(_formPanel_TSSF,_ds_TSSF);
								        	_grid_TSSF.getBottomToolbar().changePage(1);
								   		}//select
								   }
							})]
						})

			});

	_grid_TSSF.on('rowcontextmenu', onRightClick_TSSF);
	
	var _rightClickMenu_TSSF = new Ext.menu.Menu({
				
				items : [{
							handler : function(){GRID_EDITDETAIL(_grid_TSSF,onDetailTransferStorageSheet,'stockTransferManifestID');},
							text : '明细',
							iconCls: 'commonDetail'
						}]
			});

	function onRightClick_TSSF(grid, index, e) {
		ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_TSSF);
	}
