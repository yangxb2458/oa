$(function(){
	query();
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/projectbuildsupplierget/getSupplierList',
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
		      idField: 'supplierId',//key值栏位
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
		       field: 'companyName',
		       title: '供应商名称',
		       sortable : true,
		       width:'200px'
		      },
		      {
				field: 'level',
				   width:'50px',
				   title: '供应商等级',
				   formatter:function(value,row,index){
			    	if(value=="1")
			    		{
			    			return "优秀供应商";
			    		}else if(value=="2")
			    		{
			    			return "一般供应商";
			    		}else if(value=="3")
			    		{
			    			return "劣质供应商";
			    		}
		            }
				},
				{
			       field: 'city',
			       title: '地区',
			       width:'50px'		  
		      },
		      {
			       field: 'status',
			       width:'100px',
			       title: '经营状态',
			       formatter:function(value,row,index){
			    	if(value=="1")
			    		{
			    			return "优";
			    		}else if(value=="2")
			    		{
			    			return "良";
			    		}else if(value=="3")
			    		{
			    			return "差";
			    		}
		            }
			   },
		      {
			       field: 'linkName',
			       width:'100px',
			       title: '联系人'
			   },
			   {
			       field: 'tel',
			       width:'100px',
			       title: '联系电话'
			   },
		      {
			       field: 'tags',
			       width:'100px',
			       title: '特点'
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       align:'center',
		       width:'100px',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.supplierId);
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
function createOptBtn(supplierId)
{
	var html="<a href=\"javascript:void(0);readdetails('"+supplierId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);edit('"+supplierId+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);del('"+supplierId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}

function edit(supplierId)
{
	//window.location.href = "/app/core/projectbuild/supplier/supplieredit?supplierId="+supplierId;
	open("/app/core/projectbuild/supplier/supplieredit?supplierId="+supplierId,"_self");
}

function readdetails(supplierId)
{
	window.open("/app/core/projectbuild/supplier/supplierdetails?supplierId="+supplierId);
	}


function del(supplierId)
{
	if (confirm("确定删除当前供应商吗？")) {
	$.ajax({
		url : "/set/projectbuilsupplierset/delSupplier",
		type : "post",
		dataType : "json",
		data:{supplierId:supplierId},
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
