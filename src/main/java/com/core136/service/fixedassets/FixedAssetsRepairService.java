package com.core136.service.fixedassets;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.fixedassets.FixedAssetsRepair;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.fixedassets.FixedAssetsRepairMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class FixedAssetsRepairService {
@Autowired
private FixedAssetsRepairMapper fixedAssetsRepairMapper;


public int insertFixedAssetsRepair(FixedAssetsRepair fixedAssetsRepair)
{
	return fixedAssetsRepairMapper.insert(fixedAssetsRepair);
}

public int deleteFixedAssetsRepair(FixedAssetsRepair fixedAssetsRepair)
{
	return fixedAssetsRepairMapper.delete(fixedAssetsRepair);
}

public int updateFixedAssetsRepair(Example example,FixedAssetsRepair fixedAssetsRepair)
{
	return fixedAssetsRepairMapper.updateByExampleSelective(fixedAssetsRepair, example);
}

public FixedAssetsRepair selectOneFixedAssetsRepair(FixedAssetsRepair fixedAssetsRepair)
{
	return fixedAssetsRepairMapper.selectOne(fixedAssetsRepair);
}
/**
 * 
 * @Title: getFixedAssetsRepairList   
 * @Description: TODO 获取维修列表
 * @param: orgId
 * @param: beginTime
 * @param: endTime
 * @param: assetsName
 * @param: assetsSortId
 * @param: status
 * @param: search
 * @param: @return      
 * @return: List<Map<String,String>>      
 * @throws
 */
public List<Map<String, String>>getFixedAssetsRepairList(String orgId,String accountId,String beginTime,String endTime,String opFlag,String assetsSortId,String status,String search)
{
	return fixedAssetsRepairMapper.getFixedAssetsRepairList(orgId,accountId, beginTime, endTime, opFlag, assetsSortId, status, "%"+search+"%");
}
/**
 * 
 * @Title: getFixedAssetsRepairList   
 * @Description: TODO 获取维修列表
 * @param: pageParam
 * @param: beginTime
 * @param: endTime
 * @param: assetsName
 * @param: assetsSortId
 * @param: status
 * @param: @return      
 * @return: PageInfo<Map<String,String>>      
 * @throws
 */
public PageInfo<Map<String, String>> getFixedAssetsRepairList(PageParam pageParam,String beginTime,String endTime,String assetsSortId,String status) {
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getFixedAssetsRepairList(pageParam.getOrgId(),pageParam.getAccountId(),beginTime,endTime,pageParam.getOpFlag(),assetsSortId,status,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}


}
