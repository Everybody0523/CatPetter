package catcolor.cat.yemudan.graphicstest2;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;



public class MyGLRenderer implements GLSurfaceView.Renderer {


    private final Context mActivityContext;
    private Sprite sprite;
    private Sprite sprite2;
    private Sprite sprite3;

    public ScoreBoard board;

    public volatile int mCurSprite = 1;

    public MyGLRenderer(final Context activityContext)
    {
        mActivityContext = activityContext;
    }

    public volatile float [] upColor = { 0.7844f, 0.9795f, 0.7663f ,1.0f };
    public volatile float [] downColor = { 0.6844f, 0.9795f, 0.7663f ,1.0f };
    public volatile float [] dColor = { 0.7844f, 0.9795f, 0.7663f ,1.0f };

    float triangleCoords[] = {   // in counterclockwise order:
            0.0f,  0.0f, 0.0f, // top
            -0.6f, -0.6f, 0.0f, // bottom left
            0.6f, -0.6f, 0.0f  // bottom right
    };
    float turquoise[] = { 0.1844f, 0.9795f, 0.7663f ,1.0f };

    float button1Coords[] = {0,-0.8f,0.1f,0.1f};
    float button2Coords[] = {0,-0.5f,0.1f,0.1f};
    float display[] = {0, 0.5f, 0.1f,0.1f};
    float circleCoords[] = {0,0,0.4f,0.4f};

    private Triangle mTriangle;

    private Square   mSquare;
    private Circle   mCircle;
    private Circle   mLeftEyeOutline;
    private Circle   mRightEyeOutline;
    private Circle   mLeftEye;
    private Circle   mRightEye;
    private Circle   mLeftEyeBall;
    private Circle   mRightEyeBall;
    private Circle   mFace;
    private Circle   nose;

    private Circle   button1;
    private Circle   button2;
    private Circle   displayButton;

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {


        // Set the background frame color
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        // initialize a triangle
        //mTriangle = new Triangle(triangleCoords, turquoise);
        //mCircle = new Circle(circleCoords, turquoise);

        button1 = new Circle(button1Coords, upColor);
        button2 = new Circle(button2Coords, downColor);
        displayButton = new Circle(display, dColor);

        mCircle = new Circle(circleCoords, turquoise);

        float eyeOutlineCoords[] = {-0.08f,0.25f,0.08f,0.14f};
        float eyeCoords[] = {-0.08f,0.25f,0.07f,0.13f};
        //float eyeBallCoords[] = {}

        float white[] = {1.0f,1.0f,1.0f,1.0f};
        float black[] = {0,0,0,1.0f};

        mLeftEyeOutline = new Circle(eyeOutlineCoords, turquoise);
        eyeOutlineCoords[0]=0.08f;
        mRightEyeOutline = new Circle(eyeOutlineCoords, turquoise);

        mLeftEye = new Circle(eyeCoords, white);
        eyeCoords[0]=0.08f;
        mRightEye = new Circle(eyeCoords, white);

        float faceCoords[] = {0,-0.075f,0.325f,0.325f};
        mFace = new Circle(faceCoords, white);

        // initialize a square
        mSquare = new Square();

        sprite3 = new Sprite(mActivityContext, R.drawable.goodojobu);
        sprite2 = new Sprite(mActivityContext, R.drawable.catfrown);
        sprite = new Sprite(mActivityContext, R.drawable.catsmile);

        board = new ScoreBoard();

    }

    private float[] mRotationMatrix = new float[16];

    public void onDrawFrame(GL10 unused) {

        float[] scratch = new float[16];
        float[] emptyMatrix = new float[16];
        Matrix.setIdentityM(emptyMatrix, 0); // initialize to identity matrix
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        float [] mModelMatrix = new float[16];
        Matrix.setIdentityM(mModelMatrix, 0); // initialize to identity matrix


        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        Matrix.multiplyMM(emptyMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw shape

        //mTriangle.draw(mMVPMatrix);



        // Create a rotation transformation for the triangle
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);

        float dist = 0.00009f * ((int) time);

        Matrix.setRotateM(mRotationMatrix, 0, 0, 0, 0, -1.0f);
        //Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, -1.0f);
        //Matrix.translateM(mModelMatrix, 0, dist, 0, 0);

        Matrix.translateM(mModelMatrix, 0, mXDist, mYDist, 0);
        //Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, -1.0f);

        float [] mTempMatrix = mModelMatrix.clone();
        //Combine rotation and translation via Matrix.multiplyMM
        Matrix.multiplyMM(mModelMatrix, 0, mTempMatrix, 0, mRotationMatrix, 0);
        mTempMatrix = mMVPMatrix.clone();


        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        //Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
        Matrix.multiplyMM(scratch, 0, mTempMatrix, 0,mModelMatrix, 0);
        //Matrix.multiplyMM(mMVPMatrix, 0, mTempMatrix, 0, mModelMatrix, 0);

        // Draw triangle
        //mTriangle.draw(scratch);
        //mTriangle.draw(mMVPMatrix);
        //sprite.Draw(mMVPMatrix);

        if (board.score > 9){
            sprite3.Draw(scratch);
        }
        else {
            if (mCurSprite == 1) {
                sprite.Draw(scratch);
            } else {
                sprite2.Draw(scratch);
            }
        }
        //mCircle.draw(emptyMatrix);

        /*
        mCircle.draw(scratch);
        mFace.draw(scratch);
        mLeftEyeOutline.draw(scratch);
        mRightEyeOutline.draw(scratch);
        mLeftEye.draw(scratch);
        mRightEye.draw(scratch);
        */

        //button2.setColor(downColor);
        button2.draw(emptyMatrix);

        //button1.setColor(upColor);
        button1.draw(emptyMatrix);

        displayButton.setColor(dColor);
        displayButton.draw(emptyMatrix);

        /*
        for (Circle e : board.circles){
            e.draw(emptyMatrix);
        }*/


        for (int j=0; j<10; j++){
            board.circles[j].draw(emptyMatrix);
            //Log.d("x should be:", String.valueOf(board.circles[j].getX()));
        }


    }

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];


    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public volatile float mAngle;
    public volatile float mXDist;
    public volatile float mYDist;

    //public volatile float [] mColor;

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }

    public float getXDist() {
        return mXDist;
    }

    public void setXDist(float xdist) {
        mXDist = xdist;
    }

    public float getYDist() {
        return mYDist;
    }

    public void setYDist(float ydist) {
        mYDist = ydist;
    }

    public float [] getupColor() {
        return upColor;
    }

    public void setupColor(float [] color) {
        upColor = color;
    }

    public int getCur() {
        return mCurSprite;
    }

    public void setCur(int cur) {
        mCurSprite = cur;
    }

    public float [] getdColor() {
        return dColor;
    }

    public void setdColor(float [] color) {
        dColor = color;
    }

    public float [] getDownColor() {
        return downColor;
    }

}
