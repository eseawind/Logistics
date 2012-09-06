
var _win_edit_GI = null;
var _mask_edit_GI = null;
function onEditGoodsInformation(goodID)
{
	if(null == _win_edit_GI)
	{
	    createWindow_edit_GI();
	}else
	{
		_formPanel_editGoodInfo_GI.getForm().reset();
	}
	_mask_edit_GI.show();
	_formPanel_editGoodInfo_GI.getForm().load({
    	url: 'Item.queryOne.action',
    	method: 'POST',
    	params: {'itemDTO.itemID': goodID},
    	success: function(form,action){
			_mask_edit_GI.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'货品数据读取失败');
    		_mask_edit_GI.hide();
    	}
    });
	_win_edit_GI.setPagePosition(GET_WIN_X(_win_edit_GI.width),GET_WIN_Y());
    _win_edit_GI.show();
}

function createWindow_edit_GI()
{
	_win_edit_GI = new Ext.Window({
        title: '修改物料信息',
        iconCls: 'commonEditSheet',
        width: 375,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_editGoodInfo_GI,
        listeners: LISTENER_WIN_MOVE
    });
    _win_edit_GI.show();
    _mask_edit_GI = new Ext.LoadMask(_win_edit_GI.body, {msg:"正在载入,请稍后..."});
}


var _formPanel_editGoodInfo_GI = new Ext.FormPanel({
	
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
			if(_formPanel_editGoodInfo_GI.getForm().isValid())
			{
				_formPanel_editGoodInfo_GI.getForm().submit({
					url: 'Item.update.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_editGoodInfo_GI.getForm().reset();
						_win_edit_GI.hide();
						_grid_GI.getStore().reload();
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
		handler: function(){ _win_edit_GI.hide(); }
	}],
	
	items:[
		{
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '1',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					name: 'itemDTO.itemID',
					hidden: true
				}]
			}]
		},
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

