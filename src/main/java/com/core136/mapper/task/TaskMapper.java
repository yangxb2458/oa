package com.core136.mapper.task;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.task.Task;
import org.core136.common.dbutils.MyMapper;

@Mapper
public interface TaskMapper extends MyMapper<Task>{
	/**
	 * 
	 * @Title: getManageTaskList   
	 * @Description: TODO 获取我的任务列表
	 * @param: orgId
	 * @param: accountId
	 * @param: opFlag
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
public List<Map<String, String>>getManageTaskList(
		@Param(value="orgId") String orgId,
		@Param(value="accountId") String accountId,
		@Param(value="opFlag") String opFlag,
		@Param(value="status") String status,
		@Param(value="taskType")String taskType,
		@Param(value="beginTime")String beginTime,
		@Param(value="endTime") String endTime,
		@Param(value="search") String search);
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
public List<Map<String, String>>getMyChargeTaskList(
		@Param(value="orgId") String orgId,
		@Param(value="accountId") String accountId,
		@Param(value="createUser") String createUser,
		@Param(value="status") String status,
		@Param(value="taskType")String taskType,
		@Param(value="beginTime")String beginTime,
		@Param(value="endTime") String endTime,
		@Param(value="search") String search);
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
public List<Map<String, String>>getMySupervisorTaskList(
		@Param(value="orgId") String orgId,
		@Param(value="accountId") String accountId,
		@Param(value="createUser") String createUser,
		@Param(value="status") String status,
		@Param(value="taskType")String taskType,
		@Param(value="beginTime")String beginTime,
		@Param(value="endTime") String endTime,
		@Param(value="search") String search);

/**
 * 
 * @Title: getAssignmentTaskList   
 * @Description: TODO 获取待分解任务列表
 * @param: orgId
 * @param: accountId
 * @param: opFlag
 * @param: taskType
 * @param: beginTime
 * @param: endTime
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getAssignmentTaskList(
		@Param(value="orgId") String orgId,
		@Param(value="accountId") String accountId,
		@Param(value="opFlag") String opFlag,
		@Param(value="taskType")String taskType,
		@Param(value="beginTime")String beginTime,
		@Param(value="endTime") String endTime,
		@Param(value="search") String search);
}
