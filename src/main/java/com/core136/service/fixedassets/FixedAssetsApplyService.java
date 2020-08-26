/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  FixedAssetsApplyService.java   
 * @Package com.core136.service.fixedassets   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年12月26日 下午2:52:08   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.fixedassets;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.fixedassets.FixedAssetsApply;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.fixedassets.FixedAssetsApplyMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class FixedAssetsApplyService {
@Autowired
private FixedAssetsApplyMapper fixedAssetsApplyMapper;

public int insertFixedAssetsApply(FixedAssetsApply fixedAssetsApply)
{
	return fixedAssetsApplyMapper.insert(fixedAssetsApply);
}

public int deleteFixedAssetsApply(FixedAssetsApply fixedAssetsApply)
{
	return fixedAssetsApplyMapper.delete(fixedAssetsApply);
}

public int updateFixedAssetsApply(Example example,FixedAssetsApply fixedAssetsApply)
{
	return fixedAssetsApplyMapper.updateByExampleSelective(fixedAssetsApply, example);
}

public FixedAssetsApply selectOneFixedAssetsApply(FixedAssetsApply fixedAssetsApply)
{
	return fixedAssetsApplyMapper.selectOne(fixedAssetsApply);
}
/**
 * 
 * @Title: getFixedAssetsApplyList   
 * @Description: TODO 获取申请列表
 * @param: orgId
 * @param: accountId
 * @param: status
 * @param: beginTime
 * @param: endTime
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getFixedAssetsApplyList(String orgId,String accountId,String status,String beginTime,String endTime,String assetsSortId,String search,String opFlag)
{
	return fixedAssetsApplyMapper.getFixedAssetsApplyList(orgId, accountId, status, beginTime, endTime, assetsSortId,"%"+search+"%",opFlag);
}

/**
 * 
 * @Title: getFixedAssetsApplyList   
 * @Description: TODO 获取申请列表
 * @param: pageParam
 * @param: status
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getFixedAssetsApplyList(PageParam pageParam,String status,String beginTime,String endTime,String assetsSortId) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getFixedAssetsApplyList(pageParam.getOrgId(),pageParam.getAccountId(),status,beginTime,endTime,assetsSortId,pageParam.getSearch(),pageParam.getOpFlag());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getApplyAndApproveInfo   
 * @Description: TODO 申请与审批详情
 * @param: orgId
 * @param: applyId
 * @param: @return      
 * @return: Map<String,String>      
 * @throws
 */
public Map<String, String>getApplyAndApproveInfo(String orgId,String applyId)
{
	return fixedAssetsApplyMapper.getApplyAndApproveInfo(orgId, applyId);
}

}
