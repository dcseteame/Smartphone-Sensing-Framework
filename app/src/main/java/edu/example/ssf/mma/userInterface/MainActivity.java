package edu.example.ssf.mma.userInterface;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.example.ssf.mma.D3SHttpClient;
import edu.example.ssf.mma.hardwareAdapter.HardwareFactory;
import edu.example.ssf.mma.timer.StateMachineHandler;

public class MainActivity extends AppCompatActivity {

    //Init HW-Factory
    HardwareFactory hardwareFactory;

    //Permissions Android
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    /** Declare StateMachine */
    private StateMachineHandler stateMachineHandler;


    //UI
    TextView textViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this, new  String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_MULTIPLE_REQUEST);

        startD3S();

        textViewState = findViewById(R.id.textViewState);
    }

    private void startD3S() {
        hardwareFactory = new HardwareFactory(MainActivity.this);
        stateMachineHandler = new StateMachineHandler(MainActivity.this);

        hardwareFactory.hwAcc.start();
        hardwareFactory.hwGPS.initGPS(MainActivity.this);

        stateMachineHandler.startStateMachine();

        //initialize HTTP Client singleton with Context
        D3SHttpClient.getInstance(MainActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case
             PERMISSIONS_MULTIPLE_REQUEST:
                //
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    hardwareFactory = new HardwareFactory(this);
                    stateMachineHandler = new StateMachineHandler(this);
                } else {
                    // Do nothing
                }
        }
    }
}
