/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  FixedAssetsService.java   
 * @Package com.core136.service.fixedassets   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月25日 下午5:10:51   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.fixedassets;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.fixedassets.FixedAssets;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.fixedassets.FixedAssetsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class FixedAssetsService {
@Autowired
private  FixedAssetsMapper fixedAssetsMapper;

public int insertFixedAssets(FixedAssets fixedAssets)
{
	return fixedAssetsMapper.insert(fixedAssets);
}


public int deleteFixedAssets(FixedAssets fixedAssets)
{
	return fixedAssetsMapper.delete(fixedAssets);
}

public int updateFixedAssets(Example example,FixedAssets fixedAssets)
{
	return fixedAssetsMapper.updateByExampleSelective(fixedAssets, example);
}

public FixedAssets selectOneFixedAssets(FixedAssets fixedAssets)
{
	return fixedAssetsMapper.selectOne(fixedAssets);
}
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
public List<Map<String,String>>getFixedAssetsList(String orgId,String beginTime,String endTime,String sortId,String search)
{
	return fixedAssetsMapper.getFixedAssetsList(orgId,beginTime,endTime, sortId,"%"+search+"%");
}

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
public List<Map<String,String>>getAllocationList(String orgId,String beginTime,String endTime,String sortId,String search)
{
	return fixedAssetsMapper.getAllocationList(orgId,beginTime,endTime, sortId,"%"+search+"%");
}

/**
 * 
 * @Title: getFixedAssetsList   
 * @Description: TODO 固定资产列表   
 * @param: pageParam
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getFixedAssetsList(PageParam pageParam,String beginTime,String endTime,String sortId) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getFixedAssetsList(pageParam.getOrgId(),beginTime,endTime,sortId,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getAllocationList   
 * @Description: TODO 获取调拨列表  
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: sortId
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getAllocationList(PageParam pageParam,String beginTime,String endTime,String sortId) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getAllocationList(pageParam.getOrgId(),beginTime,endTime,sortId,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

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
public List<Map<String,String>>queryFixedAssetsList(String orgId,String beginTime,String endTime,String sortId,String ownDept,String status,String search)
{
	return fixedAssetsMapper.queryFixedAssetsList(orgId,beginTime,endTime, sortId,ownDept,status,"%"+search+"%");
}
/**
 * 
 * @Title: queryFixedAssetsList   
 * @Description: TODO 查询固定资产列表
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: sortId
 * @param: ownDept
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> queryFixedAssetsList(PageParam pageParam,String beginTime,String endTime,String sortId,String ownDept,String status) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= queryFixedAssetsList(pageParam.getOrgId(),beginTime,endTime,sortId,status,ownDept,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getApplyFixedAssetsList   
 * @Description: TODO 获取可申请的固定资产的列表
 * @param: orgId
 * @param: sortId
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>>getApplyFixedAssetsList(String orgId,String sortId,String search)
{
	return fixedAssetsMapper.getApplyFixedAssetsList(orgId,sortId,"%"+search+"%");
}
/**
 * 
 * @Title: getApplyFixedAssetsList   
 * @Description: TODO 获取可申请的固定资产的列表
 * @param: pageParam
 * @param: sortId
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getApplyFixedAssetsList(PageParam pageParam,String sortId) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getApplyFixedAssetsList(pageParam.getOrgId(),sortId,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
}
