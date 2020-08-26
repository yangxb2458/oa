$(function(){
	showEmailBox();
	getEmailCount();
$(".js-saveBox").unbind("click").click(function(){
	createBox();
	});
});
function getEmailCount()
{
	$.ajax({
		url : "/ret/oaget/getEmailCount",
		type : "post",
		dataType : "json",
		data:{},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				$(".js-delCount").html(data.list.delCount);
				$(".js-boxCount").html(data.list.boxCount);
				$(".js-starCount").html(data.list.starCount);
				$(".js-sendCount").html(data.list.sendCount);
				$(".js-draftCount").html(data.list.draftCount);
				}
		}
	})
}

function getEmail(boxId)
{
	$("#myTable").bootstrapTable({
	    url: '/ret/oaget/getMyEmailAll?boxId='+boxId,
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
	    sortOrder: "desc",
	    search: true,//启用搜索
	    showColumns: false,//是否显示 内容列下拉框
	    showRefresh: true,//显示刷新按钮
	    idField: 'emailId',//key值栏位
	    clickToSelect: false,//点击选中checkbox
	    pageList : [10, 20, 30, 50],//可选择单页记录数
	    queryParams:queryParams,
	    columns: [ {
		     field: '',
		     width:'1703px',
		     formatter: function (value, row, index) {
		    	 var html='<div class="list-item '+((row.readTime!=''&&row.readTime!=undefined)?'read':'unread')+'"  style="background:transparent; ">'+
					'           <div class="item-check">'+
					'               <label>'+
					'                   <input type="checkbox" name="emailcheckbox" value="'+row.emailId+'" data-value="'+row.bodyId+'">'+
					'                   <span class="text"></span>'+
					'               </label>'+
					'           </div>'+
					'           <div class="item-star" style="cursor: pointer;">'+
					'               <a data-value="'+row.emailId+'" data-star="'+row.star+'"  onclick="setEmailStar(this);" class="'+(row.star=='1'?'stared':'')+'">'+
					'                   <i class="fa fa-star"></i>'+
					'               </a>'+
					'           </div>'+
					'           <div class="item-sender">'+
					'               <a href="/app/core/oa/emaildetails?emailId='+row.emailId+'" class="col-name">'+row.fromUserName+'</a>'+
					'           </div>'+
					'           <div class="item-subject" style="white-space: nowrap;">'+
					'               <span class="label label-palegreen">'+((row.tags!=''&&row.tags!=undefined)?row.tags:'')+'</span>'+
					'               <a href="/app/core/oa/emaildetails?emailId='+row.emailId+'"><span style="color: dodgerblue;font-size: 14px;font-weight: 800;">'+row.subject+'</span><span style="margin-left:20px;">'+row.subheading+'</span></a>'+
					'           </div>'+
					'           <span style="margin-right:20px;line-height:34px;font-weight: normal;float: right">'+row.sendTime+
					'           </span>'+
					'           <span style="margin-right:20px;line-height:34px;font-weight: normal;float: right">'+
					'              '+((row.attach!=''&&row.attach!=undefined)?'<i class="fa fa-paperclip"></i>':'')+'</a>'+
					'           </span>'+
					'       </div>';
		    	 return html;
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
	if(boxId)
		{
		$("#box_id_"+boxId).addClass("active");
		}
	}

function getMyDelEmailAll()
{
	$("#myTable").bootstrapTable({
	    url: '/ret/oaget/getMyDelEmailAll',
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
	    sortOrder: "desc",
	    search: true,//启用搜索
	    showColumns: false,//是否显示 内容列下拉框
	    showRefresh: true,//显示刷新按钮
	    idField: 'emailId',//key值栏位
	    clickToSelect: false,//点击选中checkbox
	    pageList : [10, 20, 30, 50],//可选择单页记录数
	    queryParams:queryParams,
	    columns: [ {
		     field: '',
		     width:'1703px',
		     formatter: function (value, row, index) {
		    	 var html='<div class="list-item '+((row.readTime!=''&&row.readTime!=undefined)?'read':'unread')+'"  style="background:transparent; ">'+
					'           <div class="item-check">'+
					'               <label>'+
					'                   <input type="checkbox" name="emailcheckbox" value="'+row.emailId+'" data-value="'+row.bodyId+'">'+
					'                   <span class="text"></span>'+
					'               </label>'+
					'           </div>'+
					'           <div class="item-star" style="cursor: pointer;">'+
					'               <a data-value="'+row.emailId+'" data-star="'+row.star+'"  onclick="setEmailStar(this);" class="'+(row.star=='1'?'stared':'')+'">'+
					'                   <i class="fa fa-star"></i>'+
					'               </a>'+
					'           </div>'+
					'           <div class="item-sender">'+
					'               <a href="/app/core/oa/emaildetails?emailId='+row.emailId+'" class="col-name">'+row.fromUserName+'</a>'+
					'           </div>'+
					'           <div class="item-subject" style="white-space: nowrap;">'+
					'               <span class="label label-palegreen">'+((row.tags!=''&&row.tags!=undefined)?row.tags:'')+'</span>'+
					'               <a href="/app/core/oa/emaildetails?emailId='+row.emailId+'"><span style="color: dodgerblue;font-size: 14px;font-weight: 800;">'+row.subject+'</span><span style="margin-left:20px;">'+row.subheading+'</span></a>'+
					'           </div>'+
					'           <span style="margin-right:20px;line-height:34px;font-weight: normal;float: right">'+row.sendTime+
					'           </span>'+
					'           <span style="margin-right:20px;line-height:34px;font-weight: normal;float: right">'+
					'              '+((row.attach!=''&&row.attach!=undefined)?'<i class="fa fa-paperclip"></i>':'')+'</a>'+
					'           </span>'+
					'       </div>';
		    	 return html;
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
function getStarEmail()
{
	$("#myTable").bootstrapTable({
	    url: '/ret/oaget/getStarEmail',
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
	    sortOrder: "desc",
	    search: true,//启用搜索
	    showColumns: false,//是否显示 内容列下拉框
	    showRefresh: true,//显示刷新按钮
	    idField: 'emailId',//key值栏位
	    clickToSelect: false,//点击选中checkbox
	    pageList : [10, 20, 30, 50],//可选择单页记录数
	    queryParams:queryParams,
	    columns: [ {
		     field: '',
		     width:'1703px',
		     formatter: function (value, row, index) {
		    	 var html='<div class="list-item '+((row.readTime!=''&&row.readTime!=undefined)?'read':'unread')+'"  style="background:transparent; ">'+
					'           <div class="item-check">'+
					'               <label>'+
					'                   <input type="checkbox" name="emailcheckbox" value="'+row.emailId+'" data-value="'+row.bodyId+'">'+
					'                   <span class="text"></span>'+
					'               </label>'+
					'           </div>'+
					'           <div class="item-star" style="cursor: pointer;">'+
					'               <a data-value="'+row.emailId+'" data-star="'+row.star+'"  onclick="setEmailStar(this);" class="'+(row.star=='1'?'stared':'')+'">'+
					'                   <i class="fa fa-star"></i>'+
					'               </a>'+
					'           </div>'+
					'           <div class="item-sender">'+
					'               <a href="/app/core/oa/emaildetails?emailId='+row.emailId+'" class="col-name">'+row.fromUserName+'</a>'+
					'           </div>'+
					'           <div class="item-subject" style="white-space: nowrap;">'+
					'               <span class="label label-palegreen">'+((row.tags!=''&&row.tags!=undefined)?row.tags:'')+'</span>'+
					'               <a href="/app/core/oa/emaildetails?emailId='+row.emailId+'"><span style="color: dodgerblue;font-size: 14px;font-weight: 800;">'+row.subject+'</span><span style="margin-left:20px;">'+row.subheading+'</span></a>'+
					'           </div>'+
					'           <span style="margin-right:20px;line-height:34px;font-weight: normal;float: right">'+row.sendTime+
					'           </span>'+
					'           <span style="margin-right:20px;line-height:34px;font-weight: normal;float: right">'+
					'              '+((row.attach!=''&&row.attach!=undefined)?'<i class="fa fa-paperclip"></i>':'')+
					'           </span>'+
					'       </div>';
		    	 return html;
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
function getsendBoxEmail()
{
	$("#myTable").bootstrapTable({
	    url: '/ret/oaget/getsendBoxEmail',
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
	    sortOrder: "desc",
	    search: true,//启用搜索
	    showColumns: false,//是否显示 内容列下拉框
	    showRefresh: true,//显示刷新按钮
	    idField: 'bodyId',//key值栏位
	    clickToSelect: false,//点击选中checkbox
	    pageList : [10, 20, 30, 50],//可选择单页记录数
	    queryParams:queryParams,
	    columns: [ {
		     field: '',
		     width:'1703px',
		     formatter: function (value, row, index) {
		    	 var html='<div class="list-item read"  style="background:transparent; ">'+
					'           <div class="item-check">'+
					'               <label>'+
					'                   <input type="checkbox" name="emailbodycheckbox" value="'+row.bodyId+'">'+
					'                   <span class="text"></span>'+
					'               </label>'+
					'           </div>'+
					'           <div class="item-sender">'+
					'               <a href="/app/core/oa/email?view=reply&flag=0&bodyId='+row.bodyId+'" class="col-name">'+((row.webEmailTo!=''&&row.webEmailTo!=undefined)?row.webEmailTo:getUserNameByStr(row.toId))+'</a>'+
					'           </div>'+
					'           <div class="item-subject" style="white-space: nowrap;">'+
					'               <span class="label label-palegreen">'+((row.tags!=''&&row.tags!=undefined)?row.tags:'')+'</span>'+
					'               <a href="/app/core/oa/email?view=reply&flag=0&bodyId='+row.bodyId+'"><span style="color: dodgerblue;font-size: 14px;font-weight: 800;">'+row.subject+'</span><span style="margin-left:20px;">'+row.subheading+'</span></a>'+
					'           </div>'+
					'           <span style="margin-right:20px;line-height:34px;font-weight: normal;float: right">'+row.sendTime+
					'           </span>'+
					'           <span style="margin-right:20px;line-height:34px;font-weight: normal;float: right">'+
					'              '+((row.attach!=''&&row.attach!=undefined)?'<i class="fa fa-paperclip"></i>':'')+'</a>'+
					'           </span>'+
					'       </div>';
		    	 return html;
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

function getDraftBoxEmail()
{
	$("#myTable").bootstrapTable({
	    url: '/ret/oaget/getDraftBoxEmail',
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
	    sortOrder: "desc",
	    search: true,//启用搜索
	    showColumns: false,//是否显示 内容列下拉框
	    showRefresh: true,//显示刷新按钮
	    idField: 'bodyId',//key值栏位
	    clickToSelect: false,//点击选中checkbox
	    pageList : [10, 20, 30, 50],//可选择单页记录数
	    queryParams:queryParams,
	    columns: [ {
		     field: '',
		     width:'1703px',
		     formatter: function (value, row, index) {
		    	 var html='<div class="list-item read"  style="background:transparent; ">'+
					'           <div class="item-check">'+
					'               <label>'+
					'                   <input type="checkbox" name="emailbodycheckbox">'+
					'                   <span class="text"></span>'+
					'               </label>'+
					'           </div>'+
					'           <div class="item-sender">'+
					'               <a href="/app/core/oa/email?view=reply&flag=1&bodyId='+row.bodyId+'" class="col-name">'+((row.webEmailTo!=''&&row.webEmailTo!=undefined)?row.webEmailTo:getUserNameByStr(row.toId))+'</a>'+
					'           </div>'+
					'           <div class="item-subject" style="white-space: nowrap;">'+
					'               <span class="label label-palegreen">'+((row.tags!=''&&row.tags!=undefined)?row.tags:'')+'</span>'+
					'               <a href="/app/core/oa/email?view=reply&bodyId='+row.bodyId+'"><span style="color: dodgerblue;font-size: 14px;font-weight: 800;">'+row.subject+'</span><span style="margin-left:20px;">'+row.subheading+'</span></a>'+
					'           </div>'+
					'           <span style="margin-right:20px;line-height:34px;font-weight: normal;float: right">'+row.sendTime+
					'           </span>'+
					'           <span style="margin-right:20px;line-height:34px;font-weight: normal;float: right">'+
					'              '+((row.attach!=''&&row.attach!=undefined)?'<i class="fa fa-paperclip"></i>':'')+'</a>'+
					'           </span>'+
					'       </div>';
		    	 return html;
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

function getEmailBoxManageList()
{
	$("#myTable").bootstrapTable({
	    url: '/ret/oaget/getEmailBoxManageList',
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
	    sortOrder: "desc",
	    search: true,//启用搜索
	    showColumns: false,//是否显示 内容列下拉框
	    showRefresh: true,//显示刷新按钮
	    idField: 'boxId',//key值栏位
	    clickToSelect: false,//点击选中checkbox
	    pageList : [10, 20, 30, 50],//可选择单页记录数
	    queryParams:queryParams,
	    columns: [
		     {
		    	field: 'num',
				title: '序号',//标题 
				width:'50px',
				formatter: function (value, row, index) {
					return index+1;
				}
		      },
		      {
			    field: 'sortNo',
				title: '排序号',//标题
				visible:true,
				width:'100px'
			  },
			  {
				field: 'boxName',
				title: '文件名称',//标题 
				width:'300px'
			 },
			 {
					field: 'total',
					title: '邮件数',//标题 
					width:'200px'
				 },
		      {
			       field: 'opt',
			       title: '操作',
			       align:'center',
			       width:'180px',
		    	   formatter:function(value,row,index){
		    		   return createOptBtn(row.boxId);
		       }
		    }],
	    onClickCell: function (field, value, row, $element) {
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

function createOptBtn(boxId)
{
	var html="<a onclick=\"rename(this)\" value-data='"+boxId+"' class=\"btn btn-sky btn-xs\" >重命名</a>&nbsp;&nbsp;" +
			 "<a onclick=\"delbox(this)\" value-data='"+boxId+"'  class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}

function rename(Obj)
{
	var boxId=$(Obj).attr("value-data");
	//document.getElementById("form1").reset();
	$.ajax({
		url : "/ret/oaget/getEmailBox",
		type : "post",
		dataType : "json",
		data:{
			boxId:boxId
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
				$("#sortNo").val(data.list.sortNo);
				$("#boxName").val(data.list.boxName);
				$("#createBoxModal").modal("show");
				}
		}
	})
	$(".js-saveBox").unbind("click").click(function(){
		updateEmailBox(boxId);
	});
}


function updateEmailBox(boxId)
{
	if(window.confirm("确定要修改选中的文件夹吗？")){
		$.ajax({
			url : "/set/oaset/updateEmailBox",
			type : "post",
			dataType : "json",
			data:{
				boxId:boxId,
				sortNo:$("#sortNo").val(),
				boxName:$("#boxName").val()
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
					window.location.reload()
					}
			}
		})
		}else
			{
			return;
			}
	
}


function delbox(Obj)
{
	var boxId=$(Obj).attr("value-data");
	if(window.confirm("确定删除选中的文件夹吗？")){
		$.ajax({
			url : "/set/oaset/delEmailBox",
			type : "post",
			dataType : "json",
			data:{
				boxId:boxId
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
					window.location.reload()
					}
			}
		})
		}else
			{
			return;
			}
	
}

function setEmailStar(Obj)
{
	var star=$(Obj).attr("data-star")=="1"?"0":"1";
	$.ajax({
		url : "/set/oaset/updateEmail",
		type : "post",
		dataType : "json",
		data:{
			emailId:$(Obj).attr("data-value"),
			star:star
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
				$(Obj).attr("data-star",star);
				if(star=="1")
					{
					$(Obj).addClass("stared");
					top.layer.msg("设置星标记成功!")
					}else
						{
						$(Obj).removeClass("stared");
						top.layer.msg("取消星标记!")
						}
				}
		}
	});
}

function showEmailBox()
{
	$.ajax({
		url : "/ret/oaget/getEmailBoxList",
		type : "post",
		dataType : "json",
		data:{},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				var html="";
				var btnhtml="<li><a href=\"javascript:void(0);\" onclick=\"setMyEmailBox(this);\" data-value=\"0\">收件箱</a></li>";
				for(var i=0;i<data.list.length;i++)
					{
					 html+='<li id="box_id_'+data.list[i].boxId+'">'+
				           '<a href="/app/core/oa/mysortemail?boxId='+data.list[i].boxId+'">'+
				               '<i class="fa fa-inbox"></i>'+
				               '<span class="badge badge-default badge-square pull-right">'+data.list[i].total+'</span>'+
				               data.list[i].boxName+
				           '</a>'+
				      ' </li>';
					btnhtml+="<li><a href=\"javascript:void(0);\" onclick=\"setMyEmailBox(this);\" data-value=\""+data.list[i].boxId+"\">"+data.list[i].boxName+"</a></li>"; 
					}
				$("#boxlist").html(btnhtml);
				$(".myemailbox").after(html);
				}
		}
	})
}
function setMyEmailBox(Obj)
{
	var emailIds=getCheckBoxValue("emailcheckbox");
	if(!emailIds.length>0){
		top.layer.msg("至少选择一个需要移动的邮件!");
		return;
	}
	if(window.confirm("确定需要移动选中的邮件吗？")){
		var boxId=$(Obj).attr("data-value");
		$.ajax({
			url : "/set/oaset/setMyEmailBox",
			type : "post",
			dataType : "json",
			data:{
				boxId:boxId,
				emailIds:emailIds
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
					window.location.reload()
					}
			}
		});
	}else
		{
		return;
		}
}

function createBox()
{
	$.ajax({
		url : "/set/oaset/createEmailBox",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			boxName:$("#boxName").val()
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
				showEmailBox();
				$("#createBoxModal").modal("hide");
				top.layer.msg(data.msg);
				}
		}
	});
}
function createTag()
{
	$.ajax({
		url : "/set/oaset/createEmailTag",
		type : "post",
		dataType : "json",
		data:{
			tagName:$("#boxName").val()
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
				$("#createBoxModal").modal("hide");
				top.layer.msg(data.msg);
				}
		}
	})
}
function sendEmail()
{
	//禁止使用全部人员 @all
	if($("#toId").attr("data-value")=="@all"||$("#copyToId").attr("data-value")=="@all")
		{
		top.layer.msg("内部邮件禁止发送全体人员！");
		return;
		}
	$.ajax({
		url : "/set/oaset/sendEmail",
		type : "post",
		dataType : "json",
		data:{
			toId:$("#toId").attr("data-value"),
			copyToId:$("#copyToId").attr("data-value"),
			subject:$("#subject").val(),
			attach:$("#attach").attr("data_value"),
			content:$("#content").code(),
			msgType:getCheckBoxValue("msgType")
			
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
				open("/app/core/oa/email?view=sendemailbox","_self");
				}
		}
	})
}

function saveEmail()
{
	//禁止使用全部人员 @all
	if($("#toId").attr("data-value")=="@all"||$("#copyToId").attr("data-value")=="@all")
		{
		top.layer.msg("内部邮件禁止发送全体人员！");
		return;
		}
	$.ajax({
		url : "/set/oaset/saveEmail",
		type : "post",
		dataType : "json",
		data:{
			toId:$("#toId").attr("data-value"),
			copyToId:$("#copyToId").attr("data-value"),
			subject:$("#subject").val(),
			attach:$("#attach").attr("data_value"),
			content:$("#content").code()
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
				$("#createBoxModal").modal("hide");
				top.layer.msg(data.msg);
				}
		}
	})
}

function delMyEmail()
{
	var emailIds=getCheckBoxValue("emailcheckbox");
	if(!emailIds.length>0){
		top.layer.msg("请先选择需要删除的邮件");
		return;
	}
	if(window.confirm("确定删除选中的邮件吗？")){
	$.ajax({
		url : "/set/oaset/delMyEmail",
		type : "post",
		dataType : "json",
		data:{
			emailIds:emailIds
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
				window.location.reload()
				}
		}
	})
	}else
		{
		return;
		}
}

function deleteEmail()
{
	if(window.confirm("确定删除当前邮件吗？")){
	$.ajax({
		url : "/set/oaset/deleteEmail",
		type : "post",
		dataType : "json",
		data:{
			emailId:emailId
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
				//window.location.href = "/app/core/oa/email";
				open("/app/core/oa/email","_self");
				}
		}
	})
	}else
		{
		return;
		}
}

function recoveryMyEmail()
{
	var emailIds=getCheckBoxValue("emailcheckbox");
	if(!emailIds.length>0){
		top.layer.msg("请先选择需要恢复的邮件");
		return;
	}
	if(window.confirm("确定恢复选中的邮件吗？")){
	$.ajax({
		url : "/set/oaset/recoveryMyEmail",
		type : "post",
		dataType : "json",
		data:{
			emailIds:emailIds
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
				window.location.reload()
				}
		}
	})
	}else
		{
		return;
		}
}

function delMyEmailPhysics()
{
	var emailIds=getCheckBoxValue("emailcheckbox");
	if(!emailIds.length>0){
		top.layer.msg("请先选择需要删除的邮件");
		return;
	}
	if(window.confirm("确定删除选中的邮件吗？")){
	$.ajax({
		url : "/set/oaset/delMyEmailPhysics",
		type : "post",
		dataType : "json",
		data:{
			emailIds:emailIds
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
				window.location.reload()
				}
		}
	})
	}else
		{
		return;
		}
}

function updateSetStars()
{
	var emailIds=getCheckBoxValue("emailcheckbox");
	if(!emailIds.length>0){
		top.layer.msg("请先选择需要取消星标记的邮件");
		return;
	}
	if(window.confirm("确定选中取消星标记的邮件吗？")){
	$.ajax({
		url : "/set/oaset/updateSetStars",
		type : "post",
		dataType : "json",
		data:{
			emailIds:emailIds
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
				window.location.reload()
				}
		}
	})
	}else
		{
		return;
		}
}

function updateSetStar()
{
	if(window.confirm("确定取消星标记的邮件吗？")){
	$.ajax({
		url : "/set/oaset/updateSetStars",
		type : "post",
		dataType : "json",
		data:{
			emailIds:emailId
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
				window.location.reload()
				}
		}
	})
	}else
		{
		return;
		}
}


function delMySendEmail()
{
	var emailBodyIds=getCheckBoxValue("emailbodycheckbox");
	if(!emailBodyIds.length>0){
		top.layer.msg("请先选择需要删除的邮件");
		return;
	}
	if(window.confirm("确定删除选中的邮件吗？")){
	$.ajax({
		url : "/set/oaset/delMySendEmail",
		type : "post",
		dataType : "json",
		data:{
			emailBodyIds:emailBodyIds
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
				window.location.reload()
				}
		}
	})
	}else
		{
		return;
		}
}

function getEmailDetails()
{
	$.ajax({
		url : "/ret/oaget/getEmailDetails",
		type : "post",
		dataType : "json",
		data:{
			emailId:emailId
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
				$(".img-circle").attr("src","/sys/file/getOtherHeadImg?headImg="+data.list.headImg);
				$(".js-userName").html(data.list.fromUserName);
				$(".js-subject").html(data.list.subject);
				$(".mail-date").html(data.list.sendTime);
				$(".mail-text").html(data.list.content);
				$("#attach").attr("data_value",data.list.attach);
				var count = attachCount();
				if(count>0)
				{
					$("#attach").show();
					$("#attachCount").html("("+count+")个附件");
					createAttach("attach","3");
					
				}
			}
		}
	})
}
function attachCount()
{
	var attachIds = $("#attach").attr("data_value");
	var attach=[];
	if(attachIds!=null&&attachIds!="")
	{
		attach = attachIds.split(",");
	}
	return attach.length;
	}

function initReplyEmail(flag)
{
	$.ajax({
		url : "/ret/oaget/getEmailBody",
		type : "post",
		dataType : "json",
		data:{
			bodyId:bodyId
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
				for(var name in data.list)
					{
					if(name=="content")
						{
							$("#content").code(data.list[name]);
						}else if(name=="attach")
						{
							$("#attach").attr("data_value",data.list[name]);
							createAttach("attach",4);	
						}else if(name=="fromId")
						{
							if(flag=="0")
								{
								$("#toId").attr("data-value",data.list["fromId"]);
								$("#toId").val(getUserNameByStr(data.list["fromId"]));
								}
						}else if(name=="toId")
						{
							if(flag=="1")
								{
								$("#toId").attr("data-value",data.list["toId"]);
								$("#toId").val(getUserNameByStr(data.list["toId"]));
								}
						}else
							{
							$("#"+name).val(data.list[name]);
							}
					}
				}
			}
	})
}


