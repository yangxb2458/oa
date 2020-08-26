
var setting1 = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/documentget/getDocumentSortTree",// Ajax 获取数据的 URL 地址
		autoParam : ["sortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	view : {
		dblClickExpand : false,
		selectedMulti : false
	// 禁止多选
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "sortId",
			rootPId : "0"
		},
		key : {
			name : "sortName"
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
				v += nodes[i].sortName + ",";
				vid += nodes[i].sortId + ",";
			}
			if (v.length > 0)
				v = v.substring(0, v.length - 1);
			var nameem = $("#sortId");
			nameem.val(v);
			if (vid.length > 0)
				vid = vid.substring(0, vid.length - 1);
			nameem.attr("data-value",vid);
		}
	}
};
$(function() {
	getAllDocumentFlowListByManage();
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	$(".js-query-but").unbind("click").click(function(){
		$('#myTable').bootstrapTable('refresh');
	});
	query();
	$.ajax({
		url : "/ret/documentget/getDocumentSortTree",
		type : "post",
		dataType : "json",
		data : {
			levelId : "0"
		},
		success : function(data) {
			var topNode = [ {
				sortName : "TOP分类",
				isParent : "false",
				sortId : "0"
			} ];
			var newTreeNodes = topNode.concat(data);
			$.fn.zTree.init($("#menuTree"), setting1, newTreeNodes);
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
function getAllDocumentFlowListByManage()
{
	$.ajax({
		url : "/ret/documentget/getAllDocumentFlowListByManage",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
				{
					var html = "<option value=''>全部</option>"
					for(var i=0;i<data.list.length;i++)
						{
						html+="<option value='"+data.list[i].flowId+"'>"+data.list[i].flowName+"</option>"
						}
				$("#flows").html(html);
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
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/documentget/getDocumentToFilingList',
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
		      idField: 'runId',//key值栏位
		      clickToSelect: true,//点击选中checkbox
		      pageList : [10, 20, 30, 50],//可选择单页记录数
		      queryParams:queryParams,
		      columns: [ {
		      checkbox: true
		      },
		      {
				   field: 'id',
				   width:'50px',
				   sortable : true,
				   title: '流水号'
				},
		      {
		       field: 'flowTitle',
		       title: '流程标题',
		       sortable : true,
		       width:'200px'
		      },
		      {
				   field: 'createUserName',
				   width:'50px',
				   align : 'center',
				   title: '创建人'
				},
				{
					field: 'createTime',
					width:'50px',
					align : 'center',
					sortable : true,
					title: '创建时间'
				},
		      {
		    	  field: 'opUserStr',
		    	  width:'50px',
		    	  align : 'center',
		    	  title: '所有经办人',
		    	  formatter:function(value, row, index)
				     {
		    		  return  getUserNameByStr(value);
				     }
			   },
		       {
				   field: 'urgency',
				   width:'50px',
				   title: '紧急程度',
				   sortable : true,
				   align : 'center',
				   formatter:function(value, row, index)
				     {
		    		   	if(value=="0")
		    		   		{
		    		   		return "一般";
		    		   		}else if(value=="1")
		    		   			{
		    		   			return "紧急";
		    		   			}else if(value=="2")
		    		   				{
		    		   				return "加急";
		    		   				}
				     }
		       },
			   {
			       field: 'status',
			       width:'50px',
			       align : 'center',
			       title: '状态',
			       formatter:function(value, row, index)
				     {
			    	   if(value=="0")
			    		   {
			    		   		return "<a href=\"javascript:void(0);\" class=\"btn btn-palegreen btn-xs\">进行中</a>";
			    		   }else if(value=="1")
			    			{
			    			   return " <a href=\"javascript:void(0);\" class=\"btn btn-darkorange btn-xs\">已结束</a>";   
			    			}else
			    				{
			    				return "<a href=\"javascript:void(0);\" class=\"btn btn-darkorange btn-xs\">异常</a>";	
			    				}
			    	   
				     }
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       align : 'center',
		       width:'100px',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.runId,row.flowId);
	            }
		      }],
		      onClickCell: function (field, value, row, $element) {
		      //alert(row.SystemDesc);
		    },
		    responseHandler:function(res){
		    	if(res.status=="500")
		    		{
		    		console.log(res.msg);
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
function createOptBtn(runId,flowId)
{
	var html="<a href=\"javascript:void(0);tofiling('"+runId+"')\" class=\"btn btn-sky btn-xs\" >归档</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);read('" + runId + "','" + flowId + "')\" class=\"btn btn-primary btn-xs\">查看</a>";
	return html;
	}
function read(runId, flowId) {
	open("/app/core/document/documentread?runId=" + runId + "&flowId=" + flowId,"_self");
}
function queryParams(params) {
    var temp = {
        search: params.search,
        pageSize:this.pageSize,
        pageNumber:this.pageNumber,
        sort: params.sort,
        sortOrder:params.order,
        createUser:$("#createUser").attr("data-value"),
        flowId:$("#flows").val(),
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val(),
        documentRunNo:$("#documentRunNo").val()
        
    };
    return temp;
};
function tofiling(runId)
{
	$("#toFilingModal").modal("show");
	$("#sortId").attr("data-value","");
	$("#sortId").val("");
	$(".js-tofilingtn").unbind("click").click(function(){
		if($("#sortId").attr("data-value")==null||$("#sortId").attr("data-value")==''||$("#sortId").attr("data-value")==undefined)
		{
			top.layer.msg("请先选择归档分类！")
		}else
		{
			$.ajax({
				url : "/set/documentset/documentToFile",
				type : "post",
				dataType : "json",
				data:{sortId:$("#sortId").attr("data-value"),runId:runId},
				success : function(data) {
					if(data.status=="200")
						{
						$('#myTable').bootstrapTable('refresh');
						$("#toFilingModal").modal("hide");
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
	})
}

