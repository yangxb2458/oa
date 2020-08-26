$(function(){
	query();
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
	getAllBpmFlowListByManage();
	getAllBpmFormListForSelect();
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/bpmget/getRunLogList',
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
		      idField: 'logId',//key值栏位
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
		       field: 'logType',
		       title: '事件类型',
		       sortable : true,
		       width:'50px',
		       formatter:function(value,row,index){
	                if(value=="0")
	                {
	                	return "创建BPM工作流";	
	                }else if(value=="1")
	                {
	                	return "删除BPM工作流";	
	                }else if(value=="2")
	                {
	                	return "查询BPM工作流";	
	                }else if(value=="3")
	                {
	                	return "BPM表单修改";	
	                }else if(value=="4")
	                {
	                	return "BPM设置修改";	
	                }else if(value=="5")
	                {
	                	return "BMP收回";	
	                }else if(value=="6")
	                {
	                	return "BPM流程修改";	
	                }
	            }
		      },
		      {
				field: 'bpmTitle',
				   width:'150px',
				   title: '具体流程'
				},
				{
			       field: 'flowName',
			       title: '相关流程',
			       width:'150px'
		      },
		      {
			       field: 'formTitle',
			       title: '相关表单',
			       width:'150px'
		      },
		      {
			       field: 'createUser',
			       width:'50px',
			       title: '创建人',
			       formatter:function(value,row,index){
			    	   return getUserNameByStr(value);
			       }
			   },
			   {
			       field: 'createTime',
			       width:'100px',
			       title: '创建时间'
			   },
			   {
			       field: 'createUserName',
			       width:'50px',
			       title: '操作人员'
			   },
			   {
			       field: 'remark',
			       width:'200px',
			       title: '事件说明'
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
        logType:$("#logType").val(),
        flowId:$("#flowId").val(),
        runId:$("#runId").val(),
        formId:$("#formId").val(),
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val(),
        createUser:$("#createUser").attr("data-value")
    };
    return temp;
};

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
				$("#flowId").html(html);
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

function getAllBpmFormListForSelect()
{
	$.ajax({
		url : "/ret/bpmget/getAllBpmFormListForSelect",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
				{
					var html = "<option value=''>全部</option>"
					for(var i=0;i<data.list.length;i++)
						{
						html+="<option value='"+data.list[i].formId+"'>"+data.list[i].formTitle+"</option>"
						}
				$("#formId").html(html);
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



