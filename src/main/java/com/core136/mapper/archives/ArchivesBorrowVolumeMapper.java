package com.core136.mapper.archives;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.archives.ArchivesBorrowVolume;

@Mapper
public interface ArchivesBorrowVolumeMapper extends MyMapper<ArchivesBorrowVolume>{

	/**
	 * 
	 * @Title: getArchivesBorrowVolumeList   
	 * @Description: TODO 案卷借阅记录
	 * @param orgId
	 * @param approvalStatus
	 * @param opFlag
	 * @param accountId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getArchivesBorrowVolumeList(@Param(value="orgId")String orgId,
			@Param(value="approvalStatus") String approvalStatus,
			@Param(value="opFlag")String opFlag,
			@Param(value="accountId")String accountId);
	/**
	 * 
	 * @Title: getArchivesBorrowVolumeApprovalList   
	 * @Description: TODO 获取待审批记录
	 * @param orgId
	 * @param opFlag
	 * @param accountId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getArchivesBorrowVolumeApprovalList(@Param(value="orgId")String orgId,
			@Param(value="opFlag")String opFlag,
			@Param(value="accountId")String accountId);	
	
}
