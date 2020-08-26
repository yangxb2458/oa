package com.core136.service.crm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.crm.CrmOrder;
import com.core136.mapper.crm.CrmOrderMapper;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  CrmOrderService   
 * @Description:TODO 客户订单管理
 * @author: 稠云信息
 * @date:   2019年2月12日 下午5:01:58   
 *     
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class CrmOrderService{
@Autowired
private CrmOrderMapper crmOrderMapper;
/**
 * 
 * @Title selectOne   
 * @Description TODO 获取订单详情
 * @param crmOrder
 * @return      
 * CrmOrder
 */
public CrmOrder selectOne(CrmOrder crmOrder)
{
	return crmOrderMapper.selectOne(crmOrder);
}
/**
 * 
 * @Title updateCrmOrder   
 * @Description TODO 修改订单
 * @param crmOrder
 * @param example
 * @return      
 * int
 */
public int updateCrmOrder(CrmOrder crmOrder,Example example)
{
	return crmOrderMapper.updateByExampleSelective(crmOrder, example);
}
/**
 * 
 * @Title deleteCrmOrder   
 * @Description TODO 删除订单
 * @param crmOrder
 * @return      
 * int
 */
public int deleteCrmOrder(CrmOrder crmOrder)
{
	return crmOrderMapper.delete(crmOrder);
}
/**
 * 
 * @Title insertCrmOrder   
 * @Description TODO 创建订单
 * @param crmOrder
 * @return      
 * int
 */
public int insertCrmOrder(CrmOrder crmOrder)
{
	return crmOrderMapper.insert(crmOrder);
}
}
