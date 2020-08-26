package com.core136.mapper.notice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.notice.Notice;
import org.core136.common.dbutils.MyMapper;

@Mapper
public interface NoticeMapper extends MyMapper<Notice>{
	/**
	 * 
	* @Title: getNoticeManageList 
	* @Description: TODO 获取通知公告列表
	* @param @param orgId
	* @param @param opFlag
	* @param @param accountId
	* @param @param search
	* @param @return 设定文件 
	* @return List<Map<String,Object>> 返回类型
	 */
	public List<Map<String,Object>> getNoticeManageList(
			@Param (value="orgId") String orgId,
			@Param (value="opFlag") String opFlag,
			@Param (value="accountId") String accountId,
			@Param (value="search") String search,
			@Param (value="noticeType") String noticeType,
			@Param (value="beginTime") String beginTime,
			@Param (value="endTime") String endTime
			);

	/**
	 * 
	* @Title: getNoticeApproverList 
	* @Description: TODO 获取审批列表
	* @param @param orgId
	* @param @param accountId
	* @param @param search
	* @param @return 设定文件 
	* @return List<Map<String,Object>> 返回类型
	 */
	public List<Map<String,Object>> getNoticeApproverList(
			@Param (value="orgId") String orgId,
			@Param (value="accountId") String accountId,
			@Param (value="search") String search
			);
	/**
	 * 
	* @Title: setOnClickCount 
	* @Description: TODO 设置查看次数
	* @param @param orgId
	* @param @param noticeId
	* @param @return 设定文件 
	* @return int 返回类型
	 */
	public int setOnClickCount(@Param(value="orgId") String orgId,@Param(value="noticeId") String noticeId);
	
	/**
	 * 
	* @Title: isNoSendStatus 
	* @Description: TODO 判断是否有效
	* @param @param orgId
	* @param @param noticeId
	* @param @param endTime
	* @param @return 设定文件 
	* @return int 返回类型
	 */
	public int isNoSendStatus(@Param(value="orgId") String orgId,
			@Param(value="noticeId") String noticeId,
			@Param(value="endTime") String endTime);
	/**
	 * 
	* @Title: getMyNoticeList 
	* @Description: TODO 获取对应个人的通知公告
	* @param @param orgId
	* @param @param accountId
	* @param @param deptId
	* @param @param leaveId
	* @param @param noticeType
	* @param @param beginTime
	* @param @param endTime
	* @param @param search
	* @param @return 设定文件 
	* @return List<Map<String,String>> 返回类型
	 */
	public List<Map<String,String>> getMyNoticeList(
			@Param(value="orgId") String orgId,
			@Param(value="accountId") String accountId,
			@Param(value="deptId") String deptId,
			@Param(value="leaveId") String leaveId,
			@Param(value="noticeType") String noticeType,
			@Param(value="beginTime") String beginTime,
			@Param(value="endTime") String endTime,
			@Param(value="readStatus") String readStatus,
			@Param(value="search") String search
			);
	
	/**
	 * 
	 * @Title: getMyNoticeListForDesk   
	 * @Description: TODO 获取桌面的通知消息
	 * @param: orgId
	 * @param: endTime
	 * @param: accountId
	 * @param: deptId
	 * @param: leaveId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String,String>> getMyNoticeListForDesk(
			@Param (value="orgId") String orgId,
			@Param (value="endTime") String endTime,
			@Param (value="accountId") String accountId,
			@Param (value="deptId") String deptId,
			@Param (value="leaveId") String leaveId);		
	
/**
 * 	
 * @Title: getMobileMyNoticeList   
 * @Description: TODO 移动端下滑加载更多
 * @param: orgId
 * @param: accountId
 * @param: deptId
 * @param: leaveId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
	public List<Map<String, String>>getMobileMyNoticeList(
			@Param (value="orgId") String orgId,
			@Param (value="accountId") String accountId,
			@Param (value="deptId") String deptId,
			@Param (value="leaveId") String leaveId,
			@Param (value="page") Integer page);		
	
}
