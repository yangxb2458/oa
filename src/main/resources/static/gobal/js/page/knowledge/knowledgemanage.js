var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/knowledgeget/getknowledgeSortTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "sortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onClick : zTreeOnClick
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
	}
};


$(function() {
	
	var topNode = [ {
		sortName : "全部",
		orgLeaveId : '',
		isParent : "true",
		sortId : "",
		icon : "/gobal/img/org/org.png"
	} ];
	var zTreeObj = $.fn.zTree.init($("#menuTree"), setting, topNode);
	var nodes = zTreeObj.getNodes();
	for (var i = 0; i < nodes.length; i++) {
		zTreeObj.expandNode(nodes[i], true, false, false);//默认展开第一级节点
	}
	
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	
	$("#sortId").unbind("click").click(function(e) {
		e.stopPropagation();
		$("#menuContent").css({
			"width" : $(this).outerWidth() + "px",
			"left":(document.getElementById("sortId").offsetLeft-40)+ "px"
		}).slideDown(200);
	});

	$("body").unbind("click").click(function() {
		$("#menuContent").hide();
	});

	$("#menuContent").unbind("click").click(function(e) {
		e.stopPropagation();
	});
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
	 query();
});
function zTreeOnClick(event, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("menuTree"), nodes = zTree.getSelectedNodes(), v = "";
		vid = "";
		nodes.sort(function compare(a, b) {
			return a.id - b.sortId;
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
		idem.attr("data-value", vid);
		idem.val(v);
}

function query()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/knowledgeget/getMyCreateKnowledgeList',
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
	      search: true,//启用搜索
	      showColumns: true,//是否显示 内容列下拉框
	      showRefresh: true,//显示刷新按钮
	      idField: 'knowledgeId',//key值栏位
	      clickToSelect: true,//点击选中checkbox
	      pageList : [10, 20, 30, 50],//可选择单页记录数
	      queryParams:queryParams,
	      columns: [ {
	      checkbox: true
	      },
	     {
	    	field: 'num',
			title: '序号',//标题  可不加
			width:'50px',
			formatter: function (value, row, index) {
					return index+1;
				}
	      },
	      {
	       field: 'title',
	       title: '知识标题',
	       sortable : true,
	       width:'150px'
	      },
	      {
			field: 'sortName',
			   width:'50px',
			   title: '知识分类'
			},
			 {
			       field: 'version',
			       width:'50px',
			       title: '版本'
			   },
			   {
			       field: 'levelStar',
			       width:'50px',
			       title: '星级'
			   },
	      {
		       field: 'createUser',
		       width:'50px',
		       title: '创建人'
		   },
		   {
		       field: 'createTime',
		       width:'100px',
		       title: '创建时间'
		   },
		   {
		       field: 'attach',
		       width:'100px',
		       title: '相关附件',
		       formatter:function(value,row,index){
		    	   return createTableAttach(value);	   
	            }
		   },
	      {
	       field: 'opt',
	       width:'120px',
	       align:'center',
	       title: '操作',
    	   formatter:function(value,row,index){
                return createOptBtn(row.knowledgeId);
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
    sortOrder:params.order,
    sortId:$("#sortId").attr("data-value"),
    beginTime:$("#beginTime").val(),
    endTime:$("#endTime").val()
};
return temp;
};
function createOptBtn(knowledgeId)
{
	var html = "<a href=\"javascript:void(0);details('" + knowledgeId + "')\" class=\"btn btn-primary btn-xs\">详情</a>&nbsp;&nbsp;"
	+ "<a href=\"/app/core/file/knowledgecreate?view=edit&knowledgeId=" + knowledgeId + "\" class=\"btn btn-success btn-xs\">编辑</a>&nbsp;&nbsp;"
	+ "<a href=\"javascript:void(0);deleteknowledge('" + knowledgeId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
return html;
return html;
}

function deleteknowledge(knowledgeId)
{
	 if(confirm("确定删除当前知识记录吗？"))
	    {
	$.ajax({
		url : "/set/knowledgeset/deleteKnowledge",
		type : "post",
		dataType : "json",
		data:{knowledgeId:knowledgeId},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#myTable").bootstrapTable("refresh");
			}else if(data.status=="100")
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

function details(knowledgeId)
{
	window.open("/app/core/file/detailknowledge?knowledgeId="+knowledgeId);
}