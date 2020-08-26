package com.core136.controller.erp;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.core136.bean.account.Account;
import com.core136.bean.erp.ErpBom;
import com.core136.bean.erp.ErpBomDetail;
import com.core136.bean.erp.ErpBomSort;
import com.core136.bean.erp.ErpEquipmentSort;
import com.core136.bean.erp.ErpMateriel;
import com.core136.bean.erp.ErpMaterielSort;
import com.core136.bean.erp.ErpOrder;
import com.core136.bean.erp.ErpOrderDetail;
import com.core136.bean.erp.ErpProduct;
import com.core136.bean.erp.ErpProductSort;
import com.core136.service.account.AccountService;
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
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
/**
 * 
 * @ClassName:  RoutSetErpController   
 * @Description:TODO ERP数据更新接口
 * @author: 稠云信息
 * @date:   2018年12月14日 下午2:39:31   
 *     
 * @Copyright: 2018 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@RestController
@RequestMapping("/set/erpset")
public class RoutSetErpController {
@Autowired
private ErpMaterielSortService erpMaterielSortService;
@Autowired
private ErpProductSortService erpProductSortService;
@Autowired
private ErpMaterielService erpMaterielService;
@Autowired
private ErpBomSortService erpBomSortService;
@Autowired
private ErpBomService erpBomService;
@Autowired
private ErpBomDetailService erpBomDetailService;
@Autowired
private ErpProductService erpProductService;
@Autowired
private ErpOrderService erpOrderService;
@Autowired
private ErpOrderDetailService erpOrderDetailService;
@Autowired
private ErpEquipmentService erpEquipmentService;
@Autowired
private ErpEquipmentSortService erpEquipmentSortService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title: addMaterielSort   
 * @Description: TODO 创建ERP物料分类  
 * @param request
 * @param erpMaterielSort
 * @param      
 * @return: RetDataBean      数据返回类

 */
@RequestMapping(value="/insertErpMaterielSort",method=RequestMethod.POST)
	public RetDataBean insertErpMaterielSort(HttpServletRequest request,ErpMaterielSort erpMaterielSort)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(erpMaterielSort.getSortLeave()))
			{
				erpMaterielSort.setSortLeave("0");
			}
			erpMaterielSort.setSortId(SysTools.getGUID());
			erpMaterielSort.setCreateUser(account.getAccountId());
			erpMaterielSort.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			erpMaterielSort.setOrgId(account.getOrgId());
			return RetDataTools.Ok("创建成功!", erpMaterielSortService.insertErpMaterielSort(erpMaterielSort));
		}catch (Exception e) {
			// TODO: handle exception
			return RetDataTools.Error(e.getMessage());
		}
	}

/**
 * 
 * @Title: delErpMaterielSort   
 * @Description: TODO 删除物料分类
 * @param: request
 * @param: erpMaterielSort
 * @param: @return      
 * @return: RetDataBean      

 */
