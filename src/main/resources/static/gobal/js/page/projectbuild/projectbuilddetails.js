$(function(){
	$.ajax({
		url : "/ret/projectbuildget/getProjectBuildDetailsById",
		type : "post",
		dataType : "json",
		data:{
			projectId:projectId
		},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else{
				for(name in data.list)
					{
						if(name=="projectType")
						{
							if(data.list[name]=="1")
							{
								$("#projectType").html("总包项目");
							}else if(data.list[name]=="2")
							{
								$("#projectType").html("分包项目");
							}
						}else if(name=="manager")
						{
							$("#manager").html(getUserNameByStr(data.list.manager));
						}else if(name=="sortId")
						{
							$("#sortId").html(getProjectBuildSortById(data.list.sortId));
						}else if(name=="attach")
						{
							$("#projectbuildattach").attr("data_value",data.list.attach);
							createAttach("projectbuildattach","1");
						}else
						{
							$("#"+name).html(data.list[name]);
						}
					}
			}
		}
	});
})
function getProjectBuildSortById(sortId)
{
	var returnStr="";
	$.ajax({
		url : "/ret/projectbuildget/getProjectBuildSortById",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			sortId:sortId
		},
		success : function(data) {
			if (data.status == 200) {
				returnStr=data.list.sortName;
			} else {
				console.log(data.msg);
			}
		}
	});
	return returnStr;
}