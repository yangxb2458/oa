$(function() {
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	getAllBpmFlowList();
	query2();
	$(".js-send-to-query-but").unbind("click").click(function(){
		$('#myTable2').bootstrapTable('refresh');
	});
});

function read(runId,flowId,sendToId)
{
	$.ajax({
		url : "/set/bpmset/updateSendToStatus",
		type : "post",
		dataType : "json",
		data:{sendToId:sendToId},
		success : function(data) {
			if(data.status=="200")
				{
				open("/app/core/bpm/bpmread?runId=" + runId + "&flowId=" + flowId,"_self");
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


function getAllBpmFlowList()
{
	$.ajax({
		url : "/ret/bpmget/getAllBpmFlowList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
				{
					var html = "<option value=''>全部</option>"
					for(var i=0;i<data.list.length;i++)
						{
						html+="<option value='"+data.list[i].flowId+"'>"+data.list[i].flowName+"</option>"
						}
				$("#flows").html(html);
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


function query2()
{
	 $("#myTable2").bootstrapTable({
	      url: '/ret/bpmget/getSendToMeBpmList',
	      method: 'post',
	      contentType:'application/x-www-form-urlencoded',
	      toolbar: '#toobar2',//工具列
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
	      idField: 'sendToId',//key值栏位
	      clickToSelect: true,//点击选中checkbox
	      pageList : [10, 20, 30, 50],//可选择单页记录数
	      queryParams:queryParams2,
	      columns: [ {
	      checkbox: true
	      },
	      {
			   field: 'id',
			   width:'50px',
			   sortable : true,
			   title: '流水号'
			},
	      {
	       field: 'flowTitle',
	       title: '流程标题',
	       sortable : true,
	       width:'200px'
	      },
	      {
			   field: 'sendUserName',
			   width:'50px',
			   align : 'center',
			   title: '抄送人'
			},
			{
				field: 'opUserStr',
				width:'100px',
				align : 'center',
				title: '所有经办人',
				   formatter:function(value, row, index)
				     {
					   return getUserNameByStr(value);
				     }
			},
	       {
			   field: 'urgency',
			   width:'50px',
			   title: '紧急程度',
			   sortable : true,
			   align : 'center',
			   formatter:function(value, row, index)
			     {
	    		   	if(value=="0")
	    		   		{
	    		   		return "一般";
	    		   		}else if(value=="1")
	    		   			{
	    		   			return "紧急";
	    		   			}else if(value=="2")
	    		   				{
	    		   				return "加急";
	    		   				}
			     }
	       },
	       {
		    	  field: 'senToTime',
		    	  width:'100px',
		    	  align : 'center',
		    	  title: '抄送时间'
			   },
	       {
		       field: 'status',
		       width:'100px',
		       align : 'center',
		       title: '查阅状态' ,
		       formatter:function(value, row, index)
			     {
		    	   if(value=="0")
		    		{
		    		   return "未阅";
		    		}else
		    		{
		    			return "已阅";
		    		}
			     }
		   },
		   {
		    	  field: 'revTime',
		    	  width:'100px',
		    	  align : 'center',
		    	  title: '查阅时间'
			   },
	      {
	       field: 'opt',
	       title: '操作',
	       align : 'center',
	       width:'100px',
    	   formatter:function(value,row,index){
                return createOptBtn2(row.runId,row.flowId,row.sendToId);
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
function queryParams2(params) {
    var temp = {
        search: params.search,
        pageSize:this.pageSize,
        pageNumber:this.pageNumber,
        sort: params.sort,
        sortOrder:params.order,
        status:$("#status").val(),
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val(),
        flowId:$("#flows").val()
    };
    return temp;
};

function createOptBtn2(runId,flowId,sendToId)
{
	var html="<a href=\"javascript:void(0);read('"+runId+"','"+flowId+"','"+sendToId+"')\" class=\"btn btn-primary btn-xs\">查阅</a>&nbsp;&nbsp;" +
					"<a href=\"javascript:void(0);print('"+runId+"','"+flowId+"')\" class=\"btn btn-darkorange btn-xs\" >打印</a>";
	return html;
	}

function print(runId,flowId)
{
	window.open("/app/core/bpm/bpmread?runId=" + runId + "&flowId=" + flowId);
}
