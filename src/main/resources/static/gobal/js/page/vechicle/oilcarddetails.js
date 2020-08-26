$(function(){
	$.ajax({
		url : "/ret/vehicleget/getVehicleOilCardById",
		type : "post",
		dataType : "json",
		data:{cardId:cardId},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="cardUser")
					{
						$("#"+id).html(getUserNameByStr(recordInfo[id]));
					}else if(id=="oilType")
					{
						if (recordInfo[id]=="1") {
							$("#"+id).html("汽油");
						} else if(recordInfo[id]=="2"){
							$("#"+id).html("柴油");
						}
					}else if(id=="status")
					{
						if (recordInfo[id]=="0") {
							$("#"+id).html("空闲");
						} else if(recordInfo[id]=="1"){
							$("#"+id).html("使用中");
						} else if(recordInfo[id]=="2"){
							$("#"+id).html("挂失");
						} else if(recordInfo[id]=="3"){
							$("#"+id).html("失效");
						}
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