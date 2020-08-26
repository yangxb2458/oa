/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  CrmTagsMapper.java   
 * @Package com.core136.mapper.crm   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月8日 上午9:41:11   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.crm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.crm.CrmTags;
import org.core136.common.dbutils.MyMapper;

/**   
 * @ClassName:  CrmTagsMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月8日 上午9:41:11   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface CrmTagsMapper extends MyMapper<CrmTags>{

	/**
	 * 
	* @Title: getAllTagsList 
	* @Description: TODO 获取企业标签列表
	* @param @param orgId
	* @param @param search
	* @param @return 设定文件 
	* @return List<Map<String,Object>> 返回类型 

	 */
	public List<Map<String,Object>> getAllTagsList(@Param(value="orgId") String orgId,@Param(value="search") String search);
	
}
