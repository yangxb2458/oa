package com.core136.mapper.oa;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.oa.News;
import org.core136.common.dbutils.MyMapper;
@Mapper
public interface NewsMapper extends MyMapper<News>{
/**
 * 
 * @Title getNewsManageList   
 * @Description TODO 获取新闻维护列表
 * @param orgId
 * @param opFlag
 * @param accountId
 * @return      
 * List<Map<String,Object>>
 */
	public List<Map<String,Object>> getNewsManageList(@Param (value="orgId") String orgId,@Param (value="opFlag") String opFlag,@Param (value="accountId") String accountId,@Param (value="search") String search);
	
	/**
	 * 
	* @Title: getMyNewsList 
	* @Description: TODO 获取个人权限内的新闻 
	* @param @param orgId
	* @param @param accountId
	* @param @param deptId
	* @param @param levelId
	* @param @param newsType
	* @param @param status
	* @param @param beginTime
	* @param @param endTime
	* @param @param search
	* @param @return 设定文件 
	* @return List<Map<String,Object>> 返回类型 

	 */
	public List<Map<String,Object>> getMyNewsList(@Param (value="orgId") String orgId,
			@Param (value="accountId") String accountId,
			@Param (value="deptId") String deptId,
			@Param (value="levelId") String levelId,
			@Param (value="newsType") String newsType,
			@Param (value="status") String status,
			@Param (value="beginTime") String beginTime,
			@Param (value="endTime") String endTime,
			@Param (value="search") String search);
	
	/**
	 * 
	 * @Title: getMyNewsListForDesk   
	 * @Description: TODO 我的桌面上的新闻
	 * @param: orgId
	 * @param: accountId
	 * @param: deptId
	 * @param: levelId
	 * @param: @return      
	 * @return: List<Map<String,Object>>      
	 * @throws
	 */
	public List<Map<String,String>> getMyNewsListForDesk(
			@Param (value="orgId") String orgId,
			@Param (value="endTime") String endTime,
			@Param (value="accountId") String accountId,
			@Param (value="deptId") String deptId,
			@Param (value="levelId") String levelId);	
	
/**
 * 
* @Title: updateReadStatus 
* @Description: TODO 更新新闻点击次数
* @param @param orgId
* @param @param accountId
* @param @param deptId
* @param @param levelId
* @param @param newsId
* @param @return 设定文件 
* @return int 返回类型 

 */
	public int updateReadStatus(@Param (value="orgId") String orgId,
			@Param (value="accountId") String accountId,
			@Param (value="deptId") String deptId,
			@Param (value="levelId") String levelId,
			@Param (value="newsId") String newsId,
			@Param (value="reader") String reader);
	/**
	 * 
	* @Title: getReadNewsById 
	* @Description: TODO 获取阅读新闻详情
	* @param @param orgId
	* @param @param newsId
	* @param @return 设定文件 
	* @return Map<String,Object> 返回类型 

	 */
	public Map<String,Object>getReadNewsById(@Param (value="orgId") String orgId,@Param(value="newsId") String newsId);
	
	/**
	 * 	
	 * @Title: getMobileMyNewsList   
	 * @Description: TODO 获取移动端新闻列表
	 * @param: orgId
	 * @param: accountId
	 * @param: deptId
	 * @param: levelId
	 * @param: page
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getMobileMyNewsList(@Param (value="orgId") String orgId,
			@Param (value="accountId") String accountId,
			@Param (value="deptId") String deptId,
			@Param (value="levelId") String levelId,
			@Param (value="page") Integer page);
	
}
