package com.example.shoppyadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import Model.Orders;

public class Main_Page extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button btnaddshop,btnrequestshop,btnallorder;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__page);

        btnaddshop = (Button) findViewById(R.id.btnaddshop);
        btnrequestshop = (Button) findViewById(R.id.btnrequests);
        btnallorder =(Button) findViewById(R.id.btnallorder);

        firebaseAuth = FirebaseAuth.getInstance();


        Orders orders = new Orders(id);

        btnrequestshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(Main_Page.this,View_Requests.class);
                startActivity(i);
            }
        });

        btnaddshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Main_Page.this,AdminBoard.class);
                startActivity(i);
            }
        });

        btnallorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Main_Page.this,View_AllOrder.class);
                startActivity(i);
            }
        });

    }
}
