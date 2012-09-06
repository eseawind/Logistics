
function update_transportationPay(contentPanel,node)
{
    
}

function init_transportationPay(id)
{
	var page = Ext.getCmp(id);
	page.add(_formPanel_TP);
	page.add(_grid_TP);
	STORE_COSTCENTER_LOAD();
	STORE_CITY_LOAD();
	_ds_TP.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_TP.reader.message,_ds_TP.reader.valid,success);
		}
	});
}
var _limit_TP = 30;

var _formPanel_TP = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_TP,_ds_TP))
					return;
				_ds_TP.baseParams.start = 0;
				_grid_TP.getTopToolbar().findById('btn_listsave_TP').enable();
				_grid_TP.getTopToolbar().findById('btn_archive_TP').enable();
				if(_formPanel_TP.findById('con_financialState_TP').getValue()=='已归档'){
					_grid_TP.getTopToolbar().findById('btn_listsave_TP').disable();
					_grid_TP.getTopToolbar().findById('btn_archive_TP').disable();
				}
				_ds_TP.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_TP.reader.message,_ds_TP.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function(){ _formPanel_TP.getForm().reset();}
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
						name: 'fcdto.freightManifestID',
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
						name: 'fcdto.customer',
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
					       hiddenName : 'fcdto.originCity',
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
					       store : STORE_COSTCENTER,
					       valueField : 'costCenterID',
					       displayField : 'costCenterID',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: false,
					       forceSelection : true,
					       hiddenName : 'fcdto.costCenter',
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
					       store : new Ext.data.SimpleStore({      
					              fields : ["value"],
					              data : [['未归档'], ['已归档']]
					       }),
					       valueField : 'value',
					       displayField : 'value',
					       mode : 'local',
					       id:'con_financialState_TP',
					       forceSelection : true,
					       hiddenName : 'fcdto.financialState',
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
					       hiddenName : 'fcdto.destinationCity',
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
						name: 'fcdto.customerNumber',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				}]
			}]
	});

	var _ds_TP = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'FreightCost.queryOnCondition.action'
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
					name : 'costCenter'
				}, {
					name : "customer"
				}, {
					name : 'customerType'
				}, {
					name : 'sumAmount'
				}, {
					name : 'sumWeight'
				}, {
					name : 'sumVolume'
				}, {
					name : 'sumValue'
				}, {
					name : 'freightCost'
				}, {
					name : 'PrepaidCost'
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
					name : 'consigneeCompany'
				}, {
					name : 'consignee'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_TP,
				'fcdto.financialState' : '未归档'
			}
	});

	var _sm_TP = new Ext.grid.CheckboxSelectionModel();
	var _cm_TP = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_TP,
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
				dataIndex : 'costCenter',
				header : '成本中心'	
			},{
				dataIndex : 'customer',
				header : '客户名称'	
			},{
				dataIndex : 'customerType',
				header : '客户类型'	
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
				header : '运输支出',
				dataIndex : 'freightCost'
			},{
				header : '代垫费',
				dataIndex : 'PrepaidCost'
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
	
	var _grid_TP = new Ext.grid.GridPanel({
		
				region: 'center',
				sm : _sm_TP,
				cm : _cm_TP,
				ds : _ds_TP,
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
						id:'btn_listsave_TP',
						handler:function()
						{
							GRID_FINANCE_SAVE(_grid_TP,onEditTransPay,'freightManifestID');
						}
					},
					{
						text : "归档",
						iconCls: 'filing',
						id:'btn_archive_TP',
						handler: function()
						{
							GRID_ARCHIVE(_grid_TP,'FreightCost.archive.action','fmIDList','freightManifestID');
						}
					},
					{
						text : "取消归档",
						iconCls: 'unfiling',
						handler: function()
						{
							GRID_UNARCHIVE(_grid_TP,'FreightCost.unarchive.action','fmIDList','freightManifestID');
						}
					},'-',
					{
						text : "查看明细",
						iconCls: 'commonDetail',
						handler: function(){
							GRID_EDITDETAIL(_grid_TP,onDetailTransportationSheet,'freightManifestID');
						}
					},
					{
						xtype: 'tbfill'
					},'-',
					{
						text : "物流明细",
						iconCls: 'trackDetail',
						handler: function(){
							GRID_EDITDETAIL(_grid_TP,onDetailTransportationTrack,'freightManifestID');
						}
					},'-',
					{
						text : "导出数据",
						iconCls: 'commonExport',
						handler: function(){GRID_EXPORT(_grid_TP,'TP','freightManifestID');}
					},'-',
					{
						xtype: 'tbspacer'
					}]
				}),

				bbar : new Ext.PagingToolbar({
							pageSize : _limit_TP,
							store : _ds_TP,
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
							       value: _limit_TP,
							       width:　DATAFIELDWIDTH,
								   listeners : {
								    	select : function(combo, record, index) {
								        	_ds_TP.baseParams.limit = combo.getValue();
								        	_grid_TP.getBottomToolbar().pageSize = combo.getValue();
								        	SETFORMPARAMTOSTORE(_formPanel_TP,_ds_TP);
								        	_grid_TP.getBottomToolbar().changePage(1);
								   		}//select
								   }
							})]
						})

			});

	_grid_TP.on('rowcontextmenu', onRightClick_TP);
	
	var _rightClickMenu_TP = new Ext.menu.Menu({
				
				items : [{
							handler: function(){ GRID_EDITDETAIL(_grid_TP,onDetailTransportationSheet,'freightManifestID'); },
							text : '运输单明细',
							iconCls: 'commonDetail'
						},{
							handler :function(){ GRID_EDITDETAIL(_grid_TP,onDetailTransportationTrack,'freightManifestID'); },
							text : '物流明细',
							iconCls: 'trackDetail'
						},{
							handler: function(){GRID_PRINT(_grid_TP,'FreightManifest.print.action','freightManifestID')},
							text : '打印',
							iconCls: 'commonPrint'
						}]
			});

	function onRightClick_TP(grid, index, e) {
		ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_TP);
	}
