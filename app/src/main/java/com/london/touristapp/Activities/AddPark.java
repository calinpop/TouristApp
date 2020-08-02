package com.london.touristapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.london.touristapp.Model.ModelPark;
import com.london.touristapp.R;

import java.io.ByteArrayOutputStream;

public class AddPark extends AppCompatActivity
{


    ImageView imgUser;
    EditText editTextName,editTextLocation,editTextDesc;
    boolean isimageSelcted=false;

    ProgressDialog pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_park);

        imgUser=findViewById(R.id.imgUser);
        editTextName=findViewById(R.id.edittextName);
        editTextLocation=findViewById(R.id.edittextLocation);
        editTextDesc=findViewById(R.id.edittextDesc);


        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setTitle("Add Park");   //Set title on toolbar
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Enable backbutton
            getSupportActionBar().setDisplayShowHomeEnabled(true); //Show backbutton on toolbar
        }
        pb=new ProgressDialog(this);
    }

    public void onclick(View view)
    {

        if(view.getId()==R.id.btnSelect)  //Image selcet Button is pressed
        {


            /*
            Request for gallery to open so that user can select image
             */
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Choose Image"), 000);

        }
        if(view.getId()==R.id.btnSubmit)
        {

            if(!isimageSelcted)
            {
                Toast.makeText(AddPark.this,"Select Image",Toast.LENGTH_LONG).show();
                return;
            }
            if(TextUtils.isEmpty(editTextName.getText()))
            {

                editTextName.setError("Required Field");
                editTextName.requestFocus();
                return;
            }
            if(TextUtils.isEmpty(editTextLocation.getText()))
            {

                editTextLocation.setError("Required Field");
                editTextLocation.requestFocus();
                return;
            }
            if(TextUtils.isEmpty(editTextDesc.getText()))
            {

                editTextDesc.setError("Required Field");
                editTextDesc.requestFocus();
                return;
            }





            pb.setMessage("Uploading...");
            pb.setCancelable(false);
            pb.show();
            uploadFile();


        }

    }

    private void UploadToDB(String imageLink)
    {

        ModelPark obj=new ModelPark();
        obj.setDesc(editTextDesc.getText().toString());
        obj.setImageLink(imageLink);
        obj.setLocation(editTextLocation.getText().toString());
        obj.setName(editTextName.getText().toString());
        obj.setID(FirebaseDatabase.getInstance().getReference().child("Parks").push().getKey());

        FirebaseDatabase.getInstance().getReference().child("Parks").child(obj.getID()).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                pb.dismiss();
                if(task.isSuccessful())
                {
                    Toast.makeText(AddPark.this,"Data Uploaded Successfully",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(AddPark.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });



    }
    private void uploadFile() {

        Bitmap bitmap1 = ((BitmapDrawable) imgUser.getDrawable()).getBitmap();




        // Convertint image to bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();


        // Referenece to image storage
        StorageReference Ref = FirebaseStorage.getInstance().getReference("Images").child(System.currentTimeMillis()+".jpeg");

        UploadTask uploadTask = Ref.putBytes(data);  //Uplaoding image bytes to Firebase storage
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if(task.isSuccessful())
                {

                    /*
                    Image is uploaded now get image link
                     */


                    //Getting image link
                    task.getResult().getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task)
                        {

                            if(task.isSuccessful())
                            {

                                //Image link  found now upload remaining data to database
                                UploadToDB(task.getResult().toString());
                            }
                            else
                            {

                                pb.dismiss();
                                Toast.makeText(AddPark.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }

                        }
                    });


                }
                else
                {

                    pb.dismiss();
                    Toast.makeText(AddPark.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                }


            }
        });


    }
    /*

    This Method Run After User Select Image
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 000)
        {

            Uri data1 = data.getData();  //Get Image
            imgUser.setImageURI(data1);  //Set Image On ImageView
            isimageSelcted=true;  //Image selected is true

        }
    }


    /*
    This Method Will Run On Back Button Pressed
     */
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish(); //Close this activity
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}