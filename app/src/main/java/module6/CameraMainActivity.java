package module6;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import com.example.nucleartechnology.R;

/**
 * 启动相机；
 * Created by 白海涛 on 2017/8/18.
 */

public class CameraMainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "CameraActivity";
    public static final String TAG1 = "hahahaha";
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    //为了使照片竖直显示
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private TextView liangDianLv;
    private ImageButton takePhoto;
    private SurfaceView photoPreView;
    private SurfaceHolder surfaceHolder;
    private CameraManager cameraManager;//摄像头管理器；
    private Handler childHanlder,mainHandler;
    private String mCameraID;
    private ImageReader imageReader;
    private CameraCaptureSession cameraCaptureSession;
    //CameraCaptureSession控制摄像头的预览或者拍照，setRepeatingRequest()开启预览，capture()拍照
    private CameraDevice cameraDevice;
    private CaptureRequest.Builder captureRequestBuilder;
    private boolean isStarted = false;
    long start;
    long end;
    private int cut = 0x10;
    private int zhenNum = 1;
    private List<Double> result = new ArrayList<>();
    private Double xishu, jieju;
    private Boolean isKedu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module6_main);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        xishu = bundle.getDouble("a");
        jieju = bundle.getDouble("b");
        isKedu = bundle.getBoolean("isKedu");

