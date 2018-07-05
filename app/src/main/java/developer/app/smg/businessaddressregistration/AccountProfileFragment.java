package developer.app.smg.businessaddressregistration;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountProfileFragment extends Fragment {

    private EditText txtFname, txtMname, txtLname, txtEmail, txtPhone, txtLat, txtLong;
    private Button btnSaveProfile;

    private FirebaseFirestore db;

    public AccountProfileFragment() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_profile, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();

        txtFname = view.findViewById(R.id.txtFirstName);
        txtMname = view.findViewById(R.id.txtMiddleName);
        txtLname = view.findViewById(R.id.txtLastName);
        txtEmail = view.findViewById(R.id.txtEmailAddress);
        txtPhone = view.findViewById(R.id.txtPhoneNumber);
        txtLat = view.findViewById(R.id.txtLatitude);
        txtLong = view.findViewById(R.id.txtLongtude);
        btnSaveProfile = view.findViewById(R.id.btnSaveProfile);

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

                if (!ValidateInputs(fname,mname,lname, email,phone,lat,longt)){
                    CollectionReference profileInfo = db.collection("UsersProfileInfo");

                    UsersProfileClass usersProfileClass = new UsersProfileClass(
                            fname, mname, lname, email, phone, lat,longt
                    );

                    profileInfo.add(usersProfileClass).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getContext(), "Successfully Saved Profile Information!",Toast.LENGTH_LONG).show();
                            txtFname.getText().clear();
                            txtMname.getText().clear();
                            txtLname.getText().clear();
                            txtEmail.getText().clear();
                            txtPhone.getText().clear();
                            txtLat.getText().clear();
                            txtLong.getText().clear();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }



            }
        });





    }

    private boolean ValidateInputs(String fname, String mname, String lname, String email, String phone, String Lat, String Long){

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
        return false;
    }
}
