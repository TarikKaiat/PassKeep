package com.project.myprojectdemo;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetupActivity extends AppCompatActivity {
    private Button setup;
    private Button fingerprint;
    public static boolean setupIsDone;
    final static  String PASS_KEY = "com.project.myprojectdemo.passkey";
    final static  String NAME_KEY = "com.project.myprojectdemo.namekey";
    final static String DONE_KEY = "com.project.myprojectdemo.donekey";
    final static String FILE = "com.project.myprojectdemo.filePref";
    private EditText name,password;

    AlertDialog.Builder alert;


    @TargetApi(Build.VERSION_CODES.P)

    @Override
    protected void onCreate(
            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setup);

        fingerprint = findViewById(R.id.fingerprint);
        setup = findViewById(R.id.setup);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);


         alert =  new AlertDialog.Builder(SetupActivity.this)
                .setView(R.layout.fingerprint_dialog)
                .setTitle("Fingerprint")
                .setMessage("Place your finger on the sensor.")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        fingerprint.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(BiometricUtils.fingerprintChecked){
                    BiometricUtils.fingerprintChecked = false;
                    fingerprint.setBackgroundColor(Color.GRAY);

                }else{
                    fingerprint.setBackgroundResource(R.color.colorPrimary);

                    BiometricUtils.fingerprintChecked = true;
                }

            }
        });
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetupActivity.this,ListActivity.class);
                if (!name.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    setupIsDone = true;
                    store();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });


    }

    // SharedPreferences
    private void store(){
        SharedPreferences pref = getSharedPreferences(FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(NAME_KEY,name.getText().toString());
        edit.putString(PASS_KEY,password.getText().toString());
        edit.putBoolean(DONE_KEY, setupIsDone);
        edit.putBoolean("fingerprint",BiometricUtils.fingerprintChecked);
        edit.commit();
    }

//


    @Override
    protected void onResume() {
        super.onResume();
        if(BiometricUtils.isHardwareSupported(this))
            fingerprint.setVisibility(View.VISIBLE);
        else
            fingerprint.setVisibility(View.GONE);
    }
}
