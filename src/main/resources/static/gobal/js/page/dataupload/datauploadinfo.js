$(function(){
	 $('#remark').summernote({ height:300 });
	getCodeClass("dataType","dataType");
	getSmsConfig("msgType","datainfo");
	$("#createbut").unbind("click").click(function(){
		sendNews();
	});
	jeDate("#sendTime", {
		format: "YYYY-MM-DD hh:mm:ss",
		isinitVal: true
	});
	$(".js-add-save").unbind("click").click(function(){
		uploadinfo();
	})
});

function uploadinfo()
{
	$.ajax({
		url : "/set/datauploadset/insertDataUploadInfo",
		type : "post",
		dataType : "json",
		data:{
			title:$("#title").val(),
			dataType:$("#dataType").val(),
			deptId:$("#deptId").attr("data-value"),
			fromAccountId:$("#fromAccountId").attr("data-value"),
			approvedType:$("#approvedType").val(),
			approvedUser:$("#approvedUser").attr("data-value"),
			sendTime:$("#sendTime").val(),
			toUser:$("#toUser").attr("data-value"),
			attach:$("#hrattach").attr("data_value"),
			msgType:getCheckBoxValue("msgType"),
			remark:$("#remark").code()
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				location.reload();
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
	})
}