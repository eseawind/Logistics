
var _win_create_MS = null;
var _laststate_create_MS = { storageId:'',type:'全盘'};
function onCreateStocktake()
{
	if(null == _win_create_MS)
	{
	    createWindow_create_MS();
	}else
	{
		resetPage_create_MS();
	}
	_win_create_MS.setPagePosition(GET_WIN_X(_win_create_MS.width),GET_WIN_Y_M(600));
    _win_create_MS.show();
}

function createWindow_create_MS()
{
	_win_create_MS = new Ext.Window({
        title: '新建盘点单',
        iconCls: 'commonCreateSheet',
        width: 800,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createSheet_MS,
        listeners: LISTENER_WIN_MOVE
    });
}

var _record_MS = Ext.data.Record.create([
    {name: 'itemID'},
    {name: 'itemName'},
    {name: 'itemNumber'},
    {name: 'batch'},
    {name: 'unit'},
    {name: 'amountRecorded'},
    {name: 'amountExisted'},
    {name: 'amountDifference'},
    {name: 'differenceRemarks'}
]);
var _ds_createSheet_MS = new Ext.data.Store({
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			_record_MS
		)
});
	
	var _sm_createSheet_MS = new Ext.grid.CheckboxSelectionModel();
	var _cm_createSheet_MS = new Ext.grid.ColumnModel({
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_createSheet_MS,
			{
				width:60,
				dataIndex : 'itemID',
				header : '物料编号'	
			},{
				dataIndex : 'itemName',
				header : '物料名称'	
			},{
				dataIndex : 'itemNumber',
				header : '物料代码'	
			},{
				width:60,
				dataIndex : 'batch',
				header : '批次'	
			},{
				width:60,
				dataIndex : 'unit',
				header : '物料单位'	
			},{
				width:60,
				dataIndex : 'amountRecorded',
				header : '库存数量'	
			},{
				width:100,
				dataIndex : 'amountExisted',
				header : '实盘数量(编辑)',
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
				dataIndex : 'amountDifference',
				header : '差异'	
			},{
				dataIndex : 'differenceRemarks',
				header : '差异备注(编辑)',
				editor : {
					xtype: 'textfield',
					value: '',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
					allowBlank: true,
					selectOnFocus: true
				}
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_createSheet_MS = new Ext.grid.EditorGridPanel({
				sm : _sm_createSheet_MS,
				cm : _cm_createSheet_MS,
				ds : _ds_createSheet_MS,
				style: 'margin:0',
				stripeRows : true,
				loadMask : true,
				border : true,
				frame: false,
				height : 280,
				clicksToEdit : 1,
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
						width:5
					},
					{
						text : "删除",
						iconCls: 'commonDelete',
						handler: function(){
							GRID_LIST_DELETE(_grid_createSheet_MS);
						}			
					},'-',
					{
						xtype: 'tbfill'
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						text : "&nbsp;&nbsp;计算差异",
						iconCls: 'calculator',
						handler: function(){
							getStocktakeFee_create_MS();
						}
					},'-',
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'label',
						text: '动盘日期:'
					},
					{
						xtype: 'tbspacer'
					},
					{
						xtype: 'datefield',
						id: 'startDate_create_MS',
						format: 'Y-m-d',
						disabled:true,
						width:　DATAFIELDWIDTH,
						value: new Date().dateFormat('Y-m-d'),
						editable: false,
						allowBlank: false
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'label',
						text: '至'
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'datefield',
						format: 'Y-m-d',
						disabled:true,
						id: 'endDate_create_MS',
						width:　DATAFIELDWIDTH,
						value: new Date().dateFormat('Y-m-d'),
						editable: false,
						allowBlank: false
					},
					{
						xtype: 'tbspacer',
						width: 5
					},'-',
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						text : "添加全盘物料",
						iconCls:'commonQuery',
						id :'btn_add_goods_create_MS',
						handler: function(){
							var id = _formPanel_createSheet_MS.findById('storage_create_MS').getValue();
							var btn = _grid_createSheet_MS.getTopToolbar().findById('btn_add_goods_create_MS');
							if(id=='')
							{
								Ext.Msg.show({
									title : '操作提示',
									msg : '请先选择库存地！',
									buttons : Ext.Msg.OK,
									icon : Ext.Msg.WARNING
								});
								return;
							}
							if(_laststate_create_MS.type=='全盘'){
								addStocktakeAllGoods(id,_ds_createSheet_MS,btn);
							}
							else
							{
								var start = _grid_createSheet_MS.getTopToolbar().findById('startDate_create_MS').getValue().dateFormat('Y-m-d');
								var end = _grid_createSheet_MS.getTopToolbar().findById('endDate_create_MS').getValue().dateFormat('Y-m-d');
								addStocktakeChangedGoods(id,_ds_createSheet_MS,start,end,btn);
							}
						}
					},
					{
						xtype: 'tbspacer',
						width: 5
					}]
				})
			});


