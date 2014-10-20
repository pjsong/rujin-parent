package ruking.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Util {
	public static String getNoNull(Object o){
		return o==null?"":o.toString();
	}
	
	public static void redirect302(HttpServletResponse response, String targetUrl) throws IOException
	{
        // set the no cache control header, HTTP 1.1 standard
        //resp.setHeader("Cache-Control", "no-cache");
        // this is to allow user to go back to previous page after a post operation in IE
        // this fixes the error:
        //  Error Message: Warning: Page Has Expired: The Page You Requested...
        response.setHeader("Cache-Control", "public");

        // set the no cache header, HTTP 1.0 standard
        response.setHeader("Pragma", "no-cache");

        // for user-agents that ignore the above settings
        response.setHeader("Expires", "Thu, 01 Jan 1970 00.00.00 GMT");

        response.sendRedirect(targetUrl);
	}
	// get relative url
	// example: 
	// original: http://localhost:8080/products/indoor_lighting_chandeliers
	// relative: /products/indoor_lighting_chandeliers
	public static String getRelativeUrl(HttpServletRequest request) throws Exception
	{
		StringBuffer wholeUrl = request.getRequestURL();
		String queryString = request.getQueryString();
		int i = wholeUrl.indexOf("://");
		if (i > 0)
		{
			int j = wholeUrl.indexOf("/", i + 3);
			String url = wholeUrl.substring(j);
			if (queryString != null)
			{
				url += "?" + queryString;
			}
			return url;
		}
		else
		{
			// this should never happen though!
			return wholeUrl.toString();
		}
	}
	
    //return the referral base url 
	public static String getReferralBaseUrl(HttpServletRequest request) throws Exception
	{
    	String referralUrl = request.getHeader("Referer");
    	if(referralUrl == null)
    	{
    		return "";
    	}
    	
    	if (referralUrl.length() <= 8)
    	{
    		return referralUrl;
    	}
    	else
    	{
    		int indexOfSlash = referralUrl.indexOf("/", 8);
    		if (indexOfSlash > 0)
    		{
    			return referralUrl.substring(0, indexOfSlash);
    		}
    		else
    		{
    			return referralUrl;
    		}
    	}
	}
	public static String getReferralUrl(HttpServletRequest request) throws Exception
	{
    	String referralUrl = request.getHeader("Referer");
    	return referralUrl == null ? "" : referralUrl;
	}
    //return the referral extended url 
	public static String getReferralExtendedUrl(HttpServletRequest request) throws Exception
	{
    	String referralUrl = request.getHeader("Referer");
    	if(referralUrl == null)
    	{
    		return "";
    	}
    	
    	if (referralUrl.length() <= 8)
    	{
    		return "";
    	}
    	else
    	{
    		int indexOfSlash = referralUrl.indexOf("/", 8);
    		if (indexOfSlash > 0)
    		{
    			return referralUrl.substring(indexOfSlash);
    		}
    		else
    		{
    			return "";
    		}
    	}
	}
	
	public static String getIP(HttpServletRequest request) throws IOException
	{
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null)
        {
        	return "";
        }
        
        if (ip.length() > 50)
        {
        	ip = ip.substring(0, 50);
        }
        return ip;
	}
	
	public static boolean isAdministrator(String loginName){
		return loginName.equals("孙迪辉") || loginName.equals("彭劲松") || loginName.equals("欧老总");
	}
}
