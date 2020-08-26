$(function(){
	getCodeClass("taskType","task");
	query();
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
	jeDate("#completeTime", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate(),
	});
});
function query()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/taskget/getMyTaskWorkList',
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
	      idField: 'taskDataId',//key值栏位
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
		       field: 'taskType',
		       width:'50px',
		       title: '任务类型',
			   formatter:function(value,row,index){
		    	 return getCodeClassName(value,"task");
	            }
		   },
	      {
	       field: 'taskName',
	       title: '任务名称',
	       sortable : true,
	       width:'150px'
	      },
	      {
			field: 'text',
			   width:'150px',
			   title: '子任务名称'
			},
			 {
			       field: 'createUserName',
			       width:'50px',
			       title: '分配人'
			   },
			{
		       field: 'duration',
		       title: '工期',
		       sortable : true,
		       width:'50px'
	      },
	      {
		       field: 'startDate',
		       width:'100px',
		       sortable : true,
		       title: '开始时间'
		   },
		   {
		       field: 'progress',
		       width:'50px',
		       sortable : true,
		       title: '进度情况',
		       formatter:function(value,row,index){
	                return value*100+"%";
	            }
		   },
	      {
	       field: 'opt',
	       title: '操作',
	       align:'center',
	       width:'100px',
    	   formatter:function(value,row,index){
                return createOptBtn(row.taskDataId,row.taskId);
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
        createUser:$("#createUser").attr("data-value")
    };
    return temp;
};

function createOptBtn(taskDataId,taskId)
{
	var html="<a href=\"javascript:void(0);ganttdetails('"+taskId+"')\" class=\"btn btn-magenta btn-xs\" >甘特图</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);dowork('"+taskDataId+"')\" class=\"btn btn-sky btn-xs\" >办理</a>";
	return html;
}

function dowork(taskDataId)
{
	document.getElementById("form1").reset();
	$("#taskattach").attr("data_value","");
	$("#show_taskattach").html("");
	$("#myTaskModal").modal("show");
	$(".js-save").unbind("click").click(function(){
		addProcess(taskDataId);
	})
}
function ganttdetails(taskId)
{
	window.open("/app/core/task/ganttdetails?taskId="+taskId);
}

function addProcess(taskDataId)
{
	$.ajax({
		url : "/set/taskset/insertTaskProcess",
		type : "POST",
		dataType : "json",
		data : {
			taskDataId:taskDataId,
			attach:$("#taskattach").attr("data_value"),
			progress:$("#progress").val(),
			content:$("#content").val(),
			remark:$("#remark").val(),
			completeTime:$("#completeTime").val()
		},
		success : function(data) {
			if(data.status=="200")
				{
				top.layer.msg(data.msg);
				$("#myTaskModal").modal("hide");
				$("#myTable").bootstrapTable('refresh');
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
