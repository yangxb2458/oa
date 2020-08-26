$(function() {
	query();
});

function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/sysget/getSysTimingTaskList',
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
		      search: false,//启用搜索
		      showColumns: true,//是否显示 内容列下拉框
		      showRefresh: true,//显示刷新按钮
		      idField: 'taskId',//key值栏位
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
		       field: 'taskName',
		       width:'100px',
		       title: '任务名称'
		     },
		     {
			       field: 'method',
			       title: '任务方法名',
			       width:'100px',
			   },
			   {
					 field: 'rule',
					 width:'100px',
					 title: '执行规则'
			},
		     {
		       field: 'status',
		       title: '当前状态',
		       width:'50px',
				 formatter:function(value,row,index){
					 if(value=="1")
					{
						 return "<a href=\"javascript:void(0);\" class=\"btn btn-palegreen btn-xs\">启用</a>";
					}else if(value=="0")
					{
						return "<a href=\"javascript:void(0);\" class=\"btn btn-darkorange btn-xs\">停用</a>";
					}else 
						{
						return "<a href=\"javascript:void(0);\" class=\"btn btn-default btn-xs\">未知</a>";
						}
		           }
		      },
		      {
			    field: 'createUserName',
			    title: '创建人',
			    width:'100px'
			   },
			   {
			       field: 'createTime',
			       width:'100px',
			       title: '创建时间'
			   },
			   {
			       field: 'remark',
			       width:'100px',
			       title: '备注'
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       align:'center',
		       width:'150px',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.taskId);
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
function createOptBtn(taskId)
{
	var html="<a href=\"javascript:void(0);edit('"+taskId+"')\" class=\"btn btn-sky btn-xs\" >修改</a>&nbsp;&nbsp;<a href=\"javascript:void(0);del('"+taskId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
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
function edit(taskId)
{
	document.getElementById("form1").reset();
	$.ajax({
		url : "/ret/sysget/getSysTimingTaskById",
		type : "post",
		dataType : "json",
		data:{
			taskId:taskId
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
						for(var name in data.list)
							{
							if(name=="status")
							{
								$("input:radio[name='status'][value='" + data.list[name] + "']").prop("checked",true);
							}else
							{
								$("#"+name).val(data.list[name]);
							}
							}
						}
		}
	});
	$("#setTaskModal").modal("show");
	$(".js-save").unbind("click").click(function(){
		$.ajax({
			url : "/set/sysset/updateSysTimingTask",
			type : "post",
			dataType : "json",
			data:{
				taskId:taskId,
				sortNo:$("#sortNo").val(),
				taskName:$("#taskName").val(),
				method:$("#method").val(),
				rule:$("#rule").val(),
				status:$('input:radio[name="status"]:checked').val(),
				remark:$("#remark").val()
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
						$("#myTable").bootstrapTable('refresh');
						}
			}
		});
		$("#setTaskModal").modal("hide");
	});
	
	}
function del(taskId)
{
	 if(confirm("确定删除当前任务吗？"))
	    {
	$.ajax({
		url : "/set/sysset/delSysTimingTask",
		type : "post",
		dataType : "json",
		data:{
			taskId:taskId
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
				$("#myTable").bootstrapTable('refresh');
				}
		}
	});
	    }else
	    	{
	    	return;
	    	}
}

function doadd()
{
document.getElementById("form1").reset();
$("#setTaskModal").modal("show");
$(".js-save").unbind("click").click(function(){
	$.ajax({
		url : "/set/sysset/insertSysTimingTask",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			taskName:$("#taskName").val(),
			method:$("#method").val(),
			rule:$("#rule").val(),
			status:$('input:radio[name="status"]:checked').val(),
			remark:$("#remark").val()
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
					$("#myTable").bootstrapTable('refresh');
					}
		}
	});
	$("#setTaskModal").modal("hide");
});
}