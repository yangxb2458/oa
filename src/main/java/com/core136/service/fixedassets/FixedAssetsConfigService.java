/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  FixedAssetsConfigService.java   
 * @Package com.core136.service.fixedassets   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年12月10日 上午9:02:22   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.fixedassets;

import org.springframework.beans.factory.annotation.Autowired;

import com.core136.bean.fixedassets.FixedAssetsConfig;
import com.core136.mapper.fixedassets.FixedAssetsConfigMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
public class FixedAssetsConfigService {
@Autowired
private FixedAssetsConfigMapper fixedAssetsConfigMapper;

public int insertFixedAssetsConfig(FixedAssetsConfig fixedAssetsConfig)
{
	return fixedAssetsConfigMapper.insert(fixedAssetsConfig);
}

public int deleteFixedAssetsConfig(FixedAssetsConfig fixedAssetsConfig)
{
	return fixedAssetsConfigMapper.delete(fixedAssetsConfig);
}

public int updateFixedAssetsConfig(Example example,FixedAssetsConfig fixedAssetsConfig)
{
	return fixedAssetsConfigMapper.updateByExampleSelective(fixedAssetsConfig, example);
}

public FixedAssetsConfig selectOneFixedAssetsConfig(FixedAssetsConfig fixedAssetsConfig)
{
	return fixedAssetsConfigMapper.selectOne(fixedAssetsConfig);
}

}
