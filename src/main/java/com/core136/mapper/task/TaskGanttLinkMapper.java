package com.core136.mapper.task;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.task.TaskGanttLink;

@Mapper
public interface TaskGanttLinkMapper extends MyMapper<TaskGanttLink>{

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
	public List<Map<String, String>>getGanttLinkList(@Param(value="orgId") String orgId,@Param(value="taskId")String taskId);
	
}
