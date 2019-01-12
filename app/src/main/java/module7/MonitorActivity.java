package module7;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.nucleartechnology.R;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static module7.LoginActivity.ID;
import static module7.LoginActivity.IP;
import static module7.LoginActivity.len;
import static module7.LoginActivity.socket;
import static module7.LoginActivity.user;

public class MonitorActivity extends AppCompatActivity {

    public static final int TAKE_PHOTO = 1;
    public static byte[] byte_3;

    private ImageView picture;

    private Uri imageUri;

    EditText editStandard;
    EditText editMeasure;
    EditText editBoxNumber;
    EditText editInspectionNumber;
    EditText editBillNumber;
    TextView textView;

    private OutputStream outputStream=null;//定义输出流


    public static String A1;
    public static String A2;
    public static String A3;
    public static String A4;
    public static String A5;
    public static String A6;
    Bitmap image = null;
    public static int again = 0;
    private int which_num = 1;//判断当前发哪份图片包
    public static boolean isTrue = true;
    public static boolean isSend;
    boolean num1 = true;
    boolean num2 = true;
    boolean num3 = true;
    boolean num4 = true;
    boolean num5 = true;
    Bitmap bitmap;
    public static byte[] BYTE;
    public static byte[] Byte;
    public static byte[] byteChange1;
    public static byte[] byteChange2;
    public static byte[] byteChange;

    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    File outputImage;
    boolean have_photo = false;
    private SoundPool soundPool;//声明一个SoundPool
    private int soundID;//创建某个声音对应的音频ID
    private LocalReceiver localReceiver;
    private byte[] byte_0;
    private Button alarnReview;
    private byte[] byte_1;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        ActivityCollector.addActivity(this);//创建一个新活动时加入活动管理器
        initSound();

