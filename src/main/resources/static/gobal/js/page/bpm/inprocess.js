$(function() {
	jeDate("#pBeginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#pEndTime", {
		format: "YYYY-MM-DD"
	});
	
	$(".js-process-query-but").unbind("click").click(function(){
		$('#myTable').bootstrapTable('refresh');
	});
	$(".js-doAllBpmUrge").unbind("click").click(function(){
		doAllBpmUrge();
	})
	getCorssBpmFlowList();
	query1();
});

function read(runId,flowId)
{
	open("/app/core/bpm/bpmread?runId=" + runId + "&flowId=" + flowId,"_self");
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
function query1()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/bpmget/getProcessBpmFlowList',
	      method: 'post',
	      contentType:'application/x-www-form-urlencoded',
	      toolbar: '#toobar1',//工具列
	      sortOrder: "desc",
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
	      idField: 'runId',//key值栏位
	      clickToSelect: true,//点击选中checkbox
	      pageList : [10, 20, 30, 50],//可选择单页记录数
	      queryParams:queryParams1,
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
			   field: 'createUserName',
			   width:'50px',
			   align : 'center',
			   title: '创建人'
			},
			{
				field: 'createTime',
				width:'50px',
				align : 'center',
				sortable : true,
				title: '创建时间'
			},
	      {
	    	  field: 'opUserStr',
	    	  width:'100px',
	    	  align : 'center',
	    	  title: '参与人员',
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
		       field: 'prcsName',
		       width:'50px',
		       align : 'center',
		       title: '当前节点',
		       formatter:function(value, row, index)
			     {
		    	   var arr=[];
		    	   var jsonArr = getNowStep(row.runId);
		    	   for(var i=0;i<jsonArr.length;i++)
		    		   {
		    		   arr.push(jsonArr[i].prcsName);
		    		   }
		    	   return "【"+arr.join(",")+"】";
			     }
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
	       width:'100px',
    	   formatter:function(value,row,index){
                return createOptBtn1(row.runId,row.flowId);
            }
	      }],
	      onClickCell: function (field, value, row, $element) {
	      //alert(row.SystemDesc);
	    },
	    responseHandler:function(res){
	    	if(res.status=="500")
	    		{
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
function queryParams1(params) {
    var temp = {
        search: params.search,
        pageSize:this.pageSize,
        pageNumber:this.pageNumber,
        sort: params.sort,
        sortOrder:params.order,
        pBpmRunNo:$("#pBpmRunNo").val(),
        pBeginTime:$("#pBeginTime").val(),
        pEndTime:$("#pEndTime").val(),
        pFlowId:$("#flows").val()
    };
    return temp;
};

function createOptBtn1(runId,flowId)
{
	var html="<a href=\"javascript:void(0);doBpmUrge('"+runId+"')\" class=\"btn btn-sky btn-xs\" >催办</a>";
	if(isCantTaskBack(runId))
	{
		html+="&nbsp;&nbsp;<a href=\"javascript:void(0);taskBack('"+runId+"')\" class=\"btn btn-purple btn-xs\">收回</a>";
	}
	html+="&nbsp;&nbsp;<a href=\"javascript:void(0);read('"+runId+"','"+flowId+"')\" class=\"btn btn-primary btn-xs\">查看</a>";
	return html;
}

function doAllBpmUrge()
{
	var selected = $('#myTable').bootstrapTable('getSelections');
	var ids = new Array();
	for(var i=0;i<selected.length;i++){
	ids[i]=selected[i].id;
	}
	doBpmUrge(ids.join(","));
}

function doBpmUrge(runId)
{
	$.ajax({
		url : "/set/bpmset/doBpmUrge",
		type : "post",
		dataType : "json",
		data:{runIds:runId},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
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

function taskBack(runId)
{
	$.ajax({
		url : "/ret/bpmget/getCanTaskBackRunProcessList",
		type : "post",
		dataType : "json",
		data:{runId:runId},
		success : function(data) {
			if(data.status=="200")
			{
				if(data.list.length==0)
					{
						top.layer.msg("对不起,没有可收回的步骤!确保下一步无人办理结束!");
					}else
					{
						$("#taskBackModal").modal("show");
						var html="";
						for(var i=0;i<data.list.length;i++)
						{
							html+="<tr>";
							html+="<td><div class='radio' style='padding-top:0px;'><label><input value='"+data.list[i].runProcessId+"' name='gobackrunprcs' type='radio' class='colored-success'><span class='text'>"+data.list[i].prcsName+"</span></div></td>";
							html+="<td>"+data.list[i].createTime+"</td>";
							if(data.list[i].recTime==""||data.list[i].recTime==null)
							{
								html+="<td>未接收</td>";
							}else
							{
								html+="<td>已接收</td>";
							}
							html+="</tr>";
						}
						$("#taskBackTbody").html(html);
						$(".js-taskback").unbind("click").click(function(){
							var runProcessId = $('input:radio[name="gobackrunprcs"]:checked').val();
							doTaskBack(runProcessId);
						});
					}
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

function doTaskBack(runProcessId)
{
	if(runProcessId=="")
	{
		top.layer.msg("请先选择需收回的步骤!");
		return;
	}
$.ajax({
	url : "/set/bpmset/doTaskBack",
	type : "post",
	dataType : "json",
	data:{runProcessId:runProcessId},
	success : function(data) {
	if(data.status=="200")
	{
		location.reload();
		top.layer.msg(data.msg);
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

function isCantTaskBack(runId)
{
	var returnValue;
	$.ajax({
		url : "/ret/bpmget/isCantTaskBackFlag",
		type : "post",
		dataType : "json",
		async : false,
		data:{runId:runId},
		success : function(data) {
		if(data.status=="200")
		{
			returnValue = data.list;
		}else if(data.status=="100")
		{
			top.layer.msg(data.msg);
		}else
		{
			console.log(data.msg);	
		}
		}
	});
	return returnValue;
}