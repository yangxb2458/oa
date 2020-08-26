package com.core136.service.erp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.erp.ErpBom;
import com.core136.bean.erp.ErpBomDetail;
import com.core136.bean.erp.ErpMateriel;
import com.core136.bean.erp.ErpOrder;
import com.core136.bean.erp.ErpOrderDetail;
import com.core136.bean.erp.ErpProduct;

@Service
public class DoCostService {
@Autowired
private ErpOrderService erpOrderService;
@Autowired
private ErpOrderDetailService erpOrderDetailService;
@Autowired
private ErpProductService erpProductService;
@Autowired
private ErpBomDetailService erpBomDetailService;
@Autowired
private ErpMaterielService erpMaterielService;
List<Object>  reutrnList = new ArrayList<Object>();

/**
 * 
 * @Title doCostByProduct   
 * @Description TODO 按产品ID获取物料明细与用量  
 * @param erpProduct
 * @return      
 * Map<String,Object>
 */
public Map<String,Object> doCostByProduct(ErpProduct erpProduct)
{
	Map<String,Object> ptmp = new HashMap<String,Object>();
	erpProduct = erpProductService.selectOne(erpProduct);
	ptmp.put("product", erpProduct);
	ErpBomDetail erpBomDetail = new ErpBomDetail();
	erpBomDetail.setBomId(erpProduct.getBomId());
	erpBomDetail.setOrgId(erpProduct.getOrgId());
	List<ErpBomDetail> erpBomDetailList = erpBomDetailService.getErpBomDetailListByErpBom(erpBomDetail);
	List<Map<String,Object>> erpBomMaterielList =new ArrayList<Map<String,Object>>();
	for(int j=0;j<erpBomDetailList.size();j++)
	{
		//处理下正常的BOM下的料
		Map<String,Object> tmpMap = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(erpBomDetailList.get(j).getMaterielCode()))
			{
			ErpMateriel erpMateriel = erpMaterielService.getErpMaterielByBomDetail(erpBomDetailList.get(j));
			tmpMap.put("erpMateriel", erpMateriel);
			tmpMap.put("bomDetail", erpBomDetailList.get(j));
			tmpMap.put("useCount", erpBomDetailList.get(j).getUseCount());
			tmpMap.put("price", erpMateriel.getPrice());
		}else
		{
			if(StringUtils.isNotEmpty(erpBomDetailList.get(j).getChildBomId()))
			{
				tmpMap.put("useCount", erpBomDetailList.get(j).getUseCount());
				reutrnList =  new ArrayList<Object>();
				getchildBomDetailList(erpBomDetailList.get(j),erpBomDetailList.get(j).getUseCount());
				tmpMap.put("bommx", reutrnList);
			}
		}
		erpBomMaterielList.add(tmpMap);
	}
	ptmp.put("mx", erpBomMaterielList);
	return ptmp;
}
/**
 * 
 * @Title doCostByBom   
 * @Description TODO 按BOMID获取所有物料清单与用量
 * @param erpbom
 * @return      
 * Map<String,Object>
 */
public Map<String,Object> doCostByBom(ErpBom erpbom)
{
	Map<String,Object> ptmp = new HashMap<String,Object>();
	ErpBomDetail erpBomDetail = new ErpBomDetail();
	erpBomDetail.setBomId(erpbom.getBomId());
	erpBomDetail.setOrgId(erpbom.getOrgId());
	List<ErpBomDetail> erpBomDetailList = erpBomDetailService.getErpBomDetailListByErpBom(erpBomDetail);
	List<Map<String,Object>> erpBomMaterielList =new ArrayList<Map<String,Object>>();
	for(int j=0;j<erpBomDetailList.size();j++)
	{
		//处理下正常的BOM下的料
		Map<String,Object> tmpMap = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(erpBomDetailList.get(j).getMaterielCode()))
			{
			ErpMateriel erpMateriel = erpMaterielService.getErpMaterielByBomDetail(erpBomDetailList.get(j));
			tmpMap.put("erpMateriel", erpMateriel);
			tmpMap.put("bomDetail", erpBomDetailList.get(j));
			tmpMap.put("useCount", erpBomDetailList.get(j).getUseCount());
			tmpMap.put("price", erpMateriel.getPrice());
		}else
		{
			if(StringUtils.isNotEmpty(erpBomDetailList.get(j).getChildBomId()))
			{
				tmpMap.put("useCount", erpBomDetailList.get(j).getUseCount());
				reutrnList =  new ArrayList<Object>();
				getchildBomDetailList(erpBomDetailList.get(j),erpBomDetailList.get(j).getUseCount());
				tmpMap.put("bommx", reutrnList);
			}
		}
		erpBomMaterielList.add(tmpMap);
	}
	ptmp.put("mx", erpBomMaterielList);
	return ptmp;
}




