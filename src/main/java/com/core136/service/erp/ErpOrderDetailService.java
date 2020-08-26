package com.core136.service.erp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.erp.ErpOrderDetail;
import com.core136.mapper.erp.ErpOrderDetailMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class ErpOrderDetailService{
@Autowired
private ErpOrderDetailMapper erpOrderDetailMapper;
	/**
	 * 
	 */
/**
 * 获取订单产品列表
 */
public PageInfo<Map<String, Object>> getErpOrderDetailList(String orderId, String search, String orgId, Integer pageNumber,
		Integer pageSize, String sortStr) {
	PageHelper.startPage(pageNumber, pageSize);
	List<Map<String,Object>> datalist= erpOrderDetailMapper.getErpOrderDetail(orderId, search, orgId);
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}



public List<Map<String, Object>> getErpOrderDetail(String orderId, String search, String orgId) {
	// TODO Auto-generated method stub
	return erpOrderDetailMapper.getErpOrderDetail(orderId, search, orgId);
}

/**
 * 
 * @Title insertErpOrderDetail   
 * @Description TODO 添加订单中的产品
 * @param erpOrderDetail
 * @return      
 * int
 */
public int insertErpOrderDetail(ErpOrderDetail erpOrderDetail)
{
	return erpOrderDetailMapper.insert(erpOrderDetail);
}

/**
 * 
 * @Title deleteErpOrderDetail   
 * @Description TODO 删除订中的产品
 * @param erpOrderDetail
 * @return      
 * int
 */
public int deleteErpOrderDetail(ErpOrderDetail erpOrderDetail)
{
	return erpOrderDetailMapper.delete(erpOrderDetail);
}
/**
 * 
 * @Title getErpProductList   
 * @Description TODO 获取订单中的产品列表
 * @param erpOrderDetail
 * @return      
 * List<ErpOrderDetail>
 */
public List<ErpOrderDetail> getOrderDetailList(ErpOrderDetail erpOrderDetail)
{
	return erpOrderDetailMapper.select(erpOrderDetail);
}
}
