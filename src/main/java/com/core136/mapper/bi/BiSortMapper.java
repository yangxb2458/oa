package com.core136.mapper.bi;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.bi.BiSort;
import org.core136.common.dbutils.MyMapper;

@Mapper
public interface BiSortMapper extends MyMapper<BiSort>{
	/**
	 * 
	 * @Title getBiSortTree   
	 * @Description TODO 获取BI分类树结构
	 * @param levelId
	 * @param orgId
	 * @return      
	 * List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getBiSortTree (@Param(value="levelId") String levelId,@Param(value="orgId") String orgId);
}
