var zTree;
var curLocation = "";// 当前位置
var fileId = "";
var fileType = "";
var optType = ""
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/fileget/getPersonalDir",// Ajax 获取数据的 URL 地址
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
$(function() {
	$.ajax({
		url : "/ret/fileget/getPersonalDir",
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
		}
	});
	$("folderLeaveName").unbind("click").click(function(e) {
		e.stopPropagation();
		$("#menuContent").css({
			"width" : $(this).outerWidth() + "px"
		}).slideDown(200);
	});
	$("body").unbind("click").click(function() {
		$("#menuContent").hide();
	});

	$("#menuContent").unbind("click").click(function(e) {
		e.stopPropagation();
	});

	new BootstrapMenu("#dirdiv", {
		actions : [ {
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
				$("#createfilemodal").modal("show");
				$("#show_personalFile").html("");
				$("#personalFile").attr("data_value", "");
				$(".js-filesave").unbind("click").click(function() {
					createFile();
				});
			}
		}, {
			name : '粘贴',
			onClick : function() {
				paste();
			}
		}, {
			name : '权限设置',
			value : $(this).attr("data-value"),
			onClick : function() {
			}
		} ]
	});
});

function createFileList(folderId) {
	$(".bs-glyphicons-list").html("");
	var arr = getPersonalFileFolderChild(folderId);
	for (var i = 0; i < arr.length; i++) {
		$(".bs-glyphicons-list").append("<li class='js-floder' ftype='folder' id='file_folder_" + i + "' data-value='" + arr[i].folderId + "' title='"+ arr[i].folderName +"'><span style='font-size:40px;color: #428bca;' class='fa fa-folder-open-o'></span><span class='glyphicon-class filename'>" + arr[i].folderName + "</span></li>");
	}

	$(".js-floder").each(function() {
		$(this).unbind("mousedown").mousedown(function(e) {
			$(".js-floder").removeAttr("style");
			$(this).css({
				"background-color" : "#e46f61",
				"color" : "#fff"
			});
			var id = $(this).attr("id");
			var menu = new BootstrapMenu("#" + id, {
				actions : [ {
					name : '复制',
					ftype : $(this).attr("ftype"),
					value : $(this).attr("data-value"),
					onClick : function() {
						optType = "2";
						fileId = this.value;
						top.layer.msg("复制文件成功!请选择粘贴位置!");
						if (this.ftype == "file") {
							fileType = "file";
						} else {
							fileType = "folder";
						}
					}
				}, {
					name : '剪切',
					ftype : $(this).attr("ftype"),
					value : $(this).attr("data-value"),
					onClick : function() {
						optType = "1";
						fileId = this.value;
						top.layer.msg("剪切文件成功!请选择粘贴位置!");
						if (this.ftype == "file") {
							fileType = "file";
						} else {
							fileType = "folder";
						}
					}
				}, {
					name : '重命名',
					value : $(this).attr("data-value"),
					ftype : $(this).attr("ftype"),
					onClick : function() {
						rename(this.value, this.ftype);
					}
				} ]
			});
		});
	});
	$.ajax({
		url : "/ret/fileget/getfilelist",
		type : "post",
		dataType : "json",
		data : {
			folderId : folderId
		},
		success : function(data) {
			if (data.status == 200) {
				for (var i = 0; i < data.list.length; i++) {
					$(".bs-glyphicons-list").append("<li class='js-file' ftype='file' ext_name=" + data.list[i].extName + " id='file_id_" + i + "' data-value='" + data.list[i].fileId + "' attach='" + data.list[i].attach + "' title='"+ data.list[i].fileName + "'><span style='font-size:40px;color: #428bca;' class='" + getfileClass(data.list[i].fileName) + "'></span><span class='glyphicon-class filename'>" + data.list[i].fileName + "</span></li>");
				}
				$(".js-file").each(function() {
					$(this).unbind("mousedown").mousedown(function(e) {
						$(".js-file").removeAttr("style");
						$(this).css({
							"background-color" : "#e46f61",
							"color" : "#fff"
						});
						var id = $(this).attr("id");
						var attachId = $(this).attr("attach");
						var extName = $(this).attr("ext_name");
						var menu = new BootstrapMenu("#" + id, {
							actions : [ {
								name : '复制',
								ftype : $(this).attr("ftype"),
								value : $(this).attr("data-value"),
								onClick : function() {
									optType = "2";
									fileId = this.value;
									top.layer.msg("复制文件成功!请选择粘贴位置!");
									if (this.ftype == "file") {
										fileType = "file";
									} else {
										fileType = "folder";
									}
								}
							}, {
								name : '剪切',
								ftype : $(this).attr("ftype"),
								value : $(this).attr("data-value"),
								onClick : function() {
									optType = "1";
									fileId = this.value;
									top.layer.msg("剪切文件成功!请选择粘贴位置!");
									if (this.ftype == "file") {
										fileType = "file";
									} else {
										fileType = "folder";
									}
								}
							}, {
								name : '重命名',
								value : $(this).attr("data-value"),
								ftype : $(this).attr("ftype"),
								onClick : function() {
									rename(this.value, this.ftype);
								}
							}, {
								name : '编辑',
								value : $(this).attr("data-value"),
								onClick : function() {
									openFileOnLine(extName, attachId, 4);
								}
							}, {
								name : '查看',
								value : $(this).attr("data-value"),
								onClick : function() {
									openFileOnLine(extName, attachId, 1);
								}
							}, {
								name : '删除',
								value : $(this).attr("data-value"),
								ftype : $(this).attr("ftype"),
								onClick : function() {
									if (this.ftype == "file") {
										delFile(this.value);
									} else {
										delFolder(this.value);
									}
								}
							}, {
								name : '下载',
								value : $(this).attr("data-value"),
								onClick : function() {
									downFile(this.value);
								}
							} ]
						});
					});
				});
			} else {
				console.log(data.msg);
			}
		}
	});

	var treeObj = $.fn.zTree.getZTreeObj("tree");
	var nodes = treeObj.getSelectedNodes();
	if (nodes.length > 0) {
		var allNode = nodes[0]['folderName'];// 获取当前选中节点
		var node = nodes[0].getParentNode();
		getParentNodes(node, allNode);
	}
	var location = " <li><i class='fa fa-home'></i></li>";
	var nodeArrs = curLocation.split(">");
	for (var i = nodeArrs.length - 1; i >= 0; i--) {
		location += " <li><a href='#'>" + nodeArrs[i] + "</a></li>";
	}
	$(".breadcrumb").html(location);
	$(".js-floder").each(function() {
		$(this).dblclick(function() {
			var leaveFolder = $(this).attr("data-value");
			createFileList(leaveFolder);
			$("#fileList").attr("data-value", leaveFolder);
			var zTree = $.fn.zTree.getZTreeObj("tree");// treeDemo界面中加载ztree的div
			var node = zTree.getNodeByParam("folderId", $(this).attr("data-value"));
			zTree.selectNode(node, true);// 将指定ID的节点选中
			zTree.expandNode(node, true, false);// 将指定ID节点展开

		})
	});
}

function zTreeOnClick(event, treeId, treeNode) {
	$("#fileList").hide();
	$("#fileList").attr("data-value", treeNode.folderId);
	createFileList(treeNode.folderId);
}

function rename(fileId, fType) {
	document.getElementById("form3").reset();
	$("#renamemodal").modal("show");
	$(".js-rename").unbind("click").click(function() {
		if (confirm("确定修改当前文件名称吗？")) {
			$.ajax({
				url : "/set/fileset/updatePersonalFileFolder",
				type : "post",
				dataType : "json",
				data : {
					fileId : fileId,
					type : fType,
					fileName : $("#fileName").val()
				},
				success : function(data) {
					if (data.status == "200") {
						top.layer.msg(data.msg);
						$("#renamemodal").modal("hide");
					} else {
						console.log(data.msg);
					}
				}
			});

		} else {
			return;
		}
	});
}

function paste() {
	if (fileId == "" || fileId == null) {
		top.layer.msg("请选择需要粘贴的文件!");
		return;
	}
	if (optType == "2") {
		$.ajax({
			url : "/set/fileset/pastePersonalFile",
			type : "post",
			dataType : "json",
			data : {
				fileId : fileId,
				currentLocation : $("#fileList").attr("data-value"),
				type : fileType

			},
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
					location.reload();
				} else {
					console.log(data.msg);
				}
			}
		});
	} else if (optType == "1") {
		if (fileId == "" || fileId == null) {
			top.layer.msg("请选择需要粘贴的文件!");
			return;
		}
		$.ajax({
			url : "/set/fileset/shearPersonalFile",
			type : "post",
			dataType : "json",
			data : {
				fileId : fileId,
				currentLocation : $("#fileList").attr("data-value"),
				type : fileType

			},
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
					location.reload();
				} else {
					console.log(data.msg);
				}
			}
		});
	}
}
function downFile(fileId) {
	window.open("/ret/fileget/getFileDown?fileId=" + fileId);
}

function getPersonalFileFolderChild(folderId) {
	var returnArr;
	$.ajax({
		url : "/ret/fileget/getPersonalFileFolderChild",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			folderId : folderId,
		},
		success : function(data) {
			if (data.status == "200") {
				returnArr = data.list;
			} else {
				console.log(data.msg);
			}
		}
	});
	return returnArr;
}

function createFolder() {
	$.ajax({
		url : "/set/fileset/insertPersonalFileFolder",
		type : "post",
		dataType : "json",
		data : {
			sortNo : $("#sortNo").val(),
			folderName : $("#folderName").val(),
			folderLeave : $("#fileList").attr("data-value")
		},
		success : function(data) {
			if (data.status == 200) {
				top.layer.msg(data.msg);
				createFileList($("#fileList").attr("data-value"));
				$("#createfoldermodal").modal("hide");

			} else {
				console.log(data.msg);
			}
		}
	});
}

function delFolder(folderId) {
	$.ajax({
		url : "/set/fileset/delPersonalFolder",
		type : "post",
		dataType : "json",
		data : {
			folderId : folderId
		},
		success : function(data) {
			if (data.status == 200) {
				top.layer.msg(data.msg);
				location.reload();
			} else {
				console.log(data.msg);
			}
		}
	});
}

function delFile(fileId) {
	$.ajax({
		url : "/set/fileset/delPersonalFile",
		type : "post",
		dataType : "json",
		data : {
			fileId : fileId
		},
		success : function(data) {
			if (data.status == 200) {
				top.layer.msg(data.msg);
				createFileList($("#fileList").attr("data-value"));
			} else {
				console.log(data.msg);
			}
		}
	});
}

function createFile() {
	$.ajax({
		url : "/set/fileset/insertPersonalFile",
		type : "post",
		dataType : "json",
		data : {
			sortNo : $("#fileSortNo").val(),
			folderId : $("#fileList").attr("data-value"),
			attach : $("#personalFile").attr("data_value")
		},
		success : function(data) {
			if (data.status == 200) {
				top.layer.msg(data.msg);
				createFileList($("#fileList").attr("data-value"));
				$("#createfilemodal").modal("hide");
			} else {
				console.log(data.msg);
			}
		}
	});
}

function getParentNodes(node, allNode) {
	if (node != null) {
		allNode += ">" + node['folderName'];
		curNode = node.getParentNode();
		getParentNodes(curNode, allNode);
	} else {
		// 根节点
		curLocation = allNode;
	}
}
function getfileClass(filename) {
	var point = filename.lastIndexOf(".");
	var type = filename.substr(point)
	if (type == ".txt") {
		return "fa fa-file-text";
	} else if (type == ".pdf") {
		return "fa fa-file-pdf-o";
	} else if (type == ".xls" || type == ".xlsx") {
		return "fa fa-file-excel-o";
	} else if (type == ".doc" || type == ".docx" || type == ".wps" || type == ".dot") {
		return "fa fa-file-word-o";
	} else if (type == ".jpg" || type == ".png" || type == ".bpm") {
		return "fa fa-file-image-o";
	} else if (type == ".rar" || type == ".zip" || type == ".gz" || type == ".tar") {
		return "fa fa-file-zip-o";
	} else if (type == ".ppt" || type == ".pptx" || type == ".ppts") {
		return "fa fa-file-powerpoint-o";
	} else {
		return "fa fa-file";
	}
}