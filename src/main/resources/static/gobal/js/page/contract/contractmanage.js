$(function(){
	query();
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/contractget/getContractManageList',
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
		      idField: 'contractId',//key值栏位
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
			       field: 'contractCode',
			       title: '合同编号',
			       width:'100px'	  
		      },
		      {
		       field: 'title',
		       title: '合同名称',
		       sortable : true,
		       width:'150px'
		      },
		      {
			       field: 'customerName',
			       title: '签订方',
			       sortable : true,
			       width:'150px'
			      },
		      {
				field: 'contractType',
				   width:'50px',
				   title: '合同类型',
				   formatter:function(value,row,index){
					   if(value=="1")
						 {
						   return "销售合同";
						}else if(value=="2")
						{
							return "采购合同";   
						}else if(value=="3")
						{
							return "服务合同";
						}
		            }
				},
				{
				       field: '',
				       title: '合同期限',
				       width:'100px',
				       formatter:function(value,row,index){
				    	   return row.startTime+"到"+row.endTime;
				       }
				},
		      {
			       field: 'total',
			       width:'50px',
			       title: '合同总额'
			   },
			   {
			       field: 'realTotal',
			       width:'50px',
			       title: '已收款'
			   },
			   {
			       field: 'createTime',
			       width:'100px',
			       title: '创建时间',
			    visible: false
			   },
		      {
		       field: 'opt',
		       width:'100px',
		       title: '操作',
		       align:'center',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.contractId);
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
        sortOrder:params.order,
        contractType:$("#contractType").val(),
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val()
    };
    return temp;
};
function createOptBtn(contractId)
{
	var html="<a href=\"javascript:void(0);readDetails('"+contractId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);edit('"+contractId+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;";
	html+="<a href=\"javascript:void(0);del('"+contractId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}

function edit(contractId)
{
	open("/app/core/contract/create?view=edit&contractId="+contractId,"_self");
}


function readDetails(contractId)
{
	window.open("/app/core/contract/contractdetails?contractId="+contractId);
	}

function del(contractId)
{
	if(window.confirm("确定删除选中的合同吗？")){
	$.ajax({
		url : "/set/contractset/deleteContract",
		type : "post",
		dataType : "json",
		data:{contractId:contractId},
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
	}else
		{
		return;
		}
	
}
