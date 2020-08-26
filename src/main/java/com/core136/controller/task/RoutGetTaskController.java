/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutGetTaskController.java   
 * @Package com.core136.controller.unit   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年9月9日 下午1:42:35   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.controller.task;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.sys.PageParam;
import com.core136.bean.task.Task;
import com.core136.bean.task.TaskGanttData;
import com.core136.bean.task.TaskGanttLink;
import com.core136.bean.task.TaskProcess;
import com.core136.service.account.AccountService;
import com.core136.service.task.TaskGanttDataService;
import com.core136.service.task.TaskGanttLinkService;
import com.core136.service.task.TaskProcessService;
import com.core136.service.task.TaskService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;
import com.github.pagehelper.PageInfo;

/**   
 * @ClassName:  RoutGetTaskController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年9月9日 下午1:42:35   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@RestController
@RequestMapping("/ret/taskget")
public class RoutGetTaskController {
	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskGanttDataService taskGanttDataService;
	@Autowired
	private TaskGanttLinkService taskGanttLinkService;
	@Autowired
	private TaskProcessService taskProcessService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: getTaskProcessInfo   
	 * @Description: TODO 获取处理事件详情
	 * @param request
	 * @param processId
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getTaskProcessInfo",method=RequestMethod.POST)
	public RetDataBean getTaskProcessInfo(HttpServletRequest request,String processId)
	{
		try
		{
			if(StringUtils.isBlank(processId))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!",taskProcessService.getProcessInfo(account.getOrgId(), processId, account.getAccountId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getTaskProcess   
	 * @Description: TODO 获取子任务处理详情
	 * @param: request
	 * @param: taskProcess
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getTaskProcess",method=RequestMethod.POST)
	public RetDataBean getTaskProcess(HttpServletRequest request,TaskProcess taskProcess)
	{
		try
		{
			if(StringUtils.isBlank(taskProcess.getProcessId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			taskProcess.setCreateUser(account.getAccountId());
			return RetDataTools.Ok("请求成功!",taskProcessService.selectOneTaskProcess(taskProcess));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: geTaskListForDesk   
	 * @Description: TODO 获取个人桌面的任务待办列表 
	 * @param: request
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getTaskListForDesk",method=RequestMethod.POST)
	public RetDataBean getTaskListForDesk(HttpServletRequest request)
	{
		try
		{
		PageParam pageParam = new PageParam();
		pageParam.setSort("D.START_DATE");
		pageParam.setSortOrder("ASC");
		pageParam.setPageNumber(1);
		pageParam.setPageSize(10);
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOrgId(account.getOrgId());
		PageInfo<Map<String, String>> pageInfo=taskGanttDataService.getTaskListForDesk(pageParam);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	@RequestMapping(value="/getTaskListForDesk2",method=RequestMethod.POST)
	public RetDataBean getTaskListForDesk2(HttpServletRequest request)
	{
		try
		{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求数据成功!", taskGanttDataService.getTaskListForDesk2(account.getOrgId(),account.getAccountId()));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getMyTaskWorkList   
	 * @Description: TODO  获取我的待办任务
	 * @param: request
	 * @param: pageParam
	 * @param: createUser
	 * @param: taskType
	 * @param: beginTime
	 * @param: endTime
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getMyTaskWorkList",method=RequestMethod.POST)
	public RetDataBean getMyTaskWorkList(
			HttpServletRequest request,
			PageParam pageParam,String createUser,
			String taskType,String beginTime,String endTime)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("D.START_DATE");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("ASC");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOpFlag(account.getOpFlag());
		pageParam.setOrgId(account.getOrgId());
		PageInfo<Map<String, String>> pageInfo=taskGanttDataService.getMyTaskWorkList(pageParam, createUser, taskType, beginTime, endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getMyTaskProcessList   
	 * @Description: TODO 获取子任务的处理过程列表 
	 * @param request
	 * @param pageParam
	 * @param createUser
	 * @param taskType
	 * @param beginTime
	 * @param endTime
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getMyTaskProcessList",method=RequestMethod.POST)
	public RetDataBean getMyTaskProcessList(
			HttpServletRequest request,
			PageParam pageParam,String createUser,
			String taskType,String beginTime,String endTime)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("P.COMPLETE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("ASC");
			}
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOpFlag(account.getOpFlag());
		pageParam.setOrgId(account.getOrgId());
		PageInfo<Map<String, String>> pageInfo=taskProcessService.getMyTaskProcessList(pageParam, createUser, taskType, beginTime, endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getAllUserPrivList   
	 * @Description: TODO 获取已分配的子任务列表 
	 * @param: request
	 * @param: pageParam
	 * @param: status
	 * @param: taskType
	 * @param: beginTime
	 * @param: endTime
	 * @param: userPriv
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getAllUserPrivList",method=RequestMethod.POST)
	public RetDataBean getAllUserPrivList(
			HttpServletRequest request,
			PageParam pageParam,String status,
			String taskType,String beginTime,String endTime,String userPriv
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("D.START_DATE");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("ASC");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOpFlag(account.getOpFlag());
		pageParam.setOrgId(account.getOrgId());
		PageInfo<Map<String, String>> pageInfo=taskGanttDataService.getAllUserPrivList(pageParam, status, userPriv, taskType, beginTime, endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getParticipantUserList   
	 * @Description: TODO 获取责任人列表 
	 * @param: request
	 * @param: taskId
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getParticipantUserList",method=RequestMethod.POST)
	public RetDataBean getParticipantUserList(HttpServletRequest request,String taskId)
	{
		try
		{
			if(StringUtils.isBlank(taskId))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!",taskService.getParticipantUserList(account.getOrgId(),taskId));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getTaskGantInfo   
	 * @Description: TODO  获取子任甘特图信息
	 * @param: request
	 * @param: taskId
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getTaskGantInfo",method=RequestMethod.POST)
	public RetDataBean getTaskGantInfo(HttpServletRequest request,String taskId)
	{
		try
		{
			if(StringUtils.isBlank(taskId))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			return RetDataTools.Ok("请求成功!",taskGanttDataService.getTaskGantInfo(account.getOrgId(),taskId));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	
	/**
	 * 
	 * @Title: getTaskGanttLinkById   
	 * @Description: TODO 获取子任务关系详情
	 * @param: request
	 * @param: taskGanttLink
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getTaskGanttLinkById",method=RequestMethod.POST)
	public RetDataBean getTaskGanttLinkById(HttpServletRequest request,TaskGanttLink taskGanttLink)
	{
		try
		{
			if(StringUtils.isBlank(taskGanttLink.getTaskLinkId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			taskGanttLink.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",taskGanttLinkService.selectOneTaskGanttLink(taskGanttLink));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getTaskGanttDataById   
	 * @Description: TODO 获取子任务详情
	 * @param: request
	 * @param: taskGanttData
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getTaskGanttDataById",method=RequestMethod.POST)
	public RetDataBean getTaskGanttDataById(HttpServletRequest request,TaskGanttData taskGanttData)
	{
		try
		{
			if(StringUtils.isBlank(taskGanttData.getTaskDataId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			taskGanttData.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",taskGanttDataService.selectOneTaskGanttData(taskGanttData));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: getTaskById   
	 * @Description: TODO 获取任务详情
	 * @param: request
	 * @param: task
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getTaskById",method=RequestMethod.POST)
	public RetDataBean getTaskById(HttpServletRequest request,Task task)
	{
		try
		{
			if(StringUtils.isBlank(task.getTaskId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			task.setOrgId(account.getOrgId());
			return RetDataTools.Ok("请求成功!",taskService.selectOneTask(task));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: getManageTaskList   
	 * @Description: TODO 获取任务列表
	 * @param: request
	 * @param: pageParam
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/getManageTaskList",method=RequestMethod.POST)
	public RetDataBean getManageTaskList(
			HttpServletRequest request,
			PageParam pageParam,String status,
			String taskType,String beginTime,String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("ASC");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOpFlag(account.getOpFlag());
		pageParam.setOrgId(account.getOrgId());
		PageInfo<Map<String, String>> pageInfo=taskService.getManageTaskList(pageParam,status,taskType,beginTime,endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getMyChargeTaskList   
	 * @Description: TODO 获取个人负责的任务
	 * @param request
	 * @param pageParam
	 * @param createUser
	 * @param status
	 * @param taskType
	 * @param beginTime
	 * @param endTime
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getMyChargeTaskList",method=RequestMethod.POST)
	public RetDataBean getMyChargeTaskList(
			HttpServletRequest request,
			PageParam pageParam,String createUser,String status,
			String taskType,String beginTime,String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("ASC");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOrgId(account.getOrgId());
		PageInfo<Map<String, String>> pageInfo=taskService.getMyChargeTaskList(pageParam,createUser,status,taskType,beginTime,endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: getMySupervisorTaskList   
	 * @Description: TODO 获取个人督查的任务列表
	 * @param request
	 * @param pageParam
	 * @param createUser
	 * @param status
	 * @param taskType
	 * @param beginTime
	 * @param endTime
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/getMySupervisorTaskList",method=RequestMethod.POST)
	public RetDataBean getMySupervisorTaskList(
			HttpServletRequest request,
			PageParam pageParam,String createUser,String status,
			String taskType,String beginTime,String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("ASC");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOrgId(account.getOrgId());
		PageInfo<Map<String, String>> pageInfo=taskService.getMySupervisorTaskList(pageParam,createUser,status,taskType,beginTime,endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
/**
 * 	
 * @Title: getAssignmentTaskList   
 * @Description: TODO 获取待分解任务列表
 * @param: request
 * @param: pageParam
 * @param: taskType
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
	@RequestMapping(value="/getAssignmentTaskList",method=RequestMethod.POST)
	public RetDataBean getAssignmentTaskList(
			HttpServletRequest request,
			PageParam pageParam,
			String taskType,String beginTime,String endTime
			)
	{
		try
		{
			if(StringUtils.isBlank(pageParam.getSort()))
			{
				pageParam.setSort("CREATE_TIME");
			}else
			{
				pageParam.setSort(StrTools.upperCharToUnderLine(pageParam.getSort()));
			}
			if(StringUtils.isBlank(pageParam.getSortOrder()))
			{
				pageParam.setSortOrder("ASC");
			}
			
		Account account=accountService.getRedisAccount(request);
		pageParam.setOrderBy(pageParam.getSort()+ " " + pageParam.getSortOrder());
		pageParam.setAccountId(account.getAccountId());
		pageParam.setOpFlag(account.getOpFlag());
		pageParam.setOrgId(account.getOrgId());
		PageInfo<Map<String, String>> pageInfo=taskService.getAssignmentTaskList(pageParam,taskType,beginTime,endTime);
		return RetDataTools.Ok("请求数据成功!", pageInfo);
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
}
