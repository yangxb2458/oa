package com.core136.service.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.sys.PageParam;
import com.core136.bean.task.TaskGanttData;
import com.core136.mapper.task.TaskGanttDataMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class TaskGanttDataService {
@Autowired
private TaskGanttDataMapper taskGanttDataMapper;
@Autowired
private TaskGanttLinkService taskGanttLinkService;

public int insertTaskGanttData(TaskGanttData taskGanttData)
{
	return taskGanttDataMapper.insert(taskGanttData);
}

public int deleteTaskGanttData(TaskGanttData taskGanttData)
{
	return taskGanttDataMapper.delete(taskGanttData);
}

public int updateTaskGanttData(Example example,TaskGanttData taskGanttData)
{
	return taskGanttDataMapper.updateByExampleSelective(taskGanttData, example);
}

public TaskGanttData selectOneTaskGanttData(TaskGanttData taskGanttData)
{
	return taskGanttDataMapper.selectOne(taskGanttData);
}
/**
 * 
 * @Title: getGanttDataList   
 * @Description: TODO 获取子任务列表
 * @param: orgId
 * @param: taskId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getGanttDataList(String orgId,String taskId)
{
	return taskGanttDataMapper.getGanttDataList(orgId, taskId);
}

/**
 * 
 * @Title: getTaskGantInfo   
 * @Description: TODO 获取子任甘特图信息
 * @param: orgId
 * @param: taskId
 * @param: @return      
 * @return: Map<String,List<Map<String,String>>>      
 * @throws
 */
public Map<String,List<Map<String, String>>> getTaskGantInfo(String orgId,String taskId)
{
	Map<String,List<Map<String, String>>> map = new HashMap<String,List<Map<String, String>>>();
	map.put("data", getGanttDataList(orgId, taskId));
	map.put("links", taskGanttLinkService.getGanttLinkList(orgId, taskId));
	return map;
}

/**
 * 
 * @Title: getAllUserPrivList   
 * @Description: TODO 获取已分配的子任务列表 
 * @param: orgId
 * @param: opFlag
 * @param: accountId
 * @param: status
 * @param: userPriv
 * @param: taskType
 * @param: beginTime
 * @param: endTime
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getAllUserPrivList(String orgId,String opFlag,String accountId,String status,String userPriv,String taskType,String beginTime,String endTime,String search)
{
	return taskGanttDataMapper.getAllUserPrivList(orgId, opFlag, accountId, status, userPriv, taskType, beginTime, endTime, "%"+search+"%");
}

/**
 * 
 * @Title: getAllUserPrivList   
 * @Description: TODO  获取已分配的子任务列表 
 * @param: pageParam
 * @param: status
 * @param: userPriv
 * @param: taskType
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getAllUserPrivList(PageParam pageParam,String status,String userPriv,String taskType,String beginTime,String endTime) {
PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
List<Map<String,String>> datalist= getAllUserPrivList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(),status,userPriv,taskType,beginTime,endTime,pageParam.getSearch());
PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
return pageInfo;
}
/**
 * 
 * @Title: getMyTaskWorkList   
 * @Description: TODO 获取我的待办任务
 * @param: orgId
 * @param: accountId
 * @param: createUser
 * @param: taskType
 * @param: beginTime
 * @param: endTime
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>>getMyTaskWorkList(String orgId,String accountId,String createUser,String taskType,String beginTime,String endTime,String search)
{
	return taskGanttDataMapper.getMyTaskWorkList(orgId, accountId, createUser, taskType, beginTime, endTime, "%"+search+"%");
}

/**
 * 
 * @Title: getMyTaskWorkList   
 * @Description: TODO 获取我的待办任务
 * @param: pageParam
 * @param: createUser
 * @param: taskType
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getMyTaskWorkList(PageParam pageParam,String createUser,String taskType,String beginTime,String endTime) {
PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
List<Map<String,String>> datalist= getMyTaskWorkList(pageParam.getOrgId(),pageParam.getAccountId(),createUser,taskType,beginTime,endTime,pageParam.getSearch());
PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
return pageInfo;
}

/**
 * 
 * @Title: geTaskListForDesk   
 * @Description: TODO 获取个人桌面的任务待办列表 
 * @param: orgId
 * @param: accountId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>>getTaskListForDesk(String orgId,String accountId)
{
	return taskGanttDataMapper.getTaskListForDesk(orgId, accountId);
}

public List<Map<String,String>>getTaskListForDesk2(String orgId,String accountId)
{
	return taskGanttDataMapper.getTaskListForDesk2(orgId, accountId);
}
/**
 * 
 * @Title: geTaskListForDesk   
 * @Description: TODO 获取个人桌面的任务待办列表 
 * @param: pageParam
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getTaskListForDesk(PageParam pageParam) {
PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
List<Map<String,String>> datalist= getTaskListForDesk(pageParam.getOrgId(),pageParam.getAccountId());
PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
return pageInfo;
}


}
