/**  
 * All rights Reserved, Designed By www.cyunsoft.com
 * @Title:  RountPageOfficeController.java   
 * @Package com.core136.controller.file   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 稠云信息    
 * @date:   2019年5月17日 下午4:44:47   
 * @version V1.0 
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.core136.controller.file;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.core136.bean.account.Account;
import com.core136.bean.account.UserInfo;
import com.core136.bean.file.Attach;
import com.core136.bean.file.NetDisk;
import com.core136.service.account.AccountService;
import com.core136.service.file.AttachService;
import com.core136.service.file.NetDiskService;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PDFCtrl;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.wordwriter.DataRegion;
import com.zhuozhengsoft.pageoffice.wordwriter.WordDocument;


/**   
 * @ClassName:  RountPageOfficeController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 稠云信息 
 * @date:   2019年5月17日 下午4:44:47   
 * @author lsq  
 * @Copyright: 2019 www.cyunsoft.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏稠云信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
@RestController
@RequestMapping("/office")
public class RountPageOfficeController {
@Autowired
private AttachService attachService;
@Autowired
private NetDiskService netDiskService;
@Autowired
private AccountService accountService;

@RequestMapping(value="/openppt", method=RequestMethod.GET)
public ModelAndView openppt(HttpServletRequest request,Attach attach,String openModeType, Map<String,Object> map,NetDisk netDisk,String path){
	UserInfo userInfo = accountService.getRedisUserInfo(request);
	String filePath="";
	if(StringUtils.isBlank(attach.getAttachId()))
	{
		netDisk.setOrgId(userInfo.getOrgId());
		netDisk = netDiskService.selectOneNetDisk(netDisk);
		filePath = netDisk.getRootPath()+path;
	}else
	{
		attach.setOrgId(userInfo.getOrgId());
		attach = attachService.selectOne(attach);
		filePath = attach.getPath();
	}
	File file = new File(filePath);
	filePath = file.getAbsoluteFile().toString();
	//--- PageOffice的调用代码 开始 -----
	PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
	poCtrl.setServerPage("/poserver.zz");//设置授权程序servlet
	poCtrl.setSaveFilePage("/office/pptsave");//设置保存的action
	if(openModeType.equals("4"))
	{
		poCtrl.webOpen(filePath,OpenModeType.pptNormalEdit,userInfo.getUserName());
		poCtrl.addCustomToolButton("保存", "Save()", 1);
	}else if(openModeType.equals("2")||openModeType.equals("3"))
	{
		poCtrl.webOpen(filePath,OpenModeType.pptReadOnly,userInfo.getUserName());
		poCtrl.addCustomToolButton("打印", "PrintFile()", 6);
		
	}else if(openModeType.equals("1"))
	{
		poCtrl.setAllowCopy(false);//禁止拷贝
		poCtrl.setMenubar(false);//隐藏菜单栏
		poCtrl.webOpen(filePath,OpenModeType.pptReadOnly,userInfo.getUserName());
		poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");
	}
	poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
	//poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
	map.put("pageoffice",poCtrl.getHtmlCode("PageOfficeCtrl1"));
	map.put("attachName",attach.getOldName());
	map.put("attachId",attach.getAttachId());
	//--- PageOffice的调用代码 结束 -----
	ModelAndView mv = new ModelAndView("/app/core/office/openppt");
	return mv;
}
	/**
	 * 
	 * @Title: openpdf   
	 * @Description: TODO 在线打开PDF  
	 * @param: request
	 * @param: attach
	 * @param: openModeType
	 * @param: map
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping(value="/openpdf", method=RequestMethod.GET)
	public ModelAndView openPdf(HttpServletRequest request,Attach attach,String openModeType, Map<String,Object> map,NetDisk netDisk,String path){
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		String filePath="";
		if(StringUtils.isBlank(attach.getAttachId()))
		{
			netDisk.setOrgId(userInfo.getOrgId());
			netDisk = netDiskService.selectOneNetDisk(netDisk);
			filePath = netDisk.getRootPath()+path;
		}else
		{
			attach.setOrgId(userInfo.getOrgId());
			attach = attachService.selectOne(attach);
			filePath = attach.getPath();
		}
		File file = new File(filePath);
		filePath = file.getAbsoluteFile().toString();
		int dot = filePath.lastIndexOf('.');
		if ((dot >-1) && (dot < (filePath.length()))) {
			filePath= filePath.substring(0, dot);
		}
		filePath=filePath+".pdf";	
		PDFCtrl pdfCtrl1 = new PDFCtrl(request);
		pdfCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
		// Create custom toolbar
		pdfCtrl1.addCustomToolButton("隐藏/显示书签", "SetBookmarks()", 0);
		pdfCtrl1.addCustomToolButton("-", "", 0);
		pdfCtrl1.addCustomToolButton("实际大小", "SetPageReal()", 16);
		pdfCtrl1.addCustomToolButton("适合页面", "SetPageFit()", 17);
		pdfCtrl1.addCustomToolButton("适合宽度", "SetPageWidth()", 18);
		pdfCtrl1.addCustomToolButton("-", "", 0);
		pdfCtrl1.addCustomToolButton("首页", "FirstPage()", 8);
		pdfCtrl1.addCustomToolButton("上一页", "PreviousPage()", 9);
		pdfCtrl1.addCustomToolButton("下一页", "NextPage()", 10);
		pdfCtrl1.addCustomToolButton("尾页", "LastPage()", 11);
		pdfCtrl1.addCustomToolButton("-", "", 0);
		pdfCtrl1.addCustomToolButton("向左旋转90度", "SetRotateLeft()", 12);
		pdfCtrl1.addCustomToolButton("向右旋转90度", "SetRotateRight()", 13);
		pdfCtrl1.webOpen(filePath);
		if(openModeType.equals("4"))
		{
			pdfCtrl1.addCustomToolButton("打印", "PrintFile()", 6);
		}else if(openModeType.equals("2")||openModeType.equals("3"))
		{
			pdfCtrl1.addCustomToolButton("打印", "PrintFile()", 6);
			
		}else if(openModeType.equals("1"))
		{
			pdfCtrl1.setAllowCopy(false);//禁止拷贝
			pdfCtrl1.setMenubar(false);//隐藏菜单栏
			pdfCtrl1.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");
		}
		map.put("pageoffice",pdfCtrl1.getHtmlCode("PDFCtrl1"));
		map.put("attachName",attach.getOldName());
		map.put("attachId",attach.getAttachId());
		//--- PageOffice的调用代码 结束 -----
		ModelAndView mv = new ModelAndView("/app/core/office/openpdf");
		return mv;
	}	
	
	
	/**
	 * 
	 * @Title: openExcel   
	 * @Description: TODO 打开execl
	 * @param: request
	 * @param: attach
	 * @param: openModeType
	 * @param: map
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping(value="/openexcel", method=RequestMethod.GET)
	public ModelAndView openExcel(HttpServletRequest request,Attach attach,String openModeType, Map<String,Object> map,NetDisk netDisk,String path){
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		String filePath="";
		if(StringUtils.isBlank(attach.getAttachId()))
		{
			netDisk.setOrgId(userInfo.getOrgId());
			netDisk = netDiskService.selectOneNetDisk(netDisk);
			filePath = netDisk.getRootPath()+path;
		}else
		{
			attach.setOrgId(userInfo.getOrgId());
			attach = attachService.selectOne(attach);
			filePath = attach.getPath();
		}
		File file = new File(filePath);
		filePath = file.getAbsoluteFile().toString();
		//--- PageOffice的调用代码 开始 -----
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		poCtrl.setServerPage("/poserver.zz");//设置授权程序servlet
		poCtrl.setSaveFilePage("/office/excelsave");//设置保存的action
		if(openModeType.equals("4"))
		{
			poCtrl.webOpen(filePath,OpenModeType.xlsNormalEdit,userInfo.getUserName());
			poCtrl.addCustomToolButton("保存", "Save()", 1);
		}else if(openModeType.equals("2")||openModeType.equals("3"))
		{
			poCtrl.webOpen(filePath,OpenModeType.xlsReadOnly,userInfo.getUserName());
			poCtrl.addCustomToolButton("打印", "PrintFile()", 6);
			
		}else if(openModeType.equals("1"))
		{
			poCtrl.setAllowCopy(false);//禁止拷贝
			poCtrl.setMenubar(false);//隐藏菜单栏
			poCtrl.webOpen(filePath,OpenModeType.xlsReadOnly,userInfo.getUserName());
			poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");
		}
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		//poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		map.put("pageoffice",poCtrl.getHtmlCode("PageOfficeCtrl1"));
		map.put("attachName",attach.getOldName());
		map.put("attachId",attach.getAttachId());
		//--- PageOffice的调用代码 结束 -----
		ModelAndView mv = new ModelAndView("/app/core/office/openexcel");
		return mv;
	}	
	
	
	/**
	 * 
	 * @Title: taohongword   
	 * @Description: TODO 文件套红
	 * @param: request
	 * @param: attach
	 * @param: openModeType
	 * @param: map
	 * @param: @return      
	 * @return: ModelAndView      
	 * @throws
	 */
	@RequestMapping(value="/taohongword", method=RequestMethod.GET)
	public ModelAndView taohongword(HttpServletRequest request,Attach attach,String openModeType,String runId,String mb, Map<String,Object> map){
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		attach.setOrgId(userInfo.getOrgId());
		attach = attachService.selectOne(attach);
		String filePath = attach.getPath();
		File file = new File(filePath);
		filePath = file.getAbsoluteFile().toString();
		//--- PageOffice的调用代码 开始 -----
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		poCtrl.setServerPage("/poserver.zz");//设置授权程序servlet
		poCtrl.setSaveFilePage("/office/wordsave");//设置保存的action
		if(openModeType.equals("4")||openModeType.equals("5"))
		{
			poCtrl.addCustomToolButton("显示痕迹", "ShowRevisions", 5);
		    poCtrl.addCustomToolButton("隐藏痕迹", "HiddenRevisions", 5);
		    poCtrl.addCustomToolButton("领导圈阅", "StartHandDraw", 3);
		    poCtrl.addCustomToolButton("接受所有修订", "AcceptAllRevisions", 5);
		    poCtrl.addCustomToolButton("插入键盘批注", "StartRemark", 3);
			if(StringUtils.isNotBlank(mb))
			{
				Attach attach2 = new Attach();
				attach2.setOrgId(userInfo.getOrgId());
				attach2.setAttachId(mb);
				attach2 = attachService.selectOne(attach2);
				String path = attach2.getPath();
				if(StringUtils.isNotBlank(path))
				{
					File file1 = new File(path);
					path = file1.getAbsoluteFile().toString();
					WordDocument doc = new WordDocument();
					DataRegion sTextS = doc.openDataRegion("PO_STextS");
					sTextS.setValue("[word]"+filePath+"[/word]");
					//DataRegion topicWords = doc.openDataRegion("PO_TopicWords");
					//topicWords.setValue("Pageoffice、 套红");
					poCtrl.setWriter(doc);
					poCtrl.webOpen(path,OpenModeType.docNormalEdit,userInfo.getUserName());
					poCtrl.addCustomToolButton("保存", "Save()", 1);
				}else
				{
					poCtrl.webOpen(filePath,OpenModeType.docNormalEdit,userInfo.getUserName());
					poCtrl.addCustomToolButton("保存", "Save()", 1);
				}
			}else
			{
				poCtrl.webOpen(filePath,OpenModeType.docNormalEdit,userInfo.getUserName());
				poCtrl.addCustomToolButton("保存", "Save()", 1);
			}
			poCtrl.addCustomToolButton("加盖印章", "InsertSeal()", 2);
			poCtrl.addCustomToolButton("文档签名", "InsertHandSign()", 3);
			//以下先确定好位置后，再签名
//			poCtrl.addCustomToolButton("印章位置", "InsertSealPos()", 2);
//			poCtrl.addCustomToolButton("签字", "AddHandSign()", 3);
			
		}else if(openModeType.equals("2")||openModeType.equals("3"))
		{
			poCtrl.webOpen(filePath,OpenModeType.docReadOnly,userInfo.getUserName());
			poCtrl.addCustomToolButton("打印", "PrintFile()", 6);
			
		}else if(openModeType.equals("1"))
		{
			poCtrl.setAllowCopy(false);//禁止拷贝
			poCtrl.setMenubar(false);//隐藏菜单栏
			poCtrl.webOpen(filePath,OpenModeType.docReadOnly,userInfo.getUserName());
			poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");
		}
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		poCtrl.setTagId("PageOfficeCtrl1");
		//poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		map.put("pageoffice",poCtrl.getHtmlCode("PageOfficeCtrl1"));
		map.put("attachName",attach.getOldName());
		map.put("attachId",attach.getAttachId());
		map.put("runId",runId);
		//--- PageOffice的调用代码 结束 -----
		ModelAndView mv = new ModelAndView("/app/core/office/taohongword");
		return mv;
	}
	
