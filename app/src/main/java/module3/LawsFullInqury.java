package module3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nucleartechnology.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * 法律法规全文查询界面
 * Created by 白海涛 on 2017/5/12.
 */

public class LawsFullInqury extends AppCompatActivity {

    public static final String TAG = "LawsFullInqury";

    private ActionBar actionBar;
    private TextView lawsName;
    private EditText searchPharse;
    private Law law;
    private Button searchButton;
    private ListView listView;
    ArrayList<HashMap<String, Object>> chapterAndText = new ArrayList<>();
    TextInChapterAdapter adapter;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module3_full_inqury);
        actionBar = getSupportActionBar();
        actionBar.setTitle("法律法规全文查询");

        lawsName = (TextView) findViewById(R.id.lawsName);
        searchPharse = (EditText) findViewById(R.id.searchPhase3);
        listView = (ListView) findViewById(R.id.chapter_listView);
        searchButton = (Button) findViewById(R.id.searchButton3);

        Bundle bundleData = this.getIntent().getExtras();
        law = bundleData.getParcelable("law");
        String name = law.getName();
        String searchWord = bundleData.getString("searchWord");
        lawsName.setText(name);
        searchPharse.setText(searchWord);

        String catalogString = law.getCatalogString();
        List<Map<String, Object>> catalogList = FileCatalogAndFileText.getCatalog(catalogString);
        Log.d(TAG, "catalogList: " + catalogList);
        String text = law.getText();
        Log.d(TAG, "text: " + text.substring(0, 75));
        int tollItem = law.getTollItems();

        ListIterator<Map<String, Object>> iterator = catalogList.listIterator();
        Map<String, Object> map;
        ArrayList<String> chapterName = new ArrayList<>();
        ArrayList<Integer> numberInChapters = new ArrayList<>();
        int pos = 0;
        while (iterator.hasNext()) {
            map = iterator.next();
            chapterName.add(pos, (String) map.get("Chapter"));
            numberInChapters.add(pos, (int) map.get("NumberInChapter"));
            pos++;
        }

        //获得法律法规的条款内容
        getTextInCahpter(text, tollItem, chapterName, numberInChapters);

        ListIterator<HashMap<String, Object>> iterator1 = chapterAndText.listIterator();
        HashMap<String, Object> hashMap ;
        while (iterator1.hasNext()) {
            hashMap = iterator1.next();
            Log.d(TAG,"textInChapter:"+ hashMap.get("Chapter") + "\n" + hashMap.get("TextInChapter"));
        }


       adapter = new TextInChapterAdapter(LawsFullInqury.this, chapterAndText, searchPharse.getText().toString());
        listView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = searchPharse.getText().toString();
                if (string.equals("")) {
                    Toast.makeText(LawsFullInqury.this, "请输入搜索词", Toast.LENGTH_SHORT).show();
                } else {
                    adapter = new TextInChapterAdapter(LawsFullInqury.this, chapterAndText, string);
                    listView.setAdapter(adapter);
                    Toast.makeText(LawsFullInqury.this, "【"+string +"】"+ "已标记红色", Toast.LENGTH_SHORT).show();
                    // TODO: 2017/6/3 用lucene查询，显示搜索词在文中出现的次数作为提示；
                }
            }
        });


    }

    public void getTextInCahpter(String text, int tollItem, ArrayList<String> chapterName, ArrayList<Integer> numberInChapters) {
        int tollcut = 0;
        for (int j = 0; j < chapterName.size(); j++) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("Chapter", chapterName.get(j));
            StringBuffer sb = new StringBuffer("");
            int cut = numberInChapters.get(j);
            for (int k = tollcut; k < cut + tollcut; k++) {
                int from = text.indexOf("第" + (k + 1) + "条");
                int to = text.indexOf("第" + (k + 2) + "条");
                if (k != tollItem - 1) {
                    String itemText = text.substring(from , to);
                    sb.append(itemText + "\n");

                } else {
                    String itemText = text.substring(from);
                    sb.append(itemText + "\n");
                }
            }
            hashMap.put("TextInChapter", sb.toString());
            tollcut = tollcut + cut;
            chapterAndText.add(j, hashMap);
        }

    }

    public static void actionStart(Context context, String searchWord, Law law) {
        Intent intent = new Intent(context, LawsFullInqury.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("law", law);
        bundle.putString("searchWord", searchWord);
        Log.d("actionStart", "law: " + law.getName());
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
