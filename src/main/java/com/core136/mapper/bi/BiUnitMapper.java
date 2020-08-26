/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  BiUnitMapper.java   
 * @Package com.core136.mapper.bi   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年5月8日 下午12:08:18   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.mapper.bi;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**   
 * @ClassName:  BiUnitMapper   
 * @Description:TODO BI 通用MAPPER
 * @author: 稠云信息 
 * @date:   2019年5月8日 下午12:08:18   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Mapper
public interface BiUnitMapper {
	/**
	 * 
	* @Title: getPieSeriesData 
	* @Description: TODO 获取饼图数据
	* @param @param fields
	* @param @param tableName
	* @param @param condition
	* @param @return 设定文件 
	* @return List<Map<String,Object>> 返回类型
	 */
	public List<Map<String,Object>> getPieSeriesData(@Param(value="fields") String fields,@Param(value="tableName") String tableName,@Param(value="condition") String condition);
}
