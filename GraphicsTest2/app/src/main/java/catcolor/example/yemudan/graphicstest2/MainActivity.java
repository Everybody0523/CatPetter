package catcolor.example.yemudan.graphicstest2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    private MyGLSurfaceView myGLSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //EditText editBox = new EditText(getContext());
        //editBox.setText("boop");

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




