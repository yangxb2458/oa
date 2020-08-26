/**
 * 稠云智能平台表单设计插件
 */

UE.plugins['xqrcode'] = function() {
	var me = this, thePlugins = 'xqrcode';
	me.commands[thePlugins] = {
		execCommand : function() {
			var editor = this;
			var element = UE.plugins[thePlugins].editdom;
			$("#xpluging").html(xqrcodemodal);
			if (element != null) {
				$("#name").val(element.getAttribute('name'));
    			$("#style").val(element.getAttribute('style'));
    			$("#title").val(element.getAttribute('title'));
    			$("#defaultValue").val(element.getAttribute('defaultValue'));
				$(".js-savexqrcodebtn").unbind("click").click(function() {
					var name = $("#name").val();
					var dataType = $("#dataType").val();
					var style = $("#style").val();
					var defaultValue = $("#defaultValue").val();
					var title = $("#title").val();
					if (title == "") {
						top.layer.msg("字段标题不能为空!");
						return false;
					}
					if (testTableFieldsName(name)) {
						element.setAttribute('name', name);
						element.setAttribute('style', style);
						element.setAttribute('defaultValue', defaultValue);
						element.setAttribute('title', title);
						element.setAttribute('xtype', 'xqrcode');
						delete UE.plugins[thePlugins].editdom;
						$("#xmodal").modal("hide");
					}
				})
			} else
			{
			$(".js-savexqrcodebtn").unbind("click").click(function() {
				var name=$("#name").val();
    			var style=$("#style").val();
    			var title=$("#title").val();
    			var defaultValue=$("#defaultValue").val();
    			var html = "<img xtype='xqrcode' " +
			        			"title='"+title+"' " +
								"name='"+name+"' " +
								"style='"+style+"' " +
								"defaultValue='"+defaultValue+"' " +
    							"src='/module/ueditor/formdesign/images/qrcode.png'/>";
    			if (title == "") {
					top.layer.msg("字段标题不能为空!");
					return false;
				}
				if (testTableFieldsName(name)) {
					editor.execCommand('insertHtml', html);
					$("#xmodal").modal("hide");
				}
			})
			}
			$("#xmodal").modal("show");
		}
	};
	var popup = new baidu.editor.ui.Popup({
		editor : this,
		content : '',
		className : 'edui-bubble',
		_edittext : function() {
			baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
			me.execCommand(thePlugins);
			this.hide();
		},
		_delete : function() {
			if (window.confirm('是否删除此组件？删除后页面功能将会受到影响,请慎重操作!!!')) {
				baidu.editor.dom.domUtils.remove(this.anchorEl, false);
			}
			this.hide();
		}
	});
	popup.render();
	me.addListener('mouseover', function(t, evt) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
		var xtype = el.getAttribute("xtype");
		var tagName = el.tagName;
		if (tagName == 'IMG' && xtype == thePlugins) {
			var html = popup.formatHtml('<nobr>条形码: <span onclick=$$._edittext() class="edui-clickable">编辑</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>');
			if (html) {
				popup.getDom('content').innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor(popup.anchorEl);
			} else {
				popup.hide();
			}
		}
	});
};


//条形码
UE.plugins['xbarcode'] = function() {
	var me = this, thePlugins = 'xbarcode';
	me.commands[thePlugins] = {
		execCommand : function() {
			var editor = this;
			var element = UE.plugins[thePlugins].editdom;
			$("#xpluging").html(xbarcodemodal);
			if (element != null) {
				$("#name").val(element.getAttribute('name'));
    			$("#style").val(element.getAttribute('style'));
    			$("#title").val(element.getAttribute('title'));
    			$("#defaultValue").val(element.getAttribute('defaultValue'));
				$(".js-savexbarcodebtn").unbind("click").click(function() {
					var name = $("#name").val();
					var dataType = $("#dataType").val();
					var style = $("#style").val();
					var defaultValue = $("#defaultValue").val();
					var title = $("#title").val();
					if (title == "") {
						top.layer.msg("字段标题不能为空!");
						return false;
					}
					if (testTableFieldsName(name)) {
						element.setAttribute('name', name);
						element.setAttribute('style', style);
						element.setAttribute('defaultValue', defaultValue);
						element.setAttribute('title', title);
						element.setAttribute('xtype', 'xbarcode');
						delete UE.plugins[thePlugins].editdom;
						$("#xmodal").modal("hide");
					}
				})
			} else
			{
			$(".js-savexbarcodebtn").unbind("click").click(function() {
				var name=$("#name").val();
    			var style=$("#style").val();
    			var title=$("#title").val();
    			var defaultValue=$("#defaultValue").val();
    			var html = "<img xtype='xbarcode' " +
			        			"title='"+title+"' " +
								"name='"+name+"' " +
								"style='"+style+"' " +
								"defaultValue='"+defaultValue+"' " +
    							"src='/module/ueditor/formdesign/images/barcode.png'/>";
    			if (title == "") {
					top.layer.msg("字段标题不能为空!");
					return false;
				}
				if (testTableFieldsName(name)) {
					editor.execCommand('insertHtml', html);
					$("#xmodal").modal("hide");
				}
			})
			}
			$("#xmodal").modal("show");
		}
	};
	var popup = new baidu.editor.ui.Popup({
		editor : this,
		content : '',
		className : 'edui-bubble',
		_edittext : function() {
			baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
			me.execCommand(thePlugins);
			this.hide();
		},
		_delete : function() {
			if (window.confirm('是否删除此组件？删除后页面功能将会受到影响,请慎重操作!!!')) {
				baidu.editor.dom.domUtils.remove(this.anchorEl, false);
			}
			this.hide();
		}
	});
	popup.render();
	me.addListener('mouseover', function(t, evt) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
		var xtype = el.getAttribute("xtype");
		var tagName = el.tagName;
		if (tagName == 'IMG' && xtype == thePlugins) {
			var html = popup.formatHtml('<nobr>条形码: <span onclick=$$._edittext() class="edui-clickable">编辑</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>');
			if (html) {
				popup.getDom('content').innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor(popup.anchorEl);
			} else {
				popup.hide();
			}
		}
	});
};

