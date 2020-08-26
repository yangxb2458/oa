package com.core136.mapper.dataupload;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.dataupload.DataUploadInfo;

@Mapper
public interface DataUploadInfoMapper extends MyMapper<DataUploadInfo>{
	/**
	 * 
	 * @Title: getDataUploadInfoList   
	 * @Description: TODO 获取上报信息列表
	 * @param orgId
	 * @param deptId
	 * @param fromAccountId
	 * @param beginTime
	 * @param endTime
	 * @param dateType
	 * @param approvedType
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
public List<Map<String, String>>getDataUploadInfoList(@Param(value="orgId")String orgId,
		@Param(value="deptId")String deptId,@Param(value="fromAccountId")String fromAccountId,
		@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime,
		@Param(value="dataType")String dateType,@Param(value="approvedType")String approvedType,
		@Param(value="search")String search
		);
/**
 * 
 * @Title: getToProcessInfoList   
 * @Description: TODO 获取持处理的信息列表
 * @param orgId
 * @param deptId
 * @param fromAccountId
 * @param beginTime
 * @param endTime
 * @param dateType
 * @param approvedType
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getToProcessInfoList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,
		@Param(value="deptId")String deptId,@Param(value="fromAccountId")String fromAccountId,
		@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime,
		@Param(value="dataType")String dateType,@Param(value="approvedType")String approvedType,
		@Param(value="search")String search
		);
/**
 * 
 * @Title: getOldProcessInfoList   
 * @Description: TODO 信息处理历史列表
 * @param orgId
 * @param accountId
 * @param deptId
 * @param fromAccountId
 * @param beginTime
 * @param endTime
 * @param dateType
 * @param approvedType
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getOldProcessInfoList(@Param(value="orgId")String orgId,@Param(value="accountId")String accountId,
		@Param(value="deptId")String deptId,@Param(value="fromAccountId")String fromAccountId,
		@Param(value="beginTime")String beginTime,@Param(value="endTime")String endTime,
		@Param(value="dataType")String dateType,@Param(value="approvedType")String approvedType,
		@Param(value="search")String search
		);

}
