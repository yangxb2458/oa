package com.core136.mapper.archives;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.core136.common.dbutils.MyMapper;

import com.core136.bean.archives.ArchivesRepository;

@Mapper
public interface ArchivesRepositoryMapper extends MyMapper<ArchivesRepository>{

	/**
	 * 
	 * @Title: getArchivesRepositoryList   
	 * @Description: TODO 获取卷库列表
	 * @param orgId
	 * @return
	 * List<Map<String,String>>    
	 * @throws
	 */
	public List<Map<String, String>>getArchivesRepositoryList(@Param(value="orgId")String orgId,@Param(value="opFlag") String opFlag,@Param(value="accountId")String accountId);
	
}
