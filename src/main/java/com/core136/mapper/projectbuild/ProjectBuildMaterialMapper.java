package com.core136.mapper.projectbuild;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.projectbuild.ProjectBuildMaterial;
import org.core136.common.dbutils.MyMapper;
@Mapper
public interface ProjectBuildMaterialMapper extends MyMapper<ProjectBuildMaterial>{

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
	public List<Map<String,String>> getmateriallist(@Param(value="orgId") String orgId,@Param(value="sortId") String sortId,@Param(value="search") String search);


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
	public List<Map<String,String>> getMaterialListForSelet2(@Param(value="orgId") String orgId,@Param(value="search") String search);
}
