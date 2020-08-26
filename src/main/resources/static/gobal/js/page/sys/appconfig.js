$(function() {
	query();
});

function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/sysget/getAppConfigList',
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
		      idField: 'appId',//key值栏位
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
						 field: 'img',
						 width:'100px',
						 align: 'center',
						 title: 'app应用图标',
						 formatter:function(value,row,index){
				                return "<img src='"+value+"' width='62px' heigth='62px' />"
				            }
				},
		      {
		       field: 'title',
		       width:'50px',
		       title: 'APP应用标题'
		     },
		     {
			       field: 'module',
			       title: 'app应用标识',
			       align: 'center',
			       width:'50px',
			   },{
			       field: 'groupTitle',
			       title: '应用分组',
			       align: 'center',
			       width:'50px',
			   },
			{
			       field: 'appUrl',
			       title: 'App应用URL',
			       align: 'center',
			       width:'150px',
			   },
			   {
			       field: 'createTime',
			       width:'100px',
			       visible:false,
			       title: '创建时间'
			   },
			   {
				    field: 'remark',
				    title: '备注',
				    visible:false,
				    width:'100px'
				   },
		      {
		       field: 'opt',
		       title: '操作',
		       align:'center',
		       width:'100px',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.appId);
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
function createOptBtn(appId)
{
	var html="<a href=\"javascript:void(0);edit('"+appId+"')\" class=\"btn btn-sky btn-xs\" >修改</a>&nbsp;&nbsp;<a href=\"javascript:void(0);del('"+appId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
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
function edit(appId)
{
	document.getElementById("form1").reset();
	$.ajax({
		url : "/ret/sysget/getAppConfigById",
		type : "post",
		dataType : "json",
		data:{
			appId:appId
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
							$("#"+name).val(data.list[name]);
							}
						}
		}
	});
	$("#setAppConfig").modal("show");
	$(".js-save").unbind("click").click(function(){
		$.ajax({
			url : "/set/sysset/updateAppConfig",
			type : "post",
			dataType : "json",
			data:{
				appId:$("#appId").val(),
				sortNo:$("#sortNo").val(),
				title:$("#title").val(),
				module:$("#module").val(),
				remark:$("#remark").val(),
				groupTitle:$("#groupTitle").val(),
				img:$("#img").val(),
				appUrl:$("#appUrl").val()
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
		$("#setAppConfig").modal("hide");
	});
	
	}
function del(appId)
{
	 if(confirm("确定删除当前App吗？"))
	    {
	$.ajax({
		url : "/set/sysset/deleteAppConfig",
		type : "post",
		dataType : "json",
		data:{
			appId:appId
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
$("#setAppConfig").modal("show");
$(".js-save").unbind("click").click(function(){
	$.ajax({
		url : "/set/sysset/insertAppConfig",
		type : "post",
		dataType : "json",
		data:{
			appId:$("#appId").val(),
			sortNo:$("#sortNo").val(),
			title:$("#title").val(),
			module:$("#module").val(),
			groupTitle:$("#groupTitle").val(),
			remark:$("#remark").val(),
			img:$("#img").val(),
			appUrl:$("#appUrl").val()
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
	$("#setAppConfig").modal("hide");
});
}