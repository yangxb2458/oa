package com.core136.mapper.echarts;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EchartsFinanceMapper {

	/**
	 * 
	 * @Title: getPayableListData   
	 * @Description: TODO 获取合同应付款列表
	 * @param orgId
	 * @param beginTime
	 * @param endTime
	 * @return
	 * List<Map<String,Double>>    
	 * @throws
	 */
	public List<Map<String, Double>>getPayableListData(@Param(value="orgId")String orgId,@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime);
	
	/**
	 * 
	 * @Title: getReceviablesListData   
	 * @Description: TODO 获取合同应收款
	 * @param orgId
	 * @param beginTime
	 * @param endTime
	 * @return
	 * List<Map<String,Double>>    
	 * @throws
	 */
	public List<Map<String, Double>>getReceviablesListData(@Param(value="orgId")String orgId,@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime);

	/**
	 * 
	 * @Title: getPayReceivTotalData   
	 * @Description: TODO 获取应收应付总数
	 * @param orgId
	 * @return
	 * Map<String,String>  
	 * @throws
	 */
	public Map<String, String>getPayReceivTotalData(@Param(value="orgId")String orgId);
}
