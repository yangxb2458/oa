package com.core136.mapper.projectbuild;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.projectbuild.ProjectBuildMaterialStage;
import org.core136.common.dbutils.MyMapper;

@Mapper
public interface ProjectBuildMaterialStageMapper extends MyMapper<ProjectBuildMaterialStage>{
	/**
	 * 
	 * @Title: getMaterialStageList   
	 * @Description: TODO 工程节点具体材料警戒列表
	 * @param: orgId
	 * @param: stageId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
public List<Map<String,String>> getMaterialStageList(@Param(value="orgId") String orgId,@Param(value="stageId")String stageId,@Param(value="search") String search);

/**
 * 
 * @Title: isExistChild   
 * @Description: TODO 判断当前材料是否已存在
 * @param: orgId
 * @param: stageId
 * @param: marerialId
 * @param: @return      
 * @return: int      
 * @throws
 */
public int isExistChild(@Param(value="orgId") String orgId,@Param(value="stageId") String stageId,@Param(value="materialId") String materialId);

/**
 * 
 * @Title: getMaterialListInStage   
 * @Description: TODO 获取当前节点可选择的材料列表
 * @param: orgId
 * @param: stageId
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public  List<Map<String,String>>getMaterialListInStage(@Param(value="orgId")String orgId,@Param(value="stageId") String stageId,@Param(value="search") String search);
}