@RequestMapping(value="/delErpMaterielSort",method=RequestMethod.POST)
public RetDataBean delErpMaterielSort(HttpServletRequest request,ErpMaterielSort erpMaterielSort)
{
	try
	{
		if(StringUtils.isBlank(erpMaterielSort.getSortId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}
		if(erpMaterielSortService.isExistChild(erpMaterielSort.getSortId(), account.getOrgId())>0)
		{
			return RetDataTools.NotOk("当前分类下有子分类,若要删除,请先删除子分类!");
		}else
		{
		erpMaterielSort.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!", erpMaterielSortService.delErpMaterielSort(erpMaterielSort));
		}
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: updateErpMaterielSort   
 * @Description: TODO 更新物料分类
 * @param: request
 * @param: erpMaterielSort
 * @param: @return      
 * @return: RetDataBean      

 */
@RequestMapping(value="/updateErpMaterielSort",method=RequestMethod.POST)
public RetDataBean updateErpMaterielSort(HttpServletRequest request,ErpMaterielSort erpMaterielSort)
{
	try
	{
		if(StringUtils.isBlank(erpMaterielSort.getSortId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			if(StringUtils.isBlank(erpMaterielSort.getSortLeave()))
			{
				erpMaterielSort.setSortLeave("0");
			}
		erpMaterielSort.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		erpMaterielSort.setCreateUser(account.getAccountId());
		erpMaterielSort.setOrgId(account.getOrgId());
		Example example = new Example(ErpMaterielSort.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("sortId", erpMaterielSort.getSortId()).andEqualTo("orgId", account.getOrgId());
		return RetDataTools.Ok("更新成功!", erpMaterielSortService.updateErpMaterielSort(erpMaterielSort, example));
		}
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title: updateErpProductSort   
 * @Description: TODO 更新产品分类
 * @param request
 * @param erpProductSort
 * @return      
 * @return: RetDataBean      

 */
@RequestMapping(value="/updateErpProductSort",method=RequestMethod.POST)
public RetDataBean updateErpProductSort(HttpServletRequest request,ErpProductSort erpProductSort)
{
	try
	{
		if(StringUtils.isBlank(erpProductSort.getSortId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			if(StringUtils.isBlank(erpProductSort.getSortLeave()))
			{
				erpProductSort.setSortLeave("0");
			}
			erpProductSort.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			erpProductSort.setCreateUser(account.getAccountId());
			erpProductSort.setOrgId(account.getOrgId());
		Example example = new Example(ErpProductSort.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("sortId", erpProductSort.getSortId()).andEqualTo("orgId", account.getOrgId());
		return RetDataTools.Ok("更新成功!", erpProductSortService.updateErpProductSort(erpProductSort, example));
		}
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: insertErpProductSort   
 * @Description: TODO 创建产品分类
 * @param: request
 * @param: erpProductSort
 * @param: @return      
 * @return: RetDataBean      

 */
@RequestMapping(value="/insertErpProductSort",method=RequestMethod.POST)
public RetDataBean insertErpProductSort(HttpServletRequest request,ErpProductSort erpProductSort)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(erpProductSort.getSortLeave()))
		{
			erpProductSort.setSortLeave("0");
		}
		erpProductSort.setSortId(SysTools.getGUID());
		erpProductSort.setCreateUser(account.getAccountId());
		erpProductSort.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		erpProductSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("请求成功!", erpProductSortService.insert(erpProductSort));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: delErpProductSort   
 * @Description: TODO 删除产品分类 
 * @param: request
 * @param: erpProductSort
 * @param: @return      
 * @return: RetDataBean      

 */
@RequestMapping(value="/delErpProductSort",method=RequestMethod.POST)
public RetDataBean delErpProductSort(HttpServletRequest request,ErpProductSort erpProductSort)
{
	try
	{
		if(StringUtils.isBlank(erpProductSort.getSortId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}
		if(erpMaterielSortService.isExistChild(erpProductSort.getSortId(), account.getOrgId())>0)
		{
			return RetDataTools.NotOk("当前分类下有子分类,若要删除,请先删除子分类!");
		}else
		{
			if(StringUtils.isNotBlank(erpProductSort.getSortId()))
			{
				
				erpProductSort.setOrgId(account.getOrgId());
				return RetDataTools.Ok("删除成功!", erpProductSortService.delErpProductSort(erpProductSort));
			}else
			{
				return RetDataTools.NotOk("参数格式有问题!"); 
			}
		}
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title delErpProduct   
 * @Description TODO 删除产品
 * @param request
 * @param erpProduct
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/delErpProduct",method=RequestMethod.POST)
public RetDataBean delErpProduct(HttpServletRequest request,ErpProduct erpProduct)
{
	try
	{
		if(StringUtils.isBlank(erpProduct.getProductId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			if(StringUtils.isBlank(erpProduct.getProductId()))
			{
				return RetDataTools.NotOk("参数格式有问题!"); 
			}else
			{
				erpProduct.setOrgId(account.getOrgId());
				return RetDataTools.Ok("删除成功!", erpProductService.delErpProduct(erpProduct));
			}
		}
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: insertErpMateriel   
 * @Description: TODO 添加新物料
 * @param: request
 * @param: erpMateriel
 * @param: @return      
 * @return: RetDataBean      

 */
@RequestMapping(value="/insertErpMateriel",method=RequestMethod.POST)
public RetDataBean insertErpMateriel(HttpServletRequest request,ErpMateriel erpMateriel)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		erpMateriel.setMaterielId(SysTools.getGUID());
		erpMateriel.setCreateUser(account.getAccountId());
		erpMateriel.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		erpMateriel.setOrgId(account.getOrgId());
		return RetDataTools.Ok("物料添加成功!",erpMaterielService.insert(erpMateriel));
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title: delErpMateriel   
 * @Description: TODO 删除提定的物料  
 * @param: request
 * @param: erpMateriel
 * @param: @return      
 * @return: RetDataBean      

 */
@RequestMapping(value="/delErpMateriel",method=RequestMethod.POST)
public RetDataBean delErpMateriel(HttpServletRequest request,ErpMateriel erpMateriel)
{
	try
	{
		if(StringUtils.isBlank(erpMateriel.getMaterielId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			if(StringUtils.isNotBlank(erpMateriel.getMaterielId()))
			{
				erpMateriel.setOrgId(account.getOrgId());
				return RetDataTools.Ok("删除成功!", erpMaterielService.delete(erpMateriel));
			}else
			{
				return RetDataTools.NotOk("参数格式有问题!"); 
			}
		
		}
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateErpMateriel   
 * @Description: TODO 更新指定的物料信息 
 * @param: request
 * @param: erpMateriel
 * @param: @return      
 * @return: RetDataBean      

 */
@RequestMapping(value="/updateErpMateriel",method=RequestMethod.POST)
public RetDataBean updateErpMateriel(HttpServletRequest request,ErpMateriel erpMateriel)
{
	try
	{
		if(StringUtils.isBlank(erpMateriel.getMaterielId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		erpMateriel.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		erpMateriel.setCreateUser(account.getAccountId());
		erpMateriel.setOrgId(account.getOrgId());
		Example example = new Example(ErpMateriel.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("materielId", erpMateriel.getMaterielId()).andEqualTo("orgId", account.getOrgId());
		return RetDataTools.Ok("更新成功!", erpMaterielService.updateErpMateriel(erpMateriel, example));
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title insertErpBomSort   
 * @Description TODO 新建BOM分类
 * @param request
 * @param ErpBomSort
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/insertErpBomSort",method=RequestMethod.POST)
public RetDataBean insertErpBomSort(HttpServletRequest request,ErpBomSort ErpBomSort)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(ErpBomSort.getSortLeave()))
		{
			ErpBomSort.setSortLeave("0");
		}
		ErpBomSort.setSortId(SysTools.getGUID());
		ErpBomSort.setCreateUser(account.getAccountId());
		ErpBomSort.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		ErpBomSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("创建成功!", erpBomSortService.insertErpBomSort(ErpBomSort));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title updateErpBomSort   
 * @Description TODO 按条件更新BOM分类
 * @param request
 * @param erpBomSort
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/updateErpBomSort",method=RequestMethod.POST)
public RetDataBean updateErpBomSort(HttpServletRequest request,ErpBomSort erpBomSort)
{
	try
	{
		if(StringUtils.isBlank(erpBomSort.getSortId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			if(StringUtils.isBlank(erpBomSort.getSortLeave()))
			{
				erpBomSort.setSortLeave("0");
			}
			erpBomSort.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			erpBomSort.setCreateUser(account.getAccountId());
			erpBomSort.setOrgId(account.getOrgId());
		Example example = new Example(ErpBomSort.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("sortId", erpBomSort.getSortId()).andEqualTo("orgId", account.getOrgId());
		return RetDataTools.Ok("更新成功!", erpBomSortService.updateErpBomSort(erpBomSort, example));
		}
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title delErpBomSort   
 * @Description TODO 删除BOM分类  
 * @param request
 * @param erpBomSort
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/delErpBomSort",method=RequestMethod.POST)
public RetDataBean delErpBomSort(HttpServletRequest request,ErpBomSort erpBomSort)
{
	try
	{
		if(StringUtils.isBlank(erpBomSort.getSortId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}
		if(erpMaterielSortService.isExistChild(erpBomSort.getSortId(), account.getOrgId())>0)
		{
			return RetDataTools.NotOk("当前分类下有子分类,若要删除,请先删除子分类!");
		}else
		{
			if(StringUtils.isNotBlank(erpBomSort.getSortId()))
			{
				erpBomSort.setOrgId(account.getOrgId());
				return RetDataTools.Ok("删除成功!", erpBomSortService.delErpBomSort(erpBomSort));
			}else
			{
				return RetDataTools.NotOk("参数格式有问题!"); 
			}
			
		}
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title insertErpBom   
 * @Description TODO 创建BOM清单
 * @param request
 * @param erpBom
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/insertErpBom",method=RequestMethod.POST)
public RetDataBean insertErpBom(HttpServletRequest request,ErpBom erpBom)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		erpBom.setBomId(SysTools.getGUID());
		erpBom.setCreateUser(account.getAccountId());
		erpBom.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		erpBom.setOrgId(account.getOrgId());
		return RetDataTools.Ok("创建成功!", erpBomService.insertErpBom(erpBom));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title updateErpBom   
 * @Description TODO 更新BOM清单  
 * @param request
 * @param erpBom
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/updateErpBom",method=RequestMethod.POST)
public RetDataBean updateErpBom(HttpServletRequest request,ErpBom erpBom)
{
	try
	{
		if(StringUtils.isBlank(erpBom.getBomId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			erpBom.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			erpBom.setCreateUser(account.getAccountId());
			erpBom.setOrgId(account.getOrgId());
		Example example = new Example(ErpBom.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("bomId", erpBom.getBomId()).andEqualTo("orgId", account.getOrgId());
		return RetDataTools.Ok("更新成功!", erpBomService.updateErpBom(erpBom, example));
		}
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title delErpBom   
 * @Description TODO 按指定的条件删除BOM清单
 * @param request
 * @param erpBom
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/delErpBom",method=RequestMethod.POST)
public RetDataBean delErpBom(HttpServletRequest request,ErpBom erpBom)
{
	try
	{
		if(StringUtils.isBlank(erpBom.getBomId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}
		if(StringUtils.isNotBlank(erpBom.getBomId()))
		{
			erpBom.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!", erpBomService.delErpBom(erpBom));
		}else
		{
			return RetDataTools.NotOk("参数格式有问题!"); 
		}
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title insertErpBomDetail   
 * @Description TODO 添加BOM清单详情
 * @param request
 * @param erpBomDetail
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/insertErpBomDetail",method=RequestMethod.POST)
public RetDataBean insertErpBomDetail(HttpServletRequest request,ErpBomDetail erpBomDetail)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isNotBlank(erpBomDetail.getMaterielCode()))
		{
			if(erpBomDetailService.isExistMaterielCode(erpBomDetail.getBomId(), erpBomDetail.getMaterielCode(), account.getOrgId())>0)
			{
				return RetDataTools.NotOk("当前物料编码已存在,只需修改使用量即可!");	
			}
		}else if(StringUtils.isNotBlank(erpBomDetail.getChildBomId()))
		{
			if(erpBomDetailService.isExistChildBomIdCode(erpBomDetail.getBomId(), erpBomDetail.getChildBomId(), account.getOrgId())>0)
			{
				return RetDataTools.NotOk("当前子BOM已存在,只需修改使用量即可!");	
			}
		}
		
		if(StringUtils.isBlank(erpBomDetail.getChildBomId())&&StringUtils.isBlank(erpBomDetail.getMaterielCode()))
		{
			return RetDataTools.NotOk("当前添加的BOM子集参数有问题!");	
		}else
		{
		erpBomDetail.setBomDetailId(SysTools.getGUID());
		erpBomDetail.setCreateUser(account.getAccountId());
		erpBomDetail.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		erpBomDetail.setOrgId(account.getOrgId());
		return RetDataTools.Ok("添加BOM子集成功!", erpBomDetailService.insertErpBomDetail(erpBomDetail));
		}
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title deleteErpBomDetail   
 * @Description TODO 删除BOM清单详情中的物料   
 * @param request
 * @param erpBomDetail
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/deleteErpBomDetail",method=RequestMethod.POST)
public RetDataBean deleteErpBomDetail(HttpServletRequest request,ErpBomDetail erpBomDetail)
{
	try
	{
		if(StringUtils.isBlank(erpBomDetail.getBomDetailId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isNotBlank(erpBomDetail.getBomDetailId()))
		{
			erpBomDetail.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!", erpBomDetailService.deleteErpBomDetail(erpBomDetail));
		}else
		{
			return RetDataTools.NotOk("参数格式有问题!"); 
		}
		
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}


/**
 * 
 * @Title updateErpBomDetail   
 * @Description TODO 更新 ErpBomDetail
 * @param request
 * @param erpBomDetail
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/updateErpBomDetail",method=RequestMethod.POST)
public RetDataBean updateErpBomDetail(HttpServletRequest request,ErpBomDetail erpBomDetail)
{
	try
	{
		if(StringUtils.isBlank(erpBomDetail.getBomDetailId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			erpBomDetail.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			erpBomDetail.setCreateUser(account.getAccountId());
			erpBomDetail.setOrgId(account.getOrgId());
		Example example = new Example(ErpBomDetail.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("bomDetailId", erpBomDetail.getBomDetailId()).andEqualTo("bomId",erpBomDetail.getBomId()).andEqualTo("orgId", account.getOrgId());
		return RetDataTools.Ok("更新成功!", erpBomDetailService.updateErpBomDetail(erpBomDetail, example));
		}
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title insertErpProduct   
 * @Description TODO 添加新产品 
 * @param request
 * @param erpProduct
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/insertErpProduct",method=RequestMethod.POST)
public RetDataBean insertErpProduct(HttpServletRequest request,ErpProduct erpProduct)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		erpProduct.setProductId(SysTools.getGUID());
		erpProduct.setCreateUser(account.getAccountId());
		erpProduct.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		erpProduct.setOrgId(account.getOrgId());
		return RetDataTools.Ok("创建产品成功!", erpProductService.insertErpProduct(erpProduct));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title insertErpOrder   
 * @Description TODO 创建订单
 * @param request
 * @param erpOrder
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/insertErpOrder",method=RequestMethod.POST)
public RetDataBean insertErpOrder(HttpServletRequest request,ErpOrder erpOrder)
{
	try
	{
		if(StringUtils.isNotBlank(erpOrder.getOrderCode()))
		{
		Account account=accountService.getRedisAccount(request);
		erpOrder.setOrderId(SysTools.getGUID());
		erpOrder.setCreateUser(account.getAccountId());
		erpOrder.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		erpOrder.setOrgId(account.getOrgId());
		erpOrderService.insertErpOrder(erpOrder);
		return RetDataTools.Ok("创建订单成功!", "/app/core/erp/cost/orderproduct?orderId="+erpOrder.getOrderId());
		}else
		{
			return RetDataTools.NotOk("创建订单失败,订单编号不能为空!");
		}
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title updateErpOrder   
 * @Description TODO更新订单信息 
 * @param request
 * @param erpOrder
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/updateErpOrder",method=RequestMethod.POST)
public RetDataBean updateErpOrder(HttpServletRequest request,ErpOrder erpOrder)
{
	try
	{
		if(StringUtils.isBlank(erpOrder.getOrderId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		erpOrder.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		erpOrder.setCreateUser(account.getAccountId());
		erpOrder.setOrgId(account.getOrgId());
		Example example = new Example(ErpOrder.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("orderId", erpOrder.getOrderId()).andEqualTo("orgId", account.getOrgId());
		return RetDataTools.Ok("订单信息更新成功!", erpOrderService.updateErpOrder(erpOrder, example));
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}

@RequestMapping(value="/deleteErpOrder",method=RequestMethod.POST)
public RetDataBean deleteErpOrder(HttpServletRequest request,ErpOrder erpOrder)
{
	try
	{
		if(StringUtils.isBlank(erpOrder.getOrderId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			erpOrder.setCreateUser(account.getAccountId());
		}else
		{
			erpOrder.setOrgId(account.getOrgId());
		}
		if(StringUtils.isNotBlank(erpOrder.getOrderId()))
		{
			return RetDataTools.Ok("删除订单成功!", erpOrderService.deleteErpOrder(erpOrder));
		}else
		{
			return RetDataTools.NotOk("参数格式有问题!"); 
		}
		
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title Detail   
 * @Description TODO 添加订单产品
 * @param request
 * @param erpOrderDetail
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/insertErpOrderDetail",method=RequestMethod.POST)
public RetDataBean insertErpOrderDetail(HttpServletRequest request,ErpOrderDetail erpOrderDetail)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		erpOrderDetail.setDetailId(SysTools.getGUID());
		erpOrderDetail.setOrgId(account.getOrgId());
		return RetDataTools.Ok("添加产品成功!", erpOrderDetailService.insertErpOrderDetail(erpOrderDetail));
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title deleteErpOrderDetail   
 * @Description TODO 删除订单中的产品，若订单已核成本成本，则不能删除 
 * @param request
 * @param erpOrderDetail
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/deleteErpOrderDetail",method=RequestMethod.POST)
public RetDataBean deleteErpOrderDetail(HttpServletRequest request,ErpOrderDetail erpOrderDetail)
{
	try
	{
		if(StringUtils.isBlank(erpOrderDetail.getDetailId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isNotBlank(erpOrderDetail.getOrderId()))
		{
			erpOrderDetail.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除成功!", erpOrderDetailService.deleteErpOrderDetail(erpOrderDetail));
		}else
		{
			return RetDataTools.NotOk("参数格式有问题!"); 
		}
		
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title: updateErpProduct   
 * @Description: TODO 更新产品信息
 * @param request
 * @param erpProduct
 * @return      
 * @return: RetDataBean      

 */
@RequestMapping(value="/updateErpProduct",method=RequestMethod.POST)
public RetDataBean updateErpProduct(HttpServletRequest request,ErpProduct erpProduct)
{
	try
	{
		if(StringUtils.isBlank(erpProduct.getProductId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		erpProduct.setOrgId(account.getOrgId());
		Example example = new Example(ErpProduct.class);
		example.createCriteria().andEqualTo("productId",erpProduct.getProductId()).andEqualTo("orgId",account.getOrgId());
		return RetDataTools.Ok("更新产品成功!", erpProductService.updateErpProduct(erpProduct, example));
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title importmaterielsort   
 * @Description TODO 导入物料分类
 * @param request
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/importmaterielsort",method=RequestMethod.POST)
public RetDataBean importmaterielsort(HttpServletRequest request,@RequestParam MultipartFile file)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		return RetDataTools.Ok("导入物料分类成功!",erpMaterielSortService.importMaterielSort(file,account));
	}catch (Exception e) {
		e.printStackTrace();
			return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title insertErpEquipmentSort   
 * @Description TODO 创建设备分类
 * @param request
 * @param erpEquipmentSort
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/insertErpEquipmentSort",method=RequestMethod.POST)
public RetDataBean insertErpEquipmentSort(HttpServletRequest request,ErpEquipmentSort erpEquipmentSort)
{
	try
	{
		Account account=accountService.getRedisAccount(request);
		if(StringUtils.isBlank(erpEquipmentSort.getSortLeave()))
		{
			erpEquipmentSort.setSortLeave("0");
		}
		erpEquipmentSort.setSortId(SysTools.getGUID());
		erpEquipmentSort.setCreateUser(account.getAccountId());
		erpEquipmentSort.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
		erpEquipmentSort.setOrgId(account.getOrgId());
		return RetDataTools.Ok("创建成功!", erpEquipmentSortService.insertErpEquipmentSort(erpEquipmentSort));
	}catch (Exception e) {
		// TODO: handle exception
		return RetDataTools.Error(e.getMessage());
	}
}
/**
 * 
 * @Title updateErpEquipmentSort   
 * @Description TODO 更新设备分类
 * @param request
 * @param erpEquipmentSort
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/updateErpEquipmentSort",method=RequestMethod.POST)
public RetDataBean updateErpEquipmentSort(HttpServletRequest request,ErpEquipmentSort erpEquipmentSort)
{
	try
	{
		if(StringUtils.isBlank(erpEquipmentSort.getSortId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}else
		{
			if(StringUtils.isBlank(erpEquipmentSort.getSortLeave()))
			{
				erpEquipmentSort.setSortLeave("0");
			}
			erpEquipmentSort.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			erpEquipmentSort.setCreateUser(account.getAccountId());
			erpEquipmentSort.setOrgId(account.getOrgId());
		Example example = new Example(ErpEquipmentSort.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("sortId", erpEquipmentSort.getSortId()).andEqualTo("orgId", account.getOrgId());
		return RetDataTools.Ok("更新成功!", erpEquipmentSortService.updateErpEquipmentSort(erpEquipmentSort, example));
		}
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}

/**
 * 
 * @Title delErpEquipmentSort   
 * @Description TODO 删除设备分类  
 * @param request
 * @param erpEquipmentSort
 * @return      
 * RetDataBean
 */
@RequestMapping(value="/delErpEquipmentSort",method=RequestMethod.POST)
public RetDataBean delErpEquipmentSort(HttpServletRequest request,ErpEquipmentSort erpEquipmentSort)
{
	try
	{
		if(StringUtils.isBlank(erpEquipmentSort.getSortId()))
		{
			return RetDataTools.NotOk("请求参数有问题,请检查!");
		}
		Account account=accountService.getRedisAccount(request);
		if(!account.getOpFlag().equals("1"))
		{
			return RetDataTools.NotOk("对不起,您不是系统管理员!"); 
		}
		if(erpMaterielSortService.isExistChild(erpEquipmentSort.getSortId(), account.getOrgId())>0)
		{
			return RetDataTools.NotOk("当前分类下有子分类,若要删除,请先删除子分类!");
		}else
		{
			if(StringUtils.isNotBlank(erpEquipmentSort.getSortId()))
			{
				erpEquipmentSort.setOrgId(account.getOrgId());
				return RetDataTools.Ok("删除成功!",erpEquipmentSortService.deleteErpEquipmentSort(erpEquipmentSort));
			}else
			{
				return RetDataTools.NotOk("参数格式有问题!"); 
			}
			
		}
	}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
	}
}

}
