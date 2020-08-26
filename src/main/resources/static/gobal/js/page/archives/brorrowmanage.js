$(function(){
	query();
	query1();
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
		$("#myTable1").bootstrapTable("refresh");
	})
	$("#type").unbind("change").change(function(){
		if($("#type").val()=="")
		{
			$("#myTable").bootstrapTable("refresh");
			$("#myTable1").bootstrapTable("refresh");
		}else if($("#type").val()=="0")
		{
			$("#myTable").bootstrapTable("refresh");
		}else if($("#type").val()=="1")
		{
			$("#myTable1").bootstrapTable("refresh");
		}
		
	})
})
function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/archivesget/getArchivesBorrowFileList',
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
		showPaginationSwitch : false,//是否显示 数据条数选择框
		sortable : true,//排序
		search : false,//启用搜索
		sortOrder: "asc",
		showColumns : false,//是否显示 内容列下拉框
		showRefresh : false,//显示刷新按钮
		idField : 'recordId',//key值栏位
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
		},{
			field : 'title',
			width : '100px',
			title : '文件名称 '
		},{
			field : 'fileCode',
			width : '50px',
			title : '文件编号 '
		},{
			field : 'subject',
			width : '100px',
			title : '文件主题'
		}, {
			field : 'fileType',
			width : '50px',
			title : '文件类型',
			formatter : function(value, row, index) {
				 return getCodeClassName(value,"volume_file_type");
			}
		}, {
			field : 'secretLevel',
			width : '50px',
			title : '密级',
			formatter : function(value, row, index) {
				if(value=="1")
				{
					return "内部";
				}else if(value=="2")
				{
					return "秘密";
				}else if(value=="3")
				{
					return "机密";
				}else if(value=="4")
				{
					return "绝密";
				}
			}
		}, {
			field : '',
			title : '借阅时间',
			width : '100px',
			formatter : function(value, row, index) {
				return row.beginTime+"至"+row.endTime;
			}
		}, 
		{
			field : 'approvalStatus',
			title : '审批状态',
			width : '50px',
			formatter : function(value, row, index) {
				if(value=="0")
				{
					return "审批中";
				}else if(value=="1")
				{
					return "通过";
				}else if(value=="2")
				{
					return "未通过";
				}
			}
		},{
			field : 'remark',
			title : '备注',
			width : '200px'
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '50px',
			formatter : function(value, row, index) {
				return createOptBtn(row.recordId);
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

function query1() {
	$("#myTable1").bootstrapTable({
		url : '/ret/archivesget/getArchivesBorrowVolumeList',
		method : 'post',
		contentType : 'application/x-www-form-urlencoded',
		toolbar : '#toobar1',//工具列
		striped : true,//隔行换色
		cache : false,//禁用缓存
		pagination : true,//启动分页
		sidePagination : 'server',//分页方式
		pageNumber : 1,//初始化table时显示的页码
		pageSize : 10,//每页条目
		showFooter : false,//是否显示列脚
		showPaginationSwitch : false,//是否显示 数据条数选择框
		sortable : true,//排序
		search : false,//启用搜索
		sortOrder: "asc",
		showColumns : false,//是否显示 内容列下拉框
		showRefresh : false,//显示刷新按钮
		idField : 'recordId',//key值栏位
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
		},{
			field : 'volumeTitle',
			width : '100px',
			title : '案卷名称 '
		},{
			field : 'volumeCode',
			width : '50px',
			title : '案卷号 '
		},{
			field : 'createOrg',
			width : '100px',
			title : '编制机构'
		}, {
			field : 'secretLevel',
			width : '50px',
			title : '密级',
			formatter : function(value, row, index) {
				if(value=="1")
				{
					return "内部";
				}else if(value=="2")
				{
					return "秘密";
				}else if(value=="3")
				{
					return "机密";
				}else if(value=="4")
				{
					return "绝密";
				}
			}
		}, {
			field : '',
			title : '借阅时间',
			width : '100px',
			formatter : function(value, row, index) {
				return row.beginTime+"至"+row.endTime;
			}
		}, 
		{
			field : 'approvalStatus',
			title : '审批状态',
			width : '50px',
			formatter : function(value, row, index) {
				if(value=="0")
				{
					return "审批中";
				}else if(value=="1")
				{
					return "通过";
				}else if(value=="2")
				{
					return "未通过";
				}
			}
		}, {
			field : 'remark',
			title : '备注',
			width : '200px'
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '50px',
			formatter : function(value, row, index) {
				return createOptBtn1(row.recordId,row.volumeId);
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
		approvalStatus : $("#approvalStatus").val()
	};
	return temp;
};
function createOptBtn(recordId) {
	var html = "<a href=\"javascript:void(0);detailsfile('" + recordId + "')\" class=\"btn btn-sky btn-xs\" >查阅</a>";
	return html;
}

function createOptBtn1(recordId,volumeId) {
	var html ="<a href=\"javascript:void(0);detailsVolume('" + volumeId + "')\" class=\"btn btn-sky btn-xs\" >查阅</a>";
	return html;
}

function detailsfile(recordId)
{
	window.open("/app/core/archives/borrowfiledetails?recordId="+recordId);
}

function detailsVolume(volumeId)
{
	window.open("/app/core/archives/volumelistdetails?volumeId="+volumeId);
}