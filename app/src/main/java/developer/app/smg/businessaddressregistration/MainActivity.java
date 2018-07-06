package developer.app.smg.businessaddressregistration;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomMainNav;
    private FrameLayout frameLayoutMain;
    private TextView txtUsrdName;
    private AccountProfileFragment accountProfileFragment;
    private BusinessesFragment businessesFragment;
    private RegistrationFragment registrationFragment;
    private Login fragmentLogin;
    private UsersProfileInfoFragment userprofileINfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        frameLayoutMain = (FrameLayout)findViewById(R.id.main_container_frame);
        bottomMainNav = (BottomNavigationView) findViewById(R.id.main_nav_menu);
        txtUsrdName = (TextView) findViewById(R.id.tVuserName);

        registrationFragment = new RegistrationFragment();
        businessesFragment = new BusinessesFragment();
        accountProfileFragment = new AccountProfileFragment();
        fragmentLogin = new Login();
        userprofileINfo = new UsersProfileInfoFragment();

        setFragment(userprofileINfo);

        bottomMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.business_list_nav:
                        bottomMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(businessesFragment);
                        return true;
                    case R.id.registration_nav:
                        bottomMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(registrationFragment);
                        return true;
                    case R.id.profile_nav:
                        bottomMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(accountProfileFragment);
                        return true;
                        /*if (txtUsrdName.getText() != null)
                        {
                            bottomMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                            setFragment(userprofileINfo);
                            return true;
                        }
                        else
                        {
                            bottomMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                            setFragment(accountProfileFragment);
                            return true;
                        }*/

                    case R.id.login_nav:

                        bottomMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(fragmentLogin);
                        return true;

                    default:
                        return false;
                }
            }
        });

    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container_frame, fragment);
        fragmentTransaction.commit();
    }
}
