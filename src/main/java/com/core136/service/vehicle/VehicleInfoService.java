package com.core136.service.vehicle;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.sys.PageParam;
import com.core136.bean.vehicle.VehicleInfo;
import com.core136.mapper.vehicle.VehicleInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class VehicleInfoService {
@Autowired
private VehicleInfoMapper vehicleInfoMapper;

public int insertVehicleInfo(VehicleInfo vehicleInfo)
{
	return vehicleInfoMapper.insert(vehicleInfo);
}
public int deleteVehicleInfo(VehicleInfo vehicleInfo)
{
	return vehicleInfoMapper.delete(vehicleInfo);
}
public int updateVehicleInfo(Example example,VehicleInfo vehicleInfo)
{
	return vehicleInfoMapper.updateByExampleSelective(vehicleInfo, example);
}
public VehicleInfo selectOneVehicleInfo(VehicleInfo vehicleInfo)
{
	return vehicleInfoMapper.selectOne(vehicleInfo);
}

/**
 * 
 * @Title: getAllVehicleList   
 * @Description: TODO 获取所有车辆
 * @param vehicleInfo
 * @return
 * List<VehicleInfo>    
 * @throws
 */
public List<VehicleInfo>getAllVehicleList(VehicleInfo vehicleInfo)
{
	return vehicleInfoMapper.select(vehicleInfo);
}

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
public List<Map<String, String>>getManageVehicleInfoList(String orgId,String onwer,String type,String nature,String beginTime,String endTime,String beginTime1,String endTime1,String search)
{
	return vehicleInfoMapper.getManageVehicleInfoList(orgId, onwer, type, nature, beginTime, endTime, beginTime1, endTime1, "%"+search+"%");
}

/**
 * 
 * @Title: getManageVehicleInfoList   
 * @Description: TODO 获取车辆列表
 * @param pageParam
 * @param onwer
 * @param type
 * @param nature
 * @param beginTime
 * @param endTime
 * @param beginTime1
 * @param endTime1
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getManageVehicleInfoList(PageParam pageParam,String onwer,String type,String nature,String beginTime,String endTime,String beginTime1,String endTime1) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getManageVehicleInfoList(pageParam.getOrgId(),onwer,type,nature,beginTime,endTime,beginTime1,endTime1,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getCanUsedVehicleList   
 * @Description: TODO 获取可调度车辆列表
 * @param orgId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getCanUsedVehicleList(String orgId)
{
	return vehicleInfoMapper.getCanUsedVehicleList(orgId);
}
}
