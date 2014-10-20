package ruking.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class DbUtil
{  

   // Assembly "'arg1','arg2',...'argn'" type of string to use in
	// SQL statements from the input array of strings.
	public static String escList(Object[] fields) throws SQLException
	{
		StringBuffer result = new StringBuffer();
		for (Object field : fields)
		{
			if (field == null)
			{
				result.append("null").append(",");
			}
			else
			{
				result.append(escSql(field.toString())).append(",");
			}
		}
		
		// remove the last , if any
		if (result.charAt(result.length() - 1) == ',')
		{
			result.deleteCharAt(result.length() - 1);
		}
		
		return result.toString();
	}

   public static String escList(ArrayList arrList) throws SQLException
   {
      Object[] arr = new Object[arrList.size()];
      for (int i = 0; i < arr.length; i++)
      {
         arr[i] = arrList.get(i);
      }
      return escList(arr);
   }

   public static String escFieldName(String s)
   {
       return "`" + s + "`";
   }

   public static String escFieldNames(String[] fieldNames)
   {
		StringBuffer result = new StringBuffer();
		for (String field : fieldNames)
		{
			if (field == null)
			{
				throw new NullPointerException();
			}
			else
			{
				result.append(escFieldName(field)).append(",");
			}
		}
		
		// remove the last , if any
		if (result.charAt(result.length() - 1) == ',')
		{
			result.deleteCharAt(result.length() - 1);
		}
		
		return result.toString();	   
   }

   public static String escFieldNames(ArrayList<String> arrList) throws SQLException
   {
      String[] arr = new String[arrList.size()];
      for (int i = 0; i < arr.length; i++)
      {
         arr[i] = arrList.get(i);
      }
      return escFieldNames(arr);
   }

   public static String sqlListForUpdate(Map map) throws SQLException
   {
      if (map.size() == 0)
      {
         throw new SQLException("map is empty");
      }
      
      StringBuffer result = new StringBuffer();
      Iterator it = map.entrySet().iterator();
      while (it.hasNext())
      {
         Map.Entry entry = (Map.Entry) it.next();
         String name = (String) entry.getKey();
         Object value = entry.getValue();
         result.append(escFieldName(name));
         result.append('=');
         result.append('\'');
         result.append(escSql(value.toString()));
         result.append("\',");        
      }
      
      // get rid of the last ","
      result.deleteCharAt(result.length() - 1);
      return result.toString();
   }
   
   // escape the ' or \
   public static String escSql(Object obj)
   {
	   if (obj == null)
	   {
		   return null;
	   }

	   String s = obj.toString();
	   if (s.equals(""))
	   {
		   return null;
	   }
	   
	   StringBuffer hold = new StringBuffer("'");
	   for(int i=0; i < s.length(); i++ ) 
	   {
		   char c = s.charAt(i);
		   if (c == '\'') 
		   {
			   hold.append ("''");
		   }
		   else if (c == '\\')
		   {
			   hold.append("\\\\");
		   }
		   else 
		   {
			   hold.append(c);
		   }
	   }	   
	   hold.append("'");
	   return hold.toString();
   }
   
// escape the ' or \
//   public static String escapeSql(String s) 
//   {
//      String retvalue = s;
//      if (s.indexOf ("'") != -1 ) 
//      {
//        StringBuffer hold = new StringBuffer();
//        char c;
//        for(int i=0; i < s.length(); i++ ) 
//        {
//          if ((c=s.charAt(i)) == '\'' ) 
//          {
//             hold.append ("''");
//          }
//          else if (c == '\\')
//          {
//             hold.append("\\\\");
//          }
//          else 
//          {
//             hold.append(c);
//          }
//      }
//      retvalue = hold.toString();
//      }
//      return retvalue;
//    }
   
   // The characters "%" and "_" have special meaning in SQL LIKE clauses 
   // (to match zero or more characters, or exactly one character, respectively). 
   // In order to interpret them literally, they can be preceded with a special escape character in strings, 
   // e.g. "\". 
   public static String escLike(String s)
   {
	   if (s == null)
	   {
		   return null;
	   }
	   
//	   if (s.indexOf("'") < 0 && s.indexOf("\\") < 0 && s.indexOf("%") < 0 && s.indexOf("_") < 0)
//	   {
//		   return s;
//	   }
	   
	   StringBuffer hold = new StringBuffer("'");
	   for(int i=0; i < s.length(); i++ ) 
	   {
		   char c = s.charAt(i);
		   if (c == '\'') 
		   {
			   hold.append ("''");
		   }
		   else if (c == '\\')
		   {
			   hold.append("\\\\");
		   }
		   else if (c == '%')
		   {
			   hold.append ("\\\\%");  
		   }
		   else if (c == '_')
		   {
			   hold.append("\\\\_");
		   }
		   else 
		   {
			   hold.append(c);
		   }
	   }
	   hold.append("'");
	   return hold.toString();
   }      
   
   public static void close(Connection conn)
   {
      try
      {
         if (conn != null)
         {
            conn.close();
         }
      }
      catch (SQLException e) { }
   }

   public static void close(ResultSet rs)
   {
      try
      {
         if (rs != null)
         {
            rs.close();
         }
      }
      catch (SQLException e) { }
   }

   public static void close(Statement stmt)
   {
      try
      {
         if (stmt != null)
         {
            stmt.close();
         }
      }
      catch (SQLException e) { }
   }
   
   public static void close(PreparedStatement ps)
   {
      try
      {
         if (ps != null)
         {
        	 ps.close();
         }
      }
      catch (SQLException e) { }
   }
}