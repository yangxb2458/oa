var zTree;
var priv;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/fileget/getMyPublicFolderInPriv",// Ajax 获取数据的 URL 地址
		autoParam : [ "folderId" ],// 异步加载时需要自动提交父节点属性的参数
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
			name : "folderName"
		}
	}
};
function zTreeOnClick(event, treeId, treeNode) {
	$("#titdiv").hide();
	$("#fileList").show();
	$("#folderNameHead").html(treeNode.folderName);
	$("#folderNameHead").attr("data-value", treeNode.folderId);
	createOptDiv(treeNode.folderId)
	showFiles(treeNode.folderId);
}

$(function() {
	$.ajax({
		url : "/ret/fileget/getMyPublicFolderInPriv",
		type : "post",
		dataType : "json",
		data : {
			folderId : "0"
		},
		success : function(data) {
			var topNode = [ {
				folderId : "0",
				folderName : "根目录",
				isParent : true
			} ];
			var newTreeNodes = topNode.concat(data);
			zTree = $.fn.zTree.init($("#tree"), setting, newTreeNodes);// 初始化树节点时，添加同步获取的数据
			var nodes = zTree.transformToArray(zTree.getNodes())
			zTree.expandNode(nodes[0], true);
			if(folderId!=null&&folderId!="")
			{
				var defaultSelectNode =  zTree.getNodeByParam("folderId", folderId);//根据ID找到该节点
			    if(defaultSelectNode){
			    	zTree.selectNode(defaultSelectNode,true);
			    	setting.callback.onClick(null,zTree.setting.treeId,defaultSelectNode);
			    }
			}
		}
	});
});

