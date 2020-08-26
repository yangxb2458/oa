$(function() {
	$.ajax({
		url : "/ret/superversionget/getQuerySuperversionForType",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == "200") {
				var datalist = data.list;
				var html="";
				for(var i=0;i<datalist.length;i++)
				{
					html+="<tr><td>"+(i+1)+"</td><td>"+datalist[i].typeName+"</td><td>"+datalist[i].leadUserName+"</td>" +
							"<td>"+datalist[i].zsCount+"</td><td>"+datalist[i].doinCount+"</td><td>"+datalist[i].delayCount+"</td><td>"+datalist[i].endCount+"</td></tr>";
				}
				$("#tbody").html(html);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
})