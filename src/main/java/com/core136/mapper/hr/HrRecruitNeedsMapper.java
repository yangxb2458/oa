package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrRecruitNeeds;

@Mapper
public interface HrRecruitNeedsMapper extends MyMapper<HrRecruitNeeds>{

	/**
	 * 
	 * @Title: getHrRecruitNeedsList   
	 * @Description: TODO 获取需求列表
	 * @param orgId
	 * @param occupation
	 * @param highsetShool
	 * @param status
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrRecruitNeedsList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,
			@Param(value="occupation") String occupation,@Param(value="highsetShool") String highsetShool,
			@Param(value="status")String status,@Param(value="beginTime") String beginTime,
			@Param(value="endTime")String endTime,@Param(value="search")String search
			);
	
	/**
	 * 
	 * @Title: getApprovedHrRecruitNeedsList   
	 * @Description: TODO 获取待审批需求列表
	 * @param orgId
	 * @param accountId
	 * @param occupation
	 * @param highsetShool
	 * @param status
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getApprovedHrRecruitNeedsList(
			@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,
			@Param(value="occupation") String occupation,@Param(value="highsetShool") String highsetShool,
			@Param(value="beginTime") String beginTime,
			@Param(value="endTime")String endTime,@Param(value="search")String search);
	
	/**
	 * 
	 * @Title: getOldApprovedHrRecruitNeedsList   
	 * @Description: TODO 获取历史审批记录
	 * @param orgId
	 * @param accountId
	 * @param status
	 * @param occupation
	 * @param highsetShool
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getOldApprovedHrRecruitNeedsList(
			@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,@Param(value="status")String status,
			@Param(value="occupation") String occupation,@Param(value="highsetShool") String highsetShool,
			@Param(value="beginTime") String beginTime,
			@Param(value="endTime")String endTime,@Param(value="search")String search);
	
}
