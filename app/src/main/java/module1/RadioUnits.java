package module1;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.nucleartechnology.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/6/28.
 */

public class RadioUnits extends AppCompatActivity {

    ExpandableListView listView;
    ArrayList<String> parent = null;
    Map<String, List<String>> map = null;
    ActionBar actionBar;
    EditText lunqin =   null;
    EditText kuke   =   null;
    EditText gerui  =   null;
    EditText lade   =   null;
    EditText xifu   =   null;
    EditText leimu  =   null;
    EditText juli   =   null;
    EditText beike  =   null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module1_unit);
        actionBar=getSupportActionBar();
        actionBar.setTitle("辐射常用单位");

        initData();
        listView = (ExpandableListView)findViewById(R.id.ex_lv);
        listView.setAdapter(new MyAdapter(parent,map));

        lunqin = (EditText)findViewById(R.id.ac10_et_zhaoshe1);
        kuke = (EditText)findViewById(R.id.ac10_et_zhaoshe2);
        gerui = (EditText)findViewById(R.id.ac10_et_xishou1);
        lade = (EditText)findViewById(R.id.ac10_et_xishou2);
        xifu = (EditText)findViewById(R.id.ac10_et_dangliang1);
        leimu = (EditText)findViewById(R.id.ac10_et_dangliang2);
        juli = (EditText)findViewById(R.id.ac10_et_huodu1);
        beike = (EditText)findViewById(R.id.ac10_et_huodu2);

        lunqin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                lunqin.setTextColor(Color.BLACK);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String val_lunqin = lunqin.getText().toString();
                if(!val_lunqin.equals(""))
                {
                    Double input = Double.parseDouble(val_lunqin);
                    Double output = input * 2.58E-4;
                    //String val_kuke = String.valueOf(output);
                    // 2017年shinecore  formate
                    String val_kuke = String.format("%.2e",output);
                    kuke.setText(val_kuke);
                    kuke.setTextColor(Color.RED);
                    Log.e("test","单位换算完成 库克："+val_kuke);
                }
            }
        });

/*
        kuke.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                kuke.setTextColor(Color.BLACK);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String val_kuke = kuke.getText().toString();
                if(!val_kuke.equals(""))
                {
                    Double input = Double.parseDouble(val_kuke);
                    Double output = input/2.58E-4;
                    String val_lunqin = String.valueOf(output);
                    lunqin.setText(val_lunqin);
                    lunqin.setTextColor(Color.RED);
                    Log.e("test","单位换算完成 库克："+val_lunqin);
                }
            }
        });
        */

        gerui.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                gerui.setTextColor(Color.BLACK);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String val_gerui = gerui.getText().toString();
                if(!val_gerui.equals(""))
                {
                    Double input = Double.parseDouble(val_gerui);
                    Double output = input * 100;
                    //String val_lade = String.valueOf(output);
                    // 2017 shinecore
                    String val_lade = String.format("%.2e",output);
                    lade.setText(val_lade);
                    lade.setTextColor(Color.RED);
                    Log.e("test","单位换算完成 库克："+val_lade);
                }
            }
        });
/*
        lade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                lade.setTextColor(Color.BLACK);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String val_lade = lade.getText().toString();
                if(!val_lade.equals(""))
                {
                    Double input = Double.parseDouble(val_lade);
                    Double output = input/100;
                    String val_gerui = String.valueOf(output);
                    gerui.setText(val_gerui);
                    gerui.setTextColor(Color.RED);
                    Log.e("test","单位换算完成 库克："+val_gerui);
                }
            }
        });
*/
        xifu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                xifu.setTextColor(Color.BLACK);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String val_xifu = xifu.getText().toString();
                if(!val_xifu.equals(""))
                {
                    Double input = Double.parseDouble(val_xifu);
                    Double output = input*100;
                    //String val_leimu = String.valueOf(output);
                    // 2017 shinecore
                    String val_leimu = String.format("%.2e",output);
                    leimu.setText(val_leimu);
                    leimu.setTextColor(Color.RED);
                    Log.e("test","单位换算完成 库克："+val_leimu);
                }
            }
        });

        /*
        leimu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                leimu.setTextColor(Color.BLACK);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String val_leimu = leimu.getText().toString();
                if(!val_leimu.equals(""))
                {
                    Double input = Double.parseDouble(val_leimu);
                    Double output = input/100;
                    String val_xifu = String.valueOf(output);
                    xifu.setText(val_xifu);
                    xifu.setTextColor(Color.RED);
                    Log.e("test","单位换算完成 库克："+val_xifu);
                }
            }
        });
        */

        juli.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                juli.setTextColor(Color.BLACK);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String val_juli = juli.getText().toString();
                if(!val_juli.equals(""))
                {
                    Double input = Double.parseDouble(val_juli);
                    Double output = input*3.7E10;
                    //String val_beike = String.valueOf(output);
                    // 2017
                    String val_beike = String.format("%.2e",output);
                    beike.setText(val_beike);
                    beike.setTextColor(Color.RED);
                    Log.e("test","单位换算完成 库克："+val_beike);
                }
            }
        });
