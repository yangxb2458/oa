$(function(){
	jeDate("#lastTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate()
	});
	jeDate("#birthDay", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate()
	});
$(".js-add-save").unbind("click").click(function(){
	addHrRecruitNeeds();
})
$('#remark').summernote({ height:300 });
$(".js-auto-select").each(function(){
	var module = $(this).attr("module");
	createAutoSelect(module);
})
getHrRecruitPlanForSelect();
})

function addHrRecruitNeeds()
{
	if($("#planId").val()=="")
	{
		top.layer.msg("请选择招聘计划！");
		return;
	}
	$.ajax({
		url : "/set/hrset/insertHrRecruitNeeds",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			planId:$("#planId").val(),
			deptId:$("#deptId").attr("data-value"),
			title:$("#title").val(),
			userCount:$("#userCount").val(),
			skills:$("#skills").val(),
			major:$("#major").val(),
			workJob:$("#workJob").val(),
			highsetShool:$("#highsetShool").val(),
			sex:$("#sex").val(),
			birthDay:$("#birthDay").val(),
			occupation:$("#occupation").val(),
			lastTime:$("#lastTime").val(),
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

function getHrRecruitPlanForSelect()
{
	$.ajax({
		url : "/ret/hrget/getHrRecruitPlanForSelect",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var html="<option value=''>请选择</option>";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<option value='"+data.list[i].planId+"'>"+data.list[i].title+"</option>";
				}
				$("#planId").html(html);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg)
			}else if(data.statsu=="500")
			{
				console.log(data.msg);
			}
		}
		});
}