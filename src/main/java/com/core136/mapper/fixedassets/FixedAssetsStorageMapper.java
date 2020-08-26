/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  FixedAssetsStorageMapper.java   
 * @Package com.core136.mapper.fixedassets   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年12月17日 上午10:12:21   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.fixedassets;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.fixedassets.FixedAssetsStorage;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface FixedAssetsStorageMapper extends MyMapper<FixedAssetsStorage>{

	/**
	 * 
	 * @Title: getFixedAssetsStorageList   
	 * @Description: TODO 获取仓库列表
	 * @param: orgId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getFixedAssetsStorageList(@Param(value = "orgId") String orgId,@Param(value = "search") String search);

	/**
	 * 
	 * @Title: getAllFixedAssetsStorageList   
	 * @Description: TODO 获取仓库列表
	 * @param: orgId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getAllFixedAssetsStorageList(@Param(value="orgId") String orgId);

}
