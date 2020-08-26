$(function(){
	$.ajax({
		url : "/ret/archivesget/getApprovalVolumeFile",
		type : "post",
		dataType : "json",
		data:{
			fileId:fileId,
			volumeId:volumeId
		},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="fileType")
					{
						$("#"+id).html(getCodeClassName(recordInfo[id], "volume_file_type"));
					}else if(id=="volumeId")
					{
						$.ajax({
							url : "/ret/archivesget/getArchivesVolumeById",
							type : "post",
							dataType : "json",
							async : false,
							data:{
								volumeId:recordInfo[id],
							},
							success : function(data) {
								if(data.status=="200")
								{
									$("#"+id).html(data.list.volumeTitle);
								}else if(data.status=="100")
								{
									top.layer.msg(data.msg);
								}else
									{
									console.log(data.msg);
									}
							}
						})
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
					}else if(id=="isaudit")
					{
						if(recordInfo[id]=="0")
						{
							$("#"+id).html("需要审批");
						}else if(recordInfo[id]=="1")
						{
							$("#"+id).html("无需审批");
						}
					}else if(id=="attach")
					{
						$("#attach").attr("data_value", recordInfo.attach);
						createAttach("attach", 1);
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