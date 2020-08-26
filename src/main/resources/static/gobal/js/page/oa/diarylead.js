$(function(){
	query();
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	$(".js-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
	getsubordinates();
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/oaget/getMySubordinatesDiaryList',
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
		      idField: 'diaryId',//key值栏位
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
		      },{
			       field: 'diaryDay',
			       title: '指定日期',
			       sortable : true,
			       width:'100px'
			      },
		      {
		       field: 'title',
		       title: '日志标题',
		       width:'200px'
		      },
		      {
				field: 'diaryType',
				   width:'100px',
				   title: '日志类型',
				   formatter:function(value,row,index){
					   if(value=="1")
						 {
						   return "个人工作日志";
						 }else if(value=="")
						{
							 return "部门工作汇报";
						}else
						{
				    	 return "未知";
			            }
				   }
				},
				{
			       field: 'createUserName',
			       width:'100px',
			       title: '下属姓名'
			   },
			   {
			       field: 'createTime',
			       width:'100px',
			       title: '创建时间'
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       align:'center',
		       width:'100px',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.diaryId);
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
        accountId:$("#accountId").val(),
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val()
    };
    return temp;
};
function createOptBtn(diaryId)
{
	var html="<a href=\"javascript:void(0);readDiary('"+diaryId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}


function readDiary(diaryId) {
	//open("/app/core/diary/readdiary?diaryId=" + diaryId,"_self");
	window.open("/app/core/diary/readdiary?diaryId=" + diaryId);
}


function getsubordinates()
{
	$.ajax({
		url : "/ret/sysget/getMySubordinates",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				var html = "<option value=''>全部</option>"
					for(var i=0;i<data.list.length;i++)
						{
						html+="<option value='"+data.list[i].accountId+"'>"+data.list[i].userName+"</option>"
						}
				$("#accountId").html(html);
				}
		}
	})
}