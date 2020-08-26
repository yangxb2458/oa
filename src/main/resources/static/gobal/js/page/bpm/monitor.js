$(function() {
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	getAllBpmFlowListByManage();
	query();
	$(".js-monitor-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
});
function getAllBpmFlowListByManage()
{
	$.ajax({
		url : "/ret/bpmget/getAllBpmFlowListByManage",
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


function getCorssBpmFlowList()
{
	$.ajax({
		url : "/ret/bpmget/getCorssBpmFlowList",
		type : "post",
		dataType : "json",
		success : function(data) {
			console.log(data);
			if(data.status=="200")
				{
					var html = "<option value=''>全部</option>"
					for(var i=0;i<data.list.length;i++)
						{
						html+="<option value='"+data.list[i].flowId+"'>"+data.list[i].flowName+"</option>"
						}
				$("#flows").html(html);
				$("#pflows").html(html);
				$("#dflows").html(html);
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
		      url: '/ret/bpmget/getBpmRunProcessForMonitor',
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
		      idField: 'runProcessId',//key值栏位
		      clickToSelect: true,//点击选中checkbox
		      pageList : [10, 20, 30, 50],//可选择单页记录数
		      queryParams:queryParams,
		      columns: [
		      {
				   field: 'id',
				   width:'50px',
				   sortable : true,
				   title: '流水号'
				},
		      {
		       field: 'flowTitle',
		       title: '流程标题',
		       width:'200px'
		      },
		      {
			       field: 'prcsName',
			       width:'50px',
			       align : 'center',
			       title: '步骤名称'
			   },
		      {
				   field: 'opUserName',
				   width:'50px',
				   align : 'center',
				   title: '办理人'
				},
				{
					field: 'createTime',
					width:'100px',
					align : 'center',
					title: '步骤创建时间'
				},
				{
					   field: 'recTime',
					   width:'100px',
					   title: '接收时间',
					   align : 'center'
			       },
		      {
		    	  field: 'endTime',
		    	  width:'100px',
		    	  align : 'center',
		    	  title: '办结时间'
			   },
			   {
			    	  field: 'opFlag',
			    	  width:'50px',
			    	  align : 'center',
			    	  title: '步骤类型',
			    	  formatter:function(value,row,index)
			    	  {
			    		  if(value=="0")
				    		 {
				    			return "<span class=\"badge badge-success\">主办</span>";
				    		 }else
				    			{
				    			 return "<span class=\"badge badge-default\">经办</span>"; 
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
			    		 }else
			    			{
			    			 return "<a href=\"javascript:void(0);\" class=\"btn btn-darkorange btn-xs\">已办结</a>"; 
			    			}
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
function queryParams(params) {
    var temp = {
        search: params.search,
        pageSize:this.pageSize,
        pageNumber:this.pageNumber,
        sort: params.sort,
        sortOrder:params.order,
        id:$("#bpmRunNo").val(),
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val(),
        flowId:$("#flows").attr("data-value"),
        createUser:$("#createUser").attr("data-value")
    };
    return temp;
};
