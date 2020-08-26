$(function(){
	initCpuDiv();
	getCpuInfo();
	getDiskStatus();
});
setInterval(function () {
	getCpuStatus();
	initMemoryDiv();
},1000);



function getCpuInfo()
{
	$.ajax({
		url : "/ret/sysget/getCpuInfo",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var html="";
				for(var i=0;i<data.list.length;i++)
				{
					
				}
			}else if(data.status=="100")
			{
				console.log(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
}


function getDiskStatus()
{
	$.ajax({
		url : "/ret/sysget/getDiskStatus",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var html="";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<div class='col-lg-3 col-sm-6 col-xs-12'>\n"+
                    "<div class='databox radius-bordered databox-shadowed databox-graded'>\n"+
                        "<div class='databox-left bg-lightred'>\n"+
                            "<div class='databox-piechart'>\n"+
                                "<div data-toggle='easypiechart' class='easyPieChart' data-barcolor='#fff' data-linecap='butt' data-percent="+(data.list[i].usePercent)+" data-animate='500' data-linewidth='3' data-size='47' data-trackcolor='#f39e93' style='width: 47px; height: 47px; line-height: 47px;'>" +
                                		"<span class='white font-90'>"+(data.list[i].usePercent).toFixed(2)+"%</span>\n"+
                                	"<canvas width='47' height='47'></canvas></div>\n"+
                            "</div>\n"+
                        "</div>\n"+
                        "<div class='databox-right'>\n"+
                            "<span class='databox-number lightred'>"+data.list[i].dirName+"</span>\n"+
                            "<div class='databox-text darkgray'>总容量:"+(data.list[i].total/(1024*1024)).toFixed(2)+"GB,读:"+(data.list[i].diskReads/1024).toFixed(2)+"MB/s 写:"+(data.list[i].diskWrites/1024).toFixed(2)+"MB/s</div>\n"+
                            "<div class='databox-stat bg-lightred radius-bordered'>\n"+
                                "<div class='stat-text'>剩余:"+(100-(data.list[i].usePercent))+"%</div>\n"+
                                "<i class='stat-icon fa fa-arrow-down'></i>\n"+
                            "</div>\n"+
                        "</div>\n"+
                    "</div>\n"+
                    "</div>\n";
				}
				$("#diskcharts").html(html);
			}else if(data.status=="100")
			{
				console.log(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
}

var cpuOption = {
    tooltip : {
        formatter: "{a} <br/>{b} : {c}%"
    },
    toolbox: {
        feature: {
            restore: {},
            saveAsImage: {}
        }
    },
    series: [
        {
            name: 'CPU核心使用情况',
            type: 'gauge',
            detail: {formatter:'{value}%'},
            data: [{value: 50, name: '使用率'}]
        }
    ]
};
var memoryOption = {
	    title : {
	        text: '内存使用请况',
	        subtext: '检查频率2S',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        type: 'scroll',
	        orient: 'vertical',
	        right: 10,
	        top: 20,
	        bottom: 20,
	        //data: data.legendData,
	        //selected: data.selected
	    },
	    series : [
	        {
	            name: '内存大小',
	            type: 'pie',
	            radius : '55%',
	            center: ['40%', '50%'],
	            //data: data.seriesData,
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};
var swapOption = {
	    title : {
	        text: '交换区使用请况',
	        subtext: '检查频率2S',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        type: 'scroll',
	        orient: 'vertical',
	        right: 10,
	        top: 20,
	        bottom: 20,
	        //data: data.legendData,
	        //selected: data.selected
	    },
	    series : [
	        {
	            name: '文件大小',
	            type: 'pie',
	            radius : '55%',
	            center: ['40%', '50%'],
	            //data: data.seriesData,
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};
function initCpuDiv()
{
	$.ajax({
		url : "/ret/sysget/getCpuStatus",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var html="";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<div class='col-lg-3 col-sm-6 col-xs-12' ><div class='widget' style='height: 350px;'>\n"+
					"<div class='widget-header'>\n"+
						"<span class='widget-caption'>#"+(i+1)+"CPU核心使用情况</span>\n"+
					"</div>\n"+
					"<div class='widget-body' style='height: 350px;' >\n"+
					"<div id='main"+i+"' style='height: 320px;' class='col-sm-12'></div>\n"+
					"</div>\n"+
				"</div></div>";
				}
				$("#cpucharts").html(html);
			}else if(data.status=="100")
			{
				console.log(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
}

function getCpuStatus()
{
	$.ajax({
		url : "/ret/sysget/getCpuStatus",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				for(var i=0;i<data.list.length;i++)
				{
					var myChart = echarts.init(document.getElementById('main'+i));
					var value = data.list[i].cpuUser.replace("%","");
					cpuOption.series[0].data[0].value = data.list[i].cpuUser.replace("%","");
					myChart.setOption(cpuOption, true);
					myChart.setOption(cpuOption);
					window.addEventListener("resize",function(){
					    myChart.resize();
					});
				}
				
			}else if(data.status=="100")
			{
				console.log(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
}



function initMemoryDiv()
{
	$.ajax({
		url : "/ret/sysget/getMemoryStatus",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var seriesArr=[];
				var selectedArr=[];
				var json1={};
				json1.name="已用"
				json1.value = data.list.memUsed;
				seriesArr.push(json1);
				selectedArr.push("已用");
				var json2={};
				json2.name="剩余"
				json2.value = data.list.memFree;
				seriesArr.push(json2);
				selectedArr.push("剩余");
				var myChart = echarts.init(document.getElementById('memorymain'));
				memoryOption.legend.data=selectedArr;
				memoryOption.legend.selected=selectedArr;
				memoryOption.series[0].data=seriesArr;
				myChart.setOption(memoryOption, true);
				myChart.setOption(memoryOption);
				window.addEventListener("resize",function(){
				    myChart.resize();
				});
				
				var seriesArr=[];
				var selectedArr=[];
				var json1={};
				json1.name="已用"
				json1.value = data.list.swapUsed;
				seriesArr.push(json1);
				selectedArr.push("已用");
				var json2={};
				json2.name="剩余"
				json2.value = data.list.swapFree;
				seriesArr.push(json2);
				selectedArr.push("剩余");
				
				var myChart = echarts.init(document.getElementById('swapmain'));
				swapOption.legend.data=selectedArr;
				swapOption.legend.selected=selectedArr;
				swapOption.series[0].data=seriesArr;
				myChart.setOption(swapOption, true);
				myChart.setOption(swapOption);
				window.addEventListener("resize",function(){
				    myChart.resize();
				});
			}else if(data.status=="100")
			{
				console.log(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
}