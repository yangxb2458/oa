package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrWorkRecord;
import com.fasterxml.jackson.annotation.JacksonInject.Value;

@Mapper
public interface HrWorkRecordMapper extends MyMapper<HrWorkRecord>{
	/**
	 * 
	 * @Title: getHrWorkRecordList   
	 * @Description: TODO 获取工作记录
	 * @param orgId
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrWorkRecordList(@Param(value="orgId")String orgId,@Param(value="userId")String userId,
			@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime,@Param(value="nature") String nature,
			@Param(value="search")String search);

	/**
	 * 
	 * @Title: getMyHrWorkRecordList   
	 * @Description: TODO 查询个人工作经历
	 * @param orgId
	 * @param accountId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getMyHrWorkRecordList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId);
	
}
