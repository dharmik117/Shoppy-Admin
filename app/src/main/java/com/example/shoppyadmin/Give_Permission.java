package com.example.shoppyadmin;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import Model.Requests;

public class Give_Permission extends AppCompatActivity {

    private TextView email, shopname;
    private Switch pswitch;
    private String productID = "";
    private DatabaseReference RequestsRef;
    private String Permission,value;
    private String userid;
    private FirebaseUser firebaseuser;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give__permission);

        firebaseuser = FirebaseAuth.getInstance().getCurrentUser();

        firebaseAuth = FirebaseAuth.getInstance();
        RequestsRef = FirebaseDatabase.getInstance().getReference().child("Shop_List");

        email = (TextView) findViewById(R.id.email);
        pswitch = (Switch) findViewById(R.id.pswitch);
        shopname = (TextView) findViewById(R.id.shopname);

        productID = getIntent().getStringExtra("pid");
        getRequestsDetails(productID);


        pswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Update(productID);
                } else {
                    Update2(productID);
                }
            }

        });

        RequestsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String permission = dataSnapshot.child("permission").getValue().toString().trim();

                if(permission.equals("True"))
                {
                    pswitch.setChecked(true);
                }
                else
                    {
                        pswitch.setChecked(false);
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void Update2(String productID)
    {
        Permission = pswitch.getTextOff().toString();

        HashMap Usermap = new HashMap();
        Usermap.put("permission",Permission);
        RequestsRef.child(productID).updateChildren(Usermap);
    }

    private void Update(String productID)
    {
        Permission = pswitch.getTextOn().toString();

        HashMap Usermap = new HashMap();
        Usermap.put("permission",Permission);
        RequestsRef.child(productID).updateChildren(Usermap);

    }


    private void getRequestsDetails(String productID)
    {
        DatabaseReference requestsref = FirebaseDatabase.getInstance().getReference().child("Shop_List");

        requestsref.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    Requests requests = dataSnapshot.getValue(Requests.class);
                    shopname.setText(requests.getShopname());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

    }
}
