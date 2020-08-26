/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  NoticeConfigMapper.java   
 * @Package com.core136.mapper.oa   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年7月8日 下午12:31:20   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.notice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.notice.NoticeConfig;
import org.core136.common.dbutils.MyMapper;

/**   
 * @ClassName:  NoticeConfigMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年7月8日 下午12:31:20   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface NoticeConfigMapper extends MyMapper<NoticeConfig>{
	/**
	 * 
	* @Title: getApproverUserList 
	* @Description: TODO 审批人员列表
	* @param @param orgId
	* @param @return 设定文件 
	* @return List<Map<String,String>> 返回类型
	 */
public List<Map<String,String>>getApproverUserList(@Param(value="orgId") String orgId);
/**
 *
* @Title: getNotApproverUserList 
* @Description: TODO 免审人员列表
* @param @param orgId
* @param @return 设定文件 
* @return List<Map<String,String>> 返回类型
 */
public List<Map<String,String>>getNotApproverUserList(@Param(value="orgId") String orgId);
}
