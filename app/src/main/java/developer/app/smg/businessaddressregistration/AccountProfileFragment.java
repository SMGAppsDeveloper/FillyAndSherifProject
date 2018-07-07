package developer.app.smg.businessaddressregistration;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountProfileFragment extends Fragment {

/*    private EditText txtFname, txtMname, txtLname, txtEmail, txtPhone, txtLat, txtLong, txtpassWord;
    private Button btnSaveProfile, btnSignup;
    private String userID;
    private FirebaseFirestore db;
    private FirebaseAuth auth;*/

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

       /* db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        txtFname = view.findViewById(R.id.txtFirstName);
        txtMname = view.findViewById(R.id.txtMiddleName);
        txtLname = view.findViewById(R.id.txtLastName);
        txtEmail = view.findViewById(R.id.txtEmailAddress);
        txtPhone = view.findViewById(R.id.txtPhoneNumber);
        txtLat = view.findViewById(R.id.txtLatitude);
        txtLong = view.findViewById(R.id.txtLongtude);
        txtpassWord = view.findViewById(R.id.txtUserPassword);

        btnSaveProfile = view.findViewById(R.id.btnSaveProfile);*/


  /*      btnSaveProfile.setOnClickListener(new View.OnClickListener() {
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

                    registerUser();

                    userID = auth.getCurrentUser().getUid();
                    UsersProfileClass usersProfileClass = new UsersProfileClass(
                            fname, mname, lname, email, phone, lat,longt
                    );

                    CollectionReference profileInfo = db.collection( "UsersProfileInfo");

                    profileInfo.document().collection(userID);

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

        });*/


    }

    /*private String registerUser() {
        String email = txtEmail.getText().toString();
        String passWord = txtpassWord.getText().toString();
        auth.createUserWithEmailAndPassword(email, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(getContext(), "User Created successfully!",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        return auth.getUid();
    }*/

    /*private boolean ValidateInputs(String fname, String mname, String lname, String email, String phone, String Lat, String Long, String Pword){

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
    }*/
}
