package com.example.yemudan.graphicstest;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    float triangleCoords[] = {   // in counterclockwise order:
            0.0f,  0.0f, 0.0f, // top
            -0.6f, -0.6f, 0.0f, // bottom left
            0.6f, -0.6f, 0.0f  // bottom right
    };
    float turquoise[] = { 0.1844f, 0.9795f, 0.7663f ,1.0f };

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
    private Circle nose;


    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // initialize a triangle
        mTriangle = new Triangle(triangleCoords, turquoise);

        mCircle = new Circle(circleCoords, turquoise);

        float eyeOutlineCoords[] = {-0.08f,0.25f,0.08f,0.14f};
        float eyeCoords[] = {-0.08f,0.25f,0.07f,0.13f};
        //float eyeBallCoords[] = {};

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
    }

    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw shape

        //mTriangle.draw(mMVPMatrix);
        mCircle.draw(mMVPMatrix);
        mFace.draw(mMVPMatrix);
        mLeftEyeOutline.draw(mMVPMatrix);
        mRightEyeOutline.draw(mMVPMatrix);
        mLeftEye.draw(mMVPMatrix);
        mRightEye.draw(mMVPMatrix);

        //draw triangle
        //mTriangle.draw();
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

}
