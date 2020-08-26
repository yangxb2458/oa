/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  HrLicenceMapper.java   
 * @Package com.core136.mapper.hr   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年12月31日 上午10:08:01   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.hr.HrLicence;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface HrLicenceMapper extends MyMapper<HrLicence>{
	/**
	 * 
	 * @Title: getHrLicenceList   
	 * @Description: TODO 获取证照列表
	 * @param orgId
	 * @param userId
	 * @param beginTime
	 * @param licenceType
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
public List<Map<String, String>>getHrLicenceList(@Param(value="orgId")String orgId,
		@Param(value="userId")String userId,@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime,
		@Param(value="licenceType")String licenceType,@Param(value="search")String search
		);

/**
 * 
 * @Title: getMyHrLicenceList   
 * @Description: TODO 查询个人证照信息
 * @param orgId
 * @param accountId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyHrLicenceList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId);

}
