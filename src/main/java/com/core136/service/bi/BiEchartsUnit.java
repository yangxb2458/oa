package com.core136.service.bi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.bean.bi.BiTemplate;
import com.core136.bean.bi.BiType;

import cyunsoft.bi.option.bean.OptionConfig;

@Service
public class BiEchartsUnit {
@Autowired 
private LineEchartsService lineEchartsService;
@Autowired
private BiTypeService biTypeService;

/**
 * 
 * @Title: getEchartsOptConfig   
 * @Description: TODO 按模版信息获取相关图表配置
 * @param biTemplate
 * @return
 * OptionConfig    
 * @throws
 */
	public OptionConfig getEchartsOptConfig(Account account,BiTemplate biTemplate)
	{
		OptionConfig optionConfig = new OptionConfig();
		BiType biType = new BiType();
		biType.setBiTypeId(biTemplate.getBiType());
		biType.setOrgId(account.getOrgId());
		biType = biTypeService.selectOneBiType(biType);
		String key = biType.getBiFlag();
		switch (key) {
		case "pie":
			break;
		case "line":
			optionConfig = lineEchartsService.getLineOptionConfig(biTemplate,biType);
			break;
		default:
			break;
		}
		return optionConfig;
	}
	
	
}
