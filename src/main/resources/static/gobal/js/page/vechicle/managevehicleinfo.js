$(function(){
	jeDate("#buyTime", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate()
	});
	jeDate("#yearlyTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate()
	});
	jeDate("#insureTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate()
	});
	jeDate("#beginTimeQuery", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTimeQuery", {
		format: "YYYY-MM-DD"
	});
	jeDate("#beginTime1Query", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime1Query", {
		format: "YYYY-MM-DD"
	});
	$('#remark').summernote({ height:300 });
	getCodeClass("nature","vehicle");
	getCodeClass("natureQuery","vehicle");
	query();
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/vehicleget/getManageVehicleInfoList',
		method : 'post',
		contentType : 'application/x-www-form-urlencoded',
		toolbar : '#toobar',//工具列
		striped : true,//隔行换色
		cache : false,//禁用缓存
		pagination : true,//启动分页
		sidePagination : 'server',//分页方式
		pageNumber : 1,//初始化table时显示的页码
		pageSize : 10,//每页条目
		showFooter : false,//是否显示列脚
		showPaginationSwitch : true,//是否显示 数据条数选择框
		sortable : true,//排序
		search : true,//启用搜索
		sortOrder: "asc",
		showColumns : true,//是否显示 内容列下拉框
		showRefresh : true,//显示刷新按钮
		idField : 'vehicleId',//key值栏位
		clickToSelect : true,//点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],//可选择单页记录数
		queryParams : queryParams,
		columns : [ {
			checkbox : true
		}, {
			field : 'num',
			title : '序号',//标题  可不加
			width : '50px',
			formatter : function(value, row, index) {
				return index + 1;
			}
		}, {
			field : 'vehicleNumber',
			width : '50px',
			title : '车牌号'
		},{
			field : 'onwer',
			title : '拥有者',
			sortable : true,
			width : '80px',
			formatter : function(value, row, index) {
				return getUserNameByStr(value);
			}
		},{
			field : 'onwerPhone',
			width : '80px',
			title : '拥有者电话'
		},{
			field : 'seats',
			width : '50px',
			title : '座位数'
		},{
			field : 'type',
			width : '50px',
			title : '车辆类型',
			formatter : function(value, row, index) {
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
		}, {
			field : 'yearlyTime',
			width : '50px',
			title : '年检日期'
		}, {
			field : 'insureTime',
			width : '50px',
			title : '保险日期'
		}, {
			field : 'status',
			width : '50px',
			title : '当前状态'
		},   {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '120px',
			formatter : function(value, row, index) {
				return createOptBtn(row.vehicleId);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
			//alert(row.SystemDesc);
		},
		responseHandler : function(res) {
			if (res.status == "500") {
				top.layer.msg(res.msg);
			} else {
				return {
					total : res.list.total, //总页数,前面的key必须为"total"
					rows : res.list.list
				//行数据，前面的key要与之前设置的dataField的值一致.
				};
			}
		}
	});
}

function queryParams(params) {
	var temp = {
		search : params.search,
		pageSize : this.pageSize,
		pageNumber : this.pageNumber,
		sort : params.sort,
		sortOrder : params.order,
		onwer : $("#onwerQuery").attr("data-value"),
		endTime : $("#endTimeQuery").val(),
		beginTime : $("#beginTimeQuery").val(),
		endTime1 : $("#endTime1Query").val(),
		beginTime1 : $("#beginTime1Query").val(),
		nature:$("#natureQuery").val(),
		type:$("#typeQuery").val()
	};
	return temp;
};
function createOptBtn(vehicleId) {
	var html = "<a href=\"javascript:void(0);edit('" + vehicleId + "')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;" 
	+ "<a href=\"javascript:void(0);deletevehicle('" + vehicleId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;";
	html += "<a href=\"javascript:void(0);details('" + vehicleId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}
function details(vehicleId)
{
	window.open("/app/core/vehicle/vehicleinfodetails?vehicleId="+vehicleId);
}
function deletevehicle(vehicleId)
{
	$.ajax({
		url : "/set/vehicleset/deleteVehicleInfo",
		type : "post",
		dataType : "json",
		data:{vehicleId:vehicleId},
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

function goback()
{
	$("#vehiclediv").hide();
	$("#vehiclelistdiv").show();
}
function edit(vehicleId)
{
	$("#vehiclelistdiv").hide();
	$("#vehiclediv").show();
	$(".js-back-btn").unbind("click").click(function(){
		goback();
	})
	$.ajax({
		url : "/ret/vehicleget/getVehicleInfoById",
		type : "post",
		dataType : "json",
		data:{vehicleId:vehicleId},
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
					}else if(id=="onwer"||id=="userPriv"||id=="caruser")
					{
						$("#"+id).val(getUserNameByStr(vehicleInfo[id]));
						$("#"+id).attr("data-value",vehicleInfo[id]);
					}else if(id=="manageDept"||id=="deptPriv")
					{
						$("#"+id).val(getDeptNameByDeptIds(vehicleInfo[id]));
						$("#"+id).attr("data-value",vehicleInfo[id]);
					}else if(id=="levelPriv")
					{
						$("#"+id).val(getUserLevelStr(vehicleInfo[id]));
						$("#"+id).attr("data-value",vehicleInfo[id]);
					}else if(id=="remark")
					{
						$("#remark").code(vehicleInfo[id]);
					}else if(id=="photo")
					{
						$("#file_img").attr("src","/sys/file/getStaticImg?module=vechiclephotos&fileName="+vehicleInfo[id]);
						$("#file").attr("data-value",vehicleInfo[id])
					}else
					{
						$("#"+id).val(vehicleInfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateVehicleInfo(vehicleId);
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


function updateVehicleInfo(vehicleId)
{
	$.ajax({
		url : "/set/vehicleset/updateVehicleInfo",
		type : "post",
		dataType : "json",
		data:{
			vehicleId:vehicleId,
			sortNo:$("#sortNo").val(),
			vehicleNumber:$("#vehicleNumber").val(),
			brand:$("#brand").val(),
			model:$("#model").val(),
			displacement:$("#displacement").val(),
			engineNo:$("#engineNo").val(),
			color:$("#color").val(),
			seats:$("#seats").val(),
			frameNo:$("#frameNo").val(),
			certification:$("#certification").val(),
			nature:$("#nature").val(),
			type:$("#type").val(),
			manageDept:$("#manageDept").attr("data-value"),
			managePhone:$("#managePhone").val(),
			onwer:$("#onwer").attr("data-value"),
			onwerPhone:$("#onwerPhone").attr("data-value"),
			caruser:$("#caruser").attr("data-value"),
			caruserPhone:$("#caruserPhone").val(),
			price:$("#price").val(),
			tax:$("#tax").val(),
			buyTime:$("#buyTime").val(),
			mileage:$("#mileage").val(),
			yearlyTime:$("#yearlyTime").val(),
			insureTime:$("#insureTime").val(),
			userPriv:$("#userPriv").attr("data-value"),
			deptPriv:$("#deptPriv").attr("data-value"),
			levelPriv:$("#levelPriv").attr("data-value"),
			remark:$("#remark").code(),
			photo:$("#file").attr("data-value"),
			attach:$("#vechicleattach").attr("data_value")
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

function delPhotos()
{
	$("#file_img").attr("src","/gobal/img/other/car.jpg");
	$("#file").attr("data-value","");
}
