
var _win_edit_MTSS = null;
var _mask_edit_MTSS = null;
var _count_edit_MTSS = { amount:0,weight:0,volume:0};
var _laststate_edit_MTSS = { storageId:''};
function onEditTransferStorageSheet(id)
{
	if(null == _win_edit_MTSS)
	{
	    createWindow_edit_MTSS();
	}else{
		resetPage_edit_MTSS();
	}
	_mask_edit_MTSS.show();
	_formPanel_editSheet_MTSS.getForm().load({
    	url: 'StockTransferManifest.queryOne.action',
    	method: 'POST',
    	params: {'stmdto.stockTransferManifestID': id},
    	success: function(form,action){
    		if(action.result.resultMapList != null){
	    		_ds_editSheet_MTSS.loadData(action.result,false);
    		}
    		setSheetValue_edit_MTSS(action.result.data);
    		_mask_edit_MTSS.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'入库单数据读取失败！');
    		_mask_edit_MTSS.hide();
    	}
    });
	_win_edit_MTSS.setPagePosition(GET_WIN_X(_win_edit_MTSS.width),GET_WIN_Y_M(600));
    _win_edit_MTSS.show();
}

function setSheetValue_edit_MTSS(data)
{
	_grid_editSheet_MTSS.getBottomToolbar().findById('allCount_edit_MTSS').setText(data['stmdto.sumAmount'].toString());
	_grid_editSheet_MTSS.getBottomToolbar().findById('allWeight_edit_MTSS').setText(data['stmdto.sumWeight'].toString());
	_grid_editSheet_MTSS.getBottomToolbar().findById('allVolume_edit_MTSS').setText(data['stmdto.sumVolume'].toString());
}

function createWindow_edit_MTSS()
{
	_win_edit_MTSS = new Ext.Window({
        title: '修改移库单',
        iconCls: 'commonEditSheet',
        width: 800,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_editSheet_MTSS,
        listeners: LISTENER_WIN_MOVE
    });
    _win_edit_MTSS.show();
    _mask_edit_MTSS = new Ext.LoadMask(_win_edit_MTSS.body, {msg:"正在载入,请稍后..."});
}

