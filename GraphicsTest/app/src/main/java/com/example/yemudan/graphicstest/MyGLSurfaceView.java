package com.example.yemudan.graphicstest;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by Yemudan on 2017-05-04.
 */

class MyGLSurfaceView extends GLSurfaceView {


    public MyGLSurfaceView(Context context){
        super(context);
        init();
    }
    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super (context,attrs);
        init();
    }
    public void init(){

        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);

        MyGLRenderer mRenderer = new MyGLRenderer();
        setRenderer(mRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }
}