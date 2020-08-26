var ueheight = $(window).height() - 120;
var platformPageDesign;
var form=null;
var formEditor = UE.getEditor('editor1', {
	toolleipi : true, //是否显示，设计器的 toolbars
	textarea : 'design_content',
	//这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
	toolbars : [ [ 'fullscreen', 'source', '|', 'undo', 'redo', '|', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist',
			'insertunorderedlist', 'selectall', 'cleardoc', '|', 'rowspacingtop', 'rowspacingbottom', 'lineheight', '|', 'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|', 'directionalityltr', 'directionalityrtl', 'indent', '|', 'justifyleft', 'justifycenter', 'justifyright',
			'justifyjustify', '|', 'touppercase', 'tolowercase', '|', 'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|', 'simpleupload', 'insertimage', 'emotion', 'scrawl', 'insertvideo', 'music', 'attachment', 'map', 'gmap', 'insertframe', 'insertcode',
			'webapp', 'pagebreak', 'template', 'background', '|', 'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|', 'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown',
			'splittocells', 'splittorows', 'splittocols', 'charts', '|', 'print', 'preview', 'searchreplace', 'help', 'drafts', '|' ] ],
	//focus时自动清空初始化时的内容
	//autoClearinitialContent:true,
	//关闭字数统计
	wordCount : false,
	//关闭elementPath
	elementPathEnabled : false,
	//默认的编辑区域高度
	initialFrameHeight : ueheight,
	//编辑器宽度
	autoClearEmptyNode : true,
	//initialFrameWidth: auto,
iframeCssUrl : "/assets/css/beyond.min.css"
//更多其他参数，请参考ueditor.config.js中的配置项
});
$(function() {
	$(".js-plugins").unbind("click").click(function(){
		platformPageDesign.exec($(this).attr('xtype'));
	})
	platformPageDesign = {
            /*执行控件*/
            exec: function(method) {
                formEditor.execCommand(method);
            }
	};
	getPlatformPage();
	$(".js-style").unbind("click").click(function(){
		$("#formstyle").modal("show");
		$(".js-stylesave").unbind("click").click(function(){
			   $.ajax({
			        url:"/set/platformset/updatePlatformPage",
			        dataType:"json",
			        type : "post",
			        data:{
			        	pageId:pageId,
			        	pageStyle:$("#styletextarea").val()
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
		        url:"/set/platformset/updatePlatformPage",
		        dataType:"json",
		        type : "post",
		        data:{
		        	pageId:pageId,
		        	pageScript:$("#jstextarea").val()
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
	$(".saveform").unbind("click").click(function(){
		  var content=formEditor.getContent();
			$.ajax({
			    url:'/set/platformset/updatePlatformPageHtmlCode',
				type:"POST",
				async:false,
				data:{pageHtml:content,pageId:pageId},
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
})

function testTableFieldsName(fieldsName)
{
	var reg2 = /(^_([a-zA-Z0-9]_?)*$)|(^[a-zA-Z](_?[a-zA-Z0-9])*_?$)/;
	if (!reg2.test(fieldsName)){
		top.layer.msg("您输入的字段名称为：“"+fieldsName+"”格式不正确；提示：【首位可以是字母以及下划线。首位之后可以是字母，数字以及下划线。下划线后不能接下划线】");
		return false;
	}else
	{
		return true;
	}
}

//获取表单实体
function getPlatformPage(){
    $.ajax({
        url:"/ret/platformget/getPlatformPageById",
        dataType:"json",
        type : "post",
        data:{pageId:pageId},
        async:false,
        success:function(data){
        	if(data.status==200)
        		{
        		form=data.list.pageHtml;
        		$("#formName").html(data.list.pageName);
        		$("#jstextarea").val(data.list.pageScript);
        		$("#styletextarea").val(data.list.pageStyle);
        		formEditor.ready(function() {
        			formEditor.setContent(form);
        		});
        		}else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
        			{
        			console.log(data.msg)
        			}
        }
    });
}