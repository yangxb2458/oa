package com.core136.mapper.projectbuild;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.core136.bean.projectbuild.ProjectBuildMaterialPurchase;
import org.core136.common.dbutils.MyMapper;
/**
 * 
 * @ClassName:  ProjectBuildMaterialPurchaseMapper   
 * @Description:TODO 工程材料采购申请 
 * @author: 稠云技术 
 * @date:   2019年10月9日 下午8:10:29   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Mapper
public interface ProjectBuildMaterialPurchaseMapper extends MyMapper<ProjectBuildMaterialPurchase>{
	/**
	 * 
	 * @Title: getMaterialPurchaseList   
	 * @Description: TODO 获取材料审批的列表
	 * @param: orgId
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getMaterialPurchaseList(@Param(value = "orgId") String orgId,@Param(value="search") String search);
	/**
	 * 
	 * @Title: getQueryPurchaseList   
	 * @Description: TODO 获取查询采购单表列
	 * @param: orgId
	 * @param: createUser
	 * @param: projectTitle
	 * @param: beginTime
	 * @param: endTime
	 * @param: companyName
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getQueryPurchaseList(@Param(value="orgId") String orgId,@Param(value="createUser") String createUser,@Param(value="projectTitle") String projectTitle,
			@Param(value="beginTime") String beginTime,@Param(value="endTime") String endTime,@Param(value="companyName") String companyName);
}
