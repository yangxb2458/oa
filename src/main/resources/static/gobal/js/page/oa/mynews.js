$(function(){
	getCodeClass("newsType","news");
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	query();
	$(".js-query").unbind("click").click(function(){
		$('#myTable').bootstrapTable('refresh');
	});
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/oaget/getMyNewsList',
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
		      sortOrder: "desc",
		      search: true,//启用搜索
		      showColumns: true,//是否显示 内容列下拉框
		      showRefresh: true,//显示刷新按钮
		      idField: 'newsId',//key值栏位
		      clickToSelect: false,//点击选中checkbox
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
		       field: 'newsTitle',
		       title: '新闻标题',
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
			       field: 'subheading',
			       title: '摘要',
			       sortable : true,
			       width:'350px'
			      },
		      {
				field: 'newsType',
				   width:'50px',
				   title: '新闻类型',
				   formatter:function(value,row,index){
			    	 return getCodeClassName(value,"news");
		            }
		      },
		      {
			       field: 'createUser',
			       width:'50px',
			       title: '发布人'
			   },
			   {
			       field: 'sendTime',
			       width:'100px',
			       title: '发布时间'
			   },
			   {
			       field: 'onclickCount',
			       width:'50px',
			       title: '点击次数'
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       align:'center',
		       width:'100px',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.newsId);
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
        newsType:$("#newsType").val(),
        status:$("#status").val(),
        beginTime:$("#beginTime").val()
    };
    return temp;
};
function createOptBtn(newsId)
{
	var html="<a href=\"javascript:void(0);readNews('"+newsId+"')\" class=\"btn btn-sky btn-xs\" >查看</a>&nbsp;&nbsp;" +
			 "<a href=\"javascript:void(0);comments('"+newsId+"')\" class=\"btn btn-darkorange btn-xs\" >评论</a>";
	return html;
	}

function comments(newsId)
{
	$("#commentsModal").modal("show");
	$(".js-save").unbind("click").click(function(){
			$.ajax({
		url : "/set/oaset/insertNewsComments",
		type : "post",
		dataType : "json",
		data:{
			newsId:newsId,
			commType:$('input:radio[name="commType"]:checked').val(),
			commContent:$("#commContent").val()
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
				$("#commentsModal").modal("hide");
				}
		}
	})	
	})
	
}


function readNews(newsId)
{
	window.open("/app/core/news/readnews?newsId="+newsId);
	}