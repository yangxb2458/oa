$(function() {
	$.ajax({
		url : "/ret/oaget/getDiaryById",
		type : "post",
		dataType : "json",
		data : {
			diaryId : diaryId
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				$("#title").html(data.list.title);
				$("#content").html(data.list.content);
				$("#createTime").html(data.list.createTime);
				$("#diaryType").html(getCodeClassName(data.list.diaryType, "diary"));
				$("#attach").attr("data_value",data.list.attach);
				$("#diaryDay").html("指定日期：【"+data.list.diaryDay+"】");
				createAttach("attach",data.list.attachPriv);
			}
		}
	});
})