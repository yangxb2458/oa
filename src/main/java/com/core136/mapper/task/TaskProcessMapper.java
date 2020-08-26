package com.core136.mapper.task;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.task.TaskProcess;

@Mapper
public interface TaskProcessMapper extends MyMapper<TaskProcess>{
/**
 * 
 * @Title: getMyTaskProcessList   
 * @Description: TODO 获取子任务的处理过程列表 
 * @param orgId
 * @param accountId
 * @param createUser
 * @param taskType
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
	public List<Map<String, String>>getMyTaskProcessList(
			@Param(value="orgId")String orgId,
			@Param(value="accountId")String accountId,
			@Param(value="createUser")String createUser,
			@Param(value="taskType") String taskType,
			@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,
			@Param(value="search")String search
			);
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
public Map<String, String>getProcessInfo(@Param(value="orgId")String orgId,@Param(value="processId")String processId,String accountId);
}
