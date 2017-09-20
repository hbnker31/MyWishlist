 package com.example.hemant.mywishlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.dataBaseHandler;

public class WishDetailActivity extends AppCompatActivity {
    private TextView title,date,content;
    private Button deleteB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_detail);
        title=(TextView)findViewById(R.id.Title);
        date=(TextView)findViewById(R.id.wishdate);
        content=(TextView)findViewById(R.id.wishDetail);
        deleteB=(Button)findViewById(R.id.delete);
        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            title.setText(extras.getString("title"));
            date.setText("Created on: "+extras.getString("date"));
            content.setText("\""+extras.getString("content")+"\".");
            final int id=extras.getInt("ID");
            deleteB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataBaseHandler dba=new dataBaseHandler(getApplicationContext());
                    dba.deleteWish(id);
                    Toast.makeText(getApplicationContext(),"Wish Deleted!!!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(WishDetailActivity.this,DisplayWishActivity.class));
                }
            });



        }
    }
}
