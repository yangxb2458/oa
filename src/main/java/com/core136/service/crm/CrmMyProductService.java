/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  CrmMyProductService.java   
 * @Package com.core136.service.crm   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年4月8日 上午10:53:08   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.service.crm;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.crm.CrmMyProduct;
import com.core136.mapper.crm.CrmMyProductMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**   
 * @ClassName:  CrmMyProductService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年4月8日 上午10:53:08   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@Service
public class CrmMyProductService{
@Autowired
private CrmMyProductMapper crmMyProductMapper;
/**
 * 
* @Title: getAllMyProduct 
* @Description: TODO 获取所有关品列表
* @param @param orgId
* @param @return 设定文件 
* @return List<CrmMyProduct> 返回类型 

 */
public List<CrmMyProduct> getAllMyProduct(String orgId)
{
	Example example = new Example(CrmMyProduct.class);
	example.createCriteria().andEqualTo("orgId",orgId);
	return crmMyProductMapper.selectByExample(example);
}

public int insertCrmMyProduct(CrmMyProduct crmMyProduct)
{
	return crmMyProductMapper.insert(crmMyProduct);
}

public int deleteCrmMyProduct(CrmMyProduct crmMyProduct)
{
	return crmMyProductMapper.delete(crmMyProduct);
}

public int updateCrmMyProduct(CrmMyProduct crmMyProduct,Example example)
{
	return crmMyProductMapper.updateByExampleSelective(crmMyProduct, example);
}


public CrmMyProduct selectOneCrmMyProdcut(CrmMyProduct crmMyProduct)
{
	return crmMyProductMapper.selectOne(crmMyProduct);
}

/**
 * 按productId获取参应的产品名称
 */

public List<Map<String, Object>> getMyProductNameStr(String orgId, List<String> list) {
	// TODO Auto-generated method stub
	return crmMyProductMapper.getMyProductNameStr(orgId, list);
}


public PageInfo<Map<String, Object>> getAllProductList(int pageNumber,int pageSize,String orderBy,String orgId, String search) {
	// TODO Auto-generated method stub
	PageHelper.startPage(pageNumber, pageSize,orderBy);
	List<Map<String, Object>> datalist= this.getAllProductList(orgId, search);
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(datalist);
	return pageInfo;
}

/* 
 * 获取对应的产品列表
 */

public List<Map<String, Object>> getAllProductList(String orgId, String search) {
	return crmMyProductMapper.getAllProductList(orgId, search);
}

}
