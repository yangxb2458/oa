/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  SuperversionProcessService.java   
 * @Package com.core136.service.superversion   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月18日 上午9:18:42   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.superversion;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.superversion.SuperversionProcess;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.superversion.SuperversionProcessMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author lsq
 *
 */
@Service
public class SuperversionProcessService {
@Autowired
private SuperversionProcessMapper superversionProcessMapper;
public int insertSuperversionProcess(SuperversionProcess superversionProcess)
{
	return superversionProcessMapper.insert(superversionProcess);
}

public int deleteSuperversionProcess(SuperversionProcess superversionProcess)
{
	return superversionProcessMapper.delete(superversionProcess);
}

public int updateSuperversionProcess(Example example,SuperversionProcess superversionProcess)
{
	return superversionProcessMapper.updateByExampleSelective(superversionProcess, example);
}
public SuperversionProcess selectOneSuperversionProcess(SuperversionProcess superversionProcess)
{
	return superversionProcessMapper.selectOne(superversionProcess);
}

/**
 * 
 * @Title: getSuperversionProcess   
 * @Description: TODO 获取事件处理过程列表
 * @param: orgId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getMyCompleteProcessList(String orgId,String accountId,String beginTime,String endTime,String type,String search)
{
	return superversionProcessMapper.getMyCompleteProcessList(orgId,accountId,beginTime,endTime,type,"%"+search+"%");
}

/**
 * 
 * @Title: getControlProcessList   
 * @Description: TODO 获取我所管控的任务列表
 * @param: orgId
 * @param: accountId
 * @param: beginTime
 * @param: endTime
 * @param: type
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getControlProcessList(String orgId,String accountId,String beginTime,String endTime,String type,String search)
{
	return superversionProcessMapper.getControlProcessList(orgId,accountId,beginTime,endTime,type,"%"+search+"%");
}


/**
 * 
 * @Title: getSuperversionProcess   
 * @Description: TODO获取事件处理过程列表
 * @param: pageParam
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getMyCompleteProcessList(PageParam pageParam,String beginTime,String endTime,String type) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMyCompleteProcessList(pageParam.getOrgId(),pageParam.getAccountId(),beginTime,endTime,type,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getControlProcessList   
 * @Description: TODO 获取我所管控的任务列表
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: type
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getControlProcessList(PageParam pageParam,String beginTime,String endTime,String type) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getControlProcessList(pageParam.getOrgId(),pageParam.getAccountId(),beginTime,endTime,type,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
