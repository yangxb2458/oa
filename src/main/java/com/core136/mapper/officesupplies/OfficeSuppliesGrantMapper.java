package com.core136.mapper.officesupplies;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.officesupplies.OfficeSuppliesGrant;
import org.core136.common.dbutils.MyMapper;
@Mapper
public interface OfficeSuppliesGrantMapper extends MyMapper<OfficeSuppliesGrant>{
	/**
	 * 
	 * @Title: getGrantCount   
	 * @Description: TODO 统计已发放了多少办公用品
	 * @param: orgId
	 * @param: applyId
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
public int getGrantCount(@Param(value="orgId") String orgId,@Param(value="applyId") String applyId);
}
