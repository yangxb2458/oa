var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/projectbuildmaterialget/getMaterialSortTree",// Ajax 获取数据的 URL 地址
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
		url : "/ret/projectbuildmaterialget/getMaterialSortTree",// Ajax 获取数据的 URL 地址
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
	getprojectbuildunit();
	$.ajax({
		url : "/ret/projectbuildmaterialget/getMaterialSortTree",
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
			addmaterial();
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
function delmaterial(materialId) {
	if (confirm("确定删除当前材料吗？")) {
		$.ajax({
			url : "/set/projectbuildmaterialset/delMaterial",
			type : "post",
			dataType : "json",
			data : {
				materialId:materialId
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
	} else {
		return;
	}
}

function addmaterial() {
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/projectbuildmaterialset/insertMaterial",
		type : "post",
		dataType : "json",
		data : {
			sortNo:$("#sortNo").val(),
			name:$("#name").val(),
			materialCode:$("#materialCode").val(),
			sortId:$("#sortId").attr("data-value"),
			brand:$("#brand").val(),
			model:$("#model").val(),
			unit:$("#unit").val(),
			price:$("#price").val(),
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

function updatematerial() {
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/projectbuildmaterialset/updateMaterial",
		type : "post",
		dataType : "json",
		data : {
			materialId : $("#materialId").val(),
			sortNo : $("#sortNo").val(),
			name : $("#name").val(),
			materialCode : $("#materialCode").val(),
			sortId : $("#sortId").attr("data-value"),
			brand: $("#brand").val(),
			model:$("#model").val(),
			unit:$("#unit").val(),
			price:$("#price").val(),
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
	      url: '/ret/projectbuildmaterialget/getmateriallist?sortId='+sortId,
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
	      idField: 'materialId',//key值栏位
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
	       field: 'name',
	       title: '材料名称',
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
	                return getprojectbuildunitbyid(value);
	            }
		       
		   },
		   {
		       field: 'price',
		       width:'100px',
		       title: '价格',
		       formatter:function(value,row,index){
	                return value+"/元(RMB)";
	            }
		   },
	      {
	       field: 'opt',
	       title: '操作',
	       align:'center',
	       width:'200px',
    	   formatter:function(value,row,index){
                return createOptBtn(row.materialId);
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
function createOptBtn(materialId)
{
	var html="<a href=\"javascript:void(0);readmaterial('"+materialId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);editmaterial('"+materialId+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);delmaterial('"+materialId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}

function readmaterial(materialId)
{
	window.open("/app/core/projectbuild/material/materialdetails?materialId="+materialId);
}

function editmaterial(materialId)
{
	$("#listtable").hide();
	$("#creatediv").show();
	$("#createbut").hide();
	$("#updatabut").show();
	document.getElementById("form").reset();
	$.ajax({
		url : "/ret/projectbuildmaterialget/getMaterialById",
		type : "post",
		dataType : "json",
		data : {
			materialId:materialId
		},
		success : function(data) {
			if (data.status == 200) {
				for(name in data.list)
					{
						if(name=="sortId")
						{
							$("#"+name).attr("data-value",data.list[name]);
							$("#"+name).val(getMaterialSortNameById(data.list[name]));
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
		updatematerial();
	});
}

function getprojectbuildunit() {
	$.ajax({
		url : "/ret/projectbuildunitget/getAllUnit",
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

function getprojectbuildunitbyid(untiId) {
	var returnStr="";
	$.ajax({
		url : "/ret/projectbuildunitget/getunitbyid",
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

function getMaterialSortNameById(sortId) {
	var returnStr="";
	$.ajax({
		url : "/ret/projectbuildmaterialget/getMaterialSortById",
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