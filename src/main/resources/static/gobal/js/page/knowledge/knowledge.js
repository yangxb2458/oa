var setting1 = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/knowledgeget/getknowledgeSortTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "sortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	view : {
		dblClickExpand : false,
		selectedMulti : false
	//禁止多选
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "sortId",
			pIdKey : "sortLeave",
			rootPId : "0"
		},
		key : {
			name : "sortName"
		}
	},
	callback : {
		onClick : function(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("menuTree"), nodes = zTree.getSelectedNodes(), v = "";
			vid = "";
			nodes.sort(function compare(a, b) {
				return a.id - b.id;
			});
			for (var i = 0, l = nodes.length; i < l; i++) {
				v += nodes[i].sortName + ",";
				vid += nodes[i].sortId + ",";
			}
			if (v.length > 0)
				v = v.substring(0, v.length - 1);
			if (vid.length > 0)
				vid = vid.substring(0, vid.length - 1);
			var idem = $("#sortId");
			idem.attr("data-value",vid);
			idem.val(v);
		}
	}
};
$(function(){
	 $('#content').summernote({ height:300 });
	getSmsConfig("msgType","knowledge");
	$("#createbut").unbind("click").click(function(){
		addKnowledge();
	});
	$.ajax({
		url : "/ret/knowledgeget/getknowledgeSortTree",
		type : "post",
		dataType : "json",
		success : function(data) {
			$.fn.zTree.init($("#menuTree"), setting1, data);
		}
	});
	$("#sortId").unbind("click").click(function(e) {
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
});
function addKnowledge()
{
	$.ajax({
		url : "/set/knowledgeset/insertKnowledge",
		type : "post",
		dataType : "json",
		data:{
			title:$("#title").val(),
			sortNo:$("#sortNo").val(),
			content:$("#content").code(),
			sortId:$("#sortId").attr("data-value"),
			keywords:$("#keywords").val(),
			attach:$("#knowledgeattach").attr("data_value"),
			version:$("#version").val(),
			msgType:getCheckBoxValue("msgType")
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
				window.location.reload();
				top.layer.msg(data.msg);
				}
		}
	})
}