$(function(){
	jeDate("#transferTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#startTime", {
		format: "YYYY-MM-DD"
	});
$(".js-add-save").unbind("click").click(function(){
	addHrPersonnelTransfer();
})
$('#remark').summernote({ height:300 });
$(".js-auto-select").each(function(){
	var module = $(this).attr("module");
	createAutoSelect(module);
})

})

function addHrPersonnelTransfer()
{
	$.ajax({
		url : "/set/hrset/insertHrPersonnelTransfer",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			title:$("#title").val(),
			userId:$("#userId").attr("data-value"),
			transferType:$("#transferType").val(),
			transferTime:$("#transferTime").val(),
			startTime:$("#startTime").val(),
			compName:$("#compName").val(),
			transferComp:$("#transferComp").val(),
			deptName:$("#deptName").val(),
			transferDept:$("#transferDept").val(),
			levelName:$("#levelName").val(),
			transferLevel:$("#transferLevel").val(),
			transferCondition:$("#transferCondition").val(),
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