//文件上传
UE.plugins['xupload'] = function() {
	var me = this, thePlugins = 'xupload';
	me.commands[thePlugins] = {
		execCommand : function() {
			var editor = this;
			var element = UE.plugins[thePlugins].editdom;
			$("#xpluging").html(xuploadmodal);
			if (element != null) {
				$("#name").val(element.getAttribute('name'));
    			$("#title").val(element.getAttribute('title'));
				$(".js-savexuploadbtn").unbind("click").click(function() {
					var name = $("#name").val();
					var style = $("#style").val();
					var title = $("#title").val();
					if (title == "") {
						top.layer.msg("字段标题不能为空!");
						return false;
					}
					if (testTableFieldsName(name)) {
						element.setAttribute('name', name);
						element.setAttribute('title', title);
						element.setAttribute('class', 'form-control');
						element.setAttribute('placeholder', "文件上传|"+title);
						element.setAttribute('xtype', 'xupload');
						delete UE.plugins[thePlugins].editdom;
						$("#xmodal").modal("hide");
					}
				})
			} else
			{
			$(".js-savexuploadbtn").unbind("click").click(function() {
				var name=$("#name").val();
    			var dataType=$("#dataType").val();
    			var style=$("#style").val();
    			var defaultValue=$("#defaultValue").val();
    			var title=$("#title").val();
    			var html = "<input type='file' " +
    						"xtype='xupload' " +
        					"title='"+title+"' " +
        					"name='"+name+"' " +
        					"class='form-control' " +
        					"placeholder='文件上传|"+title+"'/>";
    			if (title == "") {
					top.layer.msg("字段标题不能为空!");
					return false;
				}
				if (testTableFieldsName(name)) {
					editor.execCommand('insertHtml', html);
					$("#xmodal").modal("hide");
				}
			})
			}
			$("#xmodal").modal("show");
		}
	};
	var popup = new baidu.editor.ui.Popup({
		editor : this,
		content : '',
		className : 'edui-bubble',
		_edittext : function() {
			baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
			me.execCommand(thePlugins);
			this.hide();
		},
		_delete : function() {
			if (window.confirm('是否删除此组件？删除后页面功能将会受到影响,请慎重操作!!!')) {
				baidu.editor.dom.domUtils.remove(this.anchorEl, false);
			}
			this.hide();
		}
	});
	popup.render();
	me.addListener('mouseover', function(t, evt) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
		var xtype = el.getAttribute("xtype");
		var tagName = el.tagName;
		if (tagName == 'INPUT' && xtype == thePlugins) {
			var html = popup.formatHtml('<nobr>文件上传: <span onclick=$$._edittext() class="edui-clickable">编辑</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>');
			if (html) {
				popup.getDom('content').innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor(popup.anchorEl);
			} else {
				popup.hide();
			}
		}
	});
};
//嵌套窗体
UE.plugins['xiframe'] = function() {
	var me = this, thePlugins = 'xiframe';
	me.commands[thePlugins] = {
		execCommand : function() {
			var editor = this;
			var element = UE.plugins[thePlugins].editdom;
			$("#xpluging").html(xiframemodal);
			if (element != null) {
				$("#name").val(element.getAttribute('name'));
    			$("#style").val(element.getAttribute('style'));
    			$("#title").val(element.getAttribute('title'));
    			$("#defaultValue").val(element.getAttribute('defaultValue'));
				$(".js-savexiframebtn").unbind("click").click(function() {
					var name = $("#name").val();
					var dataType = $("#dataType").val();
					var style = $("#style").val();
					var defaultValue = $("#defaultValue").val();
					var title = $("#title").val();
					if (title == "") {
						top.layer.msg("字段标题不能为空!");
						return false;
					}
					if (testTableFieldsName(name)) {
						element.setAttribute('name', name);
						element.setAttribute('dataType', dataType);
						element.setAttribute('style', style);
						element.setAttribute('defaultValue', defaultValue);
						element.setAttribute('title', title);
						element.setAttribute('xtype', 'xiframe');
						delete UE.plugins[thePlugins].editdom;
						$("#xmodal").modal("hide");
					}
				})
			} else
			{
			$(".js-savexiframebtn").unbind("click").click(function() {
				var name=$("#name").val();
    			var style=$("#style").val();
    			var defaultValue=$("#defaultValue").val();
    			var title=$("#title").val();
    			var html = "<img xtype='xiframe' " +
			        			"title='"+title+"' " +
								"name='"+name+"' " +
								"style='"+style+"' " +
								"defaultValue='"+defaultValue+"' " +
    							"src='/module/ueditor/formdesign/images/web.png'/>";
    			if (title == "") {
					top.layer.msg("字段标题不能为空!");
					return false;
				}
				if (testTableFieldsName(name)) {
					editor.execCommand('insertHtml', html);
					$("#xmodal").modal("hide");
				}
			})
			}
			$("#xmodal").modal("show");
		}
	};
	var popup = new baidu.editor.ui.Popup({
		editor : this,
		content : '',
		className : 'edui-bubble',
		_edittext : function() {
			baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
			me.execCommand(thePlugins);
			this.hide();
		},
		_delete : function() {
			if (window.confirm('是否删除此组件？删除后页面功能将会受到影响,请慎重操作!!!')) {
				baidu.editor.dom.domUtils.remove(this.anchorEl, false);
			}
			this.hide();
		}
	});
	popup.render();
	me.addListener('mouseover', function(t, evt) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
		var xtype = el.getAttribute("xtype");
		var tagName = el.tagName;
		if (tagName == 'IMG' && xtype == thePlugins) {
			var html = popup.formatHtml('<nobr>嵌套窗体: <span onclick=$$._edittext() class="edui-clickable">编辑</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>');
			if (html) {
				popup.getDom('content').innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor(popup.anchorEl);
			} else {
				popup.hide();
			}
		}
	});
};
//SQL下拉组件
UE.plugins['xsqlselect'] = function() {
	var me = this, thePlugins = 'xsqlselect';
	me.commands[thePlugins] = {
		execCommand : function() {
			var editor = this;
			var element = UE.plugins[thePlugins].editdom;
			$("#xpluging").html(xsqlselectmodal);
			getDbSource();
			if (element != null) {
				$("#name").val(element.getAttribute('name'));
    			$("#dataType").val(element.getAttribute('dataType'));
    			$("#style").val(element.getAttribute('style'));
    			$("#defaultValue").val(element.getAttribute('defaultValue'));
    			$("#title").val(element.getAttribute('title'));
    			var model=element.getAttribute('model');
    			var json=JSON.parse(model);
    			$("#dbSource").val(json.dbSource);
    			console.log(json.dbSource);
    			$("#sql").val(json.sql);
				$(".js-savexsqlselectbtn").unbind("click").click(function() {
					var name = $("#name").val();
					var dataType = $("#dataType").val();
					var style = $("#style").val();
					var defaultValue = $("#defaultValue").val();
					var title = $("#title").val();
					if (title == "") {
						top.layer.msg("字段标题不能为空!");
						return false;
					}
					if (testTableFieldsName(name)) {
						var dbSource=$("#dbSource").val();
		    			var sql=$("#sql").val().replace(/\'/g,"&#39;");
		    			var json={};
		    			json.dbSource=dbSource;
		    			json.sql=sql;
		    			var modelStr=JSON.stringify(json)
						element.setAttribute('name', name);
						element.setAttribute('dataType', dataType);
						element.setAttribute('style', style);
						element.setAttribute('defaultValue', defaultValue);
						element.setAttribute('title', title);
						element.setAttribute('model', modelStr);
						element.setAttribute('class', 'form-control');
						element.setAttribute('placeholder', "SQL下拉组件|"+title);
						element.setAttribute('xtype', 'xsqlselect');
						delete UE.plugins[thePlugins].editdom;
						$("#xmodal").modal("hide");
					}
				})
			} else
			{
			$(".js-savexsqlselectbtn").unbind("click").click(function() {
				var name=$("#name").val();
    			var dataType=$("#dataType").val();
    			var style=$("#style").val();
    			var defaultValue=$("#defaultValue").val();
    			var title=$("#title").val();
    			var dbSource=$("#dbSource").val();
    			var sql=$("#sql").val().replace(/\'/g,"&#39;");
    			var json={};
    			json.dbSource=dbSource;
    			json.sql=sql;
    			var modelStr=JSON.stringify(json)
    			var html = "<input type='text' " +
    						"xtype='xsqlselect' " +
        					"title='"+title+"' " +
        					"name='"+name+"' " +
        					"dataType='"+dataType+"' " +
        					"model='"+modelStr+"' " +
        					"defaultValue='"+defaultValue+"' " +
        					"value='"+defaultValue+"' " +
        					"style='"+style+"' " +
        					"class='form-control' " +
        					"placeholder='SQL下拉组件|"+title+"'/>";
    			if (title == "") {
					top.layer.msg("字段标题不能为空!");
					return false;
				}
				if (testTableFieldsName(name)) {
					editor.execCommand('insertHtml', html);
					$("#xmodal").modal("hide");
				}
			})
			}
			$("#xmodal").modal("show");
		}
	};
	var popup = new baidu.editor.ui.Popup({
		editor : this,
		content : '',
		className : 'edui-bubble',
		_edittext : function() {
			baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
			me.execCommand(thePlugins);
			this.hide();
		},
		_delete : function() {
			if (window.confirm('是否删除此组件？删除后页面功能将会受到影响,请慎重操作!!!')) {
				baidu.editor.dom.domUtils.remove(this.anchorEl, false);
			}
			this.hide();
		}
	});
	popup.render();
	me.addListener('mouseover', function(t, evt) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
		var xtype = el.getAttribute("xtype");
		var tagName = el.tagName;
		if (tagName == 'INPUT' && xtype == thePlugins) {
			var html = popup.formatHtml('<nobr>SQL下拉组件: <span onclick=$$._edittext() class="edui-clickable">编辑</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>');
			if (html) {
				popup.getDom('content').innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor(popup.anchorEl);
			} else {
				popup.hide();
			}
		}
	});
};

//SQL组件
UE.plugins['xsql'] = function() {
	var me = this, thePlugins = 'xsql';
	me.commands[thePlugins] = {
		execCommand : function() {
			var editor = this;
			var element = UE.plugins[thePlugins].editdom;
			$("#xpluging").html(xsqlmodal);
			getDbSource();
			if (element != null) {
				$("#name").val(element.getAttribute('name'));
    			$("#dataType").val(element.getAttribute('dataType'));
    			$("#style").val(element.getAttribute('style'));
    			$("#defaultValue").val(element.getAttribute('defaultValue'));
    			$("#title").val(element.getAttribute('title'));
    			var model=element.getAttribute('model');
    			var json=JSON.parse(model);
    			$("#dbSource").val(json.dbSource);
    			console.log(json.dbSource);
    			$("#sql").val(json.sql);
				$(".js-savexsqlbtn").unbind("click").click(function() {
					var name = $("#name").val();
					var dataType = $("#dataType").val();
					var style = $("#style").val();
					var defaultValue = $("#defaultValue").val();
					var title = $("#title").val();
					if (title == "") {
						top.layer.msg("字段标题不能为空!");
						return false;
					}
					if (testTableFieldsName(name)) {
						var dbSource=$("#dbSource").val();
		    			var sql=$("#sql").val().replace(/\'/g,"&#39;");
		    			var json={};
		    			json.dbSource=dbSource;
		    			json.sql=sql;
		    			var modelStr=JSON.stringify(json)
						element.setAttribute('name', name);
						element.setAttribute('dataType', dataType);
						element.setAttribute('style', style);
						element.setAttribute('defaultValue', defaultValue);
						element.setAttribute('title', title);
						element.setAttribute('model', modelStr);
						element.setAttribute('class', 'form-control');
						element.setAttribute('placeholder', "SQL组件|"+title);
						element.setAttribute('xtype', 'xsql');
						delete UE.plugins[thePlugins].editdom;
						$("#xmodal").modal("hide");
					}
				})
			} else
			{
			$(".js-savexsqlbtn").unbind("click").click(function() {
				var name=$("#name").val();
    			var dataType=$("#dataType").val();
    			var style=$("#style").val();
    			var defaultValue=$("#defaultValue").val();
    			var title=$("#title").val();
    			var dbSource=$("#dbSource").val();
    			var sql=$("#sql").val().replace(/\'/g,"&#39;");
    			var json={};
    			json.dbSource=dbSource;
    			json.sql=sql;
    			var modelStr=JSON.stringify(json)
    			var html = "<input type='text' " +
    						"xtype='xsql' " +
        					"title='"+title+"' " +
        					"name='"+name+"' " +
        					"dataType='"+dataType+"' " +
        					"model='"+modelStr+"' " +
        					"defaultValue='"+defaultValue+"' " +
        					"value='"+defaultValue+"' " +
        					"style='"+style+"' " +
        					"class='form-control' " +
        					"placeholder='SQL组件|"+title+"'/>";
    			if (title == "") {
					top.layer.msg("字段标题不能为空!");
					return false;
				}
				if (testTableFieldsName(name)) {
					editor.execCommand('insertHtml', html);
					$("#xmodal").modal("hide");
				}
			})
			}
			$("#xmodal").modal("show");
		}
	};
	var popup = new baidu.editor.ui.Popup({
		editor : this,
		content : '',
		className : 'edui-bubble',
		_edittext : function() {
			baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
			me.execCommand(thePlugins);
			this.hide();
		},
		_delete : function() {
			if (window.confirm('是否删除此组件？删除后页面功能将会受到影响,请慎重操作!!!')) {
				baidu.editor.dom.domUtils.remove(this.anchorEl, false);
			}
			this.hide();
		}
	});
	popup.render();
	me.addListener('mouseover', function(t, evt) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
		var xtype = el.getAttribute("xtype");
		var tagName = el.tagName;
		if (tagName == 'INPUT' && xtype == thePlugins) {
			var html = popup.formatHtml('<nobr>SQL组件: <span onclick=$$._edittext() class="edui-clickable">编辑</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>');
			if (html) {
				popup.getDom('content').innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor(popup.anchorEl);
			} else {
				popup.hide();
			}
		}
	});
};
function getDbSource()
{
	$.ajax({
		url:"/ret/sysget/getDbSourceList",
		type:"POST",
		dataType:"json",
		async : false,
		success:function(data){
			if(data.status==200)
				{
				var html="<option value='SYS_DB'>当前系统</option>";
				for(var i=0;i<data.list.length;i++)
					{
					html+="<option value='"+data.list[i].dbSourceId+"'>"+data.list[i].dbSourceName+"</option>";
					}
				$("#dbSource").html(html);
				}else if(data.status=="100")
					{
					top.layer.msg(data.msg);
					}else
						{
						console.log(data.msg);
						}
		}
	});
}

//选择组件
UE.plugins['xfetch'] = function() {
	var me = this, thePlugins = 'xfetch';
	me.commands[thePlugins] = {
		execCommand : function() {
			var editor = this;
			var element = UE.plugins[thePlugins].editdom;
			$("#xpluging").html(xfetchmodal);
			$("#xfetchtype").on("change",function(){
    			switch($(this).val()){
    				case "1":
    					$("#format").removeAttr("disabled");
    					$("#format").html(renderDatetime());
    					break;
    				case "2":
    					$("#format").removeAttr("disabled");
    					$("#format").html(renderTime());
    					break;
    				default:
    					$("#format").html("");
    					$("#format").attr("disabled", "disabled");
    				
    			}
    		})
			if (element != null) {
				$("#name").val(element.getAttribute('name'));
    			$("#dataType").val(element.getAttribute('dataType'));
    			$("#style").val(element.getAttribute('style'));
    			$("#defaultValue").val(element.getAttribute('defaultValue'));
    			$("#title").val(element.getAttribute('title'));
    			var model=element.getAttribute('model');
    			var json=JSON.parse(model);
    			$("#xfetchtype").val(json.type);
    			if(json.type=="1")
    				{
    				$("#format").removeAttr("disabled");
					$("#format").html(renderDatetime());
    				}else if(json.type=="2")
    					{
    					$("#format").removeAttr("disabled");
    					$("#format").html(renderTime());
    					}else
    					{
    						$("#format").html("");
        					$("#format").attr("disabled", "disabled");
    					}
    			$("#format").val(json.format);
				$(".js-savexfetchbtn").unbind("click").click(function() {
					var name = $("#name").val();
					var dataType = $("#dataType").val();
					var style = $("#style").val();
					var defaultValue = $("#defaultValue").val();
					var title = $("#title").val();
					if (title == "") {
						top.layer.msg("字段标题不能为空!");
						return false;
					}
					if (testTableFieldsName(name)) {
						var json={};
		    			json.type=$("#xfetchtype").val();
		    			json.format=$("#format").val();
		    			var modelStr=JSON.stringify(json);
						element.setAttribute('name', name);
						element.setAttribute('dataType', dataType);
						element.setAttribute('style', style);
						element.setAttribute('defaultValue', defaultValue);
						element.setAttribute('title', title);
						element.setAttribute('model', modelStr);
						element.setAttribute('class', 'form-control');
						element.setAttribute('placeholder', "选择组件|"+title);
						element.setAttribute('xtype', 'xfetch');
						delete UE.plugins[thePlugins].editdom;
						$("#xmodal").modal("hide");
					}
				})
			} else
			{
			$(".js-savexfetchbtn").unbind("click").click(function() {
				var name=$("#name").val();
    			var dataType=$("#dataType").val();
    			var style=$("#style").val();
    			var defaultValue=$("#defaultValue").val();
    			var title=$("#title").val();
    			var json={};
    			json.type=$("#xfetchtype").val();
    			json.format=$("#format").val();
    			var modelStr=JSON.stringify(json);
    			var html = "<input type='text' " +
    						"xtype='xfetch' " +
        					"title='"+title+"' " +
        					"name='"+name+"' " +
        					"dataType='"+dataType+"' " +
        					"defaultValue='"+defaultValue+"' " +
        					"value='"+defaultValue+"' " +
        					"style='"+style+"' " +
        					"model='"+modelStr+"' " +
        					"class='form-control' " +
        					"placeholder='选择组件|"+title+"'/>";
    			if (title == "") {
					top.layer.msg("字段标题不能为空!");
					return false;
				}
				if (testTableFieldsName(name)) {
					editor.execCommand('insertHtml', html);
					$("#xmodal").modal("hide");
				}
			})
			}
			$("#xmodal").modal("show");
		}
	};
	var popup = new baidu.editor.ui.Popup({
		editor : this,
		content : '',
		className : 'edui-bubble',
		_edittext : function() {
			baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
			me.execCommand(thePlugins);
			this.hide();
		},
		_delete : function() {
			if (window.confirm('是否删除此组件？删除后页面功能将会受到影响,请慎重操作!!!')) {
				baidu.editor.dom.domUtils.remove(this.anchorEl, false);
			}
			this.hide();
		}
	});
	popup.render();
	me.addListener('mouseover', function(t, evt) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
		var xtype = el.getAttribute("xtype");
		var tagName = el.tagName;
		if (tagName == 'INPUT' && xtype == thePlugins) {
			var html = popup.formatHtml('<nobr>选择组件: <span onclick=$$._edittext() class="edui-clickable">编辑</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>');
			if (html) {
				popup.getDom('content').innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor(popup.anchorEl);
			} else {
				popup.hide();
			}
		}
	});
};
function renderDatetime() {
	var dom = "";
	dom += "<option value='YYYY-MM-DD'>YYYY-MM-DD</option>";
	dom += "<option value='YYYY-MM-DD hh:mm'>YYYY-MM-DD hh:mm</option>";
	dom += "<option value='YYYY-MM'>YYYY-MM</option>";
	dom += "<option value='yy-MM-DD'>yy-MM-DD</option>";
	dom += "<option value='YYYYMMdd'>YYYYMMdd</option>";
	dom += "<option value='MM-DD YYYY'>MM-DD YYYY</option>";
	dom += "<option value='YYYY年MM月'>YYYY年MM月</option>";
	dom += "<option value='YYYY年MM月DD日'>YYYY年MM月DD日</option>";
	dom += "<option value='YYYY年MM月DD日 hh:mm'>YYYY年MM月DD日 hh:mm</option>";
	dom += "<option value='YYYY年MM月DD日 hh时mm分'>YYYY年MM月DD日 hh时mm分</option>";
	dom += "<option value='MM月DD日'>MM月DD日</option>";
	dom += "<option value='YYYY.MM'>YYYY.MM</option>";
	dom += "<option value='YYYY.MM.DD'>YYYY.MM.DD</option>";
	dom += "<option value='MM.DD'>MM.DD</option>";
	return dom;
};
function renderTime () {
	var dom = "";
	dom += "<option value='hh时'>hh时</option>";
	dom += "<option value='hh时mm分'>hh时mm分</option>";
	dom += "<option value='hh时mm分ss秒'>hh时mm分ss秒</option>";
	dom += "<option value='hh:mm'>hh:mm</option>";
	dom += "<option value='hh:mm:ss'>hh:mm:ss</option>";
	return dom;
};

//宏组件
UE.plugins['xmacro'] = function() {
	var me = this, thePlugins = 'xmacro';
	me.commands[thePlugins] = {
		execCommand : function() {
			var editor = this;
			var element = UE.plugins[thePlugins].editdom;
			$("#xpluging").html(xmacromodal);
			$("#xmacrotype").on("change",function(){
    			var formatOption="";
    			switch($(this).val()){
    			case "1":
    				formatOption="<option value='yyyy年'>yyyy年</option>"+
    							  "<option value='yyyy'>yyyy</option>"+
    							  "<option value='yy年'>yy年</option>"+
    							  "<option value='yy'>yy</option>";
    				break;
    			case "2":
    				formatOption="<option value='yyyy-MM-dd'>yyyy-MM-dd</option>"+
    							 "<option value='yyyy年MM月dd日'>yyyy年MM月dd日</option>"+
    							 "<option value='yyyyMMdd'>yyyyMMdd</option>"+
    							 "<option value='yyyy/MM/dd'>yyyy/MM/dd</option>"+
    							 "<option value='yyyy.MM.dd'>yyyy.MM.dd</option>"+
    							 "<option value='yyyy年MM月'>yyyy年MM月</option>"+
    							 "<option value='MM月'>MM月</option>"+
    							 "<option value='dd日'>dd日</option>"+
    							 "<option value='MM-dd'>MM-dd</option>"+
    							 "<option value='MM月dd日'>MM月dd日</option>";
    				break;
    			case "3":
    				formatOption ="<option value='yyyy-MM-dd HH:mm'>yyyy-MM-dd HH:mm</option>"+
    								"<option value='yyyy-MM-dd HH:mm:ss'>yyyy-MM-dd HH:mm:ss</option>"+
    								"<option value='yyyy年MM月dd日 HH时mm分'>yyyy年MM月dd日 HH时mm分</option>"+
    								"<option value='yyyy年MM月dd日 HH时mm分ss秒'>yyyy年MM月dd日 HH时mm分ss秒</option>"+
    								"<option value='HH'>HH</option>"+
    								"<option value='HH时'>HH时</option>"+
    								"<option value='HH:mm'>HH:mm</option>"+
    								"<option value='HH时mm分'>HH时mm分</option>"+
    								"<option value='HH:mm:ss'>HH:mm:ss</option>"+
    								"<option value='HH时mm分ss秒'>HH时mm分ss秒</option>";
    				break;
    			}
    			$("#format").html(formatOption);
    		});
			if (element != null) {
				$("#name").val(element.getAttribute('name'));
				$("#dataType").val(element.getAttribute('dataType'));
				$("#style").val(element.getAttribute('style'));
				$("#defaultValue").val(element.getAttribute('defaultValue'));
				$("#title").val(element.getAttribute('title'));
				var model=element.getAttribute('model');
    			var json=JSON.parse(model);
    			$("#xmacrotype").val(json.type);
    			var formatOption="";
    			if(json.type=="1")
    				{
    				formatOption="<option value='yyyy年'>yyyy年</option>"+
					  "<option value='yyyy'>yyyy</option>"+
					  "<option value='yy年'>yy年</option>"+
					  "<option value='yy'>yy</option>";
    				}else if(json.type=="2")
    					{
    					formatOption="<option value='yyyy-MM-dd'>yyyy-MM-dd</option>"+
						 "<option value='yyyy年MM月dd日'>yyyy年MM月dd日</option>"+
						 "<option value='yyyyMMdd'>yyyyMMdd</option>"+
						 "<option value='yyyy/MM/dd'>yyyy/MM/dd</option>"+
						 "<option value='yyyy.MM.dd'>yyyy.MM.dd</option>"+
						 "<option value='yyyy年MM月'>yyyy年MM月</option>"+
						 "<option value='MM月'>MM月</option>"+
						 "<option value='dd日'>dd日</option>"+
						 "<option value='MM-dd'>MM-dd</option>"+
						 "<option value='MM月dd日'>MM月dd日</option>";
    					}else if(json.type=="3")
    						{
    						formatOption ="<option value='yyyy-MM-dd HH:mm'>yyyy-MM-dd HH:mm</option>"+
							"<option value='yyyy-MM-dd HH:mm:ss'>yyyy-MM-dd HH:mm:ss</option>"+
							"<option value='yyyy年MM月dd日 HH时mm分'>yyyy年MM月dd日 HH时mm分</option>"+
							"<option value='yyyy年MM月dd日 HH时mm分ss秒'>yyyy年MM月dd日 HH时mm分ss秒</option>"+
							"<option value='HH'>HH</option>"+
							"<option value='HH时'>HH时</option>"+
							"<option value='HH:mm'>HH:mm</option>"+
							"<option value='HH时mm分'>HH时mm分</option>"+
							"<option value='HH:mm:ss'>HH:mm:ss</option>"+
							"<option value='HH时mm分ss秒'>HH时mm分ss秒</option>";
    						}
    			
    			$("#format").html(formatOption);
    			$("#format").val(json.format);
				$(".js-savexmacrobtn").unbind("click").click(function() {
					var name = $("#name").val();
					var dataType = $("#dataType").val();
					var style = $("#style").val();
					var defaultValue = $("#defaultValue").val();
					var title = $("#title").val();
					if (title == "") {
						top.layer.msg("字段标题不能为空!");
						return false;
					}
					if (testTableFieldsName(name)) {
						var json={};
		    			json.type=$("#xmacrotype").val();
		    			json.format=$("#format").val();
		    			var modelStr=JSON.stringify(json);
						element.setAttribute('name', name);
						element.setAttribute('dataType', dataType);
						element.setAttribute('style', style);
						element.setAttribute('defaultValue', defaultValue);
						element.setAttribute('title', title);
						element.setAttribute('model', modelStr);
						element.setAttribute('class', 'form-control');
						element.setAttribute('placeholder', "宏组件|"+title);
						element.setAttribute('xtype', 'xmacro');
						delete UE.plugins[thePlugins].editdom;
						$("#xmodal").modal("hide");
					}
				})
			} else
			{
			$(".js-savexmacrobtn").unbind("click").click(function() {
				var name=$("#name").val();
    			var dataType=$("#dataType").val();
    			var style=$("#style").val();
    			var defaultValue=$("#defaultValue").val();
    			var title=$("#title").val();
    			var json={};
    			json.type=$("#xmacrotype").val();
    			json.format=$("#format").val();
    			var modelStr=JSON.stringify(json);
    			var html = "<input type='text' " +
    						"xtype='xmacro' " +
        					"title='"+title+"' " +
        					"name='"+name+"' " +
        					"dataType='"+dataType+"' " +
        					"defaultValue='"+defaultValue+"' " +
        					"style='"+style+"' " +
        					"model='"+modelStr+"' " +
        					"class='form-control' " +
        					"placeholder='宏组件|"+title+"'/>";
    			if (title == "") {
					top.layer.msg("字段标题不能为空!");
					return false;
				}
				if (testTableFieldsName(name)) {
					editor.execCommand('insertHtml', html);
					$("#xmodal").modal("hide");
				}
			})
			}
			$("#xmodal").modal("show");
		}
	};
	var popup = new baidu.editor.ui.Popup({
		editor : this,
		content : '',
		className : 'edui-bubble',
		_edittext : function() {
			baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
			me.execCommand(thePlugins);
			this.hide();
		},
		_delete : function() {
			if (window.confirm('是否删除此组件？删除后页面功能将会受到影响,请慎重操作!!!')) {
				baidu.editor.dom.domUtils.remove(this.anchorEl, false);
			}
			this.hide();
		}
	});
	popup.render();
	me.addListener('mouseover', function(t, evt) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
		var xtype = el.getAttribute("xtype");
		var tagName = el.tagName;
		if (tagName == 'INPUT' && xtype == thePlugins) {
			var html = popup.formatHtml('<nobr>宏组件: <span onclick=$$._edittext() class="edui-clickable">编辑</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>');
			if (html) {
				popup.getDom('content').innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor(popup.anchorEl);
			} else {
				popup.hide();
			}
		}
	});
};