/*
        beike.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beike.setTextColor(Color.BLACK);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String val_beike = beike.getText().toString();
                if(!val_beike.equals(""))
                {
                    Double input = Double.parseDouble(val_beike);
                    Double output = input/3.7E10;
                    String val_juli = String.valueOf(output);
                    juli.setText(val_juli);
                    juli.setTextColor(Color.RED);
                    Log.e("test","单位换算完成 库克："+val_juli);
                }
            }
        });
        */

    }

    // 初始化数据
    public void initData() {
        parent = new ArrayList<String>();
        map = new HashMap<String, List<String>>();

        parent.add("照射量");
        parent.add("空气吸收剂量");
        parent.add("当量剂量");
        parent.add("放射性活度");

        List<String> list1 = new ArrayList<String>();
        list1.add("库伦·千克-1 (c·kg-1)");
        list1.add("伦琴 (r)");
        list1.add("1r = 2.58*10^-4 c·kg-1");
        map.put("照射量", list1);

        List<String> list3 = new ArrayList<String>();
        list3.add("焦耳·千克-1 (j·kg-1)");
        list3.add("戈瑞 (gy)");
        list3.add("拉德 (rad)");
        list3.add(" 1gy = 1 j·kg-1");
        list3.add("1rad = 10^-2 j·kg-1");
        map.put("空气吸收剂量", list3);


        List<String> list4 = new ArrayList<String>();
        list4.add("焦耳·千克-1 (j·kg-1)");
        list4.add("希沃特 (sv)");
        list4.add("雷姆 (rem)");
        list4.add("1sv = 1 j·kg-1");
        list4.add("1rem = 10^-2 j·kg-1");
        map.put("当量剂量", list4);

        List<String> list5 = new ArrayList<String>();
        list5.add("贝克勒尔 (bq)");
        list5.add("居里 (Ci)");
        list5.add("1bq = 1 s-1");
        list5.add("1Ci = 3.7*10^10 s-1");
        map.put("放射性活度", list5);
    }

    class MyAdapter extends BaseExpandableListAdapter {
        private ArrayList<String> title;
        private Map<String, List<String>> item;

        public MyAdapter(ArrayList<String> grouptitle, Map<String, List<String>> groupitem) {
            this.title = grouptitle;
            this.item = groupitem;
        }

        //得到子item需要关联的数据
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            String key = title.get(groupPosition);
            return (item.get(key).get(childPosition));
        }

        //得到子item的ID
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        //设置子item的组件
        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            String key = title.get(groupPosition);
            String info = item.get(key).get(childPosition);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) RadioUnits.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.child_item, null);
            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.tv_item);
            tv.setText(info);
            return tv;
        }

        //获取当前父item下的子item的个数
        @Override
        public int getChildrenCount(int groupPosition) {
            String key = title.get(groupPosition);
            int size = item.get(key).size();
            return size;
        }
        //获取当前父item的数据
        @Override
        public Object getGroup(int groupPosition) {
            return title.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return title.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }
        //设置父item组件
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) RadioUnits.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.parent_item, null);
            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.tv_title);
            tv.setText(title.get(groupPosition));
            return tv;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.isCheckable())
        {
            item.setCheckable(true);
        }
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
