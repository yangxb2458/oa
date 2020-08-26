/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutSetTaskController.java   
 * @Package com.core136.controller.task   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年9月9日 下午1:43:08   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.controller.task;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
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
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  RoutSetTaskController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年9月9日 下午1:43:08   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@RestController
@RequestMapping("/set/taskset")
public class RoutSetTaskController {
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
	 * @Title: insertTaskProcess   
	 * @Description: TODO 添加子任务处理结果  
	 * @param: request
	 * @param: taskProcess
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertTaskProcess",method=RequestMethod.POST)
	public RetDataBean insertTaskProcess(HttpServletRequest request,TaskProcess taskProcess,Double progress)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			TaskGanttData taskGanttData = new TaskGanttData();
			taskGanttData.setProgress(progress);
			taskGanttData.setOrgId(account.getOrgId());
			Example example = new Example(TaskGanttData.class);
			example.createCriteria().andEqualTo("orgId",taskGanttData.getOrgId()).andEqualTo("taskDataId",taskProcess.getTaskDataId());
			taskGanttDataService.updateTaskGanttData(example, taskGanttData);
			taskProcess.setProcessId(SysTools.getGUID());
			taskProcess.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			taskProcess.setCreateUser(account.getAccountId());
			taskProcess.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加处理事件成功!", taskProcessService.insertTaskProcess(taskProcess));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteTaskProcess   
	 * @Description: TODO 删除子任务处理结果
	 * @param: request
	 * @param: taskProcess
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteTaskProcess",method=RequestMethod.POST)
	public RetDataBean deleteTaskProcess(HttpServletRequest request,TaskProcess taskProcess)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(taskProcess.getProcessId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			if(!account.getOpFlag().equals("1"))
			{
				taskProcess.setCreateUser(account.getAccountId());
			}
			taskProcess.setOrgId(account.getOrgId());
			return RetDataTools.Ok("处理过程删除成功!", taskProcessService.deleteTaskProcess(taskProcess));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateTaskGanttLink   
	 * @Description: TODO 修改子任务的处理过程  
	 * @param: request
	 * @param: taskProcess
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateTaskProcess",method=RequestMethod.POST)
	public RetDataBean updateTaskGanttLink(HttpServletRequest request,TaskProcess taskProcess)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(taskProcess.getProcessId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Example example = new Example(TaskProcess.class);
			if(account.getOpFlag().equals("1"))
			{
				example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("processId",taskProcess.getProcessId());
			}else
			{
				example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("processId",taskProcess.getProcessId()).andEqualTo("createUser").andEqualTo(account.getAccountId());
			}
			return RetDataTools.Ok("处理事件更新成功!", taskProcessService.updateTaskProcess(example, taskProcess));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: insertTaskGanttLink   
	 * @Description: TODO 创建关联
	 * @param: request
	 * @param: taskGanttLink
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertTaskGanttLink",method=RequestMethod.POST)
	public RetDataBean insertTaskGanttLink(HttpServletRequest request,TaskGanttLink taskGanttLink)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			taskGanttLink.setTaskLinkId(SysTools.getGUID());
			taskGanttLink.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			taskGanttLink.setCreateUser(account.getAccountId());
			taskGanttLink.setOrgId(account.getOrgId());
			return RetDataTools.Ok("关联创建成功!", taskGanttLinkService.insertTaskGanttLink(taskGanttLink));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: TaskGanttLink   
	 * @Description: TODO 删除关联关系   
	 * @param: request
	 * @param: taskGanttLink
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteTaskGanttLink",method=RequestMethod.POST)
	public RetDataBean deleteTaskGanttLink(HttpServletRequest request,TaskGanttLink taskGanttLink)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(taskGanttLink.getTaskLinkId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			if(!account.getOpFlag().equals("1"))
			{
				taskGanttLink.setCreateUser(account.getAccountId());
			}
			taskGanttLink.setOrgId(account.getOrgId());
			return RetDataTools.Ok("子任务删除成功!", taskGanttLinkService.deleteTaskGanttLink(taskGanttLink));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: updateTaskGanttLink   
	 * @Description: TODO 更新子任务路径
	 * @param: request
	 * @param: taskGanttLink
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateTaskGanttLink",method=RequestMethod.POST)
	public RetDataBean updateTaskGanttLink(HttpServletRequest request,TaskGanttLink taskGanttLink)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(taskGanttLink.getTaskLinkId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Example example = new Example(TaskGanttLink.class);
			if(account.getOpFlag().equals("1"))
			{
				example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("taskLinkId",taskGanttLink.getTaskLinkId());
			}else
			{
				example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("taskLinkId",taskGanttLink.getTaskLinkId()).andEqualTo("createUser").andEqualTo(account.getAccountId());
			}
			return RetDataTools.Ok("关联关系更新成功!", taskGanttLinkService.updateTaskGanttLink(example,taskGanttLink));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	
	/**
	 * 
	 * @Title: insertTaskGanttData   
	 * @Description: TODO 创建子任务
	 * @param: request
	 * @param: taskGanttData
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertTaskGanttData",method=RequestMethod.POST)
	public RetDataBean insertTaskGanttData(HttpServletRequest request,TaskGanttData taskGanttData)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			taskGanttData.setTaskDataId(SysTools.getGUID());
			taskGanttData.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			taskGanttData.setCreateUser(account.getAccountId());
			taskGanttData.setOrgId(account.getOrgId());
			return RetDataTools.Ok("子任务分配成功!", taskGanttDataService.insertTaskGanttData(taskGanttData));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: TaskGanttData   
	 * @Description: TODO 删除子任务 
	 * @param: request
	 * @param: taskGanttData
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteTaskGanttData",method=RequestMethod.POST)
	public RetDataBean TaskGanttData(HttpServletRequest request,TaskGanttData taskGanttData)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(taskGanttData.getTaskDataId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			if(!account.getOpFlag().equals("1"))
			{
				taskGanttData.setCreateUser(account.getAccountId());
			}
			taskGanttData.setOrgId(account.getOrgId());
			return RetDataTools.Ok("子任务删除成功!", taskGanttDataService.deleteTaskGanttData(taskGanttData));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateTaskGanttData   
	 * @Description: TODO 更新子任务 
	 * @param: request
	 * @param: taskGanttData
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	
	@RequestMapping(value="/updateTaskGanttData",method=RequestMethod.POST)
	public RetDataBean updateTaskGanttData(HttpServletRequest request,TaskGanttData taskGanttData)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(taskGanttData.getTaskDataId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Example example = new Example(TaskGanttData.class);
			if(account.getOpFlag().equals("1"))
			{
				example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("taskDataId",taskGanttData.getTaskDataId());
			}else
			{
				example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("taskDataId",taskGanttData.getTaskDataId()).andEqualTo("createUser").andEqualTo(account.getAccountId());
			}
			return RetDataTools.Ok("子任务更新成功!", taskGanttDataService.updateTaskGanttData(example,taskGanttData));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: addTask   
	 * @Description: TODO 创建任务
	 * @param: request
	 * @param: task
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/addTask",method=RequestMethod.POST)
	public RetDataBean addTask(HttpServletRequest request,Task task)
	{
		try
		{
			task.setTaskId(SysTools.getGUID());
			Account account=accountService.getRedisAccount(request);
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			task.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			task.setStatus("0");
			task.setCreateUser(account.getAccountId());
			task.setOrgId(account.getOrgId());
			return RetDataTools.Ok("任务创建成功!", taskService.addTask(account,userInfo,task));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: deleteTask   
	 * @Description: TODO 删除任务
	 * @param: request
	 * @param: task
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteTask",method=RequestMethod.POST)
	public RetDataBean deleteTask(HttpServletRequest request,Task task)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(task.getTaskId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			if(!account.getOpFlag().equals("1"))
			{
				task.setCreateUser(account.getAccountId());
			}
			task.setOrgId(account.getOrgId());
			return RetDataTools.Ok("任务删除成功!", taskService.deleteTask(task));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateTask   
	 * @Description: TODO 更新任务
	 * @param: request
	 * @param: task
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateTask",method=RequestMethod.POST)
	public RetDataBean updateTask(HttpServletRequest request,Task task)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			if(StringUtils.isBlank(task.getTaskId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Example example = new Example(Task.class);
			if(account.getOpFlag().equals("1"))
			{
				example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("taskId",task.getTaskId());
			}else
			{
				example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("taskId",task.getTaskId()).andEqualTo("createUser").andEqualTo(account.getAccountId());
			}
			return RetDataTools.Ok("任务更新成功!", taskService.updateTask(account,userInfo,task, example));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}
	
}
