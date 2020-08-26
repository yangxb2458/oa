$(function(){
	$.ajax({
		url : "/ret/hrget/getHrWelfareRecordById",
		type : "post",
		dataType : "json",
		data:{recordId:recordId},
		success : function(data) {
			if(data.status=="200")
			{
				var recordinfo = data.list;
				for(var id in recordinfo)
				{
					if(id=="userId")
					{
						$("#"+id).html(getHrUserNameByStr(recordinfo[id]));
					}else if(id=="type")
					{
						if(recordinfo[id]=="1")
						{
							$("#"+id).html("现金");
						}else if(recordinfo[id]=="2")
						{
							$("#"+id).html("物质");
						}else if(recordinfo[id]=="0")
						{
							$("#"+id).html("其它");
						}
					}else if(id=="month")
					{
						if(recordinfo[id]=="1")
						{
							$("#"+id).html("一月");
						}else if(recordinfo[id]=="2")
						{
							$("#"+id).html("二月");
						}else if(recordinfo[id]=="3")
						{
							$("#"+id).html("三月");
						}else if(recordinfo[id]=="4")
						{
							$("#"+id).html("四月");
						}else if(recordinfo[id]=="5")
						{
							$("#"+id).html("五月");
						}else if(recordinfo[id]=="6")
						{
							$("#"+id).html("六月");
						}else if(recordinfo[id]=="7")
						{
							$("#"+id).html("七月");
						}else if(recordinfo[id]=="8")
						{
							$("#"+id).html("八月");
						}else if(recordinfo[id]=="9")
						{
							r$("#"+id).html("九月");
						}else if(recordinfo[id]=="10")
						{
							$("#"+id).html("十月");
						}else if(recordinfo[id]=="11")
						{
							$("#"+id).html("十一月");
						}else if(recordinfo[id]=="12")
						{
							$("#"+id).html("十二月");
						}
					}else
					{
						$("#"+id).html(recordinfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateHrWelfareRecord(recordId);
				})
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