$(function(){
	$('#content').summernote({ height:300 });
	getCodeClass("noticeType","notice");
	getSmsConfig("msgType","notice");
	$("#editbut").unbind("click").click(function(){
		updateNotice();
	});
	jeDate("#sendTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	$.ajax({
		url : "/ret/noticeget/getNoticeInfo",
		type : "post",
		dataType : "json",
		data : {
			noticeId : noticeId
		},
		success : function(data) {
			if(data.status==200)
				{
				for(var name in data.list)
					{
						if(name=="content")
						{
							$("#content").code(data.list[name]);
						}else if(name=="attach")
						{
							$("#noticeattach").attr("data_value", data.list[name]);
							createAttach("noticeattach");
						}else if(name=="attachPriv"){
							$("input:radio[name='attachPriv'][value='"+data.list[name]+"']").attr("checked","checked");
						}else if (name == "userPriv") {
							$("#userPriv").attr("data-value", data.list[name]);
							$("#userPriv").val(getUserNameByStr(data.list[name]));
						} else if (name == "deptPriv") {
							$("#deptPriv").attr("data-value", data.list[name]);
							$("#deptPriv").val(getDeptNameByDeptIds(data.list[name]));
						} else if (name == "leavePriv") {
							$("#leavePriv").attr("data-value",data.list[name]);
							$("#leavePriv").val(getUserLevelStr(data.list[name]));
						}else
						{
							$("#"+name).val(data.list[name]);
						}
					}
				}else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
				{
					console.log(data.msg);
				}
		}
	})
})



function updateNotice()
{
	$.ajax({
		url : "/set/noticeset/updateNotice",
		type : "post",
		dataType : "json",
		data:{
			noticeId:noticeId,
			noticeTitle:$("#noticeTitle").val(),
			noticeType:$("#noticeType").val(),
			userPriv:$("#userPriv").attr("data-value"),
			deptPriv:$("#deptPriv").attr("data-value"),
			leavePriv:$("#leavePriv").attr("data-value"),
			content:$("#content").code(),
			sendTime:$("#sendTime").val(),
			endTime:$("#endTime").val(),
			attach:$("#noticeattach").attr("data_value"),
			isTop:$("input:radio[name='isTop']:checked").val(),
			attachPriv:$("input:radio[name='attachPriv']:checked").val(),
			msgType:getCheckBoxValue("msgType")
		},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				window.location.reload();
				top.layer.msg(data.msg);
				}
		}
	})
}