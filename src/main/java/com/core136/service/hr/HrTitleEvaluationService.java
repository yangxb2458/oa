package com.core136.service.hr;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.hr.HrTitleEvaluation;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrTitleEvaluationMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrTitleEvaluationService {
@Autowired
private HrTitleEvaluationMapper hrTitleEvaluationMapper;

public int insertHrTitleEvaluation(HrTitleEvaluation hrTitleEvaluation)
{
	return hrTitleEvaluationMapper.insert(hrTitleEvaluation);
}

public int deleteHrTitleEvaluation(HrTitleEvaluation hrTitleEvaluation)
{
	return hrTitleEvaluationMapper.delete(hrTitleEvaluation);
}

public int updateHrTitleEvaluation(Example example,HrTitleEvaluation hrTitleEvaluation)
{
	return hrTitleEvaluationMapper.updateByExampleSelective(hrTitleEvaluation, example);
}

public HrTitleEvaluation selectOneHrTitleEvaluation(HrTitleEvaluation hrTitleEvaluation)
{
	return hrTitleEvaluationMapper.selectOne(hrTitleEvaluation);
}
/**
 * 
 * @Title: getHrTitleEvaluationList   
 * @Description: TODO 获取人员职称评定列表
 * @param orgId
 * @param userId
 * @param beginTime
 * @param endTime
 * @param getType
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrTitleEvaluationList(String orgId,String userId,String beginTime,String endTime,String getType,String search)
{
	return hrTitleEvaluationMapper.getHrTitleEvaluationList(orgId,userId, beginTime, endTime, getType, "%"+search+"%");
}
/**
 * 
 * @Title: getHrTitleEvaluationList   
 * @Description: TODO 获取人员职称评定列表
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param getType
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrTitleEvaluationList(PageParam pageParam,String userId,String beginTime,String endTime,String getType) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrTitleEvaluationList(pageParam.getOrgId(),userId, beginTime, endTime,getType,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
