package com.example.yemudan.graphicstest2;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import static java.lang.Math.PI;
import static java.lang.Math.floor;
import static java.lang.Math.random;

/**
 * Created by Yemudan on 2017-05-04.
 */

class MyGLSurfaceView extends GLSurfaceView {

    MyGLRenderer mRenderer;
    float [] turquoise = { 0.1844f, 0.9795f, 0.7663f ,1.0f };
    float [] turquoise2 = { 0.8844f, 0.9795f, 0.7663f ,1.0f };
    double i;

    public MyGLSurfaceView(Context context){
        super(context);
        init();
        //mRenderer = new MyGLRenderer(context);
        Log.d("context"," in mRenderer");
        i = floor(random()*2);
        colorSetter(i);

    }
    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super (context,attrs);
        init();
        //mRenderer = new MyGLRenderer(context);
    }

    public void colorSetter(double i){
        if (i == 1.0){
            mRenderer.setdColor(turquoise);
        }
        else{
            mRenderer.setdColor(turquoise2);
        }
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


            case MotionEvent.ACTION_DOWN:



                if (x < getWidth()/2+getWidth()/2*0.1f && x > getWidth()/2-getWidth()/2*0.1f
                        && y > getHeight()/2+getHeight()/2*0.6f && y < getHeight()/2+getHeight()/2*0.8f){

                        mRenderer.setColor(turquoise2);
                        mRenderer.setCur(1);
                        i = floor(random()*2);
                        colorSetter(i);
                        Log.d("detected i:", String.valueOf(i));
                        requestRender();
                    //Log.d("detected x:", String.valueOf(x));
                }

                if (x < getWidth()/2+getWidth()/2*0.1f && x > getWidth()/2-getWidth()/2*0.1f
                        && y > getHeight()/2+getHeight()/2*0.3f && y < getHeight()/2+getHeight()/2*0.5f){

                    mRenderer.setColor(turquoise);
                    mRenderer.setCur(2);
                    i = floor(random()*2);
                    colorSetter(i);
                    requestRender();
                    //Log.d("detected x:", String.valueOf(x));
                }

                requestRender();



        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }



}