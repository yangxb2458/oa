/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  HrUserInfoMapper.java   
 * @Package com.core136.mapper.hr   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月29日 上午9:34:31   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.hr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.beust.jcommander.Parameter;
import com.core136.bean.hr.HrUserInfo;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface HrUserInfoMapper extends MyMapper<HrUserInfo>{
	/**
	 * 
	 * @Title: getHrUserInfoByDeptId   
	 * @Description: TODO 获取部门下的人员列表
	 * @param: orgId
	 * @param: deptId
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
public List<Map<String,String>> getHrUserInfoByDeptId(@Param(value="orgId") String orgId,@Param(value="deptId") String deptId);
/**
 * 
 * @Title: getHrUserInfoByBeptIdInWorkList   
 * @Description: TODO 获取部门下的人员
 * @param orgId
 * @param deptId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String,String>> getHrUserInfoByBeptIdInWorkList(@Param(value="orgId") String orgId,
		@Param(value="deptId") String deptId,@Param(value="workStatus") String workStatus,
		@Param(value="employedTime") String employedTime,@Param(value="staffCardNo")String staffCardNo,
		@Param(value="search") String search);

/**
 * 
 * @Title: getHrUserInfoForTree   
 * @Description: TODO 按部门获取人员列表   
 * @param: orgId
 * @param: deptId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getHrUserInfoForTree(@Param(value="orgId") String orgId,@Param(value="deptId") String deptId);
/**
 * 
 * @Title: getUserNamesByUserIds   
 * @Description: TODO 获取HR人员列表
 * @param orgId
 * @param list
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getUserNamesByUserIds(@Param(value="orgId")String orgId,@Param(value="list")List<String>list);

/**
 * 
 * @Title: getHrUserInfoBySearchuser   
 * @Description: TODO 查询HR人员
 * @param orgId
 * @param searchuser
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String,String>>getHrUserInfoBySearchuser(@Param(value="orgId")String orgId,@Param(value="searchuser") String searchuser);

/**
 * 
 * @Title: getHrUserInfoListByDeptId   
 * @Description: TODO 获取部门下的人员列表
 * @param orgId
 * @param deptId
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrUserInfoListByDeptId(@Param(value="orgId")String orgId,@Param(value="deptId")String deptId,@Param(value="search")String search);

/**
 * 
 * @Title: getDeskHrUserInfo   
 * @Description: TODO 获取人力资源门户人员信息
 * @param orgId
 * @return
 * Map<String,String>    
 * @throws
 */
public Map<String, String>getDeskHrUserInfo(@Param(value="orgId")String orgId);

}
