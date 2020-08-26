package com.core136.mapper.projectbuild;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.projectbuild.ProjectBuildSupplier;
import org.core136.common.dbutils.MyMapper;
/**
 * 
 * @ClassName:  ProjectBuildSupplierMapper   
 * @Description:TODO 供应商台账
 * @author: 稠云技术 
 * @date:   2019年10月5日 下午3:48:55   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Mapper
public interface ProjectBuildSupplierMapper extends MyMapper<ProjectBuildSupplier>{

	/**
	 * 
	 * @Title: getSupplierList   
	 * @Description: TODO 获取供应商列表 
	 * @param: orgId
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>>getSupplierList(@Param(value="orgId") String orgId,@Param(value="search") String search);
	/**
	 * 
	 * @Title: getSelect2SupplierList   
	 * @Description: TODO 获取Select2供应商列表
	 * @param: orgId
	 * @param: companyName
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>>getSelect2SupplierList(@Param(value="orgId") String orgId,@Param(value="companyName") String companyName);
}
