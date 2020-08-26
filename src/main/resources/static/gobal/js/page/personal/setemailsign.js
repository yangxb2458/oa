$(function(){
$('#emailsignContent').summernote({ height:300 });
getEmailSign();
$(".js-emailsign").unbind("click").click(function(){
	setEmailSign();
});
})

function getEmailSign()
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
				$("#emailsignContent").code(data.list.sign);
				}
		}
	})
}
function setEmailSign()
{
	$.ajax({
		url : "/set/oaset/setEmailConfig",
		type : "post",
		dataType : "json",
		data:{
			sign:$("#emailsignContent").code()
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