package com.core136.service.archives;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.core136.common.utils.SysTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.bean.archives.ArchivesBorrowFile;
import com.core136.bean.archives.ArchivesFile;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.archives.ArchivesFileMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ArchivesFileService {
@Autowired
private ArchivesFileMapper archivesFileMapper;

public int insertArchivesFile(ArchivesFile archivesFile)
{
	return archivesFileMapper.insert(archivesFile);
}

public int deleteArchivesFile(ArchivesFile archivesFile)
{
	return archivesFileMapper.delete(archivesFile);
}

public int updateArchivesFile(Example example,ArchivesFile archivesFile)
{
	return archivesFileMapper.updateByExampleSelective(archivesFile, example);
}

public ArchivesFile selectOneArchivesFile(ArchivesFile archivesFile)
{
	return archivesFileMapper.selectOne(archivesFile);
}
/**
 * 
 * @Title: getArchivesFileList   
 * @Description: TODO 获取文件管理列表
 * @param orgId
 * @param opFlag
 * @param accountId
 * @param search
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getArchivesFileList(String orgId,String opFlag,String accountId,String volumeId,String fileType,String secretLevel,String search)
{
	return archivesFileMapper.getArchivesFileList(orgId, opFlag, accountId,volumeId,fileType,secretLevel,"%"+search+"%");
}
/**
 * 
 * @Title: getArchivesFileList   
 * @Description: TODO 获取文件管理列表
 * @param pageParam
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getArchivesFileList(PageParam pageParam,String volumeId,String fileType,String secretLevel) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getArchivesFileList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(),volumeId,fileType,secretLevel,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
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
public List<Map<String, String>>getArchivesFileQueryList(String orgId,String repositoryId,String accountId,String volumeId,String fileType,String secretLevel,String search)
{
	return archivesFileMapper.getArchivesFileQueryList(orgId, repositoryId, accountId,volumeId,fileType,secretLevel,"%"+search+"%");
}
/**
 * 
 * @Title: getArchivesFileQueryList   
 * @Description: TODO 档案查询列表
 * @param pageParam
 * @param repositoryId
 * @param volumeId
 * @param fileType
 * @param secretLevel
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getArchivesFileQueryList(PageParam pageParam,String repositoryId,String volumeId,String fileType,String secretLevel) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getArchivesFileQueryList(pageParam.getOrgId(),repositoryId,pageParam.getAccountId(),volumeId,fileType,secretLevel,pageParam.getSearch());
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
/**
 * 
 * @Title: isExist   
 * @Description: TODO 获取案卷下的文件个数
 * @param archivesFile
 * @return
 * int    
 * @throws
 */
public int isExist(ArchivesFile archivesFile)
{
	Example example = new Example(ArchivesFile.class);
	example.createCriteria().andEqualTo("orgId",archivesFile.getOrgId()).andEqualTo("volumeId",archivesFile.getVolumeId());
	return archivesFileMapper.selectCountByExample(example);
}

/**
 * @throws ParseException 
 * 
 * @Title: getApprovalFile   
 * @Description: TODO 审批通过后有附件信息
 * @param account
 * @param archivesBorrowFile
 * @return
 * ArchivesFile    
 * @throws
 */
public ArchivesFile getApprovalFile(Account account,ArchivesBorrowFile archivesBorrowFile) throws ParseException
{
	ArchivesFile archivesFile = new ArchivesFile();
	archivesFile.setOrgId(archivesBorrowFile.getOrgId());
	archivesFile.setFileId(archivesBorrowFile.getFileId());
	archivesFile = selectOneArchivesFile(archivesFile);
	if(!account.getAccountId().equals(archivesBorrowFile.getCreateUser())||!archivesBorrowFile.getApprovalStatus().equals("1"))
	{
		archivesFile.setAttach("");
	}
	String nowTime = SysTools.getTime("yyyy-MM-dd");
	String beginTime = archivesBorrowFile.getBeginTime();
	String endTime = archivesBorrowFile.getEndTime();
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
	Date bt=sdf.parse(beginTime); 
	Date et=sdf.parse(endTime); 
	Date nt=sdf.parse(nowTime);
	if(et.before(bt))
	{
		archivesFile.setAttach("");
	}
	if(nt.before(bt))
	{
		archivesFile.setAttach("");
	}
	if(et.before(nt))
	{
		archivesFile.setAttach("");
	}
	return archivesFile;
	
}

/**
 * 
 * @Title: getBorrowArchivesFileList   
 * @Description: TODO  获取借阅的文件列表
 * @param orgId
 * @param volumeId
 * @return
 * List<Map<String,String>>    
 * @throws
 */
public List<Map<String, String>>getBorrowArchivesFileList(String orgId,String volumeId)
{
	return archivesFileMapper.getBorrowArchivesFileList(orgId, volumeId);
}

/**
 * 
 * @Title: getBorrowArchivesFileList   
 * @Description: TODO 获取借阅的文件列表
 * @param pageParam
 * @param volumeId
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getBorrowArchivesFileList(PageParam pageParam,String volumeId) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getBorrowArchivesFileList(pageParam.getOrgId(),volumeId);
	PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String,String>>(datalist);
	return pageInfo;
}
}
