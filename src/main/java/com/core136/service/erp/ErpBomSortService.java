package com.core136.service.erp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.erp.ErpBomSort;
import com.core136.mapper.erp.ErpBomSortMapper;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  ErpBomSortService   
 * @Description:TODO BOM分类操作服务类
 * @author: 稠云信息
 * @date:   2018年12月14日 上午11:05:41   
 *     
 * @Copyright: 2018 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class ErpBomSortService{
@Autowired
private ErpBomSortMapper erpBomSortMapper;

/**
 * 获取BOM的父级分类
 */

public List<Map<String, Object>> getErpBomSortTree(String sortLeave, String orgId) {
	// TODO Auto-generated method stub
	return erpBomSortMapper.getErpBomSortTree(sortLeave, orgId);
}

/**
 * 判断分类下是否还有子分类
 */

public int isExistChild(String sortId, String orgId) {
	// TODO Auto-generated method stub
	return erpBomSortMapper.isExistChild(sortId, orgId);
}
/**
 * 
 * @Title selectOne   
 * @Description TODO 按条Id获取Bom分类
 * @param erpBomSort
 * @return      
 * ErpBomSort
 */
public ErpBomSort selectOne(ErpBomSort erpBomSort)
{
	return erpBomSortMapper.selectOne(erpBomSort);
}

/**
 * 
 * @Title insertErpBomSort   
 * @Description TODO 创建新的BOM分类
 * @param erpBomSort
 * @return      
 * int
 */
public int insertErpBomSort(ErpBomSort erpBomSort)
{
	return erpBomSortMapper.insert(erpBomSort);
}
/**
 * 
 * @Title delErpBomSort   
 * @Description TODO 删除BOM分类
 * @param erpBomSort
 * @return      
 * int
 */
public int delErpBomSort(ErpBomSort erpBomSort)
{
	return erpBomSortMapper.delete(erpBomSort);
}
/**
 * 
 * @Title updateErpBomSort   
 * @Description TODO 按条件更新BOM分类
 * @param erpBomSort
 * @param example
 * @return      
 * int
 */
public int updateErpBomSort(ErpBomSort erpBomSort,Example example)
{
	return erpBomSortMapper.updateByExample(erpBomSort, example);
}
}
