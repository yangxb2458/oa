var setting1 = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/bpmget/getBpmSortTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "bpmSortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	view : {
		dblClickExpand : false,
		selectedMulti : false
	// 禁止多选
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "bpmSortId",
			rootPId : "0"
		},
		key : {
			name : "bpmSortName"
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
				v += nodes[i].bpmSortName + ",";
				vid += nodes[i].bpmSortId + ",";
			}
			if (v.length > 0)
				v = v.substring(0, v.length - 1);
			if (vid.length > 0)
				vid = vid.substring(0, vid.length - 1);
			var idem = $("#sortId");
			idem.val(v);
			idem.attr("data-value", vid);
		}
	}
};

$(function() {
	$(".js-query-but").unbind("click").click(function() {
		$("#myTable").bootstrapTable("refresh");
	});
	getAllBpmFlowList();

	$.ajax({
		url : "/ret/bpmget/getBpmSortTree",
		type : "post",
		dataType : "json",
		data : {
			leaveId : "0"
		},
		success : function(data) {
			var topNode = [ {
				bpmSortName : "全部",
				isParent : "fase",
				bpmSortId : ""
			} ];
			var newTreeNodes = topNode.concat(data);
			$.fn.zTree.init($("#menuTree"), setting1, newTreeNodes);
		}
	});
	$("#sortId").unbind("click").click(function(e) {
		e.stopPropagation();
		$("#menuContent").css({
			"width" : $(this).outerWidth() + "px",
			"left" : (document.getElementById("sortId").offsetLeft - 40) + "px"
		}).slideDown(200);
	});

	$("body").unbind("click").click(function() {
		$("#menuContent").hide();
	});

	$("#menuContent").unbind("click").click(function(e) {
		e.stopPropagation();
	});
	query();
	$(".js-update").unbind("click").click(function(){
		 if(confirm("这将覆盖之前的内容！您确定需要野更新所选择的步骤缓存吗？"))
		 {
			var h=$('#myTable').bootstrapTable('getAllSelections');
			var ids=[];//如果你想获得每个选中行的ID,如下操作
			for(var i=0;i<h.length;i++){
			var json ={};
			json.processId = h[i].processId;
			json.formId = h[i].formId;
			ids.push(json);
			}
			if(ids.length<1)
			{
				top.layer.msg("请选择需要更缓存的步骤！")
			}else
			{
				$.ajax({
					url : "/set/bpmset/batchUpdateFormCache",
					type : "post",
					dataType : "json",
					data:{paramStr:JSON.stringify(ids)},
					success : function(data) {
						if (data.status == "200") {
							top.layer.msg(data.msg);
						} else if (data.status == "100") {
							top.layer.msg(data.msg);
						} else {
							console.log(data.msg);
						}
					}
				});
			}
		 }
	})
	$('#content').summernote({ height:300 });
})
function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/bpmget/getBpmCacheProcessList',
		method : 'post',
		contentType : 'application/x-www-form-urlencoded',
		toolbar : '#toobar',//工具列
		striped : true,//隔行换色
		cache : false,//禁用缓存
		pagination : true,//启动分页
		sidePagination : 'server',//分页方式
		pageNumber : 1,//初始化table时显示的页码
		pageSize : 10,//每页条目
		showFooter : false,//是否显示列脚
		showPaginationSwitch : true,//是否显示 数据条数选择框
		sortable : true,//排序
		search : true,//启用搜索
		showColumns : true,//是否显示 内容列下拉框
		showRefresh : true,//显示刷新按钮
		idField : 'processId',//key值栏位
		clickToSelect : true,//点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],//可选择单页记录数
		queryParams : queryParams,
		columns : [{
		      checkbox: true
	      }, {
			field : 'num',
			title : '序号',//标题  可不加
			width : '50px',
			formatter : function(value, row, index) {
				return index + 1;
			}
		}, {
			field : 'flowName',
			title : 'BPM名称',
			sortable : true,
			width : '100px'
		}, {
			field : 'formTitle',
			title : '表单名称',
			sortable : true,
			width : '100px'
		}, {
			field : 'prcsName',
			title : '步骤名称',
			sortable : true,
			width : '50px'
		}, {
			field : 'prcsType',
			width : '50px',
			title : '步骤类型',
			formatter : function(value, row, index){
				if(value=="1")
				{
					return "开始节点";
				}else if(value=="2")
				{
					return "结束节点";
				}else if(value=="3")
				{
					return "普通节点";
				}else if(value==="6")
				{
					return "子流程节点";
				}else if(value=="4")
				{
					return "聚合节点";
				}
			}
		},{
			field : 'createUserName',
			width : '50px',
			title : '创建人'
		}, {
			field : 'createTime',
			width : '100px',
			title : '创建时间'
		},{
			field : 'opt',
			width : '200px',
			align : 'center',
			title : '移动端缓存',
			formatter : function(value, row, index) {
				return createOptBtnMobile(row.processId, row.formId);
			}
		},{
			field : 'opt',
			width : '200px',
			align : 'center',
			title : 'PC缓存',
			formatter : function(value, row, index) {
				return createOptBtnPc(row.processId, row.formId);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
			//alert(row.SystemDesc);
		},
		responseHandler : function(res) {
			if (res.status == "500") {
				console.log(res.msg);
			} else if (res.status == "100") {
				top.layer.msg(res.msg);
			} else {
				return {
					total : res.list.total, //总页数,前面的key必须为"total"
					rows : res.list.list
				//行数据，前面的key要与之前设置的dataField的值一致.
				};
			}
		}
	});
}
function queryParams(params) {
	var temp = {
		search : params.search,
		pageSize : this.pageSize,
		pageNumber : this.pageNumber,
		sort : params.sort,
		sortOrder : params.order,
		flowId : $("#flowId").val(),
		sortId : $("#sortId").attr("data-value")
	};
	return temp;
};
function createOptBtnPc(processId,formId) {
	var html = "<a href=\"javascript:void(0);previewPc('" + processId + "')\" class=\"btn btn-sky btn-xs\" >预览缓存</a>&nbsp;&nbsp;" + 
			   "<a href=\"javascript:void(0);editHtml('" + processId + "')\" class=\"btn btn-success btn-xs\" >编辑Html</a>&nbsp;&nbsp;"+
			   "<a href=\"javascript:void(0);editStyle('" + processId + "')\" class=\"btn btn-azure btn-xs\" >编辑Styl</a>&nbsp;&nbsp;"+
			   "<a href=\"javascript:void(0);editScript('" + processId + "')\" class=\"btn btn-darkorange btn-xs\" >编辑Js</a>";
	return html;
}

