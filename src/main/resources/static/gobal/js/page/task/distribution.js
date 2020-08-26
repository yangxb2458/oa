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
});

function query()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/taskget/getAllUserPrivList',
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
			       field: 'userPrivName',
			       width:'50px',
			       title: '负者人'
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
	       width:'50px',
    	   formatter:function(value,row,index){
                return createOptBtn(row.taskDataId);
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
        status:$("#status").val(),
        userPriv:$("#userPriv").attr("data-value")
    };
    return temp;
};
function createOptBtn(taskDataId)
{
	var html="<a href=\"javascript:void(0);taskDataDetails('"+taskDataId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;";
	return html;
}

function taskDataDetails(taskDataId)
{
	window.open("/app/core/task/taskdatadetails?taskDataId="+taskDataId);
}
