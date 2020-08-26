package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrIncentive;

@Mapper
public interface HrIncentiveMapper extends MyMapper<HrIncentive>{

	/**
	 * 
	 * @Title: getHrIncentiveList   
	 * @Description: TODO 获取奖惩记录列表
	 * @param orgId
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param incentiveType
	 * @param incentiveItem
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrIncentiveList(@Param(value="orgId")String orgId,
			@Param(value="userId")String userId,@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,@Param(value="incentiveType") String incentiveType,
			@Param(value="incentiveItem") String incentiveItem
			);
	/**
	 * 
	 * @Title: getMyHrIncentiveList   
	 * @Description: TODO 个人查询奖惩记录
	 * @param orgId
	 * @param accountId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getMyHrIncentiveList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId);
}
