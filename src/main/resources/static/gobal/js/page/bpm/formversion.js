$(function(){
	query();
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/bpmget/getBpmFormVersionList?formId='+formId,
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
		      idField: 'versionId',//key值栏位
		      clickToSelect: true,//点击选中checkbox
		      pageList : [10, 20, 30, 50],//可选择单页记录数
		      queryParams:queryParams,
		      columns: [ {
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
		       field: 'title',
		       title: '版本标题',
		       width:'100px'
		      },
		      {
				field: 'formTitle',
				   width:'100px',
				   title: '表单名称'
				},
				{
			       field: 'tableName',
			       title: '数据库表名',
			       width:'50px',
			    	   formatter:function(value,row,index){
			                return "bpm_"+value;
			            }
		      },
		      {
			       field: 'createUserName',
			       width:'50px',
			       title: '创建人'
			   },
			   {
			       field: 'createTime',
			       width:'100px',
			       title: '创建时间'
			   },
		      {
		       field: 'opt',
		       width:'150px',
		       align:'center',
		       title: '操作',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.versionId);
	            }
		      }],
		      onClickCell: function (field, value, row, $element) {
		      //alert(row.SystemDesc);
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
	    				total : res.list.total, //总页数,前面的key必须为"total"
	    				rows : res.list.list //行数据，前面的key要与之前设置的dataField的值一致.
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
        sortOrder:params.order
    };
    return temp;
};
function createOptBtn(versionId)
{
	var html="<a href=\"javascript:void(0);preview('"+versionId+"')\" class=\"btn btn-sky btn-xs\" >预览</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);recovery('"+versionId+"')\" class=\"btn btn-success btn-xs\" >恢复</a>&nbsp;&nbsp;"+
			"<a href=\"javascript:void(0);delversion('"+versionId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}

function preview(versionId) {
	window.open('/app/core/bpm/bpmfromversiondesigner?versionId='+versionId);
}

function recovery(versionId)
{
	if(confirm("确定恢复当前表单版本到生产系统中吗？"))
    {
		$.ajax({
			url : "/set/bpmset/recoveryBpmFormVersion",
			type : "post",
			dataType : "json",
			data:{versionId:versionId},
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
					}
			}
		})	
    }
}

function delversion(versionId)
{
	if(confirm("确定删除当前表单版本吗？"))
    {
		$.ajax({
			url : "/set/bpmset/deleteBpmFormVersion",
			type : "post",
			dataType : "json",
			data:{versionId:versionId},
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
					$('#myTable').bootstrapTable('refresh');
					}
			}
		})	
    }
}