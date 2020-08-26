/**  
 * All rights Reserved, Designed By www.tydic.com
 * @Title:  OfficeSuppliesSortMapper.java   
 * @Package com.core136.mapper.officesupplies   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月18日 上午10:47:08   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.officesupplies;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.officesupplies.OfficeSuppliesSort;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface OfficeSuppliesSortMapper extends MyMapper<OfficeSuppliesSort>{

	/**
	 * 
	 * @Title: getOfficeSuppliesSortTree   
	 * @Description: TODO 获取办公用户分类
	 * @param: orgId
	 * @param: parentId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getOfficeSuppliesSortTree(@Param(value="orgId") String orgId,@Param(value="parentId") String parentId);
	/**
	 * 
	 * @Title: isExistChild   
	 * @Description: TODO 判断分类下是否有子节点
	 * @param: orgId
	 * @param: parentId
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	public int isExistChild(@Param(value="orgId") String orgId,@Param(value="parentId") String parentId);
}
