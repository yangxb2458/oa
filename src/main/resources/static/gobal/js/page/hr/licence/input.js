$(function(){
	jeDate("#endTime", {
		format: "YYYY-MM-DD",
	});
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
$(".js-add-save").unbind("click").click(function(){
	addLicence();
})
getSmsConfig("msgType","hr");
$('#remark').summernote({ height:300 });
$(".js-auto-select").each(function(){
	var module = $(this).attr("module");
	createAutoSelect(module);
})
})

function addLicence()
{
	$.ajax({
		url : "/set/hrset/insertHrLicence",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			userId:$("#userId").attr("data-value"),
			name:$("#name").val(),
			licenceCode:$("#licenceCode").val(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val(),
			notifiedBody:$("#notifiedBody").val(),
			licenceType:$("#licenceType").val(),
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