package com.core136.mapper.erp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.erp.ErpEquipmentSort;
import org.core136.common.dbutils.MyMapper;

@Mapper
public interface ErpEquipmentSortMapper extends MyMapper<ErpEquipmentSort>{
	
	/**
	 * 
	 * @Title getErpEquipmentSortTree   
	 * @Description TODO 获取设备分类树结构
	 * @param sortLeave
	 * @param orgId
	 * @return      
	 * List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getErpEquipmentSortTree(@Param(value="sortLeave") String sortLeave,@Param(value="orgId") String orgId);
	
	/**
	 * 
	 * @Title isExistChild   
	 * @Description TODO 判断分类下是否有子分类
	 * @param sortId
	 * @param orgId
	 * @return      
	 * int
	 */
	public int isExistChild(@Param(value="sortId") String sortId,@Param(value="orgId") String orgId);
}
