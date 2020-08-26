package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.hr.HrKpiItem;

@Mapper
public interface HrKpiItemMapper extends MyMapper<HrKpiItem>{

	/**
	 * 
	 * @Title: getHrKpiItemList   
	 * @Description: TODO 获取考核指标列表
	 * @param orgId
	 * @param kpiType
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrKpiItemList(@Param(value="orgId")String orgId,@Param(value="createUser")String createUser,@Param(value="kpiType")String kpiType,@Param(value="search")String search);
	
}
