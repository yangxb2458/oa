package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrLearnRecord;

@Mapper
public interface HrLearnRecordMapper extends MyMapper<HrLearnRecord>{
	/**
	 * 
	 * @Title: getHrLearnRecordList   
	 * @Description: TODO 获取教育经历列表
	 * @param orgId
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
public List<Map<String, String>>getHrLearnRecordList(@Param(value="orgId")String orgId,@Param(value="userId")String userId,
		@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime,@Param(value="search")String search);

/**
 * 
 * @Title: getMyHrLearnRecordList   
 * @Description: TODO 个人查询学习记录
 * @param orgId
 * @param accountId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyHrLearnRecordList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId);

}
