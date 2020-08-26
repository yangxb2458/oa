package com.core136.mapper.vehicle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.vehicle.VehicleApply;

@Mapper
public interface VehicleApplyMapper extends MyMapper<VehicleApply>{
	/**
	 * 
	 * @Title: getVehicleApplyList   
	 * @Description: TODO 获取申请记录
	 * @param orgId
	 * @param opFlag
	 * @param status
	 * @param accountId
	 * @param createUser
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
public List<Map<String, String>>getVehicleApplyList(
		@Param(value="orgId")String orgId,
		@Param(value="opFlag")String opFlag,
		@Param(value="status")String status,
		@Param(value="accountId")String accountId,
		@Param(value="createUser")String createUser,
		@Param(value="beginTime")String beginTime,
		@Param(value="endTime")String endTime,
		@Param(value="search")String search
		);
	
/**
 * 
 * @Title: getVehicleApprovedList   
 * @Description: TODO 获取待审批列表
 * @param orgId
 * @param opFlag
 * @param accountId
 * @param createUser
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getVehicleApprovedList(
		@Param(value="orgId")String orgId,
		@Param(value="opFlag")String opFlag,
		@Param(value="accountId")String accountId,
		@Param(value="createUser")String createUser,
		@Param(value="beginTime")String beginTime,
		@Param(value="endTime")String endTime,
		@Param(value="search")String search
		);
}
