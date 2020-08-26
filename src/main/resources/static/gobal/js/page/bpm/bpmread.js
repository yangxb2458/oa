$(function() {
	$("#formdatacheckbox").unbind("change").change(function() {
		var flag = $(this).is(':checked');
		if(flag)
			{
				$("#formdata").show();
			}else
			{
				$("#formdata").hide();
			}
	});
	$("#ideacheckbox").unbind("change").change(function() {
		var flag = $(this).is(':checked');
		if(flag)
			{
				$("#idea").show();
			}else
			{
				$("#idea").hide();
			}
	});
	$("#publicfilecheckbox").unbind("change").change(function() {
		var flag = $(this).is(':checked');
		if(flag)
			{
				$("#pfile").show();
			}else
			{
				$("#pfile").hide();
			}
	});
	$("#flowlogcheckbox").unbind("change").change(function() {
		var flag = $(this).is(':checked');
		if(flag)
			{
				$("#flowlog").show();
			}else
			{
				$("#flowlog").hide();
			}
	});
	$(".js-sendtoemail").unbind("click").click(function(){
		window.open("/app/core/oa/email?view=sendemail?runId="+runId+"&flowId="+flowId);
	})
	
	$(".js-sendtonotice").unbind("click").click(function(){
		window.open("/app/core/notice/index?runId="+runId+"&flowId="+flowId);
	})
	$.ajax({
		url : '/ret/bpmget/getBpmPrintData',
		type : "post",
		dataType : "json",
		data : {
			flowId : flowId,
			runId : runId
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				if(data.list.bpmFlow.printFlag=="1")
				{
					$(".js-printbtn").show();
				}
				$("#flowTitle").html(data.list.bpmList.flowTitle);
				$("#runNo").html("NO:" + data.list.bpmList.id);
				$("#runcreatetime").html("创建时间:" + data.list.bpmList.createTime);
				$("#publicfile").attr("data_value", data.list.bpmList.attach);
				var printFieldPriv = ","+data.list.bpmFlow.printField+",";
				if(data.list.bpmFlow.attachPriv=="1")
				{
					createAttach("publicfile", 2);
				}else
				{
					createAttach("publicfile", 1);
				}
				$("#formdata").html(data.list.bpmFormHtml);
				createApproval(data.list.allBpmRunProcess);
				createrunProcessList(data.list.allBpmRunProcess);
				$("[xtype='xupload']").each(function() {
					if($(this).attr("data_value")!=''&&$(this).attr("data_value")!=undefined)
					{
						var eName = $(this).attr("name");
						createAttach(eName+"attach", 1);
					}
				});
				
				$("[xtype='xlist']").each(function() {
					var eName = $(this).attr("name");
					if(printFieldPriv.indexOf("," + eName + ",") < 0)
						{
						 	initPrintChileTable(eName,this)
						}else
						{
							$(this).remove();
						}
				});
			}
		}
	})
	$(".js-printbtn").unbind("click").click(function(){
		printbpm();
	});
	$(".js-newwindow").unbind("click").click(function(){
		opennewwindow();
	});
});


function createrunProcessList(runPrcsDataList) {
	var html = "";
	for (var i = 0; i < runPrcsDataList.length; i++) {
		var statusText = "同意";
		if (runPrcsDataList[i].passStatus == "0") {
			statusText = "不同意";
		}
		html += "<tr><td>" + (i + 1) + "</td><td>" + runPrcsDataList[i].prcsName + "</td><td>" + statusText + "</td><td>" + runPrcsDataList[i].createTime + "</td><td>"
				+ runPrcsDataList[i].recTime + "</td><td>" + (runPrcsDataList[i].endTime==undefined?'': runPrcsDataList[i].endTime)+"</td><td>" + runPrcsDataList[i].userName + "</td></tr>"
	}
	$("#runProcessList").html(html);
}

