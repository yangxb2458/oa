package com.core136.mapper.oa;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.oa.LeadActivity;

@Mapper
public interface LeadActivityMapper extends MyMapper<LeadActivity>{

	/**
	 * 
	 * @Title: getLeadActivityLsit   
	 * @Description: TODO 获取领导行程列表
	 * @param orgId
	 * @param beginTime
	 * @param endTime
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String,String>>getLeadActivityLsit(
			@Param(value="orgId")String orgId,@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,@Param(value="leader")String leader,@Param(value="search")String search);
	
}
