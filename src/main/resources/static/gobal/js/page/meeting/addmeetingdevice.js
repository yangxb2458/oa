$(function() {
	query();
	$(".js-btn").unbind("click").click(function() {
		document.getElementById("form").reset();
		$("#manager").attr("data-value","");
		$("#deptPriv").attr("data-value","");
		$("#adddevicemodal").modal("show");
		$(".js-save").unbind("click").click(function() {
			addDevice();
		});
	});
});

function addDevice() {
	$.ajax({
		url : "/set/meetingset/insertMeetingDevice",
		type : "post",
		dataType : "json",
		data : {
			sortNo : $("#sortNo").val(),
			deviceCode:$("#deviceCode").val(),
			deviceName : $("#deviceName").val(),
			brand : $("#brand").val(),
			model : $("#model").val(),
			deptPriv:$("#deptPriv").attr("data-value"),
			manager : $("#manager").attr("data-value"),
			remark : $("#remark").val()
		},
		success : function(data) {
			if (data.status == 200) {
				top.layer.msg(data.msg);
				location.reload();
			} else {
				console.log(data.msg);
			}
		}
	});
}


function updateDevice(deviceId) {
	$.ajax({
		url : "/set/meetingset/updateMeetingDevice",
		type : "post",
		dataType : "json",
		data : {
			deviceId:deviceId,
			deviceCode:$("#deviceCode").val(),
			sortNo : $("#sortNo").val(),
			deviceName : $("#deviceName").val(),
			brand : $("#brand").val(),
			model : $("#model").val(),
			deptPriv:$("#deptPriv").attr("data-value"),
			manager : $("#manager").attr("data-value"),
			remark : $("#remark").val()
		},
		success : function(data) {
			if (data.status == 200) {
				top.layer.msg(data.msg);
				location.reload();
			} else {
				console.log(data.msg);
			}
		}
	});
}

function delDevice(deviceId) {
	if (confirm("确定删除当前设备吗？")) {
		$.ajax({
			url : "/set/meetingset/deleteMeetingDevice",
			type : "post",
			dataType : "json",
			data : {
				deviceId : deviceId
			},
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
					$("#addDevicemodal").modal("hide");
					$("#myTable").bootstrapTable("refresh");
				} else {
					console.log(data.msg);
				}
			}
		});
	} else {
		return;
	}
}

function edit(deviceId)
{
	$("#adddevicemodal").modal("show");
	document.getElementById("form").reset();
	$("#manager").attr("data-value","");
	$("#deptPriv").attr("data-value","");
	$.ajax({
		url : "/ret/meetingget/getMeetingDeviceById",
		type : "post",
		dataType : "json",
		data : {
			deviceId : deviceId
		},
		success : function(data) {
			if (data.status == 200) {
				for(var name in data.list)
					{
						if(name=="manager")
						{
							$("#"+name).attr("data-value",data.list[name]);
							$("#"+name).val(getUserNameByStr(data.list[name]));
						}else if(name=="deptPriv")
						{
							$("#"+name).attr("data-value",data.list[name]);
							$("#"+name).val(getDeptNameByDeptIds(data.list[name]));
						}else
						{
							$("#"+name).val(data.list[name])
						}
					}
			} else {
				console.log(data.msg);
			}
		}
	});
	$(".js-save").unbind("click").click(function() {
		updateDevice(deviceId);
	});
}

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/meetingget/getMeetingDeviceList',
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
		showColumns : true,//是否显示 内容列下拉框
		showRefresh : true,//显示刷新按钮
		idField : 'deviceId',//key值栏位
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
			field : 'deviceCode',
			title : '设备编号',
			width : '50px'
		},{
			field : 'deviceName',
			title : '设备名称',
			sortable : true,
			width : '150px'
		}, {
			field : 'brand',
			width : '50px',
			title : '品牌'
		}, {
			field : 'model',
			title : '型号',
			width : '100px'
		}, {
			field : 'managerName',
			width : '100px',
			title : '管理员'
		}, {
			field : 'deptPriv',
			width : '100px',
			title : '可使用部门',
			formatter : function(value, row, index) {
					return getDeptNameByDeptIds(value);
			}
		},{
			field : 'remark',
			width : '200px',
			title : '备注'
		}, {
			field : 'opt',
			title : '操作',
			width : '100px',
			align:'center',
			formatter : function(value, row, index) {
				return createOptBtn(row.deviceId);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
			//alert(row.SystemDesc);
		},
		responseHandler : function(res) {
			if (res.status == "500") {
				console.log(res.msg);
			} else if (res.status == "100") {
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
		sortOrder : params.order
	};
	return temp;
};
function createOptBtn(deviceId) {
	var html = "<a href=\"javascript:void(0);edit('" + deviceId + "')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" + "<a href=\"javascript:void(0);delDevice('" + deviceId
			+ "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}
