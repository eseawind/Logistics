
var _win_detail_MN = null;
var _mask_detail_MN = null;
function onDetailNews(id)
{
	if(null == _win_detail_MN)
	{
	    createWindow_detail_MN();
	}
	_mask_detail_MN.show();
	Ext.Ajax.request({
		url: 'Message.queryOne.action',
		method: 'POST',
		params: {'mdto.messageID': id},
		success : function(response,options){
			var result = Ext.util.JSON.decode(response.responseText);
			if(!result.success){
				AJAX_FAILURE_CALLBACK(result,'新闻数据失败');
			}else{
				setContent_MN(result.data,_win_detail_MN);
			}
			_mask_detail_MN.hide();
		},
		failure: function(response,options){
			AJAX_SERVER_FAILURE_CALLBACK(response,options,'新闻数据读取失败');
			_mask_detail_MN.hide();
		}
	});//Ajax
	_win_detail_MN.getTopToolbar().findById('download_detail_MN').disable();
	_win_detail_MN.setPagePosition(GET_WIN_X(_win_detail_MN.width),GET_WIN_Y_M(600));
    _win_detail_MN.show();
}

function setContent_MN(data,win)
{
	var btn = _win_detail_MN.getTopToolbar().findById('download_detail_MN');
	if(data['mdto.originName']==null||data['mdto.originName']=='')
	{
		_win_detail_MN.getTopToolbar().findById('fileName_detail_MN').setValue('没有附件');
		btn.disable();
	}
	else
	{
		_win_detail_MN.getTopToolbar().findById('fileName_detail_MN').setValue(data['mdto.originName']);
		btn.enable();
	}
	_win_detail_MN.findById('content_panel_MN').body.update(data['mdto.body']);
	_win_detail_MN.getTopToolbar().findById('messageId_detail_MN').setValue(data['mdto.messageID']);
	win.setTitle(data['mdto.header']+'&nbsp;[&nbsp;'+data['mdto.datePosted']+'&nbsp;]&nbsp;');
}

function createWindow_detail_MN()
{
	_win_detail_MN = new Ext.Window({
        title: '全文阅读',
        iconCls: 'commonDetail',
        width: 800,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        autoScroll: false,
        listeners: LISTENER_WIN_MOVE,
        tbar: new Ext.Toolbar({
			
			items: [
				{	xtype:'tbspacer'	},
				{
					xtype: 'textfield',
					id:'fileName_detail_MN',
					readOnly: true,
					emptyText: '没有附件',
					width: 400
				},
				{
					xtype: 'textfield',
					id:'messageId_detail_MN',
					hidden: true
				},
				{	xtype:'tbspacer'	},'-',
				{
					xtype: 'button',
					id:'download_detail_MN',
					text: '附件下载',
					width: 80,
					iconCls: 'attachment',
					handler:function(){
						var id = _win_detail_MN.getTopToolbar().findById('messageId_detail_MN').getValue();
						FILE_DOWNLOAD('Message.download.action','mdto.messageID',id);
					}
				}
			]
		}),
        
		items: [{
			columnWidth: '1',
			id:'content_panel_MN',
			height: 450,
			autoScroll: true,
			bodyStyle:'background-color:#FFFFFF', 
			baseCls:'x-plain',
			html:'',
			border: false,
			frame: false
		}]
    });
    _win_detail_MN.show();
    _mask_detail_MN = new Ext.LoadMask(_win_detail_MN.body, {msg:"正在载入,请稍后..."});
}