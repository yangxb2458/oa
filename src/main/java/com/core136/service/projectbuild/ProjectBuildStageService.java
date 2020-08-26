/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ProjectBuildStageService.java   
 * @Package com.core136.service.projectbuild   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年9月28日 上午9:38:49   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.projectbuild;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.projectbuild.ProjectBuildStage;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.projectbuild.ProjectBuildStageMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  ProjectBuildStageService   
 * @Description:TODO 工程阶段设置
 * @author: 稠云信息 
 * @date:   2019年9月28日 上午9:38:49   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class ProjectBuildStageService {
@Autowired
private ProjectBuildStageMapper projectBuildStageMapper;

public int insertProjectBuildStage(ProjectBuildStage projectBuildStage)
{
	return projectBuildStageMapper.insert(projectBuildStage);
}


public int deleteProjectBuildStage(ProjectBuildStage projectBuildStage)
{
	return projectBuildStageMapper.delete(projectBuildStage);
}


public int updateProjectBuildStage(Example example,ProjectBuildStage projectBuildStage)
{
	return projectBuildStageMapper.updateByExampleSelective(projectBuildStage, example);
}

public ProjectBuildStage selectOneProjectBuildStage(ProjectBuildStage projectBuildStage)
{
	return projectBuildStageMapper. selectOne(projectBuildStage);
}
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
public List<Map<String,String>> getprojectbuildStagelist(String orgId,String projectId)
{
	return projectBuildStageMapper.getprojectbuildStagelist(orgId, projectId);
}


public PageInfo<Map<String, String>> getprojectbuildStagelist(PageParam pageParam,String projectId) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getprojectbuildStagelist(pageParam.getOrgId(),projectId);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: openStage   
 * @Description: TODO 开启当前节点为可用节点
 * @param: orgId
 * @param: projectId
 * @param: stageId
 * @param: @return      
 * @return: int      
 * @throws
 */
public int openStage(String orgId,String projectId,String stageId)
{
	ProjectBuildStage projectBuildStage = new ProjectBuildStage();
	projectBuildStage.setStatus("1");
	Example example = new Example(ProjectBuildStage.class);
	example.createCriteria().andEqualTo("orgId",orgId).andEqualTo("projectId",projectId).andEqualTo("seageId",stageId);
	return projectBuildStageMapper.updateByExampleSelective(projectBuildStage, example);
}
/**
 * 
 * @Title: closeStage   
 * @Description: TODO 关闭当前节点为不可用
 * @param: orgId
 * @param: projectId
 * @param: stageId
 * @param: @return      
 * @return: int      
 * @throws
 */
public int closeStage(String orgId,String projectId,String stageId)
{
	ProjectBuildStage projectBuildStage = new ProjectBuildStage();
	projectBuildStage.setStatus("0");
	Example example = new Example(ProjectBuildStage.class);
	example.createCriteria().andEqualTo("orgId",orgId).andEqualTo("projectId",projectId).andEqualTo("seageId",stageId);
	return projectBuildStageMapper.updateByExampleSelective(projectBuildStage, example);
	
}

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
public List<Map<String,String>> getprojectbuildStageOpenlist(String orgId,String projectId)
{
	return projectBuildStageMapper.getprojectbuildStageOpenlist(orgId, projectId);
}


}
