/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  SuperversionProcessMapper.java   
 * @Package com.core136.mapper.superversion   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月18日 上午9:16:59   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.superversion;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.superversion.SuperversionProcess;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface SuperversionProcessMapper extends MyMapper<SuperversionProcess>{

	/**
	 * 
	 * @Title: getSuperversionProcess   
	 * @Description: TODO 获取事件处理过程列表
	 * @param: orgId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>>getMyCompleteProcessList(@Param(value="orgId") String orgId,@Param(value="accountId")String accountId,@Param(value="beginTime") String beginTime,
			@Param(value="endTime") String endTime,@Param(value="type") String type,@Param(value="search")String search);
	/**
	 * 
	 * @Title: getControlProcessList   
	 * @Description: TODO 获取我所管控的任务列表
	 * @param: orgId
	 * @param: accountId
	 * @param: beginTime
	 * @param: endTime
	 * @param: type
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>>getControlProcessList(@Param(value="orgId") String orgId,@Param(value="accountId")String accountId,@Param(value="beginTime") String beginTime,
			@Param(value="endTime") String endTime,@Param(value="type") String type,@Param(value="search")String search);
	
	
	
}
