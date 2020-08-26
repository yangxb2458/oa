$(function(){
	jeDate("#beginTime", {
		format: "YYYY-MM-DD hh:mm",
		minDate:getSysDate(),
		isinitVal: true
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD hh:mm",
		minDate:getSysDate(),
		isinitVal: true
	});
	$('#remark').summernote({ height:200 });
	$(".js-add-save").unbind("click").click(function(){
		addLeadActivity();
	});
})
function addLeadActivity()
{
	$.ajax({
		url : "/set/oaset/insertLeadActivity",
		type : "post",
		dataType : "json",
		data:{
			title:$("#title").val(),
			leader:$("#leader").attr("data-value"),
			userPriv:$("#userPriv").attr("data-value"),
			deptPriv:$("#deptPriv").attr("data-value"),
			levelPriv:$("#levelPriv").attr("data-value"),
			remark:$("#remark").code(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val()
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