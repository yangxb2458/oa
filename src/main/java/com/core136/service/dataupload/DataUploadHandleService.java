package com.core136.service.dataupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.dataupload.DataUploadHandle;
import com.core136.bean.dataupload.DataUploadInfo;
import com.core136.mapper.dataupload.DataUploadHandleMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class DataUploadHandleService {

	@Autowired
	private DataUploadHandleMapper dataUploadHandleMapper;
	@Autowired
	private DataUploadInfoService dataUploadInfoService;
	
	public int processDataInfo(DataUploadHandle dataUploadHandle)
	{
		DataUploadInfo dataUploadInfo = new DataUploadInfo();
		dataUploadInfo.setOrgId(dataUploadHandle.getOrgId());
		dataUploadInfo.setRecordId(dataUploadInfo.getRecordId());
		dataUploadInfo.setStatus("1");
		Example example = new Example(DataUploadInfo.class);
		example.createCriteria().andEqualTo("recordId",dataUploadInfo.getRecordId()).andEqualTo("orgId",dataUploadInfo.getOrgId());
		dataUploadInfoService.updateDataUploadInfo(example, dataUploadInfo);
		return insertDataUploadHandle(dataUploadHandle);
	}
	
	public int insertDataUploadHandle(DataUploadHandle dataUploadHandle)
	{
		return dataUploadHandleMapper.insert(dataUploadHandle);
	}
	
	public int deleteDataUploadHandle(DataUploadHandle dataUploadHandle)
	{
		return dataUploadHandleMapper.delete(dataUploadHandle);
	}
	
	public int updateDataUploadHandle(Example example,DataUploadHandle dataUploadHandle)
	{
		return dataUploadHandleMapper.updateByExampleSelective(dataUploadHandle, example);
	}
	public DataUploadHandle selectOneDataUploadHandle(DataUploadHandle dataUploadHandle)
	{
		return dataUploadHandleMapper.selectOne(dataUploadHandle);
	}
	
	
	
}
