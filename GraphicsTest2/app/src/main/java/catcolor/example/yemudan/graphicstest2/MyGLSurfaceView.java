package catcolor.example.yemudan.graphicstest2;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import static java.lang.Math.random;

/**
 * Created by Yemudan on 2017-05-04.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    MyGLRenderer mRenderer;
    //float [] turquoise = { 0.6844f, 0.9795f, 0.7663f ,1.0f };
    //float [] turquoise2 = { 0.8844f, 0.9795f, 0.7663f ,1.0f };
    double i;

    public MyGLSurfaceView(Context context){
        super(context);
        init();
        //mRenderer = new MyGLRenderer(context);
        Log.d("context"," in mRenderer");
        colorSetter();

    }
    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super (context,attrs);
        init();
        //mRenderer = new MyGLRenderer(context);
    }

    public void colorSetter(){
        i = random();
        if (i <= 0.5){
            mRenderer.setdColor(mRenderer.getupColor());
        }
        else{
            mRenderer.setdColor(mRenderer.getDownColor());
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
            /*
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;


                float translateFactor = 0.001f;

                mRenderer.setXDist(mRenderer.getXDist()+(-1)*dx*translateFactor);


                mRenderer.setYDist(mRenderer.getYDist()+ (-1)*dy*translateFactor);


                mRenderer.setAngle(
                        mRenderer.getAngle() +
                                ((dx + dy) * TOUCH_SCALE_FACTOR));
                requestRender();

            */
            case MotionEvent.ACTION_DOWN:



                if (x < getWidth()/2+getWidth()/2*0.1f && x > getWidth()/2-getWidth()/2*0.1f
                        && y > getHeight()/2+getHeight()/2*0.7f && y < getHeight()/2+getHeight()/2*0.9f){

                        //mRenderer.setColor(turquoise2);
                        if (mRenderer.getdColor().equals(mRenderer.getupColor())){
                            mRenderer.board.update(1);
                        }
                        else {
                            mRenderer.board.update(-1);
                        }
                        mRenderer.setCur(1);
                        colorSetter();
                        //Log.d("detected i:", String.valueOf(i));
                        requestRender();
                    //Log.d("detected x:", String.valueOf(x));
                }

                if (x < getWidth()/2+getWidth()/2*0.1f && x > getWidth()/2-getWidth()/2*0.1f
                        && y > getHeight()/2+getHeight()/2*0.4f && y < getHeight()/2+getHeight()/2*0.6f){

                    //mRenderer.setColor(turquoise);
                    if (mRenderer.getdColor().equals(mRenderer.getDownColor())){
                        mRenderer.board.update(1);
                    }
                    else {
                        mRenderer.board.update(-1);
                    }
                    mRenderer.setCur(2);
                    colorSetter();
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