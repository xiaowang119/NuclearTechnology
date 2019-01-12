package com.artifex.mupdfdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity{

    private ReaderView mDocView;
    private MuPDFCore mCore;
    private MuPDFPageAdapter mAdapter;
    private String mPdfFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        String str = intent.getStringExtra("path");

        mPdfFilePath = "/data/data/com.example.nucleartechnology/PDFs/"+str;
        RelativeLayout layout = new RelativeLayout(this);
        mDocView = new ReaderView(this);
        try {
            mCore = new MuPDFCore(this,mPdfFilePath);
        } catch (Exception e){
            e.printStackTrace();
        }

        if (mCore!=null&&mCore.countPages()==0){
            mCore = null;
        }
        if (null == mCore){
            Toast.makeText(this,"文件已损坏，无法打开",Toast.LENGTH_LONG).show();
            return;
        }
        mAdapter = new MuPDFPageAdapter(this,mCore);
        layout.setBackgroundColor(Color.BLACK);
        mDocView.setAdapter(mAdapter);
        layout.addView(mDocView);
        setContentView(layout);
    }
}
