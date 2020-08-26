$(function(){
	getCodeClass("noticeType","notice");
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	query();
	$(".js-query-btn").unbind("click").click(function(){
		$('#myTable').bootstrapTable('refresh');
	});
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/noticeget/getMyNoticeList',
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
		      idField: 'noticeId',//key值栏位
		      clickToSelect: true,//点击选中checkbox
		      pageList : [10, 20, 30, 50],//可选择单页记录数
		      queryParams:queryParams,
		      columns: [
		     {
		    	field: 'num',
				title: '序号',//标题  可不加
				align:'center',
				width:'30px',
				formatter: function (value, row, index) {
						return index+1;
					}
		      },
		      {
		       field: 'noticeTitle',
		       title: '通知公告标题',
		       sortable : true,
		       width:'250px',
		       formatter:function(value,row,index){
		    	   	if(row.readStatus=="false")
		    	   		{
		    	   		return value;
		    	   		}else
		    	   		{
		    	   			return value+"&nbsp;&nbsp;<span class=\"label label-darkorange\">new</span>";
		    	   		}
		    	   		
		            }
		      },
		      {
				field: 'noticeType',
				   width:'50px',
				   title: '公告类型',
				   formatter:function(value,row,index){
			    	 return getCodeClassName(value,"notice");
		            }
				},
				{
			       field: 'status',
			       title: '当前状态',
			       width:'50px',
			       formatter:function(value,row,index){
		    	   if(value=="0")
		    		   {
		    		   		return "无效";
		    		   }else
		    			{
		    			   return "有效";   
		    			}
		    				  
	            }
		      },
		      {
			       field: 'createUser',
			       width:'50px',
			       title: '创建人'
			   },
			   {
			       field: 'createTime',
			       width:'80px',
			       title: '创建时间'
			   },
			   {
			       field: 'sendTime',
			       width:'50px',
			       title: '生效时间'
			   },
			   {
			       field: 'endTime',
			       width:'50px',
			       title: '失效时间'
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       align:'center',
		       width:'50px',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.noticeId);
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
        noticeType:$("#noticeType").val(),
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val(),
        readStatus:$("#readStatus").val()
    };
    return temp;
};
function createOptBtn(noticeId)
{
	var html="<a href=\"javascript:void(0);readnotice('"+noticeId+"')\" class=\"btn btn-sky btn-xs\" >查阅 </a>";
	return html;
}

function readnotice(noticeId)
{
	window.open("/app/core/notice/details?noticeId="+noticeId);
	}


