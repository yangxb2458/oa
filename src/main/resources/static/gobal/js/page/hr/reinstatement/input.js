$(function(){
	jeDate("#applyTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#salaryTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#planTime", {
		format: "YYYY-MM-DD"
	});
$(".js-add-save").unbind("click").click(function(){
	addHrReinstatement();
})
$('#remark').summernote({ height:300 });
$(".js-auto-select").each(function(){
	var module = $(this).attr("module");
	createAutoSelect(module);
})

})

function addHrReinstatement()
{
	$.ajax({
		url : "/set/hrset/insertHrReinstatement",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			userId:$("#userId").attr("data-value"),
			levelId:$("#levelId").attr("data-value"),
			deptId:$("#deptId").attr("data-value"),
			reinstatementType:$("#reinstatementType").val(),
			salaryTime:$("#salaryTime").val(),
			applyTime:$("#applyTime").val(),
			planTime:$("#planTime").val(),
			reinstatementCondition:$("#reinstatementCondition").val(),
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