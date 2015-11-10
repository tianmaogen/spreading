package com.spreading.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.fop.ExifRet;
import com.qiniu.api.fop.ImageExif;
import com.qiniu.api.fop.ImageInfo;
import com.qiniu.api.fop.ImageInfoRet;
import com.qiniu.api.fop.ImageView;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.GetPolicy;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;
import com.qiniu.api.rs.URLUtils;




@Service("qiniuUtils")
public class QiniuUtils {
	
	@Value("${file.qiniu.ACCESS_KEY}")
    private  String ACCESS_KEY;
	@Value("${file.qiniu.SECRET_KEY}")
    private  String SECRET_KEY;
	@Value("${file.qiniu.bucketName}")
    private  String bucketName;
	@Value("${file.qiniu.qboxrsctl.private}")
    private  String qboxrsctl_private;
	@Value("${file.qiniu.domain}")
    private  String qiniu_domain;
	
	/**
	 * 上传 
	 * MultipartFile file
	 */
	public  int upload(MultipartFile file,String key){//key:  "2-2.jpg";
		System.out.println("获取到的信息==" + ACCESS_KEY + "===" + SECRET_KEY + "===" + bucketName + "===" + qboxrsctl_private + "===" + qiniu_domain);
		try{
			 Config.ACCESS_KEY = ACCESS_KEY;
	         Config.SECRET_KEY = SECRET_KEY;
	         Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
	         // 请确保该bucket已经存在
	         PutPolicy putPolicy = new PutPolicy(bucketName);
	         String uptoken = putPolicy.token(mac);
	         PutExtra extra = new PutExtra();
	         /* 根据路径上传
	          *
	            String localFile = "C:\\Users\\Administrator\\Desktop\\2-2.jpg";
	         	PutRet ret = IoApi.putFile(uptoken, key, localFile, extra);
	          */
	         
	         /*
	          * 根据文件上传图片
	          * 
	             InputStream input = new FileInputStream(file);
	             IoApi.Put(uptoken, key, input, extra);
	          */

	         InputStream reader = file.getInputStream();
	         PutRet ret =  IoApi.Put(uptoken, key, reader, extra);
	         int status= ret.statusCode;
	        
	         String keyStr=ret.getKey();
	         System.out.print(keyStr);
	         System.out.print(status);
	         return status;
		}catch(Exception ex){
			ex.printStackTrace();
			return 599;
		}
		 
	}
	
	
	
	
	/**
	 * 上传 
	 * File
	 */
	public  void upload(File file,String key){//key:  "2-2.jpg";
		try{
			 Config.ACCESS_KEY = ACCESS_KEY;
	         Config.SECRET_KEY = SECRET_KEY;
	         Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
	         // 请确保该bucket已经存在
	         PutPolicy putPolicy = new PutPolicy(bucketName);
	         String uptoken = putPolicy.token(mac);
	         PutExtra extra = new PutExtra();
	         /* 根据路径上传
	          *
	            String localFile = "C:\\Users\\Administrator\\Desktop\\2-2.jpg";
	         	PutRet ret = IoApi.putFile(uptoken, key, localFile, extra);
	          */
	         
	         /*
	          * 根据文件上传图片
	          * 
	             InputStream input = new FileInputStream(file);
	             IoApi.Put(uptoken, key, input, extra);
	          */

	         InputStream reader = new FileInputStream(file);
	         PutRet ret =  IoApi.Put(uptoken, key, reader, extra);
	         int status= ret.statusCode;
	         String keyStr=ret.getKey();
	         System.out.print(keyStr);
	         System.out.print(status);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		 
	}
	
	
	
	/**
	 * 上传 
	 * File
	 */
	public  void upload(InputStream inpustream,String key){//key:  "2-2.jpg";
		try{
			Config.ACCESS_KEY = ACCESS_KEY;
			Config.SECRET_KEY = SECRET_KEY;
			Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
			// 请确保该bucket已经存在
			PutPolicy putPolicy = new PutPolicy(bucketName);
			String uptoken = putPolicy.token(mac);
			PutExtra extra = new PutExtra();
			PutRet ret = IoApi.Put(uptoken, key, inpustream, extra);
			int status = ret.statusCode;
			String keyStr = ret.getKey();
			System.out.print(keyStr);
			System.out.print(status);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		 
	}	
	
	/**
	 * 下载
	 */
	public  void downLoad(String key){//key:"2-2.jpg"
		try{
			String type= qboxrsctl_private;
			if(type.equals("0")){//公开
			    String url = qiniu_domain+ "/" + key;
			    System.out.println(url);
			}else if(type.equals("1")){//私有空间
				Config.ACCESS_KEY = ACCESS_KEY;
		        Config.SECRET_KEY = SECRET_KEY;
				Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
			    String baseUrl = URLUtils.makeBaseUrl(qiniu_domain, key);
			    GetPolicy getPolicy = new GetPolicy();
			    String downloadUrl = getPolicy.makeRequest(baseUrl, mac);
			    System.out.println(downloadUrl);
			    System.out.println(baseUrl);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	
	/**
	 * 删除单个文件
	 */
	public  void del(String key){//key:"2-2.jpg"
		try{
				Config.ACCESS_KEY = ACCESS_KEY;
		        Config.SECRET_KEY = SECRET_KEY;
		        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
			    RSClient client = new RSClient(mac);
		        client.delete(bucketName, key);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	
	/**
	 * 查看图片属性
	 */
	public  void getInfo(String key){//key:"2-2.jpg"
		try{
			String type=qboxrsctl_private;
			if(type.equals("0")){//公开
				  String url = qiniu_domain + "/" + key;
			      ImageInfoRet ret = ImageInfo.call(url);
			      int c=ret.getStatusCode();
			      System.out.print(c);
			}else if(type.equals("1")){//私有空间
				Config.ACCESS_KEY = ACCESS_KEY;
		        Config.SECRET_KEY = SECRET_KEY;
		        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
			    String baseUrl = URLUtils.makeBaseUrl(qiniu_domain,key);
			    GetPolicy getPolicy = new GetPolicy();
			    String url = getPolicy.makeRequest(baseUrl, mac);
			      ImageInfoRet ret = ImageInfo.call(url);
			      int c=ret.getStatusCode();
			      System.out.print(c);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		 
	}
	
	/**
	 * 查看图片EXIF信息
	 */
	public  void getEXIF(String key){//key:"2-2.jpg";
		try{
			
			String type= qboxrsctl_private;
			if(type.equals("0")){//公开
					 String url =qiniu_domain + "/" + key;
					 ExifRet ret = ImageExif.call(url);
				      int c=ret.getStatusCode();
				      System.out.print(c);
			}else if(type.equals("1")){//私有空间
					Config.ACCESS_KEY = ACCESS_KEY;
			        Config.SECRET_KEY = SECRET_KEY;
			        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
				    String baseUrl = URLUtils.makeBaseUrl(qiniu_domain, key);
				    GetPolicy getPolicy = new GetPolicy();
				    String url = getPolicy.makeRequest(baseUrl, mac);
					ExifRet ret = ImageExif.call(url);
				    int c=ret.getStatusCode();
				    System.out.print(c);
				    int cc= ret.hashCode();
				    System.out.print(cc);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		 
	}
	
	
	/**
	 * 查看图片预览
	 */
	public  String getImageView(String key){
		return getImageView(key,null,null);
	}
	public  String getImageView(String key,Integer w,Integer h){//key:"2-2.jpg"  w:100(宽)  h:200(高)
		String imageUrl="";
		try{
			String type=qboxrsctl_private;
			if(type.equals("0")){//公开
				 String url = qiniu_domain + "/" + key;
				if(w==null && h==null){
					imageUrl=url;
				}else{
					 ImageView iv = new ImageView();
				        iv.mode = 2 ;//1表示，从中央裁剪为指定宽高  2:限定目标缩略图的长和宽，将缩略图的大小限定在指定的宽高矩形内。
				        iv.width = w ;
				        iv.height = h ;
				        //iv.quality = 1 ;
				       // iv.format = "jpg" ;
				        imageUrl=iv.makeRequest(url);
				       // String str= iv.makeParams();
				}
			}else if(type.equals("1")){//私有
				 String baseUrl =  URLUtils.makeBaseUrl(qiniu_domain, key);// 其中domain是该bucket任意关联的一个domain，key是访问图片（不是缩略图）的键
				 
			      GetPolicy getPolicy = new GetPolicy();// 下载策略类
			      Config.ACCESS_KEY = ACCESS_KEY;
			      Config.SECRET_KEY = SECRET_KEY;
			      Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
				  if(w==null && h==null){
						 imageUrl=getPolicy.makeRequest(baseUrl, mac);
				   }else{
						ImageView iv = new ImageView();// 缩略图类
						iv.mode = 2 ;
					    iv.width = w ;
					    iv.height = h ;
					    //  iv.quality = 100 ;
					    //  iv.format = "jpg" ;
					    String thumbUrl = iv.makeRequest(baseUrl);// 得到缩略图url（对于公开的bucket，到此为止就可以了）
						imageUrl=getPolicy.makeRequest(thumbUrl, mac);
					}
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.print(imageUrl);
		 return imageUrl;
	}
	
	/**
	 * 复制单个文件
	 */
	public  void copyImage(String srcKey,String destKey){
			Config.ACCESS_KEY = ACCESS_KEY;
	        Config.SECRET_KEY = SECRET_KEY;
	        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
	        RSClient client = new RSClient(mac);
	        com.qiniu.api.net.CallRet c=client.copy(bucketName, srcKey,  bucketName, destKey);
	        System.out.print("复制："+c.getStatusCode());
	}
 
}
