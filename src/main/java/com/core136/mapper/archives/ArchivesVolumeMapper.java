package com.core136.mapper.archives;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.beust.jcommander.Parameter;
import com.core136.bean.archives.ArchivesVolume;

@Mapper
public interface ArchivesVolumeMapper extends MyMapper<ArchivesVolume>{

	/**
	 * 
	 * @Title: getArchivesVolumeList   
	 * @Description: TODO 获取案卷列表
	 * @param orgId
	 * @param opFlag
	 * @param accountId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getArchivesVolumeList(
			@Param(value="orgId")String orgId,
			@Param(value="opFlag")String opFlag,
			@Param(value="accountId")String accountId,
			@Param(value="search") String search
			);
	/**
	 * 
	 * @Title: getArchivesVolumeListForSelect   
	 * @Description: TODO 获取案卷列表
	 * @param orgId
	 * @param opFlag
	 * @param accountId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getArchivesVolumeListForSelect(@Param(value="orgId")String orgId,@Param(value="opFlag")String opFlag,@Param(value="accountId") String accountId);

	/**
	 * 
	 * @Title: getArchivesVolumeByRepositoryId   
	 * @Description: TODO 获取卷库下的案卷列表
	 * @param orgId
	 * @param repositoryId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getArchivesVolumeByRepositoryId(@Param(value="orgId")String orgId,@Param(value="repositoryId") String repositoryId);

	
}
