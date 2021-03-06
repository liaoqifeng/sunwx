package com.koch.compent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.gson.util.PropertiesUtil;
import com.koch.bean.FileInfo;
import com.koch.bean.Setting;
import com.koch.util.SettingUtils;

@Component("localStorage")
public class LocalStorage extends FileStorage implements ServletContextAware {

	@Value("${image.project}")
	private String imageProject;
	@Value("${system.project}")
	private String systemProject;
	
	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public String getName() {
		return "本地文件存储";
	}

	public String getVersion() {
		return "1.0";
	}

	public String getAuthor() {
		return "Administrator";
	}

	public String getSiteUrl() {
		return "";
	}

	public String getInstallUrl() {
		return null;
	}

	public String getUninstallUrl() {
		return null;
	}

	public List<FileInfo> browser(String path) {
		Setting setting = SettingUtils.get();
		List list = new ArrayList();
		
		String absPath = this.servletContext.getRealPath(path);
		if(StringUtils.isNotEmpty(imageProject)){
			absPath = absPath.replaceAll(servletContext.getContextPath().replaceAll("/", ""),imageProject);
		}
		
		File file = new File(absPath);
		if ((file.exists()) && (file.isDirectory())) {
			for (File f : file.listFiles()) {
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(f.getName());
				fileInfo.setIsDirectory(Boolean.valueOf(f.isDirectory()));
				if(fileInfo.getIsDirectory()){
					fileInfo.setUrl(f.getName());
				}else{
					String imgUrl = StringUtils.isNotEmpty(imageProject) ? (PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/"+imageProject+path) : (setting.getSiteUrl() + path);
					fileInfo.setUrl(imgUrl + f.getName());
				}
				fileInfo.setSize(Long.valueOf(f.length()));
				fileInfo.setLastModified(new Date(f.lastModified()));
				list.add(fileInfo);
			}
		}
		return list;
	}

	public String getSettingUrl() {
		return null;
	}

	public String getUrl(String path) {
		Setting setting = SettingUtils.get();
		return StringUtils.isNotEmpty(imageProject) ? (PropertiesUtil.getProperty(PropertiesUtil.BASE_URL)+"/"+imageProject + path) : (setting.getSiteUrl() + path);
	}

	public void upload(String path, File file, String contentType) {
		String absPath = this.servletContext.getRealPath(path);
		if(StringUtils.isNotEmpty(imageProject)){
			absPath = absPath.replaceAll(servletContext.getContextPath().replaceAll("/", ""),imageProject);
		}
		File localFile = new File(absPath);
		File cacheFile = new File(System.getProperty("java.io.tmpdir") + "/" + systemProject + path);
		
		try {
			FileUtils.moveFile(file, localFile);
			if (!cacheFile.getParentFile().exists()) {
				cacheFile.getParentFile().mkdirs();
			}
			FileUtils.copyFile(localFile, cacheFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
