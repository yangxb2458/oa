$(function(){
	$.ajax({
		url : "/ret/vehicleget/getVehicleApplyById",
		type : "post",
		dataType : "json",
		data:{applyId:applyId},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="usedUser"||id=="createUser"||id=="optUser"||id=="tagerDriver")
					{
						$("#"+id).html(getUserNameByStr(recordInfo[id]));
					}else if(id=="type")
					{
						 if(recordInfo[id]=="1")
				    		{
							 $("#"+id).html("轿车");
				    		}else if(recordInfo[id]=="2")
				    		{
				    			$("#"+id).html("SUV");  
				    		}else if(recordInfo[id]=="3")
				    		{
				    			$("#"+id).html("MPV");
				    		}else if(recordInfo[id]=="4")
				    		{
				    			$("#"+id).html("面包车");
				    		}else if(recordInfo[id]=="5")
				    		{
				    			$("#"+id).html("皮卡");
				    		}else if(recordInfo[id]=="6")
				    		{
				    			$("#"+id).html("轻卡");
				    		}else if(recordInfo[id]=="7")
				    		{
				    			$("#"+id).html("重卡");
				    		}
					}else if(id=="driver"||id=="card")
					{
						if(recordInfo[id]=='0')
						{
							$("#"+id).html("不需要");
						}else if(recordInfo[id]=="1")
						{
							$("#"+id).html("需要");
						}
					}else if(id=="oilCard")
					{
						if(recordInfo[id]!=null && recordInfo[id]!='')
						{
							url : "/ret/vehicleget/getVehicleOilCardById",
							type : "post",
							dataType : "json",
							data:{cardId:recordInfo[id]},
							success : function(data) {
								$("#oilCard").html(data.list.cardNo);
							}
						});
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