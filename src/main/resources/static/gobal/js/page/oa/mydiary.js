var commStatus="";
var template="";
$(function() {
	getSmsConfig("msgType", "diary");
	getCodeClass("diaryType", "diary");
	$('#content').summernote({
		height : 300
	});
	getDiaryPriv();
	$("#createbut").unbind("click").click(function() {
		sendDiary();
	});
	getMySubordinates();

//  日历插件
 $('#calendar').calendar({
     width: 250,
     height: 160,
     onSelected: function (view, date) {
		var v=　date.getFullYear() + '-' + getNow((date.getMonth() + 1)) + '-' + getNow(date.getDate());
		mydiaryquery(v);
    	 $("#mydiarydiv").show();
    	 $("#creatediarydiv").hide();
     }
 });
 $(".js-create-but").unbind("click").click(function(){
	document.getElementById("form").reset();
	$("#form").bootstrapValidator('resetForm'); //重置
	$("#diaryDay").val(getSysDate());
	$("#userPriv").attr("data-value","");
	$("#deptPriv").attr("data-value","");
	$("#leavePriv").attr("data-value","");
	$("#diaryattach").attr("data_value","");
	$("#show_diaryattach").html("");
	$("#content").code(template);
	$("#creatediarydiv").show();
	$("#mydiarydiv").hide();
	$("#createbut").show();
	$("#updatebut").hide();
 })
 $(".js-mydiary").unbind("click").click(function(){
	 $("#creatediarydiv").hide();
	 $("#mydiarydiv").show();
	 mydiaryquery("");
 })
 mydiaryquery("");
 
});

