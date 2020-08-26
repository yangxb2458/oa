$(function(){
	$.ajax({
		url : "/ret/archivesget/getArchivesRepositoryById",
		type : "post",
		dataType : "json",
		data:{
			repositoryId:repositoryId,
		},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="userPriv")
					{
						$("#"+id).html(getUserNameByStr(recordInfo[id]));
					}else if(id=="deptPriv")
					{
						$("#"+id).html(getDeptNameByDeptIds(recordInfo[id]));
					}else if(id=="levelPriv")
					{
						$("#"+id).html(getUserLevelStr(recordInfo[id]));
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