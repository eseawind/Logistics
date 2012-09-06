
var _win_create_GI = null;

function onCreateGoodsInformation()
{
	if(null == _win_create_GI)
	{
	    createWindow_create_GI();
	}else
	{
		_formPanel_createGoodInfo_GI.getForm().reset();
	}
	_win_create_GI.setPagePosition(GET_WIN_X(_win_create_GI.width),GET_WIN_Y());
    _win_create_GI.show();
}

function createWindow_create_GI()
{
	_win_create_GI = new Ext.Window({
        title: '新建物料信息',
        iconCls: 'commonCreateSheet',
        width: 375,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createGoodInfo_GI,
        listeners: LISTENER_WIN_MOVE
       
    });
}

var _formPanel_createGoodInfo_GI = new Ext.FormPanel({
	
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
			if(_formPanel_createGoodInfo_GI.getForm().isValid())
			{
				_formPanel_createGoodInfo_GI.getForm().submit({
					url: 'Item.insert.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_grid_GI.getStore().reload();
					},
					failure: function(form,action){
						FORM_FAILURE_CALLBACK(form,action,"数据保存失败");
					}
				});
			}
		}
	},{
		text: '重置',
		iconCls: 'commonReset',
		handler: function(){
			_formPanel_createGoodInfo_GI.getForm().reset();
			_formPanel_createGoodInfo_GI.findById('tab_id_GI').focus();
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler: function(){ _win_create_GI.hide(); }
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
					id: 'tab_id_GI',
					fieldLabel: '物料名称',
					name: 'itemDTO.name',
					allowBlank: false,
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
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
					fieldLabel: '物料代码',
					name: 'itemDTO.number',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
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
					fieldLabel: '批次',
					name: 'itemDTO.batch',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
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
				       store : STORE_ITEMUNIT,
				       valueField : 'unit',
				       displayField : 'unit',
				       mode : 'local',
				       forceSelection : true,
				       hiddenName : 'itemDTO.unit',
				       editable : false,
				       triggerAction : 'all',
				       fieldLabel: '物料单位',
				       allowBlank : false,
				       name : 'itemunit',
				       width:　200
				})
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
					items: [{
						xtype: 'numberfield',
						fieldLabel: '单位体积',
						decimalPrecision:V_DECIMAL,
						name: 'itemDTO.unitVolume',
						allowNegative: false,
						value: 0,
						maxValue: MAX_DOUBLE,
						allowBlank: false,
						selectOnFocus: true,
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
						xtype: 'numberfield',
						fieldLabel: '单位重量',
						name: 'itemDTO.unitWeight',
						allowNegative: false,
						value: 0,
						maxValue: MAX_DOUBLE,
						allowBlank: false,
						selectOnFocus: true,
						width: 200
					}]
				}]
		},
		{//Row 6
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
						regex: REGEX_COMMON_M,
						regexText: REGEX_COMMON_M_TEXT,
						name: 'itemDTO.remarks',
						width: 200
					}]
			}]
		}
	]
});

