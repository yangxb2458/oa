package com.core136.service.oa;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.oa.DiaryComments;
import com.core136.mapper.oa.DiaryCommentsMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class DiaryCommentsService {
@Autowired
private DiaryCommentsMapper diaryCommentsMapper;

public int insertDiaryComments(DiaryComments diaryComments)
{
	return diaryCommentsMapper.insert(diaryComments);
}

public int deleteDiaryComments(DiaryComments diaryComments)
{
	return diaryCommentsMapper.delete(diaryComments);
}
public DiaryComments selectOneDiaryComments(DiaryComments diaryComments)
{
	return diaryCommentsMapper.selectOne(diaryComments);
}
public int updateDiaryComments(Example example,DiaryComments diaryComments)
{
	return diaryCommentsMapper.updateByExampleSelective(diaryComments, example);
}
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
public List<Map<String, String>>getDiaryCommentsList(String orgId,String diaryId)
{
	return diaryCommentsMapper.getDiaryCommentsList(orgId, diaryId);
}
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
public Integer getMyDiaryCommentsCount(String orgId,String accountId)
{
	return diaryCommentsMapper.getMyDiaryCommentsCount(orgId, accountId);
}

}
