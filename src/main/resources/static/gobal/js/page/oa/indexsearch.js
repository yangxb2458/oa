$(function() {
	search();
	$(".glyphicon-search").unbind("click").click(function() {
		var url = window.location.href;
		location.replace(changeURLArg(url, "keywords", $(".input-xl").val()));
		search();
	});
})

$(document).keyup(function(event) {
	if (event.keyCode == 13) {
		var url = window.location.href;
		location.replace(changeURLArg(url, "keywords", $(".input-xl").val()));
		//search();
	}
});

function search() {
	if ($(".input-xl").val() == "") {
		top.layer.msg("检索的内容不能为空！");
		return;
	}
	$("#details").empty();
	$.ajax({
		url : "/ret/knowledgeget/searchIndex",
		type : "post",
		dataType : "json",
		data : {
			keywords : $(".input-xl").val()
		},
		success : function(data) {
			var v = data.list.data;
			if(v.length>0)
			{
				$("#ts").hide();
			}else
			{
				$("#ts").show();
			}
			for (var i = 0; i < v.length; i++) {
				var fileName = v[i].fileName.split('_')[2];
				var extName = getType(fileName);
				$("#details").append('<div class="invoice-notes" style="margin-top:20px;">' + '<h5>' + fileName + '</h5>' + '<a href="javascript:void(0);" onclick="openFileOnLine(\'' + extName + '\',\'' + v[i].attachId + '\',\'1\')" ><p>' + v[i].fileContent + '</p><a>' + '</div>');

			}
		}
	});
}

function getType(file) {
	var filename = file;
	var index1 = filename.lastIndexOf(".");
	var index2 = filename.length;
	var type = filename.substring(index1, index2);
	return type;
}

function changeURLArg(url, arg, arg_val) {
	var pattern = arg + '=([^&]*)';
	var replaceText = arg + '=' + arg_val;
	if (url.match(pattern)) {
		var tmp = '/(' + arg + '=)([^&]*)/gi';
		tmp = url.replace(eval(tmp), replaceText);
		return tmp;
	} else {
		if (url.match('[\?]')) {
			return url + '&' + replaceText;
		} else {
			return url + '?' + replaceText;
		}
	}
}
