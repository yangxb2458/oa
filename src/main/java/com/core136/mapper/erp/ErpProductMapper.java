package com.core136.mapper.erp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.erp.ErpProduct;
import org.core136.common.dbutils.MyMapper;
@Mapper
public interface ErpProductMapper extends MyMapper<ErpProduct>{

	/**
	 * 
	 * @Title selectProductByName
	 * @Description TODO 按名称模糊查询产品 
	 * @param productName
	 * @param orgId
	 * @return      
	 * ErpProduct
	 */
	public List<ErpProduct> selectProductByName(@Param(value = "productName") String productName,@Param(value="orgId") String orgId);
	
	/**
	 * 
	 * @Title getProuctAndBomInfoByProductId   
	 * @Description TODO 获取产品与BOM的对应信息
	 * @param productId
	 * @param orgId
	 * @return      
	 * List<Map<String,Object>>
	 */
	public Map<String,Object> getProuctAndBomInfoByProductId(@Param(value = "productId") String productId,@Param(value="orgId") String orgId);
	
	/**
	 * 
	* @Title: getProductSelect2 
	* @Description: TODO 获取产品的select2列表
	* @param @param orgId
	* @param @param search
	* @param @return 设定文件 
	* @return List<Map<String,Object>> 返回类型 

	 */
	public List<Map<String,Object>> getProductSelect2(@Param(value = "orgId") String orgId,@Param(value="search") String search);
	
}
