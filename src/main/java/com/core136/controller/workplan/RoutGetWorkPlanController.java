package com.core136.controller.workplan;

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
import com.core136.bean.account.UserInfo;
import com.core136.bean.sys.PageParam;
import com.core136.bean.workplan.WorkPlan;
import com.core136.bean.workplan.WorkPlanProcess;
import com.core136.service.account.AccountService;
import com.core136.service.workplan.WorkPlanProcessService;
import com.core136.service.workplan.WorkPlanService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/ret/workplanget")
public class RoutGetWorkPlanController {
	@Autowired 
	private WorkPlanService workPlanService;
	@Autowired 
	private WorkPlanProcessService workPlanProcessService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: getWorkPlanProcessById   
	 * @Description: TODO 获取工作计划处理详情
	 * @param request
	 * @param workPlanProcess
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getWorkPlanProcessById",method=RequestMethod.POST)
	public RetDataBean getWorkPlanProcessById(HttpServletRequest request,WorkPlanProcess workPlanProcess)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			workPlanProcess.setOrgId(account.getOrgId());
			return RetDataTools.Ok("数据请求成功!",workPlanProcessService.selectOneWorkPlanProcess(workPlanProcess));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getWorkPlanById   
	 * @Description: TODO 获取工作计划详情
	 * @param request
	 * @param workPlan
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getWorkPlanById",method=RequestMethod.POST)
	public RetDataBean getWorkPlanById(HttpServletRequest request,WorkPlan workPlan)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			workPlan.setOrgId(account.getOrgId());
			return RetDataTools.Ok("数据请求成功!",workPlanService.selectOneWorkPlan(workPlan));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getManageWorkPlanList   
	 * @Description: TODO 获取工作列表
	 * @param request
	 * @param pageParam
	 * @param status
	 * @param planType
	 * @param beginTime
	 * @param endTime
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getManageWorkPlanList",method=RequestMethod.POST)
	public RetDataBean getManageWorkPlanList(HttpServletRequest request,PageParam pageParam,
			String status,String planType,String beginTime,String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("W.CREATE_TIME");
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
		PageInfo<Map<String, String>> pageInfo=workPlanService.getManageWorkPlanList(pageParam, beginTime, endTime, status, planType);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getHoldWorkPlanList   
	 * @Description: TODO 我负责的计划列表
	 * @param request
	 * @param pageParam
	 * @param status
	 * @param planType
	 * @param beginTime
	 * @param endTime
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getHoldWorkPlanList",method=RequestMethod.POST)
	public RetDataBean getHoldWorkPlanList(HttpServletRequest request,PageParam pageParam,
			String status,String planType,String beginTime,String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("W.CREATE_TIME");
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
		PageInfo<Map<String, String>> pageInfo=workPlanService.getHoldWorkPlanList(pageParam, beginTime, endTime, status, planType);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getSupWorkPlanList   
	 * @Description: TODO 我督查的计划列表
	 * @param request
	 * @param pageParam
	 * @param status
	 * @param planType
	 * @param beginTime
	 * @param endTime
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getSupWorkPlanList",method=RequestMethod.POST)
	public RetDataBean getSupWorkPlanList(HttpServletRequest request,PageParam pageParam,
			String status,String planType,String beginTime,String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("W.CREATE_TIME");
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
		PageInfo<Map<String, String>> pageInfo=workPlanService.getSupWorkPlanList(pageParam, beginTime, endTime, status, planType);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMyWorkPlanList   
	 * @Description: TODO 我参与的工作计划
	 * @param request
	 * @param pageParam
	 * @param status
	 * @param planType
	 * @param beginTime
	 * @param endTime
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getMyWorkPlanList",method=RequestMethod.POST)
	public RetDataBean getMyWorkPlanList(HttpServletRequest request,PageParam pageParam,
			String status,String planType,String beginTime,String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("W.CREATE_TIME");
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
		PageInfo<Map<String, String>> pageInfo=workPlanService.getMyWorkPlanList(pageParam, beginTime, endTime, status, planType);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getTodayWorkPlanList   
	 * @Description: TODO 获取今天的工作计划
	 * @param request
	 * @param pageParam
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getTodayWorkPlanList",method=RequestMethod.POST)
	public RetDataBean getTodayWorkPlanList(HttpServletRequest request,PageParam pageParam
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("W.CREATE_TIME");
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
		PageInfo<Map<String, String>> pageInfo=workPlanService.getTodayWorkPlanList(pageParam);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMonthWorkPlanList   
	 * @Description: TODO 查询本月工作计划
	 * @param request
	 * @param pageParam
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getMonthWorkPlanList",method=RequestMethod.POST)
	public RetDataBean getMonthWorkPlanList(HttpServletRequest request,PageParam pageParam
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("W.CREATE_TIME");
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
		PageInfo<Map<String, String>> pageInfo=workPlanService.getMonthWorkPlanList(pageParam);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getShareWorkPlanList   
	 * @Description: TODO 获取分享的工作计划
	 * @param request
	 * @param pageParam
	 * @param status
	 * @param planType
	 * @param beginTime
	 * @param endTime
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getShareWorkPlanList",method=RequestMethod.POST)
	public RetDataBean getShareWorkPlanList(HttpServletRequest request,PageParam pageParam,
			String status,String planType,String beginTime,String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("W.CREATE_TIME");
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("desc");
			}
			UserInfo userInfo = accountService.getRedisUserInfo(request);
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrgId(userInfo.getOrgId());
		pageParam.setAccountId(userInfo.getAccountId());
		pageParam.setLevelId(userInfo.getLeadLeave());
		pageParam.setDeptId(userInfo.getDeptId());
		pageParam.setOpFlag(account.getOpFlag());
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		PageInfo<Map<String, String>> pageInfo=workPlanService.getShareWorkPlanList(pageParam, beginTime, endTime, status, planType);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
}
