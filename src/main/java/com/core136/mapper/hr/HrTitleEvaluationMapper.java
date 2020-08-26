package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrTitleEvaluation;


@Mapper
public interface HrTitleEvaluationMapper extends MyMapper<HrTitleEvaluation>{
 
	/**
	 * 
	 * @Title: getHrTitleEvaluationList   
	 * @Description: TODO 获取人员职称评定列表
	 * @param orgId
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param getType
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrTitleEvaluationList(
			@Param(value="orgId")String orgId,
			@Param(value="userId")String userId,
			@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,
			@Param(value="getType")String getType,
			@Param(value="search")String search);
	
}
