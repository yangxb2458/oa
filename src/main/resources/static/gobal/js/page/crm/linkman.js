function createLinkMan()
{
	$.ajax({
		url : "/set/crmset/createCrmLinkMan",
		type : "post",
		dataType : "json",
		data:{
			customerId:$("#customerId").val(),
			linkName:$("#linkName").val(),
			sex:$('input:radio[name="sex"]:checked').val(),
			birthday:$("#birthday").val(),
			mobile:$("#mobile").val(),
			email:$("#email").val(),
			deptName:$("#deptName").val(),
			postion:$("#postion").val(),
			tel:$("#tel").val(),
			fax:$("#fax").val(),
			hobby:$("#hobby").val(),
			address:$("#address").val(),
			decisionMaker:$('input:radio[name="decisionMaker"]:checked').val()
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

function query()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/crmget/getCrmLinkManAllList',
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
	      idField: 'linkManId',//key值栏位
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
	       field: 'cnName',
	       title: '客户名称',
	       sortable : true,
	       width:'200px',
	   		formatter: function (value, row, index) {
	   			if(value==""||value==null)
	   				{
	   				return "<a href='/app/core/crm/customerdetails?customerId="+row.customerId+"'>"+row.enName+"</a>";
	   				}else
	   					{
	   					return "<a href='/app/core/crm/customerdetails?customerId="+row.customerId+"'>"+value+"</a>";
	   					}
	   			}
	      },
	      {
			field: 'linkName',
			   width:'50px',
			   title: '姓名'
			},
			{
				field: 'sex',
				width:'50px',
				title: '性别',
		   		formatter: function (value, row, index) {
		   			if(value=="1")
		   				{
		   					return "男";
		   				}else if(value=="2")
		   				{
		   					return "女";
		   				}else
		   					{
		   					return "其它";
		   					}
		   			}
			},
			{
		       field: 'deptName',
		       title: '部门',
		       width:'50px'
	      },
	      {
		       field: 'postion',
		       width:'100px',
		       title: '职务'
		   },
		   {
		       field: 'mobile',
		       width:'100px',
		       title: '移动电话'
		   },
		   {
		       field: 'email',
		       width:'100px',
		       title: '电子邮件'
		   },
	      {
	       field: 'opt',
	       title: '操作',
	       width:'200px',
	       align:'center',
    	   formatter:function(value,row,index){
                return createOptBtn(row.linkManId,row.email);
            }
	      }],
	      onClickCell: function (field, value, row, $element) {
	      //alert(row.SystemDesc);
	    },
	    responseHandler:function(res){
	    	if(res.status=="500")
	    		{
	    		console.log(res.msg);
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

function createOptBtn(linkManId,email)
{
var html="<a href=\"javascript:void(0);detail('"+linkManId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;" +
		 "<a href=\"javascript:void(0);edit('"+linkManId+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" +
		 "<a href=\"javascript:void(0);del('"+linkManId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;";
if(email){
	html+="<a href=\"javascript:void(0);sendWebMail('"+linkManId+"','"+email+"')\" class=\"btn btn-blue btn-xs\" >发送邮件</a>";
}
return html;
}
function edit(linkManId)
{
	//window.location.href="/app/core/crm/createLinkman?linkManId="+linkManId;
	open("/app/core/crm/createLinkman?linkManId="+linkManId,"_self");
}

function sendWebMail(linkManId,email)
{
	document.getElementById("form2").reset();
	$("#content").code("");
	$("#crmattach").attr("data_value","");
	$("#webMailModal").modal("show");
	$("#show_crmattach").html("");
	$("#webMail").val(email);
	$(".js-sendwebmail").unbind("click").click(function(){
		$.ajax({
			url : "/set/crmset/sendWebMail",
			type : "post",
			dataType : "json",
			data:{
				subject:$("#subject").val(),
				to:$("#webMail").val(),
				content:$("#content").code(),
				sendServiceType:$("input:radio[name='sendServiceType']:checked").val(),
				attachId:$("#crmattach").attr("data_value")
				},
			success : function(data) {
				if(data.status==200){
					top.layer.msg(data.msg);
				}else if(data=="100")
					{
					top.layer.msg(data.msg);
					}else
					{
					console.log(data.msg);
					}
			}
	});
		$("#webMailModal").modal("hide");
	});
}

function update()
{
	$.ajax({
		url : "/set/crmset/updateCrmLinkMan",
		type : "post",
		dataType : "json",
		data:{
			linkManId:linkManId,
			linkName:$("#linkName").val(),
			sex:$('input:radio[name="sex"]:checked').val(),
			birthday:$("#birthday").val(),
			mobile:$("#mobile").val(),
			email:$("#email").val(),
			deptName:$("#deptName").val(),
			postion:$("#postion").val(),
			tel:$("#tel").val(),
			fax:$("#fax").val(),
			hobby:$("#hobby").val(),
			address:$("#address").val(),
			decisionMaker:$('input:radio[name="decisionMaker"]:checked').val()
		},
		success : function(data) {
			if(data.status==200){
				top.layer.msg(data.msg);
			}else if(data=="100")
				{
				top.layer.msg(data.msg);
				}else
				{
				console.log(data.msg);
				}
		}
});
}
function del(linkManId)
{
	 if(confirm("确定删除当前联系人吗？"))
	    {
	$.ajax({
		url : "/set/crmset/deleteCrmLinkMan",
		type : "post",
		dataType : "json",
		data:{
			linkManId:linkManId
		},
		success : function(data) {
			if(data.status==200){
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
function detail(linkManId)
{
	$.ajax({
		url : "/ret/crmget/getCrmLinkMan",
		type : "post",
		dataType : "json",
		data:{
			linkManId:linkManId
		},
		success : function(data) {
			if(data.status==200){
				for(var name in data.list)
					{
					if(name=="sex")
						{
							$(".js-"+name).html(data.list[name]=="0"?"其它":(data.list[name]="1"?"男":"女"));
						}else if(name=="decisionMaker")
						{
							if(data.list[name])
								{
								$(".js-"+name).html(data.list[name]=="1"?"不是决策人":"是决策人");
								}else
								{
									$(".js-"+name).html("未知");
								}
						}else{
							$(".js-"+name).html(data.list[name]);
							}
					}
				$("#linkmandetail").modal("show");
			}else
				{
				console.log(data.msg);
				}
		}
	});
	
}

