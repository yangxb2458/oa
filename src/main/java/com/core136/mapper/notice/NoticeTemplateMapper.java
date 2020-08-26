/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  NoticeTemplateMapper.java   
 * @Package com.core136.mapper.oa   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年7月12日 上午11:31:03   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.notice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.notice.NoticeTemplate;
import org.core136.common.dbutils.MyMapper;

/**   
 * @ClassName:  NoticeTemplateMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年7月12日 上午11:31:03   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface NoticeTemplateMapper extends MyMapper<NoticeTemplate>{

	/**
	 * 
	* @Title: getNoticeTemplateList 
	* @Description: TODO 获取模版列表
	* @param @param orgId
	* @param @return 设定文件 
	* @return List<Map<String,String>> 返回类型
	 */
	public List<Map<String,String>> getNoticeTemplateList(@Param(value="orgId") String orgId,@Param(value="search") String search);
	
	/**
	 * 
	* @Title: getRedHeadListByType 
	* @Description: TODO 按分类获取红头列表
	* @param @param orgId
	* @param @param noticeType
	* @param @return 设定文件 
	* @return List<Map<String,String>> 返回类型
	 */
	public List<Map<String,String>> getRedHeadListByType(@Param(value="orgId") String orgId,@Param(value="noticeType") String noticeType);
}
