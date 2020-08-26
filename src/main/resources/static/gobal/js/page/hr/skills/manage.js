$(function() {
	QuerySelect("skillsLevel");
	jeDate("#beginTimeQuery", {
		format : "YYYY-MM-DD"
	});
	jeDate("#endTimeQuery", {
		format : "YYYY-MM-DD"
	});
	query();
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
	jeDate("#beginTime", {
		format: "YYYY-MM",
		minDate:getSysDate(),
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	$('#remark').summernote({ height:300 });
	$(".js-auto-select").each(function(){
		var module = $(this).attr("module");
		createAutoSelect(module);
	})

})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/hrget/getHrWorkSkillsList',
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
		}, {
			field : 'name',
			width : '100px',
			title : '技能名称'
		},{
			field : 'userId',
			title : '拥有者',
			sortable : true,
			width : '80px',
			formatter : function(value, row, index) {
				if (row.userName == "" || row.userName == null) {
					return getHrUserNameByStr(row.userId);
				} else {
					return row.userName;
				}
			}
		},{
			field : 'skillsLevel',
			width : '80px',
			title : '技能等级',
			formatter : function(value, row, index) {
				return getHrClassCodeName('skillsLevel', value);
			}
		},{
			field : 'skillsCerificate',
			width : '50px',
			title : '是否有证书',
			formatter : function(value, row, index) {
					if(value=="0")
					{
						return "无";
					}else if(value=="1")
					{
						return "有";
					}else 
					{
						return "其它";
					}
				}
		},{
			field : 'notifieBody',
			width : '100px',
			title : '发证机构'
		}, {
			field : 'beginTime',
			width : '50px',
			title : '有效日期起'
		}, {
			field : 'endTime',
			width : '50px',
			title : '有效日期止'
		},  {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '120px',
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

function queryParams(params) {
	var temp = {
		search : params.search,
		pageSize : this.pageSize,
		pageNumber : this.pageNumber,
		sort : params.sort,
		sortOrder : params.order,
		userId : $("#userIdQuery").attr("data-value"),
		endTime : $("#endTimeQuery").val(),
		beginTime : $("#beginTimeQuery").val(),
		skillsLevel:$("#skillsLevelQuery").val()
	};
	return temp;
};
function createOptBtn(recordId) {
	var html = "<a href=\"javascript:void(0);edit('" + recordId + "')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;" 
	+ "<a href=\"javascript:void(0);deleteReocrd('" + recordId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;";
	html += "<a href=\"javascript:void(0);details('" + recordId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}

function deleteReocrd(recordId)
{
	if(confirm("确定删除当前记录吗？"))
    {
	$.ajax({
		url : "/set/hrset/deleteHrWorkSkills",
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
}
function edit(recordId)
{
	$("#skillslistdiv").hide();
	$("#skillsdiv").show();
	$(".js-back-btn").unbind("click").click(function(){
		goback();
	})
	$.ajax({
		url : "/ret/hrget/getHrWorkSkillsById",
		type : "post",
		dataType : "json",
		data:{recordId:recordId},
		success : function(data) {
			if(data.status=="200")
			{
				var skillsInfo = data.list;
				for(var id in skillsInfo)
				{
					if(id=="attach")
					{
						$("#show_hrattach").html("");
						$("#hrattach").attr("data_value", skillsInfo.attach);
						createAttach("hrattach", 4);
					}else if(id=="userId")
					{
						$("#"+id).val(getHrUserNameByStr(skillsInfo[id]));
						$("#"+id).attr("data-value",skillsInfo[id]);
					}else if(id=="remark")
					{
						$("#remark").code(skillsInfo[id]);
					}else if(id=="skillsCerificate")
					{
						$("input:radio[name='skillsCerificate'][value='"+skillsInfo[id]+"']").attr("checked","checked");
					}else
					{
						$("#"+id).val(skillsInfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateHrWorkSkills(recordId);
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
function details(recordId)
{
	window.open("/app/core/hr/skillsdetails?recordId="+recordId);
}
function updateHrWorkSkills(recordId)
{
	$.ajax({
		url : "/set/hrset/updateHrWorkSkills",
		type : "post",
		dataType : "json",
		data:{
			recordId:recordId,
			sortNo:$("#sortNo").val(),
			name:$("#name").val(),
			userId:$("#userId").attr("data-value"),
			skillsLevel:$("#skillsLevel").val(),
			skillsCerificate:$("input:radio[name='skillsCerificate']:checked").val(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val(),
			notifieBody:$("#notifieBody").val(),
			attach:$("#hrattach").attr("data_value"),
			remark:$("#remark").code()
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
	})
}
function goback()
{
	$("#skillsdiv").hide();
	$("#skillslistdiv").show();
}