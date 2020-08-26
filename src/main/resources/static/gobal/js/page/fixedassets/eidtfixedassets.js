$(function(){
	 $('#remark').summernote({ height:300 });
		jeDate("#purchaseTime", {
			format: "YYYY-MM-DD"
		});
		$("#updatebut").unbind("click").click(function(){
			updateFixedAssets();
		})
		createStorageList();
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
			url : "/ret/fixedassetsget/getFixedAssetsById",
			type : "post",
			dataType : "json",
			data:{assetsId:assetsId},
			success : function(data) {
				if(data.status=="200")
				{
					for(var name in data.list)
					{
						if(name=="sortId")
						{
							$("#"+name).attr("data-value",data.list[name]);
							$.ajax({
								url : "/ret/fixedassetsget/getFixedAssetsSortById",
								type : "post",
								dataType : "json",
								data:{sortId:data.list[name]},
								success : function(data) {
									if(data.status=="200")
									{
										$("#sortId").val(data.list.sortName);
									}else if(data.status=="100")
									{
										top.layer.msg(data.msg);
									}else
									{
										console.log(data.msg);
									}
								}
							});
						}else if(name=="remark")
						{
							$("#remark").code(data.list[name])
						}else if(name=="ownDept")
						{
							$("#"+name).val(getDeptNameByDeptIds(data.list[name]));
							$("#"+name).attr("data-value",data.list[name]);
						}else if(name=="attach")
						{
							$("#fixedattach").attr("data_value", data.list[name]);
							createAttach("fixedattach",0);
						}else
						{
							$("#"+name).val(data.list[name]);
						}
					}
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else
				{
					console.log(data.msg);
				}
			}
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

function updateFixedAssets()
{
	$.ajax({
		url : "/set/fixedassetsset/updateFixedAssets",
		type : "post",
		dataType : "json",
		data:{
			assetsId:assetsId,
			sortNo:$("#sortNo").val(),
			assetsName:$("#assetsName").val(),
			assetsCode:$("#assetsCode").val(),
			brand:$("#brand").val(),
			model:$("#model").val(),
			ownDept:$("#ownDept").attr("data-value"),
			purchasePrice:$("#purchasePrice").val(),
			purchaseTime:$("#purchaseTime").val(),
			depreciation:$("#depreciation").val(),
			sortId:$("#sortId").attr("data-value"),
			storageId:$("#storageId").val(),
			remark:$("#remark").code(),
			attach:$("#fixedattach").attr("data_value")
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				window.location.reload();
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
function createStorageList()
{
	$.ajax({
		url : "/ret/fixedassetsget/getAllFixedAssetsStorageList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var html="<option value='0'>请选择</option>";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<option value=\""+data.list[i].storageId+"\">"+data.list[i].storageName+"</option>";
				}
				$("#storageId").html(html);
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