$(function() {
	readnews();
	getComments();
	$(".js-send").unbind("click").click(function() {
		sendComments();
	})
});
function readnews() {
	$.ajax({
		url : "/ret/oaget/getReadNews",
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
				for ( var name in data.list) {
					if (name == "attach") {
						$("#attach").attr("data_value", data.list.attach);
						createAttach("attach", data.list.attachPriv);
					} else {
						$("#" + name).html(data.list[name]);
					}
				}
			}
		}
	})
}

function getComments() {
	$.ajax({
		url : "/ret/oaget/getCommentsList",
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
				for (var i = 0; i < data.list.length; i++) {
					var html = '<div class="comment">' + '<img src="/sys/file/getOtherHeadImg?headImg=' + data.list[i].headImg + '" alt="" class="comment-avatar" onerror="this.onerror=null;this.src=\'/assets/img/avatars/adam-jansen.jpg\'">' + '<div class="comment-body">'
							+ '<div class="comment-text">' + '<div class="comment-header">' + '<a href="#" title="">' + data.list[i].createUserName + '</a><span>' + data.list[i].createTime + '</span>' + '</div>' + data.list[i].commContent + '</div>' + '</div>' + '</div>';
					$("#newsComment").append(html);

				}

			}
		}
	})
}

function sendComments() {
	$.ajax({
		url : "/set/oaset/insertNewsComments",
		type : "post",
		dataType : "json",
		data : {
			newsId : newsId,
			commType : $('input:radio[name="commType"]:checked').val(),
			commContent : $("#commContent").val()
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				top.layer.msg(data.msg);
				window.location.reload();
			}
		}
	})

}