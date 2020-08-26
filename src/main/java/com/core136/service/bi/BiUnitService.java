/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  BiUnitService.java   
 * @Package com.core136.service.bi   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年5月8日 下午12:44:59   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.bi;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.mapper.bi.BiUnitMapper;

/**   
 * @ClassName:  BiUnitService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年5月8日 下午12:44:59   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service 
public class BiUnitService{
@Autowired
private BiUnitMapper biUnitMapper;
	/** 
	* @Title: getPieSeriesData 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param fields
	* @param @param tableName
	* @param @param condition
	* @param @return 设定文件 
	*/ 
	
	public List<Map<String, Object>> getPieSeriesData(String fields, String tableName, String condition) {
		return biUnitMapper.getPieSeriesData(fields, tableName, condition);
	}

}
