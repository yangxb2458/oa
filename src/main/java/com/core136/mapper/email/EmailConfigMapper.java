/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  EmailConfigMapper.java   
 * @Package com.core136.mapper.oa   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 刘绍全     
 * @date:   2019年1月14日 下午7:46:07   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.email;

import org.apache.ibatis.annotations.Mapper;

import com.core136.bean.email.EmailConfig;
import org.core136.common.dbutils.MyMapper;

/**   
 * @ClassName:  EmailConfigMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 刘绍全 
 * @date:   2019年1月14日 下午7:46:07   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云科信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface EmailConfigMapper extends MyMapper<EmailConfig>{

}
