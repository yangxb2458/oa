$(function() {
	QuerySelect("licenceType");
	jeDate("#beginTimeQuery", {
		format : "YYYY-MM-DD"
	});
	jeDate("#endTimeQuery", {
		format : "YYYY-MM-DD"
	});
	query();
	$(".js-simple-query").unbind("click").click(function() {
		$("#myTable").bootstrapTable("refresh");
	})
	jeDate("#endTime", {
		format : "YYYY-MM-DD",
	});
	jeDate("#beginTime", {
		format : "YYYY-MM-DD"
	});
	getSmsConfig("msgType", "hr");
	$('#remark').summernote({
		height : 300
	});
	$(".js-auto-select").each(function() {
		var module = $(this).attr("module");
		createAutoSelect(module);
	})

})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/hrget/getHrLicenceList',
		method : 'post',
		contentType : 'application/x-www-form-urlencoded',
		toolbar : '#toobar',// 工具列
		striped : true,// 隔行换色
		cache : false,// 禁用缓存
		pagination : true,// 启动分页
		sidePagination : 'server',// 分页方式
		pageNumber : 1,// 初始化table时显示的页码
		pageSize : 10,// 每页条目
		showFooter : false,// 是否显示列脚
		showPaginationSwitch : true,// 是否显示 数据条数选择框
		sortable : true,// 排序
		search : true,// 启用搜索
		sortOrder : "asc",
		showColumns : true,// 是否显示 内容列下拉框
		showRefresh : true,// 显示刷新按钮
		idField : 'licenceId',// key值栏位
		clickToSelect : true,// 点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],// 可选择单页记录数
		queryParams : queryParams,
		columns : [ {
			checkbox : true
		}, {
			field : 'num',
			title : '序号',// 标题 可不加
			width : '50px',
			formatter : function(value, row, index) {
				return index + 1;
			}
		}, {
			field : 'userId',
			title : '持证人员',
			width : '80px',
			formatter : function(value, row, index) {
				if (row.userName == "" || row.userName == null) {
					return getHrUserNameByStr(row.userId);
				} else {
					return row.userName;
				}
			}
		}, {
			field : 'licenceType',
			width : '100px',
			title : '证照类型',
			formatter : function(value, row, index) {
				return getHrClassCodeName('licenceType', value);
			}
		}, {
			field : 'name',
			width : '100px',
			title : '证照名称'
		},{
			field : 'licenceCode',
			width : '100px',
			title : '证照编号'
		}, {
			field : 'beginTime',
			sortable : true,
			width : '50px',
			title : '生效日期'
		}, {
			field : 'endTime',
			sortable : true,
			width : '50px',
			title : '截止日期'
		}, {
			field : 'notifiedBody',
			title : '发证机构',
			width : '100px'
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '120px',
			formatter : function(value, row, index) {
				return createOptBtn(row.licenceId);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
			// alert(row.SystemDesc);
		},
		responseHandler : function(res) {
			if (res.status == "500") {
				top.layer.msg(res.msg);
			} else {
				return {
					total : res.list.total, // 总页数,前面的key必须为"total"
					rows : res.list.list
				// 行数据，前面的key要与之前设置的dataField的值一致.
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
		userId : $("#userIdQuery").attr("data-value"),
		licenceType : $("#licenceTypeQuery").val(),
		endTime : $("#endTimeQuery").val(),
		beginTime : $("#beginTimeQuery").val()
	};
	return temp;
};
function createOptBtn(licenceId) {
	var html = "<a href=\"javascript:void(0);edit('" + licenceId + "')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;" + 
	"<a href=\"javascript:void(0);deleteLicence('" + licenceId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;";
	html += "<a href=\"javascript:void(0);details('" + licenceId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}

function deleteLicence(licenceId) {
	if(confirm("确定删除当前记录吗？"))
    {
	$.ajax({
		url : "/set/hrset/deleteHrLicence",
		type : "post",
		dataType : "json",
		data : {
			licenceId : licenceId
		},
		success : function(data) {
			if (data.status == "200") {
				top.layer.msg(data.msg);
				$("#myTable").bootstrapTable("refresh");
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
    }
}
function edit(licenceId) {
	$("#licencelistdiv").hide();
	$("#licencediv").show();
	$(".js-back-btn").unbind("click").click(function() {
		goback();
	})
	$.ajax({
		url : "/ret/hrget/getHrLicenceById",
		type : "post",
		dataType : "json",
		data : {
			licenceId : licenceId
		},
		success : function(data) {
			if (data.status == "200") {
				var licenceInfo = data.list;
				for ( var id in licenceInfo) {
					if (id == "attach") {
						$("#show_hrattach").html("");
						$("#hrattach").attr("data_value", licenceInfo.attach);
						createAttach("hrattach", 4);
					} else if (id == "userId") {
						$("#" + id).val(getHrUserNameByStr(licenceInfo[id]));
						$("#" + id).attr("data-value", licenceInfo[id]);
					} else if (id == "remark") {
						$("#remark").code(licenceInfo[id]);
					}else if(id=="reminder")
					{
						$("input:radio[name='reminder'][value='"+licenceInfo[id]+"']").attr("checked","checked");
					}else if(id=="sendToUser")
					{
						$("#"+id).attr("data-value",licenceInfo[id]);
						$("#"+id).val(getUserNameByStr(licenceInfo[id]));
					}else {
						$("#" + id).val(licenceInfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function() {
					updateLicence(licenceId);
				})
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else if (data.status == "500") {
				console.log(data.msg);
			}
		}
	})
}
function details(licenceId) {
	window.open("/app/core/hr/licencedetails?licenceId=" + licenceId);
}
function updateLicence(licenceId) {
	$.ajax({
		url : "/set/hrset/updateHrLicence",
		type : "post",
		dataType : "json",
		data : {
			licenceId : licenceId,
			sortNo:$("#sortNo").val(),
			userId:$("#userId").attr("data-value"),
			name:$("#name").val(),
			licenceCode:$("#licenceCode").val(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val(),
			notifiedBody:$("#notifiedBody").val(),
			licenceType:$("#licenceType").val(),
			reminder:$("input:radio[name='reminder']:checked").val(),
			sendToUser:$("#sendToUser").attr("data-value"),
			msgType:getCheckBoxValue("msgType"),
			attach:$("#hrattach").attr("data_value"),
			remark:$("#remark").code()
		},
		success : function(data) {
			if (data.status == "200") {
				top.layer.msg(data.msg);
				location.reload();
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else if (data.status == "500") {
				console.log(data.msg);
			}
		}
	})
}
function goback() {
	$("#licencediv").hide();
	$("#licencelistdiv").show();
}