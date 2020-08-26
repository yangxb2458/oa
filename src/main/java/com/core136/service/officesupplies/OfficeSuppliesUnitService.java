/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  OfficeSuppliesUnitService.java   
 * @Package com.core136.service.officesupplies   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月18日 下午12:27:16   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.officesupplies;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.officesupplies.OfficeSuppliesUnit;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.officesupplies.OfficeSuppliesUnitMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class OfficeSuppliesUnitService {
@Autowired
private OfficeSuppliesUnitMapper officeSuppliesUnitMapper;

public int insertOfficeSuppliesUnit(OfficeSuppliesUnit officeSuppliesUnit)
{
	return officeSuppliesUnitMapper.insert(officeSuppliesUnit);
}

public int deleteOfficeSuppliesUnit(OfficeSuppliesUnit officeSuppliesUnit)
{
	return officeSuppliesUnitMapper.delete(officeSuppliesUnit);
}

public int updateOfficeSuppliesUnit(Example example,OfficeSuppliesUnit officeSuppliesUnit)
{
	return officeSuppliesUnitMapper.updateByExampleSelective(officeSuppliesUnit, example);
}

public OfficeSuppliesUnit selectOneOfficeSuppliesUnit(OfficeSuppliesUnit officeSuppliesUnit)
{
	return officeSuppliesUnitMapper.selectOne(officeSuppliesUnit);
}
/**
 * 
 * @Title: getAllUnit   
 * @Description: TODO 获取办公用品单位列表 
 * @param: orgId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>> getAllUnit(String orgId)
{
	return officeSuppliesUnitMapper.getAllUnit(orgId);
}
/**
 * 
 * @Title: getOfficeSuppliesUnitList   
 * @Description: TODO 获取办公用品单位列表
 * @param: orgId
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>> getOfficeSuppliesUnitList(String orgId, String search)
{
	return officeSuppliesUnitMapper.getOfficeSuppliesUnitList(orgId, "%"+search+"%");
}
/**
 * 
 * @Title: getOfficeSuppliesUnitList   
 * @Description: TODO 获取办公用品单位列表
 * @param: orgId
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getOfficeSuppliesUnitList(PageParam pageParam) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getOfficeSuppliesUnitList(pageParam.getOrgId(),pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}


}
