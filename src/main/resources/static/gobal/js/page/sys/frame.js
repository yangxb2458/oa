var onlineuserlist;
var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/unitget/getUnitDeptForUserInfoTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "deptId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onExpand: function(event, treeId, treeNode) {
			var deptId = treeNode.deptId;
			if (treeNode.isParent) {
				$.ajax({
					url : "/ret/unitget/getSelectUserByDeptId",
					type : "post",
					data : {
						deptId : deptId
					},
					dataType : "json",
					success : function(data) {
						var appNode=[];
						if(data.list.length>0)
						{
							for(var i=0;i<data.list.length;i++)
							{
								var newnode={};
								newnode.deptId = data.list[i].accountId;
								newnode.deptName = data.list[i].userName;
								newnode.isParent = false;
								if(data.list[i].sex=='男')
								{
									var result= $.inArray(data.list[i].accountId, onlineuserlist);
									if(result>-1)
									{
										newnode.icon = '/gobal/img/org/U01.png';
									}else
									{
										newnode.icon = '/gobal/img/org/U00.png';
									}
								}else if(data.list[i].sex=='女')
								{
									var result= $.inArray(data.list[i].accountId, onlineuserlist);
									if(result>-1)
									{
										newnode.icon = '/gobal/img/org/U11.png';
									}else
									{
										newnode.icon = '/gobal/img/org/U10.png';
									}
								}
								appNode.push(newnode);
								
							}
							zTree.reAsyncChildNodes(treeNode, "refresh");
							zTree.addNodes(treeNode, appNode);
						}
					}
				});
			}
		},
		onClick : zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "deptId",
			pIdKey : "orgLeaveId",
			rootPId : "0"
		},
		key : {
			name : "deptName"
		}
	}
};

function zTreeOnClick(event, treeId, treeNode)
{
	if(treeNode.isParent==false)
		{
		var accountId = treeNode.deptId;
		$("#userInfoModal").modal("show");
		 getHomePageByAccountId(accountId);
		}
}

function getHomePageByAccountId(accountId)
{
	$("#bpmTotal").html("")
	$("#attachTotal").html("")
	$("#diaryTotal").html("")
	$("#headImg").attr("src","")
	$("#leadId").html("");
	$("#deptName").html("");
	$("#leadLevelName").html("");
	$("#birthday").html("");
	$("#sex").html("");
	$("#mobileNo").html("");
	$("#eMail").html("");
	$("#qq").html("");
	$("#wxNo").html("");
	$("#workId").html("");
	$.ajax({
		url : "/ret/unitget/getHomePageByAccountId",
		type : "post",
		dataType : "json",
		data : {accountId:accountId},
		success : function(data) {
			if(data.status=="200")
			{
				$("#bpmTotal").html(data.list.bpmTotal+"条")
				$("#attachTotal").html(data.list.attachTotal+"份")
				$("#diaryTotal").html(data.list.diaryTotal+"份")
				var userInfo = data.list.userInfo;
				$("#headImg").attr("src","/sys/file/getOtherHeadImg?headImg="+userInfo.headImg)
				$("#leadId").html(getUserNameByStr(userInfo.leadId));
				$("#deptName").html(userInfo.deptName);
				$("#leadLevelName").html(userInfo.leadLevelName);
				$("#birthday").html(userInfo.birthday);
				$("#sex").html(userInfo.sex);
				$("#mobileNo").html(userInfo.mobileNo);
				$("#eMail").html(userInfo.eMail);
				$("#qq").html(userInfo.qq);
				$("#wxNo").html(userInfo.wxNo);
				$("#workId").html(userInfo.workId);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	});
	
}

$(function(){
	getTaskListForDesk();
	getNoReadSms();
	$("#imbtn").unbind("click").click(function(e){
		if($("#imUserList").is(":hidden")){
			$("#imUserList").css({
				"height":($(window).height()-45)+"px"
			}).slideDown(200);
			
		}else
			{
			$("#imUserList").hide();
			}
		getUserOnLineCount();
		getUserOnLineList();
	});
	$('#searchInput').unbind('keypress').bind('keypress',function(event){
        if(event.keyCode == "13")    
        {
        	var keywords = $('#searchInput').val();
        	if(keywords!="")
        	{
        		window.open("/app/core/file/indexsearch?keywords="+keywords);
        	}else
        	{
        		top.layer.msg("请先输入全文检索关键词！");
        	}
        }
    });
});

function getTaskListForDesk()
{
	$.ajax({
		url : "/ret/taskget/getTaskListForDesk",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var taskInfo = data.list;
				$("#taskTotal").html(taskInfo.total);
				var html="<li class=\"dropdown-header bordered-darkorange\"><i class=\"fa fa-tasks\">"+taskInfo.total+"(条)任务与我有关</i></li>"
				for(var i=0;i<taskInfo.list.length;i++)
				{
					html+="<li><a href=\"#\">" +
							"<div class=\"clearfix\">" +
								"<span class=\"pull-left\">"+taskInfo.list[i].text+"</span> <span class=\"pull-right\">"+(taskInfo.list[i].progress*100)+"%</span>" +
							"</div>" +
							"<div class=\"progress progress-xs\">" +
								"<div style=\"width: "+(taskInfo.list[i].progress*100)+"%\" class=\"progress-bar\"></div>" +
								"</div> </a>" +
							"</li>";
				}
				html+="<li class=\"dropdown-footer\" style=\"text-align: center\"><a href=\"#\" onclick=\"openNewTabs('/app/core/task/mytask','我的任务');\"> 更多任务 </a></li>";
				$("#tasklist").html(html);
			}else
			{
				console.log(data.msg);
			}
			}
	});
}

$(document).ready(function(){
	if(msgtime>0)
		{
		//setInterval("getNoReadSms()",msgtime); 
		}
	var topNode = [{deptName: orgName,orgLeaveId:'', isParent: "true", deptId: "0",icon:"/gobal/img/org/org.png"}];
	zTree = $.fn.zTree.init($("#tree"), setting, topNode);
	var nodes = zTree.transformToArray(zTree.getNodes());
	zTree.expandNode(nodes[0], true);
}); 

function getUserOnLineCount()
{
	$.ajax({
		url : "/ret/unitget/getUserOnLineCount",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				$(".js-onlinecount").html(data.list);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	});
}
function getUserOnLineList()
{
	$.ajax({
		url : "/ret/unitget/getUserOnLineList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				onlineuserlist=data.list;
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	});
}
