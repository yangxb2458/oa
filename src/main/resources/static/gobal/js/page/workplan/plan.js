$(function(){
	$('#remark').summernote({ height:300 });
	getCodeClass("planType","workplan");
	jeDate("#beginTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate(),
		isinitVal: true
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate(),
	});
	getSmsConfig("msgType","workplan");
	$(".js-add-save").unbind("click").click(function(){
		addWorkPlan();
	})
})

function addWorkPlan()
{
	$.ajax({
		url : "/set/workplanset/createWorkPlan",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			planType:$("#planType").val(),
			title:$("#title").val(),
			userPriv:$("#userPriv").attr("data-value"),
			deptPriv:$("#deptPriv").attr("data-value"),
			levelPriv:$("#levelPriv").attr("data-value"),
			joinUser:$("#joinUser").attr("data-value"),
			holdUser:$("#holdUser").attr("data-value"),
			supUser:$("#supUser").attr("data-value"),
			remark:$("#remark").code(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val(),
			attach:$("#workplanattach").attr("data_value"),
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