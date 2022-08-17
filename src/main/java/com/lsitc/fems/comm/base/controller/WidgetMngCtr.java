/*
* 상기 프로그램에 대한 저작권을 포함한 지적재산권은 LS ITC에 있으며,
* LS ITC가 명시적으로 허용하지 않은 사용, 복사, 변경, 제3자에의 공개, 배포는 엄격히 금지되며,
* LS ITC의 지적재산권 침해에 해당됩니다.
* (Copyright ⓒ 2021 LS ITC. All Rights Reserved| Confidential)               
* 
* You are strictly prohibited to copy, disclose, distribute, modify, or use
* this program in part or as a whole without the prior written consent of
* LS ITC Business unit. LS ITC Business unit., owns the intellectual property rights in
* and to this program.
* (Copyright ⓒ 2021 LS ITC Business unit. All Rights Reserved| Confidential)
* Author    : LS ITC
* Created   : 2021-04-23 17:58:11
*/
 
package com.lsitc.fems.comm.base.controller;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lsitc.core.base.BaseParam;
import com.lsitc.core.base.BaseResponse;
import com.lsitc.fems.comm.base.service.WidgetMngSvc;
import com.lsitc.fems.comm.base.vo.WidgetVo;

@RequestMapping("/comm/base/WidgetMngCtr")
@Controller
public class WidgetMngCtr {

    @Autowired
    private WidgetMngSvc widgetMngSvc;

    /**
     * @methodName  : selectWidget
     * @date        : 2022.05.31
     * @desc        : 프로그램 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectWidget", method=RequestMethod.POST)
    @ResponseBody
    public Object selectWidget(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("widgetData", widgetMngSvc.selectWidget(paramMap.getParams()));
        return result;
    }
    
    /**
     * @methodName  : saveWidget
     * @date        : 2022.05.31
     * @desc        : 프로그램 저장
     * @param paramMap
     * @return
     */
	
	@RequestMapping(value="/saveWidget", method=RequestMethod.POST)
	@ResponseBody 
	public Object savePrgm(@RequestBody BaseParam paramMap){
		BaseResponse result = new BaseResponse();
		widgetMngSvc.saveWidget(paramMap.getDs("dsWidget", WidgetVo.class)); return result; 
	}
	
    /**
     * @methodName  : selectNotMyWidget
     * @date        : 2022.05.31
     * @desc        : 프로그램 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectNotMyWidget", method=RequestMethod.POST)
    @ResponseBody
    public Object selectNotMyWidget(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("widgetData", widgetMngSvc.selectNotMyWidget(paramMap.getParams()));
        return result;
    }

    /**
     * @methodName  : selectMyWidget
     * @date        : 2022.05.31
     * @desc        : 프로그램 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectMyWidget", method=RequestMethod.POST)
    @ResponseBody
    public Object selectMyWidget(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("widgetData", widgetMngSvc.selectMyWidget(paramMap.getParams()));
        return result;
    }

    /**
     * @methodName  : saveMyWidget
     * @date        : 2022.05.31
     * @desc        : 프로그램 저장
     * @param paramMap
     * @return
     */
	
	@RequestMapping(value="/saveMyWidget", method=RequestMethod.POST)
	@ResponseBody 
	public Object saveMyWidget(@RequestBody BaseParam paramMap){
		BaseResponse result = new BaseResponse();
		widgetMngSvc.saveMyWidget(paramMap.getDs("dsWidget", WidgetVo.class)); return result; 
	}
	
    /**
     * @methodName  : selectMyWidget
     * @date        : 2022.05.31
     * @desc        : 프로그램 조회
     * @param paramMap
     * @return
     */
    @RequestMapping(value="/selectWidgetPrgmUrl", method=RequestMethod.POST)
    @ResponseBody
    public Object selectWidgetPrgmUrl(@RequestBody BaseParam paramMap){
        BaseResponse result = new BaseResponse();
        result.add("widgetData", widgetMngSvc.selectWidgetPrgmUrl(paramMap.getParams()));
        return result;
    }
	
    
	@RequestMapping(value="/selectFileToBase64", method=RequestMethod.POST)
	@ResponseBody
	public Object selectFileToBase64(MultipartHttpServletRequest mtfRequest) throws IOException{
		Iterator files = mtfRequest.getFileNames();
		byte[] encodedImage  = null;
		String fileName = "";
		while(files.hasNext()) {
			fileName = (String) files.next();
			MultipartFile multiFile = mtfRequest.getFile(fileName);					
			BufferedImage image = ImageIO.read(multiFile.getInputStream());					
			ByteArrayOutputStream baos = new ByteArrayOutputStream();					
			ImageIO.write(image, "png", baos);					
			encodedImage = Base64.encodeBase64(baos.toByteArray());
			//byte[] blob = multiFile.getBytes();					
			//System.out.println(multiFile.getName()+" / "+encodedImage);
			baos.close();
		}
		BaseResponse result = new BaseResponse();
        result.add("apndFileUuid", encodedImage);
        return result;
		
	}
	
	public static byte[] base64Enc(byte[] buffer) {
		return Base64.encodeBase64(buffer);
	}
}