
var _win_create_CAR_TU = null;

function onAddUnitCarFee(unitId)
{
	if(null == _win_create_CAR_TU)
	{
	    createWindow_create_CAR_TU(); 
	}else
	{
		_formPanel_createUnitCar_CAR_TU.getForm().reset();
	}
	_formPanel_createUnitCar_CAR_TU.findById('unitId_add_CAR_TU').setValue(unitId);
	_win_create_CAR_TU.setPagePosition(GET_WIN_X(_win_create_CAR_TU.width),GET_WIN_Y_M(250));
    _win_create_CAR_TU.show();
}

function createWindow_create_CAR_TU()
{
	_win_create_CAR_TU = new Ext.Window({
        title: '添加路线报价',
        iconCls: 'commonCreateSheet',
        width: 500,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createUnitCar_CAR_TU,
        listeners: LISTENER_WIN_MOVE
    });
}

var _formPanel_createUnitCar_CAR_TU = new Ext.FormPanel({
	
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
			if(_formPanel_createUnitCar_CAR_TU.getForm().isValid())
			{
				_formPanel_createUnitCar_CAR_TU.getForm().submit({
					url: 'FreightContractor.insertCTQuote.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_createUnitCar_CAR_TU.getForm().reset();
						_grid_detailTrack_CAR_TU.getStore().reload();
					},
					failure: function(form,action){
						FORM_FAILURE_CALLBACK(form,action,"数据保存失败");
					}
				});
			}
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler: function(){ _win_create_CAR_TU.hide(); }
	}],
	
	items:[
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '1',
				layout: 'form',
				border: false,
				items: [{
			       xtype : 'combo',
			       store : STORE_TRANS_WAY,
			       valueField : 'freightRouteID',
			       displayField : 'jointName',
			       mode : 'local',
			       forceSelection : true,
			       hiddenName:'ctQuote.freightRouteID',
			       selectOnFocus:true,
			       typeAhead: false,
			       fieldLabel: '运输路线',
			       editable : true,
			       triggerAction : 'all',
			       width: 350,
			       listeners: {
			       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
			       }
				}]
			},
			{
				xtype: 'textfield',
				hidden:true,
				id:'unitId_add_CAR_TU',
				name: 'ctQuote.freightContractorID'
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
				items: [{
			       xtype : 'combo',
			       store : STORE_CARTYPE,
			       valueField : 'carTypeID',
			       displayField : 'carTypeName',
			       mode : 'local',
			       forceSelection : true,
			       hiddenName:'ctQuote.carTypeID',
			       selectOnFocus:true,
			       typeAhead: false,
			       fieldLabel: '车辆类型',
			       editable : true,
			       triggerAction : 'all',
			       width: 350,
			       listeners: {
			       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
			       }
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
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '报价',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'ctQuote.price',
					width: NUMBERFIELDWIDTH
				}]
			}]
		}
	]
});

/////////////////////////////

var _win_detail_CAR_TU = null;

function onEditUnitCarFee(values)
{
	if(null == _win_detail_CAR_TU)
	{
	    createWindow_detail_CAR_TU();
	}else
	{
		_ds_CAR_TU.removeAll();
	}
	STORE_TRANS_WAY_LOAD();
	STORE_CARTYPE_LOAD();
	_ds_CAR_TU.baseParams['ctQuote.freightContractorID']=values['freightContractorID'];
	_ds_CAR_TU.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_CAR_TU.reader.message,_ds_CAR_TU.reader.valid,success);
		}
	});
	_win_detail_CAR_TU.setTitle('车辆报价维护&nbsp;'+'[&nbsp;承运单位&nbsp;：&nbsp;'+values['name']+'('+values['freightContractorID']+')&nbsp;]');
	_win_detail_CAR_TU.setPagePosition(GET_WIN_X(_win_detail_CAR_TU.width),GET_WIN_Y());
    _win_detail_CAR_TU.show();
}

function createWindow_detail_CAR_TU()
{
	_win_detail_CAR_TU = new Ext.Window({
        title: '车辆报价维护',
        iconCls: 'carType',
        width: 700,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _grid_detailTrack_CAR_TU,
        listeners: LISTENER_WIN_MOVE
    });
}

var _limit_CAR_TU = 30;

var _ds_CAR_TU = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'FreightContractor.queryCTOnCondition.action'
				}),
		reader : new Self.JsonReader({
					root : 'resultMapList'
				}, 
				[{name: 'freightContractorID'},
				{name: 'freightRouteID'},
			    {name: 'freightRoute'},
			    {name: 'carTypeID'},
			    {name: 'carTypeName'},
			    {name: 'price'}]
			),
			baseParams: {
				start: 0,
				'ctQuote.freightContractorID':'',
				limit : _limit_CAR_TU
			}
	});
	var _sm_createSheet_CAR_TU = new Ext.grid.CheckboxSelectionModel();	
	var _cm_detailTrack_CAR_TU = new Ext.grid.ColumnModel({
	
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_createSheet_CAR_TU,
			{
				dataIndex : 'freightContractorID',
				hidden: true
			},
			{
				dataIndex : 'freightRouteID',
				hidden: true
			},
			{
				dataIndex : 'freightRoute',
				header : '路线',
				width: 200
			},{
				dataIndex : 'carTypeName',
				header : '车辆类型'
			},{
				dataIndex : 'price',
				header : '报价'
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_detailTrack_CAR_TU = new Ext.grid.GridPanel({
		sm:_sm_createSheet_CAR_TU,
		cm : _cm_detailTrack_CAR_TU,
		ds : _ds_CAR_TU,
		style: 'margin:0px 0px 0px 0px',
		stripeRows : true,
		loadMask : true,
		border : true,
		frame: false,
		height : 400,
		autoScroll: true,
		draggable : false,
		hideLabel: true,
		viewConfig : {
			forceFit : true
		},
		tbar : new Ext.Toolbar({
				border: true,
				items : [
				{
					xtype: 'tbspacer'
				},
				{
					text : "添加",
					iconCls: 'commonAdd',
					handler: function(){
						onAddUnitCarFee(_ds_CAR_TU.baseParams['ctQuote.freightContractorID']);
					}
				},'-',
				{
					handler: function(){
						var mindex=['freightContractorID','freightRouteID','carTypeID'];
						GRID_M_DELETE(_grid_detailTrack_CAR_TU,'FreightContractor.deleteCTQuote.action','ctqList',mindex);
					},
					text : "删除",
					iconCls: 'commonDelete'					
				},
				{
					xtype: 'tbfill'
				},
				{
					xtype: 'tbspacer',
					width: 10
				}]
			}),
		bbar: new Ext.PagingToolbar({
				pageSize : _limit_CAR_TU,
				store : _ds_CAR_TU,
				displayInfo : true,
				width : 500,
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
						       value: _limit_CAR_TU,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_CAR_TU.baseParams.limit = combo.getValue();
							        	_grid_detailTrack_CAR_TU.getBottomToolbar().pageSize = combo.getValue();
							        	_grid_detailTrack_CAR_TU.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
			})
	});