function createApproval(runPrcsDataList) {
	for (var i = 0; i < runPrcsDataList.length; i++) {
		var passStatus = '<div class="ticket-state bg-palegreen"><i class="fa fa-check"></i></div>';
		if (runPrcsDataList[i].passStatus == "0") {
			passStatus = '<div class="ticket-state bg-darkorange"><i class="fa fa-times"></i></div>';
		}
		var el = [
				'<li class="ticket-item">',
				'    <div class="row">',
				'        <div class="ticket-user col-lg-3 col-sm-12">',
				'            <img src="/sys/file/getOtherHeadImg?headImg=' + runPrcsDataList[i].headImg + '" class="user-avatar">',
				'            <span class="user-name">' + runPrcsDataList[i].userName + '</span>',
				'            <span class="user-at">at</span>',
				'            <span class="user-company">' + runPrcsDataList[i].prcsName + '</span>',
				'        </div>',
				'        <div class="ticket-time  col-lg-3 col-sm-6 col-xs-12">',
				'            <div class="divider hidden-md hidden-sm hidden-xs"></div>',
				'            <i class="fa fa-clock-o"></i>',
				'            <span class="time">' + (runPrcsDataList[i].endTime==undefined?'未办结': runPrcsDataList[i].endTime)+'</span>',
				'        </div>',
				'        <div class="ticket-type  col-lg-6 col-sm-6 col-xs-12" style="height:auto">',
				'            <span class="divider hidden-xs"></span>',
				'            <span class="type">' + (runPrcsDataList[i].ideaText==undefined?'暂无': runPrcsDataList[i].ideaText) + '<span id="show_' + runPrcsDataList[i].runProcessId + '"></span><span id="'
						+ runPrcsDataList[i].runProcessId + '"></span></span>', '        </div>' + passStatus, '    </div>', '</li>' ].join("");
		$("#ideaList").append(el);
		$("#" + runPrcsDataList[i].runProcessId).attr("data_value", runPrcsDataList[i].attach);
		createAttach(runPrcsDataList[i].runProcessId, 3);
	}
}

function printbpm()
{
  $("#bpmformdata").jqprint();
//      $("#bpmformdata").jqprint({
//	                 debug: true,  //是否显示iframe查看效果
//	                 importCSS: true,
//	                 printContainer: true,
//	                 operaSupport: false
//	              });
}

function opennewwindow()
{
	window.open("/app/core/bpm/bpmread?runId=" + runId + "&flowId=" + flowId);
}


function initPrintChileTable(eName,Obj)
{
	var model = JSON.parse($(Obj).attr("model"));
		var tableFieldArr = [];
		for (var i = 0; i < model.length; i++) {
			var fieldJson = {};
			fieldJson.field = model[i].childName;
			fieldJson.title = model[i].childTitle;
			fieldJson.width = 200;
			tableFieldArr.push(fieldJson);
		}
		createPrintChildTable(eName, tableFieldArr);
}

function createPrintChildTable(eName, tableFields) {
	$("table[name='" + eName + "']").bootstrapTable({
		url : '/ret/bpmget/getBpmChileTableData?eName='+eName,
		method : 'post',
		contentType : 'application/x-www-form-urlencoded',
		striped : true, // 隔行换色
		pagination : true, // 启动分页
		sidePagination : 'server', // 分页方式
		idField : 'UNIQUE_ID', // key值栏位
		queryParams : queryPrintParamsChildTable,
		columns : tableFields,
		responseHandler : function(res) {
			if (res.status == "500") {
				console.log(res.msg);
				top.layer.msg(res.msg);
			} else {
				return {
					total : res.list.total, // 总页数,前面的key必须为"total"
					rows : res.list.list// 行数据，前面的key要与之前设置的dataField的值一致.
				};
			}
		}
	});
}
function queryPrintParamsChildTable(params) {
	var temp = {
		runId : runId
	};
	return temp;
};
