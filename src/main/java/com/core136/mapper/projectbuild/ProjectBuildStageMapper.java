/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ProjectBuildStageMapper.java   
 * @Package com.core136.mapper.projectbuild   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年9月28日 上午9:37:48   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.projectbuild;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.projectbuild.ProjectBuildStage;
import org.core136.common.dbutils.MyMapper;

/**   
 * @ClassName:  ProjectBuildStageMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年9月28日 上午9:37:48   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface ProjectBuildStageMapper extends MyMapper<ProjectBuildStage>{

	/**
	 * 
	 * @Title: getprojectbuildStagelist   
	 * @Description: TODO 按ProjectId 获节工程节点数据  
	 * @param: orgId
	 * @param: projectId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>>getprojectbuildStagelist(@Param(value="orgId") String orgId,@Param(value="projectId") String projectId);
	
	/**
	 * 
	 * @Title: getprojectbuildStageOpenlist   
	 * @Description: TODO 获取当前工程的进行中的节点
	 * @param: orgId
	 * @param: projectId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>>getprojectbuildStageOpenlist(@Param(value="orgId") String orgId,@Param(value="projectId") String projectId);
	
}
