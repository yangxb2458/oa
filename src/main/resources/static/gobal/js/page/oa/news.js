$(function(){
	 $('#content').summernote({ height:300 });
	getCodeClass("newsType","news");
	getSmsConfig("msgType","news");
	$("#createbut").unbind("click").click(function(){
		sendNews();
	});
	jeDate("#sendTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate(),
		isinitVal: true
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate()
	});
});
function sendNews()
{
	$.ajax({
		url : "/set/oaset/sendNews",
		type : "post",
		dataType : "json",
		data:{
			newsTitle:$("#newsTitle").val(),
			newsType:$("#newsType").val(),
			userPriv:$("#userPriv").attr("data-value"),
			deptPriv:$("#deptPriv").attr("data-value"),
			levelPriv:$("#levelPriv").attr("data-value"),
			content:$("#content").code(),
			sendTime:$("#sendTime").val(),
			endTime:$("#endTime").val(),
			attach:$("#newsattach").attr("data_value"),
			isTop:$("input:radio[name='isTop']:checked").val(),
			attachPriv:$("input:radio[name='attachPriv']:checked").val(),
			msgType:getCheckBoxValue("msgType")
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
				window.location.reload();
				top.layer.msg(data.msg);
				}
		}
	})
}