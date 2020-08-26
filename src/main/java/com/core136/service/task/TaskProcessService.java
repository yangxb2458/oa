package com.core136.service.task;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.sys.PageParam;
import com.core136.bean.task.TaskProcess;
import com.core136.mapper.task.TaskProcessMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class TaskProcessService {

	@Autowired
	private TaskProcessMapper taskProcessMapper;
	
	public int insertTaskProcess(TaskProcess taskProcess)
	{
		return taskProcessMapper.insert(taskProcess);
	}
	
	public int deleteTaskProcess(TaskProcess taskProcess)
	{
		return taskProcessMapper.delete(taskProcess);
	}
	
	public int updateTaskProcess(Example example,TaskProcess taskProcess)
	{
		return taskProcessMapper.updateByExampleSelective(taskProcess, example);
	}
	
	public TaskProcess selectOneTaskProcess(TaskProcess taskProcess)
	{
		return taskProcessMapper.selectOne(taskProcess);
	}
	/**
	 * 
	 * @Title: getMyTaskProcessList   
	 * @Description: TODO 获取子任务的处理过程列表 
	 * @param orgId
	 * @param accountId
	 * @param taskType
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getMyTaskProcessList(String orgId,String accountId,String createUser,String taskType,String beginTime,String endTime,String search)
	{
		return taskProcessMapper.getMyTaskProcessList(orgId, accountId,createUser, taskType, beginTime, endTime, "%"+search+"%");
	}
	
	/**
	 * 
	 * @Title: getMyTaskProcessList   
	 * @Description: TODO 获取子任务的处理过程列表 
	 * @param pageParam
	 * @param createUser
	 * @param taskType
	 * @param beginTime
	 * @param endTime
	 * @return
	 * PageInfo<Map<String,String>>    
	 * @throws
	 */
	public PageInfo<Map<String, String>> getMyTaskProcessList(PageParam pageParam,String createUser,String taskType,String beginTime,String endTime) {
		PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= getMyTaskProcessList(pageParam.getOrgId(),pageParam.getAccountId(),createUser,taskType,beginTime,endTime,pageParam.getSearch());
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
		}
	/**
	 * 
	 * @Title: getProcessInfo   
	 * @Description: TODO 获取处理事件详情
	 * @param orgId
	 * @param processId
	 * @return
	 * Map<String,String>    
	 * @throws
	 */
	public Map<String, String>getProcessInfo(String orgId,String processId,String accountId)
	{
		return taskProcessMapper.getProcessInfo(orgId, processId,accountId);
	}
}
