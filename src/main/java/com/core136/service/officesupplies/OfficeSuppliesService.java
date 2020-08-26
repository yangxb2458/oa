/**  
 * All rights Reserved, Designed By www.tydic.com
 * @Title:  OfficeSuppliesService.java   
 * @Package com.core136.service.officesupplies   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年10月18日 上午10:51:16   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.officesupplies;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.officesupplies.OfficeSupplies;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.officesupplies.OfficeSuppliesMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class OfficeSuppliesService {
@Autowired
private OfficeSuppliesMapper officeSuppliesMapper;

public int insertOfficeSupplies(OfficeSupplies officeSupplies)
{
	return officeSuppliesMapper.insert(officeSupplies);
}

public int deleteOfficeSupplies(OfficeSupplies officeSupplies)
{
	return officeSuppliesMapper.delete(officeSupplies);
}

public int updateOfficeSupplies(Example example,OfficeSupplies officeSupplies)
{
	return officeSuppliesMapper.updateByExampleSelective(officeSupplies, example);
}

public OfficeSupplies selectOneOfficeSupplies(OfficeSupplies officeSupplies)
{
	return officeSuppliesMapper.selectOne(officeSupplies);
}

/**
 * 
 * @Title: getOfficeSupplieslistBySortId   
 * @Description: TODO 获取办公用品列表
 * @param: orgId
 * @param: sortId
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>> getOfficeSupplieslistBySortId(String orgId,String sortId,String search)
{
	return officeSuppliesMapper.getOfficeSupplieslistBySortId(orgId,sortId,"%"+search+"%");
}

public PageInfo<Map<String, String>> getOfficeSupplieslistBySortId(PageParam pageParam,String sortId) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getOfficeSupplieslistBySortId(pageParam.getOrgId(),sortId,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getApplyOfficeSupplieslist   
 * @Description: TODO 获取可以领用的办公用品列表     
 * @param: orgId
 * @param: sortId
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>> getApplyOfficeSupplieslist(String orgId,String sortId,String deptId,String search)
{
	return officeSuppliesMapper.getApplyOfficeSupplieslist(orgId,sortId,deptId,"%"+search+"%");
}

public PageInfo<Map<String, String>> getApplyOfficeSupplieslist(PageParam pageParam,String sortId,String deptId) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getApplyOfficeSupplieslist(pageParam.getOrgId(),sortId,deptId,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