//列表组件
UE.plugins['xlist'] = function() {
	var me = this, thePlugins = 'xlist';
	me.commands[thePlugins] = {
		execCommand : function() {
			var editor = this;
			var element = UE.plugins[thePlugins].editdom;
			$("#xpluging").html(xlistmodal);
			if (element != null) {
				$("#name").val(element.getAttribute('name'));
    			$("#style").val(element.getAttribute('style'));
    			$("#title").val(element.getAttribute('title'));
    			var model = element.getAttribute('model');
    			var jsonarr=JSON.parse(model);
    			for(var i=0;i<jsonarr.length;i++)
				{
				var html=['<div class="optionclass"><div class="col-sm-2">',
					'       	 <input type="text" class="form-control" id="childTitle" name="childTitle" placeholder="字段标题" value="'+jsonarr[i].childTitle+'">',
					'    	</div>',
					'       <div class="col-sm-2">',
					'       	 <input type="text" class="form-control" id="childName" name="childName" placeholder="字段名称" value="'+jsonarr[i].childName+'">',
					'    	</div>',
					'       <div class="col-sm-1 checkbox no-padding">',
					'               <label class="no-padding">',
					'                 <input type="checkbox" name="isTotal" '+setxlistIsTotal(jsonarr[i].isTotal)+'>',
					'                 <span class="text"></span>',
					'               </label>',
					'    	</div>',
					'        <div class="col-sm-2 no-padding">',
					'         <input type="text" class="form-control" id="formula" name="formula" placeholder="计算公式" value="'+jsonarr[i].formula+'">',
					'         </div>',
					'        <label class="col-sm-2">',
					'			<select id="childModel" name="childModel" style="border-radius: 0px;">'+setxlistmodel(jsonarr[i].childModel)+'',
					'         </select>',
					'			</label>',
					'       <div class="col-sm-1 no-padding">',
					'       	 <input type="text" class="form-control" id="width" name="width" placeholder="样式宽度" value="'+jsonarr[i].width+'">',
					'    	</div>',
					'        <label class="col-sm-1 no-padding"><input type="text" class="form-control" id="defaultValue" name="defaultValue" placeholder="初始值" value="'+jsonarr[i].defaultValue+'"></label>',
					'        <label class="col-sm-1 no-padding"><a onclick="delrows(this);" class="btn btn-maroon">删除</a></label></div>'].join("");
				
				$("#childTableListDiv").append(html);
				}
				$(".js-savexlistbtn").unbind("click").click(function() {
					var name=$("#name").val();
	    			var style=$("#style").val();
	    			var defaultValue=$("#defaultValue").val();
	    			var title=$("#title").val();
					if (title == "") {
						top.layer.msg("字段标题不能为空!");
						return false;
					}
					if (testTableFieldsName(name)) {
						var modelStr=JSON.stringify(getxlistChildOpt());
						element.setAttribute('name', name);
						element.setAttribute('style', style);
						element.setAttribute('model', modelStr);
						element.setAttribute('title', title);
						element.setAttribute('src', "/module/ueditor/formdesign/images/xlist.png");
						element.setAttribute('xtype', 'xlist');
						delete UE.plugins[thePlugins].editdom;
						$("#xmodal").modal("hide");
					}
				})
			} else
			{
			$(".js-savexlistbtn").unbind("click").click(function() {
				var modelStr=JSON.stringify(getxlistChildOpt());
    			var name=$("#name").val();
    			var style=$("#style").val();
    			var title=$("#title").val();
    			var html = "<img xtype='xlist' " +
        					"title='"+title+"' " +
        					"name='"+name+"' " +
        					"model='"+modelStr+"' " +
        					"style='"+style+"' " +
        					"src='/module/ueditor/formdesign/images/xlist.png'/>";
    			if (title == "") {
					top.layer.msg("字段标题不能为空!");
					return false;
				}
				if (testTableFieldsName(name)) {
					editor.execCommand('insertHtml', html);
					$("#xmodal").modal("hide");
				}
			})
			}
			$("#xmodal").modal("show");
		}
	};
	var popup = new baidu.editor.ui.Popup({
		editor : this,
		content : '',
		className : 'edui-bubble',
		_edittext : function() {
			baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
			me.execCommand(thePlugins);
			this.hide();
		},
		_delete : function() {
			if (window.confirm('是否删除此组件？删除后页面功能将会受到影响,请慎重操作!!!')) {
				baidu.editor.dom.domUtils.remove(this.anchorEl, false);
			}
			this.hide();
		}
	});
	popup.render();
	me.addListener('mouseover', function(t, evt) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
		var xtype = el.getAttribute("xtype");
		var tagName = el.tagName;
		if (tagName == 'IMG' && xtype == thePlugins) {
			var html = popup.formatHtml('<nobr>列表组件: <span onclick=$$._edittext() class="edui-clickable">编辑</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>');
			if (html) {
				popup.getDom('content').innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor(popup.anchorEl);
			} else {
				popup.hide();
			}
		}
	});
};

