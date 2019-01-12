package module3;

import android.util.Log;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


/**
 * 对建立的索引进行搜索
 * Created by 白海涛 on 2017/5/21.
 */

public class LuceneSearcher {
    public static ArrayList<Law> search(String indexPath,String phrase) throws Exception {

        ArrayList<Law> laws = new ArrayList<>();
        Log.d("LuceneSearcher", "search:executed ");
        Log.d("LuceneSearcher", "indexPath: " + indexPath);
        File indexFile = new File(indexPath);
        Directory dir = FSDirectory.open(indexFile);
        IndexSearcher indexSearcher = new IndexSearcher(dir);
        QueryParser queryParser = new QueryParser(org.apache.lucene.util.Version.LUCENE_30, "text",
                new StandardAnalyzer(Version.LUCENE_30));
        Query query = queryParser.parse(phrase);
        long start = new Date().getTime();// start time
        TopDocs topDocs = indexSearcher.search(query, 10);//查询query“text”域中包含phrase的前10个文档，并按降序排序
        long end = new Date().getTime();//end time
        int posIndex = 0;
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = indexSearcher.doc(scoreDoc.doc);
            Law law = new Law();
            law.setName(doc.get("lawsName"));
            law.setDigest(doc.get("digest"));
            law.setCatalogString(doc.get("catalogString"));
            law.setText(doc.get("text"));

            List<Map<String, Object>> chapterCalalogMapList = FileCatalogAndFileText.getCatalog(doc.get("catalogString"));
            ListIterator<Map<String, Object>> iterator = chapterCalalogMapList.listIterator();
            Map<String, Object> map;
            ArrayList<String> chapterName = new ArrayList<>();
            ArrayList<Integer> numberInChapters = new ArrayList<>();
            int pos = 0;
            int tollItems = 0;
            while (iterator.hasNext()) {
                map = iterator.next();
                chapterName.add(pos, (String) map.get("Chapter"));
                numberInChapters.add(pos, (int) map.get("NumberInChapter"));
                tollItems = tollItems + numberInChapters.get(pos);
                pos++;
            }
//            law.setChapterName(chapterName);
//            law.setNumberInChapters(numberInChapters);
            law.setTollItems(tollItems);

            Log.d("LuceneSearcher", phrase + "搜索时，搜索到法律名：" + doc.get("lawsName"));
            Log.d("LuceneSearcher", "digest：" + doc.get("digest"));
            Log.d("LuceneSearcher", "catalogString：" + doc.get("catalogString"));
            Log.d("LuceneSearcher", "chapterName: " + chapterName);
            Log.d("LuceneSearcher", "numberInChapters: " + numberInChapters);
            Log.d("LuceneSearcher", "tollItems: " + tollItems);

            laws.add(posIndex, law);
            posIndex++;
        }
        for (int i = 0; i < laws.size(); i++) {
            Log.d("LuceneSearcher", "LawsArrayList:  " + i + "  " + laws.get(i).getName());
        }


        Log.d("LuceneSearcher", "检索到的法律数量为：" + laws.size());
        Log.d("LuceneSearcher", "Found" + topDocs.totalHits + "documents in " + (end - start) + "milliseconds");
        indexSearcher.close();
        return laws;

    }

    public static Law searchByName(String indexPath,String phrase) throws Exception{
        Law law = new Law();
        Log.d("LuceneSearcher", "searchByName:executed ");
        File indexFile = new File(indexPath);
        Directory dir = FSDirectory.open(indexFile);
        IndexSearcher indexSearcher = new IndexSearcher(dir);
        QueryParser queryParser = new QueryParser(org.apache.lucene.util.Version.LUCENE_30, "lawsName",
                new StandardAnalyzer(Version.LUCENE_30));
        Query query = queryParser.parse(phrase);
        TopDocs topDocs = indexSearcher.search(query, 10);//查询query“text”域中包含phrase的前10个文档，并按降序排序

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = indexSearcher.doc(scoreDoc.doc);
            law.setName(doc.get("lawsName"));
            law.setDigest(doc.get("digest"));
            law.setCatalogString(doc.get("catalogString"));
            law.setText(doc.get("text"));

            List<Map<String, Object>> chapterCalalogMapList = FileCatalogAndFileText.getCatalog(doc.get("catalogString"));
            ListIterator<Map<String, Object>> iterator = chapterCalalogMapList.listIterator();
            Map<String, Object> map;
            ArrayList<String> chapterName = new ArrayList<>();
            ArrayList<Integer> numberInChapters = new ArrayList<>();
            int pos = 0;
            int tollItems = 0;
            while (iterator.hasNext()) {
                map = iterator.next();
                chapterName.add(pos, (String) map.get("Chapter"));
                numberInChapters.add(pos, (int) map.get("NumberInChapter"));
                tollItems = tollItems + numberInChapters.get(pos);
                pos++;
            }
//            law.setChapterName(chapterName);
//            law.setNumberInChapters(numberInChapters);
            law.setTollItems(tollItems);

            Log.d("LuceneSearcherByName", phrase + "搜索时，搜索到法律名：" + doc.get("lawsName"));
            Log.d("LuceneSearcherByName", "digest：" + doc.get("digest"));
            Log.d("LuceneSearcherByName", "catalogString：" + doc.get("catalogString"));
            Log.d("LuceneSearcherByName", "chapterName: " + chapterName);
            Log.d("LuceneSearcherByName", "numberInChapters: " + numberInChapters);
            Log.d("LuceneSearcherByName", "tollItems: " + tollItems);
        }
        indexSearcher.close();

        return law;
    }

}
