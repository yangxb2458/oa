$(function(){
	jeDate("#year", {
		format: "YYYY"
	});
	getSmsConfig("msgType","hr");
	$("#createbut").unbind("click").click(function(){
		addSalaryRecord()
	});
})

function addSalaryRecord()
{
	$.ajax({
		url : "/set/hrset/insertHrSalaryRecord",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			userId:$("#userId").attr("data-value"),
			year:$("#year").val(),
			month:$("#month").val(),
			postSalary:$("#postSalary").val(),
			levelSalary:$("#levelSalary").val(),
			foodSalary:$("#foodSalary").val(),
			otherPassSalary:$("#otherPassSalary").val(),
			transportSalary:$("#transportSalary").val(),
			postAllowance:$("#postAllowance").val(),
			sumAmount:$("#sumAmount").val(),
			pensoin:$("#pensoin").val(),
			unemployment:$("#unemployment").val(),
			medical:$("#medical").val(),
			accumulationFund:$("#accumulationFund").val(),
			tax:$("#tax").val(),
			costOther:$("#costOther").val(),
			realCost:$("#realCost").val(),
			realSalary:$("#realSalary").val(),
			msgType:getCheckBoxValue("msgType")
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