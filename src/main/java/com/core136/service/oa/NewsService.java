package com.core136.service.oa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.core136.common.enums.GobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.oa.News;
import com.core136.bean.sys.MsgBody;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.oa.NewsMapper;
import com.core136.service.account.AccountService;
import com.core136.service.sys.MessageUnitService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
@Service
public class NewsService{
@Autowired
private NewsMapper newsMapper;
@Autowired
private MessageUnitService messageUnitService;
@Autowired
private AccountService accountService;
/**
 * 
 * @Title insertNews   
 * @Description TODO 发布
 * @param news
 * @return      
 * int
 */
public int insertNews(News news)
{
	return newsMapper.insert(news);
}
/**
 * 
 * @Title deleteNews   
 * @Description TODO 删除
 * @param news
 * @return      
 * int
 */
public int deleteNews(News news)
{
	return newsMapper.delete(news);
}
/**
 * 
 * @Title updateNews   
 * @Description TODO 更新
 * @param news
 * @param example
 * @return      
 * int
 */
public int updateNews(News news,Example example)
{
	return newsMapper.updateByExampleSelective(news, example);
}
/**
 * 
 * @Title selectOne   
 * @Description TODO 获取新闻详情
 * @param news
 * @return      
 * News
 */
public News selectOneNews(News news)
{
	return newsMapper.selectOne(news);
}

/**
 * 
* @Title: selectNewsList 
* @Description: TODO 按条件获取新闻列表
* @param @param example
* @param @return 设定文件 
* @return List<News> 返回类型
 */
public List<News> selectNewsList(Example example)
{
	return newsMapper.selectByExample(example);
}
/**
 * 
 * @Title: sendNews   
 * @Description: TODO 发布新闻
 * @param: news
 * @param: @return      
 * @return: int      
 * @throws
 */
public int sendNews(News news,UserInfo userInfo)
{
	int flag = insertNews(news);
	if(flag>0)
	{
		if(StringUtils.isNotBlank(news.getMsgType()))
		{
			List<MsgBody> msgBodyList = new ArrayList<MsgBody>();
			List<Account> accountList = accountService.getAccountInPriv(news.getOrgId(), news.getUserPriv(), news.getDeptPriv(), news.getLevelPriv());
			for(int i=0;i<accountList.size();i++)
			{
				MsgBody msgBody = new MsgBody();
				msgBody.setFromAccountId(news.getCreateUser());
				msgBody.setFormUserName(userInfo.getUserName());
				msgBody.setTitle(news.getNewsTitle());
				msgBody.setContent(news.getSubheading());
				msgBody.setSendTime(news.getSendTime());
				msgBody.setAccount(accountList.get(i));
				msgBody.setRedirectUrl("/mobile/news/details?newsId="+news.getNewsId());
				msgBody.setAttach(news.getAttach());
				msgBody.setOrgId(news.getOrgId());
				msgBodyList.add(msgBody);
			}
			messageUnitService.sendMessage(news.getMsgType(), GobalConstant.MSG_TYPE_NEWS, msgBodyList);
		}
	}
	return flag;
}

/**
 * 获取新闻维护列表
 */

public List<Map<String, Object>> getNewsManageList(String orgId, String opFlag, String accountId,String search) {
	return newsMapper.getNewsManageList(orgId, opFlag, accountId,"%"+search+"%");
}


public PageInfo<Map<String, Object>> getNewsManageList(PageParam pageParam) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,Object>> datalist= newsMapper.getNewsManageList(pageParam.getOrgId(), pageParam.getOpFlag(), pageParam.getAccountId(),pageParam.getSearch());
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: getMyNewsList   
 * @Description: TODO 获取个人权限内的新闻  
 * @param: pageParam
 * @param: deptId
 * @param: levelId
 * @param: newsType
 * @param: status
 * @param: beginTime
 * @param: endTime
 * @param: @return      
 * @return: PageInfo<Map<String,Object>>      
 * @throws
 */
public PageInfo<Map<String, Object>> getMyNewsList(PageParam pageParam,String deptId, String levelId,String newsType,String status,String beginTime,String endTime) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,Object>> datalist= getMyNewsList(pageParam.getOrgId(), pageParam.getAccountId(),deptId,levelId, newsType, status, beginTime, endTime, pageParam.getSearch());
	PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(datalist);
	return pageInfo;
}


/**
 * 获取个人权限内的新闻 
 */

public List<Map<String, Object>> getMyNewsList(String orgId, String accountId,String deptId, String levelId,  String newsType, String status,
		String beginTime, String endTime, String search) {
	return newsMapper.getMyNewsList(orgId, accountId,deptId,levelId, newsType, status, beginTime, endTime, "%"+search+"%");
}
/**
 * 
* @Title: getReadNews 
* @Description: TODO 阅读
* @param @param orgId
* @param @param accountId
* @param @param deptId
* @param @param levelId
* @param @param newsId
* @param @return 设定文件 
* @return News 返回类型 

 */
public Map<String,Object> getReadNews(String orgId, String accountId, String deptId, String levelId, String newsId) {
	News news = new News();
	news.setOrgId(orgId);
	news.setNewsId(newsId);		
	news=selectOneNews(news);
	List<String> list = new ArrayList<String>();
	String readuser = news.getReader();
	if(StringUtils.isNotBlank(readuser))
	{
		String[] userArr;
		if(readuser.indexOf(",")>0)
		{
			userArr =  readuser.split(",");
		}else
		{
			userArr = new String[] {readuser};
		}
		for(int i=0;i<userArr.length;i++)
		{
			if(StringUtils.isNotBlank(userArr[i]))
			{
				list.add(userArr[i]);
			}
		}
	}
	list.add(accountId);
	HashSet<String> h = new HashSet<String>(list);   
    list.clear();   
    list.addAll(h);
	this.updateReadStatus(orgId, accountId, deptId, levelId, newsId,String.join(",",list));
	return newsMapper.getReadNewsById(orgId, newsId);
}

/**
 * 更新新闻点点击次数
 */

public int updateReadStatus(String orgId, String accountId, String deptId, String levelId, String newsId,String reader) {
	// TODO Auto-generated method stub
	return newsMapper.updateReadStatus(orgId, accountId, deptId, levelId, newsId,reader);
}
/**
 *  获取阅读新闻详情
 */

public Map<String, Object> getReadNewsById(String orgId, String newsId) {
	// TODO Auto-generated method stub
	return newsMapper.getReadNewsById(orgId, newsId);
}

/**
 * 
 * @Title: getMyNewsListForDesk   
 * @Description: TODO 我的桌面上的新闻
 * @param: orgId
 * @param: accountId
 * @param: deptId
 * @param: levelId
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>> getMyNewsListForDesk(String orgId,String endTime,String accountId,String deptId,String levelId)
{
	return newsMapper.getMyNewsListForDesk(orgId,endTime, accountId, deptId, levelId);
}

/**
 * 
 * @Title: getMobileMyNewsList   
 * @Description: TODO 获取移动端企业新闻列表
 * @param: orgId
 * @param: accountId
 * @param: deptId
 * @param: levelId
 * @param: page
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String,String>>getMobileMyNewsList(String orgId,String accountId,String deptId,String levelId,Integer page)
{
	return newsMapper.getMobileMyNewsList(orgId, accountId, deptId, levelId, page);
}

}
