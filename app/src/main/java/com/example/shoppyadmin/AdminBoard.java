package com.example.shoppyadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminBoard extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button addnewshopbutton,btnlogout;
    private EditText inputshopname,inputshopdescryption,inputshopcategory;
    private ImageView inputshopimage;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String shopname,shopdescryption,shopcategory,sname,savecurrentdate,savecurrenttime;
    private String productrandomkey,downloadimageurl;
    private StorageReference productimagesref;
    private DatabaseReference shopsref;
    private ProgressDialog loadingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_board);

        addnewshopbutton = (Button) findViewById(R.id.btnaddshop);
        inputshopimage = (ImageView) findViewById(R.id.addshopimagebutton);
        inputshopname = (EditText) findViewById(R.id.shopname);
        inputshopdescryption = (EditText) findViewById(R.id.shopdescryption);
        inputshopcategory = (EditText) findViewById(R.id.shopcatgeroy);
        loadingbar = new ProgressDialog(this);

        btnlogout = (Button) findViewById(R.id.btnlogout);

        productimagesref = FirebaseStorage.getInstance().getReference().child("Shop Images");
        shopsref = FirebaseDatabase.getInstance().getReference().child("Add_Shop_Direct_From_Admin");

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

            inputshopimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    OpenGallery();
                }
            });
            addnewshopbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    ValidateShopData();
                }
            });

            btnlogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(AdminBoard.this,AdminLogin.class));
                }
            });



    }

    private void OpenGallery()
    {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode==RESULT_OK && data != null)
        {
            ImageUri = data.getData();
            inputshopimage.setImageURI(ImageUri);
        }
    }

    private void ValidateShopData()
    {
        shopdescryption = inputshopdescryption.getText().toString();
        shopcategory = inputshopcategory.getText().toString();
        sname = inputshopname.getText().toString();

        if(ImageUri == null)
        {
            Toast.makeText(AdminBoard.this,"Please Choose Image....",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(shopdescryption))
        {
            Toast.makeText(AdminBoard.this,"Please Write Product Descryption....",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(shopcategory))
        {
            Toast.makeText(AdminBoard.this,"Please Write Product Category....",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(sname))
        {
            Toast.makeText(AdminBoard.this,"Please Write Product Shop Name....",Toast.LENGTH_SHORT).show();
        }

        else
            {
                StoreShopInformation();
            }
    }

    private void StoreShopInformation()
    {
        /*loadingbar.setTitle("Addi New Shop");
        loadingbar.setMessage("Please Wait...Shop Is Adding");
        loadingbar.setCanceledOnTouchOutside(false);
        loadingbar.show();*/

        Intent intent = new Intent(AdminBoard.this,loadingbaranim.class);
        startActivity(intent);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
        savecurrentdate = currentdate.format(calendar.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime = currenttime.format(calendar.getTime());

        productrandomkey = savecurrentdate + savecurrenttime;

        final StorageReference filepath = productimagesref.child(ImageUri.getLastPathSegment() + productrandomkey + ".jpg");

        final UploadTask uploadTask = filepath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AdminBoard.this,"Error" + message,Toast.LENGTH_SHORT).show();
               // loadingbar.dismiss();

                Intent intent = new Intent(AdminBoard.this,loadingbaranim.class);
                startActivity(intent);
                finish();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminBoard.this,"Image Uploaded Sucessfully...",Toast.LENGTH_SHORT).show();

                Task<Uri> urltask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadimageurl = filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(AdminBoard.this,"Got Image Url Successfully",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AdminBoard.this,doneanim.class);
                            startActivity(intent);

                            SaveShopInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void SaveShopInfoToDatabase()
    {
        HashMap<String,Object> productmap = new HashMap<>();
        productmap.put("pid",productrandomkey);
        productmap.put("date",savecurrentdate);
        productmap.put("time",savecurrenttime);
        productmap.put("descryption",shopdescryption);
        productmap.put("image",downloadimageurl);
        productmap.put("category",shopcategory);
        productmap.put("shopname",sname);

        shopsref.child(productrandomkey).updateChildren(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    loadingbar.dismiss();
                    Intent intent = new Intent(AdminBoard.this,doneanim.class);
                    startActivity(intent);
                    Toast.makeText(AdminBoard.this,"Shop Is Added Sucessfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadingbar.dismiss();

                    Intent intent = new Intent(AdminBoard.this,loadingbaranim.class);
                    startActivity(intent);
                    finish();

                    String message = task.getException().toString();
                    Toast.makeText(AdminBoard.this,"Error" + message,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

