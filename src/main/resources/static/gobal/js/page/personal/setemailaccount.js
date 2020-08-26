$(function(){
$(".js-emailconfig").unbind("click").click(function(){
	setEmailConfig();
});
getMyEmailConfig();
})
function setEmailConfig()
{
	$.ajax({
		url : "/set/oaset/setEmailConfig",
		type : "post",
		dataType : "json",
		data:{
			email:$("#email").val(),
			passWord:$("#emailpassword").val(),
			pop3:$("#pop3").val(),
			smtp:$("#smtp").val(),
			port:$("#port").val(),
			type:$('input:radio[name=type]:checked').val()
		},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				top.layer.msg(data.msg);
				}
		}
	})
}

function getMyEmailConfig()
{
	$.ajax({
		url : "/ret/oaget/getMyEmailConfig",
		type : "post",
		dataType : "json",
		data:{},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				$("#email").val(data.list.email);
				$("#emailpassword").val(data.list.passWord);
				$("#pop3").val(data.list.pop3);
				$("#smtp").val(data.list.smtp);
				$("#port").val(data.list.port);
				$("input:radio[name=type][value="+data.list.type+"]").attr("checked",true);  
				}
		}
	})
}