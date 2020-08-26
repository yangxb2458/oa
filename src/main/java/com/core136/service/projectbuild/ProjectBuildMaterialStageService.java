package com.core136.service.projectbuild;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.projectbuild.ProjectBuildMaterialStage;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.projectbuild.ProjectBuildMaterialStageMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ProjectBuildMaterialStageService {

	@Autowired
	private ProjectBuildMaterialStageMapper projectBuildMaterialStageMapper;
	
	public int insertProjectBuildMaterialStage(ProjectBuildMaterialStage projectBuildMaterialStage)
	{
		return projectBuildMaterialStageMapper.insert(projectBuildMaterialStage);
	}
	public int deleteProjectBuildMaterialStage(ProjectBuildMaterialStage projectBuildMaterialStage)
	{
		return projectBuildMaterialStageMapper.delete(projectBuildMaterialStage);
	}
	public int updateProjectBuildMaterialStage(Example example,ProjectBuildMaterialStage projectBuildMaterialStage)
	{
		return projectBuildMaterialStageMapper.updateByExampleSelective(projectBuildMaterialStage, example);
	}
	
	public ProjectBuildMaterialStage selectOneProjectBuildMaterialStage(ProjectBuildMaterialStage projectBuildMaterialStage)
	{
		return projectBuildMaterialStageMapper.selectOne(projectBuildMaterialStage);
	}
	
	public List<Map<String,String>>getMaterialStageList(String orgId,String stageId,String search)
	{
		return projectBuildMaterialStageMapper.getMaterialStageList(orgId, stageId,search);
	}
	
	/**
	 * 
	 * @Title: getMaterialStageList   
	 * @Description: TODO 工程节点具体材料警戒列表
	 * @param: pageParam
	 * @param: stageId
	 * @param: @return      
	 * @return: PageInfo<Map<String,String>>      
	 * @throws
	 */
	public PageInfo<Map<String, String>> getMaterialStageList(PageParam pageParam,String stageId) {
		PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= getMaterialStageList(pageParam.getOrgId(),stageId,"%"+pageParam.getSearch()+"%");
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
	}
	
	/**
	 * 
	 * @Title: isExistChild   
	 * @Description: TODO判断当前材料是否已存在
	 * @param: orgId
	 * @param: stageId
	 * @param: materialId
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	public int isExistChild(String orgId,String stageId,String materialId)
	{
		return projectBuildMaterialStageMapper.isExistChild(orgId, stageId, materialId);
	}
	
	/**
	 * 
	 * @Title: getMaterialListInStage   
	 * @Description: TODO 获取当前节点可选择的材料列表
	 * @param: orgId
	 * @param: stageId
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getMaterialListInStage(String orgId,String stageId,String search)
	{
		return projectBuildMaterialStageMapper.getMaterialListInStage(orgId, stageId, "%"+search+"%");
	}
	
	
	
	/**
	 * 
	 * @Title: getSurplusQuantity   
	 * @Description: TODO 获取剩余量
	 * @param: orgId
	 * @param: materialId
	 * @param: stageId
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	public int getSurplusQuantity(String orgId,String materialId,String stageId)
	{
		return 1;
	}


}
