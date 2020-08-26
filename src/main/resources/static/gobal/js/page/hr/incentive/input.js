$(function(){
	jeDate("#salaryMonth", {
		format: "YYYY-MM",
		minDate:getSysDate(),
	});
	jeDate("#incentiveTime", {
		format: "YYYY-MM-DD"
	});
$(".js-add-save").unbind("click").click(function(){
	addIncentive();
})
getSmsConfig("msgType","hr");
$('#remark').summernote({ height:300 });
$(".js-auto-select").each(function(){
	var module = $(this).attr("module");
	createAutoSelect(module);
})

})

function addIncentive()
{
	$.ajax({
		url : "/set/hrset/insertHrIncentive",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			incentiveType:$("#incentiveType").val(),
			userId:$("#userId").attr("data-value"),
			incentiveItem:$("#incentiveItem").val(),
			incentiveTime:$("#incentiveTime").val(),
			incentiveAmount:$("#incentiveAmount").val(),
			salaryMonth:$("#salaryMonth").val(),
			msgType:getCheckBoxValue("msgType"),
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