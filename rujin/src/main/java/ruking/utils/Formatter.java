package ruking.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.io.IOException;
import java.math.*;

import org.apache.commons.lang.WordUtils;

public class Formatter 
{
	public static String trimWithDots(String input, int maxlength) throws IOException
	{
        if (input != null && input.length() > maxlength && maxlength > 3)
        {
        	input = input.substring(0, maxlength - 3) + "...";
        }
        return input;
	}

  public static String capFully(String s)
  {
	  return org.apache.commons.lang.WordUtils.capitalizeFully(s);
  }
  
  public static String filterMenuHeader(String menuItem0)
  {
	  int maxChars = 13;
	  if (menuItem0.length() > maxChars)
	  {
		  menuItem0 = menuItem0.substring(0, maxChars) + "...";
	  }
	  return WordUtils.capitalize(menuItem0);
  }
	
  public static String currency(String amount)
  {
	  float f;
	  try
	  {
		  f = Float.parseFloat(amount);
	  }
	  catch (Exception e)
	  {
		  f = 0.0f;
	  }
	
	  return currency(f);
  }
  
  public static String formatCount(Object count)
  {
	  String countRtn = "";
	  String countStr = count.toString();
	  String countRight = "0";
	  if(countStr.indexOf(".") != -1)
	  {
		  countRight = countStr.substring(countStr.indexOf(".") + 1);
		  double countRightNumber = Double.parseDouble(countRight);
		  if(countRightNumber > 0)
		  {
			  countRtn = countStr;
		  }
		  else
		  {
			  countRtn = countStr.substring(0, countStr.indexOf("."));
		  }
	  }
	  else
	  {
		  countRtn = countStr;
	  }
	  
	  return countRtn;
  }
  
  public static String getPluralitySuffix(Double count)
  {
	  String suffix = "";
	  if(count > 1)
	  {
		  suffix = "s";
	  }
	  
	  return suffix;
  }

  public static String currencyLinePrice(int qty, double price)
  {
	  NumberFormat f = DecimalFormat.getInstance();
	  f.setMaximumFractionDigits(2);
	  f.setMinimumFractionDigits(2);
	
	  return "$" + f.format(new Double(qty * price));
  }
  
  public static String currency(double amount)
  {
	NumberFormat f = DecimalFormat.getInstance();
	f.setMaximumFractionDigits(2);
	f.setMinimumFractionDigits(2);
    return "$" + f.format(new Double(amount));
  }

  public static String showPrice(double amount)
  {
		NumberFormat f = DecimalFormat.getInstance();
		f.setMaximumFractionDigits(2);
		f.setMinimumFractionDigits(2);
	    return f.format(new Double(amount));
  }

  public String pad(String string, int width) {
    if (string.length() >= width) {
      return string.substring(0, width);
    }

    StringBuffer output = new StringBuffer(string);
    for (int i=0; i < (width - string.length()); i++) {
      output.append(' ');
    }

    return output.toString();
  }

  //mm/dd/yyyy to yyyy-mm-dd
  public static String convertDate(String inputdate, String sourcetaken, String targettaken){	
	  if(inputdate == null)
		  return null;
		  
	  String outputdate = "";
	  if(inputdate.indexOf(" ") != -1)
	  {
		  inputdate = inputdate.substring(0, inputdate.indexOf(" "));
	  }
	  if(inputdate!=null&&inputdate.indexOf(sourcetaken)!=-1){
		  outputdate = inputdate.substring(inputdate.lastIndexOf(sourcetaken)+1)
		             + targettaken + inputdate.substring(0,inputdate.indexOf(sourcetaken))
		             + targettaken + inputdate.substring(inputdate.indexOf(sourcetaken)+1,inputdate.lastIndexOf(sourcetaken));
	  }
	  return outputdate;
  }
  
  //yyyy-mm-dd to mm/dd/yyyy
  public static String convertDate2(Object input, String sourcetaken, String targettaken){
	  if(input == null)
		  return null;
	  
	  String inputdate = "" + input;
	  String outputdate = "";
	  if(inputdate.indexOf(" ") != -1)
	  {
		  inputdate = inputdate.substring(0, inputdate.indexOf(" "));
	  }
	  if(inputdate!=null&&inputdate.indexOf(sourcetaken)!=-1){
		  outputdate = inputdate.substring(inputdate.indexOf(sourcetaken)+1,inputdate.lastIndexOf(sourcetaken))
		             + targettaken + inputdate.substring(inputdate.lastIndexOf(sourcetaken)+1)
		             + targettaken + inputdate.substring(0,inputdate.indexOf(sourcetaken));
	  }
	  return outputdate;
  }
  
  public static String getNowDate()
  {
      Date objDate = new Date();
      SimpleDateFormat objSDateFormat= new SimpleDateFormat("MM/dd/yyyy");
      String strConstructDate = objSDateFormat.format(objDate);
      return strConstructDate;	
  }
  
