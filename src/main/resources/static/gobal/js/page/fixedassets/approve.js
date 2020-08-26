$(function(){
	query();
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
	$.ajax({
		url : "/ret/fixedassetsget/getFixedAssetSortTree",
		type : "post",
		dataType : "json",
		success : function(data) {
			var topNode = [ {
				sortName : "TOP分类",
				isParent : "fase",
				sortId : ""
			} ];
			var newTreeNodes = topNode.concat(data);
			$.fn.zTree.init($("#menuTree"), setting1, newTreeNodes);
		}
	});
	$("#assetsSortId").unbind("click").click(function(e) {
		e.stopPropagation();
		$("#menuContent").css({
			"width" : $(this).outerWidth() + "px",
			"left":document.getElementById("assetsSortId").offsetLeft+ "px"
		}).slideDown(200);
	});
	$("body").unbind("click").click(function() {
		$("#menuContent").hide();
	});

	$("#menuContent").unbind("click").click(function(e) {
		e.stopPropagation();
	});
})

var setting1 = {
		async : {
			enable : true,// 设置 zTree 是否开启异步加载模式
			url : "/ret/fixedassetsget/getFixedAssetSortTree",// Ajax 获取数据的 URL 地址
			autoParam : [ "sortId" ],// 异步加载时需要自动提交父节点属性的参数
		},
		view : {
			dblClickExpand : false,
			selectedMulti: false//禁止多选
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "sortId",
				pIdKey : "leaveId",
				rootPId : "0"
			},
			key : {
				name : "sortName"
			}
		},
		callback : {
			onClick : function(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("menuTree"); 
				var nodes = zTree.getSelectedNodes();
				nodes.sort(function compare(a, b) {
					return a.id - b.id;
				});
				var vArr=[];
				var vIdArr=[];
				for (var i = 0, l = nodes.length; i < l; i++) {
					vArr.push(nodes[i].sortName);
					vIdArr.push(nodes[i].sortId);
				}
				var leaveId = $("#assetsSortId");
				leaveId.val(vArr.join(","));
				leaveId.attr("data-value",vIdArr.join(","));
			}
		}
};

function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/fixedassetsget/getFixedAssetsApplyList',
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
		      idField: 'applyId',//key值栏位
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
		       title: '资产名称',
		       sortable : true,
		       width:'100px'
		      },
		      {
				field: 'assetsCode',
				   width:'50px',
				   title: '资产编号'
				},
				{
			       field: 'status',
			       title: '当前状态',
			       width:'50px',
			       formatter:function(value,row,index){
		    	   if(value=="0")
		    		   {
		    		   		return "待审批";
		    		   }else
		    			{
		    			   return "已审批";   
		    			}
		    				  
	            }
		      },
		      {
			       field: 'usedUser',
			       width:'50px',
			       title: '使用人',
			       formatter:function(value,row,index){
			    	  return getUserNameByStr(value);
			       }
			   },
			   {
			       field: 'usedDept',
			       width:'50px',
			       title: '使用部门',
			       formatter:function(value,row,index){
			    	   return getDeptNameByDeptIds(value);
			       }
			   },
		      {
			       field: 'createUserName',
			       width:'50px',
			       title: '申请人'
			   },
			   {
			       field: 'createTime',
			       width:'100px',
			       title: '申请时间'
			   },
		      {
		       field: 'opt',
		       width:'100px',
		       align:'center',
		       title: '操作',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.applyId,row.assetsId,row.status);
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
        status:$("#status").val(),
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val(),
        assetsSortId:$("#assetsSortId").attr("data-value")
    };
    return temp;
};
function createOptBtn(applyId,assetsId,status)
{
	var html="<a href=\"javascript:void(0);details('"+applyId+"','"+assetsId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;" ;
	if(status=="0")
	{
		html+="<a href=\"javascript:void(0);approve('"+applyId+"','"+assetsId+"')\" class=\"btn btn-success btn-xs\" >审批</a>&nbsp;&nbsp;";
	}
	return html;
}




function approve(applyId,assetsId)
{
	$("#approvefixedmodal").modal("show");
	getFixedAssetsById(assetsId);
	$(".js-save").unbind("click").click(function(){
		$.ajax({
			url : "/set/fixedassetsset/insertFixedAssetsApproval",
			type : "post",
			dataType : "json",
			data:{
				applyId:applyId,
				remark:$("#remark").val(),
				status:$('input:radio[name="status"]:checked').val()
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
					top.layer.msg(data.msg);
					$("#approvefixedmodal").modal("hide");
					$('#myTable').bootstrapTable('refresh');
					}
			}
		})	
	})
}


function details(applyId,assetsId)
{
	window.open("/app/core/fixedassets/detailsfixedassetsandapply?applyId="+applyId+"&assetsId="+assetsId);
}



function getFixedAssetsById(assetsId)
{
	$.ajax({
		url : "/ret/fixedassetsget/getFixedAssetsById",
		type : "post",
		dataType : "json",
		data:{assetsId:assetsId},
		success : function(data) {
			var info = data.list;
			for(var name in info)
				{
					if(name=="assetsName")
					{
						$("#"+name).html(info[name]);
					}else if(name=="assetsCode")
					{
						$("#"+name).html(info[name]);
					}else if(name=="brand")
					{
						$("#"+name).html(info[name]);
					}else if(name=="model")
					{
						$("#"+name).html(info[name]);
					}
				}
		}
	});
}
