/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  OfficeSuppliesUnitMapper.java   
 * @Package com.core136.mapper.officesupplies   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月18日 下午12:26:25   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.officesupplies;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.officesupplies.OfficeSuppliesUnit;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface OfficeSuppliesUnitMapper extends MyMapper<OfficeSuppliesUnit>{
	/**
	 * 
	 * @Title: getAllUnit   
	 * @Description: TODO 获取办公用品单位列表 
	 * @param: orgId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getAllUnit(@Param(value="orgId") String orgId);
	
	/**
	 * 
	 * @Title: getOfficeSuppliesUnitList   
	 * @Description: TODO 获取办公用品单位列表
	 * @param: orgId
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String, String>>  
	 * @throws
	 */
	public List<Map<String, String>> getOfficeSuppliesUnitList(@Param(value="orgId") String orgId,@Param(value="search") String search);
	
	
	
}
