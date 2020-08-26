$(function() {
	getMyDeskConfig();
	InitiateWidgets();
	$(".column").sortable({
		connectWith : ".column",
		handle : ".portlet-header",
		cancel : ".portlet-toggle",
		placeholder : "portlet-placeholder ui-corner-all",
		update : function(event, ui){       //更新排序之后
			 getDeskConfigJson();
        }
	});
	$(".portlet").addClass("ui-widget ui-widget-content ui-helper-clearfix ui-corner-all").find(".portlet-header").addClass("ui-widget-header ui-corner-all").prepend("<span class='ui-icon ui-icon-minusthick portlet-toggle'></span>");
	$(".portlet-toggle").click(function() {
		var icon = $(this);
		icon.toggleClass("ui-icon-minusthick ui-icon-plusthick");
		icon.closest(".portlet").find(".portlet-content").toggle();
	});
	getMyEmail();
	getMyCalendar();
	getMyShortcutMenu();
});


function getMyDeskConfig()
{
	$.ajax({
		url : "/ret/unitget/getMyHomePage",
		type : "post",
		dataType : "json",
		async : false,
		success : function(data) {
			if(data.status=="200")
			{
				var leftJson=[];
				var rightJson=[];
				if(data.list)
				{
					leftJson=data.list.left;
				}
				if(data.list)
				{
					rightJson=data.list.right;
				}
				var htmll="";
				for(var i=0;i<leftJson.length;i++)
				{
					htmll+=getDeskHtml(leftJson[i].module);
				}
				$("#leftdiv").html(htmll);
				var htmlr="";
				for(var i=0;i<rightJson.length;i++)
				{
					htmlr+=getDeskHtml(rightJson[i].module);
				}
				$("#rightdiv").html(htmlr);
				for(var i=0;i<leftJson.length;i++)
				{
					if(leftJson[i].module=="news")
					{
						getMyNews();
					}else if(leftJson[i].module=="notice")
					{
						getMyNotice();
					}else if(leftJson[i].module=="bpm")
					{
						getMyBpm();
					}else if(leftJson[i].module=="myTask")
					{
						getMyTask();
					}else if(leftJson[i].module=="myMeeting")
					{
						getMyMeeting();
					}else if(leftJson[i].module=="myFile")
					{
						getMyFile();
					}else if(leftJson[i].module=="document")
					{
						getMyDocument();
					}
						
				}
				for(var i=0;i<rightJson.length;i++)
				{
						if(rightJson[i].module=="news")
						{
							getMyNews();
						}else if(rightJson[i].module=="notice")
						{
							getMyNotice();
						}else if(rightJson[i].module=="bpm")
						{
							getMyBpm();
						}else if(rightJson[i].module=="myTask")
						{
							getMyTask();
						}else if(rightJson[i].module=="myMeeting")
						{
							getMyMeeting();
						}else if(rightJson[i].module=="myFile")
						{
							getMyFile();
						}else if(rightJson[i].module=="document")
						{
							getMyDocument();
						}
				}
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

function getMyMeeting()
{
	$.ajax({
		url : "/ret/meetingget/getMyMeetingListForDesk",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var meetingList = data.list;
				var html="";
				for(var i=0;i<meetingList.length;i++)
				{
					html+="<tr><td>"+meetingList[i].subject+"【"+meetingList[i].name+"】"
					html+="</td><td style='width:150px'>"+meetingList[i].beginTime+"</td><td style='width:150px'>"+meetingList[i].endTime+"</td><td style='width:50px'><a href=\"javascript:void(0);readMeeting('"+meetingList[i].meetingId+"')\">查看</a></td></tr>";
				}
				$("#meetingbody").html(html);
				
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function getMyFile()
{
	$.ajax({
		url : "/ret/fileget/getMyPublicFolderInPrivForDesk",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var fileList = data.list;
				var html="";
				for(var i=0;i<fileList.length;i++)
				{
					html+="<tr><td>"+fileList[i].folderName;
					html+="</td><td style='width:50px'><a href=\"javascript:void(0);gopublicfile('"+fileList[i].folderId+"')\">浏览</a></td></tr>";
				}
				$("#filebody").html(html);
				
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function gopublicfile(folderId)
{
	var url = "/app/core/file/publicfile?folderId="+folderId;
	top.openNewTabs(url,"公共文件柜");
}

function getMyBpm() {
	$.ajax({
		url : "/ret/bpmget/getMyProcessListForDesk",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var bpmList = data.list;
				var html="";
				for(var i=0;i<bpmList.length;i++)
				{
					html+="<tr><td>"+bpmList[i].flowTitle+"&nbsp;&nbsp;【"+bpmList[i].prcsName+"】</td><td style='width:150px'>"+bpmList[i].createTime+"</td><td style='width:80px'>"+bpmList[i].createUser+"</td><td style='width:50px'><a href=\"javascript:void(0);doBpm('"+bpmList[i].runId+"','"+bpmList[i].runProcessId+"','"+bpmList[i].flowTitle+"')\">办理</a></td></tr>";
				}
				$("#bpmtbody").html(html);
				
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function getMyDocument() {
	$.ajax({
		url : "/ret/documentget/getMyProcessListForDesk",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var bpmList = data.list;
				var html="";
				for(var i=0;i<bpmList.length;i++)
				{
					html+="<tr><td>"+bpmList[i].flowTitle+"&nbsp;&nbsp;【"+bpmList[i].prcsName+"】</td><td style='width:150px'>"+bpmList[i].createTime+"</td><td style='width:80px'>"+bpmList[i].createUser+"</td><td style='width:50px'><a href=\"javascript:void(0);doDocument('"+bpmList[i].runId+"','"+bpmList[i].runProcessId+"','"+bpmList[i].flowTitle+"')\">办理</a></td></tr>";
				}
				$("#documentbody").html(html);
				
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function getMyEmail() {
	
	$.ajax({
		url : "/ret/oaget/getEmailListForDesk",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var datalist = data.list;
				var html="";
				for(var i=0;i<datalist.length;i++)
				{
					html+="<tr><td>"+datalist[i].subject;
					if(datalist[i].readTime==null)
					{
						html+="<span style='color:#fb6e52;margin-left:10px;'><i class='fa fa-diamond'></i></span>";
					}
					html+="</td><td style='width:150px'>"+datalist[i].sendTime+"</td><td style='width:80px'>"+datalist[i].fromUserName+"</td><td style='width:50px'><a href=\"javascript:void(0);readEmail('"+datalist[i].emailId+"')\">查看</a></td></tr>";
				}
				$("#emailtbody").html(html);
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function getMyCalendar() {
	
	$.ajax({
		url : "/ret/oaget/getMyCalendarListForDesk",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var datalist = data.list;
				var html="";
				for(var i=0;i<datalist.length;i++)
				{
					html+="<tr><td>"+datalist[i].title;
					html+="</td><td style='width:150px'>"+datalist[i].calendarStart+"</td><td style='width:80px'>"+datalist[i].createUserName+"</td></tr>";
				}
				$("#calendartbody").html(html);
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function getMyTask() {
	$.ajax({
		url : "/ret/taskget/getTaskListForDesk2",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var taskList = data.list;
				var html="";
				for(var i=0;i<taskList.length;i++)
				{
					html+="<tr><td>"+taskList[i].text+"&nbsp;&nbsp;【"+taskList[i].taskName+"】</td><td style='width:150px'>"+taskList[i].startDate+"</td><td style='width:80px'>进度："+(taskList[i].progress*100)+"%</td><td style='width:50px'><a href=\"javascript:void(0);doTask('"+taskList[i].taskDataId+"')\">查看</a></td></tr>";
				}
				$("#mytaskbody").html(html);
				
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}
function getMyNews() {
	$.ajax({
		url : "/ret/oaget/getMyNewsListForDesk",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var newsList = data.list;
				var html="";
				for(var i=0;i<newsList.length;i++)
				{
					html+="<tr><td>"+newsList[i].newsTitle;
					if(newsList[i].readStatus=="true")
					{
						html+="&nbsp;&nbsp;<span class=\"label label-darkorange\">new</span>";
					}
					html+="</td><td style='width:100px'>"+newsList[i].sendTime+"</td><td style='width:80px'>"+newsList[i].createUser+"</td><td style='width:50px'><a href=\"javascript:void(0);readNews('"+newsList[i].newsId+"')\">查看</a></td></tr>";
				}
				$("#newstbody").html(html);
				
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function getMyNotice() {
	$.ajax({
		url : "/ret/noticeget/getMyNoticeListForDesk",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var noticeList = data.list;
				var html="";
				for(var i=0;i<noticeList.length;i++)
				{
					html+="<tr><td>"+noticeList[i].noticeTitle
					if(noticeList[i].readStatus=="true")
					{
						html+="&nbsp;&nbsp;<span class=\"label label-darkorange\">new</span>";
					}
					html+="</td><td style='width:100px'>"+noticeList[i].sendTime+"</td><td style='width:80px'>"+noticeList[i].createUser+"</td><td style='width:50px'><a href=\"javascript:void(0);readNotice('"+noticeList[i].noticeId+"')\">查看</a></td></tr>";
				}
				$("#noticetbody").html(html);
				
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function readEmail(eamilId)
{
		top.openNewTabs("/app/core/oa/emaildetails?emailId="+eamilId,"电子邮件");
}

function readNews(newsId)
{
	window.open("/app/core/news/readnews?newsId="+newsId);
}
function doBpm(runId,runProcessId,title)
{
	top.openNewTabs("/app/core/bpm/dowork?runId="+runId+"&runProcessId="+runProcessId,title);
}
function doTask (taskDataId)
{
	window.open("/app/core/task/taskdatadetails?taskDataId="+taskDataId);
}
function doDocument(runId,runProcessId,title)
{
	top.openNewTabs("/app/core/document/dowork?runId="+runId+"&runProcessId="+runProcessId,title);
}
function readNotice(noticeId)
{
	window.open("/app/core/notice/details?noticeId="+noticeId);
}

function getDeskHtml(module)
{
	if(module=="news")
	{
		return myNews;
	}else if(module=="notice")
	{
		return myNotice;
	}else if(module=="calendar")
	{
		return "";
	}else if(module=="email")
	{
		return "";
	}else if(module=="superversion")
	{
		return "";
	}else if(module=="sms")
	{
		return "";
	}else if(module=="myfile")
	{
		return "";
	}else if(module=="bpm")
	{
		return bpmHtml;
	}else if(module=="myTask")
	{
		return myTask;
	}else if(module=="myMeeting")
	{
		return myMeeting;
	}else if(module=="myFile")
	{
		return myFile;
	}else if(module=="document")
	{
		return documentHtml;
	}
}

function getDeskConfigJson()
{
	var lArr=[];
	$("#leftdiv").find("div").each(function(){
		console.log($(this).attr("module"))
		
	});
	$("#rigthdiv").find("div").each(function(){
		console.log($(this).attr("module"))
		
	});
}

function getMyShortcutMenu()
{
	$.ajax({
		url : "/ret/sysget/getMyShortcutMenu",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				if(data.list)
				{
				var config = JSON.parse(data.list.config);
				if(config)
				{
					var html="";
					for(var i=0;i<config.length;i++)
					{
						if(config[i].url!=''&&config[i].name!=''&&config[i].status=="1")
						{
							html+='<a onclick="top.openNewTabs(\''+config[i].url+'\',\''+config[i].name+'\');" class="col-lg-4 col-sm-4 col-xs-4" style="height: auto; text-align: center;cursor: pointer;"><img style="height: 52px" src="'+config[i].img+'" title="'+config[i].name+'"></a>';
						}
					}
					if(html!="")
					{
						$("#shortcut").empty();
						$("#shortcut").append(html);
					}
					$("#shortcut").show();
				}else
				{
					$("#shortcut").show();
				}
				
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
			}
		}
	});
}



var bpmHtml=['<div class="portlet widget radius-bordered" style="margin-bottom: 10px" module="bpm" module="待办工作">',
	'						<div class="portlet-header widget-header">',
	'							<span class="portlet widget-caption">待办工作</span>',
	'							<div class="portlet-content widget-buttons">',
	'								<a href="#" data-toggle="maximize"> <i class="fa fa-expand blue"></i>',
	'								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus danger"></i>',
	'								</a>',
	'							</div>',
	'						</div>',
	'						<div class="portlet-content widget-body" style="padding:0px;height: 190px;">',
	'							<table class="table table-hover">',
	'                                    <tbody id="bpmtbody">',
	'                                    </tbody>',
	'                                </table>',
	'						</div>',
	'					</div>'].join("");
var documentHtml=['<div class="portlet widget radius-bordered" style="margin-bottom: 10px" module="bpm" module="待办工作">',
	'						<div class="portlet-header widget-header">',
	'							<span class="portlet widget-caption">我的收发文</span>',
	'							<div class="portlet-content widget-buttons">',
	'								<a href="#" data-toggle="maximize"> <i class="fa fa-expand blue"></i>',
	'								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus danger"></i>',
	'								</a>',
	'							</div>',
	'						</div>',
	'						<div class="portlet-content widget-body" style="padding:0px;height: 190px;">',
	'							<table class="table table-hover">',
	'                                    <tbody id="documentbody">',
	'                                    </tbody>',
	'                                </table>',
	'						</div>',
	'					</div>'].join("");
var myTask=['<div class="portlet widget radius-bordered" style="margin-bottom: 10px" module="myTask" module="我的任务">',
	'						<div class="portlet-header widget-header">',
	'							<span class="portlet widget-caption">我的任务</span>',
	'							<div class="portlet-content widget-buttons">',
	'								<a href="#" data-toggle="maximize"> <i class="fa fa-expand blue"></i>',
	'								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus danger"></i>',
	'								</a>',
	'							</div>',
	'						</div>',
	'						<div class="portlet-content widget-body" style="padding:0px;height: 190px;">',
	'								<table class="table table-hover">',
	'                                    <tbody id="mytaskbody">',
	'                                    </tbody>',
	'                                </table>',
	'						</div>',
	'					</div>'].join("");
var myNews=['<div class="portlet widget radius-bordered" style="margin-bottom: 10px" module="myNews" module="公司新闻">',
	'						<div class="portlet-header widget-header">',
	'							<span class="portlet widget-caption">公司新闻</span>',
	'							<div class="portlet-content widget-buttons">',
	'								<a href="#" data-toggle="maximize"> <i class="fa fa-expand blue"></i>',
	'								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus danger"></i>',
	'								</a>',
	'							</div>',
	'						</div>',
	'						<div class="portlet-content widget-body" style="padding:0px;height: 190px;">',
	'							<table class="table table-hover">',
	'                                    <tbody id="newstbody">',
	'                                        ',
	'                                    </tbody>',
	'                                </table>',
	'						</div>',
	'					</div>'].join("");
var myNotice=['<div class="portlet widget radius-bordered" style="margin-bottom: 10px;" module="notice" module="通知公告">',
	'						<div class="portlet-header widget-header">',
	'							<span class="portlet widget-caption">通知公告</span>',
	'							<div class="portlet-content widget-buttons">',
	'								<a href="#" data-toggle="maximize"> <i class="fa fa-expand blue"></i>',
	'								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus danger"></i>',
	'								</a>',
	'							</div>',
	'						</div>',
	'						<div class="portlet-content widget-body" style="padding:0px;height: 190px;">',
	'							<table class="table table-hover">',
	'                                    <tbody id="noticetbody">',
	'                                        ',
	'                                    </tbody>',
	'                                </table>',
	'						</div>',
	'					</div>'].join("");
var myMeeting=['<div class="portlet widget radius-bordered" style="margin-bottom: 10px;" module="notice" module="我的会议">',
	'						<div class="portlet-header widget-header">',
	'							<span class="portlet widget-caption">我的会议</span>',
	'							<div class="portlet-content widget-buttons">',
	'								<a href="#" data-toggle="maximize"> <i class="fa fa-expand blue"></i>',
	'								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus danger"></i>',
	'								</a>',
	'							</div>',
	'						</div>',
	'						<div class="portlet-content widget-body" style="padding:0px;height: 190px;">',
	'							<table class="table table-hover">',
	'                                    <tbody id="meetingbody">',
	'                                        ',
	'                                    </tbody>',
	'                                </table>',
	'						</div>',
	'					</div>'].join("");

var myFile=['<div class="portlet widget radius-bordered" style="margin-bottom: 10px;" module="notice" module="我的文件">',
	'						<div class="portlet-header widget-header">',
	'							<span class="portlet widget-caption">我的文件</span>',
	'							<div class="portlet-content widget-buttons">',
	'								<a href="#" data-toggle="maximize"> <i class="fa fa-expand blue"></i>',
	'								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus danger"></i>',
	'								</a>',
	'							</div>',
	'						</div>',
	'						<div class="portlet-content widget-body" style="padding:0px;height: 190px;">',
	'							<table class="table table-hover">',
	'                                    <tbody id="filebody">',
	'                                        ',
	'                                    </tbody>',
	'                                </table>',
	'						</div>',
	'					</div>'].join("");