package developer.app.smg.businessaddressregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getActionBar().setTitle("Login");
        mAuth = FirebaseAuth.getInstance();

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        userEmail = (EditText)findViewById(R.id.txtLoginEmailAdd);
        userPassword = (EditText)findViewById(R.id.txtLoginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button)findViewById(R.id.btnUserSignup);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEMailAddressValue = userEmail.getText().toString();
                String userPasswordValue = userPassword.getText().toString();

                if (!ValidateInputs(userEMailAddressValue, userPasswordValue)){

                    progressBar.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(userEMailAddressValue, userPasswordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            progressBar.setVisibility(View.GONE);


                            if (task.isSuccessful()){

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                               /* Toast.makeText(getApplicationContext(), "Successfully Logged In!  " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_LONG);
                                userEmail.getText().clear();
                                userPassword.getText().clear();*/
                            }
                            else    {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG);

                            }

                        }
                    });
                }
            }
        });
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
