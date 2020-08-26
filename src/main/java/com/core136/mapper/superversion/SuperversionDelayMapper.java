/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  SuperversionDelayMapper.java   
 * @Package com.core136.mapper.superversion   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月19日 下午5:42:17   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.superversion;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.superversion.SuperversionDelay;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface SuperversionDelayMapper extends MyMapper<SuperversionDelay>{
/**
 * 
 * @Title: getDelayApplyList   
 * @Description: TODO 获取延期审批列表
 * @param: orgId
 * @param: accountId
 * @param: status
 * @param: beginTime
 * @param: endTime
 * @param: createUser
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
	public List<Map<String, String>>getDelayApplyList(
			@Param(value="orgId")String orgId,
			@Param(value="accountId")String accountId,
			@Param(value="status")String status,
			@Param(value="type")String type,
			@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,
			@Param(value="createUser")String createUser,
			@Param(value="search")String search
			);
	
}
