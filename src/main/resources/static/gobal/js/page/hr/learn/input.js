$(function(){
	jeDate("#beginTime", {
		format: "YYYY-MM",
		maxDate:getSysDate()
	});
	jeDate("#endTime", {
		format: "YYYY-MM"
	});
$(".js-add-save").unbind("click").click(function(){
	addLearnRecord();
})
$('#remark').summernote({ height:300 });
$(".js-auto-select").each(function(){
	var module = $(this).attr("module");
	createAutoSelect(module);
})

})

function addLearnRecord()
{
	$.ajax({
		url : "/set/hrset/insertHrLearnRecord",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			shoolName:$("#shoolName").val(),
			userId:$("#userId").attr("data-value"),
			major:$("#major").val(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val(),
			highsetDegree:$("#highsetDegree").val(),
			cerifier:$("#cerifier").val(),
			cerificate:$("#cerificate").val(),
			honor:$("#honor").val(),
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