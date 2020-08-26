$(function(){
	getRepositoryList();
	getCodeClass("fileType","volume_file_type");
	query();
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
	$("#repositoryId").unbind("change").change(function(){
		getVolumeList();
	})
	
})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/archivesget/getArchivesFileQueryList',
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
		idField : 'fileId',//key值栏位
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
			title : '所属案卷',
			width : '50px'
		}, {
			field : 'title',
			width : '150px',
			title : '文件名称 '
		},{
			field : 'subject',
			width : '100px',
			title : '文件主题'
		}, {
			field : 'sendOrg',
			width : '100px',
			title : '发文单位',
		}, {
			field : 'subject',
			width : '100px',
			title : '文件主题'
		},{
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
			width : '50px'
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '50px',
			formatter : function(value, row, index) {
				return createOptBtn(row.fileId);
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
		repositoryId : $("#repositoryId").val(),
		volumeId : $("#volumeId").val(),
		fileType : $("#fileType").val(),
		secretLevel:$("#secretLevel").val(),
	};
	return temp;
};
function createOptBtn(fileId) {
	var html = "<a href=\"javascript:void(0);details('" + fileId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}
function details(fileId)
{
	window.open("/app/core/archives/filedetails?fileId="+fileId);
}

function getRepositoryList()
{
	$.ajax({
		url : "/ret/archivesget/getArchivesRepositoryList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var recordList = data.list;
				$("#repositoryId").append("<option value=\"\">全部</option>");
				for(var i=0;i<recordList.length;i++)
				{
					$("#repositoryId").append("<option value=\""+recordList[i].repositoryId+"\">"+recordList[i].title+"</option>");
				}
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
})
}

function getVolumeList()
{
	$.ajax({
		url : "/ret/archivesget/getArchivesVolumeByRepositoryId",
		type : "post",
		dataType : "json",
		data:{repositoryId:$("#repositoryId").val()},
		success : function(data) {
			if(data.status=="200")
			{
				var recordList = data.list;
				var html="<option value=\"\">全部</option>";
				for(var i=0;i<recordList.length;i++)
				{
					html+="<option value=\""+recordList[i].volumeId+"\">"+recordList[i].volumeTitle+"</option>";
				}
				$("#volumeId").html(html);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
})
}