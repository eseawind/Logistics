
function update_trackCOD(contentPanel,node)
{
    
}

function init_trackCOD(id)
{
	var page = Ext.getCmp(id);
	page.add(_formPanel_MCOD);
	page.add(_grid_MCOD);
	STORE_CITY_LOAD();
	_ds_MCOD.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_MCOD.reader.message,_ds_MCOD.reader.valid,success);
		}
	});
}

var _limit_MCOD = 30;

var _formPanel_MCOD = new Ext.FormPanel({
	
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
				if(!SETFORMPARAMTOSTORE(_formPanel_MCOD,_ds_MCOD))
					return;
				ChangeStateSelect_MCOD();
				_ds_MCOD.baseParams.start = 0;
				_grid_MCOD.getTopToolbar().findById('btn_listsave_MCOD').enable();
				_grid_MCOD.getTopToolbar().findById('btn_archive_MCOD').enable();
				if(_formPanel_MCOD.findById('con_financialState_MCOD').getValue()=='已归档'){
					_grid_MCOD.getTopToolbar().findById('btn_listsave_MCOD').disable();
					_grid_MCOD.getTopToolbar().findById('btn_archive_MCOD').disable();
				}
				_ds_MCOD.load({
					callback: function(record,option,success){
						STORE_CALLBACK(_ds_MCOD.reader.message,_ds_MCOD.reader.valid,success);
						if(success && _ds_MCOD.reader.data.state)
						{
							if(_ds_MCOD.reader.data.state!='')
							{
								_formPanel_MCOD.getForm().reset();
								_formPanel_MCOD.findById('con_financialState_MCOD').setValue(_ds_MCOD.reader.data.state);
								ChangeStateSelect_MCOD();
								_formPanel_MCOD.findById('con_financialState_MCOD').setValue(_ds_MCOD.reader.data.fState);
								_grid_MCOD.getTopToolbar().findById('btn_listsave_MCOD').enable();
								_grid_MCOD.getTopToolbar().findById('btn_archive_MCOD').enable();
								if(_formPanel_MCOD.findById('con_financialState_MCOD').getValue()=='已归档'){
									_grid_MCOD.getTopToolbar().findById('btn_listsave_MCOD').disable();
									_grid_MCOD.getTopToolbar().findById('btn_archive_MCOD').disable();
								}
							}
						}
					}
				});
			}
		},{
			text: '重置',
			iconCls: 'commonReset',
			handler: function(){ _formPanel_MCOD.getForm().reset(); }
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
						name: 'pcdto.freightManifestID',
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
					       hiddenName : 'pcdto.originCity',
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
					       hiddenName : 'pcdto.originProvince',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : true,
					       emptyText: '选择始发省份',
					       fieldLabel : '始发省份',
					       width:　TEXTFIELDWIDTH
					})]
				},
				{//Column 4
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'datefield',
						fieldLabel: '建单日期',
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
						fieldLabel: '客户名称',
						name: 'pcdto.customer',
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
					       hiddenName : 'pcdto.destinationCity',
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
					       hiddenName : 'pcdto.destinationProvince',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : true,
					       emptyText: '选择目的省份',
					       fieldLabel : '目的省份',
					       width:　TEXTFIELDWIDTH
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
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_SELLCENTER,
					       valueField : 'sellCenterID',
					       displayField : 'sellCenterID',
					       mode : 'local',
					       selectOnFocus:true,
						   typeAhead: false,
					       forceSelection : true,
					       hiddenName : 'pcdto.sellCenter',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : true,
					       fieldLabel : '销售中心',
					       width:　TEXTFIELDWIDTH
					})]
				},
				{//Column 2
					columnWidth: '0.25',
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
					       hiddenName : 'pcdto.costCenter',
					       editable : true,
					       triggerAction : 'all',
					       allowBlank : true,
					       fieldLabel : '成本中心',
					       width:　TEXTFIELDWIDTH
					})]
				},
				{//Column 3
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : STORE_MONEY_TRACKSTATE,
					       id:'con_money_state_MCOD',
					       valueField : 'value',
					       displayField : 'display',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'pcdto.state',
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : true,
					       fieldLabel : '货款状态',
					       value: '未收',
					       width:　TEXTFIELDWIDTH
					})]
				},
				{//Column 4
					columnWidth: '0.25',
					layout: 'form',
					border: false,
					items:[new Ext.form.ComboBox({
					       xtype : 'combo',
					       store : new Ext.data.SimpleStore({      
					              fields : ["value"],
					              data : [['未归档'], ['已归档']]
					       }),
					       valueField : 'value',
					       displayField : 'value',
					       mode : 'local',
					       id:'con_financialState_MCOD',
					       forceSelection : true,
					       hiddenName : 'pcdto.financialState',
					       editable : false,
					       triggerAction : 'all',
					       allowBlank : false,
					       fieldLabel : '财务状态',
					       value: '未归档',
					       width:　DATAFIELDWIDTH
					})]
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
						fieldLabel: '客户单号',
						name: 'pcdto.customerNumber',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: TEXTFIELDWIDTH
					}]
				}]
			}]
	});

	var _ds_next_state_MCOD = new Ext.data.SimpleStore
	({   
		fields : ['value','display'],
		data: M_TRACK_1
	})
	
	var _ds_MCOD = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'PaymentCollection.queryOnCondition.action'
				}),
		reader : new Self.JsonReader({
					totalProperty : 'qualifiedAmount',
					root : 'resultMapList'
				}, 
				[{
					name : 'dateCreated'
				},{
					name : 'freightManifestID'
				}, {
					name : 'origin'
				}, {
					name : 'destination'
				}, {
					name : 'customer'
				}, {
					name : 'state'
				}, {
					name : 'stateRemarks'
				}, {
					name : 'financialState'
				}, {
					name : 'sellCenter'
				}, {
					name : 'costCenter'
				}, {
					name : 'expectedPayment'
				}, {
					name : 'recievedPayment'
				}, {
					name : 'procedureFeeRate'
				}, {
					name : 'procedureFee'
				}, {
					name : 'financialRemarks'
				}, {
					name : 'customerNumber'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_MCOD,
				'pcdto.state' : '未收',
				'pcdto.financialState' : '未归档'
			}
	});

	var _sm_MCOD = new Ext.grid.CheckboxSelectionModel();
	var _cm_MCOD = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
				_sm_MCOD,
			{
				dataIndex : 'dateCreated',
				header : '建单日期'	
			},{
				dataIndex : 'freightManifestID',
				header : '运输单号'	
			},{
				dataIndex : 'customerNumber',
				header : '客户单号'
			},{
				dataIndex : 'origin',
				header : '始发地'	
			},{
				dataIndex : 'destination',
				header : '目的地'	
			},{
				dataIndex : 'customer',
				header : '客户名称'
			},{
				dataIndex : 'state',
				header : '货款状态'	
			},{
				dataIndex : 'stateRemarks',
				header : '状态中转备注(编辑)',
				width:160,
				editor : {
					xtype: 'textfield',
					value: '',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
					allowBlank: true,
					selectOnFocus: true
				}
			},{
				dataIndex : 'financialState',
				header : '财务状态'
			},{
				dataIndex : 'sellCenter',
				header : '销售中心'
			},{
				dataIndex : 'costCenter',
				header : '成本中心'
			},{
				dataIndex : 'expectedPayment',
				header : '代收货款'
			},{
				dataIndex : 'recievedPayment',
				header : '已收货款'
			},{
				dataIndex : 'procedureFeeRate',
				header : '手续费率'	
			},{
				dataIndex : 'procedureFee',
				header : '手续费收入'
			},{
				dataIndex : 'financialRemarks',
				header : '财务备注'
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_MCOD = new Ext.grid.EditorGridPanel({
		
				region: 'center',
				sm : _sm_MCOD,
				cm : _cm_MCOD,
				ds : _ds_MCOD,
				style: 'margin:0',
				stripeRows : true,
				clicksToEdit : 1,
				loadMask : true,
				border : false,
				autoScroll: true,
				draggable : false,
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
							GRID_EDITDETAIL(_grid_MCOD,onDetailTransportationSheet,'freightManifestID');
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
						id: 'show_cur_state_MCOD',
						cls: 'normalFlow_',
						text : "[1]未收"
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
					       store : _ds_next_state_MCOD,
					       valueField : 'value',
					       displayField : 'display',
					       id: 'next_state_select_MCOD',
					       mode : 'local',
					       forceSelection : true,
					       hiddenName : 'newState',
					       editable : false,
					       triggerAction : 'all',
					       fieldLabel : '货款状态',
					       value: '已收到站',
					       width:　DATAFIELDWIDTH
					}),'-',
					{
						text : "更改到下一状态",
						iconCls: 'flow',
						id: 'btn_change_state_MCOD',
						handler: function()
						{
							ChangeNextMoneyState(_grid_MCOD);
						}
					},'-',
					{
						xtype: 'textfield',
						width: '200',
						id: 'lots_stateRemarks_MCOD',
						name: 'stateRemarks',
						emptyText: '填写状态跟踪备注信息'
					},
					{
						xtype: 'tbfill'
					},'-',
					{
						text : "财务维护",
						iconCls: 'commonSaveAll',
						id:'btn_listsave_MCOD',
						handler:function()
						{
							GRID_FINANCE_SAVE(_grid_MCOD,onEditTrackCOD,'freightManifestID');
						}
					},
					{
						text : "归档",
						iconCls: 'filing',
						id:'btn_archive_MCOD',
						handler: function()
						{
							GRID_ARCHIVE(_grid_MCOD,'PaymentCollection.archive.action','ids','freightManifestID');
						}
					},
					{
						text : "取消归档",
						iconCls: 'unfiling',
						handler: function()
						{
							GRID_UNARCHIVE(_grid_MCOD,'PaymentCollection.unarchive.action','ids','freightManifestID');
						}
					},'-',
					{
						text : "导出数据",
						iconCls: 'commonExport',
						handler: function(){GRID_EXPORT(_grid_MCOD,'MCOD','freightManifestID');}
					},'-',
					{
						xtype: 'tbspacer'
					}]
				}),

				bbar : new Ext.PagingToolbar({
							pageSize : _limit_MCOD,
							store : _ds_MCOD,
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
							       value: _limit_MCOD,
							       width:　DATAFIELDWIDTH,
								   listeners : {
								    	select : function(combo, record, index) {
								        	_ds_MCOD.baseParams.limit = combo.getValue();
								        	_grid_MCOD.getBottomToolbar().pageSize = combo.getValue();
								        	SETFORMPARAMTOSTORE(_formPanel_MCOD,_ds_MCOD);
								        	_grid_MCOD.getBottomToolbar().changePage(1);
								   		}//select
								   }
							})]
						})

			});
			
	_grid_MCOD.on('rowcontextmenu', onRightClick_MCOD);
	
	var _rightClickMenu_MCOD = new Ext.menu.Menu({
				
		items : [{
					handler: function(){ GRID_EDITDETAIL(_grid_MCOD,onDetailTransportationSheet,'freightManifestID'); },
					text : '明细',
					iconCls: 'commonDetail'
				}, {
					handler: function(){GRID_PRINT(_grid_MCOD,'FreightManifest.print.action','freightManifestID')},
					text : '打印',
					iconCls: 'commonPrint'
				}]
	});
	function onRightClick_MCOD(grid, index, e) {
		ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_MCOD);
	}
	
