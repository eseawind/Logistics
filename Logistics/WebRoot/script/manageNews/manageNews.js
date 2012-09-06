
function update_manageNews(contentPanel,node)
{
    
}

function init_manageNews(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_MN);
	_ds_MN.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_MN.reader.message,_ds_MN.reader.valid,success);
		}
	});
}

var _limit_MN = 30;

var _ds_MN = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'Message.queryOnCondition.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'resultMapList'
			}, 
			[{
				name : 'messageID'
			}, {
				name : 'datePosted'
			}, {
				name : 'type'
			}, {
				name : "header"
			}]
		),
		baseParams: {
			'mdto.type': '',
			startDate: '',
			endDate: '',
			start: 0,
			limit : _limit_MN
		}
});

var _sm_MN = new Ext.grid.CheckboxSelectionModel();
var _cm_MN = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
			_sm_MN,
		{
			header : '发布日期',
			dataIndex : 'datePosted',
			width: 15
		},{
			dataIndex : 'type',
			header : '类型',
			width: 15
		},{
			dataIndex : 'header',
			header : '标题'
		}],
		
		defaults: {
			sortable: true,
			menuDisabled: true
		}
});

var _grid_MN = new Ext.grid.GridPanel({
	
			region: 'center',
			sm : _sm_MN,
			cm : _cm_MN,
			ds : _ds_MN,
			style: 'margin:0',
			stripeRows : true,
			loadMask : true,
			border : false,
			autoScroll: true,
			draggable : false,
			
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
					text : "新建",
					iconCls: 'commonAdd',
					handler: onCreateNews
				},
				{
					text : "修改",
					iconCls: 'commonEdit',
					handler :function(){ GRID_EDITDETAIL(_grid_MN,onEditNews,'messageID'); }
				},
				{
					text : "删除",
					iconCls: 'commonDelete',
					handler: function(){ 
						GRID_DELETE(_grid_MN,'Message.delete.action','ids','messageID'); 
					}				
				},'-',
				{
					text : "阅读全文",
					iconCls: 'commonDetail',
					handler :function(){ GRID_EDITDETAIL(_grid_MN,onDetailNews,'messageID'); }
				},
				{
					xtype: 'tbfill'
				},
				{
					xtype: 'label',
					text: '信息类型:'
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : STORE_NEWSSTATE,
				       id:'id_con_type_MN',
				       valueField : 'value',
				       displayField : 'display',
				       mode : 'local',
				       forceSelection : true,
				       editable : false,
				       triggerAction : 'all',
				       allowBlank : true,
				       emptyText: '信息类型',
				       value: '',
				       width:　DATAFIELDWIDTH
				}),
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'label',
					text: '发布日期:'
				},
				{
					xtype: 'tbspacer',
					width: 10
				},
				{
					xtype: 'datefield',
					id: 'id_conStartDate_MN',
					format: 'Y-m-d',
					width:　DATAFIELDWIDTH,
					editable: false,
					enableKeyEvents: true,
					listeners: { keyup: KEYDOWN_DATE_VALIDATOR }
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
					id: 'id_conEndDate_MN',
					format: 'Y-m-d',
					width:　DATAFIELDWIDTH,
					editable: false,
					enableKeyEvents: true,
					listeners: { keyup: KEYDOWN_DATE_VALIDATOR }
				},
				{
					xtype: 'tbspacer',
					width: 10
				},'-',
				{
					text : "查询",
					iconCls: 'commonQuery',
					handler: function(){
						_ds_MN.baseParams.start = 0;
						setQeuryCondition_MN();
						_ds_MN.load({
							callback: function(record,option,success){
								STORE_CALLBACK(_ds_MN.reader.message,_ds_MN.reader.valid,success);
							}
						});
					}
				},'-',
				{
					xtype: 'tbspacer',
					width: 10
				}]
			}),

			bbar : new Ext.PagingToolbar({
						pageSize : _limit_MN,
						store : _ds_MN,
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
						       value: _limit_MN,
						       width:　DATAFIELDWIDTH,
							   listeners : {
							    	select : function(combo, record, index) {
							        	_ds_MN.baseParams.limit = combo.getValue();
							        	_grid_MN.getBottomToolbar().pageSize = combo.getValue();
							        	setQeuryCondition_MN();
							        	_grid_MN.getBottomToolbar().changePage(1);
							   		}//select
							   }
						})]
					})

		});
		
	function setNewsType(type)
	{
		if(type==1)type="新闻信息";
		else if(type==2)type="行业动态";
		else if(type==3)type="公告通知";
		else if(type==4)type="知识管理";
		else if(type==5)type="文化天地";
		else type="业务动态";
		_ds_MN.baseParams['mdto.type'] = type;
		_grid_MN.getTopToolbar().findById('id_con_type_MN').setValue(type);
		_ds_MN.load({
		callback: function(record,option,success){
				STORE_CALLBACK(_ds_MN.reader.message,_ds_MN.reader.valid,success);
			}
		});
	}
		
function setQeuryCondition_MN()
{
	_ds_MN.baseParams['mdto.type'] = _grid_MN.getTopToolbar().findById('id_con_type_MN').getValue();
	_ds_MN.baseParams.startDate = _grid_MN.getTopToolbar().findById('id_conStartDate_MN').getValue();
	_ds_MN.baseParams.endDate = _grid_MN.getTopToolbar().findById('id_conEndDate_MN').getValue();
}		
		
_grid_MN.on('rowcontextmenu', onRightClick_MN);

var _rightClickMenu_MN = new Ext.menu.Menu({
			
			items : [{
						handler :function(){ GRID_EDITDETAIL(_grid_MN,onDetailNews,'messageID'); },
						text : '全文',
						iconCls: 'commonDetail'
					},{
						handler :function(){ GRID_EDITDETAIL(_grid_MN,onEditNews,'messageID'); },
						text : '修改',
						iconCls: 'commonEdit'
					}, {
						handler:function(){GRID_DELETE(_grid_MN,'Message.delete.action','ids','messageID'); },
						text : '删除',
						iconCls: 'commonDelete'
					}]
		});

function onRightClick_MN(grid, index, e) {
	ONRIGHTCLICKGRID(grid,index,e,_rightClickMenu_MN);
}