/**
 * 
 * @Title doCost   
 * @Description TODO 通过订单获取所有物料与需求量
 * @param eo
 * @return      
 * Map<String,Object>
 */
public Map<String,Object> doCost(ErpOrder eo)
{
	Map<String,Object> returnMap = new HashMap<String, Object>();
	List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
	ErpOrder erpOrder = erpOrderService.selectOne(eo);
	returnMap.put("erpOrder", erpOrder);
	ErpOrderDetail erpOrderDetail = new ErpOrderDetail();
	erpOrderDetail.setOrderId(erpOrder.getOrderId());
	erpOrderDetail.setOrgId(erpOrder.getOrgId());
	//获取订单下的所有产品
	List<ErpOrderDetail> orderDetailList = erpOrderDetailService.getOrderDetailList(erpOrderDetail);
	for(int i=0;i<orderDetailList.size();i++)
	{
		Map<String,Object> ptmp = new HashMap<String,Object>();
		ptmp.put("count", orderDetailList.get(i).getCount());
		ErpProduct erpProduct = new ErpProduct();
		erpProduct.setProductId(orderDetailList.get(i).getProductId());
		erpProduct.setOrgId(orderDetailList.get(i).getOrgId());
		erpProduct = erpProductService.selectOne(erpProduct);
		ptmp.put("product", erpProduct);
		ErpBomDetail erpBomDetail = new ErpBomDetail();
		erpBomDetail.setBomId(erpProduct.getBomId());
		erpBomDetail.setOrgId(erpProduct.getOrgId());
		List<ErpBomDetail> erpBomDetailList = erpBomDetailService.getErpBomDetailListByErpBom(erpBomDetail);
		List<Map<String,Object>> erpBomMaterielList =new ArrayList<Map<String,Object>>();
		for(int j=0;j<erpBomDetailList.size();j++)
		{
			//处理下正常的BOM下的料
			Map<String,Object> tmpMap = new HashMap<String,Object>();
			if(StringUtils.isNotEmpty(erpBomDetailList.get(j).getMaterielCode()))
				{
				ErpMateriel erpMateriel = erpMaterielService.getErpMaterielByBomDetail(erpBomDetailList.get(j));
				tmpMap.put("erpMateriel", erpMateriel);
				tmpMap.put("bomDetail", erpBomDetailList.get(j));
				tmpMap.put("useCount", erpBomDetailList.get(j).getUseCount());
				tmpMap.put("price", erpMateriel.getPrice());
			}else
			{
				if(StringUtils.isNotEmpty(erpBomDetailList.get(j).getChildBomId()))
				{
					tmpMap.put("useCount", erpBomDetailList.get(j).getUseCount());
					reutrnList =  new ArrayList<Object>();
					getchildBomDetailList(erpBomDetailList.get(j),erpBomDetailList.get(j).getUseCount());
					tmpMap.put("bommx", reutrnList);
				}
			}
			erpBomMaterielList.add(tmpMap);
		}
		ptmp.put("mx", erpBomMaterielList);
		returnList.add(ptmp);
	}
	returnMap.put("list", returnList);
	return returnMap;
}

/**
 * 
 * @Title getchildBomDetailList   
 * @Description TODO 递归查找所有物料
 * @param erpBomDetail
 * @param useCount      
 * void
 */
public void getchildBomDetailList(ErpBomDetail erpBomDetail,Double useCount)
{
	ErpBomDetail childErpBomDetail = new ErpBomDetail();
	childErpBomDetail.setBomId(erpBomDetail.getChildBomId());
	childErpBomDetail.setOrgId(erpBomDetail.getOrgId());
	List<ErpBomDetail> childBomDetailList = erpBomDetailService.getErpBomDetailListByErpBom(childErpBomDetail);
	for(int i=0;i<childBomDetailList.size();i++)
	{
		if(StringUtils.isNotEmpty(childBomDetailList.get(i).getMaterielCode()))
		{
			ErpMateriel erpMateriel = erpMaterielService.getErpMaterielByBomDetail(childBomDetailList.get(i));
			Map<String,Object> tmpMap1 = new HashMap<String,Object>();
			tmpMap1.put("erpMateriel", erpMateriel);
			tmpMap1.put("bomDetail", childBomDetailList.get(i));
			tmpMap1.put("useCount", useCount*childBomDetailList.get(i).getUseCount());
			tmpMap1.put("price", erpMateriel.getPrice());
			reutrnList.add(tmpMap1);
		}else
		{
			getchildBomDetailList(childBomDetailList.get(i),useCount*childBomDetailList.get(i).getUseCount());
		}
	}
}

}
