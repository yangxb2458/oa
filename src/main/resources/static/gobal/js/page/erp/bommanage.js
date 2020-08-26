	var zTree;
	var setting = {
		async : {
			enable : true,// 设置 zTree 是否开启异步加载模式
			url : "/ret/erpget/getErpBomSortTree ",// Ajax 获取数据的 URL 地址
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
				url : "/ret/erpget/getErpBomSortTree ",// Ajax 获取数据的 URL 地址
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
					pIdKey : "sortLeave",
					rootPId : "0"
				},
				key : {
					name : "sortName"
				}
			},
			callback : {
				onClick : function(e, treeId, treeNode) {
					var zTree = $.fn.zTree.getZTreeObj("menuTree"), nodes = zTree
							.getSelectedNodes(), v = "";
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
					var nameem = $("#sortLeaveName");
					nameem.val(v);
					if (vid.length > 0)
						vid = vid.substring(0, vid.length - 1);
					var idem = $("#sortId");
					idem.val(vid);
				}
			}
		};
		


	$(function() {
		$.ajax({
			url : "/ret/erpget/getErpBomSortTree ",
			type : "post",
			dataType : "json",
			success : function(data) {
					zTree = $.fn.zTree.init($("#tree"), setting, data);// 初始化树节点时，添加同步获取的数据
					var topNode = [{sortName: "TOP分类", isParent: "fase", sortId: ""}];
					var newTreeNodes=topNode.concat(data);
					$.fn.zTree.init($("#menuTree"), setting1,newTreeNodes);
			}
		});
		$("#createbut").unbind("click").click(function(){
			addErpBom();
		});
		$("#cbut").unbind("click").click(function(){
			document.getElementById("form").reset();
			$("#creatediv").show();
			$("#datalist").hide();
		});
		$("#qbut").unbind("click").click(function(){
			$("#creatediv").hide();
			$("#datalist").show();
			$("#myTable").bootstrapTable('destroy');
			query("");
		});
		$("#updatabut").unbind("click").click(function(){
			updateErpBom();
		});
		$("#sortLeaveName").unbind("click").click(function(e){
			e.stopPropagation();
			$("#menuContent").css({
				"width":$(this).outerWidth()+"px"
			}).slideDown(200);
		});
		$("body").unbind("click").click(function(){
			$("#menuContent").hide();
		});

		$("#menuContent").unbind("click").click(function(e){
			e.stopPropagation();
		});
	});
	function zTreeOnClick(event, treeId, treeNode)
	{
		$("#creatediv").hide();
		$("#datalist").show();
		$("#myTable").bootstrapTable('destroy');
		query(treeNode.sortId);
	}
	function delErpBom(bomId)
	{
		 if(confirm("确定删除当前BOM吗？"))
		    {
		$.ajax({
			url : "/set/erpset/delErpBom",
			type : "post",
			dataType : "json",
			data:{
				bomId:bomId
			},
			success : function(data) {
				if(data.status==200){
					top.layer.msg(data.msg);
					location.reload();
				}else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
					{
					console.log(data.msg);
					}
			}
		});
		    }else
		    	{
		    	return;
		    	}
	}

	function addErpBom()
	{
		$.ajax({
			url : "/set/erpset/insertErpBom",
			type : "post",
			dataType : "json",
			data:{
				sortNo:$("#sortNo").val(),
				bomName:$("#bomName").val(),
				sortId:$("#sortId").val(),
				version:$("#version").val(),
				materielCode:$("#materielCode").val(),
				mapNumber:$("#mapNumber").val(),
				productNumber:$("#productNumber").val(),
				status:$("input[name='status']:checked").val(),
				sketchMap:$("#sketchMap").attr("data_value"),
				remark:$("#remark").val()
			},
			success : function(data) {
				if(data.status==200){
					top.layer.msg(data.msg);
					location.reload();
				}else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
					{
					console.log(data.msg);
					}
			}
		});
	}

	function updateErpBom()
	{
		$.ajax({
			url : "/set/erpset/updateErpBom",
			type : "post",
			dataType : "json",
			data:{
				bomId:$("#bomId").val(),
				sortNo:$("#sortNo").val(),
				bomName:$("#bomName").val(),
				sortId:$("#sortId").val(),
				version:$("#version").val(),
				materielCode:$("#materielCode").val(),
				mapNumber:$("#mapNumber").val(),
				productNumber:$("#productNumber").val(),
				status:$("input[name='status']:checked").val(),
				sketchMap:$("#sketchMap").attr("data_value"),
				remark:$("#remark").val()
			},
			success : function(data) {
				if(data.status==200){
					top.layer.msg(data.msg);
					location.reload();
				}else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
					{
					console.log(data.msg);
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

	function query(sortId)
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/erpget/getErpBomBySort?sortId='+sortId,
		      method: 'post',
		      contentType:'application/x-www-form-urlencoded',
		      toolbar: '#toobar',//工具列
		      striped: true,//隔行换色
		      cache: false,//禁用缓存
		      pagination: true,//启动分页
		      sidePagination: 'server',//分页方式
		      pageNumber: 1,//初始化table时显示的页码
		      pageSize: 20,//每页条目
		      showFooter: false,//是否显示列脚
		      showPaginationSwitch: true,//是否显示 数据条数选择框
		      sortable: true,//排序
		      search: true,//启用搜索
		      showColumns: true,//是否显示 内容列下拉框
		      showRefresh: true,//显示刷新按钮
		      idField: 'materielId',//key值栏位
		      clickToSelect: true,//点击选中checkbox
		      pageList : [10, 20, 30, 50],//可选择单页记录数
		      queryParams:queryParams,
		      columns: [{
		      checkbox: true
		      },
		      {
			     field: 'materielCode',
			     title: '物料编码',
			     width:'200px'
			   },
		     {
		       field: 'bomName',
		       title: 'BOM名称',
		       sortable : true,
		       width:'200px'
		      },
		      {
		       field: 'version',
		       title: '版本'
		     },
		     {
		       field: 'mapNumber',
		       title: '设计图号'
		      },
			  {
			     field: 'productNumber',
			     title: '制品号'
			   },
		      {
		       field: 'status',
		       title: '状态',
		    	   formatter:function(value,row,index){
		                if(value==0)
		                	{
		                	return "停用";
		                	}else if(value==1)
		                		{
		                		return "启用";
		                		}
		            }
		      },
		      {
			       field: 'createTime',
			       title: '创建时间',
				   width:'200px'
			   },
			   {
			       field: 'remark',
			       title: '备注'
			   },
		      {
		       field: 'opt',
		       width:'180px',
		       align:'center',
		       title: '操作',
		    	   formatter:function(value,row,index){
		                return createOptBtn(row.bomId);
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
	function createOptBtn(bomId)
	{
		var html="<a href=\"javascript:void(0);detail('"+bomId+"')\" class=\"btn btn-maroon btn-xs\">BOM维护</a>&nbsp;&nbsp;<a href=\"javascript:void(0);edit('"+bomId+"')\" class=\"btn btn-palegreen btn-xs\">编辑</a>&nbsp;&nbsp;<a href=\"javascript:void(0);delErpBom('"+bomId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
		return html;
		}

function detail(bomId)
{
	//window.location.href = "/app/core/erp/cost/bomdetail?bomId="+bomId;
	open("/app/core/erp/cost/bomdetail?bomId="+bomId,"_self");
}
	
function edit(bomId)
{
	$("#show_sketchMap").empty();
	$("#sketchMap").attr("data_value","");
	$.ajax({
		url : "/ret/erpget/getErpBomById",
		type : "post",
		dataType : "json",
		data:{
			bomId:bomId
		},
		success : function(data) {
			
			if(data.status==200){
				document.getElementById("form").reset();
				$("#creatediv").show();
				$("#createbut").hide();
				$("#updatabut").show();
				$("#datalist").hide();
				for(name in data.list)
					{
					if(name=="sortId")
						{
						$.ajax({
							url : "/ret/erpget/getErpBomSortById",
							type : "post",
							dataType : "json",
							data:{
								sortId:data.list[name]
							},
							success : function(data) {
								if(data.status=="200")
									{
									$("#sortLeaveName").val(data.list.sortName);
									}else if(data.status==100)
									{
										top.layer.msg(data.msg);
									}else
										{
										console.log(data.msg);
										}
								
							}
						});
						$("#"+name).val(data.list[name]);
						}else if(name=="sketchMap")
						{
							$("#"+name).attr("data_value", data.list[name]);
							createAttach(name)
						}else
							{
							$("#"+name).val(data.list[name]);
							}
					}
			}else if(data.status==100)
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
	});
}