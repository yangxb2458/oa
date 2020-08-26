package com.core136.service.projectbuild;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.projectbuild.ProjectBuildMaterial;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.projectbuild.ProjectBuildMaterialMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  ProjectBuildMaterialService   
 * @Description:TODO 工程建筑材料明细
 * @author: 稠云技术 
 * @date:   2019年9月21日 下午7:09:50   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class ProjectBuildMaterialService {
@Autowired
private ProjectBuildMaterialMapper projectBuildMaterialMapper;

public int insertProjectBuildMaterial(ProjectBuildMaterial projectBuildMaterial)
{
	return projectBuildMaterialMapper.insert(projectBuildMaterial);
}
public int updateProjectBuildMaterial(Example example,ProjectBuildMaterial projectBuildMaterial)
{
	return projectBuildMaterialMapper.updateByExampleSelective(projectBuildMaterial, example);
}
public int deleteProjectBuildMaterial(ProjectBuildMaterial projectBuildMaterial)
{
	return projectBuildMaterialMapper.delete(projectBuildMaterial);
}

public ProjectBuildMaterial selectOneProjectBuildMaterial(ProjectBuildMaterial projectBuildMaterial)
{
	return projectBuildMaterialMapper.selectOne(projectBuildMaterial);
}
/**
 * 
 * @Title: getmateriallist   
 * @Description: TODO 按分类获取材料明细 
 * @param: orgId
 * @param: sortId
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>>getmateriallist(String orgId,String sortId,String search)
{
	return projectBuildMaterialMapper.getmateriallist(orgId, sortId, "%"+search+"%");
}


public PageInfo<Map<String, String>> getmateriallist(PageParam pageParam,String sortId) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getmateriallist(pageParam.getOrgId(),sortId,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getMaterialListForSelet2   
 * @Description: TODO select2的材料列表
 * @param: orgId
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getMaterialListForSelet2(String orgId,String search)
{
	return projectBuildMaterialMapper.getMaterialListForSelet2(orgId,search);
}


}
