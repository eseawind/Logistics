
Ext.onReady(function(){

	Ext.BLANK_IMAGE_URL = 'extjs/resources/images/default/s.gif';
	Ext.QuickTips.init();
	Ext.form.TextField.prototype.msgTarget = 'side';
	
	var loginform = new Ext.FormPanel({
		
		layout:'form',
		frame: true,
		buttonAlign: 'center',
		labelAlign: 'right',
		bodyStyle: 'padding: 80px 0px 70px 0px',
		height: '270',
		columnWidth: '0.68',
		defaultType: 'textfield',
		
		items:[
			{
				fieldLabel: '账号',
				name: 'uID',
				allowBlank: false,
				blankText: '请输入账号',
				enableKeyEvents: true,
				listeners : { 
					specialkey : function(field, e) {  
	                    if (e.getKey() == Ext.EventObject.ENTER) {
	                        if(loginform.findById('id_login_uPassword').getValue()!='')
	                        	formSubmit();
	                        else
	                        	loginform.findById('id_login_uPassword').focus(false);
	                    }  
	                }
				}
			},{
				fieldLabel: '密码',
				id: 'id_login_uPassword',
				allowBlank: false,
				blankText: '请输入密码',
				inputType: 'password',
				enableKeyEvents: true,
				listeners : {  
	                specialkey : function(field, e) {  
	                    if (e.getKey() == Ext.EventObject.ENTER) {
	                        formSubmit();
	                    }  
	                }  
            	}  
				
			},{
				id: 'id_cur_login_uPassword',
				name: 'uPassword',
				hidden: true
			}
		],
		
		buttons:[
		{
			text: '登录',
			iconCls: 'btnLogin',
			handler: formSubmit
		},{
			text: '重置',
			iconCls: 'btnReset',
			handler: formReset
		}
		]
		
	});
	
	var loginwindow = new Ext.Window({
		
		title: '华亿物流管理系统登录',
		maximizable: false,
		minimizable: false,
		resizable: false,
		closable: false,
		modal: true,
		draggable: false,
		autoHeight: true,
		width: 450,
		layout: 'column',
		
		items:[
		{
			border: false,
			html: '<img src="images/login/leftLogo.png">',
			columnWidth: '0.32'
		},
			loginform
		]
		
	});
	
	function formSubmit()
	{
		if(loginform.getForm().isValid())
		{
			var tmp = loginform.findById('id_login_uPassword');
			var tmp_cur = loginform.findById('id_cur_login_uPassword');
			tmp_cur.setValue(hex_md5(tmp.getValue()));
			loginform.getForm().submit({
				url: 'userSignIn.action',
				waitTitle:"请稍候",
				waitMsg: '正在登录...',
				success:function(form,action){
					window.location.href = 'main.jsp';
				},
				failure:function(form,action){
					FORM_FAILURE_CALLBACK(form,action,'登录失败');
				}
			});
		}
	}
	
	function formReset()
	{
		loginform.form.reset();
	}
	
	loginwindow.show();
	
})
