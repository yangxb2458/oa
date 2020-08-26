$(function(){
	query();
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/vehicleget/getVehicleApplyList',
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
		      idField: 'applayId',//key值栏位
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
		       field: 'type',
		       title: '车辆类型',
		       width:'50px',
		       formatter:function(value,row,index){
		    	   if(value=="1")
		    		{
		    		   return "轿车";
		    		}else if(value=="2")
		    		{
		    			return "SUV";   
		    		}else if(value=="3")
		    		{
		    			return "MPV";
		    		}else if(value=="4")
		    		{
		    			return "面包车"; 
		    		}else if(value=="5")
		    		{
		    			return "皮卡"; 
		    		}else if(value=="6")
		    		{
		    			return "轻卡";
		    		}else if(value=="7")
		    		{
		    			return "重卡";  
		    		}
			   }
		      },
		      {
				field: 'usedUser',
				   width:'50px',
				   title: '用车人',
				   formatter:function(value,row,index){
					   return getUserNameByStr(value);
				   }
				},
				{
			       field: 'beginTime',
			       title: '用车时间',
			       width:'50px'
		      },{
			       field: 'endTime',
			       title: '返程时间',
			       width:'50px',
			       visible: false
		      },
		      {
			       field: 'mileage',
			       title: '里程',
			       width:'50px'
		      },{
			       field: 'sourceAddress',
			       title: '出发地点',
			       width:'150px'
		      },{
			       field: 'destination',
			       title: '目的地',
			       width:'150px'
		      },
		      {
			       field: 'createUser',
			       width:'50px',
			       title: '申请人',
			       formatter:function(value,row,index){
					   return getUserNameByStr(value);
				   }
			   },
			   {
			       field: 'createTime',
			       width:'100px',
			       title: '申请日期'
			   },
			   {
			       field: 'status',
			       width:'50px',
			       title: '审批状态',
			       formatter:function(value,row,index){
					if(value=="0")
					{
						  return "审批中";
					}else if(value=="1")
					{
						return "审批通过";
					}else if(value=="2")
					{
						return "审批未通过";
					}
				   }
			   },
		      {
		       field: 'opt',
		       width:'100px',
		       align:'center',
		       title: '操作',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.applyId);
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
        status:$("#status").val(),
        createUser:$("#createUser").attr("data-value"),
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val()
    };
    return temp;
};
function createOptBtn(applyId)
{
	var html="<a href=\"javascript:void(0);details('"+applyId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}

function details(applyId)
{
	window.open("/app/core/vehicle/applyvehicledetails?applyId="+applyId);
}

