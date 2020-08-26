package com.core136.controller.workplan;

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
import com.core136.bean.account.UserInfo;
import com.core136.bean.workplan.WorkPlan;
import com.core136.bean.workplan.WorkPlanProcess;
import com.core136.service.account.AccountService;
import com.core136.service.workplan.WorkPlanProcessService;
import com.core136.service.workplan.WorkPlanService;

import sun.net.www.content.audio.wav;
import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("/set/workplanset")
public class RoutSetWorkPlanController {
@Autowired 
private WorkPlanService workPlanService;
@Autowired 
private WorkPlanProcessService workPlanProcessService;@Autowired
private AccountService accountService;

/**
 * 
 * @Title: insertWorkPlanProcess
 * @Description: TODO 添加计划完成情况
 * @param request
 * @param workPlanProcess
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/insertWorkPlanProcess",method=RequestMethod.POST)
public RetDataBean insertWorkPlanProcess (HttpServletRequest request,WorkPlanProcess workPlanProcess)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		workPlanProcess.setProcessId(SysTools.getGUID());
		workPlanProcess.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		workPlanProcess.setCreateUser(account.getAccountId());
		workPlanProcess.setOrgId(account.getOrgId());
		return RetDataTools.Ok("添加成功!",workPlanProcessService.insertWorkPlanProcess(workPlanProcess));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: deleteWorkPlanProcess
 * @Description: TODO 删除工作计划处理结果
 * @param request
 * @param workPlanProcess
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/deleteWorkPlanProcess",method=RequestMethod.POST)
public RetDataBean deleteWorkPlanProcess(HttpServletRequest request,WorkPlanProcess workPlanProcess)
{
	try
	{
		if(StringUtils.isBlank(workPlanProcess.getProcessId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		workPlanProcess.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除成功!",workPlanProcessService.deleteWorkPlanProcess(workPlanProcess));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateWorkPlanProcess
 * @Description: TODO 更新工作计划处理结果
 * @param request
 * @param workPlanProcess
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/updateWorkPlanProcess",method=RequestMethod.POST)
public RetDataBean updateWorkPlanProcess(HttpServletRequest request,WorkPlanProcess workPlanProcess)
{
	try
	{
		if(StringUtils.isBlank(workPlanProcess.getProcessId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(WorkPlanProcess.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("processId",workPlanProcess.getProcessId());
		return RetDataTools.Ok("更新成功!",workPlanProcessService.updateWorkPlanProcess(example, workPlanProcess));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: insertWorkPlan
 * @Description: TODO 创建工作计划
 * @param request
 * @param workPlan
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/createWorkPlan",method=RequestMethod.POST)
public RetDataBean insertWorkPlan (HttpServletRequest request,WorkPlan workPlan)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		workPlan.setPlanId(SysTools.getGUID());
		workPlan.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		workPlan.setCreateUser(account.getAccountId());
		workPlan.setOrgId(account.getOrgId());
		workPlan.setStatus("0");
		return RetDataTools.Ok("添加成功!",workPlanService.createWorkPlan(account,userInfo,workPlan));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: deleteWorkPlan
 * @Description: TODO 删除工作计划
 * @param request
 * @param workPlan
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/deleteWorkPlan",method=RequestMethod.POST)
public RetDataBean deleteWorkPlan(HttpServletRequest request,WorkPlan workPlan)
{
	try
	{
		if(StringUtils.isBlank(workPlan.getPlanId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		workPlan.setOrgId(account.getOrgId());
		return RetDataTools.Ok("删除成功!",workPlanService.deleteWorkPlan(workPlan));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateWorkPlan
 * @Description: TODO 更新工作计划
 * @param request
 * @param workPlan
 * @return
 * RetDataBean    
 * @throws
 */
@RequestMapping(value="/updateWorkPlan",method=RequestMethod.POST)
public RetDataBean updateWorkPlan(HttpServletRequest request,WorkPlan workPlan)
{
	try
	{
		if(StringUtils.isBlank(workPlan.getPlanId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		Example example = new Example(WorkPlan.class);
		example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("planId",workPlan.getPlanId());
		return RetDataTools.Ok("更新成功!",workPlanService.updateWorkPlan(example, workPlan));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

}
