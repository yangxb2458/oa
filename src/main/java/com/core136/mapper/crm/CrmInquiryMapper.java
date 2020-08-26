/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  CrmInquiryMapper.java   
 * @Package com.core136.mapper.crm   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年5月5日 上午10:22:45   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.crm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.crm.CrmInquiry;
import org.core136.common.dbutils.MyMapper;

/**   
 * @ClassName:  CrmInquiryMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年5月5日 上午10:22:45   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface CrmInquiryMapper extends MyMapper<CrmInquiry>{
/**
 * 
* @Title: getCrmInquiryList 
* @Description: TODO获限权限内的询价单列表
* @param @param orgId
* @param @param opFlag
* @param @param accountId
* @param @param beginTime
* @param @param endTime
* @param @param customerType
* @param @param search
* @param @return 设定文件 
* @return List<Map<String,String>> 返回类型
 */
public List<Map<String,String>> getCrmInquiryList(
		@Param(value="orgId") String orgId,
		@Param(value="opFlag") String opFlag,
		@Param(value="accountId") String accountId,
		@Param(value="beginTime") String beginTime,
		@Param(value="endTime") String endTime,
		@Param(value="customerType") String customerType,
		@Param(value="status")String status,
		@Param(value="search") String search);	
	
}
