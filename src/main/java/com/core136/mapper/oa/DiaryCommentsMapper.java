package com.core136.mapper.oa;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.oa.DiaryComments;

@Mapper
public interface DiaryCommentsMapper extends MyMapper<DiaryComments>{

	/**
	 * 
	 * @Title: getDiaryCommentsList   
	 * @Description: TODO 获取评论列表
	 * @param: orgId
	 * @param: diaryId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getDiaryCommentsList(@Param(value="orgId") String orgId,@Param(value="diaryId") String diaryId);
	
	/**
	 * 
	 * @Title: getMyDiaryCommentsCount   
	 * @Description: TODO 获取评论数
	 * @param: orgId
	 * @param: accountId
	 * @param: @return      
	 * @return: Integer      
	 * @throws
	 */
	public Integer getMyDiaryCommentsCount(@Param(value="orgId") String orgId,@Param(value="accountId")String accountId);
	
}
