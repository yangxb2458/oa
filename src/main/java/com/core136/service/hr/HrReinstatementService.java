package com.core136.service.hr;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.hr.HrReinstatement;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrReinstatementMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrReinstatementService {
@Autowired
private HrReinstatementMapper hrReinstatementMapper;

public int insertHrReinstatement(HrReinstatement hrReinstatement)
{
	return hrReinstatementMapper.insert(hrReinstatement);
}

public int deleteHrReinstatement(HrReinstatement hrReinstatement)
{
	return hrReinstatementMapper.delete(hrReinstatement);
}

public int updateHrReinstatement(Example example,HrReinstatement hrReinstatement)
{
	return hrReinstatementMapper.updateByExampleSelective(hrReinstatement, example);
}

public HrReinstatement selectOneHrReinstatement(HrReinstatement hrReinstatement)
{
	return hrReinstatementMapper.selectOne(hrReinstatement);
}
/**
 * 
 * @Title: getHrReinstatementList   
 * @Description: TODO 获取复值列表
 * @param orgId
 * @param userId
 * @param beginTime
 * @param endTime
 * @param reinstatementType
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrReinstatementList(String orgId,String userId,String beginTime,String endTime,String reinstatementType)
{
	return hrReinstatementMapper.getHrReinstatementList(orgId, userId, beginTime, endTime, reinstatementType);
}
/**
 * 
 * @Title: getHrReinstatementList   
 * @Description: TODO 获取复值列表
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param reinstatementType
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrReinstatementList(PageParam pageParam,String userId,String beginTime,String endTime,String reinstatementType) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrReinstatementList(pageParam.getOrgId(),userId, beginTime, endTime,reinstatementType);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
