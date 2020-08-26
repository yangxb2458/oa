$(function(){
	jeDate("#beginTime", {
		format: "YYYY-MM-DD hh:mm",
		minDate:getSysDate()
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD hh:mm",
		minDate:getSysDate()
	});
	getCodeClass("meetingType","meeting");
    var nowtime = getSysDate();
    $("#nowtime").html(nowtime);
    $("#nowtime").attr("data-value",nowtime);
    $(".js-reduceday").unbind("click").click(function(){
    	reduceDay();
    });
    $(".js-addday").unbind("click").click(function(){
    	addDay()
    });
    $(".glyphicon-search").unbind("click").click(function(){
    	getCanUseMeetingRoomList();
    });
    createTimeLine();
    getCanUseMeetingRoomList();
    $(".js-applymeeting").unbind("clikc").click(function(){
    	document.getElementById("form").reset();
    	$('#deviceId').val([]).trigger('change');
    	$("#notesUser").attr("data-value");
    	$("#chair").attr("data-value");
    	$("#userJoin").attr("data-value");
    	$("#deptJoin").attr("data-value");
    	$("#leaveJoin").attr("data-value");
    	$("#show_meetingattach").html("");
    	$("#meetingattach").attr('data_value');
    	$("#beginTime").val("");
    	$("#endTime").val("");
		$("#applymeetingmodal").modal("show");
		$(".js-save").unbind("click").click(function(){
			applymeeting();
		});
	})
	$.fn.modal.Constructor.prototype.enforceFocus = function() {};
	$.ajax({
		url : "/ret/meetingget/getCanUseDeviceList",
		type : "post",
		dataType : "json",
		async : false,
		success : function(data) {
			if(data.status=="200")
			{
				var html="";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<option value='"+data.list[i].deviceId+"'>"+data.list[i].deviceName+"</option>";
				}
			$("#deviceId").html(html);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	});
	$('#deviceId').select2();
	getMeetingByDay()
	getSmsConfig("msgType","meeting");
});
function applymeeting()
{
	$.ajax({
		url : "/set/meetingset/insertMeeting",
		type : "post",
		dataType : "json",
		data:{
			subject:$("#subject").val(),
			meetingType:$("#meetingType").val(),
			chair:$("#chair").attr("data-value"),
			roomId:$("#roomId").val(),
			notesUser:$("#notesUser").attr("data-value"),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val(),
			userJoin:$("#userJoin").attr("data-value"),
			deptJoin:$("#deptJoin").attr("data-value"),
			leaveJoin:$("#leaveJoin").attr("data-value"),
			otherJoin:$("#otherJoin").val(),
			attach:$("#meetingattach").attr("data_value"),
			deviceId:$("#deviceId").val(),
			attachPriv:$("input:radio[name='attachPriv']:checked").val(),
			remark:$("#remark").val(),
			msgType:getCheckBoxValue("msgType")
		},
		success : function(data) {
			if (data.status == "200") {
				top.layer.msg(data.msg);
				$("#applymeetingmodal").modal("hide");
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else{
				console.log(data.msg);
			}
		}
	})
}
function reduceDay()
{
	$(".filedl").removeClass().addClass("filedl")
	var nowtime=$("#nowtime").attr("data-value");
	var date = new Date(nowtime);
    date.setDate(date.getDate() - 1);
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var newtime=date.getFullYear() + '-' + getFormatDate(month) + '-' + getFormatDate(day);
    $("#nowtime").html(newtime);
    $("#nowtime").attr("data-value",newtime);
    getMeetingByDay()
}
function addDay()
{
	$(".filedl").removeClass().addClass("filedl")
	var nowtime=$("#nowtime").attr("data-value");
	var date = new Date(nowtime);
    date.setDate(date.getDate() + 1);
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var newtime=date.getFullYear() + '-' + getFormatDate(month) + '-' + getFormatDate(day);
    $("#nowtime").html(newtime);
    $("#nowtime").attr("data-value",newtime);
    getMeetingByDay()
}

function getFormatDate(arg) {
    if (arg == undefined || arg == '') {
        return '';
    }

    var re = arg + '';
    if (re.length < 2) {
        re = '0' + re;
    }
    return re;
}
function createTimeLine()
{
	$(".axis-x").empty();
	var hour = 24;
	var timeArr=[];
	for(var i=14;i<48;i++)
		{
			if(i%2==0)
			{
				timeArr.push(parseInt(i/2)+":00");
			}else
			{
				timeArr.push(parseInt(i/2)+":30");
			}
		}
	var eArr = [];
	for(var i=0;i<timeArr.length;i++)
	{
		var json={};
		json.time1=timeArr[i];
		json.time2=timeArr[(i+1)%34];
		eArr.push(json);
	}
	var html="";
	for(var i=0;i<eArr.length;i++)
	{
		html+=" <div><span class=\"time_interval\">"+eArr[i].time1+"</span><span class=\"time_interval\">"+eArr[i].time2+"</span></div>";
	}
	$(".axis-x").html(html);
}


function getCanUseMeetingRoomList()
{
	$.ajax({
		url : "/ret/meetingget/getCanUseMeetingRoomList",
		type : "post",
		dataType : "json",
		async : false,
		data:{search:$("#meetingroomsearch").val()},
		success : function(data) {
			if (data.status == "200") {
				$("#filedlist").empty();
				var html="";
				var mhtml="";
				for(var i=0;i<data.list.length;i++)
					{
					mhtml+="<option value='"+data.list[i].roomId+"'>"+data.list[i].name+"</option>";
					html+="<div data-value='"+data.list[i].roomId+"'><ul><li class='meetingitem'>"+data.list[i].name+"</li></ul></div>";
					createfiledl(data.list[i].roomId);
					}
				$("#roomId").html(mhtml);
				$("#roomlist").html(html);
				initSelectTime();
			} else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else{
				console.log(data.msg);
			}
		}
	});
}

function createfiledl(roomId)
{
	var html="<div class='timeitem' data-value='"+roomId+"'>";
	for(var i=14;i<48;i++)
	{
		html+="<div data-value='"+i+"' class='filedl'></div>"
	}
	html+="</div>";
	$("#filedlist").append(html);
}

function getMeetingByDay()
{
	$.ajax({
		url : "/ret/meetingget/getMeetingByDay",
		type : "post",
		dataType : "json",
		data:{
			dayStr:$("#nowtime").attr("data-value")
		},
		success : function(data) {
			if(data.status=="200")
			{
				for(var i=0;i<data.list.length;i++)
					{
					var roomId = data.list[i].roomId;
					var beginTime = data.list[i].beginTime;
					var endTime = data.list[i].endTime;
					$(".timeitem").each(function(){
						if($(this).attr("data-value")==roomId)
							{
								var time = beginTime.split(" ")[1];
								var h = time.split(":")[0];
								var m = time.split(":")[1];
								var b= parseInt(h*2)+(m/60)*2
								var time = endTime.split(" ")[1];
								var h = time.split(":")[0];
								var m = time.split(":")[1];
								var e= parseInt(h)*2+(m/60)*2-1
								for(b;b<=e;b++)
								{
									$(this).find(".filedl[data-value='"+b+"']").addClass("seled").addClass("noprivselect");
								}
							}
					})
					}
			}
		}
		
	});

}