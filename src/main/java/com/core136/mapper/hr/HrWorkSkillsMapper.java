package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrWorkSkills;

@Mapper
public interface HrWorkSkillsMapper extends MyMapper<HrWorkSkills>{

	/**
	 * 
	 * @Title: getHrWorkSkillsList   
	 * @Description: TODO 工作特长列表
	 * @param orgId
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param skillsLevel
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrWorkSkillsList(@Param(value="orgId")String orgId,
			@Param(value="userId")String userId,
			@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,
			@Param(value="skillsLevel")String skillsLevel,
			@Param(value="search")String search
			);
	
}
