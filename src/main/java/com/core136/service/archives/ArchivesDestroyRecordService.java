package com.core136.service.archives;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core136.bean.archives.ArchivesDestroyRecord;
import com.core136.bean.archives.ArchivesFile;
import com.core136.bean.archives.ArchivesVolume;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.archives.ArchivesDestroyRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ArchivesDestroyRecordService {

	@Autowired
	private ArchivesDestroyRecordMapper archivesDestroyRecordMapper;
	@Autowired
	private ArchivesFileService archivesFileService;
	@Autowired
	private ArchivesVolumeService archivesVolumeService;
	
	public int insertArchivesDestroyRecord(ArchivesDestroyRecord archivesDestroyRecord)
	{
		return archivesDestroyRecordMapper.insert(archivesDestroyRecord);
	}
	
	public int deleteArchivesDestroyRecord(ArchivesDestroyRecord archivesDestroyRecord)
	{
		return archivesDestroyRecordMapper.delete(archivesDestroyRecord);
	}
	
	public int updateArchivesDestroyRecord(Example example,ArchivesDestroyRecord archivesDestroyRecord)
	{
		return archivesDestroyRecordMapper.updateByExampleSelective(archivesDestroyRecord, example);
	}
	
	public ArchivesDestroyRecord selectOneArchivesDestroyRecord(ArchivesDestroyRecord archivesDestroyRecord)
	{
		return archivesDestroyRecordMapper.selectOne(archivesDestroyRecord);
	}
	
	/**
	 * 
	 * @Title: destroyArchives   
	 * @Description: TODO 销毁档案
	 * @param archivesDestroyRecord
	 * @return
	 * int    
	 * @throws
	 */
	@Transactional(value="generalTM")
	public int destroyArchives(ArchivesDestroyRecord archivesDestroyRecord)
	{
		if(archivesDestroyRecord.getArchivesType().equals("1"))
		{
			ArchivesFile archivesFile = new ArchivesFile();
			archivesFile.setOrgId(archivesDestroyRecord.getOrgId());
			archivesFile.setFileId(archivesDestroyRecord.getArchivesId());
			archivesFile.setDestoryFlag("1");
			Example example = new Example(ArchivesFile.class);
			example.createCriteria().andEqualTo("orgId",archivesFile.getOrgId()).andEqualTo("fileId",archivesFile.getFileId());
			archivesFileService.updateArchivesFile(example, archivesFile);
		}else if(archivesDestroyRecord.getArchivesType().equals("2"))
		{
			ArchivesVolume archivesVolume = new ArchivesVolume();
			archivesVolume.setOrgId(archivesDestroyRecord.getOrgId());
			archivesVolume.setVolumeId(archivesDestroyRecord.getArchivesId());
			archivesVolume.setDestoryFlag("1");
			Example example = new Example(ArchivesVolume.class);
			example.createCriteria().andEqualTo("orgId",archivesVolume.getOrgId()).andEqualTo("volumeId",archivesVolume.getVolumeId());
			archivesVolumeService.updateArchivesVolume(example, archivesVolume);
		}
		return insertArchivesDestroyRecord(archivesDestroyRecord);
	}
	
	
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
	public List<Map<String, String>> getArchivesDestoryFileList(String orgId,String accountId,String beginTime,String endTime,String search)
	{
		return archivesDestroyRecordMapper.getArchivesDestoryFileList(orgId, accountId, beginTime, endTime,"%"+search+"%");
	}
	
	/**
	 * 
	 * @Title: getArchivesDestoryFileList   
	 * @Description: TODO 获取销毁记录
	 * @param pageParam
	 * @param beginTime
	 * @param endTime
	 * @return
	 * PageInfo<Map<String,String>>    
	 * @throws
	 */
	public PageInfo<Map<String, String>> getArchivesDestoryFileList(PageParam pageParam,String beginTime,String endTime) 
	{
		PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= getArchivesDestoryFileList(pageParam.getOrgId(),pageParam.getAccountId(),beginTime,endTime,pageParam.getSearch());
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
	}
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
	public List<Map<String, String>> getArchivesDestoryVolumeList(String orgId,String accountId,String beginTime,String endTime,String search)
	{
		return archivesDestroyRecordMapper.getArchivesDestoryVolumeList(orgId, accountId, beginTime, endTime,"%"+search+"%");
	}
	/**
	 * 
	 * @Title: getArchivesDestoryVolumeList   
	 * @Description: TODO 获取销毁记录
	 * @param pageParam
	 * @param beginTime
	 * @param endTime
	 * @return
	 * PageInfo<Map<String,String>>    
	 * @throws
	 */
	public PageInfo<Map<String, String>> getArchivesDestoryVolumeList(PageParam pageParam,String beginTime,String endTime) 
	{
		PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
		List<Map<String,String>> datalist= getArchivesDestoryVolumeList(pageParam.getOrgId(),pageParam.getAccountId(),beginTime,endTime,pageParam.getSearch());
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
		return pageInfo;
	}
}
