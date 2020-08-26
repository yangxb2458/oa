/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  SuperversionDelayService.java   
 * @Package com.core136.service.superversion   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年11月19日 下午5:43:19   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.service.superversion;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.superversion.SuperversionDelay;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.superversion.SuperversionDelayMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * 
 * @ClassName:  SuperversionDelayService
 * @Description:TODO 督查事件延期申请
 * @author: 稠云技术 
 * @date:   2020年4月9日 下午7:27:48     
 * @Copyright: 2020 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Service
public class SuperversionDelayService {
@Autowired
private SuperversionDelayMapper superversionDelayMapper;


public int insertSuperversionDelay(SuperversionDelay superversionDelay)
{
	return superversionDelayMapper.insert(superversionDelay);
}


public int deleteSuperversionDelay(SuperversionDelay superversionDelay)
{
	return superversionDelayMapper.delete(superversionDelay);
}

public int updateSuperversionDelay(Example example ,SuperversionDelay superversionDelay)
{
	return superversionDelayMapper.updateByExampleSelective(superversionDelay, example);
}

public SuperversionDelay selectOneSuperversionDelay(SuperversionDelay superversionDelay)
{
	return superversionDelayMapper.selectOne(superversionDelay);
}
/**
 * 
 * @Title: getDelayApplyList   
 * @Description: TODO 获取延期审批列表
 * @param: orgId
 * @param: accountId
 * @param: status
 * @param: type
 * @param: beginTime
 * @param: endTime
 * @param: createUser
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getDelayApplyList(String orgId,String accountId,String status,String type,String beginTime,String endTime,String createUser,String search)
{
	return superversionDelayMapper.getDelayApplyList(orgId, accountId, status, type, beginTime, endTime, createUser, "%"+search+"%");
}
/**
 * 
 * @Title: getDelayApplyList   
 * @Description: TODO 获取延期审批列表
 * @param: pageParam
 * @param: status
 * @param: type
 * @param: beginTime
 * @param: endTime
 * @param: createUser
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getDelayApplyList(PageParam pageParam,String status,String type,String beginTime,String endTime,String createUser) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getDelayApplyList(pageParam.getOrgId(),pageParam.getAccountId(),status,type,beginTime,endTime,createUser,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
