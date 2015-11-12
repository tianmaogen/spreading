package com.spreading.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spreading.util.QiniuUtils;
import com.spreading.util.UUIDUtil;
import com.spreading.vo.FileVO;

@Controller
public class UploadAction {
	


	
    @Autowired
    private QiniuUtils qiniuUtils;

	@RequestMapping(value = "/upload.do")
	public String upload(	@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model) {

//		System.out.println("开始");
//		String path = request.getSession().getServletContext().getRealPath("upload");
//		String fileName = file.getOriginalFilename();
//		// String fileName = new Date().getTime()+".jpg";
//		System.out.println(path);
//		File targetFile = new File(path, fileName);
//		if (!targetFile.exists()) {
//			targetFile.mkdirs();
//		}
//
//		// 保存
//		try {
//			file.transferTo(targetFile);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		model.addAttribute("fileUrl", request.getContextPath() + "/upload/" + fileName);
		String imagePath = null;
		try {
			imagePath = saveImages(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String qiNiuPath = qiniuUtils.getImageView(imagePath);
		System.out.println(qiNiuPath);
		model.addAttribute("fileUrl", qiNiuPath);
		return "result";
	}
	
	private String saveImages(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String index = fileName.substring(fileName.lastIndexOf("."));
        // 文件名称
        String imageName = UUIDUtil.getUUID() + index;
        //保存媒体图片部分
        Map<Integer, FileVO> images = new HashMap<Integer, FileVO>();
        InputStream is = file.getInputStream();
        byte[] buffer = IOUtils.toByteArray(is);
        ByteArrayInputStream bai = new ByteArrayInputStream(buffer);
        FileVO vo = new FileVO();
        vo.setFileSize(buffer.length / 1024);
        vo.setIn(bai);
        //存放原图流
        images.put(1, vo);
        for (Integer key : images.keySet()) {
            FileVO vo1 = images.get(key);
            // 保存到物理存储
            qiniuUtils.upload(vo1.getIn(), imageName);
        }
        
        return imageName;
        
        
	}

}
