package developer.app.smg.businessaddressregistration;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomMainNav;
    private FrameLayout frameLayoutMain;

    private AccountProfileFragment accountProfileFragment;
    private BusinessesFragment businessesFragment;
    private RegistrationFragment registrationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayoutMain = (FrameLayout)findViewById(R.id.main_container_frame);
        bottomMainNav = (BottomNavigationView) findViewById(R.id.main_nav_menu);

        registrationFragment = new RegistrationFragment();
        businessesFragment = new BusinessesFragment();
        accountProfileFragment = new AccountProfileFragment();

        bottomMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.business_list_nav:
                        bottomMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(businessesFragment);
                        return true;
                    case R.id.registration_nav:
                        bottomMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(registrationFragment);
                        return true;
                    case R.id.profile_nav:
                        bottomMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(accountProfileFragment);
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
