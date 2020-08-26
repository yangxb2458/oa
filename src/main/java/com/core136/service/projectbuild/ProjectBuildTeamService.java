/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ProjectBuildTeamService.java   
 * @Package com.core136.service.projectbuild   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年9月28日 下午4:25:52   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.projectbuild;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.projectbuild.ProjectBuildTeam;
import com.core136.mapper.projectbuild.ProjectBuildTeamMapper;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  ProjectBuildTeamService   
 * @Description:TODO 工程对账台  
 * @author: 稠云信息 
 * @date:   2019年9月28日 下午4:25:52   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class ProjectBuildTeamService {
@Autowired
private ProjectBuildTeamMapper projectBuildTeamMapper;

public int insertProjectBuildTeam(ProjectBuildTeam projectBuildTeam)
{
	return projectBuildTeamMapper.insert(projectBuildTeam);
}

public int deleteProjectBuildTeam(ProjectBuildTeam projectBuildTeam)
{
	return projectBuildTeamMapper.delete(projectBuildTeam);
}

public int updateProjectBuildTeam(Example example,ProjectBuildTeam projectBuildTeam)
{
	return projectBuildTeamMapper.updateByExampleSelective(projectBuildTeam, example);
}

public ProjectBuildTeam selectOneProjectBuildTeam(ProjectBuildTeam projectBuildTeam)
{
	return projectBuildTeamMapper.selectOne(projectBuildTeam);
}

}
