package com.core136.mapper.projectbuild;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.projectbuild.ProjectBuildContract;
import org.core136.common.dbutils.MyMapper;

/**
 * 
 * @ClassName:  ProjectBuildContractMapper   
 * @Description:TODO 工程项目合同管理   
 * @author: 稠云技术 
 * @date:   2019年10月13日 上午9:42:26   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Mapper
public interface ProjectBuildContractMapper extends MyMapper<ProjectBuildContract>{

	/**
	 * 
	 * @Title: getcontractlist   
	 * @Description: TODO 获取合同分类  
	 * @param: orgId
	 * @param: sortId
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getcontractlist(@Param(value="orgId") String orgId,@Param(value="sortId") String sortId,@Param(value="search") String search);

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
	public List<Map<String,String>> querycontractlist(@Param(value="orgId") String orgId,@Param(value="beginTime") String beginTime,@Param(value="endTime") String endTime,
			@Param(value="signUser") String signUser,@Param(value="type") String type,@Param(value="search") String search);
}
