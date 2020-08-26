package com.core136.mapper.vehicle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.vehicle.VehicleInfo;

@Mapper
public interface VehicleInfoMapper extends MyMapper<VehicleInfo>{

	/**
	 * 
	 * @Title: getManageVehicleInfoList   
	 * @Description: TODO 获取车辆列表
	 * @param orgId
	 * @param onwer
	 * @param type
	 * @param nature
	 * @param beginTime
	 * @param endTime
	 * @param beginTime1
	 * @param endTime1
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getManageVehicleInfoList(
			@Param(value="orgId")String orgId,
			@Param(value="onwer")String onwer,
			@Param(value="type")String type,
			@Param(value="nature")String nature,
			@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,
			@Param(value="beginTime1")String beginTime1,
			@Param(value="endTime1")String endTime1,
			@Param(value="search")String search
			);
	
	/**
	 * 
	 * @Title: getCanUsedVehicleList   
	 * @Description: TODO 获取可调度车辆列表
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getCanUsedVehicleList(@Param(value="orgId")String orgId);
}
