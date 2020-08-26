$(function(){
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	$(".js-query").unbind("click").click(function(){
		$('#myTable').bootstrapTable('refresh');
	});
	query();
});

function query()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/projectbuildget/contract/querycontractlist',
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
	       title: '合同标题',
	       sortable : true,
	       width:'200px'
	      },
	      {
		       field: 'signPart',
		       title: '对方单位',
		       width:'200px'
	      },
	      {
			field: 'sortName',
			   width:'50px',
			   title: '所属分类'
			},
	      {
		       field: 'type',
		       width:'80px',
		       title: '合同类型',
		       formatter:function(value,row,index){
		    	   if(value=="1")
		    		   {
		    		   	  return "材料采购合同";
		    		   }else if(value=="2")
		    		   {
		    			   return "工程项目合同";
		    		   }else if(value=="3")
		    		   {
		    			   return "服务合同";
		    		   }
	            }
		   },
	      {
		       field: 'signTime',
		       width:'100px',
		       title: '签订时间'
		   },
		   {
		       field: 'amount',
		       width:'100px',
		       title: '合同金额(元)'
		       
		   },
		   {
		       field: 'createUserName',
		       width:'80px',
		       title: '创建人'
		       
		   },
	      {
	       field: 'opt',
	       title: '操作',
	       align:'center',
	       width:'150px',
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
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val(),
        type:$("#type").val(),
        signUser:$("#singUser").attr("data-value")
    };
    return temp;
};
function createOptBtn(contractId)
{
	var html="<a href=\"javascript:void(0);readcontract('"+contractId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}

function readcontract(contractId)
{
	window.open("/app/core/projectbuild/contract/details?contractId="+contractId);
}