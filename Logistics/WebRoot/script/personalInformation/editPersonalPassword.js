
var _win_edit_MPP = null;

function onEditPersonalPassword(curUser)
{
	if(null == _win_edit_MPP)
	{
	    createWindow_edit_MPP();
	}
	else
	{
		_formPanel_editPersonalPassword_MPP.getForm().reset();
	}
	Ext.Ajax.request({
		url: 'queryCurrentUser.action',
		method: 'POST',
		success : function(response,options){
			var result = Ext.util.JSON.decode(response.responseText);
			if(!result.success){
				AJAX_FAILURE_CALLBACK(result,'获取当前用户信息失败');
			}else{
				_formPanel_editPersonalPassword_MPP.findById('id_curUser_MPP').setValue(result.currentUserID);
			}
		},
		failure: function(response,options){
			AJAX_SERVER_FAILURE_CALLBACK(response,options,'获取当前用户信息失败');
		}
	});
	_win_edit_MPP.setPagePosition(GET_WIN_X(_win_edit_MPP.width),GET_WIN_Y());
    _win_edit_MPP.show();
}

function createWindow_edit_MPP()
{
	_win_edit_MPP = new Ext.Window({
        title: '修改个人密码',
        iconCls: 'editUserPassword',
        width: 375,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_editPersonalPassword_MPP,
        listeners: LISTENER_WIN_MOVE
    });
}

var _formPanel_editPersonalPassword_MPP = new Ext.FormPanel({
	
	layout: 'form',
	style: 'margin:0px',
	frame: true,
	labelAlign: 'right',
	bodyStyle: 'padding: 15px 20px 25px 10px',
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
			if(_formPanel_editPersonalPassword_MPP.getForm().isValid())
			{
				var newPass = _formPanel_editPersonalPassword_MPP.findById('id_uPassword_New');
				var confirmPass = _formPanel_editPersonalPassword_MPP.findById('id_uPassword_Confirm');
				if(newPass.getValue()!=confirmPass.getValue())
				{
					Ext.MessageBox.show({
				   		title: '密码确认错误',
			   			msg: '两次输入新密码不同，请重新输入！',
				   		buttons: Ext.MessageBox.OK,
				   		icon: Ext.MessageBox.WARNING		   		
					});
					return
				}else{
					var oldPass = _formPanel_editPersonalPassword_MPP.findById('id_uPassword_Old');
					oldPass.setValue(hex_md5(oldPass.getValue()));
					newPass.setValue(hex_md5(newPass.getValue()));
					confirmPass.setValue('xxxxxx');
					_formPanel_editPersonalPassword_MPP.getForm().submit({
						url: 'User.updatePassword.action',
						waitTitle:"保存修改",
						waitMsg: '正在保存...',
						success:function(form,action){
								Ext.MessageBox.show({
						   		title: '密码修改提示',
					   			msg: '密码已经修改，请重新登录！',
						   		buttons: Ext.MessageBox.OK,
						   		icon: Ext.MessageBox.INFO		   		
							});
							_win_edit_MPP.hide();
							window.location.href = 'index.jsp';
						},
						failure: function(form,action){
							_formPanel_editPersonalPassword_MPP.findById('id_uPassword_New').reset();
							_formPanel_editPersonalPassword_MPP.findById('id_uPassword_Confirm').reset();
							_formPanel_editPersonalPassword_MPP.findById('id_uPassword_Old').reset();
							FORM_FAILURE_CALLBACK(form,action,"密码修改失败");
						}
					});
				}
			}
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler: function(){ _win_edit_MPP.hide(); }
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
					fieldLabel: '当前账号',
					id: 'id_curUser_MPP',
					name: 'uID',
					readOnly: true,
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
				items: [{
					xtype: 'textfield',
					fieldLabel: '旧密码',
					id: 'id_uPassword_Old',
					regex: REGEX_UPASSWORD,
					regexText: REGEX_UPASSWORD_TEXT,
					allowBlank: false,
					selectOnFocus : true,
					inputType: 'password',
					name: 'uPassword',
					width: 200
				}]
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
						fieldLabel: '新密码',
						id: 'id_uPassword_New',
						regex: REGEX_UPASSWORD,
						regexText: REGEX_UPASSWORD_TEXT,
						allowBlank: false,
						selectOnFocus : true,
						inputType: 'password',
						name: 'uNewPassword',
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
						fieldLabel: '密码确认',
						id: 'id_uPassword_Confirm',
						regex: REGEX_UPASSWORD,
						regexText: REGEX_UPASSWORD_TEXT,
						allowBlank: false,
						selectOnFocus : true,
						inputType: 'password',
						name: 'uConfirmPassword',
						width: 200
					}]
				}]
		}]
});

