
var _win_edit_MN = null;
var _mask_edit_MN = null;
function onEditNews(id)
{
	if(null == _win_edit_MN)
	{
	    createWindow_edit_MN();
	}else
	{
		_formPanel_editNews_MN.getForm().reset();
	}
	_mask_edit_MN.show();
	_formPanel_editNews_MN.getForm().load({
    	url: 'Message.queryOne.action',
    	method: 'POST',
    	params: {'mdto.messageID': id},
    	success: function(form,action){
			SetDownloadfile_edit_MN();
			_mask_edit_MN.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_edit_MN.hide();
    	}
    });
	_win_edit_MN.setPagePosition(GET_WIN_X(_win_edit_MN.width),GET_WIN_Y());
    _win_edit_MN.show();
}

function createWindow_edit_MN()
{
	_win_edit_MN = new Ext.Window({
        title: '修改信息',
        iconCls: 'commonEditSheet',
        width: 800,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_editNews_MN,
        listeners: LISTENER_WIN_MOVE
    });
    _win_edit_MN.show();
    _mask_edit_MN = new Ext.LoadMask(_win_edit_MN.body, {msg:"正在载入,请稍后..."});
}

var _formPanel_editNews_MN = new Ext.FormPanel({
	
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
			if(_formPanel_editNews_MN.getForm().isValid())
			{
				
				_formPanel_editNews_MN.getForm().submit({
					url: 'Message.queryOne.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_editNews_MN.getForm().reset();
						_win_edit_MN.hide();
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
		handler: function(){ _win_edit_MN.hide(); }
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
				},
				{
					xtype: 'textfield',
					id:'messageId_edit_MN',
					name: 'mdto.messageID',
					hidden:true
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
					name: 'mdto.datePosted',
					readOnly:true,
					fieldLabel: '发布日期',
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
				columnWidth: '0.65',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'fileuploadfield',
					fieldLabel: '附件',
					id:'upload_edit_MN',
					name:'upload',
					emptyText: '上传单个文件，大小不超过 2M',
					width: 400,
					buttonText: '',
		            buttonCfg: {
		                iconCls: 'attachment'
		            }
				},
				{
					xtype: 'textfield',
					hidden:true,
					id:'fileName_edit_MN',
					name: 'mdto.originName'
				}]
			},
			{//Column 1
				columnWidth: '0.35',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'button',
					id:'download_edit_MN',
					width: 80,
					text: '下载附件',
					handler: function()
					{
						var id = _formPanel_editNews_MN.findById('messageId_edit_MN').getValue();
						FILE_DOWNLOAD('Message.download.action','mdto.messageID',id);
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
					id:'editor_edit_MN',
					width: 641,
					name: 'mdto.body',
					fieldLabel: '正文',
					height: 300
				}]
			}]
		}
	]
});

function SetDownloadfile_edit_MN()
{
	var filename = _formPanel_editNews_MN.findById('fileName_edit_MN').getValue();
	var btn = _formPanel_editNews_MN.findById('download_edit_MN');
	if(filename==null||filename=='')
	{
		btn.disable();
		return;
	}
	else
	{
		_formPanel_editNews_MN.findById('upload_edit_MN').setRawValue(filename);
		btn.enable();
	}
}
