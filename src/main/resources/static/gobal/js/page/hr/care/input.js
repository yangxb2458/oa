$(function(){
	jeDate("#careTime", {
		format: "YYYY-MM-DD"
	});
$(".js-add-save").unbind("click").click(function(){
	addHrCareRecord();
})
$('#remark').summernote({ height:300 });
$(".js-auto-select").each(function(){
	var module = $(this).attr("module");
	createAutoSelect(module);
})

})

function addHrCareRecord()
{
	$.ajax({
		url : "/set/hrset/insertHrCareRecord",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			subject:$("#subject").val(),
			userId:$("#userId").attr("data-value"),
			joinUser:$("#joinUser").attr("data-value"),
			careType:$("#careType").val(),
			careTime:$("#careTime").val(),
			careFunds:$("#careFunds").val(),
			careResult:$("#careResult").val(),
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