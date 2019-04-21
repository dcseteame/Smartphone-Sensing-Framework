package edu.example.ssf.mma.userInterface;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

import edu.example.ssf.mma.D3SHttpClient;
import edu.example.ssf.mma.data.CurrentTickData;
import edu.example.ssf.mma.data.LocalDataStorage;
import edu.example.ssf.mma.data.MathCalculations;
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
    TextView textViewQuacke;
    TextView textViewData;

    Runnable updater;
    void updateUi() {
        textViewState =(TextView) findViewById(R.id.textViewState);
        final Handler timerHandler = new Handler();

        updater = new Runnable() {
            @Override
            public void run() {
                textViewState.setText(LocalDataStorage.getStateLabel());
                textViewQuacke.setText(LocalDataStorage.getEarthquake());


                String measuredData = String.format(Locale.UK, "AccX: %.2f - AccY: %.2f - AccZ: %.2f \n" +
                                "GyrX: %.2f - GyrY: %.2f - GyrZ: %.2f",
                        CurrentTickData.accX,
                        CurrentTickData.accY,
                        CurrentTickData.accZ,
                        CurrentTickData.rotationX,
                        CurrentTickData.rotationY,
                        CurrentTickData.rotationZ);

                textViewData.setText(measuredData);
                timerHandler.postDelayed(updater,1000);
            }
        };
        timerHandler.post(updater);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this, new  String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_MULTIPLE_REQUEST);
    }

    private void startD3S() {
        hardwareFactory = new HardwareFactory(MainActivity.this);
        stateMachineHandler = new StateMachineHandler(MainActivity.this);

        hardwareFactory.hwAcc.start();
        hardwareFactory.hwGPS.initGPS(MainActivity.this);
        hardwareFactory.hwGPS.start();
        hardwareFactory.hwGyro.initGyro(MainActivity.this);
        hardwareFactory.hwGyro.start();

        textViewState = findViewById(R.id.textViewState);
        textViewData = findViewById(R.id.textViewData);
        textViewQuacke = findViewById(R.id.textViewEarthQuacke);

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
                    startD3S();
                    updateUi();
                } else {
                    // Do nothing
                }
        }
    }
}
