var setting = {
	async : {
		enable : false,// 设置 zTree 是否开启异步加载模式
		url : "/ret/bpmget/getBpmFlowSortTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "bpmSortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onClick : zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "bpmSortId",
			rootPId : "0"
		},
		key : {
			name : "bpmSortName"
		}
	}
};
var setting1 = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/bpmget/getBpmSortTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "bpmSortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	view : {
		dblClickExpand : false,
		selectedMulti : false
	// 禁止多选
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "bpmSortId",
			rootPId : "0"
		},
		key : {
			name : "bpmSortName"
		}
	},
	callback : {
		onClick : function(e, treeId, treeNode) {
			$("#form").data("bootstrapValidator").updateStatus("flowSort", "NOT_VALIDATED", null );
			var zTree = $.fn.zTree.getZTreeObj("menuTree"), 
			nodes = zTree.getSelectedNodes(), v = "";
			vid = "";
			nodes.sort(function compare(a, b) {
				return a.id - b.id;
			});
			for (var i = 0, l = nodes.length; i < l; i++) {
				v += nodes[i].bpmSortName + ",";
				vid += nodes[i].bpmSortId + ",";
			}
			if (v.length > 0)
				v = v.substring(0, v.length - 1);
			var nameem = $("#flowSort");
			nameem.val(v).trigger('change');
			if (vid.length > 0)
				vid = vid.substring(0, vid.length - 1);
			nameem.attr("data-value",vid);
			$("#menuContent").hide();
			$("#form").bootstrapValidator("validateField","flowSort");
		}
	}
};

var setting2 = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/bpmget/getBpmFormSortTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "bpmSortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	view : {
		dblClickExpand : false,
		selectedMulti : false
	// 禁止多选
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "bpmSortId",
			rootPId : "0"
		},
		key : {
			name : "bpmSortName"
		}
	},
	callback : {
		onClick : function(e, treeId, treeNode) {
			if (!treeNode.isParent) {
				$("#form").data("bootstrapValidator").updateStatus("formId", "NOT_VALIDATED", null );
				var zTree = $.fn.zTree.getZTreeObj("menuTree1"), nodes = zTree
						.getSelectedNodes(), v = "";
				vid = "";
				nodes.sort(function compare(a, b) {
					return a.id - b.id;
				});
				for (var i = 0, l = nodes.length; i < l; i++) {
					v += nodes[i].bpmSortName + ",";
					vid += nodes[i].bpmSortId + ",";
				}
				if (v.length > 0)
					v = v.substring(0, v.length - 1);
				var nameem = $("#formId");
				nameem.val(v).trigger('change');
				if (vid.length > 0)
					vid = vid.substring(0, vid.length - 1);
				nameem.attr("data-value",vid);
				$("#menuContent1").hide();
			}
			$("#form").bootstrapValidator("validateField","formId");
		}
	}
};

