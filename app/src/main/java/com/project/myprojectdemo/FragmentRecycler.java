package com.project.myprojectdemo;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRecycler extends Fragment {
    private RecyclerView viewR;
    private ArrayList<String> titles, passwords;
    private PasswordDB db;
    private  ArrayList<Password> password;
    static MyAdapter myAdapter;
    private FragmentDialog fragmentDialog;
    private DBService dbService;
    boolean isBound = false;
    private Button addPassword;
    public FragmentRecycler() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler,container,false);
        viewR = (RecyclerView) v.findViewById(R.id.recyclerView);
        addPassword = v.findViewById(R.id.addPassword);
        db = new PasswordDB(v.getContext());
        titles = new ArrayList<>();
        passwords = new ArrayList<>();
        password = MyAdapter.password;  // service yardimiyla veritabani getirebiliyoruz
        password = db.getPasswordObject();
        myAdapter = new MyAdapter(v.getContext(),password,getFragmentManager());

        fragmentDialog  = new FragmentDialog(getContext(),myAdapter,1);

        addPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentDialog.show(getFragmentManager(), "New Password");
            }
        });
        // Recycler view initialize

        viewR.setAdapter(myAdapter);
        viewR.setLayoutManager(new LinearLayoutManager(v.getContext()));

        // Inflate the layout for this fragment
        return v;
    }
    // serviec connection setup
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
    ///

}
