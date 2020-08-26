/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  EmailBoxMapper.java   
 * @Package com.core136.mapper.oa   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 刘绍全     
 * @date:   2019年1月14日 下午7:43:43   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.email;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.email.EmailBox;
import org.core136.common.dbutils.MyMapper;

/**   
 * @ClassName:  EmailBoxMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 刘绍全 
 * @date:   2019年1月14日 下午7:43:43   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云科信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface EmailBoxMapper extends MyMapper<EmailBox>{

	/**
	 * 
	 * @Title getEmailBoxList   
	 * @Description TODO 获取内部邮件自定义文件夹
	 * @param orgId
	 * @param accountId
	 * @return      
	 * List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getEmailBoxList(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId);
	/**
	 * 
	* @Title: getEmailBoxManageList 
	* @Description: TODO 个人邮件文件夹管理列表
	* @param @param orgId
	* @param @param accountId
	* @param @param search
	* @param @return 设定文件 
	* @return List<Map<String,Object>> 返回类型 

	 */
	public List<Map<String,Object>> getEmailBoxManageList(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId,@Param(value="search") String search);
}
