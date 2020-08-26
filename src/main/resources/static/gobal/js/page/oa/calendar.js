$(function() {
	getSmsConfig("msgType", "calendar");
	$(document).ready(function() {
		/* initialize the external events
		-----------------------------------------------------------------*/
		$('#external-events .external-event').each(function() {

			// store data so the calendar knows to render an event upon drop
			$(this).data('event', {
				title : $.trim($(this).text()), // use the element's text as the event title
				stick : true
			// maintain when user navigates (see docs on the renderEvent method)
			});

			// make the event draggable using jQuery UI
			$(this).draggable({
				zIndex : 999,
				revert : true, // will cause the event to go back to its
				revertDuration : 0
			//  original position after the drag
			});

		});
		/* initialize the calendar
		-----------------------------------------------------------------*/
		$('#calendar').fullCalendar({
			header : {
				left : 'prev,next today',
				center : 'title',
				right : 'month,agendaWeek,agendaDay'
			},
			eventAfterRender : function(event, element) {
				element.popover({
					title : getTypeName(event.type),
					html : true,
					placement : 'auto right',
					content : '<div style="min-width:150px;"><div style="max-height:120px;overflow-y:auto;"><div>始于:' + (event.start.format("YYYY-MM-DD hh:mm:ss")) + '</div><div>事件: ' + event.title + '</div></div>' + '<div align="center"><a onclick="edit(this);" data-value="' + event.calendarId + '" class="btn btn-azure btn-sm">编辑</a>&nbsp&nbsp<a onclick="del(this);" data-value="' + event.calendarId + '" class="btn btn-darkorange btn-sm">删除</a></div></div>'
				}).on("mouseenter", function() {
					var _this = this;
					$(this).popover("show");
					$(this).siblings(".popover").on("mouseleave", function() {
						$(_this).popover('hide');
					});
				}).on("mouseleave", function() {
					var _this = this;
					setTimeout(function() {
						if (!$(".popover:hover").length) {
							$(_this).popover("hide")
						}
					}, 100);
				});
			},
			defaultView : 'month',
			firstDay : 1,
			isRTL : false,//从右到左显示
			weekends : true,
			editable : true,
			minTime : "06:00",
			maxTime : "22:00",
			firstHour : 6,
			handleWindowResize : true,
			showNonCurrentDates : true,//是否显示非本月日期
			aspectRatio : 1.35,
			selectable : true,//是否可以拖拉选择
			selectHelper : true,//是否绘制一个“占位符”事件当用户拖动
			//unselectCancel: '.popover, .modal, .datepicker, .timepicker',
			droppable : true, // this allows things to be dropped onto the calendar
			drop : function(date, jsEvent) {
				console.log(date);
				console.log(jsEvent);
				if ($('#drop-remove').is(':checked')) {
					// if so, remove the element from the "Draggable Events" list
					$(this).remove();
				}
			},
			eventClick : function selectFunc(event, jsEvent, view) {

			},
			dayClick : function(date, jsEvent, view) {

			},
			select : function(start, end, jsEvent, view) {
				document.getElementById("form1").reset();
				$("#start").val(start.format('YYYY-MM-DD hh:mm:ss'));
				$("#end").val(end.format('YYYY-MM-DD hh:mm:ss'));
				jeDate("#start", {
					format : "YYYY-MM-DD hh:mm:ss",
					minDate:$("#start").val(),
					isinitVal: true
				});
				jeDate("#end", {
					format : "YYYY-MM-DD hh:mm:ss",
					minDate:getSysDate()
				});
				$(".js-addbtn").unbind("click").click(function() {
					addCalendar();
				});
				$("#addCalendarmodal").modal("show");
			},
			events : getMyCalendarList()
		});
	});
})
function addCalendar() {
	if ($("#content").val() == "") {
		top.layer.msg("日程内容不能为空!");
		return;
	}
	var start = $("#start").val();
	if (start == "") {
		top.layer.msg("日程开始时间不能为空!");
		return;
	}
	var now = getSysTime();
	if (now > start) {
		top.layer.msg("事件发生时间不能晚于当前时间!");
		return;
	}
	var share = "0";
	if ($("#share").is(':checked')) {
		share = "1"
	}
	$.ajax({
		url : "/set/oaset/addCalendar",
		type : "post",
		dataType : "json",
		data : {
			content : $("#content").val(),
			start : $("#start").val(),
			end : $("#end").val(),
			type : $("#type").val(),
			share : share,
			advance : $("input:radio[name='advance']:checked").val(),
			msgType : getCheckBoxValue("msgType")
		},
		success : function(data) {
			if (data.status == 200) {
				top.layer.msg(data.msg);
				location.reload();
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	})
}

function getMyCalendarList() {
	var events = [];
	$.ajax({
		url : "/ret/oaget/getMyCalendarList",
		type : "post",
		dataType : "json",
		data : {},
		async : false,
		success : function(data) {
			if (data.status == 200) {
				for (var i = 0; i < data.list.length; i++) {
					var json = {};
					for ( var name in data.list[i]) {
						if (name == "start" || name == "end") {
							json[name] = new Date(data.list[i][name]);
						} else {
							if (name == "type") {
								if (data.list[i][name] == "1") {
									json["borderColor"] = "#337ab7";
								} else if (data.list[i][name] == "2") {
									json["borderColor"] = "#ffce55";
								} else if (data.list[i][name] == "3") {
									json["borderColor"] = "#a0d468";
								} else if (data.list[i][name] == "4") {
									json["borderColor"] = "#e75b8d";
								} else if (data.list[i][name] == "5") {
									json["borderColor"] = "#2dc3e8";
								} else if (data.list[i][name] == "0") {
									json["borderColor"] = "#65b951";
								}
							}
							json[name] = data.list[i][name];
						}
					}
					events.push(json);
				}
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	})
	return events;
}
function del(Obj) {
	if (confirm("确定删除当前日程吗？")) {
		$.ajax({
			url : "/set/oaset/delCalendar",
			type : "post",
			dataType : "json",
			data : {
				calendarId : $(Obj).attr("data-value")
			},
			async : false,
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
					location.reload();
				} else if (data.status == 100) {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		})
	} else {
		return;
	}
}
function getTypeName(type) {
	if (type == "1") {
		return "部门例会";
	} else if (type == "2") {
		return "工作汇报";
	} else if (type == "3") {
		return "公司出差";
	} else if (type == "4") {
		return "接待客户";
	} else if (type == "5") {
		return "个人事务";
	} else if (type == "0") {
		return "其它事务";
	}
}

function edit(Obj) {
	document.getElementById("form1").reset();
	var calendarId = $(Obj).attr("data-value");
	$.ajax({
		url : "/ret/oaget/getCalendarById",
		type : "post",
		dataType : "json",
		data : {
			calendarId : calendarId
		},
		async : false,
		success : function(data) {
			if (data.status == 200) {
				for ( var name in data.list) {
					if (name == "advance") {
						$("input:radio[name='advance'][value='" + data.list[name] + "']").attr("checked", "checked");
					} else if (name == "share") {
						if (data.list.share == "1") {
							$("#share").attr("checked", true);
						}
					} else {
						$("#" + name).val(data.list[name]);
					}
				}
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	})

	$("#addCalendarmodal").modal("show");
	$(".js-addbtn").unbind("click").click(function() {
		updateCalendar(calendarId);
	});
}

function updateCalendar(calendarId) {
	if ($("#content").val() == "") {
		top.layer.msg("日程内容不能为空!");
		return;
	}
	var start = $("#start").val();
	if (start == "") {
		top.layer.msg("日程开始时间不能为空!");
		return;
	}
	var now = getSysTime();
	if (now > start) {
		top.layer.msg("事件发生时间不能晚于当前时间!");
		return;
	}

	var share = "0";
	if ($("#share").is(':checked')) {
		share = "1"
	}
	$.ajax({
		url : "/set/oaset/updateCalendar",
		type : "post",
		dataType : "json",
		data : {
			calendarId : calendarId,
			content : $("#content").val(),
			start : $("#start").val(),
			end : $("#end").val(),
			type : $("#type").val(),
			share : share,
			advance : $("input:radio[name='advance']:checked").val(),
			msgType : getCheckBoxValue("msgType")
		},
		success : function(data) {
			if (data.status == 200) {
				top.layer.msg(data.msg);
				location.reload();
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	})
}
