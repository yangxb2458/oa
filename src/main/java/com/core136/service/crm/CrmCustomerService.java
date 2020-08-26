package com.core136.service.crm;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.crm.CrmCustomer;
import com.core136.mapper.crm.CrmCustomerMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  CrmCustomerService   
 * @Description:TODO 基本信息操作类
 * @author: 稠云信息
 * @date:   2019年2月12日 下午3:49:32   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class CrmCustomerService{
	@Autowired
	private CrmCustomerMapper crmCustomerMapper;
	/**
	 * 
	 * @Title selectOne   
	 * @Description TODO 查询客户基本信息
	 * @param crmCustomer
	 * @return      
	 * int
	 */
	public CrmCustomer selectOne(CrmCustomer crmCustomer)
	{
		return crmCustomerMapper.selectOne(crmCustomer);
	}
	/**
	 * 
	 * @Title UpdateCrmCustomer   
	 * @Description TODO 更新客户基本信息
	 * @param crmCustomer
	 * @param example
	 * @return      
	 * int
	 */
	public int UpdateCrmCustomer(CrmCustomer crmCustomer,Example example)
	{
		return crmCustomerMapper.updateByExampleSelective(crmCustomer, example);
	}
	/**
	 * 
	 * @Title deleteCrmCustomer   
	 * @Description TODO 删除客户基本信息
	 * @param crmCustomer
	 * @return      
	 * int
	 */
	public int deleteCrmCustomer(CrmCustomer crmCustomer)
	{
		return crmCustomerMapper.delete(crmCustomer);
	}
	/**
	 * 
	 * @Title insertCrmCustomer   
	 * @Description TODO 添加客户基本信息
	 * @param crmCustomer
	 * @return      
	 * int
	 */
	public int insertCrmCustomer(CrmCustomer crmCustomer)
	{
		return crmCustomerMapper.insert(crmCustomer);
	}
	
	/**
	 * 
	 * @Title getErpOrderList   
	 * @Description TODO 获取客户列表
	 * @param example
	 * @param pageNumber
	 * @param pageSize
	 * @return      
	 * PageInfo<CrmCustomer>
	 */
	public PageInfo<CrmCustomer> getCrmCustomerList(Example example,int pageNumber,int pageSize)
	{
		PageHelper.startPage(pageNumber, pageSize);
		List<CrmCustomer> datalist=crmCustomerMapper.selectByExample(example);
		PageInfo<CrmCustomer> pageInfo = new PageInfo<CrmCustomer>(datalist);
		return pageInfo;
	}
	/**
	 * 获取权限内所有客户
	 */
	
	public List<Map<String, Object>> getAllCrmCustomerList(String orgId, String source, String model, String roles,
			String industry,String keepUser, String search,String country, String province, String city, String level,String intention,String opponent,List<String> list) {
		// TODO Auto-generated method stub
		return crmCustomerMapper.getAllCrmCustomerList(orgId, source, model, roles, industry, keepUser,search,country,province,city,level,intention,opponent,list);
	}
	
	
	/**
	 * 
	 * @Title getAllCrmCustomerList   
	 * @Description TODO 获取权限内所有客户
	 * @param pageNumber
	 * @param pageSize
	 * @param orgId
	 * @param source
	 * @param model
	 * @param roles
	 * @param industry
	 * @param search
	 * @param orderBy
	 * @return      
	 * PageInfo<Map<String,Object>>
	 */
	public PageInfo<Map<String,Object>> getAllCrmCustomerList(int pageNumber,int pageSize,String orgId, String source, String model, String roles,
			String industry,String keepUser,String search,String country, String province, String city, String level,String intention,String opponent,List<String> list,String orderBy)
	{
		PageHelper.startPage(pageNumber, pageSize);
		PageHelper.orderBy(orderBy);
		List<Map<String,Object>> datalist=crmCustomerMapper.getAllCrmCustomerList(orgId, source, model, roles, industry,keepUser, search,country,province,city,level,intention,opponent,list);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
		return pageInfo;
	}
	/**
	 * 获取SELECT2客户列表
	 */
	
	public List<Map<String, Object>> getSelect2CustomerList(String orgId, String keepUser, String search) {
		// TODO Auto-generated method stub
		return crmCustomerMapper.getSelect2CustomerList(orgId, keepUser, search);
	}
}
