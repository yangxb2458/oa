package com.core136.service.bi;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.bi.BiSort;
import com.core136.mapper.bi.BiSortMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @ClassName:  BiSortService   
 * @Description:TODO BI分类操作类
 * @author: 稠云信息
 * @date:   2019年1月25日 下午3:45:28   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class BiSortService{
@Autowired
private BiSortMapper biSortMapper;

	public int insertBiSort(BiSort biSort)
	{
		return biSortMapper.insert(biSort);
	}
	
	public int updateBiSort(BiSort biSort,Example example)
	{
		return biSortMapper.updateByExampleSelective(biSort, example);
	}
	
	public int deleteBiSort(BiSort biSort)
	{
		return biSortMapper.delete(biSort);
	}
	
	public BiSort selectOne(BiSort biSort)
	{
		return biSortMapper.selectOne(biSort);
	}

	/**
	 * 获取BI分类树结构
	 */
	
	public List<Map<String, Object>> getBiSortTree(String levelId, String orgId) {
		// TODO Auto-generated method stub
		return biSortMapper.getBiSortTree(levelId, orgId);
	}
}
