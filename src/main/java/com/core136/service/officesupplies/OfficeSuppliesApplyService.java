/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  OfficeSuppliesApplyService.java   
 * @Package com.core136.service.officesupplies   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月25日 上午9:04:43   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.officesupplies;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.officesupplies.OfficeSuppliesApply;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.officesupplies.OfficeSuppliesApplyMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class OfficeSuppliesApplyService {
@Autowired
private OfficeSuppliesApplyMapper officeSuppliesApplyMapper;

public int insertOfficeSuppliesApply(OfficeSuppliesApply officeSuppliesApply)
{
	return officeSuppliesApplyMapper.insert(officeSuppliesApply);
}

public int deleteOfficeSuppliesApply(OfficeSuppliesApply officeSuppliesApply)
{
	return officeSuppliesApplyMapper.delete(officeSuppliesApply);
}

public int updateOfficeSuppliesApply(Example example,OfficeSuppliesApply officeSuppliesApply)
{
	return officeSuppliesApplyMapper.updateByExampleSelective(officeSuppliesApply, example);
}

public OfficeSuppliesApply selectOneOfficeSuppliesApply(OfficeSuppliesApply officeSuppliesApply)
{
	return officeSuppliesApplyMapper.selectByPrimaryKey(officeSuppliesApply);
}
/**
 * 
 * @Title: getMyApplyOfficeSuppliesList   
 * @Description: TODO  获取个人历史申请记录
 * @param: orgId
 * @param: accountId
 * @param: beginTime
 * @param: endTime
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getMyApplyOfficeSuppliesList(String orgId,String accountId,String beginTime,String endTime,String status,String search)
{
	return officeSuppliesApplyMapper.getMyApplyOfficeSuppliesList(orgId, accountId, beginTime, endTime,status, "%"+search+"%");
}
/**
 * 
 * @Title: getMyApplyOfficeSuppliesList   
 * @Description: TODO  获取个人历史申请记录 
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getMyApplyOfficeSuppliesList(PageParam pageParam,String beginTime,String endTime,String status) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMyApplyOfficeSuppliesList(pageParam.getOrgId(),pageParam.getAccountId(),beginTime,endTime,status,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getApplyOfficeSuppliesList   
 * @Description: TODO 获取审批列表
 * @param: orgId
 * @param: accountId
 * @param: beginTime
 * @param: endTime
 * @param: status
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getApplyOfficeSuppliesList(String orgId,String accountId,String beginTime,String endTime,String status,String search)
{
	return officeSuppliesApplyMapper.getApplyOfficeSuppliesList(orgId, accountId, beginTime, endTime,status, "%"+search+"%");
}


public PageInfo<Map<String, String>> getApplyOfficeSuppliesList(PageParam pageParam,String beginTime,String endTime,String status) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getApplyOfficeSuppliesList(pageParam.getOrgId(),pageParam.getAccountId(),beginTime,endTime,status,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getGrantOfficeSuppliesList   
 * @Description: TODO 待发放用品列表   
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getGrantOfficeSuppliesList(String orgId,String accountId,String beginTime,String endTime,String search)
{
	return officeSuppliesApplyMapper.getGrantOfficeSuppliesList(orgId, accountId, beginTime, endTime,"%"+search+"%");
}

/**
 * 
 * @Title: getGrantOfficeSuppliesList   
 * @Description: TODO 待发放用品列表   
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getGrantOfficeSuppliesList(PageParam pageParam,String beginTime,String endTime) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getGrantOfficeSuppliesList(pageParam.getOrgId(),pageParam.getAccountId(),beginTime,endTime,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
