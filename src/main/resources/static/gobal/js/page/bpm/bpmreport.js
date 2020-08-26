$(function(){
	getBpmBiTemplateListByPriv();
})

function getBpmBiTemplateListByPriv()
{
	$.ajax({
		url : "/ret/bpmget/getBpmBiTemplateListByPriv",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				var recordInfo = data.list;
				if(recordInfo.length>0)
				{
					var html="";
					for(var i=0;i<recordInfo.length;i++)
					{
						html+="<div class=\"col=lg-4 col-md-4 col-sm-12 col-xs-12\">"+
                                    "<div class=\"databox databox-inverted radius-bordered databox-shadowed databox-graded databox-vertical\">"+
                                        "<div class=\"databox-top bg-azure no-padding\">"+
                                            "<div class=\"horizontal-space space-lg\"></div>"+
                                        "</div>"+
                                        "<div class=\"databox-bottom no-padding\">"+
                                            "<div class=\"databox-row\">"+
                                                "<div class=\"databox-cell cell-12 text-align-center\">"+
                                                    "<span class=\"databox-text\">"+recordInfo[i].createUserName+"</span>"+
                                                    "<span class=\"databox-number\"><a style=\"cursor: pointer;\" onclick=\"readreport('"+recordInfo[i].templateId+"');\">"+recordInfo[i].title+"</a></span>"+
                                                "</div>"+
                                            "</div>"+
                                        "</div>"+
                                    "</div>"+
                                "</div>";
					}
					$("#reportlist").html(html);
				}else
				{
					$("#reportlist").html("<div class=\"well bordered-top bordered-bottom bordered-pink\"><h4 class=\"block\">友情提示</h4><p>暂无报表</p></div>");
				}
				
				
				}
		}
	})
}

function readreport(templateId)
{
	window.location.href = "/app/core/bpm/bpmreport?view=data&templateId="+templateId;
}
