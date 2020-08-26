package com.core136.mapper.vehicle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.vehicle.VehicleRepairRecord;


@Mapper
public interface VehicleRepairRecordMapper extends MyMapper<VehicleRepairRecord>{

	/**
	 * 
	 * @Title: getVehicleRepairRecordList   
	 * @Description: TODO 获取维修列表
	 * @param orgId
	 * @param repairUser
	 * @param repairType
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getVehicleRepairRecordList(
			@Param(value="orgId")String orgId,@Param(value="repairUser")String repairUser,
			@Param(value="repairType")String repairType,@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,@Param(value="search")String search
			);
}
