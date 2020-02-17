package com.project.myprojectdemo;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSettings extends Fragment {

    private Button reset,signout;
    private CheckBox checkBox;
    public FragmentSettings() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_settings,container,false);
        checkBox = v.findViewById(R.id.checkbox);
        reset = v.findViewById(R.id.reset);
        signout = v.findViewById(R.id.sign_out);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                Intent intent = new Intent(getContext(),SetupActivity.class);
                startActivity(intent);
                if(checkBox.isChecked() )
                getContext().deleteDatabase("Project");
            }
        });
        // Inflate the layout for this fragment
        return v;
    }


    private void clear(){
        SharedPreferences pref = getActivity().getSharedPreferences(SetupActivity.FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.clear();
        edit.commit();
    }
}
