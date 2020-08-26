var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/fileget/getPublicFileFolderTree",// Ajax 获取数据的 URL 地址
		autoParam : ["folderId"],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onClick : zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "folderId",
			pIdKey : "folderLeave",
			rootPId : "0"
		},
		key : {
			name :"folderName"
		}
	}
};
function zTreeOnClick(event, treeId, treeNode)
{
$("#titdiv").hide();
$("#privdiv").show();
$("#folderId").val(treeNode.folderId);
$("#folderName").html("正在为："+treeNode.folderName+" 设置权限");
$.ajax({
	url : "/ret/fileget/getPublicFileFolderById",
	type : "post",
	dataType : "json",
	data:{folderId:treeNode.folderId},
	success : function(data) {
		if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
				{
				console.log(data.msg);
				}else
					{
					$("#accessUser").attr("data-value",data.list.accessUserPriv);
					$("#accessUser").val(getUserNameByStr(data.list.accessUserPriv));
					$("#accessDept").attr("data-value",data.list.accessDeptPriv);
					$("#accessDept").val(getDeptNameByDeptIds(data.list.accessDeptPriv));
					$("#accessLeave").attr("data-value",data.list.accessLeavePriv);
					$("#accessLeave").val(getUserLevelStr(data.list.accessLeavePriv));

					$("#downUser").attr("data-value",data.list.downUserPriv);
					$("#downUser").val(getUserNameByStr(data.list.downUserPriv));
					$("#downDept").attr("data-value",data.list.downDeptPriv);
					$("#downDept").val(getDeptNameByDeptIds(data.list.downDeptPriv));
					$("#downLeave").attr("data-value",data.list.downLeavePriv);
					$("#downLeave").val(getUserLevelStr(data.list.downLeavePriv));
					
					
					$("#manageUser").attr("data-value",data.list.manageUserPriv);
					$("#manageUser").val(getUserNameByStr(data.list.manageUserPriv));
					$("#manageDept").attr("data-value",data.list.manageDeptPriv);
					$("#manageDept").val(getDeptNameByDeptIds(data.list.manageDeptPriv));
					$("#manageLeave").attr("data-value",data.list.manageLeavePriv);
					$("#manageLeave").val(getUserLevelStr(data.list.manageLeavePriv));
					
					
					$("#createUser").attr("data-value",data.list.createUserPriv);
					$("#createUser").val(getUserNameByStr(data.list.createUserPriv));
					$("#createDept").attr("data-value",data.list.createDeptPriv);
					$("#createDept").val(getDeptNameByDeptIds(data.list.createDeptPriv));
					$("#createLeave").attr("data-value",data.list.createLeavePriv);
					$("#createLeave").val(getUserLevelStr(data.list.createLeavePriv));
					
					}
	}
});

}

