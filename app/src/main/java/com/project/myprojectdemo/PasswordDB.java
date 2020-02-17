package com.project.myprojectdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class PasswordDB extends SQLiteOpenHelper {
    static final private String DB_NAME = "Project";
    static final private String DB_TABLE = "Password";
    static final private int DB_VR = 1;
    private Password password;
    public int i;
    private Context ctx;
    private SQLiteDatabase mydb;

    public PasswordDB(Context c) {
        super(c, DB_NAME, null, DB_VR);
        ctx = c;
        password = new Password();

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DB_TABLE + "(id integer Primary Key autoincrement,title text,password text );");
        Log.i("Database", "TABLE created.");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DB_TABLE);
        onCreate(db);
    }

    public void insertData(String s1, String s2) {
        mydb = getWritableDatabase();
        try {
            mydb.execSQL("insert into " + DB_TABLE + "(title,password) values('" + s1 + "','" + s2 + "' )");

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("Database", "problem inserting");
        }

        Toast.makeText(ctx, "Inserted", Toast.LENGTH_SHORT).show();
    }


    public ArrayList<Password> getPasswordObject() {
        mydb = getReadableDatabase();
        ArrayList<Password> array = new ArrayList<>();
        Cursor cr;

        try {
            cr = mydb.rawQuery("select id,title,password from " + DB_TABLE + " order by title asc ;", null);

            while (cr.moveToNext()) {
                Password password = new Password();
                password.setId(Integer.parseInt(cr.getString(0)));
                password.setTitle( cr.getString(1));
                password.setPassword( cr.getString(2));
                array.add(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("Database", "problem getting" + e.getMessage());
        }


        return array;

    }
    public void update(int i,String s1, String s2){
        mydb = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",s1);
        contentValues.put("password",s2);
        String[] s = {String.valueOf(i)};
        try {
            mydb.execSQL("update " + DB_TABLE + " set title = '" + s1 + "', password = '" + s2 + "' where id = " + i);

        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("Database", "problem inserting");
        }

    }

    public boolean delete(int i){
        mydb = getWritableDatabase();

        try {

            mydb.execSQL("delete from " + DB_TABLE +" where id = " + i );
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("Database", "problem inserting");
        }

        Toast.makeText(ctx, "Deleted", Toast.LENGTH_SHORT).show();
        return true;
    }

}
