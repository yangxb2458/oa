/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ProjectBuildWorkerService.java   
 * @Package com.core136.service.projectbuild   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年9月28日 下午4:10:53   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.projectbuild;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.projectbuild.ProjectBuildWorker;
import com.core136.mapper.projectbuild.ProjectBuildWorkerMapper;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  ProjectBuildWorkerService   
 * @Description:TODO 工人台账   
 * @author: 稠云信息 
 * @date:   2019年9月28日 下午4:10:53   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class ProjectBuildWorkerService {
@Autowired
private ProjectBuildWorkerMapper projectBuildWorkerMapper;

public int insertProjectBuildWorker(ProjectBuildWorker projectBuildWorker)
{
	return projectBuildWorkerMapper.insert(projectBuildWorker);
}

public int deleteProjectBuildWorker(ProjectBuildWorker projectBuildWorker)
{
	return projectBuildWorkerMapper.delete(projectBuildWorker);
}

public int updateProjectBuildWorker(Example example,ProjectBuildWorker projectBuildWorker)
{
	return projectBuildWorkerMapper.updateByExampleSelective(projectBuildWorker, example);
}

public ProjectBuildWorker selectOneProjectBuildWorker(ProjectBuildWorker projectBuildWorker)
{
	return projectBuildWorkerMapper.selectOne(projectBuildWorker);
}

}
