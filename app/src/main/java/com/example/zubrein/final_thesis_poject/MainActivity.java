package com.example.zubrein.final_thesis_poject;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Button btnStart,btnStop,btnNext,btnSignout,btnShow,btnSave;
    EditText et;
    SensorManager sensorManager;
    Sensor accelerometer,gyroscope,proximity,light,compass,barometer,temperature,humidity;
    boolean acc = true , gyr = true , internet = true, prox = true , ligh = true, baro = true, comp = true;
    LinearLayout layout;
    String number,mNumber="",mStatus;
    int length;
    AlertDialog alertDialog;

    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,};

    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;

    SharedPreferences sharePreferenceRead;
    SharedPreferences.Editor sharedPreferenceEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SharedPreference
        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);

        ///Initializing objects
        btnStart = (Button) findViewById(R.id.start);
        btnSave = (Button) findViewById(R.id.save);
        btnStop = (Button) findViewById(R.id.stop);
        btnNext = (Button) findViewById(R.id.next);
        btnSignout = (Button) findViewById(R.id.signout);
        btnShow = (Button) findViewById(R.id.show);
        et = (EditText) findViewById(R.id.phonr_number);
        layout = (LinearLayout) findViewById(R.id.layout);
        


        sharePreferenceRead = getSharedPreferences("db_exists", MODE_PRIVATE);
        sharedPreferenceEditor = sharePreferenceRead.edit();

        ///Sqlite Database
        int isExists = sharePreferenceRead.getInt("exists", 0);
        if(isExists==0) {
            createDatabase01();
            createDatabase02();
            //createDatabase04();
        }

        sensorInitializer();  //Initializing sensors




        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                permission();

            }


        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SensorService.class);
                stopService(intent);
            }
        });
        btnSignout.setText("Clear Database");
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Are you sure, You want to clear all databases ?");
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_PRIVATE, null);
                                        db.execSQL("delete from Accelerometer");
                                        db.execSQL("delete from Gyroscope");
                                        db.close();
                                        Toast.makeText(MainActivity.this,"Cleared",Toast.LENGTH_LONG).show();
                                    }
                                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.cancel();
                    }
                });

                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SensorButton.class);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HumanActivityShowButtons.class);
                startActivity(intent);

            }
        });
        
        
    }

    public void permission(){
        //Permission
        if(ActivityCompat.checkSelfPermission(MainActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MainActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permissionsRequired[1])){
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(MainActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionsRequired[0],false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(MainActivity.this, "Go to Permissions to Grant  Camera and Location", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }  else {
                //just request the permission
                ActivityCompat.requestPermissions(MainActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
            }

            Toast.makeText(MainActivity.this, "Permissions Required", Toast.LENGTH_LONG).show();

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0],true);
            editor.putBoolean(permissionsRequired[1],true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.

            Intent intent = new Intent(MainActivity.this, HumanActivityGetDataButtons.class);
            startActivity(intent);

        }
    }

    public void sensorInitializer(){
        //Initializing sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Accelerometer
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(accelerometer != null){
            sensorManager.registerListener(MainActivity.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            acc = false;
            Toast.makeText(MainActivity.this,"Accelerometer not found",Toast.LENGTH_SHORT).show();
        }
        //Gyroscope
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(gyroscope != null){
            sensorManager.registerListener(MainActivity.this,gyroscope,SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            gyr = false;
            Toast.makeText(MainActivity.this,"Gyroscope not found",Toast.LENGTH_SHORT).show();
        }
        //Light
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(light != null){
            sensorManager.registerListener(MainActivity.this,light,SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            ligh = false;
            Toast.makeText(MainActivity.this,"Light senseor not found",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void createDatabase01() {
        SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_PRIVATE,null);

        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + "Accelerometer"
                + " (date VARCHAR, time VARCHAR, Accelerometer_X VARCHAR, Accelerometer_Y VARCHAR, Accelerometer_Z VARCHAR, TOKEN VARCHAR);";
        db.execSQL(createTableQuery);
        db.close();

        Toast.makeText(getApplicationContext(), "Table Accelerometer Created", Toast.LENGTH_LONG).show();
        sharedPreferenceEditor.putInt("exists", 1);
        sharedPreferenceEditor.commit();
    }
    public void createDatabase02() {
        SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_PRIVATE,null);

        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + "Gyroscope"
                + " (date VARCHAR, time VARCHAR, Gyroscope_X VARCHAR, Gyroscope_Y VARCHAR, Gyroscope_Z VARCHAR, TOKEN VARCHAR);";
        db.execSQL(createTableQuery);
        db.close();

        Toast.makeText(getApplicationContext(), "Table Gyroscope Created", Toast.LENGTH_LONG).show();
        sharedPreferenceEditor.putInt("exists", 1);
        sharedPreferenceEditor.commit();
    }
    public void createDatabase04() {
        SQLiteDatabase db = openOrCreateDatabase("MyDB", MODE_PRIVATE,null);

        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + "Light"
                + " (date VARCHAR, time VARCHAR, Light_X VARCHAR, TOKEN VARCHAR);";
        db.execSQL(createTableQuery);
        db.close();

        Toast.makeText(getApplicationContext(), "Table Light Created", Toast.LENGTH_LONG).show();
        sharedPreferenceEditor.putInt("exists", 1);
        sharedPreferenceEditor.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                
            }
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            Intent intent = new Intent(MainActivity.this, SensorService.class);
            stopService(intent);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


}
