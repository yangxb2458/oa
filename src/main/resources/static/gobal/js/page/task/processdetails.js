$(function(){
	$.ajax({
		url : "/ret/taskget/getTaskProcessInfo",
		type : "POST",
		dataType : "json",
		data : {
			processId:processId
		},
		success : function(data) {
			if(data.status=="200")
			{
				for(var name in data.list)
				{
					if(name=="attach")
					{
						$("#attach").attr("data_value", data.list.attach);
						createAttach("attach",1);
					}else if(name=="progress")
					{
						$("#" + name).html(data.list[name]*100+"%");
					} else if(name=="pProgress")
					{
						$("#" + name).html(data.list[name]*100+"%");
					}else if(name=="duration")
					{
						$("#" + name).html(data.list[name]+"/天");
					}else
					{
						$("#"+name).html(data.list[name]);
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
	});
	
})