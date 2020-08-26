package com.core136.service.erp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.erp.ErpOrderDetail;
import com.core136.bean.erp.ErpProduct;
import com.core136.mapper.erp.ErpProductMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  ErpProductService   
 * @Description:TODO 产品数据操作服务类   
 * @author: 稠云信息
 * @date:   2018年12月19日 上午10:15:13   
 *     
 * @Copyright: 2018 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class ErpProductService{
	@Autowired
	private ErpProductMapper erpProductMapper;
	
	/**
	 * 
	 * @Title getErpProductBySort   
	 * @Description TODO 按产品分类获取分类下的列表  
	 * @param example
	 * @param pageNumber
	 * @param pageSize
	 * @return      
	 * PageInfo<ErpProduct>
	 */
	public PageInfo<ErpProduct> getErpProductBySort(Example example,int pageNumber,int pageSize)
	{
		PageHelper.startPage(pageNumber, pageSize);
		List<ErpProduct> datalist=erpProductMapper.selectByExample(example);
		PageInfo<ErpProduct> pageInfo = new PageInfo<ErpProduct>(datalist);
		return pageInfo;
	}
	
	/**
	 * 
	 * @Title insertErpProduct   
	 * @Description TODO 添加新产品 
	 * @param erpProduct
	 * @return      
	 * int
	 */
	public int insertErpProduct(ErpProduct erpProduct)
	{
		return erpProductMapper.insert(erpProduct);
	}
	
	/**
	 * 
	 * @Title delErpProduct   
	 * @Description TODO 删除产品
	 * @param erpProduct
	 * @return      
	 * int
	 */
	public int delErpProduct(ErpProduct erpProduct)
	{
		return erpProductMapper.delete(erpProduct);
	}
	
	/**
	 * 
	 * @Title updateErpProduct   
	 * @Description TODO 更新产品
	 * @param erpProduct
	 * @param example
	 * @return      
	 * int
	 */
	public int updateErpProduct(ErpProduct erpProduct,Example example)
	{
		return erpProductMapper.updateByExampleSelective(erpProduct, example);
	}

	/**
	 * 按名称模糊查询产品
	 */
	
	public List<ErpProduct> selectProductByName(String productName, String orgId) {
		// TODO Auto-generated method stub
		return erpProductMapper.selectProductByName(productName, orgId);
	}
	
	public ErpProduct selectOne(ErpProduct erpProduct)
	{
		return erpProductMapper.selectOne(erpProduct);
	}
	
	/**
	 * 
	 * @Title getErpProductListByOrderDetailList   
	 * @Description TODO 通过订单明细获取产品列表   
	 * @param erpOrderDetailList
	 * @return      
	 * List<ErpProduct>
	 */
	public List<ErpProduct> getErpProductListByOrderDetailList(List<ErpOrderDetail> orderDetailList)
	{
		List<ErpProduct> returnList = new ArrayList<ErpProduct>();
		for(int i=0;i<orderDetailList.size();i++)
		{
			ErpProduct ep = new ErpProduct();
			ep.setProductId(orderDetailList.get(i).getProductId());
			ep.setOrgId(orderDetailList.get(i).getOrgId());
			returnList.add(erpProductMapper.selectOne(ep));
		}
		
		return returnList;
	}

	
	/**
	 * 获取产品与BOM的对应信息
	 */
	
	public Map<String, Object> getProuctAndBomInfoByProductId(String productId, String orgId) {
		// TODO Auto-generated method stub
		return erpProductMapper.getProuctAndBomInfoByProductId(productId, orgId);
	}
/**
 * 获取产品的select2列表
 */
	
	public List<Map<String,Object>> getProductSelect2(String orgId, String search) {
		// TODO Auto-generated method stub
		return erpProductMapper.getProductSelect2(orgId, search);
	}
}
