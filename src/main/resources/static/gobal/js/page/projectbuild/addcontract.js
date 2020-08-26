var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/projectbuildget/contract/getProjectBuildContractSortTree",// Ajax 获取数据的 URL 地址
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
			url : "/ret/projectbuildget/contract/getProjectBuildContractSortTree",// Ajax 获取数据的 URL 地址
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
				nameem.val(v).trigger('change');
				if (vid.length > 0)
					vid = vid.substring(0, vid.length - 1);
				nameem.attr("data-value",vid);
			}
		}
	};


function zTreeOnClick(event, treeId, treeNode) {
	$("#creatediv").hide();
	$("#listtable").show();
	var sortId = treeNode.sortId;
	$("#myTable").bootstrapTable('destroy');
	query(sortId);
}
$(function(){
	query("");
	$.ajax({
		url : "/ret/projectbuildget/contract/getProjectBuildContractSortTree",
		type : "post",
		dataType : "json",
		success : function(data) {
			zTree = $.fn.zTree.init($("#tree"), setting, data);// 初始化树节点时，添加同步获取的数据
			$.fn.zTree.init($("#menuTree"), setting1, data);
		}
	});
	jeDate("#signTime", {
		format: "YYYY-MM-DD"
	});
	$('#remark').summernote({ height:300 });
	
	$("#cbut").unbind("click").click(function() {
		document.getElementById("form").reset();
		$("#show_projectbuildattach").html("");
		$("#projectbuildattach").attr("data_value","");
		$("#signUser").attr("data-value","");
		$("#listtable").hide();
		$("#creatediv").show();
		$("#createbut").show();
		$("#updatabut").hide();
		$("#createbut").unbind("click").click(function(){
			addContract();
		})
		
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
	});
	$("#form").bootstrapValidator('validate');
});

function query(sortId)
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/projectbuildget/contract/getcontractlist?sortId='+sortId,
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
	      idField: 'contractId',//key值栏位
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
	       field: 'title',
	       title: '合同标题',
	       sortable : true,
	       width:'200px'
	      },
	      {
		       field: 'signPart',
		       title: '对方单位',
		       width:'200px'
	      },
	      {
			field: 'sortName',
			   width:'50px',
			   title: '所属分类'
			},
	      {
		       field: 'type',
		       width:'80px',
		       title: '合同类型',
		       formatter:function(value,row,index){
		    	   if(value=="1")
		    		   {
		    		   	  return "材料采购合同";
		    		   }else if(value=="2")
		    		   {
		    			   return "工程项目合同";
		    		   }else if(value=="3")
		    		   {
		    			   return "服务合同";
		    		   }
	            }
		   },
	      {
		       field: 'signTime',
		       width:'100px',
		       title: '签订时间'
		   },
		   {
		       field: 'amount',
		       width:'100px',
		       title: '合同金额(元)'
		       
		   },
		   {
		       field: 'createUserName',
		       width:'80px',
		       title: '创建人'
		       
		   },
	      {
	       field: 'opt',
	       title: '操作',
	       align:'center',
	       width:'150px',
    	   formatter:function(value,row,index){
                return createOptBtn(row.contractId);
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
function createOptBtn(contractId)
{
	var html="<a href=\"javascript:void(0);readcontract('"+contractId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);editcontract('"+contractId+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);delcontract('"+contractId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}



function readcontract(contractId)
{
	window.open("/app/core/projectbuild/contract/details?contractId="+contractId);
}


function delcontract(contractId)
{
	if (confirm("确定删除当前合同吗？")) {
		$.ajax({
			url : "/set/projectbuildset/contract/delProjectlBuildContract",
			type : "post",
			dataType : "json",
			data : {
				sortId : $("#sortId").val()
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


function addContract()
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/projectbuildset/contract/insertProjectBuildContract",
		type : "post",
		dataType : "json",
		data:{
			title:$("#title").val(),
			contractCode:$("#contractCode").val(),
			sortNo:$("#sortNo").val(),
			type:$("#type").val(),
			sortId:$("#sortId").attr("data-value"),
			signPart:$("#signPart").val(),
			signTime:$("#signTime").val(),
			amount:$("#amount").val(),
			signUser:$("#signUser").attr("data-value"),
			remark:$("#remark").code(),
			attach:$("#projectbuildattach").attr("data_value")
		},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				window.location.reload();
				top.layer.msg(data.msg);
				}
		}
	})
	}
}

function editcontract (contractId)
{
	document.getElementById("form").reset();
	$("#listtable").hide();
	$("#creatediv").show();
	$("#createbut").hide();
	$("#updatabut").show();
	$("#updatabut").unbind("click").click(function(){
		updateContract(contractId);
	})
	
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
	
	$.ajax({
		url : "/ret/projectbuildget/contract/getProjectBuildContractById",
		type : "post",
		dataType : "json",
		data:{contractId:contractId},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else{
				for(var name in data.list)
				{
					if(name=="attach")
					{
						$("#projectbuildattach").attr("data_value",data.list.attach);
						createAttach("projectbuildattach","2");
					}else if(name=="sortId")
					{
						$("#sortId").attr("data-value",data.list.sortId);
						
					}else if(name=="signUser")
					{
						$("#signUser").attr("data-value",data.list.signUser);
						$("#signUser").val(getUserNameByStr(data.list.signUser));
					}else
					{
						$("#"+name).val(data.list[name]);
					}
			}
		}
		}
	})	
}

function updateContract(contractId)
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/projectbuildset/contract/updateProjectBuildContract",
		type : "post",
		dataType : "json",
		data:{
			contractId:contractId,
			title:$("#title").val(),
			contractCode:$("#contractCode").val(),
			sortNo:$("#sortNo").val(),
			type:$("#type").val(),
			sortId:$("#sortId").attr("data-value"),
			signPart:$("#signPart").val(),
			signTime:$("#signTime").val(),
			amount:$("#amount").val(),
			signUser:$("#signUser").attr("data-value"),
			remark:$("#remark").code(),
			attach:$("#projectbuildattach").attr("data_value")
		},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				window.location.reload();
				top.layer.msg(data.msg);
				}
		}
	})
	}
}


function delcontract(contractId)
{
	$.ajax({
		url : "/set/projectbuildset/contract/delProjectBuildContract",
		type : "post",
		dataType : "json",
		data:{
			contractId:contractId
		},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				window.location.reload();
				top.layer.msg(data.msg);
				}
		}
	})	
}