package com.core136.service.projectbuild;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.projectbuild.ProjectBuildContractSort;
import com.core136.mapper.projectbuild.ProjectBuildContractSortMapper;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  ProjectBuildContractSortService   
 * @Description:TODO 工程合同操作
 * @author: 稠云技术 
 * @date:   2019年10月9日 下午8:51:16   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class ProjectBuildContractSortService {
@Autowired
private ProjectBuildContractSortMapper projectBuildContractSortMapper;

public int insertProjectBuildContractSort(ProjectBuildContractSort projectBuildContractSort)
{
	return projectBuildContractSortMapper.insert(projectBuildContractSort);
}
public int deleteProjectBuildContractSort(ProjectBuildContractSort projectBuildContractSort)
{
	return projectBuildContractSortMapper.delete(projectBuildContractSort);
}

public int updateProjectBuildContractSort(Example example,ProjectBuildContractSort projectBuildContractSort)
{
	return projectBuildContractSortMapper.updateByExampleSelective(projectBuildContractSort, example);
}

public ProjectBuildContractSort selectOneProjectBuildContractSort(ProjectBuildContractSort projectBuildContractSort)
{
	return projectBuildContractSortMapper.selectOne(projectBuildContractSort);
}

/**
 * 获取合同的分类树结构
 */

public List<Map<String, String>> getProjectBuildContractSortTree(String orgId, String sortLeave) {
	return projectBuildContractSortMapper.getProjectBuildContractSortTree(orgId, sortLeave);
}

/**
 * 判断是否还有子集
 */

public int isExistChild(String orgId, String sortId) {
	// TODO Auto-generated method stub
	return projectBuildContractSortMapper.isExistChild(orgId, sortId);
}
}
