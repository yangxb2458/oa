package com.core136.service.projectbuild;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.projectbuild.ProjectBuildMaterialSort;
import com.core136.mapper.projectbuild.ProjectBuildMaterialSortMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class ProjectBuildMaterialSortService {
@Autowired
private ProjectBuildMaterialSortMapper projectBuildMaterialSortMapper;

public int insertProjectBuildMaterialSort(ProjectBuildMaterialSort projectBuildMaterialSort)
{
	return projectBuildMaterialSortMapper.insert(projectBuildMaterialSort);
}

public int updateProjectBuildMaterialSort(Example example,ProjectBuildMaterialSort projectBuildMaterialSort)
{
	return projectBuildMaterialSortMapper.updateByExampleSelective(projectBuildMaterialSort, example);
}

public ProjectBuildMaterialSort selectOneProjectBuildMaterialSort(ProjectBuildMaterialSort projectBuildMaterialSort)
{
	return projectBuildMaterialSortMapper.selectOne(projectBuildMaterialSort);
}

public int deleteProjectBuildMaterialSort(ProjectBuildMaterialSort projectBuildMaterialSort)
{
	return projectBuildMaterialSortMapper.delete(projectBuildMaterialSort);
}

/**
 * 获取工程材料的分类树结构
 */

public List<Map<String, String>> getMaterialSortTree(String orgId, String sortLeave) {
	return projectBuildMaterialSortMapper.getMaterialSortTree(orgId, sortLeave);
}

/**
 * 判断是否还有子集
 */

public int isExistChild(String orgId, String sortId) {
	// TODO Auto-generated method stub
	return projectBuildMaterialSortMapper.isExistChild(orgId, sortId);
}

}
