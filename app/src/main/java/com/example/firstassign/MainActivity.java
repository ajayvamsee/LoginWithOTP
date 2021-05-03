package com.example.firstassign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {



//variable for FirebaseAuth class
    private FirebaseAuth mAuth;
    //variable for our text input field for phone and OTP.
    private EditText edtPhone,edtOTP;
    //buttons for generating OTP and verifying OTP
    private Button verifyOTPBtn,generateOTPBtn;
    //string for storing our verification ID
    private String verificationId;

	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);


        //below line is for getting instance of our FirebaseAuth.
        mAuth = FirebaseAuth.getInstance();
        //initializing variables for button and Edittext.
        edtPhone = findViewById(R.id.idEdtPhoneNumber);
        generateOTPBtn = findViewById(R.id.idBtnGetOtp);

//setting onclick listner for generate OTP button.
        generateOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String moblie = edtPhone.getText().toString().trim();
                //below line is for checking weather the user has entered his mobile number or not.
                if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    //when mobile number text field is empty displaying a toast message.
                    edtPhone.setError("Enter valid Mobile Number");
                    edtPhone.requestFocus();
                    Toast.makeText(MainActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();

                } else {
                    //if the text field is not empty we are calling our send OTP method for gettig OTP from Firebase.
                    Intent intentOtp = new Intent(MainActivity.this, VerifyOtp.class);
                    intentOtp.putExtra("moblienumber", moblie);
                    startActivity(intentOtp);


                    //  sendVerificationCode(phone);
                }
            }
        });




    }
        
}
