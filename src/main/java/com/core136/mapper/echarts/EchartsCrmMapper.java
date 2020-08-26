package com.core136.mapper.echarts;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EchartsCrmMapper {
	/**
	 * 
	 * @Title: getBiCustomerIndustryPie   
	 * @Description: TODO 获取CRM的行业占比
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
public List<Map<String,String>>getBiCustomerIndustryPie(@Param(value="orgId")String orgId);
/**
 * 
 * @Title: getBiCustomerKeepUserPie   
 * @Description: TODO 获取CRM销售人员的占比
 * @param orgId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String,String>>getBiCustomerKeepUserPie(@Param(value="orgId")String orgId);

/**
 * 
 * @Title: getBiCustomerAreaPie   
 * @Description: TODO 获取CRM地区占比
 * @param orgId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String,String>>getBiCustomerAreaPie(@Param(value="orgId")String orgId);

/**
 * 
 * @Title: getBiCustomerLevelPie   
 * @Description: TODO 获取CRM客户等级占比
 * @param orgId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String,String>>getBiCustomerLevelPie(@Param(value="orgId")String orgId);

}
