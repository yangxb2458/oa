package com.core136.service.oa;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.oa.NewsComments;
import com.core136.mapper.oa.NewsCommentsMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class NewsCommentsService {
@Autowired
private NewsCommentsMapper newsCommentsMapper;

public int insertNewsComments(NewsComments newsComments)
{
	return newsCommentsMapper.insert(newsComments);
}
public int deleteNewsComments(NewsComments newsComments)
{
	return newsCommentsMapper.delete(newsComments);
}
public int updateNewsComments(Example example,NewsComments newsComments)
{
	return newsCommentsMapper.updateByExampleSelective(newsComments, example);
}
public NewsComments selectOneNewsComments(NewsComments newsComments)
{
	return newsCommentsMapper.selectOne(newsComments);
}
/**
 * 
 * @Title: getCommentsList   
 * @Description: TODO 获取新闻下的表有评论
 * @param: orgId
 * @param: newsId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getCommentsList(String orgId,String newsId)
{
	return newsCommentsMapper.getCommentsList(orgId, newsId);
}

}
