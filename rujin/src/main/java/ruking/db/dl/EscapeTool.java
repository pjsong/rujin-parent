package ruking.db.dl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Tool for working with escaping in Velocity templates.
 * It provides methods to escape outputs for Java, JavaScript, HTML, XML and SQL.
 * Also provides methods to render VTL characters that otherwise needs escaping.
 *
 * <p><pre>
 * Example uses:
 *  $java                        -> He didn't say, "Stop!"
 *  $esc.java($java)             -> He didn't say, \"Stop!\"
 *
 *  $javascript                  -> He didn't say, "Stop!"
 *  $esc.javascript($javascript) -> He didn\'t say, \"Stop!\"
 *
 *  $html                        -> "bread" & "butter"
 *  $esc.html($html)             -> &amp;quot;bread&amp;quot; &amp;amp; &amp;quot;butter&amp;quot;
 *
 *  $xml                         -> "bread" & "butter"
 *  $esc.xml($xml)               -> &amp;quot;bread&amp;quot; &amp;amp; &amp;quot;butter&amp;quot;
 *
 *  $sql                         -> McHale's Navy
 *  $esc.sql($sql)               -> McHale''s Navy
 *
 *  $esc.dollar                  -> $
 *  $esc.d                       -> $
 *
 *  $esc.sharp                   -> #
 *  $esc.s                       -> #
 *
 *  $esc.backslash               -> \
 *  $esc.b                       -> \
 *
 *  $esc.quote                   -> "
 *  $esc.q                       -> "
 *
 *  $esc.exclamation             -> !
 *  $esc.e                       -> !
 *
 * Example toolbox.xml config (if you want to use this with VelocityView):
 * &lt;tool&gt;
 *   &lt;key&gt;esc&lt;/key&gt;
 *   &lt;scope&gt;application&lt;/scope&gt;
 *   &lt;class&gt;org.apache.velocity.tools.generic.EscapeTool&lt;/class&gt;
 * &lt;/tool&gt;
 * </pre></p>
 *
 * <p>This tool is entirely threadsafe, and has no instance members.
 * It may be used in any scope (request, session, or application).
 * </p>
 *
 * @author <a href="mailto:shinobu@ieee.org">Shinobu Kawai</a>
 * @version $Id: $
 *
 * @see StringEscapeUtils
 */
public class EscapeTool
{

    /**
     * Default constructor.
     */
    public EscapeTool()
    {
    }

    // use utf-8 to encode the str for the url
    public String url(String str) throws Exception
    {
    	if (str == null)
    	{
    		return "";
    	}
    	return URLEncoder.encode(str, "UTF-8");
    }
    public String deUrl(String str) throws Exception
    {
    	if (str == null)
    	{
    		return "";
    	}
    	return URLDecoder.decode(str, "UTF-8");
    }
    
    /**
     * Escapes the characters in a <code>String</code> using Java String rules.
     * <br />
     * Delegates the process to {@link StringEscapeUtils#escapeJava(String)}.
     *
     * @param java String to escape values in, may be null
     * @return String with escaped values, <code>null</code> if null string input
     *
     * @see StringEscapeUtils#escapeJava(String)
     */
    public String java(String java)
    {
        return StringEscapeUtils.escapeJava(java);
    }

    /**
     * Escapes the characters in a <code>String</code> using JavaScript String rules.
     * <br />
     * Delegates the process to {@link StringEscapeUtils#escapeJavaScript(String)}.
     *
     * @param javascript String to escape values in, may be null
     * @return String with escaped values, <code>null</code> if null string input
     *
     * @see StringEscapeUtils#escapeJavaScript(String)
     */
    public String javascript(String javascript)
    {
        return StringEscapeUtils.escapeJavaScript(javascript);
    }

