package com.core136.mapper.fixedassets;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.fixedassets.FixedAssetsRepair;

@Mapper
public interface FixedAssetsRepairMapper extends MyMapper<FixedAssetsRepair>{
/**
 * 
 * @Title: getFixedAssetsRepairList   
 * @Description: TODO 获取维修列表
 * @param: orgId
 * @param: beginTime
 * @param: endTime
 * @param: opFlag
 * @param: assetsSortId
 * @param: status
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getFixedAssetsRepairList(
		@Param(value="orgId")String orgId,
		@Param(value="accountId") String accountId,
		@Param(value="beginTime") String beginTime,
		@Param(value="endTime") String endTime,
		@Param(value="opFlag") String opFlag,
		@Param(value="assetsSortId") String assetsSortId,
		@Param(value="status") String status,
		@Param(value="search") String search
		);
}
