package com.core136.service.projectbuild;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.projectbuild.ProjectBuildSupplier;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.projectbuild.ProjectBuildSupplierMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  ProjectBuildSupplierService   
 * @Description:TODO 供应商台账操作服务类
 * @author: 稠云技术 
 * @date:   2019年10月5日 下午3:56:39   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class ProjectBuildSupplierService {
@Autowired
private ProjectBuildSupplierMapper projectBuildSupplierMapper;

public int insertProjectBuildSupplier (ProjectBuildSupplier projectBuildSupplier)
{
	return projectBuildSupplierMapper.insert(projectBuildSupplier);
}

public int deleteProjectBuildSupplier(ProjectBuildSupplier projectBuildSupplier)
{
	return projectBuildSupplierMapper.delete(projectBuildSupplier);
}
public int updateProjectBuildSupplier(Example example,ProjectBuildSupplier projectBuildSupplier)
{
	return projectBuildSupplierMapper.updateByExampleSelective(projectBuildSupplier, example);
}

public ProjectBuildSupplier selectOneProjectBuildSupplier(ProjectBuildSupplier projectBuildSupplier)
{
	return projectBuildSupplierMapper.selectOne(projectBuildSupplier);
}
/**
 * 
 * @Title: getSupplierList   
 * @Description: TODO 获取供应商列表
 * @param: orgId
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getSupplierList(String orgId,String search)
{
	return projectBuildSupplierMapper.getSupplierList(orgId, search);
}

/**
 * 
 * @Title: getSupplierList   
 * @Description: TODO 获取供应商列表
 * @param: pageParam
 * @param: orgId
 * @param: search
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getSupplierList(PageParam pageParam)
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getSupplierList(pageParam.getOrgId(),"%"+pageParam.getSearch()+"%");
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getSelect2SupplierList   
 * @Description: TODO 获取Select2供应商列表
 * @param: orgId
 * @param: companyName
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getSelect2SupplierList(String orgId,String companyName)
{
	return projectBuildSupplierMapper.getSelect2SupplierList(orgId, "%"+companyName+"%");
}

}
