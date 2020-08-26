package com.core136.mapper.superversion;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.superversion.SuperversionConfig;
import org.core136.common.dbutils.MyMapper;
@Mapper
public interface SuperversionConfigMapper extends MyMapper<SuperversionConfig>{
/**
 * 
 * @Title: getAllSuperversionConfigList   
 * @Description: TODO 获取类型与领导列表
 * @param: orgId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
	public List<Map<String,String>>getAllSuperversionConfigList(@Param(value="orgId") String orgId);
	/**
	 * 
	 * @Title: getMySuperversionConfigList   
	 * @Description: TODO 与我有关的分类列表
	 * @param: orgId
	 * @param: accountId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>>getMySuperversionConfigList(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId);
	
	/**
	 * 
	 * @Title: getQuerySuperversionForType   
	 * @Description: TODO 按类型汇总
	 * @param: orgId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getQuerySuperversionForType(@Param(value="orgId")String orgId);
}
