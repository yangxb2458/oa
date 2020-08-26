$(function() {
	$.ajax({
		url : "/ret/meetingget/getMeetingById",
		type : "post",
		dataType : "json",
		data : {
			meetingId : meetingId
		},
		success : function(data) {
			if (data.status == 200) {
				console.log(data.list);
				for(var id in data.list)
				{
					if(id=="attach")
					{
						$("#meetingattach").attr("data_value",data.list.attach);
						createAttach("meetingattach",data.list.attachPriv);
					}else if(id=="meetingType")
					{
						$("#"+id).html(getCodeClassName(data.list[id],"meeting"));
					}else if(id=="chair")
					{
						$("#"+id).html(getUserNameByStr(data.list[id]));
					}else if(id=="notesUser")
					{
						$("#"+id).html(getUserNameByStr(data.list[id]));
					}else if(id=="userJoin")
					{
						$("#"+id).html(getUserNameByStr(data.list[id]));
					}else if(id=="deptJoin")
					{
						$("#"+id).html(getDeptNameByDeptIds(data.list[id]));
					}else if(id=="leaveJoin")
					{
						$("#"+id).html(getUserLevelStr(data.list[id]));
					}else if(id=="roomId")
					{
						$("#"+id).html(getRoomName(data.list[id]));
					}else if(id=="deviceId")
					{
						$("#"+id).html(getDeviceNameList(data.list[id]));
					}else
					{
						$("#"+id).html(data.list[id]);
					}
				}
			} else if(data.status=="100"){
				top.layer.msg(data.msg);
			}else{
				console.log(data.msg);
			}
		}
	});
})

function getDeviceNameList(deviceId)
{
	var returnStr="";
	$.ajax({
		url : "/ret/meetingget/getDeviceListName",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			deviceIds : deviceId
		},
		success : function(data) {
			if(data.status=="200")
			{
				returnStr=data.redirect;
			}else
			{
				console.log(data.msg);
			}
		}
	});
	return returnStr;
}

function getRoomName(roomId)
{
	var returnStr="";
	$.ajax({
		url : "/ret/meetingget/getMeetingRoomById",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			roomId : roomId
		},
		success : function(data) {
			if(data.status=="200")
			{
				returnStr=data.list.name;
			}else
			{
				console.log(data.msg);
			}
		}
	})
	return returnStr;
}