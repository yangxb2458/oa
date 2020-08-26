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
		


	$(function() {
		$.ajax({
			url : "/ret/erpget/getErpProductSortTree",
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
			addsort();
		});
		$("#cbut").unbind("click").click(function(){
			document.getElementById("from").reset();
			$("#createbut").show();
			$("#updatabut").hide();
			$("#delbut").hide();
		});
		$("#delbut").unbind("click").click(function(){
			delErpProductSort();
		});
		$("#updatabut").unbind("click").click(function(){
			updateErpProductSort();
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
		$("#show_sortImg").empty();
		$("#sortImg").attr("data_value","");
		$.ajax({
			url : "/ret/erpget/getErpProductSortById",
			type : "post",
			dataType : "json",
			data:{
				sortId:treeNode.sortId
			},
			success : function(data) {
				if(data.status==200)
					{
					var v = data.list;
					for(name in v)
						{
						if(name=="sortLeave")
							{
							$("#"+name).val(v[name]);
							$.ajax({
								url : "/ret/erpget/getErpProductSortById",
								type : "post",
								dataType : "json",
								data:{
									sortId:v["sortLeave"]
								},
								success : function(data) {
									if(data.status=="200")
										{
										if(data.list)
											{
											$("#sortLeaveName").val(data.list.productSortName);
											}else
												{
												$("#sortLeaveName").val("");
												}
										}
								}
							});
							}else if(name=="sortImg")
								{
								$("#"+name).attr("data_value", v[name]);
								createAttach(name);
							}else
								{
								$("#"+name).val(v[name]);
								}
						}
					}else
						{
						console.log(data.msg);
						}
				
			}
		});
		$("#createbut").hide();
		$("#updatabut").show();
		$("#delbut").show();
	}
	function delErpProductSort()
	{
		 if(confirm("确定删除当前分类吗？"))
		    {
		$.ajax({
			url : "/set/erpset/delErpProductSort",
			type : "post",
			dataType : "json",
			data:{
				sortId:$("#sortId").val()
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

	function addsort()
	{
		$.ajax({
			url : "/set/erpset/insertErpProductSort",
			type : "post",
			dataType : "json",
			data:{
				sortNo:$("#sortNo").val(),
				productSortName:$("#productSortName").val(),
				sortCode:$("#sortCode").val(),
				sortImg:$("#sortImg").attr("data_value"),
				sortLeave:$("#sortLeave").val(),
				sortRemark:$("#sortRemark").val()
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
	}

	function updateErpProductSort()
	{
		$.ajax({
			url : "/set/erpset/updateErpProductSort",
			type : "post",
			dataType : "json",
			data:{
				sortId:$("#sortId").val(),
				sortNo:$("#sortNo").val(),
				productSortName:$("#productSortName").val(),
				sortCode:$("#sortCode").val(),
				sortImg:$("#sortImg").attr("data_value"),
				sortLeave:$("#sortLeave").val(),
				sortRemark:$("#sortRemark").val()
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
	}