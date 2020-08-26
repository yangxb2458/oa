$(function(){
	query();
	getCodeClass("taskType","task");
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
})
function query()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/taskget/getManageTaskList',
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
	      idField: 'taskId',//key值栏位
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
	       field: 'taskName',
	       title: '任务名称',
	       sortable : true,
	       width:'200px'
	      },
			{
		       field: 'beginTime',
		       title: '开始时间',
		       width:'50px'
	      },
	      {
		       field: 'endTime',
		       width:'100px',
		       title: '结束时间'
		   },
		   {
		       field: 'taskType',
		       width:'100px',
		       title: '任务类型',
			   formatter:function(value,row,index){
		    	 return getCodeClassName(value,"task");
	            }
		       
		   },
		   {
		       field: 'duration',
		       width:'50px',
		       title: '工作量'
		   },
		   {
		       field: 'supervisorUserName',
		       width:'50px',
		       title: '督查领导'
		   },
		   {
		       field: 'chargeUserName',
		       width:'50px',
		       title: '任务负责人'
		   },
		   {
			field: 'status',
			width:'50px',
			title: '当前状态',
				formatter:function(value,row,index){
					if(value=="0")
					{
						return "进行中";
					}else if(value=="1")
					{
						return "已结束";
					}else
					{
						return "未知";
					}
		       }
		   },
	      {
	       field: 'opt',
	       title: '操作',
	       align:'center',
	       width:'180px',
    	   formatter:function(value,row,index){
                return createOptBtn(row.taskId,row.status);
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
        taskType:$("#taskType").val(),
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val(),
        status:$("#status").val()
    };
    return temp;
};
function createOptBtn(taskId,status)
{
	var html="<a href=\"javascript:void(0);ganttdetails('"+taskId+"')\" class=\"btn btn-magenta btn-xs\" >甘特图</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);read('"+taskId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;";
	if(status=="0")
	{
		html+="<a href=\"javascript:void(0);edit('"+taskId+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" +
			  "<a href=\"javascript:void(0);setFinish('"+taskId+"')\" class=\"btn btn-purple btn-xs\">完成</a>&nbsp;&nbsp;" +
			  "<a href=\"javascript:void(0);del('"+taskId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	}
	return html;
}

function ganttdetails(taskId)
{
	window.open("/app/core/task/ganttdetails?taskId="+taskId);
}

function setFinish(taskId)
{
	$.ajax({
		url : "/set/taskset/updateTask",
		type : "post",
		dataType : "json",
		data:{
			taskId:taskId,
			status:"1"
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
				top.layer.msg(data.msg);
				$("#myTable").bootstrapTable('refresh');
				}
		}
	});
}

function read(taskId)
{
	window.open("/app/core/task/taskdetails?taskId="+taskId);
}

function edit(taskId)
{
	open("/app/core/task/createtask?view=edit&taskId="+taskId,"_self");
}

function del(taskId)
{
	if(confirm("确定删除当前任务吗？"))
    {
		$.ajax({
			url : "/set/taskset/deleteTask",
			type : "post",
			dataType : "json",
			data:{
				taskId:taskId
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
					top.layer.msg(data.msg);
					$("#myTable").bootstrapTable('refresh');
					}
			}
		});
    }else
    	{
    	return;
    	}
}