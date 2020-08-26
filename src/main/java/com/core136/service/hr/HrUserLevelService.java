/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  HrUserLevelServic.java   
 * @Package com.core136.service.hr   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月30日 上午10:42:13   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.hr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.hr.HrUserLevel;
import com.core136.mapper.hr.HrUserLevelMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class HrUserLevelService{
@Autowired
private HrUserLevelMapper hrUserLevelMapper;

public int insertHrUserLevel(HrUserLevel hrUserLevel)
{
	return hrUserLevelMapper.insert(hrUserLevel);
}

public int deleteHrUserLevel(HrUserLevel hrUserLevel)
{
	return hrUserLevelMapper.delete(hrUserLevel);
}

public int updateHrUserLevel(Example example,HrUserLevel hrUserLevel)
{
	return hrUserLevelMapper.updateByExampleSelective(hrUserLevel, example);
}

public HrUserLevel selectOneHrUserLevel(HrUserLevel hrUserLevel)
{
	return hrUserLevelMapper.selectOne(hrUserLevel);
}

public List<HrUserLevel> selectByExample(Example example)
{
	return hrUserLevelMapper.selectByExample(example);
}

/**
 * 
 * @Title: getHrUserLevelChart   
 * @Description: TODO 获取行政级别CHART数据
 * @param: orgId
 * @param: leaveId
 * @param: @return      
 * @return: List<Map<String,Object>>      
 * @throws
 */
public List<Map<String, Object>> getHrUserLevelChart(String orgId, String leaveId) {
	// TODO Auto-generated method stub
	return hrUserLevelMapper.getHrUserLevelChart(orgId, leaveId);
}

/**
 * 
 * @Title: getAllHrUserLevelChart   
 * @Description: TODO 获取行政级别CHART数据
 * @param: orgId
 * @param: leaveId
 * @param: @return      
 * @return: List<Map<String,Object>>      
 * @throws
 */
public List<Map<String, Object>> getAllHrUserLevelChart(String orgId,String leaveId)
{
	List<Map<String, Object>> ListMaper = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> userLevelList = getHrUserLevelChart(orgId,leaveId);
	for(int i=0;i<userLevelList.size();i++)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map=userLevelList.get(i);
		map.put("children", getAllHrUserLevelChart(orgId,userLevelList.get(i).get("id").toString()));
		ListMaper.add(map);
	}
	return ListMaper;
}

/**
 * 
 * @Title: getHrUserLevelByStr   
 * @Description: TODO 获取HR的行政级别名称
 * @param orgId
 * @param leaveIds
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrUserLevelByStr(String orgId,String levelIds)
{
	if(StringUtils.isNotBlank(levelIds))
	{
		String[] leaveIdArr = levelIds.split(",");
		List<String> list = Arrays.asList(leaveIdArr);
		return hrUserLevelMapper.getHrUserLevelByStr(orgId, list);
	}else
	{
		return null;
	}
}

}
