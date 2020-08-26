package com.core136.mapper.archives;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.archives.ArchivesFile;

@Mapper
public interface ArchivesFileMapper extends MyMapper<ArchivesFile>{

	/**
	 * 
	 * @Title: getArchivesFileList   
	 * @Description: TODO 获取文件管理列表
	 * @param orgId
	 * @param opFlag
	 * @param accountId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getArchivesFileList(
			@Param(value="orgId")String orgId,
			@Param(value="opFlag")String opFlag,
			@Param(value="accountId")String accountId,
			@Param(value="volumeId") String volumeId,
			@Param(value="fileType") String fileType,
			@Param(value="secretLevel") String secretLevel,
			@Param(value="search")String search);
	/**
	 * 
	 * @Title: getArchivesFileQueryList   
	 * @Description: TODO 档案查询列表
	 * @param orgId
	 * @param repositoryId
	 * @param accountId
	 * @param volumeId
	 * @param fileType
	 * @param secretLevel
	 * @param search
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getArchivesFileQueryList(
			@Param(value="orgId")String orgId,
			@Param(value="repositoryId")String repositoryId,
			@Param(value="accountId")String accountId,
			@Param(value="volumeId") String volumeId,
			@Param(value="fileType") String fileType,
			@Param(value="secretLevel") String secretLevel,
			@Param(value="search")String search);
	/**
	 * 
	 * @Title: getBorrowArchivesFileList   
	 * @Description: TODO 获取借阅的文件列表
	 * @param orgId
	 * @param volumeId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getBorrowArchivesFileList(@Param(value="orgId")String orgId,@Param(value="volumeId")String volumeId);
}
