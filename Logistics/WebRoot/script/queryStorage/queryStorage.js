function update_queryStorage(contentPanel,node)
{
    
}

function init_queryStorage(id)
{
	var page = Ext.getCmp(id);
	page.add(_formPanel_QS);
	page.add(_grid_QS);
	STORE_STORAGE_LOAD();
	_ds_QS.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_QS.reader.message,_ds_QS.reader.valid,success);
		}
	});
}
var _limit_QS = 30;

var _formPanel_QS = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_QS,_ds_QS))
					return;
				_ds_QS.baseParams.start = 0;
				_ds_QS.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_QS.reader.message,_ds_QS.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function(){ _formPanel_QS.getForm().reset();}
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
						fieldLabel: '物料编号',
						name: 'itemdto.itemID',
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
						fieldLabel: '物料名称',
						name: 'itemdto.name',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 3
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'textfield',
						fieldLabel: '客户编号',
						name: 'cusdto.customerID',
						regex: REGEX_COMMON_T,
						regexText: REGEX_COMMON_T_TEXT,
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 4
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_STORAGE,
					       valueField : 'warehouseID',
					       displayField : 'name',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'wdto.warehouseID',
					       editable : true,
					       typeAhead : false,
					       triggerAction : 'all',
					       allowBlank : true,
					       selectOnFocus:true,
					       fieldLabel : '库存地',
					       value: '',
					       width:　TEXTFIELDWIDTH,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
					       }
					})]
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
						fieldLabel: '物料代码',
						name: 'itemdto.number',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
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
						fieldLabel: '批次',
						name: 'itemdto.batch',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 3
					columnWidth: '0.5',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '客户名称',
						name: 'cusdto.name',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				}]
			}]
	});
	var _ds_QS = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'StockItem.queryOnCondition.action'
				}),
		reader : new Ext.data.JsonReader({
					totalProperty : 'qualifiedAmount',
					root : 'resultMapList'
				}, 
				[{
					name : 'itemID'
				}, {
					name : 'itemName'
				}, {
					name : 'itemNumber'
				}, {
					name : "batch"
				}, {
					name : "sumAmount"
				}, {
					name : 'warehouseID'
				}, {
					name : 'warehouseName'
				}, {
					name : 'unit'
				}, {
					name : 'unitVolume'
				}, {
					name : 'unitWeight'
				}, {
					name : 'customerID'
				}, {
					name : 'customerName'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_MESS
			}
	});
	var _sm_QS = new Ext.grid.CheckboxSelectionModel();
	var _cm_QS = new Ext.grid.ColumnModel({
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_QS,
			{
				dataIndex : 'itemID',
				header : '物料编号'	
			},{
				header : '物料名称',
				dataIndex : 'itemName'
			},{
				header : '物料代码',
				dataIndex : 'itemNumber'
			},{
				header : '批次',
				dataIndex : 'batch'
			},{
				dataIndex : 'sumAmount',
				header : '库存数量'	
			},{
				dataIndex : 'warehouseID',
				header : '库存地代码'
			},{
				dataIndex : 'warehouseName',
				header : '库存地'
			},{
				dataIndex : 'unit',
				header : '物料单位'	
			},{
				dataIndex : 'unitWeight',
				header : '单位重量'
			},{
				dataIndex : 'unitVolume',
				header : '单位体积'
			},{
				dataIndex : 'customerID',
				header : '客户编号'
			},{
				dataIndex : 'customerName',
				header : '客户名称'
			}],
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});

var _grid_QS = new Ext.grid.GridPanel({
	region: 'center',
	sm : _sm_QS,
	cm : _cm_QS,
	ds : _ds_QS,
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
			text : "物料操作明细查询",
			iconCls: 'trackDetail',
			handler:function(){
				onDetailGoodsTrack();
			}
		},
		{
			xtype: 'tbfill'
		},
//		{
//			text : "导出数据",
//			iconCls: 'commonExport'
//		},
		{
			xtype: 'tbspacer'
		}]
	}),

	bbar : new Ext.PagingToolbar({
		pageSize : _limit_QS,
		store : _ds_QS,
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
		       value: _limit_QS,
		       width:　DATAFIELDWIDTH,
			   listeners : {
			    	select : function(combo, record, index) {
			        	_ds_QS.baseParams.limit = combo.getValue();
			        	_grid_QS.getBottomToolbar().pageSize = combo.getValue();
			        	SETFORMPARAMTOSTORE(_formPanel_QS,_ds_QS);
			        	_grid_QS.getBottomToolbar().changePage(1);
			   		}//select
			   }
		})]
	})

});