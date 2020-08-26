package com.core136.mapper.projectbuild;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.projectbuild.ProjectBuildMaterialSort;
import org.core136.common.dbutils.MyMapper;

@Mapper
public interface ProjectBuildMaterialSortMapper extends MyMapper<ProjectBuildMaterialSort>{
	/**
	 * 
	* @Title: getContractSortTree 
	* @Description: TODO 获取材料的分类树结构
	* @param @param orgId
	* @param @param sortLeave
	* @param @return 设定文件 
	* @return List<Map<String,String>> 返回类型 

	 */
public List<Map<String,String>>getMaterialSortTree(@Param(value="orgId") String orgId,@Param(value="sortLeave") String sortLeave);
/**
 * 
* @Title: isExistChild 
* @Description: TODO 判断是否还有子集
* @param @param orgId
* @param @param sortId
* @param @return 设定文件 
* @return int 返回类型 

 */
public int isExistChild(@Param(value="orgId") String orgId,@Param(value="sortId") String sortId);
}
