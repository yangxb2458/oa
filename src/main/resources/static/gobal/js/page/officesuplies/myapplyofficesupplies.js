$(function() {
	query();
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	getCodeClass("noticeType","notice");
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
});
function query()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/officesuppliesget/getMyApplyOfficeSuppliesList',
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
	      idField: 'applyId',//key值栏位
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
		       title: '申请标题',
		       sortable : true,
		       width:'150px'
		      },
	      {
	       field: 'suppliesName',
	       title: '办公用品名称',
	       sortable : true,
	       width:'100px'
	      },
			{
		       field: 'brand',
		       title: '品牌',
		       width:'50px'
	      },
	      {
		       field: 'model',
		       width:'100px',
		       title: '规格型号'
		   },
		   {
		       field: 'unit',
		       width:'50px',
		       title: '计量单位',
		       formatter:function(value,row,index){
	                return getofficesuppliesunitbyid(value);
	            }
		       
		   },
		   {
		       field: 'status',
		       width:'50px',
		       title: '审批状态'
		   },
		   {
		       field: 'quantity',
		       width:'100px',
		       title: '可用数量'
		   },
		   {
		       field: 'remark',
		       width:'200px',
		       title: '备注'
		   },
	      {
	       field: 'opt',
	       title: '操作',
	       align:'center',
	       width:'50px',
    	   formatter:function(value,row,index){
                return createOptBtn(row.suppliesId);
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
        status:$("#status").val()
    };
    return temp;
};
function createOptBtn(suppliesId)
{
	var html="<a href=\"javascript:void(0);readofficesupplies('"+suppliesId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}

function getofficesuppliesunitbyid(untiId) {
	var returnStr="";
	$.ajax({
		url : "/ret/officesuppliesget/getofficeSuppliesUnitById",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			unitId:untiId
			
		},
		success : function(data) {
			if (data.status == 200) {
				if(data.list.znName!=null && data.list.znName!="")
					{
						returnStr = data.list.cnName+"|"+data.list.znName;
					}else
					{
						returnStr = data.list.cnName;
					}
			} else {
				console.log(data.msg);
			}
		}
	});
	return returnStr;
}