/**
 * 	
 * @Title: openword   
 * @Description: TODO 在线打开word文档
 * @param: request
 * @param: attach
 * @param: openModeType
 * @param: map
 * @param: @return      
 * @return: ModelAndView      
 * @throws
 */
	@RequestMapping(value="/openword", method=RequestMethod.GET)
	public ModelAndView openWord(HttpServletRequest request,Attach attach,String openModeType, Map<String,Object> map,NetDisk netDisk,String path){
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		String filePath="";
		if(StringUtils.isBlank(attach.getAttachId()))
		{
			netDisk.setOrgId(userInfo.getOrgId());
			netDisk = netDiskService.selectOneNetDisk(netDisk);
			filePath = netDisk.getRootPath()+path;
		}else
		{
			attach.setOrgId(userInfo.getOrgId());
			attach = attachService.selectOne(attach);
			filePath = attach.getPath();
		}
		File file = new File(filePath);
		filePath = file.getAbsoluteFile().toString();
		//--- PageOffice的调用代码 开始 -----
		PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
		poCtrl.setServerPage("/poserver.zz");//设置授权程序servlet
		poCtrl.setSaveFilePage("/office/wordsave");//设置保存的action
		if(openModeType.equals("4"))
		{
			poCtrl.webOpen(filePath,OpenModeType.docNormalEdit,userInfo.getUserName());
			poCtrl.addCustomToolButton("保存", "Save()", 1);
			//poCtrl.addCustomToolButton("另存为PDF", "SaveAsPDF()", 1);
		}else if(openModeType.equals("2")||openModeType.equals("3"))
		{
			poCtrl.webOpen(filePath,OpenModeType.docReadOnly,userInfo.getUserName());
			poCtrl.addCustomToolButton("打印", "PrintFile()", 6);
			
		}else if(openModeType.equals("1"))
		{
			poCtrl.setAllowCopy(false);//禁止拷贝
			poCtrl.setMenubar(false);//隐藏菜单栏
			poCtrl.webOpen(filePath,OpenModeType.docReadOnly,userInfo.getUserName());
			poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");
		}
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		//poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		map.put("pageoffice",poCtrl.getHtmlCode("PageOfficeCtrl1"));
		map.put("attachName",attach.getOldName());
		map.put("attachId",attach.getAttachId());
		//--- PageOffice的调用代码 结束 -----
		ModelAndView mv = new ModelAndView("/app/core/office/openword");
		return mv;
	}
	/**
	 * 
	 * @Title: saveFile   
	 * @Description: TODO 文件保存
	 * @param: request
	 * @param: response      
	 * @return: void      
	 * @throws
	 */
	@RequestMapping(value="/wordsave", method=RequestMethod.POST)
	public void wordSaveFile(HttpServletRequest request, HttpServletResponse response){
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		FileSaver fs = new FileSaver(request, response);
		String attachId = fs.getFormField("attachId");
		String extName = fs.getFileExtName();
		Attach attach = new Attach();
		attach.setOrgId(userInfo.getOrgId());
		attach.setAttachId(attachId);
		attach = attachService.selectOne(attach);
		String filePath = attach.getPath();
		File file = new File(filePath);
		filePath = file.getAbsoluteFile().toString();
		int dot = filePath.lastIndexOf('.');
		if ((dot >-1) && (dot < (filePath.length()))) {
			filePath= filePath.substring(0, dot);
		}
		filePath=filePath+extName;
		fs.saveToFile(filePath);
		fs.close();
	}
