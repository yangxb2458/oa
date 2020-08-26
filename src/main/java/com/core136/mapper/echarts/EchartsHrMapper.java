package com.core136.mapper.echarts;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EchartsHrMapper {

	/**
	 * 
	 * @Title: getHighsetShoolPie   
	 * @Description: TODO 获取HR学历占比
	 * @param orgId
	 * @return
	 * List<Map<String,Object>>    
	 * @throws
	 */
	public List<Map<String, Object>>getHighsetShoolPie(@Param(value="orgId")String orgId);
	
	/**
	 * 
	 * @Title: getWorkTypeBar   
	 * @Description: TODO 获取HR人员的工种占比
	 * @param orgId
	 * @return
	 * List<Map<String,Object>>    
	 * @throws
	 */
	public List<Map<String, Object>>getWorkTypeBar(@Param(value="orgId")String orgId);
	
	/**
	 * 
	 * @Title: getNativePlacePie   
	 * @Description: TODO HR人员籍贯占比
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getNativePlacePie(@Param(value="orgId")String orgId);
	
	/**
	 * 
	 * @Title: getBaseInfoAnalysis   
	 * @Description: TODO 人事档案分析
	 * @param orgId
	 * @param deptId
	 * @param module
	 * @param groupBy
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String,String>>getBaseInfoAnalysis(
			@Param(value="orgId")String orgId,
			@Param(value="deptId")String deptId,
			@Param(value="module")String module,
			@Param(value="groupBy")String groupBy
			);
	/**
	 * 
	 * @Title: getContractAnalysis   
	 * @Description: TODO 人事合同分析
	 * @param orgId
	 * @param deptId
	 * @param module
	 * @param groupBy
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getContractAnalysis(
			@Param(value="orgId")String orgId,
			@Param(value="deptId")String deptId,
			@Param(value="module")String module,
			@Param(value="groupBy")String groupBy);
	
	/**
	 * 
	 * @Title: getIncentiveAnalysis   
	 * @Description: TODO 合同奖惩分析
	 * @param orgId
	 * @param deptId
	 * @param module
	 * @param groupBy
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getIncentiveAnalysis(
			@Param(value="orgId")String orgId,
			@Param(value="deptId")String deptId,
			@Param(value="module")String module,
			@Param(value="groupBy")String groupBy);
	/**
	 * 
	 * @Title: getLicenceAnalysis   
	 * @Description: TODO证照分析
	 * @param orgId
	 * @param deptId
	 * @param module
	 * @param groupBy
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getLicenceAnalysis(
			@Param(value="orgId")String orgId,
			@Param(value="deptId")String deptId,
			@Param(value="module")String module,
			@Param(value="groupBy")String groupBy);
	/**
	 * 
	 * @Title: getLearnAnalysis   
	 * @Description: TODO 学习经历分析
	 * @param orgId
	 * @param deptId
	 * @param module
	 * @param groupBy
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getLearnAnalysis(
			@Param(value="orgId")String orgId,
			@Param(value="deptId")String deptId,
			@Param(value="module")String module,
			@Param(value="groupBy")String groupBy);
	/**
	 * 
	 * @Title: getSkillsAnalysis   
	 * @Description: TODO 劳动技能分析
	 * @param orgId
	 * @param deptId
	 * @param module
	 * @param groupBy
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getSkillsAnalysis(
			@Param(value="orgId")String orgId,
			@Param(value="deptId")String deptId,
			@Param(value="module")String module,
			@Param(value="groupBy")String groupBy);	
	/**
	 * 
	 * @Title: getTransferAnalysis   
	 * @Description: TODO 人事调动分析
	 * @param orgId
	 * @param deptId
	 * @param module
	 * @param groupBy
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getTransferAnalysis(
			@Param(value="orgId")String orgId,
			@Param(value="deptId")String deptId,
			@Param(value="module")String module,
			@Param(value="groupBy")String groupBy);	
	/**
	 * 
	 * @Title: getLeaveAnalysis   
	 * @Description: TODO 离职分析
	 * @param orgId
	 * @param deptId
	 * @param module
	 * @param groupBy
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getLeaveAnalysis(
			@Param(value="orgId")String orgId,
			@Param(value="deptId")String deptId,
			@Param(value="module")String module,
			@Param(value="groupBy")String groupBy);
	/**
	 * 
	 * @Title: getReinstatAnalysis   
	 * @Description: TODO 人员复职分析
	 * @param orgId
	 * @param deptId
	 * @param module
	 * @param groupBy
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getReinstatAnalysis(
			@Param(value="orgId")String orgId,
			@Param(value="deptId")String deptId,
			@Param(value="module")String module,
			@Param(value="groupBy")String groupBy);	
	/**
	 * 
	 * @Title: getEvaluationAnalysis   
	 * @Description: TODO 职称评定分析
	 * @param orgId
	 * @param deptId
	 * @param module
	 * @param groupBy
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getEvaluationAnalysis(
			@Param(value="orgId")String orgId,
			@Param(value="deptId")String deptId,
			@Param(value="module")String module,
			@Param(value="groupBy")String groupBy);	
	/**
	 * 
	 * @Title: getCareAnalysis   
	 * @Description: TODO 人员关怀分析
	 * @param orgId
	 * @param deptId
	 * @param module
	 * @param groupBy
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getCareAnalysis(
			@Param(value="orgId")String orgId,
			@Param(value="deptId")String deptId,
			@Param(value="module")String module,
			@Param(value="groupBy")String groupBy);		
	
	
}