  public static String getNowTime()
  {
      Date objDate = new Date();
      SimpleDateFormat objSDateFormat= new SimpleDateFormat("HH:mm");
      String strConstructDate = objSDateFormat.format(objDate);
      return strConstructDate;	
  }
  
  public static String trimFormat(Date date,String pattern) throws IllegalArgumentException {
	  if(date==null)
	      throw new IllegalArgumentException("date");
	  if(pattern==null)
	      throw new IllegalArgumentException("format");
	  SimpleDateFormat objSDateFormat= new SimpleDateFormat(pattern);
	  return objSDateFormat.format(date);
  }
  
  public static ArrayList getStringTokens(String inputstr, String token){
	  ArrayList rtn = new ArrayList();
	  if(inputstr != null){
		  StringTokenizer st = new StringTokenizer(inputstr,token);
		  while (st.hasMoreTokens()){
			  rtn.add(st.nextElement());
		  }
	  }
	  return rtn;
  }
  
  public static String convertNull(Object inputstr){
	  return inputstr == null||inputstr.equals("null") ? "":"" + inputstr;
  }
  
  public static double twoNumberAdd(double strFirst, double strSecond) {
	BigDecimal objBigDecimalFirst = new BigDecimal(strFirst);
	BigDecimal objBigDecimalSecond = new BigDecimal(strSecond);
	return (objBigDecimalFirst.add(objBigDecimalSecond).doubleValue());
  }
  public static BigDecimal twoNumberSub(double strFirst, double strSecond,int scale) {
	BigDecimal objBigDecimalFirst = new BigDecimal(strFirst);
	BigDecimal objBigDecimalSecond = new BigDecimal(strSecond);
	BigDecimal ret = objBigDecimalFirst.subtract(objBigDecimalSecond);
	ret = ret.setScale(scale, BigDecimal.ROUND_HALF_UP);
	return ret;
 }	  
  
  public static BigDecimal twoNumberDivide(double first, double second,int scale) {
	  if(second-0.0f < 0.0001)return new BigDecimal(0);
	  double dret = first * 100 / second;
	  BigDecimal ret = new BigDecimal(dret);
	  ret = ret.setScale(scale, BigDecimal.ROUND_HALF_UP);
	  return ret;
	 }	 
  
  public static double twoNumberMul(double strFirst, double strSecond) {
	BigDecimal objBigDecimalFirst = new BigDecimal(strFirst);
	BigDecimal objBigDecimalSecond = new BigDecimal(strSecond);
	return (objBigDecimalFirst.multiply(objBigDecimalSecond).doubleValue());
  }
	  
  public static String numChgStr2(double strNumber) {
	DecimalFormat df2 = new DecimalFormat("########.00");
	return df2.format(strNumber);
  }
  
