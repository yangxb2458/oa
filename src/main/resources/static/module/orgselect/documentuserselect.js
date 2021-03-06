function documentOpUserSelect(obj) {
	processId=$(obj).attr("data-value");
	var oprule = $(obj).attr("oprule");
	initDocumentUserSelect();
	$("#orgselectdiv").modal("show");
	var orgSetig = {
		async : {
			enable : true,// 设置 zTree 是否开启异步加载模式
			url : "/ret/documentget/getDocumentOpUserDept?processId="+processId,// Ajax 获取数据的 URL 地址
			autoParam : ["deptId"],// 异步加载时需要自动提交父节点属性的参数
		},
		callback : {
			onClick : toselectuserclick
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
	
	var topNode = [ {
		deptName : orgName.substring(0, 8),
		orgLeaveId : '',
		isParent : "true",
		deptId : "0",
		icon : "/gobal/img/org/org.png"
	} ];
	
	var zTree = $.fn.zTree.init($("#selectDocumentUsertree"), orgSetig, topNode);// 初始化树节点时，添加同步获取的数据
	var nodes = zTree.getNodes();
	for (var i = 0; i < nodes.length; i++) {
		zTree.expandNode(nodes[i], true, false, false);// 默认展开第一级节点
	}
	function toselectuserclick(event, treeId, treeNode) {
		$(".documentselectuser").empty();
		$.ajax({
			url : "/ret/documentget/getDocumentOpUseByPorcess?processId="+processId,
			type : "POST",
			dataType : "json",
			data : {
				deptId : treeNode.deptId
			},
			success : function(data) {
				if (data.status == 200) {
					var userlist = data.list;
					for (var i = 0; i < userlist.length; i++) {
						if (isExist(".documentselecteduser", userlist[i].accountId)) {
							var headimg = "/gobal/img/org/U01.png";
							if (userlist[i].sex == "女") {
								headimg = "/gobal/img/org/U11.png"
							}
							$(".documentselectuser").append(
									"<div class=\"documentselectdiv\" onclick=\"doSelectOpUser(this);\" style=\"padding-right:10px\" data-name=\""
											+ userlist[i].userName
											+ "\" data-value=\""
											+ userlist[i].accountId
											+ "\"><img src=\"" + headimg
											+ "\"><span>"
											+ userlist[i].userName
											+ "</span><a onclick=\"doSelectOpUser(this);\" class=\"btn btn-purple btn-xs\" style=\"float:right;line-height:22px;\">主办</a></div>");
						}
					}
				} else if (data.status == 100) {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		});
	}
	$('#documentSelectUser').modal('show');
	
	$(".documentSelectUserokbtn").unbind("click").click(function(){
		setSelectDocumentOpUser();
	});
}

function setSelectDocumentOpUser()
{
	var documentUser={};
	var opUser={};
	var otherOpUser=[];
	$(".documentselecteduser").find(".documentselecteddiv").each(function(){
		$(this).attr("data-value");
		if($(this).hasClass("isOpUser"))
			{
			opUser.accountId=$(this).attr("data-value");
			opUser.userName=$(this).attr("data-name");
			}else
			{
				var json={};
				json.accountId=$(this).attr("data-value");
				json.userName=$(this).attr("data-name");
				otherOpUser.push(json)
			}
	});
	documentUser.opUser=opUser;
	documentUser.otherOpUser=otherOpUser;
	if(documentUser)
		{
		if(!jQuery.isEmptyObject(documentUser.opUser))
		{
			if($(".js-opUser_"+processId).length>0)
			{
				var opUserHtml=['<div class="alert alert-danger fade in" style="width: 80px;height: 32px;line-height: 13px;font-size: 12px;padding-right: 4px;margin-bottom:0px;" data-value="'+documentUser.opUser.accountId+'">',
					'    <button class="close" data-dismiss="alert" style="line-height: 3px;color: white;font-size: 16px;opacity:1;">',
					'        ×',
					'    </button>',
							documentUser.opUser.userName,
					'	</div>'].join("");
				$(".js-opUser_"+processId).html(opUserHtml);
			}
		}else
			{
			$(".js-opUser_"+processId).html("");
			}
		var otherOpUser = documentUser.otherOpUser;
		if(!jQuery.isEmptyObject(documentUser.otherOpUser))
			{
		var otherOpUserHtml="";
		if($(".js-opUser_"+processId).length==0)
		{
			if(!jQuery.isEmptyObject(documentUser.opUser))
			{
				otherOpUserHtml=['<div class="alert alert-info fade in" style="display: inline-block;width: 80px;height: 32px;line-height: 13px;font-size: 12px;padding-right: 4px;margin-right:10px;margin-bottom:5px;" data-value="'+documentUser.opUser.accountId+'">',
					'    <button class="close" data-dismiss="alert" style="line-height: 3px;color: white;font-size: 16px;opacity:1;">',
					'        ×',
					'    </button>',
					documentUser.opUser.userName,
					'	</div>'].join("");
			}
		}
		for(var i=0;i<otherOpUser.length;i++)
			{
			var tempHtml=['<div class="alert alert-info fade in" style="display: inline-block;width: 80px;height: 32px;line-height: 13px;font-size: 12px;padding-right: 4px;margin-right:10px;margin-bottom:5px;" data-value="'+otherOpUser[i].accountId+'">',
				'    <button class="close" data-dismiss="alert" style="line-height: 3px;color: white;font-size: 16px;opacity:1;">',
				'        ×',
				'    </button>',
				otherOpUser[i].userName,
				'	</div>'].join("");
			otherOpUserHtml+=tempHtml;
			}
		$(".js-otherOpUser_"+processId).html(otherOpUserHtml);
			}else
				{
				$(".js-otherOpUser_"+processId).html("");
				}
		$('#documentSelectUser').modal('hide');
		}
}

function doSelectOpUser(Obj)
{
	if($(Obj).is("a"))
		{
		var html="";
			if (isExist(".documentselecteduser", $(Obj).parent("div .documentselectdiv").attr("data-value"))) {
				html="<div class=\"documentselecteddiv isOpUser\" onclick=\"doUnDocumentSelectUser(this);\"  data-name=\""
								+ $(Obj).parent("div .documentselectdiv").attr("data-name") + "\" data-value=\""
								+ $(Obj).parent("div .documentselectdiv").attr("data-value") + "\"><img src=\""
								+ $(Obj).parent("div .documentselectdiv").find("img").attr("src") + "\"><span>"
								+ $(Obj).parent("div .documentselectdiv").attr("data-name") + "<span class=\"opuserspan\">[主办]</span></span>" +
								"<a onclick=\"unsetOpUser(this);\" class=\"btn btn-blue btn-xs\" style=\"float:right;line-height:22px;\">经办</a></div>";
			resetOpUser();
			$(Obj).parent("div .documentselectdiv").remove();
			$(".documentselecteduser").prepend(html)
			}
			event.stopPropagation();
		}else
		{
			var html="";
			if (isExist(".documentselecteduser", $(Obj).parent("div .documentselectdiv").attr("data-value"))) {
				html="<div class=\"documentselecteddiv\" onclick=\"doUnDocumentSelectUser(this);\"  data-name=\""
						+ $(Obj).attr("data-name") + "\" data-value=\""
						+ $(Obj).attr("data-value") + "\"><img src=\""
						+ $(Obj).find("img").attr("src") + "\"><span>"
						+ $(Obj).attr("data-name") + "<span style=\"margin-left:10px;color:blue;\">[经办]</span></span>" +
						"<a onclick=\"setOpUser(this);\" class=\"btn btn-purple btn-xs\" style=\"float:right;line-height:22px;\">主办</a></div>";
			$(Obj).remove();
			$(".documentselecteduser").append(html)
			}
			event.stopPropagation();
		}
}

function initDocumentUserSelect() {
	$("#orgselectdiv").html(documentUserSelectModal);
}

function doUnDocumentSelectUser(Obj)
{
	$(".documentselectuser").append(
			"<div class=\"documentselectdiv\" onclick=\"doSelectOpUser(this);\" style=\"padding-right:10px\" data-name=\""
					+$(Obj).attr("data-name")
					+ "\" data-value=\""
					+ $(Obj).attr("data-value") 
					+ "\"><img src=\"" + $(Obj).find("img").attr("src")
					+ "\"><span>"
					+ $(Obj).attr("data-name")
					+ "</span><a onclick=\"doSelectOpUser(this);\" class=\"btn btn-purple btn-xs\" style=\"float:right;line-height:22px;\">主办</a></div>");
	$(Obj).remove();
}


function unsetOpUser(Obj)
{
	if($(Obj).is("a"))
	{
	html="<div class=\"documentselecteddiv\" onclick=\"doUnDocumentSelectUser(this);\"  data-name=\""
		+ $(Obj).parent("div .documentselecteddiv").attr("data-name") + "\" data-value=\""
		+ $(Obj).parent("div .documentselecteddiv").attr("data-value") + "\"><img src=\""
		+$(Obj).parent("div .documentselecteddiv").find("img").attr("src") + "\"><span>"
		+ $(Obj).parent("div .documentselecteddiv").attr("data-name") + "<span style=\"margin-left:10px;color:blue;\">[经办]</span></span>" +
		"<a onclick=\"setOpUser(this);\" class=\"btn btn-purple btn-xs\" style=\"float:right;line-height:22px;\">主办</a></div>";
	$(Obj).parent("div .documentselecteddiv").remove();
	$(".documentselecteduser").append(html);
	event.stopPropagation();
	}
}

function setOpUser(Obj)
{
	if($(Obj).is("a"))
	{
		var html="<div class=\"documentselecteddiv isOpUser\" onclick=\"doUnDocumentSelectUser(this);\"  data-name=\""
			+ $(Obj).parent("div .documentselecteddiv").attr("data-name") + "\" data-value=\""
			+ $(Obj).parent("div .documentselecteddiv").attr("data-value") + "\"><img src=\""
			+ $(Obj).parent("div .documentselecteddiv").find("img").attr("src") + "\"><span>"
			+ $(Obj).parent("div .documentselecteddiv").attr("data-name") + "<span class=\"opuserspan\">[主办]</span></span>" +
			"<a onclick=\"unsetOpUser(this);\" class=\"btn btn-blue btn-xs\" style=\"float:right;line-height:22px;\">经办</a></div>";
		$(Obj).parent("div .documentselecteddiv").remove();
		resetOpUser();
		$(".documentselecteduser").prepend(html)
		$(".documentselecteddiv").each(function() {
			//strs.push($(this).attr("data-value"));
		});
		event.stopPropagation();
	}
}

function resetOpUser()
{
	var elarr=[];
	$(".documentselecteddiv").each(function() {
		$(this).remove();
		var json={};
		json.userName=$(this).attr("data-name");
		json.accountId=$(this).attr("data-value");
		json.img=$(this).find("img").attr("src");
		elarr.push(json);
	});
	var html="";
	for(var i=0;i<elarr.length;i++)
		{
		html+="<div class=\"documentselecteddiv\" onclick=\"doUnDocumentSelectUser(this);\"  data-name=\""
			+ elarr[i].userName + "\" data-value=\""
			+elarr[i].accountId + "\"><img src=\""
			+elarr[i].img + "\"><span>"
			+elarr[i].userName + "<span style=\"margin-left:10px;color:blue;\">[经办]</span></span>" +
			"<a onclick=\"setOpUser(this);\" class=\"btn btn-purple btn-xs\" style=\"float:right;line-height:22px;\">主办</a></div>";
		}
	$(".documentselecteduser").append(html)
}


var documentUserSelectModal = [
	'<div id="documentSelectUser" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">',
	'        <div class="modal-dialog">',
	'            <div class="modal-content">',
	'                <div class="modal-header">',
	'                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>',
	'                    <h4 class="modal-title" id="myLargeModalLabel">经办人员选择</h4>',
	'                </div>',
	'                <div class="modal-body" style="padding: 0px;">',
	'                 <div class="row" style="margin: 10px">',
	'                 	<div class="col-xs-10 col-md-10"><input type="text" class="form-control" id="searchuser" placeholder="姓名,账号" ></div>',
	'                 	<div class="col-xs-2 col-md-2"><a href="javascript:void(0);" class="btn btn-darkorange js-searchuser" style="width:100%">查询</a></div>',
	'                 </div>',
	'	                <div class="row" style="margin: 0px">',
	'		                 <div class="col-xs-4 col-md-4" style="padding-left:2px;padding-right:10px;">',
	'		                  <div class="flat radius-bordered">',
	'                                <div class="widget-header bg-lightred" style="text-align: center;padding-left:0px;">',
	'                                    <span class="widget-caption" style="float:none;">权限内部门列表</span>',
	'                                </div>',
	'								<div class="widget-body-1">',
	'                                    <ul id="selectDocumentUsertree" class="ztree"></ul>',
	'                                </div>',
	'                            </div>',
	'		                 </div>',
	'		                 <div class="col-xs-4 col-md-4" style="padding-left:4px;padding-right:4px;">',
	'		                 <div class="flat radius-bordered">',
	'                                <div class="widget-header bg-blueberry" style="text-align: center;padding-left:0px;">',
	'                                    <span class="widget-caption" style="float:none;">备选人员</span>',
	'                                    <a class="selectuserall js-documentselectuserall">全选</a>',
	'                                </div>',
	'								<div class="widget-body-1 documentselectuser">',
	'									',
	'                                </div>',
	'                            </div>',
	'		                 </div>',
	'		                 <div class="col-xs-4 col-md-4" style="padding-right: 2px;padding-left:10px;">',
	'		                 <div class="flat radius-bordered">',
	'                                <div class="widget-header bg-palegreen" style="text-align: center;padding-left:0px;">',
	'                                    <span class="widget-caption" style="float:none;">已选人员</span>',
	'                                    <a class="selectuserall js-undocumentselectuserall">反选</a>',
	'                                </div>',
	'								<div class="widget-body-1 documentselecteduser">',
	'                                </div>',
	'                            </div>',
	'		                 ',
	'		                 </div>',
	'	                </div>',
	'                </div>',
	'                <div class="modal-footer">',
	'                   <button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>',
	'                  <button type="button" class="btn btn-primary documentSelectUserokbtn" >确定</button>',
	'               </div>', '            </div><!-- /.modal-content -->',
	'        </div><!-- /.modal-dialog -->', '    </div>' ].join("");


function isExist(c, a) {
	var strs = [];
	$(c + " div").each(function() {
		strs.push($(this).attr("data-value"));
	})
	if ($.inArray(a, strs) >= 0) {
		return false;
	} else {
		return true;
	}
}