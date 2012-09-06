
///////////////////////////////////String Table
var STR_MSG_SERVER_FAILURE = '服务器连接异常，请联系管理员!';
var STR_MSG_UNKNOW_ERROR = '异常错误，请联系管理员！';
var STR_MSG_CLIENT_INVALID = '提交的表单含有不合法值！';

///////////////////////////////////

//通用Grid查询回调提示函数
var STORE_CALLBACK = function(message, valid, success)
{
	if(!success)
	{
		var msg = STR_MSG_UNKNOW_ERROR;
		if(null == message || null == valid)
			msg = STR_MSG_SERVER_FAILURE;
		else
			msg = message;
		if(message=='成功!')msg=STR_MSG_SERVER_FAILURE;
		Ext.MessageBox.show({
		   		title: '数据加载异常',
	   			msg: msg,
		   		buttons: Ext.MessageBox.OK,
		   		icon: Ext.MessageBox.WARNING		   		
	   		});
	   	if(valid != null && false === valid)
	   	{//登出
	   		window.location.href = 'index.jsp';
	   	}
	}
}

//Form 提交失败回调函数
var FORM_FAILURE_CALLBACK = function(form,action,title)
{
	var msg = STR_MSG_UNKNOW_ERROR;
	switch(action.failureType) {
        case Ext.form.Action.CLIENT_INVALID:
            msg = STR_MSG_CLIENT_INVALID; break;
        case Ext.form.Action.CONNECT_FAILURE:
            msg = STR_MSG_SERVER_FAILURE; break;
        default: msg = action.result.message;
    }
	Ext.MessageBox.show({
		   		title: title,
	   			msg: msg,
		   		buttons: Ext.MessageBox.OK,
		   		icon: Ext.MessageBox.WARNING		   		
	});
	if(action != null && action.result != null && false === action.result.valid)
	{//登出
	   	window.location.href = 'index.jsp';
	}
}

//服务器响应失败
var AJAX_FAILURE_CALLBACK = function(result,title)
{
	if(!result.success)
	Ext.MessageBox.show({
		   		title: title,
	   			msg: result.message,
		   		buttons: Ext.MessageBox.OK,
		   		icon: Ext.MessageBox.WARNING		   		
	});
	if(result != null && result != null && false === result.valid)
	{//登出
	   	window.location.href = 'index.jsp';
	}
}


//服务器响应失败
var AJAX_SERVER_FAILURE_CALLBACK = function(response,options,title)
{
	Ext.MessageBox.show({
		   		title: title,
	   			msg: STR_MSG_SERVER_FAILURE,
		   		buttons: Ext.MessageBox.OK,
		   		icon: Ext.MessageBox.WARNING		   		
	});
}
		
Ext.ns('Ext.ux.form');

Ext.ux.form.FileUploadField = Ext.extend(Ext.form.TextField,  {
 
    buttonText: 'Browse...',
    buttonOnly: false,
    buttonOffset: 3,
    // private
    readOnly: true,

    autoSize: Ext.emptyFn,

    // private
    initComponent: function(){
        Ext.ux.form.FileUploadField.superclass.initComponent.call(this);

        this.addEvents(
   
            'fileselected'
        );
    },

    // private
    onRender : function(ct, position){
        Ext.ux.form.FileUploadField.superclass.onRender.call(this, ct, position);

        this.wrap = this.el.wrap({cls:'x-form-field-wrap x-form-file-wrap'});
        this.el.addClass('x-form-file-text');
        this.el.dom.removeAttribute('name');
        this.createFileInput();

        var btnCfg = Ext.applyIf(this.buttonCfg || {}, {
            text: this.buttonText
        });
        this.button = new Ext.Button(Ext.apply(btnCfg, {
            renderTo: this.wrap,
            cls: 'x-form-file-btn' + (btnCfg.iconCls ? ' x-btn-icon' : '')
        }));

        if(this.buttonOnly){
            this.el.hide();
            this.wrap.setWidth(this.button.getEl().getWidth());
        }

        this.bindListeners();
        this.resizeEl = this.positionEl = this.wrap;
    },
    
    bindListeners: function(){
        this.fileInput.on({
            scope: this,
            mouseenter: function() {
                this.button.addClass(['x-btn-over','x-btn-focus'])
            },
            mouseleave: function(){
                this.button.removeClass(['x-btn-over','x-btn-focus','x-btn-click'])
            },
            mousedown: function(){
                this.button.addClass('x-btn-click')
            },
            mouseup: function(){
                this.button.removeClass(['x-btn-over','x-btn-focus','x-btn-click'])
            },
            change: function(){
                var v = this.fileInput.dom.value;
                this.setValue(v);
                this.fireEvent('fileselected', this, v);    
            }
        }); 
    },
    
    createFileInput : function() {
        this.fileInput = this.wrap.createChild({
            id: this.getFileInputId(),
            name: this.name||this.getId(),
            cls: 'x-form-file',
            tag: 'input',
            type: 'file',
            size: 1
        });
    },
    
    reset : function(){
        this.fileInput.remove();
        this.createFileInput();
        this.bindListeners();
        Ext.ux.form.FileUploadField.superclass.reset.call(this);
    },

    // private
    getFileInputId: function(){
        return this.id + '-file';
    },

    // private
    onResize : function(w, h){
        Ext.ux.form.FileUploadField.superclass.onResize.call(this, w, h);

        this.wrap.setWidth(w);

        if(!this.buttonOnly){
            var w = this.wrap.getWidth() - this.button.getEl().getWidth() - this.buttonOffset;
            this.el.setWidth(w);
        }
    },

    // private
    onDestroy: function(){
        Ext.ux.form.FileUploadField.superclass.onDestroy.call(this);
        Ext.destroy(this.fileInput, this.button, this.wrap);
    },
    
    onDisable: function(){
        Ext.ux.form.FileUploadField.superclass.onDisable.call(this);
        this.doDisable(true);
    },
    
    onEnable: function(){
        Ext.ux.form.FileUploadField.superclass.onEnable.call(this);
        this.doDisable(false);

    },
    
    doDisable: function(disabled){
        this.fileInput.dom.disabled = disabled;
        this.button.setDisabled(disabled);
    },

    preFocus : Ext.emptyFn,

    alignErrorIcon : function(){
        this.errorIcon.alignTo(this.wrap, 'tl-tr', [2, 0]);
    }

});

Ext.reg('fileuploadfield', Ext.ux.form.FileUploadField);

Ext.form.FileUploadField = Ext.ux.form.FileUploadField;