function getMySubordinates() {
	$.ajax({
		url : "/ret/sysget/getMySubordinates",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == "200") {
				var html = "";
				for (var i = 0; i < data.list.length; i++) {
					html += "<div class=\"databox databox-graded\" style=\"margin-bottom:2px\">\n" 
							+ "<div class=\"databox-left no-padding\">\n"
							+ "<img src=\"/sys/file/getOtherHeadImg?headImg="+data.list[i].headImg
							+ "\" style=\"width:65px; height:65px;\" onerror=\"this.onerror=null;this.src='/assets/img/avatars/adam-jansen.jpg'\">\n"
							+ "</div>\n"
							+ "<div class=\"databox-right padding-top-20\">\n"
							+ "<div class=\"databox-stat yellow radius-bordered\">\n"
							+ "<a href=\"javascript:window.open('/app/core/oa/email?view=sendemail&touser="
							+ data.list[i].accountId
							+ "&userName="
							+ data.list[i].userName
							+ "');\"><i class=\"stat-icon fa fa-envelope-o\"></i></a>\n"
							+ "</div>\n"
							+ "<div class=\"databox-text darkgray\">"
							+ data.list[i].userName
							+ "</div>\n"
							+ "<div class=\"databox-text darkgray\"><span>"
							+ data.list[i].leadLevelName
							+ "</span><span style=\"margin-left:40px;float:right;cursor: pointer;\">"
							+ "<a href=\"/app/core/diary/otherdiarylist?accountId="
							+ data.list[i].accountId + "\">查看最近</a></span></div>\n" + "</div>\n" + "</div>";
				}
				$("#registration-form").html(html);
			} else if (data.status == "100") {

			} else {
				console.log(data);
			}
		}
	});
}
function getDiaryPriv() {
	$.ajax({
		url : "/ret/oaget/getDiaryPriv",
		type : "post",
		dataType : "json",
		async : false,
		success : function(data) {
			commStatus = data.list.commStatus;
			var lockDay = data.list.lockDay;
			var d = new Date(); // today!
			d.setDate(d.getDate() - lockDay);
			var v=　d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate();
			jeDate("#diaryDay", {
				isinitVal: true,
				minDate:v+" 00:00:00",
				format : "YYYY-MM-DD",
				clearfun:function(elem, val) {
			         $("#form").data('bootstrapValidator').updateStatus('diaryDay', 'NOT_VALIDATED').validateField('diaryDay');
			       },
			       donefun:function(obj) {
			         $("#form").data('bootstrapValidator').updateStatus('diaryDay', 'NOT_VALIDATED').validateField('diaryDay');
			       },
				});
			if (data.status == "200") {
				template = data.list.template;
				$("#content").code(data.list.template);
				if (data.list.shareStatus == "2") {
					var html = [ '<label class="col-sm-1 control-label no-padding-right">分享范围：</label>', '						<div class="col-sm-5" style="padding-left: 0px">',
							'							<div class="col-md-10">', '								<textarea id="userPriv" name="userPriv" placeholder="用户"',
							'									class="form-control" readonly="readonly"></textarea>', '							</div>', '							<div class="col-md-2">',
							'								<a class="selectbutton" opt-id="userPriv"', '									onclick="selectUser(this,\'true\');">选择</a>', '							</div>',
							'							<div class="col-md-10" style="margin-top: 5px">', '								<textarea id="deptPriv" name="deptPriv" placeholder="部门"',
							'									class="form-control" readonly="readonly"></textarea>', '							</div>', '							<div class="col-md-2">',
							'								<a class="selectbutton" opt-id="deptPriv"', '									onclick="selectDept(this,\'true\');">选择</a>', '							</div>',
							'							<div class="col-md-10" style="margin-top: 5px">', '								<textarea id="leavePriv" name="leavePriv" placeholder="行政级别"',
							'									class="form-control" readonly="readonly"></textarea>', '							</div>', '							<div class="col-md-2">',
							'								<a class="selectbutton" opt-id="leavePriv"', '									onclick="selectUserLevel(this,\'true\');">选择</a>', '							</div>', '						</div>' ]
							.join("");
					$("#sharediv").html(html);
				}
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}




function mydiaryquery(diaryDay) {
	$('#myTable').bootstrapTable('destroy'); 
	$("#myTable").bootstrapTable({
		url : '/ret/oaget/getMyDiaryList?diaryDay='+diaryDay,
		method : 'post',
		contentType : 'application/x-www-form-urlencoded',
		toolbar : '#toobar',//工具列
		striped : true,//隔行换色
		cache : false,//禁用缓存
		pagination : true,//启动分页
		sidePagination : 'server',//分页方式
		pageNumber : 1,//初始化table时显示的页码
		pageSize : 10,//每页条目
		showFooter : false,//是否显示列脚
		showPaginationSwitch : false,//是否显示 数据条数选择框
		sortable : true,//排序
		sortOrder:'desc',
		search : true,//启用搜索
		showColumns : false,//是否显示 内容列下拉框
		showRefresh : false,//显示刷新按钮
		idField : 'diaryId',//key值栏位
		clickToSelect : false,//点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],//可选择单页记录数
		queryParams : myqueryParams,
		columns : [{
			formatter : function(value, row, index) {
				var userInfo =getUserInfoDeatilsByAccountId(row.createUser);
				var tmp=['<div>',
					'					<div class="dairyhead col-sm-2">',
					'						<img src="/sys/file/getOtherHeadImg?headImg='+userInfo.headImg+'" onerror="this.onerror=null;this.src=\'/assets/img/avatars/adam-jansen.jpg\'" style="width: 80px">',
					'					</div>',
					'					<div class="diarycontext col-sm-8">',
					'						<div class="userinfo">',
					'							<a style="cursor: pointer;text-decoration:none;"><span class="userinfo_sapn">'+userInfo.userName+'</span><span class="userinfo_sapn">'+userInfo.deptName+'</span><span class="userinfo_sapn">'+userInfo.leaveName+'</span></a>',
					'						</div>',
					'						<div><h4>【'+row.diaryDay+'】'+row.title+'</h4></div>',
					'						<p>'+row.content+'</p><div id="show_diaryattach_'+row.diaryId+'"></div><div class="diaryattach" priv="'+row.attachPriv+'" id="diaryattach_'+row.diaryId+'" data_value="'+row.attach+'"></div>',
					'					</div>',
					'					<div class="diaryright col-sm-2">'+row.createTime+'</div>',
					'					<div class="col-sm-12">'+getCommentsList(row.diaryId)+'</div>',
					'					<div class="diaryfoot col-sm-12">',
					'						<a onclick="editDiary(\''+row.diaryId+'\')">编辑</a><a onclick="delDiary(\''+row.diaryId+'\')">删除</a>',
					'					</div>',
					'				</div>'].join("");
				return tmp;
			}
		}],
		onClickCell : function(field, value, row, $element) {
			//alert(row.SystemDesc);
		},
		onLoadSuccess: function(data){
			$(".diaryattach").each(function(){
				var attachPriv=$(this).attr("priv");
				var id = $(this).attr("id");
				createAttach(id,attachPriv);
			})
		},
		responseHandler : function(res) {
			if (res.status == "500") {
				console.log(res.msg);
			} else if (res.status == "100") {
				top.layer.msg(res.msg);
			} else {
				return {
					total : res.list.total, //总页数,前面的key必须为"total"
					rows : res.list.list
				//行数据，前面的key要与之前设置的dataField的值一致.
				};
			}
		}
	});
}
function myqueryParams(params) {
	var temp = {
		search : params.search,
		pageSize : this.pageSize,
		pageNumber : this.pageNumber,
		sort : params.sort,
		sortOrder : params.order
	};
	return temp;
};

function delDiary(diaryId)
{
	if (confirm('您确认要删除您的日志吗？')) {
	$.ajax({
		url : "/set/oaset/delDiary",
		type : "post",
		dataType : "json",
		data : {
			diaryId :diaryId
		},
		success : function(data) {
			if (data.status == "200") {
				top.layer.msg(data.msg);
				 mydiaryquery("");
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
	}
}

function editDiary(diaryId)
{
	document.getElementById("form").reset();
	$("#userPriv").attr("data-value","");
	$("#deptPriv").attr("data-value","");
	$("#leavePriv").attr("data-value","");
	$("#diaryattach").attr("data_value","");
	$("#show_diaryattach").html("");
	$("#content").code("");
	$("#form").bootstrapValidator('resetForm'); //重置
	$.ajax({
		url : "/ret/oaget/getDiaryById",
		type : "post",
		dataType : "json",
		data : {
			diaryId :diaryId
		},
		success : function(data) {
			console.log(data.list);
			if (data.status == "200") {
				for(var id in data.list)
				{
					if(id=="attach")
					{
						$("#diaryattach").attr("data_value", data.list[id]);
						createAttach("diaryattach",4);
					}else if(id=="userPriv")
					{
						$("#userPriv").val(getUserNameByStr(data.list.userPriv));
						$("#userPriv").attr("data-value",data.list.userPriv);
					}else if(id=="deptPriv")
					{
						$("#deptPriv").val(getDeptNameByDeptIds(data.list.deptPriv));
						$("#deptPriv").attr("data-value",data.list.deptPriv);
					}else if(id=="leavePriv")
					{
						$("#leavePriv").val(getUserLevelStr(data.list.leavePriv));
						$("#leavePriv").attr("data-value",data.list.leavePriv);
					}else if(id=="content")
					{
						$("#content").code(data.list[id]);
					}else if(id=="attachPriv")
					{
						$("input:radio[name='attachPriv'][value='" + data.list[id] + "']").attr("checked", "checked");
					}else
					{
						$("#"+id).val(data.list[id]);
					}
				}
				
				
				$("#mydiarydiv").hide();
				$("#creatediarydiv").show();
				$("#updatebut").unbind("click").click(function(){
					updateDiary(diaryId);
				})
				$("#createbut").hide();
				$("#updatebut").show();
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function updateDiary(diaryId)
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
		$.ajax({
			url : "/set/oaset/updateDiary",
			type : "post",
			dataType : "json",
			data : {
				diaryId:diaryId,
				diaryDay : $("#diaryDay").val(),
				title : $("#title").val(),
				diaryType : $("#diaryType").val(),
				content : $("#content").code(),
				userPriv : $("#userPriv").attr("data-value"),
				deptPriv : $("#deptPriv").attr("data-value"),
				leavePriv : $("#leavePriv").attr("data-value"),
				attach : $("#diaryattach").attr("data_value"),
				attachPriv : getCheckBoxValue("attachPriv"),
				msgType : getCheckBoxValue("msgType")
			},
			success : function(data) {
				if (data.status == "200") {
					top.layer.msg(data.msg);
					window.location.reload();
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		});
	}

}

function sendDiary() {
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/oaset/sendDiary",
		type : "post",
		dataType : "json",
		data : {
			diaryDay : $("#diaryDay").val(),
			title : $("#title").val(),
			diaryType : $("#diaryType").val(),
			content : $("#content").code(),
			userPriv : $("#userPriv").attr("data-value"),
			deptPriv : $("#deptPriv").attr("data-value"),
			leavePriv : $("#leavePriv").attr("data-value"),
			attach : $("#diaryattach").attr("data_value"),
			attachPriv : getCheckBoxValue("attachPriv"),
			msgType : getCheckBoxValue("msgType")
		},
		success : function(data) {
			if (data.status == "200") {
				top.layer.msg(data.msg);
				window.location.reload();
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
	}
} 
function getCommentsList(diaryId) {
	var returnStr="";
	$.ajax({
		url : "/ret/oaget/getDiaryCommentsList",
		type : "post",
		dataType : "json",
		async : false,
		data:{diaryId:diaryId},
		success : function(data) {
			if (data.status == "200") {
				for(var i=0;i<data.list.length;i++)
				{
					var userInfo = getUserInfoDeatilsByAccountId(data.list[i].createUser);
					var template=['<div class="comment">',
						'			<img src="/sys/file/getOtherHeadImg?headImg='+userInfo.headImg+'" alt="" class="comment-avatar">',
						'			<div class="comment-body">',
						'				<div class="comment-text">',
						'					<div class="comment-header">',
						'						<a href="#" title="">'+userInfo.userName+'</a><span>'+getshowtime(data.list[i].createTime)+'</span>',
						'				</div>'+data.list[i].content,
						'			</div>',
						'			</div>',
						'			</div>'].join("");
					returnStr+=template;
				}
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
	return returnStr;
}