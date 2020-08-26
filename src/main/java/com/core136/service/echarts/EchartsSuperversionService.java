package com.core136.service.echarts;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.account.Account;
import com.core136.mapper.echarts.EchartsSuperversionMapper;

import cyunsoft.bi.option.bean.OptionConfig;
import cyunsoft.bi.option.property.OptionSeries;
import cyunsoft.bi.option.property.OptionTitle;
import cyunsoft.bi.option.resdata.LegendData;
import cyunsoft.bi.option.resdata.SeriesData;
import cyunsoft.bi.option.style.Emphasis;
import cyunsoft.bi.option.style.ItemStyle;
import cyunsoft.bi.option.units.LineOption;
import cyunsoft.bi.option.units.PieOption;

@Service
public class EchartsSuperversionService {
	private PieOption pieOption = new PieOption();
	private LineOption lineOption = new LineOption();
@Autowired
private EchartsSuperversionMapper echartsSuperversionMapper;



/**
 * 
 * @Title: getBiSuperversionByMonthLine   
 * @Description: TODO 按月份统计工作量
 * @param account
 * @return
 * OptionConfig    
 * @throws
 */
public OptionConfig getBiSuperversionByMonthLine(Account account)
{
	OptionConfig optionConfig = new OptionConfig();
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
    Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    Date y = c.getTime();
    String endTime = format.format(y);
    c.add(Calendar.YEAR, -1);
    y = c.getTime();
    String beginTime = format.format(y);
	List<Map<String, Object>> resList = echartsSuperversionMapper.getBiSuperversionByMonthLine(account.getOrgId(), beginTime, endTime);
	String [] xAxisData = new String[resList.size()];
	Double[] resData = new Double[resList.size()];
	for(int i=0;i<resList.size();i++)
	{
		xAxisData[i]=resList.get(i).get("createTime").toString();
		resData[i]=Double.valueOf(resList.get(i).get("total").toString());
	}
	optionConfig = lineOption.getBasicLineChartOption(xAxisData, resData);
	return optionConfig;
}
	
	/**
	 * 
	 * @Title: getBiSuperversionStatusTypePie   
	 * @Description: TODO 获取督查督办当前状态总数
	 * @param account
	 * @return
	 * OptionConfig    
	 * @throws
	 */
	public OptionConfig getBiSuperversionStatusTypePie(Account account)
	{
		OptionConfig optionConfig = new OptionConfig();
		List<Map<String, String>> resdataList = echartsSuperversionMapper.getBiSuperversionStatusTypePie(account.getOrgId());
		OptionSeries[] optionSeriesArr = new OptionSeries[1];
		SeriesData[] dataArr = new SeriesData[resdataList.size()];
		int selectedLeng=0;
		if(dataArr.length>=10)
		{
			selectedLeng=10;
		}else
		{
			selectedLeng = dataArr.length;
		}
		String[] selected = new String[selectedLeng];
		LegendData[] legendDatas = new LegendData[dataArr.length];
		for(int i=0;i<dataArr.length;i++)
		{
			if(StringUtils.isBlank(resdataList.get(i).get("name")))
			{
				resdataList.get(i).put("name", "other"+i);
			}
			if(i<selectedLeng)
			{
				selected[i]=resdataList.get(i).get("name").toString();
			}
			LegendData legendData = new LegendData();
			legendData.setName(resdataList.get(i).get("name").toString());
			legendDatas[i]=legendData;
			SeriesData seriesData = new SeriesData();
			seriesData.setName(resdataList.get(i).get("name").toString());
			seriesData.setValue(Double.valueOf(String.valueOf(resdataList.get(i).get("value"))));
			dataArr[i]=seriesData;
		}
		OptionSeries optionSeries = new OptionSeries();
		optionSeries.setName("督查督办状态");
		optionSeries.setType("pie");
		optionSeries.setRadius("55%");
		optionSeries.setCenter(new String[]{"40%", "50%"});
		Emphasis emphasis = new Emphasis();
		ItemStyle itemStyle = new ItemStyle();
		itemStyle.setShadowBlur(10);
		itemStyle.setShadowOffsetX(0);
		itemStyle.setShadowColor("rgba(0, 0, 0, 0.5)");
		emphasis.setItemStyle(itemStyle);
		optionSeries.setData(dataArr);
		optionSeriesArr[0] = optionSeries;
		optionConfig.setSeries(optionSeriesArr);
		optionConfig = pieOption.getPieLegendChartOption(legendDatas,selected,optionSeriesArr);
		OptionTitle optionTitle = new OptionTitle();
		optionTitle.setText("督查督办状态总数统计");
		optionTitle.setSubtext("状态总数占比");
		optionTitle.setLeft("center");
		optionConfig.setTitle(optionTitle);
		return optionConfig;
	}
	
	
	/**
	 * 
	 * @Title: getBiSuperversionTypePie   
	 * @Description: TODO 获取BPM使用分类前10的占比
	 * @param account
	 * @return
	 * OptionConfig    
	 * @throws
	 */
	public OptionConfig getBiSuperversionTypePie(Account account)
	{
		OptionConfig optionConfig = new OptionConfig();
		List<Map<String, String>> resdataList = echartsSuperversionMapper.getBiSuperversionTypePie(account.getOrgId());
		OptionSeries[] optionSeriesArr = new OptionSeries[1];
		SeriesData[] dataArr = new SeriesData[resdataList.size()];
		int selectedLeng=0;
		if(dataArr.length>=10)
		{
			selectedLeng=10;
		}else
		{
			selectedLeng = dataArr.length;
		}
		String[] selected = new String[selectedLeng];
		LegendData[] legendDatas = new LegendData[dataArr.length];
		for(int i=0;i<dataArr.length;i++)
		{
			if(StringUtils.isBlank(resdataList.get(i).get("name")))
			{
				resdataList.get(i).put("name", "other"+i);
			}
			if(i<selectedLeng)
			{
				selected[i]=resdataList.get(i).get("name").toString();
			}
			LegendData legendData = new LegendData();
			legendData.setName(resdataList.get(i).get("name").toString());
			legendDatas[i]=legendData;
			SeriesData seriesData = new SeriesData();
			seriesData.setName(resdataList.get(i).get("name").toString());
			seriesData.setValue(Double.valueOf(String.valueOf(resdataList.get(i).get("value"))));
			dataArr[i]=seriesData;
		}
		OptionSeries optionSeries = new OptionSeries();
		optionSeries.setName("督查督办分类");
		optionSeries.setType("pie");
		optionSeries.setRadius("55%");
		optionSeries.setCenter(new String[]{"40%", "50%"});
		Emphasis emphasis = new Emphasis();
		ItemStyle itemStyle = new ItemStyle();
		itemStyle.setShadowBlur(10);
		itemStyle.setShadowOffsetX(0);
		itemStyle.setShadowColor("rgba(0, 0, 0, 0.5)");
		emphasis.setItemStyle(itemStyle);
		optionSeries.setData(dataArr);
		optionSeriesArr[0] = optionSeries;
		optionConfig.setSeries(optionSeriesArr);
		optionConfig = pieOption.getPieLegendChartOption(legendDatas,selected,optionSeriesArr);
		OptionTitle optionTitle = new OptionTitle();
		optionTitle.setText("督查督办分类总数统计");
		optionTitle.setSubtext("分类总数占比");
		optionTitle.setLeft("center");
		optionConfig.setTitle(optionTitle);
		return optionConfig;
	}
	
