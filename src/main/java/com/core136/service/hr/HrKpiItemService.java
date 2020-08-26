package com.core136.service.hr;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.hr.HrKpiItem;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrKpiItemMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrKpiItemService {

	@Autowired
	private HrKpiItemMapper hrKpiItemMapper;
	
	
	public int insertHrKpiItem(HrKpiItem hrKpiItem)
	{
		return hrKpiItemMapper.insert(hrKpiItem);
	}
	
	public int deleteHrKpiItem(HrKpiItem hrKpiItem)
	{
		return hrKpiItemMapper.delete(hrKpiItem);
	}
	
	public int updateHrKpiItem(Example example,HrKpiItem hrKpiItem)
	{
		return hrKpiItemMapper.updateByExampleSelective(hrKpiItem, example);
	}
	
	public HrKpiItem selectOneHrKpiItem(HrKpiItem hrKpiItem)
	{
		return hrKpiItemMapper.selectOne(hrKpiItem);
	}
	/**
	 * 
	 * @Title: getHrKpiItemList   
	 * @Description: TODO 获取考核指标列表
	 * @param orgId
	 * @param kpiType
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getHrKpiItemList(String orgId,String createUser,String kpiType,String search)
	{
		return hrKpiItemMapper.getHrKpiItemList(orgId,createUser, kpiType, "%"+search+"%");
	}
	/**
	 * 
	 * @Title: getHrKpiItemList   
	 * @Description: TODO 获取考核指标列表
	 * @param pageParam
	 * @param kpiType
	 * @return
	 * PageInfo<Map<String,String>>    
	 * @throws
	 */
	public PageInfo<Map<String, String>> getHrKpiItemList(PageParam pageParam,String createUser,String kpiType) 
	{
		PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= getHrKpiItemList(pageParam.getOrgId(),createUser,kpiType,pageParam.getSearch());
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
	}
}
