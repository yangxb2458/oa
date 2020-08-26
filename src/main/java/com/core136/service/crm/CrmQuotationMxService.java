package com.core136.service.crm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.crm.CrmQuotationMx;
import com.core136.mapper.crm.CrmQuotationMxMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class CrmQuotationMxService {
@Autowired
private CrmQuotationMxMapper crmQuotationMxMapper;

public int insertCrmQuotationMx(CrmQuotationMx crmQuotationMx)
{
	return crmQuotationMxMapper.insert(crmQuotationMx);
}

public int deleteCrmQuotationMx(CrmQuotationMx crmQuotationMx)
{
	return crmQuotationMxMapper.delete(crmQuotationMx);
}

public int updateCrmQuotationMx(Example example, CrmQuotationMx crmQuotationMx)
{
	return crmQuotationMxMapper.updateByExampleSelective(crmQuotationMx, example);
}

public CrmQuotationMx selectOneCrmQuotationMx(CrmQuotationMx crmQuotationMx)
{
	return crmQuotationMxMapper.selectOne(crmQuotationMx);
}


}
