package com.example.zubrein.final_thesis_poject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SensorButton extends AppCompatActivity {

    Button accelerometer,gyroscope;
    Button light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensorbutton);

        accelerometer = findViewById(R.id.accelerometer);
        gyroscope = findViewById(R.id.gyroscope);
        light = findViewById(R.id.light);

        accelerometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SensorButton.this,Show_list_three.class);
                intent.putExtra("name","Accelerometer");
                startActivity(intent);
            }
        });
        gyroscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SensorButton.this,Show_list_three.class);
                intent.putExtra("name","Gyroscope");
                startActivity(intent);
            }
        });
        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SensorButton.this,Show_list_one.class);
                intent.putExtra("name","Light");
                startActivity(intent);
            }
        });





    }
}