var _formPanel_createSheet_MS = new Ext.FormPanel({
	
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
			if(_formPanel_createSheet_MS.getForm().isValid())
			{
				getStocktakeFee_create_MS();//自动计算差异
				var params = getGridParams_create_MS();
				_formPanel_createSheet_MS.getForm().submit({
					url: 'InventoryManifest.insert.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						resetPage_create_MS();
						_grid_MS.getStore().reload();
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
		handler: function(){_win_create_MS.hide();}
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
				       id : 'storage_create_MS',
				       valueField : 'warehouseID',
				       displayField : 'name',
				       mode : 'local',
				       forceSelection : true,
				       hiddenName : 'imdto.warehouseID',
				       editable : true,
				       typeAhead : false,
				       triggerAction : 'all',
				       allowBlank : false,
				       selectOnFocus:true,
				       fieldLabel : '库存地',
				       width:　200,
				       listeners: {
				       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY,
				       		select: function(combo, record, index){
				       			if(record.get('warehouseID')!=_laststate_create_MS.storageId)
				       			{
				       				resetGoodsGrid_create_MS();
				       				_laststate_create_MS.storageId = record.get('warehouseID');
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
					fieldLabel: '盘点日期',
					readOnly:true,
					name:'imdto.dateInventoried',
					value: new Date().dateFormat('Y-m-d'),
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
					fieldLabel: '盘点人',
					width: TEXTFIELDWIDTH,
					name: 'imdto.inventoryPerson',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT
				}]
			}]
		},
		{//Row 2
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '1',
				layout: 'form',
				border: false,
				items: [new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : STORE_STOCKTAKETYPE_C,
				       valueField : 'value',
				       displayField : 'display',
				       mode : 'local',
				       forceSelection : true,
				       hiddenName : 'imdto.type',
				       editable : false,
				       triggerAction : 'all',
				       allowBlank : false,
				       fieldLabel : '盘点类型',
				       value: '全盘',
				       width:　200,
					   listeners : {
					    	select : function(combo, record, index) {
					        	if(record.get('value')!=_laststate_create_MS.type)
					        	{
					        		var btn = _grid_createSheet_MS.getTopToolbar().findById('btn_add_goods_create_MS');
					        		var start = _grid_createSheet_MS.getTopToolbar().findById('startDate_create_MS');
					        		var end = _grid_createSheet_MS.getTopToolbar().findById('endDate_create_MS');
					        		if(record.get('value')=='全盘')
					        		{
					        			btn.setText('添加全盘物料');
					        			start.disable(); end.disable();
					        			resetGoodsGrid_create_MS();
					        			_laststate_create_MS.type='全盘';
					        		}else{
					        			btn.setText('添加动盘物料');
					        			start.enable(); end.enable();
					        			resetGoodsGrid_create_MS();
					        			_laststate_create_MS.type='动盘';
					        		}
					        	}
					   		}//select
					   }
				})]
			}]
		},
		{//Row 3-Grid
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1.0',
				layout: 'form',
				border: false,
				items: _grid_createSheet_MS
			}]
		},
		{//Row 4
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1.0',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '盘点概况',
					regex: REGEX_COMMON_M,
					regexText: REGEX_COMMON_M_TEXT,
					name: 'imdto.result',
					width: 641
				}]
			}]
		}]
});

function getStocktakeFee_create_MS()
{
	var store = _grid_createSheet_MS.getStore();
	if(store.getCount()<=0) return;
	for(var i=0; i<store.getCount();i++)
	{
		var record = store.getAt(i);
		var ar = record.get('amountExisted');
		if(ar==null || ar==0) record.set('amountExisted',0);
		record.set('amountDifference',record.get('amountExisted') - record.get('amountRecorded'));
	}
	store.commitChanges();
}

function resetPage_create_MS()
{
	_formPanel_createSheet_MS.getForm().reset();
	_grid_createSheet_MS.getTopToolbar().findById('btn_add_goods_create_MS').setText('添加全盘物料');
	_grid_createSheet_MS.getTopToolbar().findById('startDate_create_MS').disable();
	_grid_createSheet_MS.getTopToolbar().findById('endDate_create_MS').disable();
	_laststate_create_MS.type='全盘';
	_ds_createSheet_MS.removeAll();
}

function getGridParams_create_MS()
{
	params = {};
	var store = _grid_createSheet_MS.getStore();
	var count = store.getCount();
	for(var i=0; i<count; i++)
	{
		var record = store.getAt(i);
		params['itemList['+i.toString()+'].itemID']=record.get('itemID');
		params['itemList['+i.toString()+'].amountRecorded']=record.get('amountRecorded');
		params['itemList['+i.toString()+'].amountExisted']=record.get('amountExisted');
		params['itemList['+i.toString()+'].amountDifference']=record.get('amountDifference');
		params['itemList['+i.toString()+'].differenceRemarks']=record.get('differenceRemarks');
	}
	return params;
}

function resetGoodsGrid_create_MS()
{
	_ds_createSheet_MS.removeAll();
}
