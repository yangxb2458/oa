$(function(){
	query();
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	getCodeClass("noticeType","notice");
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/noticeget/getNoticeManageList',
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
		       field: 'noticeTitle',
		       title: '通知公告标题',
		       sortable : true,
		       width:'200px'
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
		    		   		return "审批中";
		    		   }else if(value=="1")
		    		{
		    			   return "生效中";   
		    		}else if(value=="2")
		    			{
		    			   return "未通过";   
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
			       width:'100px',
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
		       width:'150px',
		       align:'center',
		       title: '操作',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.noticeId,row.status);
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
        endTime:$("#endTime").val()
    };
    return temp;
};
function createOptBtn(noticeId,status)
{
	var html="<a href=\"javascript:void(0);readNotice('"+noticeId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);edit('"+noticeId+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;";
	if(status=="1")
	{
		html+="<a href=\"javascript:void(0);setNotice('"+noticeId+"','0')\" class=\"btn btn-primary btn-xs\">终止</a>&nbsp;&nbsp;";
	}else
	{
		if(status!="2")
		{
		html+="<a href=\"javascript:void(0);setNotice('"+noticeId+"','1')\" class=\"btn btn-palegreen btn-xs\">生效</a>&nbsp;&nbsp;";
		}
	}
	
	html+="<a href=\"javascript:void(0);del('"+noticeId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}

function edit(noticeId)
{
	open("/app/core/notice/index?view=edit&noticeId="+noticeId,"_self");
}

function setNotice(noticeId,status)
{
	$.ajax({
		url : "/set/noticeset/updateNotice",
		type : "post",
		dataType : "json",
		data:{noticeId:noticeId,
			status:status
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
				$('#myTable').bootstrapTable('refresh');
				}
		}
	})	
}


function readNotice(noticeId)
{
	window.open("/app/core/notice/details?noticeId="+noticeId);
	}

function del(noticeId)
{
	if(window.confirm("确定删除选中的通知公告吗？")){
	$.ajax({
		url : "/set/noticeset/delNotice",
		type : "post",
		dataType : "json",
		data:{noticeId:noticeId},
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
	}else
		{
		return;
		}
	
}
