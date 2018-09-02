package com.example.zubrein.final_thesis_poject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HumanActivityGetDataButtons extends AppCompatActivity {

    private  int time = 000;

    String mHour, mMin, mSec;
    String dt;
    //DatePicker
    Calendar cal;
    SimpleDateFormat datte;
    String token;
    String mName ;
    long mil, milis;
    int second, minute, hour;

    Button walking,upstairs,downstairs,sitting,standing,lying,
            jogging,writting,typing, elevatorDown,sittingInToilet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humanactivitybuttons);

        cal = Calendar.getInstance();
        datte = new SimpleDateFormat("dd-MM");
        dt = datte.format(cal.getTime());

        walking = findViewById(R.id.walking);
        upstairs = findViewById(R.id.upstairs);
        downstairs = findViewById(R.id.downstairs);
        sitting = findViewById(R.id.sitting);
        standing = findViewById(R.id.standing);
        lying = findViewById(R.id.lying);
        sittingInToilet = findViewById(R.id.sittingInToilet);
        jogging = findViewById(R.id.jogging);
        writting = findViewById(R.id.writting);
        typing = findViewById(R.id.typing);




        walking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(HumanActivityGetDataButtons.this,SensorService.class);
                        intent.putExtra("token","1");
                        token = "1";
                        mName = "Walking";
                        startService(intent);
                    }
                }, time);

            }
        });
        upstairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(HumanActivityGetDataButtons.this,SensorService.class);
                        intent.putExtra("token","2");
                        token = "2";
                        mName = "Upstairs";
                        startService(intent);
                    }
                }, time);
            }
        });
        downstairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(HumanActivityGetDataButtons.this,SensorService.class);
                        intent.putExtra("token","3");
                        token = "3";
                        mName = "Downstairs";
                        startService(intent);
                    }
                }, time);
            }
        });
        sitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(HumanActivityGetDataButtons.this,SensorService.class);
                        intent.putExtra("token","4");
                        token = "4";
                        mName = "Sitting";
                        startService(intent);
                    }
                }, time);
            }
        });
        standing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(HumanActivityGetDataButtons.this,SensorService.class);
                        intent.putExtra("token","5");
                        token = "5";
                        mName = "Standing";
                        startService(intent);
                    }
                }, time);
            }
        });

        lying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(HumanActivityGetDataButtons.this,SensorService.class);
                        intent.putExtra("token","6");
                        token = "6";
                        mName = "Lying";
                        startService(intent);
                    }
                }, time);
            }
        });
        sittingInToilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(HumanActivityGetDataButtons.this,SensorService.class);
                        intent.putExtra("token","7");
                        token = "7";
                        mName = "Sitting In Toilet";
                        startService(intent);
                    }
                }, time);
            }
        });
        jogging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(HumanActivityGetDataButtons.this,SensorService.class);
                        intent.putExtra("token","8");
                        token = "8";
                        mName = "Jogging";
                        startService(intent);
                    }
                }, time);
            }
        });

        writting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(HumanActivityGetDataButtons.this,SensorService.class);
                        intent.putExtra("token","9");
                        token = "9";
                        mName = "Writting";
                        startService(intent);
                    }
                }, time);
            }
        });
        typing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(HumanActivityGetDataButtons.this,SensorService.class);
                        intent.putExtra("token","10");
                        token = "10";
                        mName = "Typing";
                        startService(intent);
                    }
                }, time);
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            Intent intent = new Intent(HumanActivityGetDataButtons.this, SensorService.class);
            stopService(intent);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
