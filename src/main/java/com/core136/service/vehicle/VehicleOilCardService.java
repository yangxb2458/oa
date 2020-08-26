package com.core136.service.vehicle;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.sys.PageParam;
import com.core136.bean.vehicle.VehicleOilCard;
import com.core136.mapper.vehicle.VehicleOilCardMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class VehicleOilCardService {

	@Autowired
	private VehicleOilCardMapper vehicleOilCardMapper;
	
	public int insertVehicleOilCard(VehicleOilCard vehicleOilCard)
	{
		return vehicleOilCardMapper.insert(vehicleOilCard);
	}
	
	public int deleteVehicleOilCard(VehicleOilCard vehicleOilCard)
	{
		return vehicleOilCardMapper.delete(vehicleOilCard);
	}
	
	public int updateVehicleOilCard(Example example,VehicleOilCard vehicleOilCard)
	{
		return vehicleOilCardMapper.updateByExampleSelective(vehicleOilCard, example);
	}
	
	public VehicleOilCard selectOneVehicleOilCard(VehicleOilCard vehicleOilCard)
	{
		return vehicleOilCardMapper.selectOne(vehicleOilCard);
	}
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
	public List<Map<String,String>>getVehicleOilCardList(String orgId,String oilType,String beginTime,String endTime,String search)
	{
		return vehicleOilCardMapper.getVehicleOilCardList(orgId, oilType, beginTime, endTime, "%"+search+"%");
	}
	/**
	 * 
	 * @Title: getVehicleOilCardList   
	 * @Description: TODO 获取油卡列表
	 * @param pageParam
	 * @param oilType
	 * @param beginTime
	 * @param endTime
	 * @return
	 * PageInfo<Map<String,String>>    
	 * @throws
	 */
	public PageInfo<Map<String, String>> getVehicleOilCardList(PageParam pageParam,String oilType,String beginTime,String endTime) 
	{
		PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= getVehicleOilCardList(pageParam.getOrgId(),oilType,beginTime,endTime,pageParam.getSearch());
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
	}
/**
 * 
 * @Title: getCanUsedOilCardList   
 * @Description: TODO 获取可用油卡列表
 * @param orgId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
	public List<Map<String,String>>getCanUsedOilCardList(String orgId)
	{
		return vehicleOilCardMapper.getCanUsedOilCardList(orgId);
	}
}
