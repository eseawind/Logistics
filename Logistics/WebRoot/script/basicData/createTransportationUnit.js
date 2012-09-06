
var _win_create_TU = null;

function onCreateTransportationUnit()
{
	if(null == _win_create_TU)
	{
	    createWindow_create_TU();
	}else
	{
		_formPanel_createTransUnit_TU.getForm().reset();
	}
	_win_create_TU.setPagePosition(GET_WIN_X(_win_create_TU.width),GET_WIN_Y_M(250));
    _win_create_TU.show();
}

function createWindow_create_TU()
{
	_win_create_TU = new Ext.Window({
        title: '新建承运单位',
        iconCls: 'commonCreateSheet',
        width: 675,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createTransUnit_TU,
        listeners: LISTENER_WIN_MOVE
    });
}

var _formPanel_createTransUnit_TU = new Ext.FormPanel({
	
	layout: 'form',
	style: 'margin:0px',
	frame: true,
	labelAlign: 'right',
	bodyStyle: PADDING,
	labelWidth: 70,
	border: false,
	buttonAlign: 'center',
	
	buttons:[
	{
		text: '保存',
		iconCls: 'commonSave',
		handler: function()
		{
			if(_formPanel_createTransUnit_TU.getForm().isValid())
			{
				_formPanel_createTransUnit_TU.getForm().submit({
					url: 'FreightContractor.insert.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_grid_TU.getStore().reload();
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
			_formPanel_createTransUnit_TU.getForm().reset();
			STORE_TRANS_UNIT_LOAD();
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler: function(){ _win_create_TU.hide(); }
	}],
	
	items:[
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					id: 'tab_id_TU',
					allowBlank: false,
					name: 'freightContractorDTO.name',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
					fieldLabel: '单位名称',
					width: 200
				}]
			},
			{//Column 2
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [{
						xtype: 'textfield',
						fieldLabel: '联系人',
						name: 'freightContractorDTO.contact',
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
					columnWidth: '0.5',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '联系电话',
						name: 'freightContractorDTO.phone',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: 200
				}]
			},
			{//Column 2
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [{
						xtype: 'textfield',
						fieldLabel: '邮件',
						name: 'freightContractorDTO.email',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: 200
					}]
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
						fieldLabel: '地址',
						name: 'freightContractorDTO.address',
						regex: REGEX_COMMON_M,
						regexText: REGEX_COMMON_M_TEXT,
						width: 512
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
						xtype: 'textfield',
						fieldLabel: '备注',
						name: 'freightContractorDTO.remarks',
						regex: REGEX_COMMON_M,
						regexText: REGEX_COMMON_M_TEXT,
						width: 512
					}]
			}]
		}
	]
});

