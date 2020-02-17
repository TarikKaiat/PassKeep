package com.project.myprojectdemo;

import android.annotation.TargetApi;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.M)
class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private Context context;
    public static boolean fingerprintIsDone;
    private AlertDialog alert;
    public FingerprintHandler(AlertDialog alert,Context context) {
        this.alert = alert;
        this.context = context;
    }
    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject,cancellationSignal,0,this,null);
    }
    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        Toast.makeText(context, "There was an error" + errString, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        Toast.makeText(context, "For help " + helpString, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        alert.cancel();
        fingerprintIsDone =true;
        Toast.makeText(context, "Succeed ", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(context, "Auth Failed" , Toast.LENGTH_SHORT).show();
    }
}
