package com.core136.service.oa;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.oa.LeadActivity;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.oa.LeadActivityMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class LeadActivityService {
@Autowired
private LeadActivityMapper leadActivityMapper;

public int insertLeadActivity(LeadActivity leadActivity)
{
	return leadActivityMapper.insert(leadActivity);
}

public int deleteLeadActivity(LeadActivity leadActivity)
{
	return leadActivityMapper.delete(leadActivity);
}

public int updateLeadActivity(Example example,LeadActivity leadActivity)
{
	return leadActivityMapper.updateByExampleSelective(leadActivity, example);
}

public LeadActivity selectOneLeadActivity(LeadActivity leadActivity)
{
	return leadActivityMapper.selectOne(leadActivity);
}

/**
 * 
 * @Title: getLeadActivityLsit   
 * @Description: TODO 获取领导行程列表
 * @param orgId
 * @param beginTime
 * @param endTime
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getLeadActivityLsit(String orgId,String beginTime,String endTime,String leader,String search)
{
	return leadActivityMapper.getLeadActivityLsit(orgId, beginTime, endTime,leader, "%"+search+"%");
}
/**
 * 
 * @Title: getLeadActivityLsit   
 * @Description: TODO 获取领导行程列表
 * @param pageParam
 * @param beginTime
 * @param endTime
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getLeadActivityLsit(PageParam pageParam,String beginTime,String endTime,String leader) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getLeadActivityLsit(pageParam.getOrgId(),beginTime, endTime,leader, pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
