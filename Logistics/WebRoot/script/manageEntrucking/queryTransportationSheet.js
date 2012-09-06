
var _win_query_QTS = null;
var _type_query_QTS = 1;
function onQueryTransportationSheet(type)
{
	_type_query_QTS = type;
	if(null == _win_query_QTS)
	{
	    createWindow_query_QTS();
	}
	_ds_QTS.baseParams.start = 0;
	_ds_QTS.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_QTS.reader.message,_ds_QTS.reader.valid,success);
		}
	});
	_win_query_QTS.setPagePosition(GET_WIN_X(_win_query_QTS.width),GET_WIN_Y_M(400));
    _win_query_QTS.show();
}

function createWindow_query_QTS()
{
	_win_query_QTS = new Ext.Window({
        title: '查询添加运输单',
        iconCls: 'commonQuery',
        layout: 'border',
        width: 800,
        height: 483,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: [_formPanel_QTS, _grid_QTS],
        listeners: LISTENER_WIN_MOVE
    });
}

var _limit_QTS = 30;

var _formPanel_QTS = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_QTS,_ds_QTS))
					return;
				_ds_QTS.baseParams.start = 0;
				_ds_QTS.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_QTS.reader.message,_ds_QTS.reader.valid,success);
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function()
			{
				_formPanel_QTS.getForm().reset();
			}
		},{
			text: '添加',
			iconCls: 'commonAdd',
			handler: function(){
				var _grid = null;
				if(_type_query_QTS==1)
				{
					_grid = _grid_createSheet_MES;
				}else
				{
					_grid = _grid_editSheet_MES;
				}
				var records = _grid_QTS.getSelectionModel().getSelections();
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
						if(count++ >= MAX_TRANS_SHEET_COUNT)
						{
							Ext.Msg.show({
								title : '操作提示',
								msg : '运输单数量不能超过['+MAX_TRANS_SHEET_COUNT.toString()+']单！',
								buttons : Ext.Msg.OK,
								icon : Ext.Msg.WARNING
							});
							return;
						}
						var field = ['freightManifestID'];
						var value = [records[i].get('freightManifestID')];
						if(!ISINLIST(_grid,field,value))
						{
							var record = new _record_MOSS({
							    freightManifestID: records[i].get('freightManifestID'),
							    origin:records[i].get('origin'),
							    destination:records[i].get('destination'),
							    customer:records[i].get('customer'),
							    amount: records[i].get('amount'),
							    weight: records[i].get('weight'),
							    volume: records[i].get('volume')
							});
							_grid.getStore().add(record);
						}
					}
					_grid_QTS.getSelectionModel().clearSelections();
				}
			}
		},{
			text: '关闭',
			iconCls: 'commonCancel',
			handler: function()
			{
				_win_query_QTS.hide();
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
						fieldLabel: '运输单号',
						name: 'fmdto.freightManifestID',
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
				{//Column 2
					columnWidth: '0.5',
					layout: 'form',
					border: false,
					items: [
					{
						xtype: 'checkbox',
						fieldLabel: ' ',
						checked: true,
						name:'XXX',
						boxLabel:'只查询未装车运输单',
						labelSeparator: ' ',
						width: 200,
						listeners:{
							check:function(the,checked)
							{
								if(checked)
								{
									_ds_QTS.baseParams.isntShipped = true;
								}else
								{
									_ds_QTS.baseParams.isntShipped = false;
								}
							}
						}
					}]
				},
				{//Column 3
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items: new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_ENTRUCK_TRANS_DATATYPE,
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
			}]
	});

	var _ds_QTS = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'FreightManifest.queryOnCondition.action'
				}),
		reader : new Self.JsonReader({
					totalProperty : 'qualifiedAmount',
					root : 'resultMapList'
				}, 
				[{
					name : 'freightManifestID'
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
					name : 'consignee'
				}, {
					name : 'consigneeCompany'
				}, {
					name : 'consigneePhone'
				}, {
					name : 'consigner'
				}, {
					name : 'consignerPhone'
				}, {
					name : 'amount'
				}, {
					name : 'weight'
				}, {
					name : 'volume'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_QTS,
				isntShipped: true
			}
	});

	var _sm_QTS = new Ext.grid.CheckboxSelectionModel();
	var _cm_QTS = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_QTS,
			{
				dataIndex : 'freightManifestID',
				header : '运输单号'	
			},{
				header : '建单日期',
				dataIndex : 'dateCreated'
			},{
				header : '预计到货日期',
				dataIndex : 'dateExpected'
			},{
				header : '数量',
				dataIndex : 'amount'
			},{
				header : '重量(KG)',
				dataIndex : 'weight'
			},{
				header : '体积(M3)',
				dataIndex : 'volume'
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
	
	var _grid_QTS = new Ext.grid.GridPanel({
		
				region: 'center',
				sm : _sm_QTS,
				cm : _cm_QTS,
				ds : _ds_QTS,
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
							pageSize : _limit_QTS,
							store : _ds_QTS,
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
						       value: _limit_QTS,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_QTS.baseParams.limit = combo.getValue();
							        	_grid_QTS.getBottomToolbar().pageSize = combo.getValue();
							        	SETFORMPARAMTOSTORE(_formPanel_QTS,_ds_QTS);
							        	_grid_QTS.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
						})

			});
