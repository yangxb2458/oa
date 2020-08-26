package com.core136.mapper.crm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.crm.CrmIndustry;
import org.core136.common.dbutils.MyMapper;
@Mapper
public interface CrmIndustryMapper extends MyMapper<CrmIndustry>{
	/**
	 * 
	* @Title: getAllIndustryList 
	* @Description: TODO 获取企业分类列表
	* @param @param orgId
	* @param @param search
	* @param @return 设定文件 
	* @return List<Map<String,Object>> 返回类型 

	 */
	public List<Map<String,Object>> getAllIndustryList(@Param(value="orgId") String orgId,@Param(value="search") String search);
}
