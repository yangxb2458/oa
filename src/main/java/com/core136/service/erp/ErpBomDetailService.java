package com.core136.service.erp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.erp.ErpBomDetail;
import com.core136.mapper.erp.ErpBomDetailMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
@Service
public class ErpBomDetailService{
@Autowired
private ErpBomDetailMapper erpBomDetailMapper;

/**
 * 
 * @Title insterErpBomDetail   
 * @Description TODO 添加BOM清单详情   
 * @param erpBomDetail
 * @return      
 * int
 */
public int insertErpBomDetail(ErpBomDetail erpBomDetail)
{
	return erpBomDetailMapper.insert(erpBomDetail);
}




/**
 * 
 * @Title getErpBomDetailById   
 * @Description TODO 按BOMID获取对应的BOM清单详情  
 * @param example
 * @param pageNumber
 * @param pageSize
 * @return      
 * PageInfo<ErpBomDetail>
 */
public PageInfo<ErpBomDetail> getErpBomDetailById(Example example,int pageNumber,int pageSize)
{
	PageHelper.startPage(pageNumber, pageSize);
	List<ErpBomDetail> datalist=erpBomDetailMapper.selectByExample(example);
	PageInfo<ErpBomDetail> pageInfo = new PageInfo<ErpBomDetail>(datalist);
	return pageInfo;
}



/**
 * 
 * @Title getBomDetailList   
 * @Description TODO 查询Bom下的所有物料
 * @param bomId
 * @param materielCode
 * @param orgId
 * @return      
 * PageInfo<Map<String, Object>>
 */
public PageInfo<Map<String, Object>> getBomDetailList(int pageNumber,int pageSize,String orderBy,String bomId, String materielCode, String orgId) {
	// TODO Auto-generated method stub
	PageHelper.startPage(pageNumber, pageSize,orderBy);
	List<Map<String,Object>> datalist= erpBomDetailMapper.getBomDetailList(bomId, materielCode, orgId);
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}



/**
 * 
 * @Title getBomDetailList   
 * @Description TODO 查询Bom下的所有物料
 * @param bomId
 * @param materielCode
 * @param orgId
 * @return      
 * List<Map<String,Object>>
 */

public List<Map<String, Object>> getBomDetailList(String bomId, String materielCode, String orgId) {
	// TODO Auto-generated method stub
	return erpBomDetailMapper.getBomDetailList(bomId, materielCode, orgId);
}



/**
 * 判断当前BOM清单是不存在当前需要保存的物料编码
 */

public int isExistMaterielCode(String bomId, String materielCode, String orgId) {
	// TODO Auto-generated method stub
	return erpBomDetailMapper.isExistMaterielCode(bomId, materielCode, orgId);
}

/**
 * 
 * @Title selectOne   
 * @Description  按条件查询一条物料  
 * @param example
 * @return      
 * ErpBomDetail
 */
public ErpBomDetail getErpBomDetailByDetailId(Example example)
{
	return erpBomDetailMapper.selectOneByExample(example);
}

/**
 * 
 * @Title del   
 * @Description TODO 删除指的物料  
 * @param erpBomDetail
 * @return      
 * int
 */
public int deleteErpBomDetail(ErpBomDetail erpBomDetail)
{
	return erpBomDetailMapper.delete(erpBomDetail);
}

/**
 * 
 * @Title update   
 * @Description TODO 按条件更新BOM清单详情   
 * @param erpBomDetail
 * @param example
 * @return      
 * int
 */
public int updateErpBomDetail(ErpBomDetail erpBomDetail,Example example)
{
	return erpBomDetailMapper.updateByExampleSelective(erpBomDetail, example);
}

/**
 * 获取指定的物料
 */

public Map<String,Object> getBomDetailByDetailId(String bomId, String bomDetailId, String orgId) {
	// TODO Auto-generated method stub
	return erpBomDetailMapper.getBomDetailByDetailId(bomId, bomDetailId, orgId);
}
/**
 * 
 * @Title getErpBomDetailListByErpBom   
 * @Description TODO 通近BOM获取BOM清单
 * @param erpBom
 * @return      
 * List<ErpBomDetail>
 */
public List<ErpBomDetail> getErpBomDetailListByErpBom(ErpBomDetail erpBomDetail)
{
	return erpBomDetailMapper.select(erpBomDetail);
}





public List<Map<String, Object>> getErpBomByBomIdList(String bomId, String bomName, String orgId) {
	// TODO Auto-generated method stub
	return erpBomDetailMapper.getErpBomByBomIdList(bomId, bomName, orgId);
}

public PageInfo<Map<String, Object>> getErpBomByBomIdList(int pageNumber,int pageSize,String orderBy,String bomId, String bomName, String orgId) {
	// TODO Auto-generated method stub
	PageHelper.startPage(pageNumber, pageSize,orderBy);
	List<Map<String,Object>> datalist= erpBomDetailMapper.getErpBomByBomIdList(bomId, bomName, orgId);
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}

/**
 * 查询子BOM是否存于现有的BOM中  
 */

public int isExistChildBomIdCode(String bomId, String childBomId, String orgId) {
	// TODO Auto-generated method stub
	return erpBomDetailMapper.isExistChildBomIdCode(bomId, childBomId, orgId);
}



/**
 * 产品物料详情
 */

public List<Map<String, Object>> getProductMaterielList(String bomId, String orgId) {
	// TODO Auto-generated method stub
	return erpBomDetailMapper.getProductMaterielList(bomId, orgId);
}

public PageInfo<Map<String, Object>> getProductMaterielListByBomId(int pageNumber,int pageSize,String orderBy,String bomId,String orgId) {
	// TODO Auto-generated method stub
	PageHelper.startPage(pageNumber, pageSize,orderBy);
	List<Map<String,Object>> datalist= erpBomDetailMapper.getProductMaterielList(bomId, orgId);
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}

}