//        if (!checkCameraHardware(this)) {
//            Toast.makeText(CameraMainActivity.this, "相机不支持！", Toast.LENGTH_SHORT).show();
//        } else {
//            initView();
//        }

        if (!checkCameraHardware(this)) {
            Toast.makeText(CameraMainActivity.this, "相机不支持！", Toast.LENGTH_SHORT).show();
        } else {
            if (ContextCompat.checkSelfPermission(CameraMainActivity.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CameraMainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
            } else {
                initView();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initView();
                } else {
                    Toast.makeText(this,"你拒绝了权限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void initView() {
        liangDianLv = (TextView) findViewById(R.id.liangDianLv);
        takePhoto = (ImageButton) findViewById(R.id.take_photo);
        photoPreView = (SurfaceView) findViewById(R.id.photo_preview);
        takePhoto.setOnClickListener(this);

        surfaceHolder = photoPreView.getHolder();
        surfaceHolder.setKeepScreenOn(true);
        // mSurfaceView添加回调
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d(TAG, "surfaceCreated: ");
                //SurfaceView创建，初始化Camera；
                initCamera2();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d(TAG, "surfaceChanged: ");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.d(TAG, "surfaceDestroyed: ");
                //SurfaceView销毁，释放Camera资源；
                if (cameraDevice != null) {
                    cameraDevice.close();
                    CameraMainActivity.this.cameraDevice = null;
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initCamera2() {
        final HandlerThread handlerThread = new HandlerThread("Camera2");
        handlerThread.start();
        childHanlder = new Handler(handlerThread.getLooper());
        mainHandler = new Handler(getMainLooper());//mainHandler管理主线程，在飞主线程下需要用getMainLooper（）来创建；
        mCameraID = "" + CameraCharacteristics.LENS_FACING_FRONT;//后置摄像头；
        //首先我们想要对摄像设备进行操作，需要获得CameraManager的实例
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            cameraManager.openCamera(mCameraID, stateCallback, mainHandler);//打开摄像头；
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        imageReader = ImageReader.newInstance(1080, 1920, ImageFormat.YUV_420_888, 2);
        imageReader.setOnImageAvailableListener(imageAvailableListener,childHanlder);
    }

    private ImageReader.OnImageAvailableListener imageAvailableListener= new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {
            Image image = null;
            ByteBuffer byteBuffer = null;
            image = reader.acquireLatestImage();
            //Log.d(TAG, "onImageAvailable: excused");

            if (isStarted) {
                try {
                    Image.Plane[] planes = image.getPlanes();
                    byteBuffer = planes[0].getBuffer();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    long width = image.getWidth();
                    long height = image.getHeight();
                    double percentage = getLightPointsPercent(width, height, bytes);
                    result.add(percentage);
                    Log.d(TAG, "帧数：" + zhenNum);
                    Log.d(TAG, "亮点率：" + percentage * 100);

                    zhenNum++;
                } finally {
                    if (image != null) {
                        image.close();
                        byteBuffer.clear();
                    }
                }
            }
            if (image != null) {
                image.close();
            }
        }
    };

    private double getLightPointsPercent(long width, long height, byte[] bytes) {
        double num = 0;
        for (int i = 0; i < width * height; i++) {
            //String preValue = Integer.toHexString(bytes[i]); // 转为16进制
            // 因为byte[] 元素是一个字节，这里只取16进制的最后一个字节
            //String lastValue = preValue.length() > 2 ? preValue.substring(preValue.length() - 2) : preValue;
            //if (Integer.parseInt(lastValue,16) > cut)
            //    num++;
            if(bytes[i]>cut)
                num++;
        }
        return num/((double) (width*height));
    }

    //回调函数用于指定连接摄像头设备时不同状态的操作
    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            Log.d(TAG, "CameraDevice.StateCallback--onOpened: ");
            cameraDevice = camera;
            //开始预览；
            takePreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            Log.d(TAG, "CameraDevice.StateCallback--onDisconnected: ");
            cameraDevice.close();
            CameraMainActivity.this.cameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            Toast.makeText(CameraMainActivity.this,"摄像头开启失败",Toast.LENGTH_SHORT).show();
        }
    };

    private void takePreview() {

        try {
            // 创建预览需要的CaptureRequest.Builder
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            //将ImageReader添加到请求，否则预览图片时不会调用onImageAvailable（）
            captureRequestBuilder.addTarget(imageReader.getSurface());
            // 将SurfaceView的surface作为CaptureRequest.Builder的目标
            captureRequestBuilder.addTarget(surfaceHolder.getSurface());
            //创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求
            // createCaptureSession(List, CameraCaptureSession.StateCallback, Handler)的第一个参数传入了我们想要绘制的视图列表，传的imagerReader进来，
            //第二个参数传入的是建立摄像会话的状态回调函数，第三个参数传入相应的handler处理器
            cameraDevice.createCaptureSession(Arrays.asList(surfaceHolder.getSurface(),
                    imageReader.getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    if (cameraDevice == null) {
                        return;
                    }
                    //当摄像头已准备好时，开始预览；
                    cameraCaptureSession = session;
                    try {
                        CaptureRequest captureRequest = captureRequestBuilder.build();
                        cameraCaptureSession.setRepeatingRequest(captureRequest, null, mainHandler);//setRepeatingRequest()开启预览
                        Log.d(TAG, "开启了预览");
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    Toast.makeText(CameraMainActivity.this,"配置失败",Toast.LENGTH_SHORT).show();
                }
            },childHanlder);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private boolean checkCameraHardware(Context context)  {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    public void onClick(View v) {
        if (!isStarted) {
            isStarted = true;
            result.clear();
            zhenNum = 1;
            start = (new Date()).getTime();
            Toast.makeText(getApplicationContext(),"开始测量",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "timeBetweenTwoClick   " +"startclick");
        } else {
            isStarted = false;
            Toast.makeText(getApplicationContext(),"停止测量",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "timeBetweenTwoClick   " +"endclick");
            end = (new Date()).getTime();
            Log.d(TAG, "timeBetweenTwoClick   " + (end - start) / 1000 + " 秒");
            Double sum = 0.0;
            for (int i = 0; i < result.size(); i++) {
                sum = sum + result.get(i);
            }
            Double avereage = sum / result.size();
            Log.d(TAG, "onClick: 帧数： " + zhenNum + "  平均亮点率： " + avereage);

            if (isKedu) {
                String s1 = "<font color = 'green'>亮点率：</font> <font color = 'red'>%4.4f</font>  %%" +
                        "<br><font color = 'green'>帧数：</font> <font color = 'red'>%4d</font> <font color = 'green'>帧 " +
                        "<br>用时：</font> <font color = 'red'>%4d</font>秒</br></br>" +
                        "<br>剂量率：<font color = 'red'>%4.4f</font>  mSv</br>";
                s1 = String.format(s1, avereage * 100, result.size(), (end - start) / 1000, xishu * avereage + jieju);
                liangDianLv.setText(Html.fromHtml(s1));
            } else {
                String s = "<font color = 'green'>亮点率：</font> <font color = 'red'>%4.4f</font>  %%" +
                        "<br><font color = 'green'>帧数：</font> <font color = 'red'>%4d</font> <font color = 'green'>帧 " +
                        "<br>用时：</font> <font color = 'red'>%4d</font>秒</br></br>" +
                        "<br>剂量率：<font color = 'red'>待刻度</font>  mSv</br>";
                s = String.format(s, avereage * 100, result.size(), (end - start) / 1000);
                liangDianLv.setText(Html.fromHtml(s));
            }

            result.clear();
            zhenNum = 1;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }


}
