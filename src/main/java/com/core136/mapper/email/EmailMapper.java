/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  EmailMapper.java   
 * @Package com.core136.mapper.oa   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 刘绍全     
 * @date:   2019年1月14日 下午7:42:47   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.email;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.email.Email;
import org.core136.common.dbutils.MyMapper;

/**   
 * @ClassName:  EmailMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 刘绍全 
 * @date:   2019年1月14日 下午7:42:47   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云科信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface EmailMapper extends MyMapper<Email>{
/**
 * 
 * @Title: getEmailListForDesk   
 * @Description: TODO 获取桌面个人邮件列表
 * @param: orgId
 * @param: accountId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getEmailListForDesk(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId);
			
/**
 * 
 * @Title getMyEmailAll   
 * @Description TODO 获取个人邮件列表
 * @param orgId
 * @param accountId
 * @return      
 * List<Map<String,Object>>
 */
public List<Map<String,Object>> getMyEmailAll(@Param(value="orgId")String orgId,@Param(value="accountId") String accountId,@Param(value="boxId") String boxId ,@Param(value="search") String search);
/**
 * 
* @Title: getMyDelEmailAll 
* @Description: TODO 获取回收站邮件
* @param @param orgId
* @param @param accountId
* @param @param search
* @param @return 设定文件 
* @return List<Map<String,Object>> 返回类型 

 */
public List<Map<String,Object>> getMyDelEmailAll(@Param(value="orgId")String orgId,@Param(value="accountId") String accountId,@Param(value="search") String search);

/**
 * 
* @Title: getMyStarEmail 
* @Description: TODO 获取标星邮件
* @param @param orgId
* @param @param accountId
* @param @param search
* @param @return 设定文件 
* @return List<Map<String,Object>> 返回类型 

 */
public List<Map<String,Object>> getMyStarEmail(@Param(value="orgId")String orgId,@Param(value="accountId") String accountId,@Param(value="search") String search);
/**
 * 
 * @Title getEmailDetails   
 * @Description TODO 获取内部邮件详情
 * @param orgId
 * @param accountId
 * @param emailId
 * @return      
 * Map<String,Object>
 */
public Map<String,Object>getEmailDetails(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId,@Param(value="emailId") String emailId);

/**
 * 
 * @Title getEmailCount   
 * @Description TODO 获取各类型邮件总数
 * @param orgId
 * @param accountId
 * @return      
 * Map<String,Object>
 */
public Map<String,Object>getEmailCount(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId);
/**
 * 
 * @Title delMyEmail   
 * @Description TODO 删除个人的邮件(逻辑删除)
 * @param orgId
 * @param accountId
 * @param list
 * @return      
 * int
 */
public int delMyEmail(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId,@Param(value="list") List<String> list);

/**
 * 
* @Title: recoveryMyEmail 
* @Description: TODO 恢复回收站中的个人邮件
* @param @param orgId
* @param @param accountId
* @param @param list
* @param @return 设定文件 
* @return int 返回类型 

 */
public int recoveryMyEmail(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId,@Param(value="list") List<String> list);
/**
 * 
* @Title: delMyEmailPhysics 
* @Description: TODO 物事删除个人邮件
* @param @param orgId
* @param @param accountId
* @param @param list
* @param @return 设定文件 
* @return int 返回类型 

 */
public int delMyEmailPhysics(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId,@Param(value="list") List<String> list);
/**
 * 
* @Title: updateSetStars 
* @Description: TODO 批量取消星标记
* @param @param orgId
* @param @param accountId
* @param @param list
* @param @return 设定文件 
* @return int 返回类型 

 */
public int updateSetStars(@Param(value="orgId") String orgId,@Param(value="accountId") String accountId,@Param(value="list") List<String> list);

/**
 * 
* @Title: setMyEmailBox 
* @Description: TODO 设置个人邮件文件夹
* @param @param boxId
* @param @param orgId
* @param @param accountId
* @param @param list
* @param @return 设定文件 
* @return int 返回类型 

 */
public int setMyEmailBox(@Param(value="boxId") String boxId,@Param(value="orgId") String orgId,@Param(value="accountId") String accountId,@Param(value="list") List<String> list);
/**
 * 
 * @Title: getMyEmailAllForMobile   
 * @Description: TODO 移动端内部邮件
 * @param orgId
 * @param accountId
 * @param page
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyEmailAllForMobile(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,@Param(value="page")Integer page);
}