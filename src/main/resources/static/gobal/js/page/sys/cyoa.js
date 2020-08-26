function getUserPrivNamesByIds(userPrivIds) {
	var returnData = "";
	$.ajax({
		url : "/ret/unitget/getUserPrivNamesByIds",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			userPrivIds : userPrivIds
		},
		success : function(data) {
			var tmpstr = "";
			if (data.status == 200) {
				var lists = data.list;
				for (var i = 0; i < lists.length; i++) {
					tmpstr += lists[i].userPrivName + ",";
				}
				returnData = tmpstr;
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
	return returnData;
}
function getNoReadSms() {
	$.ajax({
		url : "/ret/sysget/getNoReadSms",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				
				if (data.list) {
					var msgbody = "";
					for (var i = 0; i < data.list.length; i++) {
						var url = data.list[i].smsUrl;
						var smsId = data.list[i].smsId;
						msgbody += [ '<li>', '<a href=\"javascript:void(0);readSysSms(\''+url+'\',\''+smsId+'\');\">', '   <img style="width:42px;height:42px;" src="/sys/file/getOtherHeadImg?headImg=' + data.list[i].headImg + '" class="message-avatar" alt="' + data.list[i].userName + '">', '   <div class="message">',
								'       <span class="message-sender">', data.list[i].userName, '       </span>', '       <span class="message-time">', data.list[i].smsSendTime, '       </span>', 
								'       <span class="message-subject spanStyle">',data.list[i].smsTitle,
								'       </span>', '       <span class="message-body">', data.list[i].smsContent, '       </span>', '   </div>', '                                        </a>', '                                    </li>' ].join("");

					}
					$("#msgTotal").html(data.list.length);
					$(".dropdown-messages").html(msgbody);
				}
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
	getNoReadSmsCount()
}

function readSysSms(url,smsId)
{
	$.ajax({
		url : "/set/oaset/updateSms",
		type : "post",
		dataType : "json",
		data:{smsId:smsId,smsStatus:'1'},
		success : function(data) {
			if (data.status == 200) {
				getNoReadSms();
				window.open(url);
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	})
}
function getNoReadSmsCount() {
	$.ajax({
		url : "/ret/sysget/getNoReadSmsCount",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				$(".noreandmsg").html(data.list);
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	})
}

function getCodeClass(eId, module) {
	$.ajax({
		url : "/ret/sysget/getCodeClassByModule",
		type : "post",
		dataType : "json",
		data : {
			module : module
		},
		success : function(data) {
			if (data.status == 200) {
				var html = "<option value=''>请选择</option>";
				for (var i = 0; i < data.list.length; i++) {
					html += "<option value='" + data.list[i].codeValue + "'>" + data.list[i].codeName + "</option>";
				}
				$("#" + eId).html(html);
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	})
}

function getCodeClassName(value, module) {
	var returnStr;
	$.ajax({
		url : "/ret/sysget/getCodeClassByModule",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			module : module
		},
		success : function(data) {
			if (data.status == 200) {

				for (var i = 0; i < data.list.length; i++) {
					if (data.list[i].codeValue == value) {
						returnStr = data.list[i].codeName;
						return;
					}
				}
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	})
	return returnStr;
}

function getSmsConfig(eId, module) {
	$.ajax({
		url : "/ret/sysget/getSmsConfig",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var html = "";
				var json = JSON.parse(data.list.config);
				var mustjson = JSON.parse(data.list.mustChecked);
				if (json) {
					var smsConfig = json[module];
					var mustChecked = mustjson[module];
					for (var i = 0; i < smsConfig.length; i++) {
						var checkedHtml="";
						if(isInArray(mustChecked,smsConfig[i]))
						{
							checkedHtml=" onclick='return false;' checked='checked'";
						}
						html += "<div class='checkbox' style='display: inline-block;'><label><input type='checkbox' name='" + eId + "' value='" + smsConfig[i] + "'"+checkedHtml+" ><span class='text'>";
						if (smsConfig[i] == "webSms") {
							html += "站内消息";
						} else if (smsConfig[i] == "mobileSms") {
							html += "手机短信 ";
						} else if (smsConfig[i] == "appSms") {
							html += "APP推送 ";
						} else if (smsConfig[i] == "webMail") {
							html += "外部邮件 ";
						} else if (smsConfig[i] == "ddSms") {
							html += "钉钉消息 ";
						}else if (smsConfig[i] == "wxSms") {
							html += "微信消息 ";
						}
						html += "</span></label></div>";
					}
				}
				$("#" + eId).html(html);
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	})
}

function getCheckBoxValue(eId) {
	var returnStr = [];
	$('input[name="' + eId + '"]:checked').each(function() {
		returnStr.push($(this).val());
	});
	return returnStr.join(",");
}

function getUserInfoDeatilsByAccountId(accountId) {
	var userInfo = {};
	if (accountId) {
		$.ajax({
			url : "/ret/unitget/getUserInfoDeatilsByAccountId",
			type : "post",
			dataType : "json",
			async : false,
			data : {
				accountId : accountId
			},
			success : function(data) {
				if (data.status == 200) {
					userInfo = data.list;
				} else if (data.status == 100) {
					console.log(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		});
	}
	return userInfo
}

function getUserNameByStr(accountIds) {
	if (accountIds == "@all") {
		return "全体人员";
	} else {
		var userNames = [];
		if (accountIds) {
			$.ajax({
				url : "/ret/unitget/getUserNamesByAccountIds",
				type : "post",
				dataType : "json",
				async : false,
				data : {
					accountIds : accountIds
				},
				success : function(data) {
					if (data.status == 200) {
						for (var i = 0; i < data.list.length; i++) {
							userNames.push(data.list[i].userName);
						}
					} else if (data.status == 100) {
						console.log(data.msg);
					} else {
						console.log(data.msg);
					}
				}
			});
		}
		return userNames.join(",");
	}
}

function getUserLevelStr(userLevelIds) {
	if (userLevelIds == "@all") {
		return "所有级别";
	} else {
		var userLevelNames = [];
		if (userLevelIds) {
			$.ajax({
				url : "/ret/unitget/getUserLevelByLevelIds",
				type : "post",
				dataType : "json",
				async : false,
				data : {
					userLevelIds : userLevelIds
				},
				success : function(data) {
					if (data.status == "200") {
						for (var i = 0; i < data.list.length; i++) {
							userLevelNames.push(data.list[i].name);
						}
					} else if (data.status == "100") {
						console.log(data.msg);
					} else {
						console.log(data.msg);
					}
				}
			});
		}
		return userLevelNames.join(",");
	}
}

function getDeptNameByDeptIds(deptIds) {
	if (deptIds == "@all") {
		return "全体部门";
	} else {
		var deptNames = [];
		if (deptIds) {
			$.ajax({
				url : "/ret/unitget/getUnitDeptByDeptIds",
				type : "post",
				dataType : "json",
				async : false,
				data : {
					deptIds : deptIds
				},
				success : function(data) {
					if (data.status == 200) {
						for (var i = 0; i < data.list.length; i++) {
							deptNames.push(data.list[i].deptName);
						}
					} else if (data.status == 100) {
						console.log(data.msg);
					} else {
						console.log(data.msg);
					}
				}
			});
		}
		return deptNames.join(",");
	}
}

function getSysTime() {
	var myDate = new Date();
	//获取当前年
	var year = myDate.getFullYear();
	//获取当前月
	var month = myDate.getMonth() + 1;
	//获取当前日
	var date = myDate.getDate();
	var h = myDate.getHours(); //获取当前小时数(0-23)
	var m = myDate.getMinutes(); //获取当前分钟数(0-59)
	var s = myDate.getSeconds();
	var now = year + '-' + getNow(month) + "-" + getNow(date) + " " + getNow(h) + ':' + getNow(m) + ":" + getNow(s);
	return now;
}
/**
 * 格式化时间 yyyy-MM-dd HH:mm:ss
 * @param myDate
 * @returns
 */
function formatTime(myDate) {
	var year = myDate.getFullYear();
	//获取当前月
	var month = myDate.getMonth() + 1;
	//获取当前日
	var date = myDate.getDate();
	var h = myDate.getHours(); //获取当前小时数(0-23)
	var m = myDate.getMinutes(); //获取当前分钟数(0-59)
	var s = myDate.getSeconds();
	var now = year + '-' + getNow(month) + "-" + getNow(date) + " " + getNow(h) + ':' + getNow(m) + ":" + getNow(s);
	return now;
}
/**
 * 格式化日期 yyyy-MM-dd
 * @param myDate
 * @returns
 */
function formatDate(myDate) {
	var year = myDate.getFullYear();
	var month = myDate.getMonth() + 1;
	var date = myDate.getDate();
	var now = year + '-' + getNow(month) + "-" + getNow(date);
	return now;
}
/**
 * 日期加
 * @param dayNumber
 * @param dateStr
 * @returns
 */
function addDay(dayNumber, dateStr) {
	var date = new Date(Date.parse(dateStr.replace(/-/g, "/")));
    date = date ? date : new Date();
    var ms = dayNumber * (1000 * 60 * 60 * 24)
    var newDate = new Date(date.getTime() + ms);
    return formatDate(newDate);
}

/**
 * 获取当前日期
 * @returns
 */
function getSysDate() {
	var myDate = new Date();
	//获取当前年
	var year = myDate.getFullYear();
	//获取当前月
	var month = myDate.getMonth() + 1;
	//获取当前日
	var date = myDate.getDate();
	var now = year + '-' + getNow(month) + "-" + getNow(date);
	return now;
}
// 获取当前时间
function getNow(s) {
	return s < 10 ? '0' + s : s;
}
String.prototype.endWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substring(this.length - s.length) == s)
		return true;
	else
		return false;
	return true;
}

String.prototype.startWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substr(0, s.length) == s)
		return true;
	else
		return false;
	return true;
}
function arr_del(arr, index) {
	return arr.slice(0, index - 1).concat(arr.slice(index));
}

function getfileClass(filename) {
	var point = filename.lastIndexOf(".");
	var type = filename.substr(point)
	if (type == ".txt") {
		return "fa fa-file-text";
	} else if (type == ".pdf") {
		return "fa fa-file-pdf-o";
	} else if (type == ".xls" || type == ".xlsx") {
		return "fa fa-file-excel-o";
	} else if (type == ".doc" || type == ".docx" || type == ".wps" || type == ".dot") {
		return "fa fa-file-word-o";
	} else if (type == ".jpg" || type == ".png" || type == ".bpm") {
		return "fa fa-file-image-o";
	} else if (type == ".rar" || type == ".zip" || type == ".gz" || type == ".tar") {
		return "fa fa-file-zip-o";
	} else if (type == ".ppt" || type == ".pptx" || type == ".ppts") {
		return "fa fa-file-powerpoint-o";
	} else {
		return "fa fa-file";
	}
}

function checkTime(i) {
	if (i < 10) {
		i = "0" + i
	}
	return i;
}

function clearSelect(id) {
	$("#" + id).attr("data-value", "");
	$("#" + id).val("");

}
/*数组去重*/
function unique(arr) {
	if (!Array.isArray(arr)) {
		console.log('type error!')
		return;
	}
	arr = arr.sort()
	var arrry = [ arr[0] ];
	for (var i = 1; i < arr.length; i++) {
		if (arr[i] !== arr[i - 1]) {
			arrry.push(arr[i]);
		}
	}
	return arrry;
}

function getshowtime(createtime) {
	if (createtime) {
		createtime = createtime.replace(/-/g, "/"); // 处理 ios 不兼容问题
	}
	let createTime = new Date(createtime);
	let createTimes = createTime.getTime();
	let nowTime = new Date();
	let nowTimes = nowTime.getTime();
	if (nowTimes < createTimes) {
		return "现在时间之后";
	}

	let createYear = createTime.getFullYear();
	let nowYear = nowTime.getFullYear();
	let createMonth = createTime.getMonth();
	let nowMonth = nowTime.getMonth();
	let createDate = createTime.getDate();
	let nowDate = nowTime.getDate();

	if (createYear < nowYear) {
		return (nowYear - createYear) + "年前";
	} else {
		if (createMonth < nowMonth) {
			return (nowMonth - createMonth) + "月前";
		} else {
			if (createDate < nowDate) {
				if (nowDate - createDate == 1) {
					return "昨天"
				} else {
					return (nowDate - createDate) + "天前";
				}
			} else {
				let diffValue = nowTimes - createTimes;
				if (diffValue / (1000 * 60 * 60) >= 1) {
					return (parseInt(diffValue / (1000 * 60 * 60))) + "小时前";
				} else {
					if (diffValue / (1000 * 60) >= 1) {
						return (parseInt(diffValue / (1000 * 60))) + "分钟前";
					} else {
						return "刚刚";
					}
				}
			}
		}
	}
}
/**
使用jquery的inArray方法判断元素是否存在于数组中
@param {Object} arr 数组
@param {Object} value 元素值
*/
function isInArray(arr,value){
var index = $.inArray(value,arr);
if(index >= 0){
return true;
}
return false;
}
/**
 * 小数转成时间
 * @param minutes
 * @returns
 */
function minTommss(minutes){
	 var sign = minutes < 0 ? "-" : "";
	 var min = Math.floor(Math.abs(minutes));
	 var sec = Math.floor((Math.abs(minutes) * 60) % 60);
	 return sign + (min < 10 ? "0" : "") + min + ":" + (sec < 10 ? "0" : "") + sec;
	}

function mustNumber(val) {
    var regPos = /^\d+(\.\d+)?$/; //非负浮点数
    var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
    if(regPos.test(val) || regNeg.test(val)) {
        return true;
        } else {
        return false;
        }
    }
 

function getSmsType(module)
{
if(module=="EMAIL")
{
	return "电子邮件消息";
}else if(module=="NEWS")
{
	return "新闻消息";
}else if(module=="NOTICE")
{
	return "通知公告消息";
}else if(module=="DIARY")
{
	return "工作日志消息";
}else if(module=="BPM")
{
	return "工作流消息";
}else if(module=="DOCUMENT")
{
	return "收发文消息";
}else if(module=="USER")
{
	return "个人消息";
}else if(module=="SYS")
{
	return "系统消息";
}else if(module=="MEETING")
{
	return "会议消息";
}else if(module=="TASK")
{
	return "任务消息";
}else if(module=="HR")
{
	return "人力资源消息";
}else if(module=="SUPERVERSION")
{
	return "督查督办消息";
}else if(module=="CONTRACT")
{
	return "合同消息";
}else if(module=="CRM")
{
	return "客户关系消息";
}else if(module=="PROJECT")
{
	return "项目消息";
}else if(module=="ERP")
{
	return "ERP消息";
}else if(module=="PUBLIC_FILE")
{
	return "文件更新消息";
}else if(module=="WORKPLAN")
{
	return "工作计划消息";
}else if(module=="DATAUPLOAD")
{
	return "数据上报消息";
}
}
