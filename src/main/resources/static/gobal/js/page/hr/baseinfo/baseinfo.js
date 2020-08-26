var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/hrget/getHrUserInfoDepartmentTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "deptId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onClick : zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "deptId",
			pIdKey : "orgLeaveId",
			rootPId : "0"
		},
		key : {
			name : "deptName"
		}
	}
};

$(function() {
	jeDate("#employedTimeQuery", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate(),
	});
	query();
	var topNode = [ {
		deptName : "全部",
		orgLeaveId : '',
		isParent : "true",
		deptId : "",
		icon : "/gobal/img/org/org.png"
	} ];
	var zTreeObj = $.fn.zTree.init($("#tree"), setting, topNode);
	var nodes = zTreeObj.getNodes();
	for (var i = 0; i < nodes.length; i++) {
		zTreeObj.expandNode(nodes[i], true, false, false);//默认展开第一级节点
	}
	$("#deptIdQuery").unbind("click").click(function(e) {
		e.stopPropagation();
		$("#menuContent").css({
			"width" : $(this).outerWidth() + "px",
			"left":(document.getElementById("deptIdQuery").offsetLeft-40)+ "px"
		}).slideDown(200);
	});

	$("body").unbind("click").click(function() {
		$("#menuContent").hide();
	});

	$("#menuContent").unbind("click").click(function(e) {
		e.stopPropagation();
	});
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
	QuerySelect("workStatus");
	$(".js-back-btn").unbind("click").click(function(){
		$("#baseinfodiv").hide();
		$("#baseinfolistdiv").show();
	})
	$(".js-auto-select").each(function(){
		var module = $(this).attr("module");
		createAutoSelect(module);
	})
	getWagesLevelListForSelect();
	jeDate("#birthDay", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate(),
	});
	jeDate("#joinPartyTime", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate(),
	});
	jeDate("#employedTime", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate(),
	});
	jeDate("#graduationTime", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate(),
	});
	 $('#resume').summernote({ height:300 });
	 getAttendType("attendType");
	 
});


function zTreeOnClick(event, treeId, treeNode)
{
	var zTree = $.fn.zTree.getZTreeObj("tree");
	var nodes = zTree.getSelectedNodes();
	var v = [];
	var vid = [];
	nodes.sort(function compare(a, b) {
		return a.id - b.deptId;
	});
	for(var i = 0, l = nodes.length; i < l; i++) {
		v.push(nodes[i].deptName);
		vid.push(nodes[i].deptId);
	}
	var idem = $("#deptIdQuery");
	idem.attr("data-value", vid.join(","));
	idem.val(v.join(","));
}

function query()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/hrget/getHrUserInfoByBeptIdInWorkList',
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
	      idField: 'userId',//key值栏位
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
	       field: 'userName',
	       title: '人员姓名',
	       sortable : true,
	       width:'80px'
	      },
	      {
	       field: 'deptName',
	       width:'100px',
	       title: '部门'
	     },
	     {
		   field: 'sex',
		   width:'50px',
		   title: '性别',
		   formatter:function(value,row,index){
			   if(value=="0")
				   {
				   	return "男";
				   }else if(value=="1")
					{
					   return "女";
					}else if(value=="3")
					{
						return "其它";
					}
           }
		  },
		  {
			field: 'employedTime',
			width:'100px',
			title: '入职时间'
		 },
	     {
	       field: 'workStatus',
	       title: '在职状态',
	       width:'100px',
	       formatter:function(value,row,index){
	    	   if(value=="01")
	    		{
	    		   return "在职";
	    		}else if(value=="02")
	    		{
	    			return "辞职";
	    		}else if(value=="03")
	    		{
	    			return "离休";
	    		}else if(value=="04")
	    		{
	    			return "退休";
	    		}else if(value=="05")
	    		{
	    			return "借调";
	    		}else if(value=="06")
	    		{
	    			return "开除";
	    		}else
	    		{
	    			return "未知";
	    		}
           }
	      },
	      {
		       field: 'mobileNo',
		       title: '手机号码',
		       width:'100px'
		   },
		   {
		       field: 'leaveId',
		       title: '职务',
		       width:'100px',
		       formatter:function(value,row,index){
	                return getHrUserLevelByStr(value);
	            }
		   },
	      {
	       field: 'opt',
	       title: '操作',
	       align:'center',
	       width:'180px',
    	   formatter:function(value,row,index){
                return createOptBtn(row.userId);
            }
	      }],
	      onClickCell: function (field, value, row, $element) {
	      //alert(row.SystemDesc);
	    },
	    responseHandler:function(res){
	    	if(res.status=="500")
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
        deptId:$("#deptIdQuery").attr("data-value"),
        employedTime:$("#employedTimeQuery").val(),
        workStatus:$("#workStatusQuery").val(),
        staffCardNo:$("#staffCardNoQuery").val()
    };
    return temp;
};

function createOptBtn(userId)
{
	var html="<a href=\"javascript:void(0);edit('"+userId+"')\" class=\"btn btn-magenta btn-xs\">编辑</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);deleteUserInfo('"+userId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;";
		html+="<a href=\"javascript:void(0);quitCom('"+userId+"')\" class=\"btn btn-success btn-xs\" >离职</a>&nbsp;&nbsp;";
		html+="<a href=\"javascript:void(0);details('"+userId+"')\" class=\"btn btn-primary btn-xs\" >详情</a>";
	return html;
	}