var _ds_editSheet_MTSS = new Ext.data.Store({
	reader : new Ext.data.JsonReader({
				root : 'resultMapList'
			}, 
			_record_MTSS
		)
})

	var _sm_editSheet_MTSS = new Ext.grid.CheckboxSelectionModel();
	
	var _cm_editSheet_MTSS = new Ext.grid.ColumnModel({
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_editSheet_MTSS,
			{
				width:80,
				dataIndex : 'dateStockin',
				header : '入库日期'	
			},
			{
				width:60,
				dataIndex : 'itemID',
				header : '物料编号'	
			},{
				width:160,
				dataIndex : 'itemName',
				header : '物料名称'	
			},{
				width:80,
				dataIndex : 'itemNumber',
				header : '物料代码'	
			},{
				width:60,
				dataIndex : 'batch',
				header : '批次'	
			},{
				width:60,
				dataIndex : 'stockAmount',
				header : '库存数量'	
			},{
				width:90,
				dataIndex : 'amount',
				header : '移库数量(编辑)',
				editor : {
					xtype: 'numberfield',
					allowNegative: false,
					value: 1,
					allowDecimals : false,
					maxValue: MAX_DOUBLE,
					minValue: 1,
					allowBlank: false,
					selectOnFocus: true
				}	
			},{
				width:60,
				dataIndex : 'unitWeight',
				header : '单位重量'	
			},{
				width:60,
				dataIndex : 'unitVolume',
				header : '单位体积'
			},{
				width:60,
				dataIndex : 'customerID',
				header : '客户编号'	
			},{
				width:160,
				dataIndex : 'customer',
				header : '客户名称'
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_editSheet_MTSS = new Ext.grid.EditorGridPanel({
				sm : _sm_editSheet_MTSS,
				cm : _cm_editSheet_MTSS,
				ds : _ds_editSheet_MTSS,
				style: 'margin:0',
				stripeRows : true,
				loadMask : true,
				border : true,
				frame: false,
				clicksToEdit : 1,
				height : 200,
				autoScroll: true,
				width: 641,
				draggable : false,
				fieldLabel: '物料列表',
				viewConfig : {
					forceFit : false
				},
				tbar : new Ext.Toolbar({
					border: true,
					items : [
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						text : "移除",
						iconCls: 'commonDelete',
						handler: function(){
							GRID_LIST_DELETE(_grid_editSheet_MTSS);
						}
					},'-',
					{
						xtype: 'tbfill'
					}, 
					{
						text : "批量查询添加",
						iconCls: 'commonQuery',
						handler: function(){
							var storage = _formPanel_editSheet_MTSS.findById('storage_out_edit_MTSS');
							if(storage.getValue()=='')
							{
								Ext.Msg.show({
									title : '操作提示',
									msg : '请先选择调出仓库！',
									buttons : Ext.Msg.OK,
									icon : Ext.Msg.WARNING
								});
								return;
							}
							onQueryTransGoods(storage.getValue(),storage.getRawValue());
						}
					},
					{
						xtype: 'tbspacer',
						width: 5
					}]
				}),
				bbar: new Ext.Toolbar({
					border: true,
					frame: true,
					items : [
					{
						xtype: 'tbspacer',
						width: 10
					},
					{
						xtype: 'label',
						text: '数量合计：'
					},{
						xtype: 'tbspacer'
					},
					{
						xtype: 'tbtext',
						text: '0',
						id: 'allCount_edit_MTSS',
						width: 50
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'label',
						text: '重量合计：'
					},{
						xtype: 'tbspacer'
					},
					{
						xtype: 'tbtext',
						text: '0',
						id: 'allWeight_edit_MTSS',
						width: 50
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'label',
						text: '体积合计：'
					},{
						xtype: 'tbspacer'
					},
					{
						xtype: 'tbtext',
						text: '0',
						id: 'allVolume_edit_MTSS',
						width: 50
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'tbfill'
					},'-',
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						text : "&nbsp;&nbsp;计算合计",
						iconCls: 'calculator',
						handler: function(){
							getOutStorageFee_edit_MTSS();
						}
					},
					{
						xtype: 'tbspacer',
						width: 5
					}]
				})
			});


var _formPanel_editSheet_MTSS = new Ext.FormPanel({
	
	layout: 'form',
	style: 'margin:0px',
	frame: true,
	labelAlign: 'right',
	bodyStyle: PADDING,
	autoHeight: true,
	autoScoll: true,
	labelWidth: 70,
	border: false,
	buttonAlign: 'center',
	
	buttons:[
	{
		text: '保存',
		iconCls: 'commonSave',
		handler: function()
		{
			if(_formPanel_editSheet_MTSS.findById('storage_out_edit_MTSS').getValue()==
				_formPanel_editSheet_MTSS.findById('storage_in_edit_MTSS').getValue())
			{
				Ext.Msg.show({
					title : '操作提示',
					msg : '调出与调入仓库不能相同！',
					buttons : Ext.Msg.OK,
					icon : Ext.Msg.WARNING
				});
				return;
			}
			if(_formPanel_editSheet_MTSS.getForm().isValid())
			{
				var params = getGridParams_edit_MTSS();
				_formPanel_editSheet_MTSS.getForm().submit({
					url: 'StockTransferManifest.update.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						resetPage_edit_MTSS();
						_grid_MTSS.getStore().reload();
						_win_edit_MTSS.hide();
					},
					failure: function(form,action){
						FORM_FAILURE_CALLBACK(form,action,"数据保存失败");
					},
					params: params
				});
			}
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler: function(){_win_edit_MTSS.hide();}
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
				items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_STORAGE,
					       id : 'storage_out_edit_MTSS',
					       valueField : 'warehouseID',
					       displayField : 'name',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'stmdto.outWarehouseID',
					       editable : true,
					       typeAhead : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       selectOnFocus:true,
					       fieldLabel : '调出仓库',
					       width:　200,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY,
					       		select: function(combo, record, index){
					       			if(record.get('warehouseID')!=_laststate_edit_MTSS.storageId)
					       			{
					       				resetGoodsGrid_edit_MTSS();
					       				_laststate_edit_MTSS.storageId = record.get('warehouseID');
					       			}
					       		}
					       }
					})]
			},
			{//Column 2
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '移库单号',
					name: 'stmdto.stockTransferManifestID',
					readOnly:true,
					width: TEXTFIELDWIDTH
				}]
			},
			{//Column 3
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '建单日期',
					width: TEXTFIELDWIDTH,
					readOnly: true,
					name: 'stmdto.dateCreated'
				}]
			}]
		},
		{//Row 2
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.4',
				layout: 'form',
				border: false,
				items: [new Ext.form.ComboBox({
					       xtype : 'combo',
					       id:'storage_in_edit_MTSS',
					       store : STORE_STORAGE,
					       valueField : 'warehouseID',
					       displayField : 'name',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'stmdto.inWarehouseID',
					       editable : true,
					       typeAhead : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       selectOnFocus:true,
					       fieldLabel : '调入仓库',
					       width:　200,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
					       }
					})]
			},
			{//Column 2
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'datefield',
					fieldLabel: '移库日期',
					format: 'Y-m-d',
					name: 'stmdto.dateTransfered',
					value: new Date().dateFormat('Y-m-d'),
					allowBlank: false,
					editable: false,
					width: TEXTFIELDWIDTH
				}]
			},
			{//Column 3
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'textfield',
					fieldLabel: '操作员',
					width: TEXTFIELDWIDTH,
					allowBlank: false,
					name: 'stmdto.operator',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT
				}]
			}]
		},
		{//Row 4-Grid
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1.0',
				layout: 'form',
				border: false,
				items: _grid_editSheet_MTSS
			}]
		},
		{//Row 5
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'stmdto.loadUnloadCost',
					fieldLabel: '装卸费',
					width: 80
				}]
			},
			{//Column 2
				columnWidth: '0.7',
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
					       hiddenName : 'stmdto.costCenter',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '成本中心',
					       width:　TEXTFIELDWIDTH,
					       listeners: {
					       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
					       }
					})]
			}]
		},
		{//Row 5
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1.0',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '备注信息',
					regex: REGEX_COMMON_M,
					regexText: REGEX_COMMON_M_TEXT,
					name: 'stmdto.remarks',
					width: 641
				}]
			}]
		}]
});

