package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrEvaluate;

@Mapper
public interface HrEvaluateMapper extends MyMapper<HrEvaluate>{

	/**
	 * 
	 * @Title: getHrEvaluateByUserIdList   
	 * @Description: TODO 获取人员评价列表
	 * @param orgId
	 * @param userId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrEvaluateByUserIdList(@Param(value="orgId") String orgId,@Param(value="userId")String userId);
	/**
	 * 
	 * @Title: getHrEvaluateQueryList   
	 * @Description: TODO 获取查询人员评价列表
	 * @param orgId
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param status
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrEvaluateQueryList(@Param(value="orgId")String orgId,@Param(value="userId")String userId,
			@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime,@Param(value="status")String status,
			@Param(value="search")String search
			);
	
	
}
