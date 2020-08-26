$(function(){
	getTaskDetails();
	gantt.config.xml_date = "%Y-%m-%d %H:%i";
	gantt.addMarker({
		start_date: new Date(),
		css: "today",
		text: "今日",
		title: "日期:"+getSysDate()
	});
	gantt.config.readonly = true;
	gantt.init("gantt_here");
	gantt.attachEvent("onGanttReady", function(){
		var tooltips = gantt.ext.tooltips;
		tooltips.tooltip.setViewport(gantt.$task_data);
	});
	gantt.parse(getTaskGantInfo());
	gantt.attachEvent("onAfterTaskAdd", function(id,item){
		insertTaskData(item);
		});
	gantt.attachEvent("onAfterTaskUpdate", function(id,item){
		updateTaskGanttData(item);
	});
	gantt.attachEvent("onAfterLinkAdd", function(id,item){
		insertTaskLink(item)
		});

	gantt.attachEvent("onAfterTaskDelete", function(id,item){
		deleteTaskData(item);
	});
	
	gantt.attachEvent("onAfterLinkDelete", function(id,item){
		deleteTaskLink(item);
	});
	
});
function getTaskGantInfo()
{
	var returnData;
	$.ajax({
		url : "/ret/taskget/getTaskGantInfo",
		type : "POST",
		dataType : "json",
		async : false,
		data : {
			taskId:taskId
		},
		success : function(data) {
			if(data.status=="200")
				{
				returnData = data.list;
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else
				{
					console.log(data.msg);
				}
		}
	})
	return returnData;
}

function getTaskDetails()
{
	$.ajax({
		url : "/ret/taskget/getTaskById",
		type : "post",
		dataType : "json",
		data:{taskId:taskId},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				$("#taskName").html(data.list.taskName);
				$("#taskDetails").html("开始时间："+data.list.beginTime+"&nbsp;&nbsp;周期："+data.list.duration+"天")
				}
		}
	});
}
