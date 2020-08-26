		var zTree;
		var setting = {
			async : {
				enable : true,// 设置 zTree 是否开启异步加载模式
				url : "/ret/erpget/getErpMaterielSortTree",// Ajax 获取数据的 URL 地址
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
					url : "/ret/erpget/getErpMaterielSortTree",// Ajax 获取数据的 URL 地址
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
						if(!treeNode.isParent)
							{
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
				}
			};
			


		$(function() {
			$.ajax({
				url : "/ret/erpget/getErpMaterielSortTree",
				type : "post",
				dataType : "json",
				success : function(data) {
					zTree = $.fn.zTree.init($("#tree"), setting, data);// 初始化树节点时，添加同步获取的数据
					$.fn.zTree.init($("#menuTree"), setting1,data);
				}
			});
			createErpUnit();
			$("#createbut").unbind("click").click(function(){
				addErpMateriel();
			});
			$("#batdel").unbind("click").click(function(){
				var selected = $('#myTable').bootstrapTable('getSelections');
				var ids = new Array();
				for(var i=0;i<selected.length;i++){
				ids[i]=selected[i].id;
				}
			});
			
			$("#cbut").unbind("click").click(function(){
				document.getElementById("from").reset();
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
				updateErpMateriel();
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

		//得到查询的参数
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
			      url: '/ret/erpget/getErpMaterielBySort?sortLeave='+sortId,
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
			       sortable : true,
			       width:'300px'
			      },
			      {
			       field: 'materielName',
			       title: '物料名称'
			     },
			     {
			       field: 'param',
			       title: '规格参数'
			      },
			      {
			       field: 'source',
			       title: '物料来源',
			    	   formatter:function(value,row,index){
			                if(value==0)
			                	{
			                	return "外购";
			                	}else if(value==1)
			                		{
			                		return "自制";
			                		}
			            }
			      },
			      {
				       field: 'price',
				       title: '单价(元/RMB)',
					   width:'100px'
				   },
				   {
				       field: 'unit',
				       title: '计量单位',
				       formatter:function(value,row,index){
			                return getUnitName(value);
			            }
				   },
			      {
			       field: '',
			       title: '操作',
			       align:'center',
			    	   formatter:function(value,row,index){
			                return createOptBtn(row.materielId);
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
			    		}else{
			    			return {
			    				total : res.list.total, //总页数,前面的key必须为"total"
			    				rows : res.list.list //行数据，前面的key要与之前设置的dataField的值一致.
			    			};
			    			}
			    }
			   });
		 }
		 
		function delErpMateriel(materielId)
		{
			var msg = "您真的确定要删除吗？\n\n请确认！"; 
			if (confirm(msg)==true){ 
				$.ajax({
					url : "/set/erpset/delErpMateriel",
					type : "post",
					dataType : "json",
					data:{
						materielId:materielId
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
			}else{ 
			return; 
			} 
		}

		function addErpMateriel()
		{
			$.ajax({
				url : "/set/erpset/insertErpMateriel",
				type : "post",
				dataType : "json",
				data:{
					sortNo:$("#sortNo").val(),
					materielCode:$("#materielCode").val(),
					materielName:$("#materielName").val(),
					param:$("#param").val(),
					source:$("input[name='source']:checked").val(),
					type:$("input[name='type']:checked").val(),
					sortLeave:$("#sortLeave").val(),
					price:$("#price").val(),
					unit:$("#unit").val(),
					materielImg:$("#materielImg").attr("data_value"),
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

		function updateErpMateriel()
		{
			$.ajax({
				url : "/set/erpset/updateErpMateriel",
				type : "post",
				dataType : "json",
				data:{
					materielId:$("#materielId").val(),
					sortNo:$("#sortNo").val(),
					materielCode:$("#materielCode").val(),
					materielName:$("#materielName").val(),
					param:$("#param").val(),
					source:$("input[name='source']:checked").val(),
					type:$("input[name='type']:checked").val(),
					sortLeave:$("#sortLeave").val(),
					price:$("#price").val(),
					unit:$("#unit").val(),
					materielImg:$("#materielImg").attr("data_value"),
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

		function createOptBtn(erpMaterielId)
		{
			var html="<a href=\"javascript:void(0);edit('"+erpMaterielId+"')\" class=\"btn btn-palegreen btn-xs\">编辑</a>&nbsp;&nbsp;<a href=\"javascript:void(0);delErpMateriel('"+erpMaterielId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
			return html;
			}

	function edit(materielId)
	{
		$("#show_materielImg").empty();
		$("#materielImg").attr("data_value","");
		$.ajax({
			url : "/ret/erpget/getErpMaterielById",
			type : "post",
			dataType : "json",
			data:{
				materielId:materielId
			},
			success : function(data) {
				if(data.status==200){
					document.getElementById("from").reset();
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
								url : "/ret/erpget/getErpMaterielSortById",
								type : "post",
								dataType : "json",
								data:{
									sortId:data.list[name]
								},
								success : function(data) {
									if(data.list)
									{
									$("#sortLeaveName").val(data.list.sortName);
									}else
										{
										$("#sortLeaveName").val("");
										}
								}
							});
							}else if(name=="materielImg")
								{
								$("#"+name).attr("data_value", data.list[name]);
								createAttach(name);
								}else if(name=="source")
									{
									 $("input:radio[name='source'][value='"+data.list[name]+"']").attr('checked','true');
									}else if(name=="type")
										{
										$("input:radio[name='type'][value='"+data.list[name]+"']").attr('checked','true');
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