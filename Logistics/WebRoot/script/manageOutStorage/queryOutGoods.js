var _win_query_QOG = null;
var _type_query_QOG = 1;
function onCloseQueryOutGoods()
{
	if(_win_query_QOG!=null)
		_win_query_QOG.hide();
}
function onQueryOutGoods(type,customerId,storageId,customerName,storageName)
{
	_type_query_QOG = type;
	if(null == _win_query_QOG)
	{
	    createWindow_query_QOG();
	    STORE_GOODS_LOAD();
	}
	_win_query_QOG.setTitle('查询添加出库物料&nbsp;[&nbsp;客户：'+customerName+'&nbsp;]&nbsp;[&nbsp;库存地：'+storageName+'&nbsp;]');
	_ds_QOG.baseParams['sidto.customerID']=customerId;
	_ds_QOG.baseParams['sidto.warehouseID']=storageId;
	_ds_QOG.baseParams.start = 0;
	_ds_QOG.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_QOG.reader.message,_ds_QOG.reader.valid,success);
		}
	});
	_win_query_QOG.setPagePosition(GET_WIN_X(_win_query_QOG.width),GET_WIN_Y_M(400));
    _win_query_QOG.show();
}

function createWindow_query_QOG()
{
	_win_query_QOG = new Ext.Window({
        title: '查询添加出库物料',
        iconCls: 'commonQuery',
        layout: 'border',
        width: 700,
        height: 400,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: [_formPanel_QOG, _grid_QOG],
        listeners: LISTENER_WIN_MOVE
    });
}

var _limit_QOG = 30;

var _formPanel_QOG = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_QOG,_ds_QOG))
					return;
				_ds_QOG.baseParams.start = 0;
				_ds_QOG.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_QOG.reader.message,_ds_QOG.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function()
			{
				_formPanel_QOG.getForm().reset();
			}
		},{
			text: '添加',
			iconCls: 'commonAdd',
			handler: function(){
				var _grid = null;
				if(_type_query_QOG==1)
				{
					_grid=_grid_createSheet_MOSS;
				}else
				{
					_grid=_grid_editSheet_MOSS;
				}
				var records = _grid_QOG.getSelectionModel().getSelections();
				if(records.length <= 0)
					Ext.Msg.show({
						title : '操作提示',
						msg : '至少选择一条记录！',
						buttons : Ext.Msg.OK,
						icon : Ext.Msg.WARNING
					});
				else
				{
					var count = _grid.getStore().getCount();
					for(var i=0; i<records.length; i++)
					{
						if(count++ >= MAX_STORAGE_GOODS_COUNT)
						{
							Ext.Msg.show({
								title : '操作提示',
								msg : '物料种类不能超过['+MAX_STORAGE_GOODS_COUNT.toString()+']种！',
								buttons : Ext.Msg.OK,
								icon : Ext.Msg.WARNING
							});
							return;
						}
						var field = ['itemID','dateStockin'];
						var value = [records[i].get('itemID'),records[i].get('stockinDate')];
						if(!ISINLIST(_grid,field,value))
						{//判断列表中是否有相同的值
							var record = new _record_MOSS({
							    itemID: records[i].get('itemID'),
							    itemName: records[i].get('itemName'),
							    itemNumber: records[i].get('itemNumber'),
							    batch: records[i].get('batch'),
							    unitWeight: records[i].get('unitWeight'),
							    unitVolume: records[i].get('unitVolume'),
							    stockAmount: records[i].get('stockAmount'),
							    amount: 1,
							    weight: records[i].get('weight'),
							    volume: records[i].get('volume'),
							    dateStockin :records[i].get('stockinDate')
							});
							_grid.getStore().add(record);
						}
					}
					_grid_QOG.getSelectionModel().clearSelections();
				}
			}
		},{
			text: '关闭',
			iconCls: 'commonCancel',
			handler: function()
			{
				_win_query_QOG.hide();
			}
		}],
		
		items:[
			{//Row 1
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '0.4',
					layout: 'form',
					border: false,
					items:[
					{
						xtype : 'combo',
				       store : STORE_GOODS,
				       valueField : 'itemID',
				       displayField : 'jointName',
				       mode : 'local',
				       forceSelection : true,
				       hiddenName : 'sidto.itemID',
				       editable : false,
				       selectOnFocus:true,
				       typeAhead: false,
				       editable : true,
				       triggerAction : 'all',
				       allowBlank : true,
				       fieldLabel : '物料选择',
				       width: 150,
				       listeners: {
				       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
				       }
					}]
				},
				{//Column 2
					columnWidth: '0.3',
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
				},
				{//Column 3
					columnWidth: '0.3',
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

	var _ds_QOG = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'StockoutManifest.queryItems.action'
				}),
		reader : new Self.JsonReader({
					totalProperty : 'qualifiedAmount',
					root : 'resultMapList'
				}, 
				[{
					name : 'stockinDate'
				}, {
					name : 'customer'
				}, {
					name : 'itemID'
				}, {
					name : 'itemName'
				}, {
					name : "itemNumber"
				}, {
					name : 'batch'
				}, {
					name : 'stockAmount'
				}, {
					name : 'weight'
				}, {
					name : 'volume'
				}, {
					name : 'unit'
				}, {
					name : 'unitWeight'
				}, {
					name : 'unitVolume'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_QOG,
				'sidto.customerID':'',
				'sidto.warehouseID':''
			}
	});

	var _sm_QOG = new Ext.grid.CheckboxSelectionModel();
	var _cm_QOG = new Ext.grid.ColumnModel({
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_QOG,
			{
				dataIndex : 'stockinDate',
				header : '入库日期'	
			},
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
				dataIndex : 'stockAmount',
				header : '库存数量'	
			},{
				dataIndex : 'customer',
				header : '所属客户'	
			},{
				dataIndex : 'weight',
				header : '重量'
			},{
				dataIndex : 'volume',
				header : '体积'	
			},{
				dataIndex : 'unit',
				header : '物料单位'	
			},{
				dataIndex : 'unitWeight',
				header : '单位重量'
			},{
				dataIndex : 'unitVolume',
				header : '单位体积'
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_QOG = new Ext.grid.GridPanel({
				region: 'center',
				sm : _sm_QOG,
				cm : _cm_QOG,
				ds : _ds_QOG,
				style: 'margin:0',
				stripeRows : true,
				loadMask : true,
				border : false,
				autoScroll: true,
				draggable : false,
				viewConfig : {
					forceFit : false
				},
				bbar : new Ext.PagingToolbar({
							pageSize : _limit_QOG,
							store : _ds_QOG,
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
							       value: _limit_QOG,
							       width:　DATAFIELDWIDTH,
								   listeners : {
								    	select : function(combo, record, index) {
								        	_ds_QOG.baseParams.limit = combo.getValue();
								        	_grid_QOG.getBottomToolbar().pageSize = combo.getValue();
								        	SETFORMPARAMTOSTORE(_formPanel_QOG,_ds_QOG);
								        	_grid_QOG.getBottomToolbar().changePage(1);
								   		}//select
								   }
							})]
						})

			});
