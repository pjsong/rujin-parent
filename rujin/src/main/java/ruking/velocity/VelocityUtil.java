package ruking.velocity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VelocityUtil 
{
	public static String encUrlUnderscore(String s) throws UnsupportedEncodingException
    {
    	return URLEncoder.encode(s.replaceAll("[\\s]+", "_"), "UTF-8");
    }

	public static String encUrlHyphen(String s) throws UnsupportedEncodingException
    {
    	return URLEncoder.encode(s.replaceAll("[\\s]+", "-"), "UTF-8");
    }

	public static String decodeUrlUnderscore(String s) throws UnsupportedEncodingException
    {
		return URLDecoder.decode(s, "UTF-8").replaceAll("_", " ").replaceAll("-", " ");
    }
	
//	public static String resetProductListUrl(String url, String property)
//	{
//		if("PriceCat".equals(property))property="Price";
//		if(url.indexOf("?")==-1)return url;
//		String[] paras=(url.substring(url.indexOf('?')+1)).split("&");
//		String urlBase = url.substring(0,url.indexOf('?'));
//		for(String s:paras){
//			if(!s.startsWith(property) && urlBase.indexOf('?')==-1)
//				urlBase += "?"+s;
//			else if(!s.startsWith(property) && urlBase.indexOf('?')!=-1)
//				urlBase += "&"+s;
//		}
//		System.out.println("urlBase=" + urlBase);
//		return urlBase;
//    }
	public static String startOverProductListUrl(String url)
	{
		if(url.contains("?"))
		{
			if(url.contains("q="))
			{
				String[] paras=(url.substring(url.indexOf('?') + 1)).split("&");
				String urlBase = url.substring(0, url.indexOf('?'));
				for(String s : paras)
				{
					if(s.startsWith("q="))
						url = urlBase + "?" + s;
				}
				return url;
			}
			else 
			{
				int index = url.indexOf('?');
				url = url.substring(0, index);
				return url;
			}
		}
		else
			return url;
	}
	public static String resetProductSpecialFeaturesList(String url, String selection)
	{
		String SpecialFeatureSelections = url.substring(url.indexOf("SpecialFeatures=")+16);
		String list [] = SpecialFeatureSelections.split(",");
		if(list.length <=1)
		{
			return resetProductListUrl(url, "SpecialFeatures");
		}
		else
		{
			url= url.replace(selection+",", "");
			return url.replace(","+selection, "");
		}		
	}
	
	public static String createProductSpecialFeaturesList(String url, String baseQueryStr, String selection)
	{
		if(!url.contains(selection))
		{
            if(url.indexOf("SpecialFeatures=") == -1)
              url = baseQueryStr + "SpecialFeatures=" + selection;
            else
            {
            	int index = url.indexOf("SpecialFeatures=");
    			String beforePropertyUrl = url.substring(0, index);
    			String afterPropertyUrl = url.substring(index + 16);
    			String thePropertyUrl = "";
    			if(afterPropertyUrl.indexOf("&") == -1)
    			{
    				thePropertyUrl = afterPropertyUrl;
    				afterPropertyUrl = "";
    			}
    			else
    			{
    				thePropertyUrl = afterPropertyUrl.substring(0, afterPropertyUrl.indexOf("&"));
    				afterPropertyUrl = afterPropertyUrl.substring(afterPropertyUrl.indexOf("&"));
    			}
    			thePropertyUrl = "SpecialFeatures=" + thePropertyUrl + "," + selection;
    			url = beforePropertyUrl + thePropertyUrl + afterPropertyUrl;
            }

		}
		
		return url;
	}
	
	public static String resetProductListUrl(String url, String property)
	{
//		if (property != null && property.equals("PriceCat"))
//		{
//			property = "Price";
//		}
//		if (property != null && property.equals("xWattageCat"))
//		{
//			property = "Wattage";
//		}
		
		String rtnUrl = url;
		if (url.indexOf("/" + property + "=") == -1 && url.indexOf("?" + property + "=") == -1 && url.indexOf("&" + property + "=") == -1)
		{
			return rtnUrl;
		}
		else if (url.indexOf("/" + property + "_") != -1)
		{
			int index = url.indexOf("/" + property + "_");
			String beforePropertyUrl = url.substring(0, index);
			String afterPropertyUrl = url.substring(index + property.length());
			if(afterPropertyUrl.indexOf("/") == -1)
			{
				rtnUrl = beforePropertyUrl;
			}
			else
			{
				afterPropertyUrl = afterPropertyUrl.substring(afterPropertyUrl.indexOf("/"));
				rtnUrl = beforePropertyUrl + afterPropertyUrl;
			}
		}
		else if (url.indexOf("?" + property + "=") != -1)
		{
			int index = url.indexOf("?" + property + "=");
			String beforePropertyUrl = url.substring(0, index);
			String afterPropertyUrl = url.substring(index + property.length());
			if(afterPropertyUrl.indexOf("&") == -1)
			{
				rtnUrl = beforePropertyUrl;
			}
			else
			{
				afterPropertyUrl = afterPropertyUrl.substring(afterPropertyUrl.indexOf("&") + 1);
				rtnUrl = beforePropertyUrl + "?" + afterPropertyUrl;
			}
		}
		else if (url.indexOf("&" + property + "=") != -1)
		{
			int index = url.indexOf("&" + property + "=");
			String beforePropertyUrl = url.substring(0, index);
			String afterPropertyUrl = url.substring(index + property.length());
			if(afterPropertyUrl.indexOf("&") == -1)
			{
				rtnUrl = beforePropertyUrl;
			}
			else
			{
				afterPropertyUrl = afterPropertyUrl.substring(afterPropertyUrl.indexOf("&"));
				rtnUrl = beforePropertyUrl + afterPropertyUrl;
			}
		}
		//System.out.println("reset url2 is: " + rtnUrl);
		return rtnUrl;
    }
	
	public static String resetProductListUrl(String url, String property, String propertyValue)
	{
	
		String rtnUrl = url;
		if (url.indexOf("/" + property + "=" + propertyValue) == -1 && url.indexOf("?" + property + "=" + propertyValue) == -1 && url.indexOf("&" + property + "=" + propertyValue) == -1)
		{
			return rtnUrl;
		}
		else if (url.indexOf("/" + property + "_" + propertyValue) != -1)
		{
			int index = url.indexOf("/" + property + "_" + propertyValue);
			String beforePropertyUrl = url.substring(0, index);
			String afterPropertyUrl = url.substring(index + property.length() + 1 + propertyValue.length());
			if(afterPropertyUrl.indexOf("/") == -1)
			{
				rtnUrl = beforePropertyUrl;
			}
			else
			{
				afterPropertyUrl = afterPropertyUrl.substring(afterPropertyUrl.indexOf("/"));
				rtnUrl = beforePropertyUrl + afterPropertyUrl;
			}
		}
		else if (url.indexOf("?" + property + "=" + propertyValue) != -1)
		{
			int index = url.indexOf("?" + property + "=" + propertyValue);
			String beforePropertyUrl = url.substring(0, index);
			String afterPropertyUrl = url.substring(index + property.length() + 1 + propertyValue.length());
			if(afterPropertyUrl.indexOf("&") == -1)
			{
				rtnUrl = beforePropertyUrl;
			}
			else
			{
				afterPropertyUrl = afterPropertyUrl.substring(afterPropertyUrl.indexOf("&") + 1);
				rtnUrl = beforePropertyUrl + "?" + afterPropertyUrl;
			}
		}
		else if (url.indexOf("&" + property + "=" + propertyValue) != -1)
		{
			int index = url.indexOf("&" + property + "=" + propertyValue);
			String beforePropertyUrl = url.substring(0, index);
			String afterPropertyUrl = url.substring(index + property.length() + 1 + propertyValue.length());
			if(afterPropertyUrl.indexOf("&") == -1)
			{
				rtnUrl = beforePropertyUrl;
			}
			else
			{
				afterPropertyUrl = afterPropertyUrl.substring(afterPropertyUrl.indexOf("&"));
				rtnUrl = beforePropertyUrl + afterPropertyUrl;
			}
		}
		//System.out.println("reset url2 is: " + rtnUrl);
		return rtnUrl;
    }
	
	public static void main(String[] args)
	{
		System.out.print(resetProductListUrl("http://localhost:8080/products/Fans_and_More_Ceiling_and_Desk_Fans/Price_Lowest_to_Highest","Price"));
	}
	
   // "hello world!" => Hello world!
   //	"HELLO WORLD!" => Hello world!
   public static String ucfirst(String s)
   {
      if (s == null)
      {
    	  return null;
      }      
      else if (s.equals(""))
      {
    	  return s;
      }
      
      s = s.toLowerCase();
      return s.substring(0, 1).toUpperCase() + s.substring(1);
   }
   
   public static String ucAllFirst(String input)
   {
	   if (input == null)
	   {
		   return null;
	   }
		Pattern pattern = Pattern.compile("[-/\\s]");
		Matcher matcher = pattern.matcher(input);
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (matcher.find())
		{
			int start = matcher.start();
			int end = matcher.end();
			String firstCapStr = VelocityUtil.ucfirst(input.substring(i, start));
			String sep = input.substring(start, end);
			i = end;
			sb.append(firstCapStr).append(sep);
		}
		String firstCapStr = VelocityUtil.ucfirst(input.substring(i));
		sb.append(firstCapStr);
		return sb.toString();
   }

//   public static String getListImage(String productId) throws IOException
//   {
//	   if (productId == null)
//	   {
//		   return null;
//	   }
//	   String tmpl = "%s/products_list1/%s/%s~list.jpg";
//	   return String.format(tmpl, Config.getSetting("imageUrl"), getImageSubDir(productId), productId);
//   }
//
//   public static String getSecureListImage(String productId) throws IOException
//   {
//	   return getListImage(productId).replace("http://", "https://");
//   }
//   
//   public static String getDetailImage(String productId) throws IOException
//   {
//	   String tmpl = "%s/products_dtl1/%s/%s~dtl.jpg";
//	   return String.format(tmpl, Config.getSetting("imageUrl"), getImageSubDir(productId), productId);
//   }
//   public static String getSecureDetailImage(String productId) throws IOException
//   {
//	   String tmpl = "%s/products_dtl1/%s/%s~dtl.jpg";
//	   return String.format(tmpl, Config.getSetting("imageUrl"), getImageSubDir(productId), productId).replace("http://", "https://");
//   }
//
//   public static String getThmbImage(String productId) throws IOException
//   {
//	   if (productId == null)
//	   {
//		   return null;
//	   }
//	   String tmpl = "%s/products_thmb/%s/%s~thmb.jpg";
//	   return String.format(tmpl, Config.getSetting("imageUrl"), getImageSubDir(productId), productId);
//   }  
//
//   public static String getSecureThmbImage(String productId) throws IOException
//   {
//	   return getThmbImage(productId).replace("http://", "https://");
//   }
//
//   public static String getZoomImage(String productId) throws IOException
//   {
//	   String tmpl = "%s/products_zoom/%s/%s~zoom";
//	   return String.format(tmpl, Config.getSetting("imageUrl"), getImageSubDir(productId), productId);
//   }
   
	// given a product id, get the 3 digit folder name for the product
	public static String getImageSubDir(String productId)
	{
		if (productId.length() == 1)
		{
			return "00" + productId;
		}
		else if (productId.length() == 2)
		{
			return "0" + productId;
		}
		else if (productId.length() >= 3)
		{
			return productId.substring(productId.length() - 3);
		}
		else
		{
			 throw new IllegalArgumentException("product invalid " + productId);
		}		
	}	
}