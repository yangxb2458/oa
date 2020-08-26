$(function(){
	getCodeClass("fileType","volume_file_type");
	getCodeClass("fileTypeQuery","volume_file_type");
	jeDate("#sendTime", {
		format: "YYYY-MM-DD",
		isinitVal: true
	});
	getVolumeList();
	query();
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
	 $(".js-back-btn").unbind("click").click(function(){
			goback();
	})
})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/archivesget/getArchivesFileList',
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
			field : 'fileType',
			width : '50px',
			title : '文件类型',
			formatter : function(value, row, index) {
				 return getCodeClassName(value,"volume_file_type");
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
			width : '50px'
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '120px',
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
		volumeId : $("#volumeIdQuery").val(),
		fileType : $("#fileTypeQuery").val(),
		secretLevel:$("#secretLevelQuery").val(),
	};
	return temp;
};
function createOptBtn(fileId) {
	var html = "<a href=\"javascript:void(0);edit('" + fileId + "')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;" 
	+ "<a href=\"javascript:void(0);deleteFile('" + fileId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;";
	html += "<a href=\"javascript:void(0);details('" + fileId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0); destroyFile('" + fileId + "');\" class=\"btn btn-danger btn-xs\">销毁</a>";
	return html;
}
function destroyFile(fileId)
{
	$("#destorymodal").modal("show");
	$(".js-save").unbind("click").click(function(){
		if(confirm("确定销毁当前文件吗？"))
	    {
		$.ajax({
			url : "/set/archivesset/destroyArchives",
			type : "post",
			dataType : "json",
			data:{archivesId:fileId,remark:$("#destroyremark").val(),archivesType:'1'},
			success : function(data) {
				if(data.status=="200")
				{
					top.layer.msg(data.msg);
					$("#destorymodal").modal("hide");
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
	})
}
function details(fileId)
{
	window.open("/app/core/archives/filedetails?fileId="+fileId);
}
function deleteFile(fileId)
{
	if(confirm("确定删除当前文件吗？"))
    {
	$.ajax({
		url : "/set/archivesset/deleteArchivesFile",
		type : "post",
		dataType : "json",
		data:{fileId:fileId},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				location.reload();
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
}
function edit(fileId)
{
	$("#filelistdiv").hide();
	$("#filediv").show();
	$.ajax({
		url : "/ret/archivesget/getArchivesFileById",
		type : "post",
		dataType : "json",
		data:{
			fileId:fileId,
		},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="attach")
					{
						$("#archivesattach").attr("data_value", recordInfo[id]);
						createAttach("archivesattach",4);
					}else if(id=="attachPriv")
					{
						$("input:radio[name='attachPriv'][value='"+recordInfo[id]+"']").attr("checked","checked");
					}else if(id=="isaudit")
					{
						$("input:radio[name='isaudit'][value='"+recordInfo[id]+"']").attr("checked","checked");
					}else
					{
						$("#"+id).val(recordInfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateFile(fileId);
				})
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

function updateFile(fileId)
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/archivesset/updateArchivesFile",
		type : "post",
		dataType : "json",
		data:{
			fileId:fileId,
			sortNo:$("#sortNo").val(),
			fileCode:$("#fileCode").val(),
			title:$("#title").val(),
			sendOrg:$("#sendOrg").val(),
			subject:$("#subject").val(),
			subheading:$("#subheading").val(),
			fileType:$("#fileType").val(),
			secretLevel:$("#secretLevel").val(),
			sendTime:$("#sendTime").val(),
			volumeId:$("#volumeId").val(),
			pageTotal:$("#pageTotal").val(),
			printTotal:$("#printTotal").val(),
			attach:$("#archivesattach").attr("data_value"),
			isaudit:$("input:radio[name='isaudit']:checked").val(),
			attachPriv:$("input:radio[name='attachPriv']:checked").val(),
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
			}else
				{
				console.log(data.msg);
				}
		}
	});
	}
}

function getVolumeList()
{
	$.ajax({
		url : "/ret/archivesget/getArchivesVolumeListForSelect",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var recordList = data.list;
				$("#volumeIdQuery").append("<option value=\"\">全部</option>");
				for(var i=0;i<recordList.length;i++)
				{
					$("#volumeId").append("<option value=\""+recordList[i].volumeId+"\">"+recordList[i].volumeTitle+"</option>");
					$("#volumeIdQuery").append("<option value=\""+recordList[i].volumeId+"\">"+recordList[i].volumeTitle+"</option>")
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
function goback()
{
	$("#filediv").hide();
	$("#filelistdiv").show();
}