package com.london.touristapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.london.touristapp.MainActivity;
import com.london.touristapp.R;

public class Login extends AppCompatActivity {



    EditText edttxtEmail,edttxtPassword;  //Edit Text UserEmail And Password

    ProgressDialog pb;  //Progress Bar Used When Loging
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edttxtEmail=findViewById(R.id.edttxtEmail); //BindView EditText Email
        edttxtPassword=findViewById(R.id.edittextPassword); //BindView  Edittext Password


        pb=new ProgressDialog(this);

    }


    // OnClick Listener For Button Sign In And Text Dont Have ANy account
    public void onclick(View view)
    {

        if(view.getId()==R.id.txtDontHaveAccount)  //If Clicked  View Is txtDontHave Account
        {
            finish();
            startActivity(new Intent(Login.this,Register.class));  // Open Register Class

        }

        if(view.getId()==R.id.btnSignIn) //If Clicked View Is Button SignIn
        {

            if(TextUtils.isEmpty(edttxtEmail.getText().toString())) //Check If User Email Is Empty
            {

                edttxtEmail.setError("Required Field");  //If Field Is Empty Show error
                edttxtEmail.requestFocus();  //Focus On Email Field

            }
            else
            if(TextUtils.isEmpty(edttxtPassword.getText().toString())) //Check If User Password Is Empty
            {

                edttxtPassword.setError("Required Field");  //If Field Is Empty Show error
                edttxtPassword.requestFocus();  //Focus On Password Field

            }
            else

                // If Both Fields Are Not Empty Then Try To Login
            {


                pb.setMessage("Please Wait Logging In..");
                pb.setCancelable(false);
                pb.show();
                login();

            }



        }



    }

    private void login()
    {


        // We Are Using Firebase Authentication To Login

        // This Firebase Method (signInWithEmailAndPassword)  Takes user email And Password And Check if there is any account with these credentials
        FirebaseAuth.getInstance().signInWithEmailAndPassword(edttxtEmail.getText().toString().trim(),edttxtPassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) //If credentials Are Correct
                {
                    finish(); //Close This Activity
                    startActivity(new Intent(Login.this, MainActivity.class));  //Go To MainClass
                }
                else
                {

                    pb.dismiss();
                    //if Task is UnSuccessfull it means email or password is incorect or there is issue with in setting up firebase
                    // We will get exception  message from task variable  and will show as toast
                    Toast.makeText(Login.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }

            }
        });



    }
}