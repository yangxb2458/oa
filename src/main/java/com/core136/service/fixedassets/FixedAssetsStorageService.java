/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  FixedAssetsStorageService.java   
 * @Package com.core136.service.fixedassets   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年12月17日 上午10:14:15   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.fixedassets;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.fixedassets.FixedAssetsStorage;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.fixedassets.FixedAssetsStorageMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class FixedAssetsStorageService {
@Autowired
private FixedAssetsStorageMapper fixedAssetsStorageMapper;

public int insertFixedAssetsStorage(FixedAssetsStorage fixedAssetsStorage)
{
	return fixedAssetsStorageMapper.insert(fixedAssetsStorage);
}

public int deleteFixedAssetsStorage(FixedAssetsStorage fixedAssetsStorage)
{
	return fixedAssetsStorageMapper.delete(fixedAssetsStorage);
}

public int updateFixedAssetsStorage(Example example, FixedAssetsStorage fixedAssetsStorage)
{
	return fixedAssetsStorageMapper.updateByExampleSelective(fixedAssetsStorage, example);
}

public FixedAssetsStorage selectOneFixedAssetsStorage(FixedAssetsStorage fixedAssetsStorage)
{
	return fixedAssetsStorageMapper.selectOne(fixedAssetsStorage);
}
/**
 * 
 * @Title: getFixedAssetsStorageList   
 * @Description: TODO获取仓库列表
 * @param: orgId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getFixedAssetsStorageList(String orgId,String search)
{
	return fixedAssetsStorageMapper.getFixedAssetsStorageList(orgId,"%"+search+"%");
}

/**
 * 
 * @Title: getFixedAssetsStorageList   
 * @Description: TODO获取仓库列表
 * @param: pageParam
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getFixedAssetsStorageList(PageParam pageParam) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getFixedAssetsStorageList(pageParam.getOrgId(),pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getAllFixedAssetsStorageList   
 * @Description: TODO 获取仓库列表
 * @param: orgId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getAllFixedAssetsStorageList(String orgId)
{
	return fixedAssetsStorageMapper.getAllFixedAssetsStorageList(orgId);
}

}
