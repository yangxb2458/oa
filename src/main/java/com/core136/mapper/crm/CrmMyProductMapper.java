/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  CrmMyProductMapper.java   
 * @Package com.core136.mapper.crm   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月8日 上午10:51:45   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.crm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.crm.CrmMyProduct;
import org.core136.common.dbutils.MyMapper;

/**   
 * @ClassName:  CrmMyProductMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月8日 上午10:51:45   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface CrmMyProductMapper extends MyMapper<CrmMyProduct>{

	/**
	 * 
	* @Title: getMyProductNameStr 
	* @Description: TODO 按productId获取参应的产品名称
	* @param @param orgId
	* @param @param list
	* @param @return 设定文件 
	* @return List<Map<String,Object>> 返回类型 

	 */
	public List<Map<String,Object>> getMyProductNameStr(@Param(value="orgId") String orgId,@Param(value="list") List<String> list);
	
	/**
	 * 
	* @Title: getAllProductList 
	* @Description: TODO 获取对应的产品列表
	* @param @param orgId
	* @param @param search
	* @param @return 设定文件 
	* @return List<Map<String,Object>> 返回类型 

	 */
	public List<Map<String,Object>> getAllProductList(@Param(value="orgId") String orgId,@Param(value="search") String search);
	
}
