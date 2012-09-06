var _win_notice = null;

function onTrackNotice()
{
	if(null == _win_notice)
	{
	    createWindow_notice();
	}
	else
	{
		_formPanel_notice.getForm().reset();
	}
	_formPanel_notice.getForm().load({
    	url: 'FreightState.queryWarnings.action',
    	method: 'POST',
    	success: function(form,action){
    		_win_notice.show();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'获取预警信息失败');
    	}
    });
	_win_notice.setPagePosition(Ext.getBody().getWidth()-250-10,Ext.getBody().getHeight()-227-10);
}

function createWindow_notice()
{
	_win_notice = new Ext.Window({
        title: '跟踪预警',
        iconCls: 'commonWarning',
        width: 350,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_notice,
        listeners: LISTENER_WIN_MOVE
    });
}

var _formPanel_notice = new Ext.FormPanel({
	
	layout: 'form',
	style: 'margin:0px',
	frame: true,
	labelAlign: 'right',
	bodyStyle: 'padding: 10px 10px 5px 10px',
	autoHeight: true,
	autoScoll: true,
	labelWidth: 150,
	border: false,
	buttonAlign: 'center',
	
	buttons:[{
		text: '刷新',
		iconCls: 'commonReset',
		handler: function(){ 
		this.disable();
		var btn = this;
		_formPanel_notice.getForm().load({
	    	url: 'FreightState.queryWarnings.action',
	    	method: 'POST',
	    	success: function(form,action){
	    		btn.enable();
	    	},
	    	failure: function(form,action){
	    		FORM_FAILURE_CALLBACK(form,action,'获取预警信息失败');
	    		btn.enable();
	    	}
    		}); 
    	}
	},{
		text: '关闭',
		iconCls: 'commonCancel',
		handler: function(){ _win_notice.hide(); }
	}],
	
	items:[
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.8',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					readOnly:true,
					fieldLabel: '逾期未到运输单[已创建]',
					name: 'overtimefmsCreate',
					width: 50
				}]
			},{//Column 1
				columnWidth: '0.2',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'button',
					text: '处理',
					style: 'margin: 0px 0px 0px 5px',
					width: 50,
					handler: function(){
						GoToTransTrack('已创建');
					}
				}]
			}]
		},{//Row 2
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.8',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					readOnly:true,
					fieldLabel: '逾期未到运输单[已发货]',
					name: 'overtimefmsPost',
					width: 50
				}]
			},{//Column 1
				columnWidth: '0.2',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'button',
					text: '处理',
					style: 'margin: 0px 0px 0px 5px',
					width: 50,
					handler: function(){
						GoToTransTrack('已发货');
					}
				}]
			}]
		},{//Row 3
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.8',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					readOnly:true,
					fieldLabel: '逾期未到运输单[已中转]',
					name: 'overtimefmsTranship',
					width: 50
				}]
			},{//Column 1
				columnWidth: '0.2',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'button',
					text: '处理',
					style: 'margin: 0px 0px 0px 5px',
					width: 50,
					handler: function(){
						GoToTransTrack('已中转');
					}
				}]
			}]
		},{//Row 4
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.8',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					readOnly:true,
					fieldLabel: '逾期未到运输单[已到港]',
					name: 'overtimefmsArrive',
					width: 50
				}]
			},{//Column 1
				columnWidth: '0.2',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'button',
					text: '处理',
					style: 'margin: 0px 0px 0px 5px',
					width: 50,
					handler: function(){
						GoToTransTrack('已到港');
					}
				}]
			}]
		},
		{//Row 5
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.8',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					fieldLabel: '异常运输单',
					readOnly:true,
					name: 'unusualfms',
					width: 50
				}]
			},{//Column 1
				columnWidth: '0.2',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'button',
					text: '处理',
					style: 'margin: 0px 0px 0px 5px',
					width: 50,
					handler: function(){
						GoToTransTrack('异常');
					}
				}]
			}]
		}]
});

function GoToTransTrack(state)
{
	treeClick(TransTrackNode,null);
	LoadTrackNotice(state);
}