$(function(){
	$(".js-setbtn").unbind("click").click(function(e){
		updateSysConfig();
	});
})

function updateSysConfig()
{
	$.ajax({
		url : "/set/sysset/updateSysConfig",
		type : "post",
		dataType : "json",
		data:{
			passWordLength:$("#passWordLength").val(),
			passWordStrength:$("input[name='passWordStrength']:checked").val(),
			email:$("#email").val(),
			smtp:$("#smtp").val(),
			pop3:$("#pop3").val(),
			port:$("#port").val(),
			passWord:$("#passWord").val(),
			imHost:$("#imHost").val(),
			imPort:$("#imPort").val()
			},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
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