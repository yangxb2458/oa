package com.core136.service.projectbuild;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.projectbuild.ProjectBuildMaterialMx;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.projectbuild.ProjectBuildMaterialMxMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ProjectBuildMaterialMxService {
@Autowired
private ProjectBuildMaterialMxMapper projectBuildMaterialMxMapper;

public int insertProjectBuildMaterialMx(ProjectBuildMaterialMx projectBuildMaterialMx)
{
	return projectBuildMaterialMxMapper.insert(projectBuildMaterialMx);
}

public int deleteProjectBuildMaterialMx(ProjectBuildMaterialMx projectBuildMaterialMx)
{
	return projectBuildMaterialMxMapper.delete(projectBuildMaterialMx);
}

public int updateProjectBuildMaterialMx(Example example, ProjectBuildMaterialMx projectBuildMaterialMx)
{
	return projectBuildMaterialMxMapper.updateByExampleSelective(projectBuildMaterialMx, example);
}

public ProjectBuildMaterialMx selectOneProjectBuildMaterialMx(ProjectBuildMaterialMx projectBuildMaterialMx)
{
	return projectBuildMaterialMxMapper.selectOne(projectBuildMaterialMx);
}
/**
 * 
 * @Title: getPurchaseMaterialMxList   
 * @Description: TODO 获取材料采购清单
 * @param: orgId
 * @param: purchaseId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getPurchaseMaterialMxList(String orgId,String purchaseId,String search)
{
	return projectBuildMaterialMxMapper.getPurchaseMaterialMxList(orgId, purchaseId,search);
}
/**
 * 
 * @Title: getPurchaseMaterialMxList   
 * @Description: TODO 获取材料采购清单
 * @param: pageParam
 * @param: stageId
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getPurchaseMaterialMxList(PageParam pageParam,String stageId) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getPurchaseMaterialMxList(pageParam.getOrgId(),stageId,"%"+pageParam.getSearch()+"%");
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
