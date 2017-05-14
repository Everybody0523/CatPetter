package com.example.yemudan.graphicstest2;

import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by wmf41 on 5/13/2017.
 */

public class ScoreBoard{
    public int score;
    //public ArrayList<Circle> circles;
    public Circle [] circles = new Circle[10];
    private float [] turquoise2 = { 0.8844f, 0.9795f, 0.7663f ,1.0f };
    private float [] turquoise = { 0.6844f, 0.9795f, 0.7663f ,1.0f };

    public ScoreBoard(){
        score = 0;
        int i = 1;
        while (i < 11) {
            float x = -0.55f + 0.1f*i;
            float display[] = {x, 0.7f, 0.05f,0.05f};
            Circle e = new Circle(display, turquoise2);
            circles[i-1] = e;
            //circles[i]=e;
            i += 1;
        }
    }
    public void update(int i) {
        score += i;
        if (score > 0 && score <= 10){
            circles[score].setColor(turquoise);
        }
    }
}