package com.example.firstassign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.concurrent.TimeUnit;

public class VerifyOtp extends AppCompatActivity {


    //These are the objects needed
    //It is the verification id that will be sent to the user
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken resendingToken;

    //The edittext to input the code
    private EditText editTextCode;

    Button btnsignIN;

    // display user number
    TextView moblieNumber;
    // for resend otp
    Button resendBtn;

    //firebase auth object
    private FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        //loginCurrentUser();

        btnsignIN = findViewById(R.id.buttonSignIn);
        //initializing objects
        mAuth = FirebaseAuth.getInstance();
        editTextCode = findViewById(R.id.editTextCode);

        moblieNumber = findViewById(R.id.mobileNum);
        resendBtn = findViewById(R.id.resendCode);


        //get moblie number thruogh last activty by intent
        Intent intent = getIntent();
        String mobile = intent.getStringExtra("moblienumber");

        moblieNumber.setText(mobile);

        Log.d("Print Moblie Number",mobile);

        try {
            // calling the method to send verification code provide number by the USer
            sendVerificationCode(mobile);


            // catching some Exception here
        } catch (NumberParseException e) {

            e.printStackTrace();
        }


        //if the sms auto detection not work properly we can go through button
        btnsignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = editTextCode.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    editTextCode.setError("Enter Valid Otp");
                    editTextCode.requestFocus();
                    //return;

                } else {

                    //verify the code entered manually
                    verifyVerificationCode(code);
                }


            }
        });


    }

    private void loginCurrentUser() {

        FirebaseUser alreadyUser = FirebaseAuth.getInstance().getCurrentUser();
        if (alreadyUser != null) {
            Intent intent = new Intent(VerifyOtp.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "No user exits ", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(VerifyOtp.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            //Toast.makeText(this, "User Logout", Toast.LENGTH_SHORT).show();
            Log.d("Logout", "user Logout");
        }
    }


    private void sendVerificationCode(String mobile) throws NumberParseException {

        // these lines are used to add country code format to number which
        // is suuported only in india bcoz we are delared direclty India code +91 in code.

        PhoneNumberUtil pnu = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber pn = pnu.parse(mobile, "IN");
        String pnE164 = pnu.format(pn, PhoneNumberUtil.PhoneNumberFormat.E164);


        PhoneAuthProvider.getInstance().verifyPhoneNumber(pnE164, 60, TimeUnit.SECONDS, this, mCallbacks);
    }


    //the callback to detect the verification status
    final private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                    //Getting the code sent by SMS
                    String code = phoneAuthCredential.getSmsCode();

                    //sometime the code is not detected automatically
                    //in this case the code will be null
                    //so user has to manually enter the code
                    if (code != null) {
                        editTextCode.setText(code);
                        //verifying the code
                        verifyVerificationCode(code);
                    } else {
                        Toast.makeText(VerifyOtp.this, "No Otp", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(VerifyOtp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("NumberFormat", e.getMessage());
                }

                // these below two methods is setting otp automatically

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);

                    //storing the verification id that is sent to the user
                    mVerificationId = s;

                    resendingToken = forceResendingToken;
                }

                @Override
                public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                    super.onCodeAutoRetrievalTimeOut(s);
                }

            };


    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyOtp.this, new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //verification successful we will start the profile activity
                            Intent intent = new Intent(VerifyOtp.this, HomeActivity.class);

                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = " Sorry ..! \n Somthing is went wrong, we will fix it very soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                }
                            });
                            snackbar.show();
                        }
                    }
                });
    }
}