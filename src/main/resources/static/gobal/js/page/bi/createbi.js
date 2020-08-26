var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/biget/getBiSortTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "sortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onClick : zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "sortId",
			pIdKey : "levelId",
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
			url : "/ret/biget/getBiSortTree",// Ajax 获取数据的 URL 地址
			autoParam : [ "deptId" ],// 异步加载时需要自动提交父节点属性的参数
		},
		view : {
			dblClickExpand : false,
			selectedMulti: false//禁止多选
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "sortId",
				pIdKey : "levelId",
				rootPId : "0"
			},
			key : {
				name : "sortName"
			}
		},
		callback : {
			onClick : function(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("menuTree");
				nodes = zTree.getSelectedNodes();
				var vname = [];
				var vid = [];
				nodes.sort(function compare(a, b) {
					return a.id - b.sortId;
				});
				for (var i = 0, l = nodes.length; i < l; i++) {
					vname.push( nodes[i].sortName);
					vid.push(nodes[i].sortId);
				}
				$("#levelId").attr("data-value",vid.join(","));
				$("#levelId").val(vname.join(","));
			}
		}
	};
	


$(function() {
	getBiType("biType");
	var topNode = [{sortName: "BI顶级",levelId:'', isParent: "true", sortId: "0",icon:"/gobal/img/org/org.png"}];
	var topNode1 = [{sortName: "BI顶级",levelId:'', isParent: "true", deptId: "0",icon:"/gobal/img/org/org.png"}];
	zTree = $.fn.zTree.init($("#tree"), setting, topNode);// 初始化树节点时，添加同步获取的数据
	var zTreeObj=$.fn.zTree.init($("#menuTree"), setting1,topNode1);
	var nodes = zTree.getNodes();
	var nodes1 = zTreeObj.getNodes();
	 for(var i=0;i<nodes.length;i++){
		 zTree.expandNode(nodes[i], true, false, false);//默认展开第一级节点
         }
	 for(var i=0;i<nodes1.length;i++){
		 zTreeObj.expandNode(nodes1[i], true, false, false);//默认展开第一级节点
         }
	$("#createbut").unbind("click").click(function(){
		insertBiTemplate();
	});
	$("#cbut").unbind("click").click(function(){
		document.getElementById("from").reset();
		$("#datalist").hide();
		$("#creatediv").show();
		$("#createbut").show();
		$("#updatabut").hide();
		$("#delbut").hide();
	});
	$("#updatabut").unbind("click").click(function(){
		updateBiTemplate();
	});
	$("#delbut").unbind("click").click(function(){
		deleteBiTemplate("");
	});
	$("#cquery").unbind("click").click(function(){
		$("#creatediv").hide();
		$("#datalist").show();
		query("");
	})
	$("#levelId").unbind("click").click(function(e){
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
	getDbSource();
});
function zTreeOnClick(event, treeId, treeNode)
{
	$("#creatediv").hide();
	$("#datalist").show();
	$("#myTable").bootstrapTable('destroy');
	query(treeNode.sortId);
}

function query(levelId)
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/biget/getBiTemplateList?levelId='+levelId,
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
	      idField: 'templateId',//key值栏位
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
	       field: 'templateName',
	       title: '模版名称',
	       sortable : true,
	       width:'200px'
	      },
	      {
	       field: 'version',
	       width:'250px',
	       title: '模版版本'
	     },
	     {
		   field: 'biTypeCnName',
		   title: 'BI分类'
		  },
		  {
			field: 'userName',
			width:'100px',
			title: '创建人'
		 },
	     {
	       field: 'createTime',
	       title: '创建时间',
	       width:'180px'
	      },
	      {
	       field: 'opt',
	       title: '操作',
	       width:'180px',
	       align:'center',
    	   formatter:function(value,row,index){
                return createOptBtn(row.templateId);
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


function createOptBtn(templateId)
{
	var html="<a href=\"javascript:void(0);edit('"+templateId+"')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;<a href=\"javascript:void(0);deleteBiTemplate('"+templateId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
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

function edit(templateId)
{
	$("#creatediv").show();
	$("#datalist").hide();
	$("#createbut").hide();
	$("#updatabut").show();
	$("#delbut").show();
	$("#accountId").attr("readOnly","readOnly");
	$.ajax({
		url : "/ret/biget/getBiTemplateById",
		type : "post",
		dataType : "json",
		data:{
			templateId:templateId
		},
		success : function(data) {
			if(data.status==200){
				var info = data.list;
				for(var id in info)
					{
					if(id=="levelId")
						{
							$("#levelId").attr("data-value",info[id]);
							$.ajax({
								url : "/ret/biget/getBiSortById",
								type : "post",
								dataType : "json",
								data:{
									sortId:info[id]
								},
								success : function(data) {
									if(data.status=="200")
										{
										$("#levelId").val(data.list.sortName);
										}else if(data.status=="100")
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
							$("#"+id).val(info[id]);			
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


function deleteBiTemplate(templateId)
{
	 if(confirm("确定删除当前模版吗？"))
	    {
	if(templateId=="")
		{
		templateId=$("#templateId").val();
		}
	$.ajax({
		url : "/set/biset/deleteBiTemplate",
		type : "post",
		dataType : "json",
		data:{
			templateId:templateId
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

function insertBiTemplate()
{
	$.ajax({
		url : "/set/biset/insertBiTemplate",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			templateName:$("#templateName").val(),
			version:$("#version").val(),
			levelId:$("#levelId").attr("data-value"),
			biType:$("#biType").val(),
			dbSource:$("#dbSource").val(),
			optionConfig:$("#optionConfig").val(),
			userPriv:$("#userPriv").val()
		},
		success : function(data) {
			if(data.status=="200"){
				top.layer.msg(data.msg);
				location.reload();
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

function updateBiTemplate()
{
	$.ajax({
		url : "/set/biset/updateBiTemplate",
		type : "post",
		dataType : "json",
		data:{
			templateId:$("#templateId").val(),
			sortNo:$("#sortNo").val(),
			templateName:$("#templateName").val(),
			version:$("#version").val(),
			levelId:$("#levelId").attr("data-value"),
			biType:$("#biType").val(),
			dbSource:$("#dbSource").val(),
			sqlConfig:$("#sqlConfig").val(),
			userPriv:$("#userPriv").val()
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

function getDbSource()
{
	$.ajax({
		url:"/ret/sysget/getDbSourceList",
		type:"POST",
		dataType:"json",
		success:function(data){
			if(data.status==200)
				{
				var html="<option value=''>系统内部数据</option>";
				for(var i=0;i<data.list.length;i++)
					{
					html+="<option value='"+data.list[i].dbSourceId+"'>"+data.list[i].dbSourceName+"</option>";
					}
				$("#dbSource").html(html);
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