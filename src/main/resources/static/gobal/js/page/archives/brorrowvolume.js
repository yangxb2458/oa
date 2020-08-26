$(function(){
	$(".js-file-volume").unbind("click").click(function(){
		window.location.href='/app/core/archives/borrow?view=volume';
	})
	$(".js-file-borrow").unbind("click").click(function(){
		 window.location.href='/app/core/archives/borrow?view=create';
	})
	query();
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
	jeDate("#beginTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate(),
		isinitVal: true
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate(),
		isinitVal: true
	});
})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/archivesget/getArchivesVolumeList',
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
		search : false,//启用搜索
		sortOrder: "asc",
		showColumns : true,//是否显示 内容列下拉框
		showRefresh : true,//显示刷新按钮
		idField : 'volumeId',//key值栏位
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
			title : '案卷名称',
			width : '150px'
		}, {
			field : 'volumeCode',
			width : '100px',
			title : '案卷号 '
		},{
			field : 'repositoryTitle',
			width : '100px',
			title : '所属卷库'
		}, {
			field : 'manageUser',
			width : '50px',
			title : '管理员',
			formatter : function(value, row, index) {
				return getUserNameByStr(value);
			}
		}, {
			field : 'secretLevel',
			width : '100px',
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
			field : 'pageTotal',
			title : '总页数',
			width : '100px'
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '120px',
			formatter : function(value, row, index) {
				return createOptBtn(row.volumeId);
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
		repositoryId : $("#repositoryIdQuery").val()
	};
	return temp;
};
function createOptBtn(volumeId) {
	var html = "<a href=\"javascript:void(0);borrow('" + volumeId + "')\" class=\"btn btn-primary btn-xs\">借阅</a>&nbsp;&nbsp;" 
	html += "<a href=\"javascript:void(0);details('" + volumeId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}

function details(volumeId)
{
	window.open("/app/core/archives/volumedetails?volumeId="+volumeId);
}

function borrow(volumeId)
{
	$("#borrowmodal").modal("show");
	$(".js-save").unbind("click").click(function(){
		$.ajax({
			url : "/set/archivesset/insertArchivesBorrowVolume",
			type : "post",
			dataType : "json",
			data:{
				volumeId:volumeId,
				beginTime:$("#beginTime").val(),
				endTime:$("#endTime").val(),
				remark:$("#remark").val()
				},
			success : function(data) {
				if(data.status=="200")
				{
					top.layer.msg(data.msg);
					$("#borrowmodal").modal("hide");
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else
				{
					console.log(data.msg);
				}
			}
		})
		
	})
}
