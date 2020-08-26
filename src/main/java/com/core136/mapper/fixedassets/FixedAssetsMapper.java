/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  FixedAssetsMapper.java   
 * @Package com.core136.mapper.fixedassets   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月25日 下午5:08:13   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.fixedassets;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.fixedassets.FixedAssets;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */

@Mapper
public interface FixedAssetsMapper extends MyMapper<FixedAssets>{
	/**
	 * 
	 * @Title: getFixedAssetsList   
	 * @Description: TODO 固定资产列表 
	 * @param: orgId
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getFixedAssetsList(@Param(value="orgId") String orgId,@Param(value="beginTime") String beginTime,
			@Param(value="endTime") String endTime,@Param(value="sortId") String sortId,@Param(value="search") String search);
/**
 * 
 * @Title: queryFixedAssetsList   
 * @Description: TODO 查询固定资产列表
 * @param: orgId
 * @param: beginTime
 * @param: endTime
 * @param: sortId
 * @param: ownDept
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
	public List<Map<String,String>> queryFixedAssetsList(@Param(value="orgId") String orgId,@Param(value="beginTime") String beginTime,
			@Param(value="endTime") String endTime,@Param(value="sortId") String sortId,@Param(value="ownDept") String ownDept,
			@Param(value="status") String status,@Param(value="search") String search);

	/**
	 * 
	 * @Title: getApplyFixedAssetsList   
	 * @Description: TODO 获取可申请的固定资产的列表
	 * @param: orgId
	 * @param: sortI
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getApplyFixedAssetsList(@Param(value="orgId") String orgId,@Param(value="sortId") String sortI,@Param(value="search") String search);
	
	/**
	 * 
	 * @Title: getAllocationList   
	 * @Description: TODO 获取调拨列表
	 * @param: orgId
	 * @param: beginTime
	 * @param: endTime
	 * @param: sortId
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getAllocationList(@Param(value="orgId") String orgId,@Param(value="beginTime") String beginTime,
			@Param(value="endTime") String endTime,@Param(value="sortId") String sortId,@Param(value="search") String search);

}
