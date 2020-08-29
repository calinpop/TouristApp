package com.london.touristapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.london.touristapp.MainActivity;
import com.london.touristapp.Model.ModelUser;
import com.london.touristapp.R;

public class Register extends AppCompatActivity {


    EditText edttxtEmail,editTextFirstName,editTextSurName,edttxtPassword;  //Edit Text UserEmail And Password


    ProgressDialog pb;  //Progress Bar Used When Creating account
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        

        edttxtEmail=findViewById(R.id.edttxtEmail); //BindView EditText Email
        edttxtPassword=findViewById(R.id.edittextPassword); //BindView  Edittext Password
        editTextFirstName=findViewById(R.id.edttxtFirstName); //BindView EditText Firstname
        editTextSurName=findViewById(R.id.edttxtSurName); //BindView  Edittext SurName
        editTextDOB=findViewById(R.id.edtxtDOB); //Blind Edittext FullName

        pb=new ProgressDialog(this);
    }

    public void onclick(View view)
    {


        if(view.getId()==R.id.txtAlreadyhaveAccount)
        {
           onBackPressed();
        }


        if(view.getId()==R.id.btnSignUp) //If Clicked View Is Button SignIn
        {

            if(TextUtils.isEmpty(edttxtEmail.getText().toString())) //Check If User Email Is Empty
            {

                edttxtEmail.setError("Required Field");  //If Field Is Empty Show error
                edttxtEmail.requestFocus();  //Focus On Email Field

            }
            else
            if(TextUtils.isEmpty(editTextFirstName.getText().toString())) //Check If User FirstName Is Empty
            {

                editTextFirstName.setError("Required Field");  //If Field Is Empty Show error
                editTextFirstName.requestFocus();  //Focus On FirstName Field

            }
            else
            if(TextUtils.isEmpty(editTextSurName.getText().toString())) //Check If User SurName Is Empty
            {

                editTextSurName.setError("Required Field");  //If Field Is Empty Show error
                editTextSurName.requestFocus();  //Focus On SurName Field

            }
            else
            if(TextUtils.isEmpty(edttxtPassword.getText().toString())) //Check If User Password Is Empty
            {

                edttxtPassword.setError("Required Field");  //If Field Is Empty Show error
                edttxtPassword.requestFocus();  //Focus On Password Field

            }
            else

            // If Both Fields Are Not Empty Then Try To Create Account
            {


                pb.setMessage("Please Wait Creating Account..");
                pb.setCancelable(false);  //User Cant Cancel It Manually
                pb.show();  //Show Loading

                createAccount();

            }



        }



    }

    private void createAccount()
    {


        // We Are Using Firebase Authentication To Create Account

        // This Firebase Method (createUserWithEmailAndPassword)  Takes user email And Password And Create Account

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(edttxtEmail.getText().toString(),edttxtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {



                if(task.isSuccessful()) //If Task Is Successfull
                {
                  // Account is Created Now We Will User Email First name And SurName To Datbase


                    SaveDataInDatabase();

                }
                else
                {

                    pb.dismiss();// Dismiss Progress Bar
                    //if Task is UnSuccessfull
                    // We will get exception  message from task variable  and will show as toast
                    Toast.makeText(Register.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void SaveDataInDatabase()
    {

        ModelUser userData=new ModelUser(); //User Model Object Which Contains Information Of User

        userData.setEmail(edttxtEmail.getText().toString());
        userData.setFirstName(editTextFirstName.getText().toString());
        userData.setSurName(editTextSurName.getText().toString());


        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {


                pb.dismiss();
                if(task.isSuccessful())
                {

                    finish(); //Close This Activity
                    startActivity(new Intent(Register.this, MainActivity.class));  //Go To MainClass
                }
                else
                {


                    Toast.makeText(Register.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();  //Close This Activity
        startActivity(new Intent(Register.this,Login.class));  //Open Login Class
    }
}
