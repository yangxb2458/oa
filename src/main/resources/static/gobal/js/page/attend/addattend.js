$(function(){
	getAllAttendConfigList()	
});

function getAllAttendConfigList()
{
	$.ajax({
		url : "/ret/oaget/getMyAttendConfigList ",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				var time=getTime();
				var html="";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<tr>\n"+
								"<td style=\"width: 100px;text-align: center;vertical-align: middle;\">"+(i+1)+"</td>\n"+
								"<td style=\"text-align: center;vertical-align: middle;width: 200px;\">"+data.list[i].title+"</td>\n"+
								"<td style=\"width: 300px;text-align: center;vertical-align: middle;\">"+data.list[i].beginTime+"</td>\n"+
								"<td style=\"text-align: center;vertical-align: middle;\">"+data.list[i].remark+"</td>\n"+
								"<td style=\"width: 200px; text-align: center\">\n";
					if(data.list[i].beginTimeStatus=="0")
					{
						if(data.list[i].beginTime>time)
						{
							html+="<a href=\"javascript:void(0);addattend(1);\" class=\"btn btn-primary btn-sm\">上班打卡</a>";
						}else
						{
							html+="<a href=\"javascript:void(0);remarkattend(1);\" class=\"btn btn-primary btn-sm\">补打说明</a>";
						}
					}else
					{
						html+="已完成打卡"
					}
					html+="</td></tr>\n"+
							"<tr>\n"+
								"<td style=\"width: 100px;text-align: center;vertical-align: middle;\">"+(i+2)+"</td>\n"+
								"<td style=\"text-align: center;vertical-align: middle;width: 200px;\">"+data.list[i].title+"</td>\n"+
								"<td style=\"width: 300px;text-align: center;vertical-align: middle;\">"+data.list[i].endTime+"</td>\n"+
								"<td style=\"text-align: center;vertical-align: middle;\">"+data.list[i].remark+"</td>\n"+
								"<td style=\"width: 200px; text-align: center\">" ;
					if(data.list[i].endTimeStatus=="0")
					{
								if(data.list[i].endTime<time)
								{
									html+="<a href=\"javascript:void(0);addattend(2);\" class=\"btn btn-success btn-sm\">下班打卡</a>" ;
								}else
								{
									html+="<a href=\"javascript:void(0);remarkattend(2);\" class=\"btn btn-success btn-sm\">补打说明</a>" ;
								}
					}else
					{
						html+="已完成打卡"
					}
							html+="</td>\n"+
							"</tr>";
				}
				$("#attendtbody").html(html);
				}
		}
	});
}

function getTime() {
	var myDate = new Date();
	var h = myDate.getHours(); //获取当前小时数(0-23)
	var m = myDate.getMinutes(); //获取当前分钟数(0-59)
	var s = myDate.getSeconds();
	var now = getNow(h) + ':' + getNow(m) + ":" + getNow(s);
	return now;
}

function remarkattend (status)
{
	
}

function addattend(status)
{
	$.ajax({
		url : "/set/oaset/insertAttend",
		type : "post",
		dataType : "json",
		data : {
			status:status,
			time:getTime()
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				top.layer.msg(data.msg);
				window.location.reload();
				}
		}
	});
}