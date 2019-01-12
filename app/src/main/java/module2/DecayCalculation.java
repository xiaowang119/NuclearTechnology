package module2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nucleartechnology.CalculateDecay;
import com.example.nucleartechnology.MainActivity;
import com.example.nucleartechnology.R;

import java.math.BigDecimal;
import java.util.Vector;

import ensdfparser.Query;
import ensdfparser.SonNuclide;
import ensdfparser.StructDecay;

import static ensdfparser.QueryTypeByNuclide.NAME_PARENT;

/**
 * Created by admin on 2017/6/28.
 */

public class DecayCalculation extends AppCompatActivity{
    ActionBar actionBar;
    ImageButton bt;
    Button bt1;
    Button bt2;
    Button bt3;
    TextView tv;
    TextView output1;
    TextView output2;
    TextView output3;
    TextView tv_active;
    TextView tv_rate;
    TextView tv_class;
    EditText et_active;
    EditText et_mass;
    EditText et_year;
    EditText et_month;
    EditText et_day;
    EditText et_hour;
    EditText et_minute;
    EditText et_second;
    Spinner spinner;
    String active_unit;

    //private SQLiteDatabase database;
    //String DATA_PATH = "/data/data/com.example.nucleartechnology/databases" + "/mulu.db";

    private Query query;
    private Vector<StructDecay> resultDecays;
    private StructDecay queryResult;
    private Vector<SonNuclide> sonNuclides;
    private double halfLife;
    private double DecayLamda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module2_decay);
        actionBar=getSupportActionBar();
        actionBar.setTitle("衰变计算");

        output1 = (TextView)findViewById(R.id.output1);
        output2 = (TextView)findViewById(R.id.output2);
        output3 = (TextView)findViewById(R.id.output4);
        tv = (TextView)findViewById(R.id.output3);
        tv_active = (TextView)findViewById(R.id.ac2_tv_active);
        tv_rate = (TextView)findViewById(R.id.ac2_tv_rate);
        tv_class = (TextView)findViewById(R.id.ac2_tv_class);

        bt = (ImageButton)findViewById(R.id.imagebt1);
        bt1 = (Button)findViewById(R.id.ac2_bt_calculate1);
        bt2 = (Button)findViewById(R.id.ac2_bt_calculate2);
        bt3 = (Button)findViewById(R.id.ac2_bt_calculate3);

        et_active = (EditText)findViewById(R.id.ac2_et_active);
        et_mass = (EditText)findViewById(R.id.ac2_et_mass);
        et_mass = (EditText)findViewById(R.id.ac2_et_mass);
        et_year = (EditText)findViewById(R.id.ac2_imput_year);
        et_month = (EditText)findViewById(R.id.ac2_imput_month);
        et_day = (EditText)findViewById(R.id.ac2_imput_day);
        et_hour = (EditText)findViewById(R.id.ac2_imput_hour);
        et_minute = (EditText)findViewById(R.id.ac2_imput_minute);
        et_second = (EditText)findViewById(R.id.ac2_imput_second);

        spinner = (Spinner)findViewById(R.id.spinner_ac2);
        active_unit = "Bq";

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DecayCalculation.this,NuclideChooses.class);
                startActivityForResult(intent,0);
            }
        });

        //衰变顺推计算
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculateDecay decay = new CalculateDecay();
                String nuclide = tv.getText().toString();
                double orgin_active = 0.0D;
                double lamda = 0.0D;
                active_unit = spinner.getSelectedItem().toString();
                if(!nuclide.equals(""))
                {
                    if(!(et_active.getText().toString()).equals(""))
                    {
                        orgin_active = Double.parseDouble(et_active.getText().toString());
                    }
                    else{
                        orgin_active = 0.0D;
                    }
                    Log.e("初始活度 ",et_active.getText().toString()+active_unit);
                    //lamda = Calculate_Lamda(nuclide);
                }
                double losttime = Calculate_losttime();

                if((!nuclide.equals(""))&&(losttime!=0.0D)&&(!et_active.getText().toString().equals("")))
                {
                    double active = decay.CalculateActive(orgin_active,DecayLamda,losttime);
                    double decay_rate = active/orgin_active * 100;

                    String out_active = Sci_To_String(active);
                    String out_rate = Sci_To_String(decay_rate);
                    active = Calculate_origin_active(active);
                    String level = Classifier(Calculate_origin_active(active),queryResult);
                    tv_active.setText("衰变后活度为");
                    output1.setText(out_active + "    "+active_unit);
                    tv_rate.setText("剩余核为");
                    output2.setText(out_rate + "  %");
                    output3.setText(level);
                    Log.e("test","衰变活度计算结果:"+active);
                    Log.e("test","衰变百分比结果:"+decay_rate);
                }
                else{
                    Toast.makeText(DecayCalculation.this,"请检查并输入完整的计算参数",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //衰变逆推计算
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculateDecay decay = new CalculateDecay();
                double orgin_active = 0.0D;
                double lamda=0.0D;
                String nuclide = tv.getText().toString();
                active_unit = spinner.getSelectedItem().toString();
                if(!nuclide.equals(""))
                {
                    if(!et_active.getText().toString().equals(""))
                    {
                        orgin_active = Double.parseDouble(et_active.getText().toString());
                    }
                    else{
                        Toast.makeText(DecayCalculation.this,"请输入有效的当前活度值",Toast.LENGTH_SHORT).show();
                    }
                    //lamda = Calculate_Lamda(nuclide);
                }
                double losttime = Calculate_losttime();
                if((!nuclide.equals(""))&&(losttime!=0.0D)&&(!et_active.getText().toString().equals("")))
                {
                    double active = decay.CalculateOriginActive(orgin_active,DecayLamda,losttime);
                    double decay_rate = orgin_active/active*100;
                    String out_active = Sci_To_String(active);
                    String out_rate = Sci_To_String(decay_rate);
                    orgin_active = Calculate_origin_active(orgin_active);
                    String level = Classifier(Calculate_origin_active(orgin_active),queryResult);
                    tv_active.setText("初始活度为");
                    output1.setText(out_active + "    "+active_unit);
                    tv_rate.setText("初始活度为当前活度的");
                    output2.setText(out_rate + "  %");
                    output3.setText(level);
                    Log.e("test","初始活度计算结果:"+active);
                    Log.e("test","原始活度百分比"+decay_rate);
                }
                else{
                    Toast.makeText(DecayCalculation.this,"请检查并输入完整的计算参数",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //根据物质之量计算活度
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculateDecay decay = new CalculateDecay();
                String nuclide = tv.getText().toString();
                String mass_input = et_mass.getText().toString();
                if(!mass_input.equals("")&&(!nuclide.equals("")))
                {
                    double mass = Double.parseDouble(et_mass.getText().toString());
                    double lamda = 0.0D;
                    double Amass = 0.0D;
                    if(!nuclide.equals(""))
                    {
                        //lamda = Calculate_Lamda(nuclide);
                        Amass = Calculate_Amass(queryResult);
                        Log.e("test","读取输入质量:"+mass);
                        double MassActive;
                        MassActive = decay.CalculateMassActive(mass,DecayLamda,Amass);
                        String result = Classifier(MassActive,queryResult);
                        String out_active = Sci_To_String(MassActive);
                        et_active.setText(out_active);
                        tv_active.setText("由质量计算活度值为");
                        output1.setText(out_active + "    "+" Bq");
                        tv_rate.setText("");
                        output2.setText("");
                        output3.setText(result);
                        Log.e("test","物质活度计算结果:"+MassActive+" Bq");
                    }
                }
                else
                {
                    Toast.makeText(DecayCalculation.this,"输入物质之量与核素不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent intent){
        if(requestCode == 0 && resultCode == 0)
        {
            Bundle bundle = intent.getExtras();
            String name = bundle.getString("name");
            String sequence = bundle.getString("sequence");
            Log.d("fanhuishuju", "name :" + name);
            Log.d("fanhuishuju", "sequence :" + name);

            if(name!=null && sequence!=null)
            {
                tv.setText(name);
                tv.append("-");
                tv.append(sequence);
            }

            query = new Query(MainActivity.listDecays);
            resultDecays = query.findByNuclide(NAME_PARENT, name,sequence);
            if(resultDecays.size()<=0)
                return;
            for (int i = 0; i < resultDecays.size(); i++) {
                queryResult = resultDecays.elementAt(i);
                sonNuclides = queryResult.getSoNuclides();
            }

            //String ziheHalfString="";
            halfLife = 0.0;
            DecayLamda = 0.0;
            for (int i = 0; i < sonNuclides.size(); i++)
            {
                Log.d("halflife", "子核半衰期: " + sonNuclides.elementAt(i).getHalfLife());
                // TODO: 2017/10/16 根据母核对应所有子核的半衰期来计算母核总半衰期
                //lamda +=  sonNuclides.elementAt(i).getHalfLife();
                DecayLamda += Calculate_Lamda(sonNuclides.elementAt(i).getHalfLife());
            }
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

    //查询相应核素半衰期时长，并计算衰变常数
    private double Calculate_Lamda(String strHalfLife){
        double lamda = 0.0; //待返回的衰变常数
        double coef_half;   //单位为秒的半衰期
        double coef_ln2 = 0.6391;//计算参数ln2
        String[] query = null;  //存储中间计算结果
        String halflife = null;
        String unit = null;
        Log.e("test","待查询核素:"+queryResult.getParentName());

        query = strHalfLife.split(" ");
        halflife = query[0];
        unit = query[1];
        switch (unit)
        {
            case "Y": coef_half = Double.parseDouble(halflife)*365*24*60*60;break;
            case "M": coef_half = Double.parseDouble(halflife)*30*24*60*60;break;
            case "D": coef_half = Double.parseDouble(halflife)*24*60*60;break;
            case "H": coef_half = Double.parseDouble(halflife)*60*60;break;
            case "S": coef_half = Double.parseDouble(halflife);break;
            case "MS": coef_half = Double.parseDouble(halflife)*0.001;break;
            case "US": coef_half = Double.parseDouble(halflife)*0.000001;break;
            default: coef_half = 0.0D;break;
        }
        Log.e("test","半衰期计算测试"+coef_half);
        lamda = coef_ln2/coef_half;
        return lamda;
    }

    //计算输入活度，使其单位被统一换算为贝克(/s)
    private double Calculate_origin_active(double value){
        double active = 0.0;
        switch (active_unit)
        {
            case "Ci":  active = value*3.7E10;break;
            case "mCi": active = value*3.7E7;break;
            case "uCi": active = value*3.7E4;break;
            case "Bq":  active = value;break;
            default:    active = 0.0D;break;
        }
        Log.e("test","输入活度计算测试："+active);
        return active;
    }

    //计算作用时长，统一使用秒作为单位
    private double Calculate_losttime(){
        double losttime;
        String string;
        int lost_year = 0;
        int lost_month = 0;
        int lost_day = 0;
        int lost_hour = 0;
        int lost_minute = 0;
        int lost_second = 0;

        string = et_year.getText().toString();
        if(!string.equals("")) {lost_year = Integer.parseInt(string);}

        string = et_month.getText().toString();
        if(!string.equals("")) {lost_month = Integer.parseInt(string);}

        string = et_day.getText().toString();
        if(!string.equals("")) {lost_day = Integer.parseInt(string);}

        string = et_hour.getText().toString();
        if(!string.equals("")) {lost_hour = Integer.parseInt(string);}

        string = et_minute.getText().toString();
        if(!string.equals("")) {lost_minute = Integer.parseInt(string);}

        string = et_second.getText().toString();
        if(!string.equals("")) {lost_second = Integer.parseInt(string);}

        losttime = lost_year*365*24*60*60 + lost_month*30*24*60*60 + lost_day*24*60*60 + lost_hour*60*60
                +lost_minute*60 + lost_second;
        Log.e("test","衰变时长计算:"+losttime);
        return losttime;
    }

    //获取核素摩尔质量
    private double Calculate_Amass(StructDecay nuclide){
        double Amass; //待返回的核素摩尔质量
        Amass = Double.parseDouble(nuclide.getParentMass());
        return Amass;
    }

    //对doubel型数据保留三位有效小数输出
    private String Sci_To_String(Double value)
    {
        String result = String.valueOf(value);
        String[] test = result.split("E");
        if(test.length==1)
        {
            double f = Double.parseDouble(test[0]);
            BigDecimal b = new BigDecimal(f);
            double f1 = b.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
            result = String.valueOf(f1);
            Log.e("返回值输出:",result);
            return result;
        }
        else
        {
            double f = Double.parseDouble(test[0]);
            BigDecimal b = new BigDecimal(f);
            double f1 = b.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
            result = String.valueOf(f1)+"E"+test[1];
            Log.e("返回值输出:",result);
            return result;
        }
    }

    //读取mulu.db，如果存在分组信息，则输出相应分组标识
    private String Classifier(double value,StructDecay nuclide)
    {
        //if(!(nuclide.getGroup()==" "))
        //    return nuclide.getGroup();
        if(nuclide.getLevel5()==null || nuclide.getLevel4()==null || nuclide.getLevel3()==null ||
                nuclide.getLevel2()==null || nuclide.getLevel1()==null)
            return "数据库信息缺失";
        try {
            if (value < Double.parseDouble(nuclide.getLevel5()))
                return "豁免源";
            if (value > Double.parseDouble(nuclide.getLevel5()) &&
                    value < Double.parseDouble(nuclide.getLevel4()))
                return "V源";
            if (value > Double.parseDouble(nuclide.getLevel4()) &&
                    value < Double.parseDouble(nuclide.getLevel3()))
                return "IV类源";
            if (value > Double.parseDouble(nuclide.getLevel3()) &&
                    value < Double.parseDouble(nuclide.getLevel2()))
                return "III类源";
            if (value > Double.parseDouble(nuclide.getLevel2()) &&
                    value < Double.parseDouble(nuclide.getLevel1()))
                return "II类源";
            if (value > Double.parseDouble(nuclide.getLevel1()))
                return "I类源";
        }catch (Exception e) {
            return "数据库信息缺失";
        }
        return "数据库信息缺失";
    }
}

