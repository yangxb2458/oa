/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  FixedAssetsApplyMapper.java   
 * @Package com.core136.mapper.fixedassets   
 * @Description:    描述   
 * @author: lsq     
 * @date:   2019年12月26日 下午2:51:11   
 * @version V1.0 
 * @Copyright:江苏稠云 www.cyunsoft.com 
 */
package com.core136.mapper.fixedassets;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.core136.bean.fixedassets.FixedAssetsApply;
import org.core136.common.dbutils.MyMapper;

/**
 * @author lsq
 *
 */
@Mapper
public interface FixedAssetsApplyMapper extends MyMapper<FixedAssetsApply>{

	/**
	 * 
	 * @Title: getFixedAssetsApplyList   
	 * @Description: TODO 获取申请列表
	 * @param: orgId
	 * @param: accountId
	 * @param: status
	 * @param: beginTime
	 * @param: endTime
	 * @param: search
	 * @param: opFlag
	 * @param: @return      
	 * @return: List<Map<String,String>>      
	 * @throws
	 */
	public List<Map<String, String>>getFixedAssetsApplyList(
			@Param(value="orgId")String orgId,
			@Param(value="accountId")String accountId,
			@Param(value="status") String status,
			@Param(value="beginTime")String beginTime,
			@Param(value="endTime") String endTime,
			@Param(value="assetsSortId") String assetsSortId,
			@Param(value="search") String search,
			@Param(value="opFlag") String opFlag
			);
	/**
	 * 
	 * @Title: getApplyAndApproveInfo   
	 * @Description: TODO 申请与审批详情
	 * @param: orgId
	 * @param: applyId
	 * @param: @return      
	 * @return:Map<String,String>     
	 * @throws
	 */
	public Map<String, String>getApplyAndApproveInfo(@Param(value="orgId")String orgId,@Param(value="applyId") String applyId);
		
	
	
}
