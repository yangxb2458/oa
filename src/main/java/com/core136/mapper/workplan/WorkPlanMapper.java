package com.core136.mapper.workplan;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.workplan.WorkPlan;

@Mapper
public interface WorkPlanMapper extends MyMapper<WorkPlan>{

	/**
	 * 
	 * @Title: getManageWorkPlanList   
	 * @Description: TODO 获取工作列表
	 * @param orgId
	 * @param createUser
	 * @param opFlag
	 * @param beginTime
	 * @param endTime
	 * @param status
	 * @param planType
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getManageWorkPlanList(
			@Param(value="orgId")String orgId,@Param(value="createUser")String createUser,
			@Param(value="opFlag") String opFlag,@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,@Param(value="status")String status,
			@Param(value="planType")String planType,@Param(value="search")String search
			);
	
	/**
	 * 
	 * @Title: getHoldWorkPlanList   
	 * @Description: TODO 我负责的计划表列
	 * @param orgId
	 * @param accountId
	 * @param opFlag
	 * @param beginTime
	 * @param endTime
	 * @param status
	 * @param planType
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHoldWorkPlanList(
			@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,
			@Param(value="opFlag") String opFlag,@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,@Param(value="status")String status,
			@Param(value="planType")String planType,@Param(value="search")String search
			);
	/**
	 * 
	 * @Title: getSupWorkPlanList   
	 * @Description: TODO 我督查的计划列表
	 * @param orgId
	 * @param accountId
	 * @param opFlag
	 * @param beginTime
	 * @param endTime
	 * @param status
	 * @param planType
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getSupWorkPlanList(
			@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,
			@Param(value="opFlag") String opFlag,@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,@Param(value="status")String status,
			@Param(value="planType")String planType,@Param(value="search")String search
			);
	/**
	 * 
	 * @Title: getMyWorkPlanList   
	 * @Description: TODO 我参与的工作计划
	 * @param orgId
	 * @param accountId
	 * @param opFlag
	 * @param beginTime
	 * @param endTime
	 * @param status
	 * @param planType
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getMyWorkPlanList(
			@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,
			@Param(value="opFlag") String opFlag,@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,@Param(value="status")String status,
			@Param(value="planType")String planType,@Param(value="search")String search
			);
	/**
	 * 
	 * @Title: getShareWorkPlanList   
	 * @Description: TODO 获取分享的工作计划
	 * @param orgId
	 * @param accountId
	 * @param deptId
	 * @param levelId
	 * @param opFlag
	 * @param beginTime
	 * @param endTime
	 * @param status
	 * @param planType
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getShareWorkPlanList(
			@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,
			@Param(value="deptId")String deptId,@Param(value="levelId")String levelId,
			@Param(value="opFlag") String opFlag,@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,@Param(value="status")String status,
			@Param(value="planType")String planType,@Param(value="search")String search
			);
	/**
	 * 
	 * @Title: getTodayWorkPlanList   
	 * @Description: TODO 获取今天的工作计划
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getTodayWorkPlanList(@Param(value="orgId")String orgId,@Param(value="search")String search);
	/**
	 * 
	 * @Title: getMonthWorkPlanList   
	 * @Description: TODO 查询本月工作计划
	 * @param orgId
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getMonthWorkPlanList(@Param(value="orgId")String orgId,@Param(value="search")String search);
	
}
