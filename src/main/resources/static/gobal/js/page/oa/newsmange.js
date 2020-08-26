$(function(){
	query();
	$(".js-btn").unbind("click").click(function(){
		top.goUrl("/app/core/news");
	});
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/oaget/getNewsManageList',
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
		       field: 'newsTitle',
		       title: '新闻标题',
		       sortable : true,
		       width:'200px'
		      },
		      {
			       field: 'subheading',
			       title: '新闻摘要',
			       sortable : true,
			       width:'300px'
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
			       field: 'delFlag',
			       title: '是否删除',
			       width:'50px',
			       visible:false,
			       formatter:function(value,row,index){
		    	   if(value=="1")
		    		   {
		    		   		return "已删除";
		    		   }else
		    			{
		    			   return "未删除";   
		    			}
		    				  
	            }
		      },
		      {
			       field: 'createTime',
			       width:'100px',
			       title: '创建时间'
			   },
		      {
			       field: 'endTime',
			       width:'100px',
			       title: '终止时间'
			   },
			   {
			       field: 'onclickCount',
			       width:'50px',
			       visible:false,
			       title: '查看次数'
			   },
			   {
			       field: 'createUser',
			       width:'50px',
			       title: '创建人'
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       align:'center',
		       width:'150px',
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
function createOptBtn(newsId)
{
	var html="<a href=\"javascript:void(0);readNews('"+newsId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);edit('"+newsId+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);stopNews('"+newsId+"')\" class=\"btn btn-primary btn-xs\">终止</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);del('"+newsId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}

function edit(newsId)
{
	open("/app/core/news?view=edit&newsId="+newsId,"_self");
}



function stopNews(newsId)
{
	$.ajax({
		url : "/set/oaset/stopNews",
		type : "post",
		dataType : "json",
		data:{newsId:newsId},
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
				$('#myTable').bootstrapTable('refresh');
				}
		}
	})	
}


function readNews(newsId)
{
	window.open("/app/core/news/readnews?newsId="+newsId);
}

function del(newsId)
{
	$.ajax({
		url : "/set/oaset/delNews",
		type : "post",
		dataType : "json",
		data:{newsId:newsId},
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
				$('#myTable').bootstrapTable('refresh');
				}
		}
	})	
	
}
