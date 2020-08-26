package com.core136.controller.dataupload;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.core136.common.retdataunit.RetDataBean;
import org.core136.common.retdataunit.RetDataTools;
import org.core136.common.utils.SysTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.dataupload.DataUploadHandle;
import com.core136.bean.dataupload.DataUploadInfo;
import com.core136.service.account.AccountService;
import com.core136.service.dataupload.DataUploadHandleService;
import com.core136.service.dataupload.DataUploadInfoService;

import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("/set/datauploadset")
public class RoutSetDataInfoController {
	@Autowired
	private DataUploadInfoService dataUploadInfoService;
	@Autowired
	private DataUploadHandleService dataUploadHandleService;
	@Autowired
	private AccountService accountService;
	/**
	 * 
	 * @Title: insertDataUploadHandle
	 * @Description: TODO 添加事件处理结果
	 * @param request
	 * @param dataUploadHandle
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertDataUploadHandle",method=RequestMethod.POST)
	public RetDataBean insertDataUploadHandle (HttpServletRequest request,DataUploadHandle dataUploadHandle)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			dataUploadHandle.setProcessId(SysTools.getGUID());
			dataUploadHandle.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			dataUploadHandle.setCreateUser(account.getAccountId());
			dataUploadHandle.setOrgId(account.getOrgId());
			return RetDataTools.Ok("添加信息处理结果成功!",dataUploadHandleService.processDataInfo(dataUploadHandle));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deletDataUploadHandle
	 * @Description: TODO 删除事件处理结果
	 * @param request
	 * @param dataUploadHandle
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteDataUploadHandle",method=RequestMethod.POST)
	public RetDataBean deletDataUploadHandle(HttpServletRequest request,DataUploadHandle dataUploadHandle)
	{
		try
		{
			if(StringUtils.isBlank(dataUploadHandle.getProcessId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			dataUploadHandle.setOrgId(account.getOrgId());
			return RetDataTools.Ok("处理结果删除成功!",dataUploadHandleService.deleteDataUploadHandle(dataUploadHandle));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateDataUploadHandle
	 * @Description: TODO 更新处理结果
	 * @param request
	 * @param dataUploadHandle
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateDataUploadHandle",method=RequestMethod.POST)
	public RetDataBean updateDataUploadHandle(HttpServletRequest request,DataUploadHandle dataUploadHandle)
	{
		try
		{
			if(StringUtils.isBlank(dataUploadHandle.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			Example example = new Example(DataUploadHandle.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("processId",dataUploadHandle.getProcessId());
			return RetDataTools.Ok("更新成功!",dataUploadHandleService.updateDataUploadHandle(example, dataUploadHandle));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: insertDataUploadInfo
	 * @Description: TODO 上报信息
	 * @param request
	 * @param dataUploadInfo
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/insertDataUploadInfo",method=RequestMethod.POST)
	public RetDataBean insertDataUploadInfo (HttpServletRequest request,DataUploadInfo dataUploadInfo)
	{
		try
		{
			Account account=accountService.getRedisAccount(request);
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			dataUploadInfo.setRecordId(SysTools.getGUID());
			dataUploadInfo.setStatus("0");
			dataUploadInfo.setCreateTime(SysTools.getTime("yyyy-MM-dd HH:mm:ss"));
			dataUploadInfo.setCreateUser(account.getAccountId());
			dataUploadInfo.setOrgId(account.getOrgId());
			return RetDataTools.Ok("信息上报成功!",dataUploadInfoService.dataUploadInfo(account,userInfo,dataUploadInfo));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: deleteDataUploadInfo
	 * @Description: TODO 删除上报信息
	 * @param request
	 * @param dataUploadInfo
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/deleteDataUploadInfo",method=RequestMethod.POST)
	public RetDataBean deleteDataUploadInfo(HttpServletRequest request,DataUploadInfo dataUploadInfo)
	{
		try
		{
			if(StringUtils.isBlank(dataUploadInfo.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			dataUploadInfo.setOrgId(account.getOrgId());
			return RetDataTools.Ok("上报信息删除成功!",dataUploadInfoService.deleteDataUploadInfo(dataUploadInfo));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @Title: updateDataUploadInfo
	 * @Description: TODO 更新上报信息
	 * @param request
	 * @param dataUploadInfo
	 * @return
	 * RetDataBean    
	 * @throws
	 */
	@RequestMapping(value="/updateDataUploadInfo",method=RequestMethod.POST)
	public RetDataBean updateDataUploadInfo(HttpServletRequest request,DataUploadInfo dataUploadInfo)
	{
		try
		{
			if(StringUtils.isBlank(dataUploadInfo.getRecordId()))
			{
				return RetDataTools.NotOk("请求参数有问题,请检查!");
			}
			Account account=accountService.getRedisAccount(request);
			UserInfo userInfo = accountService.getRedisUserInfo(request);
			Example example = new Example(DataUploadInfo.class);
			example.createCriteria().andEqualTo("orgId",account.getOrgId()).andEqualTo("recordId",dataUploadInfo.getRecordId());
			return RetDataTools.Ok("更新成功!",dataUploadInfoService.updateDataUploadInfo(account,userInfo,example, dataUploadInfo));
		}catch (Exception e) {
			return RetDataTools.Error(e.getMessage());
		}
	}
	
}
