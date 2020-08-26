package com.core136.mapper.erp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.erp.ErpProductSort;
import org.core136.common.dbutils.MyMapper;

/**
 * 
 * @ClassName:  ErpProductSortMapper   
 * @Description:TODO 产品分类Mapper 
 * @author: 稠云信息
 * @date:   2018年12月10日 上午10:39:53   
 *     
 * @Copyright: 2018 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Mapper
public interface ErpProductSortMapper extends MyMapper<ErpProductSort>{
	/**
	 * 
	 * @Title: getErpProductSortTree   
	 * @Description: TODO 获取产品分类树   
	 * @param: sortLeave
	 * @param: orgId
	 * @param: @return      
	 * @return: List<Map<String,Object>>      

	 */
	public List<Map<String,Object>> getErpProductSortTree(@Param(value="sortLeave") String sortLeave,@Param(value="orgId") String orgId);
	
	/**
	 * 
	 * @Title: isExistChild   
	 * @Description: 判断当前分类下面是否还有子分类  
	 * @param: sortId
	 * @param: orgId
	 * @param: @return      
	 * @return: int      

	 */
	public int isExistChild(@Param(value="sortId") String sortId,@Param(value="orgId") String orgId);

}
