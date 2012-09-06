
var _win_create_SI = null;

function onCreateStorageInformation()
{
	if(null == _win_create_SI)
	{
	    createWindow_create_SI();
	    STORE_CITY_LOAD();
	}else
	{
		_formPanel_createStorage_SI.getForm().reset();
	}
	_win_create_SI.setPagePosition(GET_WIN_X(_win_create_SI.width),GET_WIN_Y());
    _win_create_SI.show();
}

function createWindow_create_SI()
{
	_win_create_SI = new Ext.Window({
        title: '新建仓库信息',
        iconCls: 'commonCreateSheet',
        width: 375,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createStorage_SI,
        listeners: LISTENER_WIN_MOVE
    });
}

var _formPanel_createStorage_SI = new Ext.FormPanel({
	
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
			if(_formPanel_createStorage_SI.getForm().isValid())
			{
				_formPanel_createStorage_SI.getForm().submit({
					url: 'Warehouse.insert.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_createStorage_SI.getForm().reset();
						_formPanel_createStorage_SI.findById('tab_id_SI').focus();
						_grid_SI.getStore().reload();
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
		handler: function(){ _win_create_SI.hide(); }
	}],
	
	items:[
		{//Row 0
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '1',
				layout: 'form',
				border: false,
				items: [{
						xtype: 'textfield',
						fieldLabel: '仓库编号',//6
						allowBlank: false,
						id: 'tab_id_SI',
						regex: REGEX_STORAGEID,
						regexText: REGEX_STORAGEID_TEXT,
						name: 'warehouseDTO.warehouseID',
						width: 200
					}]
			}]
		},
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '1',
				layout: 'form',
				border: false,
				items: [{
						xtype: 'textfield',
						fieldLabel: '仓库名称',
						allowBlank: false,
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						name: 'warehouseDTO.name',
						width: 200
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
				items: new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : STORE_CITY,
				       valueField : 'cityID',
				       displayField : 'jointName',
				       mode : 'local',
				       forceSelection : true,
				       hiddenName : 'warehouseDTO.cityID',
				       selectOnFocus:true,
				       typeAhead: false,
				       editable : true,
				       triggerAction : 'all',
				       fieldLabel: '仓库所在地',
				       emptyText: '请选择所在城市',
				       allowBlank: false,
				       name : 'city',
				       width:　200,
				       listeners: {
				       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
				       }
				})
			}]
		},
		{//Row 4
			layout: 'column',
			border: false,
			items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '详细地址',
						name: 'warehouseDTO.address',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: 200
					}]
			}]
		},
		{//Row 5
			layout: 'column',
			border: false,
			items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textarea',
						fieldLabel: '备注',
						name: 'warehouseDTO.remarks',
						regex: REGEX_COMMON_M,
						regexText: REGEX_COMMON_M_TEXT,
						width: 200
					}]
			}]
		}
	]
});

