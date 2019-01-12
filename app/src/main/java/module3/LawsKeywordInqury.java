package module3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.nucleartechnology.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 显示关键词搜索内容
 * 法律法规名称，简介
 * Created by 白海涛 on 2017/5/21.
 */

public class LawsKeywordInqury extends AppCompatActivity {
    private EditText searchPhase;
    private Button searchButton;
    private ListView listView;
    private ArrayList<HashMap<String, Object>> lawsList = new ArrayList<>();
    private String searchWord;
    private ArrayList<Law> laws = new ArrayList<>();

    public static final String TAG = "LawsKeywordInqury";
    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module3_keyword);

        actionBar = getSupportActionBar();
        actionBar.setTitle("法律法规关键字查询");

        laws = getIntent().getParcelableArrayListExtra("laws");
        //打印日志，看是否接收到了Laws的正确ArrayList
        for (int i = 0; i < laws.size(); i++) {
            Log.d(TAG, "LawsArrayList:  " + i + "  " + laws.get(i).getName());
        }

        searchWord = getIntent().getStringExtra("search_word");
        Log.d(TAG, "搜索词为：" + searchWord);

        searchPhase = (EditText) findViewById(R.id.searchPhase2);
        searchPhase.setText(searchWord);
        searchButton = (Button) findViewById(R.id.searchButton2);
        listView = (ListView) findViewById(R.id.laws_listView);

        addToListView(laws, listView);

        Toast.makeText(LawsKeywordInqury.this, "搜索到 " + laws.size() + " 条数量", Toast.LENGTH_SHORT).show();


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = searchPhase.getText().toString();
                searchWord = string;
                if (string.equals("")) {
                    Toast.makeText(LawsKeywordInqury.this, "请输入搜索词" + string, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        laws = LuceneSearcher.search(LawsMainActivity.getIndexPath(), string);
                        lawsList.clear();
                        addToListView(laws, listView);
                        Toast.makeText(LawsKeywordInqury.this, "搜索到 " + laws.size() + " 条数量", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //设置ListView的点击事件：
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获得法律名称的方法一： hashMap.get("name").toString();
//                HashMap<String, Object> hashMap = (HashMap<String, Object>) listView.getAdapter().getItem(position);
//                String name = hashMap.get("name").toString();
//                Log.d(TAG, "onItemClick: " + name);
                //获得法律名称的方法二：laws.get(position).getName();
                LawsFullInqury.actionStart(LawsKeywordInqury.this, searchWord, laws.get(position));
            }
        });

    }


    public void addToListView(ArrayList<Law> laws, ListView listView) {
        int size = laws.size();
        for (int i = 0; i < size; i++) {
            //new HashMap<>()必须放到循环内，否则lawsList添加的只是hasMap的引用，最后，lawsList的所有值为最后一次添加的引用值；
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", laws.get(i).getName());

            try {//可能会有越界情况：
                String text = laws.get(i).getText();
                int indexOfKeyWord = text.indexOf(searchWord);
                if (indexOfKeyWord > 0 || (text.charAt(indexOfKeyWord + 70) != -1)) {
                    String textToDigest = text.substring(indexOfKeyWord - 0, text.indexOf(searchWord) + 70);
                    hashMap.put("digest", textToDigest);
                }
            } catch (IndexOutOfBoundsException e) {
                //对越界情况的处理
                hashMap.put("digest", laws.get(i).getDigest());
            }

            lawsList.add(i, hashMap);
        }

        //打印日志，看LawsList是否正确
        for (int i = 0; i < lawsList.size(); i++) {
            Log.d(TAG, "AddToListView,LawsArrayList:  " + i + "  " + lawsList.get(i).get("name"));
        }

//        SimpleAdapter adapter = new SimpleAdapter(LawsKeywordInqury.this, lawsList, R.layout.item1,
//                new String[]{"name", "digest"}, new int[]{R.id.law_name, R.id.law_digest});

        HighLightAdapter adapter = new HighLightAdapter(LawsKeywordInqury.this, lawsList, searchWord);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lawsList.clear();
    }
}
