package com.core136.mapper.echarts;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

@Mapper
public interface EchartsBpmMapper {

	/**
	 * 
	 * @Title: getBiBpmFlowPie   
	 * @Description: TODO 获取BPM使用分类前10的占比
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getBiBpmFlowPie(@Param(value="orgId")String orgId);
	
	/**
	 * 
	 * @Title: getBiBpmFlowByDeptPie   
	 * @Description: TODO 部门BPM占比前10的占比
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getBiBpmFlowByDeptPie(@Param(value="orgId")String orgId);
	
	/**
	 * 
	 * @Title: getBiBpmFlowByAccountPie   
	 * @Description: TODO 前10位流程处理最多的人员工作量占比
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getBiBpmFlowByAccountPie(@Param(value="orgId")String orgId);
	
	/**
	 * 
	 * @Title: getBiBpmLowByMonthLine   
	 * @Description: TODO 按月份统计工作量
	 * @param orgId
	 * @param beginTime
	 * @param endTime
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String,Object>>getBiBpmFlowByMonthLine(@Param(value="orgId")String orgId,@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime); 
	
}


