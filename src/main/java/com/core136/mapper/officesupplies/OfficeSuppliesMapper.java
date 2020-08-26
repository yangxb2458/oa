/**  
 * All rights Reserved, Designed By www.tydic.com
 * @Title:  OfficeSuppliesMapper.java   
 * @Package com.core136.mapper.officesupplies   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月18日 上午10:45:56   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.officesupplies;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.officesupplies.OfficeSupplies;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface OfficeSuppliesMapper extends MyMapper<OfficeSupplies>{

	/**
	 * 
	 * @Title: getOfficeSupplieslistBySortId   
	 * @Description: TODO 获取办公用品列表
	 * @param: orgId
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getOfficeSupplieslistBySortId(@Param(value="orgId") String orgId,@Param(value="sortId") String sortId,@Param(value="search") String search);
	
	/**
	 * 
	 * @Title: getApplyOfficeSupplieslist   
	 * @Description: TODO 获取可以领用的办公用品列表  
	 * @param: orgId
	 * @param: sortId
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getApplyOfficeSupplieslist(@Param(value="orgId") String orgId,@Param(value="sortId") String sortId,
			@Param(value="deptId") String deptId,@Param(value="search") String search);
}
