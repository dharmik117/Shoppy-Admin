package com.example.shoppyadmin;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Model.Orders;
import ViewHolder.Order_View_Holder;

public class View_AllOrder extends AppCompatActivity {

    private DatabaseReference ordersref;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private FirebaseAuth firebaseauth;
    private String currentuserid;
    private FirebaseUser firebaseUser;
    private DatabaseReference profileuserref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__all_order);

        ordersref = FirebaseDatabase.getInstance().getReference().child("Orders").child("For_Admin");

        recyclerView = findViewById(R.id.aorv);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        firebaseauth = FirebaseAuth.getInstance();
        currentuserid = firebaseauth.getCurrentUser().getUid();
        profileuserref = FirebaseDatabase.getInstance().getReference().child("User").child(currentuserid);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Orders> options =
                new FirebaseRecyclerOptions.Builder<Orders>()
                .setQuery(ordersref,Orders.class)
                .build();


        FirebaseRecyclerAdapter<Orders, Order_View_Holder> adapter =
                new FirebaseRecyclerAdapter<Orders, Order_View_Holder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull Order_View_Holder order_view_holder, int i, @NonNull final Orders orders)
                    {
                        order_view_holder.textusername.setText(orders.getName());
                        order_view_holder.textphone.setText(orders.getPhone());
                        order_view_holder.textprice.setText(orders.getTotalamount());
                        order_view_holder.textcity.setText(orders.getAddress() + orders.getCity() );
                        order_view_holder.textdatetime.setText(orders.getDateandtime());

                        order_view_holder.showallproduct.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(View_AllOrder.this,All_Products.class);
                                intent.putExtra("uidp",orders.getUserId());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public Order_View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_design,parent,false);
                        Order_View_Holder holder = new Order_View_Holder(view);
                        return  holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
