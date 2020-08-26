package com.core136.mapper.erp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.erp.ErpUnit;
import org.core136.common.dbutils.MyMapper;
@Mapper
public interface ErpUnitMapper extends MyMapper<ErpUnit>{

	/**
	 * 
	 * @Title: getAllUnit   
	 * @Description: TODO 获取所有的计量单位
	 * @param: orgId
	 * @param: @return      
	 * @return: List<ErpUnit>      

	 */
	public List<ErpUnit> getAllUnit(@Param (value="orgId") String orgId);
	
	
}
