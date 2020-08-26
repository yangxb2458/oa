/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  OfficeSuppliesApplyMapper.java   
 * @Package com.core136.mapper.officesupplies   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月25日 上午9:03:16   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.officesupplies;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.officesupplies.OfficeSuppliesApply;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface OfficeSuppliesApplyMapper extends MyMapper<OfficeSuppliesApply>{
/**
 * 
 * @Title: getMyApplyOfficeSuppliesList   
 * @Description: TODO 获取个人历史申请记录
 * @param: orgId
 * @param: accountId
 * @param: beginTime
 * @param: endTime
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
	public List<Map<String, String>>getMyApplyOfficeSuppliesList(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId,
			@Param(value="beginTime") String beginTime,@Param(value="endTime") String endTime,@Param(value="status") String status,@Param(value="search") String search);
	/**
	 * 
	 * @Title: getApplyOfficeSuppliesList   
	 * @Description: TODO 获取审批列表
	 * @param: orgId
	 * @param: accountId
	 * @param: beginTime
	 * @param: endTime
	 * @param: status
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getApplyOfficeSuppliesList(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId,
			@Param(value="beginTime") String beginTime,@Param(value="endTime") String endTime,@Param(value="status") String status,@Param(value="search") String search);
	
	
	/**
	 * 
	 * @Title: getGrantOfficeSuppliesList   
	 * @Description: TODO 待发放用品列表
	 * @param: orgId
	 * @param: accountId
	 * @param: beginTime
	 * @param: endTime
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getGrantOfficeSuppliesList(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId,
			@Param(value="beginTime") String beginTime,@Param(value="endTime") String endTime,@Param(value="search") String search);
	
}
