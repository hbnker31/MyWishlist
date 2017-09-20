package com.example.hemant.mywishlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import data.dataBaseHandler;
import model.MyWish;

public class MainActivity extends AppCompatActivity {
    private EditText title,content;
    private Button save;
    private dataBaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dba= new dataBaseHandler(MainActivity.this);
        title =(EditText)findViewById(R.id.Edittitle);
        content=(EditText)findViewById(R.id.Editcontent);
        save=(Button)findViewById(R.id.SaveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTODB();
            }
        });
    }

    private void saveTODB() {
        MyWish wish=new MyWish();
        wish.setTitle(title.getText().toString());
        wish.setContent(content.getText().toString());

        dba.AddWishes(wish);
        dba.close();

        title.setText("");
        content.setText("");

        Intent i=new Intent(MainActivity.this,DisplayWishActivity.class);
        startActivity(i);


    }

}
