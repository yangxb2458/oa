$(function() {
	$(".js-sendtoemail").unbind("click").click(function(){
		window.open("/app/core/oa/email?view=sendemail?runId="+runId+"&flowId="+flowId);
	})
	
	$(".js-sendtonotice").unbind("click").click(function(){
		window.open("/app/core/notice/index?runId="+runId+"&flowId="+flowId);
	})
	$.ajax({
		url : '/ret/documentget/getDocumentPrintData',
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
				$("#flowTitle").html(data.list.documentList.flowTitle);
				$("#runNo").html("NO:" + data.list.documentList.id);
				$("#runcreatetime").html("创建时间:" + data.list.documentList.createTime);
				$("#publicfile").attr("data_value", data.list.documentList.attach);
				var printFieldPriv = ","+data.list.documentFlow.printField+",";
				createAttach("publicfile", 1);
				$("#formdata").html(data.list.documentFormHtml);
				createApproval(data.list.allDocumentRunProcess);
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
});
function createApproval(runPrcsDataList) {
	for (var i = 0; i < runPrcsDataList.length; i++) {
		var passStatus = '<div class="ticket-state bg-palegreen" style="right: 0px;"><i class="fa fa-check"></i></div>';
		if (runPrcsDataList[i].passStatus == "0") {
			passStatus = '<div class="ticket-state bg-darkorange" style="right: 0px;"><i class="fa fa-times"></i></div>';
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
		url : '/ret/documentget/getDocumentChileTableData?eName='+eName,
		method : 'post',
		cardView:true,
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
