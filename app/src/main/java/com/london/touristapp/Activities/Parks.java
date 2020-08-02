package com.london.touristapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.london.touristapp.Adapter.AttractionAdapter;
import com.london.touristapp.Adapter.MuseumAdapter;
import com.london.touristapp.Adapter.ParksAdapter;
import com.london.touristapp.Model.ModelAttraction;
import com.london.touristapp.Model.ModelMuseum;
import com.london.touristapp.Model.ModelPark;
import com.london.touristapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Parks extends AppCompatActivity
{


    ProgressDialog pb;  //Progress Dialog

    ArrayList<ModelPark> list=new ArrayList<>();  //List Contains All data

    RecyclerView recyclerView; //List Of Attraction
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parks);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));






        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setTitle("Parks and green spaces");   //Set title on toolbar
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
        FirebaseDatabase.getInstance().getReference("Parks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                list.clear();//Clear List

                /*
                dataSnapshot variables contains all data we will loop on it to get all data one by one and will save in the list
                 */

                for(DataSnapshot singleData:dataSnapshot.getChildren())
                {

                    ModelPark parks = singleData.getValue(ModelPark.class);
                    parks.setID(singleData.getKey());
                    list.add(parks);

                }

                pb.dismiss();
                if(list.size()>0)  //If List Has Some Data
                {

                    // Set data on the RecyclerView
                    recyclerView.setAdapter(new ParksAdapter(Parks.this,list));

                }
                else
                {

                    Toast.makeText(Parks.this,"No Data Found",Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


                Toast.makeText(Parks.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
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

    public void onclick(View view)
    {

        startActivity(new Intent(Parks.this,AddPark.class));

    }
}