$(function(){
	getAllDocumentFlowList();
})
function getAllDocumentFlowList()
{
	$.ajax({
		url : "/ret/documentget/getAllDocumentFlowList",
		type : "post",
		dataType : "json",
		async : false,
		success : function(data) {
			if(data.status=="200")
			{
				for(var i=0;i<data.list.length;i++)
				{
					var flowName = data.list[i].flowName;
					getDocumentSummary(data.list[i].flowId,flowName);
				}
				
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
			}
		})
}


function getDocumentSummary(flowId,flowName)
{
	$.ajax({
		url : "/ret/documentget/getDocumentSummary",
		type : "post",
		dataType : "json",
		data:{flowId:flowId},
		success : function(data) {
			if(data.status=="200")
			{
				var html="<tr><td>"+flowName+"</td><td>"+data.list.inCount+"</td><td>"+data.list.endCount+"</td><td>"+data.list.delCount+"</td><td>"+(data.list.endCount+data.list.inCount)+"</td></tr>";
				$("#summarylist").append(html);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
			}
		})
}