/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  AttendConfigService.java   
 * @Package com.core136.service.attend   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月14日 下午3:57:17   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.attend;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.attend.AttendConfig;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.attend.AttendConfigMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class AttendConfigService {
@Autowired
private AttendConfigMapper attendConfigMapper;

public int insertAttendConfig(AttendConfig attendConfig)
{
	return attendConfigMapper.insert(attendConfig);
}

public int deleteAttendConfig(AttendConfig attendConfig)
{
	return attendConfigMapper.delete(attendConfig);
}

public int updateAttendConfig(AttendConfig attendConfig,Example example)
{
	return attendConfigMapper.updateByExampleSelective(attendConfig, example);
}

public AttendConfig selectOneAttendConfig(AttendConfig attendConfig)
{
	return attendConfigMapper.selectOne(attendConfig);
}

public List<AttendConfig> getAllConfig(AttendConfig attendConfig)
{
	return attendConfigMapper.select(attendConfig);
}

/**
 * 
 * @Title: getConfigList   
 * @Description: TODO 获取所有的考勤记录 
 * @param: orgId
 * @param: @return      
 * @return: List<AttendConfig>      
 * @throws
 */
public List<AttendConfig> getConfigList(String orgId)
{
	AttendConfig attendConfig = new AttendConfig();
	attendConfig.setOrgId(orgId);
	return attendConfigMapper.select(attendConfig);
}


public PageInfo<AttendConfig> getAttendConfigList(PageParam pageParam,AttendConfig attendConfig)
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<AttendConfig> datalist= getAllConfig(attendConfig);
	PageInfo<AttendConfig> pageInfo = new PageInfo<AttendConfig>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getAttendConfigList   
 * @Description: TODO 获取考勤列表
 * @param: orgId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>> getAllAttendConfigList(String orgId)
{
	return attendConfigMapper.getAllAttendConfigList(orgId);
}
/**
 * 
 * @Title: getAttendConfigList   
 * @Description: TODO 获取考勤配置列表
 * @param: orgId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>> getMyAttendConfigList(String orgId)
{
	return attendConfigMapper.getMyAttendConfigList(orgId);
}


}
