package com.core136.mapper.oa;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.oa.NewsComments;
import org.core136.common.dbutils.MyMapper;

@Mapper
public interface NewsCommentsMapper extends MyMapper<NewsComments>{

	/**
	 * 
	 * @Title: getCommentsList   
	 * @Description: TODO 获取新闻下的所有评论
	 * @param: orgId
	 * @param: newsId
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getCommentsList(@Param(value="orgId") String orgId,@Param(value="newsId") String newsId);
	
}
