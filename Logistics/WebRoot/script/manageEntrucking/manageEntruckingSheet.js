//Switch SubPage
function update_manageEntruckingSheet(contentPanel,node)
{
    
}

function init_manageEntruckingSheet(id)
{
	var page = Ext.getCmp(id);
	page.add(_formPanel_MES);
	page.add(_grid_MES);
	STORE_CITY_LOAD();
	_ds_MES.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_MES.reader.message,_ds_MES.reader.valid,success);
		}
	});
}

var _limit_MES = 30;

var _formPanel_MES = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_MES,_ds_MES))
					return;
				_ds_MES.baseParams.start = 0;
				_ds_MES.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_MES.reader.message,_ds_MES.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function(){ _formPanel_MES.getForm().reset();}
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
						name: 'smdto.shipmentManifestID',
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
						   typeAhead: true,
					       forceSelection : false,
					       hiddenName : 'smdto.originCity',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : true,
					       emptyText: '选择始发市\县',
					       fieldLabel : '始发地',
					       width:　TEXTFIELDWIDTH,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
					       }
					})
				]
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
						   typeAhead: true,
					       forceSelection : false,
					       hiddenName : 'smdto.originProvince',
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
						fieldLabel: '承运单位',
						name: 'smdto.contractor',
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
						   typeAhead: true,
					       forceSelection : false,
					       hiddenName : 'smdto.destinationCity',
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
						   typeAhead: true,
					       forceSelection : false,
					       hiddenName : 'smdto.destinationProvince',
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
			}]
	});

	var _ds_MES = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'ShipmentManifest.queryOnCondition.action'
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
					name : 'contractor'
				}, {
					name : "origin"
				}, {
					name : 'destination'
				}, {
					name : 'carID'
				}, {
					name : 'driverName'
				}, {
					name : 'driverPhone'
				}, {
					name : 'carType'
				}, {
					name : 'consigneeCompany'
				}, {
					name : 'consignee'
				}, {
					name : 'consigneePhone'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_MES
			}
	});

	var _sm_MES = new Ext.grid.CheckboxSelectionModel();

	var _cm_MES = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_MES,
			{
				dataIndex :'shipmentManifestID',
				header : '装车单号'	
			},{
				header : '建单日期',
				dataIndex : 'dateCreated'
			},{
				dataIndex : 'contractor',
				header : '承运单位'
			},{
				dataIndex : 'origin',
				header : '始发地'	
			},{
				dataIndex : 'destination',
				header : '目的地'	
			},{
				dataIndex : 'consigneeCompany',
				header : '收货单位'
			},{
				dataIndex : 'consignee',
				header : '收货人'
			},{
				dataIndex : 'consigneePhone',
				header : '收货人电话'
			},{
				dataIndex : 'carID',
				header : '车牌号'	
			},{
				dataIndex : 'carType',
				header : '车辆类型'
			},{
				dataIndex : 'driverName',
				header : '驾驶员'
			},{
				dataIndex : 'driverPhone',
				header : '驾驶员电话'
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_MES = new Ext.grid.GridPanel({
		
				region: 'center',
				sm : _sm_MES,
				cm : _cm_MES,
				ds : _ds_MES,
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
					},
					{
						text : "新建",
						iconCls: 'commonAdd',
						handler: onCreateEntruckingSheet
					},
					{
						text : "修改",
						iconCls: 'commonEdit',
						handler: function(){
							GRID_EDITDETAIL(_grid_MES,onEditEntruckingSheet,'shipmentManifestID');
						}
					}, 
					{
						text : "删除",
						iconCls: 'commonDelete',
						handler: function(){
							GRID_DELETE(_grid_MES,'ShipmentManifest.delete.action','smIDList','shipmentManifestID');
						}
					},'-',
					{
						text : "查看明细",
						iconCls: 'commonDetail',
						handler: function(){
							GRID_EDITDETAIL(_grid_MES,onDetailEntruckingSheet,'shipmentManifestID');
						}
					},'-',
					{
						text : "打印",
						iconCls: 'commonPrint',
						handler: function(){GRID_PRINT(_grid_MES,'ShipmentManifest.print.action','shipmentManifestID')}
					},
					{
						xtype: 'tbfill'
					},
//					{
//						text : "导出数据",
//						iconCls: 'commonExport'
//					},
					{
						xtype: 'tbspacer'
					}]
				}),

				bbar : new Ext.PagingToolbar({
							pageSize : _limit_MES,
							store : _ds_MES,
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
							       value: _limit_MES,
							       width:　DATAFIELDWIDTH,
								   listeners : {
								    	select : function(combo, record, index) {
								        	_ds_MES.baseParams.limit = combo.getValue();
								        	_grid_MES.getBottomToolbar().pageSize = combo.getValue();
								        	SETFORMPARAMTOSTORE(_formPanel_MES,_ds_MES);
								        	_grid_MES.getBottomToolbar().changePage(1);
								   		}//select
								   }
							})]
						})

			});


	_grid_MES.on('rowcontextmenu', onRightClick_MES);
	
	var _rightClickMenu_MES = new Ext.menu.Menu({
				
				items : [{
							handler: function(){ GRID_EDITDETAIL(_grid_MES,onDetailEntruckingSheet,'shipmentManifestID'); },
							text : '明细',
							iconCls: 'commonDetail'
						},{
							handler :function(){ GRID_EDITDETAIL(_grid_MES,onEditEntruckingSheet,'shipmentManifestID'); },
							text : '修改',
							iconCls: 'commonEdit'
						}, {
							handler: function(){ GRID_DELETE(_grid_MES,'ShipmentManifest.delete.action','smIDList','shipmentManifestID'); },
							text : '删除',
							iconCls: 'commonDelete'
						}, {
							handler: function(){GRID_PRINT(_grid_MES,'ShipmentManifest.print.action','shipmentManifestID')},
							text : '打印',
							iconCls: 'commonPrint'
						}]
			});

	function onRightClick_MES(grid, index, e) {
		ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_MES);
	}