$(function(){
	$.ajax({
		url : "/ret/fileget/getPublicFileFolderTree",
		type : "post",
		dataType : "json",
		data:{folderId:"0"},
		success : function(data) {
			var topNode = [{folderId:"0",folderName:"根目录",isParent:true}];
			var newTreeNodes=topNode.concat(data);
			zTree = $.fn.zTree.init($("#tree"), setting, newTreeNodes);// 初始化树节点时，添加同步获取的数据
			var nodes = zTree.transformToArray(zTree.getNodes())
			zTree.expandNode(nodes[0], true);
		}
	});
	$("#cbut").unbind("click").click(function(){
		document.getElementById("form1").reset();
		$("#createPublicFolder").modal("show");
		$(".js-save").unbind("click").click(function(){
			$.ajax({
				url : "/set/fileset/createPublicFile",
				type : "post",
				dataType : "json",
				data:{
					sortNo:$("#sortNo").val(),
					folderName:$("#folderName").val(),
					owner:$("#owner").attr("data-value"),
					spaceLimit:$("#spaceLimit").val()
				},
				success : function(data) {
					if(data.status=="500")
						{
						console.log(data.msg);
						}else if(data.status=="100")
						{
							console.log(data.msg);
						}else
							{
							$('#myTable').bootstrapTable('refresh');
							top.layer.msg(data.msg);
							}
				}
			});
			$("#createPublicFolder").modal("hide");
		});
	});
	$(".js-setpriv1").unbind("click").click(function(){
		var folderId = $("#folderId").val();
		if(folderId=="")
			{
				top.layer.msg("请选择一个需设置权限的文件夹！");
				return;
			}
		addPriv1(folderId);
	});
	$(".js-setpriv2").unbind("click").click(function(){
		var folderId = $("#folderId").val();
		if(folderId=="")
			{
				top.layer.msg("请选择一个需设置权限的文件夹！");
				return;
			}
		addPriv2(folderId);
	});
	$(".js-setpriv3").unbind("click").click(function(){
		var folderId = $("#folderId").val();
		if(folderId=="")
			{
				top.layer.msg("请选择一个需设置权限的文件夹！");
				return;
			}
		addPriv3(folderId);
	});
	$(".js-setpriv4").unbind("click").click(function(){
		var folderId = $("#folderId").val();
		if(folderId=="")
			{
				top.layer.msg("请选择一个需设置权限的文件夹！");
				return;
			}
		addPriv4(folderId);
	});
	$(".js-setpriv5").unbind("click").click(function(){
		var folderId = $("#folderId").val();
		if(folderId=="")
			{
				top.layer.msg("请选择一个需设置权限的文件夹！");
				return;
			}
		addPriv5(folderId);
	});
});
function addPriv1(folderId)
{
	$.ajax({
	url : "/set/fileset/setPublicFilePriv",
	type : "post",
	dataType : "json",
	data:{
		folderId:folderId,
		accessUserPriv:$("#accessUser").attr("data-value"),
		accessDeptPriv:$("#accessDept").attr("data-value"),
		accessLeavePriv:$("#accessLeave").attr("data-value")
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
					}
	}
});
}

function addPriv2(folderId)
{$.ajax({
	url : "/set/fileset/setPublicFilePriv",
	type : "post",
	dataType : "json",
	data:{
		folderId:folderId,
		downUserPriv:$("#downUser").attr("data-value"),
		downDeptPriv:$("#downDept").attr("data-value"),
		downLeavePriv:$("#downLeave").attr("data-value")
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
					}
	}
});
}
function addPriv3(folderId)
{$.ajax({
	url : "/set/fileset/setPublicFilePriv",
	type : "post",
	dataType : "json",
	data:{
		folderId:folderId,
		manageUserPriv:$("#manageUser").attr("data-value"),
		manageDeptPriv:$("#manageDept").attr("data-value"),
		manageLeavePriv:$("#manageLeave").attr("data-value")
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
					}
	}
});
}

function addPriv4(folderId)
{$.ajax({
	url : "/set/fileset/setPublicFilePriv",
	type : "post",
	dataType : "json",
	data:{
		folderId:folderId,
		createUserPriv:$("#createUser").attr("data-value"),
		createDeptPriv:$("#createDept").attr("data-value"),
		createLeavePriv:$("#createLeave").attr("data-value")
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
					}
	}
});
}


function addPriv5(folderId)
{
	var optType=$('input:radio[name="optType"]:checked').val();
	if(optType=="1")
		{
			addpriv(folderId)
		}else if(optType=="2")
		{
			removepriv(folderId);
		}
}

function addpriv(folderId)
{
	$.ajax({
		url : "/set/fileset/addPublicFolderPriv",
		type : "post",
		dataType : "json",
		data:{
			folderId:folderId,
			user:$("#user").attr("data-value"),
			dept:$("#dept").attr("data-value"),
			leave:$("#leave").attr("data-value"),
			range:getCheckBoxValue("range")
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
						}
		}
	});
	}

function removepriv(folderId)
{
	$.ajax({
		url : "/set/fileset/removePublicFolderPriv",
		type : "post",
		dataType : "json",
		data:{
			folderId:folderId,
			user:$("#user").attr("data-value"),
			dept:$("#dept").attr("data-value"),
			leave:$("#leave").attr("data-value"),
			range:getCheckBoxValue("range")
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
						}
		}
	});
	}