
function update_transportationIncome(contentPanel,node)
{
    
}

function init_transportationIncome(id)
{
	var page = Ext.getCmp(id);
	page.add(_formPanel_TI);
	page.add(_grid_TI);
	STORE_SELLCENTER_LOAD();
	STORE_CITY_LOAD();
	_ds_TI.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_TI.reader.message,_ds_TI.reader.valid,success);
		}
	});
}

var _limit_TI = 30;

var _formPanel_TI = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_TI,_ds_TI))
					return;
				_ds_TI.baseParams.start = 0;
				_grid_TI.getTopToolbar().findById('btn_listsave_TI').enable();
				_grid_TI.getTopToolbar().findById('btn_archive_TI').enable();
				if(_formPanel_TI.findById('con_financialState_TI').getValue()=='已归档'){
					_grid_TI.getTopToolbar().findById('btn_listsave_TI').disable();
					_grid_TI.getTopToolbar().findById('btn_archive_TI').disable();
				}
				_ds_TI.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_TI.reader.message,_ds_TI.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function(){ _formPanel_TI.getForm().reset();}
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
						fieldLabel: '运输单号',
						name: 'fidto.freightManifestID',
						regex: REGEX_COMMON_T,
						regexText: REGEX_COMMON_T_TEXT,
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
						fieldLabel: '客户名称',
						name: 'fidto.customer',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
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
					       forceSelection : false,
					       hiddenName : 'fidto.originCity',
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
						fieldLabel: '开始日期',
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
					       store : STORE_SELLCENTER,
					       valueField : 'sellCenterID',
					       displayField : 'sellCenterID',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: false,
					       forceSelection : true,
					       hiddenName : 'fidto.sellCenter',
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
					       id:'con_financialState_TI',
					       forceSelection : true,
					       hiddenName : 'fidto.financialState',
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
					       forceSelection : true,
					       hiddenName : 'fidto.destinationCity',
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
						fieldLabel: '结束日期',
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
						fieldLabel: '客户单号',
						name: 'fidto.customerNumber',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				}]
			}]
	});

	var _ds_TI = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'FreightIncome.queryOnCondition.action'
				}),
		reader : new Self.JsonReader({
					totalProperty : 'qualifiedAmount',
					root : 'resultMapList'
				}, 
				[{
					name : 'freightManifestID'
				}, {
					name : 'customerNumber'
				}, {
					name : 'dateCreated'
				}, {
					name : 'sellCenter'
				}, {
					name : "customer"
				}, {
					name : 'customerType'
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
					name : 'freightIncome'
				}, {
					name : 'consignIncome'
				}, {
					name : 'deliveryIncome'
				}, {
					name : 'insuranceIncome'
				}, {
					name : 'extraIncome'
				}, {
					name : 'financialState'
				}, {
					name : 'priceByAmount'
				}, {
					name : 'priceByVolume'
				}, {
					name : 'priceByWeight'
				}, {
					name : 'insuranceRate'
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
					name : 'consigneeCompany'
				}, {
					name : 'consignee'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_TI,
				'fidto.financialState' : '未归档'
			}
	});

	var _sm_TI = new Ext.grid.CheckboxSelectionModel();
	var _cm_TI = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_TI,
			{
				dataIndex : 'freightManifestID',
				header : '运输单号'	
			},{
				header : '客户单号',
				dataIndex : 'customerNumber'
			},{
				header : '建单日期',
				dataIndex : 'dateCreated'
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
				header : '销售中心',
				dataIndex : 'sellCenter'
			},{
				dataIndex : 'customer',
				header : '客户名称'	
			},{
				header : '客户类型',
				dataIndex : 'customerType'
			},{
				dataIndex : 'consigneeCompany',
				header : '收货单位'
			},{
				dataIndex : 'consignee',
				header : '收货人'
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
				header : '数量报价',
				dataIndex : 'priceByAmount'
			},{
				header : '重量报价',
				dataIndex : 'priceByWeight'
			},{
				dataIndex : 'priceByVolume',
				header : '体积报价'	
			},{
				header : '运输费',
				dataIndex : 'freightIncome'
			},{
				header : '提货费',
				dataIndex : 'consignIncome'
			},{
				dataIndex : 'deliveryIncome',
				header : '送货费'	
			},{
				dataIndex : 'insuranceRate',
				header : '保险费率'	
			},{
				dataIndex : 'insuranceIncome',
				header : '保险费'	
			},{
				dataIndex : 'extraIncome',
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
	
	var _grid_TI = new Ext.grid.GridPanel({
		
				region: 'center',
				sm : _sm_TI,
				cm : _cm_TI,
				ds : _ds_TI,
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
						id:'btn_listsave_TI',
						handler:function()
						{
							GRID_FINANCE_SAVE(_grid_TI,onEditTransIncome,'freightManifestID');
						}
					},
					{
						text : "归档",
						iconCls: 'filing',
						id:'btn_archive_TI',
						handler: function()
						{
							GRID_ARCHIVE(_grid_TI,'FreightIncome.archive.action','fmIDList','freightManifestID');
						}
					},
					{
						text : "取消归档",
						iconCls: 'unfiling',
						handler: function()
						{
							GRID_UNARCHIVE(_grid_TI,'FreightIncome.unarchive.action','fmIDList','freightManifestID');
						}
					},'-',
					{
						text : "查看明细",
						iconCls: 'commonDetail',
						handler: function(){
							GRID_EDITDETAIL(_grid_TI,onDetailTransportationSheet,'freightManifestID');
						}
					},
					{
						xtype: 'tbfill'
					},'-',
					{
						text : "物流明细",
						iconCls: 'trackDetail',
						handler: function(){
							GRID_EDITDETAIL(_grid_TI,onDetailTransportationTrack,'freightManifestID');
						}
					},'-',
					{
						text : "导出数据",
						iconCls: 'commonExport',
						handler: function(){GRID_EXPORT(_grid_TI,'TI','freightManifestID');}
					},'-',
					{
						xtype: 'tbspacer'
					}]
				}),

				bbar : new Ext.PagingToolbar({
							pageSize : _limit_TI,
							store : _ds_TI,
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
							       value: _limit_TI,
							       width:　DATAFIELDWIDTH,
								   listeners : {
								    	select : function(combo, record, index) {
								        	_ds_TI.baseParams.limit = combo.getValue();
								        	_grid_TI.getBottomToolbar().pageSize = combo.getValue();
								        	SETFORMPARAMTOSTORE(_formPanel_TI,_ds_TI);
								        	_grid_TI.getBottomToolbar().changePage(1);
								   		}//select
								   }
							})]
						})

			});

	_grid_TI.on('rowcontextmenu', onRightClick_TI);
	
	var _rightClickMenu_TI = new Ext.menu.Menu({
				
				items : [{
							handler: function(){ GRID_EDITDETAIL(_grid_TI,onDetailTransportationSheet,'freightManifestID'); },
							text : '运输单明细',
							iconCls: 'commonDetail'
						},{
							handler :function(){ GRID_EDITDETAIL(_grid_TI,onDetailTransportationTrack,'freightManifestID'); },
							text : '物流明细',
							iconCls: 'trackDetail'
						},{
							handler: function(){GRID_PRINT(_grid_TI,'FreightManifest.print.action','freightManifestID')},
							text : '打印',
							iconCls: 'commonPrint'
						}]
			});

	function onRightClick_TI(grid, index, e) {
		ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_TI);
	}