$(function() {
	$.ajax({
		url : "/ret/bpmget/getBpmFlowSortTree",
		type : "post",
		dataType : "json",
		data : {
			bpmSortId : "0"
		},
		success : function(data) {
			$.fn.zTree.init($("#tree"), setting, data);// 初始化树节点时，添加同步获取的数据
		}
	});
	$.ajax({
		url : "/ret/bpmget/getBpmSortTree",
		type : "post",
		dataType : "json",
		data : {
			leaveId : "0"
		},
		success : function(data) {
			$.fn.zTree.init($("#menuTree"), setting1, data);
		}
	});
	$.ajax({
		url : "/ret/bpmget/getBpmFormSortTree",
		type : "post",
		dataType : "json",
		data : {
			bpmSortId : "0"
		},
		success : function(data) {
			$.fn.zTree.init($("#menuTree1"), setting2, data);// 初始化树节点时，添加同步获取的数据
		}
	});
	$("#cbut").unbind("click").click(function() {
		window.location.reload();
//		document.getElementById("form").reset();
//		document.getElementById("form2").reset();
//		document.getElementById("form3").reset();
//		$("#createbut").show();
//		$("#updatabut").hide();
//		$("#delbut").hide();
//		$("#designerbut").hide();
//		$("#infodiv").show();
//		$("#tablediv").hide();
//		$("#queryPriv").attr("data-value","");
//		$("#managePriv").attr("data-value","");
	});

	$("#updatabut").unbind("click").click(function() {
		$("#form").bootstrapValidator('validate');
		var flag = $('#form').data('bootstrapValidator').isValid();
		if (flag) {
		$.ajax({
			url : "/set/bpmset/updateBpmFlow",
			type : "post",
			dataType : "json",
			data : {
				flowId : $("#flowId").val(),
				sortNo : $("#sortNo").val(),
				flowName : $("#flowName").val(),
				flowSort : $("#flowSort").attr("data-value"),
				formId : $("#formId").attr("data-value"),
				remark : $("#remark").val(),
				docNumRule : $("#docNumRule").val(),
				beginDocNum : $("#beginDocNum").val(),
				freeToOther : $("#freeToOther").val(),
				autoStyle : $('input:radio[name=autoStyle]:checked').val(),
				flowCache : $('input:radio[name=flowCache]:checked').val(),
				attachPriv : $('input:radio[name=attachPriv]:checked').val(),
				printFlag : $('input:radio[name=printFlag]:checked').val(),
				queryPriv : $("#queryPriv").attr("data-value"),
				printField:getCheckBoxValue("printField"),
				managePriv : $("#managePriv").attr("data-value")
			},
			success : function(data) {
				if (data.status == 500) {
					console.log(data.msg);
				} else {
					top.layer.msg(data.msg);
					window.location.reload();
				}
			}
		});
		}
	});
	$("#createbut").unbind("click").click(function() {
		$("#form").bootstrapValidator('validate');
		var flag = $('#form').data('bootstrapValidator').isValid();
		if (flag) {
		$.ajax({
			url : "/set/bpmset/insertBpmFlow",
			type : "post",
			dataType : "json",
			data : {
				sortNo : $("#sortNo").val(),
				flowName : $("#flowName").val(),
				flowSort : $("#flowSort").attr("data-value"),
				formId : $("#formId").attr("data-value"),
				remark : $("#remark").val(),
				docNumRule : $("#docNumRule").val(),
				beginDocNum : $("#beginDocNum").val(),
				freeToOther : $("#freeToOther").val(),
				autoStyle : $('input:radio[name=autoStyle]:checked').val(),
				flowCache : $('input:radio[name=flowCache]:checked').val(),
				attachPriv : $('input:radio[name=attachPriv]:checked').val(),
				printFlag : $('input:radio[name=printFlag]:checked').val(),
				queryPriv : $("#queryPriv").attr("data-value"),
				printField:getCheckBoxValue("printField"),
				managePriv : $("#managePriv").attr("data-value")
			},
			success : function(data) {
				if (data.status == 500) {
					console.log(data.msg);
				} else {
					top.layer.msg(data.msg);
					window.location.reload();
				}
			}
		});
		}
	});
	$("#delbut").unbind("click").click(function() {
		if (confirm("确定删除当前流程吗？")) {
			$.ajax({
				url : "/set/bpmset/deleteBpmFlow",
				type : "post",
				dataType : "json",
				data : {
					flowId : $("#flowId").val()
				},
				success : function(data) {
					if (data.status == 500) {
						console.log(data.msg);
					} else {
						top.layer.msg(data.msg);
					}
				}
			});
		} else {
			return;
		}
	});
	$("#designerbut").unbind("click").click(function() {
		designflow();
	});
	$("#flowSort").unbind("click").click(function(e) {
		e.stopPropagation();
		$("#menuContent").css({
			"width" : $(this).outerWidth() + "px"
		}).slideDown(200);
	});
	$("#menuContent").unbind("click").click(function(e) {
		e.stopPropagation();
	});
	
	$("#form").unbind("click").click(function() {
		$("#menuContent").hide();
		$("#menuContent1").hide();
	});

	$("#formId").unbind("click").click(function(e) {
		e.stopPropagation();
		$("#menuContent1").css({
			"width" : $(this).outerWidth() + "px"
		}).slideDown(200);
	});
	$("#menuContent1").unbind("click").click(function(e) {
		e.stopPropagation();
	});
	$("#form").bootstrapValidator('resetForm');
});
function zTreeOnClick(event, treeId, treeNode) {
	if (treeNode.isParent == false) {
		$("#tablediv").hide();
		$("#infodiv").show();
		document.getElementById("form").reset();
		$.ajax({
			url : "/ret/bpmget/getBpmFlow",
			type : "post",
			dataType : "json",
			data : {
				flowId : treeNode.bpmSortId
			},
			success : function(data) {
				if (data.status == 200) {
					for (name in data.list) {
						if (name == "flowSort") {
							$.ajax({
								url : "/ret/bpmget/getBpmSortById",
								type : "post",
								dataType : "json",
								data : {
									bpmSortId : data.list[name]
								},
								success : function(data) {
									if (data.status == 200) {
										$("#flowSort").val(
												data.list.bpmSortName);
									} else {
										console.log(data.msg);
									}
								}
							});
							$("#flowSort").attr("data-value",data.list[name]);
						} else if (name == "autoStyle") {
							$("input:radio[name='autoStyle'][value='"+ data.list[name] + "']").attr("checked", true);
						} else if (name == "flowCache") {
							$("input:radio[name='flowCache'][value='"+ data.list[name] + "']").attr("checked", true);
						} else if (name == "printFlag") {
							$("input:radio[name='printFlag'][value='"+ data.list[name] + "']").attr("checked", true);
						} else if (name == "attachPriv") {
							$("input:radio[name='attachPriv'][value='"+ data.list[name] + "']").attr("checked", true);
						}else if(name=="formId")
						{
							$.ajax({
								url : "/ret/bpmget/getBpmForm",
								type : "post",
								dataType : "json",
								data : {
									formId : data.list["formId"]
								},
								success : function(res) {
									if (res.status == "200") {
										$("#formId").val(res.list.formTitle);
									} else if(res.status == "100")
									{
										top.layer.msg(res.status);
									}
									else{
										console.log(res.msg);
									}
								}
							});
							$("#formId").attr("data-value",data.list.fromId);
							getFormField(data.list[name],data.list.printField)
						}else if(name=="queryPriv")
						{
							$("#queryPriv").attr("data-value", data.list[name]);
							$("#queryPriv").val(getUserNameByStr(data.list[name]));
						}else if(name=="managePriv")
						{
							$("#managePriv").attr("data-value", data.list[name]);
							$("#managePriv").val(getUserNameByStr(data.list[name]));
						}else
						{
							$("#" + name).val(data.list[name]);
						}
					}
				} else if (data.status == 100) {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		});
		$("#createbut").hide();
		$("#updatabut").show();
		$("#delbut").show();
		$("#designerbut").show();
	}else
	{
		$("#infodiv").hide();
		$("#tablediv").show();
		$("#myTable").bootstrapTable("destroy");
		query(treeNode.bpmSortId);
	}
}
function designflow() {
	window.open('/app/core/bpm/processdesigner?flowId=' + $("#flowId").val());
}

function getFormField(formId,printField)
{
	$.ajax({
		url : "/ret/bpmget/getBpmFormFieldByFormId",
		type : "post",
		dataType : "json",
		data : {
			formId : formId
		},
		success : function(data) {
			if (data.status == "200") {
				var html="";
				for(var i=0;i<data.list.length;i++)
					{
					if(data.list[i].name!='ID'&&data.list[i].name!='RUN_ID'&&data.list[i].name!='ORG_ID')
						{
						var name = toCamel(data.list[i].name);
						html+="<label><input type=\"checkbox\" value=\""+name+"\" class=\"colored-blue\" name=\"printField\"";
						if(((","+printField+",").indexOf((","+name+",")))>=0)
							{
							html+=" checked=\"checked\"";
							}
						html+="><span class=\"text\">"+data.list[i].title+"</span></label>";
						}
					}
			$("#printFieldDiv").html(html);
			}else if(data.status=="100")
			{
				tap.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
		});
}
function query(flowSort)
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/bpmget/getAllBpmFlowListByFlowSort?flowSort='+flowSort,
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
	      search: false,//启用搜索
	      showColumns: true,//是否显示 内容列下拉框
	      showRefresh: true,//显示刷新按钮
	      idField: 'flowId',//key值栏位
	      clickToSelect: true,//点击选中checkbox
	      pageList : [10, 20, 30, 50],//可选择单页记录数
	      queryParams:queryParams,
	      columns: [
	     {
	    	field: 'num',
			title: '序号',//标题  可不加
			width:'50px',
			formatter: function (value, row, index) {
					return index+1;
				}
	      },
	      {
	       field: 'flowName',
	       title: 'BPM流程名称',
	       sortable : true,
	       width:'150px'
	      },
	      {
			field: 'formTitle',
			   width:'150px',
			   title: '相关表单名称',
			   formatter:function(value,row,index){
				   return "<a href=\"javascript:void(0);template('"+row.formId+"');\" style=\"cursor: pointer;\">"+value+"</a><a style=\"float:right;\" href=\"javascript:void(0);designform('"+row.formId+"');\" class=\"btn btn-sky shiny btn-xs\">设计表单</a>";
			   }
			},
			{
		       field: 'tableName',
		       title: '数据表名',
		       width:'50px'
	      },
	      {
	    	  field: 'version',
		       visible: false,
		       title: '当前版本标识',
		       width:'50px'
	      },
	      {
		       field: 'docNumRule',
		       title: 'BPM文号',
		       visible: false,
		       width:'150px'
	      },{
		       field: 'status',
		       title: '状态',
		       width:'50px',
		       formatter:function(value,row,index){
		    	   if(value=="1")
		    		{
		    		   return "已停用";
		    		}else
		    		{
		    			return "使用中";
		    		}
		       }
	      },
	      {
		       field: 'createUserName',
		       width:'50px',
		       visible: false,
		       title: '创建人'
		   },
		   {
		       field: 'createTime',
		       width:'100px',
		       title: '创建时间'
		   },
	      {
	       field: 'opt',
	       width:'180px',
	       title: '操作',
	       align:'center',
    	   formatter:function(value,row,index){
                return createOptBtn(row.flowId,row.status);
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
function createOptBtn(flowId,status)
{
var html="<a href=\"javascript:void(0);clearData('"+flowId+"')\" class=\"btn btn-sky btn-xs\" >初始化</a>&nbsp;&nbsp;" +
		"<a href=\"javascript:void(0);clone('"+flowId+"')\" class=\"btn btn-success btn-xs\" >克隆</a>&nbsp;&nbsp;";
if(status=="1")
{
	html+="<a href=\"javascript:void(0);setStatusFlow('"+flowId+"','0')\" class=\"btn btn-primary btn-xs\">启用</a>&nbsp;&nbsp;";
}else
{
	html+="<a href=\"javascript:void(0);setStatusFlow('"+flowId+"','1')\" class=\"btn btn-maroon btn-xs\">停用</a>&nbsp;&nbsp;";
}
html+="<a href=\"/ret/bpmget/getBpmFlowXmlFile?flowId="+flowId+"\" class=\"btn btn-palegreen btn-xs\">导出</a>";
return html;
}

function importFlow()
{
	$.ajaxFileUpload({
        url:'/set/bpmset/importFlowXml', //上传文件的服务端
        secureuri:false,  //是否启用安全提交
        async:false,
        dataType: 'json',   //数据类型  
        fileElementId:'file', //表示文件域ID
        success: function(data,status){
       	 	if(data.status==200)
       	 		{
       	 		top.layer.msg(data.msg);
       	 		location.reload()
       	 		}else if(data.status==100)
   				{
   					top.layer.msg(data.msg);
   				}else
       	 			{
       	 			console.log(data.msg);
       	 			}
        },
   //提交失败处理函数
        error: function (data,status,e){
       	 top.layer.msg("文件上传出错!请检查文件格式!");
       	console.log(data.msg);
        }
   });
}



function clearData(flowId)
{
	if(window.confirm('历史数据会丢失!您确认初始化该流程吗?')){
	$.ajax({
		url : "/set/bpmset/clearBpmFlowData",
		type : "post",
		dataType : "json",
		data : {
			flowId : flowId
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);	
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
		return;
	}
}


function clone(flowId)
{
	if(window.confirm('您确要需要克隆该流程吗?')){
		$.ajax({
			url : "/set/bpmset/cloneBpmFlow",
			type : "post",
			dataType : "json",
			data : {
				flowId : flowId
			},
			success : function(data) {
				if(data.status=="200")
				{
					top.layer.msg(data.msg);	
					$("#myTable").bootstrapTable("refresh");
					$.ajax({
						url : "/ret/bpmget/getBpmFlowSortTree",
						type : "post",
						dataType : "json",
						data : {
							bpmSortId : "0"
						},
						success : function(data) {
							$.fn.zTree.init($("#tree"), setting, data);// 初始化树节点时，添加同步获取的数据
						}
					});
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
			return;
		}
}

function designform(formId)
{
	window.open('/app/core/bpm/designerform?formId='+formId);
}
function template(formId)
{
window.open("/app/core/bpm/bpmFormTemplate?formId="+formId);
}

function setStatusFlow(flowId,status)
{
	if(window.confirm('您确定要停用当前流程吗?')){
	$.ajax({
		url : "/set/bpmset/updateBpmFlow",
		type : "post",
		dataType : "json",
		data : {
			flowId:flowId,
			status:status
		},
		success : function(data) {
			if(data.status=="200")
			{
				$("#myTable").bootstrapTable("refresh");
				top.layer.msg(data.msg);
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
}