package com.core136.controller.vehicle;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.sys.PageParam;
import com.core136.bean.vehicle.VehicleApply;
import com.core136.bean.vehicle.VehicleInfo;
import com.core136.bean.vehicle.VehicleOilCard;
import com.core136.bean.vehicle.VehicleOperator;
import com.core136.bean.vehicle.VehicleRepairRecord;
import com.core136.service.account.AccountService;
import com.core136.service.vehicle.VehicleApplyService;
import com.core136.service.vehicle.VehicleInfoService;
import com.core136.service.vehicle.VehicleOilCardService;
import com.core136.service.vehicle.VehicleOperatorService;
import com.core136.service.vehicle.VehicleRepairRecordService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/ret/vehicleget")
public class RoutGetVehicleController {
	@Autowired
	private VehicleInfoService vehicleInfoService;
	@Autowired
	private VehicleApplyService vehicleApplyService;
	@Autowired
	private VehicleOperatorService vehicleOperatorService;
	@Autowired
	private VehicleOilCardService vehicleOilCardService;
	@Autowired
	private VehicleRepairRecordService vehicleRepairRecordService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: getAllVehicleList   
	 * @Description: TODO 获取所有车辆列表
	 * @param request
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getAllVehicleList",method=RequestMethod.POST)
	public RetDataBean getAllVehicleList(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			VehicleInfo vehicleInfo = new VehicleInfo();
			vehicleInfo.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", vehicleInfoService.getAllVehicleList(vehicleInfo));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getCanUsedVehicleList   
	 * @Description: TODO 获取可调度车辆
	 * @param request
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getCanUsedVehicleList",method=RequestMethod.POST)
	public RetDataBean getCanUsedVehicleList(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", vehicleInfoService.getCanUsedVehicleList(account.getOrgId()));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getVehicleRepairRecordById   
	 * @Description: TODO 获取维修记录详情
	 * @param request
	 * @param vehicleRepairRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getVehicleRepairRecordById",method=RequestMethod.POST)
	public RetDataBean getVehicleRepairRecordById(HttpServletRequest request,VehicleRepairRecord vehicleRepairRecord)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			vehicleRepairRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", vehicleRepairRecordService.selectOneVehicleRepairRecord(vehicleRepairRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getVehicleOilCardById   
	 * @Description: TODO 获取油卡信息详情
	 * @param request
	 * @param vehicleOilCard
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getVehicleOilCardById",method=RequestMethod.POST)
	public RetDataBean getVehicleOilCardById(HttpServletRequest request,VehicleOilCard vehicleOilCard)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			vehicleOilCard.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", vehicleOilCardService.selectOneVehicleOilCard(vehicleOilCard));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getCanUsedOilCardList   
	 * @Description: TODO 获取可通的油卡列表
	 * @param request
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getCanUsedOilCardList",method=RequestMethod.POST)
	public RetDataBean getCanUsedOilCardList(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!", vehicleOilCardService.getCanUsedOilCardList(account.getOrgId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getVehicleOperatorByOrgId   
	 * @Description: TODO 获取车辆调度人员
	 * @param request
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getVehicleOperatorByOrgId",method=RequestMethod.POST)
	public RetDataBean getVehicleOperatorByOrgId(HttpServletRequest request)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			VehicleOperator vehicleOperator = new VehicleOperator();
			vehicleOperator.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", vehicleOperatorService.selectOneVehicleOperator(vehicleOperator));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getVehicleApplyById   
	 * @Description: TODO 获取车辆使用申请详情
	 * @param request
	 * @param vehicleApply
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getVehicleApplyById",method=RequestMethod.POST)
	public RetDataBean getVehicleApplyById(HttpServletRequest request,VehicleApply vehicleApply)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			vehicleApply.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", vehicleApplyService.selectOneVehicleApply(vehicleApply));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getVehicleInfoById   
	 * @Description: TODO 获取车辆信息详情
	 * @param request
	 * @param vehicleInfo
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getVehicleInfoById",method=RequestMethod.POST)
	public RetDataBean getVehicleInfoById(HttpServletRequest request,VehicleInfo vehicleInfo)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			vehicleInfo.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!", vehicleInfoService.selectOneVehicleInfo(vehicleInfo));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getManageVehicleInfoList   
	 * @Description: TODO 获取车辆列表
	 * @param request
	 * @param pageParam
	 * @param onwer
	 * @param type
	 * @param nature
	 * @param beginTime
	 * @param endTime
	 * @param beginTime1
	 * @param endTime1
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getManageVehicleInfoList",method=RequestMethod.POST)
	public RetDataBean getManageVehicleInfoList(HttpServletRequest request,PageParam pageParam,
			String onwer,String type,String nature,String beginTime,String endTime,String beginTime1,String endTime1
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("CREATE_TIME");
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("desc");
			}
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=vehicleInfoService.getManageVehicleInfoList(pageParam, onwer, type, nature, beginTime, endTime, beginTime1, endTime1);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: getVehicleRepairRecordList   
	 * @Description: TODO 获取维修列表
	 * @param request
	 * @param pageParam
	 * @param repairType
	 * @param repairUser
	 * @param beginTime
	 * @param endTime
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getVehicleRepairRecordList",method=RequestMethod.POST)
	public RetDataBean getVehicleRepairRecordList(HttpServletRequest request,PageParam pageParam,
			String repairType,String repairUser,String beginTime,String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("CREATE_TIME");
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("desc");
			}
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=vehicleRepairRecordService.getVehicleRepairRecordList(pageParam, repairUser,repairType, beginTime, endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getVehicleApplyList   
	 * @Description: TODO 获取申请记录
	 * @param request
	 * @param pageParam
	 * @param createUser
	 * @param beginTime
	 * @param endTime
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getVehicleApplyList",method=RequestMethod.POST)
	public RetDataBean getVehicleApplyList(HttpServletRequest request,PageParam pageParam,String status,
			String createUser,String beginTime,String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("CREATE_TIME");
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("desc");
			}
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOpFlag(account.getOpFlag());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=vehicleApplyService.getVehicleApplyList(pageParam, status,createUser, beginTime, endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getVehicleApprovedList   
	 * @Description: TODO 获取待审批记录
	 * @param request
	 * @param pageParam
	 * @param createUser
	 * @param beginTime
	 * @param endTime
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getVehicleApprovedList",method=RequestMethod.POST)
	public RetDataBean getVehicleApprovedList(HttpServletRequest request,PageParam pageParam,
			String createUser,String beginTime,String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("CREATE_TIME");
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("desc");
			}
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOpFlag(account.getOpFlag());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=vehicleApplyService.getVehicleApprovedList(pageParam, createUser, beginTime, endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getVehicleOilCardList   
	 * @Description: TODO 获取油卡列表
	 * @param request
	 * @param pageParam
	 * @param oilType
	 * @param beginTime
	 * @param endTime
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getVehicleOilCardList",method=RequestMethod.POST)
	public RetDataBean getVehicleOilCardList(HttpServletRequest request,PageParam pageParam,
			String oilType,String beginTime,String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("CREATE_TIME");
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("desc");
			}
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(account.getOrgId());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=vehicleOilCardService.getVehicleOilCardList(pageParam, oilType, beginTime, endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
}
