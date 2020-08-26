package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrRecruitPlan;

@Mapper
public interface HrRecruitPlanMapper extends MyMapper<HrRecruitPlan>{

	/**
	 * 
	 * @Title: getHrRecruitPlanList   
	 * @Description: TODO 获取招聘计划列表
	 * @param orgId
	 * @param opFlag
	 * @param accountId
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrRecruitPlanList(@Param(value="orgId")String orgId,@Param(value="opFlag")String opFlag,
			@Param(value="accountId")String accountId,@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime,
			@Param(value="search")String search
			);
	/**
	 * 
	 * @Title: getHrRecruitPlanForSelect   
	 * @Description: TODO 获取当前可填报的招聘计划
	 * @param orgId
	 * @param endTime
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrRecruitPlanForSelect(@Param(value="orgId")String orgId,@Param(value="endTime")String endTime);

}
