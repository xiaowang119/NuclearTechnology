package module7;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.nucleartechnology.R;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.net.Uri;
import android.widget.Toast;

import static module7.LoginActivity.AAAAA;
import static module7.LoginActivity.ID;
import static module7.LoginActivity.TEXT;
import static module7.LoginActivity.len;
import static module7.LoginActivity.number;
import static module7.LoginActivity.socket;
import static module7.LoginActivity.user;
import static module7.MonitorActivity.A1;
import static module7.MonitorActivity.A2;
import static module7.MonitorActivity.A3;
import static module7.MonitorActivity.A4;
import static module7.MonitorActivity.A5;
import static module7.MonitorActivity.A6;

public class ChatActivity extends AppCompatActivity {

    private List<MSG> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private OutputStream outputStream=null;//定义输出流
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    public static String BBBBB;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ActivityCollector.addActivity(this);//创建一个新活动时加入活动管理器
        initMsgs(); //初始化消息数据
        inputText = (EditText) findViewById(R.id.input_text);
        inputText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
        send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        localBroadcastManager = localBroadcastManager.getInstance(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("message_receive");
        intentFilter.addAction("out_data");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

        Button examine = (Button)findViewById(R.id.examine);
        examine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ChatActivity.this,InformationActivity.class);
                intent.putExtra("out_data",BBBBB);
                startActivity(intent);
            }
        });
        Button mess = (Button)findViewById(R.id.mess);
        mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(Uri.parse("smsto:" + number));
                sendIntent.putExtra("sms_body","有一条紧急情况，请上线");
                startActivity(sendIntent);

            }
        });
        Button button_download = (Button)findViewById(R.id.button_download);
        button_download.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });
        Button submit1 = (Button)findViewById(R.id.submit1);
        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();

                if(!"".equals(content)){
                    MSG msg = new MSG(content,MSG.TYPE_SEND);
                    msgList.add(msg);
                    new Thread(){
                        @Override
                        public void run(){
                            try {
                                outputStream = socket.getOutputStream();
                                StringBuffer space1 = new StringBuffer();
                                for(int i= ID.length();i<20;i++)
                                {
                                    space1.append(" ");//补全20位ID
                                }
                                StringBuffer space2 = new StringBuffer();
                                for(int j= user.length();j<20;j++)
                                {
                                    space2.append(" ");//补全20位用户名
                                }
                                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                String time = formatter.format(calendar.getTime());
                                StringBuffer space3 = new StringBuffer();
                                for(int i= time.length();i<20;i++)
                                {
                                    space3.append(" ");//补全20位用户名
                                }
                                String SEND_MESSAGE = "::MSG::" + ID + space1 + user + space2 + time + space3 + inputText.getText().toString();
                                outputStream.write(SEND_MESSAGE.getBytes("UTF-8"));

//outputStream.write("0".getBytes());
                            } catch (Exception e) {
// TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    adapter.notifyItemInserted(msgList.size()-1);
                    //刷新ListView中的显示
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    //将listview定位到最后一行
                    inputText.setText("");//清空输入框中的内容
                }
            }
        });//给其他人发送消息

    }



    //用于初始化消息
    private void initMsgs(){
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
        ActivityCollector.removeActivity(this);
    }
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            String action = intent.getAction();
            if(action.equals("again_login")){
                Toast.makeText(ChatActivity.this,"账号在别处登陆,已强制下线",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(ChatActivity.this,LoginActivity.class);
                startActivity(intent1);
            }else if(action.equals("out_data")){
                BBBBB = AAAAA;
            }else {
                String receive1 = TEXT.substring(0,7);
                String receive2 = TEXT.substring(7,len);
                int len1 = ID.length();
                String receive3 = TEXT.substring(26,26+len1);
                if(receive1.equals("::MSG::") && !receive3.equals(ID)){//如果是自己发送的消息，则不接收
                    MSG message = new MSG(receive2,MSG.TYPE_RECEIVED);
                    msgList.add(message);
                    adapter.notifyItemInserted(msgList.size()-1);
                    //刷新ListView中的显示
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    //将listview定位到最后一行
                }else if(receive1.equals("::SYS::")){
                    MSG message = new MSG("系统消息：" + receive2,MSG.TYPE_RECEIVED);
                    msgList.add(message);
                    adapter.notifyItemInserted(msgList.size()-1);
                    //刷新ListView中的显示
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    //将listview定位到最后一行
                }
            }

        }
    }
    public void showdialog()//对话框功能实现
    {
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage("是否进入结果登记归档");
        alertdialogbuilder.setPositiveButton("确定",click1);
        alertdialogbuilder.setNegativeButton("取消",click2);
        AlertDialog alertDialog1 = alertdialogbuilder.create();
        alertDialog1.show();
    }
    private DialogInterface.OnClickListener click1 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int arg1) {
            Intent intent = new Intent(ChatActivity.this,RecordActivity.class);
            startActivity(intent);
        }
    };
    private DialogInterface.OnClickListener click2 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0, int arg1) {
            arg0.cancel();
        }
    };//对话框功能实现


}
