/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  FixedAssetsSortMapper.java   
 * @Package com.core136.mapper.fixedassets   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月25日 下午5:09:15   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.fixedassets;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.fixedassets.FixedAssetsSort;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */

@Mapper
public interface FixedAssetsSortMapper extends MyMapper<FixedAssetsSort>{

	/**
	 * 
	 * @Title: getFixedAssetSortTree   
	 * @Description: TODO 获取固定资产分类
	 * @param: orgId
	 * @param: parentId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getFixedAssetSortTree(@Param(value="orgId") String orgId,@Param(value="parentId") String parentId);
	/**
	 * 
	 * @Title: isExistChild   
	 * @Description: TODO 判断分类下是否有子节点
	 * @param: orgId
	 * @param: parentId
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	public int isExistChild(@Param(value="orgId") String orgId,@Param(value="parentId") String parentId);
	
}
