$(function(){
	jeDate("#cardTime", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate()
	});
	$("#addBtn").unbind("click").click(function(){
		addVehicleOilCard();
	});
})

function addVehicleOilCard()
{
	$.ajax({
		url : "/set/vehicleset/insertVehicleOilCard",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			oilType:$("#oilType").val(),
			oilComp:$("#oilComp").val(),
			cardCode:$("#cardCode").val(),
			cardNo:$("#cardNo").val(),
			passWord:$("#passWord").val(),
			status:$("#status").val(),
			balance:$("#balance").val(),
			cardTime:$("#cardTime").val(),
			cardUser:$("#cardUser").attr("data-value"),
			remark:$("#remark").val()
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