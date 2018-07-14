package developer.app.smg.businessaddressregistration;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity {

    private EditText userEmail, userPassword;
    private Button btnLogin, btnSignup;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView lblForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        lblForgotPassword = findViewById(R.id.lblForgotCredential);
        progressBar = findViewById(R.id.loginProgressbar);
        userEmail = findViewById(R.id.txtLoginEmailAdd);
        userPassword = findViewById(R.id.txtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnUserSignup);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showSettingAlert();
                String userEMailAddressValue = userEmail.getText().toString();
                String userPasswordValue = userPassword.getText().toString();

                if (!ValidateInputs(userEMailAddressValue, userPasswordValue)) {

                    progressBar.setVisibility(View.VISIBLE);

                    LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                    boolean isGPSEnabled = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
                    if (isGPSEnabled){
                        mAuth.signInWithEmailAndPassword(userEMailAddressValue, userPasswordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    finish();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(), "Successfully Logged In!  ", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Please Enable GPS First!  ", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                boolean isGPSEnabled = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
                if (isGPSEnabled){
                    Intent intent = new Intent(LoginActivity.this, SignUpUsingEmailAndPW.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please Enable GPS First!  ", Toast.LENGTH_LONG).show();
                }
            }
        });

        lblForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userEmail.getText() != null){
                    userEmail.setError("Email is Required");
                    userEmail.requestFocus();
                }
                else {

                    mAuth.sendPasswordResetEmail("This is from Business List App to reset your password. Please check your email for instruction.");
                    Toast.makeText(getApplicationContext(), "Reset password instruction sent successfully", Toast.LENGTH_LONG);
                }

            }
        });
    }

    public void showSettingAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
        alertDialog.setTitle("GPS setting!");
        alertDialog.setMessage("GPS is not enabled, Do you want to go to settings menu? ");
        alertDialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getApplicationContext().startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
    private void buildAlertMessageNoGps() {
        Util util = new Util();
        util.displayPromptForEnablingGPS();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            finish();

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        else {

            Toast.makeText(getApplicationContext(), "You didn't sign in yet!, Please sign", Toast.LENGTH_LONG).show();
        }
    }

    private void LoadUserInformation() {
        /*firebaseUser = mAuth.getCurrentUser();
        String userEmail = firebaseUser.getEmail().toString().toLowerCase();
        String userName = firebaseUser.getDisplayName().toString().toUpperCase();

        if (firebaseUser != null) {
            if (firebaseUser.getDisplayName() != null) {
                Toast.makeText(getApplicationContext(), "Email: " + userName, Toast.LENGTH_LONG).show();
            }
            if (firebaseUser.getEmail() != null) {
                Toast.makeText(getApplicationContext(), "I have no user name on this email address", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Email: " + userEmail, Toast.LENGTH_LONG).show();
            }
            if (firebaseUser.isEmailVerified()) {
                tvUserVerifiedOrNot.setText("Email Verified!");
            } else {
                tvUserVerifiedOrNot.setText("Email not verified!");
                //bottomMainNav.setEnabled(false);
                tvUserVerifiedOrNot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(), "Verification Email Sent!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        }*/
    /*private void updateUI(FirebaseUser currentUser) {
        if (currentUser.getUid() == null){

            Intent intent = new Intent(LoginActivity.this, SignUpUsingEmailAndPW.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
*/
    }
    private boolean ValidateInputs(String email, String passWord){

        if (email.isEmpty()){
            userEmail.setError("Email is Required");
            userEmail.requestFocus();
            return  true;
        }

        if (passWord.isEmpty()){
            userPassword.setError("Password is Required");
            userPassword.requestFocus();
            return  true;
        }
        return false;
    }
}
