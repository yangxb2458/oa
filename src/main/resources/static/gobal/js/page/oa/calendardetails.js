$(function(){
	$.ajax({
		url : "/ret/oaget/getCalendarById",
		type : "post",
		dataType : "json",
		data : {
			calendarId : calendarId
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
					$("#start").html(data.list.start);
					$("#end").html(data.list.end);
					$("#content").html(data.list.content);
			}
		}
	})
})