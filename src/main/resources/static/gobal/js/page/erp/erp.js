function createErpUnit()
{
	$.ajax({
		url : "/ret/erpget/getAllErpUnit",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status="200")
				{
				var datalist = data.list;
				for(var i=0;i<datalist.length;i++)
				{
					$("#unit").append("<option value=\""+datalist[i].unitId+"\">"+datalist[i].cnName+"  |  "+datalist[i].enName+"</option>");
				}
				}else
					{
					console.log(data.msg);
					}
		}
	});
}

function getUnitName(id)
{
	if(id==1)
		{
		return "米";
		}else if(id==2)
			{
			return "千克";
			}
		else if(id==3)
		{
			return "立方米";
		}
		else if(id==4)
		{
			return "平方米";
		}
		else if(id==5)
		{
			return "升";
		}
		else if(id==6)
		{
			return "个";
		}
		else if(id==7)
		{
			return "套";
		}
		else if(id==8)
		{
			return "台";
		}else if(id==9)
		{
			return "小时";
		}
		else
		{
			return "其它";
		}
	}

function getPayType(id)
{
	if(id==1)
	{
	return "现金";
	}else if(id==2)
		{
		return "账期一个月";
		}
	else if(id==3)
	{
		return "账期二个月";
	}
	else if(id==4)
	{
		return "账期三个月";
	}
	else if(id==5)
	{
		return "账期六个月";
	}
	else
	{
		return "其它";
	}
	}

function getCostMx(orderId)
{
	var returndata;
	$.ajax({
		url : "/ret/erpget/doCostByOrder",
		type : "post",
		dataType : "json",
		async:false,
		data:{orderId:orderId},
		success : function(data) {
			returndata=data.list;
		}
	});
	return returndata;
}

function getCostMxByProductId(productId)
{
	var returndata;
	$.ajax({
		url : "/ret/erpget/doCostByProduct",
		type : "post",
		dataType : "json",
		async:false,
		data:{productId:productId},
		success : function(data) {
			returndata=data.list;
		}
	});
	return returndata;
}

function getCostMxByBomId(bomId)
{
	var returndata;
	$.ajax({
		url : "/ret/erpget/doCostByBom",
		type : "post",
		dataType : "json",
		async:false,
		data:{bomId:bomId},
		success : function(data) {
			returndata=data.list;
		}
	});
	return returndata;
}


function doCost(orderId)
{
	var returndata;
	$.ajax({
		url : "/ret/erpget/doCostByOrder",
		type : "post",
		dataType : "json",
		async:false,
		data:{orderId:orderId},
		success : function(data) {
			returndata=Calculation(data.list)
		}
	});
	return returndata;
}

function getUnitPric(mxlist)
{
	var wltotal=0;
	var returnjson ={};
	var flag=true;
	var emx=[];
	for(var j=0;j<mxlist.length;j++)
	{
		
		if(mxlist[j].price&&mxlist[j].bomDetail.useCount)
			{
			wltotal+=(mxlist[j].price)*(mxlist[j].bomDetail.useCount);
			}else
				{
				var ej={};
				if(mxlist[j].bommx)
					{
					for(var k=0;k<mxlist[j].bommx.length;k++)
						{
						if(mxlist[j].bommx[k].erpMateriel.price)
							{
							wltotal+=(mxlist[j].bommx[k].price)*(mxlist[j].bommx[k].useCount);
							}else
								{
								ej.materielId=mxlist[j].bommx[k].erpMateriel.materielId;
								ej.materielCode=mxlist[j].bommx[k].erpMateriel.materielCode;
								ej.materielName=mxlist[j].bommx[k].erpMateriel.materielName;
								ej.useCount = mxlist[j].bommx[k].useCount;
								ej.price=mxlist[j].bommx[k].price
								emx.push(ej);
								flag = false;
								}
						}
					}else
						{
						ej.materielId=mxlist[j].erpMateriel.materielId;
						ej.materielCode=mxlist[j].erpMateriel.materielCode;
						ej.materielName=mxlist[j].erpMateriel.materielName;
						ej.useCount = mxlist[j].useCount;
						ej.price=mxlist[j].price
						emx.push(ej);
						flag = false;
						}
				}
	}
	returnjson.unitPrice=wltotal;
	returnjson.flag=flag;
	returnjson.emx=emx;
	return returnjson;
}

function Calculation(data)
{
	var erpOrder = data.erpOrder;
	var dataList = data.list;
	var zs=0;
	for(var i=0;i<dataList.length;i++)
		{
			var mxlist = dataList[i].mx;
			//核算单一物料总价
			var wltotal=0;
				for(var j=0;j<mxlist.length;j++)
				{
					if(mxlist[j].bomDetail)
						{
						wltotal+=(mxlist[j].price)*(mxlist[j].bomDetail.useCount);
						}else
							{
							var bommxlist = mxlist[j].bommx;
							for(var k=0;k<bommxlist.length;k++)
								{
								wltotal+=(bommxlist[k].price)*(bommxlist[k].useCount);
								}
							}
				}
			//单一产品总价
			zs+=wltotal	* (dataList[i].count)
		}
	try
	{
		zs+=erpOrder.tax;
	}catch (e) {
	}
	try
	{
		zs+=erpOrder.packCharges;
	}catch (e) {
	}
	try
	{
		zs+=erpOrder.freight;
	}catch (e) {
	}
	try
	{
		zs+=erpOrder.otherCharges;
	}catch (e) {
	}
	return zs;
}
