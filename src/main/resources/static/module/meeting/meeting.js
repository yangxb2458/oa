var widthTmp=0;
function initSelectTime() {
	$(".divright").scroll(function() {
		widthTmp= this.scrollLeft;  //可见宽度
      //  console.log(widthTmp);
        //var contentH = $(document).height();  //内容高度
    });
	 $(".timeitem").mousedown(function(){
		 var selList = [];
		    //var fileNodes = document.getElementsByTagName("dl"); 
		 	var fileNodes = $(".filedl");
		    for ( var i = 0; i < fileNodes.length; i++) { 
		      if (fileNodes[i].className.indexOf("filedl") != -1) { 
		    	 if(fileNodes[i].className.indexOf("noprivselect") != -1) { 
		    		 fileNodes[i].className = "filedl noprivselect"; 
		    	  }else
		    	{
		    		  fileNodes[i].className = "filedl"; 
		    	}
		        selList.push(fileNodes[i]); 
		      }
		    } 
		    var isSelect = true; 
		    var evt = window.event || arguments[0]; 
		    var startX = (evt.x || evt.clientX); 
		    var startY = (evt.y || evt.clientY); 
		    var seldl = document.createElement("dl"); 
		    seldl.style.cssText = "position:absolute;width:0px;height:0px;font-size:0px;margin:0px;padding:0px;border:1px dashed #0099FF;background-color:#C3D5ED;z-index:1000;filter:alpha(opacity:60);opacity:0.6;display:none;"; 
		    seldl.id = "selectdl"; 
		    document.body.appendChild(seldl); 
		    seldl.style.left = startX + "px"; 
		    seldl.style.top = startY + "px"; 
		    var _x = null; 
		    var _y = null; 
		    clearEventBubble(evt); 
		    $(".timeitem").mouseover(function(){
		    	evt = window.event || arguments[0]; 
		        if (isSelect) { 
		          if (seldl.style.display == "none") { 
		            seldl.style.display = ""; 
		          } 
		          _x = (evt.x || evt.clientX); 
		          _y = (evt.y || evt.clientY); 
		          seldl.style.left = (Math.min(_x, startX)+widthTmp) + "px"; 
		          seldl.style.width = Math.abs(_x - startX) + "px"; 
		          var _l = seldl.offsetLeft, _t = seldl.offsetTop-34; 
		          var _w = seldl.offsetWidth, _h = seldl.offsetHeight; 
		          //判断是否有时间段冲突
		          for ( var i = 0; i < selList.length; i++) { 
		        	  var sl = selList[i].offsetWidth + selList[i].offsetLeft; 
			            var st = selList[i].offsetHeight + selList[i].offsetTop; 
			            if (sl > _l && st > _t && selList[i].offsetLeft < _l + _w && selList[i].offsetTop < _t + _h) { 
				        	  if(selList[i].className.indexOf("noprivselect")> -1)
				            	{
				            		top.layer.msg("所选的时间段冲突！请更换时间段！");
				            		return;
				            	}
			            }
		          }
		          for ( var i = 0; i < selList.length; i++) { 
		            var sl = selList[i].offsetWidth + selList[i].offsetLeft; 
		            var st = selList[i].offsetHeight + selList[i].offsetTop; 
		            if (sl > _l && st > _t && selList[i].offsetLeft < _l + _w && selList[i].offsetTop < _t + _h) { 
		              if (selList[i].className.indexOf("seled") == -1) {
		                selList[i].className = selList[i].className + " seled"; 
		              } 
		            } else { 
		              if (selList[i].className.indexOf("seled") != -1) { 
		                selList[i].className = "filedl"; 
		              } 
		            } 
		          } 
		        } 
		        clearEventBubble(evt); 
		    });
		    $(".timeitem").mouseup(function(){
		    	isSelect = false; 
		    	if (seldl) { 
		            document.body.removeChild(seldl); 
		            showSeldl(selList); 
		          }
		    	 selList = null, _x = null, _y = null, seldl = null, startX = null, startY = null, evt = null;
		    });
	 });
	
}; 

function clearEventBubble(evt) { 
  if (evt.stopPropagation) 
    evt.stopPropagation(); 
  else 
    evt.cancelBubble = true; 
  if (evt.preventDefault) 
    evt.preventDefault(); 
  else 
    evt.returnValue = false; 
} 
function showSeldl(arr) { 
  var count = 0; 
  var selInfo =[]; 
  var roomId = "";
  for ( var i = 0; i < arr.length; i++) { 
    if (arr[i].className.indexOf("seled") != -1) { 
      count++; 
      roomId = $(arr[i]).parent("div").attr("data-value");
      selInfo.push($(arr[i]).attr("data-value"));
     // selInfo += arr[i].innerHTML + "\n"; 
    } 
  }
  if(count>0)
{
	  var nowtime = $("#nowtime").attr("data-value");
	  $("#applymeetingmodal").modal("show");
	  document.getElementById("form").reset();
	  	$('#deviceId').val([]).trigger('change');
	  	$("#notesUser").attr("data-value");
	  	$("#chair").attr("data-value");
	  	$("#userJoin").attr("data-value");
	  	$("#deptJoin").attr("data-value");
	  	$("#leaveJoin").attr("data-value");
	  	$("#show_meetingattach").html("");
	  	$("#meetingattach").attr('data_value');
	  $("#roomId").val(roomId);
	  var beginTime = nowtime+" "+ minTommss(selInfo[0]/2);
	  var endTime = nowtime+" "+minTommss((parseInt(selInfo[selInfo.length-1])+1)/2);
	  $("#beginTime").val(beginTime);
	  $("#endTime").val(endTime);
	  jeDate("#beginTime", {
		  	initDate:beginTime,
			format: "YYYY-MM-DD hh:mm",
			minDate:getSysDate()
		});
		jeDate("#endTime", {
			initDate:endTime,
			format: "YYYY-MM-DD hh:mm",
			minDate:getSysDate()
			
		});
		$(".js-save").unbind("click").click(function(){
			applymeeting();
		});
	  //alert("共选择 " + count + " 个文件，分别是：\n" + selInfo); 
}
 
} 