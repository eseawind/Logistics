
var _win_edit_CTI = null;
var _mask_edit_CTI = null;
function onEditCityInformation(CityID)
{
	if(null == _win_edit_CTI)
	{
	    createWindow_edit_CTI();
	}else
	{
		_formPanel_editCityInfo_CTI.getForm().reset();
	}
	_mask_edit_CTI.show();
	_formPanel_editCityInfo_CTI.getForm().load({
    	url: 'City.queryACity.action',
    	method: 'POST',
    	params: { 'cdto.CityID': CityID },
    	success: function(form,action){
			_mask_edit_CTI.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_edit_CTI.hide();
    	}
    });
	_win_edit_CTI.setPagePosition(GET_WIN_X(_win_edit_CTI.width),GET_WIN_Y());
    _win_edit_CTI.show();
}

function createWindow_edit_CTI()
{
	_win_edit_CTI = new Ext.Window({
        title: '修改城市信息',
        iconCls: 'commonEditSheet',
        width: 375,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_editCityInfo_CTI,
        listeners: LISTENER_WIN_MOVE
    });
    _win_edit_CTI.show();
    _mask_edit_CTI = new Ext.LoadMask(_win_edit_CTI.body, {msg:"正在载入,请稍后..."});
}


var _formPanel_editCityInfo_CTI = new Ext.FormPanel({
	
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
			if(_formPanel_editCityInfo_CTI.getForm().isValid())
			{
				_formPanel_editCityInfo_CTI.getForm().submit({
					url: 'City.updateCity.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_editCityInfo_CTI.getForm().reset();
						_win_edit_CTI.hide();
						_grid_CTI.getStore().reload();
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
		handler: function(){ _win_edit_CTI.hide(); }
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
						xtype: 'textfield',
						fieldLabel: '城市编号',
						readOnly: true,
						name: 'cdto.CityID',
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
				       store : STORE_PROVINCE,
				       valueField : 'province',
				       displayField : 'display',
				       mode : 'local',
				       forceSelection : true,
				       hiddenName : 'cdto.Province',
				       selectOnFocus:true,
				       typeAhead: true,
				       editable : true,
				       triggerAction : 'all',
				       fieldLabel: '省份',
				       emptyText: '请选择省份',
				       allowBlank : false,
				       name : 'city',
				       width:　200,
				       listeners: {
				       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
				       }
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
						xtype: 'textfield',
						fieldLabel: '城市名称',
						name: 'cdto.Name',
						allowBlank: false,
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: 200
					}]
			}]
		}
	]
});

