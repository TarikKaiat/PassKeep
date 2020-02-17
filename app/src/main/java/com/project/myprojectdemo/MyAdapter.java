package com.project.myprojectdemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private int selectedPos = RecyclerView.NO_POSITION;
    private Context c;
    public static  ArrayList<Password> password;
    private FragmentManager fragmentManager;
    private PasswordDB passwordDB;
    public MyAdapter(Context c, ArrayList<Password> password, FragmentManager fragmentManager){
        this.c = c;
        this.password = password;
        this.fragmentManager = fragmentManager;
        passwordDB = new PasswordDB(c);
    }


    @NonNull
    @Override
    public MyAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater myInf = LayoutInflater.from(c);
        View myView =  myInf.inflate(R.layout.my_list,parent,false);
        return new MyHolder(myView);
    }

    public void setData(ArrayList<Password> password){
        this.password.clear();
        this.password.addAll(password);
        notifyDataSetChanged();
    }
    public void show(){
         FragmentDialog frag = new FragmentDialog(c,this,3);
        frag.show(fragmentManager,"");
    }
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyHolder holder, int position) {
        holder.title.setText(this.password.get(position).getTitle());
        holder.letter.setText(String.valueOf(this.password.get(position).getTitle().charAt(0)));
        holder.password.setText(this.password.get(position).getPassword());
        final MyHolder hold = holder;
        Log.d("MyHolder" , String.valueOf(selectedPos));
        holder.itemView.setSelected(selectedPos == position);
        holder.setItemLongClickListener(new ItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int pos) {
                hold.setPos();
                show();
            }
        });
    }


    public int getSelectedPos(){
        return selectedPos;
    }

    public void removeItemP(){
        passwordDB.delete(password.get(selectedPos).getId());

        this.password.remove(selectedPos);
        notifyItemRemoved(selectedPos);

    }

    @Override
    public void onViewRecycled(@NonNull MyHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return password.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        TextView title, password,letter;
        ItemLongClickListener itemLongClickListener;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            password = (TextView) itemView.findViewById(R.id.passwordList);
            letter = (TextView) itemView.findViewById(R.id.letter);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Redraw the old selection and the new
                    notifyItemChanged(selectedPos);
                    selectedPos = getLayoutPosition();
                    notifyItemChanged(selectedPos);
                }
            });


        }
        public void setItemLongClickListener(ItemLongClickListener ic)
        {
            this.itemLongClickListener=ic;
        }

        public void setPos(){
            notifyItemChanged(selectedPos);
            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);
        }

        @Override
        public boolean onLongClick(View v) {
            this.itemLongClickListener.onItemLongClick(v,getLayoutPosition());
            return false;
        }
    }
}

//    public void setData(ArrayList<String> titles,ArrayList<String> passwords){
//        this.titles.clear();
//        this.passwords.clear();
//        this.titles.addAll(titles);
//        this.passwords.addAll(passwords);
//        notifyDataSetChanged();
//    }



//    @Override
//    public void onBindViewHolder(@NonNull MyAdapter.MyHolder holder, final int position) {
//        holder.title.setText(this.password.get(position).getTitle());
//        holder.letter.setText(String.valueOf(this.password.get(position).getTitle().charAt(0)));
//        holder.password.setText(this.password.get(position).getPassword());
//        final String title = holder.title.getText().toString();
//        final String pass = holder.password.getText().toString();
//
//        Log.d("MyHolder" , String.valueOf(selectedPos));
//        holder.itemView.setSelected(selectedPos == position);
//        hold= holder;
//        holder.setItemLongClickListener(new ItemLongClickListener() {
//            @Override
//            public void onItemLongClick(View v, int pos) {
//                hold.setPos(pos);
//            }
//        });
//            }