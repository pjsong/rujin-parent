/*
 * Main.java
 *
 * Created on 6 March 2006, 11:51
 *
 */

package ruking.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;


public class SearchTool {
    public static void main(String[] args) {
      try {
        getResultLM("焊底","");
        System.out.println("performSearch done");
      } catch (Exception e) {
        System.out.println("Exception caught.\n");
      }
    }
	public static List<Map> getResultLM(String queryStr,String lang) throws CorruptIndexException, IOException, ParseException{
        SearchEngine instance = new SearchEngine(lang);
        TopDocs hits = instance.performSearch_Collector(queryStr);
    	List<Map> searchResult = new ArrayList<Map>();
        for(ScoreDoc sd:hits.scoreDocs){
        	if(sd.score>0.2)
        	{
        	Document doc = instance.getSearcher().doc(sd.doc);
        	HashMap map = new HashMap<String,Object>();
        	map.put("Title", doc.get("title"));
        	map.put("ID", Integer.parseInt(doc.get("id")));
            System.out.println(doc.get("id")
                               + " -- " + doc.get("title")
                               + " (" + sd.score + ")");
            searchResult.add(map);
        }}
    	return searchResult;
    }

}
//Results found: 7
//5 -- 电池自动贴胶纸设备 (0.4553553)
//2 -- 圆柱形锂电池焊镍设备（双轴） (0.43003464)
//7 -- 圆柱形锂电池焊镍设备（三轴） (0.43003464)
//8 -- 圆柱形锂电池焊底设备（单轴） (0.43003464)
//4 -- 锂电池电芯全自动卷绕机 (0.39703566)
//3 -- PTC焊接机 (0.35743737)
//6 -- FCT (0.12585682)
