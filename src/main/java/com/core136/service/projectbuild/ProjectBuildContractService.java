package com.core136.service.projectbuild;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.projectbuild.ProjectBuildContract;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.projectbuild.ProjectBuildContractMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ProjectBuildContractService {
@Autowired
private ProjectBuildContractMapper projectBuildContractMapper;

public int insertProjectBuildContract(ProjectBuildContract projectBuildContract)
{
	return projectBuildContractMapper.insert(projectBuildContract);
}

public int deleteProjectBuildContract(ProjectBuildContract projectBuildContract)
{
	return projectBuildContractMapper.delete(projectBuildContract);
}

public int updateProjectBuildContract(Example example,ProjectBuildContract projectBuildContract)
{
	return projectBuildContractMapper.updateByExampleSelective(projectBuildContract, example);
}

public ProjectBuildContract selectOneProjectBuildContract(ProjectBuildContract projectBuildContract)
{
	return projectBuildContractMapper.selectOne(projectBuildContract);
}
/**
 * 
 * @Title: getcontractlist   
 * @Description: TODO 获取工程合同列表
 * @param: orgId
 * @param: sortId
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getcontractlist(String orgId,String sortId,String search){
	return projectBuildContractMapper.getcontractlist(orgId, sortId, "%"+search+"%");
}
public PageInfo<Map<String, String>> getcontractlist(PageParam pageParam,String sortId) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getcontractlist(pageParam.getOrgId(),sortId,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: querycontractlist   
 * @Description: TODO 合同查询 
 * @param: orgId
 * @param: beginTime
 * @param: endTime
 * @param: signUser
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>querycontractlist(String orgId,String beginTime,String endTime,String signUser,String type,String search)
{
	return projectBuildContractMapper.querycontractlist(orgId, beginTime, endTime, signUser,type, "%"+search+"%");
}

/**
 * 
 * @Title: querycontractlist   
 * @Description: TODO 合同查询 
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: signUser
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> querycontractlist(PageParam pageParam,String beginTime,String endTime,String signUser,String type) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= querycontractlist(pageParam.getOrgId(),beginTime,endTime,signUser,type,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
}