function addxlistrows()
{
	$("#childTableListDiv").append(childTable);
	}
function delxlistrows(Obj)
{
	$(Obj).parent("label").parent("div").remove();
	}

function getxlistChildOpt()
{
	var jsonarr=[];
	$("#childTableListDiv").children(".optionclass").each(function(){
		var json={};
		json.childTitle=$(this).find("input[name='childTitle']").val();
		json.childName=$(this).find("input[name='childName']").val();
		json.formula=$(this).find("input[name='formula']").val();
		json.childModel=$(this).find("select[name='childModel']").val();
		json.isTotal=$(this).find("input[name='isTotal']").is(":checked");
		json.defaultValue=$(this).find("input[name='defaultValue']").val();
		json.width=$(this).find("input[name='width']").val();
		jsonarr.push(json);
	});
	return jsonarr;
	}
function setxlistIsTotal(flag)
{
	if(flag)
		{
		return "checked";
		}
	}

function setxlistmodel(modelval)
{
var html="";
if(modelval=="1")
	{
		html+="<option value='1' selected>文本输入框</option>";
		html+="<option value='2'>下拉列表</option>";
		html+="<option value='3'>单选框</option>";
		html+="<option value='4'>多选框</option>";
		html+="<option value='5'>时间选择</option>";
	}if(modelval=="2")
	{
		html+="<option value='1'>文本输入框</option>";
		html+="<option value='2' selected>下拉列表</option>";
		html+="<option value='3'>单选框</option>";
		html+="<option value='4'>多选框</option>";
		html+="<option value='5'>时间选择</option>";
	}if(modelval=="3")
	{
		html+="<option value='1'>文本输入框</option>";
		html+="<option value='2'>下拉列表</option>";
		html+="<option value='3' selected>单选框</option>";
		html+="<option value='4'>多选框</option>";
		html+="<option value='5'>时间选择</option>";
	}if(modelval=="4")
	{
		html+="<option value='1'>文本输入框</option>";
		html+="<option value='2'>下拉列表</option>";
		html+="<option value='3'>单选框</option>";
		html+="<option value='4' selected>多选框</option>";
		html+="<option value='5'>时间选择</option>";
	}if(modelval=="5")
	{
		html+="<option value='1'>文本输入框</option>";
		html+="<option value='2'>下拉列表</option>";
		html+="<option value='3'>单选框</option>";
		html+="<option value='4'>多选框</option>";
		html+="<option value='5' selected>时间选择</option>";
	}
	return html;
}
//多选按钮
UE.plugins['xcheckbox'] = function() {
	var me = this, thePlugins = 'xcheckbox';
	me.commands[thePlugins] = {
		execCommand : function() {
			var editor = this;
			var element = UE.plugins[thePlugins].editdom;
			$("#xpluging").html(xcheckboxmodal);
			if (element != null) {
				var model = element.getAttribute('model');
    			var jsonarr=JSON.parse(model);
    			for(var i=0;i<jsonarr.length;i++)
				{
				var c="";
				if(jsonarr[i].checked)
					{
					c="checked='checked'";
					}
				var html=['<div class="optdivs"><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm value" value="'+jsonarr[i].vals+'"/></div><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm name" value="'+jsonarr[i].option+'"/></div><div class="col-sm-2" align="center"><input type="checkbox" name="dxcheckbox" '+c+' style="opacity:1;position: unset"></div><div class="col-sm-2" align="center"><a style="line-height:34px;cursor: pointer;" onclick="deloption(this);">删除</a></div></div>'].join("");
				$("#optiondiv").append(html);
				}
    			$("#name").val(element.getAttribute('name'));
    			$("#dataType").val(element.getAttribute('dataType'));
    			$("#style").val(element.getAttribute('style'));
    			$("#title").val(element.getAttribute('title'));
				$(".js-savexcheckboxbtn").unbind("click").click(function() {
					var options=[];
        			$(".optdivs").each(function(){
        				var json={};
        				json.vals=$(this).find(".value").val();
        				json.option=$(this).find(".name").val();
        				json.checked=$(this).find('input:checkbox').is(':checked');
        				$(this).find(".input-sm").val();
        				options.push(json);
        			});
        			var modelStr=JSON.stringify(options);
        			var name=$("#name").val();
        			var dataType=$("#dataType").val();
        			var style=$("#style").val();
        			var title=$("#title").val();
        			var optStr="";
        			for(var i=0;i<options.length;i++)
    				{
    				if(options[i].checked)
    					{
    						optStr+="<input xtype='xcheckbox' name='"+name+"' title='"+title+"' dataType='"+dataType+"' style='"+style+"' model='"+modelStr+"' type='checkbox' checked='checked' value='"+options[i].vals+"'>"+options[i].option;
    					}else
    						{
    						optStr+="<input xtype='xcheckbox' name='"+name+"' title='"+title+"' dataType='"+dataType+"' style='"+style+"' model='"+modelStr+"' type='checkbox' value='"+options[i].vals+"'>"+options[i].option;
    						}
    				}
        			if(title=="")
					{
						top.layer.msg("字段标题不能为空!");
						return false;
					}
					if (testTableFieldsName(name)) {
						element.setAttribute('name', name);
						element.setAttribute('style', style);
						element.setAttribute('dataType', dataType);
						element.setAttribute('model', modelStr);
						element.setAttribute('title', title);
						element.setAttribute('xtype', 'xcheckbox');
						element.innerHTML = optStr;
						delete UE.plugins[thePlugins].editdom;
						$("#xmodal").modal("hide");
					}
				})
			} else
			{
			$(".js-savexcheckboxbtn").unbind("click").click(function() {
				var options=[];
    			$(".optdivs").each(function(){
    				var json={};
    				json.vals=$(this).find(".value").val();
    				json.option=$(this).find(".name").val();
    				json.checked=$(this).find('input:checkbox').is(':checked');
    				$(this).find(".input-sm").val();
    				options.push(json);
    			});
    			var modelStr=JSON.stringify(options);
    			var name=$("#name").val();
    			var dataType=$("#dataType").val();
    			var style=$("#style").val();
    			var title=$("#title").val();
    			var optStr="";
    			for(var i=0;i<options.length;i++)
				{
				if(options[i].checked)
					{
						optStr+="<input xtype='xcheckbox' name='"+name+"' title='"+title+"' dataType='"+dataType+"' style='"+style+"' model='"+modelStr+"' type='checkbox' checked='checked' value='"+options[i].vals+"'>"+options[i].option;
					}else
						{
						optStr+="<input xtype='xcheckbox' name='"+name+"' title='"+title+"' dataType='"+dataType+"' style='"+style+"' model='"+modelStr+"' type='checkbox' value='"+options[i].vals+"'>"+options[i].option;
						}
				}
    			if(title=="")
				{
					top.layer.msg("字段标题不能为空!");
					return false;
				}
            	var html = "<emxcheckbox id='em_id_"+name+"' emxtype='xcheckbox' name='"+name+"' title='"+title+"' dataType='"+dataType+"' style='"+style+"' model='"+modelStr+"'>"+optStr+"</em>";
            	editor.execCommand('insertHtml', html);
            	$("#xmodal").modal("hide");
			})
			}
			$("#xmodal").modal("show");
		}
	};
	var popup = new baidu.editor.ui.Popup({
		editor : this,
		content : '',
		className : 'edui-bubble',
		_edittext : function() {
			baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
			me.execCommand(thePlugins);
			this.hide();
		},
		_delete : function() {
			if (window.confirm('是否删除此组件？删除后页面功能将会受到影响,请慎重操作!!!')) {
				baidu.editor.dom.domUtils.remove(this.anchorEl, false);
			}
			this.hide();
		}
	});
	popup.render();
	me.addListener('mouseover', function(t, evt) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
		var xtype = el.getAttribute("emxtype");
		var tagName = el.tagName;
		if (tagName == 'EMXCHECKBOX' && xtype == thePlugins) {
			var html = popup.formatHtml('<nobr>多选按钮: <span onclick=$$._edittext() class="edui-clickable">编辑</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>');
			if (html) {
				popup.getDom('content').innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor(popup.anchorEl);
			} else {
				popup.hide();
			}
		}
	});
};
function addxcheckboxoption()
{
	var html=['<div class="optdivs"><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm value"/></div><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm name"/></div><div class="col-sm-2" align="center"><input type="checkbox" name="dxcheckbox" style="opacity:1;position: unset"></div><div class="col-sm-2" align="center"><a style="line-height:34px;cursor: pointer;" onclick="delxcheckboxoption(this);">删除</a></div></div>'].join("");
	$("#optiondiv").append(html);
	}

