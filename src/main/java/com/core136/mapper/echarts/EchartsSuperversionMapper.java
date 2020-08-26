package com.core136.mapper.echarts;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EchartsSuperversionMapper {
	
	
	/**
	 * 
	 * @Title: getBiSuperversionByLeadPie   
	 * @Description: TODO 前10位领导的人员工作量占比
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getBiSuperversionByLeadPie(@Param(value="orgId")String orgId);

	/**
	 * 
	 * @Title: getBiBpmFlowPie   
	 * @Description: TODO 获取督查督办分类前10的占比
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getBiSuperversionTypePie(@Param(value="orgId")String orgId);
	
	
	/**
	 * 
	 * @Title: getBiSuperversionStatusTypePie   
	 * @Description: TODO 获取督查督办当前状态总数
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getBiSuperversionStatusTypePie(@Param(value="orgId")String orgId);
	
	/**
	 * 
	 * @Title: getBiSuperversionByMonthLine   
	 * @Description: TODO 按月份统计工作量
	 * @param orgId
	 * @param beginTime
	 * @param endTime
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String,Object>>getBiSuperversionByMonthLine(@Param(value="orgId")String orgId,@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime);
	
}
