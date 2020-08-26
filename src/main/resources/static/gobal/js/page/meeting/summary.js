$(function(){
	 $('#remark').summernote({ height:300 });
	 getSmsConfig("msgType","meeting");
	getNotNotesMeetingList();
	$("#createbut").unbind("click").click(function(){
		createMeetingNotes();
	});
})

function getNotNotesMeetingList()
{
	$.ajax({
		url : "/ret/meetingget/getNotNotesMeetingList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == "200") {
				for(var i=0;i<data.list.length;i++)
				{
					$("#meetingId").append("<option value='"+data.list[i].meetingId+"'>"+data.list[i].subject+"</option>")
				}
			} else if(data.status=="100"){
				top.layer.msg(data.msg);
			}else{
				console.log(data.msg);
			}
		}
	});
}


function createMeetingNotes()
{
	var meetingId=$("#meetingId").val();
	if(meetingId==""||meetingId==null)
	{
		top.layer.msg("请先选择相关联的会议！");
	}else
	{
		$.ajax({
			url : "/set/meetingset/insertMeetingNotes",
			type : "post",
			dataType : "json",
			data:{
				meetingId:meetingId,
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
}