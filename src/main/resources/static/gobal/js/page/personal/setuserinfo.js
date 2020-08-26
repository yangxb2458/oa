$(function() {
	jeDate("#birthday", {
		format : "YYYY-MM-DD",
		maxDate:getSysTime()
	});
	getUserinfo();
	$(".js-setuserinfo").unbind("click").click(function(){
		setUserInfo();
	})
})

function setUserInfo()
{
	$.ajax({
		url : "/set/unitset/updateMyUserInfo",
		type : "post",
		dataType : "json",
		data:{
			nick:$("#nick").val(),
			sign:$("#sign").val(),
			mobileNo:$("#mobileNo").val(),
			wxNo:$("#wxNo").val(),
			eMail:$("#eMail").val(),
			birthday:$("#birthday").val(),
			thmem:$("#thmem").val()
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

function getUserinfo()
{
	$.ajax({
		url : "/ret/unitget/getMyUserInfo",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				$("#userName").html(data.list.userName);
				$("#nick").val(data.list.nick);
				$("#sign").val(data.list.sign);
				$("#mobileNo").val(data.list.mobileNo);
				$("#wxNo").val(data.list.wxNo);
				$("#eMail").val(data.list.eMail);
				$("#birthday").val(data.list.birthday);
				$("#thmem").val(data.list.thmem);
				
				}
		}
	})
}