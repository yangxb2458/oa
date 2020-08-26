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
	 getSuperversionType();
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/superversionget/getControlProcessList',
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
		      idField: 'processId',//key值栏位
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
		       field: 'superversionTitle',
		       title: '督查事件名称',
		       sortable : true,
		       width:'100px',
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
			       field: 'prcsValue',
			       title: '当前进度',
			       width:'50px',
			       formatter:function(value,row,index){
		    		   		return value+"%";
		    				  
	            }
		      },
		      {
			       field: 'createUserName',
			       width:'50px',
			       title: '事件处理人'
			   },
			   {
			       field: 'content',
			       width:'300px',
			       title: '处理详情' ,
			       formatter:function(value,row,index){
	    		   		return "<div style='width:300px;word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'>"+value+"</div>";
	    				  
		            }
			   },
			   {
			       field: 'finishTime',
			       width:'100px',
			       title: '完成时间'
			   },
		      {
		       field: 'opt',
		       width:'50px',
		       align:'center',
		       title: '操作',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.superversionId,row.status);
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
	var html="<a href=\"javascript:void(0);details('"+processId+"')\" class=\"btn btn-sky btn-xs\">详情</a>";
	return html;
}
function readdetails(superversionId)
{
	window.open("/app/core/superversion/superversiondetails?superversionId="+superversionId);
}
function getSuperversionType()
{
	$.ajax({
		url : "/ret/superversionget/getMySuperversionConfigList",
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
				var html="<option value=''>全部</option>"
					for(var i=0;i<data.list.length;i++)
					{
						html+="<option value='"+data.list[i].configId+"'>"+data.list[i].typeName+"</option>"
					}
				$("#type").html(html);
				}
		}
	})
}



function details(processId)
{
	window.open("/app/core/superversion/superversionprocessdetails?processId="+processId);
}

