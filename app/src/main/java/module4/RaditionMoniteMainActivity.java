package module4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.nucleartechnology.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

/**
 * Created by 白海涛 on 2017/7/18.
 */

public class RaditionMoniteMainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActionBar actionBar;

    private final String[] strings1 = {"EJ 375-2005《职业性内照射个人监测规定》", "EJ 943-1995《辐射工作人员个人监测管理规定》"
            , "EJ/T 287-2000《氚内照射剂量估算与评价方法》", "GB 16148-2009《放射性核素摄入量及内照射剂量估算规范》"
            , "GB/T 10264-1988《个人和环境监测用热释光测量系统》", "GB/T 18198-2000《矿工氡子体个人累积暴露量估算规范》"
            , "GBZ 128-2002《职业性外照射个人监测规范》", "GBZ 129-2002《职业内照射个人监测规范》"
            , "GBZ 140-2002《空勤人员宇宙辐射控制标准》", "GBZ 166-2005《职业性皮肤放射性污染个人监测规范》"
            , "GBZ/T 148-2002《用于中子测井的CR39中子剂量计的个人剂量监测方法》", "GBZ/T 151-2002《放射事故个人外照射剂量估算原则》"
            , "GBZ/T 154-2006《两种粒度放射性气溶胶年摄入量限值》", "GB/T 10264-2014《个人和环境监测用热释光剂量测量系统》"};
    private final String[] strings2 = {"EJ 1035-2011《土壤中锶-90分析方法》", "EJ/T 1035-1996《土壤中锶-90的分析方法》"
            , "GB 11219.1-1989《土壤中钚的测定 萃取色层法》", "GB 11219.2-1989《土壤中钚的测定 离子交换法》"
            , "GB 11220.1-1989《土壤中铀的测定 分光光度法》", "GB 11743-1989《土壤中放射性核素的γ能谱分析方法》"
            , "GB/T 11743-2013《土壤中放射性核素的γ能谱分析方法》", "GB/T 17947-2008《拟再循环、再利用或作非放射性废物处置的固体物质的放射性活度测量》"};
    private final String[] strings3 = {"EJ/T 1008-1996《空气中14C的取样与测定方法》", "GB/T 14582-1993《环境空气中氡的标准测量方法》"
            , "GB/T 14584-1993《空气中碘-131的取样与测定》", "GBZ/T 155-2002《空气中氡浓度的闪烁瓶测定方法》"
            , "GBZ/T 182-2006《室内氡及其衰变产物测量规范》", "WS/T 184-1999《空气中放射性核素的γ 能谱分析方法》"};
    private final String[] strings4 = {"GB 11221-1989《生物样品中铯-137的放射化学分析方法》", "GB 11222.1-1989《生物样品灰中锶-90的放射化学分析方法 二（2-乙基己基）磷酸酯萃取色层法》"
            , "GB 11223.1-1989《生物样品灰中铀的测定 固体荧光法》", "GB 14882-1994《食品中放射性物质限制浓度标准》"
            , "GB 14883.1-1994《食品中放射性物质检验总则》", "GB/T 16145-1995 《生物样品中放射性核素的γ能谱分析方法》"
            , "GB/T 13273-91《植物、动物甲状腺中碘-131的分析方法》", "GB/T 14674-1993《牛奶中碘-131的分析方法》"
            , "HJ 815-2016《水和生物灰锶-90分析方法》", "WS/T 234-2002《食品中放射性物质检验镅-241的测定》"};
    private final String[] strings5 = {"EJ/T 900-1994《水中总β放射性测定 蒸发法》", "EJ/T 1075-1998《水中总α放射性浓度的测定 厚源法》"
            , "GB 6764-86《水中锶-90放射化学分析方法 发烟硝酸沉淀法》", "GB 6766-1986《水中锶-90放射化学分析方法 二-（2-乙基己基）磷酸萃取色层法》"
            , "GB 6767-1986《水中铯-137放射化学分析方法》", "GB 11214-1989《水中镭-226的分析测定》"
            , "GB 11224-1989《水中钍的分析方法》", "GB 11225-1989《水中钚的分析方法》"
            , "GB 11338-1989《水中钾-40的分析方法》", "GB 12375-1990《水中氚的分析方法》"
            , "GB 12376-1990《水中钋-210的分析方法 电镀制样法》", "GB/T 5750.13-2006《生活饮用水标准检验方法 放射性指标》"
            , "GB/T 8538-2008《饮用天然矿泉水检验方法（总β、镭-226、氢-3）》", "GB/T 16140-1995《水中放射性核素的的γ能谱分析方法》"
            , "GB/T6768-1986《水中微量铀分析方法（液体激光荧光法）》", "GB/T 6920-1986《水质PH值侧定  电极法》"
            , "GB/T 13580.3-1992《大气降水电导率的测定方法》", "GB/T 13580.4-1992《大气降水pH值的测定  电极法》"
            , "GB/T 13580.9-1992《大气降水中氯化物的测定 硫氰酸汞高铁光度法》", "GB/T 15221-1994《水中钴-60的分析方法》"
            , "HJ 815-2016《水和生物灰锶-90分析方法》"};
    private final String[] strings6 = {"GB 8999-1988《电离辐射监测质量保证一般规定》", "GB/T 11713-1989《用半导体γ谱仪分析低比活度γ放射性样品的标准方法》"
            , "GB/T 11713-2015《高纯锗γ能谱分析通用方法》", "GB/T 14583-1993《环境地表γ辐射剂量率测定规范》"
            , "GB/T 14583-1993《环境地表γ辐射剂量率测定规范》", "HJ/T 22-1998《气载放射性物质取样一般规定》"
            , "HJ/T 61-2001《辐射环境监测技术规范》"};
    private final String[] strings7 = {"WS/T184-1999空气中放射性核素的γ能谱分析方法", "GB/T 16140-1995水中放射性核素的的γ能谱分析方法"
            , "GB/T 11743-2013土壤中放射性核素的γ能谱分析方法", "GB/T 16145-1995生物样品中放射性核素的γ能谱分析方法"
            , "GB/T 14584 -1993空气中碘-131的取样与测定", "GB/T 11713-2015高纯锗γ能谱分析通用方法"};
    private final String[] strings8 = {"GB/T 10264-2014个人和环境监测用热释光剂量测量系统"};
    private final String[] strings9 = {"EJ/T 1075-1998水中总α放射性浓度的测定 厚源法", "EJ/T 900-1994水中总β放射性测定 蒸发法"};
    private final String[] strings10 = {"HJ 815-2016水和生物灰锶-90分析方法", "HJ 815-2016水和生物灰锶-90分析方法", "EJ/T 1035-2011土壤中锶-90的分析方法"};
    private final String[] strings11 = {"GB/T6767-1986水中铯-137放射化学分析方法", "GB/T11221-89生物样品灰中铯-137放射化学分析方法"};
    private final String[] strings12 = {"GB 12375-1990水中氚的分析方法", "GB 12375-1990水中氚的分析方法"};
    private final String[] strings13 = {"EJ/T 1008-1996空气中14C的取样与测定方法", "EJ/T1008-1996空气中14C的取样与测定方法"};
    private final String[] strings14 = {"生物\tGB/T 14674-1993\t《牛奶中碘-131的分析方法》"};


    private LinearLayout linearLayout1,linearLayout2,linearLayout3, linearLayout4,linearLayout5,linearLayout6,linearLayout7
            ,linearLayout8,linearLayout9,linearLayout10,linearLayout11,linearLayout12,linearLayout13, linearLayout14;
    private ListView listView1,listView2,listView3,listView4,listView5,listView6,listView7,
            listView8,listView9,listView10,listView11,listView12,listView13, listView14;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module4_radiadion_monite_main);
        actionBar = getSupportActionBar();
        actionBar.setTitle("辐射监测分析标准");
        loadPDFs();

        linearLayout1 = (LinearLayout) findViewById(R.id.module4linearLayout1);
        linearLayout2 = (LinearLayout) findViewById(R.id.module4linearLayout2);
        linearLayout3 = (LinearLayout) findViewById(R.id.module4linearLayout3);
        linearLayout4 = (LinearLayout) findViewById(R.id.module4linearLayout4);
        linearLayout5 = (LinearLayout) findViewById(R.id.module4linearLayout5);
        linearLayout6 = (LinearLayout) findViewById(R.id.module4linearLayout6);
        linearLayout7 = (LinearLayout) findViewById(R.id.module4linearLayout7);
        linearLayout8 = (LinearLayout) findViewById(R.id.module4linearLayout8);
        linearLayout9 = (LinearLayout) findViewById(R.id.module4linearLayout9);
        linearLayout10 = (LinearLayout) findViewById(R.id.module4linearLayout10);
        linearLayout11 = (LinearLayout) findViewById(R.id.module4linearLayout11);
        linearLayout12 = (LinearLayout) findViewById(R.id.module4linearLayout12);
        linearLayout13 = (LinearLayout) findViewById(R.id.module4linearLayout13);
        linearLayout14 = (LinearLayout) findViewById(R.id.module4linearLayout14);

        listView1 = (ListView) findViewById(R.id.module4listView1);
        listView2 = (ListView) findViewById(R.id.module4listView2);
        listView3 = (ListView) findViewById(R.id.module4listView3);
        listView4 = (ListView) findViewById(R.id.module4listView4);
        listView5 = (ListView) findViewById(R.id.module4listView5);
        listView6 = (ListView) findViewById(R.id.module4listView6);
        listView7 = (ListView) findViewById(R.id.module4listView7);
        listView8 = (ListView) findViewById(R.id.module4listView8);
        listView9 = (ListView) findViewById(R.id.module4listView9);
        listView10 = (ListView) findViewById(R.id.module4listView10);
        listView11 = (ListView) findViewById(R.id.module4listView11);
        listView12 = (ListView) findViewById(R.id.module4listView12);
        listView13 = (ListView) findViewById(R.id.module4listView13);
        listView14 = (ListView) findViewById(R.id.module4listView14);

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.laws_item, strings1);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.laws_item, strings2);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(getApplicationContext(), R.layout.laws_item, strings3);
        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(getApplicationContext(), R.layout.laws_item, strings4);
        ArrayAdapter<String> arrayAdapter5 = new ArrayAdapter<String>(getApplicationContext(), R.layout.laws_item, strings5);
        ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<String>(getApplicationContext(), R.layout.laws_item, strings6);
        ArrayAdapter<String> arrayAdapter7 = new ArrayAdapter<String>(getApplicationContext(), R.layout.laws_item, strings7);
        ArrayAdapter<String> arrayAdapter8 = new ArrayAdapter<String>(getApplicationContext(), R.layout.laws_item, strings8);
        ArrayAdapter<String> arrayAdapter9 = new ArrayAdapter<String>(getApplicationContext(), R.layout.laws_item, strings9);
        ArrayAdapter<String> arrayAdapter10 = new ArrayAdapter<String>(getApplicationContext(), R.layout.laws_item, strings10);
        ArrayAdapter<String> arrayAdapter11 = new ArrayAdapter<String>(getApplicationContext(), R.layout.laws_item, strings11);
        ArrayAdapter<String> arrayAdapter12 = new ArrayAdapter<String>(getApplicationContext(), R.layout.laws_item, strings12);
        ArrayAdapter<String> arrayAdapter13 = new ArrayAdapter<String>(getApplicationContext(), R.layout.laws_item, strings13);
        ArrayAdapter<String> arrayAdapter14 = new ArrayAdapter<String>(getApplicationContext(), R.layout.laws_item, strings14);

        listView1.setAdapter(arrayAdapter1);
        listView2.setAdapter(arrayAdapter2);
        listView3.setAdapter(arrayAdapter3);
        listView4.setAdapter(arrayAdapter4);
        listView5.setAdapter(arrayAdapter5);
        listView6.setAdapter(arrayAdapter6);
        listView7.setAdapter(arrayAdapter7);
        listView8.setAdapter(arrayAdapter8);
        listView9.setAdapter(arrayAdapter9);
        listView10.setAdapter(arrayAdapter10);
        listView11.setAdapter(arrayAdapter11);
        listView12.setAdapter(arrayAdapter12);
        listView13.setAdapter(arrayAdapter13);
        listView14.setAdapter(arrayAdapter14);
        setListViewHeightBasedOnChildren(listView1);
        setListViewHeightBasedOnChildren(listView2);
        setListViewHeightBasedOnChildren(listView3);
        setListViewHeightBasedOnChildren(listView4);
        setListViewHeightBasedOnChildren(listView5);
        setListViewHeightBasedOnChildren(listView6);
        setListViewHeightBasedOnChildren(listView7);
        setListViewHeightBasedOnChildren(listView8);
        setListViewHeightBasedOnChildren(listView9);
        setListViewHeightBasedOnChildren(listView10);
        setListViewHeightBasedOnChildren(listView11);
        setListViewHeightBasedOnChildren(listView12);
        setListViewHeightBasedOnChildren(listView13);
        setListViewHeightBasedOnChildren(listView14);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Bundle data=new Bundle();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:viewPDFs(data,"1-1.pdf");break;
                    case 1:viewPDFs(data, "1-2.pdf");break;
                    case 2:viewPDFs(data, "1-3.pdf");break;
                    case 3:viewPDFs(data, "1-4.pdf");break;
                    case 4:viewPDFs(data, "1-5.pdf");break;
                    case 5:viewPDFs(data, "1-6.pdf");break;
                    case 6:viewPDFs(data, "1-7.pdf");break;
                    case 7:viewPDFs(data, "1-8.pdf");break;
                    case 8:viewPDFs(data, "1-9.pdf");break;
                    case 9:viewPDFs(data, "1-10.pdf");break;
                    case 10:viewPDFs(data, "1-11.pdf");break;
                    case 11:viewPDFs(data, "1-12.pdf");break;
                    case 12:viewPDFs(data, "1-13.pdf");break;
                    case 13:viewPDFs(data, "1-14.pdf");break;
                }
            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Bundle data = new Bundle();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:viewPDFs(data, "2-1.pdf");break;
                    case 1:viewPDFs(data, "2-2.pdf");break;
                    case 2:viewPDFs(data, "2-3.pdf");break;
                    case 3:viewPDFs(data, "2-4.pdf");break;
                    case 4:viewPDFs(data, "2-5.pdf");break;
                    case 5:viewPDFs(data, "2-6.pdf");break;
                    case 6:viewPDFs(data, "2-7.pdf");break;
                    case 7:viewPDFs(data, "2-8.pdf");break;
                }
            }
        });

        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Bundle data = new Bundle();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:viewPDFs(data, "3-1.pdf");break;
                    case 1:viewPDFs(data, "3-2.pdf");break;
                    case 2:viewPDFs(data, "3-3.pdf");break;
                    case 3:viewPDFs(data, "3-4.pdf");break;
                    case 4:viewPDFs(data, "3-5.pdf");break;
                    case 5:viewPDFs(data, "3-6.pdf");break;
                }
            }
        });

        listView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Bundle data=new Bundle();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:viewPDFs(data, "4-1.pdf");break;
                    case 1:viewPDFs(data, "4-2.pdf");break;
                    case 2:viewPDFs(data, "4-3.pdf");break;
                    case 3:viewPDFs(data, "4-4.pdf");break;
                    case 4:viewPDFs(data, "4-5.pdf");break;
                    case 5:viewPDFs(data, "4-6.pdf");break;
                    case 6:viewPDFs(data, "4-7.pdf");break;
                    case 7:viewPDFs(data, "4-8.pdf");break;
                    case 8:viewPDFs(data, "4-9.pdf");break;
                    case 9:viewPDFs(data, "4-10.pdf");break;
                }
            }
        });

        listView5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Bundle data=new Bundle();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:viewPDFs(data, "5-1.pdf");break;
                    case 1:viewPDFs(data, "5-2.pdf");break;
                    case 2:viewPDFs(data, "5-3.pdf");break;
                    case 3:viewPDFs(data, "5-4.pdf");break;
                    case 4:viewPDFs(data, "5-5.pdf");break;
                    case 5:viewPDFs(data, "5-6.pdf");break;
                    case 6:viewPDFs(data, "5-7.pdf");break;
                    case 7:viewPDFs(data, "5-8.pdf");break;
                    case 8:viewPDFs(data, "5-9.pdf");break;
                    case 9:viewPDFs(data, "5-10.pdf");break;
                    case 10:viewPDFs(data, "5-11.pdf");break;
                    case 11:viewPDFs(data, "5-12.pdf");break;
                    case 12:viewPDFs(data, "5-13.pdf");break;
                    case 13:viewPDFs(data, "5-14.pdf");break;
                    case 14:viewPDFs(data, "5-15.pdf");break;
                    case 15:viewPDFs(data, "5-16.pdf");break;
                    case 16:viewPDFs(data, "5-17.pdf");break;
                    case 17:viewPDFs(data, "5-18.pdf");break;
                    case 18:viewPDFs(data, "5-19.pdf");break;
                    case 19:viewPDFs(data, "5-20.pdf");break;
                    case 20:viewPDFs(data, "5-21.pdf");break;
                }
            }
        });

        listView6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Bundle data = new Bundle();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:viewPDFs(data, "6-1.pdf");break;
                    case 1:viewPDFs(data, "6-2.pdf");break;
                    case 2:viewPDFs(data, "6-3.pdf");break;
                    case 3:viewPDFs(data, "6-4.pdf");break;
                    case 4:viewPDFs(data, "6-5.pdf");break;
                    case 5:viewPDFs(data, "6-6.pdf");break;
                    case 6:viewPDFs(data, "6-7.pdf");break;
                }
            }
        });




    }

    public void viewPDFs(Bundle data, String pdfName) {
        Intent intent = new Intent(RaditionMoniteMainActivity.this, com.artifex.mupdfdemo.MainActivity.class);
        data.clear();
        data.putString("path", pdfName);
        intent.putExtras(data);
        startActivity(intent);

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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.module4arrowDownButton1:
                if (linearLayout1.getVisibility() == View.VISIBLE) {
                    linearLayout1.setVisibility(View.GONE);
                } else {
                    linearLayout1.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.module4arrowDownButton2:
                if (linearLayout2.getVisibility() == View.VISIBLE) {
                    linearLayout2.setVisibility(View.GONE);
                } else {
                    linearLayout2.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.module4arrowDownButton3:
                if (linearLayout3.getVisibility() == View.VISIBLE) {
                    linearLayout3.setVisibility(View.GONE);
                } else {
                    linearLayout3.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.module4arrowDownButton4:
                if (linearLayout4.getVisibility() == View.VISIBLE) {
                    linearLayout4.setVisibility(View.GONE);
                } else {
                    linearLayout4.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.module4arrowDownButton5:
                if (linearLayout5.getVisibility() == View.VISIBLE) {
                    linearLayout5.setVisibility(View.GONE);
                } else {
                    linearLayout5.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.module4arrowDownButton6:
                if (linearLayout6.getVisibility() == View.VISIBLE) {
                    linearLayout6.setVisibility(View.GONE);
                } else {
                    linearLayout6.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.module4arrowDownButton7:
                if (linearLayout7.getVisibility() == View.VISIBLE) {
                    linearLayout7.setVisibility(View.GONE);
                } else {
                    linearLayout7.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.module4arrowDownButton8:
                if (linearLayout8.getVisibility() == View.VISIBLE) {
                    linearLayout8.setVisibility(View.GONE);
                } else {
                    linearLayout8.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.module4arrowDownButton9:
                if (linearLayout9.getVisibility() == View.VISIBLE) {
                    linearLayout9.setVisibility(View.GONE);
                } else {
                    linearLayout9.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.module4arrowDownButton10:
                if (linearLayout10.getVisibility() == View.VISIBLE) {
                    linearLayout10.setVisibility(View.GONE);
                } else {
                    linearLayout10.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.module4arrowDownButton11:
                if (linearLayout11.getVisibility() == View.VISIBLE) {
                    linearLayout11.setVisibility(View.GONE);
                } else {
                    linearLayout11.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.module4arrowDownButton12:
                if (linearLayout12.getVisibility() == View.VISIBLE) {
                    linearLayout12.setVisibility(View.GONE);
                } else {
                    linearLayout12.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.module4arrowDownButton13:
                if (linearLayout13.getVisibility() == View.VISIBLE) {
                    linearLayout13.setVisibility(View.GONE);
                } else {
                    linearLayout13.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.module4arrowDownButton14:
                if (linearLayout14.getVisibility() == View.VISIBLE) {
                    linearLayout14.setVisibility(View.GONE);
                } else {
                    linearLayout14.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    private void loadPDFs() {
        String path = "/data/data/com.example.nucleartechnology/PDFs";
        String[] str = {"1-1.pdf", "1-2.pdf", "1-3.pdf", "1-4.pdf", "1-5.pdf", "1-6.pdf", "1-7.pdf", "1-8.pdf", "1-9.pdf", "1-10.pdf", "1-11.pdf", "1-12.pdf", "1-13.pdf", "1-14.pdf",
                "2-1.pdf", "2-2.pdf", "2-3.pdf", "2-4.pdf", "2-5.pdf", "2-6.pdf", "2-7.pdf", "2-8.pdf",
                "3-1.pdf", "3-2.pdf", "3-3.pdf", "3-4.pdf", "3-5.pdf", "3-6.pdf",
                "4-1.pdf", "4-2.pdf", "4-3.pdf", "4-4.pdf", "4-5.pdf", "4-6.pdf", "4-7.pdf", "4-8.pdf", "4-9.pdf", "4-10.pdf",
                "5-1.pdf", "5-2.pdf", "5-3.pdf", "5-4.pdf", "5-5.pdf", "5-6.pdf", "5-7.pdf", "5-8.pdf", "5-9.pdf", "5-10.pdf",
                "5-11.pdf", "5-12.pdf", "5-13.pdf", "5-14.pdf", "5-15.pdf", "5-16.pdf", "5-17.pdf", "5-18.pdf", "5-19.pdf", "5-20.pdf", "5-21.pdf",
                "6-1.pdf", "6-2.pdf", "6-3.pdf", "6-4.pdf", "6-5.pdf", "6-6.pdf", "6-7.pdf",};
//        String[] str = {"1-1.pdf"};

        File file1 = new File(path);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        for (int i = 0; i < str.length; i++) {
            File file = new File(path, str[i]);
            try {
                if (!file.exists()) {
                    file.createNewFile();
                    Log.d("test", "正在写入:" + str[i]);
                    InputStream is = this.getAssets().open(str[i]);
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] buffer = new byte[is.available()];
                    is.read(buffer);
                    fos.write(buffer);
                    is.close();
                    fos.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
