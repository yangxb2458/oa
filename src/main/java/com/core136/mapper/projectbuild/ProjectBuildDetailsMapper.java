/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ProjectDetailsMapper.java   
 * @Package com.core136.mapper.projectbuild   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年9月7日 下午1:17:30   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.projectbuild;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.projectbuild.ProjectBuildDetails;
import org.core136.common.dbutils.MyMapper;

/**   
 * @ClassName:  ProjectDetailsMapper   
 * @Description:TODO 工程项目台账
 * @author: 稠云信息 
 * @date:   2019年9月7日 下午1:17:30   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface ProjectBuildDetailsMapper extends MyMapper<ProjectBuildDetails>{

	/**
	 * 
	 * @Title: getprojectbuildlist   
	 * @Description: TODO 获取工程项目列表 
	 * @param: orgId
	 * @param: sortId
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getprojectbuilddetailslist(@Param(value="orgId") String orgId,@Param(value="sortId") String sortId,@Param(value="search") String search);
	

	/**
	 * 
	 * @Title: selectProjectBuild2ByTitle   
	 * @Description: TODO 模糊查询项目名称
	 * @param: projectTitle
	 * @param: orgId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> selectProjectBuild2ByTitle(@Param(value="orgId") String orgId,@Param(value="projectTitle") String projectTitle);

	
	/**
	 * 
	 * @Title: getProjectBuildListForTree   
	 * @Description: TODO 获取项目列
	 * @param: orgId
	 * @param: sortId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>>getProjectBuildListForTree(@Param(value="orgId") String orgId,@Param(value="sortId") String sortId);

}
