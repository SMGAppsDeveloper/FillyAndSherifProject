package developer.app.smg.businessaddressregistration;


import android.location.Location;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {


    private EditText txtContactPerson;
    private EditText txtBusinessName;
    private EditText txtBusinessPhone;
    private EditText txtBuildingName;
    private EditText txtFloorNumber;
    private EditText txtLatitude;
    private EditText txtLongitude;
    private Button btnSaveData;
    private FirebaseFirestore db;
    double latitude, longitude;
    private FusedLocationProviderClient mFusedLocationClient;

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        //getLastLocation();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        db = FirebaseFirestore.getInstance();
        txtContactPerson = view.findViewById(R.id.businessOwnerFullName);
        txtBusinessName = view.findViewById(R.id.businessNameValue);
        txtBusinessPhone = view.findViewById(R.id.businessPhoneNumber);
        txtBuildingName = view.findViewById(R.id.businessBuildingName);
        txtFloorNumber = view.findViewById(R.id.businessBuildingFloorNumber);
        txtLatitude = view.findViewById(R.id.addresslatitude);
        txtLongitude = view.findViewById(R.id.addresslongitude);
        btnSaveData = view.findViewById(R.id.saveBusinessData);

        GPSTracker g = new GPSTracker(getContext());
        Location l = g.getLocation();
        if(l != null){
             latitude = l.getLatitude();
             longitude = l.getLongitude();
            Toast.makeText(getContext(), "Current location info " + " \n" + "Lat: " + latitude + " \n" + "Long: " + longitude, Toast.LENGTH_LONG).show();
        }

        txtLatitude.setText(""+ latitude);
        txtLongitude.setText(""+longitude);


        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String OwnerName = txtContactPerson.getText().toString();
                String BusinessName = txtBusinessName.getText().toString();
                String BusinessPhone = txtBusinessPhone.getText().toString();
                String BuildingName = txtBuildingName.getText().toString();
                String FloorNumber = txtFloorNumber.getText().toString();
                String Latitude = "" + latitude;
                String Longitude = "" + longitude;

                if (!ValidateInputs(OwnerName, BusinessName, BusinessPhone, BuildingName, FloorNumber, Latitude, Longitude)) {

                    CollectionReference businessInfo = db.collection("AddissAbabaBusinessList");

                    AddisAbabaBusinessLocationsClass addisAbabaBusinessLocationsClass = new AddisAbabaBusinessLocationsClass(
                            OwnerName,
                            BusinessName,
                            BusinessPhone,
                            BuildingName,
                            FloorNumber,
                            Latitude,
                            Longitude
                    );

                    businessInfo.add(addisAbabaBusinessLocationsClass).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getContext(), "Successfully Added!", Toast.LENGTH_LONG).show();

                            txtContactPerson.getText().clear();
                            txtBuildingName.getText().clear();
                            txtBusinessName.getText().clear();
                            txtBusinessPhone.getText().clear();
                            txtFloorNumber.getText().clear();
                            txtLatitude.getText().clear();
                            txtLongitude.getText().clear();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


                }
            }
        });

    }

    private boolean ValidateInputs(String Person, String BusName, String Phone, String BuldName, String Floor, String Lat, String Long) {

        if (Person.isEmpty()) {
            txtContactPerson.setError("Business Owner Required");
            txtContactPerson.requestFocus();
            return true;
        }

        if (BusName.isEmpty()) {
            txtBusinessName.setError("Business Name Required");
            txtBusinessName.requestFocus();
            return true;
        }

        if (Phone.isEmpty()) {
            txtBusinessPhone.setError("Business Phone Required");
            txtBusinessPhone.requestFocus();
            return true;
        }

        if (BuldName.isEmpty()) {
            txtBuildingName.setError("Building Name Required");
            txtBuildingName.requestFocus();
            return true;
        }

        if (Floor.isEmpty()) {
            txtFloorNumber.setError("Floor Number Required");
            txtFloorNumber.requestFocus();
            return true;
        }

        if (Lat.isEmpty()) {
            txtLatitude.setError("Latitude Required");
            txtLatitude.requestFocus();
            return true;
        }

        if (Long.isEmpty()) {
            txtLongitude.setError("Longitude Required");
            txtLongitude.requestFocus();
            return true;
        }
        return false;
    }
}
