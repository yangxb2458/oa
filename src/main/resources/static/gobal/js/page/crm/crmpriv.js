$(function(){
	$.ajax({
		url : "/ret/crmget/getCrmPriv",
		type : "post",
		dataType : "json",
		data:{},
		success : function(data) {
			if(data.status==200)
			{
				if(data.list)
					{
						for(var name in data.list)
							{
							 if(name=="manager")
								 {
								 $("#manager").attr("data-value",data.list[name]);
								 $("#manager").val(getUserNameByStr(data.list[name]));
								 }else if(name=="sender")
									{
									 $("#sender").attr("data-value",data.list[name]);
									 $("#sender").val(getUserNameByStr(data.list[name]));
									}else if(name=="sale")
									{
										$("#sale").attr("data-value",data.list[name]);
										$("#sale").val(getUserNameByStr(data.list[name]));
									}
							}
					}
			}else if(data.status==100)
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
	});
	$(".js-setbtn").unbind("click").click(function(){
		$.ajax({
			url : "/set/crmset/setCrmPriv",
			type : "post",
			dataType : "json",
			data:{
				manager:$("#manager").attr("data-value"),
				sender:$("#sender").attr("data-value"),
				sale:$("#sale").attr("data-value")
			},
			success : function(data) {
				if(data.status==200)
				{
					top.layer.msg(data.msg);
				}else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
					{
					console.log(data.msg);
					}
			}
		});
	});
});