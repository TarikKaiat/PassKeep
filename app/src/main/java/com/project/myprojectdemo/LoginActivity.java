package com.project.myprojectdemo;


import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginActivity extends AppCompatActivity {
    private Button login,reset;
    private EditText password;
    private Button fingerprint;
    private String pass;
    private DBService dbService;
    boolean isBound = false;
    private AlertDialog.Builder alert;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DBService.LocalBinder localBinder = (DBService.LocalBinder) service;
            dbService = localBinder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        SharedPreferences pref = getSharedPreferences(SetupActivity.FILE,Context.MODE_PRIVATE);
        password = findViewById(R.id.password1);
        fingerprint = findViewById(R.id.fingerprint1);
        pass = pref.getString(SetupActivity.PASS_KEY,"");
        login = findViewById(R.id.login);
        // service basliyor
        Intent in = new Intent(getApplication(),DBService.class);
        getApplication().bindService(in,serviceConnection, Context.BIND_AUTO_CREATE);
        ////
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals(pass) || FingerprintHandler.fingerprintIsDone){
                    password.setText("");
                    MyAdapter.password = dbService.getItem();

                    // Explicit Intent
                    Intent intent = new Intent(LoginActivity.this,ListActivity.class);
                    startActivity(intent);
                    //////
                }else{
                    Toast.makeText(v.getContext(), "Wrong Password Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alert =  new AlertDialog.Builder(LoginActivity.this)
                .setView(R.layout.fingerprint_dialog)
                .setTitle("Fingerprint")
                .setMessage("Place your finger on the sensor.")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if(FingerprintHandler.fingerprintIsDone) {
                    Intent intent = new Intent(LoginActivity.this,ListActivity.class);
                    startActivity(intent);
                    FingerprintHandler.fingerprintIsDone = false;
                }
            }
        });
        final SetupActivity f = new SetupActivity();
        fingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = alert.show();
                alertDialog.setCancelable(false);
               BiometricUtils.check(alertDialog,LoginActivity.this);

            }
        });
}

    @Override
    protected void onPause() {
        super.onPause();
        ActivityCompat.finishAffinity(LoginActivity.this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences pref = getSharedPreferences(SetupActivity.FILE,Context.MODE_PRIVATE);
        if(BiometricUtils.isHardwareSupported(this) && pref.getBoolean("fingerprint",false))
            fingerprint.setVisibility(View.VISIBLE);
        else
            fingerprint.setVisibility(View.GONE);

    }


}
