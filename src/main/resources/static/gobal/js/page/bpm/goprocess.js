$(function() {
	$(".js-d-query-but").unbind("click").click(function(){
		$('#myTable').bootstrapTable('refresh');
	});
	getCorssBpmFlowList();
	query();
});

function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/bpmget/getGoProcessList',
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
		      sortOrder: "desc",
		      showColumns: true,//是否显示 内容列下拉框
		      showRefresh: true,//显示刷新按钮
		      idField: 'runProcessId',//key值栏位
		      clickToSelect: true,//点击选中checkbox
		      pageList : [10, 20, 30, 50],//可选择单页记录数
		      queryParams:queryParams,
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
				   field: 'createUser',
				   width:'50px',
				   align : 'center',
				   title: '发送人'
				},
				{
					field: 'createTime',
					width:'50px',
					align : 'center',
					sortable : true,
					title: '发送时间'
				},
		      {
		    	  field: 'passTime',
		    	  width:'50px',
		    	  align : 'center',
		    	  title: '办理时限',
		    	  formatter:function(value, row, index)
				     {
		    		  if(value)
		    			  {
		    			  return value+"分钟";
		    			  }else
		    				  {
		    				  return "无限制";
		    				  }
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
			       field: 'prcsName',
			       width:'50px',
			       align : 'center',
			       title: '步骤名称'
			   },
			   {
			       field: '',
			       width:'50px',
			       align : 'center',
			       title: '状态',
			       formatter:function(value, row, index)
				     {
			    	   if(row.passTime)
			    		   {
				    	   var timestamp1 = Date.parse(new Date(row.createTime))+row.passTime*60;
				    	   if(getPassTimeStatus(timestamp1))
				    		   {
				    		   	return "<a href=\"javascript:void(0);\" class=\"btn btn-palegreen btn-xs\">正常</a>";
				    		   }else
				    			   {
				    			   return "<a href=\"javascript:void(0);\" class=\"btn btn-darkorange btn-xs\">超时</a>";
				    			   }
			    		   }else
			    			   {
			    			   return "<a href=\"javascript:void(0);\" class=\"btn btn-palegreen btn-xs\">正常</a>";
			    			   }
			    	   
				     }
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       align : 'center',
		       width:'150px',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.runId,row.runProcessId,row.sendId,row.freeToOther,row.flowId);
	            }
		      }],
		      onClickCell: function (field, value, row, $element) {
		      //alert(row.SystemDesc);
		    },
		    responseHandler:function(res){
		    	if(res.status=="500")
		    		{
		    			console.log(res.msg);
		    		}else if(res.status=="100"){
		    			console.log(res.msg);
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
function createOptBtn(runId,runProcessId,sendId,freeToOther,flowId)
{
	var html="<a href=\"javascript:void(0);dowork('"+runId+"','"+runProcessId+"')\" class=\"btn btn-sky btn-xs\" >办理</a>&nbsp;&nbsp;";
	if(sendId!=""&&sendId!=null&&sendId!=undefined)
	{
		if(freeToOther!="0")
		{
			html+="<a href=\"javascript:void(0);entrust('"+runId+"','"+runProcessId+"')\" class=\"btn btn-primary btn-xs\">委托</a>&nbsp;&nbsp;"
		}
	}
		html+="<a href=\"javascript:void(0);read('"+runId+"','"+flowId+"')\" class=\"btn btn-primary btn-xs\">详情</a>&nbsp;&nbsp;";
	if(sendId==""||sendId==null||sendId==undefined)
	{
		html+="<a href=\"javascript:void(0);delBpm('"+runId+"','"+runProcessId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	}
	return html;
}

function read(runId,flowId)
{
	open("/app/core/bpm/bpmread?runId=" + runId + "&flowId=" + flowId,"_self");
}



function delBpm(runId,runProcessId)
{
	$.ajax({
		url : "/set/bpmset/delBpm",
		type : "post",
		dataType : "json",
		data:{runId:runId,runProcessId:runProcessId},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$('#myTable').bootstrapTable('refresh');
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data);	
			}
		}
	});
}

function entrust(runId,runProcessId)
{
	document.getElementById("form1").reset();
	$("#entrustmodal").modal("show");
	$.ajax({
		url : "/ret/bpmget/getEntrustUser",
		type : "post",
		dataType : "json",
		data:{runId:runId,runProcessId:runProcessId},
		success : function(data) {
			if(data.status=="200")
				{
				var html="<option></option>";
				for(var i=0;i<data.list.length;i++)
					{
						html+="<option value=\""+data.list[i].accountId+"\">"+data.list[i].userName+"</option>";
					}
				$("#entrustUser").html(html);
				$(".js-entrust").unbind("click").click(function(){
					doentrust(runId,runProcessId);
				})
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else
				{
					console.log(data);
				}
		}
	});
}

function doentrust(runId,runProcessId)
{
	$.ajax({
		url : "/set/bpmset/changeBpmUser",
		type : "post",
		dataType : "json",
		data:{
			runId:runId,
			runProcessId:runProcessId,
			accountId:$("#entrustUser").val()
			},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$('#myTable').bootstrapTable('refresh');
				$("#entrustmodal").modal("hide");
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



function dowork(runId,runProcessId)
{
	open("/app/core/bpm/dowork?runId="+runId+"&runProcessId="+runProcessId,"_self");
}
function queryParams(params) {
    var temp = {
        search: params.search,
        pageSize:this.pageSize,
        pageNumber:this.pageNumber,
        sort: params.sort,
        sortOrder:params.order,
        bpmRunNo:$("#bpmRunNo").val(),
        createUser:$("#createUser").attr("data-value"),
        flowId:$("#flows").val()
    };
    return temp;
};


function getCorssBpmFlowList()
{
	$.ajax({
		url : "/ret/bpmget/getCorssBpmFlowList",
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