function createOptDiv(folderId) {
	getSmsConfig("msgType","publicFile");
	$.ajax({
		url : "/ret/fileget/getPublicFolderPrivInfo",
		type : "post",
		dataType : "json",
		data : {
			folderId : folderId
		},
		success : function(data) {
			priv = data.list;
			if (data.status == "200") {
				var optSet = [];
				if (data.list.createPriv == true) {
					optSet = [ {
						name : '新建文件夹',
						onClick : function() {
							$("#createfoldermodal").modal("show");
							$(".js-foldersave").unbind("click").click(function() {
								createFolder();
							});
						}
					}, {
						name : '新建文件',
						value : $(this).attr("data-value"),
						onClick : function() {
							$("#show_publicFile").html("");
							$("#publicFile").val();
							$("#publicFile").attr("data_value","");
							$("#createfilemodal").modal("show");
							$(".js-filesave").unbind("click").click(function() {
								createFile();
							});
						}
					} ];
				}
				if (data.list.managePriv == true) {
					optSet = [ {
						name : '新建文件夹',
						onClick : function() {
							$("#createfoldermodal").modal("show");
							$(".js-foldersave").unbind("click").click(function() {
								createFolder();
							});
						}
					}, {
						name : '新建文件',
						value : $(this).attr("data-value"),
						onClick : function() {
							$("#show_publicFile").html("");
							$("#publicFile").val();
							$("#publicFile").attr("data_value","");
							$("#createfilemodal").modal("show");
							$(".js-filesave").unbind("click").click(function() {
								createFile();
							});
						}
					}, {
						name : '粘贴',
						value : $(this).attr("data-value"),
						onClick : function() {
						}
					} ];
				}
				if (optSet.length > 0) {
					new BootstrapMenu("#fileList", {
						actions : optSet
					});
				}
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function createFile() {
	$.ajax({
		url : "/set/fileset/createPublicFile",
		type : "post",
		dataType : "json",
		data : {
			sortNo : $("#fileSortNo").val(),
			folderId : $("#folderNameHead").attr("data-value"),
			attach : $("#publicFile").attr("data_value"),
			msgType:getCheckBoxValue("msgType")
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				console.log(data.msg);
			} else {
				top.layer.msg(data.msg);
				$("#createfilemodal").modal("hide");
				showFiles($("#folderNameHead").attr("data-value"))
			}
		}
	});
}
function showFiles(folderId) {
	$.ajax({
		url : "/ret/fileget/getPublicFilelist",
		type : "post",
		dataType : "json",
		data : {
			folderId : folderId
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				console.log(data.msg);
			} else {
				crateFileDiv(data.list,folderId)
			}
		}
	});
}

function crateFileDiv(data, folderId) {
	$(".bs-glyphicons-list").html("");
	if (data.length > 0) {
		for (var i = 0; i < data.length; i++) {
			if(data[i].isFile=="true")
			{
				$(".bs-glyphicons-list").append("<li class='js-file' ext_name="+data[i].extName+" id='file_id_" + i + "' data-value='" + data[i].fileId + "' attach='"+data[i].attach+"'  title='"+ data[i].fileName + "'><span style='font-size:40px;color: #428bca;' class='"
						+ getfileClass(data[i].fileName) + "'></span><span class='glyphicon-class filename'>" + data[i].fileName
						+ "</span><span style='padding-top: 5px;' class='glyphicon-class'>" + data[i].fileSize + "KB</span></li>");
			}else
			{
				$(".bs-glyphicons-list").append(
						"<li class='js-floder' data-floder-name='"+data[i].fileName+"' id='file_id_" + i + "' data-value='" + data[i].fileId
								+ "' title='"+ data[i].fileName + "'><span style='font-size:40px;color: #428bca;' class='fa fa-folder-open-o'></span><span class='glyphicon-class filename'>"
								+ data[i].fileName + "</span></li>");
			}
		}
	} else {
		$(".bs-glyphicons-list").html("");
	}
	
	$(".js-floder").each(function() {
		$(this).unbind("mousedown").mousedown(function(e) {
			var floderId = $("#folderNameHead").attr("data-value");
			var id = $(this).attr("id");
			var value = $(this).attr("data-value");
			var actArr = [];
			if(priv.managePriv==true)
			{
				var json8 = {};
				json8.name = "打开目录";
				json8.value = value;
				json8.onClick = function() {
					openFolder(value)
				}
				actArr.push(json8);
				var json3 = {};
				json3.name = "复制";
				json3.value = value;
				json3.onClick = function() {
					copyFolder(value, floderId)
				}
				actArr.push(json3);
				var json5 = {};
				json5.name = "剪切";
				json5.value = value;
				json5.onClick = function() {
					shearFolder(value, floderId)
				}
				actArr.push(json5);
				var json9 = {};
				json9.name = "删除";
				json9.value = value;
				json9.onClick = function() {
					delFolder(value)
				}
				actArr.push(json9);
				var json6 = {};
				json6.name = "重命名";
				json6.value = value;
				json6.onClick = function() {
					renameFolder(value)
				}
				actArr.push(json6);
			}else
				{
				var json7 = {};
				json7.name = "打开目录";
				json7.value = value;
				json7.onClick = function() {
					openFolder(attachId)
				}
				actArr.push(json7);
			}
			showMenu(id,actArr);
			//event.stopPropagation();
		});
	});
	$(".js-file").each(function() {
		$(this).unbind("mousedown").mousedown(function(e) {
		var floderId = $("#folderNameHead").attr("data-value");
		$(".js-file").removeAttr("style");
		$(this).css({
			"background-color" : "#e46f61",
			"color" : "#fff"
		});
		var id = $(this).attr("id");
		var value = $(this).attr("data-value");
		var attachId = $(this).attr("attach");
		var extName = $(this).attr("ext_name");
		var actArr = [];
		if(priv.managePriv==true)
		{
			var json3 = {};
			json3.name = "复制";
			json3.value = value;
			json3.onClick = function() {
				copyFile(value, floderId)
			}
			actArr.push(json3);
			var json5 = {};
			json5.name = "剪切";
			json5.value = value;
			json5.onClick = function() {
				shearFile(value, floderId)
			}
			actArr.push(json5);
			var json9 = {};
			json9.name = "删除";
			json9.value = value;
			json9.onClick = function() {
				delFile(value)
			}
			actArr.push(json9);
			var json7 = {};
			json7.name = "编辑";
			json7.onClick = function() {
				openFileOnLine(extName,attachId,4);
			}
			actArr.push(json7);
			var json8 = {};
			json8.name = "查看";
			json8.onClick = function() {
				openFileOnLine(extName,attachId,1);
			}
			actArr.push(json8);
			var json9 = {};
			json9.name = "下载";
			json9.value = value;
			json9.onClick = function() {
				downFile(value)
			}
			actArr.push(json9);
		}else
			{
				var json7 = {};
				json7.name = "查看";
				json7.value = value;
				json7.onClick = function() {
					openFileOnLine(extName,attachId,1);
				}
				actArr.push(json7);
				var json8 = {};
				json8.name = "下载";
				json8.value = value;
				json8.onClick = function() {
					downFile(value)
				}
				actArr.push(json8);
		}
		showMenu(id,actArr);
		//event.stopPropagation();
		});
	});
	
	$(".js-floder").each(function() {
		$(this).dblclick(function() {
			var leaveFolder = $(this).attr("data-value");
			$("#folderNameHead").html($(this).attr("data-floder-name"));
			$("#folderNameHead").attr("data-value", leaveFolder);
			showFiles(leaveFolder);

		})
	});
}
function showMenu(id,actArr)
{
	//$(".bootstrapMenu").remove();
	new BootstrapMenu("#" + id, {
		actions : actArr
	});
	//event.stopPropagation();
}
function createFolder() {
	$.ajax({
		url : "/set/fileset/createPublicFileFolder",
		type : "post",
		dataType : "json",
		data : {
			sortNo : $("#sortNo").val(),
			folderName : $("#folderName").val(),
			folderLeave : $("#folderNameHead").attr("data-value")
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				console.log(data.msg);
			} else {
				top.layer.msg(data.msg);
				$("#createfoldermodal").modal("hide");
				showFiles($("#folderNameHead").attr("data-value"));
			}
		}
	});
}


function delFile(fileId)
{
	$.ajax({
		url : "/set/fileset/deletePublicFile",
		type : "post",
		dataType : "json",
		data : {
			fileId : fileId
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				console.log(data.msg);
			} else {
				top.layer.msg(data.msg);
				showFiles($("#folderNameHead").attr("data-value"));
			}
		}
	});
}