/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ContractDetalisMapper.java   
 * @Package com.core136.mapper.contract   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月24日 上午9:27:15   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.contract;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.contract.ContractDetails;
import org.core136.common.dbutils.MyMapper;

/**   
 * @ClassName:  ContractDetalisMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月24日 上午9:27:15   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface ContractDetailsMapper extends MyMapper<ContractDetails>{
	/**
	 * 
	* @Title: getContractDetailsList 
	* @Description: TODO 获取指定合同的产品明细
	* @param @param orgId
	* @param @param contractId
	* @param @return 设定文件 
	* @return List<Map<String,Object>> 返回类型 

	 */
	public List<Map<String,Object>> getContractDetailsList(@Param(value="orgId") String orgId,@Param(value="contractId") String contractId,@Param(value="search") String search);

}
