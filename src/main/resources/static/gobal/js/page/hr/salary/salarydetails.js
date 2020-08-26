$(function(){
	$.ajax({
		url : "/ret/hrget/getHrSalaryRecordById",
		type : "post",
		dataType : "json",
		data:{recordId:recordId},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="userId")
					{
						$("#"+id).html(getHrUserNameByStr(recordInfo[id]));
					}else if(id=="month")
					{
						if(recordInfo[id]=="1")
						{
							$("#"+id).html("一月");
						}else if(recordInfo[id]=="2")
						{
							$("#"+id).html("二月");
						}else if(recordInfo[id]=="3")
						{
							$("#"+id).html("三月");
						}else if(recordInfo[id]=="4")
						{
							$("#"+id).html("四月");
						}else if(recordInfo[id]=="5")
						{
							$("#"+id).html("五月");
						}else if(recordInfo[id]=="6")
						{
							$("#"+id).html("六月");
						}else if(recordInfo[id]=="7")
						{
							$("#"+id).html("七月");
						}else if(recordInfo[id]=="8")
						{
							$("#"+id).html("八月");
						}else if(recordInfo[id]=="9")
						{
							$("#"+id).html("九月");
						}else if(recordInfo[id]=="10")
						{
							$("#"+id).html("十月");
						}else if(recordInfo[id]=="11")
						{
							$("#"+id).html("十一月");
						}else if(recordInfo[id]=="12")
						{
							$("#"+id).html("十二月");
						}
					}else
					{
						$("#"+id).html(recordInfo[id]);
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