package com.core136.service.archives;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.bean.archives.ArchivesBorrowFile;
import com.core136.bean.archives.ArchivesBorrowVolume;
import com.core136.bean.archives.ArchivesFile;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.archives.ArchivesBorrowVolumeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ArchivesBorrowVolumeService {
@Autowired
private ArchivesBorrowVolumeMapper archivesBorrowVolumeMapper;
@Autowired
private ArchivesBorrowVolumeService archivesBorrowVolumeService;
@Autowired
private ArchivesFileService archivesFileService;
@Autowired
private ArchivesVolumeService archivesVolumeService;
public int insertArchivesBorrowVolume(ArchivesBorrowVolume archivesBorrowVolume)
{
	return archivesBorrowVolumeMapper.insert(archivesBorrowVolume);
}

public int deleteArchivesBorrowVolume(ArchivesBorrowVolume archivesBorrowVolume)
{
	return archivesBorrowVolumeMapper.delete(archivesBorrowVolume);
}

public int updateArchivesBorrowVolume(Example example,ArchivesBorrowVolume archivesBorrowVolume)
{
	return archivesBorrowVolumeMapper.updateByExampleSelective(archivesBorrowVolume, example);
}

public ArchivesBorrowVolume selectOneArchivesBorrowVolume(ArchivesBorrowVolume archivesBorrowVolume)
{
	return archivesBorrowVolumeMapper.selectOne(archivesBorrowVolume);
}
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
public List<Map<String, String>>getArchivesBorrowVolumeList(String orgId,String approvalStatus,String opFlag,String accountId)
{
	return archivesBorrowVolumeMapper.getArchivesBorrowVolumeList(orgId, approvalStatus,opFlag, accountId);
}

/**
 * 
 * @Title: getArchivesBorrowVolumeList   
 * @Description: TODO  案卷借阅记录
 * @param pageParam
 * @param approvalStatus
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getArchivesBorrowVolumeList(PageParam pageParam,String approvalStatus) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getArchivesBorrowVolumeList(pageParam.getOrgId(),approvalStatus,pageParam.getOpFlag(),pageParam.getAccountId());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

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
public List<Map<String, String>>getArchivesBorrowVolumeApprovalList(String orgId,String opFlag,String accountId)
{
	return archivesBorrowVolumeMapper.getArchivesBorrowVolumeApprovalList(orgId, opFlag, accountId);
}
/**
 * 
 * @Title: getArchivesBorrowVolumeApprovalList   
 * @Description: TODO 获取待审批记录
 * @param pageParam
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getArchivesBorrowVolumeApprovalList(PageParam pageParam) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getArchivesBorrowVolumeApprovalList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getApprovalVolumeFile   
 * @Description: TODO 获取案卷借阅文件详情
 * @param account
 * @param archivesBorrowVolume
 * @param archivesFile
 * @return
 * @throws ParseException
 * ArchivesFile    
 * @throws
 */
public ArchivesFile getApprovalVolumeFile(Account account,ArchivesBorrowVolume archivesBorrowVolume,ArchivesFile archivesFile) throws ParseException
{
	archivesBorrowVolume = archivesBorrowVolumeService.selectOneArchivesBorrowVolume(archivesBorrowVolume);
	archivesFile = archivesFileService.selectOneArchivesFile(archivesFile);
	return archivesVolumeService.getApprovalVolumeFile(account, archivesBorrowVolume, archivesFile);
}

}
