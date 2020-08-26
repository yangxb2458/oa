/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  ContractMapper.java   
 * @Package com.core136.mapper.contract   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月23日 上午11:17:31   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.contract;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.contract.Contract;
import org.core136.common.dbutils.MyMapper;

/**   
 * @ClassName:  ContractMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月23日 上午11:17:31   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface ContractMapper extends MyMapper<Contract>{
/**
 * 
* @Title: getContractCount 
* @Description: TODO 获取年分的合同总数
* @param @param orgId
* @param @return 设定文件 
* @return int 返回类型 

 */
public int getContractCount(@Param(value="orgId") String orgId);

/**
 * 
 * @Title: queryContract   
 * @Description: TODO 合同查询
 * @param: orgId
 * @param: beginTime
 * @param: endTime
 * @param: contractType
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>>queryContract(
		@Param(value="orgId") String orgId,
		@Param(value="beginTime") String beginTime,
		@Param(value="endTime") String endTime,
		@Param(value="contractType") String contractType,
		@Param(value="mySignUser") String mySignUser,
		@Param(value="search") String search
		);
/**
 * 
 * @Title: getContractManageList   
 * @Description: TODO 获取合同管理列表
 * @param: orgId
 * @param: opFlag
 * @param: accountId
 * @param: beginTime
 * @param: endTime
 * @param: contractType
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>>getContractManageList(
		@Param(value="orgId") String orgId,
		@Param(value="opFlag")String opFlag,
		@Param(value="accountId")String accountId,
		@Param(value="beginTime") String beginTime,
		@Param(value="endTime") String endTime,
		@Param(value="contractType") String contractType,
		@Param(value="search") String search
		);

/**
 * 
 * @Title: getSelect2ContractList   
 * @Description: TODO SELECT2的列表
 * @param: orgId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getSelect2ContractList(@Param(value="orgId") String orgId,@Param(value="search") String search);

/**
 * 
 * @Title: getContractTop   
 * @Description: TODO 获取近期的合同列表
 * @param orgId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getContractTop(@Param(value="orgId")String orgId);

}
