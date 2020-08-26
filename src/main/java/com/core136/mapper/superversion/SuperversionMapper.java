/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  SuperversionMapper.java   
 * @Package com.core136.mapper.superversion   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月28日 下午4:27:45   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.superversion;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.superversion.Superversion;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface SuperversionMapper extends MyMapper<Superversion>{
/**
 * 
 * @Title: getSupperversionList   
 * @Description: TODO 我的历史记录 
 * @param: orgId
 * @param: accountId
 * @param: type
 * @param: handedUser
 * @param: beginTime
 * @param: endTime
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
	public List<Map<String, String>> getSupperversionList(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId,
			@Param(value="type") String type,@Param(value="handedUser") String handedUser,@Param(value="beginTime") String beginTime,
			@Param(value="endTime") String endTime,@Param(value="status") String status,@Param(value="search") String search);
	/**
	 * 
	 * @Title: getSupperversionPorcessList   
	 * @Description: TODO 获取待处理的督查列表  
	 * @param: orgId
	 * @param: accountId
	 * @param: type
	 * @param: handedUser
	 * @param: beginTime
	 * @param: endTime
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>> getSupperversionPorcessList(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId,
			@Param(value="type") String type,@Param(value="handedUser") String handedUser,@Param(value="beginTime") String beginTime,
			@Param(value="endTime") String endTime,@Param(value="status") String status,@Param(value="search") String search);
	
	/**
	 * 
	 * @Title: getLeadManageSupperversionList   
	 * @Description: TODO 获取当前用户管控的事件列表
	 * @param: orgId
	 * @param: accountId
	 * @param: type
	 * @param: handedUser
	 * @param: beginTime
	 * @param: endTime
	 * @param: status
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>> getLeadManageSupperversionList(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId,
			@Param(value="type") String type,@Param(value="handedUser") String handedUser,@Param(value="beginTime") String beginTime,
			@Param(value="endTime") String endTime,@Param(value="status") String status,@Param(value="search") String search);

	/**
	 * 
	 * @Title: getQuerySuperversionForDept   
	 * @Description: TODO 按部门汇总
	 * @param: orgId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>>getQuerySuperversionForDept(@Param(value="orgId")String orgId);
	

}


