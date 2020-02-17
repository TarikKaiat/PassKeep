package com.project.myprojectdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class ListActivity extends AppCompatActivity {
    private Button button;
    private TabLayout myTab;
    private  ViewPager myPager;
    private PasswordDB passwordDB;
    private FragmentDialog fragmentDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        button = findViewById(R.id.menuButton);
        passwordDB = new PasswordDB(this);
//        this.getBaseContext().deleteDatabase("Project");
        myTab = (TabLayout) findViewById(R.id.myTab);
        myPager = (ViewPager) findViewById(R.id.myPager);
        myPager.setAdapter(new myPagerAdapter(getSupportFragmentManager()));
        myTab.setupWithViewPager(myPager);
        myTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                myPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void menuOpen(View view) {
        PopupMenu menu = new PopupMenu(this,view);
        MenuInflater inflater = menu.getMenuInflater();

        inflater.inflate(R.menu.options_menu,menu.getMenu());
        menu.show();

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getTitle().toString().equals("Delete") && FragmentRecycler.myAdapter.getSelectedPos() >=0) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ListActivity.this);
                    alert.setTitle("Deleting Password").setMessage("Are you sure you want to delete this password?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FragmentRecycler.myAdapter.removeItemP();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog =alert.show();

                    return true;
                }
                else if(item.getTitle().toString().equals("Edit")  && FragmentRecycler.myAdapter.getSelectedPos() >=0) {
                    fragmentDialog = new FragmentDialog(ListActivity.this,FragmentRecycler.myAdapter,2);
                    fragmentDialog.show(getSupportFragmentManager(),"Edit Password");
                    return true;
                }
                else{
                    Toast.makeText(ListActivity.this, "Please select a password", Toast.LENGTH_SHORT).show();

                    return true;
                }
            }
        });
    }
}
    class myPagerAdapter extends FragmentPagerAdapter
    {
        private String data[] = {"Passwords","Settings"};
        public myPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
           if(position == 0)
                return new FragmentRecycler();
            if(position == 1)
                return new FragmentSettings();
            return null;

        }

        @Override
        public int getCount() {
            return data.length;
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return data[position];
        }
}
