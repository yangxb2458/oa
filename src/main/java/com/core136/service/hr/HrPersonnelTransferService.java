package com.core136.service.hr;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.hr.HrPersonnelTransfer;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.hr.HrPersonnelTransferMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class HrPersonnelTransferService {
@Autowired
private HrPersonnelTransferMapper hrPersonnelTransferMapper;

public int insertHrPersonnelTransfer(HrPersonnelTransfer hrPersonnelTransfer)
{
	return hrPersonnelTransferMapper.insert(hrPersonnelTransfer);
}

public int deleteHrPersonnelTransfer(HrPersonnelTransfer hrPersonnelTransfer)
{
	return hrPersonnelTransferMapper.delete(hrPersonnelTransfer);
}

public int updateHrPersonnelTransfer(Example example,HrPersonnelTransfer hrPersonnelTransfer)
{
	return hrPersonnelTransferMapper.updateByExampleSelective(hrPersonnelTransfer, example);
}

public HrPersonnelTransfer selectOneHrPersonnelTransfer(HrPersonnelTransfer hrPersonnelTransfer)
{
	return hrPersonnelTransferMapper.selectOne(hrPersonnelTransfer);
}
/**
 * 
 * @Title: getHrPersonnelTransferList   
 * @Description: TODO 获取人员调动列表
 * @param orgId
 * @param userId
 * @param beginTime
 * @param endTime
 * @param transferType
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getHrPersonnelTransferList(String orgId,String userId,String beginTime,String endTime,String transferType,String search)
{
	return hrPersonnelTransferMapper.getHrPersonnelTransferList(orgId, userId, beginTime, endTime, transferType, "%"+search+"%");
}

/**
 * 
 * @Title: getMyHrPersonnelTransferList   
 * @Description: TODO 人个工作调动记录
 * @param orgId
 * @param accountId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getMyHrPersonnelTransferList(String orgId,String accountId)
{
	return hrPersonnelTransferMapper.getMyHrPersonnelTransferList(orgId, accountId);
}

/**
 * 
 * @Title: getMyHrPersonnelTransferList   
 * @Description: TODO 人个工作调动记录
 * @param pageParam
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getMyHrPersonnelTransferList(PageParam pageParam) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getMyHrPersonnelTransferList(pageParam.getOrgId(),pageParam.getAccountId());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getHrPersonnelTransferList   
 * @Description: TODO 获取人员调动列表
 * @param pageParam
 * @param userId
 * @param beginTime
 * @param endTime
 * @param transferType
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getHrPersonnelTransferList(PageParam pageParam,String userId,String beginTime,String endTime,String transferType) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getHrPersonnelTransferList(pageParam.getOrgId(),userId, beginTime, endTime, transferType, pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

}
