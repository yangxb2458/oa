package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrWelfareRecord;

@Mapper
public interface HrWelfareRecordMapper extends MyMapper<HrWelfareRecord>{

	/**
	 * 
	 * @Title: getHrWelfareRecordList   
	 * @Description: TODO 获取福利列表
	 * @param orgId
	 * @param beginTime
	 * @param endTime
	 * @param type
	 * @param userId
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrWelfareRecordList(@Param(value="orgId")String orgId,@Param(value="beginTime")String beginTime,
															@Param(value="endTime")String endTime,@Param(value="type")String type,
															@Param(value="userId")String userId,@Param(value="search")String search);
	
}
