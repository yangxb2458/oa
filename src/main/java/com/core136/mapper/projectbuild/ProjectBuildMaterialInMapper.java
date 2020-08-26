package com.core136.mapper.projectbuild;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.projectbuild.ProjectBuildMaterialIn;
import org.core136.common.dbutils.MyMapper;
@Mapper
public interface ProjectBuildMaterialInMapper extends MyMapper<ProjectBuildMaterialIn>{

	/**
	 * 
	 * @Title: sumMaterialById   
	 * @Description: TODO 获取当前材料已入库数量
	 * @param: orgId
	 * @param: purchaseId
	 * @param: materialId
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	public int sumMaterialById(@Param(value="orgId") String orgId,@Param(value="purchaseId") String purchaseId,@Param(value="materialId") String materialId);

	/**
	 * 
	 * @Title: getMaterialByProjectId   
	 * @Description: TODO 获取项目中可领用的材料列表
	 * @param: orgId
	 * @param: projectId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getMaterialByProjectId(@Param(value="orgId") String orgId,@Param(value="projectId") String projectId,@Param(value="search") String search);
/**
 * 
 * @Title: getMaterialInList   
 * @Description: TODO 获取项目材料入库记录 
 * @param: orgId
 * @param: projectId
 * @param: materialId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
	public List<Map<String, String>> getMaterialInList(@Param(value="orgId") String orgId,@Param(value="projectId") String projectId,@Param(value="materialId") String materialId);
	
	/**
	 * 
	 * @Title: getQueryMaterialInList   
	 * @Description: TODO 查询入库记录
	 * @param: orgId
	 * @param: inUser
	 * @param: beginTime
	 * @param: endTime
	 * @param: materialName
	 * @param: purchaseTitle
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>> getQueryMaterialInList(@Param(value="orgId") String orgId,@Param(value="inUser") String inUser,@Param(value="beginTime") String beginTime,
			@Param(value="endTime") String endTime,@Param(value="materialName") String materialName,@Param(value="purchaseTitle") String purchaseTitle);

}
