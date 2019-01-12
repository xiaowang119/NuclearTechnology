package module2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nucleartechnology.CalculateExteralDose;
import com.example.nucleartechnology.MainActivity;
import com.example.nucleartechnology.Material;
import com.example.nucleartechnology.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import ensdfparser.Query;
import ensdfparser.SonNuclide;
import ensdfparser.StructDecay;

import static ensdfparser.QueryTypeByNuclide.NAME_PARENT;

/**
 * Created by admin on 2017/6/28.
 */

public class ShieldAndDoseCalculation extends AppCompatActivity {

    ActionBar actionBar;
    TextView tv_nuclides;
    EditText start_time;
    EditText end_time;
    EditText et_current_active; //当前活度输入
    EditText et_air_distance;   //作用距离
    EditText et_shield_thick;   //屏蔽层厚度
    EditText et_air_absorpt;    //空气吸收剂量

    Spinner sp_active_unit;     //活度单位选择下拉框
    Spinner sp_airdist_unit;    //作用距离单位下拉框
    Spinner sp_shdthick_unit;   //材料厚度单位下拉框
    //Spinner sp_absorpt_unit;    //空气吸收计量单位下拉框
    Spinner sp_shield_material; //屏蔽层材料下拉框

    ImageButton bt_search;          //选择核素按钮
    Button bt_AirDose;         //计算空气吸收剂量按钮
    Button bt_AirDistance;     //计算作用距离按钮
    Button bt_ShieldThick;     //计算屏蔽层厚度按钮
    Button bt_CalActive;       //计算活度按钮


    String unit_active;
    String unit_absorptdose;
    String unit_airdistance;
    String unit_shieldthick;
    String unit_shieldmaterial;
    private Query query;
    private Vector<StructDecay> resultDecays;
    private StructDecay queryResult;
    private Vector<SonNuclide> sonNuclides;