function edit(userId)
{
$("#baseinfolistdiv").hide();
$("#baseinfodiv").show();
$.ajax({
	url : "/ret/hrget/getHrUserInfoById",
	type : "post",
	dataType : "json",
	data:{
		userId:userId
	},
	success : function(data) {
		if(data.status=="200")
		{
			var userInfo = data.list;
			for(var id in userInfo)
			{
				if(id=="attach")
				{
					$("#show_hrattach").html("");
					$("#hrattach").attr("data_value", data.list.attach);
					createAttach("hrattach", 4);
				}else if(id=="accountId")
				{
					$("#"+id).val(getUserNameByStr(userInfo[id]));
					$("#"+id).attr("data-value",userInfo[id]);
				}else if(id=="deptId")
				{
					$("#"+id).attr("data-value",userInfo[id]);
					$("#"+id).val(getHrDeptNameByStr(userInfo[id]));
				}else if(id=="leaveId")
				{
					$("#"+id).attr("data-value",userInfo[id]);
					$("#"+id).val(getHrUserLevelByStr(userInfo[id]));
				}else if(id=="photos")
				{
					$("#file_img").attr("src","/sys/file/getStaticImg?module=hrphotos&fileName="+userInfo[id]);
					$("#file").attr("data-value",userInfo[id])
				}else if(id=="resume")
				{
					$("#resume").code(userInfo[id]);
				}else
				{
					$("#"+id).val(userInfo[id]);
				}
			}
			$(".js-add-save").unbind("click").click(function(){
				updateUserInfo(userId);
			})
		}else if(data.status=="100")
		{
			top.layer.msg(data.msg);
		}else if(data.status=="500")
		{
			console.log(data.msg);
		}
	}
});
}

function updateUserInfo(userId)
{
	$.ajax({
		url : "/set/hrset/updateHrUserInfo",
		type : "post",
		dataType : "json",
		data:{
			userId:userId,
			sortNo:$("#sortNo").val(),
			accountId:$("#accountId").attr("data-value"),
			deptId:$("#deptId").attr("data-value"),
			leaveId:$("#leaveId").attr("data-value"),
			userName:$("#userName").val(),
			userNameEn:$("#userNameEn").val(),
			beforeUserName:$("#beforeUserName").val(),
			sex:$("#sex").val(),
			workNo:$("#workNo").val(),
			staffNo:$("#staffNo").val(),
			staffCardNo:$("#staffCardNo").val(),
			birthDay:$("#birthDay").val(),
			levelType:$("#levelType").val(),
			animal:$("#animal").val(),
			nativePlace:$("#nativePlace").val(),
			address:$("#address").val(),
			bloodType:$("#bloodType").val(),
			nationalty:$("#nationalty").val(),
			maritalStatus:$("#maritalStatus").val(),
			health:$("#health").val(),
			politicalStatus:$("#politicalStatus").val(),
			joinPartyTime:$("#joinPartyTime").val(),
			staffType:$("#staffType").val(),
			staffAddress:$("#staffAddress").val(),
			workType:$("#workType").val(),
			wagesLevel:$("#wagesLevel").val(),
			occupation:$("#occupation").val(),
			employedTime:$("#employedTime").val(),
			workJob:$("#workJob").val(),
			workStatus:$("#workStatus").val(),
			phone:$("#phone").val(),
			attendType:$("#attendType").val(),
			persentPosition:$("#persentPosition").val(),
			workLeave:$("#workLeave").val(),
			mobileNo:$("#mobileNo").val(),
			wxNo:$("#wxNo").val(),
			email:$("#email").val(),
			homeAddress:$("#homeAddress").val(),
			qq:$("#qq").val(),
			otherContact:$("#otherContact").val(),
			bank:$("#bank").val(),
			bankAccount:$("#bankAccount").val(),
			highsetShool:$("#highsetShool").val(),
			highsetDegree:$("#highsetDegree").val(),
			graduationTime:$("#graduationTime").val(),
			graduationShool:$("#graduationShool").val(),
			major:$("#major").val(),
			skills:$("#skills").val(),
			otherLanguage:$("#otherLanguage").val(),
			otherLanguageLevel:$("#otherLanguageLevel").val(),
			cretificate:$("#cretificate").val(),
			surety:$("#surety").val(),
			insure:$("#insure").val(),
			bodyExamim:$("#bodyExamim").val(),
			remark:$("#remark").val(),
			resume:$("#resume").code(),
			photos:$("#file").attr("data-value"),
			attach:$("#hrattach").attr("data_value")
		},
		success : function(data) {
			if(data.status=="200")
			{
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

function getWagesLevelListForSelect()
{
	$.ajax({
		url : "/ret/hrget/getWagesLevelListForSelect",
		type : "post",
		dataType : "json",
		async : false,
		success : function(data) {
			if(data.status=="200")
			{
				var html="<option value=''>请选择</option>";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<option value=\""+data.list[i].wagesId+"\">"+data.list[i].title+"</option>";
				}
				$("#wagesLevel").html(html);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
			}
		})
}

function deleteUserInfo(userId)
{
	if(confirm("确定删除当前人员档案？"))
    {
	$.ajax({
		url : "/set/hrset/deleteHrUserInfo",
		type : "post",
		dataType : "json",
		data:{userId:userId},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#myTable").bootstrapTable("refresh");
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

function quitCom(userId)
{
	$.ajax({
		url : "/set/hrset/updateHrUserInfo",
		type : "post",
		dataType : "json",
		data:{userId:userId,workStatus:"02"},
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
}

function details(userId)
{
	window.open("/app/core/hr/userinfodetails?userId="+userId);
}
