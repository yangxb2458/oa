$(function(){
	 $('#remark').summernote({ height:300 });
	 getSmsConfig("msgType","meeting");
	$("#updatebut").unbind("click").click(function(){
		updateMeetingNotes();
	});
	$.ajax({
		url : "/ret/meetingget/getMeetingNotesById",
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
				var info = data.list;
				for(var id in info)
				{
					if(id=="attach")
					{
						$("#meetingattach").attr("data_value",info.attach);
						createAttach("meetingattach",4);
					}else if(id=="userPriv")
					{
						$("#userPriv").attr("data-value", info[id]);
						$("#userPriv").val(getUserNameByStr(info[id]));
					}else if(id=="deptPriv")
					{
						$("#deptPriv").attr("data-value", info[id]);
						$("#deptPriv").val(getDeptNameByDeptIds(info[id]));
					}else if(id=="leavePriv")
					{
						$("#leavePriv").attr("data-value",info[id]);
						$("#leavePriv").val(getUserLevelStr(info[id]));
					}else if(id=="remark")
					{
						$("#remark").code(info[id]);
					}else if(id=="attachPriv")
					{
						$("input:radio[name='attachPriv'][value='"+info[id]+"']").attr("checked","checked");
					}else if(id=="meetingId")
					{
						$.ajax({
						url : "/ret/meetingget/getMeetingById",
						type : "post",
						dataType : "json",
						async : false,
						data : {
							meetingId : info[id]
						},
						success : function(res) {
							$("#subject").html(res.list.subject);
						}
						});
					}else
					{
						$("#"+id).val(info[id]);
					}
				}
			}
		}
	});
	
})
function updateMeetingNotes()
{
		$.ajax({
			url : "/set/meetingset/updateMeetingNotes",
			type : "post",
			dataType : "json",
			data:{
				notesId:notesId,
				notesTitle:$("#notesTitle").val(),
				userPriv:$("#userPriv").attr("data-value"),
				deptPriv:$("#deptPriv").attr("data-value"),
				leavePriv:$("#leavePriv").attr("data-value"),
				attach:$("#meetingattach").attr("data_value"),
				attachPriv:$("input:radio[name='attachPriv']:checked").val(),
				remark:$("#remark").code(),
				msgType:getCheckBoxValue("msgType")
			},
			success : function(data) {
				if (data.status == "200") {
					top.layer.msg(data.msg);
					window.location.reload();
				} else if(data.status=="100"){
					top.layer.msg(data.msg);
				}else{
					console.log(data.msg);
				}
			}
		});
}