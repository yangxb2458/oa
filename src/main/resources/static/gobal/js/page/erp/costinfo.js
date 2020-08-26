$(function(){
	var data= getCostMx(orderId);
	for(id in data.erpOrder)
		{
		if(id=="attach")
			{
			$("#attach").attr("data_value",data.erpOrder[id]);
			}else if(id=="payType")
				{
				$("#payType").html(getPayType(data.erpOrder.payType));
				}else
				{
				$("#"+id).html(data.erpOrder[id]);
				}
		}
	createAttach("attach");
	var orderInfo = data.erpOrder;
	var mxlist = data.list;
	var hj=0;
	var zs=0;
	for(var i=0;i<mxlist.length;i++)
		{
			var mxtag =getUnitPric(mxlist[i].mx);
		 	var html="<tr>";
			html+="<td>"+(i+1)+"</td><td>"+mxlist[i].product.productName+"</td><td>"+mxlist[i].product.model+"</td><td>"+mxlist[i].count+"/"+getUnitName(mxlist[i].product.unit)+"</td>";
			if(mxtag.flag)
				{
					html+="<td>"+(mxtag.unitPrice)+"/元(RMB)</td>";	
				}else
					{
					html+="<td><a style=\"color:red;cursor: pointer;\" title='相关物料价格缺失,点击查看'>"+(mxtag.unitPrice)+"/元(RMB)</a></td>";	
					}
			hj+=mxtag.unitPrice*mxlist[i].count;
			html+="<td>"+(mxtag.unitPrice*mxlist[i].count)+"/元(RMB)</td><td><a style=\"cursor: pointer;\" onclick=\"readcostmx('"+mxlist[i].product.productId+"')\">查看成本组成</a></td>"	
			html+="</tr>";
			$("#costinfomx").append(html);
		}
	$("#costinfomx").append("<tr><td colspan='5' align='right'>合计:</td><td colspan='2'>"+hj+"/元(RMB)</td></tr>");
	$("#costinfomx").append("<tr><td colspan='5' align='right'>包装费:</td><td colspan='2'>"+orderInfo.packCharges+"/元(RMB)</td></tr>");
	$("#costinfomx").append("<tr><td colspan='5' align='right'>运输费:</td><td colspan='2'>"+orderInfo.freight+"/元(RMB)</td></tr>");
	$("#costinfomx").append("<tr><td colspan='5' align='right'>税费:</td><td colspan='2'>"+orderInfo.tax+"/元(RMB)</td></tr>");
	$("#costinfomx").append("<tr><td colspan='5' align='right'>其它费用:</td><td colspan='2'>"+orderInfo.otherCharges+"/元(RMB)</td></tr>");
	zs=hj
	try
	{
		zs+=orderInfo.tax;
	}catch (e) {
	}
	try
	{
		zs+=orderInfo.packCharges;
	}catch (e) {
	}
	try
	{
		zs+=orderInfo.freight;
	}catch (e) {
	}
	try
	{
		zs+=orderInfo.otherCharges;
	}catch (e) {
	}
	$("#costinfomx").append("<tr><td colspan='5' align='right'>总计:</td><td colspan='2'>"+zs+"/元(RMB)</td></tr>");
});

function readcostmx(productId)
{
	window.open("/app/core/erp/cost/pirceinfo?productId="+productId);
	}
