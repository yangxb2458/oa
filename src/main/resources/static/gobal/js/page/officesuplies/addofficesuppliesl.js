var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/officesuppliesget/getOfficeSuppliesSortTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "sortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onClick : zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "sortId",
			pIdKey : "sortLeave",
			rootPId : "0"
		},
		key : {
			name : "sortName"
		}
	}
};

var setting1 = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/officesuppliesget/getOfficeSuppliesSortTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "sortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	view : {
		dblClickExpand : false,
		selectedMulti : false
	//禁止多选
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "sortId",
			pIdKey : "sortLeave",
			rootPId : "0"
		},
		key : {
			name : "sortName"
		}
	},
	callback : {
		onClick : function(e, treeId, treeNode) {
			$("#form").data("bootstrapValidator").updateStatus("sortId", "NOT_VALIDATED", null );
			var zTree = $.fn.zTree.getZTreeObj("menuTree"), nodes = zTree.getSelectedNodes(), v = "";
			vid = "";
			nodes.sort(function compare(a, b) {
				return a.id - b.id;
			});
			for (var i = 0, l = nodes.length; i < l; i++) {
				v += nodes[i].sortName + ",";
				vid += nodes[i].sortId + ",";
			}
			if (v.length > 0)
				v = v.substring(0, v.length - 1);
				var nameem = $("#sortId");
				nameem.val(v);
				nameem.trigger('change');
			if (vid.length > 0)
				vid = vid.substring(0, vid.length - 1);
				nameem.attr("data-value",vid);
		}
	}
};

$(function() {
	query("");
	getofficesuppliesallunit();
	$.ajax({
		url : "/ret/officesuppliesget/getOfficeSuppliesSortTree",
		type : "post",
		dataType : "json",
		success : function(data) {
			zTree = $.fn.zTree.init($("#tree"), setting, data);// 初始化树节点时，添加同步获取的数据
			var topNode = [ {
				sortName : "TOP分类",
				isParent : "fase",
				sortId : ""
			} ];
			var newTreeNodes = topNode.concat(data);
			$.fn.zTree.init($("#menuTree"), setting1, newTreeNodes);
		}
	});
	$("#cbut").unbind("click").click(function() {
		document.getElementById("form").reset();
		$("#listtable").hide();
		$("#creatediv").show();
		$("#createbut").show();
		$("#updatabut").hide();
		$("#createbut").unbind("click").click(function() {
			addofficesupplies();
		});
	});
	

	$("#sortId").unbind("click").click(function(e) {
		e.stopPropagation();
		$("#menuContent").css({
			"width" : $(this).outerWidth() + "px"
		}).slideDown(200);
	});
	$("body").unbind("click").click(function() {
		$("#menuContent").hide();
	});

	$("#menuContent").unbind("click").click(function(e) {
		e.stopPropagation();
	});
	$("#form").bootstrapValidator('validate');
});
function zTreeOnClick(event, treeId, treeNode) {
	$("#creatediv").hide();
	$("#listtable").show();
	var sortId = treeNode.sortId;
	$("#myTable").bootstrapTable('destroy');
	query(sortId);
}
function deleteofficesupplies(suppliesId) {
	if (confirm("确定删除当前办公用品吗？")) {
		$.ajax({
			url : "/set/officesuppliesset/deleteOfficeSupplies",
			type : "post",
			dataType : "json",
			data : {
				suppliesId:suppliesId
			},
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
					$("#myTable").bootstrapTable('refresh');
				} else {
					console.log(data.msg);
				}
			}
		});
	} else {
		return;
	}
}

