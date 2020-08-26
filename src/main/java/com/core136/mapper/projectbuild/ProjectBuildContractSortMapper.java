package com.core136.mapper.projectbuild;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.projectbuild.ProjectBuildContractSort;
import org.core136.common.dbutils.MyMapper;

@Mapper
public interface ProjectBuildContractSortMapper extends MyMapper<ProjectBuildContractSort>{
	/**
	 * 
	 * @Title: getProjectBuildContractSortTree   
	 * @Description: TODO 工程合同树结构
	 * @param: orgId
	 * @param: sortLeave
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>>getProjectBuildContractSortTree(@Param(value="orgId") String orgId,@Param(value="sortLeave") String sortLeave);
	/**
	 * 
	 * @Title: isExistChild   
	 * @Description: TODO 判断是否还有子集
	 * @param: orgId
	 * @param: sortId
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	public int isExistChild(@Param(value="orgId") String orgId,@Param(value="sortId") String sortId);
}
