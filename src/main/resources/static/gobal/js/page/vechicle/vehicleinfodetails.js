$(function(){
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
				for(var id in vehicleInfo)
				{
					if(id=="attach")
					{
						$("#vechicleattach").attr("data_value", data.list.attach);
						createAttach("vechicleattach", 1);
					}else if(id=="onwer"||id=="userPriv"||id=="caruser")
					{
						$("#"+id).html(getUserNameByStr(vehicleInfo[id]));
					}else if(id=="manageDept"||id=="deptPriv")
					{
						$("#"+id).html(getDeptNameByDeptIds(vehicleInfo[id]));
					}else if(id=="levelPriv")
					{
						$("#"+id).html(getUserLevelStr(vehicleInfo[id]));
					}else if(id=="photo")
					{
						$("#file_img").attr("src","/sys/file/getStaticImg?module=vechiclephotos&fileName="+vehicleInfo[id]);
					}else if(id=="resume")
					{
						$("#resume").html(vehicleInfo[id]);
					}else if(id=="type")
					{
						if(vehicleInfo[id]=="1")
						{
							$("#type").html("轿车");
						}else if(vehicleInfo[id]=="2")
						{
							$("#type").html("SUV");
						}else if(vehicleInfo[id]=="3")
						{
							$("#type").html("MPV");
						}else if(vehicleInfo[id]=="4")
						{
							$("#type").html("面包车");
						}else if(vehicleInfo[id]=="5")
						{
							$("#type").html("皮卡");
						}else if(vehicleInfo[id]=="6")
						{
							$("#type").html("轻卡");
						}else if(vehicleInfo[id]=="7")
						{
							$("#type").html("重卡");
						}
					}else if(id=="nature")
					{
						$("#"+id).html(getCodeClassName(vehicleInfo[id],"vehicle"));
					}else
					{
						$("#"+id).html(vehicleInfo[id]);
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
	});
});