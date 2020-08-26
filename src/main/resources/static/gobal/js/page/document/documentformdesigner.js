var fieldList={};
var form=null;
var editor;
$(function(){
	$(".js-template").unbind("click").click(function(){
		template();
	});
	$(".js-version").unbind("click").click(function(){
		formversion();
	});
	var h=$(window).height()-115;
	CKEDITOR.editorConfig = function( config ){
		var html = "<div style=\"valign:middle;width:200px;height:100%;background-color:#0093FF;line-height:60px;text-align:center;margin-top:60px;color:#FFF;font-weight:900;\" align=\"center\" >请选择上传图片</div>";
		config.image_previewText=html; //预览区域显示内容
		config.extraPlugins = 'xinput,xtextarea,xtextuedit,xselect,xradio,xcheckbox,xlist,xmacro,xmacrotag,xfetch,xiframe,xcalculate,xdocnum,xsymbol,xsql,xsqlselect,xupload,xdocument,xhtml,xbarcode,xqrcode,xseal,xdocno';
//		config.filebrowserImageUploadUrl= contextPath+"/com/system/filetool/UpImgTool/imgUpload.act"; //待会要上传的action或servlet
		config.menu_groups = "clipboard,form,tablecell,tablecellproperties,tablerow,tablecolumn,table,anchor,link,flash,hiddenfield,imagebutton,button,div,xinput,xtextarea,xtextuedit,xsql,xsqlselect,xiframe,xradio,xselect,xcheckbox,xcalculate,xfetch,xlist,xseal,xmacrotag,xmacro,ximg,xdocnum,xsymbol,xuploads,xdocument,xupload,xhtml,xbarcode,xqrcode,xdocno";
		config.plugins = 'about,a11yhelp,basicstyles,bidi,blockquote,clipboard,colorbutton,colordialog,contextmenu,div,elementspath,enterkey,entities,filebrowser,find,flash,floatingspace,font,format,forms,horizontalrule,htmlwriter,image,iframe,indent,justify,link,list,liststyle,magicline,maximize,newpage,pagebreak,pastefromword,pastetext,preview,print,removeformat,resize,save,scayt,selectall,showblocks,showborders,smiley,sourcearea,specialchar,stylescombo,tab,table,tabletools,templates,toolbar,undo,wsc,wysiwygarea';
		config.allowedContent = true;
		config.baseFloatZIndex = 100;
		config.removePlugins='forms';
		config.toolbar = 'MyToolbar';//把默认工具栏改为‘MyToolbar’   		  
		config.toolbar_MyToolbar =   
			[   
			 ['Source','NewPage','Preview','Templates'],
			 ['Cut','Copy','Paste','PasteText','PasteFromWord','SpellChecker','Scayt'],   
			 ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'], 
			 ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],   
			 ['Styles','Format','Color','Font','FontSize'],   
			 ['Bold','Italic','Strike'],   
			 ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'], 
			 ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
			 ['Link','Unlink','Anchor'],
			 ['TextColor','BGColor'],
			 [ 'AlignLeft', 'AlignCenter', 'AlignRight', 'AlignJustify' ],
			 ['Maximize', 'ShowBlocks','-','About']
			 ];   
	};
	CKEDITOR.replace('editor1',{
		width : 'auto',
		height: h+'px'
	});
	CKEDITOR.on('instanceReady', function (e) {
		editor = e.editor;
		getFlowForm();
		if(form!=""&&form!=null&&form!="null"){
			editor.setData(form);
		}else{
			editor.setData("表单内容为空！");
		}
	});
	$(".js-style").unbind("click").click(function(){
		$("#formstyle").modal("show");
		$(".js-stylesave").unbind("click").click(function(){
			   $.ajax({
			        url:"/set/documentset/updateDocumentForm",
			        dataType:"json",
			        type : "post",
			        data:{
			        	formId:formId,
			        	style:$("#styletextarea").val()
			        	},
			        async:false,
			        success:function(data){
			        	if(data.status==200)
			        		{
			        		top.layer.msg(data.msg);
			        		}else if(data.status==100)
							{
								top.layer.msg(data.msg);
							}else
			        			{
			        			console.log(data.msg)
			        			}
			        }
			    });
			   $("#formstyle").modal("hide");
		});
	});
	$(".js-javascript").unbind("click").click(function(){
		$("#formjavascript").modal("show");
		$(".js-javascriptsave").unbind("click").click(function(){
			$.ajax({
		        url:"/set/documentset/updateDocumentForm",
		        dataType:"json",
		        type : "post",
		        data:{
		        	formId:formId,
		        	script:$("#jstextarea").val()
		        	},
		        async:false,
		        success:function(data){
		        	if(data.status==200)
		        		{
		        		top.layer.msg(data.msg);
		        		}else if(data.status==100)
						{
							top.layer.msg(data.msg);
						}else
		        			{
		        			console.log(data.msg)
		        			}
		        }
		    });
			$("#formjavascript").modal("hide");
		});
	});
	getFieldList();
});

