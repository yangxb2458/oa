/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  CrmContractInfoMapper.java   
 * @Package com.core136.mapper.crm   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月24日 下午3:27:07   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.crm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.crm.CrmContractInfo;
import org.core136.common.dbutils.MyMapper;

/**   
 * @ClassName:  CrmContractInfoMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月24日 下午3:27:07   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface CrmContractInfoMapper extends MyMapper<CrmContractInfo>{
/**
 * 
* @Title: getCrmContractInfoList 
* @Description: TODO 获取客户银行账户信息 
* @param @param orgId
* @param @param search
* @param @return 设定文件 
* @return List<Map<String,Object>> 返回类型 

 */
public List<Map<String,Object>> getCrmContractInfoList(@Param(value="orgId") String orgId,@Param(value="search") String search);

/**
 * 
* @Title: isExistChild 
* @Description: TODO 判断是否已经存在
* @param @param orgId
* @param @param customerId
* @param @return 设定文件 
* @return int 返回类型 

 */
public int isExistChild(@Param(value="orgId") String orgId,@Param(value="customerId") String customerId);

/**
 * 
* @Title: getContractInfoById 
* @Description: TODO 获取银行信息详情
* @param @param orgId
* @param @param contractInfoId
* @param @return 设定文件 
* @return Map<String,Object> 返回类型 

 */
public Map<String,Object> getContractInfoById(@Param(value="orgId") String orgId,@Param(value="contractInfoId") String contractInfoId);

}
