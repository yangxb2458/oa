$(function(){
	$.ajax({
		url : "/ret/hrget/getHrKpiItemById",
		type : "post",
		dataType : "json",
		data:{itemId:itemId},
		success : function(data) {
			if(data.status=="200")
			{
				var itemInfo = data.list;
				for(var id in itemInfo)
				{
					if(id=="childItem")
					{
						var childItemArr =JSON.parse(itemInfo[id]);
						createChildItem(childItemArr);
					}else if(id=="optType")
					{
						if(itemInfo[id]=="1")
						{
							$("#"+id).html("单选");
						}else if(itemInfo[id]=="2")
						{
							$("#"+id).html("多选");
						}else if(itemInfo[id]=="3")
						{
							$("#"+id).html("简述");
						}
					}else if(id=="kpiType")
					{
						$("#"+id).html(getCodeClassName(itemInfo[id],"kpiType"));
					}else
					{
						$("#"+id).html(itemInfo[id]);
					}
				}
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
		})
})

function createChildItem(childItemArr)
{
	$("#child-item-table").find("tr").next().remove();
	for(var i=0;i<childItemArr.length;i++)
	{
		$("#child-item-table").append("<tr><td>"+(i+1)+"</td>" +
				"<td>"+childItemArr[i].childTitle+"</td>" +
		"<td>"+childItemArr[i].childScore+"</td></tr>");

	}
}