package com.core136.mapper.archives;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.archives.ArchivesDestroyRecord;

@Mapper
public interface ArchivesDestroyRecordMapper extends MyMapper<ArchivesDestroyRecord>{
/**
 * 
 * @Title: getArchivesDestoryFileList   
 * @Description: TODO 获取销毁记录
 * @param orgId
 * @param accountId
 * @param beginTime
 * @param endTime
 * @return
 * List<Map<String,String>>    
 * @throws
 */
	public List<Map<String, String>>getArchivesDestoryFileList(
			@Param(value="orgId")String orgId,
			@Param(value="accountId")String accountId,
			@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,
			@Param(value="search")String search
			);
/**
 * 	
 * @Title: getArchivesDestoryVolumeList   
 * @Description: TODO 获取销毁记录
 * @param orgId
 * @param accountId
 * @param beginTime
 * @param endTime
 * @return
 * List<Map<String,String>>    
 * @throws
 */
	public List<Map<String, String>>getArchivesDestoryVolumeList(
			@Param(value="orgId")String orgId,
			@Param(value="accountId")String accountId,
			@Param(value="beginTime")String beginTime,
			@Param(value="endTime")String endTime,
			@Param(value="search")String search
			);
	
}
