package com.core136.service.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.core136.common.enums.GobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.sys.MsgBody;
import com.core136.bean.sys.PageParam;
import com.core136.bean.task.Task;
import com.core136.mapper.task.TaskMapper;
import com.core136.service.account.AccountService;
import com.core136.service.account.UserInfoService;
import com.core136.service.sys.MessageUnitService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class TaskService {
@Autowired
private TaskMapper taskMapper;
@Autowired
private UserInfoService userInfoService;
@Autowired
private AccountService accountService;
@Autowired
private MessageUnitService messageUnitService;

public int insertTask(Task task)
{
	return taskMapper.insert(task);
}
/**
 * 
 * @Title: addTask   
 * @Description: TODO 创建任务
 * @param account
 * @param userInfo
 * @param task
 * @return
 * int    
 * @throws
 */
public int addTask(Account account,UserInfo userInfo,Task task)
{
	if(StringUtils.isNotBlank(task.getMsgType()))
	{
		String participantAccountId = task.getParticipantAccountId();
		String chargeAccountId = task.getChargeAccountId();
		String supervisorAccountId = task.getSupervisorAccountId();
		List<String> userList =new ArrayList<String>();
		List<String> arr2 =new ArrayList<String>();
		List<String> arr3 =new ArrayList<String>();
		if(StringUtils.isNotBlank(participantAccountId))
		{
			userList = new ArrayList<String>(Arrays.asList(participantAccountId.split(",")));
		}
		if(StringUtils.isNotBlank(chargeAccountId))
		{
			arr2 = new ArrayList<String>(Arrays.asList(chargeAccountId.split(",")));
		}
		if(StringUtils.isNotBlank(supervisorAccountId))
		{
			arr3 = new ArrayList<String>(Arrays.asList(supervisorAccountId.split(",")));
		}
		userList.addAll(arr2);
		userList.addAll(arr3);
		Set<String> set = new HashSet<String>();
		set.addAll(userList);     // 将list所有元素添加到set中    set集合特性会自动去重复
		userList.clear();
		userList.addAll(set);
		List<MsgBody> msgBodyList = new ArrayList<MsgBody>();
		for(int i=0;i<userList.size();i++)
		{
			Account account2 = new Account();
			account2.setAccountId(userList.get(i).toString());
			account2.setOrgId(account.getOrgId());
			account2 = accountService.selectOneAccount(account2);
			MsgBody msgBody = new MsgBody();
			msgBody.setTitle("任务提醒");
			msgBody.setContent("任务标题为："+task.getTaskName()+"的查看提醒！");
			msgBody.setSendTime(task.getCreateTime());
			msgBody.setAccount(account2);
			msgBody.setFromAccountId(account.getAccountId());
			msgBody.setFormUserName(userInfo.getUserName());
			msgBody.setRedirectUrl("/app/core/task/taskdetails?taskId="+task.getTaskId());
			msgBody.setOrgId(account.getOrgId());
			msgBodyList.add(msgBody);
		}
		String smsStatus = task.getMsgType();
		messageUnitService.sendMessage(smsStatus, GobalConstant.MSG_TYPE_TASK, msgBodyList);
		}
	return insertTask(task);
	}

public Task selectOneTask(Task task)
{
	return taskMapper.selectOne(task);
}

public int updateTask(Task task,Example example)
{
	return taskMapper.updateByExampleSelective(task, example);
}
/**
 * 
 * @Title: updateTask   
 * @Description: TODO 更新任务并提醒
 * @param account
 * @param userInfo
 * @param task
 * @param example
 * @return
 * int    
 * @throws
 */
public int updateTask(Account account,UserInfo userInfo,Task task,Example example)
{
	if(StringUtils.isNotBlank(task.getMsgType()))
	{
		String participantAccountId = task.getParticipantAccountId();
		String chargeAccountId = task.getChargeAccountId();
		String supervisorAccountId = task.getSupervisorAccountId();
		List<String> userList =null;
		List<String> arr2 =null;
		List<String> arr3 =null;
		if(StringUtils.isNotBlank(participantAccountId))
		{
			userList = new ArrayList<String>(Arrays.asList(participantAccountId.split(",")));
		}
		if(StringUtils.isNotBlank(chargeAccountId))
		{
			arr2 = new ArrayList<String>(Arrays.asList(chargeAccountId.split(",")));
		}
		if(StringUtils.isNotBlank(supervisorAccountId))
		{
			arr3 = new ArrayList<String>(Arrays.asList(supervisorAccountId.split(",")));
		}
		userList.addAll(arr2);
		userList.addAll(arr3);
		Set<String> set = new HashSet<String>();
		set.addAll(userList);     // 将list所有元素添加到set中    set集合特性会自动去重复
		userList.clear();
		userList.addAll(set);
		List<MsgBody> msgBodyList = new ArrayList<MsgBody>();
		for(int i=0;i<userList.size();i++)
		{
			Account account2 = new Account();
			account2.setAccountId(userList.get(i).toString());
			account2.setOrgId(account.getOrgId());
			account2 = accountService.selectOneAccount(account2);
			MsgBody msgBody = new MsgBody();
			msgBody.setTitle("任务提醒");
			msgBody.setContent("任务标题为："+task.getTaskName()+"的查看提醒！");
			msgBody.setSendTime(task.getCreateTime());
			msgBody.setAccount(account2);
			msgBody.setFromAccountId(account.getAccountId());
			msgBody.setFormUserName(userInfo.getUserName());
			msgBody.setRedirectUrl("/app/core/task/taskdetails?taskId="+task.getTaskId());
			msgBody.setOrgId(account.getOrgId());
			msgBodyList.add(msgBody);
		}
		String smsStatus = task.getMsgType();
		messageUnitService.sendMessage(smsStatus, GobalConstant.MSG_TYPE_TASK, msgBodyList);
		}
	return taskMapper.updateByExampleSelective(task, example);
}

public int deleteTask(Task task)
{
	return taskMapper.delete(task);
}
/**
 * 
 * @Title: getManageTaskList   
 * @Description: TODO 获取任务列表 
 * @param: orgId
 * @param: accountId
 * @param: opFlag
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getManageTaskList(String orgId,String accountId,String opFlag,String status,String taksType,String beginTime,String endTime,String search)
{
	return taskMapper.getManageTaskList(orgId, accountId, opFlag,status,taksType,beginTime,endTime,"%"+search+"%");
}

/**
 * 
 * @Title: getManageTaskList   
 * @Description: TODO 获取任务列表
 * @param: pageParam
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getManageTaskList(PageParam pageParam,String status,String taskType,String beginTime,String endTime) {
PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
List<Map<String,String>> datalist= getManageTaskList(pageParam.getOrgId(),pageParam.getAccountId(), pageParam.getOpFlag(),status,taskType,beginTime,endTime,pageParam.getSearch());
PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
return pageInfo;
}
/**
 * 
 * @Title: getAssignmentTaskList   
 * @Description: TODO  获取待分解任务列表
 * @param: orgId
 * @param: accountId
 * @param: opFlag
 * @param: taksType
 * @param: beginTime
 * @param: endTime
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getAssignmentTaskList(String orgId,String accountId,String opFlag,String taksType,String beginTime,String endTime,String search)
{
	return taskMapper.getAssignmentTaskList(orgId, accountId, opFlag,taksType,beginTime,endTime,"%"+search+"%");
}
/**
 * 
 * @Title: getAssignmentTaskList   
 * @Description: TODO  获取待分解任务列表
 * @param: pageParam
 * @param: taskType
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getAssignmentTaskList(PageParam pageParam,String taskType,String beginTime,String endTime) {
PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
List<Map<String,String>> datalist= getAssignmentTaskList(pageParam.getOrgId(),pageParam.getAccountId(), pageParam.getOpFlag(),taskType,beginTime,endTime,pageParam.getSearch());
PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
return pageInfo;
}

/**
 * 
 * @Title: getParticipantUserList   
 * @Description: TODO 获取责任人列表
 * @param: task
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getParticipantUserList(String orgId,String taskId)
{
	Task task = new Task();
	task.setOrgId(orgId);
	task.setTaskId(taskId);
	task = selectOneTask(task);
	String participantAccountId = task.getParticipantAccountId();
	List<Map<String, String>> tMaps = userInfoService.getAllUserInfoByAccountList(task.getOrgId(), participantAccountId);
	List<Map<String, String>> returnList = new ArrayList<Map<String,String>>();
	for(int i=0;i<tMaps.size();i++)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", tMaps.get(i).get("accountId"));
		map.put("label", tMaps.get(i).get("userName"));
		returnList.add(map);
	}
	return returnList;
}

/**
 * 
 * @Title: getMyChargeTaskList   
 * @Description: TODO 获取个人负责的任务
 * @param orgId
 * @param accountId
 * @param createUser
 * @param status
 * @param taskType
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyChargeTaskList(String orgId,String accountId,String createUser,String status,String taskType,String beginTime,String endTime,String search)
{
	return taskMapper.getMyChargeTaskList(orgId, accountId, createUser, status, taskType, beginTime, endTime, "%"+search+"%");
}
/**
 * 
 * @Title: getMyChargeTaskList   
 * @Description: TODO 获取个人负责的任务
 * @param pageParam
 * @param createUser
 * @param status
 * @param taskType
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMyChargeTaskList(PageParam pageParam,String createUser,String status,String taskType,String beginTime,String endTime) {
PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
List<Map<String,String>> datalist= getMyChargeTaskList(pageParam.getOrgId(),pageParam.getAccountId(), createUser,status,taskType,beginTime,endTime,pageParam.getSearch());
PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
return pageInfo;
}


/**
 * 
 * @Title: getMySupervisorTaskList   
 * @Description: TODO 获取个人督查的任务列表
 * @param orgId
 * @param accountId
 * @param createUser
 * @param status
 * @param taskType
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMySupervisorTaskList(String orgId,String accountId,String createUser,String status,String taskType,String beginTime,String endTime,String search)
{
	return taskMapper.getMySupervisorTaskList(orgId, accountId, createUser, status, taskType, beginTime, endTime, "%"+search+"%");
}
/**
 * 
 * @Title: getMySupervisorTaskList   
 * @Description: TODO 获取个人督查的任务列表
 * @param pageParam
 * @param createUser
 * @param status
 * @param taskType
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMySupervisorTaskList(PageParam pageParam,String createUser,String status,String taskType,String beginTime,String endTime) {
PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
List<Map<String,String>> datalist= getMySupervisorTaskList(pageParam.getOrgId(),pageParam.getAccountId(), createUser,status,taskType,beginTime,endTime,pageParam.getSearch());
PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
return pageInfo;
}

}
