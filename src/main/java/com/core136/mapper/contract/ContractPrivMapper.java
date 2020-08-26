/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ContractPrivMapper.java   
 * @Package com.core136.mapper.contract   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月26日 上午9:13:04   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.contract;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.contract.ContractPriv;
import org.core136.common.dbutils.MyMapper;

/**   
 * @ClassName:  ContractPrivMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月26日 上午9:13:04   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface ContractPrivMapper extends MyMapper<ContractPriv>{

	/**
	 * 
	* @Title: isExistChild 
	* @Description: TODO 判断是否存在
	* @param @param orgId
	* @param @return 设定文件 
	* @return int 返回类型 

	 */
	public int isExistChild(@Param(value="orgId") String orgId);
}
