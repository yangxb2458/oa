$(function(){
	$(".js-btn").unbind("click").click(function(){
		$("#groupmodal").modal("show");
		$(".js-save").unbind("click").click(function(){
			addUserGroup();
		})
	})
	getMyUserGroup();
})
function addUserGroup()
{
	if($("#groupTitle").val()=="")
	{
		top.layer.msg("分组名称不能为空！");
	}else
	{
		var groupUsers = $("#groupUsers").attr("data-value");
		if(groupUsers=="@all")
		{
			top.layer.msg("用户分组成员不能为全体人员！")
		}else if(groupUsers==""||groupUsers==undefined)
		{
			top.layer.msg("用户分组成员不能为空！")
		}else
		{
			$.ajax({
				url : "/set/unitset/insertUserGroup",
				type : "post",
				dataType : "json",
				data:{
					groupTitle:$("#groupTitle").val(),
					groupUsers:$("#groupUsers").attr("data-value")
				},
				success : function(data) {
					if(data.status=="500")
					{
						console.log(data.msg);
					}else if(data.status=="100")
					{
						top.layer.msg(data.msg);
					}else
					{
						top.layer.msg(data.msg);
						location.reload();
					}
				}
			})
		}
	}
}

function getMyUserGroup()
{
	$.ajax({
		url : "/ret/unitget/getMyUserGroup",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="500")
			{
				console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				var recordList = data.list;
				for(var i=0;i<recordList.length;i++)
				{
					var html="<tr>";
					html+="<td>"+(i+1)+"</td>";
					html+="<td>"+recordList[i].groupTitle+"</td>";
					html+="<td>"+getUserNameByStr(recordList[i].groupUsers)+"</td>";
					html+="<td><a href=\"javascript:void(0);editgroup('"+recordList[i].groupId+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;<a href=\"javascript:void(0);deletegroup('"+recordList[i].groupId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a></td>";
					html+="</tr>";
					$("#grouplist").append(html);
				}
				
			}
		}
	})
}

function editgroup(groupId)
{
$("#groupmodal").modal("show");
$.ajax({
	url : "/ret/unitget/getUserGroupById",
	type : "post",
	dataType : "json",
	data:{groupId:groupId},
	success : function(data) {
		if(data.status=="500")
		{
			console.log(data.msg);
		}else if(data.status=="100")
		{
			top.layer.msg(data.msg);
		}else
		{
			var record = data.list;
			$("#groupTitle").val(record.groupTitle);
			$("#groupUsers").attr("data-value",record.groupUsers);
			$("#groupUsers").val(getUserNameByStr(record.groupUsers));
		}
	}
})

$(".js-save").unbind("click").click(function(){
	updateUserGroup(groupId);
})
}

function updateUserGroup(groupId)
{
	if($("#groupTitle").val()=="")
	{
		top.layer.msg("分组名称不能为空！");
	}else
	{
		var groupUsers = $("#groupUsers").attr("data-value");
		if(groupUsers=="@all")
		{
			top.layer.msg("用户分组成员不能为全体人员！")
		}else if(groupUsers==""||groupUsers==undefined)
		{
			top.layer.msg("用户分组成员不能为空！")
		}else
		{
			$.ajax({
				url : "/set/unitset/updateUserGroup",
				type : "post",
				dataType : "json",
				data:{
					groupId:groupId,
					groupTitle:$("#groupTitle").val(),
					groupUsers:$("#groupUsers").attr("data-value")
				},
				success : function(data) {
					if(data.status=="500")
					{
						console.log(data.msg);
					}else if(data.status=="100")
					{
						top.layer.msg(data.msg);
					}else
					{
						top.layer.msg(data.msg);
						location.reload();
					}
				}
			})
		}
	}
}



function deletegroup(groupId)
{
	if(confirm("确定删除当前分组吗？"))
    {
	$.ajax({
		url : "/set/unitset/deleteUserGroup",
		type : "post",
		dataType : "json",
		data:{
			groupId:groupId
		},
		success : function(data) {
			if(data.status=="500")
			{
				console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				top.layer.msg(data.msg);
				location.reload();
			}
		}
	})
    }
}