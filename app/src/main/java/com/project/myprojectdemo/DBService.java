package com.project.myprojectdemo;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBService extends Service {
    private final IBinder binder = new LocalBinder();
    PasswordDB db = new PasswordDB(getBaseContext());
    public class LocalBinder extends Binder{
        DBService getService(){
            return  DBService.this;
        }
    }
    public DBService() {
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, " Stopped", Toast.LENGTH_SHORT).show();
        Log.d("Servicess","Service has stopped");

        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }
    public ArrayList<Password> getItem(){
        PasswordDB db = new PasswordDB(getApplication().getBaseContext());
        Log.d("DBService","Service has started");
        return db.getPasswordObject();

    }

}