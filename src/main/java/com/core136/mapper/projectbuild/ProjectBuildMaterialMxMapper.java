package com.core136.mapper.projectbuild;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.projectbuild.ProjectBuildMaterialMx;
import org.core136.common.dbutils.MyMapper;

/**
 * 
 * @ClassName:  ProjectBuildMaterialMx   
 * @Description:TODO 采购明细
 * @author: 稠云技术 
 * @date:   2019年10月9日 下午8:11:33   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Mapper
public interface ProjectBuildMaterialMxMapper extends MyMapper<ProjectBuildMaterialMx>{

	/**
	 * 
	 * @Title: getPurchaseMaterialMxList   
	 * @Description: TODO 获取材料采购清单
	 * @param: orgId
	 * @param: purchaseId
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getPurchaseMaterialMxList(@Param(value="orgId") String orgId,@Param(value="purchaseId") String purchaseId,@Param(value="search") String search);
	
	
}
