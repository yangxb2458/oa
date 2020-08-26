/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ProjectBuildBpmConfigMapper.java   
 * @Package com.core136.mapper.projectbuild   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月7日 上午9:23:17   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.projectbuild;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.projectbuild.ProjectBuildBpmConfig;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface ProjectBuildBpmConfigMapper extends MyMapper<ProjectBuildBpmConfig>{

	/**
	 * 
	 * @Title: getAllProjectBuildBpmConfig   
	 * @Description: TODO(描述这个方法的作用)   
	 * @param: orgId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>>getAllProjectBuildBpmConfig(@Param(value="orgId") String orgId);
}
