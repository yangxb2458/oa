var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/bpmget/getBpmFormSortTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "bpmSortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onClick : zTreeOnClick
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
	}
};
var setting1 = {
		async : {
			enable : true,// 设置 zTree 是否开启异步加载模式
			url : "/ret/bpmget/getBpmSortTree",// Ajax 获取数据的 URL 地址
			autoParam : [ "bpmSortId" ],// 异步加载时需要自动提交父节点属性的参数
		},
		view : {
			dblClickExpand : false,
			selectedMulti: false//禁止多选
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
				$("#form").data("bootstrapValidator").updateStatus("bpmTypeName", "NOT_VALIDATED", null );//更新指定的字段
				var zTree = $.fn.zTree.getZTreeObj("menuTree"), nodes = zTree
						.getSelectedNodes(), v = "";
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
				var nameem = $("#bpmTypeName");
				nameem.val(v);
				if (vid.length > 0)
					vid = vid.substring(0, vid.length - 1);
				var idem = $("#bpmTypeId");
				idem.val(vid);
				$("#form").bootstrapValidator("validateField","bpmTypeName");
			}
		}
	};
	


$(function () {
	$.ajax({
		url : "/ret/bpmget/getBpmFormSortTree",
		type : "post",
		dataType : "json",
		data:{bpmSortId:"0"},
		success : function(data) {
				zTree = $.fn.zTree.init($("#tree"), setting, data);// 初始化树节点时，添加同步获取的数据
		}
	});
	$.ajax({
		url : "/ret/bpmget/getBpmSortTree",
		type : "post",
		dataType : "json",
		data:{leaveId:"0"},
		success : function(data) {
				$.fn.zTree.init($("#menuTree"), setting1,data);
		}
	});
	$("#cbut").unbind("click").click(function(){
		$("#form").bootstrapValidator('resetForm'); //重置
		document.getElementById("form").reset();
		if(bpmtable=="auto")
		{
			$("#tableName").attr("readonly","readonly").val(Date.parse(new Date())/1000);
		}
		$("#createbut").show();
		$("#updatabut").hide();
		$("#delbut").hide();
		$("#designerbut").hide();
		$("#infodiv").show();
		$("#tablediv").hide();
	});
	$("#createbut").unbind("click").click(function(){
		$("#form").bootstrapValidator('validate');
		var flag = $('#form').data('bootstrapValidator').isValid();
		if (flag) {
		$.ajax({
			url : "/set/bpmset/insertBpmForm",
			type : "post",
			dataType : "json",
			data:{
				sortNo:$("#sortNo").val(),
				formTitle:$("#formTitle").val(),
				tableName:$("#tableName").val(),
				bpmTypeId:$("#bpmTypeId").val()
				},
			success : function(data) {
				if(data.status==500)
					{
					console.log(data.msg);
					}else
						{
						top.layer.msg(data.msg);
						window.location.reload();
						}
			}
		});
		}
	});
	$("#delbut").unbind("click").click(function(){
		 if(confirm("确定删除当前表单吗？"))
		    {
		$.ajax({
			url : "/set/bpmset/deleteBpmForm",
			type : "post",
			dataType : "json",
			data:{
				formId:$("#formId").val()
				},
			success : function(data) {
				console.log(data);
				if(data.status=="200")
					{
						top.layer.msg(data.msg);
					}else if(data.status=="100")
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
	});
	$("#designerbut").unbind("click").click(function(){
		designform();
	});
	$("#bpmTypeName").unbind("click").click(function(e){
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
	if(bpmtable=="auto")
	{
		$("#tableName").attr("readonly","readonly").val(Date.parse(new Date())/1000);
	}
	$("#form").bootstrapValidator('resetForm');
});
function zTreeOnClick(event, treeId, treeNode)
{
	if(treeNode.isParent==false)
		{
		$("#form").bootstrapValidator('resetForm');
		$("#tablediv").hide();
		$("#infodiv").show();
		document.getElementById("form").reset();
		$.ajax({
			url : "/ret/bpmget/getBpmForm",
			type : "post",
			dataType : "json",
			data:{formId:treeNode.bpmSortId},
			success : function(data) {
					if(data.status==200)
						{
						for(name in data.list)
							{
							if(name=="bpmTypeId")
								{
								$.ajax({
									url : "/ret/bpmget/getBpmSortById",
									type : "post",
									dataType : "json",
									data:{bpmSortId:data.list[name]},
									success : function(data) {
										if(data.status==200)
											{
											$("#bpmTypeName").val(data.list.bpmSortName);
											}else
												{
												console.log(data.msg);
												}
									}
								});
								}
							$("#"+name).val(data.list[name]);
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
		$("#designerbut").show();
		$("#updatabut").unbind("click").click(function(){
			$("#form").bootstrapValidator('validate');
			var flag = $('#form').data('bootstrapValidator').isValid();
			if (flag) {
			$.ajax({
				url : "/set/bpmset/updateBpmForm",
				type : "post",
				dataType : "json",
				data:{
					formId:$("#formId").val(),
					sortNo:$("#sortNo").val(),
					formTitle:$("#formTitle").val(),
					tableName:$("#tableName").val(),
					bpmTypeId:$("#bpmTypeId").val()
					},
				success : function(data) {
					if(data.status==500)
						{
						console.log(data.msg);
						}else
							{
							top.layer.msg(data.msg);
							window.location.reload();
							}
				}
			});
			}
		});
		}else
		{
			$("#infodiv").hide();
			$("#tablediv").show();
			$("#myTable").bootstrapTable("destroy");
			query(treeNode.bpmSortId);
		}
}
function designform()
{
	window.open('/app/core/bpm/designerform?formId='+$("#formId").val());
}

function query(bpmTypeId)
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/bpmget/getBpmFormListByBpmTypeId?bpmTypeId='+bpmTypeId,
	      method: 'post',
	      contentType:'application/x-www-form-urlencoded',
	      toolbar: '#toobar',//工具列
	      striped: true,//隔行换色
	      cache: false,//禁用缓存
	      pagination: true,//启动分页
	      sidePagination: 'server',//分页方式
	      pageNumber: 1,//初始化table时显示的页码
	      pageSize: 10,//每页条目
	      showFooter: false,//是否显示列脚
	      showPaginationSwitch: true,//是否显示 数据条数选择框
	      sortable: true,//排序
	      search: false,//启用搜索
	      showColumns: true,//是否显示 内容列下拉框
	      showRefresh: true,//显示刷新按钮
	      idField: 'formId',//key值栏位
	      clickToSelect: true,//点击选中checkbox
	      pageList : [10, 20, 30, 50],//可选择单页记录数
	      queryParams:queryParams,
	      columns: [
	     {
	    	field: 'num',
			title: '序号',//标题  可不加
			width:'50px',
			formatter: function (value, row, index) {
					return index+1;
				}
	      },
	      {
	       field: 'formTitle',
	       title: '表单名称',
	       sortable : true,
	       width:'120px',
	       formatter:function(value,row,index){
			   return "<a href=\"javascript:void(0);gotemplate('"+row.formId+"');\" style=\"cursor: pointer;\">"+value+"</a>";
		   }
	      },
			{
		       field: 'tableName',
		       title: '数据表名',
		       width:'50px'
	      },
	      {
		       field: 'version',
		       visible: false,
		       title: '当前版本标识',
		       width:'100px'
	      },
	      {
		       field: 'createUserName',
		       width:'50px',
		       title: '创建人'
		   },
		   {
		       field: 'createTime',
		       width:'50px',
		       title: '创建时间'
		   },
	      {
	       field: 'opt',
	       width:'200px',
	       title: '操作',
	       align:'center',
    	   formatter:function(value,row,index){
                return createOptBtn(row.formId);
            }
	      }],
	      onClickCell: function (field, value, row, $element) {
	      //alert(row.SystemDesc);
	    },
	    responseHandler:function(res){
	    	if(res.status=="500")
    		{
    		console.log(res.msg);
    		}else if(res.status=="100")
    			{
    			top.layer.msg(res.msg);
    			}else
    			{
    			return {
    				total : res.list.total, //总页数,前面的key必须为"total"
    				rows : res.list.list //行数据，前面的key要与之前设置的dataField的值一致.
    			};
    			}
	    }
	   });
}
function queryParams(params) {
var temp = {
    search: params.search,
    pageSize:this.pageSize,
    pageNumber:this.pageNumber,
    sort: params.sort,
    sortOrder:params.order
};
return temp;
};
function createOptBtn(formId)
{
var html="<a href=\"javascript:void(0);godesignform('"+formId+"')\" class=\"btn btn-azure btn-xs\" >设计</a>&nbsp;&nbsp;" +
		"<a href=\"javascript:void(0);gobpmmobiledesignform('"+formId+"')\" class=\"btn btn-azure btn-xs\" >移动端</a>&nbsp;&nbsp;" +
		"<a href=\"/ret/bpmget/getFormFile?formId="+formId+"\" class=\"btn btn-sky btn-xs\" >导出</a>&nbsp;&nbsp;"+
		"<a href=\"javascript:void(0);getFields('"+formId+"')\" class=\"btn btn-primary btn-xs\">字段</a>&nbsp;&nbsp;"+
		"<a href=\"javascript:void(0);getTableData('"+formId+"')\" class=\"btn btn-palegreen btn-xs\">表数据</a>&nbsp;&nbsp;"+
		"<a href=\"javascript:void(0);showFormVersion('"+formId+"')\" class=\"btn btn-darkorange btn-xs\">版本</a>&nbsp;&nbsp;"+
		"<a href=\"javascript:void(0);delForm('"+formId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
return html;
}
function gobpmmobiledesignform(formId)
{
	window.open('/app/core/bpm/bpmfrommobiledesigner?formId='+formId);
}
function getTableData(formId)
{
	window.open("/app/core/bpm/createform?view=tabledata&formId="+formId);
}
function godesignform(formId)
{
	window.open('/app/core/bpm/designerform?formId='+formId);
}
function delForm(formId)
{
	if(confirm("确定删除当前表单吗？"))
    {
		$.ajax({
			url : "/set/bpmset/deleteBpmForm",
			type : "post",
			dataType : "json",
			data:{formId:formId},
			success : function(data){
				if(data.status=="200")
				{
					top.layer.msg(data.msg);
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else if(data.status=="500")
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

function showFormVersion(formId)
{
	window.open('/app/core/bpm/createform?view=version&formId='+formId);
}
function getFields(formId)
{
	window.open('/app/core/bpm/createform?view=formfields&formId='+formId);
}

function gotemplate(formId)
{
window.open("/app/core/bpm/bpmFormTemplate?formId="+formId);
}
