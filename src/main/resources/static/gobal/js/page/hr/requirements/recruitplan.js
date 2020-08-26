$(function(){
	jeDate("#beginTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate()
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate()
	});
	jeDate("#planEndTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate()
	});
	$('#remark').summernote({ height:300 });
	
	$(".js-add-save").unbind("click").click(function(){
		addRecruitPlan();
	})
})

function addRecruitPlan()
{
	$.ajax({
		url : "/set/hrset/insertHrRecruitPlan",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			planEndTime:$("#planEndTime").val(),
			title:$("#title").val(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val(),
			approvedUser:$("#approvedUser").attr("data-value"),
			attach:$("#hrattach").attr("data_value"),
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