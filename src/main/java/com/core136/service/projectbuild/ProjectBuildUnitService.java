package com.core136.service.projectbuild;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.projectbuild.ProjectBuildUnit;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.projectbuild.ProjectBuildUnitMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  ErpUnitService   
 * @Description:TODO Erp计量单位操作类
 * @author: 稠云信息
 * @date:   2018年12月12日 上午9:13:43   
 *     
 * @Copyright: 2018 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class ProjectBuildUnitService{
@Autowired
private ProjectBuildUnitMapper projectBuildUnitMapper;
/**
 * 获取所有的计量单位
 */

public List<ProjectBuildUnit> getAllUnit(String orgId) {
	// TODO Auto-generated method stub
	return projectBuildUnitMapper.getAllUnit(orgId);
}
/**
 * 
 * @Title: selectOne   
 * @Description: TODO 获取单个计量单位
 * @param: erpUnit
 * @param: @return      
 * @return: ErpUnit      

 */
public ProjectBuildUnit selectOne(ProjectBuildUnit projectBuildUnit)
{
	return projectBuildUnitMapper.selectOne(projectBuildUnit);
}

public int insertProjectBuildUnit(ProjectBuildUnit projectBuildUnit)
{
	return projectBuildUnitMapper.insert(projectBuildUnit);
}

public int deleteProjectBuildUnit(ProjectBuildUnit projectBuildUnit)
{
	return projectBuildUnitMapper.delete(projectBuildUnit);
}

public int updateProjectBuildUnit(Example example,ProjectBuildUnit projectBuildUnit)
{
	return projectBuildUnitMapper.updateByExampleSelective(projectBuildUnit, example);
}
/**
 * 
 * @Title: getProjectBuildUnitList   
 * @Description: TODO 获取工程项目计量单位列表
 * @param: orgId
 * @param: type
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>> getProjectBuildUnitList(String orgId,String type,String search)
{
	return projectBuildUnitMapper.getProjectBuildUnitList(orgId, type, "%"+search+"%");
}
/**
 * 
 * @Title: getProjectBuildUnitList   
 * @Description: TODO获取工程项目计量单位列表
 * @param: pageParam
 * @param: type
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getProjectBuildUnitList(PageParam pageParam,String type) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getProjectBuildUnitList(pageParam.getOrgId(),type,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
