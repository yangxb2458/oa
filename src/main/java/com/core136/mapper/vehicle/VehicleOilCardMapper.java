package com.core136.mapper.vehicle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.vehicle.VehicleOilCard;

@Mapper
public interface VehicleOilCardMapper extends MyMapper<VehicleOilCard>{

	/**
	 * 
	 * @Title: getVehicleOilCardList   
	 * @Description: TODO 获取油卡列表
	 * @param orgId
	 * @param oilType
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getVehicleOilCardList(@Param(value="orgId")String orgId,
			@Param(value="oilType")String oilType,
			@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,
			@Param(value="search")String search
			);
	/**
	 * 
	 * @Title: getCanUsedOilCardList   
	 * @Description: TODO 获取可表油卡列表
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getCanUsedOilCardList(@Param(value="orgId")String orgId);
}
