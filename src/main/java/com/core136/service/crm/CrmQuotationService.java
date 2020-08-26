package com.core136.service.crm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.crm.CrmQuotation;
import com.core136.mapper.crm.CrmQuotationMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class CrmQuotationService {
@Autowired
private CrmQuotationMapper crmQuotationMapper;

public int insertCrmQuotation(CrmQuotation crmQuotation)
{
	return crmQuotationMapper.insert(crmQuotation);
}

public int deleteCrmQuotation(CrmQuotation crmQuotation)
{
	return crmQuotationMapper.delete(crmQuotation);
}

public int updateCrmQuotation(Example example,CrmQuotation crmQuotation)
{
	return crmQuotationMapper.updateByExampleSelective(crmQuotation, example);
}

public CrmQuotation selectOneCrmQuotation(CrmQuotation crmQuotation)
{
	return crmQuotationMapper.selectOne(crmQuotation);
}

}