function delxcheckboxoption(Obj)
{
	$(Obj).parent().parent().remove();
	}
function clearxcheckbox()
{
	$("input[name=dxcheckbox]").each(function(){
		$(this).removeAttr("checked");
	});
	}

//单选按钮
UE.plugins['xradio'] = function() {
	var me = this, thePlugins = 'xradio';
	me.commands[thePlugins] = {
		execCommand : function() {
			var editor = this;
			var element = UE.plugins[thePlugins].editdom;
			$("#xpluging").html(xradiomodal);
			if (element != null) {
				var model = element.getAttribute('model');
    			var jsonarr=JSON.parse(model);
    			for(var i=0;i<jsonarr.length;i++)
    				{
    				var c="";
    				if(jsonarr[i].checked)
    					{
    					c="checked='checked'";
    					}
    				var html=['<div class="optdivs"><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm value" value="'+jsonarr[i].vals+'"/></div><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm name" value="'+jsonarr[i].option+'"/></div><div class="col-sm-2" align="center"><input type="radio" name="dfradio" '+c+' style="opacity:1;position: unset"></div><div class="col-sm-2" align="center"><a style="line-height:34px;cursor: pointer;" onclick="deloption(this);">删除</a></div></div>'].join("");
    				$("#optiondiv").append(html);
    				}
    			$("#name").val(element.getAttribute('name'));
    			$("#dataType").val(element.getAttribute('dataType'));
    			$("#style").val(element.getAttribute('style'));
    			$("#title").val(element.getAttribute('title'));
				$(".js-savexradiobtn").unbind("click").click(function() {
					var options=[];
        			$(".optdivs").each(function(){
        				var json={};
        				json.vals=$(this).find(".value").val();
        				json.option=$(this).find(".name").val();
        				json.checked=$(this).find('input:radio').is(':checked');
        				$(this).find(".input-sm").val();
        				options.push(json);
        			});
        			var modelStr=JSON.stringify(options);
        			var name=$("#name").val();
        			var dataType=$("#dataType").val();
        			var style=$("#style").val();
        			var title=$("#title").val();
        			var optStr="";
        			for(var i=0;i<options.length;i++)
        				{
        				if(options[i].checked)
        					{
        						optStr+="<input xtype='xradio' name='"+name+"' title='"+title+"' dataType='"+dataType+"' style='"+style+"' model='"+modelStr+"' type='radio' checked='checked' value='"+options[i].vals+"'>"+options[i].option;
        					}else
        						{
        						optStr+="<input xtype='xradio' name='"+name+"' title='"+title+"' dataType='"+dataType+"' style='"+style+"' model='"+modelStr+"' type='radio' value='"+options[i].vals+"'>"+options[i].option;
        						}
        				}
        			if(title=="")
					{
						top.layer.msg("字段标题不能为空!");
						return false;
					}
					if (testTableFieldsName(name)) {
						element.setAttribute('name', name);
						element.setAttribute('style', style);
						element.setAttribute('dataType', dataType);
						element.setAttribute('model', modelStr);
						element.setAttribute('title', title);
						element.setAttribute('xtype', 'xradio');
						element.innerHTML = optStr;
						delete UE.plugins[thePlugins].editdom;
						$("#xmodal").modal("hide");
					}
				})
			} else
			{
			$(".js-savexradiobtn").unbind("click").click(function() {
				var options=[];
    			$(".optdivs").each(function(){
    				var json={};
    				json.vals=$(this).find(".value").val();
    				json.option=$(this).find(".name").val();
    				json.checked=$(this).find('input:radio').is(':checked');
    				$(this).find(".input-sm").val();
    				options.push(json);
    			});
    			var modelStr=JSON.stringify(options);
    			var name=$("#name").val();
    			var dataType=$("#dataType").val();
    			var style=$("#style").val();
    			var title=$("#title").val();
    			var optStr="";
    			for(var i=0;i<options.length;i++)
    				{
    				if(options[i].checked)
    					{
    						optStr+="<input xtype='xradio' name='"+name+"' title='"+title+"' dataType='"+dataType+"' style='"+style+"' model='"+modelStr+"' type='radio' checked='checked' value='"+options[i].vals+"'>"+options[i].option;
    					}else
    						{
    						optStr+="<input xtype='xradio' name='"+name+"' title='"+title+"' dataType='"+dataType+"' style='"+style+"' model='"+modelStr+"' type='radio' value='"+options[i].vals+"'>"+options[i].option;
    						}
    				}
    			if(title=="")
				{
					top.layer.msg("字段标题不能为空!");
					return false;
				}
            	var html = "<emxradio id='em_id_"+name+"' emxtype='xradio' name='"+name+"' title='"+title+"' dataType='"+dataType+"' style='"+style+"' model='"+modelStr+"'>"+optStr+"</em>";
            	editor.execCommand('insertHtml', html);
            	$("#xmodal").modal("hide");
			})
			}
			$("#xmodal").modal("show");
		}
	};
	var popup = new baidu.editor.ui.Popup({
		editor : this,
		content : '',
		className : 'edui-bubble',
		_edittext : function() {
			baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
			me.execCommand(thePlugins);
			this.hide();
		},
		_delete : function() {
			if (window.confirm('是否删除此组件？删除后页面功能将会受到影响,请慎重操作!!!')) {
				baidu.editor.dom.domUtils.remove(this.anchorEl, false);
			}
			this.hide();
		}
	});
	popup.render();
	me.addListener('mouseover', function(t, evt) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
		var xtype = el.getAttribute("emxtype");
		var tagName = el.tagName;
		if (tagName == 'EMXRADIO' && xtype == thePlugins) {
			var html = popup.formatHtml('<nobr>单选按钮: <span onclick=$$._edittext() class="edui-clickable">编辑</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>');
			if (html) {
				popup.getDom('content').innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor(popup.anchorEl);
			} else {
				popup.hide();
			}
		}
	});
};

