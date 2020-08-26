package com.core136.service.erp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.erp.ErpBomDetail;
import com.core136.bean.erp.ErpMateriel;
import com.core136.mapper.erp.ErpMaterielMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
/**
 * 
 * @ClassName:  ErpMaterielService   
 * @Description:TODO 物料清单操作类 
 * @author: 稠云信息
 * @date:   2018年12月11日 下午6:35:19   
 *     
 * @Copyright: 2018 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class ErpMaterielService{
@Autowired
private ErpMaterielMapper erpMaterielMapper;

/**
 * 
 * @Title: insert   
 * @Description: TODO 添加新物料  
 * @param: erpMateriel
 * @param: @return      
 * @return: int      

 */
public int insert(ErpMateriel erpMateriel)
{
	return erpMaterielMapper.insert(erpMateriel);
}

/**
 * 
 * @Title: selectOne   
 * @Description: TODO 按条件获取一个具体的物料
 * @param: erpMateriel
 * @param: @return      
 * @return: ErpMateriel      

 */
public ErpMateriel selectOne(ErpMateriel erpMateriel)
{
	return erpMaterielMapper.selectOne(erpMateriel);
}

/**
 * 
 * @Title: update   
 * @Description: TODO 按条件更新物料信息
 * @param: erpMateriel
 * @param: example
 * @param: @return      
 * @return: int      

 */
public int updateErpMateriel(ErpMateriel erpMateriel,Example example)
{
	return erpMaterielMapper.updateByExample(erpMateriel, example);
	
}

/**
 * 
 * @Title: delete   
 * @Description: TODO 按条件删除物料
 * @param: erpMateriel
 * @param: @return      
 * @return: int      

 */
public int delete(ErpMateriel erpMateriel)
{
	return erpMaterielMapper.delete(erpMateriel);
}

/**
 * 
 * @Title: getErpMaterielBySort   
 * @Description: TODO 按条件查询ErpMateriel清单
 * @param: example
 * @param: @return      
 * @return: PageInfo<ErpMateriel>      

 */
public PageInfo<ErpMateriel> getErpMaterielBySort(Example example,int pageNumber,int pageSize)
{
	PageHelper.startPage(pageNumber, pageSize);
	List<ErpMateriel> datalist=erpMaterielMapper.selectByExample(example);
	PageInfo<ErpMateriel> pageInfo = new PageInfo<ErpMateriel>(datalist);
	return pageInfo;
}
/**
 * 按materielId判断不是否有物料存在
 */

public int isExistMaterielById(String materielId, String orgId) {
	// TODO Auto-generated method stub
	return erpMaterielMapper.isExistMaterielById(materielId, orgId);
}

/**
 * 按物料ID的模糊查询，用于SELECT2插件的选择
 */

public List<ErpMateriel> selectMateriel2ById(String materielCode, String orgId) {
	// TODO Auto-generated method stub
	return erpMaterielMapper.selectMateriel2ById(materielCode, orgId);
}

/**
 * 
 * @Title selectOneByCode   
 * @Description TODO 控物料编码查询物料 
 * @param materielCode
 * @param orgId
 * @return      
 * ErpMateriel
 */

public ErpMateriel selectOneByCode(String materielCode, String orgId) {
	// TODO Auto-generated method stub
	return erpMaterielMapper.selectOneByCode(materielCode, orgId);
}

/**
 * 
 * @Title getErpMaterielByBomDetail   
 * @Description TODO 通过BOM清单获取物料清单  
 * @param erpBomDetail
 * @return      
 * ErpMateriel
 */
public ErpMateriel getErpMaterielByBomDetail(ErpBomDetail erpBomDetail)
{
	ErpMateriel erpMateriel = new ErpMateriel();
	erpMateriel.setMaterielCode(erpBomDetail.getMaterielCode());
	erpMateriel.setOrgId(erpBomDetail.getOrgId());
	return erpMaterielMapper.selectOne(erpMateriel);
}



}