//获取表单实体
function getFlowForm(){
    $.ajax({
        url:"/ret/documentget/getDocumentFormById",
        dataType:"json",
        type : "post",
        data:{formId:formId},
        async:false,
        success:function(data){
        	if(data.status==200)
        		{
        		form=data.list.htmlCode;
        		$("#formName").html(data.list.formTitle);
        		$("#jstextarea").val(data.list.script);
        		$("#styletextarea").val(data.list.style)
        		}else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
        			{
        			console.log(data.msg)
        			}
        }
    });
$(".saveform").unbind("click").click(function(){
  var content=editor.getData();
	$.ajax({
	    url:'/set/documentset/updateDocumentFormHtmlCode',
		type:"POST",
		async:false,
		data:{htmlCode:content,formId:formId},
		dataType:"json",
		success:function(data){
			if(data==500)
				{
				console.log(data.msg)
				}else{
					top.layer.msg(data.msg);
					}
	    }
	});
	});
};
function getFieldList()
{
$.ajax({
	url:"/ret/documentget/getDocumentFormFieldByFormId",
	dataType:"json",
	type:"post",
	data:{formId:formId},
	async:false,
	success:function(data){
		if(data.status==500)
		{
		console.log(data.msg)
		}else if(data.status==100){
			top.layer.msg(data.msg);
			}else
				{
				for(var i=0;i<data.list.length;i++)
					{
					fieldList[toCamel(data.list[i].name)]=data.list[i].title;
					}
				}
	}
});
}
function template()
{
window.open("/app/core/document/documentformtemplate?formId="+formId);
}

function importDocumentForm()
{
	$.ajaxFileUpload({
        url:'/set/documentset/importFormHtml?formId='+formId, //上传文件的服务端
        secureuri:false,  //是否启用安全提交
        async:false,
        dataType: 'json',   //数据类型  
        fileElementId:'file', //表示文件域ID
        success: function(data,status){
       	 	if(data.status==200)
       	 		{
       	 		top.layer.msg(data.msg);
       	 		location.reload()
       	 		}else if(data.status==100)
   				{
   					top.layer.msg(data.msg);
   				}else
       	 			{
       	 			console.log(data.msg);
       	 			}
        },
   //提交失败处理函数
        error: function (data,status,e){
       	 top.layer.msg("文件上传出错!请检查文件格式!");
       	console.log(data.msg);
        }
   });
}
function formversion()
{
	$("#formversion").modal("show")
	$(".js-versionsave").unbind("click").click(function(){
		$.ajax({
		    url:'/set/documentset/setDocumentFormVersion',
			type:"POST",
			data:{
				formId:formId,
				title:$("#title").val(),
				remark:$("#remark").val()
				},
			dataType:"json",
			success:function(data){
				if(data=="500")
					{
					console.log(data.msg)
					}else{
						top.layer.msg(data.msg);
						}
		    }
		});
	})
}