$(function(){
	jeDate("#beginTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate()
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate()
	});
$(".js-add-save").unbind("click").click(function(){
	addHrTrainRecord();
})
$(".js-applay-sav").unbind("click").click(function(){
	applayHrTrainRecord();
})
$('#remark').summernote({ height:300 });
$(".js-auto-select").each(function(){
	var module = $(this).attr("module");
	createAutoSelect(module);
})

})

function addHrTrainRecord()
{
	$.ajax({
		url : "/set/hrset/insertHrTrainRecord",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			trainCode:$("#trainCode").val(),
			title:$("#title").val(),
			userCount:$("#userCount").val(),
			channel:$("#channel").val(),
			courseType:$("#courseType").val(),
			holdDept:$("#holdDept").attr("data-value"),
			chargePerson:$("#chargePerson").attr("data-value"),
			institutionName:$("#institutionName").val(),
			address:$("#address").val(),
			institutionUser:$("#institutionUser").val(),
			institutionContact:$("#institutionContact").val(),
			courseName:$("#courseName").val(),
			courseTime:$("#courseTime").val(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val(),
			founds:$("#founds").val(),
			approvedUser:$("#approvedUser").attr("data-value"),
			joinUser:$("#joinUser").attr("data-value"),
			joinDept:$("#joinDept").attr("data-value"),
			joinUserLevel:$("#joinUserLevel").attr("data-value"),
			description:$("#description").val(),
			trainRequires:$("#trainRequires").val(),
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

function applayHrTrainRecord()
{
	$.ajax({
		url : "/set/hrset/insertHrTrainRecord",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			trainCode:$("#trainCode").val(),
			title:$("#title").val(),
			userCount:$("#userCount").val(),
			channel:$("#channel").val(),
			courseType:$("#courseType").val(),
			holdDept:$("#holdDept").attr("data-value"),
			chargePerson:$("#chargePerson").attr("data-value"),
			institutionName:$("institutionName").val(),
			address:$("#address").val(),
			institutionUser:$("#institutionUser").val(),
			institutionContact:$("#institutionContact").val(),
			courseName:$("#courseName").val(),
			courseTime:$("#courseTime").val(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val(),
			founds:$("#founds").val(),
			approvedUser:$("#approvedUser").attr("data-value"),
			joinUser:$("#joinUser").attr("data-value"),
			joinDept:$("#joinDept").attr("data-value"),
			joinUserLevel:$("#joinUserLevel").attr("data-value"),
			description:$("#description").val(),
			trainRequires:$("#trainRequires").val(),
			attach:$("#hrattach").attr("data_value"),
			remark:$("#remark").code(),
			status:'0'
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