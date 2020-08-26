/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RoutSetSuperversionController.java   
 * @Package com.core136.controller.superversion   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月14日 下午1:18:27   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.controller.superversion;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.superversion.Superversion;
import com.core136.bean.superversion.SuperversionConfig;
import com.core136.bean.superversion.SuperversionDelay;
import com.core136.bean.superversion.SuperversionProcess;
import com.core136.bean.superversion.SuperversionScore;
import com.core136.service.account.AccountService;
import com.core136.service.superversion.SuperversionConfigService;
import com.core136.service.superversion.SuperversionDelayService;
import com.core136.service.superversion.SuperversionProcessService;
import com.core136.service.superversion.SuperversionScoreService;
import com.core136.service.superversion.SuperversionService;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@RestController
@RequestMapping("/set/superversionset")
public class RoutSetSuperversionController {
	@Autowired
	private SuperversionService superversionService;
	@Autowired
	private SuperversionConfigService superversionConfigService;
	@Autowired
	private SuperversionDelayService superversionDelayService;
	@Autowired
	private SuperversionProcessService superversionProcessService;
	@Autowired
	private SuperversionScoreService superversionScoreService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: insertSuperversionScore   
	 * @Description: TODO 添加
	 * @param request
	 * @param superversionScore
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertSuperversionScore",method=RequestMethod.POST)
	public RetDataBean insertSuperversionScore(HttpServletRequest request,SuperversionScore superversionScore)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			superversionScore.setScoreId(SysTools.getGUID());
			superversionScore.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			superversionScore.setCreateUser(account.getAccountId());
			superversionScore.setOrgId(account.getOrgId());
			return RetDataTools.Ok("评分成功!",superversionScoreService.insertSuperversionScore(superversionScore));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	@RequestMapping(value="/deleteSuperversionScore",method=RequestMethod.POST)
	public RetDataBean deleteSuperversionScore(HttpServletRequest request,SuperversionScore superversionScore)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(superversionScore.getScoreId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			superversionScore.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除评分成功!",superversionScoreService.deleteSuperversionScore(superversionScore));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	@RequestMapping(value="/updateSuperversionScore",method=RequestMethod.POST)
	public RetDataBean updateSuperversionScore(HttpServletRequest request,SuperversionScore superversionScore)
	{
		try
		{
			if(StringUtils.isBlank(superversionScore.getScoreId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(SuperversionScore.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("scoreId",superversionScore.getProcessId());
			return RetDataTools.Ok("更新评分成功!",superversionScoreService.updateSuperversionScore(example,superversionScore));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	@RequestMapping(value="/insertSuperversionProcess",method=RequestMethod.POST)
	public RetDataBean insertSuperversionProcess(HttpServletRequest request,SuperversionProcess superversionProcess)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			superversionProcess.setProcessId(SysTools.getGUID());
			superversionProcess.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			superversionProcess.setCreateUser(account.getAccountId());
			superversionProcess.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加处理过程成功!",superversionProcessService.insertSuperversionProcess(superversionProcess));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	@RequestMapping(value="/deleteSuperversionProcess",method=RequestMethod.POST)
	public RetDataBean deleteSuperversionProcess(HttpServletRequest request,SuperversionProcess superversionProcess)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(superversionProcess.getProcessId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			superversionProcess.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除处理过程成功!",superversionProcessService.deleteSuperversionProcess(superversionProcess));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	@RequestMapping(value="/updateSuperversionProcess",method=RequestMethod.POST)
	public RetDataBean updateSuperversionProcess(HttpServletRequest request,SuperversionProcess superversionProcess)
	{
		try
		{
			if(StringUtils.isBlank(superversionProcess.getProcessId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(SuperversionProcess.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("processId",superversionProcess.getProcessId());
			return RetDataTools.Ok("更新延期成功!",superversionProcessService.updateSuperversionProcess(example,superversionProcess));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: insertSuperversionDelay   
	 * @Description: TODO 发起延期审批申请   
	 * @param: request
	 * @param: superversionDelay
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertSuperversionDelay",method=RequestMethod.POST)
	public RetDataBean insertSuperversionDelay(HttpServletRequest request,SuperversionDelay superversionDelay)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			Superversion superversion = new Superversion();
			superversion.setOrgId(account.getOrgId());
			superversion.setSuperversionId(superversionDelay.getSuperversionId());
			superversion = superversionService.selectOneSuperversion(superversion);
			superversionDelay.setDelayId(SysTools.getGUID());
			superversionDelay.setLeadId(superversion.getLeadId());
			superversionDelay.setPassStatus("0");
			superversionDelay.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			superversionDelay.setCreateUser(account.getAccountId());
			superversionDelay.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加延期成功!",superversionDelayService.insertSuperversionDelay(superversionDelay));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	@RequestMapping(value="/deleteSuperversionDelay",method=RequestMethod.POST)
	public RetDataBean deleteSuperversionDelay(HttpServletRequest request,SuperversionDelay superversionDelay)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(superversionDelay.getDelayId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			superversionDelay.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除延期成功!",superversionDelayService.deleteSuperversionDelay(superversionDelay));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	@RequestMapping(value="/updateSuperversionDelay",method=RequestMethod.POST)
	public RetDataBean updateSuperversionDelay(HttpServletRequest request,SuperversionDelay superversionDelay)
	{
		try
		{
			if(StringUtils.isBlank(superversionDelay.getDelayId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			
			if(superversionDelay.getPassStatus().equals("1"))
			{
				SuperversionDelay superversionDelay2 = new SuperversionDelay();
				superversionDelay2.setOrgId(account.getOrgId());
				superversionDelay2.setDelayId(superversionDelay.getDelayId());
				superversionDelay2 = superversionDelayService.selectOneSuperversionDelay(superversionDelay2);
				Superversion superversion = new Superversion();
				superversion.setOrgId(account.getOrgId());
				superversion.setSuperversionId(superversionDelay2.getSuperversionId());
				superversion.setEndTime(superversionDelay2.getDelayTime());
				superversion.setStatus("1");
				Example example1 = new Example(Superversion.class);
				example1.createCriteria().andEqualTo("orgId",superversion.getOrgId()).andEqualTo("superversionId",superversion.getSuperversionId());
				superversionService.updateSuperversion(example1, superversion);
			}
			superversionDelay.setApplyTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			Example example = new Example(SuperversionDelay.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("delayId",superversionDelay.getDelayId());
			return RetDataTools.Ok("审批成功!",superversionDelayService.updateSuperversionDelay(example,superversionDelay));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertProjectBuildDetails   
	 * @Description: TODO 创建类型
	 * @param: request
	 * @param: superversionConfig
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertSuperversionConfig",method=RequestMethod.POST)
	public RetDataBean insertSuperversionConfig(HttpServletRequest request,SuperversionConfig superversionConfig)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			superversionConfig.setConfigId(SysTools.getGUID());
			superversionConfig.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			superversionConfig.setCreateUser(account.getAccountId());
			superversionConfig.setOrgId(account.getOrgId());
			return RetDataTools.Ok("创建类型成功!",superversionConfigService.insertSuperversionConfig(superversionConfig));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteSuperversionConfig   
	 * @Description: TODO 删除类型
	 * @param: request
	 * @param: superversionConfig
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteSuperversionConfig",method=RequestMethod.POST)
	public RetDataBean deleteSuperversionConfig(HttpServletRequest request,SuperversionConfig superversionConfig)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			if(StringUtils.isBlank(superversionConfig.getConfigId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			superversionConfig.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除类型成功!",superversionConfigService.deleteSuperversionConfig(superversionConfig));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateSuperversionConfig   
	 * @Description: TODO 更新分类
	 * @param: request
	 * @param: superversionConfig
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateSuperversionConfig",method=RequestMethod.POST)
	public RetDataBean updateSuperversionConfig(HttpServletRequest request,SuperversionConfig superversionConfig)
	{
		try
		{
			if(StringUtils.isBlank(superversionConfig.getConfigId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(SuperversionConfig.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("configId",superversionConfig.getConfigId());
			return RetDataTools.Ok("更新成功!",superversionConfigService.updateSuperversionConfig(example,superversionConfig));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @Title: insertSuperversion   
	 * @Description: TODO 创建督查项目
	 * @param: request
	 * @param: superversion
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/insertSuperversion",method=RequestMethod.POST)
	public RetDataBean insertSuperversion(HttpServletRequest request,Superversion superversion)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			superversion.setSuperversionId(SysTools.getGUID());
			superversion.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			superversion.setStatus("0");
			superversion.setCreateUser(account.getAccountId());
			superversion.setOrgId(account.getOrgId());
			return RetDataTools.Ok("创建督查项目成功!",superversionService.createSuperversion(account,userInfo,superversion));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteSuperversion   
	 * @Description: TODO 删除督查项目
	 * @param: request
	 * @param: superversion
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/deleteSuperversion",method=RequestMethod.POST)
	public RetDataBean deleteSuperversion(HttpServletRequest request,Superversion superversion)
	{
		try
		{
			if(StringUtils.isBlank(superversion.getSuperversionId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			superversion.setOrgId(account.getOrgId());
			return RetDataTools.Ok("删除督查项目成功!",superversionService.deleteSuperversion(superversion));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: updateSuperversion   
	 * @Description: TODO 更新督查项目
	 * @param: request
	 * @param: superversion
	 * @param: @return      
	 * @return: RetDataBean      
	 * @throws
	 */
	@RequestMapping(value="/updateSuperversion",method=RequestMethod.POST)
	public RetDataBean updateSuperversion(HttpServletRequest request,Superversion superversion)
	{
		try
		{
			if(StringUtils.isBlank(superversion.getSuperversionId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			Example example = new Example(Superversion.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("superversionId",superversion.getSuperversionId());
			return RetDataTools.Ok("更新成功!",superversionService.updateSuperversion(account,userInfo,example,superversion));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
}
