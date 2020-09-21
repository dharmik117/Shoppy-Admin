package com.example.shoppyadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.airbnb.lottie.animation.content.Content;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import Model.Requests;
import ViewHolder.RequestViewHolder;

public class View_Requests extends AppCompatActivity {

    private DatabaseReference RequestsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__requests);

        RequestsRef = FirebaseDatabase.getInstance().getReference().child("Shop_List");
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Requests> options =
                new FirebaseRecyclerOptions.Builder<Requests>()
                .setQuery(RequestsRef,Requests.class)
                .build();

        FirebaseRecyclerAdapter<Requests, RequestViewHolder> adapter =
                new FirebaseRecyclerAdapter<Requests, RequestViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull RequestViewHolder requestViewHolder, int i, @NonNull final Requests requests)
                    {
                            requestViewHolder.txtshopname.setText(requests.getShopname());
                            requestViewHolder.txtshopdescryption.setText(requests.getDescryption());
                            requestViewHolder.txtshopcategory.setText(requests.getCategory());
                            requestViewHolder.txtpermission.setText(requests.getPermission());


                        Picasso.get().load(requests.getImage()).into(requestViewHolder.imageView);

                        requestViewHolder.itemView.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(View_Requests.this,Give_Permission.class);
                                intent.putExtra("pid",requests.getShopid());
                                startActivity(intent);
                            }
                        });
                    }


                    @NonNull
                    @Override
                    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design,parent,false);
                       RequestViewHolder holder = new RequestViewHolder(view);
                       return holder;
                    }
                };

                recyclerView.setAdapter(adapter);
                adapter.startListening();

    }
}