function ChangeStateSelect_MCOD()
{
	var stateText = _formPanel_MCOD.findById('con_money_state_MCOD').getRawValue();
	var value = _formPanel_MCOD.findById('con_money_state_MCOD').getValue();
	_grid_MCOD.getTopToolbar().findById('show_cur_state_MCOD').setText(stateText);
	var nextCombo = _grid_MCOD.getTopToolbar().findById('next_state_select_MCOD');
	var nextbtn = _grid_MCOD.getTopToolbar().findById('btn_change_state_MCOD');
	nextCombo.enable();
	nextbtn.enable();
	switch(value)
	{
		case '未收':
			_ds_next_state_MCOD.loadData(M_TRACK_1,false);nextCombo.setValue('已收到站');
			break;
		case '已收到站':
			_ds_next_state_MCOD.loadData(M_TRACK_2,false);nextCombo.setValue('已收到总部');
			break;
		case '已收到总部':
			_ds_next_state_MCOD.loadData(M_TRACK_3,false);nextCombo.setValue('已返回客户');
			break;
		case '已返回客户':
			_ds_next_state_MCOD.loadData(M_TRACK_3,false);nextCombo.setValue('无');
			nextCombo.disable();
			nextbtn.disable();
			break;
	}
}

function ChangeNextMoneyState(grid)
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
		var stateRe = _grid_MCOD.getTopToolbar().findById('lots_stateRemarks_MCOD');
		params['stateRemarks'] = stateRe.getValue();
		params['newState'] = _grid_MCOD.getTopToolbar().findById('next_state_select_MCOD').getValue();
		for(var i=0; i<records.length; i++)
		{
			params["pcList"+'['+i.toString()+']'+'.freightManifestID'] = records[i].get('freightManifestID');
			params["pcList"+'['+i.toString()+']'+'.stateRemarks'] = records[i].get('stateRemarks');
		}
		Ext.Msg.confirm('操作确认','确认更改选中的['+records.length+']条记录到下一状态?',function(btn)
		{
			if(btn == 'yes')
			{
				var mask = new Ext.LoadMask(grid.getEl(), {msg:"正在提交,请稍等..."});
				mask.show();
				Ext.Ajax.request({
					url: 'PaymentCollection.update.action',
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
