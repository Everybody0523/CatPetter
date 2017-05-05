package com.example.yemudan.graphicstest;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class MainActivity extends AppCompatActivity {

    private MyGLSurfaceView myGLSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myGLSurfaceView = (MyGLSurfaceView) findViewById(R.id.myGLSurfaceView);

    }

    @Override
    protected  void onResume(){
        super.onResume();
        myGLSurfaceView.onResume();
    }

    @Override
    protected  void onPause(){
        super.onPause();
        myGLSurfaceView.onPause();
    }
}




