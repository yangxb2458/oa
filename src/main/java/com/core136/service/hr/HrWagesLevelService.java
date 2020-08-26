/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  HrWorkTypeService.java   
 * @Package com.core136.service.hr   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2020年1月6日 下午3:47:39   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.hr;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.hr.HrWagesLevel;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrWagesLevelMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class HrWagesLevelService {
@Autowired
private HrWagesLevelMapper hrWagesLevelMapper;

public int insertHrWagesLevel(HrWagesLevel hrWagesLevel)
{
	return hrWagesLevelMapper.insert(hrWagesLevel);
}

public int deleteHrWagesLevel(HrWagesLevel hrWagesLevel)
{
	return hrWagesLevelMapper.delete(hrWagesLevel);
}

public int updateHrWagesLevel(Example example,HrWagesLevel hrWagesLevel)
{
	return hrWagesLevelMapper.updateByExampleSelective(hrWagesLevel, example);
}

public HrWagesLevel selectOneHrWagesLevel(HrWagesLevel hrWagesLevel)
{
	return hrWagesLevelMapper.selectOne(hrWagesLevel);
}
/**
 * 
 * @Title: getWorkTypeList   
 * @Description: TODO 获取工资级别列表
 * @param: orgId
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getWagesLevelList(String orgId,String search)
{
	return hrWagesLevelMapper.getWagesLevelList(orgId,"%"+search+"%");
}
/**
 * 
 * @Title: getWagesLevelList   
 * @Description: TODO 获取工资级别列表
 * @param: pageParam
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getWagesLevelList(PageParam pageParam) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getWagesLevelList(pageParam.getOrgId(),pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

public List<Map<String, String>>getWagesLevelListForSelect(String orgId)
{
	return hrWagesLevelMapper.getWagesLevelListForSelect(orgId);
}

}
