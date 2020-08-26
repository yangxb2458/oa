package com.core136.controller.erp;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.core136.bean.account.Account;
import com.core136.bean.erp.ErpBom;
import com.core136.bean.erp.ErpOrder;
import com.core136.service.account.AccountService;
import com.core136.service.erp.ErpBomService;
import com.core136.service.erp.ErpOrderService;
import org.core136.common.utils.SysTools;

@Controller
@RequestMapping("/app/core/erp")
public class ErpPageController {
@Autowired
private ErpBomService erpBomService;
@Autowired
private ErpOrderService erpOrderService;
@Autowired
private AccountService accountService;
	/**
	 * 
	 * @Title goBommange   
	 * @Description TODO 跳转物料分类管理页面   
	 * @return      
	 * ModelAndView
	 */
	@RequestMapping("/cost/materielsort")
	public ModelAndView  goCostBaseSortSet()
	{
		try
		{
		ModelAndView mv = new ModelAndView("app/core/erp/cost/baseset/materielsort");
		return mv;
		}catch (Exception e) {
		// TODO: handle exception
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
	}
	
	/**
	 * 
	 * @Title goBommange   
	 * @Description TODO 跳转产品分类管理页面   
	 * @return      
	 * ModelAndView
	 */		
		@RequestMapping("/cost/productsort")
		public ModelAndView  goCostProductSortSet()
		{
			try
			{
			ModelAndView mv = new ModelAndView("app/core/erp/cost/baseset/productsort");
			return mv;
			}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
		
		/**
		 * 
		 * @Title goBommange   
		 * @Description TODO 跳转物料管理页面   
		 * @return      
		 * ModelAndView
		 */
		@RequestMapping("/cost/materielmagage")
		public ModelAndView  goMaterielMagage()
		{
			try
			{
			ModelAndView mv = new ModelAndView("app/core/erp/cost/baseset/materielmanage");
			return mv;
			}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
		/**
		 * 
		 * @Title goBommange   
		 * @Description TODO 跳转Bom管理页面   
		 * @return      
		 * ModelAndView
		 */
		@RequestMapping("/cost/bommange")
		public ModelAndView  goBommange()
		{
			try
			{
			ModelAndView mv = new ModelAndView("app/core/erp/cost/bom/bommanage");
			return mv;
			}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
	}
		/**
		 * 
		 * @Title goBomsort   
		 * @Description TODO 跳转Bom分类管理页面  
		 * @return      
		 * ModelAndView
		 */
		@RequestMapping("/cost/bomsort")
		public ModelAndView  goBomsort()
		{
			try
			{
			ModelAndView mv = new ModelAndView("app/core/erp/cost/bom/bomsort");
			return mv;
			}catch (Exception e) {
			// TODO: handle exception
			ModelAndView mv = new ModelAndView("titps");
			return mv;
		}
		}
			/**
			 * 
			 * @Title goBomdetail   
			 * @Description TODO 路转BOM详情页面
			 * @param bomId
			 * @return      
			 * ModelAndView
			 */
			@RequestMapping("/cost/bomdetail")
			public ModelAndView  goBomdetail(HttpServletRequest request,ErpBom erpBom)
			{
				try
				{
				Account account=accountService.getRedisAccount(request);
				erpBom.setOrgId(account.getOrgId());
				ModelAndView mv = new ModelAndView("app/core/erp/cost/bom/bomdetail");
				mv.addObject("erpBom",erpBomService.selectOne(erpBom));
				return mv;
				}catch (Exception e) {
				// TODO: handle exception
				ModelAndView mv = new ModelAndView("titps");
				return mv;
			}
	}
			
			/**
			 * 
			 * @Title goPmanage   
			 * @Description TODO 产品管理 
			 * @return      
			 * ModelAndView
			 */
			@RequestMapping("/cost/productmanage")
			public ModelAndView  goPmanage()
			{
				try
				{
				ModelAndView mv = new ModelAndView("app/core/erp/cost/baseset/pmanage");
				return mv;
				}catch (Exception e) {
				// TODO: handle exception
				ModelAndView mv = new ModelAndView("titps");
				return mv;
			}
			}
			
			/**
			 * 
			 * @Title goOrder   
			 * @Description TODO 跳转订单管理
			 * @return      
			 * ModelAndView
			 */
			@RequestMapping("/cost/order")
			public ModelAndView  goOrder(HttpServletRequest request,ErpOrder erpOrder)
			{
				String orderCode="";
				ErpOrder erpOrder1 = new ErpOrder();
				try
				{
				Account account=accountService.getRedisAccount(request);
				ModelAndView mv = new ModelAndView("app/core/erp/cost/order/index");
				if(StringUtils.isNotEmpty(erpOrder.getOrderId()))
				{
					erpOrder.setOrgId(account.getOrgId());
					erpOrder.setCreateUser(account.getAccountId());
					erpOrder1= erpOrderService.selectOne(erpOrder);
				}else
				{
					orderCode = SysTools.getCode(accountService.getRedisAccount(request),"[yyyy][MM][dd]-[HH][mm][ss]-[R]"); 
					erpOrder1.setOrderCode(orderCode);
				}
				mv.addObject("erpOrder",erpOrder1);
				return mv;
				}catch (Exception e) {
				// TODO: handle exception
				ModelAndView mv = new ModelAndView("titps");
				return mv;
			}
			}
			/**
			 * 
			 * @Title goOrdermanage   
			 * @Description TODO 订单管理   
			 * @param request
			 * @param erpOrder
			 * @return      
			 * ModelAndView
			 */
			@RequestMapping("/cost/ordermanage")
			public ModelAndView  goOrdermanage(HttpServletRequest request,ErpOrder erpOrder)
			{
				try
				{
				ModelAndView mv = new ModelAndView("app/core/erp/cost/order/ordermanage");
				return mv;
				}catch (Exception e) {
				ModelAndView mv = new ModelAndView("titps");
				return mv;
			}
			}
			
			/**
			 * 
			 * @Title goOrderproduct   
			 * @Description TODO 为订单产品管理   
			 * @param request
			 * @param erpOrder
			 * @return      
			 * ModelAndView
			 */
			@RequestMapping("/cost/orderproduct")
			public ModelAndView  goOrderproduct(HttpServletRequest request,ErpOrder erpOrder)
			{
				try
				{
				Account account=accountService.getRedisAccount(request);
				erpOrder.setOrgId(account.getOrgId());
				erpOrder = erpOrderService.selectOne(erpOrder);
				ModelAndView mv = new ModelAndView("app/core/erp/cost/order/orderproduct");
				mv.addObject("erpOrder",erpOrder);
				return mv;
				}catch (Exception e) {
				ModelAndView mv = new ModelAndView("titps");
				return mv;
			}
			}
			/**
			 * 
			 * @Title goOrder   
			 * @Description TODO 跳转订单管理
			 * @return      
			 * ModelAndView
			 */
			@RequestMapping("/cost/docost")
			public ModelAndView  goDocost()
			{
				try
				{
				ModelAndView mv = new ModelAndView("app/core/erp/cost/order/docost");
				return mv;
				}catch (Exception e) {
				// TODO: handle exception
				ModelAndView mv = new ModelAndView("titps");
				return mv;
			}
			}
			
			@RequestMapping("/cost/costinfo")
			public ModelAndView  costinfo(HttpServletRequest request,String orderId)
			{
				try
				{
				ModelAndView mv = new ModelAndView("app/core/erp/cost/order/costinfo");
				mv.addObject("orderId",orderId);
				return mv;
				}catch (Exception e) {
				// TODO: handle exception
				ModelAndView mv = new ModelAndView("titps");
				return mv;
			}
			}
			
			
			/**
			 * 
			 * @Title goPircinfo   
			 * @Description TODO 跳转成本组成页面
			 * @param orderId
			 * @return      
			 * ModelAndView
			 */
			@RequestMapping("/cost/pirceinfo")
			public ModelAndView  goPirceinfo(String productId)
			{
				try
				{
				ModelAndView mv = new ModelAndView("app/core/erp/cost/order/pirceinfo");
				mv.addObject("productId",productId);
				return mv;
				}catch (Exception e) {
				// TODO: handle exception
				ModelAndView mv = new ModelAndView("titps");
				return mv;
			}
			}
			
			/**
			 * 
			 * @Title goBompirce   
			 * @Description TODO 总成成本详情
			 * @param bomId
			 * @return      
			 * ModelAndView
			 */
			@RequestMapping("/cost/bompirce")
			public ModelAndView  goBompirce(String bomId)
			{
				try
				{
				ModelAndView mv = new ModelAndView("app/core/erp/cost/order/bompirce");
				mv.addObject("bomId",bomId);
				return mv;
				}catch (Exception e) {
				// TODO: handle exception
				ModelAndView mv = new ModelAndView("titps");
				return mv;
			}
			}
/**
 * 		
 * @Title goEquipmentsort   
 * @Description TODO 设备分类
 * @param request
 * @return      
 * ModelAndView
 */
			
			@RequestMapping("/cost/equipmentsort")
			public ModelAndView  goEquipmentsort(HttpServletRequest request)
			{
				try
				{
				ModelAndView mv = new ModelAndView("app/core/erp/cost/equipment/sort");
				return mv;
				}catch (Exception e) {
				// TODO: handle exception
				ModelAndView mv = new ModelAndView("titps");
				return mv;
			}
			}
/**
 * 			
 * @Title goEquipment   
 * @Description TODO 设备管理 
 * @param request
 * @return      
 * ModelAndView
 */
			@RequestMapping("/cost/equipment")
			public ModelAndView  goEquipment(HttpServletRequest request)
			{
				try
				{
				ModelAndView mv = new ModelAndView("app/core/erp/cost/equipment/manage");
				return mv;
				}catch (Exception e) {
				// TODO: handle exception
				ModelAndView mv = new ModelAndView("titps");
				return mv;
			}
			}

/**
 * 
 * @Title goErpbi   
 * @Description TODO Erp图形报表
 * @param request
 * @return      
 * ModelAndView
 */
@RequestMapping("/cost/erpbi")
public ModelAndView  goErpbi(HttpServletRequest request)
	{
	try
	{
		ModelAndView mv = new ModelAndView("app/core/erp/cost/query/erpbi");
		return mv;
	}catch (Exception e) {
		ModelAndView mv = new ModelAndView("titps");
		return mv;
	}
			}
}
