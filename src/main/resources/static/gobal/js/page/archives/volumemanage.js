$(function(){
	getCodeClass("voucherType","volume_voucher_type");
	jeDate("#endDate", {
		format: "YYYY-MM-DD",
		isinitVal: true
	});
	jeDate("#beginDate", {
		format: "YYYY-MM-DD",
		isinitVal: true
	});
	getRepositoryList();
	query();
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
	 $(".js-back-btn").unbind("click").click(function(){
			goback();
	})
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
	var html = "<a href=\"javascript:void(0);edit('" + volumeId + "')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;" 
	+ "<a href=\"javascript:void(0);deleteVolume('" + volumeId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;";
	html += "<a href=\"javascript:void(0);details('" + volumeId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0); destroyVolume('" + volumeId + "');\" class=\"btn btn-danger btn-xs\">销毁</a>";
	return html;
}

function destroyVolume(volumeId)
{
	$("#destorymodal").modal("show");
	$(".js-save").unbind("click").click(function(){
		if(confirm("确定销毁当前案卷吗？"))
	    {
		$.ajax({
			url : "/set/archivesset/destroyArchives",
			type : "post",
			dataType : "json",
			data:{archivesId:volumeId,remark:$("#destroyremark").val(),archivesType:'2'},
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

function details(volumeId)
{
	window.open("/app/core/archives/volumedetails?volumeId="+volumeId);
}
function deleteVolume(volumeId)
{
	if(confirm("确定删除当前案卷吗？"))
    {
	$.ajax({
		url : "/set/archivesset/deleteArchivesVolume",
		type : "post",
		dataType : "json",
		data:{volumeId:volumeId},
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
function edit(volumeId)
{
	$("#volumelistdiv").hide();
	$("#volumediv").show();
	$.ajax({
		url : "/ret/archivesget/getArchivesVolumeById",
		type : "post",
		dataType : "json",
		data:{
			volumeId:volumeId,
		},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="deptId")
					{
						$("#"+id).attr("data-value",(recordInfo[id]));
						$("#"+id).val(getDeptNameByDeptIds(recordInfo[id]));
					}else if(id=="manageUser")
					{
						$("#"+id).attr("data-value",(recordInfo[id]));
						$("#"+id).val(getUserNameByStr(recordInfo[id]));
					}else
					{
						$("#"+id).val(recordInfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateVolume(volumeId);
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

function updateVolume(volumeId)
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/archivesset/updateArchivesVolume",
		type : "post",
		dataType : "json",
		data:{
			volumeId:volumeId,
			sortNo:$("#sortNo").val(),
			volumeCode:$("#volumeCode").val(),
			volumeTitle:$("#volumeTitle").val(),
			repositoryId:$("#repositoryId").val(),
			beginDate:$("#beginDate").val(),
			endDate:$("#endDate").val(),
			createOrg:$("#createOrg").val(),
			deptId:$("#deptId").attr("data-value"),
			storagePeriod:$("#storagePeriod").val(),
			secretLevel:$("#secretLevel").val(),
			microNo:$("#microNo").val(),
			voucherType:$("#voucherType").val(),
			voucherBeginNo:$("#voucherBeginNo").val(),
			voucherEndNo:$("#voucherEndNo").val(),
			pageTotal:$("#pageTotal").val(),
			manageUser:$("#manageUser").attr("data-value"),
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
				$("#repositoryIdQuery").append("<option value=\"\">全部</option>");
				for(var i=0;i<recordList.length;i++)
				{
					$("#repositoryId").append("<option value=\""+recordList[i].repositoryId+"\">"+recordList[i].title+"</option>");
					$("#repositoryIdQuery").append("<option value=\""+recordList[i].repositoryId+"\">"+recordList[i].title+"</option>")
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
	$("#volumediv").hide();
	$("#volumelistdiv").show();
}