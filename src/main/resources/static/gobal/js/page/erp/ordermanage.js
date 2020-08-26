$(function() {
	query();
});

function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/erpget/getErpOrderList',
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
		      idField: 'orderId',//key值栏位
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
		       field: 'orderCode',
		       title: '订单编号',
		       sortable : true,
		       width:'200px'
		      },
		      {
		       field: 'orderTitle',
		       width:'200px',
		       title: '订单标题'
		     },
		     {
			       field: 'customer',
			       title: '客户名称'
			   },
			   {
					 field: 'linkName',
					 width:'100px',
					 title: '联系人'
					},
			{
				field: 'tel',
				width:'100px',
				title: '电话'
			},
		     {
		       field: 'payType',
		       title: '付款方式',
		       width:'100px',
		       formatter:function(value,row,index){
	                return getPayType(value);
	            }
		      },
			   {
			       field: 'remark',
			       title: '备注'
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       width:'180px',
		       align:'center',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.orderId);
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
function createOptBtn(orderId)
{
	var html="<a href=\"javascript:void(0);addproduct('"+orderId+"')\" class=\"btn btn-sky btn-xs\" >添加产品</a>&nbsp;&nbsp;<a href=\"javascript:void(0);edit('"+orderId+"')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;<a href=\"javascript:void(0);del('"+orderId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
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
function addproduct(orderId)
{
	location.href="/app/core/erp/cost/orderproduct?orderId="+orderId;
	}

function edit(orderId)
{
	location.href="/app/core/erp/cost/order?orderId="+orderId;
	}
/**
 * 新增一行数据
 */
function createOrder(){
	location.href="/app/core/erp/cost/order";
}



function del(orderId)
{
	var msg = "您真的确定要删除吗？\n\n请确认！"; 
	if (confirm(msg)==true){
		$.ajax({
			url : "/set/erpset/deleteErpOrder",
			type : "post",
			dataType : "json",
			data:{
				orderId:orderId
			},
			success : function(data) {
				if(data.status==200){
					top.layer.msg(data.msg);
					location.reload();
				}else
					{
					console.log(data.msg);
					}
			}
		});
	}else{ 
	return; 
	} 
}

