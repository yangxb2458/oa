$(function(){
	query();
	$(".js-create-rule").unbind("click").click(function(){
		$("#documentRuleModal").modal("show");
		$(".js-save").unbind("click").click(function(){
			createDocumentRule();
		})
	});
})
function query()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/documentget/getDocumentNumberList',
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
	      idField: 'configId',//key值栏位
	      clickToSelect: true,//点击选中checkbox
	      pageList : [10, 20, 30, 50],//可选择单页记录数
	      queryParams:queryParams,
	      columns: [ {
	      checkbox: true
	      },
	      {
		       field: 'title',
		       title: '规则名称',
		       width:'100px'
		      },
	      {
	       field: 'rule',
	       title: '文号规则',
	       width:'100px'
	      },
	      {
			   field: 'startNumber',
			   width:'50px',
			   align : 'center',
			   title: '起始号'
			},
			{
				field: 'number',
				width:'50px',
				align : 'center',
				title: '当前序号'
			}, 
	      {
	       field: 'opt',
	       title: '操作',
	       align : 'center',
	       width:'50px',
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
	var html="<a href=\"javascript:void(0);edit('"+configId+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" +
	"<a href=\"javascript:void(0);deleteConfig('"+configId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
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

function edit(configId)
{
	$("#documentRuleModal").modal("show");
	$.ajax({
		url : "/ret/documentget/getDocumentNumberById",
		type : "post",
		dataType : "json",
		data:{configId:configId},
		success : function(data) {
			if(data.status=="200")
			{
				for(var id in data.list)
				{
					$("#"+id).val(data.list[id]);
				}
				$(".js-save").unbind("click").click(function(){
					updateDocumentRule(configId);
				})
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


function deleteConfig(configId)
{
	if(confirm("确定删除当前文号规则吗？"))
    {
	$.ajax({
		url : "/set/documentset/deleteDocumentNumber",
		type : "post",
		dataType : "json",
		data:{configId:configId},
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

function createDocumentRule()
{
	$.ajax({
		url : "/set/documentset/insertDocumentNumber",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			title:$("#title").val(),
			rule:$("#rule").val(),
			startNumber:$("#startNumber").val(),
			number:$("#number").val()
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
				$('#myTable').bootstrapTable('refresh');
				top.layer.msg(data.msg);
				$("#documentRuleModal").modal("hide");
				}
		}
	})
}


function updateDocumentRule(configId)
{
	$.ajax({
		url : "/set/documentset/updateDocumentNumber",
		type : "post",
		dataType : "json",
		data:{
			configId:configId,
			sortNo:$("#sortNo").val(),
			title:$("#title").val(),
			rule:$("#rule").val(),
			startNumber:$("#startNumber").val(),
			number:$("#number").val()
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
				$('#myTable').bootstrapTable('refresh');
				top.layer.msg(data.msg);
				$("#documentRuleModal").modal("hide");
				}
		}
	})
}