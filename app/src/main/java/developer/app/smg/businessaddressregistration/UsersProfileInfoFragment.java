package developer.app.smg.businessaddressregistration;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersProfileInfoFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    private TextView Name, Email;
    public UsersProfileInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_profile_info, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        Name = view.findViewById(R.id.tVuserName);
        Email = view.findViewById(R.id.tvUserEmmail);
        //LoadUserInformation();

    }

    /*@Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(getContext(), MainActivity.class));
        }
        else {
            return;
        }
    }*/
    private void LoadUserInformation() {
        if (mAuth.getCurrentUser() != null){
            firebaseUser = mAuth.getCurrentUser();
            String userEmail = firebaseUser.getEmail().toLowerCase();
            String userName = firebaseUser.getDisplayName().toUpperCase();

            if (firebaseUser != null){
                if (firebaseUser.getDisplayName() != null){
                    Name.setText("Welcome: " + userName);
                    Email.setText("Email: " + userEmail);
                }
                if(firebaseUser.getEmail() != null)    {
                    Email.setText(userEmail.toString());
                    Toast.makeText(getContext(), "I have no user name on this email address", Toast.LENGTH_LONG).show();
                }

            }
        }
    }
}
