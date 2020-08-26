package com.core136.controller.vehicle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.vehicle.VehicleApply;
import com.core136.bean.vehicle.VehicleInfo;
import com.core136.bean.vehicle.VehicleOilCard;
import com.core136.bean.vehicle.VehicleRepairRecord;
import com.core136.service.account.AccountService;
import com.core136.service.vehicle.VehicleApplyService;
import com.core136.service.vehicle.VehicleInfoService;
import com.core136.service.vehicle.VehicleOilCardService;
import com.core136.service.vehicle.VehicleOperatorService;
import com.core136.service.vehicle.VehicleRepairRecordService;

import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("/set/vehicleset")
public class RoutSetVehicleController {
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
	 * @Title: insertVehicleRepairRecord
	 * @Description: TODO 添加维修记录
	 * @param request
	 * @param vehicleRepairRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertVehicleRepairRecord",method=RequestMethod.POST)
	public RetDataBean insertVehicleRepairRecord(HttpServletRequest request,VehicleRepairRecord vehicleRepairRecord)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			vehicleRepairRecord.setRecordId(SysTools.getGUID());
			vehicleRepairRecord.setCreateUser(account.getAccountId());
			vehicleRepairRecord.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			vehicleRepairRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加维修记录成功!", vehicleRepairRecordService.insertVehicleRepairRecord(vehicleRepairRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteVehicleRepairRecord
	 * @Description: TODO 删除维修记录
	 * @param request
	 * @param vehicleOilCard
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteVehicleRepairRecord",method=RequestMethod.POST)
	public RetDataBean deleteVehicleRepairRecord(HttpServletRequest request,VehicleRepairRecord vehicleRepairRecord)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(vehicleRepairRecord.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数不正确！请检查相关参数！");
			}
			vehicleRepairRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除维修记录成功!", vehicleRepairRecordService.deleteVehicleRepairRecord(vehicleRepairRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateVehicleRepairRecord 
	 * @Description: TODO 更新维修记录
	 * @param request
	 * @param vehicleRepairRecord
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateVehicleRepairRecord",method=RequestMethod.POST)
	public RetDataBean updateVehicleRepairRecord(HttpServletRequest request,VehicleRepairRecord vehicleRepairRecord)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(vehicleRepairRecord.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数不正确！请检查相关参数！");
			}
			Example example = new Example(VehicleRepairRecord.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",vehicleRepairRecord.getRecordId());
			vehicleRepairRecord.setOrgId(account.getOrgId());
			return RetDataTools.Ok("更新维修记录成功!",vehicleRepairRecordService.updateVehicleRepairRecord(example,vehicleRepairRecord));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertVehicleOilCard   
	 * @Description: TODO 添加油卡信息
	 * @param request
	 * @param vehicleOilCard
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertVehicleOilCard",method=RequestMethod.POST)
	public RetDataBean insertVehicleOilCard(HttpServletRequest request,VehicleOilCard vehicleOilCard)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			vehicleOilCard.setCardId(SysTools.getGUID());
			vehicleOilCard.setCreateUser(account.getAccountId());
			vehicleOilCard.setStatus("0");
			vehicleOilCard.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			vehicleOilCard.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加油卡成功!", vehicleOilCardService.insertVehicleOilCard(vehicleOilCard));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteVehicleOilCard
	 * @Description: TODO 删除油卡信息
	 * @param request
	 * @param vehicleOilCard
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteVehicleOilCard",method=RequestMethod.POST)
	public RetDataBean deleteVehicleOilCard(HttpServletRequest request,VehicleOilCard vehicleOilCard)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(vehicleOilCard.getCardId()))
			{
				return RetDataTools.NotOk("请求参数不正确！请检查相关参数！");
			}
			vehicleOilCard.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除油卡信息成功!", vehicleOilCardService.deleteVehicleOilCard(vehicleOilCard));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateVehicleOilCard   
	 * @Description: TODO 更新油卡信息
	 * @param request
	 * @param vehicleOilCard
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateVehicleOilCard",method=RequestMethod.POST)
	public RetDataBean updateVehicleOilCard(HttpServletRequest request,VehicleOilCard vehicleOilCard)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(vehicleOilCard.getCardId()))
			{
				return RetDataTools.NotOk("请求参数不正确！请检查相关参数！");
			}
			Example example = new Example(VehicleOilCard.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("cardId",vehicleOilCard.getCardId());
			vehicleOilCard.setOrgId(account.getOrgId());
			return RetDataTools.Ok("更新油卡信息成功!",vehicleOilCardService.updateVehicleOilCard(example,vehicleOilCard));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: setVehicleOperator   
	 * @Description: TODO 设置调度员
	 * @param request
	 * @param optUser
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/setVehicleOperator",method=RequestMethod.POST)
	public RetDataBean setVehicleOperator(HttpServletRequest request,String optUser)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			return vehicleOperatorService.setVehicleOperator(account,optUser);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertVehicleApply   
	 * @Description: TODO 车辆使用申请
	 * @param request
	 * @param vehicleApply
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertVehicleApply",method=RequestMethod.POST)
	public RetDataBean insertVehicleInfo(HttpServletRequest request,VehicleApply vehicleApply)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			vehicleApply.setApplyId(SysTools.getGUID());
			vehicleApply.setCreateUser(account.getAccountId());
			vehicleApply.setStatus("0");
			vehicleApply.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			vehicleApply.setOrgId(account.getOrgId());
			return RetDataTools.Ok("申请车辆成功!", vehicleApplyService.insertVehicleApply(vehicleApply));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteVehicleApply   
	 * @Description: TODO 删除车辆使用申请
	 * @param request
	 * @param vehicleApply
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteVehicleApply",method=RequestMethod.POST)
	public RetDataBean deleteVehicleApply(HttpServletRequest request,VehicleApply vehicleApply)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(vehicleApply.getApplyId()))
			{
				return RetDataTools.NotOk("请求参数不正确！请检查相关参数！");
			}
			vehicleApply.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除车辆使用申请成功!", vehicleApplyService.deleteVehicleApply(vehicleApply));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateVehicleApply   
	 * @Description: TODO 更新车辆使用申请
	 * @param request
	 * @param vehicleApply
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateVehicleApply",method=RequestMethod.POST)
	public RetDataBean updateVehicleApply(HttpServletRequest request,VehicleApply vehicleApply)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(vehicleApply.getApplyId()))
			{
				return RetDataTools.NotOk("请求参数不正确！请检查相关参数！");
			}
			Example example = new Example(VehicleApply.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("applyId",vehicleApply.getApplyId());
			vehicleApply.setOrgId(account.getOrgId());
			return RetDataTools.Ok("更新车辆使用申请成功!",vehicleApplyService.updateVehicleApply(example,vehicleApply));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: insertVehicleInfo   
	 * @Description: TODO 添加车辆信息
	 * @param request
	 * @param vehicleInfo
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertVehicleInfo",method=RequestMethod.POST)
	public RetDataBean insertVehicleInfo(HttpServletRequest request,VehicleInfo vehicleInfo)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			vehicleInfo.setVehicleId(SysTools.getGUID());
			vehicleInfo.setCreateUser(account.getAccountId());
			vehicleInfo.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			vehicleInfo.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加车辆成功!", vehicleInfoService.insertVehicleInfo(vehicleInfo));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteVehicleInfo   
	 * @Description: TODO 删除车辆
	 * @param request
	 * @param vehicleInfo
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteVehicleInfo",method=RequestMethod.POST)
	public RetDataBean deleteVehicleInfo(HttpServletRequest request,VehicleInfo vehicleInfo)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(vehicleInfo.getVehicleId()))
			{
				return RetDataTools.NotOk("请求参数不正确！请检查相关参数！");
			}
			vehicleInfo.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除车辆成功!", vehicleInfoService.deleteVehicleInfo(vehicleInfo));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateVehicleInfo   
	 * @Description: TODO 更新车辆信息
	 * @param request
	 * @param vehicleInfo
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateVehicleInfo",method=RequestMethod.POST)
	public RetDataBean updateVehicleInfo(HttpServletRequest request,VehicleInfo vehicleInfo)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(vehicleInfo.getVehicleId()))
			{
				return RetDataTools.NotOk("请求参数不正确！请检查相关参数！");
			}
			Example example = new Example(VehicleInfo.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("vehicleId",vehicleInfo.getVehicleId());
			vehicleInfo.setOrgId(account.getOrgId());
			return RetDataTools.Ok("更新车辆信息成功!", vehicleInfoService.updateVehicleInfo(example,vehicleInfo));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
}
