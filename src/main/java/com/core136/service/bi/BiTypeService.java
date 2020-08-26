/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  BiTypeService.java   
 * @Package com.core136.service.bi   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月22日 上午10:12:10   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.bi;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.bi.BiType;
import com.core136.mapper.bi.BiTypeMapper;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  BiTypeService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月22日 上午10:12:10   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class BiTypeService{
	@Autowired
	private BiTypeMapper biTypeMapper;
	
	public int insertBiType(BiType biType)
	{
		return biTypeMapper.insert(biType);
	}
	
	public BiType selectOneBiType(BiType biType)
	{
		return biTypeMapper.selectOne(biType);
	}
	
	public int deleteBiType(BiType biType)
	{
		return biTypeMapper.delete(biType);
	}
	
	public int updateBiType(BiType biType,Example example)
	{
		return biTypeMapper.updateByExampleSelective(biType, example);
	}

	/**
	 * 获取所有BI报表分类
	 */
	
	public List<Map<String, Object>> getAllBiType(String orgId) {
		return biTypeMapper.getAllBiType(orgId);
	}
	
}
