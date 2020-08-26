$(function(){
	query();
	jeDate("#beginTime1", {
		format: "YYYY-MM-DD hh:mm"
	});
	jeDate("#endTime1", {
		format: "YYYY-MM-DD hh:mm"
	});
	
	jeDate("#beginTime", {
		format: "YYYY-MM-DD hh:mm",
		minDate:getSysDate()
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD hh:mm",
		minDate:getSysDate()
	});
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
	getCodeClass("meetingType1","meeting");
	getCodeClass("meetingType","meeting");
	getCanUseMeetingRoomList();
	getSmsConfig("msgType","meeting");
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/meetingget/getMyApplyMeetingList',
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
		      idField: 'meetingId',//key值栏位
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
		       field: 'subject',
		       title: '会议主题',
		       sortable : true,
		       width:'200px'
		      },
		      {
				field: 'meetingType',
				   width:'50px',
				   title: '会议类型',
				   formatter:function(value,row,index){
			    	 return getCodeClassName(value,"meeting");
		            }
				},
				{
			       field: 'roomName',
			       title: '会议室',
			       width:'50px'
		      },
		      {
			       field: 'chairName',
			       title: '会议主持',
			       width:'50px'
		      },
		      {
			       field: 'beginTime',
			       width:'100px',
			       title: '会议开始时间'
			   },
			   {
			       field: 'endTime',
			       width:'100px',
			       title: '会议结束时间'
			   },
			   {
			       field: 'createTime',
			       width:'100px',
			       title: '创建时间'
			   },
			   {
			       field: 'status',
			       width:'100px',
			       title: '审批状态',
			       formatter:function(value,row,index){
			    	   if(value=="0")
			    		{
			    		   return "待审批";
			    		}else if(value=="1")
			    		{
			    			return "通过";
			    		}else if(value=="2")
			    		{
			    			return "未通过";
			    		}else if(value=="3")
			    		{
			    			return "取消";
			    		}else
			    		{
			    			return "未知";
			    		}
			       }
			   },
		      {
		       field: 'opt',
		       width:'150px',
		       align:'center',
		       title: '操作',
	    	   formatter:function(value,row,index){
	    		   var flag=0;
	    		   if((row.beginTime+":00")>getSysTime())
	    			{
	    			   flag=1;
	    			}
	                return createOptBtn(row.meetingId,flag,row.status);
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
        sortOrder:params.order,
        meetingType:$("#meetingType1").val(),
        beginTime:$("#beginTime1").val(),
        endTime:$("#endTime1").val(),
        chair:$("#chair1").attr("data-value")
    };
    return temp;
};
function createOptBtn(meetingId,flag,status)
{
	var html="<a href=\"javascript:void(0);details('"+meetingId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;";
	if(flag==0)
	{
		
	}else
	{
		if(status=="0")
		{
			html+="<a href=\"javascript:void(0);edit('"+meetingId+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;";
		}else
		{
			html+="<a href=\"javascript:void(0);sendMsg('"+meetingId+"')\" class=\"btn btn-maroon btn-xs\" >提醒</a>&nbsp;&nbsp;" +
			  	  "<a href=\"javascript:void(0);cancelmeeting('"+meetingId+"')\" class=\"btn btn-darkorange btn-xs\" >取消</a>";
		}
	}
	return html;
}

function cancelmeeting(meetingId)
{
	$.ajax({
		url : "/set/meetingset/cancelmeeting",
		type : "post",
		dataType : "json",
		data : {
			meetingId : meetingId
		},
		success : function(data) {
			if(data.status == "200") {
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
function sendMsg(meetingId)
{
	$.ajax({
		url : "/set/meetingset/sendMeetingMsg",
		type : "post",
		dataType : "json",
		data : {
			meetingId : meetingId
		},
		success : function(data) {
			if(data.status == "200") {
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

function edit(meetingId)
{
	document.getElementById("form").reset();
	$('#deviceId').val([]).trigger('change');
	$("#notesUser").attr("data-value");
	$("#chair").attr("data-value");
	$("#userJoin").attr("data-value");
	$("#deptJoin").attr("data-value");
	$("#leaveJoin").attr("data-value");
	$("#show_meetingattach").html("");
	$("#meetingattach").attr('data_value');
	$.fn.modal.Constructor.prototype.enforceFocus = function() {};
	$("#applymeetingmodal").modal("show");
	$.ajax({
		url : "/ret/meetingget/getMeetingById",
		type : "post",
		dataType : "json",
		data : {
			meetingId : meetingId
		},
		success : function(data) {
			if(data.status == "200") {
				console.log(data.list);
				for(var id in data.list)
				{
					if(id=="attach")
					{
						console.log(data.list[id]);
						$("#meetingattach").attr("data_value", data.list[id]);
						createAttach("meetingattach",4);
					}else if(id=="attachPriv")
					{
						$("input:radio[name='attachPriv'][value='"+data.list[id]+"']").attr("checked","checked");
					}else if(id=="deviceId")
					{
						$.ajax({
						url : "/ret/meetingget/getCanUseDeviceList",
						type : "post",
						dataType : "json",
						async : false,
						success : function(data) {
							if(data.status=="200")
							{
								var html="";
								for(var i=0;i<data.list.length;i++)
								{
									html+="<option value='"+data.list[i].deviceId+"'>"+data.list[i].deviceName+"</option>";
								}
							$("#deviceId").html(html);
							}else if(data.status=="100")
							{
								top.layer.msg(data.msg);
							}else{
								console.log(data.msg);
							}
						}
					});
						$('#deviceId').select2();
						var deviceIds = data.list.deviceId;
						var arr = deviceIds.split(",");
					    $('#deviceId').val(arr).trigger('change');
					}else if(id=="userJoin")
					{
						$("#"+id).attr("data-value",data.list[id]);
						$("#"+id).val(getUserNameByStr(data.list[id]));
					}else if(id=="chair")
					{
						$("#"+id).attr("data-value",data.list[id]);
						$("#"+id).val(getUserNameByStr(data.list[id]));
					}else if(id=="notesUser")
					{
						$("#"+id).attr("data-value",data.list[id]);
						$("#"+id).val(getUserNameByStr(data.list[id]));
					}else if(id=="deptJoin")
					{
						$("#"+id).attr("data-value",data.list[id]);
						$("#"+id).val(getDeptNameByDeptIds(data.list[id]));
					}else if(id=="leaveJoin")
					{
						$("#"+id).attr("data-value",data.list[id]);
						$("#"+id).val(getUserLevelStr(data.list[id]));
					}else
					{
						$("#"+id).val(data.list[id]);
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
		$(".js-save").unbind("click").click(function(){
			updatemeeting(meetingId);
		});
}

function updatemeeting(meetingId)
{
	var deviceIds= $("#deviceId").val();
	$.ajax({
		url : "/set/meetingset/updateMeeting",
		type : "post",
		dataType : "json",
		data:{
			meetingId:meetingId,
			subject:$("#subject").val(),
			meetingType:$("#meetingType").val(),
			chair:$("#chair").attr("data-value"),
			roomId:$("#roomId").val(),
			notesUser:$("#notesUser").attr("data-value"),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val(),
			userJoin:$("#userJoin").attr("data-value"),
			deptJoin:$("#deptJoin").attr("data-value"),
			leaveJoin:$("#leaveJoin").attr("data-value"),
			otherJoin:$("#otherJoin").val(),
			attach:$("#meetingattach").attr("data_value"),
			deviceId:deviceIds.toString(),
			attachPriv:$("input:radio[name='attachPriv']:checked").val(),
			remark:$("#remark").val(),
			msgType:getCheckBoxValue("msgType")
		},
		success : function(data) {
			if (data.status == "200") {
				top.layer.msg(data.msg);
				$("#applymeetingmodal").modal("hide");
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else{
				console.log(data.msg);
			}
		}
	})
}


function getCanUseMeetingRoomList()
{
	$.ajax({
		url : "/ret/meetingget/getCanUseMeetingRoomList",
		type : "post",
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.status == "200") {
				var mhtml="";
				for(var i=0;i<data.list.length;i++)
					{
					mhtml+="<option value='"+data.list[i].roomId+"'>"+data.list[i].name+"</option>";
					}
				$("#roomId").html(mhtml);
			} else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else{
				console.log(data.msg);
			}
		}
	});
}

function details(meetingId)
{
	window.open("/app/core/meeting/meetingdetails?meetingId="+meetingId);
}
