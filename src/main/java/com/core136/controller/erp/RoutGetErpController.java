package com.core136.controller.erp;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.erp.ErpBom;
import com.core136.bean.erp.ErpBomDetail;
import com.core136.bean.erp.ErpBomSort;
import com.core136.bean.erp.ErpEquipmentSort;
import com.core136.bean.erp.ErpMateriel;
import com.core136.bean.erp.ErpMaterielSort;
import com.core136.bean.erp.ErpOrder;
import com.core136.bean.erp.ErpProduct;
import com.core136.bean.erp.ErpProductSort;
import com.core136.service.account.AccountService;
import com.core136.service.erp.DoCostService;
import com.core136.service.erp.ErpBomDetailService;
import com.core136.service.erp.ErpBomService;
import com.core136.service.erp.ErpBomSortService;
import com.core136.service.erp.ErpEquipmentService;
import com.core136.service.erp.ErpEquipmentSortService;
import com.core136.service.erp.ErpMaterielService;
import com.core136.service.erp.ErpMaterielSortService;
import com.core136.service.erp.ErpOrderDetailService;
import com.core136.service.erp.ErpOrderService;
import com.core136.service.erp.ErpProductService;
import com.core136.service.erp.ErpProductSortService;
import com.core136.service.erp.ErpUnitService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.StrTools;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
/**
 * 
 * @ClassName:  RoutGetErpController   
 * @DescriptionTODO ERP数据获取接口   
 * @author: 稠云信息
 * @date:   2018年12月7日 下午1:36:14   
 *     
 * @Copyright: 2018 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@RestController
@RequestMapping("/ret/erpget")
public class RoutGetErpController {
	
@Autowired
private ErpMaterielSortService erpMaterielSortService;
@Autowired
private ErpProductSortService erpProductSortService;
@Autowired
private ErpUnitService erpUnitService;
@Autowired
private ErpMaterielService erpMaterielService;
@Autowired
private ErpBomSortService erpBomSortService;
@Autowired
private ErpBomDetailService erpBomDetailService;
@Autowired
private ErpBomService erpBomService;
@Autowired
private ErpProductService erpProductService;
@Autowired
private ErpOrderService erpOrderSerive;
@Autowired
private ErpOrderDetailService erpOrderDetailService;
@Autowired
private DoCostService doCostService;
@Autowired
private ErpEquipmentService erpEquipmentService;
@Autowired
private ErpEquipmentSortService erpEquipmentSortService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title getErpMaterielSortById   
 * @Description TODO 按条件查询一个分类信息     
 * @param  request
 * @param  erpMaterielSort
 *      
 * @return RetDataBean      

 */
@RequestMapping(value="/getErpMaterielSortById",method=RequestMethod.POST)
public RetDataBean getErpMaterielSortById(HttpServletRequest request,ErpMaterielSort erpMaterielSort)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		erpMaterielSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!", erpMaterielSortService.selectOne(erpMaterielSort));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title getErpMaterielSortTree   
 * @Description TODO 获取物料的分类树   
 * @param  request
 * @param  sortId
 *      
 * @return List<Map<String,Object>>      

 */
@RequestMapping(value="/getErpMaterielSortTree",method=RequestMethod.POST)
public List<Map<String,Object>> getErpMaterielSortTree(HttpServletRequest request,String sortId)
{
	try
	{
		String sortLeave = "0";
		if(StringUtils.isNotBlank(sortId))
		{
			sortLeave = sortId;
		}
		Account account=accountService.getRedisAccount(request);
		return erpMaterielSortService.getErpMaterielSortTree(sortLeave,account.getOrgId());
	}catch (Exception e) {
		// TODO: handle exception
		return null;
	}
}
/**
 * 
 * @Title getErpProductSortTree   
 * @Description TODO 获取产品分类的树 
 * @param  request
 * @param  sortId
 *      
 * @return List<Map<String,Object>>      

 */
@RequestMapping(value="/getErpProductSortTree",method=RequestMethod.POST)
public List<Map<String,Object>> getErpProductSortTree(HttpServletRequest request,String sortId)
{
	try
	{
		String sortLeave = "0";
		if(StringUtils.isNotBlank(sortId))
		{
			sortLeave = sortId;
		}
		Account account=accountService.getRedisAccount(request);
		return erpProductSortService.getErpProductSortTree(sortLeave,account.getOrgId());
	}catch (Exception e) {
		// TODO: handle exception
		return null;
	}
}

/**
 * 
 * @Title getErpProductSortById   
 * @Description TODO 按条件查询一条产品分类  
 * @param  request
 * @param  erpProductSort
 *      
 * @return RetDataBean      

 */
