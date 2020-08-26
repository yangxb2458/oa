$(function(){
	query();
	jeDate("#repairTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate()
	});
	jeDate("#beginTimeQuery", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTimeQuery", {
		format: "YYYY-MM-DD"
	});
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
	getVehicleSelect();
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/vehicleget/getVehicleRepairRecordList',
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
		      idField: 'recordId',//key值栏位
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
		       field: 'vehicleNumber',
		       title: '车牌号',
		       width:'100px'
		      },
		      {
				field: 'repairType',
				   width:'50px',
				   title: '维修类型',
					   formatter:function(value,row,index){
				    	  if(value=="1")
				    		 {
				    		  return "洗车";
				    		 }else if(value=="2")
				    		{
				    			 return "保养"; 
				    		}else if(value=="3")
				    		{
				    			 return "维修"; 
				    		}else if(value=="4")
				    		{
				    			 return "年检"; 
				    		}else if(value=="5")
				    		{
				    			 return "其它";
				    		}
				       }
				},
				{
			       field: 'repairUser',
			       title: '经办人',
			       width:'100px',
			       formatter:function(value,row,index){
			    	   return getUserNameByStr(value);
			       }
		      },{
			       field: 'repairSupplier',
			       title: '维修方',
			       width:'100px',
			       visible: false
		      },
		      {
			       field: 'repairPay',
			       title: '维修金额',
			       width:'50px'
		      },
			   {
			       field: 'createTime',
			       width:'100px',
			       title: '创建日期'
			   },
		      {
		       field: 'opt',
		       width:'100px',
		       align:'center',
		       title: '操作',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.recordId);
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
        repairType:$("#repairTypeQuery").val(),
        repairUser:$("#repairUserQuery").attr("data-value"),
        beginTime:$("#beginTimeQuery").val(),
        endTime:$("#endTimeQuery").val()
    };
    return temp;
};
function createOptBtn(recordId)
{
	var html = "<a href=\"javascript:void(0);edit('" + recordId + "')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;" 
	+ "<a href=\"javascript:void(0);delete('" + recordId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;";
	html += "<a href=\"javascript:void(0);details('" + recordId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}

function deletevehicle(recordId)
{
	$.ajax({
		url : "/set/vehicleset/deleteVehicleRepairRecord",
		type : "post",
		dataType : "json",
		data:{recordId:recordId},
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
function details(recordId)
{
	window.open("/app/core/vehicle/vehicleRepairdetails?recordId="+recordId);
}

function goback()
{
	$("#vehiclerepairdiv").hide();
	$("#vehiclerepairlistdiv").show();
}

function edit(recordId)
{
	$("#vehiclerepairlistdiv").hide();
	$("#vehiclerepairdiv").show();
	$(".js-back-btn").unbind("click").click(function(){
		goback();
	})
	$.ajax({
		url : "/ret/vehicleget/getVehicleRepairRecordById",
		type : "post",
		dataType : "json",
		data:{recordId:recordId},
		success : function(data) {
			if(data.status=="200")
			{
				var vehicleInfo = data.list;
				for(var id in vehicleInfo)
				{
					if(id=="attach")
					{
						$("#show_vechicleattach").html("");
						$("#vechicleattach").attr("data_value", vehicleInfo.attach);
						createAttach("vechicleattach", 4);
					}else if(id=="repairUser")
					{
						$("#"+id).val(getUserNameByStr(vehicleInfo[id]));
						$("#"+id).attr("data-value",vehicleInfo[id]);
					}else
					{
						$("#"+id).val(vehicleInfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateVehicleRepairRecord(recordId);
				})
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
		})
}
function updateVehicleRepairRecord(recordId)
{
	$.ajax({
		url : "/set/vehicleset/updateVehicleRepairRecord",
		type : "post",
		dataType : "json",
		data:{
			recordId:recordId,
			vehicleId:$("#vehicleId").val(),
			repairType:$("#repairType").val(),
			repairTime:$("#repairTime").val(),
			repairUser:$("#repairUser").attr("data-value"),
			reason:$("#reason").val(),
			repairSupplier:$("#repairSupplier").val(),
			repairPay:$("#repairPay").val(),
			attach:$("#vechicleattach").attr("data_value"),
			remark:$("#remark").val()
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				location.reload();
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
	});
}

function getVehicleSelect()
{
	$.ajax({
		url : "/ret/vehicleget/getAllVehicleList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
			var html="";
			for(var i=0;i<data.list.length;i++)
			{
				html+="<option value='"+data.list[i].vehicleId+"'>"+data.list[i].vehicleNumber+"</option>"
			}
				$("#vehicleId").html(html);
		}
		}
	})
}