    /**
     * Escapes the characters in a <code>String</code> using HTML entities.
     * <br />
     * Delegates the process to {@link StringEscapeUtils#escapeHtml(String)}.
     *
     * @param str the <code>String</code> to escape, may be null
     * @return a new escaped <code>String</code>, <code>null</code> if null string input
     *
     * @see StringEscapeUtils#escapeHtml(String)
     */
    public static String htm(String str)
    {
    	if (str == null)
    	{
    		return "";
    	}
    	
    	StringBuilder sb = new StringBuilder();
    	int len = str.length();
        for (int i = 0; i < len; i++)
        {
        	char c = str.charAt(i);
        	if (c > 0xFF)
        	{
        		sb.append(c);
        	}
        	else
        	{
        		sb.append(StringEscapeUtils.escapeHtml(c + ""));
        	}
        }
        
        return sb.toString();
    }

    public String html(String str)
    {
    	if (str == null)
    	{
    		return "";
    	}
    	
    	StringBuilder sb = new StringBuilder();
    	int len = str.length();
        for (int i = 0; i < len; i++)
        {
        	char c = str.charAt(i);
        	if (c > 0xFF)
        	{
        		sb.append(c);
        	}
        	else
        	{
        		sb.append(StringEscapeUtils.escapeHtml(c + ""));
        	}
        }
        
        return sb.toString();
    }
    public String decodeHtml(String str)
    {
    	if (str == null)
    	{
    		return "";
    	}
    	
    	StringBuilder sb = new StringBuilder();
    	int len = str.length();
        for (int i = 0; i < len; i++)
        {
        	char c = str.charAt(i);
        	if (c > 0xFF)
        	{
        		sb.append(c);
        	}
        	else
        	{
        		sb.append(StringEscapeUtils.unescapeHtml(c + ""));
        	}
        }
        
        return sb.toString();
    }
    /**
     * Escapes the characters in a <code>String</code> using XML entities.
     * <br />
     * Delegates the process to {@link StringEscapeUtils#escapeXml(String)}.
     *
     * @param xml the <code>String</code> to escape, may be null
     * @return a new escaped <code>String</code>, <code>null</code> if null string input
     *
     * @see StringEscapeUtils#escapeXml(String)
     */
    public String xml(String xml)
    {
        return StringEscapeUtils.escapeXml(xml);
    }

    /**
     * Escapes the characters in a <code>String</code> to be suitable to pass to an SQL query.
     * <br />
     * Delegates the process to {@link StringEscapeUtils#escapeSql(String)}.
     *
     * @param sql the string to escape, may be null
     * @return a new String, escaped for SQL, <code>null</code> if null string input
     *
     * @see StringEscapeUtils#escapeSql(String)
     */
    public String sql(String sql)
    {
        return StringEscapeUtils.escapeSql(sql);
    }

    /**
     * Renders a dollar sign ($).
     * @return a dollar sign ($).
     * @see #getD()
     */
    public String getDollar()
    {
        return "$";
    }

    /**
     * Renders a dollar sign ($).
     * @return a dollar sign ($).
     * @see #getDollar()
     */
    public String getD()
    {
        return this.getDollar();
    }

    /**
     * Renders a sharp (#).
     * @return a sharp (#).
     * @see #getS()
     */
    public String getSharp()
    {
        return "#";
    }

    /**
     * Renders a sharp (#).
     * @return a sharp (#).
     * @see #getSharp()
     */
    public String getS()
    {
        return this.getSharp();
    }

    /**
     * Renders a backslash (\).
     * @return a backslash (\).
     * @see #getB()
     */
    public String getBackslash()
    {
        return "\\";
    }

    /**
     * Renders a backslash (\).
     * @return a backslash (\).
     * @see #getBackslash()
     */
    public String getB()
    {
        return this.getBackslash();
    }

    /**
     * Renders a double quotation mark (").
     * @return a double quotation mark (").
     * @see #getQ()
     */
    public String getQuote()
    {
        return "\"";
    }

    /**
     * Renders a double quotation mark (").
     * @return a double quotation mark (").
     * @see #getQuote()
     */
    public String getQ()
    {
        return this.getQuote();
    }

    /**
     * Renders an exclamation mark (!).
     * @return an exclamation mark (!).
     * @see #getE()
     */
    public String getExclamation()
    {
        return "!";
    }

    /**
     * Renders an exclamation mark (!).
     * @return an exclamation mark (!).
     * @see #getExclamation()
     */
    public String getE()
    {
        return this.getExclamation();
    }
}