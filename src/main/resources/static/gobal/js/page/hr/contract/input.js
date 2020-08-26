$(function(){
	jeDate("#signTime", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate(),
	});
	jeDate("#startTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate(),
	});
$(".js-add-save").unbind("click").click(function(){
	addContract();
})
getSmsConfig("msgType","hr");
$('#remark').summernote({ height:300 });
$(".js-auto-select").each(function(){
	var module = $(this).attr("module");
	createAutoSelect(module);
})

})


function addContract()
{
	$.ajax({
		url : "/set/hrset/insertHrContract",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			signType:$("#signType").val(),
			userName:$("#userName").val(),
			poolPosition:$("#poolPosition").val(),
			contractCode:$("#contractCode").val(),
			contractType:$("#contractType").val(),
			enterpries:$("#enterpries").val(),
			userId:$("#userId").attr("data-value"),
			startTime:$("#startTime").val(),
			endTime:$("#endTime").val(),
			specialization:$("#specialization").val(),
			signTime:$("#signTime").val(),
			reminder:$("input:radio[name='reminder']:checked").val(),
			sendToUser:$("#sendToUser").attr("data-value"),
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