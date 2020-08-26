$(function(){
	$.ajax({
		url : "/ret/vehicleget/getVehicleRepairRecordById",
		type : "post",
		dataType : "json",
		data:{recordId:recordId},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="attach")
					{
						$("#vechicleattach").attr("data_value", data.list.attach);
						createAttach("vechicleattach", 1);
					}else
					if(id=="repairUser")
					{
						$("#"+id).html(getUserNameByStr(recordInfo[id]));
					}else if(id=="repairType")
					{
						 if(recordInfo[id]=="1")
				    		{
							 $("#"+id).html("洗车");
				    		}else if(recordInfo[id]=="2")
				    		{
				    			$("#"+id).html("保养");  
				    		}else if(recordInfo[id]=="3")
				    		{
				    			$("#"+id).html("维修");
				    		}else if(recordInfo[id]=="4")
				    		{
				    			$("#"+id).html("年检");
				    		}else if(recordInfo[id]=="5")
				    		{
				    			$("#"+id).html("其它");
				    		}
					}else if(id=="vehicleId")
					{
						getVehicleInof(recordInfo[id])
					}else
					{
						$("#"+id).html(recordInfo[id]);
					}
				}
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
		})
})

function getVehicleInof(vehicleId)
{
	$.ajax({
		url : "/ret/vehicleget/getVehicleInfoById",
		type : "post",
		dataType : "json",
		data:{
			vehicleId:vehicleId
		},
		success : function(data) {
			if(data.status=="200")
			{
				var vehicleInfo = data.list;
				$("#vehicleId").html(data.list.vehicleNumber)
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
	});
}