$(function(){
	$.ajax({
		url : "/ret/archivesget/getArchivesVolumeById",
		type : "post",
		dataType : "json",
		data:{
			volumeId:volumeId,
		},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="voucherType")
					{
						$("#"+id).html(getCodeClassName(recordInfo[id], "volume_voucher_type"));
					}else if(id=="repositoryId")
					{
						$.ajax({
							url : "/ret/archivesget/getArchivesRepositoryById",
							type : "post",
							dataType : "json",
							async : false,
							data:{
								repositoryId:recordInfo[id],
							},
							success : function(data) {
								if(data.status=="200")
								{
									$("#"+id).html(data.list.title);
								}else if(data.status=="100")
								{
									top.layer.msg(data.msg);
								}else
									{
									console.log(data.msg);
									}
							}
						})
					}else if(id=="storagePeriod")
					{
						if(recordInfo[id]=="1")
						{
							$("#"+id).html("短期");
						}else if(recordInfo[id]=="2")
						{
							$("#"+id).html("长期");
						}else if(recordInfo[id]=="3")
						{
							$("#"+id).html("永久");
						}
					}else if(id=="secretLevel")
					{
						if(recordInfo[id]=="1")
						{
							$("#"+id).html("内部");
						}else if(recordInfo[id]=="2")
						{
							$("#"+id).html("秘密");
						}else if(recordInfo[id]=="3")
						{
							$("#"+id).html("机密");
						}else if(recordInfo[id]=="4")
						{
							$("#"+id).html("绝密");
						}
					}else if(id=="deptId")
					{
						$("#"+id).html(getDeptNameByDeptIds(recordInfo[id]));
					}else if(id=="manageUser")
					{
						$("#"+id).html(getUserNameByStr(recordInfo[id]));
					}else
					{
						$("#"+id).html(recordInfo[id]);
					}
				}
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
})
})