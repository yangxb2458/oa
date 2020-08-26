$(function(){
	getNoticeInfo();
});
function getNoticeInfo()
{
	$.ajax({
		url : "/ret/noticeget/getNoticeInfo",
		type : "post",
		dataType : "json",
		data:{noticeId:noticeId},
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
					}else
						{
						$("#"+name).html(data.list[name]);
						}
				}
				}
		}
	})
	}