package com.core136.service.projectbuild;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.projectbuild.ProjectBuildMaterialIn;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.projectbuild.ProjectBuildMaterialInMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ProjectBuildMaterialInService {
@Autowired
private ProjectBuildMaterialInMapper projectBuildMaterialInMapper;

public int insertProjectBuildMaterialIn(ProjectBuildMaterialIn projectBuildMaterialIn)
{
	return projectBuildMaterialInMapper.insert(projectBuildMaterialIn);
}

public int deleteProjectBuildMaterialIn(ProjectBuildMaterialIn projectBuildMaterialIn)
{
	return projectBuildMaterialInMapper.delete(projectBuildMaterialIn);
}

public int updateProjectBuildMaterialIn(Example example,ProjectBuildMaterialIn projectBuildMaterialIn)
{
	return projectBuildMaterialInMapper.updateByExampleSelective(projectBuildMaterialIn, example);
}

public ProjectBuildMaterialIn selectOneProjectBuildMaterialIn(ProjectBuildMaterialIn projectBuildMaterialIn)
{
	return projectBuildMaterialInMapper.selectOne(projectBuildMaterialIn);
}
/**
 * 
 * @Title: sumMaterialById   
 * @Description: TODO 获取当前材料已入库数量
 * @param: orgId
 * @param: purchaseId
 * @param: materialId
 * @param: @return      
 * @return: int      
 * @throws
 */
public int sumMaterialById(String orgId,String purchaseId,String materialId)
{
	return projectBuildMaterialInMapper.sumMaterialById(orgId, purchaseId, materialId);
}
/**
 * 
 * @Title: getMaterialByProjectId   
 * @Description: TODO 获取项目中可领用的材料列表
 * @param: orgId
 * @param: projectId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getMaterialByProjectId(String orgId,String projectId,String search)
{
	return projectBuildMaterialInMapper.getMaterialByProjectId(orgId, projectId,search);
}

/**
 * 
 * @Title: getMaterialByProjectId   
 * @Description: TODO 获取项目中可领用的材料列表
 * @param: pageParam
 * @param: projectId
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getMaterialByProjectId(PageParam pageParam,String projectId) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMaterialByProjectId(pageParam.getOrgId(),projectId,"%"+pageParam.getSearch()+"%");
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getMaterialInList   
 * @Description: TODO 获取项目材料入库记录
 * @param: orgId
 * @param: projectId
 * @param: materialId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getMaterialInList(String orgId,String projectId,String materialId)
{
	return projectBuildMaterialInMapper.getMaterialInList(orgId, projectId, materialId);
}

public PageInfo<Map<String, String>> getMaterialInList(PageParam pageParam,String projectId,String materialId) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMaterialInList(pageParam.getOrgId(),projectId,materialId);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getQueryMaterialInList   
 * @Description: TODO  查询入库记录
 * @param: orgId
 * @param: inUser
 * @param: beginTime
 * @param: endTime
 * @param: materialName
 * @param: purchaseTitle
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getQueryMaterialInList(String orgId, String inUser, String beginTime,String endTime, String materialName, String purchaseTitle)
{
	return projectBuildMaterialInMapper.getQueryMaterialInList(orgId, inUser, beginTime, endTime, "%"+materialName+"%", "%"+purchaseTitle+"%");
}


/**
 * 
 * @Title: getQueryMaterialInList   
 * @Description: TODO 查询入库记录
 * @param: pageParam
 * @param: inUser
 * @param: beginTime
 * @param: endTime
 * @param: materialName
 * @param: purchaseTitle
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getQueryMaterialInList(PageParam pageParam,String inUser, String beginTime,String endTime, String materialName, String purchaseTitle) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getQueryMaterialInList(pageParam.getOrgId(),inUser, beginTime, endTime, materialName, purchaseTitle);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
