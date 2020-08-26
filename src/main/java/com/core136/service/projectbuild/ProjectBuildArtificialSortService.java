/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ProjectBuildArtificialSortService.java   
 * @Package com.core136.service.projectbuild   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年9月28日 上午9:53:33   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.projectbuild;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.projectbuild.ProjectBuildArtificialSort;
import com.core136.mapper.projectbuild.ProjectBuildArtificialSortMapper;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  ProjectBuildArtificialSortService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年9月28日 上午9:53:33   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class ProjectBuildArtificialSortService {
@Autowired
private ProjectBuildArtificialSortMapper projectBuildArtificialSortMapper;

public int insertProjectBuildArtificialSort(ProjectBuildArtificialSort projectBuildArtificialSort)
{
	return projectBuildArtificialSortMapper.insert(projectBuildArtificialSort);
}

public ProjectBuildArtificialSort selectOneProjectBuildArtificialSort(ProjectBuildArtificialSort projectBuildArtificialSort)
{
	return projectBuildArtificialSortMapper.selectOne(projectBuildArtificialSort);
}

public int deleteProjectBuildArtificialSort(ProjectBuildArtificialSort projectBuildArtificialSort)
{
	return projectBuildArtificialSortMapper.delete(projectBuildArtificialSort);
}

public int updateProjectBuildArtificialSort(Example example,ProjectBuildArtificialSort projectBuildArtificialSort)
{
	return projectBuildArtificialSortMapper.updateByExampleSelective(projectBuildArtificialSort, example);
}

/**
 * 
 * @Title: getProjectBuildArtificialSortTree   
 * @Description: TODO 获取工种分类  
 * @param: orgId
 * @param: sortLeave
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getProjectBuildArtificialSortTree(String orgId,String sortLeave)
{
	return projectBuildArtificialSortMapper.getProjectBuildArtificialSortTree(orgId, sortLeave);
}
/**
 * 
 * @Title: isExistChild   
 * @Description: TODO 判断是否有子集
 * @param: orgId
 * @param: sortId
 * @param: @return      
 * @return: int      
 * @throws
 */
public int isExistChild(String orgId, String sortId) {
	// TODO Auto-generated method stub
	return projectBuildArtificialSortMapper.isExistChild(orgId, sortId);
}


}