function getOutStorageFee_edit_MTSS()
{
	var store = _grid_editSheet_MTSS.getStore();
	if(store.getCount()<=0) return;
	var allCount = 0; var allWeight = 0; var allVolume = 0;
	for(var i=0; i<store.getCount();i++)
	{
		var record = store.getAt(i);
		allCount += record.get('amount');
		allWeight += record.get('unitWeight') * record.get('amount');
		allVolume += record.get('unitVolume') * record.get('amount');
	}
	allCount=CHANGEDECIMAL(allCount);
	allVolume=CHANGEDECIMAL(allVolume);
	allWeight=CHANGEDECIMAL(allWeight);
	_count_edit_MTSS.amount = allCount;
	_count_edit_MTSS.volume = allVolume;
	_count_edit_MTSS.weight = allWeight;
	_grid_editSheet_MTSS.getBottomToolbar().findById('allCount_edit_MTSS').setText(allCount.toString());
	_grid_editSheet_MTSS.getBottomToolbar().findById('allWeight_edit_MTSS').setText(allWeight.toString());
	_grid_editSheet_MTSS.getBottomToolbar().findById('allVolume_edit_MTSS').setText(allVolume.toString());
}

function getGridParams_edit_MTSS()
{
	params = {};
	var store = _grid_editSheet_MTSS.getStore();
	var count = store.getCount();
	for(var i=0; i<count; i++)
	{
		var record = store.getAt(i);
		params['itemList['+i.toString()+'].itemID']=record.get('itemID');
		params['itemList['+i.toString()+'].amount']=record.get('amount');
		params['itemList['+i.toString()+'].dateStockin']=record.get('dateStockin');
		params['itemList['+i.toString()+'].customerID']=record.get('customerID');
	}
	
	params['stmdto.sumAmount'] = _count_edit_MTSS.amount;
	params['stmdto.sumWeight'] = _count_edit_MTSS.weight;
	params['stmdto.sumVolume'] = _count_edit_MTSS.volume;
	
	return params;
}

function resetGoodsGrid_edit_MTSS()
{
	onCloseQueryTransGoods();
	_ds_editSheet_MTSS.removeAll();
	_grid_editSheet_MTSS.getBottomToolbar().findById('allCount_edit_MTSS').setText('0');
	_grid_editSheet_MTSS.getBottomToolbar().findById('allWeight_edit_MTSS').setText('0');
	_grid_editSheet_MTSS.getBottomToolbar().findById('allVolume_edit_MTSS').setText('0');
	_count_edit_MTSS.amount = 0;
	_count_edit_MTSS.volume = 0;
	_count_edit_MTSS.weight = 0;
}

function resetPage_edit_MTSS()
{
	_formPanel_editSheet_MTSS.getForm().reset();
	_ds_editSheet_MTSS.removeAll();
	_grid_editSheet_MTSS.getBottomToolbar().findById('allCount_edit_MTSS').setText('0');
	_grid_editSheet_MTSS.getBottomToolbar().findById('allWeight_edit_MTSS').setText('0');
	_grid_editSheet_MTSS.getBottomToolbar().findById('allVolume_edit_MTSS').setText('0');
	_count_edit_MTSS.amount = 0;
	_count_edit_MTSS.volume = 0;
	_count_edit_MTSS.weight = 0;
}
