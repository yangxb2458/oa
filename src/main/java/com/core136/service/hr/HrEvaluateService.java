package com.core136.service.hr;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.hr.HrEvaluate;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrEvaluateMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrEvaluateService {
@Autowired
private HrEvaluateMapper hrEvaluateMapper;

public int insertHrEvaluate(HrEvaluate hrEvaluate)
{
	return hrEvaluateMapper.insert(hrEvaluate);
}

public int deleteHrEvaluate(HrEvaluate hrEvaluate)
{
	return hrEvaluateMapper.delete(hrEvaluate);
}
public int updateHrEvaluate(Example example,HrEvaluate hrEvaluate)
{
	return hrEvaluateMapper.updateByExampleSelective(hrEvaluate, example);
}

public HrEvaluate selectOneHrEvaluate(HrEvaluate hrEvaluate)
{
	return hrEvaluateMapper.selectOne(hrEvaluate);
}
/**
 * 
 * @Title: getHrEvaluateByUserIdList   
 * @Description: TODO 获取人员评价列表
 * @param orgId
 * @param userId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrEvaluateByUserIdList(String orgId,String userId)
{
	return hrEvaluateMapper.getHrEvaluateByUserIdList(orgId, userId);
}
/**
 * 
 * @Title: getHrEvaluateByUserIdList   
 * @Description: TODO 获取人员评价列表
 * @param pageParam
 * @param userId
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrEvaluateByUserIdList(PageParam pageParam,String userId) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrEvaluateByUserIdList(pageParam.getOrgId(),userId);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getHrEvaluateQueryList   
 * @Description: TODO 获取查询人员评价列表
 * @param orgId
 * @param userId
 * @param beginTime
 * @param endTime
 * @param status
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrEvaluateQueryList(String orgId,String userId,String beginTime,String endTime,String status,String search)
{
	return hrEvaluateMapper.getHrEvaluateQueryList(orgId, userId, beginTime, endTime, status, "%"+search+"%");
}

/**
 * 
 * @Title: getHrEvaluateQueryList   
 * @Description: TODO 获取查询人员评价列表
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param status
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrEvaluateQueryList(PageParam pageParam,String userId,String beginTime,String endTime,String status) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrEvaluateQueryList(pageParam.getOrgId(),userId,beginTime,endTime,status,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
