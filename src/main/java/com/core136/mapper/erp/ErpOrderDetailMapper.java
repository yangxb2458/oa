package com.core136.mapper.erp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.erp.ErpOrderDetail;
import org.core136.common.dbutils.MyMapper;
@Mapper
public interface ErpOrderDetailMapper extends MyMapper<ErpOrderDetail>{
	
	/**
	 * 
	 * @Title getErpOrderDetail   
	 * @Description TODO 获取订单详情
	 * @param orderId
	 * @param search
	 * @param orgId
	 * @return      
	 * List<Map<String,String>>
	 */
	public List<Map<String,Object>> getErpOrderDetail(@Param(value="orderId") String orderId,@Param(value="search") String search,@Param(value="orgId") String orgId);
}
