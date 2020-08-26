$(function(){
	jeDate("#beginTime", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate()
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
$(".js-add-save").unbind("click").click(function(){
	addHrWorkSkills();
})
$('#remark').summernote({ height:300 });
$(".js-auto-select").each(function(){
	var module = $(this).attr("module");
	createAutoSelect(module);
})

})

function addHrWorkSkills()
{
	$.ajax({
		url : "/set/hrset/insertHrWorkSkills",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			name:$("#name").val(),
			userId:$("#userId").attr("data-value"),
			skillsLevel:$("#skillsLevel").val(),
			skillsCerificate:$("input:radio[name='skillsCerificate']:checked").val(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val(),
			notifieBody:$("#notifieBody").val(),
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