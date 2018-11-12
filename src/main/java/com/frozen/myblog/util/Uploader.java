package com.frozen.myblog.util;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
/**
 * 图片上传工具类
 *
 */
public class Uploader {
	// 输出文件地址
	private String url = "";
	// 状态
	private String state = "";
	// 保存路径
	private String savePath = "static/upload";
	//项目真实路径
	private String realPath;
	//项目根路径
	private String contextPath;
	//上传文件
	private MultipartFile file;
	//文件类型
	private String fileType;
	// 文件允许格式
	private String[] allowFiles = {".gif", ".png", ".jpg", ".jpeg", ".bmp"};
	
	public Uploader() {
		
	}
	
	public Uploader(HttpServletRequest req) {
		// 获取项目的根路径和真实路径
		this.contextPath = req.getContextPath();
		this.realPath = req.getSession().getServletContext().getRealPath("/");
	}
	
	public void upload() {
		
		try {
			if(this.contextPath ==null||this.realPath ==null) {
				throw new BlogException("上传前需要设置项目路径和真实路径");
			}
			//检查文件类型
			String originalName = this.file.getOriginalFilename();
			this.fileType = originalName.substring(originalName.lastIndexOf("."));
			this.checkFileType();
			//得到子文件夹路径
			String path = this.getFolder(this.savePath);
			//存储的随机文件名
			String newFileName = UUID.randomUUID()+this.fileType;
			file.transferTo(new File(this.realPath+"/"+path+"/"+newFileName));
			this.url = this.contextPath+"/"+path+"/"+newFileName;
			this.state="success";
		} catch (Exception e) {
			this.state="error";
			e.printStackTrace();
		} 
	}
	//文件类型检测
	private void checkFileType() throws BlogException {
		for(String type:allowFiles) {
			//若文件符合,返回
			if(type.equalsIgnoreCase(this.fileType))
				return ;
		}
		//不符合,抛出异常
		throw new BlogException("文件类型不符合要求");
	}
	
	//创建年月日文件夹
	private String getFolder(String path) {
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		path +="/"+format.format(new Date()); 
		File dir = new File(this.realPath+"/"+path);
		//若文件夹不存在,则创建
		if(!dir.exists()) {
			dir.mkdirs();
		}
		return path;
	}
	
	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getUrl() {
		return url;
	}


	public String getState() {
		return state;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
