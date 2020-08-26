package com.core136.service.projectbuild;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.projectbuild.ProjectBuildMaterialOut;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.projectbuild.ProjectBuildMaterialOutMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ProjectBuildMaterialOutService {
@Autowired
private ProjectBuildMaterialOutMapper projectBuildMaterialOutMapper;

public int insertProjectBuildMaterialOut(ProjectBuildMaterialOut projectBuildMaterialOut)
{
	return projectBuildMaterialOutMapper.insert(projectBuildMaterialOut);
}

public int deleteProjectBuildMaterialOut(ProjectBuildMaterialOut projectBuildMaterialOut)
{
	return projectBuildMaterialOutMapper.delete(projectBuildMaterialOut);
}

public int updateProjectBuildMaterialOut(Example example,ProjectBuildMaterialOut projectBuildMaterialOut)
{
	return projectBuildMaterialOutMapper.updateByExampleSelective(projectBuildMaterialOut, example);
}

public ProjectBuildMaterialOut selectOneProjectBuildMaterialOut(ProjectBuildMaterialOut projectBuildMaterialOut)
{
	return projectBuildMaterialOutMapper.selectOne(projectBuildMaterialOut);
}

/**
 * 
 * @Title: sumMaterialById   
 * @Description: TODO 统计当前材料已出库数量
 * @param: orgId
 * @param: projectId
 * @param: materialId
 * @param: @return      
 * @return: int      
 * @throws
 */
public int sumMaterialById(String orgId,String projectId,String materialId)
{
	return projectBuildMaterialOutMapper.sumMaterialById(orgId, projectId, materialId);
}
/**
 * 
 * @Title: getMaterialOutList   
 * @Description: TODO 按项目与材料获取出库账台
 * @param: orgId
 * @param: projectId
 * @param: materialId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>> getMaterialOutList(String orgId,String projectId,String materialId)
{
	return projectBuildMaterialOutMapper.getMaterialOutList(orgId, projectId, materialId);
}
/**
 * 
 * @Title: getQueryMaterialOutList   
 * @Description: TODO 获取材料出库记录 
 * @param: orgId
 * @param: beginTime
 * @param: endTime
 * @param: materialName
 * @param: projectTitle
 * @param: outUser
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getQueryMaterialOutList(String orgId,String beginTime,String endTime,
		String materialName,String projectTitle,String outUser)
{
	return projectBuildMaterialOutMapper.getQueryMaterialOutList(orgId, beginTime, endTime, "%"+materialName+"%", "%"+projectTitle+"%", outUser);
}

/**
 * 
 * @Title: getQueryMaterialOutList   
 * @Description: TODO 获取材料出库记录  
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: materialName
 * @param: projectTitle
 * @param: outUser
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getQueryMaterialOutList(PageParam pageParam,String beginTime,String endTime, String materialName, String projectTitle,String outUser) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getQueryMaterialOutList(pageParam.getOrgId(),beginTime, endTime, materialName, projectTitle,outUser);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
