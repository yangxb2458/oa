package com.core136.service.crm;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.crm.CrmIndustry;
import com.core136.mapper.crm.CrmIndustryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class CrmIndustryService{
	@Autowired
	private CrmIndustryMapper crmIndustryMapper;
	
	public List<CrmIndustry> selectCrmIndustry(Example example)
	{
		example.setOrderByClause("SORT_NO DESC");
		return crmIndustryMapper.selectByExample(example);
	}
	
	public int insertCrmIndustry(CrmIndustry crmIndustry)
	{
		return crmIndustryMapper.insert(crmIndustry);
	}
	
	public int updateCrmIndustry(CrmIndustry crmIndustry,Example example)
	{
		return crmIndustryMapper.updateByExampleSelective(crmIndustry, example);
	}
	
	public int deleteCrmIndustry(CrmIndustry crmIndustry)
	{
		return crmIndustryMapper.delete(crmIndustry);
	}
	
	public CrmIndustry selectOneCrmIndustry(CrmIndustry crmIndustry)
	{
		return crmIndustryMapper.selectOne(crmIndustry);
	}

	/* 
	 * 获取企业分类列表
	 */
	
	public List<Map<String, Object>> getAllIndustryList(String orgId, String search) {
		// TODO Auto-generated method stub
		return crmIndustryMapper.getAllIndustryList(orgId, search);
	}
	

public PageInfo<Map<String, Object>> getAllIndustryList(int pageNumber,int pageSize,String orderBy,String orgId, String search) {
	PageHelper.startPage(pageNumber, pageSize,orderBy);
	List<Map<String, Object>> datalist= this.getAllIndustryList(orgId, search);
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(datalist);
	return pageInfo;
}
	
}
