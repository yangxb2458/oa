package com.core136.mapper.erp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.erp.ErpMaterielSort;
import org.core136.common.dbutils.MyMapper;
@Mapper
public interface ErpMaterielSortMapper extends MyMapper<ErpMaterielSort>{

	/**
	 * 
	 * @Title: getErpMaterielSortParent   
	 * @Description: TODO 获取物料父级Id 
	 * @param: orgId
	 * @param: @return      
	 * @return: List<ErpMaterielSort>      

	 */
	public List<Map<String,Object>> getErpMaterielSortTree(@Param(value="sortLeave") String sortLeave,@Param(value="orgId") String orgId);	
	
	/**
	 * 
	 * @Title: isExistChild   
	 * @Description: 判断当前分类下面是否还有子分类  
	 * @param: sortId
	 * @param: orgId
	 * @param: @return      
	 * @return: int      

	 */
	public int isExistChild(@Param(value="sortId") String sortId,@Param(value="orgId") String orgId);
}
