
var _win_create_CTI = null;

function onCreateCityInformation()
{
	if(null == _win_create_CTI)
	{
	    createWindow_create_CTI(); 
	}
	else
	{
		_formPanel_createCityInfo_CTI.getForm().reset();
	}
	_win_create_CTI.setPagePosition(GET_WIN_X(_win_create_CTI.width),GET_WIN_Y());
    _win_create_CTI.show();
}

function createWindow_create_CTI()
{
	_win_create_CTI = new Ext.Window({
        title: '新建城市信息',
        iconCls: 'commonCreateSheet',
        width: 375,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createCityInfo_CTI,
        listeners: LISTENER_WIN_MOVE
    });
}

var _formPanel_createCityInfo_CTI = new Ext.FormPanel({
	
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
			if(_formPanel_createCityInfo_CTI.getForm().isValid())
			{
				_formPanel_createCityInfo_CTI.getForm().submit({
					url: 'City.insertCity.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_createCityInfo_CTI.getForm().reset();
						_formPanel_createCityInfo_CTI.findById('tab_id_CTI').focus();
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
		handler: function(){ _win_create_CTI.hide(); }
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
						regex: REGEX_CITYID,
						id: 'tab_id_CTI',
						regexText: REGEX_CITYID_TEXT,
						allowBlank: false,
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

