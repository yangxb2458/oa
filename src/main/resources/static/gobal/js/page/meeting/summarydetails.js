$(function(){
	$.ajax({
		url : "/ret/meetingget/getMeetingNotesInfo",
		type : "post",
		dataType : "json",
		data : {
			notesId : notesId
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.list);
				var info = data.list;
				for(var id in info)
				{
					if(id=="attach")
					{
						$("#meetingattach").attr("data_value",info.attach);
						createAttach("meetingattach",info.attachPriv);
					}else
					{
						$("#"+id).html(info[id]);
					}
				}
			}
		}
	});
});