  public static int mod(int i, int j) {
	return i%j;
  }
  public static String phone(String phone) {
		return phone.length()==10?phone.substring(0,3)+"-"+phone.substring(3,6)+"-"+phone.substring(6):phone;
	  }
  public static String replaceWithStr(String sourceStr, String ch, String reStr) {
	    if (sourceStr == null) {
	      sourceStr = "";
	      return (sourceStr);
	    }

	    int flag = 0;
	    int len = sourceStr.length();
	    String str1 = "", strTemp = "";
	    String str2 = sourceStr;
	    int addr = 0;
	    while (flag == 0) {
	      if (str2.indexOf(ch) != -1) {
	        strTemp = str2.substring(0, str2.indexOf(ch));
	        str1 = str1 + strTemp.concat(reStr);
	        addr = str2.indexOf(ch) + ch.length();
	        str2 = str2.substring(addr, str2.length());
	        if (addr > len - 1) {
	          flag = 1;
	        }
	      }
	      else {
	        flag = 1;
	      }
	    }
	    str1 = str1 + str2;
	    return str1;
  }
  
//get the display name for the menu based on the menu field name
  public static String getMenuDisplayName(String menuName)
  {
      if (menuName.equals("PriceSale"))
      {
          return "Price";
      }
      else if (menuName.equals("Category"))
      {
    	  return "Type";
      }
      else if (menuName.endsWith("WattageCat"))
      {
      	return "Wattage";
      }
      else if (menuName.equals("Tone"))
      {
      	return "Color";
      }
      else if (menuName.equals("MfrID"))
      {
      	return "Brand";
      }
      else if (menuName.equals("ShadeShape"))
      {
      	return "Shade Shape";
      }
      else if (menuName.equals("LanternDirection"))
      {
      	return "Lantern Direction";
      }
      else if (menuName.equals("LightDirection"))
      {
      	return "Light Direction";
      }
      else if (menuName.equals("HangingMethod"))
      {
      	return "Hanging Method";
      }
      else if (menuName.equals("ShadeDirection"))
      {
      	return "Shade Direction";
      }
      else if (menuName.equals("FabricShadeColor"))
      {
      	return "Fabric Shade Color";
      }
      else if (menuName.equals("GlassShadeColor"))
      {
      	return "Glass Shade Color";
      }
      else if (menuName.equals("SwitchFunction"))
      {
      	return "Switch Function";
      }
      else if (menuName.equals("NumberofArms"))
      {
      	return "Number of Arms";
      }
      else if (menuName.equals("NumberofBulbs"))
      {
      	return "Number of Bulbs";
      }
      else if (menuName.equals("InstallationType"))
      {
      	return "Installation Type";
      }
      else if (menuName.equals("CanSize"))
      {
      	return "Can Size";
      }
      else if (menuName.equals("CanSizeCat"))
      {
      	return "Can Size";
      }
      else if (menuName.equals("CanRating"))
      {
      	return "Can Rating";
      }
      else if (menuName.equals("CanHeight"))
      {
      	return "Can Height";
      }
      else if (menuName.equals("CanTrimShape"))
      {
      	return "Can/Trim Shape";
      }
      else if (menuName.equals("TrimSize"))
      {
      	return "Trim Size";
      }
      else if (menuName.equals("TrimSizeCat"))
      {
      	return "Trim Size";
      }
      else if (menuName.equals("TrimStyle"))
      {
      	return "Trim Style";
      }
      else if (menuName.equals("RecessedTrimFinish"))
      {
      	return "Recessed Trim Finish";
      }
      else if (menuName.equals("LampSets"))
      {
      	return "Lamp Sets";
      }
      else if (menuName.equals("LampShadeIncluded"))
      {
      	return "LampShade Included";
      }
      else if (menuName.equals("FanMountingType"))
      {
      	return "Fan Mounting Type";
      }
      else if (menuName.equals("FanControlType"))
      {
      	return "Fan Control Type";
      }
      else if (menuName.equals("FanBlades"))
      {
      	return "Fan Blades";
      }
      else if (menuName.equals("BeamSpread"))
      {
      	return "Beam Spread";
      }
      else if (menuName.equals("VoltageType"))
      {
      	return "Voltage Type";
      }
      else if (menuName.equals("VoltageInput"))
      {
      	return "Voltage Input";
      }
      else if (menuName.equals("VoltageOutput"))
      {
      	return "Voltage Output";
      }
      else if (menuName.equals("DoorChimeNotes"))
      {
      	return "Door Chime Notes";
      }
      else if (menuName.equals("Dimmable"))
      {
      	return "Dimmable";
      }
      else if (menuName.equals("SwitchType"))
      {
      	return "Switch Type";
      }
      else if (menuName.equals("CircuitType"))
      {
      	return "Circuit Type";
      }
      else if (menuName.equals("SecurityType"))
      {
      	return "Security Type";
      }
      else if (menuName.equals("WetLocation"))
      {
      	return "Wet Location";
      }
      else if (menuName.equals("DampLocation"))
      {
      	return "Damp Location";
      }
      else if (menuName.equals("Pinup"))
      {
      	return "Pin-up";
      }
      else if (menuName.equals("FanBladeTone"))
      {
      	return "Fan Blade Tone";
      }
      else if (menuName.equals("BaseType"))
      {
      	return "Base Type";
      }
      else if (menuName.equals("BulbType"))
      {
      	return "Bulb Type";
      }
      else if (menuName.equals("BulbShape"))
      {
      	return "Bulb Shape";
      }
      else if (menuName.equals("ShadeMaterial"))
      {
      	return "Shade Material";
      }
      else if (menuName.equals("LampFeature"))
      {
      	return "Lamp Feature";
      }
      else if (menuName.equals("VoltageOutputMultiTap"))
      {
      	return "Voltage Output Multi-Tap";
      }
      else if (menuName.equals("DimmerType"))
      {
    	  return "Dimmer Type";
      }
      else if (menuName.endsWith("WidthCat"))
      {
    	  return "Width";
      }
      else if (menuName.endsWith("HeightCat"))
      {
    	  return "Height";
      }
      else if (menuName.endsWith("TopWidthShadeCat"))
      {
    	  return "Top Width Shade";
      }
      else if (menuName.endsWith("BottomWidthShadeCat"))
      {
    	  return "Bottom Width Shade";
      }
      else if (menuName.endsWith("SlopeSlantCat"))
      {
    	  return "Slope / Slant";
      }
      else if (menuName.endsWith("LengthCat"))
      {
    	  return "Length";
      }
      else if (menuName.endsWith("BladeDiameterCat"))
      {
    	  return "Blade Diameter";
      }
      else if (menuName.endsWith("ColorRenderingIndexCat"))
      {
    	  return "Color Rendering Index";
      }
      else if(menuName.endsWith("SpecialFeatures"))
      {
    	  return "Special Features";
      }
      else
      {
          return menuName;
      }
  }
  public static String maskCreditCard(String num)
  {
	  return "***" + num.substring(num.length() - 4, num.length());
  }
  
  public static int remainder(int a, int b)
  {
	  return a%b;
  }
  
  public static void main(String args[])
  {
	  System.out.println(getNowDate() + "\n");
	  System.out.println(getNowTime() + "\n");
  }
}