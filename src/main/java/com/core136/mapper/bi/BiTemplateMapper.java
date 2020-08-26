package com.core136.mapper.bi;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.bi.BiTemplate;
import org.core136.common.dbutils.MyMapper;

@Mapper
public interface BiTemplateMapper extends MyMapper<BiTemplate>{
	/**
	 * 
	* @Title: getBiTemplateList 
	* @Description: TODO 按分类获取模版列表
	* @param @param orgId
	* @param @param levelId
	* @param @return 设定文件 
	* @return List<Map<String,Object>> 返回类型 

	 */
	public List<Map<String,Object>> getBiTemplateList(@Param (value="orgId") String orgId,@Param(value="levelId") String levelId,@Param(value="search") String search);
	
}
