$(function(){
	jeDate("#beginTime", {
		format: "YYYY-MM",
		maxDate:getSysDate()
	});
	jeDate("#endTime", {
		format: "YYYY-MM"
	});
$(".js-add-save").unbind("click").click(function(){
	addHrWorkRecord();
})
$('#remark').summernote({ height:300 });
$(".js-auto-select").each(function(){
	var module = $(this).attr("module");
	createAutoSelect(module);
})

})

function addHrWorkRecord()
{
	$.ajax({
		url : "/set/hrset/insertHrWorkRecord",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			compName:$("#compName").val(),
			userId:$("#userId").attr("data-value"),
			post:$("#post").val(),
			deptName:$("#deptName").val(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val(),
			industry:$("#industry").val(),
			cerifier:$("#cerifier").val(),
			nature:$("#nature").val(),
			jobContent:$("#jobContent").val(),
			achievement:$("#achievement").val(),
			reasonForLeave:$("#reasonForLeave").val(),
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