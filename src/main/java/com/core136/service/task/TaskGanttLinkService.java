package com.core136.service.task;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.core136.bean.task.TaskGanttLink;
import com.core136.mapper.task.TaskGanttLinkMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class TaskGanttLinkService {

	@Autowired
	private TaskGanttLinkMapper taskGanttLinkMapper;
	
	public int insertTaskGanttLink(TaskGanttLink taskGanttLink)
	{
		return taskGanttLinkMapper.insert(taskGanttLink);
	}
	public int deleteTaskGanttLink(TaskGanttLink taskGanttLink)
	{
		return taskGanttLinkMapper.delete(taskGanttLink);
	}
	public int updateTaskGanttLink(Example example,TaskGanttLink taskGanttLink)
	{
		return taskGanttLinkMapper.updateByExampleSelective(taskGanttLink, example);
	}
	public TaskGanttLink selectOneTaskGanttLink(TaskGanttLink taskGanttLink)
	{
		return taskGanttLinkMapper.selectOne(taskGanttLink);
	}
	
	/**
	 * 
	 * @Title: getGanttLinkList   
	 * @Description: TODO 获取子任务路径列表
	 * @param: orgId
	 * @param: taskId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getGanttLinkList(String orgId,String taskId)
	{
		return taskGanttLinkMapper.getGanttLinkList(orgId, taskId);
	}
	
}
