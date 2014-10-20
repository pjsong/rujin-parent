/*
 * Main.java
 *
 * Created on 6 March 2006, 11:51
 *
 */

package ruking.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import ruking.search.SearchEngine;


public class Main {
    public static void main(String[] args) {
      try {
        Indexer.main(null);
        SearchEngine instance = new SearchEngine("");
        TopDocs hits = instance.performSearch_Collector("焊底");
        getResultLM(hits,instance);
        System.out.println("performSearch done");
      } catch (Exception e) {
        System.out.println("Exception caught.\n");
      }
    }
    private static List<Map> getResultLM(TopDocs hits,SearchEngine instance) throws CorruptIndexException, IOException{
    	List<Map> searchResult = new ArrayList<Map>();
        for(ScoreDoc sd:hits.scoreDocs){
        	if(sd.score>0.2)
        	{
        	Document doc = instance.getSearcher().doc(sd.doc);
        	HashMap map = new HashMap<String,String>();
        	map.put("Title", doc.get("title"));
        	map.put("ID", doc.get("id"));
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
