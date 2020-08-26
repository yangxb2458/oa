$(function() {
	query();
	$(".js-addClassCode").unbind("click").click(function(){
		doadd();
	});
	$(".js-delAll").unbind("click").click(function(){
		dodelAll();
	});
});

function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/platformget/getPlatformCodeClassList',
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
		      idField: 'codeClassId',//key值栏位
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
		       field: 'module',
		       title: '模块标识',
		       sortable : true,
		       width:'200px'
		      },
		      {
		       field: 'codeName',
		       width:'200px',
		       title: '分类编码名称'
		     },
		     {
			       field: 'codeValue',
			       title: '分类编码值',
			       width:'100px',
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       align:'center',
		       width:'150px',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.codeClassId);
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
function createOptBtn(codeClassId)
{
	var html="<a href=\"javascript:void(0);edit('"+codeClassId+"')\" class=\"btn btn-sky btn-xs\" >修改</a>&nbsp;&nbsp;<a href=\"javascript:void(0);del('"+codeClassId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
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
function edit(codeClassId)
{
	document.getElementById("form1").reset();
	$.ajax({
		url : "/ret/platformget/getPlatformCodeClassById",
		type : "post",
		dataType : "json",
		data:{
			codeClassId:codeClassId
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
	$("#setCodeClass").modal("show");
	$(".js-save").unbind("click").click(function(){
		$.ajax({
			url : "/set/platformset/updatePlatformCodeClass",
			type : "post",
			dataType : "json",
			data:{
				codeClassId:$("#codeClassId").val(),
				sortNo:$("#sortNo").val(),
				module:$("#module").val(),
				codeName:$("#codeName").val(),
				codeValue:$("#codeValue").val()
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
		$("#setCodeClass").modal("hide");
	});
	
	}
function del(codeClassId)
{
	 if(confirm("确定删除当前分类吗？"))
	    {
	$.ajax({
		url : "/set/platformset/deletePlatformCodeClass",
		type : "post",
		dataType : "json",
		data:{
			codeClassId:codeClassId
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
$("#setCodeClass").modal("show");
$(".js-save").unbind("click").click(function(){
	$.ajax({
		url : "/set/platformset/insertPlatformCodeClass",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			module:$("#module").val(),
			codeName:$("#codeName").val(),
			codeValue:$("#codeValue").val()
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
	$("#setCodeClass").modal("hide");
});
}

function dodelAll()
{
	var a= $("#myTable").bootstrapTable('getSelections');
	var classCodeArr=[];
	for(var i=0;i<a.length;i++){
		classCodeArr.push(a[i].codeClassId);
	}
	if(classCodeArr.length<=0)
	{
		top.layer.msg("至少选择一个一个分类码!")
		return;
	}else
	{
		if(confirm("确定删除当前分类吗？"))
	    {
		$.ajax({
			url : "/set/platformset/deletePlatfromCodeClassBatch",
			type : "post",
			dataType : "json",
			data:{
				classCodeArr:classCodeArr
			},
			success : function(data) {
				if(data.status==200){
					top.layer.msg(data.msg);
				}else if(data.status="100")
					{
					top.layer.msg(data.msg);
					}else
					{
					console.log(data.msg);
					$("#myTable").bootstrapTable("refresh");
					}
			}
		});
	    }else
	    {
	    	return;
	    }
	}
}