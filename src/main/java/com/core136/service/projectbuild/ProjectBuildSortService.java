/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ProjectSortService.java   
 * @Package com.core136.service.projectbuild   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年9月7日 下午1:18:38   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.projectbuild;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.projectbuild.ProjectBuildSort;
import com.core136.mapper.projectbuild.ProjectBuildSortMapper;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  ProjectSortService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年9月7日 下午1:18:38   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class ProjectBuildSortService {
@Autowired
private ProjectBuildSortMapper projectBuildSortMapper;
/**
 * 
* @Title: insertProjectSort 
* @Description: TODO 添加分类
* @param @param projectSort
* @param @return 设定文件 
* @return int 返回类型
 */
public int insertProjectBuildSort(ProjectBuildSort projectBuildSort)
{
	return projectBuildSortMapper.insert(projectBuildSort);
}


public int deleteProjectBuildSort(ProjectBuildSort projectBuildSort)
{
	return projectBuildSortMapper.delete(projectBuildSort);
}


public int updateProjectBuildSort(Example example,ProjectBuildSort projectBuildSort)
{
	return projectBuildSortMapper.updateByExampleSelective(projectBuildSort, example);
}

public ProjectBuildSort selectOneProjectBuildSort(ProjectBuildSort projectBuildSort)
{
	return projectBuildSortMapper.selectOne(projectBuildSort);
}
/**
 * 
 * @Title: getProjectBuildSortTree   
 * @Description: TODO 获取工程分类树结构
 * @param: orgId
 * @param: sortLeave
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getProjectBuildSortTree(String orgId,String sortLeave)
{
	return projectBuildSortMapper.getProjectBuildSortTree(orgId, sortLeave);
}

public List<Map<String,String>> getProjectBuildSortAllParentTree(String orgId,String sortLeave)
{
	return projectBuildSortMapper.getProjectBuildSortAllParentTree(orgId, sortLeave);
}

/**
 * 
 * @Title: isExistChild   
 * @Description: TODO 判断是否还有子集
 * @param: orgId
 * @param: sortId
 * @param: @return      
 * @return: int      
 * @throws
 */
public int isExistChild(String orgId,String sortId)
{
	return projectBuildSortMapper.isExistChild(orgId, sortId);
}

}
