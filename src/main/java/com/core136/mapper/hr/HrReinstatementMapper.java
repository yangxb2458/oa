package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrReinstatement;

@Mapper
public interface HrReinstatementMapper extends MyMapper<HrReinstatement>{
/**
 * 
 * @Title: getHrReinstatementList   
 * @Description: TODO 获取复值列表
 * @param orgId
 * @param userId
 * @param beginTime
 * @param endTime
 * @param reinstatementType
 * @return
 * List<Map<String,String>>    
 * @throws
 */
	public List<Map<String, String>>getHrReinstatementList(@Param(value="orgId")String orgId,@Param(value="userId")String userId,
			@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime,@Param(value="reinstatementType") String reinstatementType);
}
