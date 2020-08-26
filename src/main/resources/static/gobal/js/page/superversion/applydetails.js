$(function(){
	$.ajax({
		url : "/ret/superversionget/getSuperversionDelayById",
		type : "post",
		dataType : "json",
		data:{delayId:delayId},
		success : function(data) {
			console.log(data);
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				for(var name in data.list)
				{
				if(name=="attach")
					{
					$("#attach").attr("data_value",data.list.attach);
					createAttach("attach","1");
					}else if(name=="createUser")
					{
						$("#"+name).html(getUserNameByStr(data.list[name]));
					}else if(name=="passStatus")
					{
						if(data.list[name]=="0")
						{
							$("#"+name).html("<a href=\"javascript:void(0);\" class=\"btn btn-palegreen btn-xs\">审批中</a>");
						}else if(data.list[name]=="1")
						{
							$("#"+name).html("<a href=\"javascript:void(0);\" class=\"btn btn-success btn-xs\">通过</a>");
						}else if(data.list[name]=="2")
						{
							$("#"+name).html("<a href=\"javascript:void(0);\" class=\"btn btn-danger btn-xs\">未通过</a>");
						}
					}else
					{
						$("#"+name).html(data.list[name]);
					}
				}
			}
		}
	})
});