/**
 * 
 * @Title: excelSaveFile   
 * @Description: TODO 保存execl
 * @param: request
 * @param: response      
 * @return: void      
 * @throws
 */
	@RequestMapping(value="/excelsave", method=RequestMethod.POST)
	public void excelSaveFile(HttpServletRequest request, HttpServletResponse response){
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		FileSaver fs = new FileSaver(request, response);
		String attachId = fs.getFormField("attachId");
		Attach attach = new Attach();
		attach.setOrgId(userInfo.getOrgId());
		attach.setAttachId(attachId);
		attach = attachService.selectOne(attach);
		String filePath = attach.getPath();
		File file = new File(filePath);
		filePath = file.getAbsoluteFile().toString();
		fs.saveToFile(filePath);
		fs.close();
	}
	
	/**
	 * 
	 * @Title: pptSaveFile   
	 * @Description: TODO 保存ppt
	 * @param: request
	 * @param: response      
	 * @return: void      
	 * @throws
	 */
	@RequestMapping(value="/pptsave", method=RequestMethod.POST)
	public void pptSaveFile(HttpServletRequest request, HttpServletResponse response){
		UserInfo userInfo = accountService.getRedisUserInfo(request);
		FileSaver fs = new FileSaver(request, response);
		String attachId = fs.getFormField("attachId");
		Attach attach = new Attach();
		attach.setOrgId(userInfo.getOrgId());
		attach.setAttachId(attachId);
		attach = attachService.selectOne(attach);
		String filePath = attach.getPath();
		File file = new File(filePath);
		filePath = file.getAbsoluteFile().toString();
		fs.saveToFile(filePath);
		fs.close();
	}
}
