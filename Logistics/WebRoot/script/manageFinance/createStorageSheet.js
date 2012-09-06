var _win_query_CSSF = null;
var _win_mask_CSSF = null;
function onCloseStorageFee()
{
	if(_win_query_CSSF!=null)
		_win_query_CSSF.hide();
	if(_win_mask_CSSF!=null)
		_win_mask_CSSF.hide();
}
function onCreateStorageFee()
{
	if(null == _win_query_CSSF)
	{
	    createWindow_query_CSSF();
	}
	_win_mask_CSSF.hide();
	_ds_CSSF.baseParams.start = 0;
	_ds_CSSF.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_CSSF.reader.message,_ds_CSSF.reader.valid,success);
		}
	});
	_win_query_CSSF.setPagePosition(GET_WIN_X(_win_query_CSSF.width),GET_WIN_Y_M(500));
    _win_query_CSSF.show();
}

function createWindow_query_CSSF()
{
	_win_query_CSSF = new Ext.Window({
        title: '新建结算',
        iconCls: 'commonAdd',
        layout: 'border',
        width: 1000,
        height: 500,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: [_formPanel_CSSF, _grid_CSSF],
        listeners: {
        	move:LISTENER_WIN_MOVE_FUNC,
        	beforehide:function()
        	{
        		_ds_SF.reload();
        	}
        }
    });
    _win_query_CSSF.show();
    _win_mask_CSSF = new Ext.LoadMask(_win_query_CSSF.body, {msg:"正在提交,请稍后..."});
}

var _limit_CSSF = 30;

var _formPanel_CSSF = new Ext.FormPanel({
	
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
				var today = new Date();
				var conDate = _formPanel_CSSF.findById('con_endDate_CSSF').getValue();
				if((today-conDate)/1000/60/60/24<1)
				{
					Ext.Msg.show({
						title : '操作提示',
						msg : '请选择结算日期在今天之前！',
						buttons : Ext.Msg.OK,
						icon : Ext.Msg.WARNING
					});	
					return;
				}
				if(!SETFORMPARAMTOSTORE(_formPanel_CSSF,_ds_CSSF))
					return;
				_ds_CSSF.baseParams.start = 0;
				_ds_CSSF.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_CSSF.reader.message,_ds_CSSF.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function()
			{
				_formPanel_CSSF.getForm().reset();
			}
		},{
			text: '结算',
			iconCls: 'commonAdd',
			handler: function(){
				var records = _grid_CSSF.getSelectionModel().getSelections();
				if(records.length <= 0)
					Ext.Msg.show({
						title : '操作提示',
						msg : '至少选择一条记录！',
						buttons : Ext.Msg.OK,
						icon : Ext.Msg.WARNING
					});
				else
				{
					var params = {};
					for(var i=0; i<records.length; i++)
					{
						params['itemList['+i.toString()+'].itemID'] = records[i].get('itemID');
						params['itemList['+i.toString()+'].warehouseID'] = records[i].get('warehouseID');
						params['itemList['+i.toString()+'].customerID'] = records[i].get('customerID');
						params['itemList['+i.toString()+'].stockinDate'] = records[i].get('stockinDate');
					}
					_win_mask_CSSF.show();
					Ext.Ajax.request({
						url: 'StockItem.account.action',
						method: 'POST',
						success : function(response,options){
							var result = Ext.util.JSON.decode(response.responseText);
							if(!result.success){
								AJAX_FAILURE_CALLBACK(result,'数据提交错误!');
							}else{
								_ds_CSSF.reload();
								_grid_CSSF.getSelectionModel().clearSelections();
							}
							_win_mask_CSSF.hide();
						},
						failure: function(response,options){
							AJAX_SERVER_FAILURE_CALLBACK(response,options,'数据提交失败！');
							_win_mask_CSSF.hide();
						},
						params:params
					});//Ajax
				}
			}
		},
		{
			text: '关闭',
			iconCls: 'commonCancel',
			handler: function()
			{
				_win_query_CSSF.hide();
			}
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
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_STORAGE,
					       valueField : 'warehouseID',
					       displayField : 'name',
					       mode : 'local',
					       forceSelection : false,
					       hiddenName : 'wdto.warehouseID',
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
				{//Column 4
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'datefield',
						fieldLabel: '上次结算',
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
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '客户编号',
						name: 'cusdto.customerID',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				},
				{//Column 4
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'datefield',
						fieldLabel: '至',
						id:'con_endDate_CSSF',
						format: 'Y-m-d',
						name: 'endDate',
						value: new Date().add(Date.DAY,-1).dateFormat('Y-m-d'),
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
						fieldLabel: '客户名称',
						name: 'cusdto.name',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				}]
			}]
	});

	var _ds_CSSF = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'StockFinance.queryStockItems.action'
				}),
		reader : new Self.JsonReader({
					totalProperty : 'qualifiedAmount',
					root : 'resultMapList'
				}, 
				[{
					name : 'itemID'
				}, {
					name : 'stockinDate'
				}, {
					name : 'lastAccountDate'
				}, {
					name : 'itemName'
				}, {
					name : "itemNumber"
				}, {
					name : 'batch'
				}, {
					name : 'unit'
				}, {
					name : 'amount'
				}, {
					name : 'warehouse'
				}, {
					name : 'customer'
				}, {
					name : 'warehouseID'
				}, {
					name : 'customerID'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_CSSF,
				endDate: new Date().add(Date.DAY,-1).dateFormat('Y-m-d')
			}
	});

	var _sm_CSSF = new Ext.grid.CheckboxSelectionModel();
	var _cm_CSSF = new Ext.grid.ColumnModel({
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_CSSF,
			{
				dataIndex : 'stockinDate',
				header : '入库日期'	
			},
			{
				dataIndex : 'lastAccountDate',
				header : '上次结算日期'	
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
				dataIndex : 'amount',
				header : '库存数量'	
			},{
				dataIndex : 'unit',
				header : '物料单位'	
			},{
				dataIndex : 'warehouse',
				header : '库存地'
			},{
				dataIndex : 'customer',
				header : '客户名称'
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_CSSF = new Ext.grid.GridPanel({
				region: 'center',
				sm : _sm_CSSF,
				cm : _cm_CSSF,
				ds : _ds_CSSF,
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
							pageSize : _limit_CSSF,
							store : _ds_CSSF,
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
							       value: _limit_CSSF,
							       width:　DATAFIELDWIDTH,
								   listeners : {
								    	select : function(combo, record, index) {
								        	_ds_CSSF.baseParams.limit = combo.getValue();
								        	_grid_CSSF.getBottomToolbar().pageSize = combo.getValue();
								        	SETFORMPARAMTOSTORE(_formPanel_CSSF,_ds_CSSF);
								        	_grid_CSSF.getBottomToolbar().changePage(1);
								   		}//select
								   }
							})]
						})

			});