function addxradiooption()
{
	var html=['<div class="optdivs"><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm value"/></div><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm name"/></div><div class="col-sm-2" align="center"><input type="radio" name="dfradio" style="opacity:1;position: unset"></div><div class="col-sm-2" align="center"><a style="line-height:34px;cursor: pointer;" onclick="delxradionoption(this);">删除</a></div></div>'].join("");
	$("#optiondiv").append(html);
	}

function delxradionoption(Obj)
{
	$(Obj).parent().parent().remove();
	}
function clearxradiochecked()
{
	$("input[name=dfradio]").each(function(){
		$(this).removeAttr("checked");
	});
	}

//宣文本编辑器
UE.plugins['xtextuedit'] = function() {
	var me = this, thePlugins = 'xtextuedit';
	me.commands[thePlugins] = {
		execCommand : function() {
			var editor = this;
			var element = UE.plugins[thePlugins].editdom;
			$("#xpluging").html(xtextueditmodal);
			if (element != null) {
				$("#name").val(element.getAttribute('name'));
    			$("#style").val(element.getAttribute('style'));
    			$("#defaultValue").val(element.getAttribute('defaultValue'));
    			$("#title").val(element.getAttribute('title'));
				$(".js-savextextueditbtn").unbind("click").click(function() {
					var name=$("#name").val();
	    			var style=$("#style").val();
	    			var defaultValue=$("#defaultValue").val();
	    			var title=$("#title").val();
					if (title == "") {
						top.layer.msg("字段标题不能为空!");
						return false;
					}
					if (testTableFieldsName(name)) {
						element.setAttribute('name', name);
						element.setAttribute('style', style);
						element.setAttribute('defaultValue', defaultValue);
						element.setAttribute('title', title);
						element.setAttribute('src', "/module/ueditor/formdesign/images/editor.jpg");
						element.setAttribute('xtype', 'xtextuedit');
						delete UE.plugins[thePlugins].editdom;
						$("#xmodal").modal("hide");
					}
				})
			} else
			{
			$(".js-savextextueditbtn").unbind("click").click(function() {
				var name=$("#name").val();
    			var style=$("#style").val();
    			var defaultValue=$("#defaultValue").val();
    			var title=$("#title").val();
    			var html = "<img xtype='xtextuedit' " +
			        			"title='"+title+"' " +
								"name='"+name+"' " +
								"defaultValue='"+defaultValue+"' " +
								"style='"+style+"' " +
    							"src='/module/ueditor/formdesign/images/editor.jpg'/>";
    			if (title == "") {
					top.layer.msg("字段标题不能为空!");
					return false;
				}
				if (testTableFieldsName(name)) {
					editor.execCommand('insertHtml', html);
					$("#xmodal").modal("hide");
				}
			})
			}
			$("#xmodal").modal("show");
		}
	};
	var popup = new baidu.editor.ui.Popup({
		editor : this,
		content : '',
		className : 'edui-bubble',
		_edittext : function() {
			baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
			me.execCommand(thePlugins);
			this.hide();
		},
		_delete : function() {
			if (window.confirm('是否删除此组件？删除后页面功能将会受到影响,请慎重操作!!!')) {
				baidu.editor.dom.domUtils.remove(this.anchorEl, false);
			}
			this.hide();
		}
	});
	popup.render();
	me.addListener('mouseover', function(t, evt) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
		var xtype = el.getAttribute("xtype");
		var tagName = el.tagName;
		if (tagName == 'IMG' && xtype == thePlugins) {
			var html = popup.formatHtml('<nobr>富文本框: <span onclick=$$._edittext() class="edui-clickable">编辑</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>');
			if (html) {
				popup.getDom('content').innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor(popup.anchorEl);
			} else {
				popup.hide();
			}
		}
	});
};
//下拉列表
UE.plugins['xselect'] = function() {
	var me = this, thePlugins = 'xselect';
	me.commands[thePlugins] = {
		execCommand : function() {
			var editor = this;
			var element = UE.plugins[thePlugins].editdom;
			$("#xpluging").html(xselectmodal);
			if (element != null) {
				var model = element.getAttribute('model');
				var jsonarr=JSON.parse(model);
    			for(var i=0;i<jsonarr.length;i++)
    				{
    				var c="";
    				if(jsonarr[i].checked)
    					{
    					c="checked='checked'";
    					}
    				var html=['<div class="optdivs"><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm value" value="'+jsonarr[i].vals+'"/></div><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm name" value="'+jsonarr[i].option+'"/></div><div class="col-sm-2" align="center"><input type="radio" name="dfradio" '+c+' style="opacity:1;position: unset"></div><div class="col-sm-2" align="center"><a style="line-height:34px;cursor: pointer;" onclick="deloption(this);">删除</a></div></div>'].join("");
    				$("#optiondiv").append(html);
    				}
    			$("#name").val(element.getAttribute('name'));
    			$("#dataType").val(element.getAttribute('dataType'));
    			$("#style").val(element.getAttribute('style'));
    			$("#title").val(element.getAttribute('title'));
				$(".js-savexselectbtn").unbind("click").click(function() {
					var name = $("#name").val();
					var dataType = $("#dataType").val();
					var style = $("#style").val();
					var title = $("#title").val();
					if (title == "") {
						top.layer.msg("字段标题不能为空!");
						return false;
					}
					if (testTableFieldsName(name)) {
						var options=[];
	        			$(".optdivs").each(function(){
	        				var json={};
	        				json.vals=$(this).find(".value").val();
	        				json.option=$(this).find(".name").val();
	        				json.checked=$(this).find('input:radio').is(':checked');
	        				$(this).find(".input-sm").val();
	        				options.push(json);
	        			});
	        			var optStr="";
	        			for(var i=0;i<options.length;i++)
	        				{
	        				if(options[i].checked)
	        					{
	        					optStr+="<option selected='selected' value='"+options[i].vals+"'>"+options[i].option+"</option>";
	        					}else
	        						{
	        						optStr+="<option value='"+options[i].vals+"'>"+options[i].option+"</option>";
	        						}
	        				}
	        			var modelStr=JSON.stringify(options);
						element.setAttribute('name', name);
						element.setAttribute('dataType', dataType);
						element.setAttribute('style', style);
						element.setAttribute('model', modelStr);
						element.setAttribute('title', title);
						element.setAttribute('class', 'form-control');
						element.setAttribute('xtype', 'xselect');
						delete UE.plugins[thePlugins].editdom;
						$("#xmodal").modal("hide");
					}
				})
			} else {
				$(".js-savexselectbtn").unbind("click").click(function() {
					var options=[];
        			$(".optdivs").each(function(){
        				var json={};
        				json.vals=$(this).find(".value").val();
        				json.option=$(this).find(".name").val();
        				json.checked=$(this).find('input:radio').is(':checked');
        				$(this).find(".input-sm").val();
        				options.push(json);
        			});
        			var optStr="";
        			for(var i=0;i<options.length;i++)
        				{
        				if(options[i].checked)
        					{
        					optStr+="<option selected='selected' value='"+options[i].vals+"'>"+options[i].option+"</option>";
        					}else
        						{
        						optStr+="<option value='"+options[i].vals+"'>"+options[i].option+"</option>";
        						}
        				}
        			var modelStr=JSON.stringify(options);
        			var name=$("#name").val();
        			var dataType=$("#dataType").val();
        			var style=$("#style").val();
        			var title=$("#title").val();
        			var html = "<select xtype='xselect'" +
					        			"title='"+title+"' " +
										"name='"+name+"' " +
										"dataType='"+dataType+"' " +
										"style='"+style+"' " +
			        					"class='form-control' " +
			        					"model='"+modelStr+"'>"+optStr+"</select>";
        			if(title=="")
					{
						top.layer.msg("字段标题不能为空!");
						return false;
					}
					if (testTableFieldsName(name)) {
						editor.execCommand('insertHtml', html);
						$("#xmodal").modal("hide");
					}
				})
			}
			$("#xmodal").modal("show");

		}
	};
	var popup = new baidu.editor.ui.Popup({
		editor : this,
		content : '',
		className : 'edui-bubble',
		_edittext : function() {
			baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
			me.execCommand(thePlugins);
			this.hide();
		},
		_delete : function() {
			if (window.confirm('是否删除此组件？删除后页面功能将会受到影响,请慎重操作!!!')) {
				baidu.editor.dom.domUtils.remove(this.anchorEl, false);
			}
			this.hide();
		}
	});
	popup.render();
	me.addListener('mouseover', function(t, evt) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
		var xtype = el.getAttribute("xtype");
		var tagName = el.tagName;
		if (tagName == 'SELECT' && xtype == thePlugins) {
			var html = popup.formatHtml('<nobr>下拉列表: <span onclick=$$._edittext() class="edui-clickable">编辑</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>');
			if (html) {
				popup.getDom('content').innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor(popup.anchorEl);
			} else {
				popup.hide();
			}
		}
	});
};

