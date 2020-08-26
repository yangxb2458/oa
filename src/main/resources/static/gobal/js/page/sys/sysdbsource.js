$(function() {
	query();
});

function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/sysget/getSysDbSourctList',
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
		      idField: 'dbSourceId',//key值栏位
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
		       field: 'dbSourceName',
		       width:'200px',
		       title: '数据源名称'
		     },
		     {
			       field: 'dbSourceType',
			       title: '数据库类型',
			       width:'50px',
			   },
			   {
					 field: 'dbLink',
					 width:'100px',
					 title: '数据库连接'
			},
		     {
		       field: 'dbUserName',
		       title: '数据库用户名',
		       width:'100px'
		      },
		      {
			    field: 'dbPassWord',
			    title: '数据库密码',
			    width:'100px'
			   },
			   {
			       field: 'createTime',
			       width:'100px',
			       title: '创建时间'
			   },
			   {
			       field: 'remark',
			       width:'50px',
			       title: '备注'
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       align:'center',
		       width:'150px',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.dbSourceId);
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
function createOptBtn(dbSourceId)
{
	var html="<a href=\"javascript:void(0);edit('"+dbSourceId+"')\" class=\"btn btn-sky btn-xs\" >修改</a>&nbsp;&nbsp;<a href=\"javascript:void(0);del('"+dbSourceId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
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
function edit(dbSourceId)
{
	document.getElementById("form1").reset();
	$.ajax({
		url : "/ret/sysget/getDbSource",
		type : "post",
		dataType : "json",
		data:{
			dbSourceId:dbSourceId
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
	$("#setdbsource").modal("show");
	$(".js-save").unbind("click").click(function(){
		$.ajax({
			url : "/set/sysset/updateDbSource",
			type : "post",
			dataType : "json",
			data:{
				dbSourceId:$("#dbSourceId").val(),
				sortNo:$("#sortNo").val(),
				dbSourceName:$("#dbSourceName").val(),
				dbSourceType:$("#dbSourceType").val(),
				dbLink:$("#dbLink").val(),
				dbUserName:$("#dbUserName").val(),
				dbPassWord:$("#dbPassWord").val()
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
		$("#setdbsource").modal("hide");
	});
	
	}
function del(dbSourceId)
{
	 if(confirm("确定删除当前数据源吗？"))
	    {
	$.ajax({
		url : "/set/sysset/deleteDbSource",
		type : "post",
		dataType : "json",
		data:{
			dbSourceId:dbSourceId
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
$("#setdbsource").modal("show");
$(".js-save").unbind("click").click(function(){
	$.ajax({
		url : "/set/sysset/insertDbSource",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			dbSourceName:$("#dbSourceName").val(),
			dbSourceType:$("#dbSourceType").val(),
			dbLink:$("#dbLink").val(),
			dbUserName:$("#dbUserName").val(),
			dbPassWord:$("#dbPassWord").val()
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
	$("#setdbsource").modal("hide");
});
}