@RequestMapping(value="/getErpProductSortById",method=RequestMethod.POST)
public RetDataBean getErpProductSortById(HttpServletRequest request,ErpProductSort erpProductSort)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		erpProductSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!", erpProductSortService.selectOne(erpProductSort));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title getAllErpUnit   
 * @Description TODO 获取所有计量单位
 * @param  request
 *      
 * @return RetDataBean      

 */
@RequestMapping(value="/getAllErpUnit",method=RequestMethod.POST)
public RetDataBean getAllErpUnit(HttpServletRequest request)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", erpUnitService.getAllUnit(account.getOrgId()));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title getErpMaterielBySort   
 * @Description TODO 获取物料清单
 * @param  request
 * @param  sortLeave
 * @param  pageNumber
 * @param  pageSize
 * @param  search
 * @param  sort
 * @param  sortOrder
 * @return RetDataBean      

 */
@RequestMapping(value="/getErpMaterielBySort",method=RequestMethod.POST)
public RetDataBean getErpMaterielBySort(
		HttpServletRequest request,
		String sortLeave,
		Integer pageNumber,
		Integer pageSize,
		String search,
		String sort,
		String sortOrder
		)
{
	try
	{
		if(StringUtils.isBlank(sort))
		{
			sort="MATERIEL_CODE";
		}else
		{
			sort=StrTools.upperCharToUnderLine(sort);
		}
		if(StringUtils.isBlank(sortOrder))
		{
			sortOrder="asc";
		}
		
	Account account=accountService.getRedisAccount(request);
	Example example = new Example(ErpMateriel.class);
	example.setOrderByClause(sort+" "+sortOrder);
	Criteria criteria = example.createCriteria();
	Criteria criteria2 = example.createCriteria();
	criteria.andEqualTo("orgId", account.getOrgId());
	
	if(!StringUtils.isBlank(search))
	{
		criteria2.orLike("materielCode", "%"+search+"%").orLike("materielName", "%"+search+"%");
		example.and(criteria2);
	}else
	{
		criteria.andEqualTo("sortLeave", sortLeave);
	}
	 PageInfo<ErpMateriel> pageInfo=erpMaterielService.getErpMaterielBySort(example,pageNumber,pageSize);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title getErpBomSortTree   
 * @Description TODO 获取BOM 分类树结构
 * @param request
 * @param sortId
 * @return      
 * List<Map<String,Object>>
 */
@RequestMapping(value="/getErpBomSortTree",method=RequestMethod.POST)
public List<Map<String,Object>> getErpBomSortTree(HttpServletRequest request,String sortId)
{
	try
	{
		String sortLeave = "0";
		if(StringUtils.isNotBlank(sortId))
		{
			sortLeave = sortId;
		}
		Account account=accountService.getRedisAccount(request);
		return erpBomSortService.getErpBomSortTree(sortLeave,account.getOrgId());
	}catch (Exception e) {
		// TODO: handle exception
		return null;
	}
}
/**
 * 
 * @Title getErpBomSortById   
 * @Description TODO  按分类ID获取分类信息
 * @param request
 * @param erpBomSort
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/getErpBomSortById",method=RequestMethod.POST)
public RetDataBean getErpBomSortById(HttpServletRequest request,ErpBomSort erpBomSort)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		erpBomSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!", erpBomSortService.selectOne(erpBomSort));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title getErpBomBySort   
 * @Description TODO 按分类获取BOM列表
 * @param request
 * @param sortId
 * @param pageNumber
 * @param pageSize
 * @param search
 * @param sort
 * @param sortOrder
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/getErpBomBySort",method=RequestMethod.POST)
public RetDataBean getErpBomBySort(
		HttpServletRequest request,
		String sortId,
		Integer pageNumber,
		Integer pageSize,
		String search,
		String sort,
		String sortOrder
		)
{
	try
	{
		if(StringUtils.isBlank(sort))
		{
			sort="SORT_NO";
		}else
		{
			sort=StrTools.upperCharToUnderLine(sort);
		}
		if(StringUtils.isBlank(sortOrder))
		{
			sortOrder="asc";
		}
		
	Account account=accountService.getRedisAccount(request);
	Example example = new Example(ErpBom.class);
	example.setOrderByClause(sort+" "+sortOrder);
	Criteria criteria = example.createCriteria();
	Criteria criteria2 = example.createCriteria();
	criteria.andEqualTo("orgId", account.getOrgId());
	if(!StringUtils.isBlank(search))
	{
		criteria2.orLike("bomName", "%"+search+"%").orLike("version", "%"+search+"%");
		example.and(criteria2);
	}else
	{
		criteria.andEqualTo("sortId", sortId);
	}
	 PageInfo<ErpBom> pageInfo=erpBomService.getErpBomBySort(example,pageNumber,pageSize);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title getErpBomById   
 * @Description TODO 查询一条BOM
 * @param request
 * @param erpBom
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/getErpBomById",method=RequestMethod.POST)
public RetDataBean getErpBomById(HttpServletRequest request,ErpBom erpBom)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		erpBom.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!", erpBomService.selectOne(erpBom));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title getErpMaterielById   
 * @Description TODO(这里用一句话描述这个方法的作用)   
 * @param request
 * @param erpMateriel
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/getErpMaterielById",method=RequestMethod.POST)
public RetDataBean getErpMaterielById(HttpServletRequest request,ErpMateriel erpMateriel)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		erpMateriel.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!", erpMaterielService.selectOne(erpMateriel));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title selectMateriel2ById   
 * @Description TODO 按物料ID的模糊查询，用于SELECT2插件的选择
 * @param request
 * @param materielId
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/selectMateriel2ById",method=RequestMethod.POST)
public RetDataBean selectMateriel2ById(HttpServletRequest request,String materielCode )
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", erpMaterielService.selectMateriel2ById(materielCode+"%", account.getOrgId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title getErpBomDetailById   
 * @Description TODO 按BOMID获取BOM清单详情  
 * @param request
 * @param sortLeave
 * @param pageNumber
 * @param pageSize
 * @param search
 * @param sort
 * @param sortOrder
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/getErpBomDetailById",method=RequestMethod.POST)
public RetDataBean getErpBomDetailById(
		HttpServletRequest request,
		String bomId,
		Integer pageNumber,
		Integer pageSize,
		String search,
		String sort,
		String sortOrder
		)
{
	try
	{
		if(StringUtils.isBlank(sort))
		{
			sort="BOM_ID";
		}else
		{
			sort=StrTools.upperCharToUnderLine(sort);
		}
		if(StringUtils.isBlank(sortOrder))
		{
			sortOrder="asc";
		}
	String orderBy = sort+ " " + sortOrder;
	Account account=accountService.getRedisAccount(request);
	PageInfo<Map<String, Object>> pageInfo=erpBomDetailService.getBomDetailList(pageNumber,pageSize,orderBy,bomId,"%"+search+"%",account.getOrgId());
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title getErpBomDetailByDetailId   
 * @Description TODO 获取一条BOMDETAIL物料
 * @param request
 * @param erpBomDetail
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/getErpBomDetailByDetailId",method=RequestMethod.POST)
public RetDataBean getErpBomDetailByDetailId(HttpServletRequest request,ErpBomDetail erpBomDetail)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", erpBomDetailService.getBomDetailByDetailId(erpBomDetail.getBomId(),erpBomDetail.getBomDetailId(),account.getOrgId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

@RequestMapping(value="/getErpProductBySort",method=RequestMethod.POST)
public RetDataBean getErpProductBySort(
		HttpServletRequest request,
		String sortLeave,
		Integer pageNumber,
		Integer pageSize,
		String search,
		String sort,
		String sortOrder
		)
{
	try
	{
		if(StringUtils.isBlank(sort))
		{
			sort="SORT_NO";
		}else
		{
			sort=StrTools.upperCharToUnderLine(sort);
		}
		if(StringUtils.isBlank(sortOrder))
		{
			sortOrder="asc";
		}
		
	Account account=accountService.getRedisAccount(request);
	Example example = new Example(ErpProduct.class);
	example.setOrderByClause(sort+" "+sortOrder);
	Criteria criteria = example.createCriteria();
	criteria.andEqualTo("orgId", account.getOrgId());
	if(StringUtils.isNotEmpty(search))
	{
		Criteria criteria2 = example.createCriteria();
		criteria2.orLike("productName", "%"+search+"%").orLike("model", "%"+search+"%");
		example.and(criteria2);
	}else
	{
		criteria.andEqualTo("sortLeave", sortLeave);
	}
	 PageInfo<ErpProduct> pageInfo=erpProductService.getErpProductBySort(example,pageNumber,pageSize);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title selectProductByName   
 * @Description TODO 按名称模糊查询产品   
 * @param request
 * @param productName
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/selectProductByName",method=RequestMethod.POST)
public RetDataBean selectProductByName(HttpServletRequest request,String productName)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", erpProductService.selectProductByName("%"+productName+"%",account.getOrgId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title getProductById   
 * @Description TODO 获取产品信息
 * @param request
 * @param erpProduct
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/getProductById",method=RequestMethod.POST)
public RetDataBean getProductById(HttpServletRequest request,ErpProduct erpProduct)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		erpProduct.setOrgId(account.getOrgId());
		return RetDataTools.Ok("获取数据成功!", erpProductService.selectOne(erpProduct));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title getErpBomTreeBySortId   
 * @Description TODO 按分类获取BOM树结构   
 * @param request
 * @param sortId
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/getErpBomTreeBySortId",method=RequestMethod.POST)
public RetDataBean getErpBomTreeBySortId(HttpServletRequest request,String sortId)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", erpBomService.getErpBomTreeBySortId(sortId,account.getOrgId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title getErpOrderList   
 * @Description TODO 获取订单列表
 * @param request
 * @param pageNumber
 * @param pageSize
 * @param search
 * @param sort
 * @param sortOrder
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/getErpOrderList",method=RequestMethod.POST)
public RetDataBean getErpOrderList(
		HttpServletRequest request,
		Integer pageNumber,
		Integer pageSize,
		String search,
		String sort,
		String sortOrder
		)
{
	try
	{
		if(StringUtils.isBlank(sort))
		{
			sort="CREATE_TIME";
		}else
		{
			sort=StrTools.upperCharToUnderLine(sort);
		}
		if(StringUtils.isBlank(sortOrder))
		{
			sortOrder="asc";
		}
	String orderBy = sort+ " " + sortOrder;
	Account account=accountService.getRedisAccount(request);
	Example example = new Example(ErpOrder.class);
	example.setOrderByClause(orderBy);
	Criteria criteria = example.createCriteria();
	if(!account.getOpFlag().equals("1"))
	{
		criteria.andEqualTo("createUser",account.getAccountId());
	}
	criteria.andEqualTo("orgId",account.getOrgId());
	if(StringUtils.isNotEmpty(search))
	{
		Criteria criteria2 = example.createCriteria();
		criteria2.orLike("orderTitle", "%"+search+"%").orLike("orderId", "%"+search+"%").orLike("customer", "%"+search+"%");
		example.and(criteria2);
	}
	PageInfo<ErpOrder> pageInfo=erpOrderSerive.getErpOrderList(example, pageNumber, pageSize);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title getErpOrderDetail   
 * @Description TODO 获取订单详情产品列表
 * @param request
 * @param pageNumber
 * @param pageSize
 * @param search
 * @param sort
 * @param sortOrder
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/getErpOrderDetail",method=RequestMethod.POST)
public RetDataBean getErpOrderDetail(
		HttpServletRequest request,
		String orderId,
		Integer pageNumber,
		Integer pageSize,
		String search,
		String sort,
		String sortOrder
		)
{
	try
	{
		if(StringUtils.isBlank(sort))
		{
			sort="CREATE_TIME";
		}else
		{
			sort=StrTools.upperCharToUnderLine(sort);
		}
		if(StringUtils.isBlank(sortOrder))
		{
			sortOrder="asc";
		}
	String orderBy = sort+ " " + sortOrder;
	Account account=accountService.getRedisAccount(request);
	PageInfo<Map<String, Object>> pageInfo= erpOrderDetailService.getErpOrderDetailList(orderId, "%"+search+"%", account.getOrgId(),pageNumber, pageSize,orderBy);
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title doCostByOrder   
 * @Description TODO 计算订单产品总价
 * @param request
 * @param erpOrder
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/doCostByOrder",method=RequestMethod.POST)
public RetDataBean doCostByOrder(HttpServletRequest request,ErpOrder erpOrder)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		erpOrder.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!", doCostService.doCost(erpOrder));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title getErpBomByBomIdList   
 * @Description TODO 获取BOM清单中的子BOM清单    
 * @param request
 * @param bomId
 * @param pageNumber
 * @param pageSize
 * @param search
 * @param sort
 * @param sortOrder
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/getErpBomByBomIdList",method=RequestMethod.POST)
public RetDataBean getErpBomByBomIdList(
		HttpServletRequest request,
		String bomId,
		Integer pageNumber,
		Integer pageSize,
		String search,
		String sort,
		String sortOrder
		)
{
	try
	{
		if(StringUtils.isBlank(sort))
		{
			sort="B.BOM_ID";
		}else
		{
			sort=StrTools.upperCharToUnderLine(sort);
		}
		if(StringUtils.isBlank(sortOrder))
		{
			sortOrder="asc";
		}
	String orderBy = sort+ " " + sortOrder;
	Account account=accountService.getRedisAccount(request);
	PageInfo<Map<String, Object>> pageInfo=erpBomDetailService.getErpBomByBomIdList(pageNumber,pageSize,orderBy,bomId,"%"+search+"%",account.getOrgId());
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title selectBomList2ById   
 * @Description TODO 获取BOM清单用于SELECT2插件
 * @param request
 * @param bomName
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/selectBomList2ById",method=RequestMethod.POST)
public RetDataBean selectBomList2ById(HttpServletRequest request,String bomName )
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", erpBomService.selectBomList2ById("%"+bomName+"%", account.getOrgId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title getProuctAndBomInfoByProductId   
 * @Description TODO 获取产品与BOM的对应信息
 * @param request
 * @param productId
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/getProuctAndBomInfoByProductId",method=RequestMethod.POST)
public RetDataBean getProuctAndBomInfoByProductId(HttpServletRequest request,String productId )
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!", erpProductService.getProuctAndBomInfoByProductId(productId, account.getOrgId()));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title getProductMaterielListByBomId   
 * @Description TODO 产品物料详情
 * @param request
 * @param bomId
 * @param pageNumber
 * @param pageSize
 * @param search
 * @param sort
 * @param sortOrder
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/getProductMaterielListByBomId",method=RequestMethod.POST)
public RetDataBean getProductMaterielListByBomId(
		HttpServletRequest request,
		String bomId,
		Integer pageNumber,
		Integer pageSize,
		String search,
		String sort,
		String sortOrder
		)
{
	try
	{
		if(StringUtils.isBlank(sort))
		{
			sort="id";
		}else
		{
			sort=StrTools.upperCharToUnderLine(sort);
		}
		if(StringUtils.isBlank(sortOrder))
		{
			sortOrder="asc";
		}
	String orderBy = sort+ " " + sortOrder;
	Account account=accountService.getRedisAccount(request);
	PageInfo<Map<String, Object>> pageInfo=erpBomDetailService.getProductMaterielListByBomId(pageNumber,pageSize,orderBy,bomId,account.getOrgId());
	return RetDataTools.Ok("请求数据成功!", pageInfo);
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title doCostByProduct   
 * @Description TODO 计算产品单价
 * @param request
 * @param erpOrder
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/doCostByProduct",method=RequestMethod.POST)
public RetDataBean doCostByProduct(HttpServletRequest request,ErpProduct erpProduct)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		erpProduct.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!", doCostService.doCostByProduct(erpProduct));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title doCostByBom   
 * @Description TODO 按BOMID获取所有物料清单与用量 
 * @param request
 * @param erpBom
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/doCostByBom",method=RequestMethod.POST)
public RetDataBean doCostByBom(HttpServletRequest request,ErpBom erpBom)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		erpBom.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!", doCostService.doCostByBom(erpBom));
	}catch (Exception e) {
		return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title getErpEquipmentSortTree   
 * @Description TODO 获取设备分类树结构
 * @param request
 * @param sortId
 * @return List<Map<String,Object>>
 */
@RequestMapping(value="/getErpEquipmentSortTree",method=RequestMethod.POST)
public List<Map<String,Object>> getErpEquipmentSortTree(HttpServletRequest request,String sortId)
{
	try
	{
		String sortLeave = "0";
		if(StringUtils.isNotBlank(sortId))
		{
			sortLeave = sortId;
		}
		Account account=accountService.getRedisAccount(request);
		return erpEquipmentSortService.getErpEquipmentSortTree(sortLeave,account.getOrgId());
	}catch (Exception e) {
		// TODO: handle exception
		return null;
	}
}
/**
 * 
 * @Title getErpEquipmentSortById   
 * @Description TODO 获取设备分类  
 * @param request
 * @param erpEquipmentSort
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/getErpEquipmentSortById",method=RequestMethod.POST)
public RetDataBean getErpEquipmentSortById(HttpServletRequest request,ErpEquipmentSort erpEquipmentSort)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		erpEquipmentSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!", erpEquipmentSortService.selectOne(erpEquipmentSort));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
* @Title: getProductSelect2 
* @Description: TODO获取产品的select2列表
* @param @param request
* @param @param search
* @param @return 设定文件 
* @return RetDataBean 返回类型 
 */
@RequestMapping(value="/getProductSelect2",method=RequestMethod.POST)
public RetDataBean getProductSelect2(HttpServletRequest request,String search)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("请求成功!",erpProductService.getProductSelect2(account.getOrgId(), "%"+search+"%"));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

}
