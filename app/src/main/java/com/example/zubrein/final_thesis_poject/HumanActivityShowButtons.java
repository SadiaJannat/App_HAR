package com.example.zubrein.final_thesis_poject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HumanActivityShowButtons extends AppCompatActivity {

    Button walking,upstairs,downstairs,sitting,standing,lying,
            jogging,writting,typing,sittingInToilet;

    private  int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humanactivityshowbuttons);

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
                        Intent intent = new Intent(HumanActivityShowButtons.this,CatagoryShowList.class);
                        intent.putExtra("token1","Walking");
                        intent.putExtra("token2","1");
                        startActivity(intent);
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
                        Intent intent = new Intent(HumanActivityShowButtons.this,CatagoryShowList.class);
                        intent.putExtra("token1","Upstairs");
                        intent.putExtra("token2","2");
                        startActivity(intent);
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
                        Intent intent = new Intent(HumanActivityShowButtons.this,CatagoryShowList.class);
                        intent.putExtra("token1","Downstairs");
                        intent.putExtra("token2","3");
                        startActivity(intent);
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
                        Intent intent = new Intent(HumanActivityShowButtons.this,CatagoryShowList.class);
                        intent.putExtra("token1","Sitting");
                        intent.putExtra("token2","4");
                        startActivity(intent);
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
                        Intent intent = new Intent(HumanActivityShowButtons.this,CatagoryShowList.class);
                        intent.putExtra("token1","Standing");
                        intent.putExtra("token2","5");
                        startActivity(intent);
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
                        Intent intent = new Intent(HumanActivityShowButtons.this,CatagoryShowList.class);
                        intent.putExtra("token1","Lying");
                        intent.putExtra("token2","6");
                        startActivity(intent);
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
                        Intent intent = new Intent(HumanActivityShowButtons.this,CatagoryShowList.class);
                        intent.putExtra("token1","Sitting In Toilet");
                        intent.putExtra("token2","7");
                        startActivity(intent);
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
                        Intent intent = new Intent(HumanActivityShowButtons.this,CatagoryShowList.class);
                        intent.putExtra("token1","Jogging");
                        intent.putExtra("token2","8");
                        startActivity(intent);
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
                        Intent intent = new Intent(HumanActivityShowButtons.this,CatagoryShowList.class);
                        intent.putExtra("token1","Writting");
                        intent.putExtra("token2","9");
                        startActivity(intent);
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
                        Intent intent = new Intent(HumanActivityShowButtons.this,CatagoryShowList.class);
                        intent.putExtra("token1","Typing");
                        intent.putExtra("token2","10");
                        startActivity(intent);
                    }
                }, time);
            }
        });




    }
}