function xselectaddoption()
{
	var html=['<div class="optdivs"><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm value"/></div><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm name"/></div><div class="col-sm-2" align="center"><input type="radio" name="dfradio" style="opacity:1;position: unset"></div><div class="col-sm-2" align="center"><a style="line-height:34px;cursor: pointer;" onclick="xselectdeloption(this);">删除</a></div></div>'].join("");
	$("#optiondiv").append(html);
	}

function xselectdeloption(Obj)
{
	$(Obj).parent().parent().remove();
}
function xselectclearchecked()
{
	$("input[name=dfradio]").each(function(){
		$(this).removeAttr("checked");
	});
}

//多行输入框
UE.plugins['xtextarea'] = function() {
	var me = this, thePlugins = 'xtextarea';
	me.commands[thePlugins] = {
		execCommand : function() {
			var editor = this;
			var element = UE.plugins[thePlugins].editdom;
			$("#xpluging").html(xtextareamodal);
			if (element != null) {
				$("#name").val(element.getAttribute('name'));
				$("#dataType").val(element.getAttribute('dataType'));
				$("#style").val(element.getAttribute('style'));
				$("#defaultValue").val(element.getAttribute('defaultValue'));
				$("#title").val(element.getAttribute('title'));
				$(".js-savextextareabtn").unbind("click").click(function() {
					var name = $("#name").val();
					var dataType = $("#dataType").val();
					var style = $("#style").val();
					var defaultValue = $("#defaultValue").val();
					var title = $("#title").val();
					if (title == "") {
						top.layer.msg("字段标题不能为空!");
						return false;
					}
					if (testTableFieldsName(name)) {
						element.setAttribute('name', name);
						element.setAttribute('dataType', dataType);
						element.setAttribute('style', style);
						element.setAttribute('defaultValue', defaultValue);
						element.setAttribute('title', title);
						element.setAttribute('class', 'form-control');
						element.setAttribute('placeholder', "输入框|"+title);
						element.setAttribute('xtype', 'xtextarea');
						delete UE.plugins[thePlugins].editdom;
						$("#xmodal").modal("hide");
					}
				})
			} else {
				$(".js-savextextareabtn").unbind("click").click(function() {
					var name = $("#name").val();
					var dataType = $("#dataType").val();
					var style = $("#style").val();
					var defaultValue = $("#defaultValue").val();
					var title = $("#title").val();
					var html = "<textarea " + "xtype='xtextarea' " + "title='" + title + "' " + "name='" + name + "' " + "dataType='" + dataType + "' " + "defaultValue='" + defaultValue + "' " + "style='" + style + "' " + "class='form-control' " + "placeholder='" + title + "'></textarea>";
					if (title == "") {
						top.layer.msg("字段标题不能为空!");
						return false;
					}
					if (testTableFieldsName(name)) {
						editor.execCommand('insertHtml', html);
						$("#xmodal").modal("hide");
					}
				})
			}
			$("#xmodal").modal("show");

		}
	};
	var popup = new baidu.editor.ui.Popup({
		editor : this,
		content : '',
		className : 'edui-bubble',
		_edittext : function() {
			baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
			me.execCommand(thePlugins);
			this.hide();
		},
		_delete : function() {
			if (window.confirm('是否删除此组件？删除后页面功能将会受到影响,请慎重操作!!!')) {
				baidu.editor.dom.domUtils.remove(this.anchorEl, false);
			}
			this.hide();
		}
	});
	popup.render();
	me.addListener('mouseover', function(t, evt) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
		var xtype = el.getAttribute("xtype");
		var tagName = el.tagName;
		if (tagName == 'TEXTAREA' && xtype == thePlugins) {
			var html = popup.formatHtml('<nobr>多行输入框: <span onclick=$$._edittext() class="edui-clickable">编辑</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>');
			if (html) {
				popup.getDom('content').innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor(popup.anchorEl);
			} else {
				popup.hide();
			}
		}
	});
};
//单行输入框
UE.plugins['xinput'] = function() {
	var me = this, thePlugins = 'xinput';
	me.commands[thePlugins] = {
		execCommand : function() {
			var editor = this;
			var element = UE.plugins[thePlugins].editdom;
			$("#xpluging").html(xinputmodal);
			if (element != null) {
				$("#name").val(element.getAttribute('name'));
				$("#dataType").val(element.getAttribute('dataType'));
				$("#style").val(element.getAttribute('style'));
				$("#defaultValue").val(element.getAttribute('defaultValue'));
				$("#title").val(element.getAttribute('title'));
				$(".js-savexinputbtn").unbind("click").click(function() {
					var name = $("#name").val();
					var dataType = $("#dataType").val();
					var style = $("#style").val();
					var defaultValue = $("#defaultValue").val();
					var title = $("#title").val();
					if (title == "") {
						top.layer.msg("字段标题不能为空!");
						return false;
					}
					if (testTableFieldsName(name)) {
						element.setAttribute('name', name);
						element.setAttribute('dataType', dataType);
						element.setAttribute('style', style);
						element.setAttribute('defaultValue', defaultValue);
						element.setAttribute('title', title);
						element.setAttribute('class', 'form-control');
						element.setAttribute('placeholder', "输入框|"+title);
						element.setAttribute('xtype', 'xinput');
						delete UE.plugins[thePlugins].editdom;
						$("#xmodal").modal("hide");
					}
				})
			} else
			{
			$(".js-savexinputbtn").unbind("click").click(function() {
				var name = $("#name").val();
				var dataType = $("#dataType").val();
				var style = $("#style").val();
				var defaultValue = $("#defaultValue").val();
				var title = $("#title").val();
				var html = "<input type='text' " + "xtype='xinput' " + "title='" + title + "' " + "name='" + name + "' " + "dataType='" + dataType + "' " + "defaultValue='" + defaultValue + "' " + "style='" + style + "' " + "class='form-control' " + "placeholder='输入框|" + title + "'/>";
				if (title == "") {
					top.layer.msg("字段标题不能为空!");
					return false;
				}
				if (testTableFieldsName(name)) {
					editor.execCommand('insertHtml', html);
					$("#xmodal").modal("hide");
				}
			})
			}
			$("#xmodal").modal("show");
		}
	};
	var popup = new baidu.editor.ui.Popup({
		editor : this,
		content : '',
		className : 'edui-bubble',
		_edittext : function() {
			baidu.editor.plugins[thePlugins].editdom = popup.anchorEl;
			me.execCommand(thePlugins);
			this.hide();
		},
		_delete : function() {
			if (window.confirm('是否删除此组件？删除后页面功能将会受到影响,请慎重操作!!!')) {
				baidu.editor.dom.domUtils.remove(this.anchorEl, false);
			}
			this.hide();
		}
	});
	popup.render();
	me.addListener('mouseover', function(t, evt) {
		evt = evt || window.event;
		var el = evt.target || evt.srcElement;
		var xtype = el.getAttribute("xtype");
		var tagName = el.tagName;
		if (tagName == 'INPUT' && xtype == thePlugins) {
			var html = popup.formatHtml('<nobr>单行输入框: <span onclick=$$._edittext() class="edui-clickable">编辑</span>&nbsp;&nbsp;<span onclick=$$._delete() class="edui-clickable">删除</span></nobr>');
			if (html) {
				popup.getDom('content').innerHTML = html;
				popup.anchorEl = el;
				popup.showAnchor(popup.anchorEl);
			} else {
				popup.hide();
			}
		}
	});
};
