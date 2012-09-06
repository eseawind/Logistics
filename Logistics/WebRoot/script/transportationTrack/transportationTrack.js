
function update_transportationTrack(contentPanel,node)
{
    
}

function init_transportationTrack(id)
{
	var page = Ext.getCmp(id);
	page.add(_formPanel_TT);
	page.add(_grid_TT);
	STORE_CITY_LOAD();
	_ds_TT.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_TT.reader.message,_ds_TT.reader.valid,success);
		}
	});
}

var _limit_TT = 30;

var _formPanel_TT = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_TT,_ds_TT))
					return;
				ChangeStateSelect_TT();
				_ds_TT.baseParams.start = 0;
				_ds_TT.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_TT.reader.message,_ds_TT.reader.valid,success);
						if(success && _ds_TT.reader.data.freightState)
						{
							if(_ds_TT.reader.data.freightState!='')
							{
								_formPanel_TT.getForm().reset();
								_formPanel_TT.findById('con_trans_state_TT').setValue(_ds_TT.reader.data.freightState);
								ChangeStateSelect_TT();
							}
						}
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function(){ _formPanel_TT.getForm().reset(); }
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
						regex: REGEX_COMMON_T,
						regexText: REGEX_COMMON_T_TEXT,
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
					       forceSelection : false,
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
						fieldLabel: '装车单号',
						name: 'smdto.shipmentManifestID',
						regex: REGEX_COMMON_T,
						regexText: REGEX_COMMON_T_TEXT,
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
					       forceSelection : false,
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
						id:'endDate_TT',
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
				{//Column 3
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_TRACKSTATE,
					       valueField : 'value',
					       displayField : 'display',
					       mode : 'local',
					       id:'con_trans_state_TT',
					       forceSelection : true,
					       hiddenName : 'fmdto.freightState',
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : true,
					       fieldLabel : '运输状态',
					       value: '已创建',
					       width:　TEXTFIELDWIDTH
					})]
				},
				{//Column 4
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items: new Ext.form.ComboBox({
					       xtype : 'combo',
					       id:'dateType_TT',
					       store : STORE_DATATYPE,
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
						fieldLabel: '客户单号',
						name: 'fmdto.customerNumber',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				},{//Column 2
					columnWidth: '0.75',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '客户名称',
						name: 'fmdto.customer',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				}]
			}]
	});

	var _ds_next_state_TT = new Ext.data.SimpleStore
	({   
		fields : ['value','display'],
		data: DATA_TRACK_1
	})
	
	var _ds_TT = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'FreightState.queryOnCondition.action'
				}),
		reader : new Self.JsonReader({
					totalProperty : 'qualifiedAmount',
					root : 'resultMapList'
				}, 
				[{
					name : 'freightManifestID'
				}, {
					name : 'origin'
				}, {
					name : 'destination'
				}, {
					name : "consignee"
				}, {
					name : 'consigneeCompany'
				}, {
					name : 'freightState'
				}, {
					name : 'dateCreated'
				}, {
					name : 'dateExpected'
				}, {
					name : 'dateDelivered'
				}, {
					name : 'dateSigned'
				}, {
					name : 'transitionInfo'
				}, {
					name : 'ackInfo'
				}, {
					name : 'customerNumber'
				}, {
					name : 'customer'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_TT,
				'fmdto.freightState' : '已创建'
			}
	});

	var _sm_TT = new Ext.grid.CheckboxSelectionModel();
	
	var _cm_TT = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_TT,
			{
				dataIndex : 'freightManifestID',
				header : '运输单号'
			},{
				dataIndex : 'freightManifestID',
				header : '装车单号'
			},{
				dataIndex : 'customerNumber',
				header : '客户单号'
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
				header : '收货人'	
			},{
				dataIndex : 'freightState',
				header : '运输状态'
			},{
				header : '建单日期',
				dataIndex : 'dateCreated'
			},{
				header : '预计到货日期',
				dataIndex : 'dateExpected'
			},{
				header : '发货日期',
				dataIndex : 'dateDelivered',
				renderer:function(value){ 
					if(value==null||value=='')
						return "<span>未发货</span>";
					else return "<span>"+value+"</span>";
				}
			},{
				header : '签收日期',
				dataIndex : 'dateSigned',
				renderer:function(value){ 
					if(value==null||value=='')
						return "<span>未签收</span>";
					else return "<span>"+value+"</span>";
				}
			},{
				dataIndex : 'transitionInfo',
				header : '业务状态备注(编辑)',
				width: 160,
				editor : {
					xtype: 'textfield',
					value: '',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
					allowBlank: true,
					selectOnFocus: true
				}
			},{
				dataIndex : 'ackInfo',
				header : '签收备注(编辑)',
				width: 160,
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
	
	var _grid_TT = new Ext.grid.EditorGridPanel({
		
				region: 'center',
				sm : _sm_TT,
				cm : _cm_TT,
				ds : _ds_TT,
				style: 'margin:0',
				stripeRows : true,
				loadMask : true,
				border : false,
				autoScroll: true,
				draggable : false,
				clicksToEdit : 1,
				
				viewConfig : {
					forceFit : false
				},
				
				tbar : new Ext.Toolbar({
					border: true,
					items : [
					{
						xtype: 'tbspacer'
					},'-',
					{
						text : "查看明细",
						iconCls: 'commonDetail',
						handler: function(){
							GRID_EDITDETAIL(_grid_TT,onDetailTransportationSheet,'freightManifestID');
						}
					},'-',
					{
						xtype: 'label',
						text : "当前状态："
					},
					{
						xtype: 'tbspacer'
					},
					{
						xtype: 'label',
						id: 'show_cur_state_TT',
						cls: 'normalFlow_',
						text : "[1]已创建"
					},'-', 
					{
						xtype: 'label',
						text : "下一状态："
					},
					{
						xtype: 'tbspacer'
					},
					new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : _ds_next_state_TT,
					       valueField : 'value',
					       displayField : 'display',
					       id: 'next_state_select_TT',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'newState',
					       editable : false,
					       triggerAction : 'all',
					       fieldLabel : '运输状态',
					       value: '已发货',
					       width:　DATAFIELDWIDTH
					}),'-',
					{
						text : "更改到下一状态",
						id: 'btn_change_state_TT',
						iconCls: 'flow',
						handler: function()
						{
							ChangeNextTransState(_grid_TT);
						}
					},'-',
					{
						xtype: 'textfield',
						width: '160',
						id: 'lots_stateRemarks_TT',
						name: 'stateRemarks',
						emptyText: '批量状态跟踪备注信息'
					},
					{
						xtype: 'tbspacer',
						width: 5
					},
					{
						xtype: 'textfield',
						width: '160',
						id:'lots_ackIn_TT',
						name: 'ackInfo',
						emptyText: '批量签收备注信息'
					},
					{
						xtype: 'tbfill'
					},'-',
					{
						text : "物流明细",
						iconCls: 'trackDetail',
						handler: function(){
							GRID_EDITDETAIL(_grid_TT,onDetailTransportationTrack,'freightManifestID');
						}
					},'-',
					{
						text : "跟踪预警",
						iconCls: 'commonWarning',
						handler:function(){onTrackNotice();}
					},'-',
					{
						xtype: 'tbspacer'
					}]
				}),

				bbar : new Ext.PagingToolbar({
							pageSize : _limit_TT,
							store : _ds_TT,
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
							       value: _limit_TT,
							       width:　DATAFIELDWIDTH,
								   listeners : {
								    	select : function(combo, record, index) {
								        	_ds_TT.baseParams.limit = combo.getValue();
								        	_grid_TT.getBottomToolbar().pageSize = combo.getValue();
								        	SETFORMPARAMTOSTORE(_formPanel_TT,_ds_TT);
								        	_grid_TT.getBottomToolbar().changePage(1);
								   		}//select
								   }
							})]
						})

			});
	
