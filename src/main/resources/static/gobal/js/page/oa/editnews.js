$(function() {
	$('#content').summernote({
		height : 300
	});
	getCodeClass("newsType", "news");
	getSmsConfig("msgType", "news");
	jeDate("#sendTime", {
		format : "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format : "YYYY-MM-DD"
	});
	doinit();
	$("#updatabut").unbind("click").click(function(){
		updatenews();
	})
})

function doinit() {
	$.ajax({
		url : "/ret/oaget/getNewsById",
		type : "post",
		dataType : "json",
		data : {
			newsId : newsId
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				for (name in data.list) {
					if (name == "content") {
						$('#content').code(data.list[name]);
					} else if (name == "userPriv") {
						$("#userPriv").attr("data-value", data.list[name]);
						$("#userPriv").val(getUserNameByStr(data.list[name]));
					} else if (name == "deptPriv") {
						$("#deptPriv").attr("data-value", data.list[name]);
						$("#deptPriv").val(getDeptNameByDeptIds(data.list[name]));
					} else if (name == "levelPriv") {
						$("#levelPriv").attr("data-value",data.list[name]);
						$("#levelPriv").val(getUserLevelStr(data.list[name]));
					} else if(name=="attachPriv"){
						$("input:radio[name='attachPriv'][value='"+data.list[name]+"']").attr("checked","checked");
					}else if(name=="attach")
					{
						$("#newsattach").attr("data_value", data.list[name]);
						createAttach("newsattach",4);
					}else
					{
						$("#" + name).val(data.list[name]);
					}

				}
			}
		}
	})
}



function updatenews()
{
	$.ajax({
		url : "/set/oaset/updateNews",
		type : "post",
		dataType : "json",
		data:{
			newsId:$("#newsId").val(),
			newsTitle:$("#newsTitle").val(),
			newsType:$("#newsType").val(),
			userPriv:$("#userPriv").attr("data-value"),
			deptPriv:$("#deptPriv").attr("data-value"),
			levelPriv:$("#levelPriv").attr("data-value"),
			content:$("#content").code(),
			sendTime:$("#sendTime").val(),
			endTime:$("#endTime").val(),
			attach:$("#newsattach").attr("data_value"),
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