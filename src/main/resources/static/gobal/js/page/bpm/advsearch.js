$(function(){
	getMyBpmList()
});
function getMyBpmList()
{
	$.ajax({
		url : "/ret/bpmget/getQueryAdvFlowList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				if(data.list.length>0)
				{
					for(var i=0;i<data.list.length;i++)
					{
						var html =['<div class="col-lg-3 col-sm-6 col-xs-12 js-flow-item" data-value='+data.list[i].flowId+' style="cursor: pointer;">',
							'							<div class="databox databox-lg databox-inverted radius-bordered databox-shadowed databox-graded databox-vertical">',
							'								<div class="databox-top no-padding">',
							'									<h3 style="line-height: 55px;">'+data.list[i].flowName+'</h3>',
							'								</div>',
							'								<div class="databox-bottom no-padding bordered-thick bordered-orange">',
							'									<div class="databox-row">',
							'										<div class="databox-cell cell-4 no-padding text-align-center bordered-right bordered-platinum">',
							'											<span class="databox-number lightcarbon no-margin">'+data.list[i].endZs+'</span> <span class="databox-text sonic-silver  no-margin">已结束</span>',
							'										</div>',
							'										<div class="databox-cell cell-4 no-padding text-align-center bordered-right bordered-platinum">',
							'											<span class="databox-number lightcarbon no-margin">'+data.list[i].processZs+'</span> <span class="databox-text sonic-silver no-margin">进行中</span>',
							'										</div>',
							'										<div class="databox-cell cell-4 no-padding text-align-center">',
							'											<span class="databox-number lightcarbon no-margin">'+data.list[i].delZs+'</span> <span class="databox-text sonic-silver no-margin">已删除</span>',
							'										</div>',
							'									</div>',
							'								</div>',
							'							</div>',
							'						</div>'].join("");
						$("#flowList").append(html);
					}
				}else
				{
					var html = "<div class=\"alert alert-info fade in\" style=\"margin-left:20px;margin-right:20px;\">\n"+
                                            "<button class=\"close\" data-dismiss=\"alert\">×</button>\n"+
                                            "<i class=\"fa-fw fa fa-info\"></i>\n"+
                                            "<strong>友情提醒!</strong>请联系管理为您设置需要查询的BPM流程权限!\n"+
                                        "</div>";
					$("#flowList").append(html);
				}
			$(".js-flow-item").each(function(){
				$(this).unbind("click").click(function(){
					advSearchFlow($(this).attr("data-value"));
				})
			});
			}else if(data.status=="100")
			{
				top.layer.msg(data.status);
			}else
			{
				console.log(data.msg);
			}
		}
	});
}

function advSearchFlow(flowId)
{
//	window.location.href="/app/core/bpm/search?view=tablesearch&flowId="+flowId;
	open("/app/core/bpm/search?view=tablesearch&flowId="+flowId,"_self");
}
