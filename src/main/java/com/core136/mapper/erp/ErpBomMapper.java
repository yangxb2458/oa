package com.core136.mapper.erp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.erp.ErpBom;
import com.core136.bean.erp.ErpMateriel;
import org.core136.common.dbutils.MyMapper;

@Mapper
public interface ErpBomMapper extends MyMapper<ErpBom>{

	/**
	 * 
	 * @Title getErpBomListBySortId   
	 * @Description TODO 按分类获取BOM树结构
	 * @param sortId
	 * @param orgId
	 * @return      
	 * List<Map<String,Object>>
	 */
public List<Map<String,Object>> getErpBomTreeBySortId(@Param(value="sortId") String sortId,@Param(value="orgId") String orgId);

/**
 * 
 * @Title selectBomList2ById   
 * @Description TODO 获取BOM清单用于SELECT2插件
 * @param bomName
 * @param orgId
 * @return      
 * List<ErpMateriel>
 */
public List<ErpMateriel> selectBomList2ById(@Param(value="bomName") String bomName,@Param(value="orgId") String orgId);

}
