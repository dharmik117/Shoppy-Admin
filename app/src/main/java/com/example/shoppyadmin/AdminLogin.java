package com.example.shoppyadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity implements View.OnClickListener{


    private EditText edittextemail;
    private EditText edittextpassword;
    private Button buttonsignin;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null) {
            startActivity(new Intent(getApplicationContext(),Main_Page.class));
            finish();
        }

        edittextemail = (EditText)findViewById(R.id.emaillogin);
        edittextpassword =(EditText)findViewById(R.id.password);
        buttonsignin = (Button)findViewById(R.id.buttonsignin);

        progressDialog = new ProgressDialog(this);

        buttonsignin.setOnClickListener(this);


    }



    @Override
    public void onClick(View view)
    {
        if (view == buttonsignin)
        {
            userLogin();
        }
    }

    private void userLogin()
    {
        String email = edittextemail.getText().toString().trim();
        String password = edittextpassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){

            Toast.makeText(this,"Please Enter Email-Id",Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){

                            Toast.makeText(AdminLogin.this, "Logged In Sucessfully" , Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),Main_Page.class));
                            finish();
                        }
                        else
                        {
                            String message = task.getException().getMessage();
                            Toast.makeText(AdminLogin.this, "Error" + message, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
