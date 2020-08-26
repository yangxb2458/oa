$(function() {
	query();
});

function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/sysget/getOrgConfigLsit',
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
		      sortable: false,//排序
		      search: false,//启用搜索
		      showColumns: true,//是否显示 内容列下拉框
		      showRefresh: true,//显示刷新按钮
		      idField: 'configId',//key值栏位
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
		       field: 'unitName',
		       width:'200px',
		       title: '机构名称'
		     },
		     {
			       field: 'orgAdminName',
			       title: '机构管理员',
			       width:'100px',
			   },
			   {
			       field: 'createTime',
			       width:'100px',
			       title: '创建时间'
			   },
			   {
			       field: 'orgId',
			       width:'150px',
			       title: '机构码'
			   },
			   {
			       field: 'remark',
			       width:'200px',
			       title: '备注'
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       width:'10px',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.configId);
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
function createOptBtn(configId)
{
	var html="<a href=\"javascript:void(0);del()\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
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

function del()
{
	top.layer.msg("不可删除!,若要删除请与开发商联系!");
}

function doadd()
{
document.getElementById("form1").reset();
$("#setorgmodal").modal("show");
$(".js-save").unbind("click").click(function(){
	 if(confirm("一旦创建,不可删除!,若要删除请与开发商联系!"))
	    {
		$.ajax({
			url : "/set/sysset/addOrgConfig",
			type : "post",
			dataType : "json",
			data:{
				sortNo:$("#sortNo").val(),
				orgName:$("#orgName").val(),
				orgAdmin:$("#orgAdmin").val(),
				password:$("#password").val(),
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
	$("#setorgmodal").modal("hide");
	    }else
	    	{
	    	return;
	    	}
});
}