package developer.app.smg.businessaddressregistration;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomMainNav;
    private FrameLayout mainFrameLayout;
    private UsersProfileInfoFragment accountProfileFragment;
    private BusinessesFragment businessesFragment;
    private RegistrationFragment registrationFragment;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        bottomMainNav = findViewById(R.id.main_nav_menu);
        registrationFragment = new RegistrationFragment();
        businessesFragment = new BusinessesFragment();
        accountProfileFragment = new UsersProfileInfoFragment();
        mapFragment = new MapFragment();

        setFragment(businessesFragment);


        bottomMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.business_list_nav:
                        bottomMainNav.setItemBackgroundResource(R.color.colorPrimary);
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
                    case R.id.map_nav:
                        bottomMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(mapFragment);
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