_grid_TT.on('rowcontextmenu', onRightClick_TT);

var _rightClickMenu_TT = new Ext.menu.Menu({
			
	items : [{
				handler: function(){ GRID_EDITDETAIL(_grid_TT,onDetailTransportationSheet,'freightManifestID'); },
				text : '运输单明细',
				iconCls: 'commonDetail'
			},{
				handler :function(){ GRID_EDITDETAIL(_grid_TT,onDetailTransportationTrack,'freightManifestID'); },
				text : '物流明细',
				iconCls: 'trackDetail'
			}]
});

function onRightClick_TT(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_TT);
}
	
function ChangeStateSelect_TT()
{
	var stateText = _formPanel_TT.findById('con_trans_state_TT').getRawValue();
	var value = _formPanel_TT.findById('con_trans_state_TT').getValue();
	_grid_TT.getTopToolbar().findById('show_cur_state_TT').setText(stateText);
	var nextCombo = _grid_TT.getTopToolbar().findById('next_state_select_TT');
	var nextbtn = _grid_TT.getTopToolbar().findById('btn_change_state_TT');
	nextCombo.enable();
	nextbtn.enable();
	switch(value)
	{
		case '已创建':
			_ds_next_state_TT.loadData(DATA_TRACK_1,false);nextCombo.setValue('已发货');
			break;
		case '已发货':
			_ds_next_state_TT.loadData(DATA_TRACK_2,false);nextCombo.setValue('已到港');
			break;
		case '已到港':
			_ds_next_state_TT.loadData(DATA_TRACK_3,false);nextCombo.setValue('已签收');
			break;
		case '已签收':
			_ds_next_state_TT.loadData(DATA_TRACK_4,false);nextCombo.setValue('已中转');
			break;
		case '已中转':
			_ds_next_state_TT.loadData(DATA_TRACK_5,false);nextCombo.setValue('已到港');
			break;
		case '异常':
			_ds_next_state_TT.loadData(DATA_TRACK_6,false);nextCombo.setValue('已发货');
			break;
		case '已收回单':
			_ds_next_state_TT.loadData(DATA_TRACK_6,false);nextCombo.setValue('无');
			nextCombo.disable();
			nextbtn.disable();
			break;
	}
}

