package module3;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.nucleartechnology.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
/**
 * Created by 白海涛 on 2017/5/23.
 */

public class LawsMainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String filePath1 = "/data/data/com.example.nucleartechnology/法律法规查询/辐射监管相关法律";
    private final String filePath2 = "/data/data/com.example.nucleartechnology/法律法规查询/辐射监管相关行政法规";
    private final String filePath3 = "/data/data/com.example.nucleartechnology/法律法规查询/辐射监管相关部门规章";
    private final String filePath4 = "/data/data/com.example.nucleartechnology/法律法规查询/地方法规";
    private final static String indexPath = "/data/data/com.example.nucleartechnology/法律法规查询/index";
    public static String getIndexPath() {
        return indexPath;
    }
    private final String TAG = "MainActivity";
    private final String[] strings1 = {"《中华人民共和国环境保护法》", "《中华人民共和国放射性污染防治法》"};
    private final String[] strings2 = {"《放射性同位素与射线装置安全和防护条例》", "《放射性废物安全管理条例》","《放射性物品运输安全管理条例》"};
    private final String[] strings3 = {"《电磁辐射环境保护管理办法》（是否废止待确认）", "《放射性固体废物贮存和处置许可管理办法》"
            , "《放射性同位素与射线装置安全和防护管理办法》", "《放射性同位素与射线装置安全许可管理办法》（不含附件）"
            , "《放射性物品运输安全监督管理办法》", "《放射性物品运输安全许可管理办法》（不含备案表）"};
    private final String[] strings4 = {"《江苏省辐射污染防治条例》"};


    private LinearLayout linearLayout1,linearLayout2,linearLayout3, linearLayout4;
    private ListView listView1,listView2,listView3, listView4;
    private EditText searchPhase;
    private Button searchButton;
    private final List<String> list1 = new ArrayList<>();
    private final List<String> list2 = new ArrayList<>();
    private final List<String> list3 = new ArrayList<>();
    private final List<String> list4 = new ArrayList<>();
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module3_laws_main);
        actionBar = getSupportActionBar();
        actionBar.setTitle("法律法规查询");

        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        linearLayout4 = (LinearLayout) findViewById(R.id.linearLayout4);
        listView1 = (ListView) findViewById(R.id.listView1);
        listView2 = (ListView) findViewById(R.id.listView2);
        listView3 = (ListView) findViewById(R.id.listView3);
        listView4 = (ListView) findViewById(R.id.listView4);
        searchPhase = (EditText) findViewById(R.id.searchPhase1);
        searchButton = (Button) findViewById(R.id.searchButton1);

        loadListFiles();

        try {
            loadIndexFiles();
            loadLawsFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.laws_item, list1);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, R.layout.laws_item, list2);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, R.layout.laws_item, list3);
        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(this, R.layout.laws_item, list4);

        listView1.setAdapter(arrayAdapter1);
        listView2.setAdapter(arrayAdapter2);
        listView3.setAdapter(arrayAdapter3);
        listView4.setAdapter(arrayAdapter4);

        setListViewHeightBasedOnChildren(listView1);
        setListViewHeightBasedOnChildren(listView2);
        setListViewHeightBasedOnChildren(listView3);
        setListViewHeightBasedOnChildren(listView4);

        searchButton.setOnClickListener(searchMethod);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = list1.get(position);
                try {
                    Law law = LuceneSearcher.searchByName(indexPath, name);
                    module3.LawsFullInqury.actionStart(LawsMainActivity.this, "", law);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        listViewItemClick(listView2, list2);
        listViewItemClick(listView3, list3);
        listViewItemClick(listView4, list4);

    }

    public void listViewItemClick(ListView listView, final List list) {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) list.get(position);
                try {
                    Law law = LuceneSearcher.searchByName(indexPath, name);
                    LawsFullInqury.actionStart(LawsMainActivity.this, "", law);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    View.OnClickListener searchMethod = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String string = searchPhase.getText().toString();
            if (string.equals("")) {
                Toast.makeText(LawsMainActivity.this, "请输入搜索词"+ string, Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Log.d(TAG, "onClick: 输入搜索词为：" + string);
                    ArrayList<Law> laws = LuceneSearcher.search(indexPath, string);
                    for (int i = 0; i < laws.size(); i++) {
                        Log.d(TAG, "LawsArrayList:  " + i + "  " + laws.get(i).getName());
                    }
                    Intent intent = new Intent(LawsMainActivity.this, module3.LawsKeywordInqury.class);
                    intent.putParcelableArrayListExtra("laws", laws);
                    intent.putExtra("search_word", string);
                    startActivity(intent);
                    Log.d(TAG, "onClick: 索索到的文档数量为："+laws.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.arrowDownButton1:
                if (linearLayout1.getVisibility() == View.VISIBLE) {
                    linearLayout1.setVisibility(View.GONE);
                } else {
                    linearLayout1.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.arrowDownButton2:
                if (linearLayout2.getVisibility() == View.VISIBLE) {
                    linearLayout2.setVisibility(View.GONE);
                } else {
                    linearLayout2.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.arrowDownButton3:
                if (linearLayout3.getVisibility() == View.VISIBLE) {
                    linearLayout3.setVisibility(View.GONE);
                } else {
                    linearLayout3.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.arrowDownButton4:
                if (linearLayout4.getVisibility() == View.VISIBLE) {
                    linearLayout4.setVisibility(View.GONE);
                } else {
                    linearLayout4.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public void loadListFiles() {
        for (int i = 0; i < strings1.length; i++) {
            list1.add(i, strings1[i]);
        }
        for (int i = 0; i < strings2.length; i++) {
            list2.add(i, strings2[i]);
        }
        for (int i = 0; i < strings3.length; i++) {
            list3.add(i, strings3[i]);
        }
        for (int i = 0; i < strings4.length; i++) {
            list4.add(i, strings4[i]);
        }
    }

    public void loadIndexFiles() throws IOException {
        File Dir = new File(indexPath);
        if (!Dir.exists()) {
            Dir.mkdirs();
        }
        File indexFile1 = new File(Dir, "_0.cfs");
        File indexFile2 = new File(Dir, "segments.gen");
        File indexFile3 = new File(Dir, "segments_2");

        try {
            if (!indexFile1.exists()) {
                indexFile1.createNewFile();
                InputStream is = getResources().openRawResource(R.raw._0);
                FileOutputStream fos = new FileOutputStream(indexFile1);
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                fos.write(buffer);
                is.close();
                fos.close();
                Log.d(TAG, "loadIndexFiles: _0.cfs ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (!indexFile2.exists()) {
                indexFile2.createNewFile();
                InputStream is = getResources().openRawResource(R.raw.segments);
                FileOutputStream fos = new FileOutputStream(indexFile2);
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                fos.write(buffer);
                is.close();
                fos.close();
                Log.d(TAG, "loadIndexFiles: segments.gen ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (!indexFile3.exists()) {
                indexFile3.createNewFile();
                InputStream is = getResources().openRawResource(R.raw.segments_2);
                FileOutputStream fos = new FileOutputStream(indexFile3);
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                fos.write(buffer);
                is.close();
                fos.close();
                Log.d(TAG, "loadIndexFiles: segments_2 ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadLawsFiles() throws IOException {
        String path = "data/data/com.example.nucleartechnology/法律法规查询/辐射监管相关法律";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File lawsFile1 = new File(dir, "《中华人民共和国放射性污染防治法》.txt");
        try {
            if (!lawsFile1.exists()) {
                lawsFile1.createNewFile();
                InputStream is = getResources().openRawResource(R.raw.fangshexing);
                FileOutputStream fos = new FileOutputStream(lawsFile1);
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                fos.write(buffer);
                is.close();
                fos.close();
                String s = getLawsName(lawsFile1);
                Log.d(TAG, "loadLawsFiles: 法律名称是： "+s);

                List<Map<String, Object>> catolog = FileCatalogAndFileText.getFileCatalog("data/data/com.example.lawsinqury/法律法规查询/辐射监管相关法律/《中华人民共和国放射性污染防治法》.txt");
                Log.d(TAG, "目录大小：" + catolog.size());
                ListIterator<Map<String, Object>> iterator = catolog.listIterator();
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
                Log.d(TAG, "目录内容："+chapterName);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getLawsName(File f) {
        String filename = f.getName();//file.getName()返回路径下的文件的文件名
        int dot = filename.lastIndexOf('.');
        String LawsName = filename.substring(0, dot);
        return LawsName;
    }


    @Override
    protected void onDestroy() {
        // TODO: 2017/5/21 删除索引文件、删除法律文件
        super.onDestroy();
        String path = "/data/data/com.example.nucleartechnology/法律法规查询";
        File f = new File(path);
        f.delete();
        Log.d(TAG, "onDestroy: ");
        try {
            String[] filelist = FileList.getFiles("/data/data/com.example.nucleartechnology/法律法规查询");
            Log.d(TAG, "删除后法律法规文件夹下含有的文件为：");
            for (int i = 0; i < filelist.length; i++) {
                Log.d(TAG, "        " + i + ": " + filelist[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
