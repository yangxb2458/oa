/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  HrDepartmentService.java   
 * @Package com.core136.service.hr   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月29日 上午9:30:40   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.hr;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.hr.HrDepartment;
import com.core136.mapper.hr.HrDepartmentMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class HrDepartmentService {
@Autowired
private HrDepartmentMapper hrDepartmentMapper;
public int insertHrDepartment(HrDepartment hrDepartment)
{
	return hrDepartmentMapper.insert(hrDepartment);
}

public int deleteHrDepartment(HrDepartment hrDepartment)
{
	return hrDepartmentMapper.delete(hrDepartment);
}

public int updateHrDepartment(Example example,HrDepartment hrDepartment)
{
	return hrDepartmentMapper.updateByExampleSelective(hrDepartment, example);
}

public HrDepartment selectOneHrDepartment(HrDepartment hrDepartment)
{
	return hrDepartmentMapper.selectOne(hrDepartment);
}
/**
 * 
 * @Title: getHrDepartmentTree   
 * @Description: TODO获取HR部门树
 * @param: orgId
 * @param: orgLevelId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getHrDepartmentTree(String orgId,String orgLevelId)
{
	return hrDepartmentMapper.getHrDepartmentTree(orgId,orgLevelId);
}
/**
 * 
 * @Title: getHrUserInfoDepartmentTree   
 * @Description: TODO 人员基本信息树结构
 * @param: orgId
 * @param: orgLevelId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getHrUserInfoDepartmentTree(String orgId,String orgLevelId)
{
	return hrDepartmentMapper.getHrUserInfoDepartmentTree(orgId,orgLevelId);
}
/**
 * 
 * @Title: getHrDeptList   
 * @Description: TODO 获取子部门列表
 * @param example
 * @return
 * List<HrDepartment>    
 * @throws
 */
public List<HrDepartment> getHrDeptList(Example example)
{
	return hrDepartmentMapper.selectByExample(example);
}
/**
 * 
 * @Title: getHrDeptNameByStr   
 * @Description: TODO 获取部门名称列表
 * @param orgId
 * @param deptStrs
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrDeptNameByStr(String orgId,String deptStrs)
{
	if(StringUtils.isNotBlank(deptStrs))
	{
		String[] deptArr = deptStrs.split(",");
		List<String> list = Arrays.asList(deptArr);
		return hrDepartmentMapper.getHrDeptNameByStr(orgId, list);
	}else
	{
		return null;
	}
}

/**
 * 
 * @Title: getHrDeptBySearchdept   
 * @Description: TODO 查询HR部门
 * @param orgId
 * @param searchdept
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrDeptBySearchdept(String orgId,String searchdept)
{
	return hrDepartmentMapper.getHrDeptBySearchdept(orgId,"%"+searchdept+"%");
}

}