        localBroadcastManager = localBroadcastManager.getInstance(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("receive_photo_yeah");
        intentFilter.addAction("not_receive");
        intentFilter.addAction(" ");
        intentFilter.addAction(" ");
        intentFilter.addAction(" ");
        intentFilter.addAction(" ");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);//注册广播
        editBoxNumber = (EditText)findViewById(R.id.edit_box_number);
        editInspectionNumber = (EditText)findViewById(R.id.edit_inspection_number);
        editBillNumber = (EditText)findViewById(R.id.edit_bill_number);
        editStandard = (EditText)findViewById(R.id.editStandard);
        editMeasure = (EditText)findViewById(R.id.editMeasure);
        editStandard.addTextChangedListener(textChange1);
        editMeasure.addTextChangedListener(textChange2);
        textView = (TextView)findViewById(R.id.textView);
        ImageButton take_photo = (ImageButton)findViewById(R.id.take_photo);
        picture = (ImageView) findViewById(R.id.picture);
        requestPermissions(new String[]{Manifest.permission.CAMERA},0);//获取相机使用权限
        Time_Thread time_thread= new Time_Thread();
        time_thread.start();//开启TCP脉搏
        final Button Intent = (Button)findViewById(R.id.intent);
        Intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MonitorActivity.this,ChatActivity.class);
                startActivity(intent);
            }
        });
        alarnReview  = (Button)findViewById(R.id.alarm_review);
        //报警复核，将几个数据以及照片传递到下一个活动
        alarnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开对话框2
                showdialog2();
            }
        });
        //处理compare（发送数据的点击事件）
        Button compare = (Button) findViewById(R.id.compare);
        compare.setOnClickListener(new View.OnClickListener(){
            @SuppressLint({"NewApi", "UseValueOf"})
            @Override
            public void onClick(View v){
                if(editStandard.getText() != null &&  editMeasure.getText() != null){
                    //取出文本框里面的俩个数
                    boolean isTrue = false;
                    boolean isRight = true;
                    try {
                        Integer num1;
                        num1=new Integer(editStandard.getText().toString());
                        Integer num2;
                        num2=new Integer(editMeasure.getText().toString());
                        if(num2 > num1 * 3){
                            isTrue = true;
                        }else {
                            isTrue = false;
                        }
                    }catch (Exception e){
                        isRight = false;
                    }
                    if(!isRight){
                        Toast.makeText(MonitorActivity.this,"请输入正确的数据",Toast.LENGTH_SHORT).show();
                    }else {
                        if(isTrue) {//放射性数据超标事件（拍照复核）
                            Toast.makeText(MonitorActivity.this, "请拍照后发送异常数据", Toast.LENGTH_SHORT).show();
                        }else{
                            //打开对话框
                            showdialog1();
                        }
                    }
                }
            }
        });
        take_photo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseValueOf")
            @Override
            public void onClick(View view) {
                boolean isRight = false;
                try {
                    Integer num1;
                    num1=new Integer(editStandard.getText().toString());
                    Integer num2;
                    num2=new Integer(editMeasure.getText().toString());
                    if(num2 > num1 * 3){
                        isRight = true;
                    }else {
                        isRight = false;
                    }
                }catch (Exception e){
                    Ext_Thread ext_thread= new Ext_Thread();
                    ext_thread.start();//开启发送程序退出的代码（发送::EXT::）
                }
                if(isRight) {//放射性数据超标事件（拍照复核）
                    Toast.makeText(MonitorActivity.this, "请拍照复核后发送", Toast.LENGTH_SHORT).show();
                    //相机打开事件,并命名拍的照片为output_image.jpg
                    outputImage = new File(getExternalCacheDir(),
                            "output_image.jpg");
                    try {
                        //如果目前imageView上有图片，则要清空imageView上的图片
                        if (outputImage.exists()) {
                            outputImage.delete();
                        }
                        //创建一个新的文件
                        outputImage.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //如果安卓版本大于7.0，则共享封装过的Uri
                    if (Build.VERSION.SDK_INT >= 24) {
                        //调用相机权限判定
                        if (!CameraCanUseUtils.isCameraCanUse()) {
                            Toast.makeText(MonitorActivity.this,"没有使用相机权限",Toast.LENGTH_SHORT).show();
                            requestPermissions(new String[]{Manifest.permission.CAMERA},0);//再次获取相机使用权限
                            return;
                        }
                        imageUri = FileProvider.getUriForFile(MonitorActivity.this, "com.example.cameratest.fileprovider", outputImage);
                    } else {
                        //调用相机权限判定
                        if (!CameraCanUseUtils.isCameraCanUse()) {
                            Toast.makeText(MonitorActivity.this,"没有使用相机权限",Toast.LENGTH_SHORT).show();
                            requestPermissions(new String[]{Manifest.permission.CAMERA},0);//再次获取相机使用权限
                            return;
                        }
                        //调用相机权限判定
                        if (CameraCanUseUtils.isCameraCanUse()) {

                        } else {
                            Toast.makeText(MonitorActivity.this,"没有使用相机权限",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        imageUri = Uri.fromFile(outputImage);
                    }
                    //调用Intent启动相机程序拍照
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, TAKE_PHOTO);
                }else {
                    Toast.makeText(MonitorActivity.this,"正常数据无需拍照",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }//调用摄相机拍照
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //处理拍照后的确认事件
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        //将拍摄的照片显现出来
                        image = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(image);
                        have_photo = true;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }//拍摄的照片显示

    class Data_Thread extends Thread
    {
        public void run() {
            try {
                outputStream = socket.getOutputStream();
                //补齐发送的用户名和密码
                StringBuffer space3 = new StringBuffer();
                StringBuffer space4 = new StringBuffer();
                for (int i = editMeasure.getText().length(); i < 20; i++) {
                    space3.append(" ");//补全20位测量值
                }
                for (int j = editStandard.getText().length(); j < 20; j++) {
                    space4.append(" ");//补全20位标准值
                }
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                String time = formatter.format(calendar.getTime());
                //定义四种空格
                StringBuffer space7 = new StringBuffer();
                StringBuffer space8 = new StringBuffer();
                StringBuffer space9 = new StringBuffer();
                StringBuffer space10 = new StringBuffer();
                //确定四个空格的长度
                for (int m = time.length(); m < 20; m++) {
                    space7.append(" ");//补全20位时间
                }
                String date = time.substring(0,10);
                for (int n = date.length(); n < 20; n++){
                    space8.append(" ");//补全20位日期
                }
                for (int o = ID.length(); o < 20; o++){
                    space9.append(" ");//补全20位账户
                }
                for (int p = user.length(); p < 20; p++){
                    space10.append(" ");//补全20位名字
                }
                //定义要发送的正常数据
                String content = "::DAT::" + ID + space9 + user + space10 + editStandard.getText().toString() + space4
                        + editMeasure.getText().toString() + space3 +  time + space7 +date +space8;
                //发送正常数据
                outputStream.write(content.getBytes("UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//发送正常数据

    class Out_Thread extends Thread
    {
        public void run() {
            try {
                outputStream = socket.getOutputStream();
                //补齐发送的用户名和密码
                StringBuffer space11 = new StringBuffer();
                StringBuffer space12 = new StringBuffer();
                for (int i = editMeasure.getText().length(); i < 20; i++) {
                    space11.append(" ");//补全20位测量值
                }
                for (int j = editStandard.getText().length(); j < 20; j++) {
                    space12.append(" ");//补全20位标准值
                }
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                String time = formatter.format(calendar.getTime());
                //定义四种空格
                StringBuffer space13 = new StringBuffer();
                StringBuffer space14 = new StringBuffer();
                StringBuffer space15 = new StringBuffer();
                StringBuffer space16 = new StringBuffer();
                StringBuffer space17 = new StringBuffer();
                StringBuffer space18 = new StringBuffer();
                StringBuffer space19 = new StringBuffer();
                //确定四个空格的长度
                for (int m = time.length(); m < 20; m++) {
                    space13.append(" ");//补全20位时间
                }
                String date = time.substring(0,10);
                for (int n = date.length(); n < 20; n++){
                    space14.append(" ");//补全20位日期
                }
                for (int o = ID.length(); o < 20; o++){
                    space15.append(" ");//补全20位账户
                }
                for (int p = user.length(); p < 20; p++){
                    space16.append(" ");//补全20位名字
                }
                for (int q = editBoxNumber.length(); q < 20; q++){
                    space17.append(" ");//补全20位箱号
                }
                for (int r = editInspectionNumber.length(); r < 20; r++){
                    space18.append(" ");//补全20位报检号
                }
                for (int s = editBillNumber.length(); s < 20; s++){
                    space19.append(" ");//补全20位提单号
                }

                A1 = editStandard.getText().toString();
                A2 = editMeasure.getText().toString();
                A3 = editBoxNumber.getText().toString();
                A4 = editInspectionNumber.getText().toString();
                A5 = editBillNumber.getText().toString();
                A6 = time;
                String content = "::OUT1::" + ID + space15 + user + space16
                        + editStandard.getText().toString() + space12 + editMeasure.getText().toString() + space11
                        + editBoxNumber.getText().toString() + space17 + editInspectionNumber.getText().toString() +space18
                        + editBillNumber.getText().toString() + space19 + time + space13 + date + space14;

                byte_1 = content.getBytes("UTF-8");
            //
                //发送异常数据(不包括图片)

                Bitmap image = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                comp(image);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int options = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                byte_0 = baos.toByteArray();
                Photo_Thread photo_thread = new Photo_Thread();
                photo_thread.start();//开启发送图片
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//发送异常数据
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            try {
                outputStream = socket.getOutputStream();
                String action = intent.getAction();
                if(action.equals("receive_photo_yeah")){
                    again = 0;
                    if(which_num == 6){
                        which_num = 1;
                        num1 = true;
                        num2 = true;
                        num3 = true;
                        num4 = true;
                        num5 = true;
                        isSend = false;
                        byte_0 = null;
                        Intent intent1 = new Intent(MonitorActivity.this,ItemActivity.class);
                        startActivity(intent1);
                        alarnReview.setText("发送异常数据");
                        Toast.makeText(MonitorActivity.this,"异常数据发送成功",Toast.LENGTH_SHORT).show();
                    }else if(which_num < 6){
                        which_num++;
                        Photo_Thread photo_thread = new Photo_Thread();
                        photo_thread.start();//开启发送其他图片
                    }
                }else if(action.equals("not_receive")){
                    if(again==3){
                        Toast.makeText(MonitorActivity.this,"当前网络环境太差，建议更换更换网络重新登陆",Toast.LENGTH_SHORT).show();
                        isSend = false;
                        alarnReview.setText("再次发送异常数据");
                    }else if(again==0){
                        again = 1;
                        Toast.makeText(MonitorActivity.this,"再次传送中...",Toast.LENGTH_SHORT).show();
                        Again_Thread again_thread = new Again_Thread();
                        again_thread.start();
                    }else if(again==1){
                        again = 2;
                        Toast.makeText(MonitorActivity.this,"再次传送中...",Toast.LENGTH_SHORT).show();
                        Again_Thread again_thread = new Again_Thread();
                        again_thread.start();
                    }else if(again==2){
                        again = 3;
                        Toast.makeText(MonitorActivity.this,"再次传送中...",Toast.LENGTH_SHORT).show();
                        Again_Thread again_thread = new Again_Thread();
                        again_thread.start();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    class Photo_Thread extends Thread
    {
        public void run()
        {
            try
            {
                outputStream = socket.getOutputStream();
                StringBuffer space0 = new StringBuffer();
                for(int i= ID.length();i<20;i++)
                {
                    space0.append(" ");
                }
                int length = byte_0.length;
                if(length<30000){
                    num1 = false;
                }
                if(length<60000){
                    num2 = false;
                }
                if(length<90000){
                    num3 = false;
                }
                if(length<120000){
                    num4 = false;
                }
                if(length<150000){
                    num5 = false;
                }
                if(which_num == 1){//如果此时对应的包是第一份
                    if(num1){//发送完整的第一份
                        Byte = new byte[30000];//定义第一份图片
                        subByte(byte_0,0,30000);//取出第一份图片
                        Byte = BYTE;//存储第一份图片数据
                        byte[] byte01 = ("::OUT00::" + ID + space0 + A6 + " ").getBytes("UTF-8");//包头和基本信息
                        byteMerger(byte01,BYTE);//连接包头和第一份图片
                        byteChange1 = byte_3;//取出图片与包头
                        byteChange2 = ("::OUT00::").getBytes("UTF-8");//包尾
                        byteMerger(byteChange1,byteChange2);//第一份封装好的数据
                        byteChange = byte_3;
                        outputStream.write(byteChange);//发送第一份包
                        alarnReview.setText("数据发送中，请稍后...");//显示正在发送图片
                    }else {//第一份补齐30000位
                        Byte = new byte[30000];//定义第一份图片
                        subByte(byte_0,0,length);//取出第一份图片
                        Byte = BYTE;//存储第一份图片数据
                        byte[] result = new byte[1024 * 50];//定义一个数组
                        System.arraycopy(Byte, 0, result, 0, length);
                        subByte(result,0,30000);//返回BYTE
                        byte[] byte01 = ("::OUT00::" + ID + space0 + A6 + " ").getBytes("UTF-8");//包头和基本信息
                        byteMerger(byte01,BYTE);//连接包头和第一份图片//返回byte_3
                        byteChange1 = byte_3;
                        byteChange2 = ("::OUT00::").getBytes("UTF-8");//包尾
                        byteMerger(byteChange1,byteChange2);
                        byteChange = byte_3;
                        outputStream.write(byteChange);//发送第一份包
                    }
                }else if(which_num == 2){
                    if(num2){
                        Byte = new byte[30000];//定义第一份图片二份图片
                        subByte(byte_0,30000,30000);//取出第一份图片
                        Byte = BYTE;//存储第二份图片数据
                        byte[] byte01 = ("::OUT01::" + ID + space0 + A6 + " ").getBytes("UTF-8");//包头和基本信息
                        byteMerger(byte01,BYTE);//连接包头和第二份图片
                        byteChange1 = byte_3;//取出图片与包头
                        byteChange2 = ("::OUT01::").getBytes("UTF-8");//包尾
                        byteMerger(byteChange1,byteChange2);//第二份封装好的数据
                        byteChange = byte_3;
                        outputStream.write(byteChange);//发送第二份包
                    }else {//第二个发送不完整的包
                        Byte = new byte[30000];//定义第一份图片
                        subByte(byte_0,30000,length-30000);//取出第二份图片
                        Byte = BYTE;//存储第一份图片数据
                        byte[] result = new byte[1024 * 50];//定义一个数组
                        System.arraycopy(Byte, 0, result, 0, Byte.length);
                        subByte(result,0,30000);//返回BYTE
                        byte[] byte01 = ("::OUT01::" + ID + space0 + A6 + " ").getBytes("UTF-8");//包头和基本信息
                        byteMerger(byte01,BYTE);//连接包头和第二份图片//返回byte_3
                        byteChange1 = byte_3;
                        byteChange2 = "::OUT01::".getBytes();
                        byteMerger(byteChange1,byteChange2);
                        byteChange = byte_3;
                        outputStream.write(byteChange);//发送第二份包
                    }
                }else if(which_num == 3){
                    if(num3){
                        Byte = new byte[30000];//定义第三份图片
                        subByte(byte_0,60000,30000);//取出第三份图片
                        Byte = BYTE;//存储第一份图片数据
                        byte[] byte01 = ("::OUT02::" + ID + space0 + A6 + " ").getBytes("UTF-8");//包头和基本信息
                        byteMerger(byte01,BYTE);//连接包头和第三份图片
                        byteChange1 = byte_3;//取出图片与包头
                        byteChange2 = ("::OUT02::").getBytes("UTF-8");//包尾
                        byteMerger(byteChange1,byteChange2);//第三份封装好的数据
                        byteChange = byte_3;
                        outputStream.write(byteChange);//发送第三份包
                    }else{//byte数据长度小于90000
                        Byte = new byte[30000];//定义第一份图片
                        subByte(byte_0,60000,length-60000);//取出第二份图片
                        Byte = BYTE;//存储第一份图片数据
                        byte[] result = new byte[1024 * 50];//定义一个数组
                        System.arraycopy(Byte, 0, result, 0, Byte.length);
                        subByte(result,0,30000);//返回BYTE
                        byte[] byte01 = ("::OUT02::" + ID + space0 + A6 + " ").getBytes("UTF-8");//包头和基本信息
                        byteMerger(byte01,BYTE);//连接包头和第二份图片//返回byte_3
                        byteChange1 = byte_3;
                        byteChange2 = "::OUT02::".getBytes();
                        byteMerger(byteChange1,byteChange2);
                        byteChange = byte_3;
                        outputStream.write(byteChange);//发送第二份包
                    }
                }else if(which_num == 4){
                    if(num4){
                        Byte = new byte[30000];//定义第四份图片
                        subByte(byte_0,90000,30000);//取出第四份图片
                        Byte = BYTE;//存储第四份图片数据
                        byte[] byte01 = ("::OUT03::" + ID + space0 + A6 + " ").getBytes("UTF-8");//包头和基本信息
                        byteMerger(byte01,BYTE);//连接包头和第四份图片
                        byteChange1 = byte_3;//取出图片与包头
                        byteChange2 = ("::OUT03::").getBytes("UTF-8");//包尾
                        byteMerger(byteChange1,byteChange2);//第四份封装好的数据
                        byteChange = byte_3;
                        outputStream.write(byteChange);//发送第四份包
                    }else{//byte数据长度小于120000
                        Byte = new byte[30000];//定义第一份图片
                        subByte(byte_0,90000,length-90000);//取出第二份图片
                        Byte = BYTE;//存储第一份图片数据
                        byte[] result = new byte[1024 * 50];//定义一个数组
                        System.arraycopy(Byte, 0, result, 0, Byte.length);
                        subByte(result,0,30000);//返回BYTE
                        byte[] byte01 = ("::OUT03::" + ID + space0 + A6 + " ").getBytes("UTF-8");//包头和基本信息
                        byteMerger(byte01,BYTE);//连接包头和第二份图片//返回byte_3
                        byteChange1 = byte_3;
                        byteChange2 = "::OUT03::".getBytes();
                        byteMerger(byteChange1,byteChange2);
                        byteChange = byte_3;
                        outputStream.write(byteChange);//发送第二份包
                    }
                }else if(which_num == 5){
                    if(num4){
                        if(num5){
                            Byte = new byte[30000];//定义第五份图片
                            subByte(byte_0,120000,length - 120000);//取出第五份图片
                            Byte = BYTE;//存储第四份图片数据
                            byte[] byte01 = ("::OUT04::" + ID + space0 + A6 + " ").getBytes("UTF-8");//包头和基本信息
                            byteMerger(byte01,BYTE);//连接包头和第五份图片
                            byteChange1 = byte_3;//取出图片与包头
                            byteChange2 = ("::OUT04::").getBytes("UTF-8");//包尾
                            byteMerger(byteChange1,byteChange2);//第五份封装好的数据
                            byteChange = byte_3;
                            outputStream.write(byteChange);//发送第五份包
                        }else {//数据小于120000
                            Byte = new byte[30000];//定义第一份图片
                            subByte(byte_0,120000,length-120000);//取出第二份图片
                            Byte = BYTE;//存储第一份图片数据
                            byte[] result = new byte[1024 * 50];//定义一个数组
                            System.arraycopy(Byte, 0, result, 0, Byte.length);
                            subByte(result,0,30000);//返回BYTE
                            byte[] byte01 = ("::OUT04::" + ID + space0 + A6 + " ").getBytes("UTF-8");//包头和基本信息
                            byteMerger(byte01,BYTE);//连接包头和第二份图片//返回byte_3
                            byteChange1 = byte_3;
                            byteChange2 = "::OUT04::".getBytes();
                            byteMerger(byteChange1,byteChange2);
                            byteChange = byte_3;
                            outputStream.write(byteChange);//发送第二份包
                        }
                    }else {//第五份没有
                        Byte = new byte[30000];//定义第一份图片
                        Byte = "".getBytes();//存储第一份图片数据
                        byte[] result = new byte[1024 * 50];//定义一个数组
                        System.arraycopy(Byte, 0, result, 0, Byte.length);
                        subByte(result,0,30000);//返回BYTE
                        byte[] byte01 = ("::OUT04::" + ID + space0 + A6 + " ").getBytes("UTF-8");//包头和基本信息
                        byteMerger(byte01,BYTE);//连接包头和第二份图片//返回byte_3
                        byteChange1 = byte_3;
                        byteChange2 = "::OUT04::".getBytes();
                        byteMerger(byteChange1,byteChange2);
                        byteChange = byte_3;
                        outputStream.write(byteChange);//发送第二份包
                    }
                }else if(which_num == 6){
                    outputStream.write(byte_1);
                }
            } catch (Exception e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }//tcp脉搏
    class Again_Thread extends Thread
    {
        public void run()
        {
            try
            {
                outputStream = socket.getOutputStream();
                if(which_num == 1){//再次发送第一份
                    outputStream.write(byteChange);//再次发送第一份包
                }else if(which_num == 2){
                    outputStream.write(byteChange);//再次发送第二份包
                }else if(which_num == 3){
                    outputStream.write(byteChange);//再次发送第三份包
                }else if(which_num == 4){
                    outputStream.write(byteChange);//再次发送第四份包
                }else if(which_num == 5){
                    outputStream.write(byteChange);//再次发送第五份包
                }
            } catch (Exception e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }//tcp脉搏

    CountDownTimer cdt = new CountDownTimer(10000, 100)//参数1：计时总时间，参数2：每次扣除时间数

    {

        @Override

        public void onTick(long millisUntilFinished)

        {
            //定时时间到执行动作;
            isTrue = false;


        }
        @Override
        public void onFinish() {



        }
    };



    public static class CameraCanUseUtils {
        public static boolean isCameraCanUse() {
            boolean canUse = true;
            Camera mCamera = null;
            try {
                mCamera = Camera.open(0);
                mCamera.setDisplayOrientation(90);
            } catch (Exception e) {
                canUse = false;
            }
            if (canUse) {
                mCamera.release();
                mCamera = null;
            }
            //Timber.v("isCameraCanuse="+canUse);
            return canUse;
        }
    }

    class Time_Thread extends Thread
    {
        public void run()
        {
            try
            {
                outputStream = socket.getOutputStream();
//发送数据
                StringBuffer space14 = new StringBuffer();
                for (int n = ID.length(); n < 20; n++){
                    space14.append(" ");//补全20位ID
                }
                String time = "::PUL::" + ID + space14;//每隔三分钟发送一个脉搏
                for(int i = 0; i < 1000000000; i++) {
                    outputStream.write(time.getBytes());
                    try {
                        Time_Thread.sleep(120000);     //设置暂停的时间，5000=5秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }//tcp脉搏

    private Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>37) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 1;//每次都减少0.1
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    private Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //考虑到不同手机的分辨率可能不同，所以这里统一设置宽和高分辨率为1024*768
        float hh = 800;
        float ww = 480;
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    public void showdialog1()//对话框功能实现
    {
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage("是否发送正常数据");
        alertdialogbuilder.setPositiveButton("确定",click11);
        alertdialogbuilder.setNegativeButton("取消",click12);
        AlertDialog alertDialog1 = alertdialogbuilder.create();
        alertDialog1.show();
    }
    private DialogInterface.OnClickListener click11 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int arg1) {
            Data_Thread data_thread= new Data_Thread();
            data_thread.start();
        }
    };
    private DialogInterface.OnClickListener click12 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0, int arg1) {
            arg0.cancel();
        }
    };//对话框功能实现
    public void showdialog2()//对话框功能实现
    {
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage("是否发送异常数据");
        alertdialogbuilder.setPositiveButton("确定",click21);
        alertdialogbuilder.setNegativeButton("取消",click22);
        AlertDialog alertDialog1 = alertdialogbuilder.create();
        alertDialog1.show();
    }

    private DialogInterface.OnClickListener click21 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int arg1) {
            if(!isSend){
                isSend = true;
                if(!have_photo){
                    Toast.makeText(MonitorActivity.this,"请拍照后发送",Toast.LENGTH_SHORT).show();
                }else if(editBoxNumber.getText() == null){
                    Toast.makeText(MonitorActivity.this,"请填写箱号",Toast.LENGTH_SHORT).show();
                }else if(editInspectionNumber.getText()==null){
                    Toast.makeText(MonitorActivity.this,"请填写报检号",Toast.LENGTH_SHORT).show();
                }else if(editBillNumber.getText()==null){
                    Toast.makeText(MonitorActivity.this,"请填写提单号",Toast.LENGTH_SHORT).show();
                }else {
                    //发送异常数据
                    Out_Thread out_thread= new Out_Thread();
                    out_thread.start();
                }
            }else {
                Toast.makeText(MonitorActivity.this,"正在发送中...请稍后",Toast.LENGTH_SHORT).show();
            }


        }
    };
    private DialogInterface.OnClickListener click22 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface arg0, int arg1) {
            arg0.cancel();
        }
    };//对话框功能实现

    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2){
        byte_3 = new byte[byte_1.length+byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }//连接俩个byte
    public byte[] subByte(byte[] b,int off,int length){
        BYTE = new byte[length];
        System.arraycopy(b, off, BYTE, 0, length);
        return BYTE;
    }//截取俩个byte
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder mDialog=new AlertDialog.
                    Builder(MonitorActivity.this);
            mDialog.setTitle("操作提示");
            mDialog.setMessage("确定退出辐射监测？");
            mDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //调取发送退出代码的线程
                    Intent intent = new Intent(MonitorActivity.this,LoginActivity.class);
                    startActivity(intent);
                    Ext_Thread ext_thread= new Ext_Thread();
                    ext_thread.start();//开启发送程序退出的代码（发送::EXT::）
                }
            });
            mDialog.setNegativeButton("取消",null);
            mDialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }
    class Ext_Thread extends Thread
    {
        public void run()
        {
            try
            {
                StringBuffer space1 = new StringBuffer();
                StringBuffer space2 = new StringBuffer();
                for(int i= user.length();i<20;i++)
                {
                    space1.append(" ");
                }
                //补全20位密码
                for(int j= ID.length();j<20;j++)
                {
                    space2.append(" ");
                }
                outputStream = socket.getOutputStream();
//发送数据
                outputStream.write(("::EXT::" + user + space1 + ID + space2 + IP).getBytes());

            } catch (Exception e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        isSend = false;
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    @SuppressLint("NewApi")
    private void initSound() {
        soundPool = new SoundPool.Builder().build();
        soundID = soundPool.load(this, R.raw.aaa, 1);
    }
    private void playSound() {
        soundPool.play(
                soundID,
                1f,      //左耳道音量【0~1】
                1f,      //右耳道音量【0~1】
                0,         //播放优先级【0表示最低优先级】
                0,         //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                2          //播放速度【1是正常，范围从0~2】
        );
    }

    TextWatcher textChange1 = new TextWatcher(){

        @SuppressLint("UseValueOf")
        @Override
        public void afterTextChanged(Editable s) {
            try{
                if(!TextUtils.isEmpty(editStandard.getText().toString()) && !TextUtils.isEmpty(editMeasure.getText().toString())){
                    Integer num1;
                    num1=new Integer(editStandard.getText().toString());
                    Integer num2;
                    num2=new Integer(editMeasure.getText().toString());
                    if(num2 > num1 * 3){
                        playSound();
                    }else {
                        Toast.makeText(MonitorActivity.this,"正常数据无需报警",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MonitorActivity.this,"无数据或数据不全",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){

            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }};
    TextWatcher textChange2 = new TextWatcher(){

        @SuppressLint("UseValueOf")
        @Override
        public void afterTextChanged(Editable s){
            try{
                if(!TextUtils.isEmpty(editStandard.getText().toString()) && !TextUtils.isEmpty(editMeasure.getText().toString())){
                    Integer num2;
                    num2=new Integer(editMeasure.getText().toString());
                    Integer num1;
                    num1=new Integer(editStandard.getText().toString());
                    if(num2 > num1 * 3){
                        playSound();
                    }else {
                        Toast.makeText(MonitorActivity.this,"正常数据无需报警",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MonitorActivity.this,"无数据或数据不全",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){

            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }};
}
