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
import com.core136.bean.archives.ArchivesBorrowVolume;
import com.core136.bean.archives.ArchivesFile;
import com.core136.bean.archives.ArchivesVolume;
import com.core136.bean.sys.PageParam;
import com.core136.mapper.archives.ArchivesVolumeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class ArchivesVolumeService {
@Autowired
private ArchivesVolumeMapper archivesVolumeMapper;

public int insertArchivesVolume(ArchivesVolume archivesVolume)
{
	return archivesVolumeMapper.insert(archivesVolume);
}

public int deleteArchivesVolume(ArchivesVolume archivesVolume)
{
	return archivesVolumeMapper.delete(archivesVolume);
}

public int updateArchivesVolume(Example example,ArchivesVolume archivesVolume)
{
	return archivesVolumeMapper.updateByExampleSelective(archivesVolume, example);
}

public ArchivesVolume selectOneArchivesVolume(ArchivesVolume archivesVolume)
{
	return archivesVolumeMapper.selectOne(archivesVolume);
}
public int isExist(ArchivesVolume archivesVolume)
{
	Example example = new Example(ArchivesVolume.class);
	example.createCriteria().andEqualTo("orgId",archivesVolume.getOrgId()).andEqualTo("repositoryId",archivesVolume.getRepositoryId());
	return archivesVolumeMapper.selectCountByExample(example);
}
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
public List<Map<String, String>>getArchivesVolumeListForSelect (String orgId,String opFlag,String accountId)
{
	return archivesVolumeMapper.getArchivesVolumeListForSelect(orgId, opFlag, accountId);
}

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
public List<Map<String, String>>getArchivesVolumeByRepositoryId (String orgId,String repositoryId)
{
	return archivesVolumeMapper.getArchivesVolumeByRepositoryId(orgId, repositoryId);
}

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
public List<Map<String, String>>getArchivesVolumeList (String orgId,String opFlag,String accountId,String search)
{
	return archivesVolumeMapper.getArchivesVolumeList(orgId, opFlag, accountId,"%"+search+"%");
}

/**
 * 
 * @Title: getArchivesVolumeList   
 * @Description: TODO  获取案卷列表
 * @param pageParam
 * @return
 * PageInfo<Map<String,String>>    
 * @throws
 */
public PageInfo<Map<String, String>> getArchivesVolumeList(PageParam pageParam) 
{
	PageHelper.startPage(pageParam.getPageNumber(), pageParam.getPageSize(),pageParam.getOrderBy());
	List<Map<String,String>> datalist= getArchivesVolumeList(pageParam.getOrgId(),pageParam.getOpFlag(),pageParam.getAccountId(),pageParam.getSearch());
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
	if(!account.getAccountId().equals(archivesBorrowVolume.getCreateUser())||!archivesBorrowVolume.getApprovalStatus().equals("1"))
	{
		archivesFile.setAttach("");
	}
	String nowTime = SysTools.getTime("yyyy-MM-dd");
	String beginTime = archivesBorrowVolume.getBeginTime();
	String endTime = archivesBorrowVolume.getEndTime();
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

}