function addofficesupplies() {
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/officesuppliesset/insertOfficeSupplies",
		type : "post",
		dataType : "json",
		data : {
			sortNo:$("#sortNo").val(),
			suppliesName:$("#suppliesName").val(),
			suppliesCode:$("#suppliesCode").val(),
			sortId:$("#sortId").attr("data-value"),
			quantity:$("#quantity").val(),
			brand:$("#brand").val(),
			model:$("#model").val(),
			unit:$("#unit").val(),
			ownDept:$("#ownDept").attr("data-value"),
			remark:$("#remark").val()
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
}

function updatesupplies(suppliesId) {
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/officesuppliesset/updateOfficeSupplies",
		type : "post",
		dataType : "json",
		data : {
			suppliesId : suppliesId,
			sortNo:$("#sortNo").val(),
			suppliesName:$("#suppliesName").val(),
			suppliesCode:$("#suppliesCode").val(),
			sortId:$("#sortId").attr("data-value"),
			quantity:$("#quantity").val(),
			brand:$("#brand").val(),
			model:$("#model").val(),
			unit:$("#unit").val(),
			ownDept:$("#ownDept").attr("data-value"),
			remark:$("#remark").val()
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
}

function query(sortId)
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/officesuppliesget/getOfficeSupplieslistBySortId?sortId='+sortId,
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
	      idField: 'suppliesId',//key值栏位
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
	       field: 'suppliesName',
	       title: '办公用品名称',
	       sortable : true,
	       width:'200px'
	      },
	      {
			field: 'sortName',
			   width:'50px',
			   title: '材料分类'
			},
			{
		       field: 'brand',
		       title: '品牌',
		       width:'50px'
	      },
	      {
		       field: 'model',
		       width:'100px',
		       title: '规格型号'
		   },
		   {
		       field: 'unit',
		       width:'100px',
		       title: '计量单位',
		       formatter:function(value,row,index){
	                return getofficesuppliesunitbyid(value);
	            }
		       
		   },
		   {
		       field: 'quantity',
		       width:'100px',
		       title: '当前数量'
		   },
	      {
	       field: 'opt',
	       title: '操作',
	       align:'center',
	       width:'200px',
    	   formatter:function(value,row,index){
                return createOptBtn(row.suppliesId);
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
        sortOrder:params.order
    };
    return temp;
};
function createOptBtn(suppliesId)
{
	var html="<a href=\"javascript:void(0);readofficesupplies('"+suppliesId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);editofficesupplies('"+suppliesId+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);deleteofficesupplies('"+suppliesId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}

function readofficesupplies(suppliesId)
{
	window.open("/app/core/projectbuild/material/materialdetails?suppliesId="+suppliesId);
}

function editofficesupplies(suppliesId)
{
	$("#listtable").hide();
	$("#creatediv").show();
	$("#createbut").hide();
	$("#updatabut").show();
	document.getElementById("form").reset();
	$.ajax({
		url : "/ret/officesuppliesget/getofficeSuppliesById",
		type : "post",
		dataType : "json",
		data : {
			suppliesId:suppliesId
		},
		success : function(data) {
			if (data.status == 200) {
				for(name in data.list)
					{
						if(name=="sortId")
						{
							$("#"+name).attr("data-value",data.list[name]);
							$("#"+name).val(getOfficeSupplesSortNameById(data.list[name]));
						}else if(name=="ownDept")
						{
							$("#"+name).attr("data-value",data.list[name]);
							$("#"+name).val(getDeptNameByDeptIds(data.list[name]));
						}else
						{
							$("#"+name).val(data.list[name]);
						}
					}
			} else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
				{
				console.log(data.msg);
			}
		}
	});
	$("#updatabut").unbind("click").click(function() {
		updatesupplies(suppliesId);
	});
}

function getofficesuppliesallunit() {
	$.ajax({
		url : "/ret/officesuppliesget/getAllUnit",
		type : "post",
		dataType : "json",
		data:{type:'material'},
		success : function(data) {
			if (data.status == 200) {
				var html="<option value=''>请选择</option>";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<option value="+data.list[i].unitId+">"+data.list[i].cnName+"|"+data.list[i].enName+"</option>";
				}
				$("#unit").html(html);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function getofficesuppliesunitbyid(untiId) {
	var returnStr="";
	$.ajax({
		url : "/ret/officesuppliesget/getofficeSuppliesUnitById",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			unitId:untiId
			
		},
		success : function(data) {
			if (data.status == 200) {
				if(data.list.znName!=null && data.list.znName!="")
					{
						returnStr = data.list.cnName+"|"+data.list.znName;
					}else
					{
						returnStr = data.list.cnName;
					}
			} else {
				console.log(data.msg);
			}
		}
	});
	return returnStr;
}

function getOfficeSupplesSortNameById(sortId) {
	var returnStr="";
	$.ajax({
		url : "/ret/officesuppliesget/getofficeSuppliesSortById",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			sortId:sortId
			
		},
		success : function(data) {
			if (data.status == 200) {
				returnStr = data.list.sortName;
			} else {
				console.log(data.msg);
			}
		}
	});
	return returnStr;
}