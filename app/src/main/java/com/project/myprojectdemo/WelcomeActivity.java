package com.project.myprojectdemo;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeActivity extends AppCompatActivity {


    private Button button;
    private TextView nameTextView;
    boolean done;

    private Intent intent;
    private ImageView imageView;
    private Context c = this;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_welcome);
        button = (Button) findViewById(R.id.button);
        intent = new Intent(c, LoginActivity.class);
        nameTextView = findViewById(R.id.nameTextView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (done) {
                    intent = new Intent(c, LoginActivity.class);
                } else {
                    intent = new Intent(c, SetupActivity.class);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences(SetupActivity.FILE, Context.MODE_PRIVATE);

        String name1 = pref.getString(SetupActivity.NAME_KEY, "");

        done = pref.getBoolean(SetupActivity.DONE_KEY, false);
        nameTextView.setText(name1);

        new Thread(){
            @Override
            public void run() {
                if(getSharedPreferences(SetupActivity.FILE,MODE_PRIVATE).getBoolean(SetupActivity.DONE_KEY,false)){
                    try {
                        button.setVisibility(View.GONE);
                        sleep(800);
                        Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }
}