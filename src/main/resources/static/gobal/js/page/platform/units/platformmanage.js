$(function(){
	query()
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/platformget/getManagePlatformDataList',
		      method: 'post',
		      contentType:'application/x-www-form-urlencoded',
		      toolbar: '#toobar',// 工具列
		      striped: true,// 隔行换色
		      cache: false,// 禁用缓存
		      pagination: true,// 启动分页
		      sidePagination: 'server',// 分页方式
		      pageNumber: 1,// 初始化table时显示的页码
		      pageSize: 10,// 每页条目
		      showFooter: false,// 是否显示列脚
		      showPaginationSwitch: true,// 是否显示 数据条数选择框
		      sortable: true,// 排序
		      search: true,// 启用搜索
		      showColumns: true,// 是否显示 内容列下拉框
		      showRefresh: true,// 显示刷新按钮
		      idField: 'recordId',// key值栏位
		      clickToSelect: true,// 点击选中checkbox
		      pageList : [10, 20, 30, 50],// 可选择单页记录数
		      queryParams:queryParams,
		      columns: columns,
		      onClickCell: function (field, value, row, $element) {
		    },
		    responseHandler:function(res){
		    	if(res.status=="500")
	    		{
	    		console.log(res.msg);
	    		}else if(res.status=="100")
	    			{
	    			top.layer.msg(res.msg);
	    			}else
	    			{
	    			return {
	    				total : res.list.total, // 总页数,前面的key必须为"total"
	    				rows : res.list.list // 行数据，前面的key要与之前设置的dataField的值一致.
	    			};
	    			}
		    }
		   });
	}
function queryParams(params) {
    var temp = {
        search: params.search,
        pageSize:this.pageSize,
        pageNumber:this.pageNumber,
        sort: params.sort,
        sortOrder:params.order,
        whereStr:JSON.stringify(getQueryFields())
    };
    return temp;
};


function getQueryFields()
{
	var json={};
	$("[xtype='xquery']").each(function() {
		var id=$(this).attr("id");
		var value=$(this).val();
		if(value!="")
		{
			json[id]=value;
		}
	});
	return json;
}

function returnval(value)
{
	return JSON.parse(value).value;
}
function readrecord(recordId)
{
	window.open("/app/core/platform/apppagedetails?menuId="+menuId+"&pageId="+pageId+"&recordId="+recordId);
}
function deleterecord(recordId)
{
	if(confirm("确定删除当前记录吗？"))
    {
	$.ajax({
		url : "/set/platformset/deletePlatformRecord",
		type : "post",
		dataType : "json",
		data:{
			recordId:recordId,
			pageId:pageId,
			menuId:menuId
			},
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
