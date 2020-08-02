package com.london.touristapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.london.touristapp.Adapter.AttractionAdapter;
import com.london.touristapp.Adapter.ReviewAdapter;
import com.london.touristapp.Constants;
import com.london.touristapp.Model.ModelAttraction;
import com.london.touristapp.Model.ModelReview;
import com.london.touristapp.R;

import java.util.ArrayList;

public class ReadReviews extends AppCompatActivity {


        ProgressDialog pb;  //Progress Dialog

        ArrayList<ModelReview> list=new ArrayList<>();  //List Contains All data

        RecyclerView recyclerView; //List Of Attraction


        String ID,Name;
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_read_reviews);

            recyclerView=findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


            if(getIntent().getExtras()==null)
            {
                Toast.makeText(ReadReviews.this,"No Data",Toast.LENGTH_SHORT).show();
                finish(); //Close This Activity
            }
            else {


                ID = getIntent().getExtras().getString(Constants.EXTRA_ID);
                Name = getIntent().getExtras().getString(Constants.EXTRA_NAME);
            }







            if(getSupportActionBar()!=null)
            {
                getSupportActionBar().setTitle(Name);   //Set title on toolbar
                getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Enable backbutton
                getSupportActionBar().setDisplayShowHomeEnabled(true); //Show backbutton on toolbar
            }
            pb=new ProgressDialog(this);

            loadData();   //Load All data from Database
        }

        private void loadData()
        {


            pb.setMessage("Please Wait Loading Data...");
            pb.setCancelable(false);
            pb.show();

        /*

        Loading Data
         */
            FirebaseDatabase.getInstance().getReference("Reviews").child(ID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {

                /*
                dataSnapshot variables contains all data we will loop on it to get all data one by one and will save in the list
                 */

                    for(DataSnapshot singleData:dataSnapshot.getChildren())
                    {

                        ModelReview review = singleData.getValue(ModelReview.class);
                        list.add(review);

                    }

                    pb.dismiss();
                    if(list.size()>0)  //If List Has Some Data
                    {

                        // Set data on the RecyclerView
                        recyclerView.setAdapter(new ReviewAdapter(ReadReviews.this,list));

                    }
                    else
                    {

                        Toast.makeText(ReadReviews.this,"No Data Found",Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {


                    Toast.makeText(ReadReviews.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });



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
    }