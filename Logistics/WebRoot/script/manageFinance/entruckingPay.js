
function update_entruckingPay(contentPanel,node)
{
    
}

function init_entruckingPay(id)
{
	var page = Ext.getCmp(id);
	page.add(_formPanel_EP);
	page.add(_grid_EP);
	STORE_COSTCENTER_LOAD();
	STORE_CITY_LOAD();
	_ds_EP.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_EP.reader.message,_ds_EP.reader.valid,success);
		}
	});
}

var _limit_EP = 30;

var _formPanel_EP = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_EP,_ds_EP))
					return;
				_ds_EP.baseParams.start = 0;
				_grid_EP.getTopToolbar().findById('btn_listsave_EP').enable();
				_grid_EP.getTopToolbar().findById('btn_archive_EP').enable();
				if(_formPanel_EP.findById('con_financialState_EP').getValue()=='已归档'){
					_grid_EP.getTopToolbar().findById('btn_listsave_EP').disable();
					_grid_EP.getTopToolbar().findById('btn_archive_EP').disable();
				}
				_ds_EP.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_EP.reader.message,_ds_EP.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function(){ _formPanel_EP.getForm().reset();}
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
						fieldLabel: '装车单号',
						name: 'scdto.shipmentManifestID',
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
					       store : new Ext.data.SimpleStore({      
					              fields : ["value"],
					              data : [['未归档'], ['已归档']]
					       }),
					       valueField : 'value',
					       displayField : 'value',
					       mode : 'local',
					       id:'con_financialState_EP',
					       forceSelection : true,
					       hiddenName : 'scdto.financialState',
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '财务状态',
					       value: '未归档',
					       width:　TEXTFIELDWIDTH
					})]
				},
				{//Column 2
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_CITY,
					       valueField : 'name',
					       displayField : 'nameID',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: false,
					       forceSelection : false,
					       hiddenName : 'scdto.originCity',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : true,
					       emptyText: '选择始发市\县',
					       fieldLabel : '始发地',
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
					items:[
					{
						xtype: 'datefield',
						fieldLabel: '装车日期',
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
					items:[
					{
						xtype: 'label',
						fieldLabel: ' ',
						labelSeparator: ' ',
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 2
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '承运单位',
						name: 'scdto.freightContractor',
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
					       store : STORE_CITY,
					       valueField : 'name',
					       displayField : 'nameID',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: false,
					       forceSelection : true,
					       hiddenName : 'scdto.destinationCity',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : true,
					       emptyText: '选择目的市\县',
					       fieldLabel : '目的地',
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

	var _ds_EP = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'ShipmentCost.queryOnCondition.action'
				}),
		reader : new Self.JsonReader({
					totalProperty : 'qualifiedAmount',
					root : 'resultMapList'
				}, 
				[{
					name : 'shipmentManifestID'
				}, {
					name : 'dateCreated'
				}, {
					name : 'costCenter'
				}, {
					name : 'sumAmount'
				}, {
					name : 'sumVolume'
				}, {
					name : 'sumWeight'
				}, {
					name : 'sumValue'
				}, {
					name : 'chargeMode'
				}, {
					name : 'unitQuote'
				}, {
					name : 'freightCost'
				}, {
					name : 'loadUnloadCost'
				}, {
					name : 'extraCost'
				}, {
					name : 'financialState'
				}, {
					name : 'financialRemarks'
				}, {
					name : 'originCity'
				}, {
					name : 'originProvince'
				}, {
					name : 'destinationCity'
				}, {
					name : 'destinationProvince'
				}, {
					name : 'freightContractor'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_EP,
				'scdto.financialState' : '未归档'
			}
	});

	var _sm_EP = new Ext.grid.CheckboxSelectionModel();
	var _cm_EP = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_EP,
			{
				dataIndex : 'shipmentManifestID',
				header : '装车单号'	
			},{
				header : '建单日期',
				dataIndex : 'dateCreated'
			},{
				header : '承运单位',
				dataIndex : 'freightContractor'
			},{
				header : '始发市县',
				dataIndex : 'originCity'
			},{
				header : '始发省',
				dataIndex : 'originProvince'
			},{
				header : '目的市县',
				dataIndex : 'destinationCity'
			},{
				header : '目的省',
				dataIndex : 'destinationProvince'
			},{
				dataIndex : 'financialState',
				header : '财务状态'
			},{
				dataIndex : 'sumAmount',
				header : '总件数'	
			},{
				dataIndex : 'sumWeight',
				header : '总重量'	
			},{
				dataIndex : 'sumVolume',
				header : '总体积'	
			},{
				dataIndex : 'sumValue',
				header : '总价值'	
			},{
				dataIndex : 'chargeMode',
				header : '计费方式'
			},{
				dataIndex : 'unitQuote',
				header : '单位价钱'
			},{
				header : '直接费用',
				dataIndex : 'freightCost'
			},{
				header : '装卸费',
				dataIndex : 'loadUnloadCost'
			},{
				header : '其他费用',
				dataIndex : 'extraCost'
			},{
				dataIndex : 'financialRemarks',
				header : '备注'
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_EP = new Ext.grid.GridPanel({
		
				region: 'center',
				sm : _sm_EP,
				cm : _cm_EP,
				ds : _ds_EP,
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
						id:'btn_listsave_EP',
						handler:function()
						{
							GRID_FINANCE_SAVE(_grid_EP,onEditEntruckingPay,'shipmentManifestID');
						}
					},
					{
						text : "归档",
						iconCls: 'filing',
						id:'btn_archive_EP',
						handler: function()
						{
							GRID_ARCHIVE(_grid_EP,'ShipmentCost.archive.action','mIDList','shipmentManifestID');
						}
					},
					{
						text : "取消归档",
						iconCls: 'unfiling',
						handler: function()
						{
							GRID_UNARCHIVE(_grid_EP,'ShipmentCost.unarchive.action','mIDList','shipmentManifestID');
						}
					},'-',
					{
						text : "查看明细",
						iconCls: 'commonDetail',
						handler: function(){
							GRID_EDITDETAIL(_grid_EP,onDetailEntruckingSheet,'shipmentManifestID');
						}
					},
					{
						xtype: 'tbfill'
					},'-',
					{
						text : "导出数据",
						iconCls: 'commonExport',
						handler: function(){GRID_EXPORT(_grid_EP,'EP','shipmentManifestID');}
					},'-',
					{
						xtype: 'tbspacer'
					}]
				}),

				bbar : new Ext.PagingToolbar({
							pageSize : _limit_EP,
							store : _ds_EP,
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
							       value: _limit_EP,
							       width:　DATAFIELDWIDTH,
								   listeners : {
								    	select : function(combo, record, index) {
								        	_ds_EP.baseParams.limit = combo.getValue();
								        	_grid_EP.getBottomToolbar().pageSize = combo.getValue();
								        	SETFORMPARAMTOSTORE(_formPanel_EP,_ds_EP);
								        	_grid_EP.getBottomToolbar().changePage(1);
								   		}//select
								   }
							})]
						})

			});

	_grid_EP.on('rowcontextmenu', onRightClick_EP);
	
	var _rightClickMenu_EP = new Ext.menu.Menu({
				
		items : [{
					handler: function(){ GRID_EDITDETAIL(_grid_EP,onDetailEntruckingSheet,'shipmentManifestID'); },
					text : '明细',
					iconCls: 'commonDetail'
				}, {
					handler: function(){GRID_PRINT(_grid_EP,'ShipmentManifest.print.action','shipmentManifestID')},
					text : '打印',
					iconCls: 'commonPrint'
				}]
	});

	function onRightClick_EP(grid, index, e) {
		ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_EP);
	}
