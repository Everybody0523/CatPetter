package com.example.yemudan.graphicstest2;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import static java.lang.Math.PI;

/**
 * Created by Yemudan on 2017-05-04.
 */

class MyGLSurfaceView extends GLSurfaceView {

    MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context){
        super(context);
        init();
        //mRenderer = new MyGLRenderer(context);
        Log.d("context"," in mRenderer");
    }
    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super (context,attrs);
        init();
        //mRenderer = new MyGLRenderer(context);
    }



    public void init(){

        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);



        //Context mActivityContext = new Context();
        mRenderer = new MyGLRenderer(getContext());
        setRenderer(mRenderer);

        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }

    //private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private final float TOUCH_SCALE_FACTOR = 45.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                /*
                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }
                */

                float translateFactor = 0.001f;

                mRenderer.setXDist(mRenderer.getXDist()+(-1)*dx*translateFactor);


                mRenderer.setYDist(mRenderer.getYDist()+ (-1)*dy*translateFactor);


                mRenderer.setAngle(
                        mRenderer.getAngle() +
                                ((dx + dy) * TOUCH_SCALE_FACTOR));
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }



}