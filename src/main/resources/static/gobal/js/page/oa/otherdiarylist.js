var commStatus;
$(function() {
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	getDiaryPriv();
	query();
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
	var userInfo = getUserInfoDeatilsByAccountId(accountId);
	$(".header-avatar").attr("src","/sys/file/getOtherHeadImg?headImg="+userInfo.headImg);
	$(".header-fullname").html(userInfo.userName);
	$(".btn-follow").html(userInfo.leaveName);
	$(".header-information").html(userInfo.sign);
	$(".js-deptName").html("部门："+userInfo.deptName);
	$(".js-sex").html("性别："+userInfo.sex);
	$(".js-lead").html("TA的领导："+getUserNameByStr(userInfo.leadId));
	showDiaryInfo();
})
function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/oaget/getOtherDiaryList',
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
		showPaginationSwitch : true,//是否显示 数据条数选择框
		sortable : true,//排序
		search : true,//启用搜索
		showColumns : true,//是否显示 内容列下拉框
		showRefresh : true,//显示刷新按钮
		idField : 'diaryId',//key值栏位
		clickToSelect : false,//点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],//可选择单页记录数
		queryParams : queryParams,
		columns : [ {
			formatter : function(value, row, index) {
				var userInfo =getUserInfoDeatilsByAccountId(row.createUser);
				var tmp=['<div>',
					'			<div class="dairyhead col-sm-2">',
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
					(commStatus==1?'<a onclick=\'commDiary("'+row.diaryId+'");\'>评论</a>':''),
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
function queryParams(params) {
	var temp = {
		search : params.search,
		pageSize : this.pageSize,
		pageNumber : this.pageNumber,
		sort : params.sort,
		sortOrder : params.order,
		accountId:accountId,
		beginTime:$("#beginTime").val(),
		endTime:$("#endTime").val()
	};
	return temp;
};
function createOptBtn(diaryId) {
	var html = "<a href=\"javascript:void(0);readDiary('" + diaryId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;";
	return html;
}

function readDiary(diaryId) {
	//window.location.href = "/app/core/diary/readdiary?diaryId=" + diaryId;
	open("/app/core/diary/readdiary?diaryId=" + diaryId,"_self");
}

function getDiaryPriv() {
	$.ajax({
		url : "/ret/oaget/getDiaryPriv",
		type : "post",
		dataType : "json",
		async : false,
		success : function(data) {
			commStatus = data.list.commStatus;
			if (data.status == "200") {
				
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function commDiary(diaryId)
{
	$("#commmodal").modal("show");
	$(".js-save").unbind("click").click(function(){
		insertDiaryComments(diaryId)
	});
}
function insertDiaryComments(diaryId) {
	$.ajax({
		url : "/set/oaset/insertDiaryComments",
		type : "post",
		dataType : "json",
		data:{
			diaryId:diaryId,
			content:$("#commContent").val()
			},
		success : function(data) {
			if (data.status == "200") {
				top.layer.msg(data.msg);
				$("#commmodal").modal("hide")
				query();
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
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

function showDiaryInfo() {
	$.ajax({
		url : "/ret/oaget/getMyDiaryInfo",
		type : "post",
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.status == "200") {
				$(".js-diaryCount").html(data.list.diaryCount);
				$(".js-commCount").html(data.list.commCount);
				$(".js-commToMeCount").html(data.list.commToMeCount);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}