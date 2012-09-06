
var _win_create_MN = null;

function onCreateNews()
{
	if(null == _win_create_MN)
	{
	    createWindow_create_MN();
	}else
	{
		_formPanel_createNews_MN.getForm().reset();
	}
	_win_create_MN.setPagePosition(GET_WIN_X(_win_create_MN.width),GET_WIN_Y());
    _win_create_MN.show();
}

function createWindow_create_MN()
{
	_win_create_MN = new Ext.Window({
        title: '信息发布',
        iconCls: 'commonCreateSheet',
        width: 800,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createNews_MN,
        listeners: LISTENER_WIN_MOVE
    });
}

var _formPanel_createNews_MN = new Ext.FormPanel({
	
	layout: 'form',
	fileUpload:true,
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
			if(_formPanel_createNews_MN.getForm().isValid())
			{
				_formPanel_createNews_MN.getForm().submit({
					url: 'Message.insert.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_createNews_MN.getForm().reset();
						_win_create_MN.hide();
						_grid_MN.getStore().reload();
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
		handler: function(){ _win_create_MN.hide(); }
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
					name: 'mdto.header',
					allowBlank: false,
					regex: REGEX_COMMON_M,
					regexText: REGEX_COMMON_M_TEXT,
					fieldLabel: '标题',
					width: 400
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
				       store : new Ext.data.SimpleStore({      
				              fields : ["returnValue", "displayText"],
				              data : [['新闻信息','新闻信息'],['行业动态','行业动态'],['公告通知','公告通知'],
				              ['知识管理','知识管理'],['文化天地','文化天地'],['业务动态','业务动态']]
				       }),
				       valueField : 'returnValue',
				       displayField : 'displayText',
				       mode : 'local',
				       forceSelection : true,
				       hiddenName : 'mdto.type',
				       editable : false,
				       triggerAction : 'all',
				       allowBlank : false,
				       fieldLabel : '信息类型',
				       value: '新闻信息',
				       width:　400
				})
			}]
		},
		{//Row 3
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'fileuploadfield',
					fieldLabel: '附件',
					name:'upload',
					emptyText: '上传单个文件，大小不超过 2M',
					width: 400,
					buttonText: '',
		            buttonCfg: {
		                iconCls: 'attachment'
		            }
				}]
			}]
		},
		{//Row 4
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'htmleditor',
					id:'editor_create_MN',
					width: 641,
					name: 'mdto.body',
					fieldLabel: '正文',
					height: 300
				}]
			}]
		}
	]
});





