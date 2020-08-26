/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  CrmInquiryDetailMapper.java   
 * @Package com.core136.mapper.crm   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年5月5日 上午10:23:57   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.crm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.crm.CrmInquiryDetail;
import org.core136.common.dbutils.MyMapper;

/**   
 * @ClassName:  CrmInquiryDetailMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年5月5日 上午10:23:57   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface CrmInquiryDetailMapper extends MyMapper<CrmInquiryDetail>{

	/**
	 * 
	 * @Title: getCrmInquiryDetailList   
	 * @Description: TODO 询价单产品详情
	 * @param orgId
	 * @param inquiryId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getCrmInquiryDetailList(@Param(value="orgId")String orgId,@Param(value="inquiryId")String inquiryId );
	
}
