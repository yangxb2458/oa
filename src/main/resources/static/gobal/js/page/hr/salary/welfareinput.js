$(function(){
	jeDate("#year", {
		format: "YYYY"
	});
	$('#remark').summernote({ height:300 });
	$("#createbut").unbind("click").click(function(){
		addWelfareRecord()
	});
})
function addWelfareRecord()
{
	$.ajax({
		url : "/set/hrset/insertHrWelfareRecord",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			userId:$("#userId").attr("data-value"),
			title:$("#title").val(),
			year:$("#year").val(),
			month:$("#month").val(),
			type:$("#type").val(),
			amount:$("#amount").val(),
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