	/**
	 * 
	 * @Title: getBiSuperversionByLeadPie   
	 * @Description: TODO 前10位领导的人员工作量占比
	 * @param account
	 * @return
	 * OptionConfig    
	 * @throws
	 */
	public OptionConfig getBiSuperversionByLeadPie(Account account)
	{
		OptionConfig optionConfig = new OptionConfig();
		List<Map<String, String>> resdataList = echartsSuperversionMapper.getBiSuperversionByLeadPie(account.getOrgId());
		OptionSeries[] optionSeriesArr = new OptionSeries[1];
		SeriesData[] dataArr = new SeriesData[resdataList.size()];
		int selectedLeng=0;
		if(dataArr.length>=10)
		{
			selectedLeng=10;
		}else
		{
			selectedLeng = dataArr.length;
		}
		String[] selected = new String[selectedLeng];
		LegendData[] legendDatas = new LegendData[dataArr.length];
		for(int i=0;i<dataArr.length;i++)
		{
			if(StringUtils.isBlank(resdataList.get(i).get("name")))
			{
				resdataList.get(i).put("name", "other"+i);
			}
			if(i<selectedLeng)
			{
				selected[i]=resdataList.get(i).get("name").toString();
			}
			LegendData legendData = new LegendData();
			legendData.setName(resdataList.get(i).get("name").toString());
			legendDatas[i]=legendData;
			SeriesData seriesData = new SeriesData();
			seriesData.setName(resdataList.get(i).get("name").toString());
			seriesData.setValue(Double.valueOf(String.valueOf(resdataList.get(i).get("value"))));
			dataArr[i]=seriesData;
		}
		OptionSeries optionSeries = new OptionSeries();
		optionSeries.setName("领导");
		optionSeries.setType("pie");
		optionSeries.setRadius("55%");
		optionSeries.setCenter(new String[]{"40%", "50%"});
		Emphasis emphasis = new Emphasis();
		ItemStyle itemStyle = new ItemStyle();
		itemStyle.setShadowBlur(10);
		itemStyle.setShadowOffsetX(0);
		itemStyle.setShadowColor("rgba(0, 0, 0, 0.5)");
		emphasis.setItemStyle(itemStyle);
		optionSeries.setData(dataArr);
		optionSeriesArr[0] = optionSeries;
		optionConfig.setSeries(optionSeriesArr);
		optionConfig = pieOption.getPieLegendChartOption(legendDatas,selected,optionSeriesArr);
		OptionTitle optionTitle = new OptionTitle();
		optionTitle.setText("领导督查量统计");
		optionTitle.setSubtext("领导督查量占比");
		optionTitle.setLeft("center");
		optionConfig.setTitle(optionTitle);
		return optionConfig;
	}
}
