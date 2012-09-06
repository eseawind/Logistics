
var _win_create_WAY_CCI = null;

function onAddTransWayFee(unitId)
{
	if(null == _win_create_WAY_CCI)
	{
	    createWindow_create_WAY_CCI(); 
	}else
	{
		_formPanel_createUnitWay_WAY_CCI.getForm().reset();
	}
	_formPanel_createUnitWay_WAY_CCI.findById('unitId_add_WAY_CCI').setValue(unitId);
	_win_create_WAY_CCI.setPagePosition(GET_WIN_X(_win_create_WAY_CCI.width),GET_WIN_Y_M(250));
    _win_create_WAY_CCI.show();
}

function createWindow_create_WAY_CCI()
{
	_win_create_WAY_CCI = new Ext.Window({
        title: '添加路线报价',
        iconCls: 'commonCreateSheet',
        width: 500,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createUnitWay_WAY_CCI,
        listeners: LISTENER_WIN_MOVE
    });
}

var _formPanel_createUnitWay_WAY_CCI = new Ext.FormPanel({
	
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
			if(_formPanel_createUnitWay_WAY_CCI.getForm().isValid())
			{
				_formPanel_createUnitWay_WAY_CCI.getForm().submit({
					url: 'Customer.insertQuote.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_createUnitWay_WAY_CCI.getForm().reset();
						_grid_detailTrack_WAY_CCI.getStore().reload();
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
		handler: function(){ _win_create_WAY_CCI.hide(); }
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
			       hiddenName:'quote.freightRouteID',
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
				id:'unitId_add_WAY_CCI',
				name: 'quote.customerID'
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
					name: 'quote.priceByAmount',
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
					name: 'quote.priceByWeight',
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
					name: 'quote.priceByVolume',
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
					fieldLabel: '提货费',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'quote.takingQuote',
					width: NUMBERFIELDWIDTH
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
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '送货费',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'quote.deliveryQuote',
					width: NUMBERFIELDWIDTH
				}]
			}]
		}
	]
});

/////////////////////////////

var _win_detail_WAY_CCI = null;

function onEditTransWayFee(values)
{
	if(null == _win_detail_WAY_CCI)
	{
	    createWindow_detail_WAY_CCI();
	}else
	{
		_ds_WAY_CCI.removeAll();
	}
	STORE_TRANS_WAY_LOAD();
	_ds_WAY_CCI.baseParams['customerDTO.customerID']=values['customerID'];
	_ds_WAY_CCI.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_WAY_CCI.reader.message,_ds_WAY_CCI.reader.valid,success);
		}
	});
	_win_detail_WAY_CCI.setTitle('运输报价维护&nbsp;'+'[&nbsp;客户&nbsp;：&nbsp;'+values['name']+'('+values['customerID']+')&nbsp;]');
	_win_detail_WAY_CCI.setPagePosition(GET_WIN_X(_win_detail_WAY_CCI.width),GET_WIN_Y());
    _win_detail_WAY_CCI.show();
}

function createWindow_detail_WAY_CCI()
{
	_win_detail_WAY_CCI = new Ext.Window({
        title: '运输报价维护',
        iconCls: 'transportationWay',
        width: 700,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _grid_detailTrack_WAY_CCI,
        listeners: LISTENER_WIN_MOVE
    });
}

var _limit_WAY_CCI = 30;

var _ds_WAY_CCI = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'Customer.queryQuoteOnCondition.action'
				}),
		reader : new Self.JsonReader({
					root : 'resultMapList'
				}, 
				[{name: 'customerID'},
				{name: 'freightRouteID'},
			    {name: 'routeName'},
			    {name: 'priceByAmount'},
			    {name: 'priceByVolume'},
			    {name: 'priceByWeight'},
			    {name: 'deliveryQuote'},
			    {name: 'takingQuote'}]
			),
			baseParams: {
				start: 0,
				'customerDTO.customerID':'',
				limit : _limit_WAY_CCI
			}
	});
	var _sm_createSheet_WAY_CCI = new Ext.grid.CheckboxSelectionModel();	
	var _cm_detailTrack_WAY_CCI = new Ext.grid.ColumnModel({
	
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_createSheet_WAY_CCI,
			{
				dataIndex : 'customerID',
				hidden: true
			},
			{
				dataIndex : 'freightRouteID',
				hidden: true
			},
			{
				dataIndex : 'routeName',
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
				dataIndex : 'deliveryQuote',
				header : '送货费'
			},{
				dataIndex : 'takingQuote',
				header : '提货费'
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_detailTrack_WAY_CCI = new Ext.grid.GridPanel({
		sm:_sm_createSheet_WAY_CCI,
		cm : _cm_detailTrack_WAY_CCI,
		ds : _ds_WAY_CCI,
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
						onAddTransWayFee(_ds_WAY_CCI.baseParams['customerDTO.customerID']);
					}
				},'-',
				{
					handler: function(){
						var mindex=['customerID','freightRouteID'];
						GRID_M_DELETE(_grid_detailTrack_WAY_CCI,'Customer.deleteQuote.action','ids',mindex);
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
				pageSize : _limit_WAY_CCI,
				store : _ds_WAY_CCI,
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
						       value: _limit_WAY_CCI,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_WAY_CCI.baseParams.limit = combo.getValue();
							        	_grid_detailTrack_WAY_CCI.getBottomToolbar().pageSize = combo.getValue();
							        	_grid_detailTrack_WAY_CCI.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
			})
	});
