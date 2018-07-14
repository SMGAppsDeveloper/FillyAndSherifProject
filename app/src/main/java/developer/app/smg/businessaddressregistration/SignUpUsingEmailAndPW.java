package developer.app.smg.businessaddressregistration;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpUsingEmailAndPW extends Activity {

    private static final int REQUEST_CODE = 1000;
    private EditText txtFname, txtMname, txtLname, txtEmail, txtPhone, txtLat, txtLong, txtpassWord;
    private Button btnSaveProfile, btngetGPSLocation;
    private String userID;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    ProgressBar progressBar;
    double latitude, longitude;
    FusedLocationProviderClient fusedLocationProviderClient;

    LocationRequest locationRequest;
    LocationCallback locationCallback;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //Toast.makeText(getApplicationContext(), "Thank you for granting location permission!", Toast.LENGTH_SHORT);

                    } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //Toast.makeText(getApplicationContext(), "You have denied permission request!", Toast.LENGTH_SHORT);
                    }
                }
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_using_email_and_pw);


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.signUpProgressbar);

        txtFname = findViewById(R.id.txtFirstName);
        txtMname = findViewById(R.id.txtMiddleName);
        txtLname = findViewById(R.id.txtLastName);
        txtEmail = findViewById(R.id.txtEmailAddress);
        txtPhone = findViewById(R.id.txtPhoneNumber);
        txtLat = findViewById(R.id.txtLatitude);
        txtLong = findViewById(R.id.txtLongtude);
        txtpassWord = findViewById(R.id.txtUserPassword);

        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btngetGPSLocation = findViewById(R.id.btnGetLocation);

        //showSettingAlert();

        /*GPSTracker g = new GPSTracker(getApplication());
        Location l = g.getLocation();
        if(l != null){
            latitude = l.getLatitude();
            longitude = l.getLongitude();
            Toast.makeText(getApplication(), "Current location info " + " \n" + "Lat: " + latitude + " \n" + "Long: " + longitude, Toast.LENGTH_LONG).show();
        }

        txtLat.setText(""+ latitude);
        txtLong.setText(""+ longitude);*/

        //check permission runtime
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        } else {
            //if permission is granted
            buildLocationRequest();
            buildLocationCallBack();

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                return;
            }
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }
        btngetGPSLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTracker g = new GPSTracker(getApplication());
                Location l = g.getLocation();
                if(l != null){
                    latitude = l.getLatitude();
                    longitude = l.getLongitude();
                    //Toast.makeText(getApplication(), "Current location info " + " \n" + "Lat: " + latitude + " \n" + "Long: " + longitude, Toast.LENGTH_LONG).show();
                }
                txtLat.setText(""+ latitude);
                txtLong.setText(""+ longitude);
            }
        });
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = txtFname.getText().toString();
                String mname = txtMname.getText().toString();
                String lname = txtLname.getText().toString();
                String email = txtEmail.getText().toString();
                String phone = txtPhone.getText().toString();
                String lat = txtLat.getText().toString();
                String longt = txtLong.getText().toString();
                String pWord = txtpassWord.getText().toString();
                if (!ValidateInputs(fname,mname,lname, email,phone,lat,longt,pWord )){

                    //userID = auth.getCurrentUser().getUid();
                    UsersProfileClass usersProfileClass = new UsersProfileClass(
                            fname, mname, lname, email, phone, lat, longt
                    );

                    CollectionReference profileInfo = db.collection("UsersProfileInfo");

                    //profileInfo.document().collection(userID);

                    profileInfo.add(usersProfileClass).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            registerUser();
                            progressBar.setVisibility(View.VISIBLE);
                            txtFname.getText().clear();
                            txtMname.getText().clear();
                            txtLname.getText().clear();
                            txtEmail.getText().clear();
                            txtPhone.getText().clear();
                            txtLat.getText().clear();
                            txtLong.getText().clear();


                            Intent intent = new Intent(SignUpUsingEmailAndPW.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //intent.putExtra(auth.getCurrentUser().toString());
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Successfully Saved Profile Information!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }

        });
    }

    private void buildLocationCallBack() {
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for(Location location:locationResult.getLocations()){
                    txtLat.setText(String.valueOf(location.getLatitude())
                    );
                    txtLong.setText(String.valueOf(location.getLongitude())
                    );
                }
            }
        };
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10);

    }

    private String registerUser() {
        String email = txtEmail.getText().toString();
        String passWord = txtpassWord.getText().toString();
        auth.createUserWithEmailAndPassword(email, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    finish();
                    startActivity(new Intent(SignUpUsingEmailAndPW.this, MainActivity.class));
                    Toast.makeText(getApplicationContext(), "Profile Created successfully!",Toast.LENGTH_LONG).show();
                }
                else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "There is a user with this email " + txtEmail.getText(), Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        return auth.getUid();
    }

    private boolean ValidateInputs(String fname, String mname, String lname, String email, String phone, String Lat, String Long, String Pword){

        if (fname.isEmpty()){
            txtFname.setError("First Name Required");
            txtFname.requestFocus();
            return  true;
        }

        if (mname.isEmpty()){
            txtMname.setError("Middle Name Required");
            txtMname.requestFocus();
            return  true;
        }

        if (lname.isEmpty()){
            txtLname.setError("Last Name Required");
            txtLname.requestFocus();
            return  true;
        }

        if (email.isEmpty()){
            txtEmail.setError("email address Required");
            txtEmail.requestFocus();
            return  true;
        }

        if (phone.isEmpty()){
            txtPhone.setError("Phone Number Required");
            txtPhone.requestFocus();
            return  true;
        }

        if (Lat.isEmpty()){
            txtLat.setError("Latitude Required");
            txtLat.requestFocus();
            return  true;
        }

        if (Long.isEmpty()){
            txtLong.setError("Longitude Required");
            txtLong.requestFocus();
            return  true;
        }
        if (Pword.isEmpty()){
            txtpassWord.setError("Password Required");
            txtpassWord.requestFocus();
            return  true;
        }
        return false;
    }
}
