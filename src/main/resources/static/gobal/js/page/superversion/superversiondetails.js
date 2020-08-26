$(function(){
	$.ajax({
		url : "/ret/superversionget/getSuperversionById",
		type : "post",
		dataType : "json",
		data:{superversionId:superversionId},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				for(var name in data.list)
				{
				if(name=="attach")
					{
					$("#attach").attr("data_value",data.list.attach);
					createAttach("attach","1");
					}else if(name=="noticeType")
					{
						$("#"+name).html(getCodeClassName(data.list.noticeType, "notice"));
					}else if(name=="createUser")
					{
						$("#"+name).html(getUserNameByStr(data.list.createUser));
					}else if(name=="type")
					{
						$("#configId").html(getConfigName(data.list[name]));
					}else if(name=="handedUser")
					{
						$("#"+name).html(getUserNameByStr(data.list[name]));
					}else if(name=="joinUser")
					{
						$("#"+name).html(getUserNameByStr(data.list[name]));
					}else if(name=="leadId")
					{
						$("#"+name).html(getUserNameByStr(data.list[name]));
					}else{
						$("#"+name).html(data.list[name]);
						}
				}
				}
		}
	})
});

function getConfigName(configId)
{
	var returnStr="";
	$.ajax({
		url : "/ret/superversionget/getSuperversionConfigById",
		type : "post",
		dataType : "json",
		async : false,
		data:{configId:configId},
		success : function(data) {
			console.log(data);
			if(data.status=="500")
			{
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
					returnStr= data.list.typeName;
				}
		}
	})
	return returnStr;
}