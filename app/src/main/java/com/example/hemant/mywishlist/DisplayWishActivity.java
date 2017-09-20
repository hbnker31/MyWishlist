package com.example.hemant.mywishlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import data.dataBaseHandler;
import model.MyWish;

public class DisplayWishActivity extends AppCompatActivity {
    private dataBaseHandler dba;
    private ArrayList<MyWish> dbwishes=new ArrayList<>();
    private WishAdapter wishAdapter;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_wish);
        list=(ListView)findViewById(R.id.listView);

        refreshdata();
    }

    private void refreshdata() {
        dbwishes.clear();
        dba= new dataBaseHandler(getApplicationContext());
        ArrayList<MyWish> wishFromDB=dba.getWishes();
        for(int i=0;i<wishFromDB.size();i++)
        {
            String title=wishFromDB.get(i).getTitle();
            String content=wishFromDB.get(i).getContent();
            String date=wishFromDB.get(i).getRecordDate();
            int mid=wishFromDB.get(i).getID();

            MyWish mwish=new MyWish();
            mwish.setContent(content);
            mwish.setTitle(title);
            mwish.setRecordDate(date);
            mwish.setID(mid);
            dbwishes.add(mwish);

        }
        dba.close();
        wishAdapter=new WishAdapter(DisplayWishActivity.this,R.layout.list_row,dbwishes);
        list.setAdapter(wishAdapter);
        wishAdapter.notifyDataSetChanged();
    }
    public class WishAdapter extends ArrayAdapter<MyWish>{
        Activity act;
        int LayooutResource;
        MyWish wish;
        ArrayList<MyWish> mdata=new ArrayList<>();

        public WishAdapter(Activity activity,int resource,ArrayList<MyWish> objects) {
            super(activity, resource, objects);
            act=activity;
            LayooutResource=resource;
            mdata=objects;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mdata.size();
        }

        @Nullable
        @Override
        public MyWish getItem(int position) {
            return mdata.get(position);
        }

        @Override
        public int getPosition(@Nullable MyWish item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row=convertView;
            ViewHolder holder=null;
            if(row==null||(row.getTag()==null))
            {
                LayoutInflater inflater=LayoutInflater.from(act);
                row=inflater.inflate(LayooutResource,null);
                holder=new ViewHolder();
                holder.mtitle=(TextView)row.findViewById(R.id.name);
                holder.mdate=(TextView)row.findViewById(R.id.datetext);
                row.setTag(holder);

            }
            else
            {
                holder= (ViewHolder) row.getTag();
            }
            holder.mywish=getItem(position);
            holder.mtitle.setText(holder.mywish.getTitle());
            holder.mdate.setText(holder.mywish.getRecordDate());
            final ViewHolder finalHolder = holder;
            holder.mtitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String text= finalHolder.mywish.getContent().toString();
                    String date=finalHolder.mywish.getRecordDate().toString();
                    String title=finalHolder.mywish.getTitle().toString();
                    int mid=finalHolder.mywish.getID();
                    Intent i=new Intent(DisplayWishActivity.this,WishDetailActivity.class);
                    i.putExtra("content",text);
                    i.putExtra("date",date);
                    i.putExtra("title",title);
                    i.putExtra("ID",mid);
                    startActivity(i);
                }
            });


            return row;
        }
    }
    class ViewHolder{
        MyWish mywish;
        TextView mtitle;
        TextView mId;
        TextView mcontent;
        TextView mdate;

    }
}
