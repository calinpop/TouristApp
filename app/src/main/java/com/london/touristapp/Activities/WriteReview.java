package com.london.touristapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.london.touristapp.Constants;
import com.london.touristapp.Model.ModelReview;
import com.london.touristapp.R;
import com.squareup.picasso.Picasso;

public class WriteReview extends AppCompatActivity {


    ProgressDialog pb;
    EditText edttxtReview;
    String ID,Name;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        edttxtReview=findViewById(R.id.edttxtReview);

        pb=new ProgressDialog(this);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Enable backbutton
            getSupportActionBar().setDisplayShowHomeEnabled(true); //Show backbutton on toolbar
        }

        getExtras(); //Get Data Passed By Previous Activity


    }

    private void getExtras()
    {

        if(getIntent().getExtras()==null) //If No Data is passed by previous Activity
        {
            Toast.makeText(WriteReview.this,"No Data",Toast.LENGTH_SHORT).show();
            finish(); //Close This Activity
        }
        else
        {



            ID=getIntent().getExtras().getString(Constants.EXTRA_ID);
            Name=getIntent().getExtras().getString(Constants.EXTRA_NAME);

            if(getSupportActionBar()!=null) {
                getSupportActionBar().setTitle(Name);   //Set title on toolbar
            }

        }

    }

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
    public void onclick(View view)
    {


        if(TextUtils.isEmpty(edttxtReview.getText().toString())) //Check If User Review Is Empty
        {

            edttxtReview.setError("Required Field");  //If Field Is Empty Show error
            edttxtReview.requestFocus();  //Focus On Email Field

        }
        else
        {


            SaveReviewToDatabase();
        }


    }

    private void SaveReviewToDatabase()
    {

        pb.setMessage(getString(R.string.please_wait_submiting_review));
        pb.setCancelable(false);
        pb.show();


        ModelReview modelReview=new ModelReview();
        modelReview.setPubEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        modelReview.setReview(edttxtReview.getText().toString());


        FirebaseDatabase.getInstance().getReference("Reviews").child(ID).child( FirebaseDatabase.getInstance().getReference("Reviews").child(ID).push().getKey()).setValue(modelReview).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                pb.dismiss();
                if(task.isSuccessful())
                {
                    Toast.makeText(WriteReview.this,"Thanks For Your Review",Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
                else
                {
                    Toast.makeText(WriteReview.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}