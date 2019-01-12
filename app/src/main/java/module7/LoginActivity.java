package module7;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nucleartechnology.MainActivity;
import com.example.nucleartechnology.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class LoginActivity extends AppCompatActivity {

    EditText accountSend;
    EditText passwordSend;//定义信息输出框
    public  static  Socket socket = null;//定义socket
    private OutputStream outputStream=null;//定义输出流
    public static String user = null;
    public static String ID;
    public static String TEXT;
    public static String number;
    public static String IP;
    public static String AAAAA;
    public static int len;
    public static int list_num = 1;
    private LocalBroadcastManager localBroadcastManager;
    private MyDatabaseHelper dbHelper;
    private int DEFAULTSIZE = 1024 * 64;
    private InputStreamReader isr;
    private  CheckBox checkBoxIp;
    private CheckBox checkBoxHttp;
    private EditText port;
    private EditText www;
    String ipAddress;
    EditText ip;
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    String http;//修改服务端的域名进行连接
    String host_1 = "172.20.10.5";//修改Ip地址进行连接
    int port_1;//自定义服务器端口
    boolean isLogin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        accountSend = (EditText)findViewById(R.id.account);
        passwordSend = (EditText)findViewById(R.id.password);
        port = (EditText)findViewById(R.id.port);
        www = (EditText) findViewById(R.id.www);
        ip = (EditText)findViewById(R.id.editView1_0);
        String inputText1 = load1();
        String inputText2 = load2();
        String inputText3 = load3();
        String inputText4 = load4();
        if(!TextUtils.isEmpty(inputText1)){
            ip.setText(inputText1);
            ip.setSelection(inputText1.length());
        }
        if(!TextUtils.isEmpty(inputText2)){
            port.setText(inputText2);
            port.setSelection(inputText2.length());
        }
        if(!TextUtils.isEmpty(inputText3)){
            www.setText(inputText3);
            www.setSelection(inputText3.length());
        }
        if(!TextUtils.isEmpty(inputText4)){
            accountSend.setText(inputText4);
            accountSend.setSelection(inputText4.length());
        }
        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,2);
        localBroadcastManager = localBroadcastManager.getInstance(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("data_right");
        intentFilter.addAction("data_wrong");
        intentFilter.addAction("again_login");
        intentFilter.addAction("receive_photo");
        intentFilter.addAction("not_receive");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
        checkBoxIp = (CheckBox)findViewById(R.id.checkBox_ip);
        checkBoxHttp = (CheckBox)findViewById(R.id.checkBox_http);
        checkBoxIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxIp.isChecked() && checkBoxHttp.isChecked()){
                    checkBoxHttp.setChecked(false);
                }else if(!checkBoxIp.isChecked() && !checkBoxHttp.isChecked()){
                    checkBoxHttp.setChecked(true);
                }
            }
        });
        checkBoxHttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxIp.isChecked() && checkBoxHttp.isChecked()){
                    checkBoxIp.setChecked(false);
                }else if(!checkBoxIp.isChecked() && !checkBoxHttp.isChecked()){
                    checkBoxIp.setChecked(true);
                }
            }
        });


    }
    public void save(String ip,String port,String www,String id){
        FileOutputStream out1 = null;
        BufferedWriter writer1 = null;
        FileOutputStream out2 = null;
        BufferedWriter writer2 = null;
        FileOutputStream out3 = null;
        BufferedWriter writer3 = null;
        FileOutputStream out4 = null;
        BufferedWriter writer4 = null;
        try{
            out1 = openFileOutput("ip", Context.MODE_PRIVATE);
            writer1 = new BufferedWriter(new OutputStreamWriter(out1));
            writer1.write(ip);
            out2 = openFileOutput("port", Context.MODE_PRIVATE);
            writer2 = new BufferedWriter(new OutputStreamWriter(out2));
            writer2.write(port);
            out3 = openFileOutput("www", Context.MODE_PRIVATE);
            writer3 = new BufferedWriter(new OutputStreamWriter(out3));
            writer3.write(www);
            out4 = openFileOutput("id", Context.MODE_PRIVATE);
            writer4 = new BufferedWriter(new OutputStreamWriter(out4));
            writer4.write(id);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(writer1 != null){
                    writer1.close();
                }
                if(writer2 != null){
                    writer2.close();
                }
                if(writer3 != null){
                    writer3.close();
                }
                if(writer4 != null){
                    writer4.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public String load1(){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            in = openFileInput("ip");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return  content.toString();
    }
    public String load2(){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            in = openFileInput("port");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return  content.toString();
    }
    public String load3(){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            in = openFileInput("www");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return  content.toString();
    }
    public String load4(){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            in = openFileInput("id");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return  content.toString();
    }
    public void Connect_onClick(View v) {
        list_num = 1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Book",null,null);
        if(!checkBoxHttp.isChecked() && !checkBoxIp.isChecked()){
            Toast.makeText(LoginActivity.this,"请选择连接方式",Toast.LENGTH_SHORT).show();
        }else {
            if(!isLogin){
                Connect_Thread connect_Thread = new Connect_Thread();
                connect_Thread.start();
            }else {
                Toast.makeText(LoginActivity.this,"正在登陆，请稍后...",Toast.LENGTH_SHORT).show();
            }
        }
    }//login按钮的点击事件，打开连接线程
    class Connect_Thread extends Thread//继承Thread
    {
        public void run()//重写run方法
        {
            //连接起来
            try
            {
                boolean isRight;


                if(port.getText().toString().equals("")){
                    port_1=65301;
                }else {
                    port_1 = Integer.parseInt(port.getText().toString());
                }
                if(ip.getText().toString().equals("")){
                    host_1 = "222.190.126.62";
                }else{
                    host_1 = ip.getText().toString();
                }
                if(www.getText().toString().equals("")){
                    http = "jaiq.org";
                }else {
                    http = www.getText().toString();
                }

                if (socket == null)//如果没有连接
                {
                    if(checkBoxIp.isChecked()){
                        socket = new Socket(host_1,port_1);//调试的IP地址
                    }else  if(!checkBoxIp.isChecked()){
                        getIP(http);
                        //解析域名获得IP
                        socket = new Socket(ipAddress, port_1);
                    }
                    isRight = true;
                    if(socket != null){
                        isLogin = true;
                    }
                    if(isRight){
                        //开启接收线程
                        Receive_Thread receive_Thread = new Receive_Thread();
                        receive_Thread.start();
                        outputStream = socket.getOutputStream();
                        //补齐发送的用户名和密码
                        StringBuffer space1 = new StringBuffer();
                        StringBuffer space2 = new StringBuffer();
                        StringBuffer space3 = new StringBuffer();
                        //补全20位用户名
                        String a = GetIp();
                        for(int i= accountSend.getText().length();i<20;i++)
                        {
                            space1.append(" ");
                        }
                        //补全20位密码
                        for(int j= passwordSend.getText().length();j<20;j++)

                        {
                            space2.append(" ");
                        }
                        for(int k= a.length();k<20;k++)
                        {
                            space3.append(" ");
                        }

                        //发送过去服务器验证的信息
                        IP = a + space3;
                        String content = "::MAN::"+accountSend.getText().toString()+space1+passwordSend.getText().toString()+space2+a+space3;
                        outputStream.write(content.getBytes("UTF-8"));
                    }
                }else{
                    socket.close();
                    if(checkBoxIp.isChecked()){
                        socket = new Socket(host_1,port_1);//调试的IP地址
                    }else  if(!checkBoxIp.isChecked()){
                        getIP(http);
                        //解析域名获得IP
                        socket = new Socket(ipAddress, port_1);
                    }
                    outputStream = socket.getOutputStream();

                    String b = GetIp();
                    //补齐发送的用户名和密码
                    StringBuffer space1 = new StringBuffer();
                    StringBuffer space2 = new StringBuffer();
                    StringBuffer space3 = new StringBuffer();
                    for(int i= accountSend.getText().length();i<20;i++)
                    {
                        space1.append(" ");//补全20位用户名
                    }
                    for(int j= passwordSend.getText().length();j<20;j++)
                    {
                        space2.append(" ");//补全20位密码
                    }
                    for(int k= b.length();k<20;k++)
                    {
                        space3.append(" ");
                    }
                    IP = b + space3;
                    String content = "::MAN::"+accountSend.getText().toString()+space1+passwordSend.getText().toString()+space2+b+space3;
                    outputStream.write(content.getBytes("UTF-8"));

                }

            }
            catch (Exception e)
            {
// TODO Auto-generated catch block

                e.printStackTrace();
            }
        }
    }//连接线程
    class Receive_Thread extends Thread
    {
        public void run()//重写run方法
        {
            while(true){
                try {
                    DataInputStream inputData = new DataInputStream(socket.getInputStream());
                    isr = new InputStreamReader(inputData, "gbk");
                    int count = 0;
                    char[] buf = new char[DEFAULTSIZE];
                    StringBuffer sb = new StringBuffer();
                    while ((count = isr.read(buf, 0, buf.length)) > -1) {
                        sb.append(buf, 0, count);
                        if (count < DEFAULTSIZE) {
                            break;
                        }
                    }
                    String receive = sb.toString();
                    len = receive.length();
                    String receive1 = " ";
                    String receive2 = " ";
                    String receive3 = " ";
                    if(len>=12 && (receive.substring(0,12).equals("::MAN::RIGHT") || receive.substring(0,12).equals("::MAN::WRONG") )){
                        receive1 = receive.substring(0,12);
                    }
                    if(len>=7){
                        receive2 = receive.substring(0,7);
                    }
                    if(len>=8){
                        receive3 = receive.substring(0,8);
                    }
                    if(!(receive1 == null) && receive1.equals("::MAN::RIGHT")) { //服务器判断账号密码正确与否的消息，再返回
                        //取出我在库里面的名字
                        ID = accountSend.getText().toString();
                        if(user == null){
                            String u1 = receive.substring(12,32);
                            user = u1.trim();
                            number = receive.substring(33,len);
                        }
                        //由登陆界面进入正式界面
                        Intent intent = new Intent(LoginActivity.this, MonitorActivity.class);
                        startActivity(intent);
                        //记住部分信息
                        String a,b,c,d;
                        if(!TextUtils.isEmpty(ip.toString())){
                            a = ip.getText().toString();
                        }else {
                            a = ip.getHint().toString();
                        }
                        if(!TextUtils.isEmpty(port.toString())){
                            b = port.getText().toString();
                        }else {
                            b = port.getHint().toString();
                        }
                        if(!TextUtils.isEmpty(www.toString())){
                            c = www.getText().toString();
                        }else {
                            c = www.getHint().toString();
                        }
                        d = accountSend.getText().toString();
                        save(a,b,c,d);
                    }else if(receive2.equals("::EXT::")){

                    }else if(receive2.equals("::SYS::") || receive2.equals("::MSG::")){
                        //接收到聊天界面的信息
                        try {
                            sleep((long) 0.01);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }//进行一定的延时保证接收到的消息不会重复
                        TEXT = receive;
//发送一条广播，告诉接收单元全局变量发生变化，把全局变量显示出来
                        Intent intent = new Intent("message_receive");
                        localBroadcastManager.sendBroadcast(intent);
                        //以广播的形式把收到的信息发送给其他活动
                    }else if(receive3.equals("::SYS1::") || receive3.equals("::SYS2::")){
                        Intent intent = new Intent("data_receive");
                        localBroadcastManager.sendBroadcast(intent);
                        //以广播的形式把收到的信息发送给其他活动
                    }else if(receive1.equals("::MAN::WRONG")){
                        Intent intent = new Intent("data_wrong");
                        localBroadcastManager.sendBroadcast(intent);
                        //以广播的形式把收到的信息发送给其他活动
                    }else if(receive2.equals("::SZS::")){
                        Intent intent = new Intent("again_login");
                        localBroadcastManager.sendBroadcast(intent);
                        ActivityCollector.finishAll();
                    }else if(receive2.equals("::POS::")){
                        AAAAA = receive.substring(7,len);
                        Intent intent = new Intent("out_data");
                        localBroadcastManager.sendBroadcast(intent);
                    }else if(receive2.equals("::SUC::")){//接收到的信息是正确接收图片的
                        Intent intent = new Intent("receive_photo_yeah");
                        localBroadcastManager.sendBroadcast(intent);
                    }else if(receive2.equals("::FAI::")){//接收到的信息是正确接收图片的
                        Intent intent = new Intent("not_receive");
                        localBroadcastManager.sendBroadcast(intent);
                    }
                }
                catch (IOException e)
                {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }//接收线程
    @Override
    protected void onDestroy(){
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
        ActivityCollector.finishAll();
    }
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            String action = intent.getAction();
            if(action.equals("data_wrong")){
                isLogin = false;
                Toast.makeText(LoginActivity.this,"用户名或密码错误,请确认无误后重试",Toast.LENGTH_SHORT).show();
            }else if(action.equals("again_login")){
                Toast.makeText(LoginActivity.this,"账号在别处登陆,已强制下线",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public String GetIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr
                        .hasMoreElements();) {
                    InetAddress inetAddress = ipAddr.nextElement();
                    // ipv4地址
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
        }
        return null;
    }//获取IPV4的地址
    public String getIP(String domain) {
        InetAddress iAddress = null;
        try {
            iAddress = InetAddress.getByName(domain);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (iAddress == null)
            Log.i("xxx", "iAddress ==null");
        else {
            ipAddress = iAddress.getHostAddress();
        }
        return ipAddress;
    }//域名解析为ip
    public void showdialog2()//对话框功能实现
    {
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage("请检查输入信息");
        AlertDialog alertDialog1 = alertdialogbuilder.create();
        alertDialog1.show();
    }

}