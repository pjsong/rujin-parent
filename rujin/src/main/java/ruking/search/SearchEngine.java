/*
 * SearchEngine.java
 *
 * Created on 6 March 2006, 14:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ruking.search;

import java.io.File;
import java.io.IOException;
import java.util.BitSet;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author John
 */
public class SearchEngine {
    private IndexSearcher searcher = null;
    private QueryParser parser = null;
    private String lang="";
    /** Creates a new instance of SearchEngine */
    public SearchEngine(String language) throws IOException {
    	lang = language;
    	String fileName="ruking-index";
    	if(!lang.equals(""))
    		fileName = fileName+"_"+lang;
        searcher = new IndexSearcher(FSDirectory.open(new File(getClass().getResource("/"+fileName).getPath())));
        parser = new QueryParser(Version.LUCENE_34,"content", new StandardAnalyzer(Version.LUCENE_34));
    }
    
    public TopDocs performSearch(String queryString)
    throws IOException, ParseException {
        Query query = parser.parse(queryString);        
        TopDocs hits = searcher.search(query,null,10);
        return hits;
    }
    public TopDocs performSearch_Collector(String queryString)
    throws IOException, ParseException {
        Query query = parser.parse(queryString);
//        IndexReader reader = IndexReader.open(FSDirectory.open(new File("ruking-index")));
        TopScoreDocCollector topScoreDocCollector= TopScoreDocCollector.create(100, true);
        searcher.search(query,topScoreDocCollector);
        return topScoreDocCollector.topDocs();
    }
	public IndexSearcher getSearcher() {
		return searcher;
	}

	public void setSearcher(IndexSearcher searcher) {
		this.searcher = searcher;
	}
    }
