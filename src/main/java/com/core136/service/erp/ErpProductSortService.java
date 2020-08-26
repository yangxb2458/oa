package com.core136.service.erp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.erp.ErpProductSort;
import com.core136.mapper.erp.ErpProductSortMapper;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  ErpProductSortService   
 * @Description:TODO EerProductSort 数据操作服务类 
 * @author: 稠云信息
 * @date:   2018年12月10日 上午10:47:41   
 *     
 * @Copyright: 2018 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class ErpProductSortService{
@Autowired
private ErpProductSortMapper erpProductSortMapper;
/**
 * 
 * @Title: getErpProductSortTree   
 * @Description: TODO 获取产品分类树   
 * @param: sortLeave
 * @param: orgId
 * @param: @return      
 * @return: List<Map<String,Object>>      

 */
	
	public List<Map<String, Object>> getErpProductSortTree(String sortLeave, String orgId) {
		// TODO Auto-generated method stub
		return erpProductSortMapper.getErpProductSortTree(sortLeave, orgId);
	}
	/**
	 * 
	 * @Title: isExistChild   
	 * @Description: 判断当前分类下面是否还有子分类  
	 * @param: sortId
	 * @param: orgId
	 * @param: @return      
	 * @return: int      

	 */
	
	public int isExistChild(String sortId, String orgId) {
		// TODO Auto-generated method stub
		return erpProductSortMapper.isExistChild(sortId, orgId);
	}

	/**
	 * 
	 * @Title: insert   
	 * @Description: TODO 新建产品分类
	 * @param: erpProductSort
	 * @param: @return      
	 * @return: int      

	 */
	public int insert(ErpProductSort erpProductSort)
	{
		return erpProductSortMapper.insert(erpProductSort);
	}
	/**
	 * 
	 * @Title: selectOne   
	 * @Description: TODO 按条件查询一条产品分类
	 * @param: erpProductSort
	 * @param: @return      
	 * @return: ErpProductSort      

	 */
	public ErpProductSort selectOne(ErpProductSort erpProductSort)
	{
		return erpProductSortMapper.selectOne(erpProductSort);
	}
	
	/**
	 * 
	 * @Title: delErpProductSort   
	 * @Description: TODO 删除产品分类
	 * @param: erpProductSort
	 * @param: @return      
	 * @return: int      

	 */
	public int delErpProductSort(ErpProductSort erpProductSort)
	{
		return erpProductSortMapper.delete(erpProductSort);
	}
	
	/**
	 * 
	 * @Title updateErpProductSort   
	 * @Description TODO 更新产品分类 
	 * @param erpProductSort
	 * @param example
	 * @return      
	 * int
	 */
	public int updateErpProductSort(ErpProductSort erpProductSort,Example example)
	{
		return erpProductSortMapper.updateByExampleSelective(erpProductSort, example);
	}
}
