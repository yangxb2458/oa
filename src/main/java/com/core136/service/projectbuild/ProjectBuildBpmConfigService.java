/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ProjectBuildBpmConfigService.java   
 * @Package com.core136.service.projectbuild   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月7日 上午9:27:56   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.projectbuild;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.projectbuild.ProjectBuildBpmConfig;
import com.core136.mapper.projectbuild.ProjectBuildBpmConfigMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class ProjectBuildBpmConfigService {
@Autowired
private ProjectBuildBpmConfigMapper projectBuildBpmConfigMapper;

public int insertProjectBuildBpmConfig(ProjectBuildBpmConfig projectBuildBpmConfig)
{
	return projectBuildBpmConfigMapper.insert(projectBuildBpmConfig);
}

public int deleteProjectBuildBpmConfig(ProjectBuildBpmConfig projectBuildBpmConfig)
{
	return projectBuildBpmConfigMapper.delete(projectBuildBpmConfig);
}

public int updateProjectBuildBpmConfig(Example example, ProjectBuildBpmConfig projectBuildBpmConfig)
{
	return projectBuildBpmConfigMapper.updateByExampleSelective(projectBuildBpmConfig, example);
}

public ProjectBuildBpmConfig selectOneProjectBuildBpmConfig(ProjectBuildBpmConfig projectBuildBpmConfig)
{
	return projectBuildBpmConfigMapper.selectOne(projectBuildBpmConfig);
}

/**
 * 
 * @Title: getAllProjectBuildBpmConfig   
 * @Description: TODO 获取本工程下的所有BPM配置列表
 * @param: orgId
 * @param: @return      
 * @return: List<Map<String, String>>      
 * @throws
 */
public List<Map<String, String>> getAllProjectBuildBpmConfig(String orgId)
{
	return projectBuildBpmConfigMapper.getAllProjectBuildBpmConfig(orgId);
}




}
