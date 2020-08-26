package com.core136.mapper.erp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.erp.ErpBomSort;
import org.core136.common.dbutils.MyMapper;

@Mapper
public interface ErpBomSortMapper extends MyMapper<ErpBomSort>{
	/**
	 * 
	 * @Title getErpBomSortTree   
	 * @Description TODO 获取Bom分类父级Id
	 * @param sortLeave
	 * @param orgId
	 * @return      
	 * List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getErpBomSortTree(@Param(value="sortLeave") String sortLeave,@Param(value="orgId") String orgId);
	/**
	 * 
	 * @Title isExistChild   
	 * @Description TODO 判断当前分类下面是否还有子分类 
	 * @param sortId
	 * @param orgId
	 * @return      
	 * int
	 */
	public int isExistChild(@Param(value="sortId") String sortId,@Param(value="orgId") String orgId);
}
