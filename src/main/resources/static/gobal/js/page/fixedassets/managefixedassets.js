$(function(){
	query();
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	$.ajax({
		url : "/ret/fixedassetsget/getFixedAssetSortTree",
		type : "post",
		dataType : "json",
		success : function(data) {
			var topNode = [ {
				sortName : "全部分类",
				isParent : "fase",
				sortId : ""
			} ];
			var newTreeNodes = topNode.concat(data);
			$.fn.zTree.init($("#menuTree"), setting1, newTreeNodes);
		}
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
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
});

var setting1 = {
		async : {
			enable : true,// 设置 zTree 是否开启异步加载模式
			url : "/ret/fixedassetsget/getFixedAssetSortTree",// Ajax 获取数据的 URL 地址
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
				if (vid.length > 0)
					vid = vid.substring(0, vid.length - 1);
				nameem.attr("data-value",vid);
			}
		}
	};
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/fixedassetsget/getFixedAssetsList',
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
		      idField: 'assetsId',//key值栏位
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
		       field: 'assetsName',
		       title: '固定资产名称',
		       sortable : true,
		       width:'100px'
		      },
		      {
			       field: 'assetsCode',
			       width:'100px',
			       title: '固定资产编号'
			   },
		      {
				field: 'ownDeptName',
				   width:'100px',
				   title: '所属部门'
				},
				{
				       field: 'sortName',
				       width:'50px',
				       title: '固定资产分类'
				   },
				{
			       field: 'brand',
			       title: '品牌',
			       width:'50px'
		      },
		      {
			       field: 'model',
			       width:'100px',
			       title: '型号'
			   },
			   {
			       field: 'purchasePrice',
			       width:'50px',
			       title: '采购价格'
			   },{
			       field: 'status',
			       title: '当前状态',
			       width:'50px',
		    	   formatter:function(value,row,index){
		    		   if(value=="0")
		    			  {
		    			   return "空闲";
		    			  }else if(value=="1")
		    			  {
		    				  return "使用中"; 
		    			  }else if(value=="2")
			    			{
			    				  return "维修中";
			    			}else if(value=="3")
			    			{
			    				return "已报废";
			    			}
		            }
		      },
		      {
		       field: 'opt',
		       title: '操作',
		       align:'center',
		       width:'100px',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.assetsId);
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
        sortOrder:params.order,
        sortId:$("#sortId").attr("data-value"),
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val()
    };
    return temp;
};
function createOptBtn(assetsId)
{
	var html="<a href=\"javascript:void(0);readdetails('"+assetsId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);edit('"+assetsId+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);del('"+assetsId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}

function edit(assetsId)
{
	//window.location.href = "/app/core/fixedassets/manage?view=edit&assetsId="+assetsId;
	open("/app/core/fixedassets/manage?view=edit&assetsId="+assetsId,"_self");
}

function readdetails(assetsId)
{
	window.open("/app/core/fixedassets/detailsfixedassets?assetsId="+assetsId);
	}

function del(assetsId)
{
	$.ajax({
		url : "/set/fixedassetsset/deleteFixedAssets",
		type : "post",
		dataType : "json",
		data:{assetsId:assetsId},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				top.layer.msg(data.msg);
				$('#myTable').bootstrapTable('refresh');
				}
		}
	})	
	
}
