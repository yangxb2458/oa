var zTree;
var priv;
var myNodes;
var copyPathSource="";
var copyNetDiskId="";
$(function() {
	getFileTree();
});
function createFile(path, netDiskId) {
	document.getElementById("form2").reset();
	$("#createFileModal").modal("show");
	$(".js-createFileBtn").unbind("click").click(function() {
		netDiskfileUpLoad('attach', path, netDiskId);
	})
}
function createFolder(path, netDiskId) {
	document.getElementById("form1").reset();
	$("#createFolderModal").modal("show");
	$(".js-createFolderBtn").unbind("click").click(function() {
		$.ajax({
			url : "/set/fileset/createNetDiskFolder",
			type : "post",
			data : {
				netDiskId : netDiskId,
				sourcePath : path,
				folderName : "\\" + $("#folderName").val()
			},
			dataType : "json",
			success : function(data) {
				if (data.status == "200") {
					top.layer.msg(data.msg);
					showFile(true, netDiskId);
					$("#createFolderModal").modal("hide");
					getFileTree();
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}

			}
		});
	});
}
function copyFile(path, netDiskId) {
	copyPathSource = path;
	copyNetDiskId = netDiskId;
	top.layer.msg("复制成功!")
}
function pasteFile(path, netDiskId) {
	if(copyPathSource=="")
		{
			top.layer.msg("请先选择需要复制文件!")
		}else
			{
			if (confirm("确定粘贴文件到目录吗？")) {
				$.ajax({
					url : "/set/fileset/copyFile",
					type : "post",
					data : {
						targetNetDiskId : netDiskId,
						targetPath : path,
						sourceNetDiskId : copyNetDiskId,
						sourcePath : copyPathSource
					},
					dataType : "json",
					success : function(data) {
						if (data.status == "200") {
							copyNetDiskId="";
							copyPathSource="";
							top.layer.msg(data.msg);
							showFile(true, netDiskId);
							getFileTree();
						} else if (data.status == "100") {
							top.layer.msg(data.msg);
						} else {
							console.log(data.msg);
						}
					}
				});
			} else {
				return;
			}
			}
}
function shearFile(path, netDiskId) {
	console.log("shearFile:" + path);
}
function renameFile(path, netDiskId) {
	document.getElementById("form3").reset();
	$("#fileRenameModal").modal("show");
	$(".js-renameFileBtn").unbind("click").click(function() {
		if (confirm("确定修改当前文件名称吗？")) {
			$.ajax({
				url : "/set/fileset/renameNetDiskFileName",
				type : "post",
				data : {
					netDiskId : netDiskId,
					sourcePath : path,
					newFileName:$("#newFileName").val()
				},
				dataType : "json",
				success : function(data) {
					console.log(data);
					if (data.status == "200") {
						top.layer.msg(data.msg);
						showFile(true, netDiskId);
						$("#fileRenameModal").modal("hide");
						getFileTree();
					} else if (data.status == "100") {
						top.layer.msg(data.msg);
					} else {
						console.log(data.msg);
					}
				}
			});
		} else {
			return;
		}
	})
}
function delFile(path, netDiskId) {
	if (confirm("确定删除当前文件吗？")) {
		$.ajax({
			url : "/set/fileset/delNetDiskfile",
			type : "post",
			data : {
				netDiskId : netDiskId,
				sourcePath : path
			},
			dataType : "json",
			success : function(data) {
				console.log(data);
				if (data.status == "200") {
					top.layer.msg(data.msg);
					showFile(true, netDiskId);
					$("#createFolderModal").modal("hide");
					getFileTree();
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		});
	} else {
		return;
	}
}
function openFile(path, netDiskId)
{
	var point = path.lastIndexOf("."); 
	var fileExt = path.substr(point).toLowerCase(); 
	openNetDiskFileOnLine(fileExt,netDiskId,path);
}



function readFile(path, netDiskId) {
		$.ajax({
			url : "/ret/fileget/getNetDiskFileInfo",
			type : "post",
			data : {
				netDiskId : netDiskId,
				sourcePath : path
			},
			dataType : "json",
			success : function(data) {
				console.log(data);
				if (data.status == "200") {
					document.getElementById("form4").reset();
					$("#fileSize").val(data.list.fileSize);
					$("#folderCount").val(data.list.folderCount);
					$("#fileCount").val(data.list.fileCount);
					$("#modifiedTime").val(data.list.modifiedTime);
					$("#fileInfoModal").modal("show");
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		});
}
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/fileget/getNetDiskDirs",// Ajax 获取数据的 URL 地址
		autoParam : [ "rootPath", "netDiskId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	view : {
		dblClickExpand : false
	// 屏蔽掉双击事件
	},
	callback : {
		onClick : zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "rootPath",
			rootPId : "0"
		},
		key : {
			name : "netDiskName"
		}
	}
};

function getFileTree() {
	$.ajax({
		url : "/ret/fileget/getNetDiskTree",
		type : "post",
		dataType : "json",
		success : function(data) {
			zTree = $.fn.zTree.init($("#tree"), setting, data);// 初始化树节点时，添加同步获取的数据
		}
	});
}

function showFile(isParent, netDiskId) {
	var sourcePath = $("#fileList").attr("data-value");
	var paths = sourcePath.split("\\");
	var html = "";
	var dataValue = "";
	for (var i = 0; i < paths.length; i++) {
		dataValue += paths[i] + "/";
		if (i == (paths.length)) {
			html += " <li class='active'><a style='color:#777;' data-value='" + dataValue + "'>" + paths[i] + "</a></li>";
		} else {
			html += " <li><a href='#' data-value='" + dataValue + "'>" + paths[i] + "</a></li>";
		}
	}
	$("#rootpathdiv").html(html);
	if (isParent) {
		$.ajax({
			url : "/ret/fileget/getNetDiskFiles?netDiskId=" + netDiskId,
			type : "post",
			dataType : "json",
			data : {
				rootPath : sourcePath
			},
			success : function(data) {
				if (data.status == "200") {
					crateFileDiv(data.list, netDiskId);
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		});
	}
	$("#fileList").unbind("mousedown").mousedown(function(e) {
		var nowPath = $("#fileList").attr("data-value");
		var actArr = [];
		var value = $(this).attr("data-value");
		if (priv.managePriv || priv.createPriv) {
			var json1 = {};
			json1.name = "新建文件";
			json1.value = value;
			json1.onClick = function() {
				createFile(nowPath, netDiskId)
			}
			actArr.push(json1);
			var json2 = {};
			json2.name = "新建文件夹";
			json2.value = value;
			json2.onClick = function() {
				createFolder(nowPath, netDiskId)
			}
			actArr.push(json2);
			var json3 = {};
			json3.name = "粘贴";
			json3.value = value;
			json3.onClick = function() {
				pasteFile(value, netDiskId)
			}
			actArr.push(json3);
		}
		showMenu("fileList",actArr);
		event.stopPropagation();
	});
}

function zTreeOnClick(event, treeId, treeNode) {
	$("#titdiv").hide();
	getNetDiskPriv(treeNode.netDiskId);
	$("#fileList").attr("data-value", treeNode.rootPath);
	showFile(true, treeNode.netDiskId);
}

function crateFileDiv(data, netDiskId) {
	$(".bs-glyphicons-list").html("");
	if (data.length > 0) {
		for (var i = 0; i < data.length; i++) {
			if (data[i].isParent == "false") {
				$(".bs-glyphicons-list").append(
						"<li class='js-file' id='file_id_" + i + "' data-value='" + data[i].rootPath + "' title='"+ data[i].netDiskName + "'><span style='font-size:40px;color: #428bca;' class='"
								+ getfileClass(data[i].netDiskName) + "'></span><span class='glyphicon-class filename'>" + data[i].netDiskName
								+ "</span><span style='padding-top: 5px;' class='glyphicon-class'>" + data[i].fileSize + "KB</span></li>");
			} else {
				$(".bs-glyphicons-list").append(
						"<li class='js-floder' id='file_id_" + i + "' data-value='" + data[i].rootPath
								+ "' title='"+ data[i].netDiskName + "'><span style='font-size:40px;color: #428bca;' class='fa fa-folder-open-o'></span><span class='glyphicon-class filename'>"
								+ data[i].netDiskName + "</span></li>");
			}
		}
	} else {
		$(".bs-glyphicons-list").html("");
	}
	$(".js-floder").each(function() {
		$(this).dblclick(function() {
			$("#fileList").attr("data-value", $(this).attr("data-value"));
			var zTree = $.fn.zTree.getZTreeObj("tree");// treeDemo界面中加载ztree的div
			var node = zTree.getNodeByParam("rootPath", $(this).attr("data-value"));
			zTree.selectNode(node, true);// 将指定ID的节点选中
			zTree.expandNode(node, true, false);// 将指定ID节点展开
			$.ajax({
				url : "/ret/fileget/getNetDiskFiles",
				type : "post",
				dataType : "json",
				data : {
					rootPath : $(this).attr("data-value"),
					netDiskId:netDiskId
				},
				success : function(data) {
					if (data.status == "200") {
						crateFileDiv(data.list,netDiskId);
						var path=$("#fileList").attr("data-value");
						var paths = path.split("\\");
						var dataValue = "";
						var html = "";
						for (var i = 0; i < paths.length; i++) {
							dataValue += paths[i] + "/";
							if (i == (paths.length)) {
								html += " <li class='active'><a style='color:#777;' data-value='" + dataValue + "'>" + paths[i] + "</a></li>";
							} else {
								html += " <li><a href='#' data-value='" + dataValue + "'>" + paths[i] + "</a></li>";
							}
						}
						$("#rootpathdiv").html(html);
					} else if (data.status == "100") {
						top.layer.msg(data.msg);
					} else {
						console.log(data.msg);
					}
				}
			});
		});
	});
	$(".js-file").each(function() {
		$(this).unbind("mousedown").mousedown(function(e) {
			var nowPath = $("#fileList").attr("data-value");
			$(".js-file").removeAttr("style");
			var sourcePath = $("#fileList").attr("data-value");
			$(this).css({
				"background-color" : "#e46f61",
				"color" : "#fff"
			});
			var id = $(this).attr("id");
			var value = $(this).attr("data-value");
			var actArr = [];
			if (priv.managePriv) {
				var json1 = {};
				json1.name = "新建文件";
				json1.value = value;
				json1.onClick = function() {
					createFile(nowPath, netDiskId)
				}
				actArr.push(json1);
				var json2 = {};
				json2.name = "新建文件夹";
				json2.value = value;
				json2.onClick = function() {
					createFolder(nowPath, netDiskId)
				}
				actArr.push(json2);
				var json3 = {};
				json3.name = "复制";
				json3.value = value;
				json3.onClick = function() {
					copyFile(value, netDiskId)
				}
				actArr.push(json3);
				var json5 = {};
				json5.name = "剪切";
				json5.value = value;
				json5.onClick = function() {
					shearFile(value, netDiskId)
				}
				actArr.push(json5);
				var json9 = {};
				json9.name = "删除";
				json9.value = value;
				json9.onClick = function() {
					delFile(value, netDiskId)
				}
				actArr.push(json9);
				var json6 = {};
				json6.name = "重命名";
				json6.value = value;
				json6.onClick = function() {
					renameFile(value, netDiskId)
				}
				actArr.push(json6);
				var json7 = {};
				json7.name = "查看";
				json7.value = value;
				json7.onClick = function() {
					openFile(value, netDiskId);
				}
				actArr.push(json7);
				var json8 = {};
				json8.name = "下载";
				json8.value = value;
				json8.onClick = function() {
					downFile(value, netDiskId)
				}
				actArr.push(json8);
			} else {
				if (priv.createPriv) {
					var json1 = {};
					json1.name = "新建文件";
					json1.value = value;
					json1.onClick = function() {
						createFile(value, netDiskId)
					}
					actArr.push(json1);
					var json2 = {};
					json2.name = "新建文件夹";
					json2.value = value;
					json2.onClick = function() {
						createFolder(value, netDiskId)
					}
					actArr.push(json2);
					var json7 = {};
					json7.name = "查看";
					json7.value = value;
					json7.onClick = function() {
						openFile(value, netDiskId);
					}
					actArr.push(json7);
					var json8 = {};
					json8.name = "下载";
					json8.value = value;
					json8.onClick = function() {
						downFile(value, netDiskId)
					}
					actArr.push(json8);
				} else {
					if (priv.downPriv) {
						var json7 = {};
						json7.name = "查看";
						json7.value = value;
						json7.onClick = function() {
							openFile(value, netDiskId);
						}
						actArr.push(json7);
						var json8 = {};
						json8.name = "下载";
						json8.value = value;
						json8.onClick = function() {
							downFile(value, netDiskId)
						}
						actArr.push(json8);
					}
				}
			}
			showMenu(id,actArr);
			event.stopPropagation();
		});
	});
}

function showMenu(id,actArr)
{
	$(".bootstrapMenu").remove();
	var memu = new BootstrapMenu("#" + id, {
		actions : actArr
	});
	event.stopPropagation();
}



function getNetDiskPriv(netDiskId) {
	$.ajax({
		url : "/ret/fileget/getNetDiskPriv",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			netDiskId : netDiskId
		},
		success : function(data) {
			if (data.status == "200") {
				priv = data.list;
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}
function netDiskfileUpLoad(fileId, sourcePath, netDiskId) {
	$.ajaxFileUpload({
		url : '/set/fileset/createNetDiskFile?' + encodeURI('sourcePath=' + sourcePath + "&netDiskId=" + netDiskId), // 上传文件的服务端
		secureuri : false, // 是否启用安全提交
		async : false,
		dataType : 'json', // 数据类型
		fileElementId : fileId, // 表示文件域ID
		success : function(data, status) {
			top.layer.msg(data.msg);
			if (data.status == "200") {
				top.layer.msg(data.msg);
				showFile(true, netDiskId);
				$("#createFileModal").modal("hide");
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		},
		//提交失败处理函数
		error : function(data, status, e) {
			console.log(data.msg);
		}
	});
}
