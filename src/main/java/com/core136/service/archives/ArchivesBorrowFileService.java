package com.core136.service.archives;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.bean.archives.ArchivesBorrowFile;
import com.core136.bean.archives.ArchivesFile;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.archives.ArchivesBorrowFileMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ArchivesBorrowFileService {
@Autowired
private ArchivesBorrowFileMapper archivesBorrowFileMapper;
@Autowired
private ArchivesFileService archivesFileService;

public int insertArchivesBorrowFile(ArchivesBorrowFile archivesBorrowFile)
{
	return archivesBorrowFileMapper.insert(archivesBorrowFile);
}

public int deleteArchivesBorrowFile(ArchivesBorrowFile archivesBorrowFile)
{
	return archivesBorrowFileMapper.delete(archivesBorrowFile);
}

public int updateArchivesBorrowFile(Example example,ArchivesBorrowFile archivesBorrowFile)
{
	return archivesBorrowFileMapper.updateByExampleSelective(archivesBorrowFile, example);
}

public ArchivesBorrowFile selectOneArchivesBorrowFile(ArchivesBorrowFile archivesBorrowFile)
{
	return archivesBorrowFileMapper.selectOne(archivesBorrowFile);
}
/**
 * 
 * @Title: getArchivesBorrowFileList   
 * @Description: TODO 文件借阅记录
 * @param orgId
 * @param approvalStatus
 * @param accountId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getArchivesBorrowFileList(String orgId,String approvalStatus,String opFlag,String accountId)
{
	return archivesBorrowFileMapper.getArchivesBorrowFileList(orgId, approvalStatus,opFlag, accountId);
}
/**
 * 
 * @Title: getArchivesBorrowFileList   
 * @Description: TODO 文件借阅记录
 * @param pageParam
 * @param approvalStatus
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getArchivesBorrowFileList(PageParam pageParam,String approvalStatus) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getArchivesBorrowFileList(pageParam.getOrgId(),approvalStatus,pageParam.getOpFlag(),pageParam.getAccountId());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * 
 * @Title: getArchivesBorrowFileApprovalList   
 * @Description: TODO 获取待审批记录
 * @param orgId
 * @param opFlag
 * @param accountId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getArchivesBorrowFileApprovalList(String orgId,String opFlag,String accountId)
{
	return archivesBorrowFileMapper.getArchivesBorrowFileApprovalList(orgId, opFlag, accountId);
}

/**
 * 
 * @Title: getArchivesBorrowFileApprovalList   
 * @Description: TODO 获取待审批记录
 * @param pageParam
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getArchivesBorrowFileApprovalList(PageParam pageParam) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getArchivesBorrowFileApprovalList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}

/**
 * @throws ParseException 
 * 
 * @Title: getApprovalFile   
 * @Description: TODO 获取借阅文件详情
 * @param account
 * @param archivesBorrowFile
 * @return
 * ArchivesFile    
 * @throws
 */
public ArchivesFile getApprovalFile(Account account,ArchivesBorrowFile archivesBorrowFile) throws ParseException
{
	archivesBorrowFile = selectOneArchivesBorrowFile(archivesBorrowFile);
	return archivesFileService.getApprovalFile(account, archivesBorrowFile);
}


}
