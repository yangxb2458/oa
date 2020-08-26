package com.core136.service.erp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.erp.ErpOrder;
import com.core136.mapper.erp.ErpOrderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ErpOrderService {
@Autowired
private ErpOrderMapper erpOrderMapper;
/**
 * 
 * @Title insertErpOrder   
 * @Description TODO 添加订单
 * @param erpOrder
 * @return      
 * int
 */
public int insertErpOrder(ErpOrder erpOrder)
{
	return erpOrderMapper.insert(erpOrder);
}
/**
 * 
 * @Title select   
 * @Description TODO 控条件获取ErpOrder  
 * @param erpOrder
 * @return      
 * ErpOrder
 */
public ErpOrder selectOne(ErpOrder erpOrder)
{
	return erpOrderMapper.selectOne(erpOrder);
}

/**
 * 
 * @Title getErpOrderList   
 * @Description TODO 获取订单列表
 * @param example
 * @param pageNumber
 * @param pageSize
 * @return      
 * PageInfo<ErpOrder>
 */

public PageInfo<ErpOrder> getErpOrderList(Example example,int pageNumber,int pageSize)
{
	PageHelper.startPage(pageNumber, pageSize);
	List<ErpOrder> datalist=erpOrderMapper.selectByExample(example);
	PageInfo<ErpOrder> pageInfo = new PageInfo<ErpOrder>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title delErpOrder   
 * @Description TODO 删除订单
 * @param erpOrder
 * @return      
 * int
 */
public int deleteErpOrder(ErpOrder erpOrder)
{
	return erpOrderMapper.delete(erpOrder);
}

/**
 * 
 * @Title updateErpOrder   
 * @Description TODO 更新订单信息
 * @param erpOrder
 * @param example
 * @return      
 * int
 */
public int updateErpOrder(ErpOrder erpOrder,Example example)
{
	return erpOrderMapper.updateByExampleSelective(erpOrder, example);
}

}
