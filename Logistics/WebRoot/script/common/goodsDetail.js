
var _win_detail_SF_COMMON = null;

function onDetailGoodsTrack()
{
	if(null == _win_detail_SF_COMMON)
	{
	    createWindow_detail_SF_COMMON();
	}else
	{
		_ds_SF_COMMON.removeAll();
	}
	_win_detail_SF_COMMON.setPagePosition(GET_WIN_X(_win_detail_SF_COMMON.width),GET_WIN_Y());
    _win_detail_SF_COMMON.show();
}

function createWindow_detail_SF_COMMON()
{
	_win_detail_SF_COMMON = new Ext.Window({
        title: '物料操作明细查询',
        iconCls: 'trackDetail',
        width: 700,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: [_formPanel_SF_COMMON,_grid_detailTrack_SF_COMMON],
        listeners: LISTENER_WIN_MOVE
    });
}
var _formPanel_SF_COMMON = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_SF_COMMON,_ds_SF_COMMON))
					return;
				_ds_SF_COMMON.baseParams.start = 0;
				_ds_SF_COMMON.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_SF_COMMON.reader.message,_ds_SF_COMMON.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function()
			{
				_formPanel_SF_COMMON.getForm().reset();
			}
		},{
			text: '关闭',
			iconCls: 'commonCancel',
			handler: function()
			{
				_win_detail_SF_COMMON.hide();
			}
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
						fieldLabel: '物料编号',
						name: 'itemID',
						allowBlank:false,
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 2
					columnWidth: '0.33',
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
			},{//Row 1
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
					       hiddenName:'warehouseID',
					       valueField : 'warehouseID',
					       displayField : 'name',
					       mode : 'local',
					       forceSelection : true,
					       editable : true,
					       typeAhead : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       selectOnFocus:true,
					       fieldLabel : '库存地',
					       width:　TEXTFIELDWIDTH,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
					       }
					})]
				},
				{//Column 2
					columnWidth: '0.67',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '客户编号',
						name: 'customerID',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: DATAFIELDWIDTH
					}]
				}]
			}]
	});

var _limit_SF_COMMON = 50;

var _ds_SF_COMMON = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'StockItem.queryItemHistory.action'
				}),
		reader : new Self.JsonReader({
					root : 'resultMapList'
				}, 
				[{
					name : 'id'
				}, {
					name : 'date'
				}, {
					name : 'type'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_SF_COMMON
			}
	});
	
	var _cm_detailTrack_SF_COMMON = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
			{
				dataIndex : 'date',
				header : '操作日期',
				width: 60
			},{
				dataIndex : 'type',
				header : '操作类型'
			},{
				dataIndex : 'id',
				header : '操作单号'
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_detailTrack_SF_COMMON = new Ext.grid.GridPanel({
		cm : _cm_detailTrack_SF_COMMON,
		ds : _ds_SF_COMMON,
		style: 'margin:0px 0px 0px 0px',
		stripeRows : true,
		loadMask : true,
		border : true,
		frame: false,
		height : 300,
		autoScroll: true,
		draggable : false,
		hideLabel: true,
		viewConfig : {
			forceFit : true
		},
		bbar: new Ext.PagingToolbar({
				pageSize : _limit_SF_COMMON,
				store : _ds_SF_COMMON,
				displayInfo : true,
				width : 500,
				displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
				emptyMsg : "没有记录"
			})
	});