function createOptBtnMobile(processId,formId) {
	var html = "<a href=\"javascript:void(0);previewMobile('" + processId + "')\" class=\"btn btn-sky btn-xs\" >预览缓存</a>&nbsp;&nbsp;" + 
			   "<a href=\"javascript:void(0);editMobileHtml('" + processId + "')\" class=\"btn btn-success btn-xs\" >编辑Html</a>&nbsp;&nbsp;"+
			   "<a href=\"javascript:void(0);editMobileStyle('" + processId + "')\" class=\"btn btn-azure btn-xs\" >编辑Styl</a>&nbsp;&nbsp;"+
			   "<a href=\"javascript:void(0);editMobileScript('" + processId + "')\" class=\"btn btn-darkorange btn-xs\" >编辑Js</a>";
	return html;
}

function previewPc(processId)
{
	window.open("/app/core/bpm/bpmFormTemplateCache?processId="+processId);
}

function previewMobile(processId)
{
	window.open("/app/core/bpm/bpmMobileFormTemplateCache?processId="+processId);
}

function editScript(processId)
{
	$.ajax({
		url : "/ret/bpmget/getBpmFormCacheFile",
		type : "post",
		dataType : "json",
		data:{processId:processId,type:'script'},
		success : function(data) {
			if(data.status=="200")
			{
				$("#setOtherMoadl").modal("show");
				$("#otherPrcsName").html(data.list.prcsName);
				$("#remark").val(data.list.value);
				$(".js-other-save").unbind("click").click(function(){
					setScriptCache(processId);
				})
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
}
function editMobileScript(processId)
{
	$.ajax({
		url : "/ret/bpmget/getBpmFormCacheFile",
		type : "post",
		dataType : "json",
		data:{processId:processId,type:'mobilescript'},
		success : function(data) {
			if(data.status=="200")
			{
				$("#setOtherMoadl").modal("show");
				$("#otherPrcsName").html(data.list.prcsName);
				$("#remark").val(data.list.value);
				$(".js-other-save").unbind("click").click(function(){
					setMobileScriptCache(processId);
				})
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
}

function editStyle(processId)
{
	$.ajax({
		url : "/ret/bpmget/getBpmFormCacheFile",
		type : "post",
		dataType : "json",
		data:{processId:processId,type:'style'},
		success : function(data) {
			if(data.status=="200")
			{
				$("#setOtherMoadl").modal("show");
				$("#otherPrcsName").html(data.list.prcsName);
				$("#remark").val(data.list.value);
				$(".js-other-save").unbind("click").click(function(){
					setStyleCache(processId);
				})
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
}

function editMobileStyle(processId)
{
	$.ajax({
		url : "/ret/bpmget/getBpmFormCacheFile",
		type : "post",
		dataType : "json",
		data:{processId:processId,type:'mobilestyle'},
		success : function(data) {
			if(data.status=="200")
			{
				$("#setOtherMoadl").modal("show");
				$("#otherPrcsName").html(data.list.prcsName);
				$("#remark").val(data.list.value);
				$(".js-other-save").unbind("click").click(function(){
					setMobileStyleCache(processId);
				})
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
}

function editHtml(processId)
{
	$.ajax({
		url : "/ret/bpmget/getBpmFormCacheFile",
		type : "post",
		dataType : "json",
		data:{processId:processId,type:'html'},
		success : function(data) {
			if(data.status=="200")
			{
				$("#setHtmlMoadl").modal("show");
				$("#prcsName").html(data.list.prcsName);
				$("#content").code(data.list.value);
				$(".js-save").unbind("click").click(function(){
					setHtmlCache(processId);
				})
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
}

function editMobileHtml(processId)
{
	$.ajax({
		url : "/ret/bpmget/getBpmFormCacheFile",
		type : "post",
		dataType : "json",
		data:{processId:processId,type:'mobilehtml'},
		success : function(data) {
			if(data.status=="200")
			{
				$("#setHtmlMoadl").modal("show");
				$("#prcsName").html(data.list.prcsName);
				$("#content").code(data.list.value);
				$(".js-save").unbind("click").click(function(){
					setMobileHtmlCache(processId);
				})
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
}


function setHtmlCache(processId)
{
	if(confirm("这将覆盖之前的内容！您确定需要野更新所选择的步骤缓存吗？"))
	 {
	$.ajax({
		url : "/set/bpmset/updateFormCache",
		type : "post",
		dataType : "json",
		data:{processId:processId,type:'html',value:$("#content").code()},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#setHtmlMoadl").modal("hide");
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
	 }
}
function setMobileHtmlCache(processId)
{
	if(confirm("这将覆盖之前的内容！您确定需要野更新所选择的步骤缓存吗？"))
	 {
	$.ajax({
		url : "/set/bpmset/updateFormCache",
		type : "post",
		dataType : "json",
		data:{processId:processId,type:'mobilehtml',value:$("#content").code()},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#setHtmlMoadl").modal("hide");
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
	 }
}
function setStyleCache(processId)
{
	if(confirm("这将覆盖之前的内容！您确定需要野更新所选择的步骤缓存吗？"))
	 {
	$.ajax({
		url : "/set/bpmset/updateFormCache",
		type : "post",
		dataType : "json",
		data:{processId:processId,type:'style',value:$("#remark").val()},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#setOtherMoadl").modal("hide");
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
	 }
}
function setMobileStyleCache(processId)
{
	if(confirm("这将覆盖之前的内容！您确定需要野更新所选择的步骤缓存吗？"))
	 {
	$.ajax({
		url : "/set/bpmset/updateFormCache",
		type : "post",
		dataType : "json",
		data:{processId:processId,type:'mobilestyle',value:$("#remark").val()},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#setOtherMoadl").modal("hide");
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
	 }
}
function setScriptCache(processId)
{
	if(confirm("这将覆盖之前的内容！您确定需要野更新所选择的步骤缓存吗？"))
	 {
	$.ajax({
		url : "/set/bpmset/updateFormCache",
		type : "post",
		dataType : "json",
		data:{processId:processId,type:'script',value:$("#remark").val()},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#setOtherMoadl").modal("hide");
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
	 }
}
function setMobileScriptCache(processId)
{
	if(confirm("这将覆盖之前的内容！您确定需要野更新所选择的步骤缓存吗？"))
	 {
	$.ajax({
		url : "/set/bpmset/updateFormCache",
		type : "post",
		dataType : "json",
		data:{processId:processId,type:'mobilescript',value:$("#remark").val()},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#setOtherMoadl").modal("hide");
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
	 }
}
function getAllBpmFlowList() {
	$.ajax({
		url : "/ret/bpmget/getAllBpmFlowList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == "200") {
				var html = "<option value=''>全部</option>"
				for (var i = 0; i < data.list.length; i++) {
					html += "<option value='" + data.list[i].flowId + "'>" + data.list[i].flowName + "</option>"
				}
				$("#flowId").html(html);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}