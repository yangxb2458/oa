package com.core136.mapper.projectbuild;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.projectbuild.ProjectBuildMaterialOut;
import org.core136.common.dbutils.MyMapper;

@Mapper
public interface ProjectBuildMaterialOutMapper extends MyMapper<ProjectBuildMaterialOut>{

	/**
	 * 
	 * @Title: sumMaterialById   
	 * @Description: TODO 按项目统计出库的材料数量
	 * @param: orgId
	 * @param: projectId
	 * @param: materialId
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	public int sumMaterialById(@Param(value="orgId") String orgId,@Param(value="projectId") String projectId,@Param(value="materialId") String materialId);
/**
 * 
 * @Title: getMaterialOutList   
 * @Description: TODO 按项目与材料获取出库账台
 * @param: orgId
 * @param: projectId
 * @param: materialId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
	public List<Map<String, String>> getMaterialOutList(@Param(value="orgId") String orgId,@Param(value="projectId") String projectId,@Param(value="materialId") String materialId);

/**
 * 
 * @Title: getQueryMaterialOutList   
 * @Description: TODO 获取材料出库记录 
 * @param: orgId
 * @param: beginTime
 * @param: endTime
 * @param: materialName
 * @param: projectTitle
 * @param: outUser
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
	public List<Map<String, String>> getQueryMaterialOutList(@Param(value="orgId") String orgId,@Param(value="beginTime") String beginTime,@Param(value="endTime") String endTime,
			@Param(value="materialName") String materialName,@Param(value="projectTitle") String projectTitle,@Param(value="outUser") String outUser);
}
