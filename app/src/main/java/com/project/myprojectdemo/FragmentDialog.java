package com.project.myprojectdemo;


import androidx.appcompat.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDialog extends DialogFragment  {



    private DBService service;
    private MyAdapter myAdapter;
    private Context ctx;
    private int i;
    private Button cancelPassword,okPassword,save,copy, cancelCopy, goTo,cancel;
    private EditText passwordTitle,passwordEditText,passEditText,titleEditText,uri;
    private PasswordDB db;
    private Intent intent;


    public FragmentDialog(Context c , MyAdapter myAdapter, int i
    ) {
        // Required empty public constructor
        this.ctx = c;
        this.myAdapter = myAdapter;
        this.i = i;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(getContext(),DBService.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intent    intent = new Intent(getActivity(),DBService.class);
        db = new PasswordDB(getContext());

        getActivity().startService(intent);
        if(i == 1){
            // iki fragment olusturduk  i == 1 ise fragment_addpassword'u çağrılır
            View v = inflater.inflate(R.layout.fragment_addpassword,container,false);
            cancelPassword = v.findViewById(R.id.cancelPassword);
            okPassword = v.findViewById(R.id.okPassword);
            passwordTitle = v.findViewById(R.id.passwordTitle);
            passwordEditText = v.findViewById(R.id.passwordEditText);

            cancelPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            okPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!passwordTitle.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()){
//                        service = new DBService(ctx);
                       db.insertData(passwordTitle.getText().toString(),passwordEditText.getText().toString());
                        myAdapter.setData(db.getPasswordObject());
                        passwordTitle.setText("");
                        passwordEditText.setText("");
                        dismiss();
                    }else {
                        Toast.makeText(v.getContext(), "Please fill in the boxes", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            return v;

        }
       else if(i == 2){
           // i == 2  ıse fragment_edit'i çağrılır
            View v = inflater.inflate(R.layout.fragment_edit,container,false);
            // layoutumuzun elemanları initialize ettik
            save = v.findViewById(R.id.save);
            copy = v.findViewById(R.id.copy);
            cancel = v.findViewById(R.id.cancel);
            titleEditText = v.findViewById(R.id.titleEditText);
            passEditText = v.findViewById(R.id.passEditText);
            db = new PasswordDB(getContext());

                titleEditText.setText(db.getPasswordObject().get(myAdapter.getSelectedPos()).getTitle());
                passEditText.setText(db.getPasswordObject().get(myAdapter.getSelectedPos()).getPassword());


            // butonların onClick listener etkinleştirmek
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!titleEditText.getText().toString().isEmpty() && !passEditText.getText().toString().isEmpty()){
                        update();
                        dismiss();
                    }else {
                        Toast.makeText(v.getContext(), "Please fill in the boxes", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!titleEditText.getText().toString().isEmpty() && !passEditText.getText().toString().isEmpty()) {
                        copyToClip();
                    }else{
                        Toast.makeText(v.getContext(), "Please fill in the boxes", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return v;

        } else{
            View v = inflater.inflate(R.layout.fragment_copy,container,false);
            cancelCopy = v.findViewById(R.id.cancelCopy);
            goTo = v.findViewById(R.id.goTo);
            uri =v.findViewById(R.id.uri);
            copyToClip();
            goTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        String url = uri.getText().toString();
                        if (!url.startsWith("http://") && !url.startsWith("https://"))
                            url = "http://" + url;
                        if(!uri.getText().toString().isEmpty()){

                                Intent intent1 = new Intent();
                                intent1.setAction("com.project.own.MyReceiver");
                                intent1.addCategory("android.intent.category.DEFAULT");
                                intent1.putExtra("uri",url);
                                ctx.sendBroadcast(intent1);
                        }else{
                            Toast.makeText(v.getContext(), "Please fill in the boxes", Toast.LENGTH_SHORT).show();
                        }



                }
            });
            cancelCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            return v;
        }

    }
    public void copyToClip(){
        Password p = db.getPasswordObject().get(myAdapter.getSelectedPos());
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Password", p.getPassword());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getContext(), "Copied To Clipboard", Toast.LENGTH_SHORT).show();

    }
    public void update(){
        Password p = db.getPasswordObject().get(myAdapter.getSelectedPos());
        db.update(p.getId(),titleEditText.getText().toString(),passEditText.getText().toString());
        myAdapter.setData(db.getPasswordObject());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(R.layout.fragment_addpassword);
        alert.setCancelable(false);
        return  alert.show();
    }
}
