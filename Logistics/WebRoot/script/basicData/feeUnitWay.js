
var _win_create_WAY_TU = null;

function onAddUnitWayFee(unitId)
{
	if(null == _win_create_WAY_TU)
	{
	    createWindow_create_WAY_TU();
	}else
	{
		_formPanel_createUnitWay_WAY_TU.getForm().reset();
	}
	_formPanel_createUnitWay_WAY_TU.findById('unitId_add_WAY_TU').setValue(unitId);
	_win_create_WAY_TU.setPagePosition(GET_WIN_X(_win_create_WAY_TU.width),GET_WIN_Y_M(250));
    _win_create_WAY_TU.show();
}

function createWindow_create_WAY_TU()
{
	_win_create_WAY_TU = new Ext.Window({
        title: '添加路线报价',
        iconCls: 'commonCreateSheet',
        width: 500,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createUnitWay_WAY_TU,
        listeners: LISTENER_WIN_MOVE
    });
}

var _formPanel_createUnitWay_WAY_TU = new Ext.FormPanel({
	
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
			if(_formPanel_createUnitWay_WAY_TU.getForm().isValid())
			{
				_formPanel_createUnitWay_WAY_TU.getForm().submit({
					url: 'FreightContractor.insertFRQuote.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_createUnitWay_WAY_TU.getForm().reset();
						_grid_detailTrack_WAY_TU.getStore().reload();
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
		handler: function(){ _win_create_WAY_TU.hide(); }
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
			       hiddenName:'frQuote.freightRouteID',
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
				id:'unitId_add_WAY_TU',
				name: 'frQuote.freightContractorID'
			}]
		},
		{//Row 2
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '数量报价',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'frQuote.priceByAmount',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '重量报价',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'frQuote.priceByWeight',
					width: NUMBERFIELDWIDTH
				}]
			}]
		},
		{//Row 3
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '体积报价',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'frQuote.priceByVolume',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '直接报价',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'frQuote.priceDirectly',
					width: NUMBERFIELDWIDTH
				}]
			}]
		},
		{//Row 4
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '送货费',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'frQuote.deliveryQuote',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '其它费用',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'frQuote.extraQuote',
					width: NUMBERFIELDWIDTH
				}]
			}]
		}
	]
});

/////////////////////////////

var _win_detail_WAY_TU = null;

function onEditUnitWayFee(values)
{
	if(null == _win_detail_WAY_TU)
	{
	    createWindow_detail_WAY_TU();
	}else
	{
		_ds_WAY_TU.removeAll();
	}
	STORE_TRANS_WAY_LOAD();
	_ds_WAY_TU.baseParams['frQuote.freightContractorID']=values['freightContractorID'];
	_ds_WAY_TU.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_WAY_TU.reader.message,_ds_WAY_TU.reader.valid,success);
		}
	});
	_win_detail_WAY_TU.setTitle('路线报价维护&nbsp;'+'[&nbsp;承运单位&nbsp;：&nbsp;'+values['name']+'('+values['freightContractorID']+')&nbsp;]');
	_win_detail_WAY_TU.setPagePosition(GET_WIN_X(_win_detail_WAY_TU.width),GET_WIN_Y());
    _win_detail_WAY_TU.show();
}

function createWindow_detail_WAY_TU()
{
	_win_detail_WAY_TU = new Ext.Window({
        title: '路线报价维护',
        iconCls: 'transportationWay',
        width: 700,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _grid_detailTrack_WAY_TU,
        listeners: LISTENER_WIN_MOVE
    });
}

var _limit_WAY_TU = 30;

var _ds_WAY_TU = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'FreightContractor.queryFROnCondition.action'
				}),
		reader : new Self.JsonReader({
					root : 'resultMapList'
				}, 
				[{name: 'freightContractorID'},
				{name: 'freightRouteID'},
			    {name: 'freightRoute'},
			    {name: 'priceByAmount'},
			    {name: 'priceByVolume'},
			    {name: 'priceByWeight'},
			    {name: 'priceDirectly'},
			    {name: 'deliveryQuote'},
			    {name: 'extraQuote'}]
			),
			baseParams: {
				start: 0,
				'frQuote.freightContractorID':'',
				limit : _limit_WAY_TU
			}
	});
	var _sm_createSheet_WAY_TU = new Ext.grid.CheckboxSelectionModel();	
	var _cm_detailTrack_WAY_TU = new Ext.grid.ColumnModel({
	
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_createSheet_WAY_TU,
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
				dataIndex : 'priceByAmount',
				header : '数量单价'
			},{
				dataIndex : 'priceByVolume',
				header : '体积单价'
			},{
				dataIndex : 'priceByWeight',
				header : '重量单价'
			},{
				dataIndex : 'priceDirectly',
				header : '直接报价'
			},{
				dataIndex : 'deliveryQuote',
				header : '送货费'
			},{
				dataIndex : 'extraQuote',
				header : '其它费用'
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_detailTrack_WAY_TU = new Ext.grid.GridPanel({
		sm:_sm_createSheet_WAY_TU,
		cm : _cm_detailTrack_WAY_TU,
		ds : _ds_WAY_TU,
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
						onAddUnitWayFee(_ds_WAY_TU.baseParams['frQuote.freightContractorID']);
					}
				},'-',
				{
					handler: function(){
						var mindex=['freightContractorID','freightRouteID'];
						GRID_M_DELETE(_grid_detailTrack_WAY_TU,'FreightContractor.deleteFRQuote.action','frqList',mindex);
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
				pageSize : _limit_WAY_TU,
				store : _ds_WAY_TU,
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
						       value: _limit_WAY_TU,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_WAY_TU.baseParams.limit = combo.getValue();
							        	_grid_detailTrack_WAY_TU.getBottomToolbar().pageSize = combo.getValue();
							        	_grid_detailTrack_WAY_TU.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
			})
	});
