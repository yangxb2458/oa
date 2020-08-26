package com.core136.mapper.projectbuild;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.projectbuild.ProjectBuildUnit;
import org.core136.common.dbutils.MyMapper;
@Mapper
public interface ProjectBuildUnitMapper extends MyMapper<ProjectBuildUnit>{

	/**
	 * 
	 * @Title: getAllUnit   
	 * @Description: TODO 获取所有的计量单位
	 * @param: orgId
	 * @param: @return      
	 * @return: List<ErpUnit>      

	 */
	public List<ProjectBuildUnit> getAllUnit(@Param (value="orgId") String orgId);
	
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
	public List<Map<String,String>> getProjectBuildUnitList(@Param (value="orgId") String orgId,@Param (value="type") String type,@Param (value="search") String search);
}
