package com.core136.mapper.task;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.task.TaskGanttData;

@Mapper
public interface TaskGanttDataMapper extends MyMapper<TaskGanttData>{

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
	public List<Map<String,String>>getGanttDataList(@Param(value="orgId") String orgId,@Param(value="taskId") String taskId);
	
	
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
	public List<Map<String,String>>getAllUserPrivList(
	@Param(value="orgId") String orgId,
	@Param(value="opFlag") String opFlag,
	@Param(value="accountId") String accountId,
	@Param(value="status") String status,
	@Param(value="userPriv") String userPriv,
	@Param(value="taskType") String taskType,
	@Param(value="beginTime") String beginTime,
	@Param(value="endTime") String endTime,
	@Param(value="search") String search
	);
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
	public List<Map<String,String>>getMyTaskWorkList(
			@Param(value="orgId") String orgId,
			@Param(value="accountId") String accountId,
			@Param(value="createUser") String createUser,
			@Param(value="taskType") String taskType,
			@Param(value="beginTime") String beginTime,
			@Param(value="endTime") String endTime,
			@Param(value="search") String search
			);
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
	public List<Map<String, String>>getTaskListForDesk(
			@Param(value="orgId")String orgId,
			@Param(value="accountId") String accountId);
	
	public List<Map<String, String>>getTaskListForDesk2(
			@Param(value="orgId")String orgId,
			@Param(value="accountId") String accountId);
	
	
}