function ChangeNextTransState(grid)
{
	var records = grid.getSelectionModel().getSelections();
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
		var stateRe = _grid_TT.getTopToolbar().findById('lots_stateRemarks_TT');
		var ackIn = _grid_TT.getTopToolbar().findById('lots_ackIn_TT');
		params['stateRemarks'] = stateRe.getValue();
		params['ackInfo'] = ackIn.getValue();
		params['newState'] = _grid_TT.getTopToolbar().findById('next_state_select_TT').getValue();
		for(var i=0; i<records.length; i++)
		{
			params["fsList"+'['+i.toString()+']'+'.freightManifestID'] = records[i].get('freightManifestID');
			params["fsList"+'['+i.toString()+']'+'.stateRemarks'] = records[i].get('transitionInfo');
			params["fmList"+'['+i.toString()+']'+'.freightManifestID'] = records[i].get('freightManifestID');
			params["fmList"+'['+i.toString()+']'+'.ackInfo'] = records[i].get('ackInfo');
		}
		Ext.Msg.confirm('操作确认','确认更改选中的['+records.length+']条记录到下一状态?',function(btn)
		{
			if(btn == 'yes')
			{
				var mask = new Ext.LoadMask(grid.getEl(), {msg:"正在提交,请稍等..."});
				mask.show();
				Ext.Ajax.request({
					url: 'FreightState.update.action',
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							Ext.Msg.show({
								title : '状态更改错误',
								msg : result.message,
								buttons : Ext.Msg.OK,
								icon : Ext.Msg.WARNING
							});		
						}else{
							stateRe.reset();
							ackIn.reset();
							grid.getStore().reload();
						}
						mask.hide();
					},
					failure: function(response,options){
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'操作请求失败');
					},
					params: params
				});//Ajax
			}
		});//Msg confirm
	}//else
}

function LoadTrackNotice(state)
{
	_formPanel_TT.getForm().reset();
	if(state=='异常')
	{
		_formPanel_TT.findById('con_trans_state_TT').setValue(state);
	}
	else{
		_formPanel_TT.findById('con_trans_state_TT').setValue(state);
		_formPanel_TT.findById('endDate_TT').setValue(new Date().dateFormat('Y-m-d'));
		_formPanel_TT.findById('dateType_TT').setValue('预计到货日期');
	}
	if(!SETFORMPARAMTOSTORE(_formPanel_TT,_ds_TT))
		return;
	ChangeStateSelect_TT();
	_ds_TT.baseParams.start = 0;
	_ds_TT.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_TT.reader.message,_ds_TT.reader.valid,success);
		}
	});
}

