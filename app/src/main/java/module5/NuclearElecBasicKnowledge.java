package module5;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.nucleartechnology.R;

import module2.ShieldAndDoseCalculation;
import module3.Law;
import module3.LawsMainActivity;

/**
 * Created by 白海涛 on 2017/7/21.
 */

public class NuclearElecBasicKnowledge extends AppCompatActivity {

    private ActionBar actionBar;
    private ListView listView;
    private ListView historyListView;
    private ListView exampleListView;
    private String[] strings = {"1、热堆的概念", "2、轻水堆――压水堆电站", "3、轻水堆――沸水堆电站", "4、重水堆核电站", "5、石墨气冷堆核电站", "6、快堆概念"};
    private String[] historyStrings = {"1、实验示范阶段(1954-1965年) ", "2、高速发展阶段(1966-1980年)", "3、减缓发展阶段(1981-2000年)", "4、开始复苏阶段(21世纪以来)"};
    private String[] nuclearAccidentExamples = {"1.1986年前苏联切尔诺贝利核灾难(INES 7)", "2.2011年日本福岛第一核电站事故(INES 4+)", "3.2004年日本美浜核电站事故(INES 1)"
            , "4.2002年美国戴维斯-贝斯反应堆事故(INES 3)", "5.1961年美国国家反应堆试验站事故(INES 4)", "6.1977年捷克斯洛伐克Bohunice核电站事故(INES 4)"
            ,"7.1993年前苏联托姆斯克-7核燃料回收设施事故(INES 4)","8.1999年日本东海村铀处理设施事故(INES 4)"
            ,"9.1979年美国三里岛核事故(INES 5)","10.1957年前苏联克什特姆核灾难(INES 6)"};
    private String[] FanYingDui = {"file:///android_asset/热堆的概念.html", "file:///android_asset/轻水堆压水堆.html", "file:///android_asset/轻水堆沸水堆.html"
            , "file:///android_asset/重水堆核电站.html", "file:///android_asset/石墨气冷堆核电站.html", "file:///android_asset/快堆概念.html"};
    private String[] history = {"file:///android_asset/实验阶段.html", "file:///android_asset/高速.html"
            , "file:///android_asset/减缓.html", "file:///android_asset/复苏.html"};
    private String[] examples = {"file:///android_asset/案列1.html", "file:///android_asset/案列2.html", "file:///android_asset/案列3.html", "file:///android_asset/案列4.html"
            , "file:///android_asset/案列5.html", "file:///android_asset/案列6.html", "file:///android_asset/案例7.html"
            , "file:///android_asset/案列8.html", "file:///android_asset/案列9.html", "file:///android_asset/案列10.html"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module5_nuclear_basic_knowledge);
        actionBar = getSupportActionBar();
        actionBar.setTitle("核电基础知识");

        listView = (ListView) findViewById(R.id.basicKnowledgeListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.laws_item, strings);
        listView.setAdapter(arrayAdapter);
        setListViewHeightBasedOnChildren(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this, FanYingDui[0], strings[0]);
                        break;
                    case 1:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this, FanYingDui[1], strings[1]);
                        break;
                    case 2:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this,  FanYingDui[2], strings[2]);
                        break;
                    case 3:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this,  FanYingDui[3], strings[3]);
                        break;
                    case 4:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this,  FanYingDui[4], strings[4]);
                        break;
                    case 5:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this,  FanYingDui[5], strings[5]);
                        break;
                }
            }
        });

        historyListView = (ListView) findViewById(R.id.historyListView);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.laws_item, historyStrings);
        historyListView.setAdapter(arrayAdapter1);
        setListViewHeightBasedOnChildren(historyListView);
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this, history[0], historyStrings[0]);
                        break;
                    case 1:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this, history[1], historyStrings[1]);
                        break;
                    case 2:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this, history[2], historyStrings[2]);
                        break;
                    case 3:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this, history[3], historyStrings[3]);
                        break;
                }
            }
        });

        exampleListView = (ListView) findViewById(R.id.nuclearAccidentExamples);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.laws_item, nuclearAccidentExamples);
        exampleListView.setAdapter(arrayAdapter2);
        setListViewHeightBasedOnChildren(exampleListView);
        exampleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this, examples[0], nuclearAccidentExamples[0]);
                        break;
                    case 1:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this, examples[1], nuclearAccidentExamples[1]);
                        break;
                    case 2:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this, examples[2], nuclearAccidentExamples[2]);
                        break;
                    case 3:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this, examples[3], nuclearAccidentExamples[3]);
                        break;
                    case 4:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this, examples[4], nuclearAccidentExamples[4]);
                        break;
                    case 5:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this, examples[5], nuclearAccidentExamples[5]);
                        break;
                    case 6:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this, examples[6], nuclearAccidentExamples[6]);
                        break;
                    case 7:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this, examples[7], nuclearAccidentExamples[7]);
                        break;
                    case 8:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this, examples[8], nuclearAccidentExamples[8]);
                        break;
                    case 9:
                        NuclearElecBasicKnowledgeItem.onActivityStart(NuclearElecBasicKnowledge.this, examples[9], nuclearAccidentExamples[9]);
                        break;
                }
            }
        });
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


}
