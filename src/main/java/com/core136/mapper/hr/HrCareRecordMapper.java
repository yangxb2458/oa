package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrCareRecord;

@Mapper
public interface HrCareRecordMapper extends MyMapper<HrCareRecord>{
/**
 * 
 * @Title: getHrCareRecordList   
 * @Description: TODO 获取人员关怀列表
 * @param orgId
 * @param userId
 * @param beginTime
 * @param endTime
 * @param careType
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
	public List<Map<String,String>>getHrCareRecordList(@Param(value="orgId")String orgId,
			@Param(value="userId")String userId,@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,@Param(value="careType")String careType,
			@Param(value="search")String search);
}
