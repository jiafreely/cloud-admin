package com.cloud.service.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ip请求工具类
 * 
 * @author hkq
 * 
 */
public class IpUtil {

    private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);
	public static boolean isIP(String addr) {
		if(addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
			return false;
		}
		/**
		 * 判断IP格式和范围
		 */
		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pat = Pattern.compile(rexp);
		Matcher mat = pat.matcher(addr);
		boolean ipAddress = mat.find();
		return ipAddress;
	}
	/**
	 * 获取访问者IP
	 * 
	 * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
	 * 如果还不存在则调用Request .getRemoteAddr()。
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request){
		String ip = "";
		try{
			ip = request.getHeader("X-Real-IP");
			if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
			ip = request.getHeader("X-Forwarded-For");
			if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
				// 多次反向代理后会有多个IP值，第一个为真实IP。
				int index = ip.indexOf(',');
				if (index != -1) {
					return ip.substring(0, index);
				} else {
					return ip;
				}
			} else {
				return request.getRemoteAddr();
			}
		}catch (Exception e) {
            logger.error("获取客户端ip异常", e);
		}
		return ip;
		
	}	
	/**
	 * 解决安全测试伪造IP
	 * 现假设从用户终端到一个或多个代理服务器，得到的IP格式:ip1,ip2,ip3,...
	 * ip1为用户的真正IP，其他的为代理的IP
	 * 左边开头的认为用户终端IP，越向右边，是越靠近服务器的代理IP
	 */
	public static String getIpAddrLink(HttpServletRequest request){
		String ipLink="";
		try{
			String realIp="";
			String realIpTemp = request.getHeader("X-Real-IP");
			if (!StringUtils.isBlank(realIpTemp) && !"unknown".equalsIgnoreCase(realIpTemp)) {
				realIp=realIpTemp;
			}
			String forwardIp="";
			String forwardIpTemp = request.getHeader("X-Forwarded-For");
			if (!StringUtils.isBlank(forwardIpTemp) && !"unknown".equalsIgnoreCase(forwardIpTemp)) {
				// 多次反向代理后会有多个IP值，第一个为真实IP。
				forwardIp=forwardIpTemp;
			}
            String ip= request.getRemoteAddr();
            logger.info("realIp:"+realIp+" forwardIp:"+forwardIp+" ip:"+ip);
            if(!StringUtils.isBlank(realIp) ) {
            	
            	ipLink=realIp;
            	
            }
            if(!StringUtils.isBlank(forwardIp)) {
            	String temp=ipLink;
                int index=forwardIp.indexOf(temp);
                if(index>=0) {
                	//认为forwardIp已经包含realIp，则不要realIp
                	ipLink=forwardIp;
                }else {
                	ipLink=realIp+","+forwardIp;
                }
            }
      
            if(!StringUtils.isBlank(ip) ) {
            	if(StringUtils.isBlank(ipLink) ) {
            		ipLink=ip;
            	}else {
            	  int index=ipLink.lastIndexOf(ip);
            	  if(index<0 ) {
                 	ipLink=ipLink+","+ip;
                  }
            	}
            }
            logger.info("ip:"+ip);
		}catch (Exception e) {
            logger.error("获取客户端ip异常", e);
		}
		return ipLink;
		
	}	
	
	public static void main(String args[]) {
		
		String configIPLink="";
		String realIpLink="";
		boolean flag;
//		configIPLink="";
//		realIpLink="127.0.0.1";
//		boolean flag=checkIP(configIPLink,realIpLink);
//		System.out.println("不配置实际一级"+flag);
//		
//
//        configIPLink="";
//	    realIpLink="10.111.26.195,127.0.0.1";;
//		flag=checkIP(configIPLink,realIpLink);
//		System.out.println("不配置实际多级"+flag);
//		
//		configIPLink="10.111.26.195";
//	    realIpLink="10.111.26.195";;
//		flag=checkIP(configIPLink,realIpLink);
//		System.out.println("配置一级实际一级"+flag);
		
//		configIPLink="10.111.26.195";
//	    realIpLink="10.111.26.196,10.111.26.195";;
//		flag=checkIP(configIPLink,realIpLink);
//		System.out.println("配置一级实际多级"+flag);
		
		configIPLink="10.111.26.196,10.111.26.195";
	    realIpLink="10.111.26.196,10.111.26.195";
		flag=checkIP(configIPLink,realIpLink);
		System.out.println("配置多级实际多级"+flag);
		
		configIPLink="10.111.26.196,10.111.26.197,10.111.26.195";
	    realIpLink="10.111.26.196,10.111.26.195";
		flag=checkIP(configIPLink,realIpLink);
		System.out.println("配置多级实际多级"+flag);
		
		configIPLink="10.111.26.196,10.111.26.197,10.111.26.195";
	    realIpLink="10.111.26.196,10.111.26.195";
		flag=checkIP(configIPLink,realIpLink);
		System.out.println("配置多级实际多级"+flag);
		
		configIPLink="10.111.26.196,10.111.26.197,10.111.26.250";
	    realIpLink="10.111.26.196,10.111.26.195";
		flag=checkIP(configIPLink,realIpLink);
		System.out.println("配置多级实际多级"+flag);
		
		configIPLink="10.111.26.196,10.111.26.197,10.111.26.250,10.111.26.195";
	    realIpLink="10.111.26.196,10.111.26.197,10.111.26.250,10.111.26.195";
		flag=checkIP(configIPLink,realIpLink);
		System.out.println("配置多级实际多级"+flag);
		
		configIPLink="10.111.26.196,10.111.26.197,10.111.26.250,10.111.26.195";
	    realIpLink="10.111.26.198,10.111.26.197,10.111.26.250,10.111.26.195";
		flag=checkIP(configIPLink,realIpLink);
		System.out.println("配置多级实际多级伪造"+flag);
		
		
		configIPLink="10.111.26.196,10.111.26.197,10.111.26.250,10.111.26.195";
	    realIpLink="10.111.26.197,10.111.26.196,10.111.26.250,10.111.26.195";
		flag=checkIP(configIPLink,realIpLink);
		System.out.println("配置多级实际多级伪造乱顺序"+flag);
		
		configIPLink="10.111.26.196,10.111.26.197,10.111.26.250,10.111.26.195";
	    realIpLink="10.111.26.195";
		flag=checkIP(configIPLink,realIpLink);
		System.out.println("配置多级实际一级"+flag);
		
		
		configIPLink="10.111.26.196,10.111.26.197,10.111.26.250,10.111.26.195";
	    realIpLink="10.111.26.250,10.111.26.195";
		flag=checkIP(configIPLink,realIpLink);
		System.out.println("配置多级实际一级"+flag);
		
		
		configIPLink="10.111.26.196,10.111.26.197,10.111.26.250,10.111.26.195";
	    realIpLink="10.111.26.251,10.111.26.195";
		flag=checkIP(configIPLink,realIpLink);
		System.out.println("配置多级实际一级"+flag);
		
		configIPLink="10.108.54.82,10.111.26.195,10.111.26.196";
	    realIpLink="1.1.1.1,10.111.26.195,10.111.26.195,10.111.26.196";
		flag=checkIP(configIPLink,realIpLink);
		System.out.println("配置多级实际多级"+flag);
		
	}
	/**
	 * 
	 * @param configIPLink 配置的IP，从左至右  用户IP可多个，用逗间开+“，”+代理IP也可多个用逗号间开
	 * @param realIpLink 实际取到的调用IP，也认为是从左到右调用，然后IP从左到右 ，最右边接近服务器
	 * @return
	 */
	public static boolean checkIP(String configIPLink,String realIpLink) {
		//不配置不限IP
		if(StringUtils.isBlank(configIPLink)) {
			return true;
		}
		
		int rIndex=realIpLink.lastIndexOf(",");
		int cIndex=configIPLink.lastIndexOf(",");
        logger.info("configIPLink:"+configIPLink+" realIpLink:"+realIpLink);
        logger.info("rIndex:"+rIndex+" cIndex:"+cIndex);
		//只配了一个IP
		if(cIndex<0) {
			//实际只传一个IP
			if(rIndex<0) {
				if(realIpLink.equals(configIPLink)) {
					return true;
				}else {
					return false;
				}
			}else {//配置是一个IP，实际多级调用,则只取实际的，最右边的IP来比较
				String nearIp=realIpLink.substring(rIndex+1, realIpLink.length());
                logger.info("nearIp:"+nearIp);
				if(nearIp.equals(configIPLink)) {
					return true;
				}else {
					return false;
				}
			}
		}else {
			if(rIndex<0) {//只有一个实际的IP，则实际IP存在配置中，则返回true
				int index=configIPLink.indexOf(realIpLink);
				if(index>=0) {
					return true;
				}else {
					return false;
				}
			}else {
				String realNearIp=realIpLink.substring(rIndex+1, realIpLink.length());
				String configNearIp=configIPLink.substring(cIndex+1, configIPLink.length());
				
				if(realNearIp.equals(configNearIp)) {
					String realFarIp=realIpLink.substring(0,rIndex);
					String configFarIp=configIPLink.substring(0,cIndex);
					return checkIP(configFarIp,realFarIp);
				}else {
					return false;
				}
			}
		}

	}

}
