package com.core136.service.vehicle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.sys.PageParam;
import com.core136.bean.vehicle.VehicleRepairRecord;
import com.core136.mapper.vehicle.VehicleRepairRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class VehicleRepairRecordService {
@Autowired
private VehicleRepairRecordMapper vehicleRepairRecordMapper;

public int insertVehicleRepairRecord(VehicleRepairRecord vehicleRepairRecord)
{
	return vehicleRepairRecordMapper.insert(vehicleRepairRecord);
}

public int deleteVehicleRepairRecord(VehicleRepairRecord vehicleRepairRecord)
{
	return vehicleRepairRecordMapper.delete(vehicleRepairRecord);
}

public int updateVehicleRepairRecord(Example example,VehicleRepairRecord vehicleRepairRecord)
{
	return vehicleRepairRecordMapper.updateByExampleSelective(vehicleRepairRecord, example);
}

public VehicleRepairRecord selectOneVehicleRepairRecord(VehicleRepairRecord vehicleRepairRecord)
{
	return vehicleRepairRecordMapper.selectOne(vehicleRepairRecord);
}
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
public List<Map<String, String>>getVehicleRepairRecordList(String orgId,String repairUser,String repairType,String beginTime,String endTime,String search)
{
	return vehicleRepairRecordMapper.getVehicleRepairRecordList(orgId, repairUser, repairType, beginTime, endTime, "%"+search+"%");
}
/**
 * 
 * @Title: getVehicleRepairRecordList   
 * @Description: TODO 获取维修列表
 * @param pageParam
 * @param repairUser
 * @param repairType
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getVehicleRepairRecordList(PageParam pageParam,String repairUser,String repairType,String beginTime,String endTime) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getVehicleRepairRecordList(pageParam.getOrgId(),repairUser,repairType,beginTime,endTime,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
