
var _win_edit_SI = null;
var _mask_edit_SI = null;
function onEditStorageInformation(storageID)
{
	if(null == _win_edit_SI)
	{
	    createWindow_edit_SI();
	    STORE_CITY_LOAD();
	}else
	{
		_formPanel_editStorage_SI.getForm().reset();
	}
	_mask_edit_SI.show();
    _formPanel_editStorage_SI.getForm().load({
    	url: 'Warehouse.queryOne.action',
    	method: 'POST',
    	params: {'warehouseDTO.warehouseID': storageID},
    	success: function(form,action){
			_mask_edit_SI.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_edit_SI.hide();
    	}
    });
	_win_edit_SI.setPagePosition(GET_WIN_X(_win_edit_SI.width),GET_WIN_Y());
    _win_edit_SI.show();
}

function createWindow_edit_SI()
{
	_win_edit_SI = new Ext.Window({
        title: '修改仓库信息',
        iconCls: 'commonEditSheet',
        width: 375,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_editStorage_SI,
        listeners: LISTENER_WIN_MOVE
    });
    _win_edit_SI.show();
    _mask_edit_SI = new Ext.LoadMask(_win_edit_SI.body, {msg:"正在载入,请稍后..."});
}

var _formPanel_editStorage_SI = new Ext.FormPanel({
	
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
			if(_formPanel_editStorage_SI.getForm().isValid())
			{
				_formPanel_editStorage_SI.getForm().submit({
					url: 'Warehouse.update.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_editStorage_SI.getForm().reset();
						_win_edit_SI.hide();
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
		handler: function(){ _win_edit_SI.hide(); }
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
						readOnly: true,
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

