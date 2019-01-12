package module7;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nucleartechnology.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static module7.LoginActivity.ID;
import static module7.LoginActivity.socket;
import static module7.LoginActivity.user;
import static module7.MonitorActivity.A1;
import static module7.MonitorActivity.A2;
import static module7.MonitorActivity.A3;
import static module7.MonitorActivity.A4;
import static module7.MonitorActivity.A5;
import static module7.MonitorActivity.A6;

public class EssentialActivity extends AppCompatActivity {

    public static String da;
    private CheckBox checkBox10;
    private CheckBox checkBox20;
    private CheckBox checkBox21;
    private CheckBox checkBox22;
    private CheckBox checkBox23;
    private CheckBox checkBox24;
    private CheckBox checkBox30;
    private CheckBox checkBox31;
    private CheckBox checkBox32;
    private CheckBox checkBox33;
    private CheckBox checkBox40;
    private CheckBox checkBox50;
    private CheckBox checkBox51;
    private CheckBox checkBox52;
    private CheckBox checkBox53;
    private CheckBox checkBox54;
    String A;
    String B;
    String C;
    String D;
    String E;
    String F;
    String G;
    String H;
    String I;
    String J;
    String K;

    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essential);
        ActivityCollector.addActivity(this);//创建一个新活动时加入活动管理器
        checkBox10 = (CheckBox) findViewById(R.id.checkBox10);
        checkBox20 = (CheckBox) findViewById(R.id.checkBox20);
        checkBox21 = (CheckBox) findViewById(R.id.checkBox21);
        checkBox22 = (CheckBox) findViewById(R.id.checkBox22);
        checkBox23 = (CheckBox) findViewById(R.id.checkBox23);
        checkBox24 = (CheckBox) findViewById(R.id.checkBox24);
        checkBox30 = (CheckBox) findViewById(R.id.checkBox30);
        checkBox31 = (CheckBox) findViewById(R.id.checkBox31);
        checkBox32 = (CheckBox) findViewById(R.id.checkBox32);
        checkBox33 = (CheckBox) findViewById(R.id.checkBox33);
        checkBox40 = (CheckBox) findViewById(R.id.checkBox40);
        checkBox50 = (CheckBox) findViewById(R.id.checkBox50);
        checkBox51 = (CheckBox) findViewById(R.id.checkBox51);
        checkBox52 = (CheckBox) findViewById(R.id.checkBox52);
        checkBox53 = (CheckBox) findViewById(R.id.checkBox53);
        checkBox54 = (CheckBox) findViewById(R.id.checkBox54);
        final TextView textView = (TextView)findViewById(R.id.textView);
        //checkBox20点击事件
        //点击checkbox20全选或全不选事件
        checkBox20.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkBox20.isChecked()){
                    checkBox21.setChecked(true);
                    checkBox22.setChecked(true);
                    checkBox23.setChecked(true);
                    checkBox24.setChecked(true);
                }else if(!checkBox20.isChecked()){
                    checkBox21.setChecked(false);
                    checkBox22.setChecked(false);
                    checkBox23.setChecked(false);
                    checkBox24.setChecked(false);
                }
            }
        });
        //点击checkbox30全选或全不选事件
        checkBox30.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkBox30.isChecked()){
                    checkBox31.setChecked(true);
                    checkBox32.setChecked(true);
                    checkBox33.setChecked(true);
                }else if(!checkBox30.isChecked()){
                    checkBox31.setChecked(false);
                    checkBox32.setChecked(false);
                    checkBox33.setChecked(false);
                }
            }
        });
        //点击checkbox40打开对话框
        checkBox40.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showdialog();
                checkBox20.setChecked(false);
                checkBox21.setChecked(false);
                checkBox22.setChecked(false);
                checkBox23.setChecked(false);
                checkBox24.setChecked(false);
                checkBox30.setChecked(false);
                checkBox31.setChecked(false);
                checkBox32.setChecked(false);
                checkBox33.setChecked(false);
                checkBox50.setChecked(false);
                checkBox51.setChecked(false);
                checkBox52.setChecked(false);
                checkBox53.setChecked(false);
                checkBox54.setChecked(false);
            }
        });
        //点击checkbox50全选或全不选事件
        checkBox50.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkBox50.isChecked()){
                    checkBox51.setChecked(true);
                    checkBox52.setChecked(true);
                    checkBox53.setChecked(true);
                    checkBox54.setChecked(true);
                }else if(!checkBox50.isChecked()){
                    checkBox51.setChecked(false);
                    checkBox52.setChecked(false);
                    checkBox53.setChecked(false);
                    checkBox54.setChecked(false);
                }
            }
        });
        //第二条的checkbox选中事件
        checkBox21.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkBox21.isChecked() && checkBox22.isChecked() && checkBox23.isChecked() && checkBox24.isChecked()){
                    checkBox20.setChecked(true);
                }else if(!checkBox21.isChecked() && checkBox22.isChecked() && checkBox23.isChecked() && checkBox24.isChecked()){
                    checkBox20.setChecked(false);
                }
            }
        });
        checkBox22.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkBox21.isChecked() && checkBox22.isChecked() && checkBox23.isChecked() && checkBox24.isChecked()){
                    checkBox20.setChecked(true);
                }else if(checkBox21.isChecked() && !checkBox22.isChecked() && checkBox23.isChecked() && checkBox24.isChecked()){
                    checkBox20.setChecked(false);
                }
            }
        });
        checkBox23.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkBox21.isChecked() && checkBox22.isChecked() && checkBox23.isChecked() && checkBox24.isChecked()){
                    checkBox20.setChecked(true);
                }else if(checkBox21.isChecked() && checkBox22.isChecked() && !checkBox23.isChecked() && checkBox24.isChecked()){
                    checkBox20.setChecked(false);
                }
            }
        });
        checkBox24.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkBox21.isChecked() && checkBox22.isChecked() && checkBox23.isChecked() && checkBox24.isChecked()){
                    checkBox20.setChecked(true);
                }else if(checkBox21.isChecked() && checkBox22.isChecked() && checkBox23.isChecked() && !checkBox24.isChecked()){
                    checkBox20.setChecked(false);
                }
            }
        });
        //第三条的checkbox选中事件
        checkBox31.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkBox31.isChecked() && checkBox32.isChecked() && checkBox33.isChecked()){
                    checkBox30.setChecked(true);
                }else if(!checkBox31.isChecked() && checkBox32.isChecked() && checkBox33.isChecked()){
                    checkBox30.setChecked(false);
                }
            }
        });
        checkBox32.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkBox31.isChecked() && checkBox32.isChecked() && checkBox33.isChecked()){
                    checkBox30.setChecked(true);
                }else if(checkBox31.isChecked() && !checkBox32.isChecked() && checkBox33.isChecked()){
                    checkBox30.setChecked(false);
                }
            }
        });
        checkBox33.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkBox31.isChecked() && checkBox32.isChecked() && checkBox33.isChecked()){
                    checkBox30.setChecked(true);
                }else if(checkBox31.isChecked() && checkBox32.isChecked() && !checkBox33.isChecked()){
                    checkBox30.setChecked(false);
                }
            }
        });
        //第五条的checkbox选中事件
        checkBox51.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkBox51.isChecked() && checkBox52.isChecked() && checkBox53.isChecked() && checkBox54.isChecked()){
                    checkBox50.setChecked(true);
                }else if(!checkBox51.isChecked() && checkBox52.isChecked() && checkBox53.isChecked() && checkBox54.isChecked()){
                    checkBox50.setChecked(false);
                }
            }
        });
        checkBox52.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkBox51.isChecked() && checkBox52.isChecked() && checkBox53.isChecked() && checkBox54.isChecked()){
                    checkBox50.setChecked(true);
                }else if(checkBox51.isChecked() && !checkBox52.isChecked() && checkBox53.isChecked() && checkBox54.isChecked()){
                    checkBox50.setChecked(false);
                }
            }
        });
        checkBox53.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkBox51.isChecked() && checkBox52.isChecked() && checkBox53.isChecked() && checkBox54.isChecked()){
                    checkBox50.setChecked(true);
                }else if(checkBox51.isChecked() && checkBox52.isChecked() && !checkBox53.isChecked() && checkBox54.isChecked()){
                    checkBox50.setChecked(false);
                }
            }
        });
        checkBox54.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkBox51.isChecked() && checkBox52.isChecked() && checkBox53.isChecked() && checkBox54.isChecked()){
                    checkBox50.setChecked(true);
                }else if(checkBox51.isChecked() && checkBox52.isChecked() && checkBox53.isChecked() && !checkBox54.isChecked()){
                    checkBox50.setChecked(false);
                }
            }
        });
        //复核按钮的注册以及点击事件
        Button Check = (Button)findViewById(R.id.check);
        Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkBox10.isChecked() && !checkBox21.isChecked() && !checkBox22.isChecked() && !checkBox23.isChecked() && !checkBox24.isChecked() && !checkBox31.isChecked() && !checkBox32.isChecked() && !checkBox33.isChecked() && !checkBox51.isChecked() && !checkBox52.isChecked() && !checkBox53.isChecked() && !checkBox54.isChecked()){
                    Toast.makeText(EssentialActivity.this,"请选择符合事件",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(EssentialActivity.this,RecordActivity.class);
                    if(checkBox10.isChecked()){
                        da = "OK";
                    }else {
                        if(checkBox21.isChecked()){
                            A="A";
                        }else{
                            A="a";
                        }
                        if(checkBox22.isChecked()){
                            B="B";
                        }else{
                            B="b";
                        }
                        if(checkBox23.isChecked()){
                            C="C";
                        }else{
                            C="c";
                        }
                        if(checkBox24.isChecked()){
                            D="D";
                        }else{
                            D="d";
                        }
                        if(checkBox31.isChecked()){
                            E="E";
                        }else{
                            E="e";
                        }
                        if(checkBox32.isChecked()){
                            F="F";
                        }else{
                            F="f";
                        }
                        if(checkBox33.isChecked()){
                            G="G";
                        }else{
                            G="g";
                        }
                        if(checkBox51.isChecked()){
                            H="H";
                        }else{
                            H="h";
                        }
                        if(checkBox52.isChecked()){
                            I="I";
                        }else{
                            I="i";
                        }
                        if(checkBox53.isChecked()){
                            J="J";
                        }else{
                            J="j";
                        }
                        if(checkBox54.isChecked()){
                            K="K";
                        }else{
                            K="k";
                        }
                        da = A+B+C+D+E+F+G+H+I+J+K;
                    }
                    startActivity(intent);
                }
            }
        });
    }//小功能结束括号

    public void checkBox10_onClick(View v) {
        if(checkBox10.isChecked()){
            checkBox20.setChecked(false);
            checkBox20.setClickable(false);
            checkBox20.setBackgroundColor(Color.parseColor("#D3D3D3"));
            checkBox21.setChecked(false);
            checkBox21.setClickable(false);
            checkBox21.setBackgroundColor(Color.parseColor("#D3D3D3"));
            checkBox22.setChecked(false);
            checkBox22.setClickable(false);
            checkBox22.setBackgroundColor(Color.parseColor("#D3D3D3"));
            checkBox23.setChecked(false);
            checkBox23.setClickable(false);
            checkBox23.setBackgroundColor(Color.parseColor("#D3D3D3"));
            checkBox24.setChecked(false);
            checkBox24.setClickable(false);
            checkBox24.setBackgroundColor(Color.parseColor("#D3D3D3"));
            checkBox30.setChecked(false);
            checkBox30.setClickable(false);
            checkBox30.setBackgroundColor(Color.parseColor("#D3D3D3"));
            checkBox31.setChecked(false);
            checkBox31.setClickable(false);
            checkBox31.setBackgroundColor(Color.parseColor("#D3D3D3"));
            checkBox32.setChecked(false);
            checkBox32.setClickable(false);
            checkBox32.setBackgroundColor(Color.parseColor("#D3D3D3"));
            checkBox33.setChecked(false);
            checkBox33.setClickable(false);
            checkBox33.setBackgroundColor(Color.parseColor("#D3D3D3"));
            checkBox40.setChecked(false);
            checkBox40.setClickable(false);
            checkBox40.setBackgroundColor(Color.parseColor("#D3D3D3"));
            checkBox50.setChecked(false);
            checkBox50.setClickable(false);
            checkBox50.setBackgroundColor(Color.parseColor("#D3D3D3"));
            checkBox51.setChecked(false);
            checkBox51.setClickable(false);
            checkBox51.setBackgroundColor(Color.parseColor("#D3D3D3"));
            checkBox52.setChecked(false);
            checkBox52.setClickable(false);
            checkBox52.setBackgroundColor(Color.parseColor("#D3D3D3"));
            checkBox53.setChecked(false);
            checkBox53.setClickable(false);
            checkBox53.setBackgroundColor(Color.parseColor("#D3D3D3"));
            checkBox54.setChecked(false);
            checkBox54.setClickable(false);
            checkBox54.setBackgroundColor(Color.parseColor("#D3D3D3"));
        }else if(!checkBox10.isChecked()){
            checkBox20.setClickable(true);
            checkBox20.setBackgroundColor(Color.parseColor("#F8F8FF"));
            checkBox21.setClickable(true);
            checkBox21.setBackgroundColor(Color.parseColor("#F8F8FF"));
            checkBox22.setClickable(true);
            checkBox22.setBackgroundColor(Color.parseColor("#F8F8FF"));
            checkBox23.setClickable(true);
            checkBox23.setBackgroundColor(Color.parseColor("#F8F8FF"));
            checkBox24.setClickable(true);
            checkBox24.setBackgroundColor(Color.parseColor("#F8F8FF"));
            checkBox30.setClickable(true);
            checkBox30.setBackgroundColor(Color.parseColor("#F8F8FF"));
            checkBox31.setClickable(true);
            checkBox31.setBackgroundColor(Color.parseColor("#F8F8FF"));
            checkBox32.setClickable(true);
            checkBox32.setBackgroundColor(Color.parseColor("#F8F8FF"));
            checkBox33.setClickable(true);
            checkBox33.setBackgroundColor(Color.parseColor("#F8F8FF"));
            checkBox40.setClickable(true);
            checkBox40.setBackgroundColor(Color.parseColor("#F8F8FF"));
            checkBox50.setClickable(true);
            checkBox50.setBackgroundColor(Color.parseColor("#F8F8FF"));
            checkBox51.setClickable(true);
            checkBox51.setBackgroundColor(Color.parseColor("#F8F8FF"));
            checkBox52.setClickable(true);
            checkBox52.setBackgroundColor(Color.parseColor("#F8F8FF"));
            checkBox53.setClickable(true);
            checkBox53.setBackgroundColor(Color.parseColor("#F8F8FF"));
            checkBox54.setClickable(true);
            checkBox54.setBackgroundColor(Color.parseColor("#F8F8FF"));
        }
    }

    public void showdialog()//对话框功能实现
    {
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage("是否立即启动应急预案");
        alertdialogbuilder.setPositiveButton("确定",click1);
        alertdialogbuilder.setNegativeButton("取消",click2);
        AlertDialog alertDialog1 = alertdialogbuilder.create();
        alertDialog1.show();
    }
    private DialogInterface.OnClickListener click1 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int arg1) {
            Intent intent = new Intent(EssentialActivity.this,ChatActivity.class);
            startActivity(intent);//进入下一个活动
            if(checkBox40.isChecked()){
                da = "NOT";
            }
            checkBox40.setChecked(false);
            OutData_Thread outData_thread = new OutData_Thread();
            outData_thread.start();
        }
    };
    private DialogInterface.OnClickListener click2 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0, int arg1) {
            arg0.cancel();
        }
    };//对话框功能实现
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            String action = intent.getAction();
            if(action.equals("again_login")){
                Toast.makeText(EssentialActivity.this,"账号在别处登陆,已强制下线",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(EssentialActivity.this,LoginActivity.class);
                startActivity(intent1);
            }
            finish();
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    class OutData_Thread extends Thread
    {
        public void run() {

            try {
                OutputStream outputStream = socket.getOutputStream();
                StringBuffer space1 = new StringBuffer();
                StringBuffer space2 = new StringBuffer();
                StringBuffer space3 = new StringBuffer();
                StringBuffer space4 = new StringBuffer();
                StringBuffer space5 = new StringBuffer();
                StringBuffer space6 = new StringBuffer();
                StringBuffer space7 = new StringBuffer();
                StringBuffer space8 = new StringBuffer();
                for (int i = A1.length(); i < 20; i++) {
                    space1.append(" ");//补全20位测量值
                }
                for (int j = A2.length(); j < 20; j++) {
                    space2.append(" ");//补全20位标准值
                }
                for (int m = A3.length(); m < 20; m++) {
                    space3.append(" ");//补全20位时间
                }
                for (int n = A4.length(); n < 20; n++){
                    space4.append(" ");//补全20位日期
                }
                for (int o = A5.length(); o < 20; o++){
                    space5.append(" ");//补全20位账户
                }
                for (int p = A6.length(); p < 20; p++){
                    space6.append(" ");//补全20位名字
                }
                for (int q = user.length(); q < 20; q++){
                    space7.append(" ");//补全20位账户
                }
                for (int r = ID.length(); r < 20; r++){
                    space8.append(" ");//补全20位名字
                }
                //定义要发送的异常数据
                String content = "::POS::"+ ID + space8 + user + space7 + A1 + space1 + A2 + space2 + A3 + space3 + A4 + space4 + A5 + space5 + A6 + " ";
                //发送正常数据
                outputStream.write(content.getBytes("UTF-8"));
            } catch (IOException e) {//发送放射性污染数据
                e.printStackTrace();
            }
        }
    }//发送正常数据
}//程序结束括号