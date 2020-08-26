$(function() {
	query();
});

function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/bpmget/getBpmPluginsList',
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
		      idField: 'pluginsId',//key值栏位
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
		       field: 'title',
		       width:'150px',
		       title: '插件名称'
		     },
		     {
			       field: 'packName',
			       title: '插件包名',
			       width:'50px',
			   },
			   {
					 field: 'className',
					 width:'100px',
					 title: '插件类名'
			},
		     {
		       field: 'method',
		       title: '插件方法名',
		       width:'100px'
		      },
		      {
			       field: 'status',
			       width:'50px',
			       title: '状态',
			       formatter:function(value,row,index)
			       {
			    	   if(value=="0"){
			    		   	return "<a href=\"javascript:void(0);\" class=\"btn btn-palegreen btn-xs\">使用中</a>";
			    		    }else if(value=="1"){
			    			  return "<a href=\"javascript:void(0);\" class=\"btn btn-danger btn-xs\">已停用</a>";
			    		   }else{
			    			   return "<a href=\"javascript:void(0);\" class=\"btn btn-yellow btn-xs\">未知</a>";
			    			}
			    		
			       }
			   },
		      {
			    field: 'remark',
			    title: '备注说明',
			    width:'250px'
			   },
			   {
			       field: 'createTime',
			       width:'150px',
			       title: '创建时间'
			   },
			   {
			       field: 'createUserName',
			       width:'100px',
			       title: '创建人员'
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       width:'150px',
		       align:'center',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.pluginsId,row.status);
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
function createOptBtn(pluginsId,status)
{
	var html="<a href=\"javascript:void(0);edit('"+pluginsId+"');\" class=\"btn btn-sky btn-xs\" >修改</a>&nbsp;&nbsp;<a href=\"javascript:void(0);del('"+pluginsId+"');\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	if(status=="0"||status=="")
		{
			html+="&nbsp;&nbsp;<a href=\"javascript:void(0);updateStatus('"+pluginsId+"','"+status+"');\" class=\"btn btn-purple btn-xs\" >停用</a>";
		}else
		{
			html+="&nbsp;&nbsp;<a href=\"javascript:void(0);updateStatus('"+pluginsId+"','"+status+"');\" class=\"btn btn-azure btn-xs\" >启用</a>";
		}
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
function edit(pluginsId)
{
	document.getElementById("form1").reset();
	$.ajax({
		url : "/ret/bpmget/getBpmPluginsRegisterById",
		type : "post",
		dataType : "json",
		data:{
			pluginsId:pluginsId
		},
		success : function(data) {
			console.log(data.list);
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
							$("#"+name).val(data.list[name]);
							}
						}
		}
	});
	$("#setpluginsmodal").modal("show");
	$(".js-save").unbind("click").click(function(){
		$.ajax({
			url : "/set/bpmset/updateBpmPluginsRegister",
			type : "post",
			dataType : "json",
			data:{
				pluginsId:pluginsId,
				sortNo:$("#sortNo").val(),
				title:$("#title").val(),
				packName:$("#packName").val(),
				className:$("#className").val(),
				method:$("#method").val(),
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
		$("#setpluginsmodal").modal("hide");
	});
	
	}
function del(pluginsId)
{
	 if(confirm("确定删除当前插件吗？"))
	    {
	$.ajax({
		url : "/set/bpmset/deleteBpmPluginsRegister",
		type : "post",
		dataType : "json",
		data:{
			pluginsId:pluginsId
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
$("#setpluginsmodal").modal("show");
$(".js-save").unbind("click").click(function(){
	$.ajax({
		url : "/set/bpmset/insertBpmPluginsRegister",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			title:$("#title").val(),
			packName:$("#packName").val(),
			className:$("#className").val(),
			method:$("#method").val(),
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
	$("#setpluginsmodal").modal("hide");
});
}


function updateStatus(pluginsId,status)
{
	 if(confirm("确定需要改变当前插件状态吗？"))
	    {
	if(status=="0"||status=="")
	{
		status="1";
	}else
	{
		status="0";
	}
	$.ajax({
		url : "/set/bpmset/updateBpmPluginsRegister",
		type : "post",
		dataType : "json",
		data:{
			pluginsId:pluginsId,
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
					$("#myTable").bootstrapTable('refresh');
					}
		}
	});
	    }else
	    {
	    	return;
	    }
}