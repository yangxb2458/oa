package com.core136.controller.mobile;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.core136.common.dingding.DDutils;
import org.core136.common.dingding.entity.DUserInfo;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.sys.DdConfig;
import com.core136.service.account.AccountService;
import com.core136.service.bpm.BpmFlowService;
import com.core136.service.bpm.BpmRunProcessService;
import com.core136.service.dingding.DingDingLoginService;
import com.core136.service.notice.NoticeService;
import com.core136.service.oa.NewsService;
import com.core136.service.sys.DdConfigService;

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
@RequestMapping("/dingding/dingdingget")
public class RoutGetDingdingController {
@Autowired
private DingDingLoginService dingDingLoginService;
@Autowired
private DdConfigService ddConfigService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title: getUserInfo   
 * @Description: TODO 获取用户信息 
 * @param: request
 * @param: code
 * @param: unit
 * @param: @return      
 * @return: RetDataBean      
 * @throws
 */
	@RequestMapping(value="/getUserInfo",method=RequestMethod.GET)
	public RetDataBean getUserInfo(HttpServletRequest request,String code,String orgId)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(account!=null)
			{
				if(StringUtils.isNotBlank(account.getAccountId()))
					{
						return RetDataTools.Ok("请求成功!");
					}else
					{
						DdConfig ddConfig = new DdConfig();
						ddConfig.setOrgId(orgId);
						ddConfig = ddConfigService.selectOneDdConfig(ddConfig);
						DUserInfo dUserInfo = DDutils.getUserInfo(code,ddConfig.getDingdingAppKey(),ddConfig.getDingdingAppSecret());
						dingDingLoginService.getDUserinfo(request, dUserInfo.getUserId(),orgId);
						return RetDataTools.Ok("请求成功!");
					}
			}else
			{
			DdConfig ddConfig = new DdConfig();
			ddConfig.setOrgId(orgId);
			ddConfig = ddConfigService.selectOneDdConfig(ddConfig);
			DUserInfo dUserInfo = DDutils.getUserInfo(code,ddConfig.getDingdingAppKey(),ddConfig.getDingdingAppSecret());
			dingDingLoginService.getDUserinfo(request, dUserInfo.getUserId(),orgId);
			return RetDataTools.Ok("请求成功!");
			}
		}catch (Exception e)
		{
			return RetDataTools.Error(e.getMessage());
		}
	}
	
}
