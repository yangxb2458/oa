package com.core136.mapper.oa;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.oa.Diary;
import org.core136.common.dbutils.MyMapper;

@Mapper
public interface DiaryMapper extends MyMapper<Diary>{
	/**
	 * 
	* @Title: getMyDiaryList 
	* @Description: TODO 获取当前用户历史工作日志
	* @param @param orgId
	* @param @param opFlag
	* @param @param accountId
	* @param @param search
	* @param @return 设定文件 
	* @return List<Map<String,Object>> 返回类型
	 */
	public List<Map<String,Object>> getMyDiaryList(
			@Param (value="orgId") String orgId,
			@Param (value="accountId") String accountId,
			@Param (value="search") String search,
			@Param (value="diaryDay") String diaryDay
			);

	
	/**
	 * 
	 * @Title: getMySubordinatesDiaryList   
	 * @Description: TODO 获取我下属的工作晶志列表   
	 * @param: orgId
	 * @param: list
	 * @param: beginTime
	 * @param: endTime
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getMySubordinatesDiaryList(@Param (value="orgId") String orgId,@Param (value="list") List<String> list,
			@Param(value="beginTime") String beginTime,@Param(value="endTime") String endTime,@Param (value="search") String search);

	
	/**
	 * 
	* @Title: getOtherDiaryList 
	* @Description: TODO 获取他人的工作日志
	* @param @param orgId
	* @param @param accountId
	* @param @param search
	* @param @param beginTime
	* @param @param endTime
	* @param @return 设定文件 
	* @return List<Map<String,Object>> 返回类型
	 */
	public List<Map<String,Object>> getOtherDiaryList(
			@Param (value="orgId") String orgId,
			@Param (value="accountId") String accountId,
			@Param (value="search") String search,
			@Param (value="beginTime") String beginTime,
			@Param (value="endTime") String endTime
			);
	/**
	 * 
	 * @Title: getMyDiaryCount   
	 * @Description: TODO 获取个人日志总数
	 * @param: orgId
	 * @param: accountId
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	public Integer getMyDiaryCount(@Param(value="orgId")String orgId,@Param(value="accountId") String accountId);
	
	/**
	 * 
	 * @Title: getDiaryCommentCount   
	 * @Description: TODO 被他人评论数
	 * @param: orgId
	 * @param: accountId
	 * @param: @return      
	 * @return: Integer      
	 * @throws
	 */
	public Integer getDiaryCommentCount(@Param(value="orgId")String orgId,@Param(value="accountId") String accountId);
	
	/**
	 * 
	 * @Title: getShowDiaryList   
	 * @Description: TODO 获取他人分享的工作日志
	 * @param: orgId
	 * @param: accountId
	 * @param: deptId
	 * @param: leaveId
	 * @param: beginTime
	 * @param: endTime
	 * @param: search
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getShowDiaryList(@Param(value="orgId")String orgId,@Param(value="accountId") String accountId,
			@Param(value="deptId") String deptId,@Param(value="leaveId")String leaveId,@Param(value="beginTime") String beginTime,
			@Param(value="endTime") String endTime,@Param(value="search") String search);
}
