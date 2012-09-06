
function update_manageTransportationSheet(contentPanel,node)
{
    
}

function init_manageTransportationSheet(id)
{
	var page = Ext.getCmp(id);
	page.add(_formPanel_MTS);
	page.add(_grid_MTS);
	STORE_CITY_LOAD();
	STORE_COSTCENTER_LOAD();
	STORE_SELLCENTER_LOAD();
	_ds_MTS.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_MTS.reader.message,_ds_MTS.reader.valid,success);
		}
	});
}

var _limit_MTS = 30;

var _formPanel_MTS = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_MTS,_ds_MTS))
				  return;
				_ds_MTS.baseParams.start = 0;
				_ds_MTS.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_MTS.reader.message,_ds_MTS.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function(){ _formPanel_MTS.getForm().reset(); }
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
						name: 'fmdto.freightManifestID',
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
					       forceSelection : false,
					       hiddenName : 'fmdto.originCity',
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
					items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_PROVINCE,
					       valueField : 'province',
					       displayField : 'display',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: false,
					       forceSelection : true,
					       hiddenName : 'fmdto.originProvince',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : true,
					       emptyText: '选择始发省份',
					       fieldLabel : '始发省份',
					       width:　TEXTFIELDWIDTH,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
					       }
					})]
				},
				{//Column 4
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
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '收货单位',
						name: 'fmdto.consigneeCompany',
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
					       forceSelection : true,
					       hiddenName : 'fmdto.destinationCity',
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
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_PROVINCE,
					       valueField : 'province',
					       displayField : 'display',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: false,
					       forceSelection : true,
					       hiddenName : 'fmdto.destinationProvince',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : true,
					       emptyText: '选择目的省份',
					       fieldLabel : '目的省份',
					       width:　TEXTFIELDWIDTH,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
					       }
					})]
				},
				{//Column 4
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
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '收货人',
						name: 'fmdto.consignee',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 1+
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
					       hiddenName : 'fmdto.costCenter',
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
					       store : STORE_TRANSTATE,
					       valueField : 'value',
					       displayField : 'display',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'fmdto.freightState',
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : true,
					       fieldLabel : '运输状态',
					       value: '',
					       width:　TEXTFIELDWIDTH
					})]
				},
				{//Column 2
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items: new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_DATATYPE,
					       valueField : 'value',
					       displayField : 'display',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'dateType',
					       editable : false,
					       triggerAction : 'all',
					       fieldLabel : '日期类型',
					       value: '',
					       width:　DATAFIELDWIDTH
					})
				}]
			},
			{//Row 4
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
						fieldLabel: '客户名称',
						name: 'fmdto.customer',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				},{//Column 2
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
					       hiddenName : 'fmdto.sellCenter',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : true,
					       fieldLabel : '销售中心',
					       width:　TEXTFIELDWIDTH,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
					       }
					})]
				},{//Column 3
					columnWidth: '0.5',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '客户单号',
						name: 'fmdto.customerNumber',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				}]
			}]
	});

	var _ds_MTS = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'FreightManifest.queryOnCondition.action'
				}),
		reader : new Self.JsonReader({
					totalProperty : 'qualifiedAmount',
					root : 'resultMapList'
				}, 
				[{
					name : 'freightManifestID'
				},{
					name : 'customerNumber'
				}, {
					name : 'costCenter'
				}, {
					name : 'sellCenter'
				}, {
					name : 'dateCreated'
				}, {
					name : 'dateExpected'
				}, {
					name : 'customer'
				}, {
					name : 'origin'
				}, {
					name : 'destination'
				}, {
					name : 'dateDelivered'
				}, {
					name : 'dateSigned'
				}, {
					name : 'freightState'
				}, {
					name : 'consignee'
				}, {
					name : 'consigneeCompany'
				}, {
					name : 'consigneePhone'
				}, {
					name : 'consigner'
				}, {
					name : 'consignerPhone'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_MTS
			}
	});

	var _sm_MTS = new Ext.grid.CheckboxSelectionModel();
	var _cm_MTS = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_MTS,
			{
				dataIndex : 'freightManifestID',
				header : '运输单号'	
			},{
				header : '客户单号',
				dataIndex : 'customerNumber',
				renderer:function(value){ 
					if(value==null||value=='')
						return "<span>未填写</span>";
					else return "<span>"+value+"</span>";
				}
			},{
				header : '建单日期',
				dataIndex : 'dateCreated'
			},{
				dataIndex : 'costCenter',
				header:'成本中心'
			},{
				dataIndex : 'sellCenter',
				header : '销售中心'	
			},{
				header : '预计到货日期',
				dataIndex : 'dateExpected'
			},{
				dataIndex : 'customer',
				header : '客户名称'
			},{
				dataIndex : 'origin',
				header : '始发地'	
			},{
				dataIndex : 'destination',
				header : '目的地'	
			},{
				header : '发货日期',
				dataIndex : 'dateDelivered',
				renderer:function(value){ 
					if(value==null||value=='')
						return "<span>未发货</span>";
					else return "<span>"+value+"</span>";
				}
			},{
				header : '到货签收日期',
				dataIndex : 'dateSigned',
				renderer:function(value){ 
					if(value==null||value=='')
						return "<span>未签收</span>";
					else return "<span>"+value+"</span>";
				}
			},{
				dataIndex : 'freightState',
				header : '运输状态'
			},{
				dataIndex : 'consigneeCompany',
				header : '收货单位'
			},{
				dataIndex : 'consignee',
				header : '收货人姓名'
			},{
				dataIndex : 'consigneePhone',
				header : '收货人电话'
			},{
				dataIndex : 'consigner',
				header : '发货人姓名'	
			},{
				dataIndex : 'consignerPhone',
				header : '发货人电话'
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
var _grid_MTS = new Ext.grid.GridPanel({
	
			region: 'center',
			sm : _sm_MTS,
			cm : _cm_MTS,
			ds : _ds_MTS,
			style: 'margin:0',
			stripeRows : true,
			loadMask : true,
			border : false,
			autoScroll: true,
			draggable : false,
			// UI视图配置
			viewConfig : {
				forceFit : false
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
					handler: function(){onCreateTransportationSheet(null);}
				},
				{
					text : "修改",
					iconCls: 'commonEdit',
					handler: function(){
						GRID_EDITDETAIL(_grid_MTS,onEditTransportationSheet,'freightManifestID');
					}
				}, 
				{
					text : "删除",
					iconCls: 'commonDelete',
					handler: function(){
						GRID_DELETE(_grid_MTS,'FreightManifest.delete.action','fmIDList','freightManifestID');
					}
				},'-',
				{
					text : "查看明细",
					iconCls: 'commonDetail',
					handler: function(){
						GRID_EDITDETAIL(_grid_MTS,onDetailTransportationSheet,'freightManifestID');
					}
				},'-',
				{
					text : "打印",
					iconCls: 'commonPrint',
					handler: function(){GRID_PRINT(_grid_MTS,'FreightManifest.print.action','freightManifestID')}
				},'-',
				{
					text : "打印出库发运单",
					iconCls: 'commonPrint',
					handler: function(){GRID_PRINT(_grid_MTS,'FreightManifest.printsofm.action','freightManifestID')}
				},
				{
					xtype: 'tbfill'
				},'-',
				{
					text : "物流明细",
					iconCls: 'trackDetail'
				},
//				'-',
//				{
//					text : "导出数据",
//					iconCls: 'commonExport'
//				},
				{
					xtype: 'tbspacer'
				}]
			}),

			bbar : new Ext.PagingToolbar({
						pageSize : _limit_MTS,
						store : _ds_MTS,
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
						       value: _limit_MTS,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_MTS.baseParams.limit = combo.getValue();
							        	_grid_MTS.getBottomToolbar().pageSize = combo.getValue();
							        	SETFORMPARAMTOSTORE(_formPanel_MTS,_ds_MTS);
							        	_grid_MTS.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
					})
		});

_grid_MTS.on('rowcontextmenu', onRightClick_MTS);

var _rightClickMenu_MTS = new Ext.menu.Menu({
			
	items : [{
				handler: function(){ GRID_EDITDETAIL(_grid_MTS,onDetailTransportationSheet,'freightManifestID'); },
				text : '明细',
				iconCls: 'commonDetail'
			},{
				handler :function(){ GRID_EDITDETAIL(_grid_MTS,onEditTransportationSheet,'freightManifestID') },
				text : '修改',
				iconCls: 'commonEdit'
			}, {
				handler: function(){GRID_DELETE(_grid_MTS,'FreightManifest.delete.action','fmIDList','freightManifestID');},
				text : '删除',
				iconCls: 'commonDelete'
			}, {
				handler: function(){GRID_PRINT(_grid_MTS,'FreightManifest.print.action','freightManifestID')},
				text : '打印',
				iconCls: 'commonPrint'
			}]
});

function onRightClick_MTS(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_MTS);
}
