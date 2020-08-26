/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  HrTrainRecordMapper.java   
 * @Package com.core136.mapper.hr   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月28日 上午9:02:54   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.hr.HrTrainRecord;
import org.core136.common.dbutils.MyMapper;

/**
 * 人员培训记录
 * @author lsq
 *
 */
@Mapper
public interface HrTrainRecordMapper extends MyMapper<HrTrainRecord>{

	/**
	 * 
	 * @Title: getHrTrainRecordList   
	 * @Description: TODO 获取培训列表
	 * @param orgId
	 * @param createUser
	 * @param channel
	 * @param courseType
	 * @param status
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrTrainRecordList(@Param(value="orgId")String orgId,@Param(value="createUser")String createUser,
			@Param(value="channel")String channel,@Param(value="courseType")String courseType,@Param(value="status")String status,
			@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime,@Param(value="search")String search
			);
	
	/**
	 * 
	 * @Title: getHrTrainRecordApprovedList   
	 * @Description: TODO 获取待审批记录
	 * @param orgId
	 * @param accountId
	 * @param channel
	 * @param courseType
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrTrainRecordApprovedList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,
			@Param(value="channel")String channel,@Param(value="courseType")String courseType,
			@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime,@Param(value="search")String search
			);
	/**
	 * 
	 * @Title: getHrTrainRecordApprovedOldList   
	 * @Description: TODO 获取审批记录
	 * @param orgId
	 * @param accountId
	 * @param channel
	 * @param courseType
	 * @param status
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrTrainRecordApprovedOldList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,
			@Param(value="channel")String channel,@Param(value="courseType")String courseType,@Param(value="status")String status,
			@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime,@Param(value="search")String search
			);
	
}
