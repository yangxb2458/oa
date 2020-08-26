/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  SuperversionScoreService.java   
 * @Package com.core136.service.superversion   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月18日 上午9:22:07   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.superversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.superversion.SuperversionScore;
import com.core136.mapper.superversion.SuperversionScoreMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class SuperversionScoreService {
@Autowired
private SuperversionScoreMapper superversionScoreMapper;
public int insertSuperversionScore(SuperversionScore superversionScore)
{
	return superversionScoreMapper.insert(superversionScore);
}

public int deleteSuperversionScore(SuperversionScore superversionScore)
{
	return superversionScoreMapper.delete(superversionScore);
}

public int updateSuperversionScore(Example example,SuperversionScore superversionScore)
{
	return superversionScoreMapper.updateByExampleSelective(superversionScore, example);
}

public SuperversionScore selectOneSuperversionScore(SuperversionScore superversionScore)
{
	return superversionScoreMapper.selectOne(superversionScore);
}

}
