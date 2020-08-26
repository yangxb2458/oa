var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/unitget/getSysMenuTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "sysMenuId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onClick : zTreeOnClick
	},
	view: {
		selectedMulti: false
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "sysMenuId",
			pIdKey : "sysMenuLeave",
			rootPId : "0"
		},
		key : {
			name : "sysMenuName"
		}
	}
};
var setting1 = {
		async : {
			enable : true,// 设置 zTree 是否开启异步加载模式
			url : "/ret/unitget/getSysMenuTree",// Ajax 获取数据的 URL 地址
			autoParam : [ "sysMenuId" ],// 异步加载时需要自动提交父节点属性的参数
		},
		view : {
			dblClickExpand : false,
			selectedMulti: false
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "sysMenuId",
				pIdKey : "sysMenuLeave",
				rootPId : "0"
			},
			key : {
				name : "sysMenuName"
			}
		},
		callback : {
			onClick : function(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("menuTree"), nodes = zTree
						.getSelectedNodes(), v = "";
				vid = "";
				nodes.sort(function compare(a, b) {
					return a.id - b.id;
				});
				for (var i = 0, l = nodes.length; i < l; i++) {
					v += nodes[i].sysMenuName + ",";
					vid += nodes[i].sysMenuId + ",";
				}
				if (v.length > 0)
					v = v.substring(0, v.length - 1);
				var nameem = $("#sysMenuLeaveName");
				nameem.val(v);
				if (vid.length > 0)
					vid = vid.substring(0, vid.length - 1);
				var idem = $("#sysMenuLeave");
				idem.val(vid);
			}
		}
	};
	
$(function() {
	$.ajax({
		url : "/ret/unitget/getSysMenuTree",
		type : "post",
		dataType : "json",
		success : function(data) {
			zTree = $.fn.zTree.init($("#tree"), setting, data);// 初始化树节点时，添加同步获取的数据
			var topNode1 = [ {
				sysMenuName : "顶层菜单",
				sysMenuLeave : '',
				isParent : "true",
				sysMenuId : "0",
				icon : "/gobal/img/org/org.png"
			} ];
			var zTreeObj = $.fn.zTree.init($("#menuTree"), setting1,topNode1);
			var nodes1 = zTreeObj.getNodes();
			for (var i = 0; i < nodes1.length; i++) {
				zTreeObj.expandNode(nodes1[i], true, false, false);// 默认展开第一级节点
			}
		}
	});
	$("#cbut").unbind("click").click(function(){
		document.getElementById("form").reset();
		$("#createbut").show();
		$("#updatabut").hide();
		$("#delbut").hide();
	});
	$("#delbut").unbind("click").click(function(){
		delSysMenu();
	});
	$("#updatabut").unbind("click").click(function(){
		updateSysMenu();
	});
	$("#createbut").unbind("click").click(function(){
		insertSysMenu();
	});
	$("#sysMenuLeaveName").unbind("click").click(function(e){
		e.stopPropagation();
		$("#menuContent").css({
			"width":$(this).outerWidth()+"px"
		}).slideDown(200);
	});
	$("body").unbind("click").click(function(){
		$("#menuContent").hide();
	});

	$("#menuContent").unbind("click").click(function(e){
		e.stopPropagation();
	});
});
function updateSysMenu()
{
	if($("#sysMenuId").val()==$("#sysMenuLeave").val())
	{
		top.layer.msg("父级分类不能为自身，请重新选择！")
	}
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/unitset/updateSysMenu",
		type : "post",
		dataType : "json",
		data:{
			sysMenuId:$("#sysMenuId").val(),
			sortNo:$("#sortNo").val(),
			sysMenuCode:$("#sysMenuCode").val(),
			sysMenuName:$("#sysMenuName").val(),
			sysMenuUrl:$("#sysMenuUrl").val(),
			sysMenuPic:$("#sysMenuPic").val(),
			sysMenuLeave:$("#sysMenuLeave").val(),
			sysMenuParm:$("#sysMenuParm").val(),
			sysMenuOpen:$('input:radio[name=sysMenuOpen]:checked').val()
		},
		success : function(data) {
			if(data.status==200){
				top.layer.msg(data.msg);
				location.reload();
			}else if(data.status==100)
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
	});
	}
}
function insertSysMenu()
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/unitset/insertSysMenu",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			sysMenuCode:$("#sysMenuCode").val(),
			sysMenuName:$("#sysMenuName").val(),
			sysMenuUrl:$("#sysMenuUrl").val(),
			sysMenuPic:$("#sysMenuPic").val(),
			sysMenuLeave:$("#sysMenuLeave").val(),
			sysMenuParm:$("#sysMenuParm").val(),
			sysMenuOpen:$('input:radio[name=sysMenuOpen]:checked').val()
		},
		success : function(data) {
			if(data.status==200){
				top.layer.msg(data.msg);
				location.reload();
			}else if(data.status==100)
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
	});
	}
}
function delSysMenu()
{
	 if(confirm("确定删除当前菜单吗？"))
	    {
	$.ajax({
		url : "/set/unitset/delSysMenu",
		type : "post",
		dataType : "json",
		data:{
			sysMenuId:$("#sysMenuId").val()
		},
		success : function(data) {
			if(data.status==200){
				top.layer.msg(data.msg);
				location.reload();
			}else if(data.status==100)
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
	});
	    }else
	    	{
	    	return;
	    	}
}
function zTreeOnClick(event, treeId, treeNode)
{
	$.ajax({
		url : "/ret/unitget/getSysMenuById",
		type : "post",
		dataType : "json",
		data:{sysMenuId:treeNode.sysMenuId},
		success : function(data) {
			if(data.status==200)
			{
			var v = data.list;
			for(name in v)
				{
				if(name=="sysMenuLeave")
					{
					$("#"+name).val(v[name]);
					$.ajax({
						url : "/ret/unitget/getSysMenuById",
						type : "post",
						dataType : "json",
						data:{
							sysMenuId:v[name]
						},
						success : function(data) {
							if(data.status=="200")
								{
								if(data.list)
									{
									$("#sysMenuLeaveName").val(data.list.sysMenuName);
									}else
										{
										$("#sysMenuLeaveName").val("顶层菜单");
										}
								}else if(data.status=="100")
								{
									top.layer.msg(data.msg);
								}else
									{
									console.log(data.msg);
									}
						}
					});
					}else if(name=="sysMenuOpen")
						{
						$("[name='sysMenuOpen'][value='"+v[name]+"']").prop("checked", "checked");
						}else
						{
							$("#"+name).val(v[name]);
						}
				}
			}else if(data.status==100)
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
	});
	$("#createbut").hide();
	$("#updatabut").show();
	$("#delbut").show();
	}