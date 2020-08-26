$(function(){
	jeDate("#repairTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate()
	});
	$("#applyBtn").unbind("click").click(function(){
		addrepair();
	});
	getVehicleSelect();
})
function addrepair()
{
	$.ajax({
		url : "/set/vehicleset/insertVehicleRepairRecord",
		type : "post",
		dataType : "json",
		data:{
			vehicleId:$("#vehicleId").val(),
			repairType:$("#repairType").val(),
			repairTime:$("#repairTime").val(),
			repairUser:$("#repairUser").attr("data-value"),
			reason:$("#reason").val(),
			repairSupplier:$("#repairSupplier").val(),
			repairPay:$("#repairPay").val(),
			attach:$("#vechicleattach").attr("data_value"),
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

function getVehicleSelect()
{
	$.ajax({
		url : "/ret/vehicleget/getAllVehicleList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
			var html="";
			for(var i=0;i<data.list.length;i++)
			{
				html+="<option value='"+data.list[i].vehicleId+"'>"+data.list[i].vehicleNumber+"</option>"
			}
				$("#vehicleId").html(html);
		}
		}
	})
}