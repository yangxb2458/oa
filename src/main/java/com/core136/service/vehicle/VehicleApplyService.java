package com.core136.service.vehicle;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.sys.PageParam;
import com.core136.bean.vehicle.VehicleApply;
import com.core136.mapper.vehicle.VehicleApplyMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class VehicleApplyService {

	@Autowired
	private VehicleApplyMapper vehicleApplyMapper;
	
	public int insertVehicleApply(VehicleApply vehicleApply)
	{
		return vehicleApplyMapper.insert(vehicleApply);
	}
	
	public int deleteVehicleApply(VehicleApply vehicleApply)
	{
		return vehicleApplyMapper.delete(vehicleApply);
	}
	
	public int updateVehicleApply(Example example,VehicleApply vehicleApply)
	{
		return vehicleApplyMapper.updateByExampleSelective(vehicleApply, example);
	}
	
	public VehicleApply selectOneVehicleApply(VehicleApply vehicleApply)
	{
		return vehicleApplyMapper.selectOne(vehicleApply);
	}
	
	/**
	 * 
	 * @Title: getVehicleApplyList   
	 * @Description: TODO 获取申请记录
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
	public List<Map<String, String>> getVehicleApplyList(String orgId,String opFlag,String status,String accountId,String createUser,String beginTime,String endTime,String search)
	{
		return vehicleApplyMapper.getVehicleApplyList(orgId, opFlag,status, accountId, createUser, beginTime, endTime, "%"+search+"%");
	}
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
	public List<Map<String, String>> getVehicleApprovedList(String orgId,String opFlag,String accountId,String createUser,String beginTime,String endTime,String search)
	{
		return vehicleApplyMapper.getVehicleApprovedList(orgId, opFlag, accountId, createUser, beginTime, endTime, "%"+search+"%");
	}
	
	/**
	 * 
	 * @Title: getVehicleApplyList   
	 * @Description: TODO 获取申请记录
	 * @param pageParam
	 * @param createUser
	 * @param beginTime
	 * @param endTime
	 * @return
	 * PageInfo<Map<String,String>>    
	 * @throws
	 */
	public PageInfo<Map<String, String>> getVehicleApplyList(PageParam pageParam,String status,String createUser,String beginTime,String endTime) 
	{
		PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= getVehicleApplyList(pageParam.getOrgId(),pageParam.getOpFlag(),status,pageParam.getAccountId(),createUser,beginTime,endTime,pageParam.getSearch());
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
	}
	
	/**
	 * 
	 * @Title: getVehicleApprovedList   
	 * @Description: TODO 获取待审批列表
	 * @param pageParam
	 * @param createUser
	 * @param beginTime
	 * @param endTime
	 * @return
	 * PageInfo<Map<String,String>>    
	 * @throws
	 */
	public PageInfo<Map<String, String>> getVehicleApprovedList(PageParam pageParam,String createUser,String beginTime,String endTime) 
	{
		PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= getVehicleApprovedList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(),createUser,beginTime,endTime,pageParam.getSearch());
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
	}
}
