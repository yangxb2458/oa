function bpmOpUserSelect(obj) {
	processId=$(obj).attr("data-value");
	var oprule = $(obj).attr("oprule");
	initBpmUserSelect();
	$("#orgselectdiv").modal("show");
	var orgSetig = {
		async : {
			enable : true,// 设置 zTree 是否开启异步加载模式
			url : "/ret/bpmget/getBpmOpUserDept?processId="+processId,// Ajax 获取数据的 URL 地址
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
	
	var zTree = $.fn.zTree.init($("#selectBpmUsertree"), orgSetig, topNode);// 初始化树节点时，添加同步获取的数据
	var nodes = zTree.getNodes();
	for (var i = 0; i < nodes.length; i++) {
		zTree.expandNode(nodes[i], true, false, false);// 默认展开第一级节点
	}
	function toselectuserclick(event, treeId, treeNode) {
		$(".bpmselectuser").empty();
		$.ajax({
			url : "/ret/bpmget/getBpmOpUseByPorcess?processId="+processId,
			type : "POST",
			dataType : "json",
			data : {
				deptId : treeNode.deptId
			},
			success : function(data) {
				if (data.status == 200) {
					var userlist = data.list;
					for (var i = 0; i < userlist.length; i++) {
						if (isExist(".bpmselecteduser", userlist[i].accountId)) {
							var headimg = "/gobal/img/org/U01.png";
							if (userlist[i].sex == "女") {
								headimg = "/gobal/img/org/U11.png"
							}
							$(".bpmselectuser").append(
									"<div class=\"bpmselectdiv\" onclick=\"doSelectOpUser(this);\" style=\"padding-right:10px\" data-name=\""
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
	$('#bpmSelectUser').modal('show');
	
	$(".bpmSelectUserokbtn").unbind("click").click(function(){
		setSelectBpmOpUser();
	});
}

function setSelectBpmOpUser()
{
	var bpmUser={};
	var opUser={};
	var otherOpUser=[];
	$(".bpmselecteduser").find(".bpmselecteddiv").each(function(){
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
	bpmUser.opUser=opUser;
	bpmUser.otherOpUser=otherOpUser;
	if(bpmUser)
		{
		if(!jQuery.isEmptyObject(bpmUser.opUser))
		{
			if($(".js-opUser_"+processId).length>0)
			{
				var opUserHtml=['<div class="alert alert-danger fade in" style="width: 80px;height: 32px;line-height: 13px;font-size: 12px;padding-right: 4px;margin-bottom:0px;" data-value="'+bpmUser.opUser.accountId+'">',
					'    <button class="close" data-dismiss="alert" style="line-height: 3px;color: white;font-size: 16px;opacity:1;">',
					'        ×',
					'    </button>',
							bpmUser.opUser.userName,
					'	</div>'].join("");
				$(".js-opUser_"+processId).html(opUserHtml);
			}
		}else
			{
			$(".js-opUser_"+processId).html("");
			}
		var otherOpUser = bpmUser.otherOpUser;
		if(!jQuery.isEmptyObject(bpmUser.otherOpUser))
			{
		var otherOpUserHtml="";
		if($(".js-opUser_"+processId).length==0)
		{
			if(!jQuery.isEmptyObject(bpmUser.opUser))
			{
				otherOpUserHtml=['<div class="alert alert-info fade in" style="display: inline-block;width: 80px;height: 32px;line-height: 13px;font-size: 12px;padding-right: 4px;margin-right:10px;margin-bottom:5px;" data-value="'+bpmUser.opUser.accountId+'">',
					'    <button class="close" data-dismiss="alert" style="line-height: 3px;color: white;font-size: 16px;opacity:1;">',
					'        ×',
					'    </button>',
					bpmUser.opUser.userName,
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
		$('#bpmSelectUser').modal('hide');
		}
}

function doSelectOpUser(Obj)
{
	if($(Obj).is("a"))
		{
		var html="";
			if (isExist(".bpmselecteduser", $(Obj).parent("div .bpmselectdiv").attr("data-value"))) {
				html="<div class=\"bpmselecteddiv isOpUser\" onclick=\"doUnBpmSelectUser(this);\"  data-name=\""
								+ $(Obj).parent("div .bpmselectdiv").attr("data-name") + "\" data-value=\""
								+ $(Obj).parent("div .bpmselectdiv").attr("data-value") + "\"><img src=\""
								+ $(Obj).parent("div .bpmselectdiv").find("img").attr("src") + "\"><span>"
								+ $(Obj).parent("div .bpmselectdiv").attr("data-name") + "<span class=\"opuserspan\">[主办]</span></span>" +
								"<a onclick=\"unsetOpUser(this);\" class=\"btn btn-blue btn-xs\" style=\"float:right;line-height:22px;\">经办</a></div>";
			resetOpUser();
			$(Obj).parent("div .bpmselectdiv").remove();
			$(".bpmselecteduser").prepend(html)
			}
			event.stopPropagation();
		}else
		{
			var html="";
			if (isExist(".bpmselecteduser", $(Obj).parent("div .bpmselectdiv").attr("data-value"))) {
				html="<div class=\"bpmselecteddiv\" onclick=\"doUnBpmSelectUser(this);\"  data-name=\""
						+ $(Obj).attr("data-name") + "\" data-value=\""
						+ $(Obj).attr("data-value") + "\"><img src=\""
						+ $(Obj).find("img").attr("src") + "\"><span>"
						+ $(Obj).attr("data-name") + "<span style=\"margin-left:10px;color:blue;\">[经办]</span></span>" +
						"<a onclick=\"setOpUser(this);\" class=\"btn btn-purple btn-xs\" style=\"float:right;line-height:22px;\">主办</a></div>";
			$(Obj).remove();
			$(".bpmselecteduser").append(html)
			}
			event.stopPropagation();
		}
}

function initBpmUserSelect() {
	$("#orgselectdiv").html(bpmUserSelectModal);
}

function doUnBpmSelectUser(Obj)
{
	$(".bpmselectuser").append(
			"<div class=\"bpmselectdiv\" onclick=\"doSelectOpUser(this);\" style=\"padding-right:10px\" data-name=\""
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
	html="<div class=\"bpmselecteddiv\" onclick=\"doUnBpmSelectUser(this);\"  data-name=\""
		+ $(Obj).parent("div .bpmselecteddiv").attr("data-name") + "\" data-value=\""
		+ $(Obj).parent("div .bpmselecteddiv").attr("data-value") + "\"><img src=\""
		+$(Obj).parent("div .bpmselecteddiv").find("img").attr("src") + "\"><span>"
		+ $(Obj).parent("div .bpmselecteddiv").attr("data-name") + "<span style=\"margin-left:10px;color:blue;\">[经办]</span></span>" +
		"<a onclick=\"setOpUser(this);\" class=\"btn btn-purple btn-xs\" style=\"float:right;line-height:22px;\">主办</a></div>";
	$(Obj).parent("div .bpmselecteddiv").remove();
	$(".bpmselecteduser").append(html);
	event.stopPropagation();
	}
}

function setOpUser(Obj)
{
	if($(Obj).is("a"))
	{
		var html="<div class=\"bpmselecteddiv isOpUser\" onclick=\"doUnBpmSelectUser(this);\"  data-name=\""
			+ $(Obj).parent("div .bpmselecteddiv").attr("data-name") + "\" data-value=\""
			+ $(Obj).parent("div .bpmselecteddiv").attr("data-value") + "\"><img src=\""
			+ $(Obj).parent("div .bpmselecteddiv").find("img").attr("src") + "\"><span>"
			+ $(Obj).parent("div .bpmselecteddiv").attr("data-name") + "<span class=\"opuserspan\">[主办]</span></span>" +
			"<a onclick=\"unsetOpUser(this);\" class=\"btn btn-blue btn-xs\" style=\"float:right;line-height:22px;\">经办</a></div>";
		$(Obj).parent("div .bpmselecteddiv").remove();
		resetOpUser();
		$(".bpmselecteduser").prepend(html)
		$(".bpmselecteddiv").each(function() {
			//strs.push($(this).attr("data-value"));
		});
		event.stopPropagation();
	}
}

function resetOpUser()
{
	var elarr=[];
	$(".bpmselecteddiv").each(function() {
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
		html+="<div class=\"bpmselecteddiv\" onclick=\"doUnBpmSelectUser(this);\"  data-name=\""
			+ elarr[i].userName + "\" data-value=\""
			+elarr[i].accountId + "\"><img src=\""
			+elarr[i].img + "\"><span>"
			+elarr[i].userName + "<span style=\"margin-left:10px;color:blue;\">[经办]</span></span>" +
			"<a onclick=\"setOpUser(this);\" class=\"btn btn-purple btn-xs\" style=\"float:right;line-height:22px;\">主办</a></div>";
		}
	$(".bpmselecteduser").append(html)
}


var bpmUserSelectModal = [
	'<div id="bpmSelectUser" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">',
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
	'                                    <ul id="selectBpmUsertree" class="ztree"></ul>',
	'                                </div>',
	'                            </div>',
	'		                 </div>',
	'		                 <div class="col-xs-4 col-md-4" style="padding-left:4px;padding-right:4px;">',
	'		                 <div class="flat radius-bordered">',
	'                                <div class="widget-header bg-blueberry" style="text-align: center;padding-left:0px;">',
	'                                    <span class="widget-caption" style="float:none;">备选人员</span>',
	'                                    <a class="selectuserall js-bpmselectuserall">全选</a>',
	'                                </div>',
	'								<div class="widget-body-1 bpmselectuser">',
	'									',
	'                                </div>',
	'                            </div>',
	'		                 </div>',
	'		                 <div class="col-xs-4 col-md-4" style="padding-right: 2px;padding-left:10px;">',
	'		                 <div class="flat radius-bordered">',
	'                                <div class="widget-header bg-palegreen" style="text-align: center;padding-left:0px;">',
	'                                    <span class="widget-caption" style="float:none;">已选人员</span>',
	'                                    <a class="selectuserall js-unbpmselectuserall">反选</a>',
	'                                </div>',
	'								<div class="widget-body-1 bpmselecteduser">',
	'                                </div>',
	'                            </div>',
	'		                 ',
	'		                 </div>',
	'	                </div>',
	'                </div>',
	'                <div class="modal-footer">',
	'                   <button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>',
	'                  <button type="button" class="btn btn-primary bpmSelectUserokbtn" >确定</button>',
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