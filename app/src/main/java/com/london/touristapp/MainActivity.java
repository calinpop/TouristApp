package com.london.touristapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.london.touristapp.Activities.Attraction;
import com.london.touristapp.Activities.Login;
import com.london.touristapp.Activities.Museum;
import com.london.touristapp.Activities.Parks;

public class MainActivity extends AppCompatActivity {


    TextView txtWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtWelcome=findViewById(R.id.txtWelcome);


        txtWelcome.setText(String.format("Welcome  : %s", FirebaseAuth.getInstance().getCurrentUser().getEmail()));
    }

    public void onclick(View view)// This  Method Will Run When Any Of Button clicked (Attractioc,Museum,Parks And Green Spaces)
    {

            if(view.getId()==R.id.Card_attractions) //If Attraction Buttion is Clicked
            {
                startActivity(new Intent(MainActivity.this, Attraction.class));  //Go To Attraction Class

            }

        if(view.getId()==R.id.Card_Musems) //If Museum Buttion is Clicked
        {
            startActivity(new Intent(MainActivity.this, Museum.class));  //Go To Museum Class

        }


        if(view.getId()==R.id.Card_Park) //If Museum Buttion is Clicked
        {
            startActivity(new Intent(MainActivity.this, Parks.class));  //Go To Museum Class

        }
        if(view.getId()==R.id.Card_Logout) //If LogOut Buttion is Clicked
        {

            ActivityCompat.finishAffinity(this);
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, Login.class));  //Go To Museum Class

        }
    }
}