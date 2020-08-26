	var zTree;
	var setting = {
		async : {
			enable : true,// 设置 zTree 是否开启异步加载模式
			url : "/ret/erpget/getErpProductSortTree",// Ajax 获取数据的 URL 地址
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
				url : "/ret/erpget/getErpProductSortTree",// Ajax 获取数据的 URL 地址
				autoParam : [ "sortId" ],// 异步加载时需要自动提交父节点属性的参数
			},
			view : {
				dblClickExpand : false,
				selectedMulti: false
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
					var idem = $("#sortLeave");
					idem.val(vid);
				}
			}
		};
	
	var setting2 = {
			async : {
				enable : true,// 设置 zTree 是否开启异步加载模式
				url : "/ret/erpget/getErpBomSortTree",// Ajax 获取数据的 URL 地址
				autoParam : [ "sortId" ],// 异步加载时需要自动提交父节点属性的参数
			},
			view : {
				dblClickExpand : false
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
					if(!treeNode.isParent)
						{
							$.ajax({
								url : "/ret/erpget/getErpBomTreeBySortId",
								type : "post",
								dataType : "json",
								data:{sortId:treeNode.sortId},
								success : function(data) {
									if(data.status==200)
									{
									var newNodes=data.list;
									if(newNodes.length==0)
									{
										var zTree = $.fn.zTree.getZTreeObj("menuTree2"), 
										nodes = zTree.getSelectedNodes(), v = "";vid = "";
										nodes.sort(function compare(a, b) {
											return a.id - b.id;
										});
										for (var i = 0, l = nodes.length; i < l; i++) {
											v += nodes[i].sortName + ",";
											vid += nodes[i].sortId + ",";
										}
										if (v.length > 0)
											v = v.substring(0, v.length - 1);
										var nameem = $("#bomName");
										nameem.val(v);
										if (vid.length > 0)
											vid = vid.substring(0, vid.length - 1);
										var idem = $("#bomId");
										idem.val(vid);
									}else
										{
										var treeObj = $.fn.zTree.getZTreeObj("menuTree2");
										nodes = treeObj.getSelectedNodes();
										treeObj.addNodes(nodes[0], newNodes);
										}
									}else
										{
										console.log(data.msg);
										}
								}
							});
							}
				}
			}
		};		
	$(function() {
		$.ajax({
			url : "/ret/erpget/getErpProductSortTree",
			type : "post",
			dataType : "json",
			success : function(data) {
				zTree = $.fn.zTree.init($("#tree"), setting, data);// 初始化树节点时，添加同步获取的数据
				$.fn.zTree.init($("#menuTree"), setting1,data);
				
			}
		});
		createErpUnit();
		$.ajax({
			url : "/ret/erpget/getErpBomSortTree",
			type : "post",
			dataType : "json",
			success : function(data) {
					$.fn.zTree.init($("#menuTree2"), setting2,data);
			}
		});
		$("#createbut").unbind("click").click(function(){
			addErpProduct();
		});
		$("#updatabut").unbind("click").click(function(){
			updateErpProduct();
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
		$("#sortLeaveName").unbind("click").click(function(e){
			e.stopPropagation();
			$("#menuContent").css({
				"width":$(this).outerWidth()+"px"
			}).slideDown(200);
		});
		$("#bomName").unbind("click").click(function(e){
			e.stopPropagation();
			$("#menuContent2").css({
				"width":$(this).outerWidth()+"px"
			}).slideDown(200);
		});
		$(".widget").unbind("click").click(function(){
			$("#menuContent").hide();
			$("#menuContent2").hide();
		});
		$("#menuContent").unbind("click").click(function(e){
			e.stopPropagation();
		});
		$("#menuContent2").unbind("click").click(function(e){
			e.stopPropagation();
		});
	});
	
	function addErpProduct()
	{
		$.ajax({
			url : "/set/erpset/insertErpProduct",
			type : "post",
			dataType : "json",
			data:{
				sortNo:$("#sortNo").val(),
				productName:$("#productName").val(),
				model:$("#model").val(),
				param:$("#param").val(),
				bomId:$("#bomId").val(),
				unit:$("#unit").val(),
				sortLeave:$("#sortLeave").val(),
				price:$("#price").val(),
				unit:$("#unit").val(),
				productImg:$("#productImg").attr("data_value"),
				remark:$("#remark").val()
			},
			success : function(data) {
				if(data.status==200)
					{
					top.layer.msg(data.msg);
					location.reload();
					}else
						{
						console.log(data.msg);
						}
			}
		});
	}
	
	function updateErpProduct()
	{
		$.ajax({
			url : "/set/erpset/updateErpProduct",
			type : "post",
			dataType : "json",
			data:{
				productId:$("#productId").val(),
				sortNo:$("#sortNo").val(),
				productName:$("#productName").val(),
				model:$("#model").val(),
				param:$("#param").val(),
				bomId:$("#bomId").val(),
				unit:$("#unit").val(),
				sortLeave:$("#sortLeave").val(),
				price:$("#price").val(),
				unit:$("#unit").val(),
				productImg:$("#productImg").attr("data_value"),
				remark:$("#remark").val()
			},
			success : function(data) {
				if(data.status==200)
					{
					top.layer.msg(data.msg);
					location.reload();
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
		      url: '/ret/erpget/getErpProductBySort?sortLeave='+sortId,
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
		      idField: 'productId',//key值栏位
		      clickToSelect: true,//点击选中checkbox
		      pageList : [10, 20, 30, 50],//可选择单页记录数
		      queryParams:queryParams,
		      columns: [{
			      checkbox: true
			      },
			     {
			       field: 'productName',
			       title: '产品名称',
			       sortable : true,
			       width:'200px'
			      },
			      {
			       field: 'model',
			       title: '产品型号'
			     },
			     {
			       field: 'param',
			       title: '规格参数'
			      },
			      {
			       field: 'bomId',
			       title: 'BOM清单',
			       width:'200px',
			       formatter:function(value,row,index){
			    	  var info=getBomInfo(value);
			    	  if(info!=undefined)
			    	{
			    		  return "<a href='#' onclick='readBomDetail(\""+value+"\")'>"+info.bomName+"</a>";
			    	}else
			    	{
			    		return "BOM未知错误";
			    	}
		            }
			       
			      },
				   {
				       field: 'remark',
				       title: '备注',
				       width:'300px'
				   },
			      {
			       field: '',
			       title: '操作',
			       align:'center',
			       width:'150px',
			    	   formatter:function(value,row,index){
			               return createOptBtn(row.productId);
			            }
			      }],
		      onClickCell: function (field, value, row, $element) {
		      //alert(row.SystemDesc);
		    },
		    responseHandler:function(res){
		    	if(res.status=="500")
		    		{
		    		console.log(res.msg);
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
	
	 function createOptBtn(productId)
		{
			var html="<a href=\"javascript:void(0);edit('"+productId+"')\" class=\"btn btn-palegreen btn-xs\">编辑</a>&nbsp;&nbsp;<a href=\"javascript:void(0);delErpProduct('"+productId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
			return html;
			}
	function edit(productId)
	{
		$("#show_productImg").empty();
		$("#productImg").attr("data_value","");
		$.ajax({
			url : "/ret/erpget/getProductById",
			type : "post",
			dataType : "json",
			data:{
				productId:productId
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
						if(name=="sortLeave")
							{
							$("#"+name).val(data.list[name]);
							$.ajax({
								url : "/ret/erpget/getErpProductSortById",
								type : "post",
								dataType : "json",
								data:{
									sortId:data.list[name]
								},
								success : function(data) {
									if(data.list)
									{
									$("#sortLeaveName").val(data.list.productSortName);
									}else
										{
										$("#sortLeaveName").val("");
										}
								}
							});
							}else if(name=="productImg")
								{
								$("#"+name).attr("data_value", data.list[name]);
								createAttach(name);
								} else if(name=="bomId")
									{
									$("#"+name).val(data.list[name]);
									$.ajax({
										url : "/ret/erpget/getErpBomById",
										type : "post",
										dataType : "json",
										data:{
											bomId:data.list[name]
										},
										success : function(data) {
											if(data.list)
											{
											$("#bomName").val(data.list.bomName);
											}else
												{
												$("#bomName").val("");
												}
										}
									});
									}else
									{
									$("#"+name).val(data.list[name]);
									}
						}
				}else
					{
					console.log(data.msg);
					}
			}
		});
	}
	
	function delErpProduct(productId)
	{
		 if(confirm("确定删除当前产品吗？"))
		    {
		$.ajax({
			url : "/set/erpset/delErpProduct",
			type : "post",
			dataType : "json",
			data:{
				productId:productId
			},
			success : function(data) {
				if(data.status==200){
					top.layer.msg(data.msg);
					location.reload();
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
	function zTreeOnClick(event, treeId, treeNode)
	{
		$("#creatediv").hide();
		$("#datalist").show();
		$("#myTable").bootstrapTable('destroy');
		query(treeNode.sortId);
	}
	
	function getBomInfo(bomId)
	{
		var info={};
		$.ajax({
			url : "/ret/erpget/getErpBomById",
			type : "post",
			dataType : "json",
			async:false,
			data:{
				bomId:bomId
			},
			success : function(data) {
				if(data.status==200)
					{
					info = data.list;
					}else if(data.status==100)
						{
						top.layer.msg(data.msg);
						}else
						{
						console.log(data.msg);
						}
			}
		});
		return info;
	}