    private List<Double> energy;
    private List<Double> problity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module2_shield_dose);
        actionBar=getSupportActionBar();
        actionBar.setTitle("点源剂量率估算");

        unit_active = "Bq";
        unit_absorptdose = "Gy";
        unit_airdistance = "cm";
        unit_shieldthick = "cm";
        unit_shieldmaterial = "无屏蔽";

        et_air_absorpt = (EditText)findViewById(R.id.ac3_et_xishou);
        et_current_active = (EditText)findViewById(R.id.ac3_et_huodu);
        et_air_distance = (EditText)findViewById(R.id.ac3_et_juli);
        et_shield_thick = (EditText)findViewById(R.id.ac3_et_houdu);

        sp_active_unit = (Spinner)findViewById(R.id.ac3_sp_active);
        sp_airdist_unit = (Spinner)findViewById(R.id.ac3_sp_distance);
        sp_shdthick_unit = (Spinner)findViewById(R.id.ac3_sp_thickness);
        //sp_absorpt_unit = (Spinner)findViewById(R.id.ac3_sp_absorptdose);
        sp_shield_material = (Spinner)findViewById(R.id.ac3_sp_material);

        tv_nuclides = (TextView)findViewById(R.id.ac3_nuclides);

        bt_search = (ImageButton)findViewById(R.id.ac3_imagebt1);
        bt_CalActive = (Button)findViewById(R.id.ac3_bt_CalActive);
        bt_AirDose = (Button)findViewById(R.id.ac3_bt_AirAbsDose);
        bt_AirDistance = (Button)findViewById(R.id.ac3_bt_AirDistance);
        bt_ShieldThick = (Button)findViewById(R.id.ac3_bt_ShieldThick);

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShieldAndDoseCalculation.this,NuclideChooses.class);
                startActivityForResult(intent,0);
            }
        });

        start_time = (EditText)findViewById(R.id.ac3_et_start);
        end_time = (EditText)findViewById(R.id.ac3_et_end);

        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c  = Calendar.getInstance();
                new DatePickerDialog(ShieldAndDoseCalculation.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        start_time.setText(year+"/"+monthOfYear+"/"+dayOfMonth);
                    }
                }
                        ,c.get(Calendar.YEAR)
                        ,c.get(Calendar.MONTH)
                        ,c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c  = Calendar.getInstance();
                new DatePickerDialog(ShieldAndDoseCalculation.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        end_time.setText(year+"/"+monthOfYear+"/"+dayOfMonth);
                    }
                }
                        ,c.get(Calendar.YEAR)
                        ,c.get(Calendar.MONTH)
                        ,c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //计算空气吸收剂量率
        bt_AirDose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tv_nuclides.getText().toString().equals(""))
                {
                    //String[] query = tv_nuclides.getText().toString().split("-");
                    //Log.e("query",query[0]);
                    //Log.e("query",query[1]);
                    CalculateExteralDose dose = new CalculateExteralDose(" ");
                    //List<Double> Energy = Input_Energy(query[0],query[1]);
                    //List<Double> Prob   = Input_Prob(query[0],query[1]);
                    double distance = getInput_AirDistance();
                    double active = getInput_Active();
                    String material = getShield_Material();
                    if(energy.size()==0)
                    {
                        et_air_absorpt.setText("无伽玛辐射");
                        et_air_absorpt.setTextColor(Color.RED);
                        return;
                    }
                    if(distance > 0.0D)
                    {
                        if(material.equals("无屏蔽"))
                        {
                            double Air_Absorption = dose.AirAbsDose(energy, problity, distance, active)*3600*1000;
                            String result = Sci_To_String(Air_Absorption);
                            et_air_absorpt.setText(result);
                            et_air_absorpt.setTextColor(Color.RED);
                            et_air_distance.setTextColor(Color.BLACK);
                            et_current_active.setTextColor(Color.BLACK);
                            et_shield_thick.setTextColor(Color.BLACK);
                            Log.e("结算结果","无屏蔽空气吸收剂量率"+Air_Absorption);
                        }
                        else if(material.equals("铁屏蔽"))
                        {
                            Material shield = new Material(56,"Fe",1.0D);
                            List<Material> list = new ArrayList<Material>();
                            list.add(shield);
                            double thickness = getInput_thickness();
                            if(thickness!=0.0D){
                                double Air_Absorption = dose.AirAbsDose(energy, problity, distance, active,list,7.9D,thickness)*3600*1000;
                                String result = Sci_To_String(Air_Absorption);
                                et_air_absorpt.setText(result);
                                et_air_absorpt.setTextColor(Color.RED);
                                et_air_distance.setTextColor(Color.BLACK);
                                et_current_active.setTextColor(Color.BLACK);
                                et_shield_thick.setTextColor(Color.BLACK);
                                Log.e("结算结果","铁屏蔽空气吸收剂量率"+Air_Absorption);
                            }
                            else
                            {
                                Toast.makeText(ShieldAndDoseCalculation.this,"请输入屏蔽层厚度之后再执行计算",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(material.equals("铅屏蔽"))
                        {
                            Material shield = new Material(82,"Pb",1.0D);
                            List<Material> list = new ArrayList<Material>();
                            list.add(shield);
                            double thickness = getInput_thickness();
                            if(thickness!=0.0D){
                                double Air_Absorption = dose.AirAbsDose(energy, problity, distance, active,list,11.3437D,thickness)*3600*1000;
                                String result = Sci_To_String(Air_Absorption);
                                et_air_absorpt.setText(result);
                                et_air_absorpt.setTextColor(Color.RED);
                                et_air_distance.setTextColor(Color.BLACK);
                                et_current_active.setTextColor(Color.BLACK);
                                et_shield_thick.setTextColor(Color.BLACK);
                                Log.e("结算结果","铅屏蔽空气吸收剂量率"+Air_Absorption);
                            }
                            else{
                                Toast.makeText(ShieldAndDoseCalculation.this,"请输入屏蔽层厚度后计算",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(material.equals("混凝土"))
                        {
                            Material shield1 = new Material(1,"H",0.0056D);
                            Material shield2 = new Material(8,"O",0.4956D);
                            Material shield3 = new Material(14,"Si",0.3135D);
                            Material shield4 = new Material(13,"Al",0.0456D);
                            Material shield5 = new Material(20,"Ca",0.0826D);
                            Material shield6 = new Material(56,"Fe",0.0122D);
                            Material shield7 = new Material(12,"Mg",0.0024D);
                            Material shield8 = new Material(11,"Na",0.0171D);
                            Material shield9 = new Material(19,"K",0.0192D);
                            Material shield10 = new Material(16,"S",0.0012D);
                            List<Material> list = new ArrayList<Material>();
                            list.add(shield1);
                            list.add(shield2);
                            list.add(shield3);
                            list.add(shield4);
                            list.add(shield5);
                            list.add(shield6);
                            list.add(shield7);
                            list.add(shield8);
                            list.add(shield9);
                            list.add(shield10);
                            double thickness = getInput_thickness();
                            if(thickness!=0.0D) {
                                double Air_Absorption = dose.AirAbsDose(energy, problity, distance, active,list,2.35D,thickness)*3600*1000;
                                String result = Sci_To_String(Air_Absorption);
                                et_air_absorpt.setText(result);
                                et_air_absorpt.setTextColor(Color.RED);
                                et_air_distance.setTextColor(Color.BLACK);
                                et_current_active.setTextColor(Color.BLACK);
                                et_shield_thick.setTextColor(Color.BLACK);
                                Log.e("结算结果","混凝土屏蔽空气吸收剂量率"+Air_Absorption);
                            }
                            else{
                                Toast.makeText(ShieldAndDoseCalculation.this,"请输入屏蔽层厚度后计算",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(ShieldAndDoseCalculation.this,"作用距离必须为正值",Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(ShieldAndDoseCalculation.this,"请选择待计算核素并输入参数",Toast.LENGTH_SHORT).show();
            }
        });

        //计算屏蔽层材料厚度
        bt_ShieldThick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String[] query = null;
                //query = tv_nuclides.getText().toString().split("-");
                if(!tv_nuclides.getText().toString().equals("")) {
                    CalculateExteralDose dose = new CalculateExteralDose(" ");
                    //List<Double> Energy = Input_Energy(query[0], query[1]);
                    //List<Double> Prob = Input_Prob(query[0], query[1]);
                    double distance = getInput_AirDistance();
                    double active = getInput_Active();
                    double airdose = getInput_Airdose();
                    String material = getShield_Material();
                    double thickness = 0.0D;
                    String result = "";
                    if(energy.size()==0)
                    {
                        et_shield_thick.setText("无伽玛辐射参数");
                        et_shield_thick.setTextColor(Color.RED);
                        return;
                    }
                    List<Material> list = new ArrayList<Material>();
                    if((airdose > 0.0D)&&(distance > 0.0D)){
                        switch (material)
                        {
                            case "无屏蔽":
                                Toast.makeText(ShieldAndDoseCalculation.this,"请选择屏蔽材料计算",Toast.LENGTH_SHORT).show();
                                break;

                            case "铁屏蔽":
                                Material Fe = new Material(56,"Fe",1.0D);
                                list.clear();
                                list.add(Fe);
                                thickness = dose.CalculateShieldThick(energy, problity,distance,active,airdose,list,7.9D);
                                Log.e("铁屏蔽层厚度计算:",""+thickness);
                                result = Sci_To_String(thickness);
                                et_shield_thick.setText(result);
                                et_shield_thick.setTextColor(Color.RED);
                                et_current_active.setTextColor(Color.BLACK);
                                et_air_distance.setTextColor(Color.BLACK);
                                et_air_absorpt.setTextColor(Color.BLACK);
                                sp_shdthick_unit.setSelection(0);
                                break;

                            case "铅屏蔽":
                                Material Pb = new Material(82,"Pb",1.0D);
                                list.clear();
                                list.add(Pb);
                                thickness = dose.CalculateShieldThick(energy, problity,distance,active,airdose,list,11.3437D);
                                Log.e("铅屏蔽层厚度计算:",""+thickness);
                                result = Sci_To_String(thickness);
                                et_shield_thick.setText(result);
                                et_shield_thick.setTextColor(Color.RED);
                                et_current_active.setTextColor(Color.BLACK);
                                et_air_distance.setTextColor(Color.BLACK);
                                et_air_absorpt.setTextColor(Color.BLACK);
                                sp_shdthick_unit.setSelection(0);
                                break;

                            case "混凝土":
                                Material shield1 = new Material(1,"H",0.0056D);
                                Material shield2 = new Material(8,"O",0.4956D);
                                Material shield3 = new Material(14,"Si",0.3135D);
                                Material shield4 = new Material(13,"Al",0.0456D);
                                Material shield5 = new Material(20,"Ca",0.0826D);
                                Material shield6 = new Material(56,"Fe",0.0122D);
                                Material shield7 = new Material(12,"Mg",0.0024D);
                                Material shield8 = new Material(11,"Na",0.0171D);
                                Material shield9 = new Material(19,"K",0.0192D);
                                Material shield10 = new Material(16,"S",0.0012D);
                                list.clear();
                                list.add(shield1);
                                list.add(shield2);
                                list.add(shield3);
                                list.add(shield4);
                                list.add(shield5);
                                list.add(shield6);
                                list.add(shield7);
                                list.add(shield8);
                                list.add(shield9);
                                list.add(shield10);
                                thickness = dose.CalculateShieldThick(energy, problity,distance,active,airdose,list,2.35D);
                                Log.e("混凝土蔽层厚度计算:",""+thickness);
                                result = Sci_To_String(thickness);
                                et_shield_thick.setText(result);
                                et_shield_thick.setTextColor(Color.RED);
                                et_current_active.setTextColor(Color.BLACK);
                                et_air_distance.setTextColor(Color.BLACK);
                                et_air_absorpt.setTextColor(Color.BLACK);
                                sp_shdthick_unit.setSelection(0);
                                break;

                            default:break;
                        }
                    }
                    else
                    {
                        Toast.makeText(ShieldAndDoseCalculation.this,"请检查距离与吸收剂量是否有效值",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //计算作用距离
        bt_AirDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String[] query = null;
                //query = tv_nuclides.getText().toString().split("-");
                if(!tv_nuclides.getText().toString().equals(""))
                {
                    CalculateExteralDose dose = new CalculateExteralDose(" ");
                    //List<Double> Energy = Input_Energy(query[0],query[1]);
                    //List<Double> Prob   = Input_Prob(query[0],query[1]);
                    double Air_dose = getInput_Airdose();
                    double active = getInput_Active();
                    if(energy.size()==0)
                    {
                        et_air_distance.setText("无伽玛射线参数");
                        et_air_distance.setTextColor(Color.RED);
                        return;
                    }
                    if(Air_dose > 0)
                    {
                        double distance = dose.CalculateAirDistance(energy, problity,active,Air_dose);
                        Log.e("test","作用距离计算测试:"+distance);
                        String result = Sci_To_String(distance);
                        et_air_distance.setText(result);
                        et_air_distance.setTextColor(Color.RED);
                        et_air_absorpt.setTextColor(Color.BLACK);
                        et_current_active.setTextColor(Color.BLACK);
                        et_shield_thick.setText("0.0");
                        et_shield_thick.setTextColor(Color.rgb(125,125,125));
                        sp_airdist_unit.setSelection(0);
                    }
                    else
                    {
                        Toast.makeText(ShieldAndDoseCalculation.this,"空气吸收剂量不能为0",Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(ShieldAndDoseCalculation.this,"请选择待计算核素并输入相应计算参数",Toast.LENGTH_LONG).show();
            }
        });

        //计算活度
        bt_CalActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String[] query = null;
                //query = tv_nuclides.getText().toString().split("-");
                if(!tv_nuclides.getText().toString().equals(""))
                {
                    CalculateExteralDose dose = new CalculateExteralDose(" ");
                    //List<Double> Energy = Input_Energy(query[0],query[1]);
                    //List<Double> Prob   = Input_Prob(query[0],query[1]);
                    if(energy.size()==0)
                    {
                        et_current_active.setText("无伽玛能谱参数");
                        et_current_active.setTextColor(Color.RED);
                        return;
                    }
                    double Air_dose = getInput_Airdose();
                    double distance = getInput_AirDistance();
                    double active = dose.CalculateActive(energy, problity,distance,Air_dose);
                    Log.e("test","活度计算测试:"+distance);
                    String result = Sci_To_String(active);
                    et_current_active.setText(result);
                    et_current_active.setTextColor(Color.RED);
                    et_air_absorpt.setTextColor(Color.BLACK);
                    et_air_distance.setTextColor(Color.BLACK);
                    sp_shield_material.setSelection(0);
                    et_shield_thick.setTextColor(Color.BLACK);
                    sp_active_unit.setSelection(0);
                }
                else Toast.makeText(ShieldAndDoseCalculation.this,"请选择待计算核素并输入计算参数",Toast.LENGTH_LONG).show();
            }
        });
    }


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

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent intent){
        if(requestCode == 0 && resultCode == 0)
        {
            Bundle bundle = intent.getExtras();
            String name = bundle.getString("name");
            String sequence = bundle.getString("sequence");
            if(name!=null && sequence!=null)
            {
                tv_nuclides.setText(name);
                tv_nuclides.append("-");
                tv_nuclides.append(sequence);
            }

            // 2017年11月 根据选择的核素，返回检索结果ResultDecay
            query = new Query(MainActivity.listDecays);
            resultDecays = query.findByNuclide(NAME_PARENT, name,sequence);
            if(resultDecays.size()<=0)
                return;
            for (int i = 0; i < resultDecays.size(); i++) {
                queryResult = resultDecays.elementAt(i);
                sonNuclides = queryResult.getSoNuclides();
            }

            // 检索出所有的能量
            // 所有的能量存储在零时变量中
            // 检索之间需要清除数组
            if(energy==null)
                energy = new ArrayList<Double>();
            if(problity==null)
                problity = new ArrayList<Double>();

            energy.clear();
            problity.clear();

            for (int i = 0; i < sonNuclides.size(); i++)
            {
                Log.d("halflife", "子核半衰期: " + sonNuclides.elementAt(i).getHalfLife());
                // TODO: 2017/10/16 根据母核对应所有子核的半衰期来计算母核总半衰期
                //lamda +=  sonNuclides.elementAt(i).getHalfLife();
                Vector<String> egString = sonNuclides.elementAt(i).getGammaEnergyVector();
                Vector<String> proString = sonNuclides.elementAt(i).getGammaIntensityVector();
                // 检索所有能量
                int j;
                for(j=0;j<egString.size();j++)
                {
                    if(egString.elementAt(j)!=null && egString.elementAt(j)!=" ") {
                        energy.add(Double.parseDouble(egString.elementAt(j))*0.001);
                        if(proString.elementAt(j)!=null && proString.elementAt(j)!=" ")
                            problity.add(Double.parseDouble(proString.elementAt(j)));
                        else
                            problity.add(0.0);
                    }

                }
            }
        }
    }

    //读取控件输入的活度值，单位同意转换为Bq
    private double getInput_Active()
    {
        double active = 0.0D;
        if(!et_current_active.getText().toString().equals(""))
        {
            active = Double.parseDouble(et_current_active.getText().toString());
        }
        else
        {
            active = 0.0D;
        }
        unit_active = sp_active_unit.getSelectedItem().toString();
        switch (unit_active)
        {
            case "Bq":  break;
            case "Ci":  active = active*3.7E10;break;
            case "mCi": active = active*3.7E7;break;
            case "uCi": active = active*3.7E4;break;
            default:    break;
        }
        Log.e("test","读取活度测试:"+active);
        return active;
    }

    //读取控件输入的作用距离值，单位统一为cm
    private double getInput_AirDistance()
    {
        double air_distance;
        if(!et_air_distance.getText().toString().equals(""))
        {
            air_distance = Double.parseDouble(et_air_distance.getText().toString());
        }
        else
        {
            air_distance = 0.0D;
        }
        unit_airdistance = sp_airdist_unit.getSelectedItem().toString();
        switch (unit_airdistance)
        {
            case "cm": break;
            case "m":   air_distance = air_distance*100;break;
            default:    break;
        }
        Log.e("test","读取输入距离测试:"+air_distance);
        return air_distance;
    }

    //读取输入的材料厚度，单位统一为cm
    private double getInput_thickness()
    {
        double thickness;
        if(!et_shield_thick.getText().toString().equals(""))
        {
            thickness = Double.parseDouble(et_shield_thick.getText().toString());
        }
        else
        {
            thickness = 0.0D;
        }
        unit_shieldthick = sp_shdthick_unit.getSelectedItem().toString();
        switch (unit_shieldthick)
        {
            case "cm":  break;
            case "m":   thickness = thickness*100;break;
            default:    break;
        }
        Log.e("test","读取材料厚度测试:"+thickness);
        return thickness;
    }

    //读取输入的空气吸收剂量率，单位统一为Gy
    private double getInput_Airdose()
    {
        double Airdose;
        if(!et_air_absorpt.getText().toString().equals(""))
        {
            Airdose = Double.parseDouble(et_air_absorpt.getText().toString());
            Airdose = Airdose/3600/1000;
            return Airdose;
        }
        else return 0.0D;
    }

    //读取屏蔽材料选择
    private String getShield_Material()
    {
        String str = sp_shield_material.getSelectedItem().toString();
        return str;
    }

    //读取对应核素gamma粒子放射能量列表
    private List<Double> Input_Energy(String name,String sequence)
    {
        String DATA_PATH = "/data/data/com.example.nucleartechnology/databases"+"/record.db";
        SQLiteDatabase database = null;
        Cursor cursor = null;
        int count;
        List<Double> Energy = new ArrayList<Double>();

        database = SQLiteDatabase.openOrCreateDatabase(DATA_PATH,null);
        cursor = database.rawQuery("select Type,Energy,Intensity from Radardec4OL where ELEM like ? and A like ?",new String[]{name,sequence});
        cursor.moveToFirst();
        count = cursor.getCount();
        Log.e("count",String.valueOf(count));
        if(count!=0)
        {
            Double temp;
            count = 0;
            do{
                String type = cursor.getString(0);
                switch (type)
                {
                    case "A" : break;
                    case "B+": break;
                    case "B-": break;
                    default  : count++; break;
                }
            }while(cursor.moveToNext());

            cursor.moveToFirst();
            if(count!=0)
            {
                do{
                    String type = cursor.getString(0);
                    switch (type)
                    {
                        case "A" : break;
                        case "B+": break;
                        case "B-": break;
                        default  : {
                            temp = Double.parseDouble(cursor.getString(1));
                            temp = temp/1000;
                            Energy.add(temp);
                            break;
                        }
                    }
                }while(cursor.moveToNext());
            }
        }

        for(int i=0;i<Energy.size();i++){
            Log.e("Energy List","测试输出:"+Energy.get(i));
        }
        return Energy;
    }

    //读取对应核素gamma粒子发射概率列表
    private List<Double> Input_Prob(String name,String sequence)
    {
        String DATA_PATH = "/data/data/com.example.nucleartechnology/databases"+"/record.db";
        SQLiteDatabase database = null;
        Cursor cursor = null;
        int count;
        List<Double> Prob = new ArrayList<Double>();

        database = SQLiteDatabase.openOrCreateDatabase(DATA_PATH,null);
        cursor = database.rawQuery("select Type,Energy,Intensity from Radardec4OL where ELEM like ? and A like ?",new String[]{name,sequence});
        cursor.moveToFirst();
        count = cursor.getCount();
        Log.e("count",String.valueOf(count));
        if(count!=0)
        {
            Double temp;
            count = 0;
            do{
                String type = cursor.getString(0);
                switch (type)
                {
                    case "A" : break;
                    case "B+": break;
                    case "B-": break;
                    default  : count++; break;
                }
            }while(cursor.moveToNext());

            cursor.moveToFirst();
            if(count!=0)
            {
                do{
                    String type = cursor.getString(0);
                    switch (type)
                    {
                        case "A" : break;
                        case "B+": break;
                        case "B-": break;
                        default  : {
                            temp = Double.parseDouble(cursor.getString(2));
                            temp = temp/100;
                            Prob.add(temp);
                            break;
                        }
                    }
                }while(cursor.moveToNext());
            }
        }

        for(int i=0;i<Prob.size();i++){
            Log.e("Energy List","测试输出:"+Prob.get(i));
        }
        return Prob;
    }

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
}

