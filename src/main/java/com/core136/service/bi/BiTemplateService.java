package com.core136.service.bi;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.bi.BiTemplate;
import com.core136.mapper.bi.BiTemplateMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class BiTemplateService{
@Autowired
private BiTemplateMapper biTemplateMapper;

public int insertBiTemplate(BiTemplate biTemplate)
{
	return biTemplateMapper.insert(biTemplate);
}

public int deleteBiTemplate(BiTemplate biRuleConfig)
{
	return biTemplateMapper.delete(biRuleConfig);
}

public BiTemplate selectOne(BiTemplate biTemplate)
{
	return biTemplateMapper.selectOne(biTemplate);
}

public int updateBiTemplate(BiTemplate biTemplate,Example example)
{
	return biTemplateMapper.updateByExampleSelective(biTemplate, example);
}

/**
 * 按分类获取模版列表
 */

public List<Map<String, Object>> getBiTemplateList(String orgId, String leaveId,String search) {
	return biTemplateMapper.getBiTemplateList(orgId, leaveId,search);
}

public PageInfo<Map<String, Object>> getBiTemplateList(int pageNumber,int pageSize,String orderBy,String levelId, String orgId , String search) {
	PageHelper.startPage(pageNumber, pageSize,orderBy);
	List<Map<String,Object>> datalist=this.getBiTemplateList(orgId, levelId,search);
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}

}
