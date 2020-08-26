package com.core136.service.bi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.core136.bean.bi.BiTemplate;
import com.core136.bean.bi.BiType;

import cyunsoft.bi.option.bean.OptionConfig;

@Service
public class LineEchartsService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public OptionConfig getLineOptionConfig(BiTemplate biTemplate,BiType biType)
	{
		String key = biTemplate.getBiType();
		switch (key) {
		case "line001":
			
			break;

		default:
			break;
		}
		return null;
	}
	
	
}
