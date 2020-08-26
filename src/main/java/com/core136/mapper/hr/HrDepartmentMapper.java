/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  HrDepartmentMapper.java   
 * @Package com.core136.mapper.hr   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月29日 上午9:29:47   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.hr.HrDepartment;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface HrDepartmentMapper extends MyMapper<HrDepartment>{

	/**
	 * 
	 * @Title: getHrDepartmentTree   
	 * @Description: TODO 获取HR部门树
	 * @param: orgLevelId
	 * @param: orgId
	 * @param: @return      
	 * @return: List<Map<String,Object>>      
	 * @throws
	 */
	public List<Map<String,String>> getHrDepartmentTree(@Param(value="orgId") String orgId,@Param(value="orgLevelId") String orgLevelId);

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
	public List<Map<String,String>> getHrUserInfoDepartmentTree(@Param(value="orgId") String orgId,@Param(value="orgLevelId") String orgLevelId);
	
	/**
	 * 
	 * @Title: getHrDeptNameByStr   
	 * @Description: TODO 获取部门名称列表
	 * @param orgId
	 * @param list
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrDeptNameByStr(@Param(value="orgId")String orgId,@Param(value="list")List<String> list);
	/**
	 * 
	 * @Title: getHrDeptBySearchdept   
	 * @Description: TODO 查询部门
	 * @param orgId
	 * @param searchdept
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrDeptBySearchdept(@Param(value="orgId")String orgId,@Param(value="searchdept") String searchdept);
	
}
