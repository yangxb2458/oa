$(function(){
	$(".js-query-but").unbind("click").click(function(){
		getMyAllAttendList();
	});
	getYearList();
	getMyAllAttendList();
})

function getYearList()
{
	$.ajax({
		url : "/ret/oaget/getAttendYearList",
		type : "post",
		dataType : "json",
		async : false,
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				var html="";
				for(var i=0;i<data.list.length;i++)
					{
					if(i==0)
					{
						html+="<option value=\""+data.list[i].year+"\" selected>"+data.list[i].year+"</option>"
					}else
					{
						html+="<option value=\""+data.list[i].year+"\">"+data.list[i].year+"</option>"
					}
					}
				$("#year").html(html);
				}
		}
	})
}


function getMyAllAttendList()
{
	$.ajax({
		url : "/ret/oaget/getMyAllAttendList",
		type : "post",
		dataType : "json",
		async : false,
		data:{year:$("#year").val()},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				var html="";
				for(var i=12;i>0;i--)
				{
					var record = data.list[i];
					html+="<div class=\"col-md-12\"><h5 class=\"row-title before-darkorange\"><i class=\"typcn typcn-th-menu blueberry\"></i>"+i+"月份</h5>" +
							"<div class=\"col-md-12\" id=\"month_"+i+"\">";
					if(record.length>0)
					{
						html+="<table class=\"table table-hover table-striped table-bordered\"><thead class=\"bordered-blueberry\">\n"+
                            	"<tr><th>序号</th><th>日期</th><th>考勤时间</th><th>考勤类型</th><th>打卡方式</th><th>考勤状态</th><th style='width:200px;'>打卡说明</th></tr></thead>\n"+
                            "<tbody>\n";
						for(var j=0;j<record.length;j++)
						{
							html+="<tr><td>"+(j+1)+"</td><td>"+record[j].day+"日</td><td>"+record[j].time+"</td><td>";
							if(record[j].status=="1")
							{
								html+="上班打卡";
							}else if(record[j].status=="2")
							{
								html+="下班打卡";
							}
							html+="</td><td>"+record[j].source+"</td><td>";
							if(record[j].type=="0")
							{
								html+="正常打卡";
							}else if(record[j].type=="1")
							{
								html+="补打卡";
							}else if(record[j].type=="2")
							{
								html+="迟到";
							}else if(record[j].type=="3")
							{
								html+="早退";
							}else if(record[j].type=="4")
							{
								html+="旷工";
							}
							html+="</td><td>"+record[j].remark+"</td></tr>";
						}
                            html+="</tbody></table>";
					}else
					{
						html+="<div class=\"alert alert-info fade in\"><button class=\"close\" data-dismiss=\"alert\">×</button><i class=\"fa-fw fa fa-info\"></i><strong>提醒!</strong>未查到考勤记录</div>";
					}
					html+="</div></div>";
				}
				$("#myTable").html(html);
				}
		}
	})
}

function getAttendList()
{
	
}
