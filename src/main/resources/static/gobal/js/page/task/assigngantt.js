$(function(){
	getTaskDetails();
	$(".js-clear").unbind("click").click(function(){
		clearAll();
	})
	gantt.config.xml_date = "%Y-%m-%d %H:%i";
	gantt.config.lightbox.sections = [
		{name: "description", height: 38, map_to: "text", type: "textarea", focus: true},
		{
			name: "mainperson", height: 22, map_to: "userPriv", type: "select", options: getParticipantUserList()
		},
		{name: "time", type: "duration", map_to: "auto"}
	];
	gantt.addMarker({
		start_date: new Date(),
		css: "today",
		text: "今日",
		title: "日期:"+getSysDate()
	});
	//gantt.config.readonly = true;
	gantt.init("gantt_here");
	gantt.attachEvent("onGanttReady", function(){
		var tooltips = gantt.ext.tooltips;
		tooltips.tooltip.setViewport(gantt.$task_data);
	});
	gantt.parse(getTaskGantInfo());
	gantt.attachEvent("onAfterTaskAdd", function(id,item){
		gantt.render();
		insertTaskData(item);
		});
	gantt.attachEvent("onAfterTaskUpdate", function(id,item){
		updateTaskGanttData(item);
	});
	gantt.attachEvent("onAfterLinkAdd", function(id,item){
		console.log(item);
		insertTaskLink(item)
		});

	gantt.attachEvent("onAfterTaskDelete", function(id,item){
		console.log(item);
		deleteTaskData(item);
	});
	
	gantt.attachEvent("onAfterLinkDelete", function(id,item){
		console.log(item);
		deleteTaskLink(item);
	});
	
});
function clearAll()
{
	gantt.clearAll();
}
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

function insertTaskData(item)
{
	var date = new Date(item.start_date);  
	var date_value=date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
	console.log(date_value);
	$.ajax({
		url : "/set/taskset/insertTaskGanttData",
		type : "POST",
		dataType : "json",
		data : {
			taskId:taskId,
			text:item.text,
			startDate:date_value,
			duration:item.duration,
			progress:item.progress,
			userPriv:item.userPriv,
			open:item.open
		},
		success : function(data) {
			if(data.status=="200")
				{
					top.layer.msg(data.msg);
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else
				{
					console.log(data.msg);
				}
		}
	})
}

function updateTaskGanttData(item)
{
	var date = new Date(item.start_date);  
	var date_value=date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
	$.ajax({
		url : "/set/taskset/updateTaskGanttData",
		type : "POST",
		dataType : "json",
		data : {
			taskDataId:item.id,
			taskId:taskId,
			text:item.text,
			startDate:date_value,
			duration:item.duration,
			userPriv:item.userPriv,
			progress:item.progress,
			open:item.open
		},
		success : function(data) {
			if(data.status=="200")
				{
					top.layer.msg(data.msg);
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else
				{
					console.log(data.msg);
				}
		}
	})
}

function deleteTaskData(item)
{
	$.ajax({
		url : "/set/taskset/deleteTaskGanttData",
		type : "POST",
		dataType : "json",
		data : {
			taskDataId:item.id,
			taskId:taskId
		},
		success : function(data) {
			if(data.status=="200")
				{
					top.layer.msg(data.msg);
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else
				{
					console.log(data.msg);
				}
		}
	})
}

function insertTaskLink(item)
{
	$.ajax({
		url : "/set/taskset/insertTaskGanttLink",
		type : "POST",
		dataType : "json",
		data : {
			taskId:taskId,
			source:item.source,
			target:item.target,
			type:item.type
		},
		success : function(data) {
			if(data.status=="200")
				{
					top.layer.msg(data.msg);
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else
				{
					console.log(data.msg);
				}
		}
	})
}

function deleteTaskLink(item)
{
	$.ajax({
		url : "/set/taskset/deleteTaskGanttLink",
		type : "POST",
		dataType : "json",
		data : {
			taskLinkId:item.id,
			taskId:taskId
		},
		success : function(data) {
			if(data.status=="200")
				{
					top.layer.msg(data.msg);
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else
				{
					console.log(data.msg);
				}
		}
	})
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



function getParticipantUserList()
{
	var returnValue;
	$.ajax({
		url : "/ret/taskget/getParticipantUserList",
		type : "post",
		dataType : "json",
		data:{taskId:taskId},
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
					returnValue = data.list;
				}
		}
	});
	return returnValue;
}