$(function(){
	jeDate("#applyTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#receiveTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#nextApplyTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#employBeginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#employEndTime", {
		format: "YYYY-MM-DD"
	});
$(".js-add-save").unbind("click").click(function(){
	addHrTitleEvaluation();
})
$('#remark').summernote({ height:300 });
$(".js-auto-select").each(function(){
	var module = $(this).attr("module");
	createAutoSelect(module);
})

})

function addHrTitleEvaluation()
{
	$.ajax({
		url : "/set/hrset/insertHrTitleEvaluation",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			userId:$("#userId").attr("data-value"),
			deptId:$("#deptId").attr("data-value"),
			post:$("#post").attr("data-value"),
			postName:$("#postName").val(),
			getType:$("#getType").val(),
			applyTime:$("#applyTime").val(),
			receiveTime:$("#receiveTime").val(),
			nextApplyTime:$("#nextApplyTime").val(),
			nextPostName:$("#nextPostName").val(),
			employComp:$("#employComp").val(),
			employPost:$("#employPost").val(),
			employBeginTime:$("#employBeginTime").val(),
			employEndTime:$("#employEndTime").val(),
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