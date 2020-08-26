$(function(){
	$.ajax({
		url : "/ret/superversionget/getSuperversionProcessById",
		type : "post",
		dataType : "json",
		data:{processId:processId},
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
					}else
					{
						$("#"+name).html(data.list[name]);
					}
				}
			}
		}
	})
});