package com.core136.service.projectbuild;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.projectbuild.ProjectBuildDetails;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.projectbuild.ProjectBuildDetailsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ProjectBuildDetailsService {
@Autowired
private ProjectBuildDetailsMapper projectBuildDetailsMapper;

public int insertProjectBuildDetails(ProjectBuildDetails projectBuildDetails)
{
	return projectBuildDetailsMapper.insert(projectBuildDetails);
}

public int updateProjectBuildDetails(Example example,ProjectBuildDetails projectBuildDetails)
{
	return projectBuildDetailsMapper.updateByExampleSelective(projectBuildDetails, example);
}

public int deleteProjectBuildDetails(ProjectBuildDetails projectBuildDetails)
{
	return projectBuildDetailsMapper.delete(projectBuildDetails);
}

public ProjectBuildDetails selectOneProjectBuildDetails(ProjectBuildDetails projectBuildDetails)
{
	return projectBuildDetailsMapper.selectOne(projectBuildDetails);
}
/**
 * 
 * @Title: getprojectbuilddetailslist   
 * @Description: TODO 获取工程项目表列
 * @param: orgId
 * @param: sortId
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getprojectbuilddetailslist(String orgId,String sortId,String search)
{
	return projectBuildDetailsMapper.getprojectbuilddetailslist(orgId, sortId, search);
}

public PageInfo<Map<String, String>> getprojectbuilddetailslist(PageParam pageParam,String sortId) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getprojectbuilddetailslist(pageParam.getOrgId(),sortId,"%"+pageParam.getSearch()+"%");
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getProjectBuildListForTree   
 * @Description: TODO 获取名称作为工程分类树结构 
 * @param: orgId
 * @param: sortId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>>getProjectBuildListForTree(String orgId,String sortId)
{
	return projectBuildDetailsMapper.getProjectBuildListForTree(orgId, sortId);
}
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
public List<Map<String,String>> selectProjectBuild2ByTitle(String orgId,String projectTitle)
{
	return projectBuildDetailsMapper.selectProjectBuild2ByTitle(orgId,projectTitle);
}


}
