package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrLeaveRecord;

@Mapper
public interface HrLeaveRecordMapper extends MyMapper<HrLeaveRecord>{
/**
 * 
 * @Title: getHrLeaveRecordList   
 * @Description: TODO 获取离职人员列表
 * @param orgId
 * @param userId
 * @param beginTime
 * @param endTime
 * @param leaveType
 * @return
 * List<Map<String,String>>    
 * @throws
 */
	public List<Map<String, String>>getHrLeaveRecordList(@Param(value="orgId")String orgId,@Param(value="userId")String userId,
			@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime,@Param(value="leaveType")String leaveType);
}
