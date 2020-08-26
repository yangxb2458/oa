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
	getsuperversiontype();
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/superversionget/getMyCompleteProcessList',
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
		      sortOrder:"DESC",
		      search: true,//启用搜索
		      showColumns: true,//是否显示 内容列下拉框
		      showRefresh: true,//显示刷新按钮
		      idField: 'processId',//key值栏位
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
		       field: 'superversionTitle',
		       title: '督查督办标题',
		       sortable : true,
		       width:'150px',
		       formatter:function(value,row,index){
	   		   		return "<a href='#' onclick='readdetails(\""+row.superversionId+"\")'>"+value+"</a>";
			   }
		      },
		      {
				field: 'typeName',
				   width:'50px',
				   title: '事件类型'
				},
				{
			       field: 'createUserName',
			       title: '任务创建人',
			       width:'50px' 
		      },
		      {
			       field: 'beginTime',
			       width:'50px',
			       title: '开始时间'
			   },
			   {
			       field: 'endTime',
			       width:'50px',
			       title: '结束时间'
			   },
			   {
			       field: 'prcsValue',
			       width:'100px',
			       title: '完成度',
			       formatter:function(value,row,index){
			    	   return value+"%";
			       }
			   },
			   {
			       field: 'finishTime',
			       width:'100px',
			       title: '完成时间'
			   },
		      {
		       field: 'opt',
		       width:'100px',
		       align:'center',
		       title: '操作',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.processId);
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
        type:$("#type").val(),
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val()
    };
    return temp;
};
function createOptBtn(processId)
{
	var html="<a href=\"javascript:void(0);details('"+processId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}

function details(processId)
{
	window.open("/app/core/superversion/superversionprocessdetails?processId="+processId);
}
function readdetails(superversionId)
{
	window.open("/app/core/superversion/superversiondetails?superversionId="+superversionId);
}
function getsuperversiontype()
{
	$.ajax({
		url : "/ret/superversionget/getAllSuperversionConfigList",
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
				var html="<option value=''>请选择</option>";
					for(var i=0;i<data.list.length;i++)
						{
							html+="<option data-value='"+data.list[i].leadId+"' value='"+data.list[i].configId+"'>"+data.list[i].typeName+"</option>"
						}
					$("#type").html(html);
				}
		}
	})
}