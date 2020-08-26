$(function(){
	jeDate("#lastSalaryTime", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate()
	});
	jeDate("#applyTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#planTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#factTime", {
		format: "YYYY-MM-DD"
	});
$(".js-add-save").unbind("click").click(function(){
	addHrLeaveRecord();
})
$('#remark').summernote({ height:300 });
$(".js-auto-select").each(function(){
	var module = $(this).attr("module");
	createAutoSelect(module);
})

})

function addHrLeaveRecord()
{
	$.ajax({
		url : "/set/hrset/insertHrLeaveRecord",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			deptId:$("#deptId").attr("data-value"),
			userId:$("#userId").attr("data-value"),
			post:$("#post").attr("data-value"),
			leaveType:$("#leaveType").val(),
			lastSalaryTime:$("#lastSalaryTime").val(),
			applyTime:$("#applyTime").val(),
			planTime:$("#planTime").val(),
			factTime:$("#factTime").val(),
			factTime:$("#factTime").val(),
			salary:$("#salary").val(),
			trace:$("#trace").val(),
			leaveCondition:$("#leaveCondition").val(),
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