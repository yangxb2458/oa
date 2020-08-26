$(function(){
	getkeywordslist();
		$('#searchInput').unbind('keypress').bind('keypress',function(event){
	        if(event.keyCode == "13")    
	        {
	        	var keywords = $('#searchInput').val();
	        	if(keywords!="")
	        	{
	        		window.open("/app/core/file/indexsearch?keywords="+keywords);
	        	}else
	        	{
	        		top.layer.msg("请先输入全文检索关键词！");
	        	}
	        }
	    });
		$(".glyphicon-search").unbind("click").click(function(){
			var keywords = $('#searchInput').val();
	        	if(keywords!="")
	        	{
	        		window.open("/app/core/file/indexsearch?keywords="+keywords);
	        	}else
	        	{
	        		top.layer.msg("请先输入全文检索关键词！");
	        	}
		})
		getAllKnowledgeSortMap();
})


function getkeywordslist()
{
	$.ajax({
		url : "/ret/knowledgeget/getHostKeyWords",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var record=data.list;
				for(var i=0;i<record.length;i++)
				{
					$("#hostword").append("<a style='margin-left: 10px;' href=\"javascript:void(0);window.open('/app/core/file/indexsearch?keywords="+record[i].keyWord+"')\" class='btn btn-darkorange btn-sm'>"+record[i].keyWord+"</a>");
				}
				
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

function getAllKnowledgeSortMap()
{
	$.ajax({
		url : "/ret/knowledgeget/getAllKnowledgeSortMap",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var record=data.list;
				for(var i=0;i<record.length;i++)
				{
					var html="<div class=\"col-md-6 col-sm-6 col-xs-12\">"+
                                    "<div class=\"widget\">"+
                                        "<div class=\"widget-header bordered-left bordered-blueberry\">"+
                                            "<span class=\"widget-caption\" data-value=\""+record[i].parentSortId+"\">"+record[i].parentSortName+"</span>"+
                                        "</div>"+
                                        "<div class=\"widget-body bordered-left bordered-blue\">";
					for(var j=0;j<record[i].childSort.length;j++)
					{
						html+= "<a class=\"btn btn-link js-gosort\" data-value=\""+record[i].childSort[j].sortId+"\">"+record[i].childSort[j].sortName+"</a>";
					}
                         html+="</div>"+
                                    "</div>"+
                                "</div>";
                         
                    $("#knowledgeSortMap").append(html);
				}
				$(".js-gosort").unbind("click").click(function(){
					 window.open("/app/core/file/knowledgestudylist?sortId="+$(this).attr("data-value"));
				})
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