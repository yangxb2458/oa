var zTree1;
var zTree2;
function selectattach(Obj,flag)
{
	$("#attachselect").html(selectmodal);
	var id = $(Obj).attr("op_id");
	$("#attachSelectModal").modal("show");
	$.ajax({
		url : "/ret/fileget/getPersonalFolderForSelect",
		type : "post",
		dataType : "json",
		data : {
			folderId : "0"
		},
		success : function(data) {
			zTree1 = $.fn.zTree.init($("#tree1"), setting1, data);// 初始化树节点时，添加同步获取的数据
		}
	});
	$.ajax({
		url : "/ret/fileget/getMyPublicFolderInPrivForSelect",
		type : "post",
		dataType : "json",
		data : {
			folderId : "0"
		},
		success : function(data) {
			zTree2 = $.fn.zTree.init($("#tree2"), setting2, data);// 初始化树节点时，添加同步获取的数据
		}
	});
	$(".js-select-btn").unbind("click").click(function(){
		var attArr = [];
		var attachs = $("#"+id).attr("data_value");
		if(attachs!=undefined&&attachs!=null&&attachs!="")
		{
			attArr=attachs.split(",");
		}
		$("#selectedFileItem").find("div").each(function(){
			attArr.push($(this).attr("data-value"));
		});
		attArr.sort();
		attArr = $.unique(attArr); 
		$("#"+id).attr("data_value",attArr.join(","));
		createAttach(id,0);
		$("#attachSelectModal").modal("hide");
	})
}

var setting1 = {
		async : {
			enable : true,// 设置 zTree 是否开启异步加载模式
			url : "/ret/fileget/getPersonalFolderForSelect",// Ajax 获取数据的 URL 地址
			autoParam : [ "folderId" ],// 异步加载时需要自动提交父节点属性的参数
		},
		view:{
			selectMuilt:false,//节点是否允许多选
			dblClickExpand: false
		},
		callback : {
			onClick : zTreeOnClick1
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
var setting2 = {
		async : {
			enable : true,// 设置 zTree 是否开启异步加载模式
			url : "/ret/fileget/getMyPublicFolderInPrivForSelect",// Ajax 获取数据的 URL 地址
			autoParam : [ "folderId" ],// 异步加载时需要自动提交父节点属性的参数
		},
		view:{
			selectMuilt:false,//节点是否允许多选
			dblClickExpand: false
		},
		callback : {
			onClick : zTreeOnClick2
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

function zTreeOnClick1(event, treeId, treeNode) {
	zTree1.expandNode(treeNode);
	if(treeNode.isParent)
	{
		$.ajax({
			url : "/ret/fileget/getFileListForSelect",
			type : "post",
			dataType : "json",
			data : {
				folderId : treeNode.folderId
			},
			success : function(data) {
				var newNodes = data.list;
				zTree1.reAsyncChildNodes(treeNode, "refresh");
				newNodes = zTree1.addNodes(treeNode, newNodes);
			}
		});
	}else
	{
		createAttachItme("personalFile",treeNode.attachId,treeNode.folderName);
	}
}
function zTreeOnClick2(event, treeId, treeNode) {
	zTree2.expandNode(treeNode);
	if(treeNode.isParent)
	{
		$.ajax({
			url : "/ret/fileget/getPublicFileByFolderId",
			type : "post",
			dataType : "json",
			data : {
				folderId : treeNode.folderId
			},
			success : function(data) {
				var newNodes = data.list;
				zTree2.reAsyncChildNodes(treeNode, "refresh");
				newNodes = zTree2.addNodes(treeNode, newNodes);
			}
		});
	}else
	{
		createAttachItme("publicFile",treeNode.attachId,treeNode.folderName);
	}
}

function createAttachItme(type,attachId,attachName)
{
	if(type=="personalFile")
	{
		if($("div[data-value="+attachId+"]").length<1)
		{
			$("#selectedFileItem").append("<div data-value='"+attachId+"' class='alert alert-success fade in' style='margin-bottom:5px'><button class='close' data-dismiss='alert'>×</button><i class='fa-fw fa fa-check'></i><strong style='margin-right:10px;'>[个人文件柜]</strong>"+attachName+"</div>")
		}
	}
	if(type=="publicFile")
	{
		if($("div[data-value="+attachId+"]").length<1)
		{
			$("#selectedFileItem").append("<div data-value='"+attachId+"' class='alert alert-success fade in' style='margin-bottom:5px'><button class='close' data-dismiss='alert'>×</button><i class='fa-fw fa fa-check'></i><strong style='margin-right:10px;'>[公共文件柜]</strong>"+attachName+"</div>")
		}
	}
}

var selectmodal=['<div class="modal fade bs-example-modal-lg" id="attachSelectModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">',
	'		<div class="modal-dialog">',
	'			<div class="modal-content">',
	'				<div class="modal-header">',
	'					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>',
	'					<h4 class="modal-title" id="myLargeModalLabel">附件选择</h4>',
	'				</div>',
	'				<div class="modal-body" style="padding: 0px;">',
	'					<div class="row">',
	'						<div class="col-lg-6 col-sm-6 col-xs-6" style="padding-right: 0px;">',
	'							<div class="tabbable">',
	'								<ul class="nav nav-tabs tabs-flat" id="myTab11">',
	'									<li class="active"><a data-toggle="tab" href="#personalfile" > 个人文件 </a></li>',
	'									<li><a data-toggle="tab" href="#publicfile"> 公共文件 </a></li>',
	'								</ul>',
	'								<div class="tab-content tabs-flat" style="height:400px;padding:0px;">',
	'									<div id="personalfile" class="tab-pane in active">',
	'										<ul id="tree1" class="ztree"></ul>',
	'									</div>',
	'									<div id="publicfile" class="tab-pane">',
	'										<ul id="tree2" class="ztree"></ul>',
	'									</div>',
	'									',
	'								</div>',
	'							</div>',
	'						</div>',
	'						<div class="col-lg-6 col-sm-6 col-xs-6" style="padding-left: 0px;">',
	'							<div class="tab-content tabs-flat" id="selectedFileItem" style="height:434px;padding-top: 34px;overflow-y:auto;">',
	'							',
	'							</div>',
	'						</div>',
	'					</div>',
	'				</div>',
	'				<div class="modal-footer">',
	'					<button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>',
	'					<button type="button" class="btn btn-primary js-select-btn">确定</button>',
	'				</div>',
	'			</div>',
	'		</div>',
	'	</div>'].join("");
