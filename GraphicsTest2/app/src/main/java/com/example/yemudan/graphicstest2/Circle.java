package com.example.yemudan.graphicstest2;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by Yemudan on 2017-05-04.
 */

public class Circle {




    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    // Use to access and set the view transformation
    private int mMVPMatrixHandle;

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";




    private FloatBuffer vertexBuffer;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;

    //private float CircleCoords[];
    /*
    static float CircleCoords[] = {   // in counterclockwise order:
            0.0f,  0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };
    */
    /*
    float CircleCoords[] = {   // in counterclockwise order:
            0.0f,  0.2f, 0.0f, // top
            -0.1f, -0.2f, 0.0f, // bottom left
            0.1f, -0.2f, 0.0f  // bottom right
    };*/

    // Set color with red, green, blue and alpha (opacity) values
    //float color[] = { 0.1844f, 0.9795f, 0.7663f ,1.0f };

    float color[];
    /*
    float CircleCoords[] = {   // in counterclockwise order:
            -0.2f,  0.2f, 0.0f, // top left
            0.2f,  0.2f, 0.0f, // top right
            -0.2f, -0.2f, 0.0f, // bottom left
            0.2f, -0.2f, 0.0f  // bottom right
    };
    */
    float CircleCoords[];
    int vertexCount;

    private final int mProgram;

    public Circle(float[] coordinates, float[] colorIn) {

        color = colorIn;
        //coordinates: 0 centerx, 1 centery, 2 width, 3 length


        CircleCoords = new float[36*3];
        for (int i=0; i<CircleCoords.length/2; i=i+3) {
            //float i2 = i+CircleCoords.length/4;
            double rad = PI/2 + PI/(CircleCoords.length/3) + 2*PI*i/(CircleCoords.length);

            CircleCoords[2*i] = coordinates[0]  + (coordinates[2])*((float)cos(rad));
            CircleCoords[2*i+1] = coordinates[1] + (coordinates[3])*((float)sin(rad));
            CircleCoords[2*i+2] = 0;

            CircleCoords[2*i+3] = coordinates[0]  - (coordinates[2])*((float)cos(rad));
            CircleCoords[2*i+4] = coordinates[1] + (coordinates[3])*((float)sin(rad));
            CircleCoords[2*i+5] = 0;

            Log.d("rad:", String.valueOf(rad*180/PI));
            Log.d("x1:", String.valueOf(CircleCoords[2*i]));
            Log.d("y1:", String.valueOf(CircleCoords[2*i+1]));
            Log.d("x2:", String.valueOf(CircleCoords[2*i+3]));
            Log.d("y2:", String.valueOf(CircleCoords[2*i+4]));
            //Log.d(CircleCoords[i+1]);

        }


        vertexCount = CircleCoords.length / COORDS_PER_VERTEX;
        Log.d("vertext count:", String.valueOf(vertexCount));

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                CircleCoords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(CircleCoords);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);


        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);
    }

    private int mPositionHandle;
    private int mColorHandle;

    //private final int vertexCount = CircleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex


    public void draw(float[] mvpMatrix) { // pass in the calculated transformation matrix
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosxition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the Circle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the Circle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the Circle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);


        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);


        // Draw the Circle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    public void setColor(float [] colorin){
        color = colorin;
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

    }

}
