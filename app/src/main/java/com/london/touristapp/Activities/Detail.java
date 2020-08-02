package com.london.touristapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.london.touristapp.Constants;
import com.london.touristapp.R;
import com.squareup.picasso.Picasso;

public class Detail extends AppCompatActivity {



    String ID,Name,Detail,Image,TicketLink;

    TextView txtDetail;
    TextView txtPlaceName;
    ImageView imgPlace;
    Button btnDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtDetail=findViewById(R.id.txtDetail);
        txtPlaceName=findViewById(R.id.txtPlaceName);
        imgPlace = findViewById(R.id.image);

        getExtras(); //Get Data Passed By Previous Activity



        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Enable backbutton
            getSupportActionBar().setDisplayShowHomeEnabled(true); //Show backbutton on toolbar
        }
    }

    private void getExtras()
    {

        if(getIntent().getExtras()==null) //If No Data is passed by previous Activity
        {
            Toast.makeText(Detail.this,"No Data",Toast.LENGTH_SHORT).show();
            finish(); //Close This Activity
        }
        else
        {



            ID=getIntent().getExtras().getString(Constants.EXTRA_ID);
            Name=getIntent().getExtras().getString(Constants.EXTRA_NAME);
            Detail=getIntent().getExtras().getString(Constants.EXTRA_DETAIL);
            Image=getIntent().getExtras().getString(Constants.EXTRA_IMAGE);
            TicketLink=getIntent().getExtras().getString(Constants.EXTRA_TICKET_LINK);






            if(getSupportActionBar()!=null) {
                getSupportActionBar().setTitle(Name);   //Set title on toolbar
            }


            txtDetail.setText(Detail);
            txtPlaceName.setText(Name);
            Picasso.get().load(Image).placeholder(R.drawable.img_placeholder).into(imgPlace);



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

        if(view.getId()==R.id.btnAddreview)
        {
            startActivity(new Intent(Detail.this,WriteReview.class).putExtras(getIntent().getExtras()));
        }
        if(view.getId()==R.id.btnReadReviews)
        {
            startActivity(new Intent(Detail.this,ReadReviews.class).putExtras(getIntent().getExtras()));
        }
        if(view.getId()==R.id.btnBookTour)
        {
            try{
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(TicketLink));
                startActivity(browserIntent);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(Detail.this,"No Ticket Link Found",Toast.LENGTH_LONG).show();
            }

        }


    }
}