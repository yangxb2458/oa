/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  FixedAssetsSortService.java   
 * @Package com.core136.service.fixedassets   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月25日 下午5:11:15   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.fixedassets;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.fixedassets.FixedAssetsSort;
import com.core136.mapper.fixedassets.FixedAssetsSortMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */

@Service
public class FixedAssetsSortService {
@Autowired
private FixedAssetsSortMapper fixedAssetsSortMapper;

public int insertFixedAssetsSort(FixedAssetsSort fixedAssetsSort)
{
	return fixedAssetsSortMapper.insert(fixedAssetsSort);
}

public int deleteFixedAssetsSort(FixedAssetsSort fixedAssetsSort)
{
	return fixedAssetsSortMapper.delete(fixedAssetsSort);
}

public int updateFixedAssetsSort(Example example,FixedAssetsSort fixedAssetsSort)
{
	return fixedAssetsSortMapper.updateByExampleSelective(fixedAssetsSort, example);
}

public FixedAssetsSort selectOneFixedAssetsSort(FixedAssetsSort fixedAssetsSort)
{
	return fixedAssetsSortMapper.selectOne(fixedAssetsSort);
}

/**
 * 
 * @Title: getOfficeSuppliesSortTree   
 * @Description: TODO 获取办公用品分类
 * @param: orgId
 * @param: parentId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>> getFixedAssetSortTree(String orgId,String parentId)
{
	return fixedAssetsSortMapper.getFixedAssetSortTree(orgId, parentId);
}
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
public int isExistChild(String orgId,String parentId)
{
	return fixedAssetsSortMapper.isExistChild(orgId, parentId);
}

}
