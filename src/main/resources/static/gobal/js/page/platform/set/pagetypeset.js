$(function() {
	query();
	$(".js-addClassCode").unbind("click").click(function(){
		doadd();
	});
});

function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/platformget/getPlatformPageTypeList',
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
		      idField: 'typeId',//key值栏位
		      clickToSelect: true,//点击选中checkbox
		      pageList : [10, 20, 30, 50],//可选择单页记录数
		      queryParams:queryParams,
		      columns: [{
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
		       field: 'typeId',
		       title: '分类标识',
		       sortable : true,
		       width:'200px'
		      },
		      {
		       field: 'title',
		       width:'200px',
		       title: '分类名称'
		     },
		     {
			       field: 'remark',
			       title: '备注说明',
			       width:'300px',
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       align:'center',
		       width:'100px',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.typeId);
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
function createOptBtn(typeId)
{
	var html="<a href=\"javascript:void(0);edit('"+typeId+"')\" class=\"btn btn-sky btn-xs\" >修改</a>&nbsp;&nbsp;<a href=\"javascript:void(0);del('"+typeId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
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
function edit(typeId)
{
	document.getElementById("form1").reset();
	$.ajax({
		url : "/ret/platformget/getPlatformPageTypeById",
		type : "post",
		dataType : "json",
		data:{
			typeId:typeId
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
	$("#setpageType").modal("show");
	$(".js-save").unbind("click").click(function(){
		$.ajax({
			url : "/set/platformset/updatePlatformPageType",
			type : "post",
			dataType : "json",
			data:{
				typeId:typeId,
				sortNo:$("#sortNo").val(),
				title:$("#title").val(),
				remark:$("#remark").val()
			},
			success : function(data) {
				if(data.status=="500")
					{
					console.log(data.msg);
					}else
						{
						top.layer.msg(data.msg);
						$("#myTable").bootstrapTable("refresh");
						}
			}
		});
		$("#setpageType").modal("hide");
	});
	
	}
function del(typeId)
{
	 if(confirm("确定删除当前分类吗？"))
	    {
	$.ajax({
		url : "/set/platformset/deletePlatformPageType",
		type : "post",
		dataType : "json",
		data:{
			typeId:typeId
		},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else
				{
				$("#myTable").bootstrapTable("refresh");
				top.layer.msg(data.msg);
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
$("#setpageType").modal("show");
$(".js-save").unbind("click").click(function(){
	$.ajax({
		url : "/set/platformset/insertPlatformPageType",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			title:$("#title").val(),
			remark:$("#remark").val()
		},
		success : function(data) {
			if(data.status=="500")
				{
				console.log(data.msg);
				}else
					{
					top.layer.msg(data.msg);
					$("#myTable").bootstrapTable("refresh");
					}
		}
	});
	$("#setpageType").modal("hide");
});
}
