package com.core136.service.archives;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.archives.ArchivesRepository;
import com.core136.mapper.archives.ArchivesRepositoryMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class ArchivesRepositoryService {
@Autowired
private ArchivesRepositoryMapper archivesRepositoryMapper;
	
	public int insertArchivesRepository(ArchivesRepository archivesRepository)
	{
		return archivesRepositoryMapper.insert(archivesRepository);
	}
	
	public int deleteArchivesRepository(ArchivesRepository archivesRepository)
	{
		return archivesRepositoryMapper.delete(archivesRepository);
	}
	public int updateArchivesRepository(Example example,ArchivesRepository archivesRepository)
	{
		return archivesRepositoryMapper.updateByExampleSelective(archivesRepository, example);
	}
	public ArchivesRepository selectOneArchivesRepository(ArchivesRepository archivesRepository)
	{
		return archivesRepositoryMapper.selectOne(archivesRepository);
	}
	
	/**
	 * 
	 * @Title: getArchivesRepositoryList   
	 * @Description: TODO 获取卷库列表
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getArchivesRepositoryList(String orgId,String opFlag,String accountId)
	{
		return archivesRepositoryMapper.getArchivesRepositoryList(orgId,opFlag,accountId);
	}
}
