package com.example.administrator.touchdemo;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
//    MyPaintView paintView = null;
//    MyPaintView view  =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //paintView = new MyPaintView(this);
        //view = (MyPaintView)findViewById(R.id.test);
        setContentView(R.layout.activity_main);
    }
}
