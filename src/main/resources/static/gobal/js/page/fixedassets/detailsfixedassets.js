$(function(){
	$.ajax({
		url : "/ret/fixedassetsget/getFixedAssetsById",
		type : "post",
		dataType : "json",
		data:{assetsId:assetsId},
		success : function(data) {
			var info = data.list;
			for(var id in info)
			{
				if(id=="sortId")
				{
					$("#"+id).html(getSortName(info[id]))
				}else if(id=="ownDept")
				{
					$("#"+id).html(getDeptNameByDeptIds(info[id]));
				}else if(id=="attach")
				{
					$("#attach").attr("data_value",data.list.attach);
					createAttach("attach","1");
				}else if(id=="status")
				{
					if(info[id]=="0")
					{
						$("#"+id).html("<a href=\"javascript:void(0);\" class=\"btn btn-success btn-xs\">空闲</a>");
					}else if(info[id]=="1")
					{
						$("#"+id).html("<a href=\"javascript:void(0);\" class=\"btn btn-purple shiny btn-xs\">使用中</a>");
					}else if(info[id]=="2")
					{
						$("#"+id).html("<a href=\"javascript:void(0);\" class=\"btn btn-danger btn-xs\">报废</a>");
					}else
					{
						$("#"+id).html("<a href=\"javascript:void(0);\" class=\"btn btn-warning btn-xs\">未知</a>");
					}
				}else if(id=="purchasePrice")
				{
					$("#"+id).html(info[id]+" &nbsp;&nbsp;(RMB)元");
				}else if(id=="storageId")
				{
					$("#"+id).html(getStorageName(info[id]))
				}else if(id=="depreciation")
				{
					if(info[id]=="0")
					{
						$("#"+id).html("平均折旧");
					}else if(info[id]=="1")
					{
						$("#"+id).html("加速折旧");
					}else
					{
						$("#"+id).html("未知");	
					}
				}else
				{
					$("#"+id).html(info[id]);
				}
			}
		}
	})
})

function getSortName(sortId)
{
	var returnStr="";
	$.ajax({
		url : "/ret/fixedassetsget/getFixedAssetsSortById",
		type : "post",
		dataType : "json",
		data:{sortId:sortId},
		async : false,
		success : function(data) {
			if(data.status=="200")
			{
				returnStr=data.list.sortName;
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	});
	return returnStr;
}

function getStorageName(storageId)
{
	var returnStr="";
	$.ajax({
		url : "/ret/fixedassetsget/getFixedAssetsStorageById",
		type : "post",
		dataType : "json",
		data:{storageId:storageId},
		async : false,
		success : function(data) {
			if(data.status=="200")
			{
				returnStr=data.list.storageName;
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	});
	return returnStr;
}