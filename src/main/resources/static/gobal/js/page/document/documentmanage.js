$(function() {
	getAllDocumentFlowListByManage();
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	$(".js-process-query-but").unbind("click").click(function(){
		$('#myTable').bootstrapTable('refresh');
	});
	query();
});
function getAllDocumentFlowListByManage()
{
	$.ajax({
		url : "/ret/documentget/getAllDocumentFlowListByManage",
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
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/documentget/getDocumentMaintainList',
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
		      idField: 'runId',//key值栏位
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
		    	  width:'50px',
		    	  align : 'center',
		    	  title: '所有经办人',
		    	  formatter:function(value, row, index)
				     {
		    		  return  getUserNameByStr(value);
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
			       title: '当前步骤',
			       formatter:function(value, row, index)
				     {
			    	   var arr=[];
			    	   var jsonArr = getNowStep(row.runId);
			    	   if(jsonArr.length==0)
			    		{
			    		   return "【结束】";
			    		}else
			    		{
			    			for(var i=0;i<jsonArr.length;i++)
			    			{
			    				arr.push(jsonArr[i].prcsName);
			    			}
			    			return "【"+arr.join(",")+"】";
			    		}
				     }
			   },
			   {
			       field: 'status',
			       width:'50px',
			       align : 'center',
			       title: '状态',
			       formatter:function(value, row, index)
				     {
			    	   if(value=="0")
			    		   {
			    		   		return "<a href=\"javascript:void(0);\" class=\"btn btn-palegreen btn-xs\">进行中</a>";
			    		   }else if(value=="1")
			    			{
			    			   return " <a href=\"javascript:void(0);\" class=\"btn btn-darkorange btn-xs\">已结束</a>";   
			    			}else
			    				{
			    				return "<a href=\"javascript:void(0);\" class=\"btn btn-darkorange btn-xs\">异常</a>";	
			    				}
			    	   
				     }
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       align : 'center',
		       width:'150px',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.runId,row.flowId,row.status);
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
function createOptBtn(runId,flowId,status)
{
	var html="";
	if(status!="1")
	{
		html+="<a href=\"javascript:void(0);toNext('"+runId+"')\" class=\"btn btn-sky btn-xs\" >转交</a>&nbsp;&nbsp;" +
			  "<a href=\"javascript:void(0);goback('"+runId+"')\" class=\"btn btn-primary btn-xs\">回退</a>&nbsp;&nbsp;";
	}
	html+="<a href=\"javascript:void(0);del('"+runId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);read('" + runId + "','" + flowId + "')\" class=\"btn btn-primary btn-xs\">查看</a>";
	return html;
	}
function read(runId, flowId) {
	open("/app/core/document/documentread?runId=" + runId + "&flowId=" + flowId,"_self");
}
function queryParams(params) {
    var temp = {
        search: params.search,
        pageSize:this.pageSize,
        pageNumber:this.pageNumber,
        sort: params.sort,
        sortOrder:params.order,
        createUser:$("#createUser").attr("data-value"),
        flowId:$("#flows").val(),
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val(),
        documentRunNo:$("#documentRunNo").val()
        
    };
    return temp;
};
function toNext(runId)
{
	var arr = getChangeDocumentUserStep(runId);
	if(arr.length==0)
	{
		top.layer.msg("对不起，此流程暂无可转交的步骤！");
		return;
	}
	$("#toNextModal").modal("show");
	document.getElementById("form1").reset();
	var html="";
	for(var i=0;i<arr.length;i++)
		{
		html+="<tr><td style='vertical-align: middle;'>"+(i+1)+"</td><td style='vertical-align: middle;'>"+arr[i].prcsName+"</td>" +
			"<td style='vertical-align: middle;'>"+arr[i].userName+"</td><td style='vertical-align: middle;'>"+arr[i].createTime+"</td>" +
			"<td style='vertical-align: middle;'><input id=\"opt_id_"+arr[i].runProcessId+"\" type='text' class='form-control js-users' data-run-process-id=\""+arr[i].runProcessId+"\" readonly/></td>" +
			"<td style='vertical-align: middle;'><a href=\"javascript:void(0);\" opt-id=\"opt_id_"+arr[i].runProcessId+"\" onclick=\"selectUser(this,'false');\" class=\"btn btn-magenta shiny\" >选择转交人</a></td>" +
			"</tr>";
		}
	$("#toNextTbody").html(html);
	$(".js-toNextbtn").unbind("click").click(function(){
		$(".js-users").each(function(){
			var accountId=$(this).attr("data-value");
			var runProcessId = $(this).attr("data-run-process-id");
			if(accountId==""||accountId==undefined)
				{
				top.layer.msg("请选择需转交的人员!");
				return;
				}
			$.ajax({
				url : "/set/documentset/changeDocumentUser",
				type : "post",
				dataType : "json",
				data:{runId:runId,
					 accountId:accountId,
					 runProcessId:runProcessId},
				success : function(data) {
					if(data.status=="200")
						{
							top.layer.msg(data.msg);
							$("#toNextModal").modal("hide");
						}else if(data.status=="100")
						{
							top.layer.msg(data.msg);
						}else
						{
							console.log(data.msg);
						}
				}
			});
			
		})
	});
}


function del(runId)
{
	if(confirm("确认删除该流程吗?")){
		　　	$.ajax({
				url : "/set/documentset/delDocumentList",
				type : "post",
				dataType : "json",
				data:{runId:runId},
				success : function(data) {
					if(data.status=="200")
						{
							top.layer.msg(data.msg);
							$("#myTable").bootstrapTable("refresh");
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
	}

function goback(runId)
{
	$.ajax({
		url : "/ret/documentget/getCanGoBckProcessList",
		type : "post",
		dataType : "json",
		data : {
			runId : runId
		},
		success : function(data) {
			if (data.status == "200") {
				$("#goBackProcessModal").modal("show");
				var html="";
				for(var i=0;i<data.list.length;i++)
					{
					html+="<tr>";
					html+="<td><div class='radio' style='margin-top:0px;margin-bottom:0px;'><label style='padding-left:0px'><input value='"+data.list[i].runProcessId+"' name='gobackradio' type='radio' class='colored-blue'><span class='text'></span></label></div></td>";
					html+="<td>"+data.list[i].prcsName+"</td>";
					html+="<td>"+data.list[i].opUserName+"</td>";
					html+="<td>"+data.list[i].endTime+"</td>";
					html+="</tr>";
					}
				$("#cangotbody").html(html);
				$(".js-goBackProcess").unbind("click").click(function(){
					var goBackRunProcessId = $('input:radio[name="gobackradio"]:checked').val();
					if(goBackRunProcessId==""||goBackRunProcessId==null)
						{
						top.layer.msg("请选择需要回退的步骤！");
						return;
						}
						$.ajax({
							url : "/set/documentset/setMaintainGobackOpt",
							type : "post",
							dataType : "json",
							data : {
								runId : runId,
								runProcessId:goBackRunProcessId
							},
							success : function(data) {
								if(data.status=="200")
								{
									$("#goBackProcessModal").modal("hide");
									$("#myTable").bootstrapTable("refresh");
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
				});
